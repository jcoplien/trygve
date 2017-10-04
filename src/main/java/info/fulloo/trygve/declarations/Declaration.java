package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 2.0
 *   Copyright (c)2016 James O. Coplien, jcoplien@gmail.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.BuiltInType;
import info.fulloo.trygve.declarations.Type.ClassOrContextType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.ContextType;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.declarations.Type.VarargsType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.BreakExpression;
import info.fulloo.trygve.expressions.Expression.ContinueExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.MethodInvocationEnvironmentClass;
import info.fulloo.trygve.parser.Pass0Listener;
import info.fulloo.trygve.parser.Pass1Listener;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticRoleScope;

public abstract class Declaration implements BodyPart {
	public Declaration(final String name) {
		name_ = name;
	}
	public String name() {
		return name_;
	}
	@Override public abstract Type type();
	
	@Override public List<BodyPart> bodyParts() {
		return new ArrayList<BodyPart>();
	}
	
	public void addInSituInitializers(List<BodyPart> initializerList) {
		assert false;	// pure virtual, kind of
	}
	
	public boolean isError() {
		return false;
	}
	
	public boolean isntError() {
		return false == isError();
	}
	
	public static class ObjectDeclaration extends Declaration
	{
		public ObjectDeclaration(final String name, final Type type, final Token token) {
			super(name);
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
			type_ = type;
			accessQualifier_ = AccessQualifier.DefaultAccess;
		}
		public ObjectDeclaration copy() {
			final ObjectDeclaration retval = new ObjectDeclaration(name(), type_, token_);
			retval.setEnclosingScope(enclosingScope());
			return retval;
		}
		@Override public Type type() {
			return type_;
		}
		public void updateType(final Type newType) {
			// Used when later passes pick up forward
			// declarations and can come back to patch
			// things up
			type_ = newType;
		}
		@Override public String name() {
			return super.name();
		}
		@Override public String getText() {
			return name();
		}
		public void setAccess(final AccessQualifier accessLevel, final StaticScope currentScope, final Token token) {
			// This method sets the corresponding access level for this declaration, yes,
			// makes sense at all
			if (accessLevel != AccessQualifier.DefaultAccess) {
				assert null != currentScope;
				if (currentScope.associatedDeclaration() instanceof MethodDeclaration) {
					final ErrorIncidenceType errorType = accessLevel == AccessQualifier.PrivateAccess?
															ErrorIncidenceType.Warning:
															ErrorIncidenceType.Fatal;
					ErrorLogger.error(errorType, token,
							errorType.toString(),
							": Identifier `", name(),
							"' has a gratuitous access qualifier."
							);
				}
			} else {
				;	// Default access (no explicit field) is always O.K.
					// In roles it defaults to public to the Context scope
					// Otherwise it will most commonly default to
					// AccessQualifier.PrivateAccess, interpreted by logic elsewhere
			}
			accessQualifier_ = accessLevel;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		public void setEnclosingScope(final StaticScope scope) {
			containingScope_ = scope;
		}
		public StaticScope enclosingScope() {
			return containingScope_;
		}
		public void setInitialization(final Expression initialization) {
			// May be called repeatedly, once each for passes 2, 3 and 4
			initialization_ = initialization;
		}
		public Expression initializationExpression() {
			return initialization_;
		}

		private       Type type_;
		private final int lineNumber_;
		private final Token token_;
		private       StaticScope containingScope_;
		public        AccessQualifier accessQualifier_;
		private       Expression initialization_;
	}
	
	public static class TypeDeclarationCommon extends Declaration implements TypeDeclaration
	{
		public TypeDeclarationCommon(final String name, final Token token, final StaticScope myEnclosedScope) {
			super(name);
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
			myEnclosedScope_ = myEnclosedScope;
			stringToStaticObjectMap_ = new LinkedHashMap<String, ObjectDeclaration>();
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public StaticScope enclosedScope() {
			return myEnclosedScope_;
		}
		@Override public StaticScope enclosingScope() {
			return myEnclosedScope_.parentScope();
		}
		@Override public Type type() {
			return type_;
		}
		@Override public void declareStaticObject(final ObjectDeclaration declaration) {
			if (stringToStaticObjectMap_.containsKey(declaration.name())) {
				ErrorLogger.error(ErrorIncidenceType.Fatal, token_, "Duplicate declaration of static object ", declaration.name(), "", "");
			} else {
				stringToStaticObjectMap_.put(declaration.name(), declaration);
			}
		}
		@Override public ObjectDeclaration lookupStaticObjectDeclaration(final String name) {
			ObjectDeclaration retval = null;
			if (stringToStaticObjectMap_.containsKey(name)) {
				retval = stringToStaticObjectMap_.get(name);
			} else {
				ErrorLogger.error(ErrorIncidenceType.Fatal, token_, "Static object ", name, " not found in type ", name());
			}
			return retval;
		}
		@Override public String getText() {
			return name();
		}

		private final int lineNumber_;
		private final Token token_;
		protected     StaticScope myEnclosedScope_;
		protected     Type type_;
		private final Map<String, ObjectDeclaration> stringToStaticObjectMap_;
	}
	
