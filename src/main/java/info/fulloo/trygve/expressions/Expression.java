package info.fulloo.trygve.expressions;

/*
 * Trygve IDE 1.4
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.code_generation.CodeGenerator;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.BodyPart;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Message;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.DeclarationList;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.StagePropType;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.parser.ParsingData;
import info.fulloo.trygve.parser.Pass0Listener;
import info.fulloo.trygve.parser.Pass1Listener;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.run_time.RTExpression.*;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public abstract class Expression implements BodyPart, ExpressionStackAPI {
	public static Expression makeConstantExpressionFrom(final String s) {
		return Constant.makeConstantExpressionFrom(s);
	}
	
	@Override public String getText() {
		return "???";
	}
	
	@Override public int lineNumber() {
		return 1;
	}
	
	
	public static class NullExpression extends Expression {
		public NullExpression() {
			super("null", StaticScope.globalScope().lookupTypeDeclaration("Null"), null);
		}
		@Override public String getText() {
			return "<NULL>";
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			/* Trivial */
			List<RTCode> retval = new ArrayList<RTCode>();
			retval.add(new RTNullExpression());
			return retval;
		}
	}
	
	public static class QualifiedIdentifierExpression extends Expression {
		public QualifiedIdentifierExpression(final Expression qualifier, final String id, final Type idType) {
			super(id, idType, qualifier.enclosingMegaType());
			qualifier_ = qualifier;
			if (null == idType) {
				assert null != idType;
			}
			qualifier_.setResultIsConsumed(true);
		}
		@Override public String getText() {
			return qualifier_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileQualifiedIdentifierExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public Expression qualifier() {
			return qualifier_;
		}
		@Override public int lineNumber() {
			return qualifier_.lineNumber();
		}
		
		private final Expression qualifier_;
	}
	
	public static class QualifiedIdentifierExpressionUnaryOp extends Expression {
		public QualifiedIdentifierExpressionUnaryOp(Expression qualifier, String id, Type idType, String operator, PreOrPost preOrPost) {
			super(id, idType, qualifier.enclosingMegaType());
			qualifier_ = qualifier;
			qualifier_.setResultIsConsumed(true);
			preOrPost_ = preOrPost;
			operator_ = operator;
		}
		@Override public String getText() {
			return qualifier_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileQualifiedIdentifierExpressionUnaryOp(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public Expression qualifier() {
			return qualifier_;
		}
		public PreOrPost preOrPost() {
			return preOrPost_;
		}
		public String operator() {
			return operator_;
		}
		@Override public int lineNumber() {
			return qualifier_.lineNumber();
		}
		
		private final Expression qualifier_;
		private final PreOrPost preOrPost_;
		private final String operator_;
	}
	
	public static class QualifiedClassMemberExpression extends Expression {
		public QualifiedClassMemberExpression(ClassType theClass, String id, Type type) {
			super(id, type, type);
			theClass_ = theClass;
		}
		@Override public String getText() {
			return theClass_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileQualifiedClassMemberExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public ClassType qualifier() {
			return theClass_;
		}
		
		private final ClassType theClass_;
	}
	
	public static class QualifiedClassMemberExpressionUnaryOp extends Expression {
		public QualifiedClassMemberExpressionUnaryOp(ClassType theClass, String id, Type type, String operator, PreOrPost preOrPost) {
			super(id, type, type);
			theClass_ = theClass;
			preOrPost_ = preOrPost;
			operator_ = operator;
		}
		@Override public String getText() {
			return theClass_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileQualifiedClassMemberExpressionUnaryOp(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public ClassType qualifier() {
			return theClass_;
		}
		public PreOrPost preOrPost() {
			return preOrPost_;
		}
		public String operator() {
			return operator_;
		}
		
		private final ClassType theClass_;
		private final PreOrPost preOrPost_;
		private final String operator_;
	}
	
	public static class MessageExpression extends Expression
	{
		public MessageExpression(final Expression object, final Message message,
				final Type type, final int lineNumber, final boolean isStatic,
				final MethodInvocationEnvironmentClass originMethodClass,
				final MethodInvocationEnvironmentClass targetMethodClass) {
			super(message.selectorName(), type, message.enclosingMegaType());

			object_ = object;
			message_ = message;
			lineNumber_ = lineNumber;
			isStatic_ = isStatic;
			originMessageClass_ = originMethodClass;
			targetMessageClass_ = targetMethodClass;
		}
		@Override public String getText() {
			return object_.getText() + "." + message_.getText();
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileMessageExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public Message message() {
			return message_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		public Type returnType() {
			return message_.returnType();
		}
		public Expression objectExpression() {
			return object_;
		}
		public boolean isStatic() {
			return isStatic_;
		}
		public MethodInvocationEnvironmentClass originMessageClass() {
			return originMessageClass_;
		}
		public MethodInvocationEnvironmentClass targetMessageClass() {
			return targetMessageClass_;
		}
		
		private final MethodInvocationEnvironmentClass originMessageClass_, targetMessageClass_;
		private final Expression object_;
		private final Message message_;
		private final int lineNumber_;
		private final boolean isStatic_;
	}
	
	public static class DupMessageExpression extends Expression
	{
		public DupMessageExpression(Expression object, Type type) {
			super("clone", type, object.enclosingMegaType());
			object_ = object;
			object_.setResultIsConsumed(true);
		}
		@Override public String getText() {
			return object_.getText() + ".clone";
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileDupMessageExpression(this, rtTypeDeclaration, scope);
		}
		public Expression objectToClone() {
			return object_;
		}
		
		private final Expression object_;
	}
	
	public static class IdentifierExpression extends Expression
	{
		public IdentifierExpression(final String id, final Type type, final StaticScope scopeWhereDeclared,
				final int lineNumber) {
			super(id, type, Expression.nearestEnclosingMegaTypeOf(scopeWhereDeclared));

			scopeWhereDeclared_ = scopeWhereDeclared;
			lineNumber_ = lineNumber;
		}
		@Override public String getText() {
			return name();
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileIdentifierExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public StaticScope scopeWhereDeclared() {
			return scopeWhereDeclared_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		
		private final StaticScope scopeWhereDeclared_;
		private final int lineNumber_;
	}
	
	public static class RelopExpression extends Expression
	{
		public RelopExpression(Expression lhs, String operator, Expression rhs) {
			super(operator, StaticScope.globalScope().lookupTypeDeclaration("boolean"), lhs.enclosingMegaType());
			
			if ( operator.equals("==") || operator.equals("!=") || operator.equals(">")
					|| operator.equals("<")  || operator.equals(">=") || operator.equals("<=")
					|| operator.equals("||") || operator.equals("&&") || operator.equals("^")) {
				;
			} else {
				assert operator.equals("==") || operator.equals("!=") || operator.equals(">")
				|| operator.equals("<")  || operator.equals(">=") || operator.equals("<=")
				|| operator.equals("||") || operator.equals("&&") || operator.equals("^");
			}
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			operator_ = operator;
		}
		public String operator() {
			return operator_;
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			String retval = "[" + lhs_.getText() + " " + operator_ + " " + rhs_.getText() + "]";
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileRelopExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class BooleanExpression extends Expression
	{
		public BooleanExpression(final Expression lhs, final String operator, final Expression rhs) {
			super(operator, StaticScope.globalScope().lookupTypeDeclaration("boolean"), lhs.enclosingMegaType());
			assert operator.equals("&&") || operator.equals("||") || operator.equals("^")
				|| (operator.equals("!") && null == rhs);
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			if (null != rhs_) {
				rhs_.setResultIsConsumed(true);
			}
			operator_ = operator;
		}
		public String operator() {
			return operator_;
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			String retval = "[" + lhs_.getText() + " " + operator_ + " " + rhs_.getText() + "]";
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileBooleanExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class BinopExpression extends Expression
	{
		public BinopExpression(final Expression lhs, final String operator, final Expression rhs) {
			// For unary operators, one or the other of lhs or rhs could be null
			super(operator, null != lhs? lhs.type(): rhs.type(), null != lhs? lhs.enclosingMegaType(): rhs.enclosingMegaType());
			assert operator.equals("*") || operator.equals("/") || operator.equals("+") || operator.equals("-")
									    || operator.equals("%");
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			operator_ = operator;
		}
		public String operator() {
			return operator_;
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			String retval = "[" + lhs_.getText() + " " + operator_ + " " + rhs_.getText() + "]";
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileBinopExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class UnaryopExpressionWithSideEffect extends Expression
	{
		public enum PreOrPost { Pre, Post };
		public UnaryopExpressionWithSideEffect(Expression lhs, String operator, PreOrPost preOrPost) {
			// For unary operators, one or the other of lhs or rhs could be null
			super(operator, lhs.type(), lhs.enclosingMegaType());
			assert operator.equals("++") || operator.equals("--");
			lhs_ = lhs;
			lhs_.setResultIsConsumed(true);
			operator_ = operator;
			preOrPost_ = preOrPost;
		}
		public String operator() {
			return operator_;
		}
		public Expression lhs() {
			return lhs_;
		}
		@Override public String getText() {
			String retval = preOrPost_ == PreOrPost.Post?
									"[ " + lhs_.getText() + operator_ + " ]":
									"[ " +  operator_ + lhs_.getText()  + " ]";
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileUnaryopExpressionWithSideEffect(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public PreOrPost preOrPost() {
			return preOrPost_;
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private final Expression lhs_;
		private final String operator_;
		private final PreOrPost preOrPost_;
	}
	
	public static class UnaryAbelianopExpression extends Expression
	{
		public UnaryAbelianopExpression(final Expression rhs, final String operator,
				final Pass0Listener pass) {
			// For unary operators, one or the other of lhs or rhs could be null
			super(operator, rhs.type(), rhs.enclosingMegaType());
			assert operator.equals("+") || operator.equals("-") || operator.equals("!");
			final Type type = rhs.type();
			if (type.hasUnaryOperator(operator)) {
				;		// is O.K.
			} else {
				pass.errorHook5p2(ErrorType.Fatal, rhs.lineNumber(),
						"The unary operator `" + operator,"' does not apply to type ",
						type.getText(), ".");
			}
			rhs_ = rhs;
			rhs_.setResultIsConsumed(true);
			operator_ = operator;
		}
		public String operator() {
			return operator_;
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			String retval = "[ " + operator_ + rhs_.getText() + " ]";
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileUnaryAbelianopExpression(this, operator_, scope, rtTypeDeclaration);
		}
		@Override public int lineNumber() {
			return rhs_.lineNumber();
		}
		
		private final Expression rhs_;
		private final String operator_;
	}
	
	public static class DoubleCasterExpression extends Expression {
		public DoubleCasterExpression(final Expression rhs) {
			super("[(double)" + rhs.getText() + "]",
					StaticScope.globalScope().lookupTypeDeclaration("double"),
					rhs.enclosingMegaType());
			rhs_ = rhs;
			rhs_.setResultIsConsumed(true);
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			final String retval = "(double) " + rhs_.getText();
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileDoubleCasterExpression(this, rtTypeDeclaration);
		}
		@Override public int lineNumber() {
			return rhs_.lineNumber();
		}
		
		private final Expression rhs_;
	}
	
	public static class AssignmentExpression extends Expression
	{
		public AssignmentExpression(final Expression lhs, final String operator, final Expression rhs, final int lineNumber, final Pass0Listener parser) {
			super("[" + lhs.getText() + " = " + rhs.getText() + "]", lhs.type(), lhs.enclosingMegaType());
			assert operator.equals("=");
			
			lhs_ = lhs;
			rhs_ = rhs;
			lineNumber_ = lineNumber;
			doTrivialConversions(parser);
			rhs_.setResultIsConsumed(true);
			
			identifierBindingCheck(lhs, rhs, parser);
		}
		private void doTrivialConversions(final Pass0Listener parser) {
			// Should be pathnames. FIXME (easy fix).
			if (null != lhs_ && null != rhs_ && null != lhs_.type() && null != rhs_.type()) {		// error stumbling check
				if (lhs_.type().name().equals("double")) {
					if (rhs_.type().name().equals("int")) {
						parser.errorHook6p2(ErrorType.Warning, lineNumber_,
								"WARNING: Substituting double object for `", rhs_.getText(),
								"' in assignment to `", lhs_.getText(), "'.",
								"");
						rhs_ = new DoubleCasterExpression(rhs_);
						rhs_.setResultIsConsumed(true);
					}
				}
			}
		}
		private boolean isIdentifierExpression(final Expression e) {
			boolean retval = false;
			if (e instanceof IdentifierExpression) {
				retval = true;
			} else if (e instanceof QualifiedIdentifierExpression) {
				retval = true;
			}
			return retval;
		}
		private void identifierBindingCheck(final Expression lhs, final Expression rhs, final Pass0Listener parser) {
			if (isIdentifierExpression(lhs) && isIdentifierExpression(rhs)) {
				final Type lhsType = lhs.type(), rhsType = rhs.type();
				if (lhsType.pathName().equals("int.") || lhsType.pathName().equals("double.") || lhsType.pathName().equals("String.")) {
					if (lhsType.pathName().equals(rhsType.pathName())) {
						parser.errorHook6p2(ErrorType.Warning, lhs.lineNumber(),
								"WARNING: Assignment / initialization does not create a new instance. Both `", lhs.name(),
								"' and `" + rhs.name(), "' will refer to the same object. Use `",
								rhs.name(), ".clone' to create a separate instance.");
					}
				}
			}
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			final String retval = lhs_.getText() + " = " + rhs_.getText();
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileAssignmentExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private Expression rhs_;
		private final Expression lhs_;
		private final int lineNumber_;
	}
	
	public static class NewExpression extends Expression
	{
		public NewExpression(final Type classType, final Message message, final int lineNumber, final Type enclosingMegaType) {
			super("new", classType, enclosingMegaType);
			message_ = message;

			classOrContextType_ = classType;
			lineNumber_ = lineNumber;
		}
		public Type classType() {
			return classOrContextType_;
		}
		public ActualArgumentList argumentList() {
			return message_.argumentList();
		}
		public Message message() {
			return message_;
		}
		@Override public String getText() {
			return "[new " + classOrContextType_.name() + "]";
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileNewExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		
		private final Type classOrContextType_;
		private final Message message_;
		private final int lineNumber_;
	}
	
	public static class NewArrayExpression extends Expression
	{
		// | 'new' type_name '[' expr ']'
		public NewArrayExpression(final Type classType, final Expression sizeExpression, final Type enclosingMegaType) {
			super("new", new ArrayType(classType.name() + "[" + "]", classType), enclosingMegaType);
			classOrContextType_ = classType;
			sizeExpression_ = sizeExpression;
			sizeExpression_.setResultIsConsumed(true);
		}
		@Override public Type baseType() {
			return classOrContextType_;
		}
		public Expression sizeExpression() {
			return sizeExpression_;
		}
		@Override public String getText() {
			return "[new " + classOrContextType_.name() + "[" + "] ]";
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileNewArrayExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public int lineNumber() {
			return sizeExpression_.lineNumber();
		}
		
		private final Type classOrContextType_;
		private final Expression sizeExpression_;
	}
	
	public static class ArrayIndexExpression extends Expression {
		// expr '[' expr ']'
		public ArrayIndexExpression(final ArrayExpression array, final Expression index, final int lineNumber) {
			super(array.getText() + " [ " + index.getText() + " ]", array.baseType(), array.enclosingMegaType());
			array_ = array;
			index_ = index;
			index_.setResultIsConsumed(true);
			arrayExpr_ = array;
			arrayExpr_.setResultIsConsumed(true);
			lineNumber_ = lineNumber;
		}
		@Override public String getText() {
			return array_.getText() + "[" + index_.getText() + "]";
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileArrayIndexExpression(this, scope, rtTypeDeclaration);
		}
		public Expression indexExpr() {
			return index_;
		}
		public ArrayExpression arrayExpr() {
			return arrayExpr_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		
		private final Expression array_, index_;
		private final ArrayExpression arrayExpr_;
		private final int lineNumber_;
	}
	
	public static class ArrayIndexExpressionUnaryOp extends Expression {
		// expr '[' expr ']'
		public ArrayIndexExpressionUnaryOp(final ArrayExpression array, final Expression index,
				final String operation, final PreOrPost preOrPost,
				final int lineNumber) {
			super(array.getText() + " [ " + index.getText() + " ] ++", array.baseType(), array.enclosingMegaType());
			array_ = array;
			index_ = index;
			index_.setResultIsConsumed(true);
			arrayExpr_ = array;
			arrayExpr_.setResultIsConsumed(true);
			preOrPost_ = preOrPost;
			operation_ = operation;
			lineNumber_ = lineNumber;
		}
		@Override public String getText() {
			return array_.getText() + "[" + index_.getText() + "]";
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileArrayIndexExpressionUnaryOp(this, scope, rtTypeDeclaration);
		}
		public Expression indexExpr() {
			return index_;
		}
		public ArrayExpression arrayExpr() {
			return arrayExpr_;
		}
		public String operation() {
			return operation_;
		}
		public PreOrPost preOrPost() {
			return preOrPost_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		
		private final Expression array_, index_;
		private final ArrayExpression arrayExpr_;
		private final PreOrPost preOrPost_;
		private final String operation_;
		private final int lineNumber_;
	}
	
	public static class RoleArrayIndexExpression extends Expression {
		public RoleArrayIndexExpression(final String roleName, final Expression roleNameInvocation, final Expression indexExpr) {
			super(roleName, roleNameInvocation.type(), roleNameInvocation.enclosingMegaType());
			indexExpr_ = indexExpr;
			roleNameInvocation_ = roleNameInvocation;
			roleName_ = roleName;
		}
		public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDecl, final RTType rTType, final StaticScope staticScope) {
			return codeGenerator.compileRoleArrayIndexExpression(this, rTType, staticScope);
		}
		public Expression indexExpression() {
			return indexExpr_;
		}
		public Expression roleNameInvocation() {
			return roleNameInvocation_;
		}
		public final String roleName() {
			return roleName_;
		}
		@Override public int lineNumber() {
			return roleNameInvocation_.lineNumber();
		}
		
		private final Expression indexExpr_;
		private final Expression roleNameInvocation_;
		private String roleName_;
	}
	
	public static class ArrayExpression extends Expression {
		// array_expr : expr
		public ArrayExpression(final Expression expr, final Type baseType) {
			super("array", null, expr.enclosingMegaType());
			expr_ = expr;
			baseType_ = baseType;
		}
		@Override public String getText() {
			return expr_.getText();
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileArrayExpression(this, scope);
		}
		@Override public Type baseType() {
			return baseType_;
		}
		public Expression originalExpression() {
			return expr_;
		}
		@Override public int lineNumber() {
			return expr_.lineNumber();
		}
		
		private final Expression expr_;
		private final Type baseType_;
	}
	
	public static class IfExpression extends Expression
	{
		public IfExpression(final Expression conditional, final Expression thenPart, final Expression elsePart) {
			super("if (" + conditional.getText() + ")", null != elsePart? thenPart.type(): StaticScope.globalScope().lookupTypeDeclaration("void"),
					conditional.enclosingMegaType());
			conditional_ = conditional;
			conditional_.setResultIsConsumed(true);
			thenPart_ = thenPart;
			elsePart_ = elsePart;
			
			thenPart_.setResultIsConsumed(this.resultIsConsumed());
			if (null != elsePart_) {
				elsePart_.setResultIsConsumed(this.resultIsConsumed());
			}
			
			// We tentatively set the type above. Do a better job.
			final Type thenPartType = thenPart.type(), elsePartType = (null != elsePart)? elsePart.type(): null;
			if (null != elsePartType) {
				if (thenPartType.canBeConvertedFrom(elsePartType)) {
					// Close enough to being the same type
					type_ = thenPartType;
				} else {
					// Can't agree; can treat it only as a statement.
					type_ = StaticScope.globalScope().lookupTypeDeclaration("void");
				}
			} else {
				// Have only the "then" part to deal with
				type_ = thenPartType;
			}
		}
		@Override public String getText() {
			return "if (" + conditional_.getText() + ") then " + thenPart_.getText()
					+ " else " +  elsePart_.getText();
		}
		public Expression conditional() {
			return conditional_;
		}
		public Expression thenPart() {
			return thenPart_;
		}
		public Expression elsePart() {
			return elsePart_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileIfExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public void setResultIsConsumed(boolean tf) {
			super.setResultIsConsumed(tf);
			thenPart_.setResultIsConsumed(tf);
			if (null != elsePart_) {
				elsePart_.setResultIsConsumed(tf);
			}
		}
		@Override public int lineNumber() {
			return conditional_.lineNumber();
		}
		
		private final Expression conditional_, thenPart_, elsePart_;
	}
	
	public static class ForExpression extends Expression implements BreakableExpression {
		public ForExpression(final List <ObjectDeclaration> initDecl, final Expression test, final Expression increment, final Expression body,
				final StaticScope scope, int lineNumber, final ParsingData parsingData) {
			super("for", StaticScope.globalScope().lookupTypeDeclaration("void"),
					Expression.nearestEnclosingMegaTypeOf(scope));
			initDecl_ = initDecl;
			if (null == initDecl_) {
				initDecl_ = new ArrayList<ObjectDeclaration>();
			}
			test_ = test;
			if (null != test_) {
				test_.setResultIsConsumed(true);
			}
			increment_ = increment;
			body_ = body;
			initializationExpression_ = null;
			thingToIterateOver_ = null;
			
			scope_ = scope;
			lineNumber_ = lineNumber;
			
			initExprs_ = new ArrayList<BodyPart>();
			
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
		}
		public void reInit(final Expression initializer, final Expression test, final Expression increment, final Expression body) {
			initializationExpression_ = initializer; 	// may be null
			test_ = test;
			if (null != test_) {
				test_.setResultIsConsumed(true);
			}
			increment_ = increment;
			body_ = body;
		}
		public void reInitIterativeFor(final ObjectDeclaration JAVA_ID, final Expression thingToIterateOver, final Expression body) {
			thingToIterateOver_ = thingToIterateOver;
			iterationVariable_ = JAVA_ID;
			body_ = body;
		}
		public ObjectDeclaration iterationVariable() {
			return iterationVariable_;
		}	
		public Expression thingToIterateOver() {
			return thingToIterateOver_;
		}
		public void addObjectDeclForBlock(final List<ObjectDeclaration> declarations) {
			initDecl_.addAll(declarations);
		}
		@Override public String getText() {
			return "for (" + "<declarations>" + " " +
							test_.getText() + "; " +
							increment_.getText() +
						") " + 
					body_.getText();
		}
		public List<ObjectDeclaration> initDecl() {
			return initDecl_;
		}
		public Expression initializationExpression() {
			return initializationExpression_;
		}
		public Expression test() {
			return test_;
		}
		@Override public Expression continueHook() {
			return test();
		}
		public Expression increment() {
			return increment_;
		}
		public Expression body() {
			return body_;
		}
		public StaticScope scope() {
			return scope_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			if (null == thingToIterateOver_) {
				return codeGenerator.compileForExpression(this, methodDeclaration, rtTypeDeclaration, scope);
			} else {
				return codeGenerator.compileForIterationExpression(this, methodDeclaration, rtTypeDeclaration, scope);
			}
		}
		@Override public String uniqueLabel() {
			return label_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		public void addInitExprs(List<BodyPart> bodyPart) {
			initExprs_.addAll(bodyPart);
		}
		public List<BodyPart> initExprs() {
			return initExprs_;
		}
		
		private ObjectDeclaration iterationVariable_;
		private List<ObjectDeclaration> initDecl_;
		private final List<BodyPart> initExprs_;
		private Expression initializationExpression_, test_, increment_, body_, thingToIterateOver_;
		private final StaticScope scope_;
		private final String label_;
		
		private int lineNumber_;
	}
	
	public static class WhileExpression extends Expression implements BreakableExpression {
		public WhileExpression(final Expression test, final Expression body, final int lineNumber, final ParsingData parsingData, final Type nearestEnclosingMegaType) {
			super("while", StaticScope.globalScope().lookupTypeDeclaration("void"), nearestEnclosingMegaType);
			test_ = test;
			body_ = body;		
			lineNumber_ = lineNumber;
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
		}
		public void reInit(final Expression test, final Expression body) {
			test_ = test;
			if (null != test_) {
				test_.setResultIsConsumed(true);
			}
			body_ = body;
		}
		@Override public String getText() {
			return "while (" + " " +
							test_.getText() + " " +
						") " + 
					body_.getText();
		}
		public Expression test() {
			return test_;
		}
		@Override public Expression continueHook() {
			return test();
		}
		public Expression body() {
			return body_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileWhileExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public String uniqueLabel() {
			return label_;
		}
		@Override public int lineNumber() {
			return test_.lineNumber();
		}
		
		private Expression test_, body_;
		
		@SuppressWarnings("unused")
		private final int lineNumber_;
		
		private final String label_;
	}
	
	public static class DoWhileExpression extends Expression implements BreakableExpression {
		public DoWhileExpression(final Expression test, final Expression body, final int lineNumber, final ParsingData parsingData, final Type nearestEnclosingMegaType) {
			super("do_while", StaticScope.globalScope().lookupTypeDeclaration("void"), nearestEnclosingMegaType);
			test_ = test;
			body_ = body;		
			lineNumber_ = lineNumber;
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
		}
		public void reInit(final Expression test, final Expression body) {
			test_ = test;
			if (null != test_) {
				test_.setResultIsConsumed(true);
			}
			body_ = body;
		}
		@Override public String getText() {
			return "do" + " " +
					  body_.getText() +
		           "while (" + " " +
							test_.getText()  +
						 ") ";
					
		}
		public Expression test() {
			return test_;
		}
		@Override public Expression continueHook() {
			return test();
		}
		public Expression body() {
			return body_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileDoWhileExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public String uniqueLabel() {
			return label_;
		}
		@Override public int lineNumber() {
			return test_.lineNumber();
		}
		
		private Expression test_, body_;
		
		@SuppressWarnings("unused")
		private final int lineNumber_;
		
		private final String label_;
	}
	
	public static class SwitchBodyElement extends Expression {
		public SwitchBodyElement(final Constant constant, final boolean isDefault, final ExprAndDeclList expressionAndDeclList, final Type enclosingMegaType) {
			super("case", StaticScope.globalScope().lookupTypeDeclaration("void"), enclosingMegaType);
			constant_ = constant;
			isDefault_ = isDefault;
			expressionAndDeclList_ = expressionAndDeclList;	// seems to work
		}
		
		// No compileCodeFor(CodeGenerator, MethodDeclaration, RTType) - RTSwitch fetches
		// our body parts and compiles them
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			assert(false);
			return null;
		}

		public boolean isDefault() {
			return isDefault_;
		}
		public Constant constant() {
			return constant_;
		}
		@Override public List<BodyPart> bodyParts() {
			return expressionAndDeclList_.bodyParts();
		}
		public Constant expression() {
			return constant_;
		}
		@Override public int lineNumber() {
			return expressionAndDeclList_.lineNumber();
		}
		@Override public Type type() {
			return expressionAndDeclList_.type();
		}
		public boolean hasNoBody() {
			final List<BodyPart> bodyParts = expressionAndDeclList_.bodyParts();
			boolean retval = bodyParts.size() == 0;
			return retval;
		}
		
		private final Constant constant_;
		private final boolean isDefault_;
		private final ExprAndDeclList expressionAndDeclList_;
	}
	
	public static class SwitchExpression extends Expression implements BreakableExpression {
		public SwitchExpression(final ParsingData parsingData, final Type enclosingMegaType,
				final StaticScope enclosedScope) {
			super("", StaticScope.globalScope().lookupTypeDeclaration("void"), enclosingMegaType);
			indexedSwitchBodyElements_ = new LinkedHashMap<Constant, SwitchBodyElement>();
			orderedSwitchBodyElements_ = new ArrayList<SwitchBodyElement>();
			expression_ = null;
			enclosedScope_ = enclosedScope;
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
			expressionType_ = StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		public void addSwitchBodyElement(final SwitchBodyElement element) {
			final Constant constant = element.isDefault()? null: element.constant();
			indexedSwitchBodyElements_.put(constant, element);
			orderedSwitchBodyElements_.add(element);
			hasDefault_ |= element.isDefault();
		}
		public Map<Constant, SwitchBodyElement> indexedSwitchBodyElements() {
			return indexedSwitchBodyElements_;
		}
		public List<SwitchBodyElement> orderedSwitchBodyElements() {
			return orderedSwitchBodyElements_;
		}
		public void addExpression(Expression expression) {
			expression_ = expression;
		}
		public Expression switchExpression() {
			return expression_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileSwitchExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public String uniqueLabel() {
			return label_;
		}
		@Override public Expression continueHook() {
			return null;
		}
		public boolean hasDefault() {
			return hasDefault_;
		}
		public SwitchBodyElement elementForConstant(final Constant theConstant) {
			SwitchBodyElement retval = null;
			for (Map.Entry<Constant,SwitchBodyElement> iter : indexedSwitchBodyElements_.entrySet() ) {
				final Constant key = iter.getKey();
				if (key.isEqualTo(theConstant)) {
					retval = iter.getValue();
					break;
				}
			}
			return retval;
		}
		@Override public int lineNumber() {
			return expression_.lineNumber();
		}
		@Override public Type type() {
			assert null != expressionType_;
			return expressionType_;
		}
		public void setExpressionType(final Type t) {
			expressionType_ = t;
		}
		public StaticScope enclosedScope() {
			return enclosedScope_;
		}
		
		private Map<Constant, SwitchBodyElement> indexedSwitchBodyElements_;
		private List<SwitchBodyElement> orderedSwitchBodyElements_;
		private Expression expression_;
		private final String label_;
		private boolean hasDefault_;
		private Type expressionType_;
		private final StaticScope enclosedScope_;
	}
	
	public static class BreakExpression extends Expression
	{
		public BreakExpression(final int lineNumber, final Expression loop, final long nestingLevelInsideBreakable) {
			super("", StaticScope.globalScope().lookupTypeDeclaration("void"), loop.enclosingMegaType());
			lineNumber_ = lineNumber;
			assert loop instanceof BreakableExpression;
			loop_ = (BreakableExpression)loop;
			nestingLevelInsideBreakable_ = nestingLevelInsideBreakable;
		}
		@Override public String getText() {
			return "break";
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileBreakExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public BreakableExpression loop() {
			return loop_;
		}
		public long nestingLevelInsideBreakable() {
			return nestingLevelInsideBreakable_;
		}
		
		private final int lineNumber_;
		private final BreakableExpression loop_;	// could be a switch statement, too...
		private final long nestingLevelInsideBreakable_;
	}
	public static class ContinueExpression extends Expression
	{
		public ContinueExpression(final int lineNumber, final Expression loop, final long nestingLevelInsideBreakable) {
			super("", StaticScope.globalScope().lookupTypeDeclaration("void"), loop.enclosingMegaType());
			lineNumber_ = lineNumber;
			assert loop instanceof BreakableExpression;
			loop_ = (BreakableExpression)loop;
			assert loop_ instanceof SwitchExpression == false;
			nestingLevelInsideBreakable_ = nestingLevelInsideBreakable;
		}
		@Override public String getText() {
			return "break";
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileContinueExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public BreakableExpression loop() {
			return loop_;
		}
		public long nestingLevelInsideBreakable() {
			return nestingLevelInsideBreakable_;
		}
		
		private final int lineNumber_;
		private final BreakableExpression loop_;	// can NOT be a switch statement
		private final long nestingLevelInsideBreakable_;
	}
	
	
	public static class ExpressionList extends Expression
	{
		public ExpressionList(final Expression seedExpression, final Type enclosingMegaType) {
			super("<expression list>", seedExpression.type(), enclosingMegaType);
			expressions_ = new ArrayList<Expression>();
			this.addExpression(seedExpression);
		}
		public void addExpression(final Expression e) {
			expressions_.add(e);
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			for (final Expression a : expressions_) {
				stringBuffer.append(a.getText());
				stringBuffer.append("$ ");
			}
			final String retval = stringBuffer.toString();
			return retval;
		}
		public List<Expression> expressions() {
			return expressions_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileExpressionList(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		
		// Used in Pass1Listener.java
		@Override public void setType(final Type t) {
			super.setType(t);
		}
		
		private List<Expression> expressions_;
	}
	
	public static class SumExpression extends Expression
	{
		public SumExpression(final Expression lhs, final String operator, final Expression rhs, final Token ctxGetStart, final Pass1Listener pass1Listener) {
			super("[" + lhs.getText() + " " + operator + " " + rhs.getText() + "]", lhs.type(), lhs.enclosingMegaType());
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			pass1Listener.binopTypeCheck(lhs_, operator, rhs_, ctxGetStart);
			operator_ = operator;
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("["); stringBuffer.append(lhs_.getText()); stringBuffer.append(" ");
			stringBuffer.append(operator_);  stringBuffer.append(" ");
			stringBuffer.append(rhs_.getText()); stringBuffer.append("]");
			final String retval = stringBuffer.toString();
			return retval;
		}
		@Override public Type type() {
			Type retval = lhs_.type();		// most of the time it will be this
			if (lhs_.type() instanceof RoleType || lhs_.type() instanceof StagePropType) {
				final Type type = lhs_.type();
				if (null != type) {
					final StaticScope typesScope = type.enclosedScope();
					if (null != typesScope) {
						final ActualArgumentList argumentList = new ActualArgumentList();
						argumentList.addActualArgument(rhs_);
						final Expression self = new IdentifierExpression("t$his", type, type.enclosedScope(), lhs_.lineNumber());
						argumentList.addFirstActualParameter(self);
						
						final MethodDeclaration methodDecl =
								typesScope.lookupMethodDeclarationIgnoringParameter(operator_, argumentList, "this",
										/* conversionAllowed = */ false);
						
						if (null != methodDecl) {	// stumbling check
							// Found it. Return type is type of this method.
							retval = methodDecl.returnType();
						} else {
							// Try looking in the requires section
							final Declaration associatedDeclaration =
									(type instanceof StagePropType)? ((StagePropType)type).associatedDeclaration():
																	 ((RoleType)type).associatedDeclaration();
							final Map<String, MethodSignature> requiresSection =
									(associatedDeclaration instanceof StagePropDeclaration)?
											((StagePropDeclaration)associatedDeclaration).requiredSelfSignatures():
											((RoleDeclaration)associatedDeclaration).requiredSelfSignatures();
							final MethodSignature newMethodSignature = requiresSection.get(operator_);
							if (null != newMethodSignature) {
								retval = newMethodSignature.returnType();
							} else {
								retval = StaticScope.globalScope().lookupTypeDeclaration("void");
							}
						}
					}
				}
			}
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileSumExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		public String operator() {
			return operator_;
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class ProductExpression extends Expression
	{
		public ProductExpression(final Expression lhs, final String operator, final Expression rhs, final Token ctxGetStart, final Pass1Listener pass1Listener) {
			super("[" + lhs.getText() + " " + operator + " " + rhs.getText() + "]",
					lhs.type(), lhs.enclosingMegaType());
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			promoteTypesAsNecessary();
			pass1Listener.binopTypeCheck(lhs_, operator, rhs_, ctxGetStart);
			operator_ = operator;
		}
		private void promoteTypesAsNecessary() {
			final Type lhsType = lhs_.type(), rhsType = rhs_.type();
			if (null != operator_ && operator_.equals("**")) {
				return;
			} else if (lhsType == rhsType) {
				return;
			} else if (lhsType.canBeConvertedFrom(rhsType)) {
				rhs_ = rhs_.promoteTo(lhsType);
			} else if (rhsType.canBeConvertedFrom(lhsType)) {
				lhs_ = lhs_.promoteTo(rhsType);
			} else {
				// eventually want to seek promoting both to a higher-order
				// type (like Complex) but for now, we just punt. The
				// binopTypeCheck call will bark.
			}
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("["); stringBuffer.append(lhs_.getText()); stringBuffer.append(" ");
			stringBuffer.append(operator_); stringBuffer.append(" ");
			stringBuffer.append(rhs_.getText()); stringBuffer.append("]");
			final String retval = stringBuffer.toString();
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileProductExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		public String operator() {
			return operator_;
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class PowerExpression extends Expression
	{
		public PowerExpression(final Expression lhs, final String operator, final Expression rhs, final Token ctxGetStart, final Pass1Listener pass1Listener) {
			super("[" + lhs.getText() + " " + operator + " " + rhs.getText() + "]",
					lhs.type(), lhs.enclosingMegaType());
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			promoteTypesAsNecessary();
			pass1Listener.binopTypeCheck(lhs_, operator, rhs_, ctxGetStart);
			operator_ = operator;
		}
		private void promoteTypesAsNecessary() {
			final Type lhsType = lhs_.type(), rhsType = rhs_.type();
			if (lhsType == rhsType) {
				return;
			} else if (rhsType.name().equals("int")) {
				return;
			} else if (lhsType.name().equals("int")) {
				lhs_ = lhs_.promoteTo(rhsType);
			} else {
				// eventually want to seek promoting both to a higher-order
				// type (like Complex) but for now, we just punt. The
				// binopTypeCheck call will bark.
			}
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("["); stringBuffer.append(lhs_.getText()); stringBuffer.append(" ");
			stringBuffer.append(operator_); stringBuffer.append(" ");
			stringBuffer.append(rhs_.getText()); stringBuffer.append("]");
			final String retval = stringBuffer.toString();
			return retval;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compilePowerExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		public String operator() {
			return operator_;
		}
		@Override public int lineNumber() {
			return lhs_.lineNumber();
		}
		
		private Expression lhs_;
		private final Expression rhs_;
		private final String operator_;
	}
	
	public static class TopOfStackExpression extends Expression {
		// Kind of a dummy - used in RTMethod.java. Never used on the parser side.
		public TopOfStackExpression() {
			super("", null, null);
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			assert false;
			return null;
		}
	}
	
	public static class ReturnExpression extends Expression
	{
		public ReturnExpression(final String methodName, final Expression returnExpression,
				final int lineNumber,
				final Type nearestEnclosingMegaType, final StaticScope enclosingScope) {
			super("return from " + methodName + ": returning " + (null !=  returnExpression? " " + returnExpression.getText(): "nothing"),
				  null != returnExpression? returnExpression.type():
						StaticScope.globalScope().lookupTypeDeclaration("void"),
						nearestEnclosingMegaType);
			
			returnExpression_ = returnExpression;
			lineNumber_ = lineNumber;
			
			int nestingLevelInsideMethod = 0;
			
			// Don't do it if someone passed in global scope
			// (kind of a sentinel, too...)
			if (null != enclosingScope.parentScope()) {
				StaticScope myScope = enclosingScope;
				while (myScope.associatedDeclaration() instanceof MethodDeclaration == false) {
					nestingLevelInsideMethod++;
					myScope = myScope.parentScope();
					if (null == myScope) {
						break;	// ?? FIXME
					}
				}
			}
			nestingLevelInsideMethod_ = nestingLevelInsideMethod;
		}
		@Override public String getText() {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("return");
			if (null != returnExpression_) {
				stringBuffer.append(" ");
				stringBuffer.append(returnExpression_.getText());
			}
			final String retval = stringBuffer.toString();
			return retval;
		}
		public Expression returnExpression() {
			return returnExpression_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileReturnExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		public int nestingLevelInsideMethod() {
			return nestingLevelInsideMethod_;
		}
		
		private final Expression returnExpression_;	// may be null
		private final int lineNumber_;
		private final int nestingLevelInsideMethod_;
	}
	
	public static class DummyReturnExpression extends ReturnExpression
	{
		public DummyReturnExpression(final Expression returnExpression, final int lineNumber,
				final Type nearestEnclosingMegaType, final StaticScope enclosingScope) {
			super("dummmy", returnExpression, lineNumber, nearestEnclosingMegaType, enclosingScope);
		}
	}
	
	public static class BlockExpression extends Expression
	{
		public BlockExpression(final int lineNumber, final ExprAndDeclList exprAndDeclList, final StaticScope scope, final Type enclosingMegaType) {
			super("<block>", null != exprAndDeclList? exprAndDeclList.type():
				StaticScope.globalScope().lookupTypeDeclaration("void"), enclosingMegaType);
			exprAndDeclList_ = exprAndDeclList;
			lineNumber_ = lineNumber;
			
			// Assume no initializations. This will be overwritten later if we
			// find declarations with initialization clauses
			initDecl_ = new ArrayList<ObjectDeclaration>();
			
			scope_ = scope;
		}
		@Override public String getText() {
			String retval = "{";
			if (null != exprAndDeclList_) {
				retval += " " + exprAndDeclList_.getText();
			}
			retval += " }";
			return retval;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public List<RTCode> compileCodeForInScope(final CodeGenerator codeGenerator, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
			return codeGenerator.compileBlockExpression(this, methodDeclaration, rtTypeDeclaration, scope);
		}
		@Override public List<BodyPart> bodyParts() {
			final List<BodyPart> retval = exprAndDeclList_.bodyParts();
			return retval;
		}
		public StaticScope scope() {
			return scope_;
		}
		public void addObjectDeclForBlock(final List<ObjectDeclaration> declarations) {
			initDecl_.addAll(declarations);
		}
		public List<ObjectDeclaration> initDecl() {
			return initDecl_;
		}
		
		public List<BodyPart> linearize(final BodyPart bodyPart) {
			List<BodyPart> retval = new ArrayList<BodyPart>();
			if (bodyPart instanceof DeclarationList) {
				final DeclarationList bodyPartAsDeclList = (DeclarationList) bodyPart;
				final List<BodyPart> interimList = bodyPartAsDeclList.declarations();
				for (BodyPart thisBodyPart : interimList) {
					retval.addAll(linearize(thisBodyPart));
				}
			} else if (bodyPart instanceof ExprAndDeclList) {
				final ExprAndDeclList bodyPartAsExprAndDeclList = (ExprAndDeclList) bodyPart;
				final List<BodyPart> interimList = bodyPartAsExprAndDeclList.bodyParts();
				for (BodyPart thisBodyPart : interimList) {
					retval.addAll(linearize(thisBodyPart));
				}
			} else if (bodyPart instanceof ExpressionList) {
				final ExpressionList bodyPartAsExprList = (ExpressionList) bodyPart;
				final List<BodyPart> interimList = bodyPartAsExprList.bodyParts();
				for (BodyPart thisBodyPart : interimList) {
					retval.addAll(linearize(thisBodyPart));
				}
			} else {
				retval.add(bodyPart);
			}
			return retval;
		}
		
		public void parserIsDone() {
			// The block has been populated. Intuit my type.
			// All exits from the block should in theory have
			// an expression right before them that evaluates
			// to the same type; if not, it's the programmer's
			// problem. The last statement is as good as any
			// other.
			final List<BodyPart> bodyParts = this.bodyParts();
			final int numberOfBodyParts = bodyParts.size();
			if (numberOfBodyParts > 0)	{
				final BodyPart lastBodyPart = bodyParts.get(numberOfBodyParts - 1);
				final List<BodyPart> linearizedBodyParts = linearize(lastBodyPart);
				final int numberOfLinearizedBodyParts = linearizedBodyParts.size();
				if (numberOfLinearizedBodyParts > 0) {
					final BodyPart reallyLastBodyPart = linearizedBodyParts.get(numberOfLinearizedBodyParts - 1);
					final Type lastBodyPartType = reallyLastBodyPart.type();
					super.setType(lastBodyPartType);
				} else {
					super.setType(StaticScope.globalScope().lookupTypeDeclaration("void"));
				}
			} else {
				super.setType(StaticScope.globalScope().lookupTypeDeclaration("void"));
			}
		}
		
		private final int lineNumber_;	// may be null
		private final ExprAndDeclList exprAndDeclList_;
		private final StaticScope scope_;
		private List<ObjectDeclaration> initDecl_;
	}
	
	public static class PromoteToDoubleExpr extends Expression {
		public PromoteToDoubleExpr(final Expression promotee) {
			super("(double)", StaticScope.globalScope().lookupTypeDeclaration("double"), promotee.enclosingMegaType());
			promotee_ = promotee;
			promotee_.setResultIsConsumed(true);
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration d, RTType t, StaticScope scope) {
			return codeGenerator.compilePromoteToDoubleExpression(this, scope, t);
		}
		public Expression promotee() {
			return promotee_;
		}
		@Override public int lineNumber() {
			return promotee_.lineNumber();
		}
		
		private final Expression promotee_;
	}
	
	public static class IndexExpression extends Expression {
		// IndexExpression is simply an invocation of the identifier "index",
		// but its use is limited to specific contexts. Currently it can
		// be used only inside a Role method where the Role is declared
		// as a Role vector. The resulting type is an integer reflecting
		// the position (0-indexed) of the Role in the vector
		public IndexExpression(final RoleDeclaration currentRole, final ContextDeclaration currentContext) {
			super("index", StaticScope.globalScope().lookupTypeDeclaration("int"), currentRole.type());
			enclosingRole_ = currentRole;
			enclosingContext_ = currentContext;
		}
		@Override public String getText() {
			return name();
		}
		@Override public List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
			return codeGenerator.compileIndexExpression(this);
		}
		public String roleName() {
			return enclosingRole_.name();
		}
		@Override public int lineNumber() {
			return enclosingRole_.lineNumber();
		}
		
		final private RoleDeclaration enclosingRole_;
		
		@SuppressWarnings("unused")
		final private ContextDeclaration enclosingContext_;
	}
	
	// This is part of the endeavor to add methods to naked
	// array objects. See also Type.java, method ArrayType.sizeMethodDeclaration.
	private Type finesseArrayType(final Type type) {
		Type retval = type;
		if (type instanceof ArrayType) {
			final String typeName = type.name();
			StaticScope scopeOfType = null;
			if (null == type.enclosedScope()) {
				scopeOfType = StaticScope.globalScope();
			} else {
				scopeOfType = type.enclosedScope().parentScope();
			}
			final Type lookedUpType = scopeOfType.lookupTypeDeclaration(typeName);
			if (null != lookedUpType) {
				retval = lookedUpType;
			} else {
				scopeOfType.declareType(type);
			}
		}
		return retval;
	}
	
	public Expression(final String id, final Type type, final Type enclosingMegaType) {
		id_ = id;
		final Type newType = this.finesseArrayType(type);
		type_ = newType;
		resultIsConsumed_ = false;
		enclosingMegaType_ = enclosingMegaType;
	}
	public Type enclosingMegaType() {
		return enclosingMegaType_;
	}
	public Type type() {
		return type_;
	}
	public Type baseType() {
		return null;
	}
	public String name() {
		return id_;
	}
	public List<BodyPart> bodyParts() {
		// Returning a null list (default for most kinds
		// of expressions) terminates the recursion in any
		// recursive tree-walk
		final List<BodyPart> retval = new ArrayList<BodyPart>();
		retval.add(this);
		return retval;
	}
	
	public static Type nearestEnclosingMegaTypeOf(StaticScope scope) {	// FIXME? not final
		Type retval = null;
		if (null != scope) {
			while (scope != StaticScope.globalScope()) {
				final Declaration associatedDeclaration = scope.associatedDeclaration();
				if (associatedDeclaration instanceof ClassDeclaration) {
					retval = associatedDeclaration.type(); break;
				} else if (associatedDeclaration instanceof StagePropDeclaration) {
					retval = associatedDeclaration.type(); break;
				} else if (associatedDeclaration instanceof RoleDeclaration) {
					retval = associatedDeclaration.type(); break;
				} else if (associatedDeclaration instanceof ContextDeclaration) {
					retval = associatedDeclaration.type(); break;
				} else if (associatedDeclaration instanceof TemplateDeclaration) {
					retval = associatedDeclaration.type(); break;
				}
				scope = scope.parentScope();
			}
		}
		return retval;
	}
	
	public static StaticScope nearestEnclosingMethodScopeAround(final StaticScope scopeArg) {
		StaticScope scopeRunner = scopeArg, retval = null;
		while (scopeRunner != StaticScope.globalScope()) {
			final Declaration associatedDeclaration = scopeRunner.associatedDeclaration();
			
			// NOTE: associatedDeclaration may be null for BlockDeclarations. No
			// problem. Their parentScope field is still O.K., and we can just
			// keep walking up the tree.
			if (associatedDeclaration instanceof MethodDeclaration) {
				retval = scopeRunner;
				break;
			}
			scopeRunner = scopeRunner.parentScope();
		}
		return retval;
	}
	protected String forgeLabel() {
		labelCounter_++;
		return "LoopLabel_" + String.valueOf(labelCounter_);
	}
	public boolean resultIsConsumed() {
		return resultIsConsumed_;
	}
	public void setResultIsConsumed(boolean tf) {
		resultIsConsumed_ = tf;
	}
	public Expression promoteTo(final Type t) {
		Expression retval = this;
		if (this.type() == StaticScope.globalScope().lookupTypeDeclaration("int")) {
			if (t.type() == StaticScope.globalScope().lookupTypeDeclaration("double")) {
				retval = new PromoteToDoubleExpr(this);
			}
		}
		return retval;
	}

	protected void setType(final Type t) {
		type_ = t;
	}	
	public abstract List<RTCode> compileCodeForInScope(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	
	private final String id_;
	protected Type type_;
	private static long labelCounter_ = 0;
	private boolean resultIsConsumed_;
	final Type enclosingMegaType_;
	
}
