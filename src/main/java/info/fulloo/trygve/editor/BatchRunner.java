package info.fulloo.trygve.editor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.parser.ParseRun;
import info.fulloo.trygve.parser.ParseRun.BatchParseRun;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RunTimeEnvironment;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2022 James O. Coplien, jcoplien@gmail.com
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

public class BatchRunner {
	public void processBatch(final String[] args) {
		programText_ = "";
		processArgs(args);
		if (programText_.length() > 0) {
		    parseRun_  = new BatchParseRun(programText_, this);
		    assert parseRun_ != null;
			virtualMachine_ = parseRun_.virtualMachine();
			final int errorCount = ErrorLogger.numberOfFatalErrors();
			compiledWithoutError_ = errorCount == 0;
			System.err.format("%d errors\n", errorCount);
			if (compiledWithoutError_) {
				if (runFlag_) {
					simpleRun();
				}
			}
		} else {
			System.err.format("No program source.\n");
		}
	}
	public void simpleRun() {
		final RTExpression rTMainExpr = parseRun_.mainExpr();
		virtualMachine_.reboot();
	    virtualMachine_.run(rTMainExpr);
	}
	private String loadFile() {
		final StringBuilder stringBuilder = new StringBuilder();
	    try {
	        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName_), "UTF-8"));
	        while (reader.ready()) {
	            stringBuilder.append(reader.readLine() + System.getProperty("line.separator"));
	        }
	        reader.close();
	    } catch (IOException ioe) {
	        System.err.format("Pardon. Can't open file `%s'. Cope needs to check his code.", fileName_);
	    }
	    return stringBuilder.toString();
	}
	private void processArgs(final String[] args) {
		runFlag_ = false;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.startsWith("-c")) {
				// -c = compile. compile the given pathname
				if (arg.length() > 2) {
					fileName_ = arg.substring(2, arg.length()-1);
				} else {
					fileName_ = arg = args[++i];
					final String program = loadFile();
					programText_ = programText_ + program;
				}
			} else if (arg.equals("-r") || arg.equals("-run")) {
				runFlag_ = true;
			} else {
				System.err.format("Usage: -c filename.k [-c otherFilename.k] ... [-r]\n");
			}
		}
	}
	
	ParseRun parseRun_;
	RunTimeEnvironment virtualMachine_;
	boolean compiledWithoutError_, runFlag_;
	String fileName_;
	String programText_;
}
