package info.fulloo.trygve.add_ons;

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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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
import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTClass.RTSystemClass.RTInputStreamInfo;
import info.fulloo.trygve.run_time.RTClass.RTSystemClass.RTPrintStreamInfo;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

public final class SystemClass {
	private static void addTypedPrintStreamPrintDeclaration(final String methodName, final Type argumentType) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration formalParameter = null;
		final FormalParameterList formals = new FormalParameterList();
		if (null != argumentType) {
			 formalParameter = new ObjectDeclaration("toprint", argumentType, 0);
			 formals.addFormalParameter(formalParameter);
		}
		formalParameter = new ObjectDeclaration("this", printStreamType_, 0);
		formals.addFormalParameter(formalParameter);
		final StaticScope methodScope = new StaticScope(printStreamType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodName, methodScope, printStreamType_, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setHasConstModifier(true);
		printStreamType_.enclosedScope().declareMethod(methodDecl);
		methodDecl.setReturnType(printStreamType_);
	}
	private static void addTypedInputStreamDeclaration(final String methodName, final Type argumentType) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration formalParameter = null;
		final FormalParameterList formals = new FormalParameterList();
		formalParameter = new ObjectDeclaration("this", inputStreamType_, 0);
		formals.addFormalParameter(formalParameter);
		final StaticScope methodScope = new StaticScope(inputStreamType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodName, methodScope, inputStreamType_, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setHasConstModifier(true);
		inputStreamType_.enclosedScope().declareMethod(methodDecl);
		final StaticScope globalScope = StaticScope.globalScope();
		final Type integerType = globalScope.lookupTypeDeclaration("int");
		methodDecl.setReturnType(integerType);
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
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			StaticScope newScope = new StaticScope(globalScope);
			ClassDeclaration classDecl = new ClassDeclaration("PrintStream", newScope, objectBaseClass, 0);
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
			
			// method println()
			addTypedPrintStreamPrintDeclaration("println", null);
			
			// Declare the type
			globalScope.declareType(printStreamType_);
			globalScope.declareClass(classDecl);
			
			newScope = new StaticScope(globalScope);
			classDecl = new ClassDeclaration("InputStream", newScope, objectBaseClass, 0);
			newScope.setDeclaration(classDecl);
			inputStreamType_ = new ClassType("InputStream", newScope, null);
			classDecl.setType(inputStreamType_);
			typeDeclarationList_.add(classDecl);
			
			// method read()
			addTypedInputStreamDeclaration("read", null);
			
			// Declare the type
			globalScope.declareType(inputStreamType_);
			globalScope.declareClass(classDecl);
			
			newScope = new StaticScope(globalScope);
			classDecl = new ClassDeclaration("System", newScope, objectBaseClass, 0);
			newScope.setDeclaration(classDecl);
			final Type systemClassType = new ClassType("System", newScope, null);
			classDecl.setType(systemClassType);
			typeDeclarationList_.add(classDecl);
			
			final ObjectDeclaration outDeclaration = new ObjectDeclaration("out", printStreamType_, 0);
			systemClassType.enclosedScope().declareStaticObject(outDeclaration);
			systemClassType.declareStaticObject(outDeclaration);
			
			final ObjectDeclaration errDeclaration = new ObjectDeclaration("err", printStreamType_, 0);
			systemClassType.enclosedScope().declareStaticObject(errDeclaration);
			systemClassType.declareStaticObject(errDeclaration);
			
			final ObjectDeclaration inputDeclaration = new ObjectDeclaration("in", inputStreamType_, 0);
			systemClassType.enclosedScope().declareStaticObject(inputDeclaration);
			systemClassType.declareStaticObject(inputDeclaration);
			
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
			super(methodName,
					RTMessage.buildArguments(className, methodName,
							null == parameterName?     null: asList(parameterName),
							null == parameterTypeName? null: asList(parameterTypeName),
							enclosingMethodScope, false),
					printStreamType_, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
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
			
			final RTObject theStream = myEnclosedScope.getObject("this");
			final RTObject printStreamInfo = theStream.getObject("printStreamInfo");
			assert printStreamInfo instanceof RTPrintStreamInfo;
			final PrintStream finalStream = ((RTPrintStreamInfo)printStreamInfo).printStream();
			
			final RTCode nextPC = this.runDetails(myEnclosedScope, finalStream);
			
			// We DO push a return value, which is just "this"
			// It is always returned. It is up to the RTReturn / RTMessage /
			// RTPostReturnProcessing logic to deal with consumption.
			
			final RTObject self = myEnclosedScope.getObject("this");
			assert null != self;
			RunTimeEnvironment.runTimeEnvironment_.pushStack(self);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return nextPC;
		}
		public RTCode runDetails(final RTObject scope, final PrintStream finalStream) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails (System domain)", "", "", "");
			return null;	// halt the machine
		}
		
