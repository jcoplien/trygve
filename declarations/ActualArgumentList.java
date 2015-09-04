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

public class ActualArgumentList implements ActualOrFormalParameterList{
	public ActualArgumentList() {
		arguments_ = new SimpleList();
	}
	public void addActualArgument(Expression argument) {
		// arguments_.insertAtStart(argument);
		arguments_.add(argument);
	}
	public Expression argumentAtPosition(int i) {
		return (Expression)arguments_.objectAtIndex(i);
	}
	
	// NOTE: This method is here just for genericity in
	// implementing the ActualOrFormalParameterList interface
	public Type typeOfParameterAtPosition(int i) {
		return this.argumentAtPosition(i).type();
	}
	
	public String nameOfParameterAtPosition(int i) {
		return this.argumentAtPosition(i).name();
	}
	
	public int count() {
		return arguments_.count();
	}
	public String getText() {
		String retval = "";
		final int l = arguments_.count();
		for (int i = 0; i < l; i++) {
			Expression e = (Expression)arguments_.objectAtIndex(i);
			retval += e.getText();
			if (i < l-1) retval += ", ";
		}
		return retval;
	}
	public void addFirstActualParameter(Expression e) {
		arguments_.insertAtStart(e);
	}
	
	private SimpleList arguments_;
}
