package info.fulloo.trygve.editor;

import java.util.ArrayList;
import java.util.List;

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

public class TestRunner {
	private enum TestSource { UseUrl, UseLocalFile };
	TestSource testSource_;
	private final static String testPrefix_ = "tests/";
	private final static String examplePrefix_ = "examples/";
	private final static String urlTestPrefix_ = "file:" + testPrefix_;
	private final static String localTestDir_ = "file:" + testPrefix_;
	private final static String urlExamplePrefix_ = "file:" + examplePrefix_;
	private final static String fileNames_[] = {
		"ctor1.k",
		"exprtest.k",
		"for1.k",
		"forloop1.k",
		"forloop2.k",
		"forloop3.k",
		"forloop4.k",
		"inheritance.k",
		"inheritance2.k",
		"inheritance3.k",
		"inheritance4.k",
		"inheritance5.k",
		"inheritance6.k",
		"derived1.k",
		"inhertest.k",
		"inhertest2.k",
		"roletest.k",
		"roletest2.k",
		"roletest3.k",
		"roletest4.k",
		"roletest5.k",
		"rolemethod_primacy.k",
		"returntest1.k",
		"returntest2.k",
		"trygve1.k",
		"simplemain.k",
		"simplearray.k",
		"arraydup.k",
		"simpleincrement.k",
		"switchtest.k",
		"switch_test2.k",
		"return_switches.k",
		"unaryop1.k",
		"trygve2.k",
		"trygve3.k",
		"whiletest.k",
		"whiletest2.k",
		"roleplaytest.k",
		"roleplaytest2.k",
		"roleplaytest3.k",
		"assignchain.k",
		"forloop2.k",
		"fact.k",
		"simpleprintchain.k",
		"simpletemplate.k",
		"basectortest.k",
		"base_class1.k",
		"rolevec1.k",
		"rolevec2.k",
		"badrole.k",
		"badrole2.k",
		"vec8.k",
		"access1.k",
		"roleclass1.k",
		"roleclass2.k",
		"sqrt.k",
		"interface1.k",
		"interface2.k",
		"interface3.k",
		"interface_compareto_1.k",
		"quadratic1.k",
		"rightAssociativeSum2.k",
		"thisqualbug1.k",
		"simplestring1.k",
		"simplest_role.k",
		"initordertest.k",
		"arraysizetest1.k",
		"andreas1.k",
		"andreas2.k",
		"andreas3.k",
		"andreas4.k",
		"assertbug1.k",
		"luhnvalidation.k",
		"new_luhnvalidation.k",
		"chord_identifier.k",
		"chord_identifier2.k",
		"chord_identifier2_minimal.k",
		"chord_identifier3_stripped.k",
		"simplereturn.k",
		"naked_new.k",
		"simplearraysize.k",
		"simplearraysize2.k",
		"chord_identifier3.k",
		"returnbreak.k",
		"printstreamrole.k",
		"chord_identifier4.k",
		"chord_identifier4_original.k",
		"chord_identifier5.k",
		"chord_identifier6.k",
		"chord_identifier7.k",
		"assert_in_roletest.k",
		"moneytransfer_original.k",
		"bool_op1.k",
		"fractal0.k",
		"fractal1.k",
		"fractal.k",
		"compareTo_test1.k",
		"exprtest1.k",
		"money_transfer2.k",
		"money_transfer.k",
		"money_transfer3.k",
		"matt_money_xfer.k",
		"date_test.k",
		"number_test0.k",
		"number_test1.k",
		"role_and_double_test.k",
		"complex1.k",
		"disk1.k",
		"hanoi.k",
		"hanoi2.k",
		"hanoi2.5.k",
		"hanoi3.k",
		"context_role_bug1.k",
		"matt_20160112.k",
		"matt3.k",
		"matt4.k",
		"requires_method1.k",
		"illegal_context_access.k",
		"frontloading1.k",
		"spell_check.k",
		"spell_check2.k",
		"spell_check3.k",
		"chainable1.k",
		"join1.k",
		"InSituInit1.k",
		"nestedContext1.k",
		"nestedContext2.k",
		"nestedContext3.k",
		"runeInitialization1.k",
		"runeInitialization2.k",
		"runeInitialization3.k",
		"listrole2.k",
		"isexpression_test.k",
		"confused_role_vec.k",
		"frankfurt1.k",
		"frankfurt2.k",
		"frankfurt3.k",
		"frankfurt4.k",
		"july_money_transfer_2.k",
		"role_class_compatibility.k",
		"role_class_compatibility_new.k",
		"role_interface_compatibility.k",
		"twelve_days_of_christmas.k",
		"twelve_days_of_christmas2.k",
		"issue65.k",
	};
	private final static String exampleNames_[] = {
		"borrow_library_items.k",
		"spellcheck.k",
		"spellcheck2.k",
		"borrow_library_panel5.k",
		"panel1.k",
		"pong2.k",
		"trygve_pong.k",
		"breakout.k",
		"keypad.k",
		"simple_list.k",
		"july_money_transfer.k",
	};
	public static int numberOfTestCases() {
		return fileNames_.length;
	}
	public static int numberOfExampleCases() {
		return exampleNames_.length;
	}
	public static String urlForTestCase(final int i) {
		return urlTestPrefix_ + fileNames_[i];
	}
	public static String fileNameForTestCase(final int i) {
		return fileNames_[i];
	}
	public static String urlForExample(final int i) {
		return urlExamplePrefix_ + exampleNames_[i];
	}
	public static String fileNameForExample(final int i) {
		return exampleNames_[i];
	}
	public TestRunner(final TextEditorGUI gui) {
		gui_ = gui;
		plusses_ = " +  +  +  ";
		passCounter_ = failCounter_ = 0;
		testSource_ = TestSource.UseLocalFile;
		failures_ = new ArrayList<String>();
	}
	public void runTests() {
		final String saveFileNameField = gui_.getFileNameField();
		System.out.flush();
		System.err.flush();
		String testResults = gui_.errorPanelContents();
		String lastTestResults = testResults;
		passCounter_ = failCounter_ = 0;
		for (final String filename : fileNames_) {
			runATest(filename);
			if (false == gui_.compiledWithoutError()) {
				// break;
			}
			System.out.flush();
			System.err.flush();
			testResults = gui_.errorPanelContents();
			checkTestResults(lastTestResults, testResults);
			
			System.out.flush();
			System.err.flush();
			
			lastTestResults = gui_.errorPanelContents();
		}
		
		gui_.console().redirectErr(java.awt.Color.BLUE, null);
		gui_.printBreak();
		
		if (failCounter_ > 0) {
			gui_.console().redirectErr(java.awt.Color.RED, null);
		} else {
			gui_.console().redirectErr(new java.awt.Color(20, 210, 20), null);
		}
		System.err.print(passCounter_);
		System.err.print(" tests passed; ");
		System.err.print(failCounter_);
		System.err.print(" tests failed.");
		if (failCounter_ > 0) {
			System.err.print(" Failed tests are:");
		}
		System.err.println();
		gui_.console().redirectErr(java.awt.Color.RED, null);
		if (0 == failCounter_) {
			gui_.setFileNameField(saveFileNameField);
		} else {
			for (final String failure : failures_) {
				System.err.format("\t%s", failure);
				System.err.println();
			}
			final String firstFailure = failures_.get(0);
			loadTestFile(firstFailure);
			gui_.setFileNameField(testPrefix_ + firstFailure);
		}
	}
	private void loadTestFile(final String filename) {
		gui_.setFileNameField(localTestDir_ + filename);	// just in case user edits / saves - goes to the right place
		gui_.setWWWFileNameField(urlTestPrefix_ + filename);
		gui_.wwwButtonActionPerformed(null);
	}
	private void runATest(final String filename) {
		currentTestName_ = filename;
		String url = null;
		switch (testSource_) {
		case UseUrl:
			url = urlTestPrefix_ + filename; break;
		case UseLocalFile:
			url = localTestDir_ + filename; break;
		}
		
		gui_.console().redirectErr(java.awt.Color.BLUE, null);
		gui_.printBreak();
		System.err.print(plusses_); System.err.print(url); System.err.println(plusses_);
		gui_.console().redirectErr(java.awt.Color.RED, null);
		gui_.resetCompiledWithoutError();
		this.loadTestFile(filename);
		gui_.parseButtonActionPerformed(null);
		if (gui_.compiledWithoutError()) {
			// No: gui_.runButtonActionPerformed(null);
			// Don't run it on a separate thread — it messes up
			// the interleaving of the output.
			gui_.simpleRun();
			
			// Special case - running this twice in a row
			// once elicited bugs.
			if (filename.equals("chord_identifier2.k")) {
				gui_.runButtonActionPerformed(null);
			}
		}
	}
	
