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

import mylibrary.SimpleList;
import expressions.Expression;

public class ActualArgumentList extends ParameterListCommon implements ActualOrFormalParameterList{
	public ActualArgumentList() {
		super(new SimpleList());
	}
	public void addActualArgument(Expression argument) {
		addArgument(argument);
	}
	public Expression parameterAtPosition(int i) {
		return (Expression)this.parameterAtIndex(i);
	}
	public String getText() {
		String retval = "";
		final int l = count();
		for (int i = 0; i < l; i++) {
			Expression e = (Expression)parameterAtIndex(i);
			retval += e.getText();
			if (i < l-1) retval += ", ";
		}
		return retval;
	}
	public void addFirstActualParameter(Expression e) {
		insertAtStart(e);
	}
	@Override public Type typeOfParameterAtPosition(int i) {
		return parameterAtPosition(i).type();
	}
	@Override public String nameOfParameterAtPosition(int i) {
		return parameterAtPosition(i).name();
	}
}
