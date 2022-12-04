package info.fulloo.trygve.parser;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2023 James O. Coplien, jcoplien@gmail.com
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
import java.util.Stack;

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.declarations.BodyPart;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.ExpressionList;
import info.fulloo.trygve.expressions.Expression.ReturnExpression;

// This is just a little utility class to audit basic
// properties of return statements in methods
public class ReturnStatementAudit {
	public ReturnStatementAudit(final Type returnType, final ExprAndDeclList exprAndDeclList, final Token token, final Pass1Listener pass) {
		super();
		returnType_ = returnType;
		token_ = token;
		pass_ = pass;
		
		returnExpressions_ = new Stack<ReturnExpression>();
		bodyParts_ = exprAndDeclList.bodyParts();
		somethingFollowedReturn_ = false;
		lastLineNumber_ = 1;
		
		this.extractFromExprAndDeclList(exprAndDeclList);
		this.wrapup();
	}
	public void processExpression(final BodyPart bodyPart) {
		if (bodyPart instanceof ExprAndDeclList) {
			somethingFollowedReturn_ = true;
			this.extractFromExprAndDeclList((ExprAndDeclList)bodyPart);
		} else if (bodyPart instanceof ReturnExpression) {
			somethingFollowedReturn_ = false;
			returnExpressions_.push((ReturnExpression)bodyPart);
		} else if (bodyPart instanceof ExpressionList) {
			final ExpressionList expressionList = (ExpressionList)bodyPart;
			this.extractFromExpressionList(expressionList);
		} else {
			somethingFollowedReturn_ = true;
		}
		lastLineNumber_ = lastLineNumber_ > bodyPart.lineNumber()?
							lastLineNumber_: bodyPart.lineNumber();
	}
	public void extractFromExpressionList(final ExpressionList expressionList) {
		for (final Expression expression : expressionList.expressions()) {
			this.processExpression(expression);
		}
	}
	public void extractFromExprAndDeclList(final ExprAndDeclList exprAndDeclList) {
		for (final BodyPart bodyPart : bodyParts_) {
			this.processExpression(bodyPart);
		}
	}
	public void wrapup() {
		for (final ReturnExpression retExpr : returnExpressions_) {
			if (null == returnType_) {
				if (null != retExpr.returnExpression() && retExpr.isntError()) {
					pass_.errorHook5p2(ErrorIncidenceType.Fatal, retExpr.token(), "Attempt to return value of type ",
							null == retExpr.type()? "unknown": retExpr.type().name(), " when no return value was expected.", "");
				}
			} else if (null == retExpr.type() && retExpr.isntError()) {
				pass_.errorHook5p2(ErrorIncidenceType.Internal, retExpr.token(), "Something wrong in your return expression. ",
						"Please read nearby error messages carefully.", "", "");
			} else if (returnType_.canBeConvertedFrom(retExpr.type()) == false) {
				if (null == retExpr.returnExpression() && returnType_.isntError()) {
					pass_.errorHook5p2(ErrorIncidenceType.Fatal, retExpr.token(), "Return statement with no return type cannot be converted to expected type of ",
							returnType_.getText(), "", "");
				} else if (retExpr.isntError() && returnType_.isntError()) {
					pass_.errorHook5p2(ErrorIncidenceType.Fatal, retExpr.token(), "Return statement with return type of ",
							retExpr.type().getText(), " cannot be converted to expected type of ",
							returnType_.getText());
				}
			}
		}
		if (somethingFollowedReturn_ && null != returnType_ && (false == returnType_.name().equals("void"))) {
			pass_.errorHook5p2(ErrorIncidenceType.Warning, token_, "WARNING: Possible missing return statement. ", 
					"Do you need to parenthesize the return expression?", "", "");
		}
		if (0 == returnExpressions_.size() && null != returnType_ && (false == returnType_.name().equals("void"))) {
			pass_.errorHook5p2(ErrorIncidenceType.Fatal, token_, "Missing return statement.", "", "", "");
		}
	}
	
	private Stack<ReturnExpression> returnExpressions_;
	private List<BodyPart> bodyParts_;
	private boolean somethingFollowedReturn_;
	private int lastLineNumber_;
	
	private Type returnType_;
	private Token token_;
	Pass1Listener pass_;
}
