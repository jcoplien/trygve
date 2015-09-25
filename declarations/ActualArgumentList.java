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

import semantic_analysis.StaticScope;
import declarations.Type.TemplateParameterType;
import declarations.Type.TemplateType;
import mylibrary.SimpleList;
import expressions.Constant;
import expressions.Expression;
import expressions.Expression.ArrayExpression;
import expressions.Expression.ArrayIndexExpression;
import expressions.Expression.BreakExpression;
import expressions.Expression.ContinueExpression;
import expressions.Expression.IdentifierExpression;
import expressions.Expression.MessageExpression;
import expressions.Expression.NullExpression;
import expressions.Expression.QualifiedIdentifierExpression;

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
	private Type typeMap(TemplateInstantiationInfo templateTypes, int i) {
		// This method's scope has been been given a templateTypes
		// list only if that scope corresponds to an instantiated
		// class. We can get here for the lookup in the initial template,
		// in which case templateTypes.size() == 0.
		Type retval = null;
		final Type typeOfParameter = typeOfParameterAtPosition(i);
		if (null != typeOfParameter && typeOfParameter instanceof TemplateParameterType &&
				null != templateTypes && templateTypes.size() > 0) {
			retval = templateTypes.get(i - 1);
		} else if (null != typeOfParameter && typeOfParameter instanceof TemplateType && null != templateTypes) {
			retval = templateTypes.classType();
		} else {
			retval = typeOfParameter;
		}
		return retval;
	}
	@Override public ActualOrFormalParameterList mapTemplateParameters(TemplateInstantiationInfo templateTypes) {
		// templateTypes can be null if we're processing a lookup in an actual template
		ActualArgumentList retval = null;
		if (null == templateTypes) {
			return this;
		} else {
			retval = new ActualArgumentList();
			// We don't need to return an *exact* copy Ñ just one good
			// enough for type comparison
			for (int i = 0; i < count(); i++) {
				final Expression aParameter = parameterAtPosition(i);
				final Type newType = this.typeMap(templateTypes, i);
				
				if (aParameter instanceof ArrayIndexExpression) {
					final ArrayIndexExpression aParam = (ArrayIndexExpression)aParameter;
					final ArrayExpression aParamBase = aParam.arrayExpr();
					final ArrayExpression newArrayExpr = new ArrayExpression(aParamBase.originalExpression(), newType);
					final ArrayIndexExpression newParameter = new ArrayIndexExpression(newArrayExpr, aParam.indexExpr());
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof IdentifierExpression) {
					final IdentifierExpression aParam = (IdentifierExpression)aParameter;
					final IdentifierExpression newParameter = new IdentifierExpression(aParam.name(), newType, aParam.scopeWhereDeclared());
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof QualifiedIdentifierExpression) {
					final QualifiedIdentifierExpression aParam = (QualifiedIdentifierExpression)aParameter;
					final QualifiedIdentifierExpression newParameter = new QualifiedIdentifierExpression(aParam.qualifier(), aParam.name(), newType);
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof MessageExpression) {
					final MessageExpression aParam = (MessageExpression)aParameter;
					final MessageExpression newParameter = new MessageExpression(
							aParam.objectExpression(), aParam.message(), newType, aParam.lineNumber());
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof NullExpression) {
					retval.addActualArgument(aParameter);
				} else if (aParameter instanceof Constant) {
					retval.addActualArgument(aParameter);
				} else {
					// retval.addActualArgument(aParameter);
					
					// Can always treat like an identifier and get by.
					// It's really the type that matters.
					final IdentifierExpression newParameter = new IdentifierExpression(aParameter.name(), newType, StaticScope.globalScope());
					retval.addActualArgument(newParameter);
				}
				
			}
			return retval;
		}
	}
}
