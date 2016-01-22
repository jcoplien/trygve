package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.2
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

import java.util.LinkedHashMap;
import java.util.Map;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropArrayDeclaration;

public class RTStageProp extends RTClassAndContextCommon implements RTType {
	public RTStageProp(final StagePropDeclaration associatedDeclaration) {
		super(associatedDeclaration);
		associatedDeclaration_ = associatedDeclaration;
		stringToContextDeclMap_ = new LinkedHashMap<String, RTContext>();
	}
	public StagePropDeclaration associatedDeclaration() {
		return associatedDeclaration_;
	}
	@Override public void addClass(final String typeName, final RTClass classDecl) {
		assert false;
	}
	@Override public void addContext(final String typeName, final RTContext contextDecl) {
		stringToContextDeclMap_.put(typeName, contextDecl);
	}
	public void addObject(final String objectName, final Type objectType) {
		assert false;
	}
	@Override public void addStageProp(final String stagePropName, final RTStageProp stagePropType) {
		assert false;
	}
	@Override public void addRole(final String roleName, final RTRole roleType) {
		assert false;
	}
	@Override public RTType typeNamed(final String name) {
		assert false;
		return null;
	}
	@Override public void setObject(final String unused1, final RTObject unused2) {
		assert false;
	}
	@Override public RTObject getObject(final String objectName) {
		assert false;
		// Doesn't seem right to get an instance from a stageprop.
		return null;
	}
	@Override public void addObjectDeclaration(final String unused1, final RTType unused2) {
		assert false;
	}
	@Override public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}
	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		return new LinkedHashMap<String, RTRole>();
	}
	@Override public Map<String, RTStageProp> nameToStagePropDeclMap() {
		return new LinkedHashMap<String, RTStageProp>();
	}
	public boolean isArray() {
		return associatedDeclaration_ instanceof StagePropArrayDeclaration;
	}
	
	private Map<String, RTContext> stringToContextDeclMap_;
	private StagePropDeclaration associatedDeclaration_;
}
