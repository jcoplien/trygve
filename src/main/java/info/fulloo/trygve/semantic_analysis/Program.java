package info.fulloo.trygve.semantic_analysis;

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

import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.expressions.Expression;

public class Program {
	public Program(final Expression main, final TypeDeclarationList theRest, final TypeDeclarationList templateInstantationList) {
		main_ = main;
		theRest_ = theRest;
		templateInstantiations_ = templateInstantationList;
		setProgram(this);
	}
	private static void setProgram(final Program theThis) {
		program_ = theThis;
	}
	public static Program program() {
		return program_;
	}
	public Expression main() {
		return main_;
	}
	public TypeDeclarationList templateInstantiations() {
		return templateInstantiations_;
	}
	public TypeDeclarationList theRest() {
		return theRest_;
	}
	private final Expression main_;
	private final TypeDeclarationList theRest_, templateInstantiations_;
	private static Program program_ = null;
}