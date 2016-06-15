package info.fulloo.trygve.parser;

/*
 * Trygve IDE 2.0
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

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.ClassOrContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectSubclassDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Message;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.BuiltInType;
import info.fulloo.trygve.declarations.Type.ClassOrContextType;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.VarargsType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.ContextType;
import info.fulloo.trygve.declarations.Type.ErrorType;
import info.fulloo.trygve.declarations.Type.StagePropType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.ErrorExpression;
import info.fulloo.trygve.expressions.ExpressionStackAPI;
import info.fulloo.trygve.expressions.MethodInvocationEnvironmentClass;
import info.fulloo.trygve.expressions.Expression.ArrayExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.AssignmentExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedIdentifierExpression;
import info.fulloo.trygve.expressions.Expression.IndexExpression;
import info.fulloo.trygve.expressions.Expression.LastIndexExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.Expression.NullExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedClassMemberExpression;
import info.fulloo.trygve.expressions.Expression.RoleArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect;
import info.fulloo.trygve.parser.KantParser.Builtin_type_nameContext;
import info.fulloo.trygve.parser.KantParser.Class_bodyContext;
import info.fulloo.trygve.parser.KantParser.Method_declContext;
import info.fulloo.trygve.parser.KantParser.Method_decl_hookContext;
import info.fulloo.trygve.parser.KantParser.Method_signatureContext;
import info.fulloo.trygve.parser.KantParser.ProgramContext;
import info.fulloo.trygve.parser.KantParser.Role_bodyContext;
import info.fulloo.trygve.parser.KantParser.Role_declContext;
import info.fulloo.trygve.parser.KantParser.Stageprop_bodyContext;
import info.fulloo.trygve.parser.KantParser.Stageprop_declContext;
import info.fulloo.trygve.parser.KantParser.Type_declarationContext;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticRoleScope;


public class Pass2Listener extends Pass1Listener {
	public Pass2Listener(ParsingData parsingData) {
		super(parsingData);
		
		currentScope_ = parsingData_.globalScope();
		currentContext_ = null;
	}

	@Override protected ClassDeclaration lookupOrCreateNewClassDeclaration(String name, StaticScope newScope, ClassDeclaration rawBaseClass, int lineNumber) {
		return currentScope_.lookupClassDeclarationRecursive(name);
	}
	
	@Override protected void createNewTemplateTypeSuitableToPass(TemplateDeclaration newClass, String name, StaticScope newScope, ClassType baseType) {
	}

	@Override protected void lookupOrCreateRoleDeclaration(final String roleName, final int lineNumber, final boolean isRoleArray) {
		// Return value is through currentRole_
		currentRoleOrStageProp_ = currentScope_.lookupRoleOrStagePropDeclarationRecursive(roleName);
		if (null == currentRoleOrStageProp_) {
			assert null != currentRoleOrStageProp_;
		}
	}
	@Override protected void lookupOrCreateStagePropDeclaration(final String stagePropName, final int lineNumber, final boolean isStagePropArray) {
		// Return value is through currentRole_
		currentRoleOrStageProp_ = currentScope_.lookupRoleOrStagePropDeclarationRecursive(stagePropName);
		if (null == currentRoleOrStageProp_) {
			assert null != currentRoleOrStageProp_;
		}
	}
	
	@Override public void enterMethod_decl(KantParser.Method_declContext ctx)
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

	@Override public void exitMethod_decl(KantParser.Method_declContext ctx)
	{
		//  : method_decl_hook '{' type_and_expr_and_decl_list '}'
		// Declare parameters in the new scope
		// This is definitely a Pass2 thing
		final MethodSignature signature = parsingData_.popMethodSignature();

		MethodDeclaration currentMethod = currentScope_.lookupMethodDeclarationRecursive(signature.name(),
				signature.formalParameterList(), false);
		if (null == currentMethod) {
			// It could be because the signatures don't match. Try patching up the signature
			// of a leftover current method with a jimmied signature. We'll have to fix
			// this someday to support overloading. (But it seems to work now - probably O.K.)
			currentMethod = currentScope_.lookupMethodDeclarationRecursive(signature.name(),
					parsingData_.currentFormalParameterList(), false);
		}
		if (null != currentMethod) {
			// It *can* be null. We had an example where one of the
			// parameter types was undeclared and...
		
			// +++++++++++++++++++++++++
			final Type returnType = signature.returnType();
			currentMethod.setReturnType(returnType);
			if (null != returnType) {
				final String returnTypeName = returnType.getText();
				if (null == currentScope_.lookupTypeDeclarationRecursive(returnTypeName)) {
					errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart().getLine(), "Return type `", returnTypeName, "' not declared for `",
							currentMethod.name(), "'.", "");
				} else {
					currentMethod.setReturnType(returnType);
				}
			} else {
				final StaticScope currentScope = currentMethod.enclosedScope();
				final StaticScope parentScope = currentScope.parentScope();
				final Declaration otherAssociatedDeclaration = parentScope.associatedDeclaration();
				if (otherAssociatedDeclaration instanceof ContextDeclaration) {
					if (currentMethod.name().equals(otherAssociatedDeclaration.name())) {
						; // then O.K. - constructor
					} else {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart().getLine(), "Return type not declared for `", currentMethod.name(), "'.", "");
					}
				} else if (otherAssociatedDeclaration instanceof ClassDeclaration) {
					if (currentMethod.name().equals(otherAssociatedDeclaration.name())) {
						; // then O.K. - constructor
					} else {
						final TemplateDeclaration templateDeclaration = ((ClassDeclaration)otherAssociatedDeclaration).generatingTemplate();
						if (null != templateDeclaration) {
							if (currentMethod.name().equals(templateDeclaration.name())) {
								// o.k. - constructors on templates don't include parameter names
							} else {
								errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart().getLine(), "Return type not declared for template method `", currentMethod.name(), "'.", "");
							}
						} else {
							errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart().getLine(), "Return type not declared for class method `", currentMethod.name(), "'.", "");
						}
					}
				} else if (otherAssociatedDeclaration instanceof TemplateDeclaration) {
					if (currentMethod.name().equals(otherAssociatedDeclaration.name())) {
						; // then O.K. - constructor
					} else {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart().getLine(), "Return type not declared for template method `", currentMethod.name(), "'.", "");
					}
				} else {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart().getLine(), "Bad declaration of `", currentMethod.name(), "': ", "bad return type?");
				}
			}
			// +++++++++++++++++++++++++
			
			this.checkMethodAccess(currentMethod, ctx.getStart().getLine());
			
			final StaticScope parentScope = currentScope_.parentScope();
			currentScope_ = parentScope;
			
			@SuppressWarnings("unused")
			final FormalParameterList pl = parsingData_.popFormalParameterList();	// hope this is the right place
			final int lastLineNumber = ctx.getStop().getLine();
			
			final ReturnStatementAudit audit = new ReturnStatementAudit(currentMethod.returnType(), parsingData_.currentExprAndDecl(), lastLineNumber, this);
			assert null != audit;	// just so it's used...
			this.setMethodBodyAccordingToPass(currentMethod);
		}
	}
	
	private void checkMethodAccess(final MethodDeclaration currentMethod, int lineNumber) {
		final AccessQualifier activeAccessQualifier = currentMethod.accessQualifier();
		final StaticScope currentScope = currentMethod.enclosedScope();
		final StaticScope parentScope = currentScope.parentScope();
		final Declaration otherAssociatedDeclaration = parentScope.associatedDeclaration();
		final Type derivedClassReturnType = currentMethod.returnType();
		
		if (otherAssociatedDeclaration instanceof ClassDeclaration) {
			ClassDeclaration baseClass = ((ClassDeclaration) otherAssociatedDeclaration).baseClassDeclaration();
			while (null != baseClass) {
				final StaticScope baseClassScope = baseClass.enclosedScope();
				final MethodDeclaration baseClassVersionOfMethod =
						baseClassScope.lookupBaseClassMethodLiskovCompliantTo(currentMethod);
				if (null != baseClassVersionOfMethod) {
					final AccessQualifier baseClassAccessQualifier = baseClassVersionOfMethod.accessQualifier();
					if (baseClassAccessQualifier != activeAccessQualifier) {
						errorHook6p2(ErrorIncidenceType.Fatal, lineNumber,
								"Derived class declaration of `", currentMethod.signature().getText(),
								"' must have same access qualifier as that of declaration in base class `",
								baseClass.name(), "'.", "");
						break;	// don't cascade errors
					}
					
					// Check Liskov
					final Type baseClassReturnType = baseClassVersionOfMethod.returnType();
					if (null != baseClassReturnType && null != derivedClassReturnType &&
							baseClassReturnType.isntError() &&
							derivedClassReturnType.isntError() &&
							baseClassReturnType.canBeConvertedFrom(derivedClassReturnType,
									lineNumber, this) == false) {
						errorHook6p2(ErrorIncidenceType.Fatal, lineNumber,
								"Return type `",
								derivedClassReturnType.getText(),
								"' of `" + currentMethod.signature().getText(),
								"' in class `" + otherAssociatedDeclaration.name(),
								"' must be no less restrictive than `",
								baseClassReturnType.getText() +
								"' in the base class."
								);
					}
				}
				
				// Next
				baseClass = baseClass.baseClassDeclaration();
			}
		}
	}
	
	protected void setMethodBodyAccordingToPass(MethodDeclaration unused)
	{
		/* Nothing. Just clean up the data structures. */
		@SuppressWarnings("unused")
		final ExprAndDeclList body = parsingData_.popExprAndDecl();
	}
	@Override public void enterRole_decl(KantParser.Role_declContext ctx)
	{
		// : 'role' JAVA_ID '{' role_body '}'
		// | 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | 'role' JAVA_ID '{' '}'
		// | 'role' JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{ '}'
		// | access_qualifier 'role' JAVA_ID '{ '}' REQUIRES '{' self_methods '}'
		
		super.enterRole_decl(ctx);
		this.processRequiredDeclarations(ctx.getStart().getLine());
	}
	@Override public void exitRole_decl(KantParser.Role_declContext ctx)
	{
		// : 'role' JAVA_ID '{' role_body '}'
		// | 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | 'role' JAVA_ID '{' '}'
		// | 'role' JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{ '}'
		// | access_qualifier 'role' JAVA_ID '{ '}' REQUIRES '{' self_methods '}'
		
		this.processDeclareRoleArrayAlias(ctx.getStart().getLine());
		super.exitRole_decl(ctx);	// necessary? some of the cleanup seems relevant
	}
	@Override public void enterStageprop_decl(KantParser.Stageprop_declContext ctx)
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
		currentRoleOrStageProp_.processRequiredDeclarations(lineNumber);
	}
	
	@Override public void enterArgument_list(KantParser.Argument_listContext ctx)
	{
	}

	@Override public void exitMessage(KantParser.MessageContext ctx)
	{
		//  : method_name '(' argument_list ')'
		//  | type_name '(' argument_list ')'
		
		// Certified Pass 2 version ;-)
		
		String selectorName = null;
		if (null != ctx.method_name()) {
			selectorName = ctx.method_name().getText();
		} else if (null != ctx.type_name()) {
			final ExpressionStackAPI typeName = parsingData_.popRawExpression();
			selectorName = typeName.name();
		} else {
			assert false;
		}	

		final long lineNumber = ctx.getStart().getLine();
		
		// This is definitely Pass 2 stuff.
		final ActualArgumentList argumentList = parsingData_.popArgumentList();
		
		// All arguments are evaluated and are pushed onto the stack
		for (int i = 0; i < argumentList.count(); i++) {
			final Object rawArgument = argumentList.argumentAtPosition(i);
			assert null != rawArgument && rawArgument instanceof Expression;
			final Expression argument = (Expression)rawArgument;
			argument.setResultIsConsumed(true);
		}
		
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final Message newMessage = new Message(selectorName, argumentList, lineNumber, enclosingMegaType);
		parsingData_.pushMessage(newMessage);
	}
	
	@Override protected Expression processIndexExpression(final Expression rawArrayBase, final Expression indexExpr, final int lineNumber) {
		Expression expression = null;
		
		// On pass one, types may not yet be set up so we may
		// stumble here (particularly if there is a forward reference
		// to a type). Here on pass 2 we're a bit more anal
		final Type baseType = rawArrayBase.type();
		if (baseType instanceof RoleType) {
			final String roleName = rawArrayBase.name();
			final RoleType roleBaseType = (RoleType)baseType;
			// Look up the actual array. It is in the current scope as a type
			final StaticScope contextScope = roleBaseType.contextDeclaration().type().enclosedScope();
			final RoleDeclaration roleDecl = contextScope.lookupRoleOrStagePropDeclaration(roleName);
			if (null == roleDecl) {
				assert false;
			} else {
				// do something useful
				
				final Type contextType = Expression.nearestEnclosingMegaTypeOf(roleDecl.enclosedScope());
				final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
				final Expression currentContext = new IdentifierExpression("current$context", contextType, nearestMethodScope, lineNumber);
				final Expression roleNameInvocation = new QualifiedIdentifierExpression(currentContext, roleName, roleDecl.type());
				expression = new RoleArrayIndexExpression(roleName, roleNameInvocation, indexExpr);
			}
		} else if (baseType instanceof ArrayType) {
			final Type arrayBaseType = rawArrayBase.type();
			assert arrayBaseType instanceof ArrayType;
			final ArrayType arrayType = (ArrayType)arrayBaseType;	// instance of ArrayType
			final Type aBaseType = arrayType.baseType();	// like int
			final ArrayExpression arrayBase = new ArrayExpression(rawArrayBase, aBaseType);
			arrayBase.setResultIsConsumed(true);
			expression = new ArrayIndexExpression(arrayBase, indexExpr, lineNumber);
		} else if (baseType instanceof BuiltInType && baseType.name().equals("void")) {
			// Stumbling error
			expression = new ErrorExpression(rawArrayBase);
		} else if (baseType instanceof BuiltInType && baseType.name().equals("Null")) {
			// Stumbling error
			expression = new ErrorExpression(rawArrayBase);
		} else {
			assert false;
		}
		return expression;
	}
	
	@Override protected void checkExprDeclarationLevel(RuleContext ctxParent, Token ctxGetStart) {
		// Certified Pass 2 version :-)
		RuleContext executionContext = ctxParent;
		while ((executionContext instanceof ProgramContext) == false) {
			if (executionContext instanceof Method_declContext) {
				break;
			} else if (executionContext instanceof Stageprop_bodyContext) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in stageprop scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Role_bodyContext) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in Role scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Stageprop_declContext) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in stageprop scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Role_declContext) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in Role scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Class_bodyContext) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in Class scope: it must be in a method", "", "", "");
				break;
			} else if (executionContext instanceof Type_declarationContext) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Expression cannot just appear in a global program element scope: it must be in a method", "", "", "");
				break;
			}
			executionContext = executionContext.parent;
		}
	}
	
	@Override public void binopTypeCheck(final Expression leftExpr, final String operationAsString,
			final Expression rightExpr, final Token ctxGetStart) {
		// Certified Pass 2 version ;-)
		final Type leftExprType = leftExpr.type(), rightExprType = rightExpr.type();
		final Type resultType = leftExprType;

		if (resultType.canBeConvertedFrom(rightExpr.type()) == false && leftExpr.isntError() && rightExpr.isntError()
				&& leftExpr.type().isntError() && rightExpr.type().isntError()) {
			errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Invalid operands to `" +
					"", operationAsString, "' on type ", leftExpr.type().name(),
					" with operand of type ", rightExpr.type().name());
		}
		final ActualArgumentList argList = new ActualArgumentList();
		argList.addActualArgument(rightExpr);
		final Expression self = new IdentifierExpression("t$his", resultType, resultType.enclosedScope(), ctxGetStart.getLine());
		argList.addFirstActualParameter(self);
		final StaticScope enclosedScope = resultType.enclosedScope();
		
		MethodDeclaration mdecl = enclosedScope.lookupMethodDeclarationWithConversionIgnoringParameter(
				operationAsString, argList, false, /*parameterToIgnore*/ null);
		if (null == mdecl) {
			mdecl = enclosedScope.lookupMethodDeclarationRecursive(operationAsString, argList, false);
			if (null == mdecl) {
				mdecl = enclosedScope.lookupMethodDeclarationIgnoringRoleStuff(operationAsString, argList);
				if (null == mdecl && rightExpr.isntError() && rightExpr.type().isntError() && resultType.isntError()) {
					errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "No such operation `", operationAsString, "' on type `",
							resultType.name(), "' for argument `" + rightExpr.type().name(), "'.");
				}
			}
		}
		
		// First, check "overloading"
		final ActualArgumentList argumentList = new ActualArgumentList();
		argumentList.addActualArgument(rightExpr);
		argumentList.addFirstActualParameter(leftExpr);
		final MethodDeclaration checkMethodOverload =
				leftExprType.enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
						operationAsString, argumentList, false, /*parameterToIgnore*/ null);
		
		if (null != checkMethodOverload) {
			;	// then the class overloads the operator. O.K.
		} else if (leftExprType.canBeLhsOfBinaryOperatorForRhsType(operationAsString, rightExprType) &&
				rightExprType.canBeRhsOfBinaryOperator(operationAsString)) {
			;	// o.k.
		} else if (rightExpr.isntError() && resultType.isntError() && rightExpr.type().isntError()){
			errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Operation `", operationAsString, "' cannot be applied to type `",
					resultType.name(), "' for argument `", rightExpr.type().name() + "'.");
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
		
		if (qualifier.type().name().equals("Class")) {
			// This is where we handle types like "System" for System.out.print*
			// Now we need to get the actual class of that name
			final Type rawClass = currentScope_.lookupTypeDeclarationRecursive(qualifier.name());
			assert rawClass instanceof ClassType;
			final ClassType theClass = (ClassType)rawClass;
			
			final ObjectDeclaration odecl = theClass.type().enclosedScope().lookupObjectDeclaration(javaIdString);
			if (odecl.type() != null) type = odecl.type();
			expression = new QualifiedClassMemberExpression(theClass, javaIdString, type);
		} else {
			final ObjectDeclaration odecl = qualifier.type().enclosedScope().lookupObjectDeclarationRecursive(javaIdString);
		
			if (null == odecl) {
				final MethodDeclaration javaId2 = qualifier.type().enclosedScope().lookupMethodDeclarationRecursive(javaIdString, null, true);
				if (null == javaId2) {
					errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Identifier `", javaIdString, "' not declared for object `", qualifier.name(), "'.", "");
					type = new ErrorType();
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
	
	@Override public void ctorCheck(final Type type, final Message message, final int lineNumber) {
		// Is there a constructor?
		// We're not ready for this until here in Pass 2
		final String className = message.selectorName();
		final ActualArgumentList actualArgumentList = message.argumentList();
		if (null != type) {
			// Under error conditions we sometimes get a null type parameter
			final StaticScope declarationScope = type.enclosedScope();
			
			// Can be null in error condition
			if (null != declarationScope) {
				final String typeName = type.name();
				final MethodDeclaration constructor = declarationScope.lookupMethodDeclarationWithConversionIgnoringParameter(
						typeName, actualArgumentList, false, /*parameterToIgnore*/ null);
				if (null != actualArgumentList && 1 < actualArgumentList.count()) {
					// So the "new" message actually had arguments, which means
					// it's expecting a constructor
					if (null == constructor) {
						errorHook5p2(ErrorIncidenceType.Fatal, lineNumber, "No matching constructor on class `", className, "' for `new' invocation", "");
					}
				}
				
				if (null != constructor) {
					final boolean isAccessible = currentScope_.canAccessDeclarationWithAccessibility(constructor, constructor.accessQualifier(), lineNumber);
					if (isAccessible == false) {
						errorHook6p2(ErrorIncidenceType.Fatal, lineNumber,
								"Cannot access constructor `", constructor.name(),
								"' with `", constructor.accessQualifier().asString(), "' access qualifier.","");
					}
				}
			}
		}
	}
	public void addSelfAccordingToPass(final Type type, final Message message, final StaticScope scope) {
		// Apparently called only for constructor processing.
		// The simple part. Add this.
		final Expression self = new IdentifierExpression("t$his", type, scope, 0);
		message.addActualThisParameter(self);
	}
	
	private MethodSignature declarationForMessageFromRequiresSectionOfRole(final Message message,
			final RoleDeclaration roleDecl) {
		MethodSignature retval = null;
		final Map<String, List<MethodSignature>> requiresDecls = roleDecl.requiredSelfSignatures();
		for (Map.Entry<String, List<MethodSignature>> entry : requiresDecls.entrySet()) {
			if (entry.getKey().equals(message.selectorName())) {
				final List<MethodSignature> potentialRequiresMethods = entry.getValue();
				for (final MethodSignature potentialRequiresMethod : potentialRequiresMethods) {
					if (null != potentialRequiresMethod) {
						final FormalParameterList requiresSignature = potentialRequiresMethod.formalParameterList();
						final ActualArgumentList callingSignature = message.argumentList();
						if (requiresSignature.alignsWithUsingConversion(callingSignature)) {
							retval = potentialRequiresMethod;
							break;
						}
					}
				}
				if (null != retval) break;
			}
		}
		return retval;
	}
	
	private void contextInvocationCheck(final Type nearestEnclosingMegaType, final Message message,
			final Type objectType, final Type wannabeContextType, final Token ctxGetStart) {
		// Don't allow Role methods directly to invoke
		// Context methods. Look up the method in the enclosing
		// context and make sure it's not there. But first we
		// can say it's O.K. if it's either another method
		// in the Role.
		MethodDeclaration testDecl = nearestEnclosingMegaType.enclosedScope().lookupMethodDeclarationIgnoringParameter(message.selectorName(),
				message.argumentList(), "this", true);
		if (null == testDecl) {
			// Check in the Requires list
			final RoleType objectTypeAsRoleType = (RoleType)objectType;
			final RoleDeclaration roleDecl = (RoleDeclaration)objectTypeAsRoleType.associatedDeclaration();
			final MethodSignature signatureInRequiresSection = declarationForMessageFromRequiresSectionOfRole(
					message, roleDecl);
			if (null == signatureInRequiresSection) {
				testDecl = wannabeContextType.enclosedScope().lookupMethodDeclarationIgnoringParameter(message.selectorName(),
						message.argumentList(), "this", true);
				if (null != testDecl) {
					final StaticScope currentMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
					errorHook5p2(ErrorIncidenceType.Noncompliant, ctxGetStart.getLine(),
							"NONCOMPLIANT: Enacting enclosed Context script `", message.selectorName() + message.argumentList().selflessGetText(),
							"' from within `" + nearestEnclosingMegaType.name() + "." + currentMethodScope.associatedDeclaration().name(),
							"'.");
				}
			}
		}
	}

	private void otherRolesRequiresInvocationCheck(
			final Type nearestEnclosingMegaType, final Message message,
			final Type objectType, final Type wannabeContextType,
			final Token ctxGetStart) {
		// Don't allow Role methods directly to invoke
		// the "requires" methods of other Roles in the same Context.
		// But first we can say it's O.K. if it's within the same Role.
		if (currentRoleOrStageProp_.type().pathName().equals(objectType.pathName())) {
			;	// is within the same Role; it's cool
		} else {
			final MethodDeclaration testDecl = nearestEnclosingMegaType.enclosedScope().lookupMethodDeclarationIgnoringParameter(message.selectorName(),
					message.argumentList(), "this", true);
			if (null != testDecl) {
				;	// is a regular Role method — is O.K.
			} else {
				// Check in the Requires list
				final RoleType objectTypeAsRoleType = (RoleType)objectType;
				final RoleDeclaration roleDecl = (RoleDeclaration)objectTypeAsRoleType.associatedDeclaration();
				final MethodSignature signatureInRequiresSection = declarationForMessageFromRequiresSectionOfRole(
						message, roleDecl);
				if (null != signatureInRequiresSection) {
					// It's O.K. if the signature has been pulled into the Role interface
					
					final MethodSignature publishedSignature = roleDecl.lookupPublishedSignatureDeclaration(signatureInRequiresSection);
					if (null != publishedSignature) {
						// Is it public?
						if (publishedSignature.accessQualifier() != AccessQualifier.PublicAccess) {
							errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Script `", message.selectorName(),
									"' is not public and so is not accessible to `", nearestEnclosingMegaType.name(), "'.", "");
						}
					} else {
						errorHook5p2(ErrorIncidenceType.Noncompliant, ctxGetStart.getLine(),
							"NONCOMPLIANT: Trying to enact object script `", message.selectorName() + message.argumentList().selflessGetText(),
							"' without using the interface of the Role it is playing: `" + roleDecl.name(),
							"'.");
					}
				}
			}
		}
	}
	
	private <ExprType> Expression messageSendGetObject(final Token ctxGetStart,
			final ExprType ctx_abelianAtom, final Builtin_type_nameContext ctx_builtin_typeName) {
		// Pop the expression for the indicated object and message
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
		
		Expression object = null;
		if (ctx_abelianAtom != null) {
			// Error stumbling check
			if (parsingData_.currentExpressionExists()) {
				if (null == parsingData_.peekExpression()) {
					// Get rid of the null junk (error stumbling logic)
					@SuppressWarnings("unused")
					final Object unused = parsingData_.popRawExpression();
					
					// Come in with a suitable substitute
					object = new ErrorExpression(null);
				} else {
					object = parsingData_.popExpression();
				}
			} else {
				return new ErrorExpression(null);	// get out
			}
		} else if (ctx_builtin_typeName != null) {
			// e.g. String.join
			final String typeName = ctx_builtin_typeName.getText();
			final Type theType = currentScope_.lookupTypeDeclarationRecursive(typeName);
			final Type classType = StaticScope.globalScope().lookupTypeDeclaration("Class");
			object = new IdentifierExpression(theType.name(), classType, classType.enclosedScope().parentScope(),
						ctxGetStart.getLine());
		} else if (null != nearestEnclosingMegaType) {
			object = new IdentifierExpression("this", nearestEnclosingMegaType, nearestMethodScope, ctxGetStart.getLine());
		} else {
			object = new ErrorExpression(null);
		}
		object.setResultIsConsumed(true);
		
		return object;
	}
	
	private Expression messageSendGenerateCall(final Token ctxGetStart,
			final Expression object,
			final MethodDeclaration methodDeclaration, final Message message,
			final Type returnType, final MethodSignature methodSignature) {
		Expression retval = null;
		if (null != methodSignature) {
			MethodInvocationEnvironmentClass originMethodClass = MethodInvocationEnvironmentClass.Unknown;

			if (null != currentScope_.associatedDeclaration()) {
				originMethodClass = currentScope_.methodInvocationEnvironmentClass();
			} else {
				final Type anotherType = object.enclosingMegaType();
				if (null != anotherType) {
					final StaticScope anotherScope = anotherType.enclosedScope();
					originMethodClass = anotherScope.methodInvocationEnvironmentClass();
				} else {
					originMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;	// outermost scope
				}
			}
			
			MethodInvocationEnvironmentClass targetMethodClass = MethodInvocationEnvironmentClass.Unknown;
			if (null != object && null != object.type()) {
				if (object.type() instanceof StagePropType) {
					targetMethodClass = MethodInvocationEnvironmentClass.RoleEnvironment;
				} else if (object.type() instanceof RoleType) {
					targetMethodClass = MethodInvocationEnvironmentClass.RoleEnvironment;
				} else if (object.type() instanceof ContextType) {
					targetMethodClass = MethodInvocationEnvironmentClass.ContextEnvironment;
				} else if (object.type() instanceof InterfaceType) {
					// Interfaces wrap classes
					targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
				} else if (object.type() instanceof ArrayType) {
					// Arrays kind of behave like classes, certainly as regards dispatching
					targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
				} else if (null != methodDeclaration) {
					targetMethodClass = methodDeclaration.enclosingScope().methodInvocationEnvironmentClass();
				} else  {
					targetMethodClass = MethodInvocationEnvironmentClass.Unknown;
				}
				
				// Double-check to make sure that, if this is going through a Role
				// interface, whether it is actually a "requires" declaration
				if (MethodInvocationEnvironmentClass.RoleEnvironment == targetMethodClass) {
					final RoleType roleType = (RoleType)object.type();
					final RoleDeclaration roleDecl = (RoleDeclaration)roleType.associatedDeclaration();
					final MethodSignature requiredSignatureDecl = roleDecl.lookupRequiredMethodSignatureDeclaration(message.selectorName());
					if (null != requiredSignatureDecl) {
						// It could be a context, but we can't tell here. We must wait until
						// run-time and adjust
						targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
					}
				}
			} else if (null != methodDeclaration) {
				targetMethodClass = methodDeclaration.enclosingScope().methodInvocationEnvironmentClass();
			} else  {
				targetMethodClass = MethodInvocationEnvironmentClass.Unknown;
			}
			
			checkForMessageSendViolatingConstness(methodSignature, ctxGetStart);
			
			boolean isPolymorphic = true;
			if (amInConstructor()) {
				if (object instanceof IdentifierExpression) {
					if (((IdentifierExpression)object).name().equals("this")) {
						isPolymorphic = false;
					}
				}
			}
			retval = new MessageExpression(object, message, returnType, ctxGetStart.getLine(), methodSignature.isStatic(),
					originMethodClass, targetMethodClass, isPolymorphic);
			if (null == methodDeclaration) {
				// Could be a "required" method in a Role. It's O.K.
				assert true;
			} else {
				final boolean accessOK = currentScope_.canAccessDeclarationWithAccessibility(methodDeclaration, methodDeclaration.accessQualifier(), ctxGetStart.getLine());
				if (accessOK == false) {
					errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
							"Cannot access method `", methodDeclaration.name(),
							"' with `", methodDeclaration.accessQualifier().asString(), "' access qualifier.", "");
				}
			}
		} else {
			// Stumble elegantly
			retval = new ErrorExpression(object);
		}
		
		return retval;
	}
	
	@Override public <ExprType> Expression messageSend(final Token ctxGetStart, final ExprType ctx_abelianAtom,
			final Builtin_type_nameContext ctx_builtin_typeName) {
		// | expr '.' message
		// | message
		// Certified Pass 2 version.
		// REFACTOR! TODO
		
		MethodDeclaration methodDeclaration = null;
		Expression retval = null;
		final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		Expression object = messageSendGetObject(ctxGetStart, ctx_abelianAtom, ctx_builtin_typeName);
									
		Message message = parsingData_.popMessage();
				
		if (null == message) {
			return new ErrorExpression(object);		// yuk. refactor.
		}
		
		if (null == nearestEnclosingMegaType && object instanceof NullExpression) {
			errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
					"Invoking method `", message.selectorName(), "' on implied object `this' in a non-object context.", "");;
		} else if (null != object) {
			// For future reference, we don't want to do this if the
			// method is static. Of course, we can't in general know
			// that until we look it up, and we can't look it up
			// until we have its signature, and we can't have its
			// signature until we know whether it takes a "this"
			// parameter... Maybe the answer is to pass a parameter
			// even for static functions (the class object)
			message.addActualThisParameter(object);
		}
		
		Type objectType = null == object? null: object.type();
		if (null == objectType) {
			objectType = parsingData_.globalScope().lookupTypeDeclaration("Object");
		}
		assert null != objectType;
		
		MethodSignature methodSignature = null;
		boolean isOKMethodSignature = false;
		
		if (objectType.name().equals("Class")) {
			// Static method invocation. The "object" is really a class name.
			
			assert object instanceof IdentifierExpression;
			final Type type = currentScope_.lookupTypeDeclarationRecursive(object.name());
			methodDeclaration = type.enclosedScope().lookupMethodDeclaration(
					message.selectorName(), message.argumentList(), false);
			if (null == methodDeclaration) {
				methodDeclaration = type.enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
					message.selectorName(), message.argumentList(), false, /*parameterToIgnore*/ null);
			}
			if (null == methodDeclaration) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
						"Cannot find declaration of ", object.name() + ".", message.selectorName(),
						".");
			} else {
				methodSignature = methodDeclaration.signature();
				isOKMethodSignature = null != methodSignature;
			}
		} else if (objectType instanceof RoleType || objectType instanceof StagePropType) {
			Type wannabeContextType = nearestEnclosingMegaType;
			if (wannabeContextType instanceof RoleType) {
				final RoleType nearestEnclosingRoleOrStageProp = (RoleType) nearestEnclosingMegaType;
				wannabeContextType = Expression.nearestEnclosingMegaTypeOf(nearestEnclosingRoleOrStageProp.enclosingScope());
				assert wannabeContextType instanceof ContextType;
				
				if (nearestEnclosingMegaType instanceof RoleType || nearestEnclosingMegaType instanceof StagePropType) {
					// Don't allow Role methods directly to invoke Context methods.
					contextInvocationCheck(nearestEnclosingMegaType, message, objectType, wannabeContextType, ctxGetStart);
					
					// Don't allow Role methods directly to invoke other Roles' "requires" methods.
					otherRolesRequiresInvocationCheck(nearestEnclosingMegaType, message, objectType, wannabeContextType, ctxGetStart);
				
					if (((RoleType)objectType).isArray()) {
						if (object instanceof RoleArrayIndexExpression) {
							// o.k.
						} else if (object.name().equals("this")) {
							// then it's not really a Role method, but a requires method
							// (hope and pray)
						} else {
							// Then this is trying to invoke a Role vector method
							// without specifying the individual vector method

							// This makes sense only if we are calling a method in the same Role
							// as holds this method
							final String objectTypePathName = object.type().pathName();
							if (nearestEnclosingMegaType.pathName().equals(objectTypePathName) == false) {
								errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
										"Trying to access method `", message.selectorName(), "' of another Role (`",
										object.name(), "') with indexing the Role vector to yield a Role-player.", "");
							} else {
								final IndexExpression theIndex = new IndexExpression(
										nearestEnclosingRoleOrStageProp.associatedDeclaration(),
										currentContext_);
								object = new RoleArrayIndexExpression(object.name(), object, theIndex);
							
								// Woops — message is wrong. Replace its "this"
								message.replaceActualThisParameter(object);
							}
						}
					}
				}
			} else if (wannabeContextType instanceof ContextType) {
				// We don't want Context methods to be able directly
				// to call requires methods of Roles
				final RoleType objectTypeAsRoleType = (RoleType)objectType;
				
				if (((RoleType)objectType).isArray()) {
					if (object instanceof RoleArrayIndexExpression) {
						// o.k.
					} else {
						// Then this is trying to invoke a Role vector method
						// without specifying the individual vector method
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
							"Trying to access a Role method `", message.selectorName(), "' on the vector ID `", object.name() + "'.");
					}
				}
				
				final RoleDeclaration roleDecl = (RoleDeclaration)objectTypeAsRoleType.associatedDeclaration();
				final MethodSignature signatureInRequiresSection = declarationForMessageFromRequiresSectionOfRole(
						message, roleDecl);
				if (null != signatureInRequiresSection) {
					// Is O.K. if it is also declared in the Role interface
					final MethodSignature signatureInRoleInterface = roleDecl.lookupPublishedSignatureDeclaration(signatureInRequiresSection);
					if (null == signatureInRoleInterface) {
						final StaticScope currentMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
						errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
								"Context script `", currentMethodScope.associatedDeclaration().name(),
								"' may enact only Role scripts. Script `",
								message.selectorName() + message.argumentList().selflessGetText(),
								"' is an instance script from a class and is inaccessible to Context `",
								wannabeContextType.name() + "'.");
					}
				}
			}
			
			// Look this thing up in the "required" interface to see
			// if it's really a Role method or just a latently bound
			// instance method in an object bound to this role
			assert objectType instanceof RoleType;
			final RoleType roleType = (RoleType)objectType;
			methodSignature = roleType.lookupMethodSignatureDeclaration(message.selectorName());
			if (null != methodSignature) {
				// Then it may be in the "required" declarations and is NOT a role method per se
				isOKMethodSignature = true;
			} else {
				// If we're calling from a Context script to a script of one of its Roles,
				// we want to pass "this" in the "current$context" slot. Otherwise, if it's
				// from another Role, just use "current$context". There should be no other
				// possibilities
				final Type enclosingType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				
				String nameOfContextIdentifier;
				if (enclosingType instanceof ContextType) {
					nameOfContextIdentifier = "this";
				} else if (enclosingType instanceof RoleType) {
					nameOfContextIdentifier = "current$context";
				} else {
					nameOfContextIdentifier = "current$context";	// arbitrary.
					assert false;
				}

				final Expression currentContext = new IdentifierExpression(nameOfContextIdentifier, wannabeContextType, nearestMethodScope, ctxGetStart.getLine());
				final ActualArgumentList saveArgumentList = message.argumentList().copy();
				message.argumentList().addFirstActualParameter(currentContext);
				currentContext.setResultIsConsumed(true);
				
				// NOTE: Leaves methodSignature null.
				// We need it for call of checkForMessageSendViolatingConstness below.
				methodDeclaration = objectType.enclosedScope().lookupMethodDeclaration(message.selectorName(), message.argumentList(), false);
				if (null != methodDeclaration) {
					// Null check is related to error stumbling
					methodSignature = methodDeclaration.signature();
				} else {
					// It's a Role. Could be a call to assert or something like that
					// in the base class
					final ClassDeclaration objectDecl = currentScope_.lookupClassDeclarationRecursive("Object");
					assert null != objectDecl;
					methodDeclaration = processReturnTypeLookupMethodDeclarationIgnoringRoleStuffIn(objectDecl, message.selectorName(), message.argumentList());
					
					if (null != methodDeclaration) {
						// I THINK that the right thing to do at this point is to pull
						// the context out of the signature. Luckily, we copied the
						// parameter list above...
						
						message = new Message(message.selectorName(), saveArgumentList,
								message.lineNumber(), message.enclosingMegaType());
						methodSignature = methodDeclaration.signature();
					}
				}
			}
		} else if (objectType instanceof ClassType || objectType instanceof ContextType) {
			final ClassOrContextType classObjectType = (ClassOrContextType) objectType;
			final StaticScope classScope = null == nearestEnclosingMegaType? null: nearestEnclosingMegaType.enclosedScope();
			final TemplateInstantiationInfo templateInstantiationInfo = null == classScope? null: classScope.templateInstantiationInfo();
			
			final ActualOrFormalParameterList argumentList = null != message && null != message.argumentList()?
						message.argumentList().mapTemplateParameters(templateInstantiationInfo):
						null;
			methodDeclaration = null != classObjectType && null != classObjectType.enclosedScope()?
						classObjectType.enclosedScope().lookupMethodDeclarationRecursive(message.selectorName(), argumentList, false):
						null;
			if (null == methodDeclaration) {
				// Check the base class
				if  (null != classObjectType && null != classObjectType.enclosedScope()) {
					ClassType baseClassType = classObjectType.baseClass();
					while (null != baseClassType) {
						final StaticScope baseClassScope = baseClassType.enclosedScope();
						assert null != baseClassScope;
						methodDeclaration = baseClassScope.lookupMethodDeclarationWithConversionIgnoringParameter(
								message.selectorName(),
								argumentList, false, /*parameterToIgnore*/ null);
						if (null != methodDeclaration) {
							break;
						}
						baseClassType = baseClassType.baseClass();
					}
				}
				if (null == methodDeclaration) {
					// If we're inside of a template, many argument types won't match.
					// Try anyhow and see if we can find something.
	
					methodDeclaration = null != classObjectType && null != classObjectType.enclosedScope()?
								classObjectType.enclosedScope().lookupMethodDeclarationRecursive(message.selectorName(), argumentList, true):
								null;
					if (null == methodDeclaration) {
						// Mainly for error recovery (bad argument to method / method not declared)
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Script `",
								message.getText(),
								"' not declared in class `", classObjectType.name() + "'.");
						return new ErrorExpression(null);		// punt
					} else {
						methodSignature = methodDeclaration.signature();
					}
				} else {
					methodSignature = methodDeclaration.signature();
				}
			} else {
				methodSignature = methodDeclaration.signature();
			}
		} else if (objectType instanceof BuiltInType) {
			final BuiltInType classObjectType = (BuiltInType) objectType;
			final StaticScope classScope = null == nearestEnclosingMegaType? null: nearestEnclosingMegaType.enclosedScope();
			final TemplateInstantiationInfo templateInstantiationInfo = null == classScope? null: classScope.templateInstantiationInfo();
			final ActualOrFormalParameterList argumentList = null != message && null != message.argumentList()?
						message.argumentList().mapTemplateParameters(templateInstantiationInfo):
						null;
			methodDeclaration = null != classObjectType && null != classObjectType.enclosedScope()?
						classObjectType.enclosedScope().lookupMethodDeclarationRecursive(message.selectorName(), argumentList, false):
						null;
			if (null == methodDeclaration) {
				// If we're inside of a template, many argument types won't match.
				// Try anyhow and see if we can find something.
				methodDeclaration = null != classObjectType && null != classObjectType.enclosedScope()?
							classObjectType.enclosedScope().lookupMethodDeclarationRecursive(message.selectorName(), argumentList, true):
							null;
				if (null == methodDeclaration) {
					// Mainly for error recovery (bad argument to method / method not declared)
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Script `", message.getText(),
							"' not declared in class `", classObjectType.name() + "'.");
					return new ErrorExpression(null);		// punt
				} else {
					methodSignature = methodDeclaration.signature();
				}
			} else {
				methodSignature = methodDeclaration.signature();
			}
		} else if (objectType instanceof InterfaceType) {
			final InterfaceType classObjectType = (InterfaceType) objectType;
			final ActualOrFormalParameterList argumentList = message.argumentList();
			methodSignature = null != classObjectType?
						classObjectType.lookupMethodSignature(message.selectorName(), argumentList):
						null;
			if (null == methodSignature) {
				// Mainly for error recovery (bad argument to method / method not declared)
				final String methodSelectorName = message.selectorName();
				if (argumentList.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
							"Script `",
							methodSelectorName + argumentList.getText(),
							"' not declared in Interface `",
							classObjectType.name() + "'.");
				}
			} else {
				isOKMethodSignature = true;
			}
		} else if (objectType.name().endsWith("_$array") && objectType instanceof ArrayType) {
			// This is part of the endeavor to add method invocations to
			// naked array object appearances (e.g., size())
			if (message.selectorName().equals("size")) {
				methodSignature = new MethodSignature(message.selectorName(), 
						StaticScope.globalScope().lookupTypeDeclaration("int"),
						AccessQualifier.PublicAccess, (int)message.lineNumber(), false);
				methodSignature.setHasConstModifier(true);
			} else if (message.selectorName().equals("at")) {
				methodSignature = new MethodSignature(message.selectorName(), 
						message.returnType(),
						AccessQualifier.PublicAccess, (int)message.lineNumber(), false);
				methodSignature.setHasConstModifier(true);
			} else if (message.selectorName().equals("atPut")) {
				methodSignature = new MethodSignature(message.selectorName(), 
						StaticScope.globalScope().lookupTypeDeclaration("void"),
						AccessQualifier.PublicAccess, (int)message.lineNumber(), false);
				methodSignature.setHasConstModifier(false);
			}
			isOKMethodSignature = true;
		}
		
		Type returnType = this.processReturnType(ctxGetStart, object, objectType, message);
		
		if (null != returnType && returnType instanceof TemplateParameterType) {
			// Is a template type. Change the return type into a bona fide type here
			final StaticScope objectScope = objectType.enclosedScope();
			final TemplateInstantiationInfo newTemplateInstantiationInfo = objectScope.templateInstantiationInfo();
			returnType = newTemplateInstantiationInfo.classSubstitionForTemplateTypeNamed(returnType.name());
		}
		
		if (objectType.name().equals(message.selectorName())) {
			errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Cannot 'call' constructor of ", objectType.name(), ". Use 'new' instead.", "");
		}
		
		if (null == methodDeclaration && isOKMethodSignature == false) {
			final String methodSelectorName = message.selectorName();
			if (message.argumentList().isntError()) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Script `",
						methodSelectorName + message.argumentList().selflessGetText(),
						"' not declared in class ", "classname (dubious error — see other messages)");
			}
		}
		
		assert null != returnType;
		assert null != object;
		assert null != message;
		
		message.setReturnType(returnType);
		
		retval = messageSendGenerateCall(ctxGetStart, object, methodDeclaration,
				message, returnType, methodSignature);
		
		return retval;
	}
	
	@Override protected MethodDeclaration processReturnTypeLookupMethodDeclarationIn(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 2 / 3 version turns on signature checking
		final StaticScope classOrRoleOrWhateverScope = classDecl.enclosedScope();
		
		// Give exact match the first chance
		MethodDeclaration retval = classOrRoleOrWhateverScope.lookupMethodDeclarationIgnoringParameter(methodSelectorName, parameterList, "this",
				/* conversionAllowed = */ false);
		if (null == retval) {
			// If exact match doesn't work, try promition
			retval = classOrRoleOrWhateverScope.lookupMethodDeclarationIgnoringParameter(methodSelectorName, parameterList, "this",
					/* conversionAllowed = */ true);
		}
		return retval;
	}
	@Override protected MethodDeclaration processReturnTypeLookupMethodDeclarationIgnoringRoleStuffIn(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 2 / 3 version turns on signature checking
		final StaticScope classScope = classDecl.enclosedScope();
		return classScope.lookupMethodDeclarationIgnoringRoleStuff(methodSelectorName, parameterList);
	}
	@Override protected MethodDeclaration processReturnTypeLookupMethodDeclarationUpInheritanceHierarchy(final TypeDeclaration classDecl,
			final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 2 / 3 version turns on signature checking
		StaticScope classScope = classDecl.enclosedScope();
		MethodDeclaration retval = classScope.lookupMethodDeclarationIgnoringParameter(methodSelectorName, parameterList, "this", true);
		if (null == retval) {
			if (classDecl instanceof ClassDeclaration || classDecl instanceof ContextDeclaration) {	// should be
				final ObjectSubclassDeclaration classDeclAsClassOrContextDecl = (ObjectSubclassDeclaration) classDecl;
				final ClassDeclaration baseClassDeclaration = classDeclAsClassOrContextDecl.baseClassDeclaration();
				if (null != baseClassDeclaration) {
					classScope = baseClassDeclaration.enclosedScope();
					retval = classScope.lookupMethodDeclarationIgnoringParameter(methodSelectorName, parameterList, "this",
							/* conversionAllowed = */ false);
				} else {
					retval = null;
				}
			}
		}
		return retval;
	}
	private void typeCheckHelperForRoleMismatches(final FormalParameterList formals, final ActualArgumentList actuals,
			final MethodDeclaration mdecl, final TypeDeclaration classdecl, final String parameterToIgnore,
			final Token ctxGetStart) {
		
		// This is for checking agreement with functions like assert
		
		int actualParameterIndex = 0, formalParameterIndex = 0;
		final int numberOfFormalParameters = formals.count(),
				  numberOfActualParameters = (null == actuals)? 0: actuals.count();
		if (null == actuals) {
			if (numberOfActualParameters != 0) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Number of arguments in enactment of script `", mdecl.name(),
						"' does not match declaration of `", mdecl.name() + "'.");
				int lineNumber = mdecl.lineNumber();
				if (0 == lineNumber) {
					// e.g., for a built-in type
					lineNumber = ctxGetStart.getLine();
				}
				errorHook5p2(ErrorIncidenceType.Fatal, lineNumber, "\tMethod ", mdecl.name(), " is declared in ", classdecl.name());
			}
		} else {
			// Strip off all the stuff up front
			String actualParameterName = actuals.nameOfParameterAtPosition(actualParameterIndex);
			while (actualParameterName.equals("this") || actualParameterName.equals("t$this") ||
					actualParameterName.equals("current$context") || actualParameterName.equals("current$role")) {
				actualParameterIndex++;
				if (actualParameterIndex < numberOfActualParameters) {
					actualParameterName = actuals.nameOfParameterAtPosition(actualParameterIndex);
				} else {
					break;
				}
			}
			
			String formalParameterName = formals.nameOfParameterAtPosition(formalParameterIndex);
			while (formalParameterName.equals("this") || formalParameterName.equals("t$this") ||
					formalParameterName.equals("current$context") || formalParameterName.equals("current$role")) {
				formalParameterIndex++;
				if (formalParameterIndex < numberOfFormalParameters) {
					formalParameterName = formals.nameOfParameterAtPosition(formalParameterIndex);
				} else {
					break;
				}
			}
				
			Expression actualParameter = null;
			Type actualParameterType = null;
			Declaration formalParameter = null;
			Type formalParameterType = null;
				
			while (formalParameterIndex < numberOfFormalParameters &&
					actualParameterIndex < numberOfActualParameters) {
				boolean parametersMatch = true;

				final Object rawActualParameter = actuals.argumentAtPosition(actualParameterIndex);
				if (rawActualParameter == null || (rawActualParameter instanceof Expression) == false) {
					assert rawActualParameter != null && rawActualParameter instanceof Expression;
				}
				actualParameter = (Expression)rawActualParameter;
				actualParameterType = actualParameter.type();
					
				formalParameterName = formals.nameOfParameterAtPosition(formalParameterIndex);
				formalParameter = formals.parameterAtPosition(formalParameterIndex);
				assert formalParameter instanceof ObjectDeclaration || formalParameter.isError();
				formalParameterType = formalParameter.type();
					
				if (actualParameterType.enclosedScope() == formalParameterType.enclosedScope()) {
					actualParameterIndex++; formalParameterIndex++;
				} else if (actualParameterType.isBaseClassOf(formalParameterType)) {
					actualParameterIndex++; formalParameterIndex++;
				} else if (formalParameterType.canBeConvertedFrom(actualParameterType)) {
					actualParameterIndex++; formalParameterIndex++;
				} else {
					final Type enclosingType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
					if (enclosingType instanceof TemplateType) {
						// It could just work. This is just the template we're processing. Check things out
						// later in the class instead.
						actualParameterIndex++; formalParameterIndex++;
					} else {
						parametersMatch = false;
					}
				}
					
				if (false == parametersMatch && actualParameter.isntError() && formalParameter.isntError()) {
					final String actualParamMsg = actualParameter.getText() + "' (" + actualParameterType.name() + ")";
					final String formalParamMsg = "`" + formalParameter.name() + "' (" + formalParameterType.name() + " " + formalParameter.name() + ")";
					errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Type of actual parameter `", actualParamMsg,
							" in call of `", mdecl.name(), "' does not match type of formal parameter ", formalParamMsg);
				}
			}
				
			if (formalParameterIndex != numberOfFormalParameters ||
						actualParameterIndex != numberOfActualParameters) {
				if (null != mdecl) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Number of arguments in enactment of script `", mdecl.name(),
						"' does not match declaration of `", mdecl.name() + "'.");
				}
					
				int lineNumber = null == mdecl ? 0 : mdecl.lineNumber();
				if (0 == lineNumber) {
					// e.g., for a built-in type
					lineNumber = ctxGetStart.getLine();
				}
				if (null != classdecl && null != mdecl) {
					errorHook5p2(ErrorIncidenceType.Fatal, lineNumber, "\tScript ", mdecl.name(), " is declared in ", classdecl.name());
				}	
			}
		}
	}
	
	protected void typeCheckIgnoringParameterNormal(final FormalParameterList formals, final ActualArgumentList actuals,
			final MethodDeclaration mdecl, final TypeDeclaration classdecl, final String parameterToIgnore,
			final Token ctxGetStart) {
		final long numberOfActualParameters = actuals.count();
		final long numberOfFormalParameters = formals.count();

		if (numberOfFormalParameters != numberOfActualParameters) {
			if (formals.containsVarargs()) {
				if (numberOfActualParameters < numberOfFormalParameters) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Number of arguments in enactment of script `", mdecl.name(),
							"' must be at least as many as in declaration of the script (", String.format("%d", numberOfFormalParameters-1) + ").");
				} else {
					;	// O.K.
				}
			} else {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Number of arguments in enactment of script `", mdecl.name(),
						"' does not match declaration of `", mdecl.name() + "'.");
				int lineNumber = mdecl.lineNumber();
				if (0 == lineNumber) {
					// e.g., for a built-in type
					lineNumber = ctxGetStart.getLine();
				}
				if (null != classdecl) {
					errorHook5p2(ErrorIncidenceType.Fatal, lineNumber, "\tMethod ", mdecl.name(), " is declared in ", classdecl.name());
				}
			}
		} else {

			for (int j = 0; j < numberOfActualParameters; j++) {
				final Object rawParameter = actuals.argumentAtPosition(j);
				assert rawParameter != null && rawParameter instanceof Expression;
				final Expression actualParameter = (Expression)rawParameter;
				final Type actualParameterType = actualParameter.type();

				final Declaration formalParameter = formals.parameterAtPosition(j);
				final Type formalParameterType = formalParameter.type();

				if (formalParameterType instanceof VarargsType) {
					break;
				} else if (null == formalParameterType) {
					;	// formalParameter is likely of ErrorDeclaration type — just skip it
				} else if (formalParameterType.canBeConvertedFrom(actualParameterType)) {
					continue;
				} else if (formalParameter.name().equals(parameterToIgnore)) {
					continue;
				} else {
					final Type enclosingType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
					if (enclosingType instanceof TemplateType) {
						// It could just work. This is just the template we're processing. Check things out
						// later in the class instead.
					} else {
						// See if it could be an interface of the class
						boolean isOK = false;
						if (actualParameterType instanceof ClassOrContextType) {
							final List<InterfaceType> interfaceTypes = ((ClassOrContextType)actualParameterType).interfaceTypes();
							for (final Type alternativeActualParameterType : interfaceTypes) {
								if (formalParameterType.canBeConvertedFrom(alternativeActualParameterType)) {
									isOK = true;
									break;
								}
							}
						}
						if (false == isOK && actualParameter.isntError() && formalParameter.isntError()) {
							final String actualParamMsg = actualParameter.getText() + "' (" + actualParameterType.name() + ")";
							final String formalParamMsg = "`" + formalParameter.name() + "' (" + formalParameterType.name() + " " + formalParameter.name() + ")";
							errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Type of actual parameter `", actualParamMsg,
									" in call of `", mdecl.name(), "' does not match type of formal parameter ", formalParamMsg);
						}
					}
				}	
			}
		}
	}
	@Override protected void typeCheckIgnoringParameter(final FormalParameterList formals, final ActualArgumentList actuals,
			final MethodDeclaration mdecl, final TypeDeclaration classdecl, final String parameterToIgnore,
			final Token ctxGetStart, final boolean roleHint) {
		
		// roleHint is set if the method was successfully found by turning off
		// any consideration of Role parameters, like current$context. This
		// typically applies in the ancillary lookup of the assert API in
		// class Object when invoked through a Role pointer.
		if (roleHint) {
			typeCheckHelperForRoleMismatches(formals, actuals, mdecl, classdecl, parameterToIgnore, ctxGetStart);
		} else {
			typeCheckIgnoringParameterNormal(formals, actuals, mdecl, classdecl, parameterToIgnore, ctxGetStart);
		}
	}

	private Expression canBeAScriptEnactment(final StaticScope megaTypeEnclosedScope, final String scriptName,
			final int lineNumber) {
		// Eiffel-style script (feature) enactment
		Expression retval = null;
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		if (megaTypeEnclosedScope instanceof StaticRoleScope) {
			final RoleDeclaration roleDecl = (RoleDeclaration) megaTypeEnclosedScope.associatedDeclaration();
			MethodSignature methodSignature = roleDecl.lookupRequiredMethodSignatureDeclaration(scriptName);
			if (null == methodSignature) {
				methodSignature = roleDecl.lookupPublishedSignatureDeclaration(scriptName);
			}
			if (null != methodSignature) {
				if (0 == methodSignature.formalParameterList().userParameterCount()) {
					// o.k.
					final ActualArgumentList argumentList = new ActualArgumentList();
					
					// There is no current$context for a published method declaration or a requires declaration.
					/*
					final Expression currentContext = new IdentifierExpression("current$context", roleDecl.contextDeclaration().type(),
							currentContext_.enclosedScope(), lineNumber);
					argumentList.addActualArgument(currentContext);
					*/
					final Expression self = new IdentifierExpression(roleDecl.name(), roleDecl.type(),
							currentContext_.enclosedScope(), lineNumber);
					argumentList.addActualArgument(self);
					
					final Message message = new Message(scriptName, argumentList, lineNumber, enclosingMegaType);
					final MethodInvocationEnvironmentClass originMethodClass = currentScope_.methodInvocationEnvironmentClass();
					final MethodInvocationEnvironmentClass targetMethodClass = self.type().enclosedScope().methodInvocationEnvironmentClass();
					retval = new MessageExpression(self, message, methodSignature.returnType(),
							lineNumber, /*isStatic*/ false, originMethodClass, targetMethodClass,
							true);
				}
			}
		} else if (null != megaTypeEnclosedScope && enclosingMegaType instanceof ContextType) {
			final ContextDeclaration contextDecl = (ContextDeclaration) enclosingMegaType.enclosedScope().associatedDeclaration();
			final ActualArgumentList argumentList = new ActualArgumentList();
			final Expression self = new IdentifierExpression("this", contextDecl.type(), currentScope_, lineNumber);
			argumentList.addActualArgument(self);
			final MethodDeclaration methodDeclaration = currentContext_.enclosedScope().lookupMethodDeclaration(scriptName, argumentList, false);
			if (null != methodDeclaration) {
				final MethodSignature methodSignature = methodDeclaration.signature();
				if (0 == methodSignature.formalParameterList().userParameterCount()) {
					// o.k. — Eiffel-style feature invocation
					final Message message = new Message(scriptName, argumentList, lineNumber, enclosingMegaType);
					final MethodInvocationEnvironmentClass originMethodClass = currentScope_.methodInvocationEnvironmentClass();
					final MethodInvocationEnvironmentClass targetMethodClass = self.type().enclosedScope().methodInvocationEnvironmentClass();
					retval = new MessageExpression(self, message, methodSignature.returnType(),
							lineNumber, /*isStatic*/ false, originMethodClass, targetMethodClass,
							true);
				}
			}
		}
		return retval;
	}
	
	@Override public Expression idExpr(final TerminalNode ctxJAVA_ID, final Token ctxGetStart) {
		// | JAVA_ID
		// Special version for pass 2 and 3
		
		Type type = null;
		Expression retval = null;
		RoleDeclaration aRoleDecl = null;
		StaticScope declaringScope = null;
		final StaticScope globalScope = StaticScope.globalScope();
		final String idText = ctxJAVA_ID.getText();
		ObjectDeclaration objectDecl = null;
		
		final ObjectDeclaration objdecl = currentScope_.lookupObjectDeclarationRecursive(idText);
		if (null != objdecl) {
			type = objdecl.type();
			declaringScope = objdecl.enclosingScope();
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
				final StaticScope enclosingMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
				final Declaration associatedDeclaration = declaringScope.associatedDeclaration();
				final IdentifierExpression self = new IdentifierExpression("this", associatedDeclaration.type(), enclosingMethodScope, ctxGetStart.getLine());
				retval = new QualifiedIdentifierExpression(self, idText, type);
				
				// Further checks
				this.ensureNotDuplicatedInBaseClass(associatedDeclaration, idText, ctxGetStart.getLine());
			} else {
				retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope, ctxGetStart.getLine());
			}
			
			
			// We don't want Role scope to be able to access symbols at
			// Context scope. Do a specific check for that and issue
			// a non-compliance warning
			
			final StaticScope currentProcScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
			if (null != currentProcScope) {
				final MethodDeclaration currentMethod = (MethodDeclaration)currentProcScope.associatedDeclaration();
				
				// Is it a Role method?
				final StaticScope methodsEnclosingScope = currentMethod.enclosingScope();
				final Declaration enclosingMegaTypeDeclaration = methodsEnclosingScope.associatedDeclaration();
				if (enclosingMegaTypeDeclaration instanceof RoleDeclaration) {
					final Declaration symbolsEnclosingMegaType = declaringScope.associatedDeclaration();
					if (symbolsEnclosingMegaType instanceof ContextDeclaration) {
						// Then there is code within the Role method accessing a symbol
						// in the surrounding Context. Maybe a no-no.
						errorHook6p2(ErrorIncidenceType.Noncompliant, ctxGetStart.getLine(),
								"NONCOMPLIANT: Attempt to access Context member `", idText,
								"' from within scope of Role script `",
								enclosingMegaTypeDeclaration.name() + "." + currentMethod.name(),
								"'. Roles may not directly access Context data. ",
								" Consider binding the Context to one of its own Roles instead.");
					}
				}
			}
			
			assert null != retval;
		} else if (null != currentScope_.lookupClassDeclarationRecursive(idText)) {
			// Could be a reference to a class itself (like System)
			type = StaticScope.globalScope().lookupTypeDeclaration("Class");
			declaringScope = StaticScope.globalScope();
			retval = new IdentifierExpression(idText, type, declaringScope, ctxGetStart.getLine());
		} else if (null != (aRoleDecl = super.isRoleAssignmentWithinContext(idText))) {
			type = aRoleDecl.type();
			declaringScope = aRoleDecl.enclosingScope();
			retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope, ctxGetStart.getLine());
		} else if (null != Expression.nearestEnclosingMegaTypeOf(currentScope_)
				&& null != Expression.nearestEnclosingMegaTypeOf(currentScope_).enclosedScope()
				&& null != (objectDecl = Expression.nearestEnclosingMegaTypeOf(currentScope_).enclosedScope().lookupObjectDeclarationRecursive(idText))) {
			// done — get outta here
			final IdentifierExpression self = new IdentifierExpression("this",		// name
					Expression.nearestEnclosingMegaTypeOf(currentScope_),			// type of identifier
					Expression.nearestEnclosingMethodScopeAround(currentScope_),	// scope where *declared*
					ctxGetStart.getLine());
			self.setResultIsConsumed(true);
			retval = new QualifiedIdentifierExpression(self, idText, objectDecl.type());
		} else if (null != Expression.nearestEnclosingMegaTypeOf(currentScope_)
				&& null != Expression.nearestEnclosingMegaTypeOf(currentScope_).enclosedScope()
				&& null != (aRoleDecl = Expression.nearestEnclosingMegaTypeOf(currentScope_).enclosedScope().lookupRoleOrStagePropDeclarationRecursive(idText))) {
			// done — get outta here
			final IdentifierExpression currentContext = new IdentifierExpression("current$context", Expression.nearestEnclosingMegaTypeOf(aRoleDecl.enclosedScope()),
					Expression.nearestEnclosingMethodScopeAround(currentScope_), ctxGetStart.getLine());
			currentContext.setResultIsConsumed(true);
			retval = new QualifiedIdentifierExpression(currentContext, idText, aRoleDecl.type());
		} else {
			final StaticScope possibleMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
			final StaticScope possibleRoleScope = null == possibleMethodScope? null: possibleMethodScope.parentScope();
			final StaticScope possibleContextScope = null ==  possibleRoleScope? null: possibleRoleScope.parentScope();
			final Declaration associatedDeclaration = null == possibleContextScope? null: possibleContextScope.associatedDeclaration();
			
			if (null != possibleRoleScope && (idText.equals("index") || idText.equals("lastIndex"))) {
				// It's O.K.
				final RoleDeclaration roleDeclaration = (RoleDeclaration)possibleRoleScope.associatedDeclaration();
				if (roleDeclaration.isArray()) {
					retval = idText.equals("index")?
							new IndexExpression(roleDeclaration, (ContextDeclaration)associatedDeclaration):
								new LastIndexExpression(roleDeclaration, (ContextDeclaration)associatedDeclaration);
				} else {
					retval = new ErrorExpression(null);
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
							"The identifier `", idText, "' may be invoked only from a method of an element of a Role vector.", "");
				}
			} else if (associatedDeclaration == currentContext_) {
				if (null != possibleContextScope) {
					final RoleDeclaration roleDecl = possibleContextScope.lookupRoleOrStagePropDeclaration(idText);
					if (null == roleDecl) {
						// Check to see if it is a script invocation
						if (null == (retval = canBeAScriptEnactment(possibleRoleScope, idText, ctxGetStart.getLine()))) {
							errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Object `", idText, 
									"' is not declared in scope `", currentScope_.name(), "'.", "");
							type = new ErrorType();
							retval = new ErrorExpression(null);
						}
					} else {
						// it's O.K. - maybe. Can be used as an L-value in an assignment. R-value, too, I guess
						type = possibleContextScope.lookupTypeDeclaration(idText);
						declaringScope = roleDecl.enclosingScope();
						retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope, ctxGetStart.getLine());
					}
				} else {
					errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Object `", idText, 
							"' is not declared in scope `", currentScope_.name(), "'.", "");
					type = new ErrorType();
					retval = new IdentifierExpression(ctxJAVA_ID.getText(), type, declaringScope, ctxGetStart.getLine());
					retval = new ErrorExpression(retval);
				}
			} else {
				// Could be a base class reference
				retval = this.lookToBaseClassForHelp(idText, ctxGetStart.getLine(), currentScope_);
				
				// That was about the last chance
				if (null == retval) {
					// How about believing it could be a method call?
					retval = canBeAScriptEnactment(possibleContextScope, idText, ctxGetStart.getLine());
					if (null == retval) {
						errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Object `", idText,
								"' is not declared in scope `", currentScope_.name(), "'.", "");
						type = new ErrorType();
						retval = new ErrorExpression(null);
					}
				}
			}
		}
		return retval;
    }

	private void ensureNotDuplicatedInBaseClass(final Declaration associatedDeclaration, final String idName, int lineNumber) {
		if (associatedDeclaration instanceof ClassDeclaration) {
			// See if there is a base declaration
			final ClassDeclaration cdecl = (ClassDeclaration)associatedDeclaration;
			final ClassDeclaration baseClassDeclaration = cdecl.baseClassDeclaration();
			if (null != baseClassDeclaration) {
				// See if the base class also has this name
				final StaticScope baseClassScope = baseClassDeclaration.enclosedScope();
				final ObjectDeclaration baseClassInstance = baseClassScope.lookupObjectDeclaration(idName);
				if (null != baseClassInstance) {
					// Hmmm. It also exists in the base class
					final String lastPartOfMessage = baseClassDeclaration.name() + "'.";
					errorHook6p2(ErrorIncidenceType.Fatal, lineNumber, "Object declaration `", idName,
							"' appears both in class `",
							associatedDeclaration.name(), "' and in base class `", lastPartOfMessage);
					errorHook5p2(ErrorIncidenceType.Fatal, lineNumber, "  (The same identifier name may not appear multiple times in the same run-time scope.)",
							"", "", "");
				}
			}
		}
	}
	
	private Expression lookToBaseClassForHelp(final String idName, int lineNumber, StaticScope scope) {
		Expression retval = null;
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(scope);
		StaticScope megaTypeScope = null;
		if (null != enclosingMegaType) {
			megaTypeScope = enclosingMegaType.enclosedScope();
		} else {
			megaTypeScope = StaticScope.globalScope();
		}
		final Declaration associatedDeclaration = megaTypeScope.associatedDeclaration();
		if (associatedDeclaration instanceof ClassDeclaration) {
			// See if there is a base declaration
			final ClassDeclaration cdecl = (ClassDeclaration)associatedDeclaration;
			final ClassDeclaration baseClassDeclaration = cdecl.baseClassDeclaration();
			if (null != baseClassDeclaration) {
				// See if the base class also has this name
				final StaticScope baseClassScope = baseClassDeclaration.enclosedScope();
				final ObjectDeclaration baseClassInstance = baseClassScope.lookupObjectDeclaration(idName);
				if (null != baseClassInstance) {
					// Good. It also exists in the base class
					if (baseClassInstance.accessQualifier_ == AccessQualifier.PublicAccess) {
						final Type type = baseClassInstance.type();
						final StaticScope nearestEnclosingMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
						final IdentifierExpression self = new IdentifierExpression("this", type, nearestEnclosingMethodScope, lineNumber);
						self.setResultIsConsumed(true);
						retval = new QualifiedIdentifierExpression(self, idName, type);
					} else {
						errorHook6p2(ErrorIncidenceType.Fatal, lineNumber, "Symbol `", idName,
								"' is not public and so is not accessible to `", associatedDeclaration.name(), "'.", "");
					}
				} else {
					final ClassDeclaration nextBaseClassDeclaration = baseClassDeclaration.baseClassDeclaration();
					if (null == nextBaseClassDeclaration) {
						errorHook5p2(ErrorIncidenceType.Fatal, lineNumber, "Symbol `", idName, "' cannot be found.", "");
					} else {
						// Recur
						retval = this.lookToBaseClassForHelp(idName, lineNumber, nextBaseClassDeclaration.enclosedScope());
					}
				}
			}
		}
		return retval;
	}
	
	@Override protected void updateTypesAccordingToPass(final /*Class*/Type type, final List<String> typeNameList) {
		// This little ditty is mainly for Pass2. It updates the template
		// instantiation information of classes like List<Foobar> where Foobar
		// is a forward type reference. Normally, template stuff can't take
		// advantage of type information again until Pass4 when templates
		// are fully instantiated. This is a bit of a cheat that at least
		// makes the template parameter mappings current, so that the appearance
		// of a template-typed script parameter will match properly on
		// lookup. It helps avoid some false warnings about type mismatches
		// when in fact there are none. Perhaps a rare occurrence (forward
		// references, templates, etc.) but nonetheless part of what we must
		// deal with....

		final StaticScope templateScope = null != type? type.enclosedScope(): null;
		final TemplateInstantiationInfo currentTemplateInstantiationInfo = null != templateScope?
				templateScope.templateInstantiationInfo(): null;
		final TemplateDeclaration templateDeclaration = null != currentTemplateInstantiationInfo?
				currentTemplateInstantiationInfo.templateDeclaration(): null;
		final TemplateInstantiationInfo newTemplateInstantiationInfo = null != type?
				new TemplateInstantiationInfo(templateDeclaration, type.name()):
					null;
		
		if (null != currentTemplateInstantiationInfo) {
			newTemplateInstantiationInfo.setClassType(currentTemplateInstantiationInfo.classType());
		
			for (final String typeName: typeNameList) {
				final Type templateParameterType = currentScope_.lookupTypeDeclarationRecursive(typeName);
				newTemplateInstantiationInfo.add(templateParameterType);
			}
			
			templateScope.resetTemplateInstationInfo(newTemplateInstantiationInfo);
		}
	}
	
	@Override protected ClassDeclaration lookupOrCreateClassDeclaration(final String name, final ClassDeclaration rawBaseClass, final ClassType baseType, final int lineNumber) {
		final ClassDeclaration newClass = currentScope_.lookupClassDeclarationRecursive(name);
		final Type rawClass = newClass.type();
		assert rawClass instanceof ClassType;
		final ClassType classType = (ClassType)rawClass;
		classType.updateBaseType(baseType);
		return newClass;
	}
	@Override protected void declareTypeSuitableToPass(final StaticScope scope, final Type decl) {
		/* Nothing */
	}
	@Override protected void declareObjectSuitableToPass(final StaticScope scope, final ObjectDeclaration objDecl) {
		if (scope.hasDeclarationsThatAreLostBetweenPasses()) {
			// e.g., a FOR Loop or a Block
			scope.declareObject(objDecl, this);
		} else {
			// most of the time...
			; 		/* Nothing */
		}
	}
	@Override protected void declareFormalParametersSuitableToPass(final StaticScope scope, final ObjectDeclaration objDecl) {
		scope.reDeclareObject(objDecl);
	}
	@Override protected void addSignatureSuitableToPass(final InterfaceType interfaceType, final MethodSignature signature) {
		interfaceType.addSignature(signature);
	}
	@Override protected void addInterfaceTypeSuitableToPass(final ClassOrContextType classOrContextType, final InterfaceType interfaceType) {
		classOrContextType.addInterfaceType(interfaceType);
	}
	@Override protected void implementsCheck(final ClassOrContextDeclaration newDeclaration, final int lineNumber) {
		newDeclaration.doIImplementImplementsList(this, lineNumber);
	}
	@Override protected void errorHook5p1(final ErrorIncidenceType errorType, final int i, final String s1, final String s2, final String s3, final String s4) {
		/* Nothing */
	}
	@Override protected void errorHook6p1(final ErrorIncidenceType errorType, final int i, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		/* Nothing */
	}	
	@Override public void errorHook5p2(final ErrorIncidenceType errorType, final int i, final String s1, final String s2, final String s3, final String s4) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4);
	}
	@Override public void errorHook6p2(final ErrorIncidenceType errorType, final int i, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4, s5, s6);
	}
	@Override protected void updateInitializationLists(final Expression initializationExpr, final ObjectDeclaration objDecl) {
		// It actually is right that one of these is an add and one is an insert...
		// Same version for pass 2, 3, and 4
		initializationExpressions_.add(initializationExpr);
		variablesToInitialize_.insertAtStart(objDecl);
	}
	@Override public ObjectDeclaration pass1InitialDeclarationCheck(final String name, final int lineNumber) {
		final ObjectDeclaration objDecl = currentScope_.lookupObjectDeclaration(name);
		// It's been declared, so multiple declarations aren't an error
		return objDecl;
	}
	@Override protected void reportMismatchesWith(final int lineNumber, final RoleType lhsType, final Type rhsType) {
		lhsType.reportMismatchesWith(lineNumber, rhsType);
	}
	
	@Override protected void checkForAssignmentViolatingConstness(final AssignmentExpression assignment, final Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			final Expression assignee = assignment.lhs();
			checkLhsForAssignmentViolatingConstness(assignee, enclosingMethod, ctxGetStart);
		}
	}
	
	@Override protected void checkForIncrementOpViolatingConstness(final ArrayIndexExpressionUnaryOp expression, final Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			// We can have no idea where the array base is "pointing," so we have
			// to deny such expressions
			errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
					"Modification of array ", expression.getText(),
					" from within const method ",
					enclosingMethod.name(), ", which violates the const modifier of the latter.", "");
		}
	}
	
	@Override protected void checkForIncrementOpViolatingIdentifierConstness(final UnaryopExpressionWithSideEffect id, final Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			checkLhsForAssignmentViolatingConstness(id.lhs(), enclosingMethod, ctxGetStart);
		}
	}
	
	private void checkLhsForAssignmentViolatingConstness(final Expression assignee, final MethodDeclaration enclosingMethod, final Token ctxGetStart) {
		if (assignee instanceof IdentifierExpression) {
			final Declaration idDecl = currentScope_.lookupObjectDeclarationRecursiveWithinMethod(assignee.name());
			if (null == idDecl) {
				// Then it's not on the activation record
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
						"Assignment statement violates constness of declaration of ",
						enclosingMethod.name(), "", "");
			}
		} else if (assignee instanceof QualifiedIdentifierExpression) {
			// We're assigning to something within "qualifier." That doesn't immediately
			// disqualify it - could be a locally created object.
			errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
					"Assignment statement modifies a member object that could be shared; ",
					"that violates the constness of ",
					enclosingMethod.name(), "");
		} else if (assignee instanceof QualifiedClassMemberExpression) {
			// Certainly off-limits
			errorHook5p2(ErrorIncidenceType.Warning, ctxGetStart.getLine(),
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
			errorHook5p2(ErrorIncidenceType.Warning, ctxGetStart.getLine(),
					"WARNING: Assignment statement modifies an array member that could be shared; ",
					"that violates the constness of `",
					enclosingMethod.name(), "'.");
		}
	}
	
	protected void checkForMessageSendViolatingConstness(final MethodSignature signature, final Token ctxGetStart) {
		final MethodDeclaration enclosingMethod = super.methodWithinWhichIAmDeclared(currentScope_);
		if (null != enclosingMethod && enclosingMethod.isConst()) {
			if (signature.hasConstModifier()) {
				; // it's O.K. - this is a const method
			} else {
				errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(),
						"Call of non-const method ", signature.name(),
						" from within const method ",
						enclosingMethod.name(), ", which violates the const modifier of the latter.", "");
			}
		}
	}

    // -------------------------------------------------------------------------------------------------------

	// WARNING. Tricky code here
	@Override public void declareObject(final StaticScope s, final ObjectDeclaration objdecl) {
		s.declareObject(objdecl, this);
	}
	@Override public void declareRoleOrStageProp(final StaticScope s, final RoleDeclaration roledecl, final int lineNumber) {
		s.declareRoleOrStageProp(roledecl);	// probably redundant; done in pass 1
	}
	private void processDeclareRoleArrayAlias(final int lineNumber) {
		// Declare an actual object for the Role, if the Role is a RoleArray type
		if (currentRoleOrStageProp_.isArray()) {
			final String roleName = currentRoleOrStageProp_.type().getText();
			
			// Then declare an array base handle for it as well
			final String compoundName = roleName + "_$array";
			Type newType = currentScope_.lookupTypeDeclarationRecursive(compoundName);
			if (null == newType) {
				newType = new ArrayType(compoundName, currentRoleOrStageProp_.type());
				final ContextDeclaration contextDeclaration = currentRoleOrStageProp_.contextDeclaration();
				final StaticScope contextScope = contextDeclaration.type().enclosedScope();
				contextScope.declareType(newType);
				
				final ObjectDeclaration baseArrayObject = new ObjectDeclaration(compoundName, newType, lineNumber);
				contextScope.declareObject(baseArrayObject, this);
			}
		}
	}

	protected ActualArgumentList currentArgumentList() { return parsingData_.currentArgumentList(); }
}