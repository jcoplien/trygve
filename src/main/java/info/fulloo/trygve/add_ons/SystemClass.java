package info.fulloo.trygve.add_ons;

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

import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public final class SystemClass {
	private static void addTypedPrintStreamPrintDeclaration(String methodName, Type argumentType) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration formalParameter = new ObjectDeclaration("toprint", argumentType, 0);
		final FormalParameterList formals = new FormalParameterList();
		formals.addFormalParameter(formalParameter);
		formalParameter = new ObjectDeclaration("this", printStreamType_, 0);
		formals.addFormalParameter(formalParameter);
		final StaticScope methodScope = new StaticScope(printStreamType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodName, methodScope, printStreamType_, Public, 0, false);
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
		public RTPrintCommon(final String className, final String methodName, final String parameterName, final String parameterTypeName, final StaticScope enclosingMethodScope) {
			super("println", RTPrintCommon.buildArguments(className, methodName, parameterName, parameterTypeName, enclosingMethodScope, false), printStreamType_, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
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
			final RTCode retval = this.runDetails(myEnclosedScope);
			
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
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails (System domain)", "", "", "");
			return null;	// halt the machine
		}
		
		protected String parameterName_;
	}
	
	public static class RTPrintlnStringCode extends RTPrintCommon {
		public RTPrintlnStringCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "String", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTStringObject toPrint = (RTStringObject) rawToPrint;
				assert toPrint instanceof RTStringObject;
				final String foobar = toPrint.stringValue();
				System.out.println(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintlnIntegerCode extends RTPrintCommon {
		public RTPrintlnIntegerCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "int", enclosingMethodScope);
		}
		protected RTPrintlnIntegerCode(final String typeName, final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", typeName, enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
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
			return super.nextCode();
		}
	}
	public static class RTPrintlnBigIntegerCode extends RTPrintIntegerCode  {
		public RTPrintlnBigIntegerCode(final StaticScope enclosingMethodScope) {
			super("Integer", enclosingMethodScope);
		}
	}
	public static class RTPrintlnBooleanCode extends RTPrintCommon {
		public RTPrintlnBooleanCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "boolean", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTBooleanObject toPrint = (RTBooleanObject) rawToPrint;
				assert toPrint instanceof RTBooleanObject;
				final boolean foobar = toPrint.value();
				System.out.println(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintlnDoubleCode extends RTPrintCommon {
		public RTPrintlnDoubleCode(StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "double", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTDoubleObject toPrint = (RTDoubleObject) rawToPrint;
				assert toPrint instanceof RTDoubleObject;
				final double foobar = toPrint.doubleValue();
				System.out.println(foobar);
			}
			return super.nextCode();
		}
	}
	
	public static class RTPrintStringCode extends RTPrintCommon {
		public RTPrintStringCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "String", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTStringObject toPrint = (RTStringObject) rawToPrint;
				assert toPrint instanceof RTStringObject;
				final String foobar = toPrint.stringValue();
				System.out.print(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintIntegerCode extends RTPrintCommon {
		public RTPrintIntegerCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "int", enclosingMethodScope);
		}
		protected RTPrintIntegerCode(final String typeName, final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", typeName, enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTIntegerObject toPrint = (RTIntegerObject) rawToPrint;
				assert toPrint instanceof RTIntegerObject;
				final long foobar = toPrint.intValue();
				System.out.print(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintBigIntegerCode extends RTPrintIntegerCode  {
		public RTPrintBigIntegerCode(final StaticScope enclosingMethodScope) {
			super("Integer", enclosingMethodScope);
		}
	}
	public static class RTPrintBooleanCode extends RTPrintCommon {
		public RTPrintBooleanCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "boolean", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTBooleanObject toPrint = (RTBooleanObject)rawToPrint;
				assert toPrint instanceof RTBooleanObject;
				final boolean foobar = toPrint.value();
				System.out.print(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintDoubleCode extends RTPrintCommon {
		public RTPrintDoubleCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "double", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			if (rawToPrint instanceof RTNullExpression) {
				System.out.print("<null>");
			} else {
				final RTDoubleObject toPrint = (RTDoubleObject) rawToPrint;
				assert toPrint instanceof RTDoubleObject;
				final double foobar = toPrint.doubleValue();
				System.out.print(foobar);
			}
			return super.nextCode();
		}
	}
	
	private static Type printStreamType_ = null;
}
