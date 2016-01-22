package info.fulloo.trygve.run_time;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
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
			final RTType nearestEnclosingType
			) {
		return new RTNonContextToNonContext(
				messageExpr,
				methodSelectorName,
				argPush,
				postReturnProcessing,
				expressionsCountInArguments,
				actualParameters,
				isStatic,
				nearestEnclosingType
				);
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
		lineNumber_ = messageExpr_.lineNumber();
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
	}
	
	protected abstract RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self);
	
	public RTMethod methodDecl() {
		return methodDecl_;
	}
	
	public int lineNumber() {
		return lineNumber_;
	}
	
	public RTCode hasError() {
		return hasError_;
	}
	
	/*
	private RTMethod getMethodDecl(final Type typeOfThisParameterToMethod, final int indexForThisExtraction, final RTObject self) {
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
				// Major refactoring in order. TODO.
				methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
						typeOfThisParameterToMethod);
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
				// assert null != rTTypeOfSelf;
			}
			
			final ClassType classType = typeOfThisParameterToMethod instanceof ClassType? (ClassType)typeOfThisParameterToMethod: null;
			TemplateInstantiationInfo templateInstantiationInfo = null == classType? null: classType.templateInstantiationInfo();
			
			if (null == templateInstantiationInfo) {
				RTClass nearestEnclosingType_;
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
	*/

	protected RTStackable pushArgumentLoop(final RTCode start, final int expressionCounterForThisExtraction,
			final int indexForThisExtraction) {
		RTCode pc = start;
		final int startingStackIndex = RunTimeEnvironment.runTimeEnvironment_.stackIndex();
		RTObject self = null;
		while (null != pc) {
			// This evaluation leaves a result on the stack -
			// a result which will be a parameter to the method
			
			// Make sure it doesn't get popped. Setting things in ActualParameters
			// doesn't seem to be enough (setResultIsConsumed(true))
			if (pc instanceof RTExpression) {
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
		
		if (false == isStatic_ && RunTimeEnvironment.runTimeEnvironment_.stackIndex() <= startingStackIndex + indexForThisExtraction) {
			assert RunTimeEnvironment.runTimeEnvironment_.stackIndex() > startingStackIndex + indexForThisExtraction;
		}
		
		self = isStatic_? null:
			(RTObject)RunTimeEnvironment.runTimeEnvironment_.stackValueAtIndex(startingStackIndex + indexForThisExtraction);

		assert null != self || isStatic_;
		return self;
	}
	
	protected void populateActivationRecord(final RTMethod methodDecl, final RTDynamicScope activationRecord) {
		final FormalParameterList formalParameters = methodDecl.formalParameters();
		for (int i = formalParameters.count() - 1; 0 <= i; --i) {
			final ObjectDeclaration ithParameter = formalParameters.parameterAtPosition(i);
			final String ithParameterName = ithParameter.name();
			final RTType rTIthParameterType = null; // InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(ithParameter.type());
			activationRecord.addObjectDeclaration(ithParameterName, rTIthParameterType);
			final RTStackable rawArgument = RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (rawArgument instanceof RTObject == false) {
				assert rawArgument instanceof RTObject;
			}
			final RTObject anArgument = (RTObject)rawArgument;
			assert null != anArgument;
			activationRecord.setObject(ithParameterName, anArgument);
			anArgument.decrementReferenceCount();	// not on the stack any more
		}
		if (this.isBuiltInAssert_) {
			// Put the line number in the activation record
			final RTIntegerObject lineNumberToPush = new RTIntegerObject(lineNumber_);
			activationRecord.addObjectDeclaration("lineNumber", null);
			activationRecord.setObject("lineNumber", lineNumberToPush);
		}
	}
	
	protected void commonWrapup(final Type typeOfThisParameterToCalledMethod,
			final int indexForThisExtraction,
			final RTObject self) {
		// Get the method declaration by looking it up in the receiver's scope
		// Null return on error (e.g., attempting to invoke a method on a null object)
		methodDecl_ = this.getMethodDecl(typeOfThisParameterToCalledMethod, indexForThisExtraction, self);
		
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
			final RTDynamicScope activationRecord = new RTDynamicScope(methodDecl_.name(), RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
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
			
			RTCode start = argPush_;
			RTObject self = null;
			
			// Push the return address onto the stack
			RunTimeEnvironment.runTimeEnvironment_.pushStack(postReturnProcessing_);
			RunTimeEnvironment.runTimeEnvironment_.setFramePointer();
			
			// Now push the arguments onto the stack

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
			
			// This loop just processes the pushing of the arguments
			// The value of "pc" will eventually return null - there
			// is no link to subsequent code
			final RTStackable tempSelf = this.pushArgumentLoop(start, expressionCounterForThisExtraction, indexForThisExtraction);
			
			if (tempSelf instanceof RTHalt) {
				hasError_ = (RTHalt)tempSelf;
			} else {
				if (tempSelf instanceof RTObject) {
					self = (RTObject)tempSelf;
				}
				
				commonWrapup(typeOfThisParameterToCalledMethod, 0, self);
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
					// Major refactoring in order. TODO.
					methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
							typeOfThisParameterToMethod);
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
							methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
							if (null == methodDecl) {
								methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
							}
						}
						if (null == methodDecl) {
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
					// Major refactoring in order. TODO.
					methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
							typeOfThisParameterToMethod);
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
							methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
							if (null == methodDecl) {
								methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
							}
						}
						if (null == methodDecl) {
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
					// Major refactoring in order. TODO.
					methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
							typeOfThisParameterToMethod);
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
							methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
							if (null == methodDecl) {
								methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
							}
						}
						if (null == methodDecl) {
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
					// Major refactoring in order. TODO.
					methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
							typeOfThisParameterToMethod);
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
							methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
							if (null == methodDecl) {
								methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
							}
						}
						if (null == methodDecl) {
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
					// Major refactoring in order. TODO.
					methodDecl = contextMethodDeclLookup(contextOfRoleOfInvokingMethod,
							typeOfThisParameterToMethod);
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
							methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterAtPosition(methodSelectorName, actualParameters, 0);
							if (null == methodDecl) {
								methodDecl = rTTypeOfSelf.lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(methodSelectorName, actualParameters, 0);
							}
						}
						if (null == methodDecl) {
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
	
	protected       RTMethod methodDecl_;
	protected final int lineNumber_;
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
}
