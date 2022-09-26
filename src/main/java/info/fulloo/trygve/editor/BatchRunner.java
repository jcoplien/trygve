package info.fulloo.trygve.editor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Timer;

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.parser.ParseRun;
import info.fulloo.trygve.parser.ParseRun.BatchParseRun;
import info.fulloo.trygve.parser.ParseRun.GuiParseRun;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RunTimeEnvironment;

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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

public class BatchRunner {
	public int processBatch(final String[] args) {
		programText_ = "";
		processArgs(args);
		if (programText_.length() > 0) {
			final var needsGUI = runFlag_ && programText_.matches("[\\s\\S]*\\bnew\\s+Frame\\s*\\([\\s\\S]*");
			//System.out.println("Needs GUI: " + needsGUI);
			if(needsGUI) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override public void run() {
						guiRun();
					}
				});	
				return -1;
			} else {
		    parseRun_  = new BatchParseRun(programText_, this);
		    assert parseRun_ != null;
				virtualMachine_ = parseRun_.virtualMachine();
				final int errorCount = ErrorLogger.numberOfFatalErrors();
				compiledWithoutError_ = errorCount == 0;
				if (compiledWithoutError_ && runFlag_) {
					simpleRun();
					return 0;
				}
				System.err.format("%d errors\n", errorCount);
				return compiledWithoutError_ ? 0 : 1;
			}
		} else {
			System.err.format("No program source.\n");
			return 2;
		}
	}

	private void resetStreams() {
		System.setOut(out_);
		System.setErr(err_);
	}

	public void guiRun() {
		var gui = new TextEditorGUI(false);

		resetStreams();

		final var parseRun = new GuiParseRun(programText_, gui);
		assert parseRun != null;

		// Reset output streams again after parsing
		resetStreams();
		
		final int errorCount = ErrorLogger.numberOfFatalErrors();
		if(errorCount > 0) {
			System.err.format("%d errors\n", errorCount);
			System.exit(1);
		} else {
			gui.setParseRun(parseRun);
			gui.runButtonActionPerformed(null);
			
			// A final reset
			resetStreams();

			class WaitForClose extends java.util.TimerTask {
				@Override public void run() {		
					if(!gui.userWindowsAreOpen()) {
						System.exit(0);
					}		
				}
			}

			new Timer().schedule(new WaitForClose(), 100, 100);
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
	
	static PrintStream out_ = System.out;
	static PrintStream err_ = System.err;
	ParseRun parseRun_;
	RunTimeEnvironment virtualMachine_;
	boolean compiledWithoutError_, runFlag_;
	String fileName_;
	String programText_;
}
