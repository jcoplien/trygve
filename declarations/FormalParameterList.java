package declarations;

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

import java.util.List;

import declarations.Declaration.ObjectDeclaration;
import declarations.Type.TemplateParameterType;
import declarations.Type.TemplateType;
import mylibrary.SimpleList;


public class FormalParameterList extends ParameterListCommon implements ActualOrFormalParameterList {
	public FormalParameterList() {
		super(new SimpleList());
	}
	public void addFormalParameter(Declaration parameter) {
		insertAtStart(parameter);
	}
	public ObjectDeclaration parameterAtPosition(int i) {
		return (ObjectDeclaration) parameterAtIndex(i);
	}
	public boolean alignsWith(ActualOrFormalParameterList pl) {
		return FormalParameterList.alignsWithParameterListIgnoringParam(this, pl, null);
	}
	public static boolean alignsWithParameterListIgnoringParam(ActualOrFormalParameterList pl1, ActualOrFormalParameterList pl2, String paramToIgnore) {
		boolean retval = true;
		final int myCount = pl1.count();
		if (null == pl2) {
			if (myCount != 0) {
				retval = false;
			} else {
				// Redundant, but clear
				retval = true;
			}
		} else {
			final int plCount = pl2.count();
			if (plCount != myCount) {
				retval = false;
			} else {
				for (int i = 0; i < plCount; i++) {
					final String plName = pl2.nameOfParameterAtPosition(i);
					if (null != plName && null != paramToIgnore && plName.equals(paramToIgnore)) {
						continue;
					}
					final Type plt = pl2.typeOfParameterAtPosition(i);
					final Type myt = pl1.typeOfParameterAtPosition(i);
					if (plt.enclosedScope() == myt.enclosedScope()) {
						continue;
					} else if (plt.isBaseClassOf(myt)) {
						continue;
					} else {
						retval = false;
						break;
					}
				}
			}
		}
		return retval;
	}
	
	@Override public Type typeOfParameterAtPosition(int i) {
		return parameterAtPosition(i).type();
	}
	@Override public String nameOfParameterAtPosition(int i) {
		return parameterAtPosition(i).name();
	}
	@Override public ActualOrFormalParameterList mapTemplateParameters(TemplateInstantiationInfo templateTypes) {
		final FormalParameterList retval = new FormalParameterList();
		for (int i = count() - 1; i >= 0; --i) {
			final ObjectDeclaration aParameter = parameterAtPosition(i);
			final Type typeOfParameter = typeOfParameterAtPosition(i);
			
			// This method's scope has been been given a templateTypes
			// list only if that scope corresponds to an instantiated
			// class. We can get here for the lookup in the initial template,
			// in which case templateTypes.size() == 0. 
			if (typeOfParameter instanceof TemplateParameterType && templateTypes.size() > 0) {
				assert templateTypes.size() > i - 1;
				final ObjectDeclaration substituteDecl = new ObjectDeclaration(
						aParameter.name(), templateTypes.get(i - 1), aParameter.lineNumber());
				retval.addFormalParameter(substituteDecl);
			} else if (typeOfParameter instanceof TemplateType) {
				final ObjectDeclaration substituteDecl = new ObjectDeclaration(
						aParameter.name(), templateTypes.classType(), aParameter.lineNumber());
				retval.addFormalParameter(substituteDecl);
			} else {
				retval.addFormalParameter(aParameter);
			}
		}
		return retval;
	}
}
