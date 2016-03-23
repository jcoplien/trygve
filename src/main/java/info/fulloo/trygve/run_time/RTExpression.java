package info.fulloo.trygve.run_time;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.BodyPart;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.ObjectSubclassDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Message;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.BreakableExpression;
import info.fulloo.trygve.expressions.Constant;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.DummyReturnExpression;
import info.fulloo.trygve.expressions.Expression.IdentityBooleanExpression;
import info.fulloo.trygve.expressions.Expression.InternalAssignmentExpression;
import info.fulloo.trygve.expressions.Expression.LastIndexExpression;
import info.fulloo.trygve.expressions.Expression.TopOfStackExpression;
import info.fulloo.trygve.expressions.MethodInvocationEnvironmentClass;
import info.fulloo.trygve.expressions.Constant.BooleanConstant;
import info.fulloo.trygve.expressions.Constant.DoubleConstant;
import info.fulloo.trygve.expressions.Constant.IntegerConstant;
import info.fulloo.trygve.expressions.Constant.StringConstant;
import info.fulloo.trygve.expressions.Expression.ArrayExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.AssignmentExpression;
import info.fulloo.trygve.expressions.Expression.BinopExpression;
import info.fulloo.trygve.expressions.Expression.BlockExpression;
import info.fulloo.trygve.expressions.Expression.BooleanExpression;
import info.fulloo.trygve.expressions.Expression.BreakExpression;
import info.fulloo.trygve.expressions.Expression.ContinueExpression;
import info.fulloo.trygve.expressions.Expression.DoWhileExpression;
import info.fulloo.trygve.expressions.Expression.DoubleCasterExpression;
import info.fulloo.trygve.expressions.Expression.DupMessageExpression;
import info.fulloo.trygve.expressions.Expression.ExpressionList;
import info.fulloo.trygve.expressions.Expression.ForExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.IfExpression;
import info.fulloo.trygve.expressions.Expression.IndexExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.Expression.NewArrayExpression;
import info.fulloo.trygve.expressions.Expression.NewExpression;
import info.fulloo.trygve.expressions.Expression.NullExpression;
import info.fulloo.trygve.expressions.Expression.PowerExpression;
import info.fulloo.trygve.expressions.Expression.ProductExpression;
import info.fulloo.trygve.expressions.Expression.PromoteToDoubleExpr;
import info.fulloo.trygve.expressions.Expression.QualifiedClassMemberExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedClassMemberExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.QualifiedIdentifierExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedIdentifierExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.RelopExpression;
import info.fulloo.trygve.expressions.Expression.ReturnExpression;
import info.fulloo.trygve.expressions.Expression.RoleArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.SumExpression;
import info.fulloo.trygve.expressions.Expression.SwitchBodyElement;
import info.fulloo.trygve.expressions.Expression.SwitchExpression;
import info.fulloo.trygve.expressions.Expression.UnaryAbelianopExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect;
import info.fulloo.trygve.expressions.Expression.WhileExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.parser.ParsingData;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTContext.RTContextInfo;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public abstract class RTExpression extends RTCode {
	public RTExpression() {
		super();
		resultIsConsumed_ = false;
		nextCode_ = null;
	}
	public static RTExpressionList bodyPartsToRTExpressionList(final List<BodyPart> bodyParts, final RTType enclosingMegaType) {
		final RTExpressionList retval = new RTExpressionList();
		if (null != retval) {
			RTExpression last = null;
			for (final BodyPart statement : bodyParts) {
				RTExpression rTStatement = null;
				if (statement instanceof Expression) {
					rTStatement = RTExpression.makeExpressionFrom((Expression)statement, enclosingMegaType);
				} else if (statement instanceof Declaration) {
					// ignore
					continue;
				} else {
					assert false;	// something wrong...
				}
				if (null != last) {
					last.setNextCode(rTStatement);
				}
				last = rTStatement;
				retval.addExpression(rTStatement);
			}
		}
		return retval;
	}

	public static RTExpression makeExpressionFrom(final Expression expr, final RTType nearestEnclosingType) {
		RTExpression retval = null;

		if (expr instanceof QualifiedIdentifierExpression) {
			retval = new RTQualifiedIdentifier(expr.name(), expr, nearestEnclosingType);
		} else if (expr instanceof QualifiedIdentifierExpressionUnaryOp) {
			retval = new RTQualifiedIdentifierUnaryOp(expr.name(), expr, nearestEnclosingType);
		} else if (expr instanceof QualifiedClassMemberExpression) {
			retval = new RTClassMemberIdentifier(expr.name(), expr);
		} else if (expr instanceof QualifiedClassMemberExpressionUnaryOp) {
			retval = new RTClassMemberIdentifierUnaryOp(expr.name(), expr);
		} else if (expr instanceof MessageExpression) {
			StaticScope scope = null;
			if (nearestEnclosingType instanceof RTClass) {
				final RTClass rTClass = (RTClass)nearestEnclosingType;
				final TypeDeclaration typeDeclaration = rTClass.typeDeclaration();
				scope = typeDeclaration.enclosedScope();
			}
			retval = RTMessage.makeRTMessage(expr.name(), (MessageExpression)expr, nearestEnclosingType, scope,
					((MessageExpression)expr).isStatic());
		} else if (expr instanceof DupMessageExpression) {
			retval = new RTDupMessage(expr.name(), (DupMessageExpression)expr, nearestEnclosingType);
		} else if (expr instanceof IdentifierExpression) {
			retval = RTIdentifier.makeIdentifier(expr.name(), (IdentifierExpression)expr);
		} else if (expr instanceof RelopExpression) {
			retval = new RTRelop((RelopExpression)expr, nearestEnclosingType);
		} else if (expr instanceof IdentityBooleanExpression) {
			retval = new RTIdentityBooleanExpression((IdentityBooleanExpression)expr, nearestEnclosingType);
		} else if (expr instanceof BooleanExpression) {
			retval = new RTBoolean((BooleanExpression)expr, nearestEnclosingType);
		} else if (expr instanceof BinopExpression) {
			retval = new RTBinop((BinopExpression)expr, nearestEnclosingType);
		} else if (expr instanceof UnaryAbelianopExpression) {
			retval = new RTUnaryAbelianop((UnaryAbelianopExpression)expr, nearestEnclosingType);
		} else if (expr instanceof UnaryopExpressionWithSideEffect) {
			retval = new RTUnaryopWithSideEffect((UnaryopExpressionWithSideEffect)expr, nearestEnclosingType);
		} else if (expr instanceof InternalAssignmentExpression) {
			retval = new RTInternalAssignment((InternalAssignmentExpression)expr, nearestEnclosingType);
		} else if (expr instanceof AssignmentExpression) {
			retval = new RTAssignment((AssignmentExpression)expr, nearestEnclosingType);
		} else if (expr instanceof IfExpression) {
			retval = new RTIf((IfExpression)expr, nearestEnclosingType);
		} else if (expr instanceof ForExpression) {
			final ForExpression exprAsFor = (ForExpression) expr;
			if (null == exprAsFor.thingToIterateOver()) {
				retval = new RTFor((ForExpression)expr, nearestEnclosingType);
			} else {
				retval = new RTForIteration((ForExpression)expr, nearestEnclosingType);
			}
		} else if (expr instanceof WhileExpression) {
			retval = new RTWhile((WhileExpression)expr, nearestEnclosingType);
		} else if (expr instanceof DoWhileExpression) {
			retval = new RTDoWhile((DoWhileExpression)expr, nearestEnclosingType);
		} else if (expr instanceof ExpressionList) {
			retval = new RTExpressionList((ExpressionList)expr, nearestEnclosingType);
		} else if (expr instanceof AssignmentExpression) {
			retval = new RTExpressionList(expr, nearestEnclosingType);
		} else if (expr instanceof SwitchExpression) {
			retval = new RTSwitch((SwitchExpression)expr, nearestEnclosingType);
		} else if (expr instanceof SwitchBodyElement) {
			// Handled by switch processing?
			assert false;
			// retval = new RTCase((SwitchBodyElement)expr);
		} else if (expr instanceof Constant) {
			retval = new RTConstant((Constant)expr);
		} else if (expr instanceof BreakExpression) {
			retval = new RTBreak((BreakExpression)expr);
		} else if (expr instanceof ContinueExpression) {
			retval = new RTContinue((ContinueExpression)expr);
		} else if (expr instanceof SumExpression) {
			retval = new RTSum((SumExpression)expr, nearestEnclosingType);
		} else if (expr instanceof ProductExpression) {
			retval = new RTProduct((ProductExpression)expr, nearestEnclosingType);
		} else if (expr instanceof PowerExpression) {
			retval = new RTPower((PowerExpression)expr, nearestEnclosingType);
		} else if (expr instanceof DummyReturnExpression) {
			retval = new RTDummyReturn(((ReturnExpression)expr).name(), (DummyReturnExpression)expr, nearestEnclosingType,
					((DummyReturnExpression) expr).nestingLevelInsideMethod());
		} else if (expr instanceof ReturnExpression) {
			retval = new RTReturn(((ReturnExpression)expr).name(), ((ReturnExpression)expr).returnExpression(),
					nearestEnclosingType,
					((ReturnExpression) expr).nestingLevelInsideMethod());
		} else if (expr instanceof BlockExpression) {
			retval = new RTBlock((BlockExpression)expr, nearestEnclosingType);
		} else if (expr instanceof NewExpression) {
			retval = new RTNew((NewExpression)expr, nearestEnclosingType);
		} else if (expr instanceof NewArrayExpression) {
			assert true;
			retval = new RTNewArray((NewArrayExpression)expr, nearestEnclosingType);
		} else if (expr instanceof ArrayExpression) {
			assert true;
			retval = new RTArrayExpression((ArrayExpression)expr, nearestEnclosingType);
		} else if (expr instanceof ArrayIndexExpression) {
			assert true;
			retval = new RTArrayIndexExpression((ArrayIndexExpression)expr, nearestEnclosingType);
		} else if (expr instanceof ArrayIndexExpressionUnaryOp) {
			retval = new RTArrayIndexExpressionUnaryOp((ArrayIndexExpressionUnaryOp)expr, nearestEnclosingType);
		} else if (expr instanceof RoleArrayIndexExpression) {
			retval = new RTRoleArrayIndexExpression((RoleArrayIndexExpression)expr, nearestEnclosingType);
		} else if (expr instanceof PromoteToDoubleExpr) {
			retval = new RTPromoteToDoubleExpr((PromoteToDoubleExpr)expr, nearestEnclosingType);
		} else if (expr instanceof DoubleCasterExpression) {
			retval = new RTDoubleCaster((DoubleCasterExpression)expr, nearestEnclosingType);
		} else if (expr instanceof NullExpression) {
			retval = new RTNullExpression();
		} else if (expr instanceof IndexExpression) {
			retval = new RTIndexExpression((IndexExpression)expr);
		} else if (expr instanceof LastIndexExpression) {
			retval = new RTLastIndexExpression((LastIndexExpression)expr);
		} else if (expr instanceof TopOfStackExpression) {
			retval = new RTTopOfStackExpression((TopOfStackExpression)expr);
		} else {
			retval = new RTNullExpression();
		}
		return retval;
	}
	protected static RTObject getObjectUpToMethodScopeFrom(final String id, final RTDynamicScope scopeArg) {
		// Like getObjectRecursive, but it stops at method scope
		RTObject retval = null;
		RTDynamicScope scope = scopeArg;
		do {
			retval = scope.getObject(id);
			scope = scope.parentScope();
		} while (null == retval && null != scope && scope.rTType() instanceof RTMethod == false);
		
		//  If we didn't find it, give it one more chance, at method level
		if (null == retval && null != scope) {
			retval = scope.getObject(id);
		}
		
		return retval;
	}
	public static class RTNullExpression extends RTExpression {
		public RTNullExpression() {
			super();
		}
		@Override public RTCode run() {
			if (resultIsConsumed()) {
				final RTNullObject nullObject = new RTNullObject();
				RunTimeEnvironment.runTimeEnvironment_.pushStack(nullObject);
				setLastExpressionResult(nullObject, 0);
			}
			return super.nextCode();
		}
	}
	public static class RTTopOfStackExpression extends RTExpression {
		public RTTopOfStackExpression(final TopOfStackExpression unused) {
			super();
		}
		@Override public RTCode run() {
			final RTStackable value = RunTimeEnvironment.runTimeEnvironment_.peekStack();
			if (value instanceof RTObject == false) {
				assert value instanceof RTObject;
			}
			if (resultIsConsumed()) {
				RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
				setLastExpressionResult((RTObject)value, 0);
			}
			return super.nextCode();
		}
	}
	public static class RTQualifiedIdentifier extends RTExpression {
		public RTQualifiedIdentifier(final String name, final Expression expr, final RTType nearestEnclosingType) {
			super();
			
			lineNumber_ = expr.lineNumber();
			part2_ = new RTQualifiedIdentifierPart2(name, lineNumber());
			final QualifiedIdentifierExpression qie = (QualifiedIdentifierExpression) expr;
			stringRep_ = qie.getText();
			
			// Stumbling check
			final RTExpression qualifier = RTExpression.makeExpressionFrom(qie.qualifier(), nearestEnclosingType);
			qualifier_ = null == qualifier? new RTNullExpression(): qualifier;
			
			qualifier_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// Evaluate the expression, leaving the result on the stack
			return RunTimeEnvironment.runTimeEnvironment_.runner(qualifier_);
		}
		public RTDynamicScope dynamicScope() {
			assert null != part2_.dynamicScope_;
			return part2_.dynamicScope_;
		}
		public String name() {
			return part2_.idName_;
		}
		public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTQualifiedIdentifierPart2 extends RTExpression {
			public RTQualifiedIdentifierPart2(final String name, final int lineNumber) {
				super();
				lineNumber_ = lineNumber;
				idName_ = name;
				dynamicScope_ = null;
			}
			@Override public RTCode run() {
				// Pop the expression off the stack
				final RTObject qualifier = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (qualifier == null) {
					// Run-time error
					ErrorLogger.error(ErrorType.Runtime, lineNumber_, "INTERNAL Error: Unitialized reference to object `",
									idName_, "'.", "");
					return new RTHalt();
				} else {
					// What's this dynamicScope_ crap? Does anybody use it?
					
					dynamicScope_ = new RTDynamicScope(idName_, qualifier, null);	// cheesy constructor -- increments ref count, don't worry
					RTObject value = qualifier.getObject(idName_);
					
					// Maybe getObject should handle Role lookup, dunno...
					// We do it here
					if (null == value && qualifier instanceof RTContextObject) {
						final RTContextObject contextQualifier = (RTContextObject) qualifier;
						value = contextQualifier.getRoleOrStagePropBinding(idName_);
					}
					
					// NOTE: Value may be instance of RTNullObject.
					
					RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
					qualifier.decrementReferenceCount();
					setLastExpressionResult(value, lineNumber_);
					return nextCode_;
				}
			}
			public int lineNumber() {
				return lineNumber_;
			}
			
			private RTDynamicScope dynamicScope_;	// this should not be in a program element. FIXME.
			private final String idName_;
			private final int lineNumber_;
		}
		public int lineNumber() {
			return lineNumber_;
		}
		public String getText() {
			return stringRep_;
		}

		private final RTExpression qualifier_;
		private final RTQualifiedIdentifierPart2 part2_;
		private final int lineNumber_;
		private final String stringRep_;
	}
	public static class RTQualifiedIdentifierUnaryOp extends RTExpression {
		public RTQualifiedIdentifierUnaryOp(final String name, final Expression expr, final RTType nearestEnclosingType) {
			super();
			lineNumber_ = expr.lineNumber();
			idName_ = name;
			dynamicScope_ = null;
			final QualifiedIdentifierExpressionUnaryOp qie = (QualifiedIdentifierExpressionUnaryOp) expr;
			qualifier_ = RTExpression.makeExpressionFrom(qie.qualifier(), nearestEnclosingType);
			operator_ = qie.operator();
			preOrPost_ = qie.preOrPost();
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// Evaluate the expression, leaving the result on the stack
			RTCode haltInstruction = RunTimeEnvironment.runTimeEnvironment_.runner(qualifier_);
			assert null == haltInstruction;
			
			// Pop the qualifier expression off the stack
			final RTObject qualifier = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			dynamicScope_ = new RTDynamicScope(this.name(), qualifier, null);	// cheesy constructor -- increments ref count, don't worry
			final RTObject value = qualifier.performUnaryOpOnObjectNamed(idName_, operator_, preOrPost_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value, lineNumber_);
			qualifier.decrementReferenceCount();
			return nextCode_;
		}
		public RTDynamicScope dynamicScope() {
			assert null != dynamicScope_;
			return dynamicScope_;
		}
		public String name() {
			return idName_;
		}

		private final String idName_;
		private final int lineNumber_;
		private final RTExpression qualifier_;
		private       RTDynamicScope dynamicScope_;
		private final String operator_;
		private final PreOrPost preOrPost_;
	}
	public static class RTClassMemberIdentifier extends RTExpression {
		public RTClassMemberIdentifier(final String name, final Expression expr) {
			super();
			lineNumber_ = expr.lineNumber();
			idName_ = name;
			
			assert expr instanceof QualifiedClassMemberExpression;
			final QualifiedClassMemberExpression qcme = (QualifiedClassMemberExpression) expr;
			theClassItself_ = qcme.qualifier();
			
			final RTType wannabeClass = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(theClassItself_.enclosedScope());
			assert wannabeClass instanceof RTClass;
			rTClass_ = (RTClass)wannabeClass; 
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			final RTObject value = rTClass_.getStaticObject(idName_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value, lineNumber_);
			return nextCode_;
		}
		public RTDynamicScope dynamicScope() {
			return null;
		}
		public String name() {
			return idName_;
		}

		private final String idName_;
		private final int lineNumber_;
		private final ClassType theClassItself_;
		private RTClass rTClass_;
	}
	public static class RTClassMemberIdentifierUnaryOp extends RTExpression {
		public RTClassMemberIdentifierUnaryOp(final String name, final Expression expr) {
			super();
			idName_ = name;
			lineNumber_ = expr.lineNumber();
			
			assert expr instanceof QualifiedClassMemberExpressionUnaryOp;
			final QualifiedClassMemberExpressionUnaryOp qcme = (QualifiedClassMemberExpressionUnaryOp) expr;
			theClassItself_ = qcme.qualifier();
			preOrPost_ = qcme.preOrPost();
			operator_ = qcme.operator();
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			final RTType wannabeClass = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(theClassItself_.enclosedScope());
			assert wannabeClass instanceof RTClass;
			rTClass_ = (RTClass)wannabeClass; 
			
			final RTObject value = rTClass_.performUnaryOpOnStaticObjectNamed(idName_, operator_, preOrPost_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value, lineNumber_);
			return nextCode_;
		}
		public RTDynamicScope dynamicScope() {
			return null;
		}
		public String name() {
			return idName_;
		}

		private String idName_;
		private ClassType theClassItself_;
		private RTClass rTClass_;
		private final String operator_;
		private final int lineNumber_;
		private final PreOrPost preOrPost_;
	}
	
	public static class RTMessage extends RTExpression {
		public static RTMessage makeRTMessage(final String name, final MessageExpression messageExpr,
				final RTType nearestEnclosingType, final StaticScope scope, final boolean isStatic) {
			final RTMessage retval = new RTMessage(name, messageExpr, nearestEnclosingType, scope, isStatic);
			return retval;
		}
		public String methodSelectorName() {		// for debugging only
			return methodSelectorName_;
		}
		public RTMessage(final String name, final MessageExpression messageExpr, final RTType nearestEnclosingType, final StaticScope scope, final boolean isStatic) {
			super();

			methodSelectorName_ = messageExpr.name();
			originMessageClass_ = messageExpr.originMessageClass();
			targetMessageClass_ = messageExpr.targetMessageClass();
			
			templateInstantiationInfo_ = null == scope? null : scope.templateInstantiationInfo();
			if (null != nearestEnclosingType && nearestEnclosingType instanceof RTClass) {
				final RTClass enclosingClass = (RTClass)nearestEnclosingType;
				templateInstantiationInfo_ = enclosingClass.templateInstantiationInfo();
			}
			
			nearestEnclosingType_ = nearestEnclosingType;
			isStatic_ = isStatic;
			
			messageExpr_ = messageExpr;
			final Message message = messageExpr_.message();
			actualParameters_ = message.argumentList();
			
			final int actualParametersCount = actualParameters_.count();
			if (0 < actualParametersCount) {
				expressionsCountInArguments_ = new int [actualParametersCount];
			} else {
				expressionsCountInArguments_ = new int[1];	// we insert a dummy
			}
			
			lineNumber_ = messageExpr.lineNumber();

			final MethodDeclaration methodDecl = this.staticLookupMethodDecl(messageExpr);
			final StaticScope parentScope = methodDecl == null? null: methodDecl.enclosingScope();
			final String debugName = parentScope == null? "???": parentScope.name();
			
			if (null != methodDecl) {
				// "if" test is error stumble insurance
				argPush_ = this.buildArgumentPushList(methodDecl.formalParameterList(), messageExpr.name(), messageExpr.lineNumber());
				assert null != argPush_;
			} else {
				argPush_ = null;
			}
			final Type returnType = messageExpr.returnType();
			final boolean resultNeedsToBePopped = (!messageExpr.resultIsConsumed()) &&
					(returnType.name().equals("void") == false);
			postReturnProcessing_ = new RTPostReturnProcessing(null, name, debugName);
			postReturnProcessing_.setResultIsConsumed(!resultNeedsToBePopped);
			super.setNextCode(postReturnProcessing_);	// necessary?
			setResultIsConsumed(!resultNeedsToBePopped);
		}
		
		public RTMessage(final String name, final ActualArgumentList actualParameters, final Type returnType,
				final Type enclosingMegaType, final boolean isStatic) {
			super();
			methodSelectorName_ = name;

			// for built-in methods like System.out.print, List
			actualParameters_ = actualParameters;
			lineNumber_ = 0;		// used by built-ins like println
			isStatic_ = isStatic;
			expressionsCountInArguments_ = new int [actualParameters_.count()];
			
			Expression fakeSelfExpression = null;
			final Message fakeMessage = new Message(name, actualParameters, lineNumber_, enclosingMegaType);
			fakeMessage.setReturnType(returnType);
			MessageExpression fakeMessageExpression = null;
			
			originMessageClass_ = enclosingMegaType.enclosedScope().methodInvocationEnvironmentClass();
			targetMessageClass_ = MethodInvocationEnvironmentClass.ClassEnvironment;
			
			if (false == isStatic) {
				fakeSelfExpression = actualParameters.parameterAtPosition(0);
				fakeMessageExpression = new MessageExpression(fakeSelfExpression, fakeMessage,
					returnType, lineNumber_, isStatic_, originMessageClass_, targetMessageClass_, true);
			} else {
				fakeSelfExpression = new IdentifierExpression(enclosingMegaType.name(), enclosingMegaType, enclosingMegaType.enclosedScope(), 0);
				fakeMessageExpression = new MessageExpression(fakeSelfExpression, fakeMessage,
						returnType, lineNumber_, isStatic_, originMessageClass_, targetMessageClass_, true);
			}

			final MethodDeclaration methodDecl = this.staticLookupMethodDecl(fakeMessageExpression);
			final StaticScope parentScope = methodDecl == null? null: methodDecl.enclosingScope();
			final String debugName = parentScope == null? "???": parentScope.name();
			
			FormalParameterList fakeParameterList = null;
			if (null != methodDecl) {
				fakeParameterList = methodDecl.formalParameterList();
			} else {
				// error stumbling catch
				fakeParameterList = new FormalParameterList();
			}
			
			argPush_ = this.buildArgumentPushList(fakeParameterList, name, lineNumber_);
			postReturnProcessing_ = new RTPostReturnProcessing(null, name, debugName);
			final boolean resultNeedsToBePopped = (returnType.name().equals("void") == false);
			setResultIsConsumed(!resultNeedsToBePopped);
			postReturnProcessing_.setResultIsConsumed(this.resultIsConsumed());
			super.setNextCode(postReturnProcessing_);
		}
		
		private MethodDeclaration staticLookupMethodDecl(final MessageExpression messageExpr) {
			final Expression objectExpression = messageExpr.objectExpression();
			Type typeOfReceiver = null;
			if (messageExpr.isStatic()) {
				assert objectExpression instanceof IdentifierExpression;
				final String className = objectExpression.name();
				typeOfReceiver = StaticScope.globalScope().lookupTypeDeclaration(className);
			} else {
				typeOfReceiver = null != objectExpression? objectExpression.type(): StaticScope.globalScope().lookupTypeDeclaration("void");
			}
			
			if (null == typeOfReceiver) {
				assert null != typeOfReceiver;
			}
			
			final StaticScope receiverScope = typeOfReceiver.enclosedScope();
			String methodSelectorName = methodSelectorName_;
			if (methodSelectorName.matches("[a-zA-Z]<.*>") || methodSelectorName.matches("[A-Z][a-zA-Z0-9_]*<.*>")) {
				final int indexOfDelimeter = methodSelectorName.indexOf('<');
				methodSelectorName = methodSelectorName.substring(0, indexOfDelimeter);
			}
			
			// Need template conversion
			ActualOrFormalParameterList parameterList = actualParameters_;
			parameterList = parameterList.mapTemplateParameters(templateInstantiationInfo_);

			MethodDeclaration retval = null;
			if (typeOfReceiver instanceof InterfaceType) {
				final StaticScope enclosedScope = typeOfReceiver.enclosedScope();
				final InterfaceDeclaration associatedDeclaration = (InterfaceDeclaration)enclosedScope.associatedDeclaration();
				final MethodSignature methodSignature = associatedDeclaration.lookupMethodSignatureDeclaration(methodSelectorName_, actualParameters_);
				retval = new MethodDeclaration(methodSignature, enclosedScope, methodSignature.lineNumber());
			} else if (typeOfReceiver instanceof ArrayType && methodSelectorName.equals("size")) {
				// Special kludge for method invocation on array "object." Can
				// add others here (clone?). If the number of methods we add gets
				// too large we should explore another architectural alternative.
				retval = ((ArrayType)typeOfReceiver).sizeMethodDeclaration(StaticScope.globalScope());
			} else {
				if (null == receiverScope) {
					assert null != receiverScope;
				}
				retval = receiverScope.lookupMethodDeclaration(methodSelectorName, parameterList, false);
				if (null == retval) {
					retval = receiverScope.lookupMethodDeclarationWithConversionIgnoringParameter(methodSelectorName, parameterList,
							false, /*parameterToIgnore*/ null);
				}
				if (null == retval) {
					// Check out the base class
					final Declaration declOfCurrent = typeOfReceiver.enclosedScope().associatedDeclaration();
					if (declOfCurrent instanceof ClassDeclaration || declOfCurrent instanceof ContextDeclaration) {
						ObjectSubclassDeclaration classOfCurrent = (ObjectSubclassDeclaration)declOfCurrent;
						
						while (null != (classOfCurrent = classOfCurrent.baseClassDeclaration()) ) {
							final StaticScope huntingScope = classOfCurrent.enclosedScope();
							retval = huntingScope.lookupMethodDeclaration(methodSelectorName, parameterList, false);
							if (null == retval) {
								retval = huntingScope.lookupMethodDeclarationWithConversionIgnoringParameter(methodSelectorName, parameterList,
										false, /*parameterToIgnore*/ null);
							}
							if (null != retval) {
								break;
							}
						}
					}
				}
			}
			if (null == retval) {
				if (typeOfReceiver instanceof RoleType) {
					// Normally, Role lookups don't come here. (I need better to document here where
					// they properly *are* handled...) But a typical case that lands us here is
					// an assert invocation from inside a Role method. We need to switch to
					// the base class of all Role-players -- Object -- and look there.
					final ClassDeclaration classObject = StaticScope.globalScope().lookupClassDeclaration("Object");
					final StaticScope classObjectScope = classObject.enclosedScope();
					retval =  classObjectScope.lookupMethodDeclarationIgnoringParameter(methodSelectorName, parameterList, "this",
							/* conversionAllowed = */ false);
				}
			}
			return retval;
		}

		@Override public RTCode run() {
			RTMessageDispatcher dispatcher =
				RTMessageDispatcher.makeDispatcher(
					messageExpr_,
					methodSelectorName_,
					argPush_,
					postReturnProcessing_,
					expressionsCountInArguments_,
					actualParameters_,
					isStatic(),
					nearestEnclosingType_,
					originMessageClass(),
					targetMessageClass());
            
			RTCode retval = null;
            
			if (null == dispatcher) {
				retval = new RTHalt();
			} else {
	            retval = dispatcher.hasError();
	            if (null == retval) {
	            	retval = dispatcher.methodDecl();
	            	if (null == retval) {
	            		retval = new RTHalt();
	            	}
	            }
			}
			return retval;
		}
		
		@Override public void setNextCode(final RTCode nextCode) {
			// This becomes the public version of setNextCode. The value
			// it sets reflects the static sequence, e.g. from the method
			// send to the following statement. It does NOT reflect the
			// dynamic branch to the RTMethod - which is what nextCode()
			// (in the base class) must return.
			super.setNextCode(nextCode);
			postReturnProcessing_.setNextCode(nextCode);
		}
		
		private int expressionsInExpression(final RTCode rtCodePointer) {
			assert null != rtCodePointer;
			RTCode pc = rtCodePointer;
			int retval = 0;
			do {
				pc = pc.nextCode();
				retval++;
			} while (null != pc);
			return retval;
		}
		
		private Expression promoteArgToType(final Expression anArgument, final Type formalParameterType, final String methodName, final int lineNumber) {
			Expression retval = anArgument;
			final Type sourceType = anArgument.type();
			final String sourceTypePathName = sourceType.pathName();
			if (formalParameterType.pathName().equals("double.")) {
				if (sourceTypePathName.equals("int.")) {
					ErrorLogger.error(ErrorType.Warning, lineNumber,
							"WARNING: Substituting double object for `", anArgument.getText(),
							"' for call to method `", methodName, "' at line ",
							String.valueOf(anArgument.lineNumber()));
					retval = new DoubleCasterExpression(anArgument);
				}
			}
			return retval;
		}
		
		private RTCode buildArgumentPushList(final FormalParameterList actualMethodFormals, final String methodName, final int lineNumber) {
			// Arrange an arguments data structure in preparation
			// for pushing them onto the stack.
			// This returns a code block (just a linked list)
			// with no ties to any method body.
			
			final RTCode myNextCode = nextCode();
			RTCode retval = myNextCode, previous = null;
			final int actualParametersCount = actualParameters_.count();
			if (0 < actualParametersCount) {
				for (int i = 0; i < actualParametersCount; i++) {
					final Object anObjectArgument = actualParameters_.argumentAtPosition(i);
					assert null != anObjectArgument && anObjectArgument instanceof Expression;
					Expression anArgument = (Expression)anObjectArgument;
					
					final ObjectDeclaration formalParameter = actualMethodFormals.parameterAtPosition(i);
					final Type formalParameterType = null == formalParameter
							? StaticScope.globalScope().lookupTypeDeclaration("void")
							: formalParameter.type();
					final Type anArgumentType = anArgument.type();
					
					// null check is for error stumbling.  should be pathName check. FIXME.
					if (null != anArgumentType && false == formalParameterType.name().equals(anArgumentType.name()) && 0 != i) {
						if (false == formalParameterType.isBaseClassOf(anArgumentType)) {
							if (formalParameterType.canBeConvertedFrom(anArgument.type())) {
								anArgument = this.promoteArgToType(anArgument, formalParameterType, methodName, lineNumber);
							}
						}
					}
					
					// Can be null on error conditions
					final RTCode rtCodePointer = RTExpression.makeExpressionFrom(anArgument, nearestEnclosingType_);
					
					if (i < 0 || i >= expressionsCountInArguments_.length) {
						assert i >= 0 && i < expressionsCountInArguments_.length;
					}
					
					if (null != rtCodePointer) { 	// can happen with programmer errors
						expressionsCountInArguments_[i] = expressionsInExpression(rtCodePointer);
						rtCodePointer.setNextCode(null);		// just neatness
						if (0 == i) {
							previous = retval = rtCodePointer;
						} else {
							previous.setNextCode(rtCodePointer);
							previous = rtCodePointer;
						}
					} else {
						retval =  null;		// no need to carry run-time forward
						previous = null;	// more safety for below
					}
				}
			} else {
				// Special kludge when argument push is zero
				// (e.g., Math.random()). Handle boundary conditions
				// with a pseudo push
				Expression anArgument = new NullExpression();
				
				// Can be null on error conditions
				final RTCode rtCodePointer = RTExpression.makeExpressionFrom(anArgument, nearestEnclosingType_);
				
				assert (null != rtCodePointer);
				expressionsCountInArguments_[0] = expressionsInExpression(rtCodePointer);
				rtCodePointer.setNextCode(null);		// just neatness
				previous = retval = rtCodePointer;
			}
			
			if (null != previous) {
				// We set it to null. Pushing arguments is currently handled by
				// a loop inside RTMessage.run() rather than running on the
				// standard instruction-fetch loop in RunTimeEnvironment.
				// So it's terminated in a "halt". On reaching it, RTMessage
				// yields control to RunTimeEnvironment - with its own
				// nextCode value.
				previous.setNextCode(null);
			} else {
				;	// is O.K.? no initializations, I guess
					// Just defaults to return the original nextCode_ value
			}
			
			return retval;
		}
		
		public ActualArgumentList actualParameters() {
			return actualParameters_;
		}
		
		public int lineNumber() {
			return lineNumber_;
		}
		
		// Should be a private class, but it's public for access
		// to the debugging empire in RunTimeEnvironment
		
		public static class RTPostReturnProcessing extends RTExpression {
			public RTPostReturnProcessing(final RTCode nextCode, final String name, String debugName) {
				super();
				super.setNextCode(nextCode);
				name_ = name;
				debugName_ = debugName;
			}
			@Override public RTCode run() {
				if (false == resultIsConsumed()) {
					RunTimeEnvironment.runTimeEnvironment_.popStack();
				}
				return super.nextCode();
			}
			
			public String name() {
				return name_;
			}
			public String debugName() {
				return debugName_;
			}
			
			private final String name_, debugName_;		// for debugging only
		}
		
		@Override public void setResultIsConsumed(final boolean tf) {
			super.setResultIsConsumed(tf);
			postReturnProcessing_.setResultIsConsumed(tf);
			if (null != messageExpr_) {
				messageExpr_.setResultIsConsumed(tf);
			}
		}
		public TemplateInstantiationInfo templateInstantiationInfo() {
			return templateInstantiationInfo_;
		}
		protected static ActualArgumentList buildArguments(final String className, final String methodName,
				final List<String> parameterNames, final List<String> parameterTypeNames, final StaticScope enclosedMethodScope,
				final boolean isStatic) {
			final ActualArgumentList argList = new ActualArgumentList();
			final Type outType = StaticScope.globalScope().lookupTypeDeclaration(className);
			assert null != enclosedMethodScope;
			final Declaration associatedDeclaration = null == enclosedMethodScope?
																null: 
																enclosedMethodScope.associatedDeclaration();
			final int lineNumber = null == associatedDeclaration? 0: associatedDeclaration.lineNumber();
					
			if (false == isStatic) {
				final IdentifierExpression self = new IdentifierExpression("this", outType, enclosedMethodScope, lineNumber);
				argList.addActualArgument(self);
			}
			
			if (null != parameterNames) {
				if (null == parameterTypeNames) {
					assert null != parameterTypeNames;
				}
				final Iterator<String> typeNameIter = parameterTypeNames.iterator();
				for (final String parameterName : parameterNames) {
					assert typeNameIter.hasNext();
					final String parameterTypeName = typeNameIter.next();
					final Type stringType = StaticScope.globalScope().lookupTypeDeclaration(parameterTypeName);

					final IdentifierExpression theArgument = new IdentifierExpression(parameterName, stringType,
							enclosedMethodScope, lineNumber);
					argList.addActualArgument(theArgument);
				}
			}
			return argList;
		}
		
		public MethodInvocationEnvironmentClass originMessageClass() {
			return originMessageClass_;
		}
		
		public MethodInvocationEnvironmentClass targetMessageClass() {
			return targetMessageClass_;
		}
		
		public boolean isStatic() {
			return isStatic_;
		}
		
		protected static void printMiniStackStatus() {
			// Experiment: Pop down stack looking for information about the method
			RTStackable topOfStack;
			while (RunTimeEnvironment.runTimeEnvironment_.stackSize() > 0) {
				topOfStack = RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (topOfStack instanceof RTPostReturnProcessing) {
					RTPostReturnProcessing currentMethodReturn = (RTPostReturnProcessing)topOfStack;
					ErrorLogger.error(ErrorType.Runtime, "\tIn script `", currentMethodReturn.debugName() + ".",
							currentMethodReturn.name(), "'");
					while (RunTimeEnvironment.runTimeEnvironment_.stackSize() > 0) {
						topOfStack = RunTimeEnvironment.runTimeEnvironment_.popStack();
						if (topOfStack instanceof RTPostReturnProcessing) {
							currentMethodReturn = (RTPostReturnProcessing)topOfStack;
							ErrorLogger.error(ErrorType.Runtime, "\tCalled from script `", currentMethodReturn.debugName() + ".",
									currentMethodReturn.name(), "'");
						}
					}
					break;
				}
				System.err.format("popping stack: type is %s\n", topOfStack.getClass().getSimpleName());
			}
			System.err.println();
		}


		private TemplateInstantiationInfo templateInstantiationInfo_;
		private String methodSelectorName_;
		private MessageExpression messageExpr_;
		protected final RTCode argPush_;
		private ActualArgumentList actualParameters_;
		private final int lineNumber_;
		private final int [] expressionsCountInArguments_;
		private final RTPostReturnProcessing postReturnProcessing_;
		private RTType nearestEnclosingType_;
		private final boolean isStatic_;
		private final MethodInvocationEnvironmentClass originMessageClass_, targetMessageClass_;
	}	// class RTMessage

	public static class RTDupMessage extends RTExpression {
		public RTDupMessage(final String name, final DupMessageExpression expr, final RTType enclosingMegaType) {
			super();
			lineNumber_ = expr.lineNumber();
			final Expression objectToClone = expr.objectToClone();
			final List<BodyPart> intermediate = objectToClone.bodyParts();
			objectToClone_ = bodyPartsToRTExpressionList(intermediate, enclosingMegaType);
			part2_ = new RTDupMessagePart2(name, lineNumber_);
			objectToClone_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
			name_ = name;
		}
		
		@Override public RTCode run() {
			final RTCode retval = RunTimeEnvironment.runTimeEnvironment_.runner(objectToClone_);
			return retval;
		}
		
		public String name() {
			return name_;
		}
		
		public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		private final String name_;
		private final int lineNumber_;
		private final RTExpressionList objectToClone_;
		private final RTDupMessagePart2 part2_;
	}
	public static class RTDupMessagePart2 extends RTExpression {
		public RTDupMessagePart2(final String name, final int lineNumber) {
			super();
			lineNumber_ = lineNumber;
			name_ = name;
		}
		
		@Override public RTCode run() {
			final RTStackable rawOriginal = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawOriginal instanceof RTObject;
			final RTObject original = (RTObject)rawOriginal;
			final RTObject clone = original.dup();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(clone);
			original.decrementReferenceCount();
			setLastExpressionResult(clone, lineNumber_);
			return nextCode_;
		}
		
		public String name() {
			return name_;
		}
		
		final private String name_;
		final private int lineNumber_;
	};
	
	public static class RTIdentifier extends RTExpression {
		static RTIdentifier makeIdentifier(final String name, final IdentifierExpression expression) {
			RTIdentifier retval = null;
			assert null != expression;
			final StaticScope declaringScope = expression.scopeWhereDeclared();
			if (null != declaringScope) {		// mainly in user program error situations
				final Declaration associatedDeclaration = declaringScope.associatedDeclaration();
				if (associatedDeclaration instanceof ContextDeclaration) {
					final RoleDeclaration isItARole = declaringScope.lookupRoleOrStagePropDeclaration(name);
					if (null != isItARole) {
						if (isItARole instanceof StagePropDeclaration) {
							retval = new RTStagePropIdentifier(name, expression);
						} else if (isItARole instanceof RoleDeclaration) {
							retval = new RTRoleIdentifier(name, expression);
						} else {
							assert false;
						}
					} else {
						retval = new RTIdentifier(name, expression);
					}
				} else if (expression.type() instanceof ArrayType) {
					final ArrayType arrayType = (ArrayType)expression.type();
					final Type baseType = arrayType.baseType();
					retval = new RTArrayIdentifier(name, expression, baseType);
				} else {
					retval = new RTIdentifier(name, expression);
				}
			}
			return retval;
		}
		public RTIdentifier(final String name, final IdentifierExpression expression) {
			super();

			lineNumber_ = expression.lineNumber();
			declaringScope_ = expression.scopeWhereDeclared();
			
			if (null != declaringScope_) {
				final Declaration associatedDeclaration = declaringScope_.associatedDeclaration();
				isLocal_ = (null == associatedDeclaration) || !(associatedDeclaration instanceof TypeDeclaration);
				if (name.equals("t$his") || name.equals("ret$val") || name.equals("current$context")) {
					if (!isLocal_) {
						assert isLocal_;
					}
				}
			} else {
				// Just plug in some reasonable values
				// We come here only if there is an error upstream, so
				// we stumble adeptly
				isLocal_ = true;
				declaringScope_ = StaticScope.globalScope();
			}
			idName_ = name;
			
			setResultIsConsumed(expression.resultIsConsumed());
		}
		@Override public RTCode run() {
			// Get the value of an identifier from the appropriate scope
			RTObject value = null;
			final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			if (isLocal_) {
				// It's just in the local activation record
				final RTDynamicScope activationRecord = scope;
				assert null != activationRecord;
				value = RTExpression.getObjectUpToMethodScopeFrom(idName_, activationRecord);
				if (null == value) {
					if (idName_.equals("current$context")) {
						// This is kind of an ugly kludge. If we're being asked
						// for the current context, and if it's not in the current
						// method activation record, then this must not be a
						// Role method from which we're making the invocation.
						// But it must be a Context method - only Context methods
						// or Role methods can call Role methods (guaranteed by the
						// grammar).
						//
						// Probably worth adding some assertions around this, if
						// there's enough data in the environment
						if (declaringScope_.parentScope().associatedDeclaration() instanceof ContextDeclaration == false) {
							assert declaringScope_.parentScope().associatedDeclaration() instanceof ContextDeclaration;
						}
						final RTObject self = RTExpression.getObjectUpToMethodScopeFrom("this", scope);
						assert self instanceof RTContextObject;
						value = self;
					} else {
						final RTDynamicScope parentScope = activationRecord.nearestEnclosingScopeDeclaring(idName_);
						value = null == parentScope? null: parentScope.getObject(idName_);
					}
					if (null == value) {
						ErrorLogger.error(ErrorType.Internal, lineNumber_,
								"RUNTIME: Unknown run-time error around source line ",
								Integer.toString(lineNumber_),
								" associated with `", idName_,
								"'. Check other error messages.", "");
						return new RTHalt();
					}
				}
			} else {
				// WARNING. This is a bit presumptuous and needs work. TODO.
				// Maybe will fail if there is a nested declaration of an
				// identifier in a Role method and it goes looking for
				// it... Need some tests.
				final RTObject self = RTExpression.getObjectUpToMethodScopeFrom("this", scope);
				value = self.getObject(idName_);
				if ((null == value) && (self instanceof RTContextObject)) {
					// WARNING. Self could be pointing to a Role-player, though the method is
					// still a Role method — albeit of another Context type (or, worse, Context
					// instance even of the same type). If this method is in a  Role-player
					// as such, it has no business addressing a member of the enclosing Context
					// of the Role it is playing.
					
					// Check to see if we're running in a Role method. We can determine
					// that from the dynamic scope. Just go looking for a declaration of "this"
					// and we'll have the scope; then see if it has a current$context
					final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
					
					// This is just a hope...
					final RTDynamicScope scopeOfCurrentActiveRoleMethod = currentScope.nearestEnclosingScopeDeclaring("this");
					
					// This may be null if we are in a Context method, and not in a Role method!
					final RTObject rawContextContainingRoleWhoseMethodWeAreExecuting = scopeOfCurrentActiveRoleMethod.getObject("current$context");
					
					if (null != rawContextContainingRoleWhoseMethodWeAreExecuting &&
							rawContextContainingRoleWhoseMethodWeAreExecuting instanceof RTContextObject) {
						final RTContextObject contextContainingRoleWhoseMethodWeAreExecuting = (RTContextObject)rawContextContainingRoleWhoseMethodWeAreExecuting;
						// Then we are executing within a Role method!
						// Now: self points to the Context object that is *playing* the
						// current Role, and contextContainingRoleWhoseMethodWeAreExecuting
						// points to the Context *containing* the current Role.
						//
						// Example: accessing SpellCheck.currentPos_ from SpellCheck.Text.isFinished(),
						// where SpellCheck.Text is a role being played by TextFile.
						// contextContainingRoleWhoseMethodWeAreExecuting points to SpellCheck;
						// self points to TextFile
						value = contextContainingRoleWhoseMethodWeAreExecuting.getRoleOrStagePropBinding(idName_);
						if (null == value) {
							value = contextContainingRoleWhoseMethodWeAreExecuting.getObject(idName_);
							if (null == value) {
								assert null != value;
							}
						}
					} else {
						// We are not within a Role method. Probably a Context method.
						// Here, a cigar is just a cigar and because self instanceof RTContextObject,
						// we process this as an ordinary Context method.
					
						final RTContextObject rTSelf = (RTContextObject)self;
						value = rTSelf.getRoleOrStagePropBinding(idName_);
						if (null == value) {
							// Maybe it's an attempt by a Role method to access ordinary
							// Context member data. This should be illegal. But it's the
							// job of this part of the code to carry out mechanism rather
							// to enforce policy. Make sure the semantic routines check
							// on this and at least offer a warning...
							//
							// Example: accessing currentPos_. self is an RTContextObject for "TextFile"
							assert null != value;
						}
					}
					
					assert null != value;
				} else {
					assert false;
				}
			}

			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value, lineNumber_);
			return nextCode_;
		}
		public RTDynamicScope dynamicScope() {
			RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			if (declaringScope_ instanceof TypeDeclaration) {
				assert false; // can never be. maybe mean "declaringScoe.associatedDeclaration()" ?
				// scope = new RTDynamicScope(this.name(), scope.getObject("this"), null);
			} else {
				;
			}
			return scope;
		}
		public String name() {
			return idName_;
		}
		public StaticScope declaringScope() {
			return declaringScope_;
		}
		public int lineNumber() {
			return lineNumber_;
		}

		protected final String idName_;
		protected StaticScope declaringScope_;
		protected final boolean isLocal_;
		protected final int lineNumber_;
	}
	
	public static class RTArrayIdentifier extends RTIdentifier {
		RTArrayIdentifier(final String name, final IdentifierExpression expression, final Type baseType) {
			super(name, expression);
			baseType_ = baseType;
			
			setResultIsConsumed(expression.resultIsConsumed());
		}
		
		public Type baseType() {
			return baseType_;
		}
		
		@Override public RTCode run() {
			// after all, it's just an identifier
			return super.run();
		}
		
		private final Type baseType_;
	}
	
	public static class RTRoleIdentifier extends RTIdentifier {
		public RTRoleIdentifier(final String name, final IdentifierExpression expression) {
			super(name, expression);
			setResultIsConsumed(expression.resultIsConsumed());
		}
		@Override public RTCode run() {
			// Get the value of an identifier from the appropriate scope
			RTObject value = null;
			final RTDynamicScope currentDynamicScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			if (isLocal_) {
				// It's just in the local activation record
				final RTDynamicScope activationRecord = currentDynamicScope;
				value = activationRecord.getObject(idName_);
				assert null != value;
			} else {
				// WARNING. This is a bit presumptuous and needs work.
				// It's a bit better now...
				final RTObject self = RTExpression.getObjectUpToMethodScopeFrom("this", currentDynamicScope);
				if (null == self) {
					assert null != self;
				}
				value = self.getObject(idName_);
				if ((null == value) && (self instanceof RTContextObject)) {
					final RTContextObject rTSelf = (RTContextObject)self;
					value = rTSelf.getRoleOrStagePropBinding(idName_);
					assert null != value;
				} else {
					// We need to get the role binding
					final RTObject tempContextPointer = currentDynamicScope.getObjectRecursive("current$context");
					if (null == tempContextPointer) {
						assert null != tempContextPointer;
					} else if (false == tempContextPointer instanceof RTContextObject) {
						assert tempContextPointer instanceof RTContextObject;
					}
					
					final RTContextObject contextPointer = (RTContextObject)tempContextPointer;
					value = contextPointer.getRoleOrStagePropBinding(idName_);
					assert null != value;
				}
			}
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value, lineNumber_);
			return nextCode_;
		}
	}
	public static class RTStagePropIdentifier extends RTRoleIdentifier {
		public RTStagePropIdentifier(final String name, final IdentifierExpression expression) {
			super(name, expression);
		}
	}
	
	public static class RTRelop extends RTExpression {
		public RTRelop(final RelopExpression expr, final RTType nearestEnclosingType) {
			super();
			final RTExpression lhs = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType),
					           rhs = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			if (null == lhs || null == rhs) {
				// error stumbling check
				lhs_ = new RTNullExpression();
				rhs_ = new RTNullExpression();
			} else {
				lhs_ = lhs;
				rhs_ = rhs;
			}
			part2_ = new RTRelopPart2(expr);
			
			setResultIsConsumed(expr.resultIsConsumed());
			// part2_ takes care of itself...
			
			// But in fact everything is consumed
			// Just guarantee it here.
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			
			lhs_.setNextCode(rhs_);
			rhs_.setNextCode(part2_);
		}
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(lhs_);
		}
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTRelopPart2 extends RTExpression {
			public RTRelopPart2(final RelopExpression expr) {
				super();
				lineNumber_ = expr.lineNumber();
				operator_ = expr.operator();
				setResultIsConsumed(expr.resultIsConsumed());
			}
			@Override public RTCode run() {
				// They should be on the stack
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();

				boolean value = false;
				if (operator_.equals("==")) {
					value = lhs.isEqualTo(rhs);
				} else if (operator_.equals(">=")) {
					value = lhs.isEqualTo(rhs) || lhs.gt(rhs);
				} else if (operator_.equals("<=")) {
					value = !lhs.gt(rhs);
				} else if (operator_.equals("<")) {
					value = (!lhs.isEqualTo(rhs)) && (!lhs.gt(rhs));
				} else if (operator_.equals(">")) {
					value = lhs.gt(rhs);
				} else if (operator_.equals("!=")) {
					value = !lhs.isEqualTo(rhs);
				} else if (operator_.equals("&&")) {
					// Inefficient — clean up. TODO.
					value = ((RTBooleanObject)lhs.logicalAnd(rhs)).value();
				} else if (operator_.equals("||")) {
					value = ((RTBooleanObject)lhs.logicalOr(rhs)).value();
				} else if (operator_.equals("^")) {
					value = ((RTBooleanObject)lhs.logicalXor(rhs)).value();
				} else {
					assert false;
				}
				
				final RTBooleanObject newBoolean = new RTBooleanObject(value);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(newBoolean);
				setLastExpressionResult(newBoolean, lineNumber_);
				
				rhs.decrementReferenceCount();
				lhs.decrementReferenceCount();
				newBoolean.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final int lineNumber_;
		}
		
		private final RTExpression lhs_, rhs_;
		private final RTRelopPart2 part2_;
	}
	
	
	public static class RTIdentityBooleanExpression extends RTExpression {
		public RTIdentityBooleanExpression(final IdentityBooleanExpression expr, final RTType nearestEnclosingType) {
			super();
			final RTExpression lhs = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType),
					           rhs = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			if (null == lhs || null == rhs) {
				// error stumbling check
				lhs_ = new RTNullExpression();
				rhs_ = new RTNullExpression();
			} else {
				lhs_ = lhs;
				rhs_ = rhs;
			}
			part2_ = new RTIdentityBooleanExpressionPart2(expr);
			
			setResultIsConsumed(expr.resultIsConsumed());
			// part2_ takes care of itself...
			
			// But in fact everything is consumed
			// Just guarantee it here.
			lhs_.setResultIsConsumed(true);
			rhs_.setResultIsConsumed(true);
			
			lhs_.setNextCode(rhs_);
			rhs_.setNextCode(part2_);
		}
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(lhs_);
		}
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTIdentityBooleanExpressionPart2 extends RTExpression {
			public RTIdentityBooleanExpressionPart2(final IdentityBooleanExpression expr) {
				super();
				lineNumber_ = expr.lineNumber();
				operator_ = expr.operator();
				setResultIsConsumed(expr.resultIsConsumed());
			}
			@Override public RTCode run() {
				// They should be on the stack
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();

				boolean value = false;
				if (operator_.equals("is") || operator_.equals("Is")) {
					value = lhs == rhs;
				} else if (operator_.equals("isnot") || operator_.equals("IsNot") ||
						operator_.equals("is not") || operator_.equals("Is Not")) {
					value = lhs == rhs == false == true;
				} else {
					assert false;
				}
				
				final RTBooleanObject newBoolean = new RTBooleanObject(value);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(newBoolean);
				setLastExpressionResult(newBoolean, lineNumber_);
				
				rhs.decrementReferenceCount();
				lhs.decrementReferenceCount();
				newBoolean.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final int lineNumber_;
		}
		
		private final RTExpression lhs_, rhs_;
		private final RTIdentityBooleanExpressionPart2 part2_;
	}
	
	public static class RTBoolean extends RTExpression {
		public RTBoolean(final BooleanExpression expr, final RTType nearestEnclosingType) {
			super();
			lineNumber_ = expr.lineNumber();
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			operator_ = expr.operator();
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			RunTimeEnvironment.runTimeEnvironment_.runner(lhs_);
			RunTimeEnvironment.runTimeEnvironment_.runner(rhs_);
			
			// That should get them on the stack
			final RTBooleanObject rhs = (RTBooleanObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			final RTBooleanObject lhs = (RTBooleanObject)RunTimeEnvironment.runTimeEnvironment_.popStack();

			boolean value = false;
			if (operator_.equals("&&")) {
				value = rhs.value() && lhs.value();
			} else if (operator_.equals("||")) {
				value = rhs.value() || lhs.value();
			} else if (operator_.equals("^")) {
				value = rhs.value() ^ lhs.value();
			} else if (operator_.equals("!")) {
				value = !rhs.value();
			} else {
				assert false;
			}
			
			final RTBooleanObject newBoolean = new RTBooleanObject(value);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(newBoolean);
			setLastExpressionResult(newBoolean, lineNumber_);
			
			rhs.decrementReferenceCount();
			lhs.decrementReferenceCount();
			newBoolean.decrementReferenceCount();
			
			return nextCode_;
		}

		private final RTExpression lhs_, rhs_;
		private final String operator_;
		private final int lineNumber_;
	}
	public static class RTBinop extends RTExpression {
		public RTBinop(final BinopExpression expr, final RTType nearestEnclosingType) {
			super();
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTBinopPart2(expr);
			lhs_.setNextCode(rhs_);
			rhs_.setNextCode(part2_);
			
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			return lhs_;
		}
		
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTBinopPart2 extends RTExpression {
			public RTBinopPart2(final BinopExpression expr) {
				super();
				lineNumber_ = expr.lineNumber();
				operator_ = expr.operator();
			}
			@Override public RTCode run() {
				// They should be on the stack
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();

				RTObject value = null;
				if (operator_.equals("+")) {
					value = rhs.plus(lhs);
				} else if (operator_.equals("-")) {
					value = rhs.minus(lhs);
				} else if (operator_.equals("*")) {
					value = rhs.times(lhs);
				} else if (operator_.equals("/")) {
					value = rhs.divideBy(lhs);
				} else if (operator_.equals("%")) {
					value = rhs.modulus(lhs);
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
				setLastExpressionResult(value, lineNumber_);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final int lineNumber_;
		}
		
		private final RTBinopPart2 part2_;
		private final RTExpression lhs_, rhs_;
	}
	public static class RTUnaryopWithSideEffect extends RTExpression {
		public RTUnaryopWithSideEffect(final UnaryopExpressionWithSideEffect expr, final RTType nearestEnclosingType) {
			super();
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			part2_ = new RTUnaryopWithSideEffectPart2(expr);
			lhs_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(lhs_);
		}
		
		@Override public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		public static class RTUnaryopWithSideEffectPart2 extends RTExpression {
			public RTUnaryopWithSideEffectPart2(final UnaryopExpressionWithSideEffect expr) {
				super();
				lineNumber_ = expr.lineNumber();
				operator_ = expr.operator();
				preOrPost_ = expr.preOrPost();
				setResultIsConsumed(expr.resultIsConsumed());
			}
			@Override public RTCode run() {
				// The expr should be on the stack
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();

				RTObject value = null;
				if (operator_.equals("+")) {
					value = lhs.unaryPlus();
				} else if (operator_.equals("-")) {
					value = lhs.unaryMinus();
				} else if (operator_.equals("++")) {
					switch (preOrPost_) {
					case Pre:
						value = lhs.preIncrement();
						break;
					case Post:
						value = lhs.postIncrement();
						break;
					default:
						assert false;
					}
				} else if (operator_.equals("--")) {
					switch (preOrPost_) {
					case Pre:
						value = lhs.preDecrement();
						break;
					case Post:
						value = lhs.postDecrement();
						break;
					default:
						assert false;
					}
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
				setLastExpressionResult(value, lineNumber_);
				
				lhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final UnaryopExpressionWithSideEffect.PreOrPost preOrPost_;
			private final int lineNumber_;
		}
		
		private final RTExpression lhs_;
		private final RTUnaryopWithSideEffectPart2 part2_;
	}
	public static class RTUnaryAbelianop extends RTExpression {
		public RTUnaryAbelianop(final UnaryAbelianopExpression expr, final RTType nearestEnclosingType) {
			super();
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTUnaryAbelianopPart2(expr);
			if (null != rhs_) {		// stumbling check
				rhs_.setNextCode(part2_);
			}
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(rhs_);
		}
		
		@Override public void setNextCode(RTCode next) {
			part2_.setNextCode(next);
		}
		
		public static class RTUnaryAbelianopPart2 extends RTExpression {
			public RTUnaryAbelianopPart2(UnaryAbelianopExpression expr) {
				super();
				lineNumber_ = expr.lineNumber();
				operator_ = expr.operator();
				setResultIsConsumed(expr.resultIsConsumed());
			}
			@Override public RTCode run() {
				// The expr should be on the stack
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();

				RTObject value = null;
				if (operator_.equals("+")) {
					value = rhs.unaryPlus();
				} else if (operator_.equals("-")) {
					value = rhs.unaryMinus();
				} else if (operator_.equals("!")) {
					value = rhs.unaryLogicalNegation();
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
				setLastExpressionResult(value, lineNumber_);
				
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final int lineNumber_;
		}
		
		private final RTExpression rhs_;
		private final RTUnaryAbelianopPart2 part2_;
	}
	public static class RTDoubleCaster extends RTExpression {
		public RTDoubleCaster(final DoubleCasterExpression expr, final RTType nearestEnclosingType) {
			super();
			originalRHS_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTDoubleCasterPart2();
			originalRHS_.setNextCode(part2_);
		}
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(originalRHS_);
		}
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTDoubleCasterPart2 extends RTExpression {
			public RTDoubleCasterPart2() {
				super();
			}
			@Override public RTCode run() {
				final RTObject value = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				assert value instanceof RTIntegerObject;
				final long rawValue = ((RTIntegerObject)value).intValue();
				final RTDoubleObject newValue = new RTDoubleObject((double)rawValue);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(newValue);
				return super.nextCode();
			}
		}
		
		private final RTExpression originalRHS_;
		private final RTDoubleCasterPart2 part2_;
	}
	public static class RTAssignment extends RTExpression {
		public RTAssignment(final AssignmentExpression expr, final RTType nearestEnclosedType) {
			super();
			
			if (nearestEnclosedType instanceof RTClass) {
				final RTClass rtTypeDeclAsRTClass = (RTClass)nearestEnclosedType;
				this.setTemplateInstantiationInfo(rtTypeDeclAsRTClass.templateInstantiationInfo());
			} else {
				templateInstantiationInfo_ = null;
			}
			
			ctorCommon(expr, nearestEnclosedType);
		}
		private void ctorCommon(final AssignmentExpression expr, final RTType nearestEnclosedType) {
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosedType);
			part2_ = this instanceof RTInternalAssignment?
						new RTInternalAssignmentPart2(expr, rhs_, nearestEnclosedType):
						new RTAssignmentPart2(expr, rhs_, nearestEnclosedType);
			rhs_.setNextCode(part2_);
			rhs_.setResultIsConsumed(true);
			this.setResultIsConsumed(expr.resultIsConsumed());
			lineNumber_ = expr.lineNumber();
			gettableText_ = expr.getText();
		}
		
		@Override public void setResultIsConsumed(final boolean tf) {
			super.setResultIsConsumed(tf);
			part2_.setResultIsConsumed(tf);
		}
		
		@Override public RTCode run() {
			// I found I needed to add this loop to a new ArrayType [expr]
			// expression. Invoking rhs_.run() alone just sets up the object
			// and does null initialization - it does not call the constructor.
			//
			// So I broke out the real assignment processing into RTAssignmentPart2.
			
			return RunTimeEnvironment.runTimeEnvironment_.runner(rhs_);
		}
		public void setNextCode(final RTCode pc) {
			part2_.setNextCode(pc);
		}
	
		public int lineNumber() {
			return lineNumber_;
		}
		
		public static class RTAssignmentPart2 extends RTExpression {
			public RTAssignmentPart2(final AssignmentExpression expr, final RTExpression rhs, final RTType nearestEnclosingType) {
				super();
				lineNumber_ = expr.lineNumber();
				lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
				if (lhs_ instanceof RTQualifiedIdentifier) {
					part2b_ = new RTAssignmentPart2B(lhs_, lineNumber_);
					lhs_.setNextCode(part2b_);
				}
				this.setResultIsConsumed(expr.resultIsConsumed());
				gettableText_ = expr.getText();
			}
			@Override public void setResultIsConsumed(final boolean tf) {
				super.setResultIsConsumed(tf);
				if (null != part2b_) part2b_.setResultIsConsumed(tf);
			}
			@Override public RTCode run() {
				// RHS processing takes place in RTAssignment, because it may take
				// several machine cycles to process that expression (example:
				// instantiate an object and call its constructor). We eventually
				// come here to do the real assignment.
				
				// That should get it on the stack. Get the RHS value now.
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				assert null != rhs;
	
				// Only three types make sense as L-values: Identifier, QualifiedIdentifier, and ArrayExpression
				// TODO: Add QualifiedClassMemberExpression
				RTDynamicScope dynamicScope = null;
				String name = null;
				if (lhs_ instanceof RTRoleIdentifier) {
					final StaticScope declaringScope = ((RTRoleIdentifier)lhs_).declaringScope();
					final RoleDeclaration lhsDecl = declaringScope.lookupRoleOrStagePropDeclaration(((RTRoleIdentifier)lhs_).name());
					if (lhsDecl.isArray()) {
						if (rhs instanceof RTArrayObject) {
							final RTArrayObject aRhs = (RTArrayObject)rhs;
							assert lhs_ instanceof RTRoleIdentifier;
							this.processRoleArrayBinding2(aRhs);
						} else if (rhs instanceof RTListObject) {
							final RTListObject aRhs = (RTListObject)rhs;
							assert lhs_ instanceof RTRoleIdentifier;
							this.processRoleArrayBinding2b((RTListObject)aRhs);
						} else {
							ErrorLogger.error(ErrorType.Runtime, lineNumber(),
									"Argument `", rhs.getText(), "' is not of an iterable type (List or vector) suitable to playing Role `" +
											((RTRoleIdentifier)lhs_).name(),
											"'.");
							return new RTHalt();
						}
					} else {
						this.processRoleOrStagePropBinding(rhs);
					}
					RunTimeEnvironment.runTimeEnvironment_.pushStack(rhs);	// need reference count cleanup code here?
					return staticNextCode_;
				/*
				 * Actually, just handling it as an ordinary RTIdentifier
				 * works just fine. We don't need this code.
				} else if (lhs_ instanceof RTArrayIdentifier) {
					final RTArrayIdentifier lhs = (RTArrayIdentifier)lhs_;
					dynamicScope = lhs.dynamicScope();
					name = lhs.name();
				*/
				} else if (lhs_ instanceof RTRoleArrayIndexExpression) {
					// Role array member assignment.
					final RTRoleArrayIndexExpression lhs = (RTRoleArrayIndexExpression)lhs_;
					final RTCode check = this.processRoleArrayElementBinding(lhs, rhs);
					if (check instanceof RTHalt) {
						return check;
					} else if (this.resultIsConsumed()) {
						RunTimeEnvironment.runTimeEnvironment_.pushStack(rhs);	// need reference count cleanup code here?
					}	
					return staticNextCode_;
				} else if (lhs_ instanceof RTArrayExpression && ((RTArrayExpression)lhs_).baseType() instanceof RoleType) {
					// Role array member assignment.
					final RTArrayExpression lhs = (RTArrayExpression)lhs_;
					
					assert rhs instanceof RTArrayObject;
					final RTArrayObject rhsAsArray = (RTArrayObject)rhs;
					
					this.processRoleArrayBinding(lhs, rhsAsArray);
					
					RunTimeEnvironment.runTimeEnvironment_.pushStack(rhs);	// need reference count cleanup code here?
					return staticNextCode_;
				} else if (lhs_ instanceof RTIdentifier) {
					final RTIdentifier lhs = (RTIdentifier)lhs_;
					dynamicScope = lhs.dynamicScope();
					name = lhs.name();
				} else if (lhs_ instanceof RTQualifiedIdentifier) {
					// We need to actually run LHS in order to compute
					// the qualifier...
					final RTCode restOfLhs = RunTimeEnvironment.runTimeEnvironment_.runner(lhs_);
					part2b_.setRhs(rhs);
					return restOfLhs;
				} else if (lhs_ instanceof RTArrayIndexExpression) {
					final RTArrayIndexExpression indexedExpression = (RTArrayIndexExpression) lhs_;
					
					final RTCode haltInstruction = indexedExpression.assign(rhs);
					assert null == haltInstruction;
					// ... I think this will always return null
					
					// We don't need the rest of the processing below -
					// it's all done within the "assign" method called above
					return staticNextCode_;
				} else if (lhs_ instanceof RTArrayIndexExpressionUnaryOp) {
					ErrorLogger.error(ErrorType.Internal, 0, "INTERNAL Error: Invalid left-hand side: unary increment or decrement operation on indexed array element", "", "", "");
				} else {
					assert false;
				}
				
				dynamicScope = dynamicScope.nearestEnclosingScopeDeclaring(name);
				if (null == dynamicScope) {
					assert null != dynamicScope;
				}
				
				// Reference count increment is done within the dynamic scope object
				// (Note: setNamedSlotToValue decrements the reference count of
				// the previous occupant).
				dynamicScope.setNamedSlotToValue(name, rhs);
				
				if (resultIsConsumed() == true) {
					RunTimeEnvironment.runTimeEnvironment_.pushStack(rhs);
				}
				setLastExpressionResult(rhs, lineNumber_);
				
				rhs.decrementReferenceCount();	// because we initially popped it from the stack
												// (the above pushStack increments it)
				
				return staticNextCode_;
			}
			
			private void processRoleOrStagePropBinding(final RTObject rhs) {
				// Role binding is unlike ordinary assignment in many ways. The Role
				// name is allowed to bind both to a class-like entity (a Role declaration)
				// that bears the methods for the role, and to the object playing that
				// role in a given Context.
				//
				// Roles become the method-binding focus of the computational model.
				// A Role supports all the methods of the object bound to it, as well
				// as its own Role methods. The Role methods may invoke methods on the
				// objects bound to the role if they have been put in the "requires"
				// list (either by a requires block or automatically).
				//
				// A client holding a Role identifier can invoke only Role
				// methods through that identifier.
				// 
				// If an object is bound to a Role and there is a method name
				// collision between public methods of the object and those
				// of the Role, the Role methods win.
				//
				// Dispatching of all methods is handled by the Role. It contains
				// in internal method lookup table built as a result of invoking
				// the method in which you currently find yourself
				
				// Let's do this the easy way and store administrative stuff
				// in the Context.
	
				// We need to evaluate lhs to get the actual Context object
				final RTRoleIdentifier lhs = (RTRoleIdentifier)lhs_;
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)RTExpression.getObjectUpToMethodScopeFrom("this", scope);
				assert contextScope.rTType() instanceof RTContext;
				if (lhs instanceof RTStagePropIdentifier) {
					contextScope.setStagePropBinding(lhs.name(), rhs);
				} else if (lhs instanceof RTRoleIdentifier) {
					contextScope.setRoleBinding(lhs.name(), rhs);
				} else {
					assert false;
				}
			}
			
			// Array
			private void processRoleArrayBinding2(final RTArrayObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an array object
				// to that role
				
				final RTRoleIdentifier lhs = (RTRoleIdentifier)lhs_;
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)RTExpression.getObjectUpToMethodScopeFrom("this", scope);
				
				assert contextScope.rTType() instanceof RTContext;
				
				contextScope.setRoleArrayBindingToArray(lhs, rhs);
			}
			
			// List
			private void processRoleArrayBinding2b(final RTListObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an array object
				// to that role
				
				final RTRoleIdentifier lhs = (RTRoleIdentifier)lhs_;
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)RTExpression.getObjectUpToMethodScopeFrom("this", scope);
				
				assert contextScope.rTType() instanceof RTContext;
				
				contextScope.setRoleArrayBindingToList(lhs, rhs);
			}
			
			private void processRoleArrayBinding(final RTArrayExpression lhs, final RTArrayObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an array object
				// to that role
				
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)RTExpression.getObjectUpToMethodScopeFrom("this", scope);
				
				assert contextScope.rTType() instanceof RTContext;
				ErrorLogger.error(ErrorType.Unimplemented, 0, "Unimplemented: assigment of vector to scalar role", "", "", "");
			}
			
			private RTCode processRoleArrayElementBinding(final RTRoleArrayIndexExpression lhs, final RTObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an object
				// to one of those elements
				
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)RTExpression.getObjectUpToMethodScopeFrom("this", scope);
				
				assert contextScope.rTType() instanceof RTContext;
				return contextScope.setRoleArrayElementBinding(lhs, rhs);
			}
			
			@Override public void setNextCode(final RTCode code) {
				if (null != part2b_) {
					part2b_.setNextCode(code);
				}
				staticNextCode_ = code;
				super.setNextCode(code);
			}
			
			// Public for run-time trace stuff
			public static class RTAssignmentPart2B extends RTExpression {
				public RTAssignmentPart2B(final RTExpression lhs, final int lineNumber) {
					super();
					lineNumber_ = lineNumber;
					lhs_ = lhs;
				}
				@Override public RTCode run() {
					// LHS evaluation is just a qualified identifier.
					// It evaluates as an R-value and leaves tracks
					// on the stack. Clean up.
					
					final RTQualifiedIdentifier lhs = (RTQualifiedIdentifier)lhs_;
					RTDynamicScope dynamicScope = lhs.dynamicScope();
					final String name = lhs.name();
					lhs.decrementReferenceCount();
					
					dynamicScope = dynamicScope.nearestEnclosingScopeDeclaring(name);
					assert null != dynamicScope;
					
					// Reference count increment is done within the dynamic scope object
					// (NOTE: setNamedSlotToValue decrements the count of the
					// previous occupant)
					dynamicScope.setNamedSlotToValue(name, rhs_);
					
					if (this.resultIsConsumed()) {
						RunTimeEnvironment.runTimeEnvironment_.pushStack(rhs_);
					}
					
					setLastExpressionResult(rhs_, lineNumber_);
					
					rhs_.decrementReferenceCount();
					
					return nextCode_;
				}
				public void setRhs(final RTObject rhs) {
					rhs_ = rhs;
				}
				
				public int lineNumber() {
					return lineNumber_;
				}
				
				private final RTExpression lhs_;
				private RTObject rhs_;
				private final int lineNumber_;
			}
			
			public String getText() {
				return gettableText_;
			}
			public int lineNumber() {
				return lineNumber_;
			}
			
			private RTExpression lhs_;
			private RTAssignmentPart2B part2b_;
			private RTCode staticNextCode_;
			private final int lineNumber_;
			private final String gettableText_;
		}
		
		public static class RTInternalAssignmentPart2 extends RTAssignmentPart2 {
			public RTInternalAssignmentPart2(final AssignmentExpression expr, final RTExpression rhs, final RTType nearestEnclosingType) {
				super(expr, rhs, nearestEnclosingType);
			}
		}
		
		public void setTemplateInstantiationInfo(final TemplateInstantiationInfo templateInstantiationInfo) {
			templateInstantiationInfo_ = templateInstantiationInfo;
		}
		public TemplateInstantiationInfo templateInstantiationInfo() {
			return templateInstantiationInfo_;
		}
		public String getText() {
			return gettableText_;
		}
		
		private RTExpression rhs_;
		private RTAssignmentPart2 part2_;
		private TemplateInstantiationInfo templateInstantiationInfo_;
		private int lineNumber_;
		private String gettableText_;
	}
	
	public static class RTInternalAssignment extends RTAssignment {
		// We merely use the type to signal that certain facets
		// of this expression should be ignored in normal processing.
		// For example, when an assignment statement appears in a
		// parameter list, it should be marked as being consumed.
		// But internal assignments need to be cleaned off the stack
		public RTInternalAssignment(final InternalAssignmentExpression expr, final RTType nearestEnclosedType) {
			super(expr, nearestEnclosedType);
		}
	}
	
	public static class RTNew extends RTExpression {
		public RTNew(final NewExpression expr, final RTType nearestEnclosingType) {
			super();
			
			// I initially thought that we'd need a unique variable each
			// time they are used. They are used to push the "object for
			// which this method is called" arguments on the stack - as
			// actual arguments, not formals - from which they will be
			// copied into the callee's activation record. But since there
			// is only one call active at a time, they don't collide with
			// each other. And since t$his is type-less it can stand in as
			// an actual parameter for any method.
			//
			// The sticking point is current$context, which could collide
			// with the real McCoy in the activation record of a Role method.
			// Fortunately, it doesn't seem like we need a *named* actual
			// parameter to affect this. First, we can just push an extra
			// (unnamed) parameter on the stack as part of the stack protocol,
			// so the RTMethod code picks it up and puts it in the activation
			// record for the Role method. Second, there are some corner cases
			// when code will go looking for current$context in the current
			// scope, but it isn't there (involving a Context method). The
			// right thing to do there is to just use the value of this, and
			// there is a fake-out to do that. See the kludges above in
			// RTArrayIndexExpression.run() and in pushContextPointerIfNecessary.
			//
			// currentContextVariableName_ removed.
			
			lineNumber_ = expr.lineNumber();
			thisVariableName_ = "t$his";
			classType_ = expr.classType();
			StaticScope classScope = classType_.enclosedScope();
			if (null == classScope) {
				final RTClass rtClass = nearestEnclosingType instanceof RTClass? (RTClass)nearestEnclosingType: null;
				final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
				
				// Maybe a template (e.g., new List<int,String>)
				if (classType_ instanceof TemplateType) {
					assert false;	// do we ever get here? fish. depends on rTType_.name(), which hasn't been set...
					// final TemplateInstantiationInfo templateInstantiationInfo = null == rtClass? null: rtClass.templateInstantiationInfo();
					// if (null != templateInstantiationInfo) {
					//	final Type boundType = templateInstantiationInfo.classSubstitionForTemplateTypeNamed(rTType_.name());
					//	if (null != boundType) {
					//		rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(boundType.enclosedScope());
					//	} else {
					//		rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(voidType.enclosedScope());
					//	}
					// } else {
					// 	rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(voidType.enclosedScope());
					// }
				} else if (classType_ instanceof TemplateParameterType) { // e.g., "new T" within a template
					final TemplateInstantiationInfo templateInstantiationInfo = null == rtClass? null: rtClass.templateInstantiationInfo();
					if (null != templateInstantiationInfo) {
						final Type boundType = templateInstantiationInfo.classSubstitionForTemplateTypeNamed(classType_.name());
						if (null != boundType) {
							classScope = boundType.enclosedScope();
						} else {
							classScope = voidType.enclosedScope();
						}
						rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(classScope);
					} else {
						rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(voidType.enclosedScope());
					}
				}
			} else {
				rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(classScope);
			}
			
			rtNewCommon(expr, classScope);
		}
		private void rtNewCommon(final NewExpression expr, final StaticScope classScope) {
			final ActualArgumentList actualArguments = expr.argumentList();
			final Message message = expr.message();
			
			assert null != classScope;
			
			String constructorSelectorName = message.selectorName();
			if (null != classScope.templateInstantiationInfo()) {
				// Then it's a template instantiation. The name of the constructor won't
				// be List<int,String> but rather List
				final TemplateInstantiationInfo templateInstantiationInfo = classScope.templateInstantiationInfo();
				constructorSelectorName = templateInstantiationInfo.templateName();
			}

			MethodDeclaration constructor = classScope.lookupMethodDeclaration(
					constructorSelectorName,
					actualArguments, false);
			if (null == constructor) {
				constructor = classScope.lookupMethodDeclarationWithConversionIgnoringParameter(
						constructorSelectorName,
						actualArguments, false, /*parameterToIgnore*/ null);
			}
			
			if (null == constructor) {
				rTConstructor_ = null;
			} else {
				// compile it
				final MethodInvocationEnvironmentClass originMessageClass = null == expr.enclosingMegaType()?
						MethodInvocationEnvironmentClass.ClassEnvironment:
						expr.enclosingMegaType().enclosedScope().methodInvocationEnvironmentClass();
				final MethodInvocationEnvironmentClass targetMessageClass = constructor.enclosedScope().methodInvocationEnvironmentClass();
				final IdentifierExpression rawSelf = new IdentifierExpression("this", classType_, classScope, expr.lineNumber());
				final MessageExpression messageExpression = new MessageExpression(rawSelf, message, classType_, expr.lineNumber(),
						false, originMessageClass, targetMessageClass, true);
				
				// The selectorName() will be the name of the class. The messageExpression
				// carries the constructor arguments
				final RTType classScopesrTType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(classScope);
				rTConstructor_ = RTMessage.makeRTMessage(message.selectorName(), messageExpression, classScopesrTType, classScope, messageExpression.isStatic());
				
				// In the past, there was code in RTContext.run that checked to see if
				// rTConstructor was set and, if so, put t$his in the activation record
				// and then called called on rTConstructor.run(), which just does the
				// standard message thing: push the return value and arguments, create
				// and push and activation record and pop the arguments into the
				// activation record, look up the method address and return it. It
				// was just wrong: the stack got messed up and information got lost.
				//
				// We need to do a proper job of "compiling" a new invocation. There are
				// three activities that can be assembled in a small number of ways.
				// First, there is building the object and putting its address on the
				// stack; that includes populating the activation record. Second, there
				// is calling the constructor. That may entail the evaluation of
				// arguments which themselves effect message calls. Third is to transfer
				// control to the next instruction, which will have been storied in
				// the RTNew code's own nextCode_ field.
				//
				// It's pretty simple, actually...
				rTConstructor_.setNextCode(nextCode_);
				super.setNextCode(rTConstructor_);
			}
			
			setResultIsConsumed(expr.resultIsConsumed());
			
			assert null != rTType_;
		}
		@Override public RTCode run() {
			final boolean isAContextConstructor = rTType_ instanceof RTContext;
			
			// The first thing is to actually create the new object.
			// Its type can be class or Context
			RTObject newlyCreatedObject = null;
			if (isAContextConstructor) {
				newlyCreatedObject = new RTContextObject(rTType_);
				for (final String iter : ((RTContext)rTType_).isRoleArrayMapEntries()) {
					((RTContextObject)newlyCreatedObject).designateRoleAsArray(iter);
				}
				for (final String iter : ((RTContext)rTType_).isStagePropArrayMapEntries()) {
					((RTContextObject)newlyCreatedObject).designateStagePropAsArray(iter);
				}
				
			// These should all be pathnames. Easy fix. FIXME.
			} else if (classType_.name().startsWith("List<")) {
				newlyCreatedObject = new RTListObject(rTType_);	// rTType_ is, e.g. an instance of RTClass
			} else if (classType_.name().startsWith("Set<")) {
				newlyCreatedObject = new RTSetObject(rTType_);	// rTType_ is, e.g. an instance of RTClass
			} else if (classType_.name().startsWith("Map<")) {
				newlyCreatedObject = new RTMapObject(rTType_);	// rTType_ is, e.g. an instance of RTClass
			} else if (classType_.name().equals("Date")) {
				newlyCreatedObject = new RTDateObject(rTType_);
			} else if (classType_.name().equals("Scanner")) {
				newlyCreatedObject = new RTScannerObject(rTType_);
			} else if (classType_.name().equals("Color")) {
				newlyCreatedObject = new RTColorObject(rTType_, null);
			} else if (classType_.name().equals("Panel")) {
				newlyCreatedObject = new RTPanelObject(rTType_);
			} else if (classType_.name().equals("Frame")) {
				newlyCreatedObject = new RTFrameObject(rTType_);
			} else if (classType_.name().equals("Event")) {
				newlyCreatedObject = new RTEventObject(rTType_);
			} else {
				newlyCreatedObject = new RTObjectCommon(rTType_);
			}
			
			final RTObject callingActivationRecord = (RTObject)RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			
			// We can just push it on the stack here. The constructor call may
			// come later, but that doesn't change the object that the
			// new expression will return. The return address for the call
			// to the constructor will be pushed on top of the actual object
			// pointer value, and then the value can be retrieved as the result
			// of the expression - after returning from the constructor.
			
			// If it is a Role method, we push an extra argument, which is
			// current$context. We never need to do that for a Constructor, since
			// it is a class method.
			
			// On this site in 1897, nothing happened.
			
			// Now push "this". NOTE: Stack push increments reference count.
			RunTimeEnvironment.runTimeEnvironment_.pushStack(newlyCreatedObject);
			
			// We have just created a skeleton object. Fill it in with its
			// data fields using the RTType information from the declarations.
			final Map<String, RTType> objectDecls = rTType_.objectDeclarations();
			for (final Map.Entry<String, RTType> objectDecl : objectDecls.entrySet()) {
				final String name = objectDecl.getKey();
				newlyCreatedObject.addObjectDeclaration(name, objectDecl.getValue());
				newlyCreatedObject.setObject(name, new RTObjectCommon.RTNullObject());
			}
			
			// Likewise for any roles! (Relevant only for Contexts, of course)
			if (isAContextConstructor) {
				final Map<String, RTRole> nameToRoleDeclMap = rTType_.nameToRoleDeclMap();
				assert newlyCreatedObject instanceof RTContextObject;
				final RTContextObject downcastObject = (RTContextObject)newlyCreatedObject;
				for (final Map.Entry<String, RTRole> roleDeclIter : nameToRoleDeclMap.entrySet()) {
					final String name = roleDeclIter.getKey();
					final RTRole theRole = roleDeclIter.getValue();
					if (theRole.isArray() == false) {
						downcastObject.addRoleDeclaration(name, roleDeclIter.getValue());
						final RTNullObject nullObject = new RTNullObject();
						downcastObject.setRoleBinding(name, nullObject);
						nullObject.decrementReferenceCount();
					}
				}
				
				
				// ... and StageProps
				final Map<String, RTStageProp> nameToStagePropDeclMap = rTType_.nameToStagePropDeclMap();
				for (final Map.Entry<String, RTStageProp> stagePropDeclIter : nameToStagePropDeclMap.entrySet()) {
					final String name = stagePropDeclIter.getKey();
					final RTStageProp theStageProp = stagePropDeclIter.getValue();
					if (theStageProp.isArray() == false) {
						downcastObject.addStagePropDeclaration(name, stagePropDeclIter.getValue());
						final RTNullObject nullObject = new RTNullObject();
						downcastObject.setStagePropBinding(name, nullObject);
						nullObject.decrementReferenceCount();
					}
				}
			}

			// Call the Constructor!!
			if (null != rTConstructor_) {
				// The object pointer is passed into the formal parameter "this".
				// The way the rTConstructor code is set up is to pass in
				// the value of an actual parameter called t$his, since we
				// need some positional parameter (and needed something for
				// type alignment and overload resolution lookups.) So set up
				// that stupid variable now... It corresponds to the thing
				// we pushed onto the stack above.
				
				// if (isAContextConstructor) {
				//	// BUG: This can't be called current$context — that mapping must be
				//	// constant from the time it is passed in to us as an argument. The
				//	// dummy that we set up for the ensuing logic must be something else.
				//	// It needn't be unique, but it cannot be current$context.
				//	
				//	// BIGGER BUG: These don't seem to be needed at all...
				//	callingActivationRecord.addObjectDeclaration(currentContextVariableName_ + "$", rTType_);
				//	callingActivationRecord.setObject(currentContextVariableName_ + "$", newlyCreatedObject);
				// }

				callingActivationRecord.addObjectDeclaration(thisVariableName_, rTType_);
				callingActivationRecord.setObject(thisVariableName_, newlyCreatedObject);
			}
			
			// The ordering here is important. Because of the anomaly in
			// how reference counting works, we want to log this as the
			// last expression before decrementing the reference count
			// by our own "ownership" of the object here in this activation
			// record
			setLastExpressionResult(newlyCreatedObject, lineNumber_);
			
			// The variable newlyCreatedObject is going out of scope
			newlyCreatedObject.decrementReferenceCount();
			
			// If there is a constructor, this returns its address. If not,
			// it is just the nextCode_ value set in the flow.
			return nextCode_;
		}
		
		@Override public void setNextCode(final RTCode nextCode) {
			if (null != rTConstructor_) {
				rTConstructor_.setNextCode(nextCode);
			} else {
				super.setNextCode(nextCode);
			}
		}
		
		@Override public String toString() {
			return "new " + classType_.name();
		}
		
		public int lineNumber() {
			return lineNumber_;
		}
		
		private Type classType_;
		private RTType rTType_;
		private RTMessage rTConstructor_;
		private final int lineNumber_;
		private String thisVariableName_;
	}
	public static class RTNewArray extends RTExpression {
		public RTNewArray(final NewArrayExpression expr, final RTType nearestEnclosingType) {
			super();
			lineNumber_ = expr.lineNumber();
			final Type baseType = expr.baseType();
			rTType_ = new RTArrayType(baseType, (ArrayType)expr.type());
			sizeExpression_ = RTExpression.makeExpressionFrom( expr.sizeExpression(), nearestEnclosingType );
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			final RTCode haltInstruction = RunTimeEnvironment.runTimeEnvironment_.runner(sizeExpression_);	// will return null
			assert haltInstruction == null;
			final RTStackable tempSizeExpr = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert tempSizeExpr instanceof RTIntegerObject;
			final RTIntegerObject sizeExpr = (RTIntegerObject)tempSizeExpr;
			
			final int size = (int)sizeExpr.intValue();
			
			final RTArrayObject newArrayObjectToPush = new RTArrayObject(size, rTType_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(newArrayObjectToPush);
			setLastExpressionResult(newArrayObjectToPush, lineNumber_);
			sizeExpr.decrementReferenceCount();
			
			return nextCode_;
		}
		
		@Override public void setNextCode(final RTCode nextCode) {
			super.setNextCode(nextCode);
		}
		
		private final RTExpression sizeExpression_;
		private final RTArrayType rTType_;
		private final int lineNumber_;
	}
	public static class RTArrayExpression extends RTExpression {
		public RTArrayExpression(final ArrayExpression expr, final RTType nearestEnclosingType) {
			super();
			baseType_ = expr.baseType();
			arrayName_ = expr.name();
			
			// I mean, it's just an expression...
			arrayBase_ = RTExpression.makeExpressionFrom(expr.originalExpression(), nearestEnclosingType);
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		public Type baseType() {
			return baseType_;
		}
		public RTExpression arrayExpr() {
			return arrayBase_;
		}
		public final String arrayName() {
			return arrayName_;
		}
		@Override public RTCode run() {
			RTCode pc = arrayBase_;
			do {
				if (pc instanceof RTHalt) {
					return pc;
				} else {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				}
			} while (null != pc);
			return nextCode_;
		}
		
		private final Type baseType_;
		private final String arrayName_;
		private final RTExpression arrayBase_;
	}
	public static class RTArrayIndexExpression extends RTExpression {
		public RTArrayIndexExpression(final ArrayIndexExpression expr, final RTType nearestEnclosingType) {
			super();
			rTIndexExpression_ = RTExpression.makeExpressionFrom( expr.indexExpr(), nearestEnclosingType );
			rTArrayExpression_ = RTExpression.makeExpressionFrom( expr.arrayExpr(), nearestEnclosingType );
			lineNumber_ = expr.lineNumber();
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		
		@Override public RTCode run() {
			RTObject result = null;
			
			RTCode pc = rTArrayExpression_;
			do {
				if (pc instanceof RTHalt) {
					return pc;
				} else {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				}
			} while (null != pc);
			
			pc = rTIndexExpression_;
			do {
				if (pc instanceof RTHalt) {
					return pc;
				} else {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				}
			} while (null != pc);
			
			final RTObject theIndex = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			final RTStackable rawArrayBase = RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (rawArrayBase instanceof RTArrayObject) {
				final RTArrayObject arrayBase = (RTArrayObject)rawArrayBase;
				result = arrayBase.getObject(theIndex);
			} else if (rawArrayBase instanceof RTNullObject) {
				ErrorLogger.error(ErrorType.Runtime, lineNumber_, "Likely access of uninitialized array. Machine halted.", "", "", "");
				return new RTHalt();
			} else if (true) {
				assert false;
			}
			
			if (null == result) {
				ErrorLogger.error(ErrorType.Runtime, 0, "Likely access of uninitialized array element.", "", "", "");
				return null;
			}
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
			setLastExpressionResult(result, lineNumber_);
			
			theIndex.decrementReferenceCount();
			rawArrayBase.decrementReferenceCount();
			
			return nextCode_;	// probably null
		}
		
		public RTCode assign(final RTObject rhs) {
			final RTCode haltInstruction = RunTimeEnvironment.runTimeEnvironment_.runner(rTArrayExpression_);
			assert null == haltInstruction;
			
			// Yuk. Firmware loop.
			RTCode pc2 = rTIndexExpression_;
			do {
				pc2 = RunTimeEnvironment.runTimeEnvironment_.runner(pc2);	// probably also returns null... well... maybe..
			} while (null != pc2);
			
			final RTObject theIndex = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (theIndex instanceof RTNullObject) {
				ErrorLogger.error(ErrorType.Runtime, 0, "Array index ", rTIndexExpression_.toString(), " unitialized at run-time for array ", 
						rTArrayExpression_.toString());
				pc2 = null; 	// halt
			} else {
				final RTStackable evaluationResult = RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (evaluationResult instanceof RTNullObject) {
					ErrorLogger.error(ErrorType.Runtime, 0, "Object ", rTArrayExpression_.toString(), " unitialized at run-time.", "");
					pc2 = null; 	// halt
				} else {
					assert evaluationResult instanceof RTArrayObject;
					final RTArrayObject arrayBase = (RTArrayObject)evaluationResult;
					arrayBase.setObject(theIndex, rhs);
					arrayBase.decrementReferenceCount();
				}
			}
			
			theIndex.decrementReferenceCount();
			
			return pc2;
		}
		public Type baseType() {
			return ((RTArrayExpression)rTArrayExpression_).baseType();
		}
		
		private final RTExpression rTIndexExpression_;
		private final RTExpression rTArrayExpression_;
		private final int lineNumber_;
	}
	public static class RTArrayIndexExpressionUnaryOp extends RTExpression {
		public RTArrayIndexExpressionUnaryOp(final ArrayIndexExpressionUnaryOp expr, final RTType nearestEnclosingType) {
			super();
			rTArrayExpression_ = RTExpression.makeExpressionFrom( expr.arrayExpr(), nearestEnclosingType );
			rTIndexExpression_ = RTExpression.makeExpressionFrom( expr.indexExpr(), nearestEnclosingType );
			part2_ = new RTArrayIndexExpressionUnaryOpPart2(expr);
			
			rTArrayExpression_.setNextCode(rTIndexExpression_);
			rTIndexExpression_.setNextCode(part2_);
			
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(rTArrayExpression_);
		}
		
		@Override public void setNextCode(RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTArrayIndexExpressionUnaryOpPart2 extends RTExpression {
			public RTArrayIndexExpressionUnaryOpPart2(final ArrayIndexExpressionUnaryOp expr) {
				super();
				lineNumber_ = expr.lineNumber();
				operation_ = expr.operation();
				preOrPost_ = expr.preOrPost();
			}
			@Override public RTCode run() {
				RTObject result = null;
				final RTObject theIndex = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTStackable rawArrayBase = RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTArrayObject arrayBase = (RTArrayObject)rawArrayBase;
				result = arrayBase.performUnaryOpOnObject(theIndex, operation_, preOrPost_);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result, lineNumber_);
				
				theIndex.decrementReferenceCount();
				rawArrayBase.decrementReferenceCount();
				
				return nextCode_;	// probably null
			}
			
			private final String operation_;
			private final PreOrPost preOrPost_;
			private final int lineNumber_;
		}
		
		private final RTExpression rTIndexExpression_;
		private final RTExpression rTArrayExpression_;
		private final RTArrayIndexExpressionUnaryOpPart2 part2_;
	}
	
	public static class RTRoleArrayIndexExpression extends RTExpression {
		public RTRoleArrayIndexExpression(final RoleArrayIndexExpression accessExpression, final RTType nearestEnclosingType) {
			super();
			roleName_ = accessExpression.roleName();
			rTIndexExpression_ = RTExpression.makeExpressionFrom( accessExpression.indexExpression(), nearestEnclosingType );
			nearestEnclosingType_ = nearestEnclosingType;
		}
		
		@Override public RTCode run() {
			RTCode pc = rTIndexExpression_;
			do {
				if (pc instanceof RTHalt) {
					return pc;
				}
				pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			} while (null != pc);
			
			final RTObject rawIndexResult = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawIndexResult instanceof RTIntegerObject;
			final RTIntegerObject indexResult = (RTIntegerObject) rawIndexResult;
			
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			
			// This can be elicited either in a Role method or a Context method. If
			// it's a Role method the Context pointer is in current$context. Otherwise,
			// current$context is undeclared and we just use "this"
			RTObject currentContext = null;
			if (nearestEnclosingType_ instanceof RTRole) {
				currentContext = RTExpression.getObjectUpToMethodScopeFrom("current$context", currentScope);
			} else {
				currentContext = RTExpression.getObjectUpToMethodScopeFrom("this", currentScope);
			}
			
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			final RTStackable rolePlayer = contextInfo.rolePlayerNamedAndIndexed(roleName_, indexResult);
			if (rolePlayer instanceof RTHalt) {
				return (RTCode)rolePlayer;
			} else if (null != rolePlayer) {
				RunTimeEnvironment.runTimeEnvironment_.pushStack(rolePlayer);
				return nextCode_;
			} else {
				return new RTHalt();
			}
		}
		public final String roleName() {
			return roleName_;
		}
		public RTExpression indexExpression() {
			return rTIndexExpression_;
		}

		private final RTExpression rTIndexExpression_;
		private final String roleName_;
		private final RTType nearestEnclosingType_;
	}
	
	public static class RTIf extends RTExpression {
		public RTIf(final IfExpression expr, final RTType nearestEnclosingType) {
			super();
			conditionalExpression_ = RTExpression.makeExpressionFrom(expr.conditional(), nearestEnclosingType);
			part2_ = new RTIfPart2(expr, nearestEnclosingType);
			if (null != conditionalExpression_) {
				// Stumbling check
				conditionalExpression_.setNextCode(part2_);
			}
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
			lineNumber_ = expr.lineNumber();
		}
		@Override public RTCode run() {
			// Put the condition on the stack
			return RunTimeEnvironment.runTimeEnvironment_.runner(conditionalExpression_);
		}
		@Override public void setNextCode(RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTIfPart2 extends RTExpression {
			public RTIfPart2(final IfExpression expr, final RTType nearestEnclosingType) {
				super();
				thenPart_ = RTExpression.makeExpressionFrom(expr.thenPart(), nearestEnclosingType);
				elsePart_ = RTExpression.makeExpressionFrom(expr.elsePart(), nearestEnclosingType);
			}
			@Override public RTCode run() {
				// The condition is on the stack
				RTCode retval = null;
				final RTObject conditionalExpression = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				assert conditionalExpression instanceof RTBooleanObject;
				final RTBooleanObject booleanExpression = (RTBooleanObject) conditionalExpression;
				if (booleanExpression.value()) {
					retval = thenPart_;
				} else {
					retval = elsePart_;
				}
				
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			@Override public void setNextCode(final RTCode code) {
				thenPart_.setNextCode(code);
				elsePart_.setNextCode(code);
			}
			private final RTExpression thenPart_, elsePart_;
		}
		public int lineNumber() {
			return lineNumber_;
		}
		
		private final RTIfPart2 part2_;
		private final RTExpression conditionalExpression_;
		private final int lineNumber_;
	}
	
	private static class RTForTestRunner extends RTExpression {
		public RTForTestRunner(final Expression testExpr, final RTExpression body, final RTPopDynamicScope popScope, final RTType nearestEnclosingType) {
			super();
			lineNumber_ = testExpr.lineNumber();
			test_ = RTExpression.makeExpressionFrom(testExpr, nearestEnclosingType);
			part2_ = new RTForTestRunnerPart2(body, popScope, lineNumber_);
			test_.setNextCode(part2_);
		}
		
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(test_);
		}
		
		@Override public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		private static class RTForTestRunnerPart2 extends RTExpression {
			public RTForTestRunnerPart2(final RTExpression body, final RTPopDynamicScope popScope, final int lineNumber) {
				super();
				lineNumber_ = lineNumber;
				body_ = body;
				popScope_ = popScope;
			}
			@Override public RTCode run() {
				RTCode retval = null;
				final RTObject conditionalExpression = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (conditionalExpression instanceof RTBooleanObject == false) {
					assert conditionalExpression instanceof RTBooleanObject;
				}
				final RTBooleanObject booleanExpression = (RTBooleanObject) conditionalExpression;
				if (booleanExpression.value()) {
					retval = body_;
				} else {
					// We're closing a scope - going out of the FOR.
					retval = popScope_;
				}
				
				setLastExpressionResult(conditionalExpression, lineNumber_);
				
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			private final RTExpression body_;
			private final RTPopDynamicScope popScope_;
			private final int lineNumber_;
		}
		
		private final RTExpression test_;
		private final RTForTestRunnerPart2 part2_;
		private final int lineNumber_;
	}
	
	public static class RTForCommon extends RTExpression implements RTBreakableExpression {
		public RTForCommon(final ForExpression expr, final RTType nearestEnclosingType) {
			super();
			
			objectDeclarations_ = new LinkedHashMap<String, RTType>();
			evaluationResult_ = null;
			
			// Copy from the compiler data, the local variable names
			// declared in the FOR scope
			for (ObjectDeclaration objectDecl : expr.scope().objectDeclarations()) {
				final RTType objectType = null;	 // sigh...
				objectDeclarations_.put(objectDecl.name(), objectType);
			}

			RTExpression anInitialization = null;
			final List<BodyPart> rawInitializations = expr.initExprs();
			initializations_ = new ArrayList<RTExpression>();
			for (final BodyPart aBodyPart : rawInitializations) {
				// "for (int i = 0 ..." ==> Expression$AssignmentExpression i = 0
				assert aBodyPart instanceof Expression;
				final Expression bodyPartAsExpression = (Expression)aBodyPart;
				anInitialization = RTExpression.makeExpressionFrom(bodyPartAsExpression, nearestEnclosingType);
				initializations_.add(anInitialization);
			}
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// A FOR loop opens a new scope. Open it.
			dynamicScope_ = new RTDynamicScope("for", RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(dynamicScope_);
						
			// Declare local variables
			for (final Map.Entry<String, RTType> iter : objectDeclarations_.entrySet()) {
				// "for (int i = 0 ..." ==> i
				final String objectName = iter.getKey();
				final RTType type = iter.getValue();
				dynamicScope_.addObjectDeclaration(objectName, type);
			}

			// Process the initializations in this scope
			// WAIT - do we do this any more? Yeah, it's O.K.
						
			RTCode retval = null;
			if (initializations_.size() > 0) {
				retval = RunTimeEnvironment.runTimeEnvironment_.runner(initializations_.get(0));
			} else {
				retval = test_;
			}
			
			final RTCode checkIterator = setupIterator();
			if (checkIterator instanceof RTHalt) {
				retval = checkIterator;
			}
			
			// And run the loop, starting with the rest of the initializations
			// if necessary
			return retval;
		}
		protected RTCode setupIterator() {
			// by default, nothing. Overridden in some derived classes
			return null;
		}
		@Override public RTCode continueHook() {
			return test_;
		}
		public String uniqueLabel() {
			return label_;
		}
		@Override public RTCode last() {
			return last_;
		}
		public RTCode breakExit() {
			// return popScope_;
			return evaluationResult_;
		}
		@Override public void setNextCode(final RTCode code) {
			assert null != last_;
			super.setNextCode(code);
			last_.setNextCode(code);
		}
		
		protected RTExpression test_, body_, last_;
		protected RTDynamicScope dynamicScope_;
		protected String label_;
		protected RTPopDynamicScope popScope_;
		protected final List<RTExpression> initializations_;
		protected final Map<String, RTType> objectDeclarations_;
		protected       RTExpression evaluationResult_;
	}
	
	public static class RTFor extends RTForCommon implements RTBreakableExpression {
		public RTFor(final ForExpression expr, final RTType nearestEnclosingType) {
			super(expr, nearestEnclosingType);
			final ParsingData parsingData = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();
			
			// See if the "for" loop itself has an "initialization" expression.
			// If it was part of a declaration / initialization then it
			// was already covered in the above loop. But if the loop variable
			// is declared in a more outer scope it comes in here as a statement
			// in its own right.
			final Expression initializationExpression = expr.initializationExpression();
			if (null != initializationExpression) {
				final RTExpression anInitialization = RTExpression.makeExpressionFrom(initializationExpression, nearestEnclosingType);
				initializations_.add(anInitialization);
			}
			
			final RTExpressionList body = new RTExpressionList( expr.body(), nearestEnclosingType );
			body_ = body;
			
			increment_ = RTExpression.makeExpressionFrom( expr.increment(), nearestEnclosingType );
			body.addExpression(increment_);
			
			final RTExpression goBackToTest = new RTNullExpression();
			body.addExpression(goBackToTest);
			
			popScope_ = new RTPopDynamicScope();
			body.addExpression(popScope_);
			
			if (this.resultIsConsumed()) {
				evaluationResult_ = new RTPushEvaluationResult();
				body.addExpression(evaluationResult_);
			} else {
				evaluationResult_ = new RTNullExpression();
				body.addExpression(evaluationResult_);
			}
			
			last_ = new RTNullExpression();
			body.addExpression(last_);
			
			test_ = new RTForTestRunner(expr.test(), body_, popScope_, nearestEnclosingType);
			goBackToTest.setNextCode(test_);	// the test chunk
			
			final int listSize = initializations_.size();
			if (0 < listSize) {
				final RTExpression lastInitialization = initializations_.get(listSize - 1);
				assert null != test_;
				lastInitialization.setNextCode(test_);
			}
			
			dynamicScope_ = null;
			
			label_ = expr.uniqueLabel();
			parsingData.addBreakableRTExpression(label_, this);
		}
		
		private final RTExpression increment_;
		private final RTPopDynamicScope popScope_;
	}
	
	private static class RTForIterationTestRunner extends RTExpression {
		public RTForIterationTestRunner(final RTExpression body, final RTPopDynamicScope popScope, final ObjectDeclaration iterationVariable) {
			super();
			popScope_ = popScope;
			part2_ = new RTForIterationTestRunnerPart2(body, iterationVariable);
		}
		
		@Override public RTCode run() {
			final RTDynamicScope dynamicScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject rawIterator = dynamicScope.getObject("for$iterator");
			assert rawIterator instanceof RTIterator;
			final RTIterator iterator = (RTIterator)rawIterator;
			RTCode retval = null;
			if (iterator.isThereANext()) {
				retval = part2_;
				final RTObject currentElement = iterator.next();
				RunTimeEnvironment.runTimeEnvironment_.pushStack(currentElement);
				iterator.advance();
			} else {
				retval = popScope_;
			}
			return retval;
		}
		
		private static class RTForIterationTestRunnerPart2 extends RTExpression {
			RTForIterationTestRunnerPart2(final RTExpression body, final ObjectDeclaration iterationVariable) {
				super();
				body_ = body;
				iterationVariable_ = iterationVariable;
			}
			@Override public RTCode run() {
				// Do the assign
				final String iterationIdentifierName = iterationVariable_.name();
				final RTObject valueFromIteration = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTDynamicScope dynamicScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTDynamicScope scope = dynamicScope.nearestEnclosingScopeDeclaring(iterationIdentifierName);
				
				if (null == scope) {
					ErrorLogger.error(ErrorType.Internal, 0, "INTERNAL: Cannot find loop variable ",
							iterationIdentifierName, ".", "");
					RTMessage.printMiniStackStatus();
					
					// Halt the machine
					return new RTHalt();
				} else if (null != valueFromIteration) {
					scope.setObject(iterationIdentifierName, valueFromIteration);
					return body_;
				} else {
					ErrorLogger.error(ErrorType.Internal, 0, "Runtime: `",
							iterationIdentifierName, "' has been bound to an uninitialized object.", "");
					RTMessage.printMiniStackStatus();
					
					// Halt the machine
					return new RTHalt();
				}
			}
			
			final RTExpression body_;
			final ObjectDeclaration iterationVariable_;
		}
		
		final RTPopDynamicScope popScope_;
		final RTForIterationTestRunnerPart2 part2_;
	}
	public static class RTForIteration extends RTForCommon implements RTBreakableExpression {
		public RTForIteration(final ForExpression expr, final RTType nearestEnclosingType) {
			super(expr, nearestEnclosingType);
			final ParsingData parsingData = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();
			
			final RTExpressionList body = new RTExpressionList( expr.body(), nearestEnclosingType );
			body_ = body;
			
			final RTExpression goBackToTest = new RTNullExpression();
			body.addExpression(goBackToTest);
			
			popScope_ = new RTPopDynamicScope();
			body.addExpression(popScope_);
			
			if (this.resultIsConsumed()) {
				evaluationResult_ = new RTPushEvaluationResult();
				body.addExpression(evaluationResult_);
			} else {
				evaluationResult_ = new RTNullExpression();
				body.addExpression(evaluationResult_);
			}
			
			last_ = new RTNullExpression();
			body.addExpression(last_);
			
			test_ = new RTForIterationTestRunner(body_, popScope_, expr.iterationVariable());
			
			assert null != test_;
			goBackToTest.setNextCode(test_);	// the test chunk
			
			final int listSize = initializations_.size();
			if (0 < listSize) {
				final RTExpression lastInitialization = initializations_.get(listSize - 1);
				lastInitialization.setNextCode(test_);
			}
			
			rTThingToIterateOverExpr_ = RTExpression.makeExpressionFrom(expr.thingToIterateOver(), nearestEnclosingType);
			
			dynamicScope_ = null;
			
			label_ = expr.uniqueLabel();
			parsingData.addBreakableRTExpression(label_, this);
		}
		
		protected RTCode setupIterator() {
			RTCode pc = RunTimeEnvironment.runTimeEnvironment_.runner(rTThingToIterateOverExpr_);
			while (null != pc) {
				if (pc instanceof RTHalt) {
					return pc;
				} else {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				}
			}
			final RTStackable rawThingToIterateOver = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawThingToIterateOver instanceof RTIterable;
			final RTIterable rTThingToIterateOver = (RTIterable)rawThingToIterateOver;
			final RTIterator iterator = RTIterator.makeIterator(rTThingToIterateOver);
			dynamicScope_.addObjectDeclaration("for$iterator", null);
			dynamicScope_.setObject("for$iterator", iterator);
			return pc;
		}
	
		private RTExpression rTThingToIterateOverExpr_;
	}
	
	private static class RTWhileTestRunner extends RTExpression {
		public RTWhileTestRunner(final Expression testExpr, final RTExpression body, final RTExpression last, final RTType nearestEnclosingType) {
			super();
			lineNumber_ = testExpr.lineNumber();
			test_ = RTExpression.makeExpressionFrom(testExpr, nearestEnclosingType);
			part2_ = new RTWhileTestRunnerPart2(body, last, lineNumber_);
			test_.setNextCode(part2_);
		}
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(test_);
		}
		
		@Override public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		private static class RTWhileTestRunnerPart2 extends RTExpression {
			public RTWhileTestRunnerPart2(final RTExpression body, final RTExpression last, final int lineNumber) {
				super();
				lineNumber_ = lineNumber;
				body_ = body;
				last_ = last;
			}
			@Override public RTCode run() {
				RTCode retval = null;
				final RTObject conditionalExpression = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				assert conditionalExpression instanceof RTBooleanObject;
				
				final RTBooleanObject booleanExpression = (RTBooleanObject) conditionalExpression;
				if (booleanExpression.value()) {
					retval = body_;
				} else {
					retval = last_;
				}
				
				setLastExpressionResult(conditionalExpression, lineNumber_);
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			
			private final RTExpression body_, last_;
			private final int lineNumber_;
		}
		
		private final RTExpression test_;
		private final int lineNumber_;;
		private final RTWhileTestRunnerPart2 part2_;
	}
	public static class RTWhile extends RTExpression implements RTBreakableExpression {
		public RTWhile(final WhileExpression expr, final RTType nearestEnclosingType) {
			super();
			final ParsingData parsingData = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();

			body_ = new RTExpressionList(expr.body(), nearestEnclosingType);
			
			final RTExpression goBackToTest = new RTNullExpression();
			((RTExpressionList)body_).addExpression(goBackToTest);
			
			if (expr.resultIsConsumed()) {
				last_ = new RTPushEvaluationResult();
			} else {
				last_ = new RTNullExpression();
			}
			((RTExpressionList)body_).addExpression(last_);
			
			test_ = new RTWhileTestRunner(expr.test(), body_, last_, nearestEnclosingType);
			
			label_ = expr.uniqueLabel();
			parsingData.addBreakableRTExpression(label_, this);
			
			assert null != test_;
			goBackToTest.setNextCode(test_);	// the test chunk
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// And run the loop
			return test_;
		}
		@Override public void setNextCode(final RTCode code) {
			assert null != last_;
			last_.setNextCode((nextCode_ = code));
		}
		public String uniqueLabel() {
			return label_;
		}
		@Override public RTCode last() {
			return last_;
		}
		@Override public RTCode breakExit() {
			// There is no RTPopDynamicScope node in our list, so the exit
			// point is "last_" with no need for a separate node
			return last_;
		}
		@Override public RTCode continueHook() {
			return test_;
		}
		
		private RTExpression test_, body_, last_;
		private final String label_;
	}
	
	private static class RTDoWhileTestRunner extends RTExpression {
		public RTDoWhileTestRunner(final Expression testExpr, final RTExpression body, final RTExpression last, final RTType nearestEnclosingType) {
			super();
			test_ = RTExpression.makeExpressionFrom(testExpr, nearestEnclosingType);
			part2_ = new RTDoWhileTestRunnerPart2(testExpr, body, last);
			test_.setNextCode(part2_);
		}
		
		@Override public RTCode run() {
			return RunTimeEnvironment.runTimeEnvironment_.runner(test_);
		}
		
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		private static class RTDoWhileTestRunnerPart2 extends RTExpression {
			public RTDoWhileTestRunnerPart2(final Expression testExpr, final RTExpression body, final RTExpression last) {
				super();
				body_ = body;
				last_ = last;
				lineNumber_ = testExpr.lineNumber();
			}
			
			@Override public RTCode run() {
				RTCode retval = null;
				final RTObject conditionalExpression = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				assert conditionalExpression instanceof RTBooleanObject;
				final RTBooleanObject booleanExpression = (RTBooleanObject) conditionalExpression;
				if (booleanExpression.value()) {
					retval = body_;
				} else {
					retval = last_;
				}
				
				setLastExpressionResult(conditionalExpression, lineNumber_);
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			
			private final RTExpression body_, last_;
			private final int lineNumber_;
		}
		
		private final RTExpression test_;
		private final RTDoWhileTestRunnerPart2 part2_;
	}
	public static class RTDoWhile extends RTExpression implements RTBreakableExpression {
		public RTDoWhile(final DoWhileExpression expr, final RTType nearestEnclosingType) {
			super();
			final ParsingData parsingData = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();

			body_ = new RTExpressionList(expr.body(), nearestEnclosingType);
			
			if (expr.resultIsConsumed()) {
				last_ = new RTPushEvaluationResult();
			} else {
				last_ = new RTNullExpression();
			}
			last_.setNextCode(nextCode());
			test_ = new RTDoWhileTestRunner(expr.test(), body_, last_, nearestEnclosingType);
			
			body_.setNextCode(test_);
			
			label_ = expr.uniqueLabel();
			parsingData.addBreakableRTExpression(label_, this);
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// And run the loop
			return body_;
		}
		@Override public void setNextCode(final RTCode code) {
			assert null != last_;
			last_.setNextCode((nextCode_ = code));
		}
		public String uniqueLabel() {
			return label_;
		}
		@Override public RTCode last() {
			return last_;
		}
		@Override public RTCode breakExit() {
			// There is no RTPopDynamicScope node in our list, so the exit
			// point is "last_" with no need for a separate node
			return last_;
		}
		@Override public RTCode continueHook() {
			return test_;
		}
		
		private RTExpression test_, body_, last_;
		private final String label_;
	}
	
	public static class RTExpressionList extends RTExpression {
		// This class is a bit different, because it can in theory take
		// any kind of expression as an argument
		public RTExpressionList(final Expression expr, final RTType nearestEnclosingType) {
			this();
			if (expr instanceof ExpressionList) {
				List<Expression> exprList = ((ExpressionList)expr).expressions();
				for (Expression e : exprList) {
					final RTExpression rTNewExpr = RTExpression.makeExpressionFrom(e, nearestEnclosingType);
					if (null != last_) {
						last_.setNextCode(rTNewExpr);
					}
					last_ = rTNewExpr;
					expressionList_.add(rTNewExpr);
				}
			} else {
				expressionList_.add((last_ = RTExpression.makeExpressionFrom(expr, nearestEnclosingType)));
			}
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		public RTExpressionList() {
			super();
			expressionList_ = new ArrayList<RTExpression>();
			last_ = null;
		}
		public void addExpression(final RTExpression rTNewExpr) {
			if (null != rTNewExpr) {
				RTExpression lastExpression = null;
				final int sizeOfExpressionList = expressionList_.size();
				if (0 < sizeOfExpressionList) {
					lastExpression = expressionList_.get(sizeOfExpressionList - 1);
					rTNewExpr.setNextCode(lastExpression.nextCode());
					lastExpression.setNextCode(rTNewExpr);
				} else {
					;
				}
				expressionList_.add((last_ = rTNewExpr));
			}
		}
		@Override public RTCode run() {
			RTCode retval = null;
			if (expressionList_.size() > 0) {
				final RTCode doit = expressionList_.get(0);
				retval = RunTimeEnvironment.runTimeEnvironment_.runner(doit);
			} else {
				retval = this.nextCode();
			}
			return retval;
		}
		@Override public RTCode nextCode() {
			// The nextCode() value of an ExpressionList is always the
			// second element in the list. The RTExpressionList node is a
			// wrapper around a sequence of statements. Anyone holding
			// that wrapper will have executed its "run", which will
			// handle the get(0) entry. Then the holder will ask for the
			// nextCode() value, which keeps the ball rolling.
			//
			// Note a lack of parallelism with setNextCode, below!!!
			//
			// By default, send a null value
			RTCode retval = null;
			if (expressionList_.size() > 0) {
				retval = expressionList_.get(0).nextCode();
			}
			return retval;
		}
		@Override public void setNextCode(final RTCode code) {
			if (null == last_) {
				last_ = new RTNullExpression();
				expressionList_.add(last_);
			}
			last_.setNextCode((nextCode_ = code));
		}
		public RTExpression last() {
			return last_;
		}
		public List<RTExpression> expressionList() {
			return expressionList_;
		}
		
		private List<RTExpression> expressionList_;
		private RTExpression last_;
	}
	
	public static class RTSwitch extends RTExpression implements RTBreakableExpression {
		public RTSwitch(final SwitchExpression expr, final RTType enclosingType) {
			super();
			part2_ = new RTSwitchPart2(expr, this);
			final ParsingData parsingData = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();
			
			lineNumber_ = expr.lineNumber();
			
			RTCode lastAdded = null;
			
			// A switch statement opens a new activation record.
			// The last thing that the "switch block" should do is to close
			// that scope
			last_ = new RTPopDynamicScope();
			
			// Copy from the compiler data, the local variable names
			// declared in the "Block" scope
			objectDeclarations_ = new LinkedHashMap<String, RTType>();
			
			for (final ObjectDeclaration objectDecl : expr.enclosedScope().objectDeclarations()) {
				final RTType objectType = null;	 // sigh...
				objectDeclarations_.put(objectDecl.name(), objectType);
			}

			cases_ = new LinkedHashMap<RTObject, RTExpressionList>();
			defaultCase_ = null;
			switchStack_.push(this);
			switchExpression_ = RTExpression.makeExpressionFrom(expr.switchExpression(), enclosingType);
			orderedSwitchBodyElements_ = expr.orderedSwitchBodyElements();
			for (final SwitchBodyElement caseOrDefault : orderedSwitchBodyElements_) {
				final Constant caseValue = caseOrDefault.constant();
				RTObject rTCaseValue = null;
				if (null != caseValue) {
					// Non-default case
					rTCaseValue = new RTConstant(caseValue);
				} else {
					rTCaseValue = null;
				}
				RTExpressionList rTCaseBody = bodyPartsToRTExpressionList(caseOrDefault.bodyParts(), enclosingType);
				RTCode lastInThisLeg = rTCaseBody.last();
				if (null == lastInThisLeg) {
					// We can actually get zero-length bodies. Consider two
					// case labels in a row.

					rTCaseBody = new RTExpressionList();
					lastInThisLeg = new RTNullExpression();
					rTCaseBody.addExpression((RTExpression)lastInThisLeg);
				}
				
				assert null != lastInThisLeg;
				
				if (null != lastAdded) {
					lastAdded.setNextCode(rTCaseBody);
				}
				lastAdded = rTCaseBody;
				rTCaseBody.setNextCode(last_);
				
				// last_ doesn't move....
				
				if (null != rTCaseValue) {	
					cases_.put(rTCaseValue, rTCaseBody);
				} else {
					defaultCase_ = rTCaseBody;
				}
			}
			
			switchStack_.pop();
			label_ = expr.uniqueLabel();
			parsingData.addBreakableRTExpression(label_, this);
			
			switchExpression_.setNextCode(part2_);
			
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}

		@Override public RTCode run() {
			final RTDynamicScope dynamicScope = new RTDynamicScope("switch @ line " + Integer.toString(lineNumber_),
					RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
			
			// Declare local variables
			for (Map.Entry<String, RTType> iter : objectDeclarations_.entrySet()) {
				final String objectName = iter.getKey();
				final RTType type = iter.getValue();
				dynamicScope.addObjectDeclaration(objectName, type);
			}
			
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(dynamicScope);
			final RTCode retval = RunTimeEnvironment.runTimeEnvironment_.runner(switchExpression_);
			assert null != retval;
			return retval;
		}
		@Override public RTCode nextCode() {
			return last_;
		}
		@Override public void setNextCode(final RTCode code) {
			if (null == last_) {
				// Should never be true. Test with an assert. TODO.
				last_ = new RTNullExpression();
			}
			last_.setNextCode((nextCode_ = code));
		}
		public String uniqueLabel() {
			return label_;
		}
		@Override public RTCode last() {
			return last_;
		}
		@Override public RTCode breakExit() {
			// There is no RTPopDynamicScope node in our list, so the exit
			// point is "last_" with no need for a separate node
			return last_;
		}
		@Override public RTCode continueHook() {
			return null;
		}
		
		private static class RTSwitchPart2 extends RTExpression implements RTBreakableExpression {
			public RTSwitchPart2(final SwitchExpression expr, final RTSwitch original) {
				super();
				original_ = original;
			}

			@Override public RTCode run() {
				RTCode retval = null != original_.defaultCase_? original_.defaultCase_: original_.last_;
				final RTObject expression = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				for (Map.Entry<RTObject, RTExpressionList> iter: original_.cases_.entrySet()) {
					final RTObject selector = iter.getKey();
					assert selector instanceof RTConstant;
					final RTConstant caseValueAsRTConstant = (RTConstant)selector;
					
					if (caseValueAsRTConstant.isEqualTo(expression)) {
						final RTExpressionList caseLogic = iter.getValue();
						retval = caseLogic;
						break;
					}
				}
				
				expression.decrementReferenceCount();
				
				assert null != retval;
				return retval;
			}
			@Override public RTCode nextCode() {
				return original_.last_;
			}
			@Override public void setNextCode(final RTCode code) {
				if (null == original_.last_) {
					// Should never be true. Test with an assert. TODO.
					original_.last_ = new RTNullExpression();
				}
				original_.last_.setNextCode((nextCode_ = code));
			}
			@Override public RTCode last() {
				assert false;
				return original_.last_;
			}
			@Override public RTCode breakExit() {
				// There is no RTPopDynamicScope node in our list, so the exit
				// point is "last_" with no need for a separate node
				assert false;
				return original_.last_;
			}
			@Override public RTCode continueHook() {
				assert false;
				return null;
			}
			
			final private RTSwitch original_;
		}
		
		private RTExpression switchExpression_;
		private RTCode last_;
		private List<SwitchBodyElement> orderedSwitchBodyElements_;
		private Map<RTObject, RTExpressionList> cases_;
		private HashMap<String, RTType> objectDeclarations_;
		private RTExpressionList defaultCase_;
		private final String label_;
		private final RTSwitchPart2 part2_;
		private final int lineNumber_;
	}
	
	public static class RTConstant extends RTExpression implements RTObject {
		public RTConstant(final int lineNumber) {
			super();
			lineNumber_ = lineNumber;
			rTExpr_ = null;
		}
		public RTConstant(final Constant expr) {
			this(expr.lineNumber());
			if (expr instanceof StringConstant) {
				rTExpr_ = new RTStringObject(((StringConstant)expr).value());
			} else if (expr instanceof IntegerConstant) {
				rTExpr_ = new RTIntegerObject(((IntegerConstant)expr).value());
			} else if (expr instanceof DoubleConstant) {
				rTExpr_ = new RTDoubleObject(((DoubleConstant)expr).value());
			} else if (expr instanceof BooleanConstant) {
				rTExpr_ = new RTBooleanObject(((BooleanConstant)expr).value());
			} else {
				assert false;
			}
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTObject toThePowerOf(final RTObject other) {
			return rTExpr_.toThePowerOf(other);
		}
		@Override public RTCode run() {
			// WARNING: We dup this here, otherwise it could be bound to
			// an identifier and potentially have its value changed!!!
			RunTimeEnvironment.runTimeEnvironment_.pushStack(rTExpr_.dup());
			setLastExpressionResult(rTExpr_, lineNumber_);
			return nextCode_;
		}
		@Override public RTObject performUnaryOpOnObjectNamed(final String idName, final String operator, final PreOrPost preOrPost_) {
			assert false; 	// meaningless for constants
			return null;
		}
		@Override public RTObject dup() {
			// silly wabbit
			return this;
		}
		
		// For "implements RTObject":
		@Override public RTObject getObject(final String name) { assert false; return null; }
		@Override public void addObjectDeclaration(final String objectName, final RTType type) { assert false; }
		@Override public Map<String, RTType> objectDeclarations() { assert false; return null; }
		@Override public void setObject(final String objectName, final RTObject object) { assert false; }
		@Override public RTType rTType() { return rTExpr_.rTType(); }
		@Override public boolean isEqualTo(final Object another) { return rTExpr_.isEqualTo(another); }
		@Override public boolean gt(final RTObject another) { return rTExpr_.gt(another); }
		@Override public int compareTo(final Object another) { return rTExpr_.compareTo(another); }
		@Override public RTObject plus(final RTObject other) { return rTExpr_.plus(other); }
		@Override public RTObject minus(final RTObject other) { return rTExpr_.minus(other); }
		@Override public RTObject logicalAnd(final RTObject other) { assert false; return null; }
		@Override public RTObject logicalOr(final RTObject other) { assert false; return null; }
		@Override public RTObject logicalXor(final RTObject other) { assert false; return null; }
		@Override public RTObject times(final RTObject other) { return rTExpr_.times(other); }
		@Override public RTObject divideBy(final RTObject other) { return rTExpr_.divideBy(other); }
		@Override public RTObject modulus(final RTObject other) { return rTExpr_.modulus(other); }
		@Override public RTObject unaryPlus() { return rTExpr_.unaryPlus(); }
		@Override public RTObject unaryMinus() { return rTExpr_.unaryMinus(); }
		@Override public RTObject unaryLogicalNegation() { return rTExpr_.unaryLogicalNegation(); }
		@Override public RTObject preIncrement() { return rTExpr_.preIncrement(); }
		@Override public RTObject postIncrement() { return rTExpr_.postIncrement(); }
		@Override public RTObject preDecrement() { return rTExpr_.preDecrement(); }
		@Override public RTObject postDecrement() { return rTExpr_.postDecrement(); }
		@Override public void enlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) { assert false; }
		@Override public void unenlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {  assert false; }
		@Override public void enlistAsStagePropPlayerForContext(final String stagePropName, final RTContextObject contextInstance) { assert false; }
		@Override public void unenlistAsStagePropPlayerForContext(final String stagePropName, final RTContextObject contextInstance) {  assert false; }
		@Override public boolean equals(final RTObject other) { assert false; return false; }
		
		@Override public String getText() {
			return rTExpr_.getText();
		}
		@Override public String toString() {
			return this.getText();
		}
		public int lineNumber() {
			return lineNumber_;
		}
		
		protected RTObject rTExpr_;
		private final int lineNumber_;
	}
	
	public static class RTBreak extends RTExpression {
		public RTBreak(final BreakExpression expr) {
			super();
			parsingData_ = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();
			final BreakableExpression associatedLoop = expr.loop();
			label_ = associatedLoop.uniqueLabel();
			firstIter_ = true;
			nestingLevelInsideBreakable_ = expr.nestingLevelInsideBreakable();
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			if (firstIter_) {
				// efficiency hack
				final Map<String, RTBreakableExpression> allBreakablesMap = parsingData_.breakableRTExpressions();
				associatedBreakable_ = allBreakablesMap.get(label_);
				assert null != associatedBreakable_;
				breakExit_ = associatedBreakable_.breakExit();
				assert null != breakExit_;
				firstIter_ = false;
			}
			
			if (associatedBreakable_.resultIsConsumed()) {
				// Put the last expression on the stack
				RunTimeEnvironment.runTimeEnvironment_.pushStack(lastExpressionResult_);
			}
			
			// Pop as many dynamic scopes as we must
			RunTimeEnvironment.runTimeEnvironment_.popDynamicScopeInstances(nestingLevelInsideBreakable_);
			
			return breakExit_;
		}
		private boolean firstIter_;
		private RTBreakableExpression associatedBreakable_;
		private final String label_;
		private final ParsingData parsingData_;
		private RTCode breakExit_;
		private final long nestingLevelInsideBreakable_;
	}
	
	public static class RTContinue extends RTExpression {
		public RTContinue(final ContinueExpression expr) {
			super();
			parsingData_ = InterpretiveCodeGenerator.interpretiveCodeGenerator.parsingData();
			final BreakableExpression associatedLoop = expr.loop();
			label_ = associatedLoop.uniqueLabel();
			nestingLevelInsideBreakable_ = expr.nestingLevelInsideBreakable();
			firstIter_ = true;
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			if (firstIter_) {
				// efficiency hack
				final Map<String, RTBreakableExpression> allBreakablesMap = parsingData_.breakableRTExpressions();
				associatedBreakable_ = allBreakablesMap.get(label_);
				assert null != associatedBreakable_;
				continueHook_ = associatedBreakable_.continueHook();
				assert null != continueHook_;
				firstIter_ = false;
			}
			
			if (this.resultIsConsumed()) {
				// Put the last expression on the stack
				RunTimeEnvironment.runTimeEnvironment_.pushStack(lastExpressionResult_);
			}

			if (nestingLevelInsideBreakable_ > 0) {
				// Pop as many dynamic scopes as we must
				RunTimeEnvironment.runTimeEnvironment_.popDynamicScopeInstances(nestingLevelInsideBreakable_);
			}
			
			return continueHook_;
		}
		private boolean firstIter_;
		private RTBreakableExpression associatedBreakable_;
		private final String label_;
		private final ParsingData parsingData_;
		private RTCode continueHook_;
		private final long nestingLevelInsideBreakable_;
	}
	
	public static class RTSum extends RTExpression {
		public RTSum(final SumExpression expr, final RTType nearestEnclosingType) {
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTSumPart2(expr);
			
			lhs_.setNextCode(rhs_);
			rhs_.setNextCode(part2_);
			
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			return lhs_;
		}
		
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		private static class RTSumPart2 extends RTExpression {
			public RTSumPart2(final SumExpression expr) {
				operator_ = expr.operator();
				lineNumber_ = expr.lineNumber();
				setResultIsConsumed(expr.resultIsConsumed());
			}
			@Override public RTCode run() {
				RTObject result = null;
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (operator_.equals("+")) {
					result = lhs.plus(rhs);
				} else if (operator_.equals("-")) {
					result = lhs.minus(rhs);
				} else if (operator_.equals("||")) {
					result = lhs.logicalOr(rhs);
				} else if (operator_.equals("&&")) {
					result = lhs.logicalAnd(rhs);
				} else if (operator_.equals("^")) {
					result = lhs.logicalXor(rhs);
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result, lineNumber_);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final int lineNumber_;
		}
		
		private RTExpression lhs_, rhs_;
		private final RTSumPart2 part2_;
	}
	public static class RTProduct extends RTExpression {
		public RTProduct(final ProductExpression expr, final RTType nearestEnclosingType) {
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTProductPart2(expr);
			
			if (null != rhs_ && null != lhs_) {
				lhs_.setNextCode(rhs_);
				rhs_.setNextCode(part2_);
				
				setResultIsConsumed(expr.resultIsConsumed());
				part2_.setResultIsConsumed(expr.resultIsConsumed());
			} else {
				lhs_ = rhs_ = new RTNullExpression();
				ErrorLogger.error(ErrorType.Internal, 0, "Internal error in building RTProduct", "", "", "");
			}
		}
		@Override public RTCode run() {
			return lhs_;
		}
		
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		private static class RTProductPart2 extends RTExpression {
			public RTProductPart2(final ProductExpression expr) {
				operator_ = expr.operator();
				lineNumber_ = expr.lineNumber();
				setResultIsConsumed(expr.resultIsConsumed());
			}
			@Override public RTCode run() {
				RTObject result = null;
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (operator_.equals("*")) {
					result = lhs.times(rhs);
				} else if (operator_.equals("/")) {
					result = lhs.divideBy(rhs);
				} else if (operator_.equals("%")) {
					result = lhs.modulus(rhs);
				} else if (operator_.equals("&&")) {
					result = lhs.logicalAnd(rhs);
				} else if (operator_.equals("||")) {
					result = lhs.logicalOr(rhs);
				} else if (operator_.equals("^")) {
					result = lhs.logicalXor(rhs);
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result, lineNumber_);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final int lineNumber_;
		}
		
		private RTExpression lhs_, rhs_;
		private final RTProductPart2 part2_;
	}
	
	public static class RTPower extends RTExpression {
		public RTPower(final PowerExpression expr, final RTType nearestEnclosingType) {
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTPowerPart2(expr);
			
			lhs_.setNextCode(rhs_);
			rhs_.setNextCode(part2_);
			
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			return lhs_;
		}
		
		@Override public void setNextCode(RTCode code) {
			part2_.setNextCode(code);
		}
		
		private static class RTPowerPart2 extends RTExpression {
			public RTPowerPart2(final PowerExpression expr) {
				setResultIsConsumed(expr.resultIsConsumed());
				lineNumber_ = expr.lineNumber();
			}
			@Override public RTCode run() {
				RTObject result = null;
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				result = lhs.toThePowerOf(rhs);
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result, lineNumber_);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final int lineNumber_;
		}
		
		private RTExpression lhs_, rhs_;
		private final RTPowerPart2 part2_;
	}
	
	public static class RTPopDynamicScope extends RTExpression {
		public RTPopDynamicScope() {
			super();
		}
		@Override public RTCode run() {
			// Pop activation record
			final RTDynamicScope lastPoppedScope = RunTimeEnvironment.runTimeEnvironment_.popDynamicScope();
			lastPoppedScope.decrementReferenceCount();
			lastPoppedScope.closeScope();	// this isn't uniformly used; should be deprecated. FIXME
			return super.nextCode();
		}
	}
	
	public static class RTReturn extends RTExpression {
		public RTReturn(final String methodName, final List<RTCode> returnExpression,
				final RTType nearestEnclosingType, final int nestingLevelInsideMethod) {
			super();
			
			rTRe_ = returnExpression;

			lineNumber_ = 0;
			nestingLevelInsideMethod_ = nestingLevelInsideMethod;
			
			// So far this is used only for debugging
			methodName_ = methodName;
		}
		public RTReturn(final String methodName, final Expression returnExpression,
				final RTType nearestEnclosingType, final int nestingLevelInsideMethod) {
			super();

			// If returnExpr isn't null, then there's a return value. It's the
			// responsibility of the return statement to evaluate it and put
			// it on the stack, and to get it back to the caller
			if (null != returnExpression) {
				rTRe_ = new ArrayList<RTCode>();
				
				// Can be a NullExpression under error conditions
				if (returnExpression instanceof ReturnExpression) {
					assert returnExpression instanceof ReturnExpression == false;
				}
				final RTExpression expressionToCreateReturnValue =
						RTExpression.makeExpressionFrom(returnExpression, nearestEnclosingType);
				rTRe_.add(expressionToCreateReturnValue);
			} else {
				rTRe_ = null;
			}
			
			// So far this is used only for debugging
			methodName_ = methodName;
			lineNumber_ = null == returnExpression? 0: returnExpression.lineNumber();

			nestingLevelInsideMethod_ = nestingLevelInsideMethod;
		}
		@Override public RTCode run() {
			RTCode returnAddress = null;
			boolean thereIsAReturnExpression = false;		// hasAReturnExpression
			List<RTCode> returnExpressionList = null;
		
			if (null != rTRe_) {
				returnExpressionList = rTRe_;
				if (returnExpressionList.size() == 1) {
					final RTCode returnExpression = returnExpressionList.get(0);
					if (returnExpression instanceof RTNullExpression == true) {
						// In the old days we didn't handle return expressions
						// so religiously, and all it took was a non-null
						// rTRe_ list here to constitute a return expression. Now
						// with real ReturnExpression processing we actually evaluate
						// the expression explicitly. This makes a problem for the
						// print empire, which takes a shortcut.
						thereIsAReturnExpression = false;
					} else {
						thereIsAReturnExpression = true;
					}
				} else {
					thereIsAReturnExpression = false;
				}
			}
			if (thereIsAReturnExpression) {
				// It's already evaluated and on top of the stack? No. Gotta do it now.
				// Step 1: Get it - if it's used
				RTStackable returnValue = null;
				if (resultIsConsumed()) {	// maybe is always true?
					RTCode pc = returnExpressionList.get(0);
					do {
						pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
						if (pc instanceof RTHalt) return pc;
					} while (pc != null && pc instanceof RTHalt == false);
					
					// Now, the return value is on top of the stack. Pop it temporarily.
					returnValue = RunTimeEnvironment.runTimeEnvironment_.popStack();
				}
				
				// Step 2. Clean up the stack. First, get out to method scope
				// Pop as many dynamic scopes as we must
				RunTimeEnvironment.runTimeEnvironment_.popDynamicScopeInstances(nestingLevelInsideMethod_);
				
				// Now get ready to interface with the caller
				RunTimeEnvironment.runTimeEnvironment_.popDownToFramePointer();
				returnAddress = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				
				// Step 3. Put the return value back on the stack.
				if (resultIsConsumed()) {
					if (null != returnValue) {		// stumbling check
						RunTimeEnvironment.runTimeEnvironment_.pushStack(returnValue);
						returnValue.decrementReferenceCount();	// (from the stack pop above)
					} else {
						return new RTHalt();
					}
				}
			} else {
				// There is no R-value on the stack: just get the return address
				
				// Step 2, as above Clean up the stack. Get out to method scope
				// Pop as many dynamic scopes as we must
				RunTimeEnvironment.runTimeEnvironment_.popDynamicScopeInstances(nestingLevelInsideMethod_);
				
				// Do the corresponding pops of scope from the run-time stack
				RunTimeEnvironment.runTimeEnvironment_.popDownToFramePointer();
				returnAddress = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
			}
			
			assert returnAddress instanceof RTCode || returnAddress == null;
			
			// Pop the last activation record for this call
			final RTDynamicScope lastPoppedScope = RunTimeEnvironment.runTimeEnvironment_.popDynamicScope();
			lastPoppedScope.decrementReferenceCount();
			
			// No one else should have a handle to the activation record
			assert lastPoppedScope.referenceCount() == 0;
			
			lastPoppedScope.closeScope();
			
			if (null != returnAddress) {
				returnAddress.decrementReferenceCount();
			}
			
			return returnAddress;
		}
		public String methodName() {
			return methodName_;
		}
		public int lineNumber() {
			return lineNumber_;
		}
		@Override public boolean resultIsConsumed() {
			// All dogs go to heaven
			return true;
		}
		
		private List<RTCode> rTRe_;
		
		// Only for debugging and tracing
		private final String methodName_;
		private final int lineNumber_;
		private final int nestingLevelInsideMethod_;
	}
	
	public static class RTDummyReturn extends RTReturn {
		public RTDummyReturn(final String methodName, final Expression returnExpression, final RTType nearestEnclosingType,
				final int nestingLevelInsideMethod) {
			super (methodName, returnExpression, nearestEnclosingType, nestingLevelInsideMethod);
		}
	}
	
	public static class RTBlock extends RTExpression {
		public RTBlock(final BlockExpression expr, final RTType nearestEnclosingType) {
			super();
			rTBlockBody_ = bodyPartsToRTExpressionList(expr.bodyParts(), nearestEnclosingType);
			ctorCommon(expr);
		}
		public RTBlock(final BlockExpression expr) {
			super();
			assert false;
			ctorCommon(expr);
		}
		private void ctorCommon(final BlockExpression expr) {
			objectDeclarations_ = new LinkedHashMap<String, RTType>();
			
			// Copy from the compiler data, the local variable names
			// declared in the Block scope
			for (final ObjectDeclaration objectDecl : expr.scope().objectDeclarations()) {
				final RTType objectType = null;	 // sigh...
				objectDeclarations_.put(objectDecl.name(), objectType);
			}
			
			setResultIsConsumed(expr.resultIsConsumed());
			rTBlockBody_.setResultIsConsumed(expr.resultIsConsumed());
			
			popScope_ = new RTPopDynamicScope();
			rTBlockBody_.addExpression(popScope_);
			
			if (resultIsConsumed()) {
				evaluationResult_ = new RTPushEvaluationResult();
				rTBlockBody_.addExpression(evaluationResult_);
			} else {
				evaluationResult_ = null;
			}
			
			last_ = new RTNullExpression();
			rTBlockBody_.addExpression(last_);
			
			lineNumber_ = expr.lineNumber();
		}
		@Override public RTCode run() {
			// Get the first node from the body, run it,
			// and return the nextCode() field so the main loop
			// can handle the rest
			
			// We don't block-batch initialisations any more, but execute
			// them as statements that have been inlined at the point
			// of the corresponding declarations
			// final RTCode nextCode = initializations_.size() > 0? initializations_.get(0): rTBlockBody_;
			final RTCode nextCode = rTBlockBody_;
			
			final RTDynamicScope dynamicScope = new RTDynamicScope("block @ line " + Integer.toString(lineNumber_),
					RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(dynamicScope);
			
			// Declare local variables
			for (Map.Entry<String, RTType> iter : objectDeclarations_.entrySet()) {
				final String objectName = iter.getKey();
				final RTType type = iter.getValue();
				dynamicScope.addObjectDeclaration(objectName, type);
			}
			
			// And run the block
			return nextCode;
		}
		@Override public void setNextCode(final RTCode code) {
			rTBlockBody_.setNextCode(code);
			super.setNextCode(code);
		}
		
		private RTExpressionList rTBlockBody_;
		private Map<String, RTType> objectDeclarations_;
		private RTPopDynamicScope popScope_;
		private RTNullExpression last_;
		private int lineNumber_;
		
		private RTPushEvaluationResult evaluationResult_;
	}
	
	public static class RTPromoteToDoubleExpr extends RTExpression {
		public RTPromoteToDoubleExpr(final PromoteToDoubleExpr expr, final RTType nearestEnclosingType) {
			super();
			lineNumber_ = expr.lineNumber();
			expr_ = RTExpression.makeExpressionFrom(expr.promotee(), nearestEnclosingType);
			part2_ = new RTPromoteToDoubleExprPart2(lineNumber_);
			expr_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		
		@Override public RTCode run() {
			return expr_;
		}
		
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		@Override public void setResultIsConsumed(final boolean tf) {
			expr_.setResultIsConsumed(tf);
		}
		
		private final RTExpression expr_;
		private final RTPromoteToDoubleExprPart2 part2_;
		private final int lineNumber_;
	}
	public static class RTPromoteToDoubleExprPart2 extends RTExpression {
		public RTPromoteToDoubleExprPart2(final int lineNumber) {
			super();
			lineNumber_ = lineNumber;
		}
		
		@Override public RTCode run() {
			RTObject object = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (object instanceof RTIntegerObject) {
				final long rawInt = ((RTIntegerObject)object).intValue();
				object = new RTDoubleObject((double)rawInt);
			} else {
				assert false;
			}
			RunTimeEnvironment.runTimeEnvironment_.pushStack(object);
			setLastExpressionResult(object, lineNumber_);
			
			object.decrementReferenceCount();		// either way
			
			return nextCode_;
		}
		
		private final int lineNumber_;
	}
	public static class RTIndexExpression extends RTExpression {
		public RTIndexExpression(final IndexExpression indexExpression) {
			super();
			roleName_ = indexExpression.roleName();
		}
		@Override public RTCode run() {
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject currentContext = RTExpression.getObjectUpToMethodScopeFrom("current$context", currentScope);
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			final RTObject self = RTExpression.getObjectUpToMethodScopeFrom("this", currentScope);
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			final RTIntegerObject theIndex = contextInfo.indexOfRolePlayer(roleName_, self);
			if (null == theIndex) {
				return new RTHalt();
			} else {
				RunTimeEnvironment.runTimeEnvironment_.pushStack(theIndex);
				return nextCode_;
			}
		}
		
		final private String roleName_;
	}
	public static class RTLastIndexExpression extends RTExpression {
		public RTLastIndexExpression(final LastIndexExpression expr) {
			super();
			roleName_ = expr.roleName();
		}
		@Override public RTCode run() {
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject currentContext = RTExpression.getObjectUpToMethodScopeFrom("current$context", currentScope);
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			final RTIntegerObject theIndex = contextInfo.indexOfLastRolePlayer(roleName_);
			
			if (null == theIndex) {
				return new RTHalt();
			} else {
				RunTimeEnvironment.runTimeEnvironment_.pushStack(theIndex);
				return nextCode_;
			}
		}
		
		final private String roleName_;
	}
	
	public static class RTPushEvaluationResult extends RTExpression {
		public RTPushEvaluationResult() {
			super();
		}
		@Override public RTCode run() {
			RunTimeEnvironment.runTimeEnvironment_.pushStack(lastExpressionResult_);
			return nextCode_;
		}
	}
	
	public boolean resultIsConsumed() {
		return resultIsConsumed_;
	}
	public void setResultIsConsumed(boolean tf) {
		resultIsConsumed_ = tf;
	}
	
	// These are ridiculous for expressions
	public void addRoleDeclaration(final String name, final RTRole role) {
		assert false;
	}
	public void setRoleBinding(final String name, final RTObject value) {
		assert false;
	}
	public void setStagePropBinding(final String name, final RTObject value) {
		assert false;
	}
	
	
	protected static void setLastExpressionResult(final RTObject value, final int lineNumber) {
		if (null == value) {
			ErrorLogger.error(ErrorType.Internal, "INTERNAL ERROR: Internal expression evaluated to a NULL Java object, line ", Integer.toString(lineNumber), ".", "");
		} else {
			final RTObject previousLastResult = lastExpressionResult_;
			value.incrementReferenceCount();
			if (null != previousLastResult) {
				previousLastResult.decrementReferenceCount();
			}
			lastExpressionResult_ = value;
		}
	}
	public static final RTObject lastExpressionResult() {
		return lastExpressionResult_;
	}
	public static void reboot() {
		if (null != lastExpressionResult_) {
			// Make sure it lets go of its Role-players
			// if it is a Context.  Mieux vaut tard que jamais...
			lastExpressionResult_.decrementReferenceCount();
			lastExpressionResult_ = null;
		}
	}
	
	protected final static Stack<RTSwitch> switchStack_ = new Stack<RTSwitch>();
	private boolean resultIsConsumed_;
	protected static RTObject lastExpressionResult_ = null;
}
