package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2022 James O. Coplien, jcoplien@gmail.com
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

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.MethodInvocationEnvironmentClass;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTExpression.RTAssignment.RTInternalAssignmentPart2;
import info.fulloo.trygve.run_time.RTExpression.RTInternalAssignment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTExpression.RTMessage.RTPostReturnProcessing;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public abstract class RTMessageDispatcher {
	public static RTMessageDispatcher makeDispatcher(
			final MessageExpression messageExpr,
			final String methodSelectorName,
			final RTCode argPush,
			final RTPostReturnProcessing postReturnProcessing,
			final int [] expressionsCountInArguments,
			final ActualArgumentList actualParameters,
			final boolean isStatic,
			final RTType nearestEnclosingType,
			final MethodInvocationEnvironmentClass originMessageClass,
			final MethodInvocationEnvironmentClass targetMessageClass
			) {
		RTMessageDispatcher retval = null;
		switch (originMessageClass) {
		case ClassEnvironment:
			switch(targetMessageClass) {
			case ClassEnvironment:
				// WARNING: Factory-method-like constructor
				retval = new RTNonContextToNonContext(messageExpr, methodSelectorName, argPush, postReturnProcessing, expressionsCountInArguments, actualParameters, isStatic, nearestEnclosingType);
				retval = ((RTNonContextToNonContext)retval).value();
				// NO: lookup can fail. assert null != retval;
				break;
			case RoleEnvironment:
				assert false;
				retval = null;
				break;
			case ContextEnvironment:
				retval = new RTNonContextToContext(
						messageExpr,
						methodSelectorName,
						argPush,
						postReturnProcessing,
						expressionsCountInArguments,
						actualParameters,
						isStatic,
						nearestEnclosingType
						);
				break;
			case Unknown:
				assert false;
				break;
			}
			break;
		case RoleEnvironment:
			switch (targetMessageClass) {
			case ClassEnvironment:
				retval = new RTRoleToClass(
						messageExpr,
						methodSelectorName,
						argPush,
						postReturnProcessing,
						expressionsCountInArguments,
						actualParameters,
						isStatic,
						nearestEnclosingType
						);
				break;
			case RoleEnvironment:
				retval = new RTRoleToRole(
						messageExpr,
						methodSelectorName,
						argPush,
						postReturnProcessing,
						expressionsCountInArguments,
						actualParameters,
						isStatic,
						nearestEnclosingType
						);
				break;
			case ContextEnvironment:
				retval = new RTRoleToContext(
						messageExpr,
						methodSelectorName,
						argPush,
						postReturnProcessing,
						expressionsCountInArguments,
						actualParameters,
						isStatic,
						nearestEnclosingType
						);
				break;
			case Unknown:
				assert false;
				break;
			}
			break;
		case ContextEnvironment:
			switch (targetMessageClass) {
			case ClassEnvironment:
				retval = new RTContextToClass(messageExpr, methodSelectorName, argPush, postReturnProcessing, expressionsCountInArguments, actualParameters, isStatic, nearestEnclosingType);
				break;
			case RoleEnvironment:
				retval = new RTContextToRole(messageExpr, methodSelectorName, argPush, postReturnProcessing, expressionsCountInArguments, actualParameters, isStatic, nearestEnclosingType);
				break;
			case ContextEnvironment:
				retval = new RTContextToContext(
						messageExpr,
						methodSelectorName,
						argPush,
						postReturnProcessing,
						expressionsCountInArguments,
						actualParameters,
						isStatic,
						nearestEnclosingType
						);
				break;
			case Unknown:
				assert false;
				break;
			}
			break;
		case Unknown:
			assert false;
			break;
		}
		return retval;
	}
	
	public RTMessageDispatcher(final MessageExpression messageExpr,
								final String methodSelectorName,
								final RTCode argPush,
								final RTPostReturnProcessing postReturnProcessing,
								final int [] expressionsCountInArguments,
								final ActualArgumentList actualParameters,
								final boolean isStatic,
								final RTType nearestEnclosingType) {
		messageExpr_ = messageExpr;
		token_ = messageExpr_.token();
		methodSelectorName_ = methodSelectorName;
		argPush_ = argPush;
		postReturnProcessing_ = postReturnProcessing;
		expressionsCountInArguments_ = expressionsCountInArguments;
		actualParameters_ = actualParameters;
		isStatic_ = isStatic;
		currentScope_ = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
		isBuiltInAssert_ = false;
		hasError_ = null;
		methodDecl_ = null;
		nearestEnclosingType_ = nearestEnclosingType;
		
		final Type objectListType = StaticScope.globalScope().lookupTypeDeclaration("List<Object>");
		if (null == objectListType) {
			ErrorLogger.error(ErrorIncidenceType.Internal, "List<Object> lookup failure.", "", "", "");
			assert false;
		}
		final Type objectType = StaticScope.globalScope().lookupTypeDeclaration("Object");
		if (null == objectType) {
			assert false;
		}
		
		rTListOfObjectType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(objectListType.enclosedScope());
		rTObjectType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(objectType.enclosedScope());
	}
	
	protected abstract RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self);
	
	public RTMethod methodDecl() {
		return methodDecl_;
	}
	
	public int lineNumber() {
		return token_.getLine();
	}
	
	public Token token() {
		return token_;
	}
	
	public RTCode hasError() {
		return hasError_;
	}

	protected RTStackable pushArgumentLoop(final RTCode start, final int expressionCounterForThisExtraction,
			final int indexForThisExtraction) {
		RTCode pc = start;
		startingStackIndex_ = RunTimeEnvironment.runTimeEnvironment_.stackIndex();
		RTObject self = null;
		RTStackable retval = null;
		while (null != pc) {
			// This evaluation leaves a result on the stack -
			// a result which will be a parameter to the method
			
			// Make sure it doesn't get popped. Setting things in ActualParameters
			// doesn't seem to be enough (setResultIsConsumed(true)). This one line
			// is the entire reason for the existence of InternalAssignmentExpression
			// and RTInternalAssignment.
			if (pc instanceof RTExpression && (pc instanceof RTInternalAssignment == false) && (pc instanceof RTInternalAssignmentPart2 == false)) {
				((RTExpression)pc).setResultIsConsumed(true);
			}
			
			// Woops - this gets short-circuited if it's a method invocation?
			// It goes off to evaluate the method (as a "this" argument) and
			// then, just below, expects to pull "this" (e.g. PrintStream)
			// off the stack - but what's sitting on the stack is the return
			// address for the method (RTPostReturnProcessing) and "nextInstruction"
			// points to a method
			final RTCode nextInstruction = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			final RTCode oldPc = pc;
			pc = nextInstruction;
			if (pc instanceof RTHalt) {
				return pc;
			} else if (null != pc) {
				pc.incrementReferenceCount();
			}
			oldPc.decrementReferenceCount();
		}
		
		if (false == isStatic_ && RunTimeEnvironment.runTimeEnvironment_.stackIndex() <= startingStackIndex_ + indexForThisExtraction) {
			ErrorLogger.error(ErrorIncidenceType.Internal, "Internal error in message dispatching: no `this' pushed?",
					new Throwable().getStackTrace()[1].getMethodName(), ":", Integer.toString(new Throwable().getStackTrace()[1].getLineNumber()));
			assert RunTimeEnvironment.runTimeEnvironment_.stackIndex() > startingStackIndex_ + indexForThisExtraction;
		}
		
		self = isStatic_? null:
			(RTObject)RunTimeEnvironment.runTimeEnvironment_.stackValueAtIndex(startingStackIndex_ + indexForThisExtraction);
		if (self == null && !isStatic_) {
			// Some kind of error. Assume we're in the stumbling business
			retval = (RTStackable)new RTHalt();
		} else {
			retval = self;
		}
		return retval;
	}
	
	protected void populateActivationRecord(final RTMethod methodDecl, final RTDynamicScope activationRecord) {
		// Get class for debugging info
		final MethodDeclaration originalMethodDecl = methodDecl.methodDeclaration();
		final StaticScope methodScope = originalMethodDecl.enclosingScope();
		activationRecord.setDebuggingTypeName(methodScope.name());
		final FormalParameterList formalParameters = methodDecl.formalParameters();
		if (formalParameters.containsVarargs()) {
			varargsPopulateActivationRecord(methodDecl, activationRecord);
		} else {
			nonVarargsPopulateActivationRecord(methodDecl, activationRecord);
		}
	}
	
	private void varargsPopulateActivationRecord(final RTMethod methodDecl, final RTDynamicScope activationRecord) {
		final int numberOfArgumentsPushed = RunTimeEnvironment.runTimeEnvironment_.stackIndex() - startingStackIndex_;
		final FormalParameterList formalParameters = methodDecl.formalParameters();
		
		// The number of varargs parameters should be the difference of the
		// number of args pushed and the number expected
		final int numberOfFormalParameters = formalParameters.count();
		final int numberOfVarargsParameters = numberOfArgumentsPushed - numberOfFormalParameters + 1;
		
		// Start with the varargs parameters - they're on top of the stack
		final RTListObject varargs = new RTListObject(rTObjectType_);
		for (int i = 0; i < numberOfVarargsParameters; i++) {
			final RTObject rawArgument = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			varargs.add(rawArgument);
		}
		
		activationRecord.addObjectDeclaration("arguments", null);
		activationRecord.setObject("arguments", varargs);
		
		// Continue with the named formal parameters, and self
		// Second -1 is for the varargs
		// This indexing causes "i" to traverse the arguments lexically right-to-left
		for (int i = formalParameters.count() - 1 - 1; i >= 0; --i) {
			final Declaration ithParameter = formalParameters.parameterAtPosition(i);
			assert ithParameter instanceof ObjectDeclaration || ithParameter.isError();
			final String ithParameterName = ithParameter.name();
			final RTType rTIthParameterType = null; // InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(ithParameter.type());
			assert(null == activationRecord.getObject(ithParameterName));
			activationRecord.addObjectDeclaration(ithParameterName, rTIthParameterType);
			final RTStackable rawArgument = RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (rawArgument instanceof RTObject == false) {
				assert rawArgument instanceof RTObject;
			}
			if (ithParameterName.equals("current$context") && rawArgument instanceof RTContextObject == false) {
				assert rawArgument instanceof RTContextObject;
				// probably didn't push it when we should have
			}
			final RTObject anArgument = (RTObject)rawArgument;
			assert null != anArgument;
			activationRecord.setObject(ithParameterName, anArgument);
			anArgument.decrementReferenceCount();	// not on the stack any more
		}
		if (this.isBuiltInAssert_) {
			// Put the line number in the activation record
			final RTIntegerObject lineNumberToPush = new RTIntegerObject(token_.getLine());
			activationRecord.addObjectDeclaration("lineNumber", null);
			activationRecord.setObject("lineNumber", lineNumberToPush);
		}
	}
	
	private void nonVarargsPopulateActivationRecord(final RTMethod methodDecl, final RTDynamicScope activationRecord) {
		final FormalParameterList formalParameters = methodDecl.formalParameters();
		for (int i = formalParameters.count() - 1; 0 <= i; --i) {
			final Declaration ithParameter = formalParameters.parameterAtPosition(i);
			assert ithParameter instanceof ObjectDeclaration || ithParameter.isError();
			final String ithParameterName = ithParameter.name();
			final RTType rTIthParameterType = null; // InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(ithParameter.type());
			assert(null == activationRecord.getObject(ithParameterName));
			activationRecord.addObjectDeclaration(ithParameterName, rTIthParameterType);
			final RTStackable rawArgument = RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (rawArgument instanceof RTObject == false) {
				assert rawArgument instanceof RTObject;
			}
			if (ithParameterName.equals("current$context") && rawArgument instanceof RTContextObject == false) {
				assert rawArgument instanceof RTContextObject;
				// if assertion fires, we probably didn't push it when we should have
			}
			final RTObject anArgument = (RTObject)rawArgument;
			assert null != anArgument;
			activationRecord.setObject(ithParameterName, anArgument);
			anArgument.decrementReferenceCount();	// not on the stack any more
		}
		if (this.isBuiltInAssert_) {
			// Put the line number in the activation record
			final RTIntegerObject lineNumberToPush = new RTIntegerObject(token_.getLine());
			activationRecord.addObjectDeclaration("lineNumber", null);
			activationRecord.setObject("lineNumber", lineNumberToPush);
		}
	}
	
	protected RTMethod getNonPolymorphicMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
		// Should look up method in the receiver's STATIC scope. Get the
		// current scope so we can get all the magic identifiers
		// (this, current$context, etc.)
		final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
		RTMethod methodDecl = null;
		assert currentScope.rTType() instanceof RTContextObject == false;
		assert currentScope.rTType() instanceof RTRole == false;

		methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, false);
		
		if (null == methodDecl) {
			assert null != methodDecl;
		}
		return methodDecl;
	}
	
	protected void commonWrapup(final Type typeOfThisParameterToCalledMethod,
			final int indexForThisExtraction,
			final RTObject self, final boolean isPolymorphic) {
		// Get the method declaration by looking it up in the receiver's scope
		// Null return on error (e.g., attempting to invoke a method on a null object)
		if (isPolymorphic) {
			methodDecl_ = this.getMethodDecl(typeOfThisParameterToCalledMethod, indexForThisExtraction, self);
		} else {
			methodDecl_ = this.getNonPolymorphicMethodDecl(typeOfThisParameterToCalledMethod, indexForThisExtraction, self);
		}
		
		// may be null if, for example, invoking a method on a null object
		// (test case tests/inheritance.k)
		
		// While we're at it, see if we're calling the real assert. It
		// gets an extra argument pushed in the loop below.
		isBuiltInAssert_ = false;
		if (methodSelectorName_.equals("assert")) {
			if (null != methodDecl_) {
				final MethodDeclaration originalMethodDeclaration = methodDecl_.methodDeclaration();
				if (null != originalMethodDeclaration) {
					final StaticScope methodScope = originalMethodDeclaration.enclosedScope();
					final StaticScope declaringScope = null == methodScope? null: methodScope.parentScope();
					final Declaration associatedDeclaration = null == declaringScope? null:
											declaringScope.associatedDeclaration();
					final String typePathName = null == associatedDeclaration? " ":
						associatedDeclaration.type().pathName();
					if (typePathName.equals("Object.")) {
						isBuiltInAssert_ = true;
					}
				}
			}
		}
		
		if (null != methodDecl_) {
			// Now that the actual parameters are on the stack, let's
			// open up an activation record, pop off the arguments
			// and move them into the activation record as formal
			// parameters
			
			// Push the activation record onto the activation record stack
			// (I think this is the only place in the code where activation
			// records are pushed)
			final RTDynamicScope activationRecord = new RTDynamicScope(methodDecl_.name(), RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope(), true);
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
			activationRecord.incrementReferenceCount();
			this.populateActivationRecord(methodDecl_, activationRecord);
		}
	
		// Turn it over to the executive to call the method.
	}
	
	protected RTMethod contextMethodDeclLookup(final RTObject contextOfRoleOfInvokingMethod,
			final Type typeOfThisParameterToMethod) {
		RTMethod methodDecl = null;
		final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
		
		if (contextOfRoleOfInvokingMethod instanceof RTContextObject) {
			// Then the invoker is inside of a Context, or perhaps the Role
			// of a Context.
			final RTContextObject invokingContext = (RTContextObject)contextOfRoleOfInvokingMethod;
			final RTType rawContextType = invokingContext.rTType();
			assert rawContextType instanceof RTContext;
			final RTContext typeOfInvokingContext = (RTContext)rawContextType;
			
			// See if it is another Role in this context.
			// (But, hey, we *have* the Context of the target — why
			// go skurking for it here?!)
			RTRole theRole = typeOfInvokingContext.getRole(typeOfThisParameterToMethod.name());
			if (null == theRole) {
				final RTStageProp theStageProp = typeOfInvokingContext.getStageProp(typeOfThisParameterToMethod.name());
				if (null == theStageProp) {
					// Example: contextType here is TextFile
					// typeOfThisParameterToMethod is for Role SpellCheck.Text
					// it's an argument of an assert, called from within
					//	SpellCheck.Text.nextWord, called by println, right after SpellCheck
					// 	is instantiated (around line 155). Trying to call
					// 	SpellCheck.Text.isFinished.
					//
					// See cotnext_role_bug1.k
					//
					// Role Text is of type TextFile, which is also a Context
					assert null != theStageProp;
				}
				methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
			} else {
				assert null != theRole;
				methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
			}
			assert null != methodDecl;
		} else if (contextOfRoleOfInvokingMethod instanceof RTObjectCommon) {
			// The "this" parameter is a pointer to the Role Player, typed
			// in terms of the Role Player's type.

			// That implies *for now* that the call is invoking an instance method
			// and not a role method. So:
			
			final RTObject rolePlayer = (RTObject)contextOfRoleOfInvokingMethod;
			final RTType rolePlayerType = rolePlayer.rTType();
			assert null != rolePlayerType;
			methodDecl = rolePlayerType.lookupMethod(methodSelectorName_, actualParameters_);
			if (null == methodDecl) {
				// ... then it is another Role method invocation on the
				// RolePlayer.  First, get the RTRole:
				final RTObject objectContextPointer = RTExpression.getObjectUpToMethodScopeFrom("current$context", currentScope);
				if (objectContextPointer instanceof RTContextObject == false) {
					assert objectContextPointer instanceof RTContextObject;
				}
				final RTContextObject contextPointer = (RTContextObject)objectContextPointer;
				
				final RTType rawContextType = contextPointer.rTType();
				assert rawContextType instanceof RTContext;
				final RTContext contextType = (RTContext)rawContextType;
				RTRole theRole = contextType.getRole(typeOfThisParameterToMethod.name());
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
			// It could be that we are calling from a Role method to
			// another Role method. Then could indicate a Role
			// object in this (though it is more likely to point to
			// a RTCommonObject, which it probably does — so we should
			// never get here).
			assert false;
		}
		
		return methodDecl;
	}
	protected Type commonProlog(final int indexForThisExtraction, final int expressionCounterForThisExtraction) {
		// Push the return address onto the stack
		RunTimeEnvironment.runTimeEnvironment_.pushStack(postReturnProcessing_);
		RunTimeEnvironment.runTimeEnvironment_.setFramePointer();

		final ActualArgumentList argList = actualParameters_;
		Expression thisDeclaration = null;
		Type typeOfThisParameterToCalledMethod = null;
		if (this.isStatic_) {
			// There is no "this" on the stack for static method calls
			if (null == this.messageExpr_) {
				assert null != this.messageExpr_;
			}
			final Expression classNameExpression = this.messageExpr_.objectExpression();
			assert classNameExpression instanceof IdentifierExpression;
			final String className = classNameExpression.name();
			
			// Works only for global scope types. FIXME.
			typeOfThisParameterToCalledMethod = StaticScope.globalScope().lookupTypeDeclaration(className);
			assert null != typeOfThisParameterToCalledMethod;
		} else {
			final Object rawThisDeclaration = argList.argumentAtPosition(indexForThisExtraction); // could be a Role identifier...
			assert null != rawThisDeclaration && rawThisDeclaration instanceof Expression;
			thisDeclaration = (Expression)rawThisDeclaration;
			typeOfThisParameterToCalledMethod = thisDeclaration.type();	// tentative...
		}
		return typeOfThisParameterToCalledMethod;
	}
	
	protected RTMethod getRoleMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self,
			final String identifierHoldingContextPointer) {
		RTMethod methodDecl = null;
		if (typeOfThisParameterToMethod instanceof RoleType) {
			final String roleName = typeOfThisParameterToMethod.name();
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			
			// Get the calling Context (and it will be the target Context, too)
			final RTObject newSelf = currentScope.getObject(identifierHoldingContextPointer);
			if (null != newSelf && newSelf instanceof RTContextObject) {
				final RTContextObject contextPointer = (RTContextObject)newSelf;
				RTType type = contextPointer.rTType();
				if (type instanceof RTContext) {
					final RTContext context = (RTContext)type;
					
					// note that we want the type of the
					// declaration and NOT of the object
					// (so we should get the Role type)
					final RTRole theRole = context.getRoleDecl(roleName);
					// final RTRole theRole = context.getRole(roleName);
					if (null == theRole) {
						final RTStageProp theStageProp = context.getStagePropDecl(roleName);
						if (null != theStageProp) {
							methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
						}
					} else {
						methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
					}
				}
			}
		}
		
		if (null != methodDecl && null == methodDecl.nextCode()) {
			// Then it's just a declaration (e.g., in the
			// "requires" part of a Role declaration)
			methodDecl = null;
		}
		return methodDecl;
	}
	
	private RTMethod genericPolymorphicMethodLookup(final RTType rTTypeOfSelf, final Type typeOfThisParameterToMethod,
			final String methodSelectorName, final ActualOrFormalParameterList actualParameters) {
		// Give a direct match the first chance
		RTMethod methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureNamed(methodSelectorName, actualParameters, "this");
		if (null == methodDecl) {
			methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionNamed(methodSelectorName, actualParameters, "this");
			if (null == methodDecl) {
				if (typeOfThisParameterToMethod instanceof RoleType) {
					methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
					if (null == methodDecl) {
						methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
					}
				}
				
				RTType selfType = rTTypeOfSelf;
				if (null == methodDecl && selfType instanceof RTClass) {
					// Try the base classes
				
					ClassDeclaration baseClassDecl = null;
					do {
						final RTClass rTTypeOfSelfAsClass = (RTClass) selfType;
						baseClassDecl = rTTypeOfSelfAsClass.baseClassDeclaration();
						if (null != baseClassDecl) {
							final RTType rTBaseClassType = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(baseClassDecl);
							assert rTBaseClassType instanceof RTClass;
							methodDecl = rTBaseClassType.lookupMethodIgnoringParameterInSignatureWithConversionNamed(methodSelectorName, actualParameters, "this");
							// No. methodDecl = rTBaseClassType.lookupBaseClassMethodLiskovCompliantTo(methodSelectorName, actualParameters);
							if (null == methodDecl) {
								selfType = rTBaseClassType;
							}
						}
					} while (null == methodDecl && null != baseClassDecl);
				}
				
				if (null == methodDecl) {
					// One last try - look it up in Object.
					methodDecl = rTTypeOfSelf.lookupMethod(methodSelectorName, actualParameters);
					if (null == methodDecl) {
						methodDecl = rTTypeOfSelf.lookupMethod(methodSelectorName, actualParameters);
						assert null != methodDecl;
					}
				}
			}
			assert null != methodDecl;
		}
		return methodDecl;
	}
	
	private RTMethod nonPolymorphicMethodLookup(final Type typeOfThisParameterToMethod,
			final String methodSelectorName, final ActualOrFormalParameterList actualParameters) {
		// Give a direct match the only chance
		final RTType rTTypeOfLocalSelf = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(typeOfThisParameterToMethod.enclosedScope());
;		RTMethod methodDecl = rTTypeOfLocalSelf.lookupMethodIgnoringParameterInSignatureNamed(methodSelectorName, actualParameters, "this");
		if (null == methodDecl) {
			methodDecl = rTTypeOfLocalSelf.lookupMethodIgnoringParameterInSignatureWithConversionNamed(methodSelectorName, actualParameters, "this");
			if (null == methodDecl) {
				if (typeOfThisParameterToMethod instanceof RoleType) {
					methodDecl = rTTypeOfLocalSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
					if (null == methodDecl) {
						methodDecl = rTTypeOfLocalSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
					}
				}
				if (null == methodDecl) {
					// One last try - look it up in Object
					methodDecl = rTTypeOfLocalSelf.lookupMethod(methodSelectorName, actualParameters);
					assert null != methodDecl;
				}
			}
			assert null != methodDecl;
		}
		return methodDecl;
	}
	
	protected RTMethod genericMethodDeclLookup(final Type typeOfThisParameterToMethod, final RTObject self, boolean isPolymorphic) {
		RTMethod methodDecl = null;	
		// Calculate the address of the method. Generalize for classes and Contexts.
		final RTType rTTypeOfSelf = null != self? self.rTType():
				InterpretiveCodeGenerator.scopeToRTTypeDeclaration(typeOfThisParameterToMethod.enclosedScope());

		if (self instanceof RTNullObject) {
			ErrorLogger.error(ErrorIncidenceType.Fatal, token(), "FATAL: TERMINATED: Attempting to invoke method `",
					methodSelectorName_, "' on a null object", "");
			RTMessage.printMiniStackStatus();
			
			// Halt the machine
			hasError_ = new RTHalt();
			return null;
		} else if (null == rTTypeOfSelf) {
			ErrorLogger.error(ErrorIncidenceType.Internal, token(), "INTERNAL: Attempting to invoke method `",
					methodSelectorName_, "' on a null Java object", "");
			hasError_ = new RTHalt();
			return null;
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
		
		if (isPolymorphic) {
			methodDecl = genericPolymorphicMethodLookup(rTTypeOfSelf,
					typeOfThisParameterToMethod, methodSelectorName, actualParameters);
		} else {
			if (typeOfThisParameterToMethod	instanceof ArrayType) {
				// As usual
				methodDecl = genericPolymorphicMethodLookup(rTTypeOfSelf,
						typeOfThisParameterToMethod, methodSelectorName, actualParameters);
			} else {
				methodDecl = nonPolymorphicMethodLookup(typeOfThisParameterToMethod, methodSelectorName, actualParameters);
			}
		}

		return methodDecl;
	}
		
	protected RTMethod noncompliantRoleMethodDeclLookup(final Type typeOfThisParameterToMethod, final int indexForThisExtraction,
			final RTObject self) {
		RTMethod methodDecl = null;
		final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
		// Then self is an object playing the Role (with rTType an instance of RTClass)
		// Get the current RTContext (must be in self of current activation record)
		final RTObject contextOfRoleOfInvokingMethod = RTExpression.getObjectUpToMethodScopeFrom("current$context", currentScope);
		assert null != contextOfRoleOfInvokingMethod;
		assert contextOfRoleOfInvokingMethod instanceof RTContextObject;
		
		// Also get the Context of the Role of the invoked method. IT IS OK
		// THAT IT NOT BE THE CURRENT CONTEXT, in the event that the
		// Role-player is itself a Context instance!
		assert typeOfThisParameterToMethod instanceof RoleType;
		
		final RoleType roleOfCallee = (RoleType)typeOfThisParameterToMethod;
		final Declaration rawTargetContext = roleOfCallee.contextDeclaration();
		assert rawTargetContext instanceof ContextDeclaration;
		final ContextDeclaration targetContext = (ContextDeclaration) rawTargetContext;
		final RTType rawrTTypeOfTargetContext = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(targetContext);
		assert rawrTTypeOfTargetContext instanceof RTContext;
		final RTContext rTTypeOfTargetContext = (RTContext)rawrTTypeOfTargetContext;
		
		RTRole theRole = rTTypeOfTargetContext.getRole(typeOfThisParameterToMethod.name());
		if (null == theRole) {
			final RTStageProp theStageProp = rTTypeOfTargetContext.getStageProp(typeOfThisParameterToMethod.name());
			methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
		} else {
			methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
		}
		
		if (null == methodDecl) {
			methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
					typeOfThisParameterToMethod);
		}

		return methodDecl;
	}
	
	// -----------------------------------------------------------------

	
	private static class RTNonContextToNonContext extends RTMessageDispatcher {
		public RTNonContextToNonContext(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);
			
			final int indexForThisExtraction = 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			retval_ = null;
			
			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			if (typeOfThisParameterToCalledMethod instanceof InterfaceType) {
				// Uh-oh. We don't really know what it is. It could be a Context or
				// a class. If it's a class - fine; we're in the right place.
				// If it's a Context, we've been hoodwinked behind the
				// interface. Well, we can't know until run time, so now
				// is the time to make amends and send control off to
				// another handler.
				//
				// Effectively push the arguments. Ugly and inefficient, but
				// it works.
				
				int stackSize = 0;
				final int startingStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				RTCode pc = start;
				do {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				} while (null != pc && pc instanceof RTHalt == false);
				
				// All the arguments are on the stack. Pop them off down to self.
				RTObject inquisitiveSelf = null;
				do {
					inquisitiveSelf = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
					stackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				} while (stackSize - startingStackSize > indexForThisExtraction);
				
				// The value of this should be in inquisitiveSelf. Clean up the other arguments.
				while (stackSize > startingStackSize) {
					RunTimeEnvironment.runTimeEnvironment_.popStack();
					stackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				}
				
				assert (stackSize == startingStackSize);
				
				if (inquisitiveSelf instanceof RTContextObject) {
					// Houston, we have a problem.
					// Start by undoing what commonProlog did
					RunTimeEnvironment.runTimeEnvironment_.popDownToFramePointer();
					RunTimeEnvironment.runTimeEnvironment_.popStack();
					
					retval_ = new RTNonContextToContext(messageExpr,
							methodSelectorName,
							argPush,
							postReturnProcessing,
							expressionsCountInArguments,
							actualParameters,
							isStatic,
							nearestEnclosingType
							);
					return;
				}
			}
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code.
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					if (self instanceof RTContextObject || self instanceof RTRole) {
						ErrorLogger.error(ErrorIncidenceType.Internal, "Dispatching error for `",
								methodSelectorName, "'.", "");
						hasError_ = new RTHalt();
					}
				}
				commonWrapup(typeOfThisParameterToCalledMethod, 0, self, messageExpr.isPolymorphic());
				retval_ = this;
			}
		}
		
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			RTMethod methodDecl = null;
			assert currentScope.rTType() instanceof RTContextObject == false;
			assert currentScope.rTType() instanceof RTRole == false;

			methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
			
			if (null == methodDecl) {
				;	// we propagate the error upstream and it
					// eventually will generate an RTHalt
			}
			return methodDecl;
		}
		
		public RTMessageDispatcher value() {
			return retval_;
		}
		private RTMessageDispatcher retval_;
	}
	
	private static class RTNonContextToContext extends RTMessageDispatcher {
		public RTNonContextToContext(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);
			
			final int indexForThisExtraction = 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			
			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					assert self instanceof RTContextObject /* && self instanceof RTRole == false */;
				}
				commonWrapup(typeOfThisParameterToCalledMethod, 0, self, messageExpr.isPolymorphic());
			}
		}
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			RTMethod methodDecl = null;
			assert currentScope.rTType() instanceof RTContextObject == false;
			assert currentScope.rTType() instanceof RTRole == false;

			if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
				// Then self is an object playing the Role (with rTType an instance of RTClass)
				// Get the current RTContext (must be in self of current activation record)
				assert false;
			} else {
				// Calculate the address of the method. Generalize for classes and Contexts.
				final RTType rTTypeOfSelf = null != self? self.rTType():
					InterpretiveCodeGenerator.scopeToRTTypeDeclaration(typeOfThisParameterToMethod.enclosedScope());
				if (self instanceof RTNullObject) {
					ErrorLogger.error(ErrorIncidenceType.Fatal, token(), "FATAL: TERMINATED: Attempting to invoke method ",
							methodSelectorName_, " on a null object", "");
					RTMessage.printMiniStackStatus();
					
					// Halt the machine
					return null;
				} else if (null == rTTypeOfSelf) {
					ErrorLogger.error(ErrorIncidenceType.Internal, token(), "INTERNAL: Attempting to invoke method `",
							methodSelectorName_, "' on a null Java object", "");
					return null;
					// assert null != rTTypeOfSelf;
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
							assert false;
						} else {
							// One last try - look it up in Object
							methodDecl = rTTypeOfSelf.lookupMethod(methodSelectorName, actualParameters);
							assert null != methodDecl;
						}
					}
					assert null != methodDecl;
				}
			}
			
			if (null == methodDecl) {
				assert null != methodDecl;
			}
			return methodDecl;
		}
	}
	
	private static class RTContextToContext extends RTMessageDispatcher {
		public RTContextToContext(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);

			final int indexForThisExtraction = 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			
			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					assert self instanceof RTContextObject /* && self instanceof RTRole == false */;
				}
				commonWrapup(typeOfThisParameterToCalledMethod, 0, self, messageExpr.isPolymorphic());
			}
		}
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			RTMethod methodDecl = null;

			if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
				assert false;	// we shouldn't be in a Role
			} else {
				methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
			}

			if (null == methodDecl) {
				assert null != methodDecl;
			}
			
			return methodDecl;
		}
	}
	
	private static class RTContextToClass extends RTMessageDispatcher {
		public RTContextToClass(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr, methodSelectorName, argPush, postReturnProcessing, expressionsCountInArguments, actualParameters, isStatic, nearestEnclosingType);
			
			final int indexForThisExtraction = 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			
			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					assert self instanceof RTContextObject == false;
					assert self instanceof RTRole == false;
				}
				commonWrapup(typeOfThisParameterToCalledMethod, 0, self, messageExpr.isPolymorphic());
			}
		}
		
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			RTMethod methodDecl = null;

			if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
				// Then self is an object playing the Role (with rTType an instance of RTClass)
				// Get the current RTContext (must be in self of current activation record)
				final RTObject contextOfRoleOfInvokingMethod = RTExpression.getObjectUpToMethodScopeFrom("this", currentScope);;
				assert null != contextOfRoleOfInvokingMethod;
				
				// Also get the Context of the Role of the invoked method. IT IS OK
				// THAT IT NOT BE THE CURRENT CONTEXT, in the event that the
				// Role-player is itself a Context instance!
				assert typeOfThisParameterToMethod instanceof RoleType;
				
				final RoleType roleOfCallee = (RoleType)typeOfThisParameterToMethod;
				final Declaration rawTargetContext = roleOfCallee.contextDeclaration();
				assert rawTargetContext instanceof ContextDeclaration;
				final ContextDeclaration targetContext = (ContextDeclaration) rawTargetContext;
				final RTType rawrTTypeOfTargetContext = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(targetContext);
				assert rawrTTypeOfTargetContext instanceof RTContext;
				final RTContext rTTypeOfTargetContext = (RTContext)rawrTTypeOfTargetContext;
				
				RTRole theRole = rTTypeOfTargetContext.getRole(typeOfThisParameterToMethod.name());
				if (null == theRole) {
					final RTStageProp theStageProp = rTTypeOfTargetContext.getStageProp(typeOfThisParameterToMethod.name());
					methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
				} else {
					methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
				}
				
				if (null == methodDecl) {
					methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
							typeOfThisParameterToMethod);
				}
			} else {
				methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
			}
			
			if (null == methodDecl) {
				assert null != methodDecl;
			}
			return methodDecl;
		}
	}
	
	private static class RTContextToRole extends RTMessageDispatcher {
		public RTContextToRole(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);
			
			final int indexForThisExtraction = 1;	// seems very temperamental about being generalised...
			// final int indexForThisExtraction = ((Expression)actualParameters_.argumentAtPosition(0)).name().equals("current$context")? 1: 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			
			RTObject self = null;
			RTCode start = argPush_;
			
			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					
					// NO. A Context can play a Role
					// assert self instanceof RTContextObject == false;
					
					// NO. Even though we are calling a Role method,
					// the type of self will be the type of a RolePlayer.
					// At this point we know it's a Role method (do we?)
					// so we have used the right stack protocol
					// assert self instanceof RTRole == true;
					
					commonWrapup(typeOfThisParameterToCalledMethod, 1, self, messageExpr.isPolymorphic());
				} else {
					assert false;
				}
			}
		}
		
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			RTMethod methodDecl = getRoleMethodDecl(typeOfThisParameterToMethod, indexForThisExtraction, self, "this");
			
			if (null == methodDecl) {
				assert true;	// maybe can clean up all this code some day
				final RTDynamicScope currentScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
				if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
					// Then self is an object playing the Role (with rTType an instance of RTClass)
					// Get the current RTContext (must be in self of current activation record)
					final RTObject contextOfRoleOfInvokingMethod = RTExpression.getObjectUpToMethodScopeFrom("this", currentScope);;
					assert null != contextOfRoleOfInvokingMethod;
					
					// Also get the Context of the Role of the invoked method. IT IS OK
					// THAT IT NOT BE THE CURRENT CONTEXT, in the event that the
					// Role-player is itself a Context instance!
					assert typeOfThisParameterToMethod instanceof RoleType;
					
					final RoleType roleOfCallee = (RoleType)typeOfThisParameterToMethod;
					final Declaration rawTargetContext = roleOfCallee.contextDeclaration();
					assert rawTargetContext instanceof ContextDeclaration;
					final ContextDeclaration targetContext = (ContextDeclaration) rawTargetContext;
					final RTType rawrTTypeOfTargetContext = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(targetContext);
					assert rawrTTypeOfTargetContext instanceof RTContext;
					final RTContext rTTypeOfTargetContext = (RTContext)rawrTTypeOfTargetContext;
					
					final RTRole theRole = rTTypeOfTargetContext.getRole(typeOfThisParameterToMethod.name());
					if (null == theRole) {
						final RTStageProp theStageProp = rTTypeOfTargetContext.getStageProp(typeOfThisParameterToMethod.name());
						if (null == theStageProp) {
							assert false;
						} else {
							methodDecl = theStageProp.lookupMethod(methodSelectorName_, actualParameters_);
						}
					} else {
						methodDecl = theRole.lookupMethod(methodSelectorName_, actualParameters_);
					}
					
					if (null == methodDecl) {
						methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
								typeOfThisParameterToMethod);
					}
				} else {
					methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
				}
				
				if (null == methodDecl) {
					assert null != methodDecl;
				}
			}
			return methodDecl;
		}
	}
	
	private static class RTRoleToRole extends RTMessageDispatcher {
		public RTRoleToRole(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);
			
			final int indexForThisExtraction = ((Expression)actualParameters_.argumentAtPosition(0)).name().equals("current$context")? 1: 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];

			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					
					// Sure. A Context object can play a Role
					// assert self instanceof RTContextObject == false;
					
					// This need not be true — could be the RolePlayer type
					// assert self instanceof RTRole == true;
				}
				commonWrapup(typeOfThisParameterToCalledMethod, indexForThisExtraction, self, messageExpr.isPolymorphic());
			}
		}
		
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			RTMethod methodDecl = getRoleMethodDecl(typeOfThisParameterToMethod, indexForThisExtraction, self, "current$context");
			
			if (null == methodDecl) {
				assert true;
				// It's a Role-to-Role call, but a non-compliant one
				// (probably) invoking one of the methods in the "requires"
				// section of the target
				if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
					methodDecl = noncompliantRoleMethodDeclLookup(typeOfThisParameterToMethod, indexForThisExtraction, self);
				} else {
					assert true;	// should be able to get rid of this code?  nope.
					methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
				}
			}
			
			// Might be null
			return methodDecl;
		}
	}
	
	private static class RTRoleToClass extends RTMessageDispatcher {
		public RTRoleToClass(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);
			final int indexForThisExtraction = 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			
			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					if (self instanceof RTContextObject) {
						// If an object is playing a Role and if someone is
						// invoking a Requires script directly (instead of
						// through the Published interface), its actually
						// possible that the Role-player is a Context.
						// Adjust this later.
					} else if (self instanceof RTRole) {
						assert self instanceof RTRole == false;
					}
				}
				commonWrapup(typeOfThisParameterToCalledMethod, 0, self, messageExpr.isPolymorphic());
			}
		}
		
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			RTMethod methodDecl = null;

			if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
				assert false;	// can't be (empirical)
			} else {
				methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
			}
			
			return methodDecl;
		}
	}
	
	private static class RTRoleToContext extends RTMessageDispatcher {
		public RTRoleToContext(final MessageExpression messageExpr,
				final String methodSelectorName,
				final RTCode argPush,
				final RTPostReturnProcessing postReturnProcessing,
				final int [] expressionsCountInArguments,
				final ActualArgumentList actualParameters,
				final boolean isStatic,
				final RTType nearestEnclosingType) {
			super(messageExpr,
					methodSelectorName,
					argPush,
					postReturnProcessing,
					expressionsCountInArguments,
					actualParameters,
					isStatic,
					nearestEnclosingType);
			
			final int indexForThisExtraction = ((Expression)actualParameters_.argumentAtPosition(0)).name().equals("current$context")? 1: 0;
			final int expressionCounterForThisExtraction = expressionsCountInArguments[indexForThisExtraction];
			
			RTObject self = null;
			RTCode start = argPush_;

			// Now push the arguments onto the stack
			final Type typeOfThisParameterToCalledMethod = super.commonProlog(indexForThisExtraction, expressionCounterForThisExtraction);
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
					// self could be anything at this point — a Role,
					// or a RolePlayer including RTObjectCommon type
					// or RTContextObject. But it's an RTObject...
				}
				final int newIndexForThisExtraction = ((Expression)actualParameters_.argumentAtPosition(0)).name().equals("current$context")? 1: 0;
				commonWrapup(typeOfThisParameterToCalledMethod, newIndexForThisExtraction, self, messageExpr.isPolymorphic());
			}
		}
		
		protected RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
			// Should look up method in the receiver's scope. Get the
			// current scope so we can get all the magic identifiers
			// (this, current$context, etc.)
			RTMethod methodDecl = getRoleMethodDecl(typeOfThisParameterToMethod, indexForThisExtraction, self, "current$context");

			if (null == methodDecl) {
				assert true;
				if (typeOfThisParameterToMethod instanceof RoleType && 1 == indexForThisExtraction) {	// a guess...
					assert false;	// is a Context; can't be a Role
				} else {
					methodDecl = genericMethodDeclLookup(typeOfThisParameterToMethod, self, true);
				}
			}
			
			if (null == methodDecl) {
				assert null != methodDecl;
			}
			return methodDecl;
		}
	}
	
	protected       RTMethod methodDecl_;
	protected final Token token_;
	protected final MessageExpression messageExpr_;
	protected final String methodSelectorName_;
	protected final RTCode argPush_;
	protected final RTDynamicScope currentScope_;
	protected final RTPostReturnProcessing postReturnProcessing_;
	protected final int [] expressionsCountInArguments_;
	protected final ActualArgumentList actualParameters_;
	protected final boolean isStatic_;
	protected       boolean isBuiltInAssert_;
	protected       RTCode hasError_;
	protected final RTType nearestEnclosingType_;
	protected       int startingStackIndex_;
	protected final RTType rTListOfObjectType_;
	protected final RTType rTObjectType_;
}
