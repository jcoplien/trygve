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

import declarations.Declaration.ObjectDeclaration;
import mylibrary.SimpleList;


public class FormalParameterList implements ActualOrFormalParameterList {
	public FormalParameterList() {
		formalParameters_ = new SimpleList();
	}
	public void addFormalParameter(Declaration parameter) {
		formalParameters_.insertAtStart(parameter);
	}
	public int count() {
		return formalParameters_.count();
	}
	public ObjectDeclaration parameterAtPosition(int i) {
		return (ObjectDeclaration) formalParameters_.objectAtIndex(i);
	}
	
	// NOTE: This method is here just for genericity in
	// implementing the ActualOrFormalParameterList interface
	public Type typeOfParameterAtPosition(int i) {
		return this.parameterAtPosition(i).type();
	}
	public String nameOfParameterAtPosition(int i) {
		return this.parameterAtPosition(i).name();
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
// System.out.print("\t");System.out.print(plCount);System.out.print(" ");System.out.print(myCount);System.out.println(" argument count disagreement");
			} else {
				for (int i = 0; i < plCount; i++) {
					final String plName = pl2.nameOfParameterAtPosition(i);
					if (null != plName && null != paramToIgnore && plName.equals(paramToIgnore)) {
						continue;
					}
// System.out.print("\t");System.out.print(i);System.out.print(": ");System.out.print(pl1.nameOfParameterAtPosition(i));System.out.print(" ");System.out.println(pl2.nameOfParameterAtPosition(i));
					final Type plt = pl2.typeOfParameterAtPosition(i);
					final Type myt = pl1.typeOfParameterAtPosition(i);
					if (plt.enclosedScope() == myt.enclosedScope()) {
						continue;
					} else if(plt.isBaseClassOf(myt)) {
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
	
	private SimpleList formalParameters_;
}
