package info.fulloo.trygve.add_ons;

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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;

import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.VarargsType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;   
import info.fulloo.trygve.run_time.RTClass.RTSystemClass.RTPrintStreamInfo;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTListObject;
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
	private static void declareSystemMethod(final String methodSelector,
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
					final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, null);
					formals.addFormalParameter(formalParameter);
				}
			}
		}
		final StaticScope methodScope = new StaticScope(systemType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, null, isStatic);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		systemType_.enclosedScope().declareMethod(methodDecl, null);
	}
	public static class RTExitCode extends RTMessage {
		public RTExitCode(final StaticScope enclosingMethodScope) {
			super("exit",
					RTMessage.buildArguments("System", "exit",
							asList("status"),
							asList("int"),
							new StaticScope(enclosingMethodScope),
							true),
					systemType_, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), true);
		}
		@Override public RTCode run() {
			RunTimeEnvironment runTimeEnvironment = RunTimeEnvironment.runTimeEnvironment_;
			runTimeEnvironment.stopAllThreads();
			return new RTHalt();
		}
	}
	private static void addTypedPrintStreamPrintDeclaration(final String methodName, final Type argumentType) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration formalParameter = null;
		final FormalParameterList formals = new FormalParameterList();
		if (null != argumentType) {
			 formalParameter = new ObjectDeclaration("toprint", argumentType, null);
			 formals.addFormalParameter(formalParameter);
		}
		formalParameter = new ObjectDeclaration("this", printStreamType_, null);
		formals.addFormalParameter(formalParameter);
		final StaticScope methodScope = new StaticScope(printStreamType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodName, methodScope, printStreamType_, Public, null, false);
		methodDecl.addParameterList(formals);
		methodDecl.setHasConstModifier(true);
		printStreamType_.enclosedScope().declareMethod(methodDecl, null);
		methodDecl.setReturnType(printStreamType_);
	}
	
	private static void addGeneralPrintStreamDeclaration(final String methodName, List<String> parameterNames, final List<Type> argumentTypes) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		ObjectDeclaration formalParameter = null;
		final FormalParameterList formals = new FormalParameterList();
		for (int i = 0; i < parameterNames.size(); i++) {
			final Type argumentType = argumentTypes.get(i);
			final String argumentName = parameterNames.get(i);
			formalParameter = new ObjectDeclaration(argumentName, argumentType, null);
			formals.addFormalParameter(formalParameter);
		}
		formalParameter = new ObjectDeclaration("this", printStreamType_, null);
		formals.addFormalParameter(formalParameter);
		final StaticScope methodScope = new StaticScope(printStreamType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodName, methodScope, printStreamType_, Public, null, false);
		methodDecl.addParameterList(formals);
		methodDecl.setHasConstModifier(true);
		printStreamType_.enclosedScope().declareMethod(methodDecl, null);
		methodDecl.setReturnType(printStreamType_);
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
			ClassDeclaration classDecl = new ClassDeclaration("PrintStream", newScope, objectBaseClass, null);
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
			
			// method (format)
			Type varargsType = new VarargsType("format");
			
			// Arguments are, alas, backwards
			addGeneralPrintStreamDeclaration("format", asList("arguments", "format"), asList(varargsType, stringType));
			
			// Declare the type
			globalScope.declareType(printStreamType_);
			globalScope.declareClass(classDecl);
			
			newScope = new StaticScope(globalScope);
			classDecl = new ClassDeclaration("System", newScope, objectBaseClass, null);
			newScope.setDeclaration(classDecl);
			systemType_ = new ClassType("System", newScope, null);
			classDecl.setType(systemType_);
			typeDeclarationList_.add(classDecl);
			
			declareSystemMethod("exit", null, asList("status"), asList(integerType), true, true);
			
			final ObjectDeclaration outDeclaration = new ObjectDeclaration("out", printStreamType_, null);
			systemType_.enclosedScope().declareStaticObject(outDeclaration);
			systemType_.declareStaticObject(outDeclaration);
			
			assert null != printStreamType_;
			final ObjectDeclaration errDeclaration = new ObjectDeclaration("err", printStreamType_, null);
			systemType_.enclosedScope().declareStaticObject(errDeclaration);
			systemType_.declareStaticObject(errDeclaration);
			
			// This code belongs here, but reciprocal precedence means that
			// we're stuck because System depends on InputStream and InputStream
			// depends on System. So we moved this into the InputStream initialization.
			// assert null != inputStreamType_;
			// final ObjectDeclaration inputDeclaration = new ObjectDeclaration("in", inputStreamType_, 0);
			// systemClassType.enclosedScope().declareStaticObject(inputDeclaration);
			// systemClassType.declareStaticObject(inputDeclaration);
			
			// Declare the type
			globalScope.declareType(systemType_);
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
			if (false == printStreamInfo instanceof RTPrintStreamInfo) {
				ErrorLogger.error(ErrorIncidenceType.Internal, token(), "Print empire (", this.methodSelectorName(),
						"): Internal Error with stack corruption so PrintStreamInfo is missing.", "");
				return new RTHalt();
			}
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
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails (System domain)", "", "", "");
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
				finalStream.println("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.println("<null>");
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
				finalStream.println("<null>");
			} else if (rawToPrint instanceof RTNullObject) {
				finalStream.println("<null>");
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
	
	public static class RTFormatCode extends RTPrintCommon {
		public RTFormatCode(final StaticScope enclosingMethodScope) {
			super("PrintStream", "format", "format", "arguments", enclosingMethodScope);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final PrintStream finalStream) {
			RTObject rawFormat = myEnclosedScope.getObject("format");
			RTObject rawArguments = myEnclosedScope.getObject("arguments");
			assert rawArguments instanceof RTListObject;
			RTListObject arguments = (RTListObject) rawArguments;
			boolean printedOK = true;

			if (rawFormat instanceof RTNullExpression) {
				finalStream.println("<null>");
			} else if (rawFormat instanceof RTNullObject) {
				finalStream.println("<null>");
			} else {
				assert rawFormat instanceof RTStringObject;
				final RTStringObject format = (RTStringObject) rawFormat;
				final String formatString = format.stringValue();
				final String [] formats = formatString.split("%");
				int j = arguments.size() - 1;
				
				// Maybe the format string start with just a
				// normal string
				int i = 0;
				if (false == formatString.substring(0,1).equals("%")) {
					finalStream.format(formats[0]);
					i++;
				}
				
				for (; i < formats.length; i++) {
					if (formats[i].length() == 0) {
						// %%stuff
						if (i + 1 < formats.length && formats[i+1].length() == 0) {
							// %%%sstuff
							// Don't print two "%" characters
							i++;
						}
					} else if (formats[i].startsWith("n")) {
						// takes no argument
						finalStream.format("%" + formats[i]);
					} else if (formats[i].startsWith("\n")) {
						// takes no argument
						finalStream.format("%" + formats[i]);
					} else {
						printedOK = printedOK && this.printObjectInFormatOn((RTObject)arguments.get(j), formats[i], finalStream);
						j--;
					}
				}
			}
			if (printedOK) {
				return super.nextCode();
			} else {
				return new RTHalt();
			}
		}
		private boolean formatSuitsType(final String formatSpec, final RTObject object, final boolean isOk) {
			if (false == isOk) {
				System.err.format("format: format %%%s is not compatible with object type %s%n",
						formatSpec, object.rTType().name());
			}
			return isOk;
		}
		private boolean printObjectInFormatOn(final RTObject object, final String formatString, final PrintStream str) {
			String formatSpec = formatString;
			boolean retval = true;
			
			// Strip off field length modifier
			while (formatSpec.length() > 0 &&
						(Character.isDigit((char)formatSpec.charAt(0)) ||
								formatSpec.startsWith("+") ||
								formatSpec.startsWith("-") ||
								formatSpec.startsWith(".") ||
								formatSpec.startsWith("^") ||
								formatSpec.startsWith("#")
						)
					) {
				formatSpec = formatSpec.substring(1);
			};

			try {
				if (object instanceof RTNullObject) {
					str.format("<null>");
				} else if (formatSpec.startsWith("s") || formatSpec.startsWith("S")) {
					if (object instanceof RTStringObject) {
						str.format("%" + formatString, ((RTStringObject)object).stringValue());
					} else {
						str.format("%" + formatString, object.toString());
					}
				} else if (formatSpec.startsWith("d") || formatString.startsWith("ld") ||
						   formatSpec.startsWith("x") || formatString.startsWith("X") ||
						   formatSpec.startsWith("o") || formatString.startsWith("u") ||
						   formatSpec.startsWith("z") || formatString.startsWith("Z") ) {
					if (formatSuitsType(formatSpec, object, object instanceof RTIntegerObject)) {
						str.format("%" + formatString, ((RTIntegerObject)object).intValue());
					}
				} else if (formatSpec.startsWith("f") || formatString.startsWith("lf") ||
						formatSpec.startsWith("e") || formatString.startsWith("E") ||
						formatSpec.startsWith("g") || formatString.startsWith("G")) {
					if (formatSuitsType(formatSpec, object, object instanceof RTDoubleObject)) {
						str.format("%" + formatString, ((RTDoubleObject)object).doubleValue());
					}
				} else if (formatSpec.startsWith("p")) {
					str.format("%" + formatString, object);
				} else if (formatSpec.startsWith("b")) {
					if (formatSuitsType(formatSpec, object, object instanceof RTBooleanObject)) {
						str.format("%" + formatString, ((RTBooleanObject)object).value());
					}
				}
			} catch (final IllegalFormatException e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, "Invalid format specification: ", "`%", formatString, "'.");
				retval = false;
			}
			return retval;
		}
	}
	
	private static Type printStreamType_ = null, systemType_ = null;
}
