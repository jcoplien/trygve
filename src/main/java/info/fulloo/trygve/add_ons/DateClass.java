package info.fulloo.trygve.add_ons;

import static java.util.Arrays.asList;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDateObject;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.StaticScope;

import java.util.ArrayList;
import java.util.List;

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

public final class DateClass {
	private static void singleSimpleFunctionSetup(final String methodSelector,
			final ObjectDeclaration parameter, final String returnTypeName) {
		final FormalParameterList formals = new FormalParameterList();

		if (null != parameter) {
			formals.addFormalParameter(parameter);
		}
		
		final ObjectDeclaration self = new ObjectDeclaration("this", dateType_, null);
		formals.addFormalParameter(self);
		
		final StaticScope methodScope = new StaticScope(dateType_.enclosedScope());
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector,
				methodScope, dateType_, Public, null, false);
		methodDecl.addParameterList(formals);
		Type returnType = null;
		if (null != returnTypeName) {
			returnType = StaticScope.globalScope().lookupTypeDeclaration(returnTypeName);
			methodDecl.setReturnType(returnType);
		} else {
			returnType = StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(true);
		dateType_.enclosedScope().declareMethod(methodDecl, null);
	}
	public static void setup() {
		typeDeclarationList_ = new ArrayList<TypeDeclaration>();
		final StaticScope globalScope = StaticScope.globalScope();
		final Type integerType = StaticScope.globalScope().lookupTypeDeclaration("int");
		assert null != integerType;
		final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
		assert null != voidType;
		final Type stringType = StaticScope.globalScope().lookupTypeDeclaration("String");
		assert null != stringType;
		
		if (null == globalScope.lookupTypeDeclaration("Date")) {
			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;
			final ClassDeclaration dateDecl = new ClassDeclaration("Date", newScope, objectBaseClass, null);
			newScope.setDeclaration(dateDecl);
			dateType_ = new ClassType("Date", newScope, null);
			dateDecl.setType(dateType_);
			typeDeclarationList_.add(dateDecl);
			
			final AccessQualifier Public = AccessQualifier.PublicAccess;
			
			// constructor (with three arguments)
			FormalParameterList formals = new FormalParameterList();
			ObjectDeclaration self = new ObjectDeclaration("this", dateType_, null);
			ObjectDeclaration year = new ObjectDeclaration("year", integerType, null),
					         month = new ObjectDeclaration("month", integerType, null),
					          date = new ObjectDeclaration("date", integerType, null);
			
			formals.addFormalParameter(date);
			formals.addFormalParameter(month);
			formals.addFormalParameter(year);
			
			formals.addFormalParameter(self);
			
			StaticScope methodScope = new StaticScope(dateType_.enclosedScope());
			MethodDeclaration methodDecl = new MethodDeclaration("Date", methodScope, null, Public, null, false);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(null);
			methodDecl.setHasConstModifier(false);
			dateType_.enclosedScope().declareMethod(methodDecl, null);

			// constructor (with no arguments)
			formals = new FormalParameterList();
			self = new ObjectDeclaration("this", dateType_, null);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(dateType_.enclosedScope());
			methodDecl = new MethodDeclaration("Date", methodScope, null, Public, null, false);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(null);
			methodDecl.setHasConstModifier(false);
			dateType_.enclosedScope().declareMethod(methodDecl, null);
			
			singleSimpleFunctionSetup("getYear", null, "int");
			singleSimpleFunctionSetup("setYear", new ObjectDeclaration("year", integerType, null), null);
			singleSimpleFunctionSetup("getMonth", null, "int");
			singleSimpleFunctionSetup("setMonth", new ObjectDeclaration("month", integerType, null), null);
			singleSimpleFunctionSetup("getDate", null, "int");
			singleSimpleFunctionSetup("setDate", new ObjectDeclaration("date", integerType, null), null);
			singleSimpleFunctionSetup("getDay", null, "int");
			singleSimpleFunctionSetup("setDay", new ObjectDeclaration("day", integerType, null), null);
			singleSimpleFunctionSetup("compareTo", new ObjectDeclaration("other", dateType_, null), "int");
			
			formals = new FormalParameterList();
			self = new ObjectDeclaration("this", dateType_, null);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(dateType_.enclosedScope());
			methodDecl = new MethodDeclaration("toString", methodScope, stringType, Public, null, false);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(stringType);
			methodDecl.setHasConstModifier(true);
			dateType_.enclosedScope().declareMethod(methodDecl, null);
			
			// Declare the type
			globalScope.declareType(dateType_);
			globalScope.declareClass(dateDecl);
		}
	}
	
