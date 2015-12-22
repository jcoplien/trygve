package info.fulloo.trygve.run_time;

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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.BuiltInType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBigIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;


public class RTClass extends RTClassAndContextCommon implements RTType {
	public RTClass(final TypeDeclaration decl) {
		super(decl);
		assert decl instanceof ClassDeclaration;
		
		stringToContextDeclMap_ = new LinkedHashMap<String, RTContext>();
		stringToClassDeclMap_ = new LinkedHashMap<String, RTClass>();
		nameToObjectDeclMap_ = new LinkedHashMap<String, RTObject>();
		RunTimeEnvironment.runTimeEnvironment_.addToListOfAllClasses(this);
		
		final Type rawClassType = decl.type();
		if (null != rawClassType) {
			if (rawClassType instanceof BuiltInType) {
				;
			} else if (rawClassType instanceof ClassType) {
				final ClassType classType = (ClassType)rawClassType;
				this.doBaseClassProcessing(classType);
			} else {
				assert false;
			}
		}
		
		super.populateNameToTypeObjectMap();
		super.populateNameToStaticObjectMap();
	}
	private void doBaseClassProcessing(final ClassType classType) {
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
	@Override public void addClass(final String typeName, final RTClass classDecl) {
		stringToClassDeclMap_.put(typeName, classDecl);
	}
	@Override public void addContext(final String typeName, final RTContext contextDecl) {
		stringToContextDeclMap_.put(typeName, contextDecl);
	}
	public RTType typeNamed(final String typeName) {
		RTType retval = this.stringToClassDeclMap_.get(typeName);
		if (null == retval) {
			retval = this.stringToContextDeclMap_.get(typeName);
		}
		return retval;
	}
	@Override public void setObject(final String objectName, final RTObject object) {
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
	@Override public RTObject getObject(final String objectName) {
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
	@Override public void addObjectDeclaration(final String objectName, final RTType objectType) {
		assert false;
	}
	@Override public void addStageProp(final String stagePropName, final RTStageProp stagePropType) {
		assert false;
	}
	@Override public void addRole(final String roleName, final RTRole roleType) {
		assert false;
	}
	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		assert false;
		return null;
	}
	@Override public Map<String, RTStageProp> nameToStagePropDeclMap() {
		assert false;
		return null;
	}
	/*
	protected void populateNameToMethodMap() {
		final List<MethodDeclaration> methodDeclarations = typeDeclaration_.enclosedScope().methodDeclarations();
		for (final MethodDeclaration methodDecl : methodDeclarations) {
			final String methodName = methodDecl.name();
			final RTMethod rTMethodDecl = new RTMethod(methodName, methodDecl);
			super.addMethod(methodName, rTMethodDecl);
		}
	}
	*/
	
	// ---------------------
	
	public RTObject performUnaryOpOnStaticObjectNamed(final String objectName, final String operator, final PreOrPost preOrPost) {
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

	public static class RTObjectClass extends RTClass {
		public RTObjectClass(final TypeDeclaration associatedType) {
			super(associatedType);
		}
		public static class RTObjectCommon extends RTMessage {
			public RTObjectCommon(final String className, final String methodName, final List<String> parameterNames, final List<String> parameterTypeNames,
					 final StaticScope enclosingMethodScope, final Type returnType, final boolean isStatic) {
				super(methodName, RTMessage.buildArguments(className, methodName, parameterNames, parameterTypeNames, enclosingMethodScope, isStatic), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
						isStatic);
			}
			protected RTObjectCommon(final String objectName, final String halt) {
				// Just for RTHalt
				super(objectName, RTMessage.buildArguments("Object", "halt", asList("x"), asList("int"), StaticScope.globalScope(), true),
						StaticScope.globalScope().lookupTypeDeclaration("void"),
						StaticScope.globalScope().lookupTypeDeclaration("void"), true);
			}
			public RTCode run() {
				final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				RTCode retval = this.runDetails(myEnclosedScope);
				return retval;
			}
			public RTCode runDetails(final RTObject scope) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
				return null;	// halt the machine
			}
		}
		public static class RTSimpleObjectMethodsCommon extends RTMessage {
			public RTSimpleObjectMethodsCommon(final String name, final ActualArgumentList parameters,
					final Type returnType, final Type enclosingMegaType,
					final boolean isStatic) {
				super(name, parameters, returnType, enclosingMegaType, isStatic);
			}
			protected void addRetvalTo(final RTDynamicScope activationRecord) {
				if (null == activationRecord.getObject("ret$val")) {
					activationRecord.addObjectDeclaration("ret$val", null);
				}
			}
		}
		public static class RTAssertCode extends RTObjectCommon {
			public RTAssertCode(final StaticScope methodEnclosedScope) {
				super("Object", "assert",
						asList("tf", "msg"), asList("boolean", "String"),
						methodEnclosedScope,
						StaticScope.globalScope().lookupTypeDeclaration("void"),
						false);
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTIntegerObject lineNumber = (RTIntegerObject)dynamicScope.getObject("lineNumber");
				final RTStackable tfArg = dynamicScope.getObject("tf");
				assert tfArg instanceof RTBooleanObject;
				final RTBooleanObject booleanObject = (RTBooleanObject)tfArg;
				final boolean tf = booleanObject.value();
				if (!tf) {
					final RTStackable msgArg = dynamicScope.getObject("msg");
					assert msgArg instanceof RTStringObject;
					final RTStringObject stringObject = (RTStringObject)msgArg;
					final String msg = stringObject.stringValue();
					System.err.format("Line %d: Assertion failed: %s\n", lineNumber.intValue(), msg);
				}
				final RTCode retval = tf? super.nextCode(): new RTHalt();
				return retval;
			}
		}
		public static class RTAssertCodeMinimal extends RTObjectCommon {
			public RTAssertCodeMinimal(final StaticScope methodEnclosedScope) {
				super("Object", "assert",
						asList("tf"), asList("boolean"),
						methodEnclosedScope,
						StaticScope.globalScope().lookupTypeDeclaration("void"),
						false);
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTIntegerObject lineNumber = (RTIntegerObject)dynamicScope.getObject("lineNumber");
				final RTStackable tfArg = dynamicScope.getObject("tf");
				assert tfArg instanceof RTBooleanObject;
				final RTBooleanObject booleanObject = (RTBooleanObject)tfArg;
				final boolean tf = booleanObject.value();
				if (!tf) {
					System.err.format("Line %d: Assertion failed.\n", lineNumber.intValue());
				}
				final RTCode retval = tf? super.nextCode(): new RTHalt();
				return retval;
			}
		}
		public static class RTHalt extends RTObjectCommon {
			public RTHalt() {
				super("Object", "halt");
			}
		}
	}
	
	public static class RTIntegerClass extends RTClass {
		public RTIntegerClass(final TypeDeclaration associatedType) {
			super(associatedType);
		}
		public static class RTIntegerCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
			public RTIntegerCommon(final String className, final String methodName, final String parameterName,
					final String parameterTypeName, final StaticScope enclosingMethodScope, final Type returnType) {
				super(methodName, RTMessage.buildArguments(className, methodName,
						null == parameterName? null: asList(parameterName),
						null == parameterTypeName? null: asList(parameterTypeName),
						enclosingMethodScope, false), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
						false);
				parameterName_ = parameterName;
			}
			public RTCode run() {
				// Don't need to push or pop anything. The return code stays
				// until the RTReturn statement processes it, and everything
				// else has been popped into the activation record by
				// RTMessage
				// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				// 		Yes, but...: assert returnCode instanceof RTCode;
				
				// Parameters have all been packaged into the
				// activation record
				final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				RTCode retval = this.runDetails(myEnclosedScope);

				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
				return null;	// halt the machine
			}
			
			protected final String parameterName_;
		}
		public static class RTToStringCode extends RTIntegerCommon {
			public RTToStringCode(final StaticScope methodEnclosedScope) {
				super("int", "toString", null, null, methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTIntegerObject;
				final RTIntegerObject intObject = (RTIntegerObject)self;
				final long iRetval = intObject.intValue();
				final RTStringObject retval = new RTStringObject(String.valueOf(iRetval));

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		@Override public RTType typeNamed(final String typeName) { return null; }
		@Override public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("int"); }
	}
	public static class RTBigIntegerClass extends RTClass {
		public RTBigIntegerClass(final TypeDeclaration associatedType) {
			super(associatedType);
		}
		public static class RTBigIntegerCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
			public RTBigIntegerCommon(final String className, final String methodName, final String parameterName,
					final String parameterTypeName, final StaticScope enclosingMethodScope, final Type returnType) {
				super(methodName, RTMessage.buildArguments(className, methodName,
						null == parameterName? null: asList(parameterName),
						null == parameterTypeName? null: asList(parameterTypeName),
						enclosingMethodScope, true), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
						true);
				parameterName_ = parameterName;
			}
			public RTCode run() {
				// Don't need to push or pop anything. The return code stays
				// until the RTReturn statement processes it, and everything
				// else has been popped into the activation record by
				// RTMessage
				// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				// 		Yes, but...: assert returnCode instanceof RTCode;
				
				// Parameters have all been packaged into the
				// activation record
				final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				RTCode retval = this.runDetails(myEnclosedScope);

				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
				return null;	// halt the machine
			}
			
			protected final String parameterName_;
		}
		public static class RTToStringCode extends RTBigIntegerCommon {
			public RTToStringCode(final StaticScope methodEnclosedScope) {
				super("Integer", "toString", null, null, methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTBigIntegerObject;
				final RTBigIntegerObject intObject = (RTBigIntegerObject)self;
				final long iRetval = intObject.intValue();
				final RTStringObject retval = new RTStringObject(String.valueOf(iRetval));

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		@Override public RTType typeNamed(final String typeName) { return null; }
		@Override public RTMethod lookupMethod(final String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("Integer"); }
	}
	public static class RTDoubleClass extends RTClass {
		public RTDoubleClass(final TypeDeclaration associatedType) {
			super(associatedType);
		}
		public static class RTDoubleCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
			public RTDoubleCommon(final String className, final String methodName, final String parameterName,
					final String parameterTypeName, final StaticScope enclosingMethodScope, final Type returnType) {
				super(methodName, RTMessage.buildArguments(className, methodName, 
						null == parameterName? null: asList(parameterName),
						null == parameterTypeName? null: asList(parameterTypeName),
						enclosingMethodScope, false), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
						false);
				parameterName_ = parameterName;
			}
			public RTCode run() {
				// Don't need to push or pop anything. The return code stays
				// until the RTReturn statement processes it, and everything
				// else has been popped into the activation record by
				// RTMessage
				// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				// 		Yes, but...: assert returnCode instanceof RTCode;
				
				// Parameters have all been packaged into the
				// activation record
				final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				RTCode retval = this.runDetails(myEnclosedScope);

				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
				return null;	// halt the machine
			}
			
			protected final String parameterName_;
		}
		public static class RTToStringCode extends RTDoubleCommon {
			public RTToStringCode(final StaticScope methodEnclosedScope) {
				super("double", "toString", null, null, methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTDoubleObject;
				final RTDoubleObject doubleObject = (RTDoubleObject)self;
				final double dRetval = doubleObject.doubleValue();
				final RTStringObject retval = new RTStringObject(String.valueOf(dRetval));

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		@Override public RTType typeNamed(final String typeName) { return null; }
		@Override public RTMethod lookupMethod(final String methodName, ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("double"); }
	}
	public static class RTStringClass extends RTClass {
		public RTStringClass(final TypeDeclaration associatedType) {
			super(associatedType);
			// super.populateNameToMethodMap();
		}
		public static class RTStringCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
			public RTStringCommon(final String className, final String methodName, final List<String> parameterNames,
					final List<String> parameterTypeNames, final StaticScope enclosingMethodScope, final Type returnType) {
				super(methodName,
						RTMessage.buildArguments(className, methodName, parameterNames, parameterTypeNames, enclosingMethodScope, false),
						returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
						false);
			}
			public RTCode run() {
				// Don't need to push or pop anything. The return code stays
				// until the RTReturn statement processes it, and everything
				// else has been popped into the activation record by
				// RTMessage
				// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				// 		Yes, but...: assert returnCode instanceof RTCode;
				
				// Parameters have all been packaged into the
				// activation record
				final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				RTCode retval = this.runDetails(myEnclosedScope);

				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
				return null;	// halt the machine

			}
		}
		public static class RTLengthCode extends RTStringCommon {
			public RTLengthCode(final StaticScope methodEnclosedScope) {
				super("String", "length", null, null, methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject stringObject = (RTStringObject)self;
				final long iRetval = stringObject.stringValue().length();
				final RTIntegerObject result = new RTIntegerObject(iRetval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", result);
				
				return super.nextCode();
			}
		}
		public static class RTSubstringCode extends RTStringCommon {
			public RTSubstringCode(final StaticScope methodEnclosedScope) {
				super("String", "substring", asList("start", "end"), asList("int", "int"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject stringObject = (RTStringObject)self;
				final RTObject from = dynamicScope.getObject("start");
				final RTObject to = dynamicScope.getObject("end");
				final RTStringObject retval = stringObject.substring(from, to);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		public static class RTToStringCode extends RTStringCommon {
			public RTToStringCode(final StaticScope methodEnclosedScope) {
				super("String", "toString", null, null, methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject retval = (RTStringObject)self;

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		public static class RTPlusCode extends RTStringCommon {
			public RTPlusCode(final StaticScope methodEnclosedScope) {
				super("String", "+", asList("rhs"), asList("String"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject firstStringObject = (RTStringObject)self;
				final String sRetval1 = firstStringObject.stringValue();
				final RTStackable secondStringObject = dynamicScope.getObject("rhs");
				assert secondStringObject instanceof RTStringObject;
				final RTStringObject rhs = (RTStringObject)secondStringObject;
				final String sRetval2 = rhs.stringValue();
				final RTStringObject retval = new RTStringObject(sRetval1 + sRetval2);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				return super.nextCode();
			}
		}
		public static class RTIndexOfCode extends RTStringCommon {
			public RTIndexOfCode(final StaticScope methodEnclosedScope) {
				super("String", "indexOf", asList("searchString"), asList("String"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject thisStringObject = (RTStringObject)self;
				final String thisString = thisStringObject.stringValue();
				final RTStackable searchStringObject = dynamicScope.getObject("searchString");
				assert searchStringObject instanceof RTStringObject;
				final RTStringObject searchString = (RTStringObject)searchStringObject;
				final String sstring = searchString.stringValue();
				final long lRetval = thisString.indexOf(sstring);
				final RTIntegerObject retval = new RTIntegerObject(lRetval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		
		public static class RTContainsCode extends RTStringCommon {
			public RTContainsCode(final StaticScope methodEnclosedScope) {
				super("String", "contains", asList("searchString"), asList("String"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject thisStringObject = (RTStringObject)self;
				final String thisString = thisStringObject.stringValue();
				final RTStackable searchStringObject = dynamicScope.getObject("searchString");
				assert searchStringObject instanceof RTStringObject;
				final RTStringObject searchString = (RTStringObject)searchStringObject;
				final String sstring = searchString.stringValue();
				final boolean bRetval = thisString.contains(sstring);
				final RTBooleanObject retval = new RTBooleanObject(bRetval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		
		@Override public RTType typeNamed(final String typeName) { return null; }
		@Override public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("String"); }
	}
	public static class RTBooleanClass extends RTClass {
		public RTBooleanClass(final TypeDeclaration associatedType) {
			super(associatedType);
		}
		public static class RTBooleanCommon extends RTMessage {
			public RTBooleanCommon(final String className, final String methodName, final String parameterName,
					final String parameterTypeName, final StaticScope enclosingMethodScope, final Type returnType) {
				super(methodName, RTMessage.buildArguments(className, methodName,
						null == parameterName? null: asList(parameterName),
						null == parameterTypeName? null: asList(parameterTypeName),
						enclosingMethodScope, false), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
						false);
			}
			public RTCode run() {
				// Don't need to push or pop anything. The return code stays
				// until the RTReturn statement processes it, and everything
				// else has been popped into the activation record by
				// RTMessage
				// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				// 		Yes, but...: assert returnCode instanceof RTCode;
				
				// Parameters have all been packaged into the
				// activation record
				final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				RTCode retval = this.runDetails(myEnclosedScope);

				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
				return null;	// halt the machine

			}
		}
		public static class RTToStringCode extends RTBooleanCommon {
			public RTToStringCode(final StaticScope methodEnclosedScope) {
				super("boolean", "toString", null, null, methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTBooleanObject;
				RTBooleanObject bo = (RTBooleanObject)self;
				final boolean bRetval = bo.value();
				final RTStringObject retval = new RTStringObject(bRetval? "true": "false");
				RunTimeEnvironment.runTimeEnvironment_.pushStack(retval);
				return super.nextCode();
			}
		}
		
		@Override public RTType typeNamed(final String typeName) { return null; }
		@Override public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("boolean"); }
	}
	
	private final Map<String, RTContext> stringToContextDeclMap_;
	private final Map<String, RTClass> stringToClassDeclMap_;
	private final Map<String, RTObject> nameToObjectDeclMap_;
}