	/**/
	private String canonize(final char c) {
		String retval = String.valueOf(c);
		switch (c) {
		case '\n': retval = "\\n"; break;
		case '\r': retval = "\\r"; break;
		case '\t': retval = "\\t"; break;
		}
		return retval;
	}
	/**/
	
	private void analyzeFailure(final String s1, final String s2) {
		/**/
		if (s1.equals(s2)) {
			return;
		} else {
			final int s1Length = s1.length(), s2Length = s2.length();
			final int shortest = Math.min(s1Length, s2Length);
			for (int i = 0; i < shortest; i++) {
				if (s1.charAt(i) == s2.charAt(i)) {
					continue;
				} else {
					int j = i, counter = 0;
					while (j < shortest && counter < 10) {
						final char c1 = s1.charAt(j), c2 = s2.charAt(j);
						final String sc1 = canonize(c1);
						final String sc2 = canonize(c2);
						System.err.format("%d: '%s' '%s'\n", j, sc1, sc2);
						counter++;
						j++;
					}
					break;
				}
			}
		}
		/**/
	}

	private void checkTestResults(final String lastTestResults, final String rawTestResults) {
		final String testResults = thisTestResults(lastTestResults, rawTestResults);
		final String goldContents = thisRunGoldContents();

		if (testResults.equals(goldContents)) {
			gui_.console().redirectErr(new java.awt.Color(20, 210, 20), null);
			System.err.println("Test passed");
			gui_.console().redirectErr(java.awt.Color.BLUE, null);
			passCounter_++;
		} else {
			gui_.console().redirectErr(java.awt.Color.RED, null);
			System.err.println("Test failed");
			gui_.console().redirectErr(java.awt.Color.BLUE, null);
			failCounter_++;
			failures_.add(currentTestName_);
			analyzeFailure(testResults, goldContents);
		}
	}

