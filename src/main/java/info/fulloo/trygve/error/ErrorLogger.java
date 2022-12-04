package info.fulloo.trygve.error;

import org.antlr.v4.runtime.Token;

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

public class ErrorLogger {
	public ErrorLogger() {
		resetCounts();
	}
	public static void error(final ErrorIncidenceType errorType, final String s1, final String s2, final String s3, final String s4) {
		updateCounts(errorType);
		System.err.print(s1);
		System.err.print(s2);
		System.err.print(s3);
		System.err.print(s4);
		System.err.println();
	}
	public static void error(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4) {
		System.err.print("line ");
		System.err.print(null == token? 0: token.getLine());
		System.err.print(": ");
		ErrorLogger.error(errorType, s1, s2, s3, s4);
	}
	public static void error(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		updateCounts(errorType);
		System.err.print("line ");
		System.err.print(null == token? 0: token.getLine());
		System.err.print(": ");
		System.err.print(s1);
		System.err.print(s2);
		System.err.print(s3);
		System.err.print(s4);
		System.err.print(s5);
		System.err.print(s6);
		System.err.println();
	}
	public static void resetCounts() {
		numberOfFatalErrors_ = 0;
		numberOfWarnings_ = 0;
		numberOfNonCompliances_ = 0;
		numberOfInternalErrors_ = 0;
		numberOfRuntimeErrors_ = 0;
		numberOfUnimplementedErrors_ = 0;
		numberOfParseErrors_ = 0;
	}
	private static void updateCounts(ErrorIncidenceType errorType) {
		switch (errorType) {
		case Warning: numberOfWarnings_++; break;
		case Fatal: numberOfFatalErrors_++; break;
		case Noncompliant: numberOfNonCompliances_++; break;
		case Internal: numberOfInternalErrors_++; break;
		case Runtime: numberOfRuntimeErrors_++; break;
		case Unimplemented: numberOfUnimplementedErrors_++; break;
		case Parse: numberOfParseErrors_++; break;
		default: assert false;
		}
	}
	public static int numberOfWarnings() { return numberOfWarnings_; }
	public static int numberOfFatalErrors() { return numberOfFatalErrors_ + numberOfInternalErrors_ + numberOfUnimplementedErrors_ + numberOfParseErrors_; }
	public enum ErrorIncidenceType { Warning, Fatal, Noncompliant, Internal, Runtime, Parse, Unimplemented };
	
	@SuppressWarnings("unused")
	private static int numberOfFatalErrors_, numberOfWarnings_, numberOfNonCompliances_, numberOfInternalErrors_,
						numberOfRuntimeErrors_, numberOfUnimplementedErrors_, numberOfParseErrors_;
}
