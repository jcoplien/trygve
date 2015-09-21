package error;

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

public class ErrorLogger {
	public ErrorLogger() {
		resetCounts();
	}
	public static void error(ErrorType errorType, String s1, String s2, String s3, String s4) {
		updateCounts(errorType);
		System.err.print(s1);
		System.err.print(s2);
		System.err.print(s3);
		System.err.println(s4);
	}
	public static void error(ErrorType errorType, long lineNumber, String s1, String s2, String s3, String s4) {
		updateCounts(errorType);
		System.err.print("line ");
		System.err.print(lineNumber);
		System.err.print(": ");
		ErrorLogger.error(errorType, s1, s2, s3, s4);
	}
	public static void error(ErrorType errorType, long lineNumber, String s1, String s2, String s3, String s4, String s5, String s6) {
		updateCounts(errorType);
		System.err.print("line ");
		System.err.print(lineNumber);
		System.err.print(": ");
		System.err.print(s1);
		System.err.print(s2);
		System.err.print(s3);
		System.err.print(s4);
		System.err.print(s5);
		System.err.println(s6);
	}
	public static void resetCounts() {
		numberOfFatalErrors_ = 0;
		numberOfWarnings_ = 0;
		numberOfNonCompliances_ = 0;
		numberOfInternalErrors_ = 0;
		numberOfRuntimeErrors_ = 0;
	}
	private static void updateCounts(ErrorType errorType) {
		switch (errorType) {
		case Warning: numberOfWarnings_++; break;
		case Fatal: numberOfFatalErrors_++; break;
		case Noncompliant: numberOfNonCompliances_++; break;
		case Internal: numberOfInternalErrors_++; break;
		case Runtime: numberOfRuntimeErrors_++; break;
		default: assert false;
		}
	}
	public static int numberOfFatalErrors() { return numberOfFatalErrors_ + numberOfInternalErrors_; }
	public enum ErrorType { Warning, Fatal, Noncompliant, Internal, Runtime };
	@SuppressWarnings("unused")
	private static int numberOfFatalErrors_, numberOfWarnings_, numberOfNonCompliances_, numberOfInternalErrors_,
						numberOfRuntimeErrors_;
}