	public static class RTDateCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTDateCommon(final String className, final String methodName, final String parameterName, final String parameterTypeName,
				final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName, RTMessage.buildArguments(className, methodName,
					null == parameterName? null: asList(parameterName),
					null == parameterTypeName? null: asList(parameterTypeName),
					enclosingMethodScope, false), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
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
			final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTCode retval = this.runDetails(myEnclosedScope);
			
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails (Date domain)", "", "", "");
			return null;	// halt the machine
		}
	}
	public static class RTDateSimpleCtorCode extends RTDateCommon {
		public RTDateSimpleCtorCode(final StaticScope enclosingMethodScope) {
			super("Date", "Date", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			theDateObject.simpleCtor();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(this);
			return super.nextCode();
		}
	}
	public static class RTDateCtorCode extends RTDateCommon {
		public RTDateCtorCode(final StaticScope enclosingMethodScope) {
			super("Date", "Date", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTIntegerObject year = (RTIntegerObject)activationRecord.getObject("year");
			final RTIntegerObject month = (RTIntegerObject)activationRecord.getObject("month");
			final RTIntegerObject date = (RTIntegerObject)activationRecord.getObject("date");
			theDateObject.ctor(year, month, date);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(this);
			return super.nextCode();
		}
	}
	public static class RTGetYearCode extends RTDateCommon {
		public RTGetYearCode(final StaticScope enclosingMethodScope) {
			super("Date", "getYear", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject year = theDateObject.getYear();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", year);
			
			return super.nextCode();
		}
	}
	public static class RTSetYearCode extends RTDateCommon {
		public RTSetYearCode(final StaticScope enclosingMethodScope) {
			super("Date", "setYear", "year", "int", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawDateObject = activationRecord.getObject("this");
			assert rawDateObject instanceof RTDateObject;
			final RTDateObject theDateObject = (RTDateObject)rawDateObject;
			final RTObject rawYear = activationRecord.getObject("year");
			theDateObject.setYear(rawYear);
			return super.nextCode();
		}
	}
	public static class RTGetMonthCode extends RTDateCommon {
		public RTGetMonthCode(final StaticScope enclosingMethodScope) {
			super("Date", "getMonth", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject month = theDateObject.getMonth();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", month);
			
			return super.nextCode();
		}
	}
	public static class RTSetMonthCode extends RTDateCommon {
		public RTSetMonthCode(final StaticScope enclosingMethodScope) {
			super("Date", "setMonth", "month", "int", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject rawMonth = activationRecord.getObject("month");
			theDateObject.setMonth(rawMonth);
			return super.nextCode();
		}
	}
	public static class RTGetDateCode extends RTDateCommon {
		public RTGetDateCode(final StaticScope enclosingMethodScope) {
			super("Date", "getDate", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject date = theDateObject.getDate();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", date);
			
			return super.nextCode();
		}
	}
	public static class RTSetDateCode extends RTDateCommon {
		public RTSetDateCode(final StaticScope enclosingMethodScope) {
			super("Date", "setDate", "date", "int", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject rawDate = activationRecord.getObject("date");
			theDateObject.setDate(rawDate);
			return super.nextCode();
		}
	}
	public static class RTGetDayCode extends RTDateCommon {
		public RTGetDayCode(final StaticScope enclosingMethodScope) {
			super("Date", "getDay", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject day = theDateObject.getDay();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", day);
			
			return super.nextCode();
		}
	}
	public static class RTSetDayCode extends RTDateCommon {
		public RTSetDayCode(final StaticScope enclosingMethodScope) {
			super("Date", "setDay", "day", "int", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject rawDay = activationRecord.getObject("day");
			theDateObject.setDay(rawDay);
			return super.nextCode();
		}
	}
	public static class RTToStringCode extends RTDateCommon {
		public RTToStringCode(final StaticScope enclosingMethodScope) {
			super("Date", "toString", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject string = theDateObject.toStringCall();
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", string);
			
			return super.nextCode();
		}
	}
	public static class RTCompareToCode extends RTDateCommon {
		public RTCompareToCode(final StaticScope enclosingMethodScope) {
			super("Date", "compareTo", "other", "Date", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTDateObject theDateObject = (RTDateObject)activationRecord.getObject("this");
			final RTObject rawOther = activationRecord.getObject("other");
			assert rawOther instanceof RTDateObject || rawOther instanceof RTNullObject;
			
			int rawResult;
			if (rawOther instanceof RTDateObject) {
				final RTDateObject other = (RTDateObject) rawOther;
				rawResult = theDateObject.compareTo(other);
			} else {
				rawResult = -1;
			}
			
			final RTIntegerObject result = new RTIntegerObject(rawResult);

			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType dateType_;
}
