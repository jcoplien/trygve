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
import java.util.List;
import java.util.Map;

import code_generation.InterpretiveCodeGenerator;
import semantic_analysis.StaticScope;
import declarations.Declaration.ObjectDeclaration;
import declarations.Type.ClassType;
import declarations.ActualOrFormalParameterList;
import declarations.FormalParameterList;
import declarations.TemplateInstantiationInfo;
import declarations.Type;
import declarations.TypeDeclaration;
import run_time.RTObjectCommon.RTNullObject;

public abstract class RTClassAndContextCommon implements RTType {
	public RTClassAndContextCommon(TypeDeclaration typeDeclaration) {
		super();
		if (null == typeDeclaration) {
			assert null != typeDeclaration;
		}
		stringToMethodDeclMap_ = new HashMap<String, Map<FormalParameterList, RTMethod>>();
		nameToStaticObjectMap_ = new HashMap<String, RTObject>();
		nameToStaticObjectTypeMap_ = new HashMap<String, Type>();
		nameToRoleDeclMap_ = new HashMap<String, RTRole>();
		nameToRoleBindingMap_ = new HashMap<String, RTObject>();;
		typeDeclaration_ = typeDeclaration;
		
		// Get TemplateInstantiationInfo, if a any
		final Type classType = typeDeclaration.type();
		if (classType instanceof ClassType) {
			templateInstantiationInfo_ = null == classType? null: classType.enclosedScope().templateInstantiationInfo();
		} else {
			templateInstantiationInfo_ = null;
		}
	}
	@Override public RTObject defaultObject() {
		final RTObject retval = new RTObjectCommon(this);
		return retval;
	}
	protected void populateNameToTypeObjectMap() {
		assert null != typeDeclaration_;
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		List<ObjectDeclaration> objectDeclarations = enclosedScope.objectDeclarations();
		for (final ObjectDeclaration objectDecl : objectDeclarations) {
			final String name = objectDecl.name();
			RTType rTObjectDecl = null;
			nameToTypeObjectMap_.put(name, rTObjectDecl);
		}
	}
	protected void populateNameToStaticObjectMap() {
		assert null != typeDeclaration_;
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		final Map<String, ObjectDeclaration> staticDeclarations = enclosedScope.staticObjectDeclarations();
		
		final RTObject nullObject = new RTNullObject();

		for (final Map.Entry<String,ObjectDeclaration> iter : staticDeclarations.entrySet()) {
			final ObjectDeclaration objectDecl = iter.getValue();
			final String name = objectDecl.name();
			nameToStaticObjectMap_.put(name, nullObject);
			nameToStaticObjectTypeMap_.put(name, objectDecl.type());
		}
	}
	public void metaInit() {
		// This method sets up static members
		for (Map.Entry<String, RTObject> iter : nameToStaticObjectMap_.entrySet()) {
			// Get a default value. TODO: Check for static initialiezer and use that instead
			final Type typeOfStatic = nameToStaticObjectTypeMap_.get(iter.getKey());
			final TypeDeclaration typeDeclaration = (TypeDeclaration) typeOfStatic.enclosedScope().associatedDeclaration();
			assert typeDeclaration instanceof TypeDeclaration;
			final RTType rTTypeOfStatic = InterpretiveCodeGenerator.TypeDeclarationToRTTypeDeclaration(typeDeclaration);
			final RTObject initializingObject = new RTObjectCommon(rTTypeOfStatic);
			
			// Bind the static identifier to a value
			nameToStaticObjectMap_.put(iter.getKey(), initializingObject);
		}
	}
	public Map<String, RTType> objectDeclarations() {
		return nameToTypeObjectMap_;
	}
	public Map<String, RTObject> staticObjects() {
		return nameToStaticObjectMap_;
	}
	public RTObject getStaticObject(String objectName) {
		RTObject retval = null;
		if (nameToStaticObjectMap_.containsKey(objectName)) {
			retval = nameToStaticObjectMap_.get(objectName);
		}
		return retval;
	}
	public RTRole getRole(String name) {
		return nameToRoleDeclMap_.get(name);
	}
	@Override public void addMethod(String methodName, RTMethod methodDecl) {
		if (stringToMethodDeclMap_.containsKey(methodName)) {
			final Map<FormalParameterList, RTMethod> possibilities = stringToMethodDeclMap_.get(methodName);
			for (Map.Entry<FormalParameterList, RTMethod> aPair : possibilities.entrySet()) {
				final FormalParameterList loggedSignature = aPair.getKey();
				if (methodDecl.formalParameters().alignsWith(loggedSignature)) {
					assert false;	 // duplicate signature
				}
			}
			possibilities.put(methodDecl.formalParameters(), methodDecl);
		} else {
			final Map<FormalParameterList, RTMethod> newVector = new HashMap<FormalParameterList, RTMethod>();
			newVector.put(methodDecl.formalParameters(), methodDecl);
			stringToMethodDeclMap_.put(methodName, newVector);
		}
	}
	@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) {
		return this.lookupMethodIgnoringParameterInSignature(methodName, pl, null);
	}
	@Override public RTMethod lookupMethodIgnoringParameterInSignature(String methodName, ActualOrFormalParameterList pl, String ignoreName) {
		RTMethod retval = null;
		if (stringToMethodDeclMap_.containsKey(methodName)) {
			final Map<FormalParameterList, RTMethod> possibilities = stringToMethodDeclMap_.get(methodName);
			for (final Map.Entry<FormalParameterList, RTMethod> aPair : possibilities.entrySet()) {
				final FormalParameterList loggedSignature = aPair.getKey();
				
				ActualOrFormalParameterList mappedLoggedSignature = loggedSignature;
				if (null != templateInstantiationInfo_) {
					mappedLoggedSignature = loggedSignature.mapTemplateParameters(templateInstantiationInfo_);
				}
				
				if (FormalParameterList.alignsWithParameterListIgnoringParam(mappedLoggedSignature, pl, ignoreName)) {
					retval = aPair.getValue();
					break;
				}
			}
		} else {
			retval = null;
		}
		return retval;
	}
	public String name() {
		return null == typeDeclaration_? "*null*": typeDeclaration_.name();
	}
	public TypeDeclaration typeDeclaration() {
		assert false;	// ever get here?
		return typeDeclaration_;
	}
	
	public void incrementReferenceCount() {
		referenceCount_++;
	}
	public void decrementReferenceCount() {
		--referenceCount_;
	}
	public long referenceCount() {
		return referenceCount_;
	}
	
	
	private long referenceCount_;
	
	protected Map<String, RTType> nameToTypeObjectMap_;
	protected Map<String, RTRole> nameToRoleDeclMap_;
	protected Map<String, RTObject> nameToRoleBindingMap_;
	protected TypeDeclaration typeDeclaration_;
	private Map<String, Map<FormalParameterList, RTMethod>> stringToMethodDeclMap_;
	protected Map<String, RTObject> nameToStaticObjectMap_;
	private Map<String, Type> nameToStaticObjectTypeMap_;
	final TemplateInstantiationInfo templateInstantiationInfo_;
}
