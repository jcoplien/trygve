package parser;

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
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.Token;

import parser.KantParser.Method_decl_hookContext;
import parser.KantParser.Stageprop_bodyContext;
import parser.KantParser.Stageprop_declContext;
import parser.Pass1Listener;
import parser.KantParser.Class_bodyContext;
import parser.KantParser.Method_declContext;
import parser.KantParser.Method_signatureContext;
import parser.KantParser.ProgramContext;
import parser.KantParser.Role_bodyContext;
import parser.KantParser.Role_declContext;
import parser.KantParser.Type_declarationContext;
import declarations.Type;
import declarations.Declaration;
import declarations.Declaration.ContextDeclaration;
import declarations.Declaration.ClassDeclaration;
import declarations.Declaration.ExprAndDeclList;
import declarations.Declaration.MethodSignature;
import declarations.Declaration.RoleDeclaration;
import declarations.Declaration.MethodDeclaration;
import declarations.Declaration.ObjectDeclaration;
import declarations.Declaration.StagePropDeclaration;
import declarations.Declaration.TemplateDeclaration;
import declarations.Type.ArrayType;
import declarations.Type.ClassType;
import declarations.Type.RoleType;
import declarations.ActualOrFormalParameterList;
import declarations.Message;
import declarations.ActualArgumentList;
import declarations.FormalParameterList;
import declarations.Type.ContextType;
import declarations.Type.StagePropType;
import declarations.TypeDeclaration;
import semantic_analysis.StaticScope;
import error.ErrorLogger;
import error.ErrorLogger.ErrorType;
import expressions.Expression;
import expressions.Expression.ArrayExpression;
import expressions.Expression.ArrayIndexExpression;
import expressions.Expression.ArrayIndexExpressionUnaryOp;
import expressions.Expression.AssignmentExpression;
import expressions.Expression.QualifiedClassMemberExpression;
import expressions.Expression.QualifiedIdentifierExpression;
import expressions.Expression.MessageExpression;
import expressions.Expression.NullExpression;
import expressions.Expression.IdentifierExpression;
import expressions.Expression.UnaryopExpressionWithSideEffect;
import expressions.ExpressionStackAPI;


public class Pass2Listener extends Pass1Listener {
	public Pass2Listener(ParsingData parsingData) {
		super(parsingData);
		
		currentScope_ = parsingData_.globalScope();
		currentContext_ = null;
	}
	
	// Here's where Contexts are created
	
	@Override protected ClassDeclaration lookupOrCreateNewClassDeclaration(String name, StaticScope newScope, ClassDeclaration rawBaseClass, int lineNumber) {
		return currentScope_.lookupClassDeclarationRecursive(name);
	}
	
	@Override protected void createNewClassTypeSuitableToPass(ClassDeclaration newClass, String name, StaticScope newScope, ClassType baseType) {
	}
	@Override protected void createNewTemplateTypeSuitableToPass(TemplateDeclaration newClass, String name, StaticScope newScope, ClassType baseType) {
	}

	@Override protected void lookupOrCreateRoleDeclaration(String roleName, int lineNumber) {
		// Return value is through currentRole_
		currentRole_ = currentScope_.lookupRoleDeclarationRecursive(roleName);
		if (null == currentRole_) {
			assert null != currentRole_;
		}
	}
	@Override protected void lookupOrCreateStagePropDeclaration(String stagePropName, int lineNumber) {
		// Return value is through currentRole_
		currentRole_ = currentScope_.lookupRoleDeclarationRecursive(stagePropName);
		if (null == currentRole_) {
			assert null != currentRole_;
		}
	}
	
	@Override public void enterMethod_decl(@NotNull KantParser.Method_declContext ctx)
	{
		//  : method_decl_hook '{' expr_and_decl_list '}'
		// This is our own pass 2 version
		
		// Just for reference:
		// 
		// 		method_decl_hook : method_signature

		
		// There will be a (potentially null) method body. Set up the
		// ExprAndDeclList to receive it.
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart().getLine());
		parsingData_.pushExprAndDecl(newList);
		
