package info.fulloo.trygve.editor;

import java.util.ArrayList;
import java.util.List;

/*
 * Trygve IDE
 *   Copyright (c)2015 James O. Coplien
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
	private final static String urlPrefix_ = "file:tests/";
	private final static String localTestDir_ = "file:tests/";
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
		"inhertest.k",
		"inhertest2.k",
		"roletest.k",
		"roletest2.k",
		"roletest3.k",
		"roletest4.k",
		"roletest5.k",
		"returntest1.k",
		"returntest2.k",
		"trygve1.k",
		"simplemain.k",
		"simplearray.k",
		"arraydup.k",
		"simpleincrement.k",
		"switchtest.k",
		"unaryop1.k",
		"trygve2.k",
		"trygve3.k",
		"whiletest.k",
		"roleplaytest.k",
		"roleplaytest2.k",
		"roleplaytest3.k",
		"assignchain.k",
		"forloop2.k",
		"fact.k",
		"simpleprintchain.k",
		"simpletemplate.k",
		"basectortest.k",
		"rolevec1.k",
		"access1.k",
		"roleclass1.k",
		"roleclass2.k",
		"sqrt.k",
		"interface1.k",
		"interface2.k",
		"interface3.k",
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
		"naked_new.k",
		// "chord_identifier3.k",
	};
	public static int numberOfTestCases() {
		return fileNames_.length;
	}
	public static String urlForTestCase(final int i) {
		return urlPrefix_ + fileNames_[i];
	}
	public static String fileNameForTestCase(final int i) {
		return fileNames_[i];
	}
	public TestRunner(final TextEditorGUI gui) {
		gui_ = gui;
		underscores_ = "___________________________________________________________";
		plusses_ = " +  +  +  ";
		passCounter_ = failCounter_ = 0;
		testSource_ = TestSource.UseLocalFile;
		failures_ = new ArrayList<String>();
	}
	public void runTests() {
		final String saveFileNameField = gui_.getFileNameField();
		String testResults = gui_.errorPanelContents();
		String lastTestResults = testResults;
		passCounter_ = failCounter_ = 0;
		for (final String filename : fileNames_) {
			runATest(filename);
			if (gui_.compiledWithoutError() == false) {
				// break;
			}
			System.out.flush();
			System.err.flush();
			testResults = gui_.errorPanelContents();
			checkTestResults(lastTestResults, testResults);
			lastTestResults = gui_.errorPanelContents();
		}
		
		gui_.console().redirectErr(java.awt.Color.BLUE, null);
		System.err.println(underscores_);
		
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
				System.err.format("\t%s\n", failure);
			}
			final String firstFailure = failures_.get(0);
			loadFile(firstFailure);
		}
	}
	private void loadFile(final String filename) {
		gui_.setFileNameField(localTestDir_ + filename);	// just in case user edits / saves - goes to the right place
		gui_.setWWWFileNameField(urlPrefix_ + filename);
		gui_.wwwButtonActionPerformed(null);
	}
	private void runATest(final String filename) {
		currentTestName_ = filename;
		String url = null;
		switch (testSource_) {
		case UseUrl:
			url = urlPrefix_ + filename; break;
		case UseLocalFile:
			url = localTestDir_ + filename; break;
		}
		
		gui_.console().redirectErr(java.awt.Color.BLUE, null);
		System.err.println(underscores_);
		System.err.print(plusses_); System.err.print(url); System.err.println(plusses_);
		gui_.console().redirectErr(java.awt.Color.RED, null);
		gui_.resetCompiledWithoutError();
		this.loadFile(filename);
		gui_.parseButtonActionPerformed(null);
		if (gui_.compiledWithoutError()) {
			gui_.runButtonActionPerformed(null);
			
			// Special case - running this twice in a row
			// is a problem
			if (filename.equals("chord_identifier2.k")) {
				gui_.runButtonActionPerformed(null);
			}
		}
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
		}
	}

	private String thisTestResults(final String lastTestResults, final String rawTestResults) {
		final int lastTestResultsLength = lastTestResults.length();
		String testResults = rawTestResults.substring(lastTestResultsLength + underscores_.length() + 1);
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
	private final String underscores_, plusses_;
	private int passCounter_, failCounter_;
	private String currentTestName_;
	private List<String> failures_;
}
