package add_ons;

import java.util.ArrayList;
import java.util.List;

import run_time.RTCode;
import run_time.RTDynamicScope;
import run_time.RTListObject;
import run_time.RTObject;
import run_time.RTObjectCommon.RTBooleanObject;
import run_time.RTObjectCommon.RTIntegerObject;
import run_time.RTStackable;
import run_time.RunTimeEnvironment;
import run_time.RTExpression.RTMessage;
import semantic_analysis.StaticScope;
import declarations.Declaration.MethodDeclaration;
import declarations.Declaration.ObjectDeclaration;
import declarations.Declaration.TemplateDeclaration;
import declarations.AccessQualifier;
import declarations.ActualArgumentList;
import declarations.FormalParameterList;
import declarations.Type;
import declarations.Type.TemplateType;
import declarations.Type.TemplateParameterType;
import declarations.TypeDeclaration;
import expressions.Expression.IdentifierExpression;

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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

public final class ListClass {
	public static void setup() {
		typeDeclarationList_ = new ArrayList<TypeDeclaration>();
		final StaticScope globalScope = StaticScope.globalScope();
		final Type integerType = StaticScope.globalScope().lookupTypeDeclaration("int");
		assert null != integerType;
		final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
		assert null != voidType;
		final Type booleanType = StaticScope.globalScope().lookupTypeDeclaration("boolean");
		assert null != booleanType;
		
		if (null == globalScope.lookupTypeDeclaration("List")) {
			final StaticScope newScope = new StaticScope(globalScope);
			final TemplateDeclaration templateDecl = new TemplateDeclaration("List", newScope, /*Base Class*/ null, 0);
			newScope.setDeclaration(templateDecl);
			final Type T = new TemplateParameterType("T", null);
			final IdentifierExpression typeParamId = new IdentifierExpression("T", T, newScope);
			templateDecl.addTypeParameter(typeParamId, 1);
			listType_ = new TemplateType("List", newScope, null);
			templateDecl.setType(listType_);
			typeDeclarationList_.add(templateDecl);
			
			final AccessQualifier Public = AccessQualifier.PublicAccess;
			
			FormalParameterList formals = new FormalParameterList();
			ObjectDeclaration self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			StaticScope methodScope = new StaticScope(listType_.enclosedScope());
			MethodDeclaration methodDecl = new MethodDeclaration("List", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(listType_);
			methodDecl.signature().setHasConstModifier(false);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			formals = new FormalParameterList();
			ObjectDeclaration formalParameter = new ObjectDeclaration("element", T, 0);
			formals.addFormalParameter(formalParameter);
			self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(listType_.enclosedScope());
			methodDecl = new MethodDeclaration("add", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(voidType);
			methodDecl.signature().setHasConstModifier(false);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			formalParameter = new ObjectDeclaration("index", integerType, 0);
			formals = new FormalParameterList();
			formals.addFormalParameter(formalParameter);
			self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(listType_.enclosedScope());
			methodDecl = new MethodDeclaration("get", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(T);
			methodDecl.signature().setHasConstModifier(true);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			formalParameter = new ObjectDeclaration("element", T, 0);
			formals = new FormalParameterList();
			formals.addFormalParameter(formalParameter);
			self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(listType_.enclosedScope());
			methodDecl = new MethodDeclaration("indexOf", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(integerType);
			methodDecl.signature().setHasConstModifier(true);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			formalParameter = new ObjectDeclaration("element", T, 0);
			formals = new FormalParameterList();
			formals.addFormalParameter(formalParameter);
			self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(listType_.enclosedScope());
			methodDecl = new MethodDeclaration("contains", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(booleanType);
			methodDecl.signature().setHasConstModifier(true);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			formals = new FormalParameterList();
			self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(listType_.enclosedScope());
			methodDecl = new MethodDeclaration("size", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(integerType);
			methodDecl.signature().setHasConstModifier(true);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			formals = new FormalParameterList();
			self = new ObjectDeclaration("this", listType_, 0);
			formals.addFormalParameter(self);
			methodScope = new StaticScope(listType_.enclosedScope());
			methodDecl = new MethodDeclaration("isEmpty", methodScope, listType_, Public, 0);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(booleanType);
			methodDecl.signature().setHasConstModifier(true);
			listType_.enclosedScope().declareMethod(methodDecl);
			
			// Declare the type
			globalScope.declareType(listType_);
			globalScope.declareTemplate(templateDecl);
		}
	}
	
	public static class RTListCommon extends RTMessage {
		public RTListCommon(String className, String methodName, String parameterName, String parameterTypeName, StaticScope enclosingMethodScope, Type returnType) {
			super(methodName, RTListCommon.buildArguments(className, methodName, parameterTypeName, enclosingMethodScope), returnType);
			parameterName_ = parameterName;
		}
		private static ActualArgumentList buildArguments(String className, String methodName, String parameterTypeName, StaticScope enclosedMethodScope) {
			final Type stringType = StaticScope.globalScope().lookupTypeDeclaration(parameterTypeName);
			final ActualArgumentList argList = new ActualArgumentList();
			Type outType = StaticScope.globalScope().lookupTypeDeclaration(className);
			
			assert null != enclosedMethodScope;
			
			final IdentifierExpression toprint = new IdentifierExpression(methodName, stringType, enclosedMethodScope);
			argList.addActualArgument(toprint);
			final IdentifierExpression self = new IdentifierExpression("this", outType, enclosedMethodScope);
			argList.addActualArgument(self);
			return argList;
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
			this.runDetails(myEnclosedScope);
			
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return super.nextCode();
		}
		public void runDetails(RTObject scope) {
			// Effectively a pure virtual method, but Java screws us again...
			assert false;
		}
		
		protected String parameterName_;
	}
	public static class RTListCtorCode extends RTListCommon {
		public RTListCtorCode(StaticScope enclosingMethodScope) {
			super("List", "List", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			theListObject.ctor();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(this);
		}
	}
	public static class RTAddCode extends RTListCommon {
		public RTAddCode(StaticScope enclosingMethodScope) {
			super("List", "add", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTObject rawElement = activationRecord.getObject("element");
			theListObject.add(rawElement);
		}
	}
	public static class RTSizeCode extends RTListCommon {
		public RTSizeCode(StaticScope enclosingMethodScope) {
			super("List", "size", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final int rawResult = theListObject.size();
			final RTIntegerObject result = new RTIntegerObject(rawResult);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
		}
	}
	public static class RTGetCode extends RTListCommon {
		public RTGetCode(StaticScope enclosingMethodScope) {
			super("List", "get", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTIntegerObject argument = (RTIntegerObject)activationRecord.getObject("element");
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTStackable result = (RTStackable)theListObject.get((int)argument.intValue());
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
		}
	}
	public static class RTIndexOfCode extends RTListCommon {
		public RTIndexOfCode(StaticScope enclosingMethodScope) {
			super("List", "indexOf", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTStackable stackableArgument = activationRecord.getObject("element");
			final RTIntegerObject argument = (RTIntegerObject)stackableArgument;
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTStackable result = (RTStackable)theListObject.indexOf(argument);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
			System.err.println("Someone asked the indes of a list element");
		}
	}
	public static class RTContainsCode extends RTListCommon {
		public RTContainsCode(StaticScope enclosingMethodScope) {
			super("List", "contains", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject argument = activationRecord.getObject("element");
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTStackable result = (RTStackable)theListObject.contains(argument);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
		}
	}
	public static class RTIsEmptyCode extends RTListCommon {
		public RTIsEmptyCode(StaticScope enclosingMethodScope) {
			super("List", "isEmpty", "element", "T", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final boolean rawResult = theListObject.isEmpty();
			final RTBooleanObject result = new RTBooleanObject(rawResult);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
		}
	}

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static TemplateType listType_;
}
