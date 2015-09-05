package add_ons;

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

import java.util.ArrayList;
import java.util.List;

import semantic_analysis.StaticScope;
import declarations.ActualArgumentList;
import declarations.Declaration.ClassDeclaration;
import declarations.FormalParameterList;
import declarations.Type;
import declarations.Type.ClassType;
import declarations.TypeDeclaration;
import declarations.Declaration.MethodDeclaration;
import declarations.Declaration.ObjectDeclaration;
import declarations.AccessQualifier;
import expressions.Expression.IdentifierExpression;
import run_time.RTCode;
import run_time.RTExpression.RTMessage;
import run_time.RTObject;
import run_time.RTObjectCommon.RTBooleanObject;
import run_time.RTObjectCommon.RTDoubleObject;
import run_time.RTObjectCommon.RTIntegerObject;
import run_time.RTObjectCommon.RTStringObject;
import run_time.RunTimeEnvironment;

public class SystemClass {
	public static void addTypedPrintStreamPrintDeclaration(String methodName, Type argumentType) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration formalParameter = new ObjectDeclaration("toprint", argumentType, 0);
		final FormalParameterList formals = new FormalParameterList();
		formals.addFormalParameter(formalParameter);
		formalParameter = new ObjectDeclaration("this", printStreamType_, 0);
		formals.addFormalParameter(formalParameter);
		StaticScope methodScope = new StaticScope(printStreamType_.enclosedScope());
		MethodDeclaration methodDecl = new MethodDeclaration(methodName, methodScope, printStreamType_, Public, 0);
		methodDecl.addParameterList(formals);
		methodDecl.signature().setHasConstModifier(true);
		printStreamType_.enclosedScope().declareMethod(methodDecl);
	}
	public static void setup() {
		typeDeclarationList_ = new ArrayList<TypeDeclaration>();
		
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == globalScope.lookupTypeDeclaration("System")) {
			final Type stringType = globalScope.lookupTypeDeclaration("String");
			final Type integerType = globalScope.lookupTypeDeclaration("int");
			final Type bigIntegerType = globalScope.lookupTypeDeclaration("Integer");
			final Type booleanType = globalScope.lookupTypeDeclaration("boolean");
			final Type doubleType = globalScope.lookupTypeDeclaration("double");

			StaticScope newScope = new StaticScope(globalScope);
			ClassDeclaration classDecl = new ClassDeclaration("PrintStream", newScope, /*Base Class*/ null, 0);
			newScope.setDeclaration(classDecl);
			printStreamType_ = new ClassType("PrintStream", newScope, null);
			classDecl.setType(printStreamType_);
			typeDeclarationList_.add(classDecl);
			
			// method print(String)
			addTypedPrintStreamPrintDeclaration("print", stringType);
			
			// method print(int)
			addTypedPrintStreamPrintDeclaration("print", integerType);

			// method print(Integer)
			addTypedPrintStreamPrintDeclaration("print", bigIntegerType);
			
			// method print(boolean)
			addTypedPrintStreamPrintDeclaration("print", booleanType);
						
			// method print(double)
			addTypedPrintStreamPrintDeclaration("print", doubleType);
			
			// method println(String)
			addTypedPrintStreamPrintDeclaration("println", stringType);
						
			// method println(int)
			addTypedPrintStreamPrintDeclaration("println", integerType);
			
			// method println(Integer)
			addTypedPrintStreamPrintDeclaration("println", bigIntegerType);
			
			// method println(boolean)
			addTypedPrintStreamPrintDeclaration("println", booleanType);

			// method println(double)
			addTypedPrintStreamPrintDeclaration("println", doubleType);
			
			// Declare the type
			globalScope.declareType(printStreamType_);
			globalScope.declareClass(classDecl);
			
			newScope = new StaticScope(globalScope);
			classDecl = new ClassDeclaration("System", newScope, /*Base Class*/ null, 0);
			newScope.setDeclaration(classDecl);
			final Type systemClassType = new ClassType("System", newScope, null);
			classDecl.setType(systemClassType);
			typeDeclarationList_.add(classDecl);
			
			final ObjectDeclaration outDeclaration = new ObjectDeclaration("out", printStreamType_, 0);
			systemClassType.enclosedScope().declareStaticObject(outDeclaration);
			systemClassType.declareStaticObject(outDeclaration);
			
			// Declare the type
			globalScope.declareType(systemClassType);
			globalScope.declareClass(classDecl);
		}
	}
	
	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	
	public static class RTPrintCommon extends RTMessage {
		public RTPrintCommon(String className, String methodName, String parameterName, String parameterTypeName, StaticScope enclosingMethodScope) {
			super("println", RTPrintCommon.buildArguments(className, methodName, parameterTypeName, enclosingMethodScope), printStreamType_);
			parameterName_ = parameterName;
		}
		private static ActualArgumentList buildArguments(String className, String methodName, String parameterTypeName, StaticScope enclosedMethodScope) {
			final Type stringType = StaticScope.globalScope().lookupTypeDeclaration(parameterTypeName);
			final ActualArgumentList argList = new ActualArgumentList();
			Type outType = StaticScope.globalScope().lookupTypeDeclaration(className);
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
			
			// We DO push a return value, which is just "this"
			// It is always returned. It is up to the RTMessage
			// logic to deal with consumption.
			
			final RTObject self = myEnclosedScope.getObject("this");
			assert null != self;
			RunTimeEnvironment.runTimeEnvironment_.pushStack(self);
			
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
	
	public static class RTPrintlnStringCode extends RTPrintCommon {
		public RTPrintlnStringCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "String", enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTStringObject toPrint = (RTStringObject) rawToPrint;
				assert toPrint instanceof RTStringObject;
				final String foobar = toPrint.stringValue();
				System.out.println(foobar);
			}
		}
	}
	public static class RTPrintlnIntegerCode extends RTPrintCommon {
		public RTPrintlnIntegerCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "int", enclosingMethodScope);
		}
		protected RTPrintlnIntegerCode(String typeName, StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", typeName, enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTIntegerObject toPrint = (RTIntegerObject) rawToPrint;
				if (toPrint instanceof RTIntegerObject == false) {
					assert toPrint instanceof RTIntegerObject;
				}
				final long foobar = toPrint.intValue();
				System.out.println(foobar);
			}
		}
	}
	public static class RTPrintlnBigIntegerCode extends RTPrintIntegerCode  {
		public RTPrintlnBigIntegerCode(StaticScope enclosingMethodScope) {
			super("Integer", enclosingMethodScope);
		}
	}
	public static class RTPrintlnBooleanCode extends RTPrintCommon {
		public RTPrintlnBooleanCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "boolean", enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTBooleanObject toPrint = (RTBooleanObject) rawToPrint;
				assert toPrint instanceof RTBooleanObject;
				final boolean foobar = toPrint.value();
				System.out.println(foobar);
			}
		}
	}
	public static class RTPrintlnDoubleCode extends RTPrintCommon {
		public RTPrintlnDoubleCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "double", enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTDoubleObject toPrint = (RTDoubleObject) rawToPrint;
				assert toPrint instanceof RTDoubleObject;
				final double foobar = toPrint.doubleValue();
				System.out.println(foobar);
			}
		}
	}
	
	public static class RTPrintStringCode extends RTPrintCommon {
		public RTPrintStringCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "String", enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTStringObject toPrint = (RTStringObject) rawToPrint;
				assert toPrint instanceof RTStringObject;
				final String foobar = toPrint.stringValue();
				System.out.print(foobar);
			}
		}
	}
	public static class RTPrintIntegerCode extends RTPrintCommon {
		public RTPrintIntegerCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "int", enclosingMethodScope);
		}
		protected RTPrintIntegerCode(String typeName, StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", typeName, enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTIntegerObject toPrint = (RTIntegerObject) rawToPrint;
				assert toPrint instanceof RTIntegerObject;
				final long foobar = toPrint.intValue();
				System.out.print(foobar);
			}
		}
	}
	public static class RTPrintBigIntegerCode extends RTPrintIntegerCode  {
		public RTPrintBigIntegerCode(StaticScope enclosingMethodScope) {
			super("Integer", enclosingMethodScope);
		}
	}
	public static class RTPrintBooleanCode extends RTPrintCommon {
		public RTPrintBooleanCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "boolean", enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTBooleanObject toPrint = (RTBooleanObject)rawToPrint;
				assert toPrint instanceof RTBooleanObject;
				final boolean foobar = toPrint.value();
				System.out.print(foobar);
			}
		}
	}
	public static class RTPrintDoubleCode extends RTPrintCommon {
		public RTPrintDoubleCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "double", enclosingMethodScope);
		}
		@Override public void runDetails(RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTDoubleObject toPrint = (RTDoubleObject) rawToPrint;
				assert toPrint instanceof RTDoubleObject;
				final double foobar = toPrint.doubleValue();
				System.out.print(foobar);
			}
		}
	}
	
	private static Type printStreamType_ = null;
}
