package info.fulloo.trygve.editor;

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

public class TestRunner {
	public TestRunner(TextEditorGUI gui) {
		gui_ = gui;
		underscores_ = "___________________________________________________________";
		plusses_ = " +  +  +  ";
		passCounter_ = failCounter_ = 0;
		fileNames_ = new String [] {
			"http://fulloo.info/Examples/TrygveExamples/ctor1.k",
			"http://fulloo.info/Examples/TrygveExamples/exprtest.k",
			"http://fulloo.info/Examples/TrygveExamples/for1.k",
			"http://fulloo.info/Examples/TrygveExamples/forloop1.k",
			"http://fulloo.info/Examples/TrygveExamples/forloop2.k",
			"http://fulloo.info/Examples/TrygveExamples/forloop3.k",
			"http://fulloo.info/Examples/TrygveExamples/inheritance.k",
			"http://fulloo.info/Examples/TrygveExamples/inheritance2.k",
			"http://fulloo.info/Examples/TrygveExamples/inheritance3.k",
			"http://fulloo.info/Examples/TrygveExamples/inheritance4.k",
			"http://fulloo.info/Examples/TrygveExamples/inheritance5.k",
			"http://fulloo.info/Examples/TrygveExamples/inhertest.k",
			"http://fulloo.info/Examples/TrygveExamples/roletest.k",
			"http://fulloo.info/Examples/TrygveExamples/roletest2.k",
			"http://fulloo.info/Examples/TrygveExamples/roletest3.k",
			"http://fulloo.info/Examples/TrygveExamples/roletest4.k",
			"http://fulloo.info/Examples/TrygveExamples/roletest5.k",
			"http://fulloo.info/Examples/TrygveExamples/trygve1.k",
			"http://fulloo.info/Examples/TrygveExamples/simplemain.k",
			"http://fulloo.info/Examples/TrygveExamples/simplearray.k",
			"http://fulloo.info/Examples/TrygveExamples/arraydup.k",
			"http://fulloo.info/Examples/TrygveExamples/simpleincrement.k",
			"http://fulloo.info/Examples/TrygveExamples/switchtest.k",
			"http://fulloo.info/Examples/TrygveExamples/unaryop1.k",
			"http://fulloo.info/Examples/TrygveExamples/trygve2.k",
			"http://fulloo.info/Examples/TrygveExamples/trygve3.k",
			"http://fulloo.info/Examples/TrygveExamples/whiletest.k",
			"http://fulloo.info/Examples/TrygveExamples/roleplaytest.k",
			"http://fulloo.info/Examples/TrygveExamples/roleplaytest2.k",
			"http://fulloo.info/Examples/TrygveExamples/roleplaytest3.k",
			"http://fulloo.info/Examples/TrygveExamples/assignchain.k",
			"http://fulloo.info/Examples/TrygveExamples/forloop2.k",
			"http://fulloo.info/Examples/TrygveExamples/fact.k",
			"http://fulloo.info/Examples/TrygveExamples/simpleprintchain.k",
			"http://fulloo.info/Examples/TrygveExamples/simpletemplate.k",
			"http://fulloo.info/Examples/TrygveExamples/basectortest.k",
			"http://fulloo.info/Examples/TrygveExamples/rolevec1.k",
			"http://fulloo.info/Examples/TrygveExamples/access1.k",
			"http://fulloo.info/Examples/TrygveExamples/roleclass1.k",
			"http://fulloo.info/Examples/TrygveExamples/roleclass2.k"
		};
	}
	public void runTests() {
		final String saveFileNameField = gui_.getFileNameField();
		String testResults = gui_.errorPanelContents();
		String lastTestResults = testResults;
		passCounter_ = failCounter_ = 0;
		for (String filename : fileNames_) {
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
		System.err.println(" tests failed.");
		gui_.console().redirectErr(java.awt.Color.RED, null);
		if (0 == failCounter_) {
			gui_.setFileNameField(saveFileNameField);
		}
	}
	private void runATest(String pathname) {
		gui_.console().redirectErr(java.awt.Color.BLUE, null);
		System.err.println(underscores_);
		System.err.print(plusses_); System.err.print(pathname); System.err.println(plusses_);
		gui_.console().redirectErr(java.awt.Color.RED, null);
		gui_.resetCompiledWithoutError();
		gui_.setFileNameField(pathname.substring("file://".length()));	// just in case user edits / saves - goes to the right place
		gui_.setWWWFileNameField(pathname);
		gui_.wwwButtonActionPerformed(null);
		gui_.parseButtonActionPerformed(null);
		if (gui_.compiledWithoutError()) {
			gui_.runButtonActionPerformed(null);
		}
	}
	private void checkTestResults(String lastTestResults, String rawTestResults) {
		final String testResults = thisTestResults(lastTestResults, rawTestResults);
		final String goldContents = thisRunGoldContents();
		testResults.replaceAll("\r", "\n");
		goldContents.replaceAll("\r", "\n");
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
		}
	}

	private String thisTestResults(String lastTestResults, String rawTestResults) {
		final int lastTestResultsLength = lastTestResults.length();
		String testResults = rawTestResults.substring(lastTestResultsLength + underscores_.length() + 1);
		if (testResults.substring(0,1).equals("\n")) {
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
	private String[] fileNames_;
	private final String underscores_, plusses_;
	private int passCounter_, failCounter_;
}
