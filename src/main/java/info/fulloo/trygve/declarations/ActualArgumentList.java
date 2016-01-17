package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 1.1
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

import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.expressions.Constant;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.ArrayExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.Expression.NullExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedIdentifierExpression;
import info.fulloo.trygve.mylibrary.SimpleList;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class ActualArgumentList extends ParameterListCommon implements ActualOrFormalParameterList {
	public ActualArgumentList() {
		super(new SimpleList());
	}
	public void addActualArgument(final Expression argument) {
		addArgument(argument);
		
		// We don't want this popped prematurely. I don't know
		// how often this is a problem, but it's at least a
		// problem for NULL literals. The reason is that NullExpression
		// serves both as a No-Op and as the Null Object. Most of
		// the time it doesn't have a stack appearance. But if we
		// say the result is consumed, then at run time the
		// RTNullExpression will push an RTNullObject when its
		// run() method is invoked.
		argument.setResultIsConsumed(true);
	}
	public Expression parameterAtPosition(final int i) {
		return (Expression)this.parameterAtIndex(i);
	}
	public String getText() {
		String retval = "";
		final int l = count();
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("(");
		for (int i = 0; i < l; i++) {
			final Expression e = (Expression)parameterAtIndex(i);
			final Type t = e.type();
			if (null == t) {
				stringBuffer.append("NULL");
			} else {
				stringBuffer.append(t.getText());
			}
			stringBuffer.append(" ");
			stringBuffer.append(e.getText());
			if (i < l-1) stringBuffer.append(", ");
		}
		stringBuffer.append(")");
		retval = stringBuffer.toString();
		return retval;
	}
	public String selflessGetText() {
		// Used mainly for error reporting, where we
		// want to hide the declaration of self. And
		// we generate types here rather than expressions
		String retval = "";
		final int l = count();
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("(");
		final int afterSelfIndex = 1;
		for (int i = afterSelfIndex; i < l; i++) {
			final Expression e = (Expression)parameterAtIndex(i);
			final Type t = e.type();
			if (null == t) {
				stringBuffer.append("NULL");
			} else {
				stringBuffer.append(t.getText());
			}
			if (i < l-1) stringBuffer.append(", ");
		}
		stringBuffer.append(")");
		retval = stringBuffer.toString();
		return retval;
	}
	public void addFirstActualParameter(final Expression e) {
		insertAtStart(e);
	}
	@Override public Type typeOfParameterAtPosition(final int i) {
		return parameterAtPosition(i).type();
	}
	@Override public String nameOfParameterAtPosition(final int i) {
		return parameterAtPosition(i).name();
	}
	private Type typeMap(final TemplateInstantiationInfo templateTypes, final int i) {
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
	@Override public ActualOrFormalParameterList mapTemplateParameters(final TemplateInstantiationInfo templateTypes) {
		// templateTypes can be null if we're processing a lookup in an actual template
		ActualArgumentList retval = null;
		if (null == templateTypes) {
			return this;
		} else {
			retval = new ActualArgumentList();
			// We don't need to return an *exact* copy - just one good
			// enough for type comparison
			for (int i = 0; i < count(); i++) {
				final Expression aParameter = parameterAtPosition(i);
				final Type newType = this.typeMap(templateTypes, i);
				
				if (aParameter instanceof ArrayIndexExpression) {
					final ArrayIndexExpression aParam = (ArrayIndexExpression)aParameter;
					final ArrayExpression aParamBase = aParam.arrayExpr();
					final ArrayExpression newArrayExpr = new ArrayExpression(aParamBase.originalExpression(), newType);
					final ArrayIndexExpression newParameter = new ArrayIndexExpression(newArrayExpr, aParam.indexExpr(), aParam.lineNumber());
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof IdentifierExpression) {
					final IdentifierExpression aParam = (IdentifierExpression)aParameter;
					final IdentifierExpression newParameter = new IdentifierExpression(aParam.name(), newType, aParam.scopeWhereDeclared(), 0);
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof QualifiedIdentifierExpression) {
					final QualifiedIdentifierExpression aParam = (QualifiedIdentifierExpression)aParameter;
					final QualifiedIdentifierExpression newParameter = new QualifiedIdentifierExpression(aParam.qualifier(), aParam.name(), newType);
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof MessageExpression) {
					final MessageExpression aParam = (MessageExpression)aParameter;
					final MessageExpression newParameter = new MessageExpression(
							aParam.objectExpression(), aParam.message(), newType,
							aParam.lineNumber(), false);
					retval.addActualArgument(newParameter);
				} else if (aParameter instanceof NullExpression) {
					retval.addActualArgument(aParameter);
				} else if (aParameter instanceof Constant) {
					retval.addActualArgument(aParameter);
				} else {
					// Can always treat like an identifier and get by.
					// It's really the type that matters.
					final IdentifierExpression newParameter = new IdentifierExpression(aParameter.name(), newType, StaticScope.globalScope(), 0);
					retval.addActualArgument(newParameter);
				}
				
			}
			return retval;
		}
	}
	public ActualArgumentList copy() {
		// We need to make only a shallow copy
		final ActualArgumentList retval = new ActualArgumentList();
		final int numberOfParameters = count();
		for (int i = 0; i < numberOfParameters; i++) {
			final Expression anActualParameter = parameterAtPosition(i);
			retval.addActualArgument(anActualParameter);
		}
		return retval;
	}
}
