package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.1
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
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
	public RTClassAndContextCommon(final TypeDeclaration typeDeclaration) {
		super();
		assert null != typeDeclaration;
		stringToMethodDeclMap_ = new LinkedHashMap<String, Map<FormalParameterList, RTMethod>>();
		nameToStaticObjectMap_ = new LinkedHashMap<String, RTObject>();
		nameToStaticObjectTypeMap_ = new LinkedHashMap<String, Type>();
		nameToRoleDeclMap_ = new LinkedHashMap<String, RTRole>();
		nameToStagePropDeclMap_ = new LinkedHashMap<String, RTStageProp>();
		nameToRoleBindingMap_ = new LinkedHashMap<String, RTObject>();
		nameToStagePropBindingMap_ = new LinkedHashMap<String, RTObject>();
		nameToTypeObjectMap_ = new LinkedHashMap<String, RTType>();
		typeDeclaration_ = typeDeclaration;
		
		// Get TemplateInstantiationInfo, if any
		final Type rawClassType = typeDeclaration.type();
		if (rawClassType instanceof ClassType) {
			final ClassType classType = (ClassType)rawClassType;
			templateInstantiationInfo_ = classType.enclosedScope().templateInstantiationInfo();
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
		for (final Map.Entry<String, RTObject> iter : nameToStaticObjectMap_.entrySet()) {
			// Get a default value. TODO: Check for static initializer and use that instead
			final Type typeOfStatic = nameToStaticObjectTypeMap_.get(iter.getKey());
			final Declaration rawDecl = typeOfStatic.enclosedScope().associatedDeclaration();
			assert rawDecl instanceof TypeDeclaration;
			final TypeDeclaration typeDeclaration = (TypeDeclaration)rawDecl;
			final RTType rTTypeOfStatic = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
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
		assert false;
		return nameToRoleDeclMap_.get(name);
	}
	public RTStageProp getStageProp(final String name) {
		return nameToStagePropDeclMap_.get(name);
	}
	@Override public void addMethod(final String methodName, final RTMethod methodDecl) {
		if (stringToMethodDeclMap_.containsKey(methodName)) {
			final Map<FormalParameterList, RTMethod> possibilities = stringToMethodDeclMap_.get(methodName);
			for (final Map.Entry<FormalParameterList, RTMethod> aPair : possibilities.entrySet()) {
				final FormalParameterList loggedSignature = aPair.getKey();
				if (methodDecl.formalParameters().alignsWith(loggedSignature)) {
					String toPrint = methodName;
					final MethodDeclaration originalMethodDecl = methodDecl.methodDeclaration();
					final int lineNumber = originalMethodDecl == null? 0: originalMethodDecl.lineNumber();
					if (null != originalMethodDecl) {
						toPrint = originalMethodDecl.signature().getText();
					}
					ErrorLogger.error(ErrorType.Fatal, lineNumber, "Multiple declarations of `",
							toPrint + "' in scope `", name(), "'.");
				}
			}
			possibilities.put(methodDecl.formalParameters(), methodDecl);
		} else {
			final Map<FormalParameterList, RTMethod> newVector = new LinkedHashMap<FormalParameterList, RTMethod>();
			newVector.put(methodDecl.formalParameters(), methodDecl);
			stringToMethodDeclMap_.put(methodName, newVector);
		}
	}
	@Override public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl) {
		return this.lookupMethodIgnoringParameterInSignatureNamed(methodName, pl, null);
	}
	@Override public RTMethod lookupMethodIgnoringParameterInSignatureWithConversionNamed(final String methodName, final ActualOrFormalParameterList suppliedParameters, final String ignoreName) {
		return this.lookupMethodIgnoringParameterInSignatureCommon(methodName, suppliedParameters, ignoreName, true, -1);
	}
	@Override public RTMethod lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(final String methodName, final ActualOrFormalParameterList suppliedParameters, final int ignoreName) {
		return this.lookupMethodIgnoringParameterInSignatureCommon(methodName, suppliedParameters, null, true, ignoreName);
	}
	@Override public RTMethod lookupMethodIgnoringParameterAtPosition(final String methodName, final ActualOrFormalParameterList suppliedParameters, final int ignoredParameterPosition) {
		return this.lookupMethodIgnoringParameterInSignatureCommon(methodName, suppliedParameters, null, true, ignoredParameterPosition);
	}
	@Override public RTMethod lookupMethodIgnoringParameterInSignatureNamed(final String methodName, final ActualOrFormalParameterList suppliedParameters, final String ignoreName) {
		return this.lookupMethodIgnoringParameterInSignatureCommon(methodName, suppliedParameters, ignoreName, false, -1);
	}
	private RTMethod lookupMethodIgnoringParameterInSignatureCommon(final String methodName, final ActualOrFormalParameterList suppliedParameters,
			final String ignoreName, final boolean allowPromotion, final int ignoredParameterPosition) {
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
				
				if (-1 == ignoredParameterPosition) {
					if (FormalParameterList.alignsWithParameterListIgnoringParamNamed(mappedDeclaredMethodSignature, mappedSuppliedParameters, ignoreName, allowPromotion)) {
						retval = aPair.getValue();
						break;
					}
				} else {
					if (FormalParameterList.alignsWithParameterListIgnoringParamAtPosition(mappedDeclaredMethodSignature, mappedSuppliedParameters, ignoredParameterPosition, allowPromotion)) {
						retval = aPair.getValue();
						break;
					}
				}
			}
		} else if (null != this.baseClassDeclaration()) {
			// We inherit base class methods. Recur.
			final RTType runTimeBaseClassType = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(this.baseClassDeclaration());
			assert (runTimeBaseClassType instanceof RTClass);
			retval = runTimeBaseClassType.lookupMethodIgnoringParameterInSignatureNamed(methodName, suppliedParameters, ignoreName);
		} else {
			retval = null;
		}
		return retval;
	}
	public ClassDeclaration baseClassDeclaration() {
		final ClassDeclaration classDeclaration = typeDeclaration_ instanceof ClassDeclaration? (ClassDeclaration)typeDeclaration_: null;
		final ContextDeclaration contextDeclaration = typeDeclaration_ instanceof ContextDeclaration? (ContextDeclaration)typeDeclaration_: null;
		
		ClassDeclaration baseClassDeclaration = null;
		if (null != classDeclaration) {
			baseClassDeclaration = classDeclaration.baseClassDeclaration();
		} else if (null != contextDeclaration) {
			baseClassDeclaration = contextDeclaration.baseClassDeclaration();
		}
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
	
	// Not final; modified in derived classes
	protected Map<String, RTType> nameToTypeObjectMap_;
	
	protected final Map<String, RTRole> nameToRoleDeclMap_;
	protected final Map<String, RTStageProp> nameToStagePropDeclMap_;
	protected final Map<String, RTObject> nameToRoleBindingMap_, nameToStagePropBindingMap_;
	protected TypeDeclaration typeDeclaration_;
	private final Map<String, Map<FormalParameterList, RTMethod>> stringToMethodDeclMap_;
	protected final Map<String, RTObject> nameToStaticObjectMap_;
	protected final Map<String, Type> nameToStaticObjectTypeMap_;
	private final TemplateInstantiationInfo templateInstantiationInfo_;
}
