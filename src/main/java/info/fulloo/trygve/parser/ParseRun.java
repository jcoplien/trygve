package info.fulloo.trygve.parser;

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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import info.fulloo.trygve.configuration.ConfigurationOptions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import info.fulloo.trygve.code_generation.CodeGenerator;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.editor.BatchRunner;
import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTClassAndContextCommon;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTContext;
import info.fulloo.trygve.run_time.RTDebuggerWindow;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RTExpression.RTConstant;
import info.fulloo.trygve.run_time.RTMethod;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.Program;


public class ParseRun {
	final static String grammarName = "Kant";
	final static String startRuleName = "program";
	final String input_;
	Class<? extends Parser> parserClass_ = null;
	final ParsingData parsingData_;
	
	public ParseRun(final String input)
	{
        parsingData_ = new ParsingData();
        parserClass_ = KantParser.class;
        ErrorLogger.resetCounts();
        input_ = input;
	}
	
	// http://stackoverflow.com/questions/18132078/handling-errors-in-antlr4
	public static class DescriptiveErrorListener extends BaseErrorListener {
	    public static DescriptiveErrorListener INSTANCE = new DescriptiveErrorListener();
	    
	    private boolean REPORT_SYNTAX_ERRORS = true;
	    
	    @Override
	    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
	                            int line, int charPositionInLine,
	                            String msg, RecognitionException e)
	    {
	        if (!REPORT_SYNTAX_ERRORS) {
	            return;
	        }

	        String sourceName = recognizer.getInputStream().getSourceName();
	        if (!sourceName.isEmpty()) {
	            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
	        }

	        final CommonToken errorToken = new CommonToken(0);
	        errorToken.setLine(line);
	        errorToken.setCharPositionInLine(charPositionInLine);
	        ErrorLogger.error(ErrorIncidenceType.Parse, errorToken, "column ", Integer.toString(charPositionInLine), ": ",  msg);
	    }
	}
	
	private void setupParseErrorReportingFor(final KantLexer lexer, final KantParser parser) {
		lexer.removeErrorListeners();
		lexer.addErrorListener(DescriptiveErrorListener.INSTANCE);
		parser.removeErrorListeners();
		parser.addErrorListener(DescriptiveErrorListener.INSTANCE);
	}
	
	protected void commonInit() {
    	final ANTLRInputStream inputStream = new ANTLRInputStream(input_);
    	final KantLexer lexer = new KantLexer(inputStream);
    	final CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
    	final KantParser aParser = new KantParser(commonTokenStream);
    	
    	setupParseErrorReportingFor(lexer, aParser);
    
    	try {
    		final Method startRule = parserClass_.getMethod(startRuleName);
    		final ParserRuleContext tree = (ParserRuleContext)startRule.invoke(aParser, (Object[])null);
    		
    		if (ConfigurationOptions.treewalkTraceEnabled()) {
    			ParseTreeWalker.DEFAULT.walk(new DebugPassListener(), tree);
    		}
    		this.pass0(parsingData_, tree);
    		this.pass1(parsingData_, tree);
    		this.pass2(parsingData_, tree);
    		this.pass3(parsingData_, tree);
    		
    		// Pass 4 mainly does template instantiations
    		this.pass4(parsingData_, tree);
    	}
    	catch (final NoSuchMethodException nsme) {
    		System.err.println("No method for rule "+startRuleName+" or it has arguments");
    	}
    	catch (final InvocationTargetException ite) {
    		System.err.println("InvocationTargetException");
    	}
    	catch (final IllegalAccessException iae) {
    		System.err.println("IllegalAccessException");
    	}
    	
        allExpressions_ = new HashMap<Integer, RTCode>();
	}

	private void pass0(final ParsingData parsingData, final ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass0Listener(parsingData), tree);
	}
	
	private void pass1(final ParsingData parsingData, final ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass1Listener(parsingData), tree);
	}

	private void pass2(final ParsingData parsingData, final ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass2Listener(parsingData), tree);
	}

	private void pass3(final ParsingData parsingData, final ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass3Listener(parsingData), tree);
	}
	
	private void pass4(final ParsingData parsingData, final ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass4Listener(parsingData), tree);
	}
	
	private void buildExpressionMap() {
		if (null != mainExpr_) {
        	buildExpressionMapFor(mainExpr_);
        }
        
        // All classes
        final Collection<RTClass> allClasses = RunTimeEnvironment.runTimeEnvironment_.allRTClasses();
        for (final RTClass aClassDecl: allClasses) {
        	final Collection<RTMethod> allRTMethods = aClassDecl.allRTMethods();

        	for (final RTCode aMethod: allRTMethods) {
        		buildExpressionMapFor(aMethod);
        	}
        }
        
        // Aaaaand all Contexts
        final Collection<RTContext> allContexts = RunTimeEnvironment.runTimeEnvironment_.allRTContexts();
        for (final RTContext aContextDecl: allContexts) {
        	// This uses the same method in the base class it shares
        	// in common with classes
        	final Collection<RTMethod> allRTMethods = aContextDecl.allRTMethods();
        	for (final RTCode aMethod: allRTMethods) {
        		buildExpressionMapFor(aMethod);
        	}
        	
        	// It gets StageProps too
        	final Collection<RTClassAndContextCommon> allRTRoles = aContextDecl.allRTRoles();
        	for (final RTClassAndContextCommon aRoleDecl: allRTRoles) {
        		final Collection<RTMethod> allRTMethods2 = aRoleDecl.allRTMethods();
            	for (final RTCode aMethod: allRTMethods2) {
            		buildExpressionMapFor(aMethod);
            	}
        	}
        }
        return;		// just as a debugging breakpoint hook
	}
	
	protected void generateCode(final ParsingData parsingData, final TextEditorGUI gui) {
		final Program program = Program.program();
		RTDebuggerWindow saveDebugger = null;
		if (null != RunTimeEnvironment.runTimeEnvironment_) {
			saveDebugger = RunTimeEnvironment.runTimeEnvironment_.getDebugger();
		}

		// WARNING: gui may be null in batch mode
		final CodeGenerator codeGenerator = new InterpretiveCodeGenerator(program, parsingData, gui);
		codeGenerator.compile();
		virtualMachine_ = codeGenerator.virtualMachine();
		RunTimeEnvironment.runTimeEnvironment_.setDebugger(saveDebugger);
		mainExpr_ = codeGenerator.mainExpr();
		
		// Get rid of old, and make a new one
		// (used by buildExpressionsMapFor)
		allExpressions_ = new HashMap<Integer, RTCode>();
		
		if (null != gui && gui.debuggingEnabled()) {
			buildExpressionMap();
		}
	}
	
	public RunTimeEnvironment virtualMachine() {
		return virtualMachine_;
	}
	
	public RTExpression mainExpr() {
		return mainExpr_;
	}
	
	public static class GuiParseRun extends ParseRun {
		public GuiParseRun(final String input, final TextEditorGUI gui) {
			super(input);
	        try {
	        	commonInit();
	    		this.generateCode(parsingData_, gui);
	        }
		    finally {
		    	gui.console().redirectErr(java.awt.Color.BLUE, null);
		    	gui.printParseDoneBreak();
		    	gui.console().redirectErr(java.awt.Color.RED, null);
		    }
		}
	}
	
	public static class BatchParseRun extends ParseRun {
		public BatchParseRun(final String input, final BatchRunner batchRunner) {
			super(input);
	        try {
	        	commonInit();
	    		this.generateCode(parsingData_, null);
	        }
		    finally {
		    	System.err.println("Compilation complete");
		    }
		}
	}
	
	/* Original:
	 * 
	private void buildExpressionMapFor(final RTCode expr) {
		if (null != expr && (false == allExpressions_.containsValue(expr))) {
			final int lineNumber = expr.lineNumber();
			if (0 != lineNumber) {	// discard library declarations
				allExpressions_.put(lineNumber, expr);
				final List<RTCode> connectedExpressions = expr.connectedExpressions();
				for (final RTCode anExpression: connectedExpressions) {
					buildExpressionMapFor(anExpression);
				}
			}
		}
	}
	 *
	 * These data structures can become very large, and the recursion
	 * can run deep enough to create a stack size error in the JVM. So
	 * we get out our old CS books and create an interative, rather than
	 * explicitly recursive, version of the algorithm.
	 * 
	 * Iterative version:
	 */
	/*
	// http://www.dreamincode.net/forums/topic/82376-non-recursive-tree-traversal/
	private void buildExpressionMapFor(final RTCode expr) {
		final Stack<RTCode> exprStack_ = new Stack<RTCode>();
		exprStack_.push(expr);
		RTCode anExpr = null;
		while(exprStack_.size() > 0){
			anExpr = exprStack_.pop();
			if (null != anExpr) {
				if (false == allExpressions_.containsValue(anExpr)) {
					final int lineNumber = anExpr.lineNumber();
					allExpressions_.put(lineNumber, anExpr);
					final List<RTCode> connectedExpressions = anExpr.connectedExpressions();
					for (final RTCode e: connectedExpressions) {
						if (false == allExpressions_.containsValue(e) && false == exprStack_.contains(e)) {
							exprStack_.push(e);
						}
					}
				}
			}
		}
	}
	*/
	
	// This is an optimized version based on the fact that most RTCode
	// nodes will have zero or one subnodes. Without branching there
	// is not need to stack the node.
	private void buildExpressionMapFor(final RTCode expr) {
		final Stack<RTCode> exprStack_ = new Stack<RTCode>();
		exprStack_.push(expr);
		RTCode anExpr = null;
		while(exprStack_.size() > 0){
			anExpr = exprStack_.pop();
			if (null != anExpr && 0 < anExpr.lineNumber()) {
				if (false == allExpressions_.containsValue(anExpr)) {
					
					final int debuglineNumber = anExpr.lineNumber();
					allExpressions_.put(debuglineNumber, anExpr);
					List<RTCode> connectedExpressions = anExpr.connectedExpressions();
					
					// Don't stack singletons - just do them now
					                // while (1 == connectedExpressions.size()) { // old - ???
					while (0 < connectedExpressions.size()) {
						anExpr = connectedExpressions.get(0);
						assert (null != anExpr);
						final int lineNumber = anExpr.lineNumber();
						
						// Honor only that code that has a genuine
						// source language appearance
						if (0 != lineNumber) {
							if (false == allExpressions_.containsKey(lineNumber)) {
								// First entry for this line
								allExpressions_.put(lineNumber, anExpr);
							} else {
								final RTCode existingExpr = allExpressions_.get(lineNumber);
								final Token token1 = anExpr.token(), token2 = existingExpr.token();
								if (null != token1 && null != token2) {
									if (token1.getStartIndex() < token2.getStartIndex()) {
										// Take just the first one on every line
										allExpressions_.put(lineNumber, anExpr);
									}
								}
							}
						} 
						connectedExpressions = anExpr.connectedExpressions();
					}
					
					// Done with singletons, stack the children
					for (final RTCode e: connectedExpressions) {
						if (e instanceof RTConstant) {
							continue;
						}
						/*
						if (false == allExpressions_.containsValue(e)) {
							boolean isOnStack = false;
							final int stackSize = exprStack_.size();
							
							// Just check and see if it's in the top 10 on
							// the stack ("limit" because it limits the
							// computation).
							//
							// This seems only to make time worse for limit > 0,
							// and it seems to work within the space confines O.K.
							int limit = 0;
							if (limit > stackSize) limit = stackSize - 1;
							for (int i = 1; i < limit; i++) {
								final RTCode aCode = exprStack_.get(stackSize - limit);
								if (aCode == e) {
									isOnStack = true;
									break;
								}
							}
							if (!isOnStack) {
								exprStack_.push(e);
							}
						}
						*/

						if (null != e && false == allExpressions_.containsValue(e)) {
							// Remember only that object code that has
							// a genuine source code appearance
							if (0 < e.lineNumber()) {
								exprStack_.push(e);
							}
						}
					}
				}
			}
		}
	}
	public RTCode expressionForByteOffsetInBuffer(int byteOffset, TextEditorGUI gui) {
		RTCode retval = null;
		final int lineNumber = gui.lineNumberForBufferOffset(byteOffset);
		retval = (RTCode)allExpressions_.get(Integer.valueOf(lineNumber));
		return retval;
	}
	
	private RunTimeEnvironment virtualMachine_;
	private RTExpression mainExpr_;
	private Map<Integer /* Line Number*/, RTCode> allExpressions_;
}
