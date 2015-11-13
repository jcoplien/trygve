package info.fulloo.trygve.run_time;

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

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;

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
		nameToRoleBindingMap_ = new HashMap<String, RTObject>();
		nameToTypeObjectMap_ = new HashMap<String, RTType>();
		typeDeclaration_ = typeDeclaration;
		
		// Get TemplateInstantiationInfo, if any
		final Type rawClassType = typeDeclaration.type();
		if (rawClassType instanceof ClassType) {
			final ClassType classType = (ClassType)rawClassType;
			templateInstantiationInfo_ = null == classType? null: classType.enclosedScope().templateInstantiationInfo();
		} else {
			templateInstantiationInfo_ = null;
		}
	}
	@Override public RTObject defaultObject() {
		final RTObject retval = new RTObjectCommon(this);
		return retval;
	}
	protected void populateNameToTypeObjectMap(Map<String, RTType> nameToTypeObjectMap) {
		assert null != typeDeclaration_;
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		List<ObjectDeclaration> objectDeclarations = enclosedScope.objectDeclarations();
		for (final ObjectDeclaration objectDecl : objectDeclarations) {
			final String name = objectDecl.name();
			RTType rTObjectDecl = null;
			nameToTypeObjectMap.put(name, rTObjectDecl);
		}
	}
	protected void populateNameToTypeObjectMap() {
		this.populateNameToTypeObjectMap(nameToTypeObjectMap_);
	}
	protected void populateNameToStaticObjectMap(Map<String, RTObject> nameToStaticObjectMap,
												 Map<String, Type> nameToStaticObjectTypeMap) {
		assert null != typeDeclaration_;
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		final Map<String, ObjectDeclaration> staticDeclarations = enclosedScope.staticObjectDeclarations();
		
		final RTObject nullObject = new RTNullObject();

		for (final Map.Entry<String,ObjectDeclaration> iter : staticDeclarations.entrySet()) {
			final ObjectDeclaration objectDecl = iter.getValue();
			final String name = objectDecl.name();
			nameToStaticObjectMap.put(name, nullObject);
			nameToStaticObjectTypeMap.put(name, objectDecl.type());
		}
	}
	protected void populateNameToStaticObjectMap() {
		this.populateNameToStaticObjectMap(nameToStaticObjectMap_, nameToStaticObjectTypeMap_);
	}
	public void metaInit() {
		// This method sets up static members
		for (Map.Entry<String, RTObject> iter : nameToStaticObjectMap_.entrySet()) {
			// Get a default value. TODO: Check for static initializer and use that instead
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
	public RTObject getStaticObject(final String objectName) {
		RTObject retval = null;
		if (nameToStaticObjectMap_.containsKey(objectName)) {
			retval = nameToStaticObjectMap_.get(objectName);
		}
		return retval;
	}
	public RTRole getRole(final String name) {
		return nameToRoleDeclMap_.get(name);
	}
	@Override public void addMethod(final String methodName, final RTMethod methodDecl) {
		if (stringToMethodDeclMap_.containsKey(methodName)) {
			final Map<FormalParameterList, RTMethod> possibilities = stringToMethodDeclMap_.get(methodName);
			for (final Map.Entry<FormalParameterList, RTMethod> aPair : possibilities.entrySet()) {
				final FormalParameterList loggedSignature = aPair.getKey();
				if (methodDecl.formalParameters().alignsWith(loggedSignature)) {
					final MethodDeclaration originalMethodDecl = methodDecl.methodDeclaration();
					final int lineNumber = originalMethodDecl == null? 0: originalMethodDecl.lineNumber();
					ErrorLogger.error(ErrorType.Fatal, lineNumber, "Multiple declarations of ",
							methodName + " in scope ", name(), ".");
				}
			}
			possibilities.put(methodDecl.formalParameters(), methodDecl);
		} else {
			final Map<FormalParameterList, RTMethod> newVector = new HashMap<FormalParameterList, RTMethod>();
			newVector.put(methodDecl.formalParameters(), methodDecl);
			stringToMethodDeclMap_.put(methodName, newVector);
		}
	}
	@Override public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl) {
		return this.lookupMethodIgnoringParameterInSignature(methodName, pl, null);
	}
	@Override public RTMethod lookupMethodIgnoringParameterInSignatureWithConversion(final String methodName, final ActualOrFormalParameterList suppliedParameters, final String ignoreName) {
		return this.lookupMethodIgnoringParameterInSignatureCommon(methodName, suppliedParameters, ignoreName, true);
	}
	@Override public RTMethod lookupMethodIgnoringParameterInSignature(final String methodName, final ActualOrFormalParameterList suppliedParameters, final String ignoreName) {
		return this.lookupMethodIgnoringParameterInSignatureCommon(methodName, suppliedParameters, ignoreName, false);
	}
	private RTMethod lookupMethodIgnoringParameterInSignatureCommon(final String methodName, final ActualOrFormalParameterList suppliedParameters, final String ignoreName, final boolean allowPromotion) {
		RTMethod retval = null;
		if (stringToMethodDeclMap_.containsKey(methodName)) {
			final Map<FormalParameterList, RTMethod> possibilities = stringToMethodDeclMap_.get(methodName);
			for (final Map.Entry<FormalParameterList, RTMethod> aPair : possibilities.entrySet()) {
				final FormalParameterList declaredMethodSignature = aPair.getKey();
				
				ActualOrFormalParameterList mappedDeclaredMethodSignature = declaredMethodSignature,
				                            mappedSuppliedParameters = suppliedParameters;
				if (null != templateInstantiationInfo_) {
					mappedDeclaredMethodSignature = declaredMethodSignature.mapTemplateParameters(templateInstantiationInfo_);
					mappedSuppliedParameters = suppliedParameters.mapTemplateParameters(templateInstantiationInfo_);
				}
				
				if (FormalParameterList.alignsWithParameterListIgnoringParam(mappedDeclaredMethodSignature, mappedSuppliedParameters, ignoreName, allowPromotion)) {
					retval = aPair.getValue();
					break;
				}
			}
		} else if (null != this.baseClassDeclaration()) {
			// We inherit base class methods. Recur.
			final RTType runTimeBaseClassType = InterpretiveCodeGenerator.TypeDeclarationToRTTypeDeclaration(this.baseClassDeclaration());
			assert (runTimeBaseClassType instanceof RTClass);
			retval = runTimeBaseClassType.lookupMethodIgnoringParameterInSignature(methodName, suppliedParameters, ignoreName);
		} else {
			retval = null;
		}
		return retval;
	}
	public ClassDeclaration baseClassDeclaration() {
		final ClassDeclaration classDeclaration = typeDeclaration_ instanceof ClassDeclaration? (ClassDeclaration)typeDeclaration_: null;
		final ClassDeclaration baseClassDeclaration = null == classDeclaration? null: classDeclaration.baseClassDeclaration();
		return baseClassDeclaration;
	}
	public final String name() {
		return null == typeDeclaration_? "*null*": typeDeclaration_.name();
	}
	public TypeDeclaration typeDeclaration() {
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
	public final TemplateInstantiationInfo templateInstantiationInfo() {
		return templateInstantiationInfo_;
	}
	
	
	private long referenceCount_;
	
	protected Map<String, RTType> nameToTypeObjectMap_;
	protected Map<String, RTRole> nameToRoleDeclMap_;
	protected Map<String, RTObject> nameToRoleBindingMap_;
	protected TypeDeclaration typeDeclaration_;
	private Map<String, Map<FormalParameterList, RTMethod>> stringToMethodDeclMap_;
	protected Map<String, RTObject> nameToStaticObjectMap_;
	protected Map<String, Type> nameToStaticObjectTypeMap_;
	private final TemplateInstantiationInfo templateInstantiationInfo_;
}
