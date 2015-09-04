package run_time;

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

import java.util.Map;

import declarations.ActualOrFormalParameterList;
import declarations.Type;

public class RTArrayType implements RTType {
	public RTArrayType(Type baseType) {
		super();
		baseType_ = baseType;
	}

	@Override
	public void addClass(String typeName, RTClass classDecl) {
		assert false;
	}

	@Override
	public void addContext(String typeName, RTContext classDecl) {
		assert false;
	}

	@Override
	public RTType typeNamed(String typeName) {
		assert false;
		return null;
	}

	@Override
	public void addMethod(String methodName, RTMethod method) {
		assert false;
	}

	@Override
	public void addObjectDeclaration(String objectName, RTType objectType) {
		assert false;
	}

	@Override
	public void addStageProp(String objectName, RTStageProp stagePropType) {
		assert false;
	}

	@Override
	public void addRole(String objectName, RTRole roleType) {
		assert false;
	}

	@Override
	public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl) {
		assert false;
		return null;
	}

	@Override
	public RTMethod lookupMethodIgnoringParameterInSignature(String methodName,
			ActualOrFormalParameterList pl, String paramToIgnore) {
		assert false;
		return null;
	}

	@Override
	public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}

	@Override
	public void setObject(String objectName, RTObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RTObject getObject(String objectName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RTObject defaultObject() {
		assert false;
		return null;
	}

	@Override
	public Map<String, RTRole> nameToRoleDeclMap() {
		assert false;
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Type baseType() {
		return baseType_;
	}
	
	
	private final Type baseType_;
}
