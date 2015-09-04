package expressions;

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

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import parser.KantParser.MessageContext;
import parser.ParsingData;
import parser.Pass1Listener;
import code_generation.CodeGenerator;
import run_time.RTCode;
import run_time.RTType;
import semantic_analysis.StaticScope;
import declarations.ActualArgumentList;
import declarations.BodyPart;
import declarations.Declaration.ClassDeclaration;
import declarations.Declaration.ContextDeclaration;
import declarations.Declaration.DeclarationList;
import declarations.Declaration.ExprAndDeclList;
import declarations.Declaration.MethodDeclaration;
import declarations.Declaration.ObjectDeclaration;
import declarations.Declaration.RoleDeclaration;
import declarations.Declaration;
import declarations.Declaration.StagePropDeclaration;
import declarations.Message;
import declarations.Type;
import declarations.Type.ClassType;
import declarations.Type.ArrayType;
import expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import run_time.RTExpression.*;


public abstract class Expression implements BodyPart, ExpressionStackAPI {
	public static Expression makeConstantExpressionFrom(String s) {
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
			super("null", StaticScope.globalScope().lookupTypeDeclaration("Null"));
		}
		@Override public String getText() {
			return "<NULL>";
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			/* Trivial */
			List<RTCode> retval = new ArrayList<RTCode>();
			retval.add(new RTNullExpression());
			return retval;
		}
	}
	
	public static class QualifiedIdentifierExpression extends Expression {
		public QualifiedIdentifierExpression(Expression qualifier, String id, Type idType) {
			super(id, idType);
			qualifier_ = qualifier;
			qualifier_.setResultIsConsumed(true);
		}
		@Override public String getText() {
			return qualifier_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileQualifiedIdentifierExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		public Expression qualifier() {
			return qualifier_;
		}
		
		private final Expression qualifier_;
	}
	
	public static class QualifiedIdentifierExpressionUnaryOp extends Expression {
		public QualifiedIdentifierExpressionUnaryOp(Expression qualifier, String id, Type idType, String operator, PreOrPost preOrPost) {
			super(id, idType);
			qualifier_ = qualifier;
			qualifier_.setResultIsConsumed(true);
			preOrPost_ = preOrPost;
			operator_ = operator;
		}
		@Override public String getText() {
			return qualifier_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileQualifiedIdentifierExpressionUnaryOp(this, methodDeclaration, rtTypeDeclaration);
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
		
		private final Expression qualifier_;
		private final PreOrPost preOrPost_;
		private final String operator_;
	}
	
	public static class QualifiedClassMemberExpression extends Expression {
		public QualifiedClassMemberExpression(ClassType theClass, String id, Type type) {
			super(id, type);
			theClass_ = theClass;
		}
		@Override public String getText() {
			return theClass_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileQualifiedClassMemberExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		public ClassType qualifier() {
			return theClass_;
		}
		
		private final ClassType theClass_;
	}
	
	public static class QualifiedClassMemberExpressionUnaryOp extends Expression {
		public QualifiedClassMemberExpressionUnaryOp(ClassType theClass, String id, Type type, String operator, PreOrPost preOrPost) {
			super(id, type);
			theClass_ = theClass;
			preOrPost_ = preOrPost;
			operator_ = operator;
		}
		@Override public String getText() {
			return theClass_.getText() + "." + name();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileQualifiedClassMemberExpressionUnaryOp(this, methodDeclaration, rtTypeDeclaration);
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
		public MessageExpression(Expression object, Message message, Type type, int lineNumber) {
			super(message.selectorName(), type);
			object_ = object;
			message_ = message;
			lineNumber_ = lineNumber;
		}
		@Override public String getText() {
			return object_.getText() + message_.getText();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileMessageExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		public Message message() {
			return message_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		
		private final Expression object_;
		private final Message message_;
		private final int lineNumber_;
	}
	
	public static class DupMessageExpression extends Expression
	{
		public DupMessageExpression(Expression object, Type type) {
			super("clone", type);
			object_ = object;
			object_.setResultIsConsumed(true);
		}
		@Override public String getText() {
			return object_.getText() + ".clone";
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileDupMessageExpression(this, rtTypeDeclaration);
		}
		public Expression objectToClone() {
			return object_;
		}
		
		private final Expression object_;
	}
	
	public static class IdentifierExpression extends Expression
	{
		public IdentifierExpression(String id, Type type, StaticScope scopeWhereDeclared){
			super(id, type);
			scopeWhereDeclared_ = scopeWhereDeclared;
		}
		@Override public String getText() {
			return name();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileIdentifierExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		public StaticScope scopeWhereDeclared() {
			return scopeWhereDeclared_;
		}
		
		private final StaticScope scopeWhereDeclared_;
	}
	
	public static class RelopExpression extends Expression
	{
		public RelopExpression(Expression lhs, String operator, Expression rhs) {
			super(operator, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
			assert operator.equals("==") || operator.equals("!=") || operator.equals(">")
				|| operator.equals("<")  || operator.equals(">=") || operator.equals("<=");
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileRelopExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class BooleanExpression extends Expression
	{
		public BooleanExpression(Expression lhs, String operator, Expression rhs) {
			super(operator, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
			assert operator.equals("&&") || operator.equals("||") || operator.equals("^")
				|| (operator.equals("!") && null == rhs);
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileBooleanExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class BinopExpression extends Expression
	{
		public BinopExpression(Expression lhs, String operator, Expression rhs) {
			// For unary operators, one or the other of lhs or rhs could be null
			super(operator, null != lhs? lhs.type(): rhs.type());
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileBinopExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class UnaryopExpressionWithSideEffect extends Expression
	{
		public enum PreOrPost { Pre, Post };
		public UnaryopExpressionWithSideEffect(Expression lhs, String operator, PreOrPost preOrPost) {
			// For unary operators, one or the other of lhs or rhs could be null
			super(operator, lhs.type());
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileUnaryopExpressionWithSideEffect(this, methodDeclaration, rtTypeDeclaration);
		}
		public PreOrPost preOrPost() {
			return preOrPost_;
		}
		
		private final Expression lhs_;
		private final String operator_;
		private final PreOrPost preOrPost_;
	}
	
	public static class UnaryAbelianopExpression extends Expression
	{
		public UnaryAbelianopExpression(Expression rhs, String operator) {
			// For unary operators, one or the other of lhs or rhs could be null
			super(operator, rhs.type());
			assert operator.equals("+") || operator.equals("-");
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileUnaryAbelianopExpression(this, operator_);
		}
		
		private final Expression rhs_;
		private final String operator_;
	}
	
	public static class AssignmentExpression extends Expression
	{
		public AssignmentExpression(Expression lhs, String operator, Expression rhs) {
			super(lhs.getText(), lhs.type());
			assert operator.equals("=");
			lhs_ = lhs;
			rhs_ = rhs;
			rhs_.setResultIsConsumed(true);
		}
		public Expression lhs() {
			return lhs_;
		}
		public Expression rhs() {
			return rhs_;
		}
		@Override public String getText() {
			String retval = lhs_.getText() + " = " + rhs_.getText();
			return retval;
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileAssignmentExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private final Expression lhs_, rhs_;
	}
	
	public static class NewExpression extends Expression
	{
		public NewExpression(Type classType, Message message, MessageContext ctx) {
			super("new", classType);
			message_ = message;
			classOrContextType_ = classType;
			lineNumber_ = ctx.getStart().getLine();
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileNewExpression(this, methodDeclaration, rtTypeDeclaration);
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
		public NewArrayExpression(Type classType, Expression sizeExpression) {
			super("new", new ArrayType(classType.name() + "[" + "]", classType));
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileNewArrayExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private final Type classOrContextType_;
		private final Expression sizeExpression_;
	}
	
	public static class ArrayIndexExpression extends Expression {
		// expr '[' expr ']'
		public ArrayIndexExpression(ArrayExpression array, Expression index) {
			super(array.getText() + " [ " + index.getText() + " ]", array.baseType());
			array_ = array;
			index_ = index;
			index_.setResultIsConsumed(true);
			arrayExpr_ = array;
			arrayExpr_.setResultIsConsumed(true);
		}
		@Override public String getText() {
			return array_.getText() + "[" + index_.getText() + "]";
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileArrayIndexExpression(this);
		}
		public Expression indexExpr() {
			return index_;
		}
		public ArrayExpression arrayExpr() {
			return arrayExpr_;
		}
		
		private final Expression array_, index_;
		private final ArrayExpression arrayExpr_;
	}
	
	public static class ArrayIndexExpressionUnaryOp extends Expression {
		// expr '[' expr ']'
		public ArrayIndexExpressionUnaryOp(ArrayExpression array, Expression index, String operation, PreOrPost preOrPost) {
			super(array.getText() + " [ " + index.getText() + " ] ++", array.baseType());
			array_ = array;
			index_ = index;
			index_.setResultIsConsumed(true);
			arrayExpr_ = array;
			arrayExpr_.setResultIsConsumed(true);
			preOrPost_ = preOrPost;
			operation_ = operation;
		}
		@Override public String getText() {
			return array_.getText() + "[" + index_.getText() + "]";
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileArrayIndexExpressionUnaryOp(this);
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
		
		private final Expression array_, index_;
		private final ArrayExpression arrayExpr_;
		private final PreOrPost preOrPost_;
		private final String operation_;
	}
	
	public static class ArrayExpression extends Expression {
		// array_expr : expr
		public ArrayExpression(Expression expr, Type baseType) {
			super("array", null);
			expr_ = expr;
			baseType_ = baseType;
		}
		@Override public String getText() {
			return expr_.getText();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileArrayExpression(this);
		}
		@Override public Type baseType() {
			return baseType_;
		}
		public Expression originalExpression() {
			return expr_;
		}
		
		private final Expression expr_;
		private final Type baseType_;
	}
	
	public static class IfExpression extends Expression
	{
		public IfExpression(Expression conditional, Expression thenPart, Expression elsePart) {
			super("if (" + conditional.getText() + ")", null != elsePart? thenPart.type(): StaticScope.globalScope().lookupTypeDeclaration("void"));
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileIfExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		@Override public void setResultIsConsumed(boolean tf) {
			super.setResultIsConsumed(tf);
			thenPart_.setResultIsConsumed(tf);
			if (null != elsePart_) {
				elsePart_.setResultIsConsumed(tf);
			}
		}
		
		private final Expression conditional_, thenPart_, elsePart_;
	}
	
	public static class ForExpression extends Expression implements BreakableExpression {
		public ForExpression(List <ObjectDeclaration> initDecl, Expression test, Expression increment, Expression body,
				StaticScope scope, int lineNumber, ParsingData parsingData) {
			super("for", StaticScope.globalScope().lookupTypeDeclaration("void"));
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
		public void reInit(Expression initializer, Expression test, Expression increment, Expression body) {
			initializationExpression_ = initializer; 	// may be null
			test_ = test;
			if (null != test_) {
				test_.setResultIsConsumed(true);
			}
			increment_ = increment;
			body_ = body;
		}
		public void reInitIterativeFor(ObjectDeclaration JAVA_ID, Expression thingToIterateOver, Expression body) {
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
		public void addObjectDeclForBlock(List<ObjectDeclaration> declarations) {
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			if (null == thingToIterateOver_) {
				return codeGenerator.compileForExpression(this, methodDeclaration, rtTypeDeclaration);
			} else {
				return codeGenerator.compileForIterationExpression(this, methodDeclaration, rtTypeDeclaration);
			}
		}
		@Override public String uniqueLabel() {
			return label_;
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
		
		@SuppressWarnings("unused")
		private int lineNumber_;
	}
	
	public static class WhileExpression extends Expression implements BreakableExpression {
		public WhileExpression(Expression test, Expression body, int lineNumber, ParsingData parsingData) {
			super("while", StaticScope.globalScope().lookupTypeDeclaration("void"));
			test_ = test;
			body_ = body;		
			lineNumber_ = lineNumber;
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
		}
		public void reInit(Expression test, Expression body) {
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileWhileExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		@Override public String uniqueLabel() {
			return label_;
		}
		
		private Expression test_, body_;
		
		@SuppressWarnings("unused")
		private final int lineNumber_;
		
		private final String label_;
	}
	
	public static class DoWhileExpression extends Expression implements BreakableExpression {
		public DoWhileExpression(Expression test, Expression body, int lineNumber, ParsingData parsingData) {
			super("do_while", StaticScope.globalScope().lookupTypeDeclaration("void"));
			test_ = test;
			body_ = body;		
			lineNumber_ = lineNumber;
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
		}
		public void reInit(Expression test, Expression body) {
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileDoWhileExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		@Override public String uniqueLabel() {
			return label_;
		}
		
		private Expression test_, body_;
		
		@SuppressWarnings("unused")
		private final int lineNumber_;
		
		private final String label_;
	}
	
	public static class SwitchBodyElement extends Expression {
		public SwitchBodyElement(Constant constant, boolean isDefault, ExprAndDeclList expressionAndDeclList) {
			super("case", StaticScope.globalScope().lookupTypeDeclaration("void"));
			constant_ = constant;
			isDefault_ = isDefault;
			expressionAndDeclList_ = expressionAndDeclList;	// seems to work
		}
		
		// No compileCodeFor(CodeGenerator, MethodDeclaration, RTType) Ñ RTSwitch fetches
		// our body parts and compiles them
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
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
		
		private final Constant constant_;
		private final boolean isDefault_;
		private final ExprAndDeclList expressionAndDeclList_;
	}
	
	public static class SwitchExpression extends Expression implements BreakableExpression {
		public SwitchExpression(ParsingData parsingData) {
			super("", StaticScope.globalScope().lookupTypeDeclaration("void"));
			indexedSwitchBodyElements_ = new HashMap<Constant, SwitchBodyElement>();
			orderedSwitchBodyElements_ = new ArrayList<SwitchBodyElement>();
			expression_ = null;
			label_ = this.forgeLabel();
			if (null != parsingData) {
				parsingData.addBreakableExpression(label_, this);
			}
		}
		public void addSwitchBodyElement(SwitchBodyElement element) {
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileSwitchExpression(this, methodDeclaration, rtTypeDeclaration);
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
		public SwitchBodyElement elementForConstant(Constant theConstant) {
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
		
		private Map<Constant, SwitchBodyElement> indexedSwitchBodyElements_;
		private List<SwitchBodyElement> orderedSwitchBodyElements_;
		private Expression expression_;
		private final String label_;
		private boolean hasDefault_;
	}
	
	public static class BreakExpression extends Expression
	{
		public BreakExpression(int lineNumber, Expression loop, long nestingLevelInsideBreakable) {
			super("", StaticScope.globalScope().lookupTypeDeclaration("void"));
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileBreakExpression(this, methodDeclaration, rtTypeDeclaration);
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
		public ContinueExpression(int lineNumber, Expression loop, long nestingLevelInsideBreakable) {
			super("", StaticScope.globalScope().lookupTypeDeclaration("void"));
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileContinueExpression(this, methodDeclaration, rtTypeDeclaration);
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
		public ExpressionList(Expression seedExpression) {
			super("<expression list>", seedExpression.type());
			expressions_ = new ArrayList<Expression>();
			this.addExpression(seedExpression);
		}
		public void addExpression(Expression e) {
			expressions_.add(e);
		}
		@Override public String getText() {
			String retval = new String();
			for (Expression a : expressions_) {
				retval += a.getText() + "$ ";
			}
			return retval;
		}
		public List<Expression> expressions() {
			return expressions_;
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileExpressionList(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private List<Expression> expressions_;
	}
	
	public static class SumExpression extends Expression
	{
		public SumExpression(Expression lhs, String operator, Expression rhs) {
			super("<sum>", lhs.type());
			lhs_ = lhs;
			rhs_ = rhs;
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			operator_ = operator;
		}
		@Override public String getText() {
			String retval = new String();
			retval += "[" + lhs_.getText() + " ";
			retval += operator_ + " ";
			retval += rhs_.getText() + "]";
			return retval;
		}
		@Override public Type type() {
			return lhs_.type();
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileSumExpression(this, methodDeclaration, rtTypeDeclaration);
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
		
		private final Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class ProductExpression extends Expression
	{
		public ProductExpression(Expression lhs, String operator, Expression rhs, Token ctxGetStart, Pass1Listener pass1Listener) {
			super("<product>", lhs.type());
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
			String retval = new String();
			retval += "[" + lhs_.getText() + " ";
			retval += operator_ + " ";
			retval += rhs_.getText() + "]";
			return retval;
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileProductExpression(this, methodDeclaration, rtTypeDeclaration);
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
		
		private Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class PowerExpression extends Expression
	{
		public PowerExpression(Expression lhs, String operator, Expression rhs, Token ctxGetStart, Pass1Listener pass1Listener) {
			super("<power>", lhs.type());
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
			String retval = new String();
			retval += "[" + lhs_.getText() + " ";
			retval += operator_ + " ";
			retval += rhs_.getText() + "]";
			return retval;
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compilePowerExpression(this, methodDeclaration, rtTypeDeclaration);
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
		
		private Expression lhs_, rhs_;
		private final String operator_;
	}
	
	public static class TopOfStackExpression extends Expression {
		// Kind of a dummy Ñ used in RTMethod.java. Never used on the parser side.
		public TopOfStackExpression() {
			super("", null);
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			assert false;
			return null;
		}
	}
	
	public static class ReturnExpression extends Expression
	{
		public ReturnExpression(Expression returnExpression, int lineNumber) {
			super(returnExpression.getText(), returnExpression.type());
			returnExpression_ = returnExpression;
			lineNumber_ = lineNumber;
		}
		@Override public String getText() {
			String retval = new String();
			retval += "return";
			if (null != returnExpression_) {
				retval += " " + returnExpression_.getText();
			}
			return retval;
		}
		public Expression returnExpression() {
			return returnExpression_;
		}
		@Override public int lineNumber() {
			return lineNumber_;
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileReturnExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		
		private final Expression returnExpression_;	// may be null
		private final int lineNumber_;
	}
	
	public static class BlockExpression extends Expression
	{
		public BlockExpression(int lineNumber, ExprAndDeclList exprAndDeclList, StaticScope scope) {
			super("<block>", null != exprAndDeclList? exprAndDeclList.type():
				StaticScope.globalScope().lookupTypeDeclaration("void"));
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
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration) {
			return codeGenerator.compileBlockExpression(this, methodDeclaration, rtTypeDeclaration);
		}
		@Override public List<BodyPart> bodyParts() {
			final List<BodyPart> retval = exprAndDeclList_.bodyParts();
			return retval;
		}
		public StaticScope scope() {
			return scope_;
		}
		public void addObjectDeclForBlock(List<ObjectDeclaration> declarations) {
			initDecl_.addAll(declarations);
		}
		public List<ObjectDeclaration> initDecl() {
			return initDecl_;
		}
		
		public List<BodyPart> linearize(BodyPart bodyPart) {
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
		public PromoteToDoubleExpr(Expression promotee) {
			super("(double)", StaticScope.globalScope().lookupTypeDeclaration("double"));
			promotee_ = promotee;
			promotee_.setResultIsConsumed(true);
		}
		@Override public List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration d, RTType t) {
			return codeGenerator.compilePromoteToDoubleExpression(this);
		}
		public Expression promotee() {
			return promotee_;
		}
		private final Expression promotee_;
	}
	
	public Expression(String id, Type type) {
		id_ = id;
		type_ = type;
		resultIsConsumed_ = false;
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
	
	public static Type nearestEnclosingMegaTypeOf(StaticScope scope) {
		while (scope != StaticScope.globalScope()) {
			final Declaration associatedDeclaration = scope.associatedDeclaration();
			if (associatedDeclaration instanceof ClassDeclaration) {
				return associatedDeclaration.type();
			} else if (associatedDeclaration instanceof StagePropDeclaration) {
				return associatedDeclaration.type();
			} else if (associatedDeclaration instanceof RoleDeclaration) {
				return associatedDeclaration.type();
			} else if (associatedDeclaration instanceof ContextDeclaration) {
				return associatedDeclaration.type();
			}
			scope = scope.parentScope();
		}
		return null;
	}
	
	public static StaticScope nearestEnclosingMethodScopeOf(StaticScope scope) {
		while (scope != StaticScope.globalScope()) {
			final Declaration associatedDeclaration = scope.associatedDeclaration();
			if (associatedDeclaration instanceof MethodDeclaration) {
				return scope;
			}
			scope = scope.parentScope();
		}
		return null;
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
	public Expression promoteTo(Type t) {
		Expression retval = this;
		if (this.type() == StaticScope.globalScope().lookupTypeDeclaration("int")) {
			if (t.type() == StaticScope.globalScope().lookupTypeDeclaration("double")) {
				retval = new PromoteToDoubleExpr(this);
			}
		}
		return retval;
	}
	protected void setType(Type t) {
		type_ = t;
	}
	
	public abstract List<RTCode> compileCodeFor(CodeGenerator codeGenerator, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration);
	
	private final String id_;
	protected Type type_;
	private static long labelCounter_ = 0;
	private boolean resultIsConsumed_;
}


