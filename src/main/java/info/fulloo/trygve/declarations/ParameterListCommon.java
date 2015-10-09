package info.fulloo.trygve.declarations;

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

import info.fulloo.trygve.mylibrary.SimpleList;

public abstract class ParameterListCommon implements ActualOrFormalParameterList  {
	public ParameterListCommon(SimpleList formalParameters) {
		parameters_ = formalParameters;
	}
	public int count() {
		return parameters_.count();
	}
	public Object parameterAtIndex(int i) {
		return parameters_.objectAtIndex(i);
	}
	public void insertAtStart(Object parameter) {
		parameters_.insertAtStart(parameter);
	}
	public void addArgument(Object parameter) {
		parameters_.add(parameter);
	}
	public Object argumentAtPosition(int i) {
		return (Object)parameterAtIndex(i);
	}
	// NOTE: This method is here just for genericity in
	// implementing the ActualOrFormalParameterList interface
	@Override public abstract Type typeOfParameterAtPosition(int i);
	@Override public abstract String nameOfParameterAtPosition(int i);
	@Override public ActualOrFormalParameterList mapTemplateParameters(TemplateInstantiationInfo templateTypes) {
		return this;
	}
	
	private SimpleList parameters_;
}
