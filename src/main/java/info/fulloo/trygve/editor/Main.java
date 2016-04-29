package info.fulloo.trygve.editor;

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

public class Main {

	public static final String TRYGVE_VERSION = "2.10";

	public static void main(String[] args) {
		// We adopt a Windows-like line formatting, uniformly, as a way
		// to get platform independence in the output. Many thanks to
		// Egon Elbre!
		System.setProperty("line.separator", "\n");
		
		if (args.length == 0) {
			printUsage();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new TextEditorGUI().setVisible(true);
				}
			});
		} else if (args[0].startsWith("-g")) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new TextEditorGUI().setVisible(true);
				}
			});
		} else if (args[0].startsWith("-c")) {
			new BatchRunner().processBatch(args);
		} else if (args[0].startsWith("-v")) {
			System.out.print("trygve version ");
			System.out.println(Main.TRYGVE_VERSION);
		} else {
			printUsage();
		}
	}
	private static void printUsage() {
		System.out.format("Usage: trygve -gui\n");
		System.out.format("       trygve -c filename.k\n");
		System.out.format("       trygve -c filename.k -r\n");
		System.out.format("       trygve -v\n");
	}
}