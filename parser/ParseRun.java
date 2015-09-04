package parser;

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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import parser.KantParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import parser.Pass1Listener;
import run_time.RTExpression;
import run_time.RunTimeEnvironment;
import semantic_analysis.Program;
import code_generation.CodeGenerator;
import code_generation.InterpretiveCodeGenerator;
import error.ErrorLogger;


public class ParseRun {
	final String grammarName = "Kant";
	final String startRuleName = "program";
	public ParseRun(final String input)
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
        		
        		this.pass1(parsingData, tree);
        		this.pass2(parsingData, tree);
        		this.pass3(parsingData, tree);
        		this.generateCode(parsingData);
        	}
        	catch (NoSuchMethodException nsme) {
        		System.err.println("No method for rule "+startRuleName+" or it has arguments");
        	}
        	catch (InvocationTargetException ite) {
        		System.err.println("InvocationTargetException");
        	}
        	catch (IllegalAccessException iae) {
        		System.err.println("IllegalAccessException");
        	}
        }
	    finally {
	    	System.err.println("___________________________________________________________");
	    }
	}
	
	private void pass1(ParsingData parsingData, ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass1Listener(parsingData), tree);
	}

	private void pass2(ParsingData parsingData, ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass2Listener(parsingData), tree);
	}

	private void pass3(ParsingData parsingData, ParserRuleContext tree) {
        ParseTreeWalker.DEFAULT.walk(new Pass3Listener(parsingData), tree);
	}
	
	private void generateCode(ParsingData parsingData) {
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
