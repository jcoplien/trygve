package run_time;

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

import java.util.HashMap;
import java.util.Map;

import code_generation.InterpretiveCodeGenerator;
import semantic_analysis.StaticScope;
import declarations.ActualOrFormalParameterList;
import declarations.Type;
import declarations.Declaration.ClassDeclaration;
import declarations.Type.ClassType;
import declarations.TypeDeclaration;
import expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;


public class RTClass extends RTClassAndContextCommon implements RTType {
	public RTClass(TypeDeclaration decl) {
		super(decl);
		assert decl instanceof ClassDeclaration;
		
		stringToContextDeclMap_ = new HashMap<String, RTContext>();
		stringToClassDeclMap_ = new HashMap<String, RTClass>();
		nameToObjectDeclMap_ = new HashMap<String, RTObject>();
		RunTimeEnvironment.runTimeEnvironment_.addToListOfAllClasses(this);
		
		final Type rawClassType = decl.type();
		if (null != rawClassType) {
			assert rawClassType instanceof ClassType;
			final ClassType classType = (ClassType)rawClassType;
			this.doBaseClassProcessing(classType);
		}
		
		super.populateNameToTypeObjectMap();
		super.populateNameToStaticObjectMap();
	}
	private void doBaseClassProcessing(ClassType classType) {
		final ClassType baseClassType = classType.baseClass();
		if (null != baseClassType) {
			// Add base class stuff, too.
			final StaticScope baseClassEnclosedScope = baseClassType.enclosedScope();
			final RTType rawBaseClass = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(baseClassEnclosedScope);
			assert rawBaseClass instanceof RTClass;
			final RTClass baseClass = (RTClass)rawBaseClass;
			baseClass.doBaseClassProcessing(baseClassType);	// recur up the inheritance hierarchy
			baseClass.populateNameToTypeObjectMap(nameToTypeObjectMap_);
			baseClass.populateNameToStaticObjectMap(nameToStaticObjectMap_, nameToStaticObjectTypeMap_);
		}
	}
	@Override public void addClass(String typeName, RTClass classDecl) {
		stringToClassDeclMap_.put(typeName, classDecl);
	}
	@Override public void addContext(String typeName, RTContext contextDecl) {
		stringToContextDeclMap_.put(typeName, contextDecl);
	}
	public RTType typeNamed(String typeName) {
		RTType retval = this.stringToClassDeclMap_.get(typeName);
		if (null == retval) {
			retval = this.stringToContextDeclMap_.get(typeName);
		}
		return retval;
	}
	@Override public void setObject(String objectName, RTObject object) {
		if (nameToObjectDeclMap_.containsKey(objectName)) {
			final RTObject oldOne = nameToObjectDeclMap_.get(objectName);
			nameToObjectDeclMap_.put(objectName, object);
			oldOne.decrementReferenceCount();
		} else if (nameToStaticObjectMap_.containsKey(objectName)) {
			final RTObject oldOne = nameToStaticObjectMap_.get(objectName);
			nameToStaticObjectMap_.put(objectName, object);
			oldOne.decrementReferenceCount();
		}
		object.incrementReferenceCount();
	}
	@Override public RTObject getObject(String objectName) {
		RTObject retval = null;
		if (nameToObjectDeclMap_.containsKey(objectName)) {
			retval = nameToObjectDeclMap_.get(objectName);
		} else if (nameToStaticObjectMap_.containsKey(objectName)) {
			retval = nameToStaticObjectMap_.get(objectName);
		}
		return retval;
	}
	
	// All of these are fishy as class members...  They're here just to
	// satisfy the pure virtuals in the base class..
	@Override public void addObjectDeclaration(String objectName, RTType objectType) {
		assert false;
	}
	@Override public void addStageProp(String stagePropName, RTStageProp stagePropType) {
		assert false;
	}
	@Override public void addRole(String roleName, RTRole roleType) {
		assert false;
	}
	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		assert false;
		return null;
	}
	
	// ---------------------
	
	public RTObject performUnaryOpOnStaticObjectNamed(String objectName, String operator, PreOrPost preOrPost) {
		RTObject retval = null;
		if (nameToStaticObjectMap_.containsKey(objectName)) {
			retval = nameToStaticObjectMap_.get(objectName);
		} else {
			assert false;
		}
		
		switch (preOrPost) {
		case Pre:
			if (operator.equals("++")) {
				retval = retval.preIncrement();
			} else if (operator.equals("--")) {
				retval = retval.preDecrement();
			} else {
				assert false;
			}
			break;
		case Post:
			if (operator.equals("++")) {
				retval = retval.postIncrement();
			} else if (operator.equals("--")) {
				retval = retval.postDecrement();
			} else {
				assert false;
			}
			break;
		}
		
		return retval;
	}

	
	public static class RTIntegerClass extends RTClass {
		public RTIntegerClass(TypeDeclaration associatedType) {
			super(associatedType);
		}
		@Override public RTType typeNamed(String typeName) { return null; }
		@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("int"); }
	}
	public static class RTBigIntegerClass extends RTClass {
		public RTBigIntegerClass(TypeDeclaration associatedType) {
			super(associatedType);
		}
		@Override public RTType typeNamed(String typeName) { return null; }
		@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("Integer"); }
	}
	public static class RTDoubleClass extends RTClass {
		public RTDoubleClass(TypeDeclaration associatedType) {
			super(associatedType);
		}
		@Override public RTType typeNamed(String typeName) { return null; }
		@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("double"); }
	}
	public static class RTStringClass extends RTClass {
		public RTStringClass(TypeDeclaration associatedType) {
			super(associatedType);
		}
		@Override public RTType typeNamed(String typeName) { return null; }
		@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("String"); }
	}
	public static class RTBooleanClass extends RTClass {
		public RTBooleanClass(TypeDeclaration associatedType) {
			super(associatedType);
		}
		@Override public RTType typeNamed(String typeName) { return null; }
		@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("boolean"); }
	}
	
	private Map<String, RTContext> stringToContextDeclMap_;
	private Map<String, RTClass> stringToClassDeclMap_;
	private Map<String, RTObject> nameToObjectDeclMap_;
}