	public static class ObjectSubclassDeclaration extends TypeDeclarationCommon {
		public ObjectSubclassDeclaration(final String name, final StaticScope myEnclosedScope, final ClassDeclaration baseClass, Token token) {
			super(name, token, myEnclosedScope);
			baseClass_ = baseClass;
			inSituInitializations_ = new ArrayList<BodyPart>();
		}
		public ClassDeclaration baseClassDeclaration() {
			return baseClass_;
		}
		public void addInSituInitializers(final List<BodyPart> initializerList) {
			inSituInitializations_.addAll(initializerList);
		}
		public List<BodyPart> inSituInitializations() {
			return inSituInitializations_;
		}
		
		protected final ClassDeclaration baseClass_;
		protected final List<BodyPart> inSituInitializations_;
	}
	
	public static class ClassOrContextDeclaration extends ObjectSubclassDeclaration implements TypeDeclaration {
		public ClassOrContextDeclaration(final String name, final StaticScope myEnclosedScope,
				final ClassDeclaration baseClass, Token token) {
			super(name, myEnclosedScope, baseClass, token);
		}
		public void doIImplementImplementsList(final Pass1Listener parser, final Token token) {
			assert null != type_;
			final List<InterfaceType> theInterfaceTypes = ((ClassOrContextType)type_).interfaceTypes();
			final int listSize = theInterfaceTypes.size();
			InterfaceType anInterfaceType = null;
			
			// Iterate through all the interfaces that I implement
			for (int i = 0; i < listSize; i++) {
				anInterfaceType = theInterfaceTypes.get(i);
				if (null == anInterfaceType) {
					parser.errorHook5p2(ErrorIncidenceType.Fatal, token,
							"Class `", name(), "' is using an undeclared interface: see other error messages", "");
				} else {
					final Map<String, List<MethodSignature>> selectorSignatureMap = anInterfaceType.selectorSignatureMap();
					
					// For each interface, iterate over the signatures it declares
					for (final Map.Entry<String, List<MethodSignature>> iter : selectorSignatureMap.entrySet()) {
						final String signatureMethodSelector = iter.getKey();
						final List<MethodSignature> signatures = iter.getValue();
						for (final MethodSignature anInterfaceSignature: signatures) {
							final ActualOrFormalParameterList parameterList = anInterfaceSignature.formalParameterList();
							final MethodDeclaration methodDecl = myEnclosedScope_.lookupMethodDeclarationIgnoringParameter(
									signatureMethodSelector, parameterList, "this",
									/* conversionAllowed = */ false);
							if (null == methodDecl) {
								parser.errorHook6p2(ErrorIncidenceType.Fatal, token,
										"Class `", name(), "' does not implement interface `", anInterfaceType.name(),
										"' because definition of `" + anInterfaceSignature.getText(), "' is missing in the class.");
							} else {
								// Check return type
								final Type methodReturnType = methodDecl.returnType();
								final Type signatureReturnType = anInterfaceSignature.returnType();
								if (methodReturnType.pathName().equals(signatureReturnType.pathName())) {
									continue; // is cool
								} else if (signatureReturnType.canBeConvertedFrom(methodReturnType)) {
									continue; // is also cool
								} else {
									parser.errorHook6p2(ErrorIncidenceType.Fatal, token,
											"Class `", name(), "' does not implement interface `", anInterfaceType.name(),
											"' because of a mismatch in the return type of `" + anInterfaceSignature.getText(), "'.");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static class ContextDeclaration extends ClassOrContextDeclaration implements TypeDeclaration
	{
		public ContextDeclaration(final String name, final StaticScope myEnclosedScope, final ContextDeclaration currentContext, Token token) {
			super(name, myEnclosedScope, StaticScope.globalScope().lookupClassDeclaration("Object"), token);
			parentContext_ = currentContext;
		}
		public void setType(final ContextType type) {
			type_ = type;
		}
		public ContextDeclaration parentContext() {
			return parentContext_;
		}
		public void setParentContext(ContextDeclaration parentContext) {
			parentContext_ = parentContext;
		}
		
		private ContextDeclaration parentContext_;
	}
	
	public static class ClassDeclaration extends ClassOrContextDeclaration implements TypeDeclaration
	{
		public ClassDeclaration(final String name, final StaticScope myEnclosedScope, final ClassDeclaration baseClass, final Token token) {
			super(name, myEnclosedScope, baseClass, token);
			templateDeclaration_ = null;
			methodsHaveBodyParts_ = false;
		}
		public void updateWithDataFrom(final ClassDeclaration newerDeclaration) {
			if (this != newerDeclaration) {
				// Should be the same. Just double-check.
				assert false;
				;
			}
		}
		public void setType(final Type t) {
			assert t instanceof ClassType || t instanceof BuiltInType;
			type_ = t;
		}
		public void elaborateFromTemplate(final TemplateDeclaration templateDeclaration,
				final TemplateInstantiationInfo newTypes,
				Token token) {
			templateDeclaration_ = templateDeclaration;
			
			// This turns a TemplateDeclaration into a class
			assert null == baseClass_ || baseClass_.type() instanceof ClassType;
			final ClassType baseClassType = null == baseClass_? null: (ClassType)baseClass_.type();
			
			myEnclosedScope_ = new StaticScope(enclosedScope(), "copy", templateDeclaration.enclosingScope(),
					this, newTypes);
			final ClassType newClassType = new ClassType(name(), myEnclosedScope_, baseClassType);
			newClassType.elaborateFromTemplate(templateDeclaration, baseClassType, myEnclosedScope_, this, token);
			this.setType(newClassType);
		}
		public TemplateDeclaration generatingTemplate() {
			return templateDeclaration_;
		}
		public boolean methodsHaveBodyParts() {
			return methodsHaveBodyParts_;
		}
		public void setMethodsHaveBodyParts(final boolean tf) {
			methodsHaveBodyParts_ = tf;
		}
		
		private TemplateDeclaration templateDeclaration_;
		private boolean methodsHaveBodyParts_;
	}
	
	public static class TypeParameter extends Object {
		public TypeParameter(final String name, final ClassType baseClassType) {
			name_ = name;
			baseClassType_ = baseClassType;
		}
		public ClassType baseClassDeclaration() {
			return baseClassType_;
		}
		public String name() {
			return name_;
		}
		public void setArgumentPosition(final int i) {
			argumentPosition_ = i;
		}
		public int argumentPosition() {
			return argumentPosition_;
		}
		
		final private String name_;
		final private ClassType baseClassType_;
		      private int argumentPosition_;
	}
	
	public static class TemplateDeclaration extends TypeDeclarationCommon
				implements TypeDeclaration
	{
		public TemplateDeclaration(final String name, final StaticScope myEnclosedScope, final TypeDeclaration baseClass, final Token token) {
			super(name, token, myEnclosedScope);
			baseClass_ = baseClass;
			argumentPositionCounter_ = 0;
			typeParameters_ = new ArrayList<TypeParameter>();
		}
		public void setType(final Type t) {
			assert t instanceof TemplateType;
			type_ = t;
		}
		public TypeDeclaration baseClass() {
			return baseClass_;
		}
		public List<TypeParameter> typeParameters() {
			return typeParameters_;
		}
		public TypeParameter typeParameterNamed(final String name) {
			TypeParameter retval = null;
			for (final TypeParameter tp : typeParameters_) {
				if (tp.name().equals(name)) {
					retval = tp;
					break;
				}
			}
			return retval;
		}
		public void addTypeParameter(final IdentifierExpression rawTypeParameter, final int numberOfTypeParameters) {
			final TemplateParameterType parameterType = (TemplateParameterType)rawTypeParameter.type();
			if (null == myEnclosedScope_.lookupTypeDeclaration(parameterType.name())) {
				final ClassType baseClassType = parameterType.baseClassType();
				final TypeParameter typeParameter = new TypeParameter(rawTypeParameter.name(),
						baseClassType);
				
				// These type parameters are added here in reverse order, so we
				// need to reverse the ordering. List<T, U> will call here first
				// with U and then with T. So squeegee the index. Positions are
				// zero-indexed in lexical order
				typeParameter.setArgumentPosition(numberOfTypeParameters - argumentPositionCounter_ - 1);
				argumentPositionCounter_++;
				typeParameters_.add(typeParameter);
			
				myEnclosedScope_.declareType(parameterType);
			}
		}
		
		protected final TypeDeclaration baseClass_;
		private   final List<TypeParameter> typeParameters_;
		private         int argumentPositionCounter_;
	}
	
	public static class InterfaceDeclaration extends TypeDeclarationCommon
			implements TypeDeclaration{
		public InterfaceDeclaration(final String name, final StaticScope enclosedScope, final Token token) {
			super(name, token, enclosedScope);
			signatures_ = new LinkedHashMap<String, MethodSignature>();
			methodsHaveBodyParts_ = false;
		}
		public void setType(final Type t) {
			assert t instanceof InterfaceType;
			type_ = t;
		}
		public List<MethodSignature> lookupMethodSignatureDeclaration(final String name) {
			final List<MethodSignature> retval = new ArrayList<MethodSignature>();
			retval.add(signatures_.get(name));
			return retval;
		}
		public MethodSignature lookupMethodSignatureDeclaration(final String methodSelectorName, final ActualOrFormalParameterList argumentList) {
			return ((InterfaceType)type_).lookupMethodSignature(methodSelectorName, argumentList);
		}
		public MethodSignature lookupMethodSignatureDeclarationInInterfaceDecl(final String methodSelectorName, final ActualOrFormalParameterList argumentList) {
			// FIXME: Design doesn't accommodate overloading?
			MethodSignature retval = null;
			if (signatures_.containsKey(methodSelectorName)) {
				retval = signatures_.get(methodSelectorName);
			}
			return retval;
		}
		public void addSignature(final MethodSignature signature) {
			signatures_.put(signature.name(), signature);
		}
		public Map<String, MethodSignature> signatureMap() {
			return signatures_;
		}
		public void elaborateFromTemplate(final TemplateDeclaration templateDeclaration,
				final TemplateInstantiationInfo newTypes,
				Token token) {
			// This turns a TemplateDeclaration into a class
			myEnclosedScope_ = new StaticScope(enclosedScope(), "copy", templateDeclaration.enclosingScope(),
					this, newTypes);
			
			// We don't yet allow base types for template interfaces — just use Object
			final ClassType objectType = (ClassType)StaticScope.globalScope().lookupTypeDeclaration("Object").type();
			final InterfaceType newInterfaceType = new InterfaceType(name(), myEnclosedScope_, true);
			newInterfaceType.elaborateFromTemplate(templateDeclaration, objectType, myEnclosedScope_, this, token);
			this.setType(newInterfaceType);
		}
		public boolean methodsHaveBodyParts() {
			return methodsHaveBodyParts_;
		}
		public void setMethodsHaveBodyParts(final boolean tf) {
			methodsHaveBodyParts_ = tf;
		}
		
		protected final Map<String, MethodSignature> signatures_;
		private boolean methodsHaveBodyParts_;
	}
	
	public static class RoleDeclaration extends TypeDeclarationCommon
			implements TypeDeclaration
	{
		public RoleDeclaration(final String name, final StaticScope myEnclosedScope, final ContextDeclaration context, final Token token) {
			super(name, token, myEnclosedScope);
			context_ = context;
			requiredSelfSignatures_ = new Hashtable<String, MethodSignature>();
			publishedSignatures_ = new Hashtable<String, MethodSignature>();
		}
		public ContextDeclaration contextDeclaration() {
			return context_;
		}
		public boolean requiresConstMethods() {
			return false;
		}
		public void setType(RoleType t) {
			type_ = t;
		}
		public MethodSignature lookupRequiredMethodSignatureDeclaration(final String name) {
			return requiredSelfSignatures_.get(name);
		}
		public MethodSignature lookupRequiredMethodSignatureDeclarationIgnoringParamAtPosition(final MethodSignature signature,
				final int paramIgnorePosition) {
			MethodSignature possibleSignature = requiredSelfSignatures_.get(signature.name());
			if (null != possibleSignature) {
				final FormalParameterList formalParameterList = possibleSignature.formalParameterList();
				final FormalParameterList actualParameterList = signature.formalParameterList();
				if (FormalParameterList.alignsWithParameterListIgnoringParamCommon(
						formalParameterList,
						actualParameterList, null, true, paramIgnorePosition)) {
					;		// O.K.
				} else {
					possibleSignature = null;
				}
			}
			return possibleSignature;
		}
		public MethodSignature lookupRequiredMethodSignatureDeclaration(final MethodSignature signature) {
			return lookupRequiredMethodSignatureDeclarationIgnoringParamAtPosition(signature, -1);
		}
		public boolean isAParameterlessRequiresMethod(final String methodSelectorName) {
			boolean retval = false;
			final MethodSignature methodSignature = this.lookupRequiredMethodSignatureDeclaration(methodSelectorName);
			if (null != methodSignature) {
				final FormalParameterList parameterList = methodSignature.formalParameterList();
				final int numberOfParameters = parameterList.count();
				retval = numberOfParameters == 1;
			}
			return retval;
		}
		public void addPublishedSignature(final MethodSignature signature) {
			publishedSignatures_.put(signature.name(), signature);
		}
		public MethodSignature lookupPublishedSignatureDeclaration(final String name) {
			return publishedSignatures_.get(name);
		}
		public MethodSignature lookupPublishedSignatureDeclaration(final MethodSignature otherSignature) {
			MethodSignature publishedSignature = publishedSignatures_.get(otherSignature.name());
				if (null != publishedSignature) {
					if (false == FormalParameterList.alignsWithParameterListIgnoringParamNamed(
							publishedSignature.formalParameterList(), otherSignature.formalParameterList(), "this", true)) {
						publishedSignature = null;
				}
			}
			return publishedSignature;
		}
		private boolean checkParameterConcretenessOf(final FormalParameterList pl,
				final Pass0Listener parserPass, final Token token) {
			boolean retval = true;
			final int numberOfParameters = pl.count();
			for (int i = 0; i < numberOfParameters; i++) {
				final Type paramType = pl.typeOfParameterAtPosition(i);
				final String paramName = pl.nameOfParameterAtPosition(i);
				if (paramName.equals("this")) {
					// Role methods quack out the type of the object
					continue;
				} else if (paramType instanceof RoleType) {
					parserPass.errorHook5p2(ErrorIncidenceType.Fatal, token,
							"You cannot require that a Role-player have a script that takes a Role argument such as `",
							paramType.name(), "'.", "");
					retval = false;
				}
			}
			return retval;
		}
		public void addRequiredSignatureOnSelf(final MethodSignature signature,
				final Pass0Listener parserPass) {
			// Make sure none of the parameters are role types
			checkParameterConcretenessOf(signature.formalParameterList(),
					parserPass, signature.token());
			requiredSelfSignatures_.put(signature.name(), signature);
		}
		public Map<String, List<MethodSignature>> requiredSelfSignatures() {
			final Map<String, List<MethodSignature> > retval = new LinkedHashMap<String, List<MethodSignature> >();
			for (final String methodName : requiredSelfSignatures_.keySet()) {
				final List<MethodSignature> signatureList = new ArrayList<MethodSignature>();
				signatureList.add(requiredSelfSignatures_.get(methodName));
				retval.put(methodName, signatureList);
			}
			return retval;
		}
		public void processRequiredDeclarations(final Token token) {
			// Declare requiredSelfSignatures_ in my scope
			assert enclosedScope() instanceof StaticRoleScope;
			final StaticRoleScope myEnclosedScope = (StaticRoleScope)this.enclosedScope();
			
			for (final Map.Entry<String, MethodSignature> signature : requiredSelfSignatures_.entrySet()) {
				final MethodSignature methodSignature = signature.getValue();
				final StaticScope pseudoScope = new StaticScope(myEnclosedScope);
				final MethodDeclaration methodDecl = new MethodDeclaration(methodSignature, pseudoScope, token);
				myEnclosedScope.declareRequiredMethod(methodDecl);
			}
		}
		public boolean isArray() {
			return false;
		}
		
		protected final ContextDeclaration context_;
		protected final Map<String, MethodSignature> requiredSelfSignatures_;
		protected final Map<String, MethodSignature> publishedSignatures_;
	}
	
	public static class RoleArrayDeclaration extends RoleDeclaration implements TypeDeclaration {
		public RoleArrayDeclaration(final String name, final StaticScope myEnclosedScope, final ContextDeclaration context, final Token token) {
			super(name, myEnclosedScope, context, token);
		}
		public boolean isArray() {
			return true;
		}
	}
	
	public static class StagePropDeclaration extends RoleDeclaration implements TypeDeclaration
	{
		public StagePropDeclaration(final String name, final StaticScope myEnclosedScope, final ContextDeclaration context, final Token token) {
			super(name, myEnclosedScope, context, token);
		}
		@Override public boolean requiresConstMethods() {
			return true;
		}
	}
	
	public static class StagePropArrayDeclaration extends StagePropDeclaration implements TypeDeclaration {
		public StagePropArrayDeclaration(final String name, final StaticScope myEnclosedScope, final ContextDeclaration context, final Token token) {
			super(name, myEnclosedScope, context, token);
		}
		public boolean isArray() {
			return true;
		}
	}
	
	public static class MethodDeclaration extends Declaration
	{
		public MethodDeclaration(final String name, final StaticScope myEnclosedScope,
				final Type returnType, final AccessQualifier accessQualifier, final Token token,
				final boolean isStatic) {
			super(name);
			signature_ = new MethodSignature(name(), returnType, accessQualifier, token, isStatic);
			isAConstructor_ = calculateConstructorStatus(myEnclosedScope);
			this.commonInit(myEnclosedScope, returnType, accessQualifier, token);
		}
		public MethodDeclaration(final MethodSignature signature, final StaticScope myEnclosedScope, 
				final Token token, final boolean manuallyInvokesConstructor) {
			super(signature.name());
			signature_ = signature;
			isAConstructor_ = calculateConstructorStatus(myEnclosedScope);
			this.commonInit(myEnclosedScope, signature.returnType(),
					signature.accessQualifier(), token);
		}
		public MethodDeclaration(final MethodSignature signature, final StaticScope myEnclosedScope, 
				final Token token) {
			super(signature.name());
			signature_ = signature;
			isAConstructor_ = calculateConstructorStatus(myEnclosedScope);
			this.commonInit(myEnclosedScope, signature.returnType(),
					signature.accessQualifier(), token);
		}
		private boolean calculateConstructorStatus(final StaticScope myEnclosedScope) {
			// Am I a constructor?
			
			boolean retval = false;
			
			final StaticScope parentScope = myEnclosedScope.parentScope();
			assert null != parentScope;
			final String className = parentScope.name();
			final String methodSelectorName = signature_.name();
			
			if (methodSelectorName.equals(className)) {
				retval = true;
			} else {
				if (className.matches("[a-zA-Z]<.*>") || className.matches("[A-Z][a-zA-Z0-9_]*<.*>")) {
					final int msnl = methodSelectorName.length();
					if (className.startsWith(methodSelectorName) && className.length() < msnl) {
						if (className.charAt(msnl-1) == '<') {
							retval = true;
						}
					}
				}
			}
			
			return retval;
		}
		public void setHasConstModifier(final boolean tf) {
			signature_.setHasConstModifier(tf);
		}
		private void commonInit(final StaticScope myEnclosedScope, final Type returnType,
				final AccessQualifier accessQualifier, final Token token) {
			if (null== myEnclosedScope) {
				assert null != myEnclosedScope;
			}
			final StaticScope parentScope = myEnclosedScope.parentScope();
			final Declaration associatedDeclaration = parentScope.associatedDeclaration();
			
			bodyPrefix_ = new ExprAndDeclList(token);	// for implicit constructor
			hasManualBaseClassConstructorInvocations_ = false;
			
			if (associatedDeclaration instanceof ContextDeclaration ||
					associatedDeclaration instanceof ClassDeclaration) {
				if (name().equals(associatedDeclaration.name())) {
					if (null != returnType) {
						assert returnType == null;
					}
				} else if (associatedDeclaration instanceof ClassDeclaration &&
						null != ((ClassDeclaration)associatedDeclaration).generatingTemplate()) {
					final boolean methodNameEqualsTemplateName = name().equals(((ClassDeclaration)associatedDeclaration).generatingTemplate().name());
					if (methodNameEqualsTemplateName) {
						if (null != returnType) {
							assert returnType == null;
						}
					} else {
						if (null == returnType) {
							// In the best of circumstances just check to make
							// sure returnType isn't null. We do stumbling handling below.
							;
						}
					}
				} else {
					// In the best of circumstances just check to make
					// sure returnType isn't null. We do stumbling handling below.
					;
				}
				ctorCheck(myEnclosedScope, parentScope, token);
			} else {
				// In the best of circumstances just check to make
				// sure returnType isn't null. We do stumbling handling below.
				;
			}
			
			returnType_ = null == returnType? StaticScope.globalScope().lookupTypeDeclaration("void") : returnType;
			myEnclosedScope_ = myEnclosedScope;
			accessQualifier_ = accessQualifier;
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
		}
		
		private void callBaseCtor(final StaticScope methodScope, final ClassType baseClass, final Token token) {
			// Look up constructor in that base class
			final String baseClassName = baseClass.name();
			final ActualArgumentList actualArgumentList = new ActualArgumentList();
			final IdentifierExpression self = new IdentifierExpression("this", baseClass, methodScope, token);
			actualArgumentList.addActualArgument(self);
			final StaticScope baseClassScope = baseClass.enclosedScope();
			if (null != baseClassScope) {
				final MethodDeclaration constructor = baseClassScope.lookupMethodDeclaration(baseClassName, actualArgumentList, false);
			
				// If there's a constructor, set up to call it from the beginning
				// of this constructor. Very first thing.
				if (null != constructor) {
					// If it's private, the programmer is telling us that it
					// shouldn't be called. Don't insert it.
					if (constructor.accessQualifier() == AccessQualifier.PublicAccess) {
						MethodInvocationEnvironmentClass originMessageClass, targetMessageClass;
						
						originMessageClass = MethodInvocationEnvironmentClass.ClassEnvironment;
						targetMessageClass = methodScope.methodInvocationEnvironmentClass();
						assert MethodInvocationEnvironmentClass.ClassEnvironment == targetMessageClass;
						
						final Message message = new Message(baseClassName, actualArgumentList, token, baseClass);
						final MessageExpression messageExpr = new MessageExpression(self, message, baseClass, token, false,
								originMessageClass, targetMessageClass, true);
						bodyPrefix_.addBodyPart(messageExpr);
					} else {
						// If it's not public, we simply don't call it
						;
					}
				}
			}
		}
	
		private void ctorCheck(final StaticScope methodScope, final StaticScope parentScope, final Token token) {
			if (isAConstructor_) {
				// Special things constructors need to do:
				// base class processing and initializations
				final Declaration associatedDeclaration = parentScope.associatedDeclaration();
				final Type megaType = null == associatedDeclaration? null: associatedDeclaration.type();
				if (null != megaType && megaType instanceof ClassType) {
					final ClassType theClass = (ClassType)megaType;
					
					// It's a class. Does the class have a base class?
					final ClassType baseClass = theClass.baseClass();
					if (null != baseClass) {
						callBaseCtor(methodScope, baseClass, token);
					}
				}
			}
		}
		
		public void setMyEnclosingScope(final StaticScope scope) {
			myEnclosedScope_.setParentScope(scope);
		}
		public void addParameterList(final FormalParameterList formalParameterList) {
			assert null != signature_;
			signature_.addParameterList(formalParameterList);
		}
		public Type returnType() {
			return returnType_;
		}
		@Override public Type type() {
			// Hmmm.
			return this.returnType();
		}
		public void setReturnType(final Type returnType) {
			returnType_ = returnType;
		}
		public void setBody(final ExprAndDeclList body) {
			body_ = body;
		}
		public StaticScope enclosedScope() {
			return myEnclosedScope_;
		}
		public StaticScope enclosingScope() {
			return enclosedScope().parentScope();
		}
		public FormalParameterList formalParameterList() {
			assert null != signature_;
			return signature_.formalParameterList();
		}
		public AccessQualifier accessQualifier() {
			return accessQualifier_;
		}
		public boolean isConst() {
			return null != signature_? signature_.hasConstModifier(): false;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public String getText() {
			return name();
		}
		public MethodSignature signature() {
			return signature_;
		}
		@Override public List<BodyPart> bodyParts() {
			List<BodyPart> retval = null;
			if (null != body_) {
				retval = body_.bodyParts();
			} else {
				retval = new ArrayList<BodyPart>();
			}
			return retval;
		}
		public List<BodyPart> prefixBodyParts() {
			return bodyPrefix_.bodyParts();
		}
		public void hasManualBaseClassConstructorInvocations(final boolean tf) {
			// hasManualBaseClassConstructorInvocations is called at the point
			// that we discover an explicit constructor. But the code has already
			// pumped in a default construct for all classes (see
			// MethodDeclaration.callBaseCtor). We call it via code
			// that's been inserted in bodyPrefix_. Here, we need to remove it
			//
			// However, it can also be the case that the base class already
			// has a private default constructor, in which case the code in
			// MethodDeclaration.callBaseCtor does NOT add a constructor
			// call to bodyPrefix_. We keep the tag
			// hasManualBaseClassConstructorInvocations_ so that other code
			// can distinguish between those two situations.
			if (tf) {
				// Don¨t use the implicit base class ctor call
				// if the programmer is doing his or her own
				bodyPrefix_ = new ExprAndDeclList(token_);
			}

			hasManualBaseClassConstructorInvocations_ = tf;
		}
		
		public MethodDeclaration copyWithNewEnclosingScopeAndTemplateParameters(
				final StaticScope newEnclosingScope,
				final TemplateInstantiationInfo newTypes
		) {
			final StaticScope enclosedScope = new StaticScope(myEnclosedScope_, "copy",
					newEnclosingScope, null, newTypes);
			
			Type returnType = returnType_;
			if (null != returnType && returnType_.name().equals(newTypes.templateName())) {
				returnType = myEnclosedScope_.parentScope().lookupTypeDeclarationRecursive(newTypes.fullTypeName());
			}
			
			final MethodDeclaration retval = new MethodDeclaration(
					name(), enclosedScope, returnType,
					accessQualifier_, token_, signature_.isStatic());
			
			retval.signature_ = signature_;
			retval.body_ = body_;
			
			retval.addParameterList(signature_.formalParameterList());
			enclosedScope.setDeclaration(retval);

			return retval;
		}
		
		public boolean hasManualBaseClassConstructorInvocations() {
			return hasManualBaseClassConstructorInvocations_;
		}
		
		public boolean isAConstructor() {
			return isAConstructor_;
		}
		
		private Type returnType_;
		private StaticScope myEnclosedScope_;
		private AccessQualifier accessQualifier_;
		private int lineNumber_;
		private Token token_;
		private ExprAndDeclList body_;
		private MethodSignature signature_;
		private ExprAndDeclList bodyPrefix_;
		private boolean hasManualBaseClassConstructorInvocations_;
		private final boolean isAConstructor_;
	}
	
	public static class MethodSignature extends Declaration
	{
		public MethodSignature(final String name, final Type returnType,
				final AccessQualifier accessQualifier, final Token token,
				final boolean isStatic) {
			super(name);
			
			returnType_ = returnType;
			accessQualifier_ = accessQualifier;
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
			hasConstModifier_ = false;
			isStatic_ = isStatic;
			isUnused_ = false;
		}
		public void addParameterList(final FormalParameterList formalParameterList) {
			formalParameterList_ = formalParameterList;
		}
		public Type returnType() {
			return returnType_;
		}
		@Override public Type type() {
			// Hmmm.
			return this.returnType();
		}
		public void setReturnType(final Type returnType) {
			returnType_ = returnType;
		}
		public FormalParameterList formalParameterList() {
			return formalParameterList_;
		}
		public AccessQualifier accessQualifier() {
			return accessQualifier_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(name());
			stringBuffer.append("(");
			final int numberOfParameters = formalParameterList().count();
						
			// Skip current$context if it's there
			final int startingIndex = numberOfParameters > 0?
											(formalParameterList().parameterAtPosition(0).name().equals("current$context")? 2: 1):
											(isStatic_? 0: 1);
			
			for (int i = startingIndex; i < numberOfParameters; i++) {
				stringBuffer.append(formalParameterList().parameterAtPosition(i).type().getText());
				if (i != numberOfParameters - 1) {
					stringBuffer.append(",");
				}
			}
			stringBuffer.append(")");
			final String retval = stringBuffer.toString();
			return retval;
		}
		public void setHasConstModifier(final boolean tf) {
			hasConstModifier_ = tf;
		}
		public boolean hasConstModifier() {
			return hasConstModifier_;
		}
		public boolean isStatic() {
			return isStatic_;
		}
		public boolean isUnusedInThisContext() {
			return isUnused_;
		}
		public void setUnused(final boolean isUnusedInThisContext) {
			isUnused_ = isUnusedInThisContext;
		}
		
		private       Type returnType_;
		private       boolean hasConstModifier_;
		private       FormalParameterList formalParameterList_;
		private final AccessQualifier accessQualifier_;
		private final Token token_;
		private final int lineNumber_;
		private final boolean isStatic_;
		private       boolean isUnused_;
	}
	
	public static class ExprAndDeclList extends Declaration
	{
		public ExprAndDeclList(final Token token) {
			super("");
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
			bodyParts_ = new ArrayList<BodyPart>();
		}
		public void addAssociatedDeclaration(final MethodDeclaration associatedDeclaration) {
			associatedDeclaration_ = associatedDeclaration;
		}
		public void addBodyPart(final BodyPart bp) {
			bodyParts_.add(bp);
		}
		public void addBodyParts(final List<BodyPart> bps) {
			for (final BodyPart bodyPart : bps) {
				addBodyPart(bodyPart);
			}
		}
		@Override public List<BodyPart> bodyParts() {
			return bodyParts_;
		}
		public MethodDeclaration associatedDeclaration() {
			return associatedDeclaration_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		private boolean ignoreBodyPartForReturnValue(final BodyPart bodyPart) {
			return  bodyPart instanceof BreakExpression ||
					bodyPart instanceof ContinueExpression;
		}
		@Override public Type type() {
			Type retval = StaticScope.globalScope().lookupTypeDeclaration("void");	// default
			// Used to be void. Now, return the type of the
			// last expression in the block, except for a
			// few that we ignore
			// return StaticScope.globalScope().lookupTypeDeclaration("void");
			final List<BodyPart> bodyParts = bodyParts();
			for (final BodyPart bodyPart : bodyParts) {
				final Type bodyPartType = bodyPart.type();
				if (false == ignoreBodyPartForReturnValue(bodyPart)) {
					retval = bodyPartType;
				}
			}
			return retval;
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			for (final BodyPart bodyPart : bodyParts_) {
				stringBuffer.append(bodyPart.getText());
				stringBuffer.append("; ");
			}
			final String retval = stringBuffer.toString();
			return retval;
		}
		
		private       MethodDeclaration associatedDeclaration_;
		private final int lineNumber_;
		private final Token token_;
		private final List<BodyPart> bodyParts_;
	}
	
	public static class DeclarationList extends Declaration
	{
		public DeclarationList(final Token token) {
			super("");
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
			declarations_ = new ArrayList<Declaration>();
		}
		public void addDeclaration(final Declaration d) {
			declarations_.add(d);
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public Type type() {
			return StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		@Override public String getText() {
			return name();
		}
		public List<BodyPart> declarations() {
			List<BodyPart> retval = new ArrayList<BodyPart>();
			retval.addAll(declarations_);
			return retval;
		}
		
		private final int lineNumber_;
		private final Token token_;
		private final List<Declaration> declarations_;
	}
	
	public static class TypeDeclarationList extends Declaration
	{
		public TypeDeclarationList(final Token token) {
			super("");
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
			declarations_ = new ArrayList<TypeDeclaration>();
		}
		public void addDeclaration(final TypeDeclaration d) {
			declarations_.add(d);
		}
		public void removeDeclaration(final TypeDeclaration d) {
			declarations_.remove(d);
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public Type type() {
			return StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		@Override public String getText() {
			return name();
		}
		public List<TypeDeclaration> declarations() {
			return declarations_;
		}
		
		private final int lineNumber_;
		private final Token token_;
		private final List<TypeDeclaration> declarations_;
	}
	
	public static class ArrayDecl extends Declaration
	{
		// type_name '[' ']' JAVA_ID
		public ArrayDecl(final String name, final Type baseType, final Token token) {
			super(baseType.getText() + " [" + "]");
			baseType_ = baseType;
			
			// name is kind of silly here, but it may help with
			// debugging things
			type_ = new ArrayType(name, baseType);
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
		}
		
		public Type baseType() {
			return baseType_;
		}
		@Override public String getText() {
			return baseType_.getText() + " [" + "]";
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public Type type() {
			return type_;
		}
		
		private final int lineNumber_;
		private final Token token_;
		private final Type baseType_;
		private final Type type_;
	}
	
	public static class VarargsDecl extends Declaration
	{
		public VarargsDecl(final String name, final Token token) {
			super(name + " varargs");
			
			// name is kind of silly here, but it may help with
			// debugging things
			type_ = new VarargsType(name);
			token_ = token;
			lineNumber_ = (null == token_)? 0: token_.getLine();
		}
		@Override public String getText() {
			return name();
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public Token token() {
			return token_;
		}
		@Override public Type type() {
			return type_;
		}
		
		private final Type type_;
		private final int lineNumber_;
		private final Token token_;
	}
	
	public static class ErrorDeclaration extends Declaration {
		public ErrorDeclaration(final String name) {
			super(name);
		}
		@Override public String getText() {
			return "*Error*";
		}
		@Override public int lineNumber() {
			return 0;
		}
		@Override public Token token() {
			return null;
		}
		@Override public Type type() {
			return null;
		}
		@Override public boolean isError() {
			return true;
		}
	}

	
	private final String name_;
}
