package info.fulloo.trygve.semantic_analysis;

/*
 * Trygve IDE
 *   Copyright (c)2015 James O. Coplien
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import static java.util.Arrays.asList;
import info.fulloo.trygve.add_ons.DateClass;
import info.fulloo.trygve.add_ons.ListClass;
import info.fulloo.trygve.add_ons.MathClass;
import info.fulloo.trygve.add_ons.SystemClass;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Type.BuiltInType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.ContextType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.mylibrary.SimpleList;

public class StaticScope {
	public StaticScope(final StaticScope parentScope) {
		// Usual constructor
		parentScope_ = parentScope;
		subScopes_ = new SimpleList();
		if (parentScope_ != null) {
			parentScope_.addSubScope(this);
		}
		objectDeclarationDictionary_ = new LinkedHashMap<String, ObjectDeclaration>();
		staticObjectDeclarationDictionary_ = new LinkedHashMap<String, ObjectDeclaration>();
		typeDeclarationDictionary_ = new LinkedHashMap<String, Type>();
		methodDeclarationDictionary_ = new LinkedHashMap<String, ArrayList<MethodDeclaration>>();
		contextDeclarationDictionary_ = new LinkedHashMap<String, ContextDeclaration>();
		roleAndStagePropDeclarationDictionary_ = new LinkedHashMap<String, RoleDeclaration>();
		classDeclarationDictionary_ = new LinkedHashMap<String, ClassDeclaration>();
		templateDeclarationDictionary_ = new LinkedHashMap<String, TemplateDeclaration>();
		interfaceDeclarationDictionary_ =  new LinkedHashMap<String,InterfaceDeclaration>();
		hasDeclarationsThatAreLostBetweenPasses_ = false;
		templateInstantiationInfo_ = null;
	}
	
	public StaticScope(final StaticScope parentScope, final boolean losesMemory) {
		this(parentScope);
		hasDeclarationsThatAreLostBetweenPasses_ = losesMemory;
	}
	
	public StaticScope(final StaticScope scope, final String copy, final StaticScope newEnclosingScope,
			final Declaration newAssociatedDeclaration, final TemplateInstantiationInfo newTypes) {
		// Special copy constructor, mainly for instantiating templates
		super();
		assert null != newTypes;
		
		parentScope_ = newEnclosingScope;	// could be global scope. just don't want it to be the template
		if (null != parentScope_) {
			parentScope_.addSubScope(this);
		}
		subScopes_ = scope.subScopes_;		// will update below
		
		// This will point to the template declaration?
		// No, not always. Ideally, a class like List<int,String>
		this.setDeclaration(newAssociatedDeclaration);
		
		objectDeclarationDictionary_ = scope.objectDeclarationDictionary_;
		staticObjectDeclarationDictionary_ = scope.staticObjectDeclarationDictionary_;
		typeDeclarationDictionary_ = scope.typeDeclarationDictionary_;
		contextDeclarationDictionary_ = scope.contextDeclarationDictionary_;
		classDeclarationDictionary_ = scope.classDeclarationDictionary_;
		templateDeclarationDictionary_ = scope.templateDeclarationDictionary_;
		roleAndStagePropDeclarationDictionary_ = scope.roleAndStagePropDeclarationDictionary_;
		interfaceDeclarationDictionary_ =  scope.interfaceDeclarationDictionary_;
		hasDeclarationsThatAreLostBetweenPasses_ = scope.hasDeclarationsThatAreLostBetweenPasses_;
		templateInstantiationInfo_ = newTypes;
		
		methodDeclarationDictionary_ = new LinkedHashMap<String,ArrayList<MethodDeclaration>>();
		for (Map.Entry<String,ArrayList<MethodDeclaration>> iter : scope.methodDeclarationDictionary_.entrySet()) {
			final String methodSelector = iter.getKey();
			final ArrayList<MethodDeclaration> decls = new ArrayList<MethodDeclaration>();
			final ArrayList<MethodDeclaration> oldDecls = iter.getValue();
			for (MethodDeclaration methodDecl : oldDecls) {
				final MethodDeclaration newMethodDecl = methodDecl.copyWithNewEnclosingScopeAndTemplateParameters(this, newTypes);
				decls.add(newMethodDecl);
				subScopes_.remove(methodDecl.enclosedScope());
				subScopes_.add(newMethodDecl.enclosedScope());
			}
			methodDeclarationDictionary_.put(methodSelector, decls);
		}
	}
	
	public static void resetGlobalScope() {
		globalScope_ = new StaticScope(null);
		StaticScope.reinitializeBuiltIns();
		
		// Meta-stuff for boundary conditions
		final Type t = new ClassType(" Class", globalScope_, null);
		final ObjectDeclaration object = new ObjectDeclaration(" Object", t, 0);
		globalScope_.setDeclaration(object);
	}

	private static void reinitializeBuiltIns() {
		if (null == globalScope_.lookupTypeDeclaration("int")) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			
			final StaticScope objectsScope = new StaticScope(globalScope_);
			final Type objectType = new ClassType("Object", objectsScope, null);
			globalScope_.declareType(objectType);
			
			// Should really take itself as a base class.... Or something... I don't
			// want a metaclass architecture because it's just not part of the human
			// mental model — at least not in terms of everyday childlike communication.
			// Something with self-reference would be O.K.

			reinitializeObject(objectType, objectsScope);
			
			final Type classType = new BuiltInType("Class");
			globalScope_.declareType(classType);
			
			final Type intType = reinitializeInt("int");
			
			reinitializeInt("Integer");
			
			reinitializeDouble(intType);
			
			reinitializeBoolean();
		
			reinitializeString(intType, globalScope_.lookupTypeDeclaration("boolean"));
			
			final Type voidType = new BuiltInType("void");
			globalScope_.declareType(voidType);
			
			final Type nullType = new BuiltInType("Null");
			globalScope_.declareType(nullType);
			
			reinitializeCombos(voidType, intType);
			
			SystemClass.setup();
			ListClass.setup();
			MathClass.setup();
			DateClass.setup();
		}
	}
	
	private static void addObjectMethod(final Type objectType, final String methodSelectorName, final Type returnType,
			final List<String> paramNames, final List<Type> paramTypes,
			final boolean isStatic) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;

		FormalParameterList formals = new FormalParameterList();
		if (null != paramNames) {
			final Iterator<Type> typeIter = paramTypes.iterator();
			for (final String paramName : paramNames) {
				final Type paramType = typeIter.next();
			    final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
			    formals.addFormalParameter(formalParameter);
			}
		}
		
		if (false == isStatic) {
			final ObjectDeclaration self = new ObjectDeclaration("this", objectType, 0);
			formals.addFormalParameter(self);
		}
		
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelectorName, objectType.enclosedScope(), returnType, Public, 0, isStatic);
		methodDecl.addParameterList(formals);
		objectType.enclosedScope().declareMethod(methodDecl);
	}
	
	private static void reinitializeObject(final Type objectType, final StaticScope objectsScope) {
		final ClassDeclaration objectClass = new ClassDeclaration("Object", objectsScope, null, 157239);
		globalScope_.declareClass(objectClass);
		objectClass.setType(objectType);
		objectsScope.setDeclaration(objectClass);
		
		typeDeclarationList_.add(objectClass);
	}
	
	private static Type reinitializeInt(final String typeName) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		final Type intType = new BuiltInType(typeName);
		final ClassDeclaration objectBaseClass = StaticScope.globalScope().lookupClassDeclaration("Object");
		assert null != objectBaseClass;
		
		final ClassDeclaration intDeclaration = new ClassDeclaration(typeName, intType.enclosedScope(), objectBaseClass, 0);
		
		final ObjectDeclaration formalParameter = new ObjectDeclaration("rhs", intType, 0);
		final ObjectDeclaration self = new ObjectDeclaration("this", intType, 0);
		final FormalParameterList formals = new FormalParameterList();
		formals.addFormalParameter(formalParameter);
		formals.addFormalParameter(self);
		MethodDeclaration methodDecl = new MethodDeclaration("+", intType.enclosedScope(), intType, Public, 0, false);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		methodDecl = new MethodDeclaration("-", intType.enclosedScope(), intType, Public, 0, false);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		methodDecl = new MethodDeclaration("*", intType.enclosedScope(), intType, Public, 0, false);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		methodDecl = new MethodDeclaration("**", intType.enclosedScope(), intType, Public, 0, false);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		methodDecl = new MethodDeclaration("/", intType.enclosedScope(), intType, Public, 0, false);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		methodDecl = new MethodDeclaration("%", intType.enclosedScope(), intType, Public, 0, false);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		globalScope_.declareType(intType);
		globalScope_.declareClass(intDeclaration);
		intType.enclosedScope().setDeclaration(intDeclaration);
		intDeclaration.setType(intType);
		
		typeDeclarationList_.add(intDeclaration);
		
		return intType;
	}
	
	private static void reinitializeDouble(final Type intType) {
		final Type doubleType = new BuiltInType("double");
		final ClassDeclaration objectBaseClass = StaticScope.globalScope().lookupClassDeclaration("Object");
		assert null != objectBaseClass;
		
		final ClassDeclaration doubleDeclaration = new ClassDeclaration("double", doubleType.enclosedScope(), objectBaseClass, 0);
		
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		final ObjectDeclaration formalParameter = new ObjectDeclaration("rhs", doubleType, 0);
		ObjectDeclaration self = new ObjectDeclaration("this", doubleType, 0);
		FormalParameterList formals = new FormalParameterList();
		formals.addFormalParameter(formalParameter);
		formals.addFormalParameter(self);
		
		StaticScope newScope = new StaticScope(doubleType.enclosedScope());
		MethodDeclaration methodDecl = new MethodDeclaration("+", newScope, doubleType, Public, 0, false);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		newScope = new StaticScope(doubleType.enclosedScope());
		methodDecl = new MethodDeclaration("-", newScope, doubleType, Public, 0, false);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		newScope = new StaticScope(doubleType.enclosedScope());
		methodDecl = new MethodDeclaration("*", newScope, doubleType, Public, 0, false);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		newScope = new StaticScope(doubleType.enclosedScope());
		methodDecl = new MethodDeclaration("/", newScope, doubleType, Public, 0, false);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		newScope = new StaticScope(doubleType.enclosedScope());
		methodDecl = new MethodDeclaration("**", newScope, doubleType, Public, 0, false);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		newScope = new StaticScope(doubleType.enclosedScope());
		methodDecl = new MethodDeclaration("%", newScope, doubleType, Public, 0, false);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		final FormalParameterList formalsWithInt = new FormalParameterList();
		final ObjectDeclaration formalIntParameter = new ObjectDeclaration("rhs", intType, 0);
		formalsWithInt.addFormalParameter(formalIntParameter);
		formalsWithInt.addFormalParameter(self);
		
		methodDecl = new MethodDeclaration("**", doubleType.enclosedScope(), doubleType, Public, 0, false);
		methodDecl.addParameterList(formalsWithInt);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		globalScope_.declareType(doubleType);
		globalScope_.declareClass(doubleDeclaration);

		doubleType.enclosedScope().setDeclaration(doubleDeclaration);
		doubleDeclaration.setType(doubleType);
		
		typeDeclarationList_.add(doubleDeclaration);
	}
	
	private static void addStringMethod(final Type stringType, final String methodSelectorName, final Type returnType,
			final List<String> paramNames, final List<Type> paramTypes) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration self = new ObjectDeclaration("this", stringType, 0);
		FormalParameterList formals = new FormalParameterList();
		if (null != paramNames) {
			final Iterator<Type> typeIter = paramTypes.iterator();
			for (final String paramName : paramNames) {
				final Type paramType = typeIter.next();
			    final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
			    formals.addFormalParameter(formalParameter);
			}
		}
		formals.addFormalParameter(self);
		final StaticScope myScope = new StaticScope(stringType.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelectorName, myScope, returnType, Public, 0, false);
		methodDecl.addParameterList(formals);
		stringType.enclosedScope().declareMethod(methodDecl);
	}
	
	private static void reinitializeString(final Type intType, final Type booleanType) {
		final Type stringType = new BuiltInType("String");
		final ClassDeclaration objectBaseClass = StaticScope.globalScope().lookupClassDeclaration("Object");
		assert null != objectBaseClass;
		
		final ClassDeclaration stringDeclaration = new ClassDeclaration("String", stringType.enclosedScope(), objectBaseClass, 0);
		
		addStringMethod(stringType, "+", stringType, asList("rhs"), asList(stringType));
		
		addStringMethod(stringType, "length", intType, null, null);
		
		addStringMethod(stringType, "substring", stringType, asList("end", "start"), asList(intType, intType));
		
		addStringMethod(stringType, "indexOf", intType, asList("searchString"), asList(stringType));
		
		addStringMethod(stringType, "contains", booleanType, asList("searchString"), asList(stringType));
		
		addStringMethod(stringType, "toString", stringType, null, null);
		
		globalScope_.declareType(stringType);
		stringDeclaration.setType(stringType);
		globalScope_.declareClass(stringDeclaration);
		
		stringType.enclosedScope().setDeclaration(stringDeclaration);
		stringDeclaration.setType(stringType);
		
		typeDeclarationList_.add(stringDeclaration);
	}
	
	private static void reinitializeBoolean() {
		final Type booleanType = new BuiltInType("boolean");
		final ClassDeclaration objectBaseClass = StaticScope.globalScope().lookupClassDeclaration("Object");
		assert null != objectBaseClass;
		
		final StaticScope booleanScope = booleanType.enclosedScope();
		final ClassDeclaration booleanClassDecl = new ClassDeclaration("boolean", booleanScope, objectBaseClass, 0);
		booleanScope.setDeclaration(booleanClassDecl);
		
		globalScope_.declareType(booleanType);
		booleanClassDecl.setType(booleanType);
		globalScope_.declareClass(booleanClassDecl);
		
		typeDeclarationList_.add(booleanClassDecl);
	}
	
	private static void reinitializeCombos(final Type voidType, final Type intType) {
		MethodDeclaration methodDecl = null;
		FormalParameterList formals = null;
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration self = null;
		
		// Anticlimatic...
		final Type stringType = StaticScope.globalScope().lookupTypeDeclaration("String");
		assert null != stringType;
		final Type doubleType = StaticScope.globalScope().lookupTypeDeclaration("double");
		assert null != doubleType;
		
		StaticScope newScope = new StaticScope(doubleType.enclosedScope());
		methodDecl = new MethodDeclaration("toString", newScope, stringType, Public, 0, false);
		methodDecl.signature().setHasConstModifier(true);
		formals = new FormalParameterList();
		self = new ObjectDeclaration("this", doubleType, 0);
		formals.addFormalParameter(self);
		methodDecl.addParameterList(formals);
		doubleType.enclosedScope().declareMethod(methodDecl);
		
		newScope = new StaticScope(intType.enclosedScope());
		methodDecl = new MethodDeclaration("toString", newScope, stringType, Public, 0, false);
		methodDecl.signature().setHasConstModifier(true);
		formals = new FormalParameterList();
		self = new ObjectDeclaration("this", intType, 0);
		formals.addFormalParameter(self);
		methodDecl.addParameterList(formals);
		intType.enclosedScope().declareMethod(methodDecl);
		
		final Type booleanType = StaticScope.globalScope().lookupTypeDeclaration("boolean");
		newScope = new StaticScope(booleanType.enclosedScope());
		methodDecl = new MethodDeclaration("toString", newScope, stringType, Public, 0, false);
		methodDecl.signature().setHasConstModifier(true);
		formals = new FormalParameterList();
		self = new ObjectDeclaration("this", booleanType, 0);
		formals.addFormalParameter(self);
		methodDecl.addParameterList(formals);
		booleanType.enclosedScope().declareMethod(methodDecl);
		
		final Type bigIntegerType = StaticScope.globalScope().lookupTypeDeclaration("Integer");
		assert null != bigIntegerType;
		newScope = new StaticScope(bigIntegerType.enclosedScope());

		methodDecl = new MethodDeclaration("toString", newScope, stringType, Public, 0, false);
		methodDecl.signature().setHasConstModifier(true);
		formals = new FormalParameterList();
		self = new ObjectDeclaration("this", bigIntegerType, 0);
		formals.addFormalParameter(self);
		methodDecl.addParameterList(formals);
		bigIntegerType.enclosedScope().declareMethod(methodDecl);
		
		final Type objectType = StaticScope.globalScope().lookupTypeDeclaration("Object");
		assert null != objectType;
		addObjectMethod(objectType, "assert", voidType, asList("msg", "tf"), asList(stringType, booleanType), false);
		addObjectMethod(objectType, "assert", voidType, asList("tf"), asList(booleanType), false);
	}
	
	public static StaticScope globalScope() { return globalScope_; }
	
	public void declare(Declaration decl) {
		assert false;
	}
	
	public boolean hasDeclarationsThatAreLostBetweenPasses() {
		return hasDeclarationsThatAreLostBetweenPasses_;
	}
	
	public String name() {
		String retval = null;
		if (associatedDeclaration_ != null) {
			retval = associatedDeclaration_.name();
		} else {
			retval = "*unknown*";
		}
		assert retval != null;
		return retval;
	}
	
	public void declareContext(final ContextDeclaration decl) {
		final String contextName = decl.name();
		if (contextDeclarationDictionary_.containsKey(contextName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of context ", contextName, " in ", name());
		} else {
			contextDeclarationDictionary_.put(contextName, decl);
			if (null != parentScope_) parentScope_.checkMegaTypeShadowing(decl);
		}
	}
	
	private void checkMegaTypeShadowing(final TypeDeclaration decl) {
		final StaticScope parent = this.parentScope();
		if (null != parent) {
			final String name = decl.name();
			TypeDeclaration collidingDeclaration = null;
			if (name.equals(decl.name())) {
				boolean collision = false;
				if (contextDeclarationDictionary_.containsKey(name)) {
					collidingDeclaration = contextDeclarationDictionary_.get(name);
					collision = true;
				} else if (classDeclarationDictionary_.containsKey(name)) {
					collidingDeclaration = classDeclarationDictionary_.get(name);
					collision = true;
				} else if (roleAndStagePropDeclarationDictionary_.containsKey(name)) {
					collidingDeclaration = roleAndStagePropDeclarationDictionary_.get(name);
					collision = true;
				}
				if (collision) {
					ErrorLogger.error(ErrorType.Warning, decl.lineNumber(), "WARNING: Declaration hides ", name, " declaration at line ",
							Integer.toString(collidingDeclaration.lineNumber()));
				}
			}
			parent.checkMegaTypeShadowing(decl);
		}
	}
	
	public ContextDeclaration lookupContextDeclarationRecursive(final String contextName)
	{
		ContextDeclaration retval = this.lookupContextDeclaration(contextName);
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupContextDeclarationRecursive(contextName);
			}
		}
		return retval;
	}
	public ContextDeclaration lookupContextDeclaration(final String contextName) {
		ContextDeclaration retval = null;
		if (contextDeclarationDictionary_.containsKey(contextName)) {
			retval = contextDeclarationDictionary_.get(contextName);
		}
		return retval;
	}
	
	public void declareRoleOrStageProp(final RoleDeclaration decl) {
		final String roleOrStagePropName = decl.name();
		if (roleAndStagePropDeclarationDictionary_.containsKey(roleOrStagePropName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of role ", roleOrStagePropName, " in ", name());
		} else {
			roleAndStagePropDeclarationDictionary_.put(roleOrStagePropName, decl);
		}
		if (null != parentScope_) parentScope_.checkMegaTypeShadowing(decl);
	}
	
	public RoleDeclaration lookupRoleOrStagePropDeclarationRecursive(final String roleName) {
		RoleDeclaration retval = this.lookupRoleOrStagePropDeclaration(roleName);
		if (null == retval) {
			// Stop searching at Context boundary. If there are nested
			// Contexts we don't want to go wandering into THAT Context
			// and pick up a role. Also stop at Class boundaries - it
			// doesn't make sense to refer to a role inside of a class
			final Declaration associatedDeclaration = this.associatedDeclaration();
			final Type myType = null != associatedDeclaration? associatedDeclaration.type(): null;
			if (myType instanceof ClassType || myType instanceof ContextType) {
				retval = null;	// redundant, but clear
			} else if (null != parentScope_) {
				retval = parentScope_.lookupRoleOrStagePropDeclarationRecursive(roleName);
			}
		}
		return retval;
	}
	public RoleDeclaration lookupRoleOrStagePropDeclaration(final String roleName) {
		RoleDeclaration retval = null;
		if (roleAndStagePropDeclarationDictionary_.containsKey(roleName)) {
			retval = roleAndStagePropDeclarationDictionary_.get(roleName);
		} else {
			retval = null;
		}
		return retval;
	}
	
	public void declareClass(final ClassDeclaration decl) {
		final String className = decl.name();
		if (classDeclarationDictionary_.containsKey(className)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of class ", className, " in ", name());
		} else {
			classDeclarationDictionary_.put(className, decl);
		}
		if (null != parentScope_) parentScope_.checkMegaTypeShadowing(decl);
	}
	
	public void undeclareClass(final ClassDeclaration decl) {
		final String className = decl.name();
		if (classDeclarationDictionary_.containsKey(className)) {
			assert classDeclarationDictionary_.containsValue(decl);
			classDeclarationDictionary_.remove(className);
		} else {
			assert false;
		}
	}
	
	public void declareTemplate(final TemplateDeclaration decl) {
		final String templateName = decl.name();
		if (templateDeclarationDictionary_.containsKey(templateName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of template ", templateName, " in ", name());
		} else {
			templateDeclarationDictionary_.put(templateName, decl);
		}
		if (null != parentScope_) parentScope_.checkMegaTypeShadowing(decl);
	}
	public TemplateDeclaration lookupTemplateDeclarationRecursive(final String templateName) {
		TemplateDeclaration retval = this.lookupTemplateDeclaration(templateName);
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupTemplateDeclarationRecursive(templateName);
			}
		}
		return retval;
	}
	public TemplateDeclaration lookupTemplateDeclaration(final String templateName) {
		TemplateDeclaration retval = null;
		if (templateDeclarationDictionary_.containsKey(templateName)) {
			retval = templateDeclarationDictionary_.get(templateName);
		}
		return retval;
	}
	
	public ClassDeclaration lookupClassDeclarationRecursive(final String className) {
		ClassDeclaration retval = this.lookupClassDeclaration(className);
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupClassDeclarationRecursive(className);
			}
		}
		return retval;
	}
	public ClassDeclaration lookupClassDeclaration(final String className) {
		ClassDeclaration retval = null;
		if (classDeclarationDictionary_.containsKey(className)) {
			retval = classDeclarationDictionary_.get(className);
		}
		return retval;
	}
	
	public InterfaceDeclaration lookupInterfaceDeclarationRecursive(final String interfaceName) {
		InterfaceDeclaration retval = this.lookupInterfaceDeclaration(interfaceName);
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupInterfaceDeclarationRecursive(interfaceName);
			}
		}
		return retval;
	}
	public InterfaceDeclaration lookupInterfaceDeclaration(final String interfaceName) {
		InterfaceDeclaration retval = null;
		if (interfaceDeclarationDictionary_.containsKey(interfaceName)) {
			retval = interfaceDeclarationDictionary_.get(interfaceName);
		}
		return retval;
	}
	public void declareInterface(final InterfaceDeclaration decl) {
		final String interfaceName = decl.name();
		if (interfaceDeclarationDictionary_.containsKey(interfaceName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of interface ", interfaceName, " in ", name());
		} else {
			interfaceDeclarationDictionary_.put(interfaceName, decl);
		}
		if (null != parentScope_) parentScope_.checkMegaTypeShadowing(decl);
	}
	
	public void declareMethod(final MethodDeclaration decl) {
		final String methodName = decl.name();
			
		if (methodDeclarationDictionary_.containsKey(methodName)) {
			final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(methodName);
			for (final MethodDeclaration aDecl : oldEntry) {
				final FormalParameterList loggedSignature = aDecl.formalParameterList();
				if (null == loggedSignature && null == decl.formalParameterList()) {
					ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of method ", methodName, " in ", name());
					break;
				} else if (null != loggedSignature && loggedSignature.alignsWith(decl.formalParameterList())) {
					ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of method ", methodName, " in ", name());
					break;
				}
			}
			oldEntry.add(decl);
		} else {
			final ArrayList<MethodDeclaration> newEntry = new ArrayList<MethodDeclaration>();
			newEntry.add(decl);
			methodDeclarationDictionary_.put(methodName, newEntry);
		}
		if (null != parentScope_) parentScope_.checkMethodShadowing(decl);
	}
	
	private void checkMethodShadowing(final MethodDeclaration decl) {
		final StaticScope parent = this.parentScope();
		if (null != parent) {
			final String name = decl.name();
			MethodDeclaration collidingDeclaration = null;
			if (name.equals(decl.name())) {
				boolean collision = false;
				if (methodDeclarationDictionary_.containsKey(name)) {
					final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(name);
					for (final MethodDeclaration aDecl : oldEntry) {
						final FormalParameterList loggedSignature = aDecl.formalParameterList();
						if (null == loggedSignature && null == decl.formalParameterList()) {
							collidingDeclaration = aDecl;
							collision = true;
							break;
						} else if (null != loggedSignature && loggedSignature.alignsWith(decl.formalParameterList())) {
							collidingDeclaration = aDecl;
							collision = true;
							break;
						} else {
							ErrorLogger.error(ErrorType.Warning,
									decl.lineNumber(),
									"WARNING: Method declaration for `",
									decl.name(),
									"' may hide method declared at line ",
									String.valueOf(aDecl.lineNumber()));
						}
					}
				}
				if (collision) {
					ErrorLogger.error(ErrorType.Fatal, decl.lineNumber(), "Method ", name, " hides method of same name at line ",
							Integer.toString(collidingDeclaration.lineNumber()));
				}
			}
			parent.checkMethodShadowing(decl);
		}
	}
	
	public void declareType(final Type typeDecl) {
		final String typeName = typeDecl.name();
		if (typeDeclarationDictionary_.containsKey(typeName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of type ", typeName, " in ", name());
		} else {
			if (this.lookupTypeDeclarationRecursive(typeName) != null) {
				ErrorLogger.error(ErrorType.Fatal, "Type declaration of ", typeName, " might hide declaration in enclosing scope", "");
			} else {
				typeDeclarationDictionary_.put(typeName, typeDecl);
			}
		}
	}
	
	public void undeclareType(final Type typeDecl) {
		final String typeName = typeDecl.name();
		if (typeDeclarationDictionary_.containsKey(typeName)) {
			assert typeDeclarationDictionary_.containsValue(typeDecl);
			typeDeclarationDictionary_.remove(typeName);
		} else {
			assert false;
		}
	}
	
	public Type lookupTypeDeclarationRecursive(final String typeName) {
		Type retval = this.lookupTypeDeclaration(typeName);
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupTypeDeclarationRecursive(typeName);
			}
		}
		return retval;
	}
	public Type lookupTypeDeclaration(final String simpleTypeName) {
		Type retval = null;
		if (typeDeclarationDictionary_.containsKey(simpleTypeName)) {
			retval = typeDeclarationDictionary_.get(simpleTypeName);
		}
		return retval;
	}
	
	public void declareObject(final ObjectDeclaration decl) {
		final String objectName = decl.name();

		if (objectDeclarationDictionary_.containsKey(objectName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of object ", objectName, " in ", name());
		} else {
			reDeclareObject(decl);
		}
	}
	
	public void reDeclareObject(final ObjectDeclaration decl) {
		final String objectName = decl.name();
		objectDeclarationDictionary_.put(objectName, decl);
		decl.setEnclosingScope(this);
		if (null != parentScope_) parentScope_.checkObjectDeclarationShadowing(decl);
	}
	
	public void declareStaticObject(final ObjectDeclaration decl) {
		final String objectName = decl.name();
		if (objectDeclarationDictionary_.containsKey(objectName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of object ", objectName, " in ", name());
		} else if (staticObjectDeclarationDictionary_.containsKey(objectName)) {
			ErrorLogger.error(ErrorType.Fatal, "Multiple definitions of object: static ", objectName, " in ", name());
		} else {
			// Make sure that this is a class scope
			final Declaration associatedDeclaration = this.associatedDeclaration();
			assert null != associatedDeclaration;
			
			if (!(associatedDeclaration instanceof TypeDeclaration)) {
				ErrorLogger.error(ErrorType.Fatal, 0, "Static member ", objectName, " in ", name(),
						" may be declared only in a class, Context or Role", "");
			} else {
				final TypeDeclaration typeDeclaration = (TypeDeclaration)associatedDeclaration;
				typeDeclaration.declareStaticObject(decl);
				staticObjectDeclarationDictionary_.put(objectName, decl);
			}
		}
	}
	
	public Map<String, ObjectDeclaration> staticObjectDeclarations() {
		return staticObjectDeclarationDictionary_;
	}
	
	private void checkObjectDeclarationShadowing(final ObjectDeclaration decl) {
		final StaticScope parent = this.parentScope();
		if (null != parent) {
			final String name = decl.name();
			ObjectDeclaration collidingDeclaration = null;
			if (name.equals(decl.name())) {
				boolean collision = false;
				if (objectDeclarationDictionary_.containsKey(name)) {
					collidingDeclaration = objectDeclarationDictionary_.get(name);
					collision = true;
				}
				if (collision) {
					ErrorLogger.error(ErrorType.Fatal, decl.lineNumber(), "Declaration of ", name, " may hide declaration at line ",
							Integer.toString(collidingDeclaration.lineNumber()));
				}
			}
			parent.checkObjectDeclarationShadowing(decl);
		}
	}

	
	public ObjectDeclaration lookupObjectDeclarationRecursive(final String simpleIDName) {
		ObjectDeclaration retval = this.lookupObjectDeclaration(simpleIDName);
		if (null == retval) {
			// Stop searching at Class or Context boundary. If there are nested
			// Classes or Contexts we don't want to go wandering into THAT scope
			// and pick up a declaration.
			final Declaration associatedDeclaration = this.associatedDeclaration();
			final Type myType = null != associatedDeclaration? associatedDeclaration.type(): null;
			if (myType instanceof ClassType || myType instanceof ContextType) {
				retval = null;	// redundant, but clear
			} else if (null != parentScope_) {
				retval = parentScope_.lookupObjectDeclarationRecursive(simpleIDName);
			}
		}
		return retval;
	}
	public ObjectDeclaration lookupObjectDeclarationRecursiveWithinMethod(final String simpleIDName) {
		ObjectDeclaration retval = this.lookupObjectDeclaration(simpleIDName);
		if (null == retval) {
			// Like lookupObjectDeclarationRecursive, but it will also stop if
			// it encounters a method boundary. It searches for variables only
			// that are on the activation record (in any level of scope) within
			// the current method.
			final Declaration associatedDeclaration = this.associatedDeclaration();
			final Type myType = null != associatedDeclaration? associatedDeclaration.type(): null;
			if (myType instanceof ClassType || myType instanceof ContextType) {
				retval = null;	// redundant, but clear
			} else if (associatedDeclaration instanceof MethodDeclaration) {
				retval = null;	// redundant, but clear
			} else if (null != parentScope_) {
				retval = parentScope_.lookupObjectDeclarationRecursive(simpleIDName);
			}
		}
		return retval;
	}
	public ObjectDeclaration lookupObjectDeclaration(final String simpleIDName) {
		ObjectDeclaration retval = null;
		if (objectDeclarationDictionary_.containsKey(simpleIDName)) {
			retval = objectDeclarationDictionary_.get(simpleIDName);
		} else if (staticObjectDeclarationDictionary_.containsKey(simpleIDName)) {
			final TypeDeclaration associatedDeclaration = (TypeDeclaration)this.associatedDeclaration();
			retval = associatedDeclaration.lookupStaticObjectDeclaration(simpleIDName);
		}
		return retval;
	}
	
	public MethodDeclaration lookupMethodDeclarationRecursive(final String methodSelector,
			final ActualOrFormalParameterList parameterList,
			final boolean ignoreSignature) {
		MethodDeclaration retval = this.lookupMethodDeclaration(methodSelector, parameterList, ignoreSignature);
		if (null == retval) {
			// If this is a class, see if the base class has it
			if (associatedDeclaration_ instanceof ClassDeclaration) {
				final ClassDeclaration thisClass = (ClassDeclaration)associatedDeclaration_;
				final ClassDeclaration baseClass = thisClass.baseClassDeclaration();
				if (null != baseClass) {
					final StaticScope baseClassScope = baseClass.enclosedScope();
					retval = baseClassScope.lookupMethodDeclarationRecursive(methodSelector, parameterList, ignoreSignature);
				}
			}
		}
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupMethodDeclarationRecursive(methodSelector, parameterList, ignoreSignature);
			}
		}
		return retval;
	}
	
	public MethodDeclaration lookupMethodDeclaration(final String methodSelector,
			final ActualOrFormalParameterList parameterList,
			final boolean ignoreSignature) {
		MethodDeclaration retval = null;
		if (methodDeclarationDictionary_.containsKey(methodSelector)) {
			final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(methodSelector);
			for (final MethodDeclaration aDecl : oldEntry) {
				final FormalParameterList loggedSignature = aDecl.formalParameterList();
				final ActualOrFormalParameterList mappedLoggedSignature = null == loggedSignature? null:
					loggedSignature.mapTemplateParameters(templateInstantiationInfo_);
				final ActualOrFormalParameterList mappedParameterList = null == parameterList? null:
					(ActualOrFormalParameterList)parameterList.mapTemplateParameters(templateInstantiationInfo_);
				if (ignoreSignature) {
					retval = aDecl; break;
				} else if (null == mappedLoggedSignature && null == mappedParameterList) {
					retval = aDecl; break;
				} else if (null != mappedLoggedSignature && ((FormalParameterList)mappedLoggedSignature).alignsWith(mappedParameterList)) {
					retval = aDecl; break;
				}
			}
		} else {
			;
		}
		return retval;
	}
	public MethodDeclaration lookupMethodDeclarationWithConversion(final String methodSelector, final ActualOrFormalParameterList parameterList,
			boolean ignoreSignature) {
		MethodDeclaration retval = null;
		if (methodDeclarationDictionary_.containsKey(methodSelector)) {
			final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(methodSelector);
			for (final MethodDeclaration aDecl : oldEntry) {
				final FormalParameterList loggedSignature = aDecl.formalParameterList();
				final ActualOrFormalParameterList mappedLoggedSignature = null == loggedSignature? null:
					loggedSignature.mapTemplateParameters(templateInstantiationInfo_);
				final ActualOrFormalParameterList mappedParameterList = null == parameterList? null:
					(ActualOrFormalParameterList)parameterList.mapTemplateParameters(templateInstantiationInfo_);
				if (ignoreSignature) {
					retval = aDecl; break;
				} else if (null == mappedLoggedSignature && null == mappedParameterList) {
					retval = aDecl; break;
				} else if (null != mappedLoggedSignature && ((FormalParameterList)mappedLoggedSignature).alignsWith(mappedParameterList)) {
					// exact matches get preference
					retval = aDecl; break;
				} else if (null != mappedLoggedSignature && ((FormalParameterList)mappedLoggedSignature).alignsWithUsingConversion(mappedParameterList)) {
					// no exact match; try with conversion
					retval = aDecl; break;
				}
			}
		}
		return retval;
	}
	public MethodDeclaration lookupMethodDeclarationIgnoringParameter(final String methodSelector, final ActualOrFormalParameterList parameterList,
			final String paramToIgnore) {
		MethodDeclaration retval = null;
		if (methodDeclarationDictionary_.containsKey(methodSelector)) {
			final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(methodSelector);
			for (MethodDeclaration aDecl : oldEntry) {
				final FormalParameterList loggedSignature = aDecl.formalParameterList();
				final ActualOrFormalParameterList mappedLoggedSignature = null == loggedSignature? null:
					loggedSignature.mapTemplateParameters(templateInstantiationInfo_);
				final ActualOrFormalParameterList mappedParameterList = null == parameterList? null:
					(ActualOrFormalParameterList)parameterList.mapTemplateParameters(templateInstantiationInfo_);
				if (null == mappedLoggedSignature && null == mappedParameterList) {
					retval = aDecl; break;
				} else if (null != mappedLoggedSignature && FormalParameterList.alignsWithParameterListIgnoringParamNamed(mappedLoggedSignature, mappedParameterList, paramToIgnore, false)) {
					retval = aDecl; break;
				}
			}
		}
		return retval;
	}
	
	public MethodDeclaration lookupMethodDeclarationIgnoringRoleStuff(final String methodSelector, final ActualOrFormalParameterList parameterList) {
		MethodDeclaration retval = null;
		if (methodDeclarationDictionary_.containsKey(methodSelector)) {
			final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(methodSelector);
			for (MethodDeclaration aDecl : oldEntry) {
				final FormalParameterList loggedSignature = aDecl.formalParameterList();
				final ActualOrFormalParameterList mappedLoggedSignature = null == loggedSignature? null:
					loggedSignature.mapTemplateParameters(templateInstantiationInfo_);
				final ActualOrFormalParameterList mappedParameterList = null == parameterList? null:
					(ActualOrFormalParameterList)parameterList.mapTemplateParameters(templateInstantiationInfo_);
				if (null == mappedLoggedSignature && null == mappedParameterList) {
					retval = aDecl; break;
				} else if (null != mappedLoggedSignature && FormalParameterList.alignsWithParameterListIgnoringRoleStuff(mappedLoggedSignature, mappedParameterList)) {
					retval = aDecl; break;
				}
			}
		}
		return retval;
	}
	
	public MethodDeclaration lookupMethodDeclarationRecursiveWithLineNumber(final String methodSelector, final int lineNumber) {
		MethodDeclaration retval = this.lookupMethodDeclarationWithLineNumber(methodSelector, lineNumber);
		if (null == retval) {
			// If this is a class, see if the base class has it
			if (associatedDeclaration_ instanceof ClassDeclaration) {
				final ClassDeclaration thisClass = (ClassDeclaration)associatedDeclaration_;
				final ClassDeclaration baseClass = thisClass.baseClassDeclaration();
				final StaticScope baseClassScope = baseClass.enclosedScope();
				retval = baseClassScope.lookupMethodDeclarationRecursiveWithLineNumber(methodSelector, lineNumber);
			}
		}
		if (null == retval) {
			if (null != parentScope_) {
				retval = parentScope_.lookupMethodDeclarationRecursiveWithLineNumber(methodSelector, lineNumber);
			}
		}
		return retval;
	}
	public MethodDeclaration lookupMethodDeclarationWithLineNumber(final String methodSelector, final int lineNumber) {
		MethodDeclaration retval = null;
		if (methodDeclarationDictionary_.containsKey(methodSelector)) {
			final ArrayList<MethodDeclaration> oldEntry = methodDeclarationDictionary_.get(methodSelector);
			for (MethodDeclaration aDecl : oldEntry) {
				if (aDecl.lineNumber() == lineNumber) {
					retval = aDecl;
					break;
				}
			}
		}
		return retval;
	}
	
	// Scope management
	public void setDeclaration(final Declaration associatedDeclaration) {
		associatedDeclaration_ = associatedDeclaration;
	}
	public Declaration associatedDeclaration() {
		return associatedDeclaration_;
	}
	public void addSubScope(final StaticScope child) {
		subScopes_.add(child);
	}
	public StaticScope parentScope() {
		return parentScope_;
	}
	public void setParentScope(final StaticScope scope) {
		parentScope_ = scope;
	}
	
	public List<ObjectDeclaration> objectDeclarations() {
		final List<ObjectDeclaration> retval = new ArrayList<ObjectDeclaration>();
		for (Map.Entry<String, ObjectDeclaration> objectDecl : objectDeclarationDictionary_.entrySet()) {
			retval.add(objectDecl.getValue());
		}
		return retval;
	}
	public List<ClassDeclaration> classDeclarations() {
		final List<ClassDeclaration> retval = new ArrayList<ClassDeclaration>();
		for (Map.Entry<String, ClassDeclaration> classDecl : classDeclarationDictionary_.entrySet()) {
			retval.add(classDecl.getValue());
		}
		return retval;
	}
	public List<ContextDeclaration> contextDeclarations() {
		final List<ContextDeclaration> retval = new ArrayList<ContextDeclaration>();
		for (Map.Entry<String, ContextDeclaration> contextDecl : contextDeclarationDictionary_.entrySet()) {
			retval.add(contextDecl.getValue());
		}
		return retval;
	}
	public List<MethodDeclaration> methodDeclarations() {
		final List<MethodDeclaration> retval = new ArrayList<MethodDeclaration>();
		for (Map.Entry<String, ArrayList<MethodDeclaration>> iter : methodDeclarationDictionary_.entrySet()) {
			for (MethodDeclaration mDecl : iter.getValue()) {
				retval.add(mDecl);
			}
		}
		return retval;
	}
	public List<StagePropDeclaration> stagePropDeclarations() {
		final List<StagePropDeclaration> retval = new ArrayList<StagePropDeclaration>();
		for (Map.Entry<String, RoleDeclaration> stagePropDecl : roleAndStagePropDeclarationDictionary_.entrySet()) {
			final Declaration d = stagePropDecl.getValue();
			if (d instanceof StagePropDeclaration) {
				retval.add((StagePropDeclaration)d);
			}
		}
		return retval;
	}
	public List<RoleDeclaration> roleDeclarations() {
		final List<RoleDeclaration> retval = new ArrayList<RoleDeclaration>();
		for (Map.Entry<String, RoleDeclaration> roleDecl : roleAndStagePropDeclarationDictionary_.entrySet()) {
			retval.add(roleDecl.getValue());
		}
		return retval;
	}
	public String pathName() {
		if (null == associatedDeclaration_) {
			assert null != associatedDeclaration_;
		}
		if (null == associatedDeclaration_.type()) {
			assert null != associatedDeclaration_.type();
		}
		return associatedDeclaration_.type().pathName();
	}
	
	public static class StaticRoleScope extends StaticScope {
		public StaticRoleScope(final StaticScope parentScope) {
			super(parentScope);
			requiredMethodDeclarationDictionary_ = new LinkedHashMap<String,ArrayList<MethodDeclaration>>();
		}
		public void declareMethod(final MethodDeclaration decl) {
			boolean dup = false;
			final String methodName = decl.name();
			
			if (requiredMethodDeclarationDictionary_.containsKey(methodName)) {
				final ArrayList<MethodDeclaration> oldEntry = requiredMethodDeclarationDictionary_.get(methodName);
				for (final MethodDeclaration aDecl : oldEntry) {
					final FormalParameterList loggedSignature = aDecl.formalParameterList();
					if (null == loggedSignature && null == decl.formalParameterList()) {
						dup = true;
						break;
					} else if (null != loggedSignature && loggedSignature.alignsWith(decl.formalParameterList())) {
						dup = true;
						break;
					}
				}
			}
			
			if (dup) {
				ErrorLogger.error(ErrorType.Fatal, decl.lineNumber(), "Declaration of `", methodName, "' in ",
					name(), " would create multiple methods of the same name in the same object.", "");
			} else {
				super.declareMethod(decl);
			}
		}
		public void declareRequiredMethod(final MethodDeclaration decl) {
			final String methodName = decl.name();
			
			final MethodDeclaration lookupExistingEntry = this.lookupMethodDeclaration(methodName,
					decl.formalParameterList(), true);
			if (null != lookupExistingEntry) {
				ErrorLogger.error(ErrorType.Fatal, decl.lineNumber(), "Declaration of `", methodName, "' in ",
						name(), " would create multiple methods of the same name in the same object.", "");
			} else if (requiredMethodDeclarationDictionary_.containsKey(methodName)) {
				final ArrayList<MethodDeclaration> oldEntry = requiredMethodDeclarationDictionary_.get(methodName);
				for (final MethodDeclaration aDecl : oldEntry) {
					final FormalParameterList loggedSignature = aDecl.formalParameterList();
					if (null == loggedSignature && null == decl.formalParameterList()) {
						ErrorLogger.error(ErrorType.Fatal, "Multiple declarations of `required' method `", methodName, "' in " + name(), "'.");
						break;
					} else if (null != loggedSignature && loggedSignature.alignsWith(decl.formalParameterList())) {
						ErrorLogger.error(ErrorType.Fatal, "Multiple declarations of `required' method `", methodName, "' in " + name(), "'.");
						break;
					}
				}
				oldEntry.add(decl);
			} else {
				final ArrayList<MethodDeclaration> newEntry = new ArrayList<MethodDeclaration>();
				newEntry.add(decl);
				requiredMethodDeclarationDictionary_.put(methodName, newEntry);
			}
			if (null != parentScope()) parentScope().checkMethodShadowing(decl);
		}
		public List<MethodDeclaration> methodDeclarations() {
			final List<MethodDeclaration> retval = super.methodDeclarations();

			for (Map.Entry<String, ArrayList<MethodDeclaration>> iter : requiredMethodDeclarationDictionary_.entrySet()) {
				for (MethodDeclaration mDecl : iter.getValue()) {
					retval.add(mDecl);
				}
			}
			return retval;
		}
		public MethodDeclaration lookupMethodDeclaration(final String methodSelector, final ActualOrFormalParameterList parameterList,
				final boolean ignoreSignature) {
			MethodDeclaration retval = super.lookupMethodDeclaration(methodSelector, parameterList,
					 ignoreSignature);
			if (null == retval) {
				if (requiredMethodDeclarationDictionary_.containsKey(methodSelector)) {
					final ArrayList<MethodDeclaration> oldEntry = requiredMethodDeclarationDictionary_.get(methodSelector);
					for (final MethodDeclaration aDecl : oldEntry) {
						final FormalParameterList loggedSignature = aDecl.formalParameterList();
						if (ignoreSignature) {
							retval = aDecl; break;
						} else if (null == loggedSignature && null == parameterList) {
							retval = aDecl; break;
						} else if (null != loggedSignature && loggedSignature.alignsWith(parameterList)) {
							retval = aDecl; break;
						}
					}
				} else {
					;
				}
			} else {
				;
			}
			return retval;
		}
		public MethodDeclaration lookupMethodDeclarationIgnoringParameter(final String methodSelector, final ActualOrFormalParameterList parameterList,
				String paramToIgnore) {
			MethodDeclaration retval = super.lookupMethodDeclarationIgnoringParameter(methodSelector, parameterList,
					 paramToIgnore);
			if (null == retval) {
				if (requiredMethodDeclarationDictionary_.containsKey(methodSelector)) {
					final ArrayList<MethodDeclaration> oldEntry = requiredMethodDeclarationDictionary_.get(methodSelector);
					for (MethodDeclaration aDecl : oldEntry) {
						final FormalParameterList loggedSignature = aDecl.formalParameterList();
						if (null == loggedSignature && null == parameterList) {
							retval = aDecl; break;
						} else if (null != loggedSignature && FormalParameterList.alignsWithParameterListIgnoringParamNamed(loggedSignature, parameterList, paramToIgnore, false)) {
							retval = aDecl; break;
						}
					}
				}
			}
			return retval;
		}
		
		private Map<String,ArrayList<MethodDeclaration>> requiredMethodDeclarationDictionary_;
	}
	
	public final TemplateInstantiationInfo templateInstantiationInfo() {
		return templateInstantiationInfo_;
	}
	
	private static boolean aHasBAsBaseClass(ClassDeclaration a, ClassDeclaration b) {
		final boolean retval = b.type().isBaseClassOf(a.type());
		return retval;
	}
	public boolean canAccessDeclarationWithAccessibility(final Declaration decl, final AccessQualifier accessQualifier, final int lineNumber) {
		// Can this scope access the given declaration?
		boolean retval = false;
		StaticScope myEnclosingMethodScope = null, declsEnclosingMethodScope = null;
		
		@SuppressWarnings("unused")
		boolean declIsInBaseClass = false;	// in case we implement Protected stuff
		
		final Type myEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(this);
		Type declarationEnclosingMegaType = null;
		if (decl instanceof MethodDeclaration) {
			StaticScope scope = ((MethodDeclaration)decl).enclosingScope();
			Declaration associatedScopeDeclaration = scope.associatedDeclaration();
			while (associatedScopeDeclaration instanceof ClassDeclaration == false &&
					associatedScopeDeclaration instanceof RoleDeclaration == false &&
					associatedScopeDeclaration instanceof ContextDeclaration == false &&
					scope != StaticScope.globalScope()) {
				scope = scope.parentScope();
				associatedScopeDeclaration = scope.associatedDeclaration();
			}
 			declarationEnclosingMegaType = associatedScopeDeclaration.type();
		} else if (decl instanceof ObjectDeclaration) {
			myEnclosingMethodScope = Expression.nearestEnclosingMethodScopeAround(this);
			declsEnclosingMethodScope = Expression.nearestEnclosingMethodScopeAround(((ObjectDeclaration) decl).enclosingScope());
		} else {
			declarationEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(decl.type().enclosedScope());
		}
		if (null != myEnclosingMethodScope && null != declsEnclosingMethodScope &&
				myEnclosingMethodScope.associatedDeclaration() == declsEnclosingMethodScope.associatedDeclaration()) {
			retval = true;
		} else if (accessQualifier == AccessQualifier.PublicAccess) {
			retval = true;
		} else {
			final Declaration declForThisScope = this.associatedDeclaration();
			if (declForThisScope instanceof ClassDeclaration) {
				final ClassDeclaration meAsClass = (ClassDeclaration)declForThisScope;
				if (declarationEnclosingMegaType instanceof ClassType) {
					if (decl.type().pathName().equals(meAsClass.type().pathName())) {
						retval = true;
					} else {
						declIsInBaseClass = aHasBAsBaseClass(meAsClass, (ClassDeclaration)declarationEnclosingMegaType.enclosedScope().associatedDeclaration());
					}
				}
			} else if (myEnclosingMegaType instanceof TemplateType) {
				retval = true;	// lie, but let it go for now
			} else if (declForThisScope instanceof MethodDeclaration) {
				if (decl instanceof ObjectDeclaration) {
					// See if it's a method accessing instance data
					final StaticScope declaringScope = ((ObjectDeclaration) decl).enclosingScope();
					final StaticScope myMegaTypeScope = myEnclosingMegaType.enclosedScope();
					if (declaringScope == myMegaTypeScope) {
						retval = true;
					}
				} else if (decl instanceof MethodDeclaration) {
					// See if it's a method calling another instance method
					final StaticScope declaringScope = ((MethodDeclaration) decl).enclosingScope();
					final StaticScope myMegaTypeScope = myEnclosingMegaType.enclosedScope();
					if (declaringScope == myMegaTypeScope) {
						retval = true;
					}
				}
			} else if (null != declarationEnclosingMegaType &&
					   null != myEnclosingMegaType &&
					   declarationEnclosingMegaType.pathName().equals(myEnclosingMegaType.pathName())) {
				retval = true;
			}
		}
		return retval;
	}
	
	public static ArrayList<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	public void printClassMethodDecls() {
		for (final Map.Entry<String,ClassDeclaration> classIter : classDeclarationDictionary_.entrySet()) {
			final String className = classIter.getKey();
			System.err.format("Class   %s\n", className);
			final ClassDeclaration classDecl = classIter.getValue();
			final StaticScope classScope = classDecl.enclosedScope();
			final List<MethodDeclaration> methodDecls = classScope.methodDeclarations();
			printMethodDecls(methodDecls);
		}
	}
	
	public void printContextMethodDecls() {
		for (final Map.Entry<String,ContextDeclaration> contextIter : contextDeclarationDictionary_.entrySet()) {
			final String className = contextIter.getKey();
			System.err.format("Context %s\n", className);
			final ContextDeclaration classDecl = contextIter.getValue();
			final StaticScope classScope = classDecl.enclosedScope();
			final List<MethodDeclaration> methodDecls = classScope.methodDeclarations();
			printMethodDecls(methodDecls);
		}
	}

	private void printMethodDecls(List<MethodDeclaration> methodDecls) {
		for (final MethodDeclaration methodDecl : methodDecls) {
			final Type returnType = methodDecl.returnType();
			String staticModifier;
			if (methodDecl.signature().isStatic()) {
				staticModifier = "static ";
			} else {
				staticModifier = "";
			}
			if (null != returnType) {
				System.err.format("\t%s%s ", staticModifier, returnType.name());
			} else {
				System.err.format("\t%s%s ", staticModifier, "NULL");
			}
			System.err.format("%s", methodDecl.name());
			final FormalParameterList formalParameterList = methodDecl.formalParameterList();
			System.err.format("%s", formalParameterList.getText());
			if (methodDecl.isConst()) {
				System.err.format(" const");
			}
			System.err.format("\n");
		}
	}
	
	private StaticScope parentScope_;
	private SimpleList subScopes_;
	protected Declaration associatedDeclaration_;
	private final Map<String,ObjectDeclaration> objectDeclarationDictionary_;
	private final Map<String,ObjectDeclaration> staticObjectDeclarationDictionary_;
	private final Map<String,Type> typeDeclarationDictionary_;
	private final Map<String,ArrayList<MethodDeclaration>> methodDeclarationDictionary_;
	private final Map<String,ContextDeclaration> contextDeclarationDictionary_;
	private final Map<String,ClassDeclaration> classDeclarationDictionary_;
	private final Map<String,InterfaceDeclaration> interfaceDeclarationDictionary_;
	private final Map<String,TemplateDeclaration> templateDeclarationDictionary_;
	private Map<String,RoleDeclaration> roleAndStagePropDeclarationDictionary_;
	private boolean hasDeclarationsThatAreLostBetweenPasses_;
	private TemplateInstantiationInfo templateInstantiationInfo_;
	private static ArrayList<TypeDeclaration> typeDeclarationList_;
	
	private static StaticScope globalScope_ = new StaticScope(null);
}
