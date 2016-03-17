package info.fulloo.trygve.add_ons;

import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt; 
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.StaticScope;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/*
 * Trygve IDE 1.6
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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

public final class MathClass {
	private static void addSimpleStaticMethodDeclaration(
			final String methodName, final List<String> argumentNames, final List<Type> argumentTypes, final Type returnType) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final FormalParameterList formals = new FormalParameterList();
		
		if (null != argumentNames) {
			for (int i = 0; i < argumentNames.size(); i++) {
				final ObjectDeclaration formalParameter = 
						new ObjectDeclaration(argumentNames.get(i), argumentTypes.get(i), 0);
				formals.addFormalParameter(formalParameter);
			}
		} else {
			;
		}
		
		final StaticScope methodScope = new StaticScope(
				mathType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodName,
				methodScope, mathType_, Public, 0, true);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(true);
		mathType_.enclosedScope().declareMethod(methodDecl);
	}

	public static void setup() {
		typeDeclarationList_ = new ArrayList<TypeDeclaration>();
		final StaticScope globalScope = StaticScope.globalScope();

		if (null == globalScope.lookupTypeDeclaration("Math")) {
			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;
			final ClassDeclaration mathDecl = new ClassDeclaration("Math",
					newScope, objectBaseClass, 0);
			newScope.setDeclaration(mathDecl);
			mathType_ = new ClassType("Math", newScope, null);
			mathDecl.setType(mathType_);
			typeDeclarationList_.add(mathDecl);

			final Type doubleType = globalScope.lookupTypeDeclaration("double");
			final Type intType = globalScope.lookupTypeDeclaration("int");
			
			addSimpleStaticMethodDeclaration("random", null, null, doubleType);
			addSimpleStaticMethodDeclaration("sqrt", asList("x"), asList(doubleType), doubleType);
			addSimpleStaticMethodDeclaration("abs", asList("x"), asList(doubleType), doubleType);
			addSimpleStaticMethodDeclaration("abs", asList("x"), asList(intType), intType);
			addSimpleStaticMethodDeclaration("max", asList("x", "y"), asList(doubleType, doubleType), doubleType);
			addSimpleStaticMethodDeclaration("max", asList("x", "y"), asList(intType, intType), intType);
			addSimpleStaticMethodDeclaration("min", asList("x", "y"), asList(doubleType, doubleType), doubleType);
			addSimpleStaticMethodDeclaration("min", asList("x", "y"), asList(intType, intType), intType);

			// Declare the type
			globalScope.declareType(mathType_);
			globalScope.declareClass(mathDecl);
		}
	}
	public static class RTMathCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTMathCommon(final String className, final String methodName,
				final List<String> parameterNames,
				final List<String> parameterTypeNames,
				final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName, RTMessage.buildArguments(className, methodName, 
					parameterNames,
					parameterTypeNames,
					enclosingMethodScope, true), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), 
					true);
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
		public RTCode runDetails(RTObject scope) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine

		}
	}
	public static class RTRandomCode extends RTMathCommon {
		public RTRandomCode(StaticScope enclosingMethodScope) {
			super("Math", "random", asList("x"), asList("double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("double"));
		}
		@Override public RTCode runDetails(RTObject myEnclosedScope) {
			final RTDoubleObject answer = new RTDoubleObject(Math.random());
			
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", answer);
			
			return super.nextCode();
		}
	}
	public static class RTSqrtCode extends RTMathCommon {
		public RTSqrtCode(final StaticScope enclosingMethodScope) {
			super("Math", "sqrt", asList("x"), asList("double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("double"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawElement = activationRecord.getObject("x");
			double argument = 0.0;
			if (rawElement instanceof RTDoubleObject) {
				final RTDoubleObject element = (RTDoubleObject)rawElement;
				argument = Math.sqrt(element.doubleValue());
			} else if(rawElement instanceof RTIntegerObject) {
				final RTIntegerObject element = (RTIntegerObject)rawElement;
				argument = Math.sqrt((double)element.intValue());
			} else {
				assert false;
			}

			RTDoubleObject answer = null;
			if (argument < 0) {
				ErrorLogger.error(ErrorType.Runtime, "square root of negative number", "", "", "");
				answer = new RTDoubleObject(0);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(answer);
				nextPC = new RTHalt();	// halt the machine
			} else {
				answer = new RTDoubleObject(argument);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(answer);
				nextPC = super.nextCode();
			}
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", answer);
			
			return nextPC;
		}
	}
	public static class RTRealMaxCode extends RTMathCommon {
		public RTRealMaxCode(final StaticScope enclosingMethodScope) {
			super("Math", "max", asList("x", "y"), asList("double", "double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("double"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawXElement = activationRecord.getObject("x");
			final RTObject rawYElement = activationRecord.getObject("y");
			
			RTDoubleObject result = null;
			if (rawXElement instanceof RTDoubleObject) {
				double xArgument = 0.0, yArgument = 0.0;
				xArgument = ((RTDoubleObject)rawXElement).doubleValue();
				yArgument = ((RTDoubleObject)rawYElement).doubleValue();
				final double rawResult = Math.max(xArgument, yArgument);
				result = new RTDoubleObject(rawResult);
			} else {
				assert false;
			}

			nextPC = super.nextCode();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return nextPC;
		}
	}
	public static class RTIntMaxCode extends RTMathCommon {
		public RTIntMaxCode(final StaticScope enclosingMethodScope) {
			super("Math", "max", asList("x", "y"), asList("int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawXElement = activationRecord.getObject("x");
			final RTObject rawYElement = activationRecord.getObject("y");
			
			RTIntegerObject result = null;
			if (rawXElement instanceof RTIntegerObject) {
				int xArgument = 0, yArgument = 0;
				xArgument = (int)((RTIntegerObject)rawXElement).intValue();
				yArgument = (int)((RTIntegerObject)rawYElement).intValue();
				final int rawResult = Math.max(xArgument, yArgument);
				result = new RTIntegerObject((long)rawResult);
			} else {
				assert false;
			}

			nextPC = super.nextCode();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return nextPC;
		}
	}
	public static class RTRealMinCode extends RTMathCommon {
		public RTRealMinCode(final StaticScope enclosingMethodScope) {
			super("Math", "min", asList("x", "y"), asList("double", "double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("double"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawXElement = activationRecord.getObject("x");
			final RTObject rawYElement = activationRecord.getObject("y");
			
			RTDoubleObject result = null;
			if (rawXElement instanceof RTDoubleObject) {
				double xArgument = 0.0, yArgument = 0.0;
				xArgument = ((RTDoubleObject)rawXElement).doubleValue();
				yArgument = ((RTDoubleObject)rawYElement).doubleValue();
				final double rawResult = Math.min(xArgument, yArgument);
				result = new RTDoubleObject(rawResult);
			} else {
				assert false;
			}

			nextPC = super.nextCode();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return nextPC;
		}
	}
	public static class RTIntMinCode extends RTMathCommon {
		public RTIntMinCode(final StaticScope enclosingMethodScope) {
			super("Math", "min", asList("x", "y"), asList("int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawXElement = activationRecord.getObject("x");
			final RTObject rawYElement = activationRecord.getObject("y");
			
			RTIntegerObject result = null;
			if (rawXElement instanceof RTIntegerObject) {
				int xArgument = 0, yArgument = 0;
				xArgument = (int)((RTIntegerObject)rawXElement).intValue();
				yArgument = (int)((RTIntegerObject)rawYElement).intValue();
				final int rawResult = Math.min(xArgument, yArgument);
				result = new RTIntegerObject((long)rawResult);
			} else {
				assert false;
			}

			nextPC = super.nextCode();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return nextPC;
		}
	}
	public static class RTRealAbsCode extends RTMathCommon {
		public RTRealAbsCode(final StaticScope enclosingMethodScope) {
			super("Math", "abs", asList("x"), asList("double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("double"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawXElement = activationRecord.getObject("x");
			
			RTObject result = null;
			if (rawXElement instanceof RTIntegerObject) {
				int xArgument = (int)((RTIntegerObject)rawXElement).intValue();
				final int rawResult = Math.abs(xArgument);
				result = new RTIntegerObject(rawResult);
			} else if (rawXElement instanceof RTDoubleObject) {
				double xArgument = 0.0;
				xArgument = ((RTDoubleObject)rawXElement).doubleValue();
				final double rawResult = Math.abs(xArgument);
				result = new RTDoubleObject(rawResult);
			} else {
				assert false;
			}

			nextPC = super.nextCode();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return nextPC;
		}
	}
	public static class RTIntAbsCode extends RTMathCommon {
		public RTIntAbsCode(final StaticScope enclosingMethodScope) {
			super("Math", "abs", asList("x"), asList("int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode nextPC = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawXElement = activationRecord.getObject("x");
			
			RTIntegerObject result = null;
			if (rawXElement instanceof RTIntegerObject) {
				int xArgument = 0;
				xArgument = (int)((RTIntegerObject)rawXElement).intValue();
				final int rawResult = Math.abs(xArgument);
				result = new RTIntegerObject((long)rawResult);
			} else {
				assert false;
			}

			nextPC = super.nextCode();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return nextPC;
		}
	}
	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}

	private static List<TypeDeclaration> typeDeclarationList_;
	private static Type mathType_;
}
