package info.fulloo.trygve.add_ons;

import java.util.ArrayList;
import java.util.List;

import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTSetObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

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

public final class SetClass {
	private static void declareSetMethod(final String methodSelector, final Type returnType,
			final String paramName,
			final Type paramType, final boolean isConst) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final FormalParameterList formals = new FormalParameterList();
		if (null != paramName) {
			final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
			formals.addFormalParameter(formalParameter);
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", listType_, 0);
		formals.addFormalParameter(self);
		StaticScope methodScope = new StaticScope(listType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		listType_.enclosedScope().declareMethod(methodDecl);
	}
	public static void setup() {
		typeDeclarationList_ = new ArrayList<TypeDeclaration>();
		final StaticScope globalScope = StaticScope.globalScope();
		final Type integerType = StaticScope.globalScope().lookupTypeDeclaration("int");
		assert null != integerType;
		final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
		assert null != voidType;
		final Type booleanType = StaticScope.globalScope().lookupTypeDeclaration("boolean");
		assert null != booleanType;
		
		if (null == globalScope.lookupTypeDeclaration("Set")) {
			final StaticScope newScope = new StaticScope(globalScope);
			final TemplateDeclaration templateDecl = new TemplateDeclaration("Set", newScope, /*Base Class*/ null, 0);
			newScope.setDeclaration(templateDecl);
			final Type T = new TemplateParameterType("T", null);
			final IdentifierExpression typeParamId = new IdentifierExpression("T", T, newScope, 0);
			templateDecl.addTypeParameter(typeParamId, 1);
			listType_ = new TemplateType("Set", newScope, null);
			templateDecl.setType(listType_);
			typeDeclarationList_.add(templateDecl);
			
			final Type intType = globalScope.lookupTypeDeclaration("int");
			
			declareSetMethod("Set", listType_, null, null, false);
			
			declareSetMethod("add", voidType, "element", T, false);
			
			declareSetMethod("remove", booleanType, "element", T, false);
			
			declareSetMethod("contains", booleanType, "element", T, true);
			
			declareSetMethod("size", intType, null, null, true);
			
			declareSetMethod("isEmpty", booleanType, null, null, true);
			
			// Declare the type
			globalScope.declareType(listType_);
			globalScope.declareTemplate(templateDecl);
		}
	}
	
	public static class RTSetCommon extends RTMessage {
		public RTSetCommon(final String className, final String methodName, final String parameterName, String parameterTypeName,
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
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
		protected void addRetvalTo(final RTDynamicScope activationRecord) {
			if (null == activationRecord.getObject("ret$val")) {
				activationRecord.addObjectDeclaration("ret$val", null);
			}
		}
	}
	public static class RTSetCtorCode extends RTSetCommon {
		public RTSetCtorCode(final StaticScope enclosingMethodScope) {
			super("Set", "Set", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTSetObject theListObject = (RTSetObject)activationRecord.getObject("this");
			theListObject.ctor();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(this);
			return super.nextCode();
		}
	}
	public static class RTAddCode extends RTSetCommon {
		public RTAddCode(final StaticScope enclosingMethodScope) {
			super("Set", "add", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTSetObject theListObject = (RTSetObject)activationRecord.getObject("this");
			final RTObject rawElement = activationRecord.getObject("element");
			theListObject.add(rawElement);
			return super.nextCode();
		}
	}
	public static class RTRemoveTCode extends RTSetCommon {
		public RTRemoveTCode(final StaticScope enclosingMethodScope) {
			super("Set", "remove", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTSetObject theListObject = (RTSetObject)activationRecord.getObject("this");
			final RTObject rawElement = activationRecord.getObject("element");

			// following method decrements reference count 
			final boolean bResult = theListObject.remove(rawElement);
			final RTObject result = new RTBooleanObject(bResult);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	public static class RTSizeCode extends RTSetCommon {
		public RTSizeCode(final StaticScope enclosingMethodScope) {
			super("Set", "size", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTSetObject theListObject = (RTSetObject)activationRecord.getObject("this");
			final int rawResult = theListObject.size();
			final RTIntegerObject result = new RTIntegerObject(rawResult);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	
	public static class RTContainsCode extends RTSetCommon {
		public RTContainsCode(final StaticScope enclosingMethodScope) {
			super("Set", "contains", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject argument = activationRecord.getObject("element");
			final RTSetObject theListObject = (RTSetObject)activationRecord.getObject("this");
			final RTObject result = (RTObject)theListObject.contains(argument);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	public static class RTIsEmptyCode extends RTSetCommon {
		public RTIsEmptyCode(final StaticScope enclosingMethodScope) {
			super("Set", "isEmpty", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTSetObject theListObject = (RTSetObject)activationRecord.getObject("this");
			final boolean rawResult = theListObject.isEmpty();
			final RTBooleanObject result = new RTBooleanObject(rawResult);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static TemplateType listType_;
}