	private String thisTestResults(final String lastTestResults, final String rawTestResults) {
		final int lastTestResultsLength = lastTestResults.length();
		String testResults = rawTestResults.substring(lastTestResultsLength +
				gui_.underscoresLength() + 1);
		while (testResults.substring(0,1).equals("\n") || testResults.substring(0,1).equals("\r")) {
			testResults = testResults.substring(1);
		}

		if (testResults.length() > 10 && testResults.substring(0, plusses_.length()).equals(plusses_)) {
			testResults = testResults.substring(plusses_.length());
			final int indexOfDelimitingSpace = testResults.indexOf(' ');
			final String fileName = testResults.substring(0, indexOfDelimitingSpace);
			testResults = testResults.substring(fileName.length());
			if (testResults.length() > 10 && testResults.substring(0,plusses_.length()).equals(plusses_)) {
				// +1 for newline after plusses, +1 for newline after underscores
				testResults = testResults.substring(plusses_.length() + 1);
			}
		} else {
			;
		}
		return testResults;
	}
	private String thisRunGoldContents() {
		final String goldDelimiter = "/* GOLD:\n", endDelimiter = "\n*/\n";
		String program = gui_.editPanelContents();
		final int goldIndex = program.indexOf(goldDelimiter);
		if (0 < goldIndex) {
			program = program.substring(goldIndex + goldDelimiter.length());
		}
		final int closingCommentIndex = program.indexOf(endDelimiter);
		if (0 < closingCommentIndex) {
			program = program.substring(0, closingCommentIndex);
		}
		return program;
	}

	private final TextEditorGUI gui_;
	private final String plusses_;
	private int passCounter_, failCounter_;
	private String currentTestName_;
	private List<String> failures_;
}
