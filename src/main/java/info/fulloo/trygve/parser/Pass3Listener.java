package info.fulloo.trygve.parser;

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

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;

import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.NullExpression;
import info.fulloo.trygve.expressions.Expression.ReturnExpression;
import info.fulloo.trygve.parser.KantParser.Class_bodyContext;
import info.fulloo.trygve.parser.KantParser.Method_declContext;
import info.fulloo.trygve.parser.KantParser.Method_decl_hookContext;
import info.fulloo.trygve.parser.KantParser.Method_signatureContext;
import info.fulloo.trygve.parser.KantParser.ProgramContext;
import info.fulloo.trygve.parser.KantParser.Role_bodyContext;
import info.fulloo.trygve.parser.KantParser.Role_declContext;
import info.fulloo.trygve.parser.KantParser.Type_declarationContext;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public class Pass3Listener extends Pass2Listener {
	public Pass3Listener(ParsingData parsingData) {
		super(parsingData);
	}
	
	@Override public void enterExpr_or_null(@NotNull KantParser.Expr_or_nullContext ctx) {
	}
	@Override public void exitExpr_or_null(@NotNull KantParser.Expr_or_nullContext ctx) {
		// expr_or_null : expr | /* null */ ;
		// Same as pass2?
		Expression expression = null;
		if (null != ctx.expr()) {
			expression = parsingData_.popExpression();
		} else {
			expression = new NullExpression();
		}
		assert null != expression;
		parsingData_.pushExpression(expression);
	}	
	
	private <ExprType> Declaration findProperMethodScopeAround(ExprType ctxExpr, RuleContext ctxParent, Token ctxGetStart) {
		Declaration retval = null;
		RuleContext executionContext = ctxParent;
		while ((executionContext instanceof ProgramContext) == false) {
			if (executionContext instanceof Method_declContext) {
				final Method_declContext mdeclContext = (Method_declContext)executionContext;
				final Method_decl_hookContext hookContext = mdeclContext.method_decl_hook();
				final Method_signatureContext signatureCtx = hookContext.method_signature();
				final String methodName = signatureCtx.method_name().getText();
				final MethodDeclaration methodDecl = currentScope_.lookupMethodDeclarationRecursive(methodName, null, true);
				retval = methodDecl;
				break;
			} else if (executionContext instanceof Role_bodyContext) {
				break;
			} else if (executionContext instanceof Role_declContext) {
				break;
			} else if (executionContext instanceof Class_bodyContext) {
				break;
			} else if (executionContext instanceof Type_declarationContext) {
				break;
			}
			executionContext = executionContext.parent;
		}
		return retval;
	}
	
	@Override protected <ExprType> Expression expressionFromReturnStatement(ExprType ctxExpr, RuleContext ctxParent, Token ctxGetStart)
	{
		Expression returnExpression = null;
		if (null != ctxExpr) {
			returnExpression = parsingData_.popExpression();
		}
		final MethodDeclaration methodDecl = (MethodDeclaration)findProperMethodScopeAround(ctxExpr, ctxParent, ctxGetStart);
		if (null == methodDecl) {
			final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
			returnExpression = new ReturnExpression(new NullExpression(), ctxGetStart.getLine(), enclosingMegaType);
			ErrorLogger.error(ErrorType.Fatal, ctxGetStart.getLine(), "Return statement must be within a method scope ", "", "", "");
		} else {
			assert methodDecl instanceof MethodDeclaration;
			if (methodDecl.returnType() == null || methodDecl.returnType().name().equals("void")) {
				if (null == returnExpression || returnExpression.type().name().equals("void")) {
					;
				} else {
					ErrorLogger.error(ErrorType.Fatal, ctxGetStart.getLine(), "Return expression `", returnExpression.getText(), "« of type ",
							returnExpression.type().getText(), " is incompatible with method that returns no value.", "");
				}
			} else if (methodDecl.returnType().canBeConvertedFrom(returnExpression.type())) {
				;
			} else {
				ErrorLogger.error(ErrorType.Fatal, ctxGetStart.getLine(), "Return expression '", returnExpression.getText(),
						" of type ", returnExpression.type().getText(),
						" is not compatible with declared return type ", methodDecl.returnType().getText());
			}
		}
		return returnExpression;
	}
	
	@Override protected void setMethodBodyAccordingToPass(MethodDeclaration currentMethod)
	{
		final ExprAndDeclList body = parsingData_.popExprAndDecl();
		body.addAssociatedDeclaration(currentMethod);	// maybe not needed, but looks nice
		// Because we set bodies just here in Pass 3, we need
		// another pass for template instantiations to pull
		// the body code
		currentMethod.setBody(body);
	}
	
	@Override protected void typeCheck(FormalParameterList formals, ActualArgumentList actuals,
			MethodDeclaration mdecl, TypeDeclaration classdecl, @NotNull org.antlr.v4.runtime.Token ctxGetStart)
	{
		/* Nothing */
	}
	protected void processRequiredDeclarations(int lineNumber) {
		/* Nothing */
	}
	@Override protected void reportMismatchesWith(int lineNumber, RoleType lhsType, Type rhsType)
	{
		/* Nothing */
	}
	@Override public void declareObject(StaticScope s, ObjectDeclaration objdecl) { }
	@Override public void declareRole(StaticScope s, RoleDeclaration roledecl, int lineNumber) { }
	@Override public void errorHook5p1(ErrorType errorType, int i, String s1, String s2, String s3, String s4) { }
	@Override public void errorHook6p1(ErrorType errorType, int i, String s1, String s2, String s3, String s4, String s5, String s6) { }
	@Override public void errorHook5p2(ErrorType errorType, int i, String s1, String s2, String s3, String s4) { }
	@Override public void errorHook6p2(ErrorType errorType, int i, String s1, String s2, String s3, String s4, String s5, String s6) { }
}
