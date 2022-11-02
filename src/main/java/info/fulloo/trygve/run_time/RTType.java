package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2022 James O. Coplien, jcoplien@gmail.com
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
import java.util.List;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;

public interface RTType {
	public void addClass(final String typeName, final RTClass classDecl);
	public void addContext(final String typeName, final RTContext classDecl);
	public RTType typeNamed(final String typeName);
	public void addMethod(final String methodName, final RTMethod method);
	public void addObjectDeclaration(final String objectName, final RTType objectType);
	public void addStageProp(final String objectName, final RTStageProp stagePropType);
	public void addRole(final String objectName, final RTRole roleType);
	public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl);
	public RTMethod lookupMethodIgnoringParameterInSignatureNamed(final String methodName, final List<RTType> actualParameterStaticTypes, final ActualOrFormalParameterList pl, final String paramToIgnore);
	public RTMethod lookupMethodIgnoringParameterAtPosition(final String methodName, final List<RTType> actualParameterStaticTypes, final ActualOrFormalParameterList pl, final int paramToIgnore);
	public RTMethod lookupMethodIgnoringParameterInSignatureWithConversionNamed(final String methodName, final List<RTType> actualParameterStaticTypes, final ActualOrFormalParameterList pl, final String paramToIgnore);
	public RTMethod lookupBaseClassMethodLiskovCompliantTo(final String methodName, final ActualOrFormalParameterList pl);
	public RTMethod lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(final String methodName, final List<RTType> actualParameterStaticTypes, final ActualOrFormalParameterList pl, final int paramToIgnore);
	public Map<String, RTType> objectDeclarations();
	public void setObject(final String objectName, final RTObject object);
	public RTObject getObject(final String objectName);
	public RTObject defaultObject();
	public Map<String, RTRole> nameToRoleDeclMap();
	public Map<String, RTStageProp> nameToStagePropDeclMap();
	public String name();
}
