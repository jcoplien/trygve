package info.fulloo.trygve.add_ons;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.run_time.RTScannerObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

/*
 * Trygve IDE 4.0
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

public final class ScannerClass {
	public static void declareScannerMethod(final String methodSelector, final Type returnType,
			final String paramName,
			final Type paramType, final boolean isConst) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final FormalParameterList formals = new FormalParameterList();
		if (null != paramName) {
			final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, null);
			formals.addFormalParameter(formalParameter);
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", scannerType_, null);
		formals.addFormalParameter(self);
		StaticScope methodScope = new StaticScope(scannerType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, null, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		scannerType_.enclosedScope().declareMethod(methodDecl, null);
	}
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == globalScope.lookupTypeDeclaration("Scanner")) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final Type stringType = globalScope.lookupTypeDeclaration("String");
			assert null != stringType;
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("Scanner", newScope, objectBaseClass, null);
			newScope.setDeclaration(classDecl);
			scannerType_ = new ClassType("Scanner", newScope, null);
			classDecl.setType(scannerType_);
			typeDeclarationList_.add(classDecl);
			
			// method print(String)
			declareScannerMethod("nextLine", stringType, null, null, false);
			
			final Type inputStreamType = StaticScope.globalScope().lookupTypeDeclaration("InputStream");
			assert null != inputStreamType;
			ScannerClass.declareScannerMethod("Scanner", null, "stream", inputStreamType, false);
			
			globalScope.declareType(scannerType_);
			globalScope.declareClass(classDecl);
		}
	}
	
	public static class RTScannerCommon extends RTMessage {
		public RTScannerCommon(final String className, final String methodName, final String parameterName, String parameterTypeName,
				final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName, RTMessage.buildArguments(className, methodName, 
					null == parameterName? null: asList(parameterName),
					null == parameterTypeName? null: asList(parameterTypeName),
					enclosingMethodScope, false), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
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
			final RTScannerObject self = (RTScannerObject)myEnclosedScope.getObject("this");
			final Scanner theScanner = self.theScanner();	// may be null if we're just calling the constructor
			final RTCode retval = this.runDetails(myEnclosedScope, theScanner);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope, final Scanner theScanner) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
		protected void addRetvalTo(final RTDynamicScope activationRecord) {
			if (null == activationRecord.getObject("ret$val")) {
				activationRecord.addObjectDeclaration("ret$val", null);
			}
		}
	}
	public static class RTNextLineCode extends RTScannerCommon {
		public RTNextLineCode(final StaticScope enclosingMethodScope) {
			super("Scanner", "nextLine", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("String"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final Scanner theScanner) {
			assert null != theScanner;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final String sNextLine = theScanner.nextLine();
			final RTStringObject retval = new RTStringObject(sNextLine);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	public static class RTScannerCtor1Code extends RTScannerCommon {
		public RTScannerCtor1Code(final StaticScope enclosingMethodScope) {
			super("Scanner", "Scanner", "stream", "InputStream", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final Scanner theScanner) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTScannerObject theScannerObject = (RTScannerObject)activationRecord.getObject("this");
			final RTObject streamArg = (RTObject)activationRecord.getObject("stream");
			theScannerObject.ctor1(streamArg);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theScannerObject);
			return super.nextCode();
		}
	}

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType scannerType_;
}