		protected String parameterName_;	// used? FIXME
	}
	
	public static class RTPrintlnStringCode extends RTPrintCommon {
		public RTPrintlnStringCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "String", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			
			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				assert rawToPrint instanceof RTStringObject;
				final RTStringObject toPrint = (RTStringObject) rawToPrint;
				final String foobar = toPrint.stringValue();
				finalStream.println(foobar);
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
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			
			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				assert rawToPrint instanceof RTIntegerObject;
				final RTIntegerObject toPrint = (RTIntegerObject) rawToPrint;
				final long foobar = toPrint.intValue();
				finalStream.println(foobar);
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
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			
			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				assert rawToPrint instanceof RTBooleanObject;
				final RTBooleanObject toPrint = (RTBooleanObject) rawToPrint;
				final boolean foobar = toPrint.value();
				finalStream.println(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintlnDoubleCode extends RTPrintCommon {
		public RTPrintlnDoubleCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", "toprint", "double", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);
			
			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				assert rawToPrint instanceof RTDoubleObject;
				final RTDoubleObject toPrint = (RTDoubleObject) rawToPrint;
				final double foobar = toPrint.doubleValue();
				finalStream.println(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintlnCode extends RTPrintCommon {
		public RTPrintlnCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "println", null, null, enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			finalStream.println();
			return super.nextCode();
		}
	}
	
	public static class RTPrintStringCode extends RTPrintCommon {
		public RTPrintStringCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "String", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);

			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				assert rawToPrint instanceof RTStringObject;
				final RTStringObject toPrint = (RTStringObject) rawToPrint;
				final String foobar = toPrint.stringValue();
				finalStream.print(foobar);
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
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);

			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				final RTIntegerObject toPrint = (RTIntegerObject) rawToPrint;
				assert rawToPrint instanceof RTIntegerObject;
				final long foobar = toPrint.intValue();
				finalStream.print(foobar);
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
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			final RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);

			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				assert rawToPrint instanceof RTBooleanObject;
				final RTBooleanObject toPrint = (RTBooleanObject)rawToPrint;
				final boolean foobar = toPrint.value();
				finalStream.print(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTPrintDoubleCode extends RTPrintCommon {
		public RTPrintDoubleCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "print", "toprint", "double", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			RTObject rawToPrint = myEnclosedScope.getObject(parameterName_);

			if (rawToPrint instanceof RTNullExpression) {
				finalStream.print("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.print("<null>");
			} else {
				if (rawToPrint instanceof RTIntegerObject) {
					// Yeah, it can happen. chord_identifier3.k
					rawToPrint = new RTDoubleObject(((RTIntegerObject)rawToPrint).intValue());
				}
				assert rawToPrint instanceof RTDoubleObject;
				final RTDoubleObject toPrint = (RTDoubleObject) rawToPrint;
				final double foobar = toPrint.doubleValue();
				finalStream.print(foobar);
			}
			return super.nextCode();
		}
	}
	public static class RTReadCommon extends RTMessage {
		public RTReadCommon(final String className, final String methodName, final String parameterName, final String parameterTypeName, final StaticScope enclosingMethodScope) {
			super(methodName,
					RTMessage.buildArguments(className, methodName,
							null == parameterName?     null: asList(parameterName),
							null == parameterTypeName? null: asList(parameterTypeName),
							enclosingMethodScope, false),
					inputStreamType_, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
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
			
			final RTObject theStream = myEnclosedScope.getObject("this");
			final RTObject inputStreamInfo = theStream.getObject("inputStreamInfo");
			assert inputStreamInfo instanceof RTInputStreamInfo;
			final InputStream finalStream = ((RTInputStreamInfo)inputStreamInfo).inputStream();
			
			final RTCode nextPC = this.runDetails(myEnclosedScope, finalStream);
			
			// We DO push a return value, which is just "this"
			// It is always returned. It is up to the RTReturn / RTMessage /
			// RTPostReturnProcessing logic to deal with consumption.
			
			final RTObject self = myEnclosedScope.getObject("this");
			assert null != self;
			RunTimeEnvironment.runTimeEnvironment_.pushStack(self);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return nextPC;
		}
		public RTCode runDetails(final RTObject scope, final InputStream finalStream) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails (System domain)", "", "", "");
			return null;	// halt the machine
		}
		protected void addRetvalTo(final RTDynamicScope activationRecord) {
			if (null == activationRecord.getObject("ret$val")) {
				activationRecord.addObjectDeclaration("ret$val", null);
			}
		}
		
		protected String parameterName_;	// used? FIXME
	}
	public static class RTReadCode extends RTReadCommon {
		public RTReadCode(final StaticScope enclosingMethodScope) {
			super("InputStream", "read", null, null, enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final InputStream finalStream) {
			RTObject retval = null;
			try {
				final int theInput = finalStream.read();
				retval = new RTIntegerObject(theInput);
			} catch (IOException expection) {
				retval = new RTIntegerObject(-1);
			}

			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	
	private static Type printStreamType_ = null;
	private static Type inputStreamType_ = null;
}
