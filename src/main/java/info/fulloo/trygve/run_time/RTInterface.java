package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.1 1.1
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

import java.util.Map;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.TypeDeclaration;


public class RTInterface extends RTClassAndContextCommon implements RTType {
	public RTInterface(final TypeDeclaration decl) {
		super(decl);
		assert decl instanceof InterfaceDeclaration;
	}
	public RTType typeNamed(final String typeName) {
		assert false;
		return null;
	}
	@Override public void setObject(final String objectName, final RTObject object) {
		assert false;
	}
	@Override public RTObject getObject(final String objectName) {
		assert false;
		return null;
	}
	
	// All of these are fishy as class members...  They're here just to
	// satisfy the pure virtuals in the base class..
	@Override public void addObjectDeclaration(final String objectName, final RTType objectType) {
		assert false;
	}
	@Override public void addStageProp(final String stagePropName, final RTStageProp stagePropType) {
		assert false;
	}
	@Override public void addRole(final String roleName, final RTRole roleType) {
		assert false;
	}
	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		assert false;
		return null;
	}
	@Override public Map<String, RTStageProp> nameToStagePropDeclMap() {
		assert false;
		return null;
	}
	@Override public void addContext(String typeName, RTContext classDecl) {
		assert false;
	}
	@Override public void addClass(String typeName, RTClass classDecl) {
		assert false;
	}
}