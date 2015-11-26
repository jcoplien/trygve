package info.fulloo.trygve.run_time;

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

import java.util.LinkedHashMap;
import java.util.Map;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.RoleArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;


public class RTRole extends RTClassAndContextCommon implements RTType  {
	public RTRole(final RoleDeclaration associatedDeclaration) {
		super(associatedDeclaration);
		assert associatedDeclaration instanceof RoleDeclaration;
		associatedDeclaration_ = associatedDeclaration;
		stringToContextDeclMap_ = new LinkedHashMap<String, RTContext>();
		populateNameToStaticObjectMap();
	}
	@Override protected void populateNameToTypeObjectMap() {
		assert false;
	}
	@Override public RTObject getObject(final String objectName) {
		assert false;
		return null;
	}
	public RoleDeclaration associatedDeclaration() {
		return associatedDeclaration_;
	}
	@Override public void addClass(final String typeName, RTClass classDecl) {
		assert false;
	}
	@Override public void addContext(String typeName, RTContext contextDecl) {
		stringToContextDeclMap_.put(typeName, contextDecl);
	}
	public void addObject(String objectName, Type objectType) {
		assert false;
	}
	@Override public void addStageProp(String stagePropName, RTStageProp stagePropType) {
		assert false;
	}
	@Override public void addRole(String roleName, RTRole roleType) {
		assert false;
	}
	@Override public RTType typeNamed(String name) {
		assert false;
		return null;
	}
	@Override public void setObject(String unused1, RTObject unused2) {
		assert false;
	}
	@Override public void addObjectDeclaration(String unused1, RTType unused2) {
		assert false;
	}
	@Override public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}
	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		return new LinkedHashMap<String, RTRole>();
	}
	public boolean isArray() {
		return associatedDeclaration_ instanceof RoleArrayDeclaration;
	}
	
	private Map<String, RTContext> stringToContextDeclMap_;
	private RoleDeclaration associatedDeclaration_;
}
