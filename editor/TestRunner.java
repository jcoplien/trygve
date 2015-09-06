package editor;

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
		fileNames_ = new String [] {
			"file:///Users/cope/Programs/Trygve/ctor1.k",
			"file:///Users/cope/Programs/Trygve/exprtest.k",
			"file:///Users/cope/Programs/Trygve/for1.k",
			"file:///Users/cope/Programs/Trygve/forloop1.k",
			"file:///Users/cope/Programs/Trygve/inheritance.k",
			"file:///Users/cope/Programs/Trygve/inheritance2.k",
			"file:///Users/cope/Programs/Trygve/inhertest.k",
			"file:///Users/cope/Programs/Trygve/roletest.k",
			"file:///Users/cope/Programs/Trygve/roletest2.k",
			"file:///Users/cope/Programs/Trygve/roletest3.k",
			"file:///Users/cope/Programs/Trygve/roletest4.k",
			"file:///Users/cope/Programs/Trygve/trygve1.k",
			"file:///Users/cope/Programs/Trygve/simplemain.k",
			"file:///Users/cope/Programs/Trygve/simplearray.k",
			"file:///Users/cope/Programs/Trygve/simpleincrement.k",
			"file:///Users/cope/Programs/Trygve/switchtest.k",
			"file:///Users/cope/Programs/Trygve/unaryop1.k",
			"file:///Users/cope/Programs/Trygve/trygve2.k",
			"file:///Users/cope/Programs/Trygve/trygve3.k",
			"file:///Users/cope/Programs/Trygve/whiletest.k",
			"file:///Users/cope/Programs/Trygve/roleplaytest.k",
			"file:///Users/cope/Programs/Trygve/roleplaytest2.k",
			"file:///Users/cope/Programs/Trygve/roleplaytest3.k",
			"file:///Users/cope/Programs/Trygve/assignchain.k",
			"file:///Users/cope/Programs/Trygve/forloop2.k",
			"file:///Users/cope/Programs/Trygve/fact.k",
			"file:///Users/cope/Programs/Trygve/simpleprintchain.k"
		};
	}
	public void runTests() {
		String testResults = gui_.errorPanelContents();
		for (String filename : fileNames_) {
			runATest(filename);
			if (gui_.compiledWithoutError() == false) {
				// break;
			}
			final String lastTestResults = testResults;
			testResults = gui_.errorPanelContents();
			final int lastTestResultsLength = lastTestResults.length();
			testResults = testResults.substring(lastTestResultsLength);
		}
	}
	private void runATest(String pathname) {
		System.err.println("___________________________________________________________");
		System.err.print(" +  +  +  "); System.err.print(pathname); System.err.println(" +  +  +  ");
		gui_.resetCompiledWithoutError();
		gui_.setFileNameField(pathname);	// just in case user edits / saves - goes to the right place
		gui_.setWWWFileNameField(pathname);
		gui_.wwwButtonActionPerformed(null);
		gui_.parseButtonActionPerformed(null);
		if (gui_.compiledWithoutError()) {
			gui_.runButtonActionPerformed(null);
		}
	}

	private final TextEditorGUI gui_;
	private String[] fileNames_;
}
