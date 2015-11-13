package info.fulloo.trygve.declarations;

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

import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.ExpressionStackAPI;
import info.fulloo.trygve.parser.Pass1Listener;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTExpression.RTNullExpression;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public abstract class Type implements ExpressionStackAPI
{
	enum HierarchySelector { ThisClassOnly, AlsoSearchBaseClass };
	public Type(final StaticScope enclosedScope) {
		super();
		enclosedScope_ = enclosedScope;
		staticObjectDeclarationDictionary_ = new HashMap<String, ObjectDeclaration>();
		staticObjects_ = new HashMap<String, RTCode>();
	}
	public StaticScope enclosedScope() {
		return enclosedScope_;
	}
	public void addDeclaration(final Declaration declaration) {
		enclosedScope_.declare(declaration);
	}
	public void declareStaticObject(final ObjectDeclaration declaration) {
		final String objectName = declaration.name();
		if (staticObjectDeclarationDictionary_.containsKey(objectName)) {
			ErrorLogger.error(ErrorType.Internal, declaration.lineNumber(), "Multiple definitions of static member ",
					objectName, "", "");
		} else {
			staticObjectDeclarationDictionary_.put(objectName, declaration);
			staticObjects_.put(objectName, new RTNullExpression());
		}
	}
	public ObjectDeclaration lookupStaticObjectDeclaration(final String objectName) {
		return staticObjectDeclarationDictionary_.get(objectName);
	}
	public Map<String,ObjectDeclaration> staticObjects() {
		return staticObjectDeclarationDictionary_;
	}
	public String name() {
		String retval = "*unknwown*";
		if (enclosedScope_ != null) {
			retval = enclosedScope_.name();
		}
		return retval;
	}
	public boolean isBaseClassOf(final Type aDerived) {
		// false for all but class types
		return false;
	}
	public String pathName() {
		// pathName is just a unique String for this type. We
		// generate it as the linear catenation of the names
		// of all the scopes up to the top
		final StaticScope globalScope = StaticScope.globalScope();
		String retval = "";
		StaticScope scope = this.enclosedScope();
		while (scope != globalScope) {
			if (null == scope) {
				// might be a template. return something.
				retval = this.name();
				break;
			} else {
				final Declaration associatedDeclaration = scope.associatedDeclaration();
				retval = associatedDeclaration.name() + "." + retval;
				scope = scope.parentScope();
			}
		}
		return retval;
	}
	public static class ClassType extends Type {
		public ClassType(final String name, final StaticScope enclosedScope, final ClassType baseClass) {
			super(enclosedScope);
			baseClass_ = baseClass;
			name_ = name;
			interfaceTypes_ =  new ArrayList<InterfaceType>();
		}
		@Override public String name() {
			return name_;
		}
		public ClassType baseClass() {
			return baseClass_;
		}
		public void updateBaseType(final ClassType baseType) {
			baseClass_ = baseType;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final int lineNumber, final Pass1Listener parserPass) {
			return this.canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			boolean retval = t.name().equals(name_);
			if (!retval) {
				if (t.name().equals("Null")) {
					retval = true;
				} else if (t instanceof ClassType) {
					final ClassType classyT = (ClassType)t;
					for (ClassType aBase = classyT.baseClass(); null != aBase; aBase = aBase.baseClass()) {
						if (aBase.name().equals(name_)) {
							retval = true;
							break;
						}
					}
				}
			}
			return retval;
		}
		@Override public Type type() {
			return this;
		}
		@Override public boolean isBaseClassOf(final Type aDerived) {
			// IMPROPER base class!!
			boolean retval = false;
			if (aDerived instanceof ClassType) {
				final ClassType aClassyDerived = (ClassType)aDerived;
				for (ClassType aBase = aClassyDerived; null != aBase; aBase = aBase.baseClass()) {
					if (aBase.enclosedScope() == this.enclosedScope()) {
						retval = true;
						break;
					}
				}
			}
			return retval;
		}
		public void elaborateFromTemplate(final TemplateDeclaration templateDeclaration, final ClassType baseClass,
				final StaticScope newEnclosedScope, final Declaration newAssociatedDeclaration) {
			baseClass_ = baseClass;
			final TemplateType nominalType = (TemplateType)templateDeclaration.type();
			assert null != newEnclosedScope.parentScope();
			enclosedScope_ = newEnclosedScope;
			staticObjectDeclarationDictionary_ = new HashMap<String, ObjectDeclaration>();
			for (Map.Entry<String,ObjectDeclaration> iter : nominalType.staticObjectDeclarationDictionary_.entrySet()) {
				final String name = iter.getKey();
				final ObjectDeclaration decl = iter.getValue();
				final ObjectDeclaration declCopy = decl.copy();
				staticObjectDeclarationDictionary_.put(new String(name), declCopy);
			}
			
			staticObjects_ = new HashMap<String, RTCode>();
			for (final Map.Entry<String,RTCode> iter : nominalType.staticObjects_.entrySet()) {
				final String name = iter.getKey();
				final RTCode programElement = iter.getValue();
				
				// We really should do a copy here of program element, but
				// we'll presume that code isn't modified and cross our
				// fingers. Adding a dup() function to that class hierarchy
				// would be quite a chore...
				staticObjects_.put(new String(name), programElement);
			}
		}
		public final TemplateInstantiationInfo templateInstantiationInfo() {
			return enclosedScope_.templateInstantiationInfo();
		}
		public void addInterfaceType(final InterfaceType it) {
			interfaceTypes_.add(it);
		}
		public final List<InterfaceType> interfaceTypes() {
			return interfaceTypes_;
		}
		
		private final String name_;
		private ClassType baseClass_;
		private List<InterfaceType> interfaceTypes_;
	}
	public static class TemplateType extends Type {
		public TemplateType(final String name, final StaticScope scope, final ClassType baseClass) {
			super(scope);
			baseClass_ = baseClass;
			name_ = name;
		}
		@Override public String name() {
			return name_;
		}
		public ClassType baseClass() {
			return baseClass_;
		}
		public void updateBaseType(ClassType baseType) {
			baseClass_ = baseType;
		}
		@Override public boolean canBeConvertedFrom(Type t, int lineNumber, Pass1Listener parserPass) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(Type t) {
			return true;
		}
		@Override public Type type() {
			return this;
		}
		@Override public boolean isBaseClassOf(Type aDerived) {
			return false;
		}
		
		private String name_;
		private ClassType baseClass_;
	}
	public static class ContextType extends Type {
		public ContextType(final String name, final StaticScope scope) {
			super(scope);
			name_ = name;
		}
		@Override public String name() {
			return name_;
		}
		@Override public boolean canBeConvertedFrom(Type t, int lineNumber, Pass1Listener parserPass) {
			return this.canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(Type t) {
			boolean retval = t.name().equals(name_);
			if (!retval) {
				retval = t.name().equals("Null");
			}
			return retval;
		}
		@Override public Type type() {
			return this;
		}
		
		private String name_;
	}
	public static class InterfaceType extends Type {
		public InterfaceType(final String name, final StaticScope enclosedScope) {
			super(enclosedScope);
			name_ = name;
			selectorSignatureMap_ = new HashMap<String, List<MethodSignature>>();
		}
		@Override public String name() {
			return name_;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final int lineNumber, final Pass1Listener parserPass) {
			return this.canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			boolean retval = t.name().equals(name_);
			if (!retval) {
				if (t.name().equals("Null")) {
					retval = true;
				} else if (t instanceof ClassType) {
					final ClassType classyT = (ClassType)t;
					for (final InterfaceType it : classyT.interfaceTypes()) {
						if (it.pathName().equals(pathName())) {
							retval = true;
							break;
						}
					}
				}
			}
			return retval;
		}
		@Override public Type type() {
			return this;
		}
		@Override public MethodSignature signatureForMethodSelectorCommon(final String methodSelector, final MethodSignature methodSignature,
				final String paramToIgnore, final HierarchySelector baseClassSearch) {
			MethodSignature retval = null;
			assert null == paramToIgnore;
			
			assert false;		// ever called?
			
			final FormalParameterList methodSignatureFormalParameterList = methodSignature.formalParameterList();
			
			List<MethodSignature> signatures = null;
			if (selectorSignatureMap_.containsKey(methodSelector)) {
				signatures = selectorSignatureMap_.get(methodSelector);
				for (final MethodSignature signature : signatures) {
					if (signature.formalParameterList().alignsWith(methodSignatureFormalParameterList)) {
						retval = signature;
						break;
					}
				}
			} else {
				retval = null;
			}
			
			return retval;
		}
		
		public MethodSignature lookupMethodSignature(final String selectorName, final ActualOrFormalParameterList argumentList) {
			MethodSignature retval = null;
			List<MethodSignature> signatures = null;
			if (selectorSignatureMap_.containsKey(selectorName)) {
				signatures = selectorSignatureMap_.get(selectorName);
				for (final MethodSignature signature : signatures) {
					if (signature.formalParameterList().alignsWith(argumentList)) {
						retval = signature;
						break;
					}
				}
			} else {
				retval = null;
			}
			
			return retval;
		}
		
		public void addSignature(final MethodSignature signature) {
			List<MethodSignature> signatures = null;
			if (selectorSignatureMap_.containsKey(signature.name())) {
				signatures = selectorSignatureMap_.get(signature.name());
			} else {
				signatures = new ArrayList<MethodSignature>();
				selectorSignatureMap_.put(signature.name(), signatures);
			}
			signatures.add(signature);
		}
		
		public final Map<String, List<MethodSignature>> selectorSignatureMap() {
			return selectorSignatureMap_;
		}
		
		private final String name_;
		private final Map<String, List<MethodSignature>> selectorSignatureMap_;
	}
	public static class BuiltInType extends Type {
		public BuiltInType(final String name) {
			super(new StaticScope(StaticScope.globalScope()));
			name_ = name;
		}
		@Override public String name() {
			return name_;
		}
		@Override public boolean hasUnaryOperator(final String operator) {
			boolean retval = false;
			if (this.name().equals("int") || this.name().equals("Integer")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("--")) retval = true;
				else if (operator.equals("++")) retval = true;
			} else if (this.name().equals("double")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("--")) retval = true;
				else if (operator.equals("++")) retval = true;
			}
			return retval;
		}
		@Override public boolean canBeLhsOfBinaryOperator(final String operator) {
			boolean retval = false;
			if (this.name().equals("int") || this.name().equals("Integer")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = true;
				else if (operator.equals("**")) retval = true;
			} else if (this.name().equals("double") || this.name().equals("Double")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = false;
				else if (operator.equals("**")) retval = true;
			} else if (this.name().equals("boolean")) {
				if (operator.equals("||")) retval = true;
				else if (operator.equals("&&")) retval = true;
				else if (operator.equals("^")) retval = true;
			}
			return retval;
		}
		@Override public boolean canBeRhsOfBinaryOperator(final String operator) {
			boolean retval = false;
			if (this.name().equals("int") || this.name().equals("Integer")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = true;
				else if (operator.equals("**")) retval = true;
			} else if (this.name().equals("double") || this.name().equals("Double")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = false;
				else if (operator.equals("**")) retval = true;
			} else if (this.name().equals("boolean")) {
				if (operator.equals("||")) retval = true;
				else if (operator.equals("&&")) retval = true;
				else if (operator.equals("^")) retval = true;
			}
			return retval;
		}
		@Override public boolean canBeConvertedFrom(Type t, int lineNumber, Pass1Listener parserPass) {
			return canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			if (null == t) {
				assert null != t;
			}
			boolean retval = false;
			if (t.name().equals(name_)) {
				retval = true;
			} else if (name().equals("double") && (t.name().equals("int") || t.name().equals("Integer"))) {
				retval = true;
			} else if ((t.name().equals("int") || t.name().equals("Integer")) && t.name().equals("double")) {
				// TODO: Issue truncation warning?
				retval = true;
			} else if (t.name().equals("Null")) {
				retval = true;
			}
			return retval;
		}
		@Override public Type type() {
			return this;
		}
		
		private String name_;
	}
	public static class RoleType extends Type {
		public RoleType(final String name, final StaticScope scope) {
			super(scope);
			name_ = name;
		}
		public void reportMismatchesWith(int lineNumber, Type t) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			
			if (t instanceof RoleType && t.name().equals(name())) {
				// it's just one of us...
				;
			} else {
				final Map<String, MethodSignature> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (String methodName : requiredSelfSignatures.keySet()) {
					final MethodSignature rolesSignature = requiredSelfSignatures.get(methodName);
					final MethodSignature signatureForMethodSelector =
							t.signatureForMethodSelectorIgnoringThis(methodName, rolesSignature);
					if (null == signatureForMethodSelector) {
						ErrorLogger.error(ErrorType.Fatal, lineNumber, "\t", rolesSignature.name(), " needed by role ", name(),
								" does not appear in interface of ", t.name());
					}
				}
			}
		}
		@Override public boolean canBeConvertedFrom(final Type t, final int lineNumber, final Pass1Listener parserPass) {
			return canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			boolean retval = true;
			if (t instanceof RoleType && t.name().equals(name())) {
				// it's just one of us...
				;
			} else {
				final Map<String, MethodSignature> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (final String methodName : requiredSelfSignatures.keySet()) {
					final MethodSignature rolesSignature = requiredSelfSignatures.get(methodName);
					final MethodSignature signatureForMethodSelector =
							t.signatureForMethodSelectorInHierarchyIgnoringThis(methodName, rolesSignature);
					if (null == signatureForMethodSelector) {
						retval = false;
						break;
					}
				}
			}
			return retval;
		}
		@Override public String name() {
			return name_;
		}
		public void setBacklinkToRoleDecl(final RoleDeclaration roleDecl) {
			associatedDeclaration_ = roleDecl;
		}
		public RoleDeclaration associatedDeclaration() {
			assert false;	// ever called?
			return associatedDeclaration_;
		}
		@Override public Type type() {
			return this;
		}
		public StaticScope enclosingScope() {
			return this.enclosedScope().parentScope();
		}
		public MethodSignature lookupMethodSignatureDeclaration(final String methodName) {
			return associatedDeclaration_.lookupMethodSignatureDeclaration(methodName);
		}
		public Declaration contextDeclaration() {
			return associatedDeclaration_.contextDeclaration();
		}
		public boolean isArray() {
			return associatedDeclaration_ instanceof RoleArrayDeclaration ||
				   associatedDeclaration_ instanceof StagePropArrayDeclaration;
		}
		
		protected final String name_;
		protected RoleDeclaration associatedDeclaration_;
	}
	
	public static class StagePropType extends RoleType {
		public StagePropType(String name, StaticScope scope) {
			super(name, scope);
		}
		public void reportMismatchesWith(final int lineNumber, final Type t) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			
			if (t instanceof StagePropType && t.name().equals(name())) {
				// it's just one of us...
				;
			} else {
				final Map<String, MethodSignature> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (String methodName : requiredSelfSignatures.keySet()) {
					final MethodSignature rolesSignature = requiredSelfSignatures.get(methodName);
					final MethodSignature signatureForMethodSelector =
							t.signatureForMethodSelectorIgnoringThis(methodName, rolesSignature);
					if (null == signatureForMethodSelector) {
						ErrorLogger.error(ErrorType.Fatal, lineNumber, "\t", rolesSignature.name(), " needed by role ", name(),
								" does not appear in interface of ", t.name());
					}
				}
			}
		}
		public Map<String, MethodSignature> requiredSelfSignatures() {
			return associatedDeclaration_.requiredSelfSignatures();
		}
		@Override public boolean canBeConvertedFrom(final Type t, final int lineNumber, final Pass1Listener parserPass) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			boolean retval = true;
			if (t instanceof StagePropType && t.name().equals(name())) {
				// it's just one of us...
				;
			} else {
				final Map<String, MethodSignature> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (final String methodName : requiredSelfSignatures.keySet()) {
					final MethodSignature rolesSignature = requiredSelfSignatures.get(methodName);
					final MethodSignature signatureForMethodSelector =
							t.signatureForMethodSelectorInHierarchyIgnoringThis(methodName, rolesSignature);
					if (null == signatureForMethodSelector) {
						retval = false;
						break;
					} else if (signatureForMethodSelector.hasConstModifier() == false) {
						final String roleSignatureLineNumber = Integer.toString(signatureForMethodSelector.lineNumber()) +
								" does not match the contract at line " +
								Integer.toString(rolesSignature.lineNumber());
						parserPass.errorHook5p2(ErrorType.Warning, lineNumber,
								"Required methods for stage props should be const. The declaration of method ",
								signatureForMethodSelector.name(), " at line ",
								roleSignatureLineNumber);
					}
				}
			}
			return retval;
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			boolean retval = true;
			if (t instanceof StagePropType && t.name().equals(name())) {
				// it's just one of us...
				;
			} else {
				final Map<String, MethodSignature> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (final String methodName : requiredSelfSignatures.keySet()) {
					final MethodSignature rolesSignature = requiredSelfSignatures.get(methodName);
					final MethodSignature signatureForMethodSelector =
							t.signatureForMethodSelectorInHierarchyIgnoringThis(methodName, rolesSignature);
					if (null == signatureForMethodSelector) {
						retval = false;
						break;
					} else if (signatureForMethodSelector.hasConstModifier() == false) {
						// Now handled by error-reporting version above...
						// final String roleSignatureLineNumber = Integer.toString(rolesSignature.lineNumber());
						// ErrorLogger.error(ErrorType.Warning, signatureForMethodSelector.lineNumber(),
						// 		"Required methods for stage props should be const. The declaration of method ",
						//		signatureForMethodSelector.name(), " does not match the contract at line ", roleSignatureLineNumber);
					}
				}
			}
			return retval;
		}
	}
	
	public static class ArrayType extends Type implements IndexableType {
		public ArrayType(final String name, final Type baseType) {
			super(null);
			name_ = name;
			baseType_ = baseType;
		}
		@Override public boolean canBeConvertedFrom(Type t, int lineNumber, Pass1Listener parserPass) {
			return canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(Type t) {
			boolean retval = true;
			if (t.name().equals("Null")) {
				// redundant but clear
				retval = true;
			} else if (t instanceof ArrayType == false) {
				retval = false;
			} else {
				final ArrayType tAsArray = (ArrayType) t;
				retval = baseType_.canBeConvertedFrom(tAsArray.baseType());
			}
			return retval;
		}
		@Override public String name() {
			return name_;
		}
		@Override public Type type() {
			return this;
		}
		public Type baseType() {
			return baseType_;
		}
		private final Type baseType_;
		private final String name_;
	}
	
	public static class TemplateParameterType extends Type {
		public TemplateParameterType(final String name, final ClassType baseClassType) {
			super(null);
			name_ = name;
			baseClassType_ = baseClassType;
		}
		@Override public boolean canBeConvertedFrom(Type t, int lineNumber, Pass1Listener parserPass) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(Type t) {
			return true;
		}
		@Override public String name() {
			return name_;
		}
		@Override public Type type() {
			return this;
		}
		public ClassType baseClassType() {
			return baseClassType_;
		}
		
		private final ClassType baseClassType_;
		private final String name_;
	}
	
	
	public abstract boolean canBeConvertedFrom(Type t);
	public abstract boolean canBeConvertedFrom(Type t, int lineNumber, Pass1Listener parserPass);
	
	public boolean hasUnaryOperator(final String operator) {
		return false;
	}
	public boolean canBeLhsOfBinaryOperator(final String operator) {
		return false;
	}
	public boolean canBeRhsOfBinaryOperator(final String operator) {
		return false;
	}
	public String getText() {
		return this.name();
	}
	public RTCode getStaticObject(final String name) {
		assert staticObjects_.containsKey(name);
		return staticObjects_.get(name);
	}
	public MethodSignature signatureForMethodSelectorCommon(final String methodSelector, final MethodSignature methodSignature,
			String paramToIgnore, HierarchySelector baseClassSearch) {
		final FormalParameterList methodSignatureFormalParameterList = methodSignature.formalParameterList();
		if (null == enclosedScope_) {
			assert null != enclosedScope_;
		}
		final MethodDeclaration mDecl = /*class*/enclosedScope_.lookupMethodDeclarationIgnoringParameter(methodSelector, methodSignatureFormalParameterList, paramToIgnore);
		
		// mDecl can be null under error conditions
		MethodSignature retval = null == mDecl? null: mDecl.signature();
	
		// See if we need to explore base class signatures
		if (null == retval && baseClassSearch == HierarchySelector.AlsoSearchBaseClass) {
			if (this instanceof ClassType) {
				final ClassType classType = (ClassType) this;
				if (null != classType.baseClass()) {
					// Recur. Good code reuse.
					retval = classType.baseClass().signatureForMethodSelectorCommon(methodSelector, methodSignature,
							paramToIgnore, baseClassSearch);
				} else {
					retval = null;	// redundant
				}
			} else {
				retval = null;	// redundant
			}
		} else {
			;	// is O.K.
		}
		
		return retval;
	}
	public MethodSignature signatureForMethodSelectorInHierarchyIgnoringThis(String methodSelector, MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, "this",
				HierarchySelector.AlsoSearchBaseClass);
	}
	public MethodSignature signatureForMethodSelectorIgnoringThis(String methodSelector, MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, "this",
				HierarchySelector.ThisClassOnly);
	}
	public MethodSignature signatureForMethodSelector(String methodSelector, MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, null,
				HierarchySelector.ThisClassOnly);
	}
	
	protected StaticScope enclosedScope_;
	protected Map<String, ObjectDeclaration> staticObjectDeclarationDictionary_;
	protected Map<String, RTCode> staticObjects_;
}
