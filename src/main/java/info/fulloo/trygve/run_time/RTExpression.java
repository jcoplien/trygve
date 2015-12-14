package info.fulloo.trygve.run_time;

/*
 * Trygve IDE
 *   Copyright (c)2015 James O. Coplien
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
import info.fulloo.trygve.run_time.RTClass.RTIntegerClass;
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
			retval = new RTMessage(expr.name(), (MessageExpression)expr, nearestEnclosingType, scope,
					((MessageExpression)expr).isStatic());
		} else if (expr instanceof DupMessageExpression) {
			retval = new RTDupMessage(expr.name(), (DupMessageExpression)expr, nearestEnclosingType);
		} else if (expr instanceof IdentifierExpression) {
			retval = RTIdentifier.makeIdentifier(expr.name(), (IdentifierExpression)expr);
		} else if (expr instanceof RelopExpression) {
			retval = new RTRelop((RelopExpression)expr, nearestEnclosingType);
		} else if (expr instanceof BooleanExpression) {
			retval = new RTBoolean((BooleanExpression)expr, nearestEnclosingType);
		} else if (expr instanceof BinopExpression) {
			retval = new RTBinop((BinopExpression)expr, nearestEnclosingType);
		} else if (expr instanceof UnaryAbelianopExpression) {
			retval = new RTUnaryAbelianop((UnaryAbelianopExpression)expr, nearestEnclosingType);
		} else if (expr instanceof UnaryopExpressionWithSideEffect) {
			retval = new RTUnaryopWithSideEffect((UnaryopExpressionWithSideEffect)expr, nearestEnclosingType);
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
		} else if (expr instanceof ReturnExpression) {
			retval = new RTReturn("return", (ReturnExpression)expr, nearestEnclosingType);
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
		} else {
			retval = new RTNullExpression();
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
				setLastExpressionResult(nullObject);
			}
			return super.nextCode();
		}
	}
	public static class RTQualifiedIdentifier extends RTExpression {
		public RTQualifiedIdentifier(final String name, final Expression expr, final RTType nearestEnclosingType) {
			super();
			part2_ = new RTQualifiedIdentifierPart2(name);
			final QualifiedIdentifierExpression qie = (QualifiedIdentifierExpression) expr;
			qualifier_ = RTExpression.makeExpressionFrom(qie.qualifier(), nearestEnclosingType);
			qualifier_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// Evaluate the expression, leaving the result on the stack
			return qualifier_.run();
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
			public RTQualifiedIdentifierPart2(final String name) {
				super();
				idName_ = name;
				dynamicScope_ = null;
			}
			@Override public RTCode run() {
				// Pop the expression off the stack
				final RTObject qualifier = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				if (qualifier == null) {
					// Run-time error
					return null;
				} else {
					dynamicScope_ = new RTDynamicScope(idName_, qualifier, null);	// cheesy constructor -- increments ref count, don't worry
					final RTObject value = qualifier.getObject(idName_);
					RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
					qualifier.decrementReferenceCount();
					setLastExpressionResult(value);
					return nextCode_;
				}
			}
			
			private RTDynamicScope dynamicScope_;	// this should not be in a program element. FIXME.
			private final String idName_;
		}

		private final RTExpression qualifier_;
		private final RTQualifiedIdentifierPart2 part2_;
	}
	public static class RTQualifiedIdentifierUnaryOp extends RTExpression {
		public RTQualifiedIdentifierUnaryOp(final String name, final Expression expr, final RTType nearestEnclosingType) {
			super();
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
			RTCode haltInstruction = qualifier_.run();
			assert null == haltInstruction;
			
			// Pop the qualifier expression off the stack
			final RTObject qualifier = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			dynamicScope_ = new RTDynamicScope(this.name(), qualifier, null);	// cheesy constructor -- increments ref count, don't worry
			final RTObject value = qualifier.performUnaryOpOnObjectNamed(idName_, operator_, preOrPost_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value);
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
		private final RTExpression qualifier_;
		private       RTDynamicScope dynamicScope_;
		private final String operator_;
		private final PreOrPost preOrPost_;
	}
	public static class RTClassMemberIdentifier extends RTExpression {
		public RTClassMemberIdentifier(final String name, final Expression expr) {
			super();
			idName_ = name;
			
			assert expr instanceof QualifiedClassMemberExpression;
			final QualifiedClassMemberExpression qcme = (QualifiedClassMemberExpression) expr;
			theClassItself_ = qcme.qualifier();
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// FIXME. Shouldn't this go in the constructor?
			final RTType wannabeClass = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(theClassItself_.enclosedScope());
			assert wannabeClass instanceof RTClass;
			rTClass_ = (RTClass)wannabeClass; 
			
			final RTObject value = rTClass_.getStaticObject(idName_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value);
			return nextCode_;
		}
		public RTDynamicScope dynamicScope() {
			return null;
		}
		public String name() {
			return idName_;
		}

		private final String idName_;
		private final ClassType theClassItself_;
		private RTClass rTClass_;
	}
	public static class RTClassMemberIdentifierUnaryOp extends RTExpression {
		public RTClassMemberIdentifierUnaryOp(final String name, final Expression expr) {
			super();
			idName_ = name;
			
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
			setLastExpressionResult(value);
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
		private final PreOrPost preOrPost_;
	}
	
	public static class RTMessage extends RTExpression {
		public String msn() {		// for debugging only
			return methodSelectorName_;
		}
		public RTMessage(final String name, final MessageExpression messageExpr, final RTType nearestEnclosingType, final StaticScope scope, final boolean isStatic) {
			super();
			
			methodSelectorName_ = messageExpr.name();

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
			expressionsCountInArguments_ = new int [actualParameters_.count()];
			lineNumber_ = messageExpr.lineNumber();
			final MethodDeclaration methodDecl = this.staticLookupMethodDecl(messageExpr);
			
			if (null != methodDecl) {
				// "if" test is error stumble insurance
				argPush_ = this.buildArgumentPushList(methodDecl.formalParameterList(), messageExpr.name(), messageExpr.lineNumber());
			}
			final Type returnType = messageExpr.returnType();
			final boolean resultNeedsToBePopped = (!messageExpr.resultIsConsumed()) &&
					(returnType.name().equals("void") == false);
			postReturnProcessing_ = new RTPostReturnProcessing(null, name);
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
			
			final int thisIndex = actualParameters_.count() - 1;
			Expression fakeSelfExpression = null;
			final Message fakeMessage = new Message(name, actualParameters, lineNumber_, enclosingMegaType);
			fakeMessage.setReturnType(returnType);
			MessageExpression fakeMessageExpression = null;
			
			if (false == isStatic) {
				fakeSelfExpression = actualParameters.parameterAtPosition(thisIndex);
				fakeMessageExpression = new MessageExpression(fakeSelfExpression, fakeMessage,
					returnType, lineNumber_, isStatic_);
			} else {
				fakeSelfExpression = new IdentifierExpression(enclosingMegaType.name(), enclosingMegaType, enclosingMegaType.enclosedScope());
				fakeMessageExpression = new MessageExpression(fakeSelfExpression, fakeMessage,
						returnType, lineNumber_, isStatic_);
			}
			
			final MethodDeclaration methodDecl = this.staticLookupMethodDecl(fakeMessageExpression);
			FormalParameterList fakeParameterList = null;
			if (null != methodDecl) {
				fakeParameterList = methodDecl.formalParameterList();
			} else {
				// error stumbling catch
				fakeParameterList = new FormalParameterList();
			}
			
			argPush_ = this.buildArgumentPushList(fakeParameterList, name, lineNumber_);
			postReturnProcessing_ = new RTPostReturnProcessing(null, name);
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
				typeOfReceiver = null != objectExpression? objectExpression.type(): StaticScope.globalScope().lookupTypeDeclaration("void");;
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
				// add others here (dup?). If the number of methods we add gets
				// too large we should explore another architectural alternative.
				retval = ((ArrayType)typeOfReceiver).sizeMethodDeclaration(StaticScope.globalScope());
			} else {
				if (null == receiverScope) {
					assert null != receiverScope;
				}
				retval = receiverScope.lookupMethodDeclaration(methodSelectorName, parameterList, false);
				if (null == retval) {
					retval = receiverScope.lookupMethodDeclarationWithConversion(methodSelectorName, parameterList, false);
				}
				if (null == retval) {
					// Check out the base class
					final Declaration declOfCurrent = typeOfReceiver.enclosedScope().associatedDeclaration();
					if (declOfCurrent instanceof ClassDeclaration) {
						ClassDeclaration classOfCurrent = (ClassDeclaration)declOfCurrent;
						
						while (null != (classOfCurrent = classOfCurrent.baseClassDeclaration()) ) {
							final StaticScope huntingScope = classOfCurrent.enclosedScope();
							retval = huntingScope.lookupMethodDeclaration(methodSelectorName, parameterList, false);
							if (null == retval) {
								retval = huntingScope.lookupMethodDeclarationWithConversion(methodSelectorName, parameterList, false);
							}
							if (null != retval) {
								break;
							}
						}
					}
				}
			}
			return retval;
		}
		
		private RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			RTMethod methodDecl = null;
			if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
				// Then self is an object playing the Role (with rTType an instance of RTClass)
				// Get the current RTContext (must be in self of current activation record)
				final RTObject tempContextPointer = currentScope.getObject("this");
				assert null != tempContextPointer;
				if (tempContextPointer instanceof RTContextObject) {
					final RTContextObject contextPointer = (RTContextObject)tempContextPointer;
					final RTType rawContextType = contextPointer.rTType();
					assert rawContextType instanceof RTContext;
					final RTContext contextType = (RTContext)rawContextType;
					
					final RTRole theRole = contextType.getRole(typeOfThisParameterToMethod.name());
					if (null == theRole) {
						final RTStageProp theStageProp = contextType.getStageProp(typeOfThisParameterToMethod.name());
						assert null != theStageProp;
						methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
					} else {
						assert null != theRole;
						methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
					}
					assert null != methodDecl;
				} else if (tempContextPointer instanceof RTObjectCommon) {
					// The "this" parameter is a pointer to the Role Player, typed
					// in terms of the Role Player's type.

					// That implies that the call is invoking an instance method
					// and not a role method. So:
					
					final RTObject rolePlayer = (RTObject)tempContextPointer;
					final RTType rolePlayerType = rolePlayer.rTType();
					assert null != rolePlayerType;
					methodDecl = rolePlayerType.lookupMethod(methodSelectorName_, actualParameters_);
					if (null == methodDecl) {
						// ... then it is another Role method invocation on the
						// RolePlayer.  First, get the RTRole:
						final RTObject objectContextPointer = currentScope.getObject("current$context");
						assert objectContextPointer instanceof RTContextObject;
						final RTContextObject contextPointer = (RTContextObject)objectContextPointer;
						
						final RTType rawContextType = contextPointer.rTType();
						assert rawContextType instanceof RTContext;
						final RTContext contextType = (RTContext)rawContextType;
						final RTRole theRole = contextType.getRole(typeOfThisParameterToMethod.name());
						if (null != theRole) {
							// Look up the method in the Role scope.
							methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
						} else {
							// Look up the method in the StageProp scope.
							final RTStageProp theStageProp = contextType.getStageProp(typeOfThisParameterToMethod.name());
							assert null != theStageProp;
							methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
						}
						assert null != methodDecl;
					}
				} else {
					assert false;
				}
			} else {
				// Calculate the address of the method. Generalize for classes and Contexts.
				final RTType rTTypeOfSelf = null != self? self.rTType():
					InterpretiveCodeGenerator.scopeToRTTypeDeclaration(typeOfThisParameterToMethod.enclosedScope());
				if (self instanceof RTNullObject) {
					ErrorLogger.error(ErrorType.Fatal, lineNumber(), "FATAL: TERMINATED: Attempting to invoke method ",
							methodSelectorName_, " on a null object", "");

					// Halt the machine
					return null;
				} else if (null == rTTypeOfSelf) {
					ErrorLogger.error(ErrorType.Internal, lineNumber(), "INTERNAL: Attempting to invoke method `",
							methodSelectorName_, "' on a null Java object", "");
					return null;
					//assert null != rTTypeOfSelf;
				}
				
				final ClassType classType = typeOfThisParameterToMethod instanceof ClassType? (ClassType)typeOfThisParameterToMethod: null;
				TemplateInstantiationInfo templateInstantiationInfo = null == classType? null: classType.templateInstantiationInfo();
				
				if (null == templateInstantiationInfo) {
					final RTClass rTclassType = nearestEnclosingType_ instanceof RTClass? (RTClass)nearestEnclosingType_: null;
					templateInstantiationInfo = null == rTclassType? null: rTclassType.templateInstantiationInfo();
				}
				
				ActualOrFormalParameterList actualParameters = actualParameters_;
				if (null != templateInstantiationInfo) {
					actualParameters = actualParameters.mapTemplateParameters(templateInstantiationInfo);
				}
				
				String methodSelectorName = methodSelectorName_;
				if (null != classType && methodSelectorName.equals(classType.name())) {
					// assert isaconstructor
					if (methodSelectorName.matches("[a-zA-Z]<.*>") || methodSelectorName.matches("[A-Z][a-zA-Z0-9_]*<.*>")) {
						// Is a template constructor. Just get the base name
						final int indexOfLessThan = methodSelectorName.indexOf('<');
						methodSelectorName = methodSelectorName.substring(0, indexOfLessThan);
					}
				}
				
				// Give a direct match the first chance
				methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureNamed(methodSelectorName, actualParameters, "this");
				if (null == methodDecl) {
					methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionNamed(methodSelectorName, actualParameters, "this");
					if (null == methodDecl) {
						if (typeOfThisParameterToMethod instanceof RoleType) {
							methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
							if (null == methodDecl) {
								methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
							}
						}
						if (null == methodDecl) {
							assert null != methodDecl;
						}
					}
					assert null != methodDecl;
				}
			}
			
			assert null != methodDecl;
			return methodDecl;
		}
		
		private RTObject pushArgumentLoop(final RTCode start, final int expressionCounterForThisExtraction,
				final int indexForThisExtraction) {
			RTCode pc = start;
			final int startingStackIndex = RunTimeEnvironment.runTimeEnvironment_.stackIndex();
			RTObject self = null;
			while (null != pc) {
				// This evaluation leaves a result on the stack -
				// a result which will be a parameter to the method
				
				// Woops - this gets short-circuited if it's a method invocation?
				// It goes off to evaluate the method (as a "this" argument) and
				// then, just below, expects to pull "this" (e.g. PrintStream)
				// off the stack - but what's sitting on the stack is the return
				// address for the method (RTPostReturnProcessing) and "nextInstruction"
				// points to a method
				final RTCode nextInstruction = pc.run();
				
				final RTCode oldPc = pc;
				pc = nextInstruction;
				if (null != pc) {
					pc.incrementReferenceCount();
				}
				oldPc.decrementReferenceCount();
			}
			
			if (false == isStatic() && RunTimeEnvironment.runTimeEnvironment_.stackIndex() <= startingStackIndex + indexForThisExtraction) {
				assert RunTimeEnvironment.runTimeEnvironment_.stackIndex() > startingStackIndex + indexForThisExtraction;
			}
			
			self = isStatic()? null:
				(RTObject)RunTimeEnvironment.runTimeEnvironment_.stackValueAtIndex(startingStackIndex + indexForThisExtraction);

			assert null != self || isStatic();
			return self;
		}
		
		private void pushContextPointerIfNecessary(final Type typeOfThisParameterToMethod, final int indexForThisExtraction) {
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
				// If we're a non-Role method *and* we're calling a Role method
				// (we got to this line in this code), we need
				// to push an extra argument - current$context. If we're calling
				// a Role method it can be only from within a Context.
				
				if (null == currentScope.getObject("current$context")) {
					// No declaration of current$context in the current scope.
					// We're not a Role method. We are just a Context method.

					// If we're not a Role method the right value to pass is
					// the current value of "this" in the calling activation
					// record. Set up for an extra push before everything else
					// is pushed
					final RTObject tempContextPointer = currentScope.getObject("this");
					assert null != tempContextPointer;
					assert tempContextPointer instanceof RTContextObject;
					
					final RTContextObject contextPointer = (RTContextObject)tempContextPointer;
					RunTimeEnvironment.runTimeEnvironment_.pushStack(contextPointer);
				}
			}
		}
		
		private void populateActivationRecord(final RTMethod methodDecl, final RTDynamicScope activationRecord) {
			final FormalParameterList formalParameters = methodDecl.formalParameters();
			for (int i = formalParameters.count() - 1; 0 <= i; --i) {
				final ObjectDeclaration ithParameter = formalParameters.parameterAtPosition(i);
				final String ithParameterName = ithParameter.name();
				final RTType rTIthParameterType = null; // InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(ithParameter.type());
				activationRecord.addObjectDeclaration(ithParameterName, rTIthParameterType);
				final RTObject anArgument = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				assert null != anArgument;
				activationRecord.setObject(ithParameterName, anArgument);
				anArgument.decrementReferenceCount();	// not on the stack any more
			}
		}

		@Override public RTCode run() {
			RTCode start = argPush_;
			RTObject self = null;

			// Push the return address onto the stack
			RunTimeEnvironment.runTimeEnvironment_.pushStack(postReturnProcessing_);
			RunTimeEnvironment.runTimeEnvironment_.setFramePointer();
			
			// We're about to push the arguments onto the stack. If the
			// target method is a Role method it will have current$context
			// as one of its formal parameters.
			
			// The positional index of "this" should be 0 in ordinary case,
			// 1 if there is current$context pushed on the stack
			// (two stack protocols...). The indexForThisExtraction
			// is a positional (slot) index; the expressionCounterForThisExtraction
			// is how many expressions we need to wade through to get
			// to the object pointer. See below.
			int indexForThisExtraction = 0, expressionCounterForThisExtraction = 0;
			if (null != start && start instanceof RTIdentifier) {
				final RTIdentifier startAsRTIdent = (RTIdentifier) start;
				if (startAsRTIdent.name().equals("current$context")) {
					indexForThisExtraction = 1;	// now unused?
					expressionCounterForThisExtraction = expressionsCountInArguments_[indexForThisExtraction - 1];
				}
			}
			assert indexForThisExtraction < actualParameters_.count();
			
			final ActualArgumentList argList = actualParameters_;
			Expression thisDeclaration = null;
			Type typeOfThisParameterToMethod = null;
			if (this.isStatic()) {
				// There is no "this" on the stack for static method calls
				final Expression classNameExpression = this.messageExpr_.objectExpression();
				assert classNameExpression instanceof IdentifierExpression;
				final String className = classNameExpression.name();
				// Works only for global scope types. FIXME.
				typeOfThisParameterToMethod = StaticScope.globalScope().lookupTypeDeclaration(className);
				assert null != typeOfThisParameterToMethod;
			} else {
				final Object rawDeclaration = argList.argumentAtPosition(indexForThisExtraction); // could be a Role identifier...
				assert null != rawDeclaration && rawDeclaration instanceof Expression;
				thisDeclaration = (Expression)rawDeclaration;
				typeOfThisParameterToMethod = thisDeclaration.type();	// tentative...
			}

			this.pushContextPointerIfNecessary(typeOfThisParameterToMethod, indexForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			self = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			// Get the method declaration by looking it up in the receiver's scope
			// Null return on error (e.g., attempting to invoke a method on a null object)
						
			final RTMethod methodDecl = this.getMethodDecl(typeOfThisParameterToMethod, indexForThisExtraction, self);
			
			if (null != methodDecl) {
				// Now that the actual parameters are on the stack, let's
				// open up an activation record, pop off the arguments
				// and move them into the activation record as formal
				// parameters
				
				// Push the activation record onto the activation record stack
				// (I think this is the only place in the code where activation
				// records are pushed)
				final RTDynamicScope activationRecord = new RTDynamicScope(methodDecl.name(), RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
				RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
				this.populateActivationRecord(methodDecl, activationRecord);
			}
		
			// Turn it over to the executive to call the method.
			return methodDecl;
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
			for (int i = 0; i < actualParameters_.count(); i++) {
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
		
		private static class RTPostReturnProcessing extends RTExpression {
			public RTPostReturnProcessing(final RTCode nextCode, final String name) {
				super();
				super.setNextCode(nextCode);
				name_ = name;
			}
			@Override public RTCode run() {
			if (false == resultIsConsumed()) {
					RunTimeEnvironment.runTimeEnvironment_.popStack();
				}
				return super.nextCode();
			}
			
			@SuppressWarnings("unused")
			private final String name_;		// for debugging only
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
			
			if (null != parameterNames) {
				final Iterator<String> typeNameIter = parameterTypeNames.iterator();
				for (final String parameterName : parameterNames) {
					assert typeNameIter.hasNext();
					final String parameterTypeName = typeNameIter.next();
					final Type stringType = StaticScope.globalScope().lookupTypeDeclaration(parameterTypeName);

					final IdentifierExpression theArgument = new IdentifierExpression(parameterName, stringType, enclosedMethodScope);
					argList.addActualArgument(theArgument);
				}
			}
			
			if (false == isStatic) {
				final IdentifierExpression self = new IdentifierExpression("this", outType, enclosedMethodScope);
				argList.addActualArgument(self);
			}
			
			return argList;
		}
		public boolean isStatic() {
			return isStatic_;
		}

		private TemplateInstantiationInfo templateInstantiationInfo_;
		private String methodSelectorName_;
		private MessageExpression messageExpr_;
		protected RTCode argPush_;
		private ActualArgumentList actualParameters_;
		private final int lineNumber_;
		private final int [] expressionsCountInArguments_;
		private final RTPostReturnProcessing postReturnProcessing_;
		private RTType nearestEnclosingType_;
		private final boolean isStatic_;
	}
	
	public static class RTDupMessage extends RTExpression {
		public RTDupMessage(final String name, final DupMessageExpression expr, final RTType enclosingMegaType) {
			super();
			final Expression objectToClone = expr.objectToClone();
			final List<BodyPart> intermediate = objectToClone.bodyParts();
			objectToClone_ = bodyPartsToRTExpressionList(intermediate, enclosingMegaType);
			part2_ = new RTDupMessagePart2(name);
			objectToClone_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
			name_ = name;
		}
		
		@Override public RTCode run() {
			final RTCode retval = objectToClone_.run();
			return retval;
		}
		
		public String name() {
			return name_;
		}
		
		public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		private final String name_;
		private final RTExpressionList objectToClone_;
		private final RTDupMessagePart2 part2_;
	}
	public static class RTDupMessagePart2 extends RTExpression {
		public RTDupMessagePart2(final String name) {
			super();
			name_ = name;
		}
		
		@Override public RTCode run() {
			final RTStackable rawOriginal = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawOriginal instanceof RTObject;
			final RTObject original = (RTObject)rawOriginal;
			final RTObject clone = original.dup();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(clone);
			original.decrementReferenceCount();
			setLastExpressionResult(clone);
			return nextCode_;
		}
		
		public String name() {
			return name_;
		}
		
		final private String name_;
	};
	
	public static class RTIdentifier extends RTExpression {
		static RTIdentifier makeIdentifier(final String name, final IdentifierExpression expression) {
			RTIdentifier retval = null;
			assert null != expression;
			final StaticScope declaringScope = expression.scopeWhereDeclared();
			if (null != declaringScope) {		// mainly in user program error situations
				final Declaration associatedDeclaration = declaringScope.associatedDeclaration();
				if (associatedDeclaration instanceof ContextDeclaration) {
					final RoleDeclaration isItARole = declaringScope.lookupRoleDeclaration(name);
					if (null != isItARole) {
						retval = new RTRoleIdentifier(name, expression);
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
				
			declaringScope_ = expression.scopeWhereDeclared();
			
			if (null != declaringScope_) {
				final Declaration associatedDeclaration = declaringScope_.associatedDeclaration();
				isLocal_ = (null == associatedDeclaration) || !(associatedDeclaration instanceof TypeDeclaration);
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
				value = activationRecord.getObject(idName_);
				if (null == value) {
					if (idName_.equals("current$context")) {
						// This is kind of an ugly kludge. If we're being asked
						// for the current context, and if it's not in the current
						// method activation record, then this must not be a
						// Role method from which we're making the invocation.
						// But it must be a Context method - only Context methods
						// can call Role methods.
						//
						// Probably worth adding some assertions around this, if
						// there's enough data in the environment
						assert declaringScope_.parentScope().associatedDeclaration() instanceof ContextDeclaration;
						final RTObject self = scope.getObject("this");
						assert self instanceof RTContextObject;
						value = self;
					} else {
						final RTDynamicScope parentScope = activationRecord.nearestEnclosingScopeDeclaring(idName_);
						value = null == parentScope? null: parentScope.getObject(idName_);
					}
					assert null != value;
				}
			} else {
				// WARNING. This is a bit presumptuous and needs work. TODO.
				final RTObject self = scope.getObject("this");
				value = self.getObject(idName_);
				if ((null == value) && (self instanceof RTContextObject)) {
					final RTContextObject rTSelf = (RTContextObject)self;
					value = rTSelf.getRoleBinding(idName_);
					assert null != value;
				} else {
					assert false;
				}
			}
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value);
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

		protected final String idName_;
		protected StaticScope declaringScope_;
		protected final boolean isLocal_;
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
			final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			if (isLocal_) {
				// It's just in the local activation record
				final RTDynamicScope activationRecord = scope;
				value = activationRecord.getObject(idName_);
				assert null != value;
			} else {
				// WARNING. This is a bit presumptuous and needs work. TODO.
				final RTObject self = scope.getObject("this");
				value = self.getObject(idName_);
				if ((null == value) && (self instanceof RTContextObject)) {
					final RTContextObject rTSelf = (RTContextObject)self;
					value = rTSelf.getRoleBinding(idName_);
					assert null != value;
				} else {
					// We need to get the role binding
					final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
					final RTObject tempContextPointer = currentScope.getObject("current$context");
					assert null != tempContextPointer;
					assert tempContextPointer instanceof RTContextObject;
					
					final RTContextObject contextPointer = (RTContextObject)tempContextPointer;
					value = contextPointer.getRoleBinding(idName_);
					assert null != value;
				}
			}
			RunTimeEnvironment.runTimeEnvironment_.pushStack(value);
			setLastExpressionResult(value);
			return nextCode_;
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
			return lhs_.run();
		}
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTRelopPart2 extends RTExpression {
			public RTRelopPart2(final RelopExpression expr) {
				super();
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
				} else {
					assert false;
				}
				
				final RTBooleanObject newBoolean = new RTBooleanObject(value);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(newBoolean);
				setLastExpressionResult(newBoolean);
				
				rhs.decrementReferenceCount();
				lhs.decrementReferenceCount();
				newBoolean.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
		}
		
		private final RTExpression lhs_, rhs_;
		private final RTRelopPart2 part2_;
	}
	
	public static class RTBoolean extends RTExpression {
		public RTBoolean(final BooleanExpression expr, final RTType nearestEnclosingType) {
			super();
			lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			operator_ = expr.operator();
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			lhs_.run();
			rhs_.run();
			
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
			setLastExpressionResult(newBoolean);
			
			rhs.decrementReferenceCount();
			lhs.decrementReferenceCount();
			newBoolean.decrementReferenceCount();
			
			return nextCode_;
		}

		private final RTExpression lhs_, rhs_;
		private final String operator_;
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
				setLastExpressionResult(value);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
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
			return lhs_.run();
		}
		
		@Override public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		public static class RTUnaryopWithSideEffectPart2 extends RTExpression {
			public RTUnaryopWithSideEffectPart2(final UnaryopExpressionWithSideEffect expr) {
				super();
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
				setLastExpressionResult(value);
				
				lhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
			private final UnaryopExpressionWithSideEffect.PreOrPost preOrPost_;
		}
		
		private final RTExpression lhs_;
		private final RTUnaryopWithSideEffectPart2 part2_;
	}
	public static class RTUnaryAbelianop extends RTExpression {
		public RTUnaryAbelianop(final UnaryAbelianopExpression expr, final RTType nearestEnclosingType) {
			super();
			rhs_ = RTExpression.makeExpressionFrom(expr.rhs(), nearestEnclosingType);
			part2_ = new RTUnaryAbelianopPart2(expr);
			rhs_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			return rhs_.run();
		}
		
		@Override public void setNextCode(RTCode next) {
			part2_.setNextCode(next);
		}
		
		public static class RTUnaryAbelianopPart2 extends RTExpression {
			public RTUnaryAbelianopPart2(UnaryAbelianopExpression expr) {
				super();
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
				setLastExpressionResult(value);
				
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
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
			return originalRHS_.run();
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
			part2_ = new RTAssignmentPart2(expr, rhs_, nearestEnclosedType);
			rhs_.setNextCode(part2_);
			rhs_.setResultIsConsumed(true);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// I found I needed to add this loop to a new ArrayType [expr]
			// expression. Invoking rhs_.run() alone just sets up the object
			// and does null initialization - it does not call the constructor.
			//
			// So I broke out the real assignment processing into RTAssignmentPart2.
			return rhs_.run();
		}
		public void setNextCode(final RTCode pc) {
			part2_.setNextCode(pc);
		}
	
		public static class RTAssignmentPart2 extends RTExpression {
			public RTAssignmentPart2(final AssignmentExpression expr, final RTExpression rhs, final RTType nearestEnclosingType) {
				super();
				lhs_ = RTExpression.makeExpressionFrom(expr.lhs(), nearestEnclosingType);
				if (lhs_ instanceof RTQualifiedIdentifier) {
					part2b_ = new RTAssignmentPart2B(lhs_);
					part2b_.setResultIsConsumed(expr.resultIsConsumed());
					lhs_.setNextCode(part2b_);
				}
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
					final RoleDeclaration lhsDecl = declaringScope.lookupRoleDeclaration(((RTRoleIdentifier)lhs_).name());
					if (lhsDecl.isArray()) {
						assert rhs instanceof RTArrayObject;
						final RTArrayObject aRhs = (RTArrayObject)rhs;
						assert lhs_ instanceof RTRoleIdentifier;
						this.processRoleArrayBinding2(aRhs);
					} else {
						this.processRoleBinding(rhs);
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
					this.processRoleArrayElementBinding(lhs, rhs);
					if (this.resultIsConsumed()) {
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
					final RTCode restOfLhs = lhs_.run();
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
					ErrorLogger.error(ErrorType.Internal, 0, "Invalid left-hand side: unary increment or decrement operation on indexed array element", "", "", "");
				} else {
					// Odd. RTRole isn't processed here but appears to work...
					assert false;	// to be implemented (RTRole, RTQualifiedClassMember, etc.)
				}
				
				dynamicScope = dynamicScope.nearestEnclosingScopeDeclaring(name);
				assert null != dynamicScope;
				
				// Reference count increment is done within the dynamic scope object
				// (Note: setNamedSlotToValue decrements the reference count of
				// the previous occupant).
				dynamicScope.setNamedSlotToValue(name, rhs);
				
				if (resultIsConsumed() == true) {
					RunTimeEnvironment.runTimeEnvironment_.pushStack(rhs);
				}
				setLastExpressionResult(rhs);
				
				rhs.decrementReferenceCount();	// because we initially popped it from the stack
												// (the above pushStack increments it)
				
				return staticNextCode_;
			}
			
			private void processRoleBinding(final RTObject rhs) {
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
				final RTContextObject contextScope = (RTContextObject)scope.getObject("this");
				
				assert contextScope.rTType() instanceof RTContext;
				contextScope.setRoleBinding(lhs.name(), rhs);
			}
			
			private void processRoleArrayBinding2(final RTArrayObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an array object
				// to that role
				
				final RTRoleIdentifier lhs = (RTRoleIdentifier)lhs_;
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)scope.getObject("this");
				
				assert contextScope.rTType() instanceof RTContext;
				
				contextScope.setRoleArrayBindingToArray(lhs, rhs);
			}
			
			private void processRoleArrayBinding(final RTArrayExpression lhs, final RTArrayObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an array object
				// to that role
				
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)scope.getObject("this");
				
				assert contextScope.rTType() instanceof RTContext;
				ErrorLogger.error(ErrorType.Unimplemented, 0, "Unimplemented: assigment of vector to scalar role", "", "", "");
			}
			
			private void processRoleArrayElementBinding(final RTRoleArrayIndexExpression lhs, final RTObject rhs) {
				// Analogous to processRoleBinding(RTObject rhs) but the Role type
				// is declared as an array, and we're handling the binding of an object
				// to one of those elements
				
				final RTDynamicScope scope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				final RTContextObject contextScope = (RTContextObject)scope.getObject("this");
				
				assert contextScope.rTType() instanceof RTContext;
				contextScope.setRoleArrayElementBinding(lhs, rhs);
			}
			
			@Override public void setNextCode(final RTCode code) {
				if (null != part2b_) {
					part2b_.setNextCode(code);
				}
				staticNextCode_ = code;
				super.setNextCode(code);
			}
			
			private static class RTAssignmentPart2B extends RTExpression {
				public RTAssignmentPart2B(final RTExpression lhs) {
					super();
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
					
					setLastExpressionResult(rhs_);
					
					rhs_.decrementReferenceCount();
					
					return nextCode_;
				}
				public void setRhs(final RTObject rhs) {
					rhs_ = rhs;
				}
				
				private final RTExpression lhs_;
				private RTObject rhs_;
			}
			
			private RTExpression lhs_;
			private RTAssignmentPart2B part2b_;
			private RTCode staticNextCode_;
		}
		
		public void setTemplateInstantiationInfo(final TemplateInstantiationInfo templateInstantiationInfo) {
			templateInstantiationInfo_ = templateInstantiationInfo;
		}
		public TemplateInstantiationInfo templateInstantiationInfo() {
			return templateInstantiationInfo_;
		}
		
		private RTExpression rhs_;
		private RTAssignmentPart2 part2_;
		private TemplateInstantiationInfo templateInstantiationInfo_;
	}
	
	public static class RTNew extends RTExpression {
		public RTNew(final NewExpression expr, final RTType nearestEnclosingType) {
			super();
			currentContextVariableName_ = "current$context"; // + counter_;  TODO - FIXME
			thisVariableName_ = "t$his"; //  + counter_;   TODO - FIXME
			// counter_++;
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
				constructor = classScope.lookupMethodDeclarationWithConversion(
						constructorSelectorName,
						actualArguments, false);
			}
			
			if (null == constructor) {
				rTConstructor_ = null;
			} else {
				// compile it
				final IdentifierExpression rawSelf = new IdentifierExpression("this", classType_, classScope);
				final MessageExpression messageExpression = new MessageExpression(rawSelf, message, classType_, expr.lineNumber(),
						false);
				
				// The selectorName() will be the name of the class. The messageExpression
				// carries the constructor arguments
				final RTType classScopesrTType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(classScope);
				rTConstructor_ = new RTMessage(message.selectorName(), messageExpression, classScopesrTType, classScope, messageExpression.isStatic());
				
				// In the past, there was code in RTContext::run that checked to see if
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
			} else if (classType_.name().startsWith("List<")) {
				newlyCreatedObject = new RTListObject(rTType_);	// rTType_ is, e.g. an instance of RTClass
			} else if (classType_.name().startsWith("Map<")) {
				newlyCreatedObject = new RTMapObject(rTType_);	// rTType_ is, e.g. an instance of RTClass
			} else if (classType_.name().equals("Date")) {
				newlyCreatedObject = new RTDateObject(rTType_);
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
				
				if (isAContextConstructor) {
					callingActivationRecord.addObjectDeclaration(currentContextVariableName_, rTType_);
					callingActivationRecord.setObject(currentContextVariableName_, newlyCreatedObject);
				}

				callingActivationRecord.addObjectDeclaration(thisVariableName_, rTType_);
				callingActivationRecord.setObject(thisVariableName_, newlyCreatedObject);
			}
			
			// The ordering here is important. Because of the anomaly in
			// how reference counting works, we want to log this as the
			// last expression before decrementing the reference count
			// by our own "ownership" of the object here in this activation
			// record
			setLastExpressionResult(newlyCreatedObject);
			
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
		
		private Type classType_;
		private RTType rTType_;
		private RTMessage rTConstructor_;
		// private static int counter_ = 0;
		private String currentContextVariableName_, thisVariableName_;
	}
	public static class RTNewArray extends RTExpression {
		public RTNewArray(final NewArrayExpression expr, final RTType nearestEnclosingType) {
			super();
			final Type baseType = expr.baseType();
			rTType_ = new RTArrayType(baseType, (ArrayType)expr.type());
			sizeExpression_ = RTExpression.makeExpressionFrom( expr.sizeExpression(), nearestEnclosingType );
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			final RTCode haltInstruction = sizeExpression_.run();	// will return null
			assert haltInstruction == null;
			final RTStackable tempSizeExpr = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert tempSizeExpr instanceof RTIntegerObject;
			final RTIntegerObject sizeExpr = (RTIntegerObject)tempSizeExpr;
			
			final int size = (int)sizeExpr.intValue();
			
			final RTArrayObject newArrayObjectToPush = new RTArrayObject(size, rTType_);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(newArrayObjectToPush);
			setLastExpressionResult(newArrayObjectToPush);
			sizeExpr.decrementReferenceCount();
			
			return nextCode_;
		}
		
		@Override public void setNextCode(final RTCode nextCode) {
			super.setNextCode(nextCode);
		}
		
		private final RTExpression sizeExpression_;
		private final RTArrayType rTType_;
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
				pc = pc.run();
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
			
			setResultIsConsumed(expr.resultIsConsumed());
		}
		
		@Override public RTCode run() {
			RTObject result = null;
			
			RTCode pc = rTArrayExpression_;
			do {
				pc = pc.run();
			} while( null != pc);
			
			pc = rTIndexExpression_;
			do {
				pc = pc.run();
			} while (pc != null);
			
			final RTObject theIndex = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			final RTStackable rawArrayBase = RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (rawArrayBase instanceof RTArrayObject) {
				final RTArrayObject arrayBase = (RTArrayObject)rawArrayBase;
				result = arrayBase.getObject(theIndex);
			} else if (rawArrayBase instanceof RTNullObject) {
				ErrorLogger.error(ErrorType.Runtime, 0, "Likely access of uninitialized array.", "", "", "");
				return null;
			} else if (true) {
				assert false;
			}
			
			if (null == result) {
				ErrorLogger.error(ErrorType.Runtime, 0, "Likely access of uninitialized array element.", "", "", "");
				return null;
			}
			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
			setLastExpressionResult(result);
			
			theIndex.decrementReferenceCount();
			rawArrayBase.decrementReferenceCount();
			
			return nextCode_;	// probably null
		}
		
		public RTCode assign(final RTObject rhs) {
			final RTCode haltInstruction = rTArrayExpression_.run();
			assert null == haltInstruction;
			
			// Yuk. Firmware loop.
			RTCode pc2 = rTIndexExpression_;
			do {
				pc2 = pc2.run();	// probably also returns null... well... maybe..
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
			return rTArrayExpression_.run();
		}
		
		@Override public void setNextCode(RTCode code) {
			part2_.setNextCode(code);
		}
		
		public static class RTArrayIndexExpressionUnaryOpPart2 extends RTExpression {
			public RTArrayIndexExpressionUnaryOpPart2(final ArrayIndexExpressionUnaryOp expr) {
				super();
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
				setLastExpressionResult(result);
				
				theIndex.decrementReferenceCount();
				rawArrayBase.decrementReferenceCount();
				
				return nextCode_;	// probably null
			}
			
			private final String operation_;
			private final PreOrPost preOrPost_;
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
				pc = pc.run();
			} while ( null != pc);
			
			final RTObject rawIndexResult = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawIndexResult instanceof RTIntegerObject;
			final RTIntegerObject indexResult = (RTIntegerObject) rawIndexResult;
			
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			
			// This can be elicited either in a Role method or a Context method. If
			// it's a Role method the Context pointer is in current$context. Otherwise,
			// current$context is undeclared and we just use "this"
			RTObject currentContext = null;
			if (nearestEnclosingType_ instanceof RTRole) {
				currentContext = currentScope.getObject("current$context");
			} else {
				currentContext = currentScope.getObject("this");
			}
			
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			final RTObject rolePlayer = contextInfo.rolePlayerNamedAndIndexed(roleName_, indexResult);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(rolePlayer);
			return nextCode_;
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
			conditionalExpression_.setNextCode(part2_);
			setResultIsConsumed(expr.resultIsConsumed());
			part2_.setResultIsConsumed(expr.resultIsConsumed());
		}
		@Override public RTCode run() {
			// Put the condition on the stack
			return conditionalExpression_.run();
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
		
		private final RTIfPart2 part2_;
		private final RTExpression conditionalExpression_;
	}
	
	private static class RTForTestRunner extends RTExpression {
		public RTForTestRunner(final Expression testExpr, final RTExpression body, final RTPopDynamicScope popScope, final RTType nearestEnclosingType) {
			super();
			test_ = RTExpression.makeExpressionFrom(testExpr, nearestEnclosingType);
			part2_ = new RTForTestRunnerPart2(body, popScope);
			test_.setNextCode(part2_);
		}
		
		@Override public RTCode run() {
			return test_.run();
		}
		
		@Override public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		private static class RTForTestRunnerPart2 extends RTExpression {
			public RTForTestRunnerPart2(final RTExpression body, final RTPopDynamicScope popScope) {
				super();
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
				
				setLastExpressionResult(conditionalExpression);
				
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			private final RTExpression body_;
			private final RTPopDynamicScope popScope_;
		}
		
		private final RTExpression test_;
		private final RTForTestRunnerPart2 part2_;
	}
	
	public static class RTForCommon extends RTExpression implements RTBreakableExpression {
		public RTForCommon(final ForExpression expr, final RTType nearestEnclosingType) {
			super();
			
			objectDeclarations_ = new LinkedHashMap<String, RTType>();
			
			// Copy from the compiler data, the local variable names
			// declared in the FOR scope
			for (ObjectDeclaration objectDecl : expr.scope().objectDeclarations()) {
				final RTType objectType = null;	 // sigh...
				objectDeclarations_.put(objectDecl.name(), objectType);
			}

			// OLD: deprecated
			// initializations_ = this.expressionListFromObjectDeclarations(expr.initDecl());
			
			// NEW:
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
			for (Map.Entry<String, RTType> iter : objectDeclarations_.entrySet()) {
				// "for (int i = 0 ..." ==> i
				final String objectName = iter.getKey();
				final RTType type = iter.getValue();
				dynamicScope_.addObjectDeclaration(objectName, type);
			}

			// Process the initializations in this scope
			// WAIT - do we do this any more? Yeah, it's O.K.
						
			RTCode retval = null;
			if (initializations_.size() > 0) {
				retval = initializations_.get(0).run();
			} else {
				retval = test_;
			}
			
			setupIterator();
			
			// And run the loop, starting with the rest of the initializations
			// if necessary
			return retval;
		}
		protected void setupIterator() {
			// by default, nothing. Overridden in some derived classes
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
			return popScope_;
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
				evaluationResult_ = null;
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
		private final RTPushEvaluationResult evaluationResult_;
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
					
					// Halt the machine
					return null;
				} else {
					scope.setObject(iterationIdentifierName, valueFromIteration);
					return body_;
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
				evaluationResult_ = null;
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
		
		protected void setupIterator() {
			RTCode pc = rTThingToIterateOverExpr_.run();
			while (null != pc) {
				pc = pc.run();
			}
			final RTStackable rawThingToIterateOver = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawThingToIterateOver instanceof RTIterable;
			final RTIterable rTThingToIterateOver = (RTIterable)rawThingToIterateOver;
			final RTIterator iterator = RTIterator.makeIterator(rTThingToIterateOver);
			dynamicScope_.addObjectDeclaration("for$iterator", null);
			dynamicScope_.setObject("for$iterator", iterator);
		}
	
		private RTExpression rTThingToIterateOverExpr_;
		private final RTPushEvaluationResult evaluationResult_;
	}
	
	private static class RTWhileTestRunner extends RTExpression {
		public RTWhileTestRunner(final Expression testExpr, final RTExpression body, final RTExpression last, final RTType nearestEnclosingType) {
			super();
			test_ = RTExpression.makeExpressionFrom(testExpr, nearestEnclosingType);
			part2_ = new RTWhileTestRunnerPart2(body, last);
			test_.setNextCode(part2_);
		}
		
		@Override public RTCode run() {
			return test_.run();
		}
		
		@Override public void setNextCode(final RTCode next) {
			part2_.setNextCode(next);
		}
		
		private static class RTWhileTestRunnerPart2 extends RTExpression {
			public RTWhileTestRunnerPart2(final RTExpression body, final RTExpression last) {
				super();
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
				
				setLastExpressionResult(conditionalExpression);
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			
			private final RTExpression body_, last_;
		}
		
		private final RTExpression test_;
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
			return test_.run();
		}
		
		@Override public void setNextCode(final RTCode code) {
			part2_.setNextCode(code);
		}
		
		private static class RTDoWhileTestRunnerPart2 extends RTExpression {
			public RTDoWhileTestRunnerPart2(final Expression testExpr, final RTExpression body, final RTExpression last) {
				super();
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
				
				setLastExpressionResult(conditionalExpression);
				conditionalExpression.decrementReferenceCount();
				
				return retval;
			}
			
			private final RTExpression body_, last_;
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
		@Override public RTCode run() {
			RTCode retval = null;
			if (expressionList_.size() > 0) {
				retval = expressionList_.get(0).run();
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
			
			RTCode lastAdded = null;
			last_ = new RTNullExpression();
			cases_ = new LinkedHashMap<RTObject, RTExpressionList>();
			defaultCase_ = null;
			switchStack_.push(this);
			switchExpression_ = RTExpression.makeExpressionFrom(expr.switchExpression(), enclosingType);
			orderedSwitchBodyElements_ = expr.orderedSwitchBodyElements();
			for (SwitchBodyElement caseOrDefault : orderedSwitchBodyElements_) {
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
			RTCode retval = switchExpression_.run();
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
		private RTExpressionList defaultCase_;
		private final String label_;
		private final RTSwitchPart2 part2_;
	}
	
	public static class RTConstant extends RTExpression implements RTObject {
		public RTConstant() {
			super();
			rTExpr_ = null;
		}
		public RTConstant(final Constant expr) {
			this();
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
			setLastExpressionResult(rTExpr_);
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
		@Override public Map<String, RTObject> objectMembers() { assert false; return null; }
		@Override public RTType rTType() { return rTExpr_.rTType(); }
		@Override public boolean isEqualTo(final Object another) { return rTExpr_.isEqualTo(another); }
		@Override public boolean gt(final RTObject another) { return rTExpr_.gt(another); }
		@Override public RTObject plus(final RTObject other) { return rTExpr_.plus(other); }
		@Override public RTObject minus(final RTObject other) { return rTExpr_.minus(other); }
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
		@Override public boolean equals(final RTObject other) { assert false; return false; }
		
		protected RTObject rTExpr_;
	}
	
	public static class RTBreak extends RTExpression {
		public RTBreak(BreakExpression expr) {
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
				firstIter_ = false;
			}
			
			if (this.resultIsConsumed()) {
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
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
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
				} else {
					assert false;
				}
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
			
			private final String operator_;
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
			}
			@Override public RTCode run() {
				RTObject result = null;
				final RTObject rhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				final RTObject lhs = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
				result = lhs.toThePowerOf(rhs);
				
				RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
				setLastExpressionResult(result);
				
				lhs.decrementReferenceCount();
				rhs.decrementReferenceCount();
				
				return nextCode_;
			}
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
		public RTReturn(final String methodName, final List<RTCode> re, final RTType ignoredForNow) {
			super();
			rTRe_ = re;
			
			// So far this is used only for debugging
			methodName_ = methodName;
		}
		public RTReturn(final String methodName, final Expression returnExpression, final RTType ignoredForNow) {
			super();

			// If returnExpr isn't null, it's a dummy
			if (null != returnExpression) {
				rTRe_ = new ArrayList<RTCode>();
				rTRe_.add(new RTNullExpression());
			} else {
				rTRe_ = null;
			}
			
			// So far this is used only for debugging
			methodName_ = methodName;
		}
		@Override public RTCode run() {
			RTCode returnAddress = null;
			boolean thereIsAReturnExpression = false;
			if (null != rTRe_) {
				final List<RTCode> returnExpressionList = rTRe_;
				if (returnExpressionList.size() == 1) {
					final RTCode returnExpression = returnExpressionList.get(0);
					if (returnExpression instanceof RTNullExpression == true) {
						thereIsAReturnExpression = true;
					}
				} else {
					thereIsAReturnExpression = false;
				}
			}
			if (thereIsAReturnExpression) {
				// It's already evaluated and on top of the stack.
				// Step one: Get it - if it's used
				RTStackable returnValue = null;
				if (resultIsConsumed()) {
					returnValue = RunTimeEnvironment.runTimeEnvironment_.popStack();
				}
				
				// Step 2. Clean up the stack.
				RunTimeEnvironment.runTimeEnvironment_.popDownToFramePointer();
				returnAddress = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
				
				// Step 3. Put the return value on the stack.
				if (resultIsConsumed()) {
					RunTimeEnvironment.runTimeEnvironment_.pushStack(returnValue);
					returnValue.decrementReferenceCount();	// (from the stack pop)
				}
			} else {
				// There is no R-value on the stack: just get the return address
				RunTimeEnvironment.runTimeEnvironment_.popDownToFramePointer();
				returnAddress = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
			}
			
			assert returnAddress instanceof RTCode || returnAddress == null;
			
			// Pop activation record
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

		
		private List<RTCode> rTRe_;
		
		@SuppressWarnings("unused")
		private String methodName_;
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
			for (ObjectDeclaration objectDecl : expr.scope().objectDeclarations()) {
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
			
			final RTDynamicScope dynamicScope = new RTDynamicScope("block", RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
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
		
		private RTPushEvaluationResult evaluationResult_;
	}
	
	public static class RTPromoteToDoubleExpr extends RTExpression {
		public RTPromoteToDoubleExpr(final PromoteToDoubleExpr expr, final RTType nearestEnclosingType) {
			super();
			expr_ = RTExpression.makeExpressionFrom(expr.promotee(), nearestEnclosingType);
			part2_ = new RTPromoteToDoubleExprPart2();
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
	}
	public static class RTPromoteToDoubleExprPart2 extends RTExpression {
		public RTPromoteToDoubleExprPart2() {
			super();
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
			setLastExpressionResult(object);
			
			object.decrementReferenceCount();		// either way
			
			return nextCode_;
		}
	}
	public static class RTIndexExpression extends RTExpression {
		public RTIndexExpression(final IndexExpression indexExpression) {
			super();
			roleName_ = indexExpression.roleName();
		}
		@Override public RTCode run() {
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject currentContext = currentScope.getObject("current$context");
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			final RTObject self = currentScope.getObject("this");
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			final RTIntegerObject theIndex = contextInfo.indexOfRolePlayer(roleName_, self);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theIndex);
			return nextCode_;
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
	
	
	protected static void setLastExpressionResult(final RTObject value) {
		if (null == value) {
			ErrorLogger.error(ErrorType.Internal, "Internal expression evaluated to a NULL Java object", "", "", "");
			assert null != value;
		}
		final RTObject previousLastResult = lastExpressionResult_;
		value.incrementReferenceCount();
		if (null != previousLastResult) {
			previousLastResult.decrementReferenceCount();
		}
		lastExpressionResult_ = value;
	}
	public static final RTObject lastExpressionResult() {
		return lastExpressionResult_;
	}
	
	protected final static Stack<RTSwitch> switchStack_ = new Stack<RTSwitch>();
	private boolean resultIsConsumed_;
	protected static RTObject lastExpressionResult_ = null;
}
