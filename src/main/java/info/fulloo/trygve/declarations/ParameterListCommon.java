package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 1.6
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

import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.mylibrary.SimpleList;
import info.fulloo.trygve.declarations.Type.VarargsType;

public abstract class ParameterListCommon implements ActualOrFormalParameterList  {
	public ParameterListCommon(final SimpleList formalParameters) {
		parameters_ = formalParameters;
		containsVarargs_ = false;
	}
	public int count() {
		return parameters_.count();
	}
	public Object parameterAtIndex(final int i) {
		Object retval = null;
		if (i < parameters_.count()) {
			retval = parameters_.objectAtIndex(i);
		} else {
			// for error stumbling
			retval = null;
		}
		return retval;
	}
	private Type typeOf(final Object parameter) {
		Type type = null;
		if (parameter instanceof Expression) {
			final Expression parameterAsExpression = (Expression) parameter;
			type = parameterAsExpression.type();
		} else if (parameter instanceof Declaration) {
			final Declaration parameterAsDeclaration = (Declaration) parameter;
			type = parameterAsDeclaration.type();
		}
		return type;
	}
	public void insertAtStart(final Object parameter) {
		containsVarargs_ |= typeOf(parameter) instanceof VarargsType;
		parameters_.insertAtStart(parameter);
	}
	public void replaceFirstParameter(final Object parameter) {
		containsVarargs_ |= typeOf(parameter) instanceof VarargsType;
		parameters_.replaceFirstWith(parameter);
	}
	public void addArgument(final Object parameter) {
		final Type type = typeOf(parameter);
		containsVarargs_ |= type instanceof VarargsType;
		parameters_.add(parameter);
	}
	public Object argumentAtPosition(final int i) {
		return (Object)parameterAtIndex(i);
	}
	// NOTE: This method is here just for genericity in
	// implementing the ActualOrFormalParameterList interface
	@Override public abstract Type typeOfParameterAtPosition(int i);
	@Override public abstract String nameOfParameterAtPosition(int i);
	@Override public ActualOrFormalParameterList mapTemplateParameters(TemplateInstantiationInfo templateTypes) {
		return this;
	}
	@Override public boolean containsVarargs() {
		return containsVarargs_;
	}
	@Override public boolean isntError() {
		return isError() == false;
	}
	
	private final SimpleList parameters_;
	protected boolean containsVarargs_;
}
