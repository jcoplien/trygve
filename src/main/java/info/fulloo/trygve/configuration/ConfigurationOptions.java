package info.fulloo.trygve.configuration;

/*
 * Trygve IDE 4.0
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

public class ConfigurationOptions {
	public static boolean treewalkTraceEnabled() {
		return false;
	}
	public static boolean tracePass1() {
		return false;
	}
	public static boolean tracePass4() {
		return false;
	}
	public static boolean stackSnapshotDebug() {
		return false;
	}
	public static boolean printClassMethodDecls() {
		return false;
	}
	public static boolean printContextMethodDecls() {
		return false;
	}
	public static boolean fullExecutionTrace() {
		return false;
	}
	public static boolean runtimeStackTrace() {
		return false;
	}
	public static boolean activationRecordStackTrace() {
		return false;
	}
	public static boolean redirectStandardErrorToConsole() {
		return false;
	}
	public static boolean enforceObjectEncapsulation() {
		return true;
	}
	
	static {
		  boolean assertsEnabled = false;
		  assert assertsEnabled = true : "The build should be configured to enable asserts."; // Intentional side effect!!!
		  if (!assertsEnabled) {
			  throw new RuntimeException("Asserts must be enabled!!! Re-build trygve with -ea option or equivalent.");
		  }
	}
}
