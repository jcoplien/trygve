package declarations;

/*
 * Trygve IDE
 *   Copyright ©2015 James O. Coplien
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

import declarations.Type.ArrayType;
import declarations.Type.BuiltInType;
import declarations.Type.ClassType;
import declarations.Type.ContextType;
import declarations.Type.RoleType;
import declarations.Type.TemplateParameterType;
import declarations.Type.TemplateType;
import semantic_analysis.StaticScope;
import semantic_analysis.StaticScope.StaticRoleScope;
import error.ErrorLogger;
import error.ErrorLogger.ErrorType;
import expressions.Expression.IdentifierExpression;
import expressions.Expression.MessageExpression;

public abstract class Declaration implements BodyPart {
	public Declaration(String name) {
		name_ = name;
	}
	public String name() {
		return name_;
	}
	@Override public abstract Type type();
	
	@Override public List<BodyPart> bodyParts() {
		return new ArrayList<BodyPart>();
	}
	
	public static class ObjectDeclaration extends Declaration
	{
		public ObjectDeclaration(String name, Type type, int lineNumber) {
			super(name);
			lineNumber_ = lineNumber;
			type_ = type;
			accessQualifier_ = AccessQualifier.PrivateAccess;
		}
		public ObjectDeclaration copy() {
			final ObjectDeclaration retval = new ObjectDeclaration(name(), type_, lineNumber_);
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
		public void setAccess(AccessQualifier accessLevel) {
			accessQualifier_ = accessLevel;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		public void setEnclosingScope(StaticScope scope) {
			containingScope_ = scope;
		}
		public StaticScope enclosingScope() {
			return containingScope_;
		}

		private Type type_;
		private int lineNumber_;
		private StaticScope containingScope_;
		public AccessQualifier accessQualifier_;
	}
	
	public static class TypeDeclarationCommon extends Declaration implements TypeDeclaration
	{
		public TypeDeclarationCommon(String name, int lineNumber, StaticScope myEnclosedScope) {
			super(name);
			lineNumber_ = lineNumber;
			myEnclosedScope_ = myEnclosedScope;
			stringToStaticObjectMap_ = new HashMap<String, ObjectDeclaration>();
		}
		@Override public int lineNumber() {
			return lineNumber_;
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
		@Override public void declareStaticObject(ObjectDeclaration declaration) {
			if (stringToStaticObjectMap_.containsKey(declaration.name())) {
				ErrorLogger.error(ErrorType.Fatal, lineNumber_, "Duplicate declaration of static object ", declaration.name(), "", "");
			} else {
				stringToStaticObjectMap_.put(declaration.name(), declaration);
			}
		}
		@Override public ObjectDeclaration lookupStaticObjectDeclaration(String name) {
			ObjectDeclaration retval = null;
			if (stringToStaticObjectMap_.containsKey(name)) {
				retval = stringToStaticObjectMap_.get(name);
			} else {
				ErrorLogger.error(ErrorType.Fatal, lineNumber_, "Static object ", name, " not found in type ", name());
			}
			return retval;
		}
		@Override public String getText() {
			return name();
		}

		private int lineNumber_;
		protected StaticScope myEnclosedScope_;
		protected Type type_;
		private Map<String, ObjectDeclaration> stringToStaticObjectMap_;
	}
	
	public static class ContextDeclaration extends TypeDeclarationCommon implements TypeDeclaration
	{
		public ContextDeclaration(String name, StaticScope myEnclosedScope, ContextDeclaration currentContext, int lineNumber) {
			super(name, lineNumber, myEnclosedScope);
			parentContext_ = currentContext;
		}
		public void setType(ContextType type) {
			type_ = type;
		}
		public ContextDeclaration parentContext() {
			return parentContext_;
		}
		
		private ContextDeclaration parentContext_;
	}
	
	public static class ClassDeclaration extends TypeDeclarationCommon implements TypeDeclaration
	{
		public ClassDeclaration(String name, StaticScope myEnclosedScope, ClassDeclaration baseClass, int lineNumber) {
			super(name, lineNumber, myEnclosedScope);
			baseClass_ = baseClass;
			templateDeclaration_ = null;
			methodsHaveBodyParts_ = false;
		}
		public void setType(Type t) {
			assert t instanceof ClassType || t instanceof BuiltInType;
			type_ = t;
		}
		public ClassDeclaration baseClassDeclaration() {
			return baseClass_;
		}
		public void elaborateFromTemplate(final TemplateDeclaration templateDeclaration, TemplateInstantiationInfo newTypes) {
			templateDeclaration_ = templateDeclaration;
			// This turns a TemplateDeclaration into a class
			final ClassType baseClassType = null == baseClass_? null: (ClassType)baseClass_.type();
			assert null == baseClassType || baseClassType instanceof ClassType;
			
			myEnclosedScope_ = new StaticScope(enclosedScope(), "copy", templateDeclaration.enclosingScope(),
					this, newTypes);
			final ClassType newClassType = new ClassType(name(), myEnclosedScope_, baseClassType);
			newClassType.elaborateFromTemplate(templateDeclaration, baseClassType, myEnclosedScope_, this);
			this.setType(newClassType);
		}
		public TemplateDeclaration generatingTemplate() {
			return templateDeclaration_;
		}
		public boolean methodsHaveBodyParts() {
			return methodsHaveBodyParts_;
		}
		public void setMethodsHaveBodyParts(boolean tf) {
			methodsHaveBodyParts_ = tf;
		}

		private ClassDeclaration baseClass_;
		private TemplateDeclaration templateDeclaration_;
		private boolean methodsHaveBodyParts_;
	}
	
	public static class TypeParameter extends Object {
		public TypeParameter(String name, ClassType baseClassType) {
			name_ = name;
			baseClassType_ = baseClassType;
		}
		public ClassType baseClassDeclaration() {
			return baseClassType_;
		}
		public String name() {
			return name_;
		}
		public void setArgumentPosition(int i) {
			argumentPosition_ = i;
		}
		public int argumentPosition() {
			return argumentPosition_;
		}
		
		final private String name_;
		final private ClassType baseClassType_;
		private int argumentPosition_;
	}
	
	public static class TemplateDeclaration extends TypeDeclarationCommon implements TypeDeclaration
	{
		public TemplateDeclaration(String name, StaticScope myEnclosedScope, TypeDeclaration baseClass, int lineNumber) {
			super(name, lineNumber, myEnclosedScope);
			baseClass_ = baseClass;
			argumentPositionCounter_ = 0;
			typeParameters_ = new ArrayList<TypeParameter>();
		}
		public void setType(Type t) {
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
		public void addTypeParameter(IdentifierExpression rawTypeParameter, int numberOfTypeParameters) {
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
		
		private TypeDeclaration baseClass_;
		private List<TypeParameter> typeParameters_;
		private int argumentPositionCounter_;
	}
	
	public static class RoleDeclaration extends TypeDeclarationCommon implements TypeDeclaration
	{
		public RoleDeclaration(String name, StaticScope myEnclosedScope, ContextDeclaration context, int lineNumber) {
			super(name, lineNumber, myEnclosedScope);
			context_ = context;
			requiredSelfSignatures_ = new Hashtable<String, MethodSignature>();
		}
		public ContextDeclaration context() {
			return context_;
		}
		public boolean requiresConstMethods() {
			return false;
		}
		public void setType(RoleType t) {
			type_ = t;
		}
		public MethodSignature lookupMethodSignatureDeclaration(final String name) {
			return requiredSelfSignatures_.get(name);
		}
		public void addRequiredSignatureOnSelf(MethodSignature signature) {
			requiredSelfSignatures_.put(signature.name(), signature);
		}
		public Map<String, MethodSignature> requiredSelfSignatures() {
			return requiredSelfSignatures_;
		}
		public void processRequiredDeclarations(int lineno) {
			// Declare requiredSelfSignatures_ in my scope
			final StaticRoleScope myEnclosedScope = (StaticRoleScope)this.enclosedScope();
			assert myEnclosedScope instanceof StaticRoleScope;
			
			for (Map.Entry<String, MethodSignature> signature : requiredSelfSignatures_.entrySet()) {
				final MethodSignature methodSignature = signature.getValue();
				final MethodDeclaration methodDecl = new MethodDeclaration(methodSignature, myEnclosedScope, lineno);
				myEnclosedScope.declareRequiredMethod(methodDecl);
			}
		}
		
		private ContextDeclaration context_;
		private Map<String, MethodSignature> requiredSelfSignatures_;
	}
	
	public static class StagePropDeclaration extends RoleDeclaration implements TypeDeclaration
	{
		public StagePropDeclaration(String name, StaticScope myEnclosedScope, ContextDeclaration context, int lineNumber) {
			super(name, myEnclosedScope, context, lineNumber);
		}
		@Override public boolean requiresConstMethods() {
			return true;
		}
	}
	
	public static class MethodDeclaration extends Declaration
	{
		public MethodDeclaration(String name, StaticScope myEnclosedScope, Type returnType,
				AccessQualifier accessQualifier, int lineNumber) {
			super(name);
			signature_ = new MethodSignature(name(), returnType, accessQualifier, lineNumber);
			this.commonInit(myEnclosedScope, returnType, accessQualifier, lineNumber);
		}
		public MethodDeclaration(MethodSignature signature, StaticScope myEnclosedScope, int lineNumber) {
			super(signature.name());
			signature_ = signature;
			this.commonInit(myEnclosedScope, signature.returnType(),
					signature.accessQualifier(), lineNumber);
		}
		private void commonInit(StaticScope myEnclosedScope, Type returnType,
				AccessQualifier accessQualifier, int lineNumber) {
			final StaticScope parentScope = myEnclosedScope.parentScope();
			final Declaration associatedDeclaration = parentScope.associatedDeclaration();
			
			bodyPrefix_ = new ExprAndDeclList(lineNumber);
			
			if (associatedDeclaration instanceof ContextDeclaration ||
					associatedDeclaration instanceof ClassDeclaration) {
				if (name().equals(associatedDeclaration.name())) {
					if (null != returnType) {
						assert returnType == null;
					}
				} else if (associatedDeclaration instanceof ClassDeclaration &&
						null != ((ClassDeclaration)associatedDeclaration).generatingTemplate()) {
					if (name().equals(((ClassDeclaration)associatedDeclaration).generatingTemplate().name())) {
						if (null != returnType) {
							assert returnType == null;
						}
					} else {
						if (null == returnType) {
							assert returnType != null;
						}
					}
				} else {
					if (null == returnType) {
						assert returnType != null;
					}
				}
				ctorCheck(myEnclosedScope, parentScope, lineNumber);
			} else {
				if (null == returnType) {
					assert returnType != null;
				}
			}
			
			returnType_ = returnType;
			myEnclosedScope_ = myEnclosedScope;
			accessQualifier_ = accessQualifier;
			lineNumber_ = lineNumber;
		}
		private void ctorCheck(StaticScope methodScope, StaticScope parentScope, int lineNumber) {
			// Am I a constructor?
			boolean isCtor = false;
			final String className = parentScope.name();
			final String methodSelectorName = signature_.name();
			
			if (methodSelectorName.equals(className)) {
				isCtor = true;
			} else {
				if (className.matches("[a-zA-Z]<.*>") || className.matches("[A-Z][a-zA-Z0-9_]*<.*>")) {
					final int msnl = methodSelectorName.length();
					if (className.startsWith(methodSelectorName) && className.length() < msnl) {
						if (className.charAt(msnl-1) == '<') {
							isCtor = true;
						}
					}
				}
			}
			
			if (isCtor) {
				// Do we have a base class?
				final Declaration associatedDeclaration = null == parentScope? null: parentScope.associatedDeclaration();
				final Type megaType = null == associatedDeclaration? null: associatedDeclaration.type();
				if (null != megaType && megaType instanceof ClassType) {
					ClassType theClass = (ClassType)megaType;
					
					// It's a class. Does the class have a base class?
					final ClassType baseClass = theClass.baseClass();
					
					if (null != baseClass) {
						// Look up constructor in that base class
						final String baseClassName = baseClass.name();
						final ActualArgumentList actualArgumentList = new ActualArgumentList();
						final IdentifierExpression self = new IdentifierExpression("this", baseClass, methodScope);
						actualArgumentList.addActualArgument(self);
						final StaticScope baseClassScope = baseClass.enclosedScope();
						if (null != baseClassScope) {
							final MethodDeclaration constructor = baseClassScope.lookupMethodDeclaration(baseClassName, actualArgumentList, false);
						
							// If there's a constructor, set up to call it from the beginning
							// of this constructor. Very first thing.
							if (null!= constructor) {
								final Message message = new Message(baseClassName, actualArgumentList, lineNumber, baseClass);
								final MessageExpression messageExpr = new MessageExpression(self, message, baseClass, lineNumber);
								bodyPrefix_.addBodyPart(messageExpr);
							}
						}
					}
				}
			}
		}
		public void setMyEnclosingScope(StaticScope scope) {
			myEnclosedScope_.setParentScope(scope);
		}
		public void addParameterList(FormalParameterList formalParameterList) {
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
		public void setReturnType(Type returnType) {
			returnType_ = returnType;
		}
		public void setBody(ExprAndDeclList body) {
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
		@Override public String getText() {
			return name();
		}
		public MethodSignature signature() {
			return signature_;
		}
		@Override public List<BodyPart> bodyParts() {
			List<BodyPart> retval = null;
			if (null != body_) {
				retval = bodyPrefix_.bodyParts();
				retval.addAll(body_.bodyParts());
			} else {
				retval = bodyPrefix_.bodyParts();
			}
			return retval;
		}
		
		public MethodDeclaration copyWithNewEnclosingScopeAndTemplateParameters(
				StaticScope newEnclosingScope,
				TemplateInstantiationInfo newTypes
		) {
			final StaticScope enclosedScope = new StaticScope(myEnclosedScope_, "copy",
					newEnclosingScope, null, newTypes);
			
			Type returnType = returnType_;
			if (null != returnType && returnType_.name().equals(newTypes.templateName())) {
				returnType = myEnclosedScope_.parentScope().lookupTypeDeclarationRecursive(newTypes.fullTypeName());
			}
			
			final MethodDeclaration retval = new MethodDeclaration(
					name(), enclosedScope, returnType,
					accessQualifier_, lineNumber_);
			
			retval.signature_ = signature_;
			retval.body_ = body_;
			
			retval.addParameterList(signature_.formalParameterList());
			enclosedScope.setDeclaration(retval);

			return retval;
		}
		
		private Type returnType_;
		private StaticScope myEnclosedScope_;
		private AccessQualifier accessQualifier_;
		private int lineNumber_;
		private ExprAndDeclList body_;
		private MethodSignature signature_;
		private ExprAndDeclList bodyPrefix_;
	}
	
	public static class MethodSignature extends Declaration
	{
		public MethodSignature(String name, Type returnType,
				AccessQualifier accessQualifier, int lineNumber) {
			super(name);
			returnType_ = returnType;
			accessQualifier_ = accessQualifier;
			lineNumber_ = lineNumber;
			hasConstModifier_ = false;
		}
		public void addParameterList(FormalParameterList formalParameterList) {
			formalParameterList_ = formalParameterList;
		}
		public Type returnType() {
			return returnType_;
		}
		@Override public Type type() {
			// Hmmm.
			return this.returnType();
		}
		public void setReturnType(Type returnType) {
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
		@Override public String getText() {
			return name();
		}
		public void setHasConstModifier(boolean tf) {
			hasConstModifier_ = tf;
		}
		public boolean hasConstModifier() {
			return hasConstModifier_;
		}
		
		private Type returnType_;
		private boolean hasConstModifier_;
		private FormalParameterList formalParameterList_;
		private final AccessQualifier accessQualifier_;
		private final int lineNumber_;
	}
	
	public static class ExprAndDeclList extends Declaration
	{
		public ExprAndDeclList(int lineNumber) {
			super("");
			lineNumber_ = lineNumber;
			bodyParts_ = new ArrayList<BodyPart>();
		}
		public void addAssociatedDeclaration(MethodDeclaration associatedDeclaration) {
			associatedDeclaration_ = associatedDeclaration;
		}
		public void addBodyPart(BodyPart bp) {
			bodyParts_.add(bp);
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
		@Override public Type type() {
			return StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		@Override public String getText() {
			String retval = "";
			for (BodyPart bodyPart : bodyParts_) {
				retval += bodyPart.getText() + "; ";
			}
			return retval;
		}
		
		private MethodDeclaration associatedDeclaration_;
		private int lineNumber_;
		private List<BodyPart> bodyParts_;
	}
	
	public static class DeclarationList extends Declaration
	{
		public DeclarationList(int lineNumber) {
			super("");
			lineNumber_ = lineNumber;
			declarations_ = new ArrayList<Declaration>();
		}
		public void addDeclaration(Declaration d) {
			declarations_.add(d);
		}
		@Override public int lineNumber() {
			return lineNumber_;
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
		
		private int lineNumber_;
		private List<Declaration> declarations_;
	}
	
	public static class TypeDeclarationList extends Declaration
	{
		public TypeDeclarationList(int lineNumber) {
			super("");
			lineNumber_ = lineNumber;
			declarations_ = new ArrayList<TypeDeclaration>();
		}
		public void addDeclaration(TypeDeclaration d) {
			declarations_.add(d);
		}
		public void removeDeclaration(TypeDeclaration d) {
			declarations_.remove(d);
		}
		@Override public int lineNumber() {
			return lineNumber_;
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
		
		private int lineNumber_;
		private List<TypeDeclaration> declarations_;
	}
	
	public static class ArrayDecl extends Declaration
	{
		// type_name '[' ']' JAVA_ID
		public ArrayDecl(String name, Type baseType, int lineNumber) {
			super(baseType.getText() + " [" + "]");
			baseType_ = baseType;
			
			// name is kind of silly here, but it may help with
			// debugging things
			type_ = new ArrayType(name, baseType);
			lineNumber_ = lineNumber;
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
		@Override public Type type() {
			return type_;
		}
		
		private final int lineNumber_;
		private final Type baseType_;
		private final Type type_;
	}

	
	private String name_;
}
