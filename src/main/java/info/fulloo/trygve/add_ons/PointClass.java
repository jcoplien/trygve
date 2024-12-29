package info.fulloo.trygve.add_ons;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import info.fulloo.trygve.run_time.RTObjectCommon;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

public final class PointClass {
	private static void declarePointMethod(final String methodSelector,
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
					final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, null);
					formals.addFormalParameter(formalParameter);
				}
			}
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", pointType_, null);
		formals.addFormalParameter(self);
		final StaticScope methodScope = new StaticScope(pointType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, null, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		pointType_.enclosedScope().declareMethod(methodDecl, null);
	}
	
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == (pointType_ = (ClassType)globalScope.lookupTypeDeclaration("Point"))) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final Type intType = globalScope.lookupTypeDeclaration("int");
			final Type voidType = globalScope.lookupTypeDeclaration("void");
			
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("Point", newScope, objectBaseClass, null);
			newScope.setDeclaration(classDecl);
			pointType_ = new ClassType("Point", newScope, null);
			classDecl.setType(pointType_);
			typeDeclarationList_.add(classDecl);

			declarePointMethod("Point", null, asList("y", "x"), asList(intType, intType), false);
			declarePointMethod("Point", null, null, null, false);
			declarePointMethod("getX", intType, null, null, true);
			declarePointMethod("getY", intType, null, null, true);
			declarePointMethod("setXY", voidType, asList("y", "x"), asList(intType, intType), false);
			
			// These need to be coordinated only with what is in the postSetupInitialization
			// method below.
			for (final String attributeName : asList("x", "y")) {
				final ObjectDeclaration attributeDeclaration = new ObjectDeclaration(attributeName, intType, null);
				attributeDeclaration.setAccess(AccessQualifier.PublicAccess, pointType_.enclosedScope(), null);
				pointType_.enclosedScope().declareObject(attributeDeclaration, null);
			}
			
			globalScope.declareType(pointType_);
			globalScope.declareClass(classDecl);
			classDecl.setType(pointType_);
		}
	}
	
	
	public static class RTPointCode extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTPointCode(final String className, final String methodName, final List<String> parameterNames,
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
			final RTCode retval = this.runDetails(activationRecord, self);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope, final RTObject point) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
	}
	public static class RTPointCtor1 extends RTPointCode {
		public RTPointCtor1(final StaticScope enclosingMethodScope) {
			super("Point", "Point", asList("x", "y"), asList("int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObject thePointObject) {
			assert null != thePointObject;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject x = activationRecord.getObject("x");
			final RTObject y = activationRecord.getObject("y");
			assert x instanceof RTIntegerObject;
			assert y instanceof RTIntegerObject;
			thePointObject.setObject("x", x);
			thePointObject.setObject("y", y);
			
			RunTimeEnvironment.runTimeEnvironment_.pushStack(thePointObject);
			
			return super.nextCode();
		}
	}
	public static class RTPointCtor2 extends RTPointCode {
		public RTPointCtor2(final StaticScope enclosingMethodScope) {
			super("Point", "Point", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObject thePointObject) {
			thePointObject.setObject("x", new RTIntegerObject(0));
			thePointObject.setObject("y", new RTIntegerObject(0));
			
			RunTimeEnvironment.runTimeEnvironment_.pushStack(thePointObject);
			
			return super.nextCode();
		}
	}
	public static class RTPointGetYCode extends RTPointCode {
		public RTPointGetYCode(final StaticScope enclosingMethodScope) {
			super("Point", "getY", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObject thePointObject) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject retval = thePointObject.getObject("y");
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	public static class RTPointGetXCode extends RTPointCode {
		public RTPointGetXCode(final StaticScope enclosingMethodScope) {
			super("Point", "getX", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObject thePointObject) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject retval = thePointObject.getObject("x");
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	public static class RTPointSetXYCode extends RTPointCode {
		public RTPointSetXYCode(final StaticScope enclosingMethodScope) {
			super("Point", "setXY", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObject thePointObject) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject x = activationRecord.getObject("x");
			final RTObject y = activationRecord.getObject("y");
			
			thePointObject.setObject("x", x);
			thePointObject.setObject("y", y);
			
			return super.nextCode();
		}
	}
	
	public static class RTPointObject extends RTObjectCommon {
		public RTPointObject(final Point point) {
			super(InterpretiveCodeGenerator.scopeToRTTypeDeclaration(
					StaticScope.globalScope().lookupTypeDeclaration("Point").enclosedScope()));
			final RTType rTIntType = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(
					(TypeDeclaration)StaticScope.globalScope().lookupTypeDeclaration("int").enclosedScope().associatedDeclaration());
			this.addObjectDeclaration("x", rTIntType);
			this.addObjectDeclaration("y", rTIntType);
			this.setObject("x", new RTIntegerObject(point.x));
			this.setObject("y", new RTIntegerObject(point.y));
		}
	}

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType pointType_ = null;
}