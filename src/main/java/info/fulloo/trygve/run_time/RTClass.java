package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.5
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

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.BuiltInType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;   
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBigIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
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
		this.doBaseClassProcessingHelper(classType, new ArrayList<RTClass>());
	}
	private void doBaseClassProcessingHelper(final ClassType classType, List<RTClass> derivedClasses) {
		final ClassType baseClassType = classType.baseClass();
		if (null != baseClassType) {
			// Add base class stuff, too.
			derivedClasses.add(this);
			final StaticScope baseClassEnclosedScope = baseClassType.enclosedScope();
			final RTType rawBaseClass = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(baseClassEnclosedScope);
			assert rawBaseClass instanceof RTClass;
			final RTClass baseClass = (RTClass)rawBaseClass;
			baseClass.doBaseClassProcessingHelper(baseClassType, derivedClasses);	// recur up the inheritance hierarchy
			for (final RTClass aDerivedClass : derivedClasses) {
				baseClass.populateNameToTypeObjectMap(aDerivedClass);
				baseClass.populateNameToStaticObjectMap(aDerivedClass);
			}
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
		// Can get here with stumbling (e.g., adding Roles to a Context that has
		// the same name as an existing class, like Scanner). Ignore it.
		// assert false;
	}
	@Override public void addRole(final String roleName, final RTRole roleType) {
		// Can get here with stumbling (e.g., adding Roles to a Context that has
		// the same name as an existing class, like Scanner). Ignore it.
		// assert false;
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
	
	public void postSetupInitialization() { }	// mainly for use in initializing statics
												// of built-in types (like System)
	
	public static RTDoubleObject makeDouble(final RTObject object) {
		RTDoubleObject retval = null;
		if (object instanceof RTDoubleObject) {
			retval = (RTDoubleObject)object;
		} else if (object instanceof RTIntegerObject) {
			retval = new RTDoubleObject((double)((RTIntegerObject)object).intValue());
		} else {
			// Return it and handle failure at a higher level
			// assert false;
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
					System.err.format("Line %d: Assertion failed: %s", lineNumber.intValue(), msg);
					System.err.println();
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
					System.err.format("Line %d: Assertion failed.", lineNumber.intValue());
					System.err.println();
				}
				final RTCode retval = tf? super.nextCode(): new RTHalt();
				return retval;
			}
		}
		public static class RTConvertCompareToToBooleanCode extends RTSimpleObjectMethodsCommon {
			public RTConvertCompareToToBooleanCode(final StaticScope enclosingMethodScope) {
				super("compareTo$toBoolean",
						RTMessage.buildArguments("Object", "compareTo$toBoolean",
										asList("code", "operator"),
										asList("int", "String"),
										enclosingMethodScope, true),
						StaticScope.globalScope().lookupTypeDeclaration("boolean"),
						Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), true);
			}
			@Override public RTCode run() {
				final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTIntegerObject theIntegerCodeObject = (RTIntegerObject)activationRecord.getObject("code");
				final RTStringObject theOperatorStringObject = (RTStringObject)activationRecord.getObject("operator");

				final long integerCode = theIntegerCodeObject.intValue();
				final String operator = theOperatorStringObject.stringValue();
				
				boolean rawRetval = false;
				
				switch ((int)integerCode) {
				case -1:
					rawRetval = (operator.equals("<") || operator.equals("<=")); break;
				case 0:
					rawRetval = (operator.equals("<=") || operator.equals(">=") || operator.equals("==")); break;
				case 1:
					rawRetval = (operator.equals(">") || operator.equals(">=")); break;
				default:
					assert false;
				}
				
				final RTBooleanObject result = new RTBooleanObject(rawRetval);
				
				addRetvalTo(activationRecord);
				activationRecord.setObject("ret$val", result);
				
				return super.nextCode();
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
				methodName_ = methodName;
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
			
			protected final String parameterName_, methodName_;
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
		public static class RTBinaryOpCode extends RTIntegerCommon {
			public RTBinaryOpCode(final StaticScope methodEnclosedScope, final String operation) {
				super("int", operation, "rhs", "int", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				RTCode nextPC = null;
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				if (self instanceof RTNullObject) {
					ErrorLogger.error(ErrorType.Runtime,
							"FATAL: TERMINATED: Attempt to invoke script `", methodName_, "' on null object on left-hand side of operation involving `",
							parameterName_ + "'.");
					RTMessage.printMiniStackStatus();
					nextPC = new RTHalt();
				} else {
					final RTStackable rhs = dynamicScope.getObject("rhs");
					if (rhs instanceof RTNullObject) {
						ErrorLogger.error(ErrorType.Runtime,
								"FATAL: TERMINATED: Attempt to invoke script `", methodName_, "' on null object on right-hand side, named `",
								parameterName_ + "'.");
						RTMessage.printMiniStackStatus();
						nextPC = new RTHalt();
					} else {
						assert self instanceof RTIntegerObject;
						assert rhs instanceof RTIntegerObject;

						final RTIntegerObject selfObject = (RTIntegerObject)self;
						final long selfValue = selfObject.intValue();
						final RTIntegerObject rhsObject = (RTIntegerObject)rhs;
						final long rhsValue = rhsObject.intValue();
						
						final String operator = this.methodSelectorName();
						long iRetval = 0;
						
						if (operator.equals("+")) {
							iRetval = selfValue + rhsValue;
						} else if (operator.equals("-")) {
							iRetval = selfValue - rhsValue;
						} else if (operator.equals("*")) {
							iRetval = selfValue * rhsValue;
						} else if (operator.equals("/")) {
							iRetval = selfValue / rhsValue;
						} else if (operator.equals("%")) {
							iRetval = selfValue % rhsValue;
						}
						final RTIntegerObject retval = new RTIntegerObject(iRetval);
		
						addRetvalTo(dynamicScope);
						dynamicScope.setObject("ret$val", retval);
						
						nextPC = super.nextCode();
					}
				}
				return nextPC;
			}
		}
		public static class RTCompareToCode extends RTIntegerCommon {
			public RTCompareToCode(final StaticScope methodEnclosedScope, final String operation) {
				super("int", operation, "other", "int", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTIntegerObject;
				final RTStackable other = dynamicScope.getObject("other");
				assert other instanceof RTIntegerObject;
				final RTIntegerObject selfObject = (RTIntegerObject)self;
				final long selfValue = selfObject.intValue();
				final RTIntegerObject otherObject = (RTIntegerObject)other;
				final long otherValue = otherObject.intValue();
				
				long iRetval = 0;
				if (selfValue > otherValue) iRetval = 1;
				else if (selfValue < otherValue) iRetval = -1;
				
				final RTIntegerObject retval = new RTIntegerObject(iRetval);

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
		public static class RTCompareToCode extends RTBigIntegerCommon {
			public RTCompareToCode(final StaticScope methodEnclosedScope, final String operation) {
				super("int", operation, "other", "int", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTIntegerObject;
				final RTStackable other = dynamicScope.getObject("other");
				assert other instanceof RTIntegerObject;
				final RTIntegerObject selfObject = (RTIntegerObject)self;
				final long selfValue = selfObject.intValue();
				final RTIntegerObject otherObject = (RTIntegerObject)other;
				final long otherValue = otherObject.intValue();
				
				long iRetval = 0;
				if (selfValue > otherValue) iRetval = 1;
				else if (selfValue < otherValue) iRetval = -1;
				
				final RTIntegerObject retval = new RTIntegerObject(iRetval);

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
		public static class RTBinaryOpCode extends RTDoubleCommon {
			public RTBinaryOpCode(final StaticScope methodEnclosedScope, final String operation) {
				super("double", operation, "rhs", "double", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("double"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTDoubleObject;
				final RTStackable rhs = dynamicScope.getObject("rhs");
				final RTDoubleObject selfObject = (RTDoubleObject)self;
				final double selfValue = selfObject.doubleValue();
				final RTDoubleObject rhsObject = RTClass.makeDouble((RTObject)rhs);
				final double rhsValue = rhsObject.doubleValue();
				
				final String operator = this.methodSelectorName();
				double dRetval = 0;
				
				if (operator.equals("+")) {
					dRetval = selfValue + rhsValue;
				} else if (operator.equals("-")) {
					dRetval = selfValue - rhsValue;
				} else if (operator.equals("*")) {
					dRetval = selfValue * rhsValue;
				} else if (operator.equals("/")) {
					dRetval = selfValue / rhsValue;
				} else if (operator.equals("%")) {
					dRetval = selfValue % rhsValue;
				}
				final RTDoubleObject retval = new RTDoubleObject(dRetval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		public static class RTCompareToCode extends RTDoubleCommon {
			public RTCompareToCode(final StaticScope methodEnclosedScope, final String operation) {
				super("double", operation, "other", "double", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				RTCode retval = null;
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTDoubleObject;
				final RTStackable rhs = dynamicScope.getObject("other");
				final RTDoubleObject selfObject = (RTDoubleObject)self;
				final double selfValue = selfObject.doubleValue();
				final RTDoubleObject otherObject = RTClass.makeDouble((RTObject)rhs);
				if (null == otherObject) {
					final RTDynamicScope parentScope = ((RTDynamicScope)myEnclosedScope).parentScope();
					ErrorLogger.error(ErrorType.Runtime, 0,
							"Attempt to access uninitialized double in argument to `",
							"compareTo'; calling context may be `",
							parentScope.name(), "'.");
					retval = new RTHalt();
				} else {
					final double otherValue = otherObject.doubleValue();
					
					int iResult = 0;
					if (selfValue > otherValue) iResult = 1;
					else if (selfValue < otherValue) iResult = -1;
					
					final RTIntegerObject result = new RTIntegerObject(iResult);
	
					addRetvalTo(dynamicScope);
					dynamicScope.setObject("ret$val", result);
					
					retval = super.nextCode();
				}
				
				return retval;
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
		public static class RTReplaceFirstCode extends RTStringCommon {
			public RTReplaceFirstCode(final StaticScope methodEnclosedScope) {
				super("String", "replaceFirst", asList("regex", "replacement"), asList("String", "String"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject stringObject = (RTStringObject)self;
				final RTObject regex = dynamicScope.getObject("regex");
				assert regex instanceof RTStringObject;
				final RTObject replacement = dynamicScope.getObject("replacement");
				assert replacement instanceof RTStringObject;
			
				RTStringObject retval = null;
				try {
					retval = stringObject.replaceFirst(regex, replacement);
				} catch (final PatternSyntaxException e) {
					ErrorLogger.error(ErrorType.Runtime, 0, "FATAL: Bad pattern to replaceFirst: `", regex.getText(), "'.", "");
					RTMessage.printMiniStackStatus();
					return null;
				}
				
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
		public static class RTSplitCode extends RTStringCommon {
			public RTSplitCode(final StaticScope methodEnclosedScope) {
				super("String", "split", asList("regex"), asList("String"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String_$array"));
				arrayType_ = (ArrayType)StaticScope.globalScope().lookupTypeDeclaration("String_$array");
				assert null != arrayType_;
				stringType_ = StaticScope.globalScope().lookupTypeDeclaration("String");
				assert null != stringType_;
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStringObject thisStringObject = (RTStringObject)self;
				final String thisString = thisStringObject.stringValue();
				final RTStackable regexStringObject = dynamicScope.getObject("regex");
				assert regexStringObject instanceof RTStringObject;
				final RTStringObject regexString = (RTStringObject)regexStringObject;
				final String rxstring = regexString.stringValue();
				String [] sRetval = null;
				try {
					sRetval = thisString.split(rxstring);
				} catch (final PatternSyntaxException e) {
					ErrorLogger.error(ErrorType.Runtime, 0, "FATAL: Bad pattern to regex: `", rxstring, "'.", "");
					RTMessage.printMiniStackStatus();
					return null;
				}
				
				final RTType type = new RTArrayType(stringType_, arrayType_);
				assert null != type;
				assert type instanceof RTArrayType;
				final RTArrayObject retval = new RTArrayObject(sRetval.length, (RTArrayType)type);
				
				// Now copy the String empire results into the RT* empire
				int runningIndex = 0;
				for (final String object : sRetval) {
					final RTStringObject rTString = new RTStringObject(object);
					final RTIntegerObject rTRunningIndex = new RTIntegerObject(runningIndex);
					retval.setObject(rTRunningIndex, rTString);
					runningIndex++;
				}

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
			
			final ArrayType arrayType_;
			final Type stringType_;
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
		public static class RTCompareToCode extends RTStringCommon {
			public RTCompareToCode(final StaticScope methodEnclosedScope, final String operation) {
				super("String", operation, asList("other"), asList("String"), methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTStringObject;
				final RTStackable otherObject = dynamicScope.getObject("other");
				final RTStringObject selfObject = (RTStringObject)self;
				final String selfValue = selfObject.stringValue();
				final String otherValue = ((RTStringObject)otherObject).stringValue();
				
				final int iResult = selfValue.compareTo(otherValue);
				
				final RTDoubleObject result = new RTDoubleObject(iResult);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", result);
				
				return super.nextCode();
			}
		}
		private static String stringJoin(final CharSequence delimiter, final Iterable<? extends CharSequence> elements) {
			final StringBuffer retvalBuffer = new StringBuffer();
			for (Iterator<? extends CharSequence> anIterator = elements.iterator(); anIterator.hasNext(); ) {
				final CharSequence aSlice = anIterator.next();
				retvalBuffer.append(aSlice);
				if (anIterator.hasNext()) {
					retvalBuffer.append(delimiter);
				}
			}
			final String retval = retvalBuffer.toString();
			return retval;
		}
		public static class RTJoinListCode extends RTStringCommon {
			public RTJoinListCode(final StaticScope methodEnclosedScope) {
				super("String", "join", asList("delimiter", "elements"), asList("String", "List<String>"),
						methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable delimeterObject = dynamicScope.getObject("delimiter");
				final RTStringObject delimeter = (RTStringObject)delimeterObject;
				final RTStackable elementsObjects = dynamicScope.getObject("elements");
				final RTListObject elements = (RTListObject)elementsObjects;
				final List<String> listCopy = new ArrayList<String>();
				final int listSize = elements.size();
				for (int i = 0; i < listSize; i++) {
					final RTStringObject aString = (RTStringObject)elements.get(i);
					listCopy.add(aString.stringValue());
				}
				
				final String sResult = stringJoin(delimeter.stringValue(), listCopy);

				final RTStringObject result = new RTStringObject(sResult);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", result);
				
				return super.nextCode();
			}
		}
		public static class RTJoinArrayCode extends RTStringCommon {
			public RTJoinArrayCode(final StaticScope methodEnclosedScope) {
				super("String", "join", asList("delimiter", "elements"), asList("String", "String_$array"),
						methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable delimeterObject = dynamicScope.getObject("delimiter");
				final RTStringObject delimeter = (RTStringObject)delimeterObject;
				final RTStackable elementsObjects = dynamicScope.getObject("elements");
				final RTArrayObject elements = (RTArrayObject)elementsObjects;
				final int arraySize = elements.size();
				final List<String> arrayCopy = new ArrayList<String>();
				for (int i = 0; i < arraySize; i++) {
					final RTStringObject aString = (RTStringObject)elements.get(i);
					arrayCopy.add(aString.stringValue());
				}
				
				final String sResult = stringJoin(delimeter.stringValue(), arrayCopy);

				final RTStringObject result = new RTStringObject(sResult);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", result);
				
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
		public static class RTBooleanCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
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
		public static class RTBinaryOpCode extends RTBooleanCommon {
			public RTBinaryOpCode(final StaticScope methodEnclosedScope, final String operation) {
				super("boolean", operation, "rhs", "boolean", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTBooleanObject;
				final RTStackable rhs = dynamicScope.getObject("rhs");
				assert rhs instanceof RTBooleanObject;
				final RTBooleanObject selfObject = (RTBooleanObject)self;
				final boolean selfValue = selfObject.value();
				final RTBooleanObject rhsObject = (RTBooleanObject)rhs;
				final boolean rhsValue = rhsObject.value();
				
				final String operator = this.methodSelectorName();
				boolean iRetval = false;
				
				if (operator.equals("&&")) {
					iRetval = selfValue && rhsValue;
				} else if (operator.equals("||")) {
					iRetval = selfValue || rhsValue;
				} else if (operator.equals("^")) {
					iRetval = selfValue ^ rhsValue;
				} else if (operator.equals("==")) {
					iRetval = selfValue == rhsValue;
				} else if (operator.equals("!=")) {
					iRetval = selfValue != rhsValue;
				}
				final RTBooleanObject retval = new RTBooleanObject(iRetval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		public static class RTCompareToCode extends RTBooleanCommon {
			public RTCompareToCode(final StaticScope methodEnclosedScope, final String operation) {
				super("boolean", operation, "other", "boolean", methodEnclosedScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope) {
				assert myEnclosedScope instanceof RTDynamicScope;
				final RTDynamicScope dynamicScope = (RTDynamicScope)myEnclosedScope;
				final RTStackable self = dynamicScope.getObject("this");
				assert self instanceof RTBooleanObject;
				final RTStackable other = dynamicScope.getObject("other");
				assert other instanceof RTBooleanObject;
				final RTBooleanObject selfObject = (RTBooleanObject)self;
				final boolean selfValue = selfObject.value();
				final RTBooleanObject otherObject = (RTBooleanObject)other;
				final boolean otherValue = otherObject.value();
				
				int iRetval = 0;
				
				if (selfValue == otherValue) iRetval = 0;
				else if (selfValue == false) iRetval = -1;
				else iRetval = 1;
				
				final RTIntegerObject retval = new RTIntegerObject(iRetval);

				addRetvalTo(dynamicScope);
				dynamicScope.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		
		@Override public RTType typeNamed(final String typeName) { return null; }
		@Override public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl) { return null; }
		@Override public TypeDeclaration typeDeclaration() { return StaticScope.globalScope().lookupClassDeclaration("boolean"); }
	}
	
	public static class RTSystemClass extends RTClass {
		public RTSystemClass(final TypeDeclaration associatedType) {
			super(associatedType);
		}
		public static class RTPrintStreamInfo extends RTObjectCommon {
			public RTPrintStreamInfo(final PrintStream printStream) {
				super((RTType)null);
				printStream_ = printStream;
			}
			public PrintStream printStream() {
				return printStream_;
			}
			
			private final PrintStream printStream_;
		}
		public static class RTInputStreamInfo extends RTObjectCommon {
			public RTInputStreamInfo(final InputStream inputStream) {
				super((RTType)null);
				inputStream_ = inputStream;
			}
			public InputStream inputStream() {
				return inputStream_;
			}
			
			private final InputStream inputStream_;
		}
		@Override public void postSetupInitialization() {
			// Lookup "out" and "err" and set them up
			final RTObject out = nameToStaticObjectMap_.get("out");
			RTPrintStreamInfo printStreamInfo = new RTPrintStreamInfo(System.out);
			out.addObjectDeclaration("printStreamInfo", null);
			out.setObject("printStreamInfo", printStreamInfo);
			
			final RTObject err = nameToStaticObjectMap_.get("err");
			printStreamInfo = new RTPrintStreamInfo(System.err);
			err.addObjectDeclaration("printStreamInfo", null);
			err.setObject("printStreamInfo", printStreamInfo);
			
			final RTObject in = nameToStaticObjectMap_.get("in");
			// final RTInputStreamInfo inputStreamInfo = new RTInputStreamInfo(System.in);
			final RTInputStreamInfo inputStreamInfo = new RTInputStreamInfo(RunTimeEnvironment.runTimeEnvironment_.redirectedInputStream());
			in.addObjectDeclaration("inputStreamInfo", null);
			in.setObject("inputStreamInfo", inputStreamInfo);
		}
	}
	
	private final Map<String, RTContext> stringToContextDeclMap_;
	private final Map<String, RTClass> stringToClassDeclMap_;
	private final Map<String, RTObject> nameToObjectDeclMap_;
}
