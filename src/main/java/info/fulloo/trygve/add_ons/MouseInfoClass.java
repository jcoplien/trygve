package info.fulloo.trygve.add_ons;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.fulloo.trygve.add_ons.PointClass.RTPointObject;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTObjectCommon;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.StaticScope;

/*
 * Trygve IDE 2.0
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

public final class MouseInfoClass {
	private static void declareMouseInfoMethod(final String methodSelector,
			final Type returnType,
			final List<String> paramNames,
			final List<Type> paramTypes,
			final boolean isConst,
			final boolean isStatic) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final Iterator<Type> typeIterator = null == paramTypes? null: paramTypes.iterator();
		final FormalParameterList formals = new FormalParameterList();
		if (null != paramNames) {
			for (final String paramName : paramNames) {
				if (null != paramName) {
					final Type paramType = typeIterator.next();
					final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
					formals.addFormalParameter(formalParameter);
				}
			}
		}
		final StaticScope methodScope = new StaticScope(mouseInfoType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, isStatic);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		mouseInfoType_.enclosedScope().declareMethod(methodDecl);
	}
	
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == (mouseInfoType_ = (ClassType)globalScope.lookupTypeDeclaration("MouseInfo"))) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final Type pointerInfoType = globalScope.lookupTypeDeclaration("PointerInfo");
			
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("MouseInfo", newScope, objectBaseClass, 0);
			newScope.setDeclaration(classDecl);
			mouseInfoType_ = new ClassType("MouseInfo", newScope, null);
			classDecl.setType(mouseInfoType_);
			typeDeclarationList_.add(classDecl);

			declareMouseInfoMethod("getPointerInfo", pointerInfoType, null, null, true, true);
			
			globalScope.declareType(mouseInfoType_);
			globalScope.declareClass(classDecl);
			classDecl.setType(mouseInfoType_);
		}
	}
	
	public static class RTMouseInfoCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTMouseInfoCommon(final String className, final String methodName, final List<String> parameterNames,
				final List<String> parameterTypeNames, final StaticScope enclosingMethodScope, final Type returnType,
				final boolean isStatic) {
			super(methodName, RTMessage.buildArguments(className, methodName, parameterNames, parameterTypeNames, enclosingMethodScope, false),
					returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), isStatic);
		}
		@Override public RTCode run() {
			// Don't need to push or pop anything. The return code stays
			// until the RTReturn statement processes it, and everything
			// else has been popped into the activation record by
			// RTMessage
			// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
			// 		Yes, but...: assert returnCode instanceof RTCode;
			
			// Parameters have all been packaged into the
			// activation record
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTCode retval = this.runDetails(activationRecord);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
	}
	
	public static class RTPointerInfoObject extends RTObjectCommon {
		public RTPointerInfoObject(final PointerInfo pointerInfo) {
			super(InterpretiveCodeGenerator.scopeToRTTypeDeclaration(
					StaticScope.globalScope().lookupTypeDeclaration("PointerInfo").enclosedScope()));
			pointerInfo_ = pointerInfo;
		}
		public PointerInfo pointerInfo() { return pointerInfo_; }
		private final PointerInfo pointerInfo_;
	}
	
	public static class RTGetPointerInfoCode extends RTMouseInfoCommon {
		public RTGetPointerInfoCode(final StaticScope enclosingMethodScope) {
			super("MouseInfo", "getPointerInfo", null, null, enclosingMethodScope,
					StaticScope.globalScope().lookupTypeDeclaration("PointerInfo"), true);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			final RTPointerInfoObject retval = new RTPointerInfoObject(pointerInfo);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	
	public static class PointerInfoClass extends RTObjectClass {
		public PointerInfoClass(final PointerInfo pointerInfo) {
			super((TypeDeclaration)StaticScope.globalScope().lookupTypeDeclaration("PointerInfo"));
		}
		private static void declarePointerInfoMethod(final String methodSelector,
				final Type returnType,
				final List<String> paramNames,
				final List<Type> paramTypes,
				final boolean isConst) {
			final AccessQualifier Public = AccessQualifier.PublicAccess;
			
			final Iterator<Type> typeIterator = null == paramTypes? null: paramTypes.iterator();
			final FormalParameterList formals = new FormalParameterList();
			if (null != paramNames) {
				for (final String paramName : paramNames) {
					if (null != paramName) {
						final Type paramType = typeIterator.next();
						final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
						formals.addFormalParameter(formalParameter);
					}
				}
			}
			final ObjectDeclaration self = new ObjectDeclaration("this", pointerInfoType_, 0);
			formals.addFormalParameter(self);
			final StaticScope methodScope = new StaticScope(pointerInfoType_.enclosedScope());
			final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, false);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(returnType);
			methodDecl.setHasConstModifier(isConst);
			pointerInfoType_.enclosedScope().declareMethod(methodDecl);
		}
		
		public static void setup() {
			final StaticScope globalScope = StaticScope.globalScope();
			if (null == (pointerInfoType_ = (ClassType)globalScope.lookupTypeDeclaration("PointerInfo"))) {
				typeDeclarationList_ = new ArrayList<TypeDeclaration>();
				final Type pointType = globalScope.lookupTypeDeclaration("Point");
				assert null != pointType;
				
				final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
				assert null != objectBaseClass;

				final StaticScope newScope = new StaticScope(globalScope);
				final ClassDeclaration classDecl = new ClassDeclaration("PointerInfo", newScope, objectBaseClass, 0);
				newScope.setDeclaration(classDecl);
				pointerInfoType_ = new ClassType("PointerInfo", newScope, null);
				classDecl.setType(pointerInfoType_);
				typeDeclarationList_.add(classDecl);

				declarePointerInfoMethod("getLocation", pointType, null, null, false);
				
				globalScope.declareType(pointerInfoType_);
				globalScope.declareClass(classDecl);
				classDecl.setType(pointerInfoType_);
			}
		}
		
		public static class RTPointerInfoCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
			public RTPointerInfoCommon(final String className, final String methodName, final List<String> parameterNames,
					final List<String> parameterTypeNames, final StaticScope enclosingMethodScope, final Type returnType) {
				super(methodName, RTMessage.buildArguments(className, methodName, parameterNames, parameterTypeNames, enclosingMethodScope, false),
						returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
			}
			@Override public RTCode run() {
				// Don't need to push or pop anything. The return code stays
				// until the RTReturn statement processes it, and everything
				// else has been popped into the activation record by
				// RTMessage
				// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				// 		Yes, but...: assert returnCode instanceof RTCode;
				
				// Parameters have all been packaged into the
				// activation record
				final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTObject self = (RTObject)activationRecord.getObject("this");
				assert self instanceof RTPointerInfoObject;
				final RTCode retval = this.runDetails(activationRecord, (RTPointerInfoObject)self);
				
				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope, final RTPointerInfoObject pointerInfo) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
				return null;	// halt the machine
			}
		}
		public static class RTGetLocationCode extends RTPointerInfoCommon {
			public RTGetLocationCode(final StaticScope enclosingMethodScope) {
				super("PointerInfo", "getLocation", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("Point"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPointerInfoObject pointerInfoObject) {
				assert null != pointerInfoObject;
				final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final PointerInfo pointerInfo = pointerInfoObject.pointerInfo();
				final Point rawRetval = pointerInfo.getLocation();
				final RTPointObject retval = new RTPointObject(rawRetval);
				
				addRetvalTo(activationRecord);
				activationRecord.setObject("ret$val", retval);
				
				return super.nextCode();
			}
		}
		
		public static List<TypeDeclaration> typeDeclarationList() {
			return typeDeclarationList_;
		}
		
		private static List<TypeDeclaration> typeDeclarationList_;
		private static ClassType pointerInfoType_ = null;
	}
	
	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType mouseInfoType_ = null;
}