		final Method_decl_hookContext declHookContext = ctx.method_decl_hook();
		final Method_signatureContext signatureCtx = declHookContext.method_signature();
		final String methodSelector = signatureCtx.method_name().getText();
		assert null != methodSelector;
		
		// Let's say that we overload method X in some scope.
		// Every time through this production, for each declaration,
		// it will return the same declaration (let's say, the first
		// one) and the give it a parameter list.... The second and
		// subsequent entries will never be set up. We fix it by
		// looking up the method according to its place in the source.
		// Crude but effective.
		final MethodDeclaration currentMethod = currentScope_.lookupMethodDeclarationRecursiveWithLineNumber(
				methodSelector,
				ctx.start.getLine());
		assert null != currentMethod;
		
		final FormalParameterList pl = new FormalParameterList();
		
		// Give the method a parameter list
		// This overwrites the one that we gave it in
		// Pass 1 and is likely to be better here in Pass 2,
		// and maybe even better in Pass 3
		currentMethod.addParameterList(pl);
		
		currentScope_ = currentMethod.enclosedScope();
		parsingData_.pushFormalParameterList(pl);
	}

	@Override public void exitMethod_decl(@NotNull KantParser.Method_declContext ctx)
	{
		//  : method_decl_hook '{' expr_and_decl_list '}'
		// Declare parameters in the new scope
		// This is definitely a Pass2 thing
		final MethodSignature signature = parsingData_.popMethodSignature();

		MethodDeclaration currentMethod = currentScope_.lookupMethodDeclarationRecursive(signature.name(),
				signature.formalParameterList(), false);
		if (null == currentMethod) {
			// It could be because the signatures don't match. Try patching up the signature
			// of a leftover current method with a jimmied signature. We'll have to fix
			// this someday to support overloading. (But it seems to work now Ñ probably O.K.)
			currentMethod = currentScope_.lookupMethodDeclarationRecursive(signature.name(),
					parsingData_.currentFormalParameterList(), false);
			assert null != currentMethod;
		}
		assert null != currentMethod;
		
		// +++++++++++++++++++++++++
		final Type returnType = signature.returnType();
		currentMethod.setReturnType(returnType);
		if (null != returnType) {
			final String returnTypeName = returnType.getText();
			if (null == currentScope_.lookupTypeDeclarationRecursive(returnTypeName)) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Return type ", returnTypeName, " not declared for ",
						currentMethod.name());
			} else {
				currentMethod.setReturnType(returnType);
			}
		} else {
			final StaticScope currentScope = currentMethod.enclosedScope();
			final StaticScope parentScope = currentScope.parentScope();
			final Declaration otherAssociatedDeclaration = parentScope.associatedDeclaration();
			if (otherAssociatedDeclaration instanceof ContextDeclaration) {
				if (currentMethod.name().equals(otherAssociatedDeclaration.name())) {
					; // then O.K. Ñ constructor
				} else {
					errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Return type not declared for ", currentMethod.name(), "", "");
				}
			} else if (otherAssociatedDeclaration instanceof ClassDeclaration) {
				if (currentMethod.name().equals(otherAssociatedDeclaration.name())) {
					; // then O.K. Ñ constructor
				} else {
					final TemplateDeclaration templateDeclaration = ((ClassDeclaration)otherAssociatedDeclaration).generatingTemplate();
					if (null != templateDeclaration) {
						if (currentMethod.name().equals(templateDeclaration.name())) {
							// o.k. - constructors on templates don't include parameter names
						} else {
							errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Return type not declared for template method ", currentMethod.name(), "", "");
						}
					} else {
						errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Return type not declared for class method ", currentMethod.name(), "", "");
					}
				}
			} else if (otherAssociatedDeclaration instanceof TemplateDeclaration) {
				if (currentMethod.name().equals(otherAssociatedDeclaration.name())) {
					; // then O.K. Ñ constructor
				} else {
					errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Return type not declared for template method ", currentMethod.name(), "", "");
				}
			} else {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Bad declaration of ", currentMethod.name(), "", "");
			}
		}
		// +++++++++++++++++++++++++
		
		final StaticScope parentScope = currentScope_.parentScope();
		currentScope_ = parentScope;
		
		@SuppressWarnings("unused")
		final FormalParameterList pl = parsingData_.popFormalParameterList();	// hope this is the right place
		
		final int lastLineNumber = ctx.getStop().getLine();
		@SuppressWarnings("unused")
		final ReturnStatementAudit audit = new ReturnStatementAudit(currentMethod.returnType(), parsingData_.currentExprAndDecl(), lastLineNumber, this);
		this.setMethodBodyAccordingToPass(currentMethod);
	}
	
	protected void setMethodBodyAccordingToPass(MethodDeclaration unused)
	{
		/* Nothing. Just clean up the data structures. */
		@SuppressWarnings("unused")
		final ExprAndDeclList body = parsingData_.popExprAndDecl();
	}
	@Override public void enterRole_decl(@NotNull KantParser.Role_declContext ctx)
	{
		// : 'role' JAVA_ID '{' role_body '}'
		// | 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		super.enterRole_decl(ctx);
		this.processRequiredDeclarations(ctx.getStart().getLine());
	}
	@Override public void enterStageprop_decl(@NotNull KantParser.Stageprop_declContext ctx)
	{
		// : 'stageprop' JAVA_ID '{' role_body '}'
		// | 'stageprop' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' role_body '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		super.enterStageprop_decl(ctx);
		this.processRequiredDeclarations(ctx.getStart().getLine());
	}
	
	protected void processRequiredDeclarations(int lineNumber)
	{
		currentRole_.processRequiredDeclarations(lineNumber);
	}
	
	@Override public void enterArgument_list(@NotNull KantParser.Argument_listContext ctx)
	{
	}
	@Override public void exitArgument_list(@NotNull KantParser.Argument_listContext ctx)
	{
		// Yes Ñ this work belongs in Pass 2
		if (ctx.expr() != null) {
			final Expression expr = parsingData_.popExpression();
			expr.setResultIsConsumed(true);
			currentArgumentList().addActualArgument(expr);
		} else {
			// no actual argument Ñ OK
		}
	}

	@Override public void exitMessage(@NotNull KantParser.MessageContext ctx)
	{
		// JAVA_ID '(' argument_list ')'
		// Certified Pass 2 version ;-)
		final String selectorName = ctx.JAVA_ID().getText();
		final long lineNumber = ctx.getStart().getLine();
		
		// This is definitely Pass 2 stuff.
		final ActualArgumentList argumentList = parsingData_.popArgumentList();
		
		// All arguments are evaluated and are pushed onto the stack
		for (int i = 0; i < argumentList.count(); i++) {
			final Expression argument = (Expression)argumentList.argumentAtPosition(i);
			assert null != argument & argument instanceof Expression;
			argument.setResultIsConsumed(true);
		}
		
		final Message newMessage = new Message(selectorName, argumentList, lineNumber);
		parsingData_.pushMessage(newMessage);
	}
	
	protected Expression processIndexExpression(Expression rawArrayBase, Expression indexExpr) {
		Expression expression = null;
		
		// On pass one, types may not yet be set up so we may
		// stumble here (particularly if there is a forward reference
		// to a type). Here on pass 2 we're a bit more anal
		final ArrayType arrayType = (ArrayType)rawArrayBase.type();
		assert arrayType instanceof ArrayType;
		final Type baseType = arrayType.baseType();
		final ArrayExpression arrayBase = new ArrayExpression(rawArrayBase, baseType);
		arrayBase.setResultIsConsumed(true);
		expression = new ArrayIndexExpression(arrayBase, indexExpr);
		return expression;
	}
	
	@Override protected void checkExprDeclarationLevel(RuleContext ctxParent, Token ctxGetStart) {
		// Certified Pass 2 version :-)
		RuleContext executionContext = ctxParent;
		while ((executionContext instanceof ProgramContext) == false) {
			if (executionContext instanceof Method_declContext) {
				break;
			} else if (executionContext instanceof Stageprop_bodyContext) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in stageprop scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Role_bodyContext) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in Role scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Stageprop_declContext) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in stageprop scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Role_declContext) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in Role scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Class_bodyContext) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in Class scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Type_declarationContext) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in a global program element scope: it must be in a method", "", "", "");
				break;
			}
			executionContext = executionContext.parent;
		}
	}

	
	@Override public void binopTypeCheck(final Expression leftExpr, String operationAsString,
			final Expression rightExpr, Token ctxGetStart) {
		// Certified Pass 2 version ;-)
		final Type resultType = leftExpr.type();
		if (resultType.canBeConvertedFrom(rightExpr.type()) == false) {
			errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Invalid operands to `" +
					"", operationAsString, "' on type ", leftExpr.type().name(),
					" with operand of type ", rightExpr.type().name());
		}
		final ActualArgumentList argList = new ActualArgumentList();
		argList.addActualArgument(rightExpr);
		final Expression self = new IdentifierExpression("t$his", resultType, resultType.enclosedScope());
		argList.addActualArgument(self);
		final MethodDeclaration mdecl = resultType.enclosedScope().lookupMethodDeclaration(operationAsString, argList, false);
		if (null == mdecl) {
			errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "No such operation '", operationAsString, "' on type ",
					resultType.name(), " for argument ", rightExpr.type().name());
		}
	}
	
	// ------------------------------------------------------------------------------------------------------- 
	public ExpressionStackAPI exprFromExprDotJavaId(TerminalNode ctxJAVA_ID, Token ctxGetStart) {
		// : expr '.' JAVA_ID
		// Pop the expression for the indicated object and message
		// Certified Pass 2 version ;-)

		Type type = null;
		final ExpressionStackAPI qualifier = parsingData_.popRawExpression();
		Expression expression = null;
		final String javaIdString = ctxJAVA_ID.getText();
		
		if (null != qualifier && null != qualifier.type() && qualifier.type().name().equals("Class")) {
			// This is where we handle types like "System" for System.out.print*
			// Now we need to get the actual class of that name
			final ClassType theClass = (ClassType)currentScope_.lookupTypeDeclarationRecursive(qualifier.name());
			assert theClass instanceof ClassType;
			
			final ObjectDeclaration odecl = theClass.type().enclosedScope().lookupObjectDeclaration(javaIdString);
			if (odecl.type() != null) type = odecl.type();
			expression = new QualifiedClassMemberExpression(theClass, javaIdString, type);
		} else {
			final ObjectDeclaration odecl = qualifier.type().enclosedScope().lookupObjectDeclarationRecursive(javaIdString);
		
			if (null == odecl) {
				final MethodDeclaration javaId2 = qualifier.type().enclosedScope().lookupMethodDeclarationRecursive(javaIdString, null, true);
				if (null == javaId2) {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Identifier ", javaIdString, " not declared for object ", qualifier.name());
					type = StaticScope.globalScope().lookupTypeDeclaration("void");
				} else {
					type = javaId2.returnType();
				}
			} else {
				type = odecl.type();
				assert type != null;
			}

			assert qualifier instanceof Expression;
			expression = new QualifiedIdentifierExpression((Expression)qualifier, javaIdString, type);
		}
		
		assert null != expression;
		
		return expression;
    }
	@Override public void ctorCheck(Type type, Message message, int lineNumber) {
		// Is there a constructor?
		// We're not ready for this until Pass 2
		final String className = message.selectorName();
		final ActualArgumentList actualArgumentList = message.argumentList();
		if (null != type) {
			// Under error conditions we sometimes get a null type parameter
			final StaticScope declarationScope = type.enclosedScope();
			final MethodDeclaration constructor = declarationScope.lookupMethodDeclaration(type.name(), actualArgumentList, false);
			if (null != actualArgumentList && 1 < actualArgumentList.count()) {
				// So the "new" message actually had arguments, which means
				// it's expecting a constructor
				if (null == constructor) {
					errorHook5p2(ErrorType.Fatal, lineNumber, "No matching constructor on class ", className, " for `new« invocation", "");
				}
			} else if (null != actualArgumentList && 1 == actualArgumentList.count()) {
				// Could be that just the t$his argument is in the
				// actual argument list.
				message.setArgumentList(new ActualArgumentList());
			}
		}
	}
	public void addSelfAccordingToPass(Type type, Message message, StaticScope scope) {
		// Apparently called only for constructor processing.
		// The simple part. Add this.
		final Expression self = new IdentifierExpression("t$his", type, scope);
		message.addActualThisParameter(self);
	}
	
	@Override public <ExprType> Expression messageSend(Token ctxGetStart, ExprType ctxExpr) {
		// | expr '.' message
		// | message
		// Certified Pass 2 version. Can maybe be folded with pass 1....
		
		Expression object = null, retval = null;
		final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		
		// Pop the expression for the indicated object and message
		if (ctxExpr != null) {
			object = parsingData_.popExpression();
		} else {
			object = new IdentifierExpression("this", nearestEnclosingMegaType, nearestMethodScope);
		}
		object.setResultIsConsumed(true);
						
		Type objectType = object.type();
		if (null == objectType) {
			objectType = parsingData_.globalScope().lookupTypeDeclaration("Object");
		}
		assert null != objectType;
									
		final Message message = parsingData_.popMessage();
		message.addActualThisParameter(object);
		
		MethodSignature methodSignature = null;
		
		if (objectType instanceof RoleType || objectType instanceof StagePropType) {
			Type wannabeContextType = nearestEnclosingMegaType;
			if (wannabeContextType instanceof RoleType) {
				RoleType nearestEMT = (RoleType) nearestEnclosingMegaType;
				wannabeContextType = Expression.nearestEnclosingMegaTypeOf(nearestEMT.enclosingScope());
				assert wannabeContextType instanceof ContextType;
			}
			
			// Look this thing up in the "required" interface to see
			// if it's really a role method or just a latently bound
			// instance method in an object bound to this role
			final RoleType roleType = (RoleType)objectType;
			methodSignature = roleType.lookupMethodSignatureDeclaration(message.selectorName());
			if (null != methodSignature) {
				// Then it's in the "required" declarations and is NOT a role method per se
			} else {
				final Expression currentContext = new IdentifierExpression("current$context", wannabeContextType, nearestMethodScope);
				message.argumentList().addFirstActualParameter(currentContext);
				currentContext.setResultIsConsumed(true);
				
				// NOTE: Leaves methodSignature null.
				// We need it for call of checkForMessageSendViolatingConstness below.
				final MethodDeclaration methodDecl = objectType.enclosedScope().lookupMethodDeclaration(message.selectorName(), message.argumentList(), false);
				methodSignature = methodDecl.signature();
			}
		} else if (objectType instanceof ClassType) {
			final ClassType classObjectType = (ClassType) objectType;
			final MethodDeclaration methodDeclaration = classObjectType.enclosedScope().lookupMethodDeclarationRecursive(
					message.selectorName(), message.argumentList(), false);
			if (null == methodDeclaration) {
				// Mainly for error recovery (bad argument to method)
				return null;		// punt
			} else {
				methodSignature = methodDeclaration.signature();
			}
		} else if (objectType instanceof ContextType) {
			final ContextType contextObjectType = (ContextType) objectType;
			final MethodDeclaration methodDeclaration = contextObjectType.enclosedScope().lookupMethodDeclarationRecursive(
					message.selectorName(), message.argumentList(), false);
			methodSignature = methodDeclaration.signature();
		}
		
		final Type returnType = this.processReturnType(ctxGetStart, object, objectType, message);
		
		if (objectType.name().equals(message.selectorName())) {
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot 'call' constructor of ", objectType.name(), ". Use 'new' instead.", "");
		}
		
		assert null != returnType;
		assert null != object;
		assert null != message;
		
		message.setReturnType(returnType);
		
		if (null != methodSignature) {
			checkForMessageSendViolatingConstness(methodSignature, ctxGetStart);
			retval = new MessageExpression(object, message, returnType, ctxGetStart.getLine());
		} else {
			// Stumble elegantly
			retval = new NullExpression();
		}
		
		return retval;
	}
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationIn(TypeDeclaration classDecl, String methodSelectorName, ActualOrFormalParameterList parameterList) {
		// Pass 2 / 3 version turns on signature checking
		return classDecl.enclosedScope().lookupMethodDeclarationIgnoringParameter(methodSelectorName, parameterList, "this");
	}
	
	@Override protected void typeCheck(FormalParameterList formals, ActualArgumentList actuals,
			MethodDeclaration mdecl, TypeDeclaration classdecl, @NotNull Token ctxGetStart) {
		final long numberOfActualParameters = actuals.count();
		final long numberOfFormalParameters = formals.count();

		if (numberOfFormalParameters != numberOfActualParameters) {
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Number of arguments in call of method ", mdecl.name(),
					" does not match declaration of ", mdecl.name());
			int lineNumber = mdecl.lineNumber();
			if (0 == lineNumber) {
				// e.g., for a built-in type
				lineNumber = ctxGetStart.getLine();
			}
			errorHook5p2(ErrorType.Fatal, lineNumber, "\tMethod ", mdecl.name(), " is declared in ", classdecl.name());
		} else {
			for (int j = 0; j < numberOfActualParameters; j++) {
				final Expression actualParameter = (Expression)actuals.argumentAtPosition(j);
				assert actualParameter != null && actualParameter instanceof Expression;
				final Type actualParameterType = actualParameter.type();

				final ObjectDeclaration formalParameter = formals.parameterAtPosition(j);
				final Type formalParameterType = formalParameter.type();

				if (formalParameterType.canBeConvertedFrom(actualParameterType)) {
					continue;
				} else {
					final String actualParamMsg = actualParameter.getText() + " (" + actualParameterType.name() + ")";
					final String formalParamMsg = "`" + formalParameter.name() + "« (" + formalParameterType.name() + " " + formalParameter.name() + ")";
					errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Type of actual parameter ", actualParamMsg,
							" in call of `", mdecl.name(), "« does not match type of formal parameter ", formalParamMsg);
				}
			}
		}
	}

	@Override public Expression idExpr(TerminalNode ctxJAVA_ID, Token ctxGetStart) {
		// | JAVA_ID
		// Special version for pass 2 and 3
		Type type = null;
		Expression retval = null;
		RoleDeclaration aRoleDecl = null;
		StaticScope declaringScope = null;
		final String idText = ctxJAVA_ID.getText();
		final ObjectDeclaration objdecl = currentScope_.lookupObjectDeclarationRecursive(idText);
		if (null != objdecl) {
			type = objdecl.type();
			declaringScope = objdecl.enclosingScope();
			final StaticScope globalScope = StaticScope.globalScope();
			final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
			StaticScope megaTypeScope = null;
			if (null != enclosingMegaType) {
				megaTypeScope = enclosingMegaType.enclosedScope();
			} else {
				megaTypeScope = globalScope;
			}
			
			if (declaringScope == megaTypeScope) {
				// Then it's a member of an object of the current class / context
				// Probably better to make it a qualified identifier
				final StaticScope enclosingMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
				final Declaration associatedDeclaration = declaringScope.associatedDeclaration();
				final IdentifierExpression self = new IdentifierExpression("this", associatedDeclaration.type(), enclosingMethodScope);
				retval = new QualifiedIdentifierExpression(self, idText, type);
			} else {
				retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope);
			}
			assert null != retval;
		} else if (null != currentScope_.lookupClassDeclarationRecursive(idText)) {
			// Could be a reference to a class itself (like System)
			type = StaticScope.globalScope().lookupTypeDeclaration("Class");
			declaringScope = StaticScope.globalScope();
			retval = new IdentifierExpression(idText, type, declaringScope);
		} else if (null != (aRoleDecl = super.isRoleAssignmentWithinContext(idText))) {
			type = aRoleDecl.type();
			declaringScope = aRoleDecl.enclosingScope();
			retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope);
		} else {
			final StaticScope possibleProcedureScope = currentScope_;
			final StaticScope possibleRoleScope = null == possibleProcedureScope? null: possibleProcedureScope.parentScope();
			final StaticScope possibleContextScope = null ==  possibleRoleScope? null: possibleRoleScope.parentScope();
			final Declaration associatedDeclaration = null == possibleContextScope? null: possibleContextScope.associatedDeclaration();
			if (associatedDeclaration == currentContext_) {
				if (null != possibleContextScope) {
					final RoleDeclaration roleDecl = possibleContextScope.lookupRoleDeclaration(idText);
					if (null == roleDecl) {
						errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Object ", idText, " is not declared in scope ", currentScope_.name());
						type = StaticScope.globalScope().lookupTypeDeclaration("void");
					} else {
						// it's O.K. Ñ maybe. Can be used as an L-value in an assignment. R-value, too, I guess
						type = possibleContextScope.lookupTypeDeclaration(idText);
						declaringScope = roleDecl.enclosingScope();
					}
				} else {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Object ", idText, " is not declared in scope ", currentScope_.name());
					type = StaticScope.globalScope().lookupTypeDeclaration("void");
				}
			} else {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Object ", idText, " is not declared in scope ", currentScope_.name());
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
			assert null != type;
			retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope);
		}
		assert null != retval;
		return retval;
    }
	@Override protected ContextDeclaration lookupOrCreateContextDeclaration(String name, int lineNumber) {
		final ContextDeclaration contextDecl = currentScope_.lookupContextDeclarationRecursive(name);
		assert null != contextDecl;  // maybe turn into an error message later
		currentScope_ = contextDecl.enclosedScope();
		assert null != currentScope_;	// maybe turn into an error message later
		return contextDecl;
	}
	@Override protected ClassDeclaration lookupOrCreateClassDeclaration(String name, ClassDeclaration rawBaseClass, ClassType baseType, int lineNumber) {
		final ClassDeclaration newClass = currentScope_.lookupClassDeclarationRecursive(name);
		final ClassType classType = (ClassType)newClass.type();
		assert classType instanceof ClassType;
		classType.updateBaseType(baseType);
		return newClass;
	}
	@Override  protected TemplateDeclaration lookupOrCreateTemplateDeclaration(String name, TypeDeclaration rawBaseType, Type baseType, int lineNumber) {
		final TemplateDeclaration newTemplate = currentScope_.lookupTemplateDeclarationRecursive(name);
		return newTemplate;
	}
	@Override protected void declareTypeSuitableToPass(StaticScope scope, Type decl) {
		/* Nothing */
	}
	@Override protected void declareObjectSuitableToPass(StaticScope scope, ObjectDeclaration objDecl) {
		if (scope.hasDeclarationsThatAreLostBetweenPasses()) {
			// e.g., a FOR Loop or a Block
			scope.declareObject(objDecl);
		} else {
			// most of the time...
			; 		/* Nothing */
		}
	}
	@Override protected void declareFormalParametersSuitableToPass(StaticScope scope, ObjectDeclaration objDecl) {
		scope.reDeclareObject(objDecl);
	}
	@Override protected void errorHook5p1(ErrorType errorType, int i, String s1, String s2, String s3, String s4) {
		/* Nothing */
	}
	@Override protected void errorHook6p1(ErrorType errorType, int i, String s1, String s2, String s3, String s4, String s5, String s6) {
		/* Nothing */
	}	
	@Override public void errorHook5p2(ErrorType errorType, int i, String s1, String s2, String s3, String s4) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4);
	}
	@Override protected void errorHook6p2(ErrorType errorType, int i, String s1, String s2, String s3, String s4, String s5, String s6) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4, s5, s6);
	}
	@Override protected void updateInitializationLists(Expression initializationExpr, ObjectDeclaration objDecl) {
		// It actually is right that one of these is an add and one is an insert...
		initializationExpressions_.add(initializationExpr);
		variablesToInitialize_.insertAtStart(objDecl);
	}
	@Override protected void reportMismatchesWith(int lineNumber, RoleType lhsType, Type rhsType) {
		lhsType.reportMismatchesWith(lineNumber, rhsType);
	}
	
	@Override protected void checkForAssignmentViolatingConstness(AssignmentExpression assignment, Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			final Expression assignee = assignment.lhs();
			checkLhsForAssignmentViolatingConstness(assignee, enclosingMethod, ctxGetStart);
		}
	}
	
	@Override protected void checkForIncrementOpViolatingConstness(ArrayIndexExpressionUnaryOp expression, Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			// We can have no idea where the array base is "pointing," so we have
			// to deny such expressions
			errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(),
					"Modification of array ", expression.getText(),
					" from within const method ",
					enclosingMethod.name(), ", which violates the const modifier of the latter.", "");
		}
	}
	
	@Override protected void checkForIncrementOpViolatingIdentifierConstness(UnaryopExpressionWithSideEffect id, Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			checkLhsForAssignmentViolatingConstness(id.lhs(), enclosingMethod, ctxGetStart);
		}
	}
	
	private void checkLhsForAssignmentViolatingConstness(Expression assignee, MethodDeclaration enclosingMethod, Token ctxGetStart) {
		if (assignee instanceof IdentifierExpression) {
			final Declaration idDecl = currentScope_.lookupObjectDeclarationRecursiveWithinMethod(assignee.name());
			if (null == idDecl) {
				// Then it's not on the activation record
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
						"Assignment statement violates constness of declaration of ",
						enclosingMethod.name(), "", "");
			}
		} else if (assignee instanceof QualifiedIdentifierExpression) {
			// We're assigning to something within "qualifier." That doesn't immediately
			// disqualify it Ñ could be a locally created object.
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
					"Assignment statement modifies a member object that could be shared; ",
					"that violates the constness of ",
					enclosingMethod.name(), "");
		} else if (assignee instanceof QualifiedClassMemberExpression) {
			// Certainly off-limits
			errorHook5p2(ErrorType.Warning, ctxGetStart.getLine(),
					"WARNING: Assignment statement modifies a class member that could be shared; ",
					"that violates the constness of ",
					enclosingMethod.name(), "");
		} else if (assignee instanceof ArrayExpression) {
			// This is just an ArrayBase. In itself not a problem if it
			// is on the activation record of the method
			final ArrayExpression arrayExpression = (ArrayExpression) assignee;
			checkLhsForAssignmentViolatingConstness(arrayExpression.originalExpression(), enclosingMethod, ctxGetStart);
		} else if (assignee instanceof ArrayIndexExpression) {
			// Can't know without full dataflow analysis
			errorHook5p2(ErrorType.Warning, ctxGetStart.getLine(),
					"WARNING: Assignment statement modifies an array member that could be shared; ",
					"that violates the constness of ",
					enclosingMethod.name(), "");
		}
	}
	
	protected void checkForMessageSendViolatingConstness(MethodSignature signature, Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			if (signature.hasConstModifier()) {
				; // it's O.K. Ñ this is a const method
			} else {
				errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(),
						"Call of non-const method ", signature.name(),
						" from within const method ",
						enclosingMethod.name(), ", which violates the const modifier of the latter.", "");
			}
		}
	}

    // -------------------------------------------------------------------------------------------------------

	// WARNING. Tricky code here
	@Override public void declareObject(StaticScope s, ObjectDeclaration objdecl) { s.declareObject(objdecl); }
	@Override public void declareRole(StaticScope s, RoleDeclaration roledecl) { s.declareRole(roledecl); }

	protected StagePropDeclaration currentStageProp_;
	protected ActualArgumentList currentArgumentList() { return parsingData_.currentArgumentList(); }
}