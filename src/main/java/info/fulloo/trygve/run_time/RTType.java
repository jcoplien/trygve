package info.fulloo.trygve.run_time;

/*
 * Trygve IDE
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

import info.fulloo.trygve.declarations.ActualOrFormalParameterList;

public interface RTType {
	public void addClass(String typeName, RTClass classDecl);
	public void addContext(String typeName, RTContext classDecl);
	public RTType typeNamed(String typeName);
	public void addMethod(String methodName, RTMethod method);
	public void addObjectDeclaration(String objectName, RTType objectType);
	public void addStageProp(String objectName, RTStageProp stagePropType);
	public void addRole(String objectName, RTRole roleType);
	public RTMethod lookupMethod(String methodName, ActualOrFormalParameterList pl);
	public RTMethod lookupMethodIgnoringParameterInSignature(String methodName, ActualOrFormalParameterList pl, String paramToIgnore);
	public RTMethod lookupMethodIgnoringParameterInSignatureWithConversion(final String methodName, final ActualOrFormalParameterList pl, final String paramToIgnore);
	public Map<String, RTType> objectDeclarations();
	public void setObject(String objectName, RTObject object);
	public RTObject getObject(String objectName);
	public RTObject defaultObject();
	public Map<String, RTRole> nameToRoleDeclMap();
	public String name();
}
