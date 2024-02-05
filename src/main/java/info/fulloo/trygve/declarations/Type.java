package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 4.3
 *   Copyright (c)2023 James O. Coplien, jcoplien@gmail.com
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
import java.util.Set;

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.ExpressionStackAPI;
import info.fulloo.trygve.parser.Pass1Listener;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTExpression.RTNullExpression;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticRoleScope; 

public abstract class Type implements ExpressionStackAPI
{
	enum HierarchySelector { ThisClassOnly, AlsoSearchBaseClass };
	public Type(final StaticScope enclosedScope) {
		super();
		enclosedScope_ = enclosedScope;
		staticObjectDeclarationDictionary_ = new LinkedHashMap<String, ObjectDeclaration>();
		staticObjects_ = new LinkedHashMap<String, RTCode>();
		token_ = null != enclosedScope?
					(null != enclosedScope.associatedDeclaration()?
							enclosedScope.associatedDeclaration().token(): null):
									null;
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
			ErrorLogger.error(ErrorIncidenceType.Internal, declaration.token(), "Multiple definitions of static member ",
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
	public boolean canBeLhsOfBinaryOperatorForRhsType(final String operator, final Type type) {
		// Type
		boolean retval = false;
		if ((operator.equals("!=") || operator.equals("==")) && type.pathName().equals("Null")) {
			// Can always compare with null
			retval = true;
		} else if ((operator.equals("!=") || operator.equals("==")) && this.pathName().equals("Null")) {
			// Can always compare with null
			retval = true;
		} else if (operator.equals("!=") || operator.equals("==") || operator.equals("<") ||
				operator.equals(">") || operator.equals("<=") || operator.equals(">=")) {
			// Valid if compareTo(X) is defined on us. Probably needs some loosening up
			final FormalParameterList parameterList = new FormalParameterList();
			ObjectDeclaration self = new ObjectDeclaration("this", this, null);
			parameterList.addFormalParameter(self);
			ObjectDeclaration other = new ObjectDeclaration("other", this, null);
			parameterList.addFormalParameter(other);
			MethodDeclaration compareTo = this.enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
					"compareTo", parameterList, false, /*parameterToIgnore*/ null);
			if (null == compareTo) {
				self = new ObjectDeclaration("this", type, null);
				parameterList.addFormalParameter(self);
				other = new ObjectDeclaration("other", type, null);
				parameterList.addFormalParameter(other);
				compareTo = this.enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
						"compareTo", parameterList, false, /*parameterToIgnore*/ null);
				if (null == compareTo) {
					retval = false;
				} else {
					retval = true;
				}
			} else {
				retval = true;
			}
		}
		return retval;
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
				if (null == associatedDeclaration) {
					// e.g. at global scope
					retval = this.name();
				} else {
					retval = associatedDeclaration.name() + "." + retval;
				}
				scope = scope.parentScope();
			}
		}
		return retval;
	}
	public void pass2Instantiations() { /* NOP */ }
	public static class ClassOrContextType extends Type {
		public ClassOrContextType(final String name, final StaticScope enclosedScope,
				final ClassType baseType) {
			super(enclosedScope);
			name_ = name;
			baseClass_ = baseType;
			interfaceTypes_ =  new ArrayList<InterfaceType>();
		}
		@Override public Type type() {
			return this;
		}
		@Override public String name() {
			return name_;
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			assert false;
			return false;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return this.canBeConvertedFrom(t);
		}
		@Override public void pass2Instantiations() { /* NOP */ }
		public ClassType baseClass() {
			return baseClass_;
		}
		public void updateBaseType(final ClassType baseType) {
			baseClass_ = baseType;
		}
		public void addInterfaceType(final InterfaceType it) {
			interfaceTypes_.add(it);
		}
		public final List<InterfaceType> interfaceTypes() {
			return interfaceTypes_;
		}

		private final List<InterfaceType> interfaceTypes_;
		private final String name_;
		private       ClassType baseClass_;
	}

	public static class ClassType extends ClassOrContextType {
		public ClassType(final String name, final StaticScope enclosedScope, final ClassType baseClass) {
			super(name, enclosedScope, baseClass);
		}
		public boolean canBeConvertedFromRole(final RoleType roleType) {
			// Very customized. Please refactor. Used in only one place, I think.
			boolean retval = false;
			if (null == roleType || null == roleType.pathName() || null == pathName()) {
				assert false;
			} else if (name().equals("Object")) {
				// anything can be converted to Object
				retval = true;
			} else {
				retval = roleType.pathName().equals(pathName());
				if (!retval) {
					if (roleType.name().equals("Null")) {
						retval = true;
					} else if (null != isTemplate(this) && null != isTemplate(roleType)) {
						// Messy, messy, messy
						final String thisParameterName = isTemplate(this);
						final String tParameterName = isTemplate(roleType);
						Type thisType = enclosedScope().lookupTypeDeclarationRecursive(thisParameterName);
						if (null == thisType) {
							if (this.name().startsWith("List<")) {
								final StaticScope enclosedScope = this.enclosedScope();
								final TemplateInstantiationInfo templateInstantiationInfo = enclosedScope.templateInstantiationInfo();
								thisType = templateInstantiationInfo.classSubstitionForFormalParameterNamed(thisParameterName);
							}
						}
						Type tType = enclosedScope().lookupTypeDeclarationRecursive(tParameterName);
						if (null == tType) {
							if (roleType.name().startsWith("List<")) {
								final StaticScope enclosedScope = roleType.enclosedScope();
								final TemplateInstantiationInfo templateInstantiationInfo = enclosedScope.templateInstantiationInfo();
								tType = templateInstantiationInfo.classSubstitionForFormalParameterNamed(tParameterName);
							}
						}
						assert null != tType;
						assert null != thisType;
						retval = thisType.canBeConvertedFrom(tType);
					} else {
						// Compare signatures. For each Class signature, we should
						// be able to find it in the Role as well
						retval = true;
						final List<MethodDeclaration> classMethodDecls = this.enclosedScope().methodDeclarations();
						for (final MethodDeclaration methodDecl : classMethodDecls) {
							if (methodDecl.isAConstructor()) continue;
							final MethodSignature classMethodSignature = methodDecl.signature();
							final MethodSignature roleDecl1 = roleType.associatedDeclaration().lookupRequiredMethodSignatureDeclaration(classMethodSignature);
							if (null == roleDecl1) {
								retval = false;
								break;
							}
						}
						
						// Also, for each base class public method, we
						// should be able to find it in the Role as well!
						if (false == retval) {
							final ClassType baseClass = this.baseClass();
							if (null != baseClass){
								retval = baseClass.recursivelyCheckBaseClasses(roleType);
							}
						}
					}
				}
			}
			
			if (false == retval) {
				retval = complexCanBeConvertedCheck(roleType);
			}
			
			return retval;
		}
		
		private boolean checkIfIsObjectScript(
				final MethodSignature formalParamSignature) {
			boolean retval = false;
			final StaticScope objectScope = StaticScope.globalScope().lookupClassDeclaration("Object").enclosedScope();
			assert null != objectScope;
			retval = null != objectScope.lookupMethodDeclaration(formalParamSignature.name(),
					formalParamSignature.formalParameterList(), false);
			return retval;
		}
		
		private boolean recursivelyCheckBaseClasses(final RoleType roleType) {
			boolean retval = true;
			
			// Compare signatures. For each Class signature, we should
			// be able to find it in the Role as well
			retval = true;
			final List<MethodDeclaration> classMethodDecls = this.enclosedScope().methodDeclarations();
			for (final MethodDeclaration methodDecl : classMethodDecls) {
				if (methodDecl.isAConstructor())
					continue;
				if (methodDecl.accessQualifier() != AccessQualifier.PublicAccess)
					continue;
				final MethodSignature classMethodSignature = methodDecl.signature();
				final MethodSignature roleDecl1 = roleType.associatedDeclaration().lookupRequiredMethodSignatureDeclaration(classMethodSignature);
				if (null == roleDecl1) {
					// Every Role-player plays class Object. If we can
					// find it in class Object, we're O.K.
					retval = checkIfIsObjectScript(classMethodSignature);
					break;
				}
			}
			
			if (false == retval) {
				final ClassType baseClass = this.baseClass();
				if (null != baseClass){
					retval = baseClass.recursivelyCheckBaseClasses(roleType);
				}
			}
			
			return retval;
		}
		
		@Override public boolean canBeConvertedFrom(final Type t) {
			boolean retval = false;
			
			if (t instanceof BuiltInType) {
				return t.name() == "Null" || t.name().equals(name());
			}
			
			if (!(t instanceof ClassType) && !(t instanceof ErrorType) && !(t instanceof BuiltInType) &&
					!(t instanceof ArrayType) && !(t instanceof ContextType) &&
					!(t instanceof RoleType) &&!(t instanceof InterfaceType)) {
				assert (t instanceof ClassType || t instanceof ErrorType || t instanceof BuiltInType ||
						t instanceof ArrayType || t instanceof ContextType ||
						t instanceof RoleType  || t instanceof InterfaceType);
			}
			
			if (null == t || null == t.pathName() || null == pathName()) {
				assert false;
			} else if (name().equals("Object")) {
				// anything can be converted to Object
				retval = true;
			} else {
				retval = t.pathName().equals(pathName());
				if (!retval) {
					if (t.name().equals("Null")) {
						retval = true;
					} else if (null != isTemplate(this) && null != isTemplate(t)) {
						// Messy, messy, messy
						final String thisParameterName = isTemplate(this);
						final String tParameterName = isTemplate(t);
						Type thisType = enclosedScope().lookupTypeDeclarationRecursive(thisParameterName);
						boolean thisTypeIsList = false, thisTypeIsSet = false, tTypeIsSet = false, tTypeIsList = false;
						if (null == thisType) {
							if (this.name().startsWith("List<")) {
								final StaticScope enclosedScope = this.enclosedScope();
								final TemplateInstantiationInfo templateInstantiationInfo = enclosedScope.templateInstantiationInfo();
								thisType = templateInstantiationInfo.classSubstitionForFormalParameterNamed(thisParameterName);
								thisTypeIsList = true;
							} else if (this.name().startsWith("Set<")) {
								final StaticScope enclosedScope = this.enclosedScope();
								final TemplateInstantiationInfo templateInstantiationInfo = enclosedScope.templateInstantiationInfo();
								thisType = templateInstantiationInfo.classSubstitionForFormalParameterNamed(thisParameterName);
								thisTypeIsSet = true;
							}
						}
						Type tType = enclosedScope().lookupTypeDeclarationRecursive(tParameterName);
						if (null == tType) {
							if (t.name().startsWith("List<")) {
								final StaticScope enclosedScope = t.enclosedScope();
								final TemplateInstantiationInfo templateInstantiationInfo = enclosedScope.templateInstantiationInfo();
								tType = templateInstantiationInfo.classSubstitionForFormalParameterNamed(tParameterName);
							} else if (t.name().startsWith("Set<")) {
								final StaticScope enclosedScope = t.enclosedScope();
								final TemplateInstantiationInfo templateInstantiationInfo = enclosedScope.templateInstantiationInfo();
								tType = templateInstantiationInfo.classSubstitionForFormalParameterNamed(tParameterName);
							}
						}
						assert null != tType;
						assert null != thisType;
						retval = thisTypeIsList == tTypeIsList && thisTypeIsSet == tTypeIsSet &&
								thisType.canBeConvertedFrom(tType);
					} else if (t instanceof RoleType && this instanceof ClassType) {
						// Does class have all the methods needed by the role?
						retval = true;
						final RoleDeclaration roleDecl = (RoleDeclaration)t.enclosedScope().associatedDeclaration();
						for (final String a : roleDecl.requiredSignatures().keySet()) {
							final List<MethodSignature> roleExpectations = roleDecl.requiredSelfSignatures().get(a);
							for (final MethodSignature a2: roleExpectations) {
								if (null == this.enclosedScope().lookupMethodDeclaration(a2.name(), null, true)) {
									retval = false;
									break;
								}
							}
						}
					} else if (t instanceof ClassType || t instanceof ContextType) {
						// Base class check
						final ClassOrContextType classyT = (ClassOrContextType)t;
						for (ClassType aBase = classyT.baseClass(); null != aBase; aBase = aBase.baseClass()) {
							if (aBase.pathName().equals(pathName())) {
								retval = true;
								break;
							}
						}
					} else if (this instanceof ClassType && t instanceof BuiltInType) {
						retval = false;
						for (MethodDeclaration methodDecl : enclosedScope().methodDeclarations()) {
							if (methodDecl.name().equals(name())) {
								// Is a constructor
								final MethodSignature methodSignature = methodDecl.signature();
								final FormalParameterList parameterList = methodSignature.formalParameterList();
								if (1 == parameterList.userParameterCount()) {
									final Declaration parameter = parameterList.parameterAtPosition(1);
									if (parameter.type().pathName().equals(t.pathName())) {
										retval = true;
										break;
									}
								}
							}
						}
					} else if (t instanceof ErrorType) {
						retval = false;
					} else if (t instanceof ArrayType) {
						retval = false;
					} else {
						assert (false);	// missing case?
					}
				}
			}
			
			if (false == retval) {
				retval = complexCanBeConvertedCheck(t);
			}
			
			return retval;
		}
		private boolean complexCanBeConvertedCheck(final Type t) {
			// If every script in the signature of this is also in the
			// signature of t, then they are type compatible
			boolean retval = true;
			
			String nameWithoutTemplateParameter = name();
			final int paramIndex = nameWithoutTemplateParameter.indexOf('<');
			if (paramIndex != -1) {
				nameWithoutTemplateParameter = nameWithoutTemplateParameter.substring(0, paramIndex);
			}

			if (t.isBaseClassOf(this)) {
				retval = false;
			} else if (t instanceof ErrorType || null == t.enclosedScope()) {
				retval = false;
			} else if (t instanceof RoleType) {
				final List<MethodDeclaration> myMethods = this.enclosedScope().methodDeclarations();
				for (final MethodDeclaration mdecl : myMethods) {
					// Ignore constructors
					if (mdecl.name().equals(nameWithoutTemplateParameter)) {
						continue;
					} else {
						// Check the rest
						final MethodDeclaration otherTypeMDecl = t.enclosedScope().lookupMethodDeclarationIgnoringRoleStuff(
								mdecl.signature().name(), mdecl.formalParameterList());
						if (null == otherTypeMDecl) {
							// Check requires signatures (published ones instead?)
							final RoleType roleType = (RoleType) t;
							final RoleDeclaration associatedDeclaration = roleType.associatedDeclaration();
							final MethodSignature publishedSignature = associatedDeclaration.lookupPublishedSignatureDeclaration(mdecl.signature());
							if (null == publishedSignature) {
								retval = false;
								break;
							}
						}
					}
				}
			} else {
				// Probably shouldn't have called here in the first place
				retval = false;
			}
			return retval;
		}
		@Override public void pass2Instantiations() {
			// For List template instantiations, we need to repair
			// the return type in the method declaration for "reverse."
			// In a way it's a kludge, but it kind of makes sense.
			if (name().startsWith("List<")) {
				final StaticScope scope = enclosedScope();
				final ActualOrFormalParameterList paramList = new FormalParameterList();
				final MethodDeclaration reverse = scope.lookupMethodDeclaration(
						"reverse", paramList, true
				);
				assert(null != reverse);
				reverse.setReturnType(this);
			}
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
			} else if (name().equals("Object")) {
				// Object is a base class of all types - even of BuiltInType, which
				// for some strange reason do not pass the instanceof ClassType test..
				// This is safe in just about any case, anyhow.
				retval = true;
			}
			return retval;
		}
		public void elaborateFromTemplate(final TemplateDeclaration templateDeclaration, final ClassType baseClass,
				final StaticScope newEnclosedScope, final Declaration newAssociatedDeclaration,
				final Token token) {
			super.updateBaseType(baseClass);
			final TemplateType nominalType = (TemplateType)templateDeclaration.type();
			assert null != newEnclosedScope.parentScope();
			enclosedScope_ = newEnclosedScope;
			staticObjectDeclarationDictionary_ = new LinkedHashMap<String, ObjectDeclaration>();
			for (Map.Entry<String,ObjectDeclaration> iter : nominalType.staticObjectDeclarationDictionary_.entrySet()) {
				final String name = iter.getKey();
				final ObjectDeclaration decl = iter.getValue();
				final ObjectDeclaration declCopy = decl.copy();
				staticObjectDeclarationDictionary_.put(new String(name), declCopy);
			}
			
			staticObjects_ = new LinkedHashMap<String, RTCode>();
			for (final Map.Entry<String,RTCode> iter : nominalType.staticObjects_.entrySet()) {
				final String name = iter.getKey();
				final RTCode programElement = iter.getValue();
				
				// We really should do a copy here of program element, but
				// we'll presume that code isn't modified and cross our
				// fingers. Adding a dup() function to that class hierarchy
				// would be quite a chore...
				final String stringCopy = name.substring(0, name.length() - 1);
				staticObjects_.put(stringCopy, programElement);
			}
		}
		public final TemplateInstantiationInfo templateInstantiationInfo() {
			return enclosedScope_.templateInstantiationInfo();
		}
		@Override public boolean canBeLhsOfBinaryOperatorForRhsType(final String operator, final Type type) {
			// ClassType
			assert true;		// tests/new_luhnvalidation.k
			boolean retval = false;
			if (this.type().canBeConvertedFrom(type)) {
				retval = true;
			} else {
				// Can always compare with null
				if (operator.equals("==") && type.pathName().equals("Null")) {
					retval = true;
				} else if (operator.equals("!=") && type.pathName().equals("Null")) {
					retval = true;
				}
			}
			return retval;
		}
		private String isTemplate(final Type t) {
			// Returns the template parameter or null
			String retval = null;
			final String typeName = t.name();
			
			// For now, just deal with Lists. We'll have to deal with Maps later
			if (typeName.startsWith("List<")) {
				if (typeName.matches("[a-zA-Z]<.*>") || typeName.matches("[A-Z][a-zA-Z0-9_]*<.*>")) {
					final int indexOfDelimeter = typeName.indexOf('<');
					final int l = typeName.length();
					retval = typeName.substring(indexOfDelimeter + 1, l - 1);
				}
			} else if (typeName.startsWith("Set<")) {
				if (typeName.matches("[a-zA-Z]<.*>") || typeName.matches("[A-Z][a-zA-Z0-9_]*<.*>")) {
					final int indexOfDelimeter = typeName.indexOf('<');
					final int l = typeName.length();
					retval = typeName.substring(indexOfDelimeter + 1, l - 1);
				}
			}
			
			return retval;
		}
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
		public void updateBaseType(final ClassType baseType) {
			baseClass_ = baseType;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			return true;
		}
		@Override public Type type() {
			return this;
		}
		@Override public boolean isBaseClassOf(final Type aDerived) {
			return false;
		}
		
		private final String name_;
		private ClassType baseClass_;
	}
	public static class TemplateTypeForAnInterface extends TemplateType {
		public TemplateTypeForAnInterface(final String name, final StaticScope scope, final ClassType baseClass) {
			super(name, scope, baseClass);
			selectorSignatureMap_ = new LinkedHashMap<String, List<MethodSignature>>();
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
		public Map<String, List<MethodSignature>> selectorSignatureMap() {
			return selectorSignatureMap_;
		}
		
		private Map<String, List<MethodSignature>> selectorSignatureMap_;
	}
	public static class ContextType extends ClassOrContextType {
		public ContextType(final String name, final StaticScope scope, final ClassType baseClass) {
			super(name, scope, baseClass);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			boolean retval = t.name().equals(name());
			if (!retval) {
				retval = t.name().equals("Null");
			}
			return retval;
		}
	}
	public static class InterfaceType extends Type {
		public InterfaceType(final String name, final StaticScope enclosedScope, final boolean isTemplateInstantiation) {
			super(enclosedScope);
			name_ = name;
			isTemplateInstantiation_ = isTemplateInstantiation;
			selectorSignatureMap_ = new LinkedHashMap<String, List<MethodSignature>>();
		}
		@Override public String name() {
			return name_;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
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
			
			if (false == retval) {
				// See if t just outright implements this interface
				if (t instanceof ClassOrContextType) {
					final ClassOrContextType cocType = (ClassOrContextType)t;
					final List<InterfaceType> itsInterfaces = cocType.interfaceTypes();
					for (final InterfaceType anInterface : itsInterfaces) {
						if (anInterface.pathName().equals(pathName())) {
							retval = true;
							break;
						}
					}
				}
			}
			
			if (false == retval) {
				// See if all of my operations are supported by t
				retval = true;
				for (final String methodName : this.selectorSignatureMap().keySet()) {
					final List<MethodSignature> signatures = this.selectorSignatureMap().get(methodName);
					for (final MethodSignature interfaceSignature : signatures) {
						// Maybe there is a fight here to be had about whether the method
						// has public access...
						MethodDeclaration otherTypesMethod = t.enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
								methodName, interfaceSignature.formalParameterList(), false, /*parameterToIgnore*/ "this");
						if (null == otherTypesMethod) {
							if (t instanceof RoleType || t instanceof StagePropType) {
								// Then methods in the requires interface also apply. As long as
								// the object respond to the method, we don't care if if it's
								// part of the Role "public" interface or the Role / object
								// interface. There is no "public" or "private" here, so we'll
								// be generous
								final RoleType roleType = (RoleType)t;
								otherTypesMethod = ((StaticRoleScope)roleType.enclosedScope()).
										lookupRequiredMethodSelectorAndParameterList(methodName, interfaceSignature.formalParameterList());
								retval = (null != otherTypesMethod);
							} else {
								retval = false;
							}
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
			
			assert true;		// ever called? Yup. spell_check2.k
			
			final FormalParameterList methodSignatureFormalParameterList = methodSignature.formalParameterList();
			
			List<MethodSignature> signatures = null;
			if (selectorSignatureMap_.containsKey(methodSelector)) {
				signatures = selectorSignatureMap_.get(methodSelector);
				for (final MethodSignature signature : signatures) {
					if (FormalParameterList.alignsWithParameterListIgnoringParamNamed(
							signature.formalParameterList(),
							methodSignatureFormalParameterList,
							paramToIgnore, /*conversionAllowed*/ true)) {
						retval = signature;
						break;
					}
				}
			} else {
				// Get it from the declaration
				final Declaration potentialInterfaceDeclaration = enclosedScope().associatedDeclaration();
				if (potentialInterfaceDeclaration instanceof InterfaceDeclaration) {
					final InterfaceDeclaration interfaceDeclaration = (InterfaceDeclaration) potentialInterfaceDeclaration;
					retval = interfaceDeclaration.signatures_.get(methodSelector);
					if (null != retval) {
						final FormalParameterList formalParameterList = retval.formalParameterList();
						final FormalParameterList argumentList = methodSignature.formalParameterList();
						if (false == formalParameterList.alignsWith(argumentList)) {
							retval = null;
						}
					}
				}
				retval = null;
			}
			
			return retval;
		}
		
		public MethodSignature lookupMethodSignature(final String selectorName, final ActualOrFormalParameterList argumentList) {
			return lookupMethodSignatureCommon(selectorName, argumentList, false, null);
		}
		public MethodSignature lookupMethodSignatureWithConversion(final String selectorName, final ActualOrFormalParameterList argumentList) {
			return lookupMethodSignatureCommon(selectorName, argumentList, true, null);
		}
		public MethodSignature lookupMethodSignatureWithConversionIgnoringParameter(final String selectorName,
				final ActualOrFormalParameterList argumentList, final String parameterToIgnore) {
			return lookupMethodSignatureCommon(selectorName, argumentList, true, parameterToIgnore);
		}
		
		int recurDepth = 0;
		boolean recurRecovery = false;

		public MethodSignature lookupMethodSignatureCommon(final String selectorName,
				final ActualOrFormalParameterList argumentList, final boolean conversionAllowed,
				final String parameterToIgnore) {
			if (recurDepth > 5) {
				if (false == recurRecovery) {
						ErrorLogger.error(ErrorIncidenceType.Fatal, token(),
								"Type reference recursion involving argument of `", selectorName, "'.", "");
				}
				recurRecovery = true;
				return null;
			} else if (recurRecovery) {
				--recurDepth;
				if (0 == recurDepth) recurRecovery = false;
				return null;
			}
			recurDepth++;
			
			MethodSignature retval = null;
			List<MethodSignature> signatures = null;
			if (selectorSignatureMap_.containsKey(selectorName)) {
				signatures = selectorSignatureMap_.get(selectorName);
				for (final MethodSignature signature : signatures) {
					if (recurRecovery) break;
					if (FormalParameterList.alignsWithParameterListIgnoringParamNamed(signature.formalParameterList(),
							argumentList, parameterToIgnore, conversionAllowed)) {
						retval = signature;
						break;
					}
					assert (argumentList != null);
				}
			} else {
				final Declaration potentialInterfaceDecl = null == enclosedScope()? null:
					(null == enclosedScope().associatedDeclaration()? null: enclosedScope().associatedDeclaration());
				if (potentialInterfaceDecl instanceof InterfaceDeclaration) {
					final InterfaceDeclaration interfaceDecl = (InterfaceDeclaration) potentialInterfaceDecl;
					retval = interfaceDecl.lookupMethodSignatureDeclarationInInterfaceDecl(selectorName, argumentList);
				}
			}
			
			--recurDepth;
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
		
		public void elaborateFromTemplate(final TemplateDeclaration templateDeclaration,
				final ClassType baseClass,
				final StaticScope newEnclosedScope,
				final Declaration newAssociatedDeclaration,
				final Token token) {
			final TemplateType nominalType = (TemplateType)templateDeclaration.type();
			assert null != newEnclosedScope.parentScope();
			enclosedScope_ = newEnclosedScope;
			staticObjectDeclarationDictionary_ = new LinkedHashMap<String, ObjectDeclaration>();
			for (Map.Entry<String,ObjectDeclaration> iter : nominalType.staticObjectDeclarationDictionary_.entrySet()) {
				final String name = iter.getKey();
				final ObjectDeclaration decl = iter.getValue();
				final ObjectDeclaration declCopy = decl.copy();
				staticObjectDeclarationDictionary_.put(new String(name), declCopy);
			}
			
			// The Meat
			final TemplateTypeForAnInterface interfaceType = (TemplateTypeForAnInterface)templateDeclaration.type();
			final Map<String, List<MethodSignature>> signatureMap = interfaceType.selectorSignatureMap();
			
			final TemplateInstantiationInfo instantiationInfo = enclosedScope().templateInstantiationInfo();
			for (final String signatureName : signatureMap.keySet()) {
				final List<MethodSignature> signatureList = signatureMap.get(signatureName);
				if (null == signatureList) {
					assert null != signatureList;
				}
				for (final MethodSignature aSignature : signatureList) {
					final String returnTypeName = aSignature.type().name();
					Type returnType = instantiationInfo.classSubstitionForTemplateTypeNamed(returnTypeName);
					returnType = null != returnType? returnType: aSignature.type();

					final MethodSignature newSignature = new MethodSignature(aSignature.name(), returnType,
							aSignature.accessQualifier(), token, aSignature.isStatic());
					final FormalParameterList formalParameterList = new FormalParameterList();
					newSignature.addParameterList(formalParameterList);
					
					// Add a formal "this" parameter
					if (false == aSignature.isStatic()) {
						final ObjectDeclaration self = new ObjectDeclaration("this", this, token);
						formalParameterList.addFormalParameter(self);
					}
					
					// Update the types (return types and parameters) in the signature
					newSignature.setReturnType(returnType);	// redundant...
					
					final FormalParameterList formalParams = newSignature.formalParameterList();
					formalParams.mapTemplateParameters(instantiationInfo);
					
					// Add the signature to selectorSignatureMap_
					this.addSignature(newSignature);
				}
			}
			
			// No static objects in interfaces for now, I think,
			// so maybe this code can go.
			staticObjects_ = new LinkedHashMap<String, RTCode>();
			for (final Map.Entry<String,RTCode> iter : nominalType.staticObjects_.entrySet()) {
				final String name = iter.getKey();
				final RTCode programElement = iter.getValue();
				
				// We really should do a copy here of program element, but
				// we'll presume that code isn't modified and cross our
				// fingers. Adding a dup() function to that class hierarchy
				// would be quite a chore...
				final String stringCopy = name.substring(0, name.length() - 1);
				staticObjects_.put(stringCopy, programElement);
			}
		}
		
		public boolean isTemplateInstantiation() {
			return isTemplateInstantiation_;
		}

		
		private final String name_;
		private final boolean isTemplateInstantiation_;
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
			} else if (this.name().equals("boolean")) {
				if (operator.equals("!")) retval = true;
			}
			return retval;
		}
		@Override public boolean canBeLhsOfBinaryOperatorForRhsType(final String operator, final Type type) {
			// Built-in type
			assert true;
			boolean retval = false;
			if (false == type instanceof RoleType &&
					false == type instanceof StagePropType &&
					false == this.type().canBeConvertedFrom(type)) {
				retval = false;
			} else if (this.name().equals("int") || this.name().equals("Integer")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = true;
				else if (operator.equals("**")) retval = true;
				else if (operator.equals("<")) retval = true;
				else if (operator.equals("<=")) retval = true;
				else if (operator.equals(">")) retval = true;
				else if (operator.equals(">=")) retval = true;
				else if (operator.equals("==")) retval = true;
				else if (operator.equals("!=")) retval = true;
			} else if (this.name().equals("double") || this.name().equals("Double")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = false;
				else if (operator.equals("**")) retval = true;
				else if (operator.equals("<")) retval = true;
				else if (operator.equals("<=")) retval = true;
				else if (operator.equals(">")) retval = true;
				else if (operator.equals(">=")) retval = true;
				else if (operator.equals("==")) retval = true;
				else if (operator.equals("!=")) retval = true;
			} else if (this.name().equals("boolean")) {
				if (operator.equals("||")) retval = true;
				else if (operator.equals("&&")) retval = true;
				else if (operator.equals("^")) retval = true;
				else if (operator.equals("==")) retval = true;
				else if (operator.equals("!=")) retval = true;
			} else if (this.name().equals("String")) {
				if (operator.equals("+")) retval = true;
				else if (operator.equals("%")) retval = true;
				else if (operator.equals("-")) retval = true;
				else if (operator.equals("<")) retval = true;
				else if (operator.equals("<=")) retval = true;
				else if (operator.equals(">")) retval = true;
				else if (operator.equals(">=")) retval = true;
				else if (operator.equals("==")) retval = true;
				else if (operator.equals("!=")) retval = true;
			} else {
				// == and != are always Ok to test against NULL
				if (type.pathName().equals("Null")) {
					if (operator.equals("==")) retval = true;
					else if (operator.equals("!=")) retval = true;
				}
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
				else if (operator.equals("<=")) retval = true;
				else if (operator.equals(">=")) retval = true;
				else if (operator.equals("<")) retval = true;
				else if (operator.equals(">")) retval = true;
				else if (operator.equals("!=")) retval = true;
				else if (operator.equals("==")) retval = true;
			} else if (this.name().equals("double") || this.name().equals("Double")) {
				if (operator.equals("-")) retval = true;
				else if (operator.equals("+")) retval = true;
				else if (operator.equals("*")) retval = true;
				else if (operator.equals("/")) retval = true;
				else if (operator.equals("%")) retval = false;
				else if (operator.equals("**")) retval = true;
				else if (operator.equals("<=")) retval = true;
				else if (operator.equals(">=")) retval = true;
				else if (operator.equals("<")) retval = true;
				else if (operator.equals(">")) retval = true;
				else if (operator.equals("!=")) retval = true;
				else if (operator.equals("==")) retval = true;
			} else if (this.name().equals("boolean")) {
				if (operator.equals("||")) retval = true;
				else if (operator.equals("&&")) retval = true;
				else if (operator.equals("^")) retval = true;
				else if (operator.equals("!=")) retval = true;
				else if (operator.equals("==")) retval = true;
			} else if (this.name().equals("String")) {
				if (operator.equals("+")) retval = true;
				else if (operator.equals("%")) retval = true;
				else if (operator.equals("-")) retval = true;
				else if (operator.equals("<=")) retval = true;
				else if (operator.equals(">=")) retval = true;
				else if (operator.equals("<")) retval = true;
				else if (operator.equals(">")) retval = true;
				else if (operator.equals("!=")) retval = true;
				else if (operator.equals("==")) retval = true;
			}
			return retval;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			boolean retval = false;
			
			// t can be null on error conditions, so stumble elegantly
			if (null != t) {
				if (t.name().equals(name_)) {
					retval = true;
				} else if (name().equals("double") && (t.name().equals("int") || t.name().equals("Integer"))) {
					retval = true;
				} else if ((t.name().equals("int") || t.name().equals("Integer")) && t.name().equals("double")) {
					// TODO: Issue truncation warning?
					retval = true;
				} else if (t.name().equals("Null")) {
					retval = true;
				} else if (t instanceof RoleType|| t instanceof StagePropType) {
					// Then make sure that everything in the "requires" list of
					// the this is supported by the Role type
					
					final RoleType roleType = (RoleType)t;
					final StaticScope roleScope = roleType.enclosedScope();
					final Declaration associatedDecl = roleScope.associatedDeclaration();
					assert associatedDecl instanceof RoleDeclaration;
					final List<MethodDeclaration> requiredMethodDecls = this.enclosedScope().methodDeclarations();
					if (this.pathName().equals("Null")) {
						// Can always have a null object
						retval = true;
					} else {
						retval = true;
						for (final MethodDeclaration declaration : requiredMethodDecls) {
							final String requiredMethodName = declaration.name();
							final MethodSignature requiredMethodSignature = declaration.signature();
							
							// Does the Role have it?
							final MethodDeclaration mdecl = roleScope.lookupMethodDeclarationWithConversionIgnoringParameter(
									requiredMethodName,
									requiredMethodSignature.formalParameterList(),
									/*ignoreSignature*/ false,
									/*parameterToIgnore*/ "this");
							
							if (null == mdecl) {
								retval = false;
								break;
							}
						}
					}
				} else if (t instanceof InterfaceType) {
					// Then make sure that everything in the "requires" list of
					// this is supported by the Interface type
					
					final InterfaceType interT = (InterfaceType) t;
					if (this.pathName().equals("Null")) {
						// Can always have a null object
						retval = true;
					} else {
						retval = true;
				
						// Loop through all interface declarations. Make sure that
						// I support them all
						final Map<String, List<MethodSignature>> interfaceSignatures = interT.selectorSignatureMap();
						final Set<String> methodNames = interfaceSignatures.keySet();
						for (final String requiredMethodName : methodNames) {
							final List<MethodSignature> signaturesForName = interfaceSignatures.get(requiredMethodName);
							for (final MethodSignature requiredMethodSignature : signaturesForName) {
								final MethodSignature msignature = interT.lookupMethodSignatureWithConversionIgnoringParameter(
										requiredMethodName,
										requiredMethodSignature.formalParameterList(),
										/*parameterToIgnore*/ "this");
								if (null == msignature) {
									retval = false;
									break;
								}
							}
						}
					}
				}
			}
			
			return retval;
		}
		@Override public Type type() {
			return this;
		}
		
		private final String name_;
	}
	public static class RoleType extends Type {
		public RoleType(final String name, final StaticScope scope) {
			super(scope);
			name_ = name;
		}
		public void reportMismatchesWith(final Token token, final Type type) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			
			if (type instanceof RoleType && type.pathName().equals(pathName())) {
				// it's just one of us...
				;
			} else {
				final Map<String, List<MethodSignature>> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (final String methodName : requiredSelfSignatures.keySet()) {
					final List<MethodSignature> rolesSignatures = requiredSelfSignatures.get(methodName);
					
					for (final MethodSignature rolesSignature : rolesSignatures) {
						final MethodSignature signatureForMethodSelector =
							type.signatureForMethodSelectorIgnoringThisWithPromotionAndConversion(methodName, rolesSignature);
						if (null == signatureForMethodSelector) {
							ErrorLogger.error(ErrorIncidenceType.Fatal, token, "\t`",
									rolesSignature.name() + rolesSignature.formalParameterList().selflessGetText(),
									"' needed by Role `", name(),
									"' does not appear in interface of `", type.name() + "'.");
						} else if (signatureForMethodSelector.accessQualifier() != AccessQualifier.PublicAccess) {
							ErrorLogger.error(ErrorIncidenceType.Fatal, token, "\t`",
									rolesSignature.name() + rolesSignature.formalParameterList().selflessGetText(),
									"' needed by Role `", name(),
									"' is declared as private in interface of `", type.name() +
									"' and is therefore inaccessible to the Role.");
						} else if (rolesSignature.hasConstModifier()) {
							if (false == signatureForMethodSelector.hasConstModifier()) {
								ErrorLogger.error(ErrorIncidenceType.Fatal, signatureForMethodSelector.token(), "\t`",
										rolesSignature.name() + rolesSignature.formalParameterList().selflessGetText(),
										"' needed by Role `", name(),
										"' is missing a const modifier.", "");
							}
						}
					}
				}
			}
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t

			boolean retval = false;
			
			if (null != t && t.pathName().equals("Null")) {
				// Anything can be associated with a null object
				retval = true;
			} else if (t instanceof RoleType && t.pathName().equals(pathName())) {
				// it's just one of us...
				retval = true;
			} else if (null != associatedDeclaration_){
				final Map<String, List<MethodSignature>> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				retval = true;
				for (final Map.Entry<String, List<MethodSignature>> entry : requiredSelfSignatures.entrySet()) {
					final String methodName = entry.getKey();
					final List<MethodSignature> rolesSignatures = entry.getValue();
					
					// It's doubtful there are many, but it's possible. Cover them all.
					for (final MethodSignature rolesSignature : rolesSignatures) {
						final MethodSignature signatureForMethodSelector = null != t?
								t.signatureForMethodSelectorInHierarchyIgnoringThis(methodName, rolesSignature):
								null;
						if (null == signatureForMethodSelector) {
							// See if RHS is itself a Role / StageProp, and compare
							if (null != t && (t instanceof RoleType || t instanceof StagePropType)) {
								final RoleType otherAsRole = (RoleType)t;
								final Map<String, List<MethodSignature>> otherSignatures = otherAsRole.associatedDeclaration().requiredSelfSignatures();
								assert (otherSignatures != null);
								
								// Any one of the "otherSignatures" will do if it matches
								boolean found = false;
								final List<MethodSignature> appearancesOfThisSignatureInOther = otherSignatures.get(methodName);
								
								if (null == appearancesOfThisSignatureInOther || appearancesOfThisSignatureInOther.size() == 0) {
									// Nothing there to satisfy the signature, so no dice
									// (delicious find)
									retval = false;
									break;
								}
								
								for (final MethodSignature possibleMatchinSignature : appearancesOfThisSignatureInOther) {
									final FormalParameterList myParameterList = rolesSignature.formalParameterList();
									final FormalParameterList otherArgumentList = possibleMatchinSignature.formalParameterList();
									if (FormalParameterList.alignsWithParameterListIgnoringRoleStuff(myParameterList, otherArgumentList, true)) {
										if (rolesSignature.hasConstModifier()) {
											if (false == possibleMatchinSignature.hasConstModifier()) {
												// const doesn't match — isn't this one
												continue;
											}
										}
										found = true;
										break;
									}
								}
								retval = found;
								break;
							} else {
								retval = false;
								break;
							}
						} else {
							if (signatureForMethodSelector.accessQualifier() != AccessQualifier.PublicAccess) {
								// It would violate encapsulation of the object by the Role
								// if Role scripts could invoke private scripts of their Role-player
								retval = false;
							} else {
								if (rolesSignature.hasConstModifier()) {
									if (false == signatureForMethodSelector.hasConstModifier()) {
										retval = false;
										break;
									}
								}
							}
							break;
						}
					}

					if (false == retval) break;
				}
			} else {
				// associatedDeclaration_ is null?
				// assert false;
				retval = false;	// probably just a Pass1 stumble. chainable1.k
			}
			return retval;
		}
		@Override public boolean canBeLhsOfBinaryOperatorForRhsType(final String operator, final Type type) {
			// RoleType
			assert true;
			boolean retval;
			if ((operator.equals("==") || operator.equals("!=")) && type.pathName().equals("Null")) {
				// Can always compare with null
				retval = true;
			} else if (operator.equals(">") || operator.equals(">=") || operator.equals("<") || operator.equals("<=") ||
					operator.equals("==") || operator.equals("!=")) {
				retval = this.hasCompareToOrHasOperatorForArgOfType(operator, type);
			} else {
				retval = false;
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
			assert true;	// ever called? yes.
			return associatedDeclaration_;
		}
		@Override public Type type() {
			return this;
		}
		public StaticScope enclosingScope() {
			return this.enclosedScope().parentScope();
		}
		public MethodSignature lookupMethodSignatureDeclaration(final String methodName) {
			return associatedDeclaration_.lookupRequiredMethodSignatureDeclaration(methodName);
		}
		public boolean isAParameterlessRequiresMethod(final String methodSelectorName) {
			return associatedDeclaration_.isAParameterlessRequiresMethod(methodSelectorName);
		}
		public Declaration contextDeclaration() {
			return associatedDeclaration_.contextDeclaration();
		}
		public boolean isArray() {
			return associatedDeclaration_ instanceof RoleArrayDeclaration ||
				   associatedDeclaration_ instanceof StagePropArrayDeclaration;
		}
		public boolean hasCompareToOrHasOperatorForArgOfType(final String operator, final Type rhs) {
			boolean retval = false;
			final Map<String, List<MethodSignature>> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
			for (final String methodName : requiredSelfSignatures.keySet()) {
				final List<MethodSignature> requiredSignatureList = requiredSelfSignatures.get(methodName);
				if (methodName.equals("compareTo") || methodName.equals(operator)) {
					for (final MethodSignature rolesSignature: requiredSignatureList) {
						final FormalParameterList formalParameters = rolesSignature.formalParameterList();
						if (formalParameters.count() > 1) {
							final Declaration wannabeRhsArg = formalParameters.parameterAtPosition(1);
							assert wannabeRhsArg instanceof ObjectDeclaration || wannabeRhsArg.isError();
							final Type argType = wannabeRhsArg.type();
							if (rhs.pathName().equals(argType.pathName())) {
								retval = true;
								break;
							}
						}
					}
				}
			}
			return retval;
		}
		
		protected final String name_;
		protected RoleDeclaration associatedDeclaration_;
	}
	
	public static class StagePropType extends RoleType {
		public StagePropType(final String name, final StaticScope scope) {
			super(name, scope);
		}
		public void reportMismatchesWith(final Token token, final Type type) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t
			
			if (type instanceof StagePropType && type.pathName().equals(pathName())) {
				// it's just one of us...
				;
			} else {
				final Map<String, List<MethodSignature>> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				for (final String methodName : requiredSelfSignatures.keySet()) {
					final List<MethodSignature> rolesSignatures = requiredSelfSignatures.get(methodName);
					
					for (final MethodSignature rolesSignature : rolesSignatures) {
						final MethodSignature signatureForMethodSelector =
							type.signatureForMethodSelectorIgnoringThisWithPromotionAndConversion(methodName, rolesSignature);
						if (null == signatureForMethodSelector) {
							ErrorLogger.error(ErrorIncidenceType.Fatal, token, "\t`",
									rolesSignature.name() + rolesSignature.formalParameterList().selflessGetText(),
									"' needed by Stage Prop `", name(),
									"' does not appear in interface of `", type.name() + "'.");
						} else if (signatureForMethodSelector.accessQualifier() != AccessQualifier.PublicAccess) {
							ErrorLogger.error(ErrorIncidenceType.Fatal, token, "\t`",
									rolesSignature.name() + rolesSignature.formalParameterList().selflessGetText(),
									"' needed by Stage Prop `", name(),
									"' is declared as private in interface of `", type.name() +
									"' and is therefore inaccessible to the Stage Prop.");
						} else if (rolesSignature.hasConstModifier()) {
							if (false == signatureForMethodSelector.hasConstModifier() &&
									false == signatureForMethodSelector.isUnusedInThisContext()) {
								ErrorLogger.error(ErrorIncidenceType.Fatal, signatureForMethodSelector.token(), "\t`",
										rolesSignature.name() + rolesSignature.formalParameterList().selflessGetText(),
										"' needed by Stage Prop `", name(),
										"' is missing a const modifier.", "");
							}
						}
					}
				}
			}
		}
		public Map<String, List<MethodSignature>> requiredSelfSignatures() {
			return associatedDeclaration_.requiredSelfSignatures();
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t

			boolean retval = false;
			
			if (null != t && t.pathName().equals("Null")) {
				// Anything can be associated with a null object
				retval = true;
			} else if (t instanceof RoleType && t.name().equals(name())) {
				// it's just one of us...
				retval = true;
			} else if (null != associatedDeclaration_){
				final Map<String, List<MethodSignature>> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				retval = true;
				for (final Map.Entry<String, List<MethodSignature>> entry : requiredSelfSignatures.entrySet()) {
					final String methodName = entry.getKey();
					final List<MethodSignature> rolesSignatures = entry.getValue();
					
					// It's doubtful there are many, but it's possible. Cover them all.
					for (final MethodSignature rolesSignature : rolesSignatures) {
						final MethodSignature signatureForMethodSelector = null != t?
								t.signatureForMethodSelectorInHierarchyIgnoringThis(methodName, rolesSignature):
								null;
						if (null == signatureForMethodSelector) {
							// See if RHS is itself a Role / StageProp, and compare
							if (t instanceof RoleType || t instanceof StagePropType) {
								final RoleType otherAsRole = (RoleType)t;
								final Map<String, List<MethodSignature>> otherSignatures = otherAsRole.associatedDeclaration().requiredSelfSignatures();
								
								// Any one of the "otherSignatures" will do if it matches
								boolean found = false;
								final List<MethodSignature> appearancesOfThisSignatureInOther = otherSignatures.get(methodName);
								for (final MethodSignature possibleMatchinSignature : appearancesOfThisSignatureInOther) {
									final FormalParameterList myParameterList = rolesSignature.formalParameterList();
									final FormalParameterList otherArgumentList = possibleMatchinSignature.formalParameterList();
									if (FormalParameterList.alignsWithParameterListIgnoringRoleStuff(myParameterList, otherArgumentList, true)) {
										found = true;
										break;
									}
								}
								retval = found;
							} else {
								retval = false;
							}
							break;
						} else if (signatureForMethodSelector.accessQualifier() != AccessQualifier.PublicAccess) {
							// It would violate encapsulation of the object by the Role
							// if Role scripts could invoke private scripts of their Role-player
							retval = false;
							break;
						} else if (false == signatureForMethodSelector.hasConstModifier()) {
							// If it's declared unused in the published interface, it's O.K.
							
							final StagePropType stagePropType = t instanceof StagePropType? (StagePropType)t: null;
							final MethodSignature publishedStagePropMethodSignature = null == stagePropType? null:
												stagePropType.associatedDeclaration().lookupPublishedSignatureDeclaration(signatureForMethodSelector);
							if (null != publishedStagePropMethodSignature && false == publishedStagePropMethodSignature.isUnusedInThisContext()) {
								final String roleSignatureLineNumber = Integer.toString(signatureForMethodSelector.lineNumber()) +
										" does not match the contract at line " +
										Integer.toString(rolesSignature.lineNumber());
								parserPass.errorHook5p2(ErrorIncidenceType.Warning, token,
										"WARNING: Required methods for stage props should be const. The declaration of method `",
										signatureForMethodSelector.name(), "' at line ",
										roleSignatureLineNumber);
								break;
							}
						}
					}

					if (false == retval) break;
				}
			} else {
				// associatedDeclaration_ is null?
				// assert false;
				retval = false;	// probably just a Pass1 stumble. chainable1.k
			}
			return retval;
		}

		@Override public boolean canBeConvertedFrom(final Type t) {
			// Make sure that each method in my "requires" signature
			// is satisfied in the signature of t

			boolean retval = false;
			if (t instanceof StagePropType && t.name().equals(name())) {
				// it's just one of us...
				retval = true;
			} else if (t.pathName().equals("Null")) {
				// can always compare with Null
				retval = true;
			} else {
				retval = true;
				
				final Map<String, List<MethodSignature>> requiredSelfSignatures = associatedDeclaration_.requiredSelfSignatures();
				
				// Must cover them all
				for (final String methodName : requiredSelfSignatures.keySet()) {
					final List<MethodSignature> roleSignatures = requiredSelfSignatures.get(methodName);
					
					// Have to cover all of them
					for (final MethodSignature rolesSignature : roleSignatures) {
						final MethodSignature signatureForMethodSelector =
								t.signatureForMethodSelectorInHierarchyIgnoringThis(methodName, rolesSignature);
						if (null == signatureForMethodSelector) {
							retval = lookForRoleRHSThatMatches(t, methodName, rolesSignature);
						} else if (false == signatureForMethodSelector.hasConstModifier()) {
							// Now handled by error-reporting version above...
							// final String roleSignatureLineNumber = Integer.toString(rolesSignature.lineNumber());
							// ErrorLogger.error(ErrorIncidenceType.Warning, signatureForMethodSelector.lineNumber(),
							// 		"Required methods for stage props should be const. The declaration of method ",
							//		signatureForMethodSelector.name(), " does not match the contract at line ", roleSignatureLineNumber);
						}
						
						if (false == retval) break;
					}
					
					if (false == retval) break;
				}
			}
			return retval;
		}
		
		private boolean lookForRoleRHSThatMatches(final Type t, final String methodName,
				final MethodSignature lhsRoleSignature) {
			// See if RHS is itself a Role / StageProp, and compare
			
			boolean retval = false;
			if (t instanceof RoleType || t instanceof StagePropType) {
				final RoleType otherAsRole = (RoleType)t;
				final Map<String, List<MethodSignature>> otherSignatures = otherAsRole.associatedDeclaration().requiredSelfSignatures();
				final List<MethodSignature> appearancesOfThisSignatureInOther = otherSignatures.get(methodName);
				
				// Any one will do
				for (final MethodSignature appearanceOfThisSignatureInOther : appearancesOfThisSignatureInOther) {
					final FormalParameterList myParameterList = lhsRoleSignature.formalParameterList();
					if (null != appearanceOfThisSignatureInOther) {
						final FormalParameterList otherArgumentList = appearanceOfThisSignatureInOther.formalParameterList();
						if (FormalParameterList.alignsWithParameterListIgnoringRoleStuff(myParameterList, otherArgumentList, true)) {
							if (appearanceOfThisSignatureInOther.hasConstModifier()) {
								retval = true;
								break;
							}
						}
					}
				}
			} else {
				retval = false;
			}
			return retval;
		}
		
		@Override public boolean canBeLhsOfBinaryOperatorForRhsType(final String operator, final Type rhsType) {
			// This is a bit dicey — it really should be type-checked
			// by the caller as well. This doesn't amount to much more
			// than a syntactic check, since Roles have a decidely macro-
			// like behaviour to them..
			//
			// Check out caller: Pass2Listener.binopTypeCheck(Expression, String, Expression, Token) line: 441	
			// StageProp Type
			assert true;	// money_transfer3.k
			boolean retval;
			if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("*")) {
				retval = true;
			} else {
				if (hasCompareToOrHasOperatorForArgOfType(operator, rhsType) && operator.equals(">") || operator.equals("<") || operator.equals(">=") ||
						operator.equals("<=") || operator.equals("!=") || operator.equals("==")) {
					retval = true;
				} else {
					retval = false;
				}
			}
			return retval;
		}
	}
	
	public static class ArrayType extends Type implements IndexableType {
		public ArrayType(final String name, final Type baseType) {
			super(null);
			name_ = name;
			sizeMethodDeclaration_ = null;
			atMethodDeclaration_ = null;
			atPutMethodDeclaration_ = null;
			baseType_ = baseType;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return canBeConvertedFrom(t);
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			boolean retval = true;
			if (t.name().equals("Null")) {
				// redundant but clear
				retval = true;
			} else if (false == t instanceof ArrayType) {
				retval = false;
			} else {
				final ArrayType tAsArray = (ArrayType) t;
				retval = baseType_.canBeConvertedFrom(tAsArray.baseType());
			}
			return retval;
		}
		public MethodSignature signatureForMethodSelectorCommon(final String methodSelector, final MethodSignature methodSignature,
				final String paramToIgnore, final HierarchySelector baseClassSearch) {
			final FormalParameterList methodSignatureFormalParameterList = methodSignature.formalParameterList();
			MethodSignature retval = null;
			final StaticScope scope = StaticScope.globalScope();
			if (methodSelector.equals("at")) {
				retval = atMethodDeclaration(scope).signature();
				if (false == FormalParameterList.alignsWithParameterListIgnoringParamNamed(retval.formalParameterList(), methodSignatureFormalParameterList, paramToIgnore, true)) {
					retval = null;
				}
			} else if (methodSelector.equals("atPut")) {
				retval = atPutMethodDeclaration(scope).signature();
				if (false == FormalParameterList.alignsWithParameterListIgnoringParamNamed(retval.formalParameterList(), methodSignatureFormalParameterList, paramToIgnore, true)) {
					retval = null;
				}
			} else if (methodSelector.equals("size")) {
				retval = sizeMethodDeclaration(scope).signature();
				if (false == FormalParameterList.alignsWithParameterListIgnoringParamNamed(retval.formalParameterList(), methodSignatureFormalParameterList, paramToIgnore, true)) {
					retval = null;
				}
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
		
		// This is kind of a hack because arrays as objects don't
		// have that many methods and they really don't have
		// their own scope where we can declare things like this.
		public MethodDeclaration sizeMethodDeclaration(final StaticScope enclosingScope) {
			if (null == sizeMethodDeclaration_) {
				final ObjectDeclaration self = new ObjectDeclaration("this", this, null);
				final FormalParameterList formalParameterList = new FormalParameterList();
				formalParameterList.addFormalParameter(self);
				final MethodSignature signature = new MethodSignature("size",
						StaticScope.globalScope().lookupTypeDeclaration("int"),
						AccessQualifier.PublicAccess, null, false);
				signature.addParameterList(formalParameterList);
				dummyScope_ = new StaticScope(enclosingScope);
				sizeMethodDeclaration_ = new MethodDeclaration(signature, dummyScope_, null);
				sizeMethodDeclaration_.setHasConstModifier(true);
				dummyScope_.declareMethod(sizeMethodDeclaration_, null);
			}
			return sizeMethodDeclaration_;
		}
		public MethodDeclaration atMethodDeclaration(final StaticScope enclosingScope) {
			if (null == atMethodDeclaration_) {
				final ObjectDeclaration self = new ObjectDeclaration("this", this, null);
				final FormalParameterList formalParameterList = new FormalParameterList();
				final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
				final ObjectDeclaration theIndex = new ObjectDeclaration("theIndex", intType, null);
				
				formalParameterList.addFormalParameter(theIndex);
				formalParameterList.addFormalParameter(self);
				
				final MethodSignature signature = new MethodSignature("at",
						baseType_,
						AccessQualifier.PublicAccess, null, false);
				signature.addParameterList(formalParameterList);
				dummyScope_ = new StaticScope(enclosingScope);
				atMethodDeclaration_ = new MethodDeclaration(signature, dummyScope_, null);
				atMethodDeclaration_.setHasConstModifier(true);
				dummyScope_.declareMethod(atMethodDeclaration_, null);
			}
			return atMethodDeclaration_;
		}
		public MethodDeclaration atPutMethodDeclaration(final StaticScope enclosingScope) {
			if (null == atPutMethodDeclaration_) {
				final ObjectDeclaration self = new ObjectDeclaration("this", this, null);
				final FormalParameterList formalParameterList = new FormalParameterList();
				final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
				final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
				final ObjectDeclaration theIndex = new ObjectDeclaration("theIndex", intType, null);
				final ObjectDeclaration object = new ObjectDeclaration("object", baseType_, null);
				
				formalParameterList.addFormalParameter(object);
				formalParameterList.addFormalParameter(theIndex);
				formalParameterList.addFormalParameter(self);
				
				final MethodSignature signature = new MethodSignature("atPut",
						voidType,
						AccessQualifier.PublicAccess, null, false);
				signature.addParameterList(formalParameterList);
				dummyScope_ = new StaticScope(enclosingScope);
				atPutMethodDeclaration_ = new MethodDeclaration(signature, dummyScope_, null);
				atPutMethodDeclaration_.setHasConstModifier(false);
				dummyScope_.declareMethod(atPutMethodDeclaration_, null);
			}
			return atPutMethodDeclaration_;
		}
		
		private final Type baseType_;
		private final String name_;
		private MethodDeclaration sizeMethodDeclaration_;
		private MethodDeclaration atMethodDeclaration_;
		private MethodDeclaration atPutMethodDeclaration_;
		private StaticScope dummyScope_;
	}
	
	public static class TemplateParameterType extends Type {
		public TemplateParameterType(final String name, final ClassType baseClassType) {
			super(null);
			name_ = name;
			baseClassType_ = baseClassType;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
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
	
	public static class VarargsType extends Type {
		public VarargsType(final String name) {
			super(null);
			name_ = name;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			return true;
		}
		@Override public String name() {
			return name_;
		}
		@Override public Type type() {
			return this;
		}

		private final String name_;
	}
	
	public static class ErrorType extends Type {
		public ErrorType() {
			super(null);
		}
		@Override public boolean canBeLhsOfBinaryOperatorForRhsType(final String operator, final Type type) {
			return true;
		}
		@Override public boolean isBaseClassOf(final Type t) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass) {
			return true;
		}
		@Override public boolean canBeConvertedFrom(final Type t) {
			return true;
		}
		@Override public String name() {
			return "*Error*";
		}
		@Override public Type type() {
			return this;
		}
		@Override public boolean isError() {
			return true;
		}
		@Override public StaticScope enclosedScope() {
			return StaticScope.globalScope();
		}
	}
	
	
	public abstract boolean canBeConvertedFrom(final Type t);
	public abstract boolean canBeConvertedFrom(final Type t, final Token token, final Pass1Listener parserPass);

	public boolean isError() {
		return false;
	}
	public boolean isntError() {
		return false == isError();
	}
	
	public boolean hasUnaryOperator(final String operator) {
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
	public MethodSignature signatureForMethodSelectorCommon(final String methodSelector,
			final MethodSignature methodSignature,
			final String paramToIgnore, final HierarchySelector baseClassSearch) {
		final FormalParameterList methodSignatureFormalParameterList = methodSignature.formalParameterList();
		MethodSignature retval = null;
		if (null != enclosedScope_) {
			final MethodDeclaration mDecl = /*class*/enclosedScope_.lookupMethodDeclarationIgnoringParameter(methodSelector, methodSignatureFormalParameterList, paramToIgnore,
					/* conversionAllowed = */ false);
			
			// mDecl can be null under error conditions
			retval = null == mDecl? null: mDecl.signature();
		
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
		}
		
		return retval;
	}
	public MethodSignature signatureForMethodSelectorGeneric(final String methodSelector, final MethodSignature methodSignature) {
		final FormalParameterList methodSignatureFormalParameterList = methodSignature.formalParameterList();
		MethodSignature retval = null;
		if (null == enclosedScope_ && (false == this instanceof ErrorType)) {
			assert null != enclosedScope_;
		} else if (this instanceof ErrorType) {
			retval = null;
		} else {
			final MethodDeclaration mDecl = /*class*/enclosedScope_.lookupMethodDeclarationIgnoringParameter(methodSelector,
					methodSignatureFormalParameterList,
					/* paramToIgnore */ "this",
					/* conversionAllowed = */ true);
			
			retval = null == mDecl? null: mDecl.signature();
		
			// We need to explore base class signatures
			if (null == retval) {
				if (this instanceof ClassType) {
					final ClassType classType = (ClassType) this;
					if (null != classType.baseClass()) {
						// Recur. Good code reuse.
						retval = classType.baseClass().signatureForMethodSelectorGeneric(methodSelector, methodSignature);
					} else {
						retval = null;	// redundant
					}
				} else {
					retval = null;	// redundant
				}
			}
		}
		
		if (null == retval) {
			// Check declaration
			final Declaration declaration = enclosedScope().associatedDeclaration();
			if (declaration instanceof InterfaceDeclaration) {
				final InterfaceDeclaration interfaceDeclaration = (InterfaceDeclaration) declaration;
				retval = interfaceDeclaration.lookupMethodSignatureDeclaration(methodSelector, methodSignature.formalParameterList());
			}
		}
		
		return retval;
	}
	public MethodSignature signatureForMethodSelectorInHierarchyIgnoringThis(final String methodSelector, final MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, "this",
				HierarchySelector.AlsoSearchBaseClass);
	}
	public MethodSignature signatureForMethodSelectorIgnoringThis(final String methodSelector, final MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, "this",
				HierarchySelector.ThisClassOnly);
	}
	public MethodSignature signatureForMethodSelectorIgnoringThisWithPromotion(final String methodSelector, final MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, "this",
				HierarchySelector.AlsoSearchBaseClass);
	}
	public MethodSignature signatureForMethodSelectorIgnoringThisWithPromotionAndConversion(final String methodSelector, final MethodSignature methodSignature) {
		return signatureForMethodSelectorGeneric(methodSelector, methodSignature);
	}
	public MethodSignature signatureForMethodSelector(final String methodSelector, final MethodSignature methodSignature) {
		return signatureForMethodSelectorCommon(methodSelector, methodSignature, null,
				HierarchySelector.ThisClassOnly);
	}
	public int lineNumber() {
		return null == token_? 0: token_.getLine();
	}
	public Token token() {
		return token_;
	}
	
	protected StaticScope enclosedScope_;
	protected Map<String, ObjectDeclaration> staticObjectDeclarationDictionary_;
	protected Map<String, RTCode> staticObjects_;
	private final Token token_;
}
