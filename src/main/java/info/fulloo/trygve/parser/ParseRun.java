package info.fulloo.trygve.parser;

/*
 * Trygve IDE 1.2
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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import info.fulloo.trygve.configuration.ConfigurationOptions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import info.fulloo.trygve.code_generation.CodeGenerator;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.Program;


public class ParseRun {
	final static String grammarName = "Kant";
	final static String startRuleName = "program";
	public ParseRun(final String input, final TextEditorGUI gui)
	{
        final ParsingData parsingData = new ParsingData();
        Class<? extends Parser> parserClass = null;
        parserClass = KantParser.class;
        ErrorLogger.resetCounts();
        
        try {
        	final ANTLRInputStream inputStream = new ANTLRInputStream(input);
        	final KantLexer lexer = new KantLexer(inputStream);
        	final CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        	final KantParser aParser = new KantParser(commonTokenStream);
	    
        	try {
        		final Method startRule = parserClass.getMethod(startRuleName);
        		final ParserRuleContext tree = (ParserRuleContext)startRule.invoke(aParser, (Object[])null);
        		
        		if (ConfigurationOptions.treewalkTraceEnabled()) {
        			ParseTreeWalker.DEFAULT.walk(new DebugPassListener(), tree);
        		}
        		this.pass0(parsingData, tree);
        		this.pass1(parsingData, tree);
        		this.pass2(parsingData, tree);
        		this.pass3(parsingData, tree);
        		
        		// Pass 4 mainly does template instantiations
        		this.pass4(parsingData, tree);
        		
        		this.generateCode(parsingData);
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
        }
	    finally {
	    	gui.console().redirectErr(java.awt.Color.BLUE, null);
	    	System.err.println("___________________________________________________________");
	    	gui.console().redirectErr(java.awt.Color.RED, null);
	    }
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
	
	private void generateCode(final ParsingData parsingData) {
		final Program program = Program.program();
		final CodeGenerator codeGenerator = new InterpretiveCodeGenerator(program, parsingData);
		codeGenerator.compile();
		virtualMachine_ = codeGenerator.virtualMachine();
		mainExpr_ = codeGenerator.mainExpr();
	}
	
	public RunTimeEnvironment virtualMachine() {
		return virtualMachine_;
	}
	
	public RTExpression mainExpr() {
		return mainExpr_;
	}
	
	private RunTimeEnvironment virtualMachine_;
	private RTExpression mainExpr_;
}
