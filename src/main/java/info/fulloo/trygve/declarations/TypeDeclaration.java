package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 1.3
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

import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public interface TypeDeclaration {
	public int lineNumber();
	public String name();
	public StaticScope enclosingScope();
	public StaticScope enclosedScope();
	public Type type();
	public ObjectDeclaration lookupStaticObjectDeclaration(final String name);
	public void declareStaticObject(final ObjectDeclaration decl);
}
