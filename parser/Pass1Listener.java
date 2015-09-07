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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import parser.KantBaseListener;
import parser.KantParser.Abelian_exprContext;
import parser.KantParser.BlockContext;
import parser.KantParser.Compound_type_nameContext;
import parser.KantParser.Do_while_exprContext;
import parser.KantParser.ExprContext;
import parser.KantParser.Expr_and_decl_listContext;
import parser.KantParser.For_exprContext;
import parser.KantParser.Identifier_listContext;
import parser.KantParser.MessageContext;
import parser.KantParser.Method_declContext;
import parser.KantParser.Method_decl_hookContext;
import parser.KantParser.Method_signatureContext;
import parser.KantParser.Switch_exprContext;
import parser.KantParser.Type_declarationContext;
import parser.KantParser.ProgramContext;
import parser.KantParser.While_exprContext;
import declarations.ActualArgumentList;
import declarations.ActualOrFormalParameterList;
import declarations.BodyPart;
import declarations.Declaration;
import declarations.AccessQualifier;
import declarations.Declaration.DeclarationList;
import declarations.Declaration.ExprAndDeclList;
import declarations.Declaration.MethodSignature;
import declarations.Declaration.TypeDeclarationList;
import declarations.Type;
import declarations.Type.ContextType;
import declarations.Type.ClassType;
import declarations.Type.RoleType;
import declarations.Declaration.ContextDeclaration;
import declarations.Declaration.ClassDeclaration;
import declarations.Declaration.RoleDeclaration;
import declarations.Declaration.StagePropDeclaration;
import declarations.Declaration.MethodDeclaration;
import declarations.Declaration.ObjectDeclaration;
import declarations.Message;
import declarations.FormalParameterList;
import declarations.Type.StagePropType;
import declarations.TypeDeclaration;
import declarations.Type.ArrayType;
import semantic_analysis.Program;
import semantic_analysis.StaticScope;
import semantic_analysis.StaticScope.StaticRoleScope;
import error.ErrorLogger;
import error.ErrorLogger.ErrorType;
import expressions.Constant;
import expressions.Constant.BooleanConstant;
import expressions.Expression;
import expressions.Expression.ArrayExpression;
import expressions.Expression.ArrayIndexExpression;
import expressions.Expression.ArrayIndexExpressionUnaryOp;
import expressions.Expression.DoWhileExpression;
import expressions.Expression.IfExpression;
import expressions.Expression.UnaryopExpressionWithSideEffect;
import expressions.Expression.BlockExpression;
import expressions.Expression.RelopExpression;
import expressions.Expression.QualifiedIdentifierExpression;
import expressions.Expression.QualifiedIdentifierExpressionUnaryOp;
import expressions.Expression.QualifiedClassMemberExpression;
import expressions.Expression.QualifiedClassMemberExpressionUnaryOp;
import expressions.Expression.MessageExpression;
import expressions.Expression.DupMessageExpression;
import expressions.Expression.IdentifierExpression;
import expressions.Expression.AssignmentExpression;
import expressions.Expression.NewExpression;
import expressions.Expression.NewArrayExpression;
import expressions.Expression.NullExpression;
import expressions.Expression.SumExpression;
import expressions.Expression.ProductExpression;
import expressions.Expression.PowerExpression;
import expressions.Expression.ForExpression;
import expressions.Expression.ReturnExpression;
import expressions.Expression.SwitchBodyElement;
import expressions.Expression.SwitchExpression;
import expressions.Expression.BreakExpression;
import expressions.Expression.ContinueExpression;
import expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import expressions.Expression.WhileExpression;
import expressions.Expression.UnaryAbelianopExpression;
import expressions.ExpressionStackAPI;
import mylibrary.SimpleList;


public class Pass1Listener extends KantBaseListener {
	private boolean printProductionsDebug;
	private boolean stackSnapshotDebug;
	
	public Pass1Listener(ParsingData parsingData) {
		parsingData_ = parsingData;
		
		currentScope_ = parsingData_.globalScope();
		currentContext_ = null;
		variableGeneratorCounter_ = 101;
		
		printProductionsDebug = false;
		stackSnapshotDebug = false;
	}
	
	private static class DeclarationsAndInitializers {
		public DeclarationsAndInitializers(List<ObjectDeclaration> objectDecls, List<BodyPart> initializations) {
			objectDecls_ = objectDecls;
			initializations_ = initializations;
		}
		public List<ObjectDeclaration> objectDecls() {
			return objectDecls_;
		}
		public List<BodyPart> initializations() {
			return initializations_;
		}
		private final List<ObjectDeclaration> objectDecls_;
		private final List<BodyPart> initializations_;
	}
	
	// -----------------------------------------------------------------------------------
	
	@Override public void enterProgram(@NotNull KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// : type_declaration_list
		
		final TypeDeclarationList currentList = new TypeDeclarationList(ctx.getStart().getLine());
		parsingData_.pushTypeDeclarationList(currentList);
		
		if (printProductionsDebug) {
			if (null != ctx.main()) {
				System.err.println("program : type_declaration_list main");
			} else {
				System.err.println("program : type_declaration_list [ERROR]");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitProgram(@NotNull KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// | type_declaration_list
		if (null == ctx.main()) {
			if (null != ctx.getStop()) {
				errorHook5p2(ErrorType.Fatal, ctx.getStop().getLine(), "Missing main expression.", "", "", "");
			} else {
				errorHook5p2(ErrorType.Fatal, 1, "Missing main expression.", " Did you enter any program at all?", "", "");
			}
			final TypeDeclarationList currentList = parsingData_.popTypeDeclarationList();
			new Program(null, currentList);	// static singleton
		} else {
			final TypeDeclarationList currentList = parsingData_.popTypeDeclarationList();
			final Expression main = parsingData_.popExpression();
			new Program(main, currentList);	// static singleton
			if (printProductionsDebug) System.err.println("program : type_declaration_list main");
			if (stackSnapshotDebug) stackSnapshotDebug();
			
			printProductionsDebug = false;
			stackSnapshotDebug = false;
		}
	}
	
	@Override public void enterMain(@NotNull KantParser.MainContext ctx)
	{
		// main
		//	: expr
		
		/* nothing */
	}
	
	@Override public void exitMain(@NotNull KantParser.MainContext ctx)
	{
		// main
		//	: expr
		
		if (printProductionsDebug) {
			System.err.println("main : expr");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterType_declaration_list(@NotNull KantParser.Type_declaration_listContext ctx)
	{
		// type_declaration_list
	    //		: type_declaration
	    //		| type_declaration_list type_declaration
	    // 		| /* null */
		
		/* nothing */
	}

	@Override public void enterType_declaration(@NotNull KantParser.Type_declarationContext ctx)
	{
        // : 'context' JAVA_ID '{' context_body '}'
        // | 'class'   JAVA_ID '{' class_body   '}'
        // | 'class'   JAVA_ID 'extends' JAVA_ID '{' class_body   '}'
		
		final String name = ctx.JAVA_ID(0).getText();
		ClassDeclaration rawBaseClass = null;
		if (null != ctx.context_body()) {
			currentContext_ = this.lookupOrCreateContextDeclaration(name, ctx.getStart().getLine());
		} else if (null != ctx.class_body()) {
			// TODO: Should default base type be null, or Object?
			ClassType baseType = null;
			final TerminalNode baseClassNode = ctx.JAVA_ID(1);
			
			if (null != baseClassNode) {
				final String baseTypeName = baseClassNode.getText();
				final Type rawBaseType = currentScope_.lookupTypeDeclarationRecursive(baseTypeName);
				rawBaseClass = currentScope_.lookupClassDeclarationRecursive(baseTypeName);
				if ((rawBaseType instanceof ClassType) == false) {
					// Leave to pass 2
					errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Base type ", baseTypeName, " is not a declared class type as base of ", name);
				} else {
					baseType = (ClassType)rawBaseType;
				}
			} else {
				// Redundant: for readability...
				rawBaseClass = null;
			}
			
			final ClassDeclaration newClass = this.lookupOrCreateClassDeclaration(name, rawBaseClass, baseType, ctx.getStart().getLine());
			currentScope_ = newClass.enclosedScope();
			parsingData_.pushClassDeclaration(newClass);
		} else {
			assert false;	// need diagnostic message for user later
		}
	}
	
	@Override public void exitType_declaration(@NotNull KantParser.Type_declarationContext ctx)
	{
		// : 'context' JAVA_ID '{' context_body '}'
        // | 'class'   JAVA_ID '{' class_body   '}'
        // | 'class'   JAVA_ID 'extends' JAVA_ID '{' class_body  '}'

		final TypeDeclaration newDeclaration = (TypeDeclaration)currentScope_.associatedDeclaration();
		assert newDeclaration instanceof TypeDeclaration;
		parsingData_.currentTypeDeclarationList().addDeclaration(newDeclaration);
		
		if (null != currentScope_) {
			final StaticScope parentScope = currentScope_.parentScope();
			currentScope_ = parentScope;
		}
		if (null != currentContext_) {
			currentContext_ = currentContext_.parentContext();
		}
		if (printProductionsDebug) {
			if (ctx.context_body() != null) {
				System.err.println("type_declaration : 'context' JAVA_ID '{' context_body '}'");
			} else if (ctx.class_body() != null && ctx.JAVA_ID(1) == null) {
				System.err.println("type_declaration : 'class' JAVA_ID '{' class_body   '}'");
			} else {
				System.err.println("type_declaration : 'class' JAVA_ID 'extends' JAVA_ID '{' class_body  '}'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterContext_body(@NotNull KantParser.Context_bodyContext ctx)
	{
		// context_body
	    //		: context_body context_body_element
	    //		| context_body_element
	    //		| /* null */
		
		/* nothing */
	}

	@Override public void enterContext_body_element(@NotNull KantParser.Context_body_elementContext ctx)
	{
		// context_body_element
	    //	: method_decl
	    //	| object_decl
	    //	| role_decl
	    //	| stageprop_decl
		
		/* nothing */
	}

	@Override public void enterRole_decl(@NotNull KantParser.Role_declContext ctx)
	{
		// : 'role' JAVA_ID '{' role_body '}'
		// | 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		//
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		if (null != ctx.access_qualifier()) {
			errorHook5p1(ErrorType.Warning, ctx.getStart().getLine(), "Gratuitous access qualifier `",
					ctx.access_qualifier().getText(), "« ignored", " (warning).");
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role Ñ resulting grammar error got here with that
			// null condition. Not much to do but to punt

			final String roleName = JAVA_ID.getText();
		
			// Return value is through currentRole_
			lookupOrCreateRoleDeclaration(roleName, ctx.getStart().getLine());
		
			assert null != currentRole_;
		
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorType.Fatal, ctx.getStart().getLine(), "Role ", roleName, " can be declared only in a Context scope Ñ not ", currentScope_.name());
			}
			currentScope_ = currentRole_.enclosedScope();
		} else {
			currentRole_ = null;
		}
	}
	
	@Override public void exitRole_decl(@NotNull KantParser.Role_declContext ctx)
	{
		// : 'role' JAVA_ID '{' role_body '}'
		// | 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		
		// In the self_methods rule we've been gathering signatures
		// in the "requires" section and storing them in the Role
		// declaration. Just tell the type about the declaration so
		// that type checking can track it back.
		if (null != currentRole_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See
			// the comment above on entry to the production.

			final RoleType type = (RoleType)currentRole_.type();
			assert type instanceof RoleType;
			type.setBacklinkToRoleDecl(currentRole_);

			currentRole_ = null;
			currentScope_ = currentScope_.parentScope();
		}
		if (printProductionsDebug) {
			if (ctx.self_methods() == null && ctx.access_qualifier() == null) {
				System.err.println("role_decl : 'role' JAVA_ID '{' role_body '}'");
			} else if(ctx.self_methods() != null && ctx.access_qualifier() == null) {
				System.err.println("role_decl : 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			} else if(ctx.self_methods() == null && ctx.access_qualifier() != null) {
				System.err.println("role_decl : access_qualifier 'role' JAVA_ID '{' role_body '}'");
			} else {
				System.err.println("role_decl : access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterRole_body(@NotNull KantParser.Role_bodyContext ctx)
	{
		// role_body
	    //	: method_decl
	    //	| role_body method_decl
	    //	| object_decl				// illegal
	    //	| role_body object_decl		// illegal Ñ for better error messages only
		
		/* nothing */
	}
	
	@Override public void exitRole_body(@NotNull KantParser.Role_bodyContext ctx)
	{
		// : method_decl
        // | role_body method_decl
        // | object_decl				// illegal
        // | role_body object_decl		// illegal Ñ for better error messages only
		
		if (null != ctx.object_decl()) {
			@SuppressWarnings("unused")
			final DeclarationList objectDecl = parsingData_.popDeclarationList();
			// We have issued an error message about this already elsewhere
		}
		
		if (printProductionsDebug) {
			if (ctx.role_body() == null && ctx.method_decl() != null) {
				System.err.println("role_body : method_decl");
			} else if(ctx.role_body() != null && ctx.method_decl() != null) {
				System.err.println("role_body : role_body method_decl");
			} else if(ctx.role_body() == null && ctx.object_decl() != null) {
				System.err.println("role_body : object_decl");
			} else {
				System.err.println("role_body : role_body object_decl");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterSelf_methods(@NotNull KantParser.Self_methodsContext ctx)
	{
		// : self_methods ';' method_signature
		// | method_signature
		// | self_methods /* null */ ';'
		
		parsingData_.pushFormalParameterList(new FormalParameterList());
	}
	
	@Override public void exitSelf_methods(@NotNull KantParser.Self_methodsContext ctx)
	{
		// : self_methods ';' method_signature
		// | method_signature
		// | self_methods /* null */ ';'
		
		final Method_signatureContext contextForSignature = ctx.method_signature();
		if (null != contextForSignature) {
			final MethodSignature signature = parsingData_.popMethodSignature();
			final FormalParameterList plInProgress = parsingData_.popFormalParameterList();
		
			// Add a declaration of "this." These are class instance methods, never
			// role methods, so there is no need to add a current$context argument
			final ObjectDeclaration self = new ObjectDeclaration("this", currentRole_.type(), ctx.getStart().getLine());
			plInProgress.addFormalParameter(self);
		
			signature.addParameterList(plInProgress);
			currentRole_.addRequiredSignatureOnSelf(signature);
		}
		
		if (printProductionsDebug) {
			if (ctx.self_methods() != null) {
				System.err.println("self_methods : self_methods ';' method_signature");
			} else {
				System.err.println("self_methods : method_signature");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterStageprop_decl(@NotNull KantParser.Stageprop_declContext ctx)
	{
		// stageprop_decl
		//	: 'stageprop' JAVA_ID '{' stageprop_body '}'
		//	| 'stageprop' JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		//	| access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}'
		//	| access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		if (null != ctx.access_qualifier()) {
			errorHook5p1(ErrorType.Warning, ctx.getStart().getLine(), "Gratuitous access qualifier `",
					ctx.access_qualifier().getText(), "« ignored", " (warning).");
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role Ñ resulting grammar error got here with that
			// null condition. Not much to do but to punt
			
			final String stagePropName = JAVA_ID.getText();
			lookupOrCreateStagePropDeclaration(stagePropName, ctx.getStart().getLine());
			
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorType.Fatal, ctx.getStart().getLine(), "Stageprop ", stagePropName, " can be declared only in a Context scope Ñ not ", currentScope_.name());
			}
			currentScope_ = currentRole_.enclosedScope();
		} else {
			currentRole_ = null;
		}
	}

	@Override public void exitStageprop_decl(@NotNull KantParser.Stageprop_declContext ctx)
	{
		// stageprop_decl
		//	: 'stageprop' JAVA_ID '{' stageprop_body '}'
		//	| 'stageprop' JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		//	| access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}'
		//	| access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		
		if (null != currentRole_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See comment
			// elsewhere (in exitRole_decl?)

			final StagePropType type = (StagePropType)currentRole_.type();
			assert type instanceof StagePropType;
			type.setBacklinkToRoleDecl(currentRole_);
			
			// Make sure self_methods are const
			if (currentRole_.requiresConstMethods()) {
				final Map<String, MethodSignature> requiredSelfSignatures = currentRole_.requiredSelfSignatures();
				for (Map.Entry<String, MethodSignature> iter : requiredSelfSignatures.entrySet()) {
					final String methodName = iter.getKey();
					final MethodSignature signature = iter.getValue();
					if (signature.hasConstModifier() == false) {
						errorHook6p2(ErrorType.Warning, ctx.getStart().getLine(),
								"Signatures for functions required by stageprops like ", currentRole_.name(),
								" should have a const modifier: method ", methodName, " does not.", "");
					}
				}
			}

			currentRole_ = null;
			currentScope_ = currentScope_.parentScope();
		}

		if (printProductionsDebug) {
			if (ctx.self_methods() == null && ctx.access_qualifier() == null) {
				System.err.println("stageprop_decl : 'role' JAVA_ID '{' role_body '}'");
			} else if(ctx.self_methods() != null && ctx.access_qualifier() == null) {
				System.err.println("stageprop_decl : 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			} else if(ctx.self_methods() == null && ctx.access_qualifier() != null) {
				System.err.println("stageprop_decl : access_qualifier 'role' JAVA_ID '{' stageprop_body '}'");
			} else {
				System.err.println("stageprop_decl : access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterStageprop_body(@NotNull KantParser.Stageprop_bodyContext ctx) {
		// stageprop_body
	    //	: method_decl
	    //	| stageprop_body method_decl
	    
		/* nothing */
	}
	
	@Override public void exitStageprop_body(@NotNull KantParser.Stageprop_bodyContext ctx) {
		// stageprop_body
	    //	: method_decl
	    //	| stageprop_body method_decl
		//  | object_decl
		//  | stageprop_body object_decl
		
		if (null != ctx.object_decl()) {
			@SuppressWarnings("unused")
			final DeclarationList objectDecl = parsingData_.popDeclarationList();
			// We have issued an error message about this already elsewhere
		}
		
		if (printProductionsDebug) {
			if (ctx.stageprop_body() == null && ctx.method_decl() != null) {
				System.err.println("stageprop_body : method_decl");
			} else if(ctx.stageprop_body() != null && ctx.method_decl() != null) {
				System.err.println("stageprop_body : role_body method_decl");
			} else if(ctx.stageprop_body() == null && ctx.object_decl() != null) {
				System.err.println("stageprop_body : object_decl");
			} else {
				System.err.println("stageprop_body : role_body object_decl");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterClass_body(@NotNull KantParser.Class_bodyContext ctx) {
		// class_body
	    //	: class_body class_body_element
	    //	| class_body_element
	    //	| /* null */
	    
		/* nothing */
	}

	@Override public void enterClass_body_element(@NotNull KantParser.Class_body_elementContext ctx) {
		// class_body_element
	    //	: method_decl
	    //	| object_decl
	    
		/* nothing */
	}

	@Override public void enterMethod_decl(@NotNull KantParser.Method_declContext ctx)
	{
		// : method_decl_hook '{' expr_and_decl_list '}'
		
		// This one seems different enough that we'll have our own pass 2 version.
		// In Pass 1, get just enough to define the scope for the enclosed
		// declarations.
		
		// Set up the block
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart().getLine());
		parsingData_.pushExprAndDecl(newList);

		final int lineNumber = ctx.getStart().getLine();
		final StaticScope newScope = new StaticScope(currentScope_);
		final Method_decl_hookContext declHookContext = ctx.method_decl_hook();
		final Method_signatureContext signatureCtx = declHookContext.method_signature();
		final String methodSelector = signatureCtx.method_name().getText();
		final String access_level_string = signatureCtx.access_qualifier().getText();
		final AccessQualifier accessQualifier = AccessQualifier.accessQualifierFromString(access_level_string);
		Type returnType = StaticScope.globalScope().lookupTypeDeclaration("void");
		if (currentScope_.associatedDeclaration() instanceof ContextDeclaration ||
				currentScope_.associatedDeclaration() instanceof ClassDeclaration) {
			if (methodSelector.equals(currentScope_.name())) {
				returnType = null;
			}
		}
		final FormalParameterList pl = new FormalParameterList();
		
		// No signature on the parsingData_.methodSignature() stack yet, since
		// we're just entering the production. The MethodDeclaration object will
		// create its own default MethodSignature.
		final MethodDeclaration currentMethod = new MethodDeclaration(methodSelector, newScope, returnType,
				accessQualifier, lineNumber);
		currentMethod.addParameterList(pl);
		
		newScope.setDeclaration(currentMethod);
		
		currentScope_.declareMethod(currentMethod);
		currentScope_ = newScope;
		
		parsingData_.pushFormalParameterList(pl);
	}
	
	@Override public void exitMethod_decl_hook(@NotNull KantParser.Method_decl_hookContext ctx)
	{
		// method_decl_hook
		//	: method_signature

		final Declaration associatedDeclaration = currentScope_.parentScope().associatedDeclaration();
		final Type classOrRoleOrContextType = associatedDeclaration.type();
		
		boolean isRoleMethodInvocation = false;
		
		assert  classOrRoleOrContextType instanceof ClassType || 
				(isRoleMethodInvocation = classOrRoleOrContextType instanceof RoleType) ||
				classOrRoleOrContextType instanceof ContextType;
		
		// Add declaration of "this" as a formal parameter
		final ObjectDeclaration self = new ObjectDeclaration("this", classOrRoleOrContextType, ctx.getStart().getLine());
		parsingData_.currentFormalParameterList().addFormalParameter(self);
		
		if (isRoleMethodInvocation) {
			// Add declaration of "current$context" as a formal parameter,
			// right next to "this"
			Type contextType = classOrRoleOrContextType;
			if (contextType instanceof RoleType) {
				final StaticScope scope = contextType.enclosedScope();
				contextType = Expression.nearestEnclosingMegaTypeOf(scope.parentScope());
			}
			final ObjectDeclaration currentContext = new ObjectDeclaration("current$context", contextType, ctx.getStart().getLine());
			parsingData_.currentFormalParameterList().addFormalParameter(currentContext);
		}
		
		for (int i = 0; i <  parsingData_.currentFormalParameterList().count(); i++) {
			final ObjectDeclaration objectDeclaration = parsingData_.currentFormalParameterList().parameterAtPosition(i);
			
			// Nothing on Pass 1
			// Processed on Pass 2
			// Functionality duplicated on Pass 3
			declareFormalParametersSuitableToPass(currentScope_, objectDeclaration);
		}
		
		// Can't we associate the signature with the parameter list here? Experiment.
		parsingData_.currentMethodSignature().addParameterList(parsingData_.currentFormalParameterList());
		
		if (printProductionsDebug) {
			System.err.println("method_decl_hook : method_signature");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitMethod_decl(@NotNull KantParser.Method_declContext ctx)
	{
		// : method_decl_hook '{' expr_and_decl_list '}'
		
		// Declare parameters in the new scope
		// This is definitely a Pass2 thing, so there is a special Pass 2 version
		
		final MethodDeclaration currentMethod = (MethodDeclaration)currentScope_.associatedDeclaration();
		assert currentMethod instanceof MethodDeclaration;
		
		final MethodSignature sig = parsingData_.currentMethodSignature();
		final Type returnType = sig.returnType();
		currentMethod.setReturnType(returnType);
		
		final StaticScope parentScope = currentScope_.parentScope();
		currentScope_ = parentScope;
		
		// Keep the stack clean
		final MethodSignature methodSignatureInProgress = parsingData_.popMethodSignature();
		currentMethod.signature().setHasConstModifier(methodSignatureInProgress.hasConstModifier());
		
		parsingData_.popFormalParameterList();	// hope this is the right place
		parsingData_.popExprAndDecl();  // Move to Context, Role, Class, StageProp productions???
		
		if (printProductionsDebug) {
			System.err.println("method_decl : method_decl_hook '{' expr_and_decl_list '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterMethod_signature(@NotNull KantParser.Method_signatureContext ctx)
	{
		// : access_qualifier return_type method_name '(' param_list ')' CONST*
		// | access_qualifier return_type method_name CONST*
		// | access_qualifier method_name '(' param_list ')' CONST*
		final String name = ctx.method_name().getText();
		final String accessQualifierString = null != ctx.access_qualifier()? ctx.access_qualifier().getText(): "";
		final AccessQualifier accessQualifier = AccessQualifier.accessQualifierFromString(accessQualifierString);
		
		Type returnType = null;
		// There may not be any return type at all Ñ as for a constructor
		final parser.KantParser.Return_typeContext returnTypeContext = ctx.return_type();
		if (null != returnTypeContext) {
			final String returnTypeName = returnTypeContext.getText();
			returnType = currentScope_.lookupTypeDeclarationRecursive(returnTypeName);
		
			// null is O.K. as a return type!
		}
		
		final int lineNumber = ctx.getStart().getLine();
		final MethodSignature currentMethod = new MethodSignature(name, returnType, accessQualifier, lineNumber);

		if (null != ctx.CONST()) {
			currentMethod.setHasConstModifier(ctx.CONST().size() > 0);
		}
		
		parsingData_.pushMethodSignature(currentMethod);
	}
	
	@Override public void exitMethod_signature(@NotNull KantParser.Method_signatureContext ctx)
	{
		// : access_qualifier return_type method_name '(' param_list ')' CONST*
		// | access_qualifier return_type method_name CONST*
		// | access_qualifier method_name '(' param_list ')' CONST*
		
		if (printProductionsDebug) {
			if ((null != ctx.CONST()) && (ctx.CONST().size() > 0)) {
				if (ctx.return_type() != null && ctx.param_list() != null) {
					System.err.println("method_signature : access_qualifier return_type method_name '(' param_list ')' CONST");
				} else if (ctx.return_type() != null && ctx.param_list() == null) {
					System.err.println("method_signature : access_qualifier return_type method_name COSNT");
				} else {
					System.err.println("method_signature : access_qualifier method_name '(' param_list ')' CONST");
				}
			} else {
				if (ctx.return_type() != null && ctx.param_list() != null) {
					System.err.println("method_signature : access_qualifier return_type method_name '(' param_list ')'");
				} else if (ctx.return_type() != null && ctx.param_list() == null) {
					System.err.println("method_signature : access_qualifier return_type method_name");
				} else {
					System.err.println("method_signature : access_qualifier method_name '(' param_list ')'");
				}
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterExpr_and_decl_list(@NotNull KantParser.Expr_and_decl_listContext ctx)
	{
	}
	
	@Override public void exitExpr_and_decl_list(@NotNull KantParser.Expr_and_decl_listContext ctx)
	{
		//  	: object_decl
        //		| expr ';' object_decl
        //		| expr_and_decl_list object_decl
        //		| expr_and_decl_list expr
        //		| expr_and_decl_list /* null-expr */ ';'
        //		| /* null */

		final KantParser.Expr_and_decl_listContext expr_and_decl_list = ctx.expr_and_decl_list();
		DeclarationList object_decl = null;
		BodyPart expr = null;
		final ExprAndDeclList inProcess = parsingData_.currentExprAndDecl();
		if (null != ctx.expr()) {
			expr = parsingData_.popExpression();
		}
		if (null != ctx.object_decl()) {
			object_decl = parsingData_.popDeclarationList();
		}
		if (null != expr && null != object_decl) {
			inProcess.addBodyPart(expr);
			inProcess.addBodyPart(object_decl);
		} else if (null != expr_and_decl_list && null != object_decl) {
			inProcess.addBodyPart(object_decl);
		} else if (null != object_decl) {
			inProcess.addBodyPart(object_decl);
		} else if (null != expr_and_decl_list && null != expr) {
			inProcess.addBodyPart(expr);
		} else if (null != ctx.expr_and_decl_list() && null == ctx.expr() && null == ctx.object_decl()) {
			// just a gratuitous null statement that we can ignore
		} else {
			// null list Ñ it's O.K.
		}
		
		if (printProductionsDebug) {
			if (null != ctx.expr() && null != ctx.object_decl()) {
				System.err.println("expr_and_decl_list : expr ';' object_decl");
			} else if (null != ctx.expr_and_decl_list() && null != ctx.object_decl()) {
				System.err.println("expr_and_decl_list : expr_and_decl_list object_decl");
			} else if (null == expr && null != object_decl) {
				System.err.println("expr_and_decl_list : object_decl");
			} else if (null != expr_and_decl_list && null != ctx.expr()) {
				System.err.println("expr_and_decl_list : expr_and_decl_list expr");
			} else  {
				System.err.println("expr_and_decl_list : /* null */");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitReturn_type(@NotNull KantParser.Return_typeContext ctx)
	{
		// return_type
        //		: type_name
		//		| /* null */

		// We need just to pop the type that the type_name production
		// put on the stack
		
		if (null != ctx.type_name()) {
			@SuppressWarnings("unused")
			final ExpressionStackAPI unusedType = parsingData_.popRawExpression();
		}
		
		if (printProductionsDebug) {
			System.err.println("return_type : type_name");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitMethod_name(@NotNull KantParser.Method_nameContext ctx)
	{
		// method_name
		//	: JAVA_ID

		/* nothing */
	}
	
	@Override public void exitAccess_qualifier(@NotNull KantParser.Access_qualifierContext ctx)
	{
		//	access_qualifier
	    //		: 'public'
		//		| 'private'
		//		| /* null */

		/* nothing */
	}

	@Override public void enterObject_decl(@NotNull KantParser.Object_declContext ctx)
	{
		// object_decl
	    //	: access_qualifier compound_type_name identifier_list ';'
	    //	| access_qualifier compound_type_name identifier_list
	    //	| compound_type_name identifier_list /* null expr */ ';'
	    //	| compound_type_name identifier_list
	}
	
	@Override public void exitObject_decl(@NotNull KantParser.Object_declContext ctx)
	{
		// object_decl
	    //	: access_qualifier compound_type_name identifier_list ';'
	    //	| access_qualifier compound_type_name identifier_list
	    //	| compound_type_name identifier_list /* null expr */ ';'
	    //	| compound_type_name identifier_list
		
		// One semantic routine serves all three passes

		final int lineNumber = ctx.getStart().getLine();
		final parser.KantParser.Access_qualifierContext accessQualifierContext = ctx.access_qualifier();
		final String accessQualifierString = accessQualifierContext != null? accessQualifierContext.getText(): "";
		final AccessQualifier accessQualifier = AccessQualifier.accessQualifierFromString(accessQualifierString);
		if (null != accessQualifier) {
			assert accessQualifier instanceof AccessQualifier;
		}
		List<ObjectDeclaration> declaredObjectDeclarations = null;
		
		final Compound_type_nameContext compound_type_name = ctx.compound_type_name();
		if (null == compound_type_name) {
			// It can happen if there's a bad syntax error.
			// Just punt for now.
			return;
		}
		final List<ParseTree> children = compound_type_name.children;
		final int numberOfChildren = children.size();
		final String typeName = children.get(0).getText();
		boolean isArray = false;
		if (numberOfChildren == 3) {
			final String firstModifier = children.get(1).getText();
			final String secondModifier = children.get(2).getText();
			if (firstModifier.equals("[") && secondModifier.equals("]")) {
				// Is an array declaration
				isArray = true;
			}
		}
		
		final Declaration associatedDeclaration = currentScope_.associatedDeclaration();
		if (associatedDeclaration instanceof StagePropDeclaration) {
			errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(),"Stage props are stateless, so the decaration of objects of type ", typeName, " in ",
					associatedDeclaration.name(), " are not allowed.", "");
			declaredObjectDeclarations = new ArrayList<ObjectDeclaration>();	// empty list just to keep things happy
		} else if (associatedDeclaration instanceof RoleDeclaration) {
			errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(),"Roles are stateless, so the decaration of objects of type ", typeName, " in ",
					associatedDeclaration.name(), " are not allowed.", "");
			declaredObjectDeclarations = new ArrayList<ObjectDeclaration>();	// empty list just to keep things happy
		} else {
			Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			
			if (null == type) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Type ", typeName, " undefined for declaration", "");
				
				// Put in some reasonable type to avoid stumbling
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
			
			if (isArray) {
				// A derived type
				final String aName = type.getText() + "_array";
				type = new ArrayType(aName, type);
			}
			
			final Identifier_listContext identifier_list = ctx.identifier_list();
			final DeclarationsAndInitializers idInfo = this.processIdentifierList(identifier_list, type, lineNumber);
			declaredObjectDeclarations = idInfo.objectDecls();
			
			
			final RuleContext myParent = ctx.parent;
			ForExpression currentForExpression = null;
			BlockExpression currentBlockExpression = null;
			if (myParent instanceof For_exprContext) {
				currentForExpression = parsingData_.currentForExpression();
				assert null != currentForExpression;
				currentForExpression.addObjectDeclForBlock(declaredObjectDeclarations);
			} else if (ifIsInABlockContext(myParent)) {
				currentBlockExpression = parsingData_.currentBlockExpression();
				assert null != currentBlockExpression;
				currentBlockExpression.addObjectDeclForBlock(declaredObjectDeclarations);
			}
			
			// Maybe here...  Add the initializations to the expression in progress
			final List<BodyPart> intializationExprs = idInfo.initializations();
			if (0 < intializationExprs.size()) {
				// There may not even be a currentExprAndDecl... We presume that initializations
				// occur where there are ExprAndDecl blocks in play. We'll have to change this
				// if we come to allow inline initialization of object members in the class
				// syntax, and handle the intializations in some other way (e.g., letting
				// the constructor handle them.
				final ExprAndDeclList currentExprAndDecl = parsingData_.currentExprAndDecl();
				for (int z = 0; z < intializationExprs.size(); z++) {
					if (null != currentForExpression) {
						// For loops are special
						final List<BodyPart> bodyParts = new ArrayList<BodyPart>();
						bodyParts.add(intializationExprs.get(z));
						currentForExpression.addInitExprs(bodyParts);
					} else if (null != currentBlockExpression) {
						// Blocks are... kind of special...
						currentBlockExpression.bodyParts().add(intializationExprs.get(z));
					} else {
						currentExprAndDecl.addBodyPart(intializationExprs.get(z));
					}
				}
			}
			
			for (final ObjectDeclaration aDecl : declaredObjectDeclarations) {
				if (aDecl.name().equals("this") || aDecl.name().equals("Ralph") || aDecl.name().equals("Sue")) {
					errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Please avoid the use of the names this, Sue and Ralph for identifiers", "", "", "");
				}
			}
		}

		// Package all the stuff in declaredObjectDeclarations into an ExprAndDeclList
		// and push it onto the ExprAndDecl stack.
		final DeclarationList declarationList = new DeclarationList(lineNumber);
		
		for (ObjectDeclaration aDecl : declaredObjectDeclarations) {
			declarationList.addDeclaration(aDecl);
		}
		
		parsingData_.pushDeclarationList(declarationList);
		
		if (printProductionsDebug) {
			if (ctx.access_qualifier() != null) {
				System.err.print("object_decl : access_qualifier compound_type_name identifier_list (");
				final int size = declaredObjectDeclarations.size();
				for (int y = 0; y < size; y++) {
					final ObjectDeclaration aDecl = declaredObjectDeclarations.get(y);
					System.err.print(aDecl.name());
					if (y != size - 1) {
						System.err.print(", ");
					}
				}
				System.err.println(") ';'");
			} else {
				System.err.println("object_decl : compound_type_name identifier_list ';'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitCompound_type_name(@NotNull KantParser.Compound_type_nameContext ctx)
	{	
        // : type_name '[' ']'
        // | type_name
		
		// We need just to pop the type that the type_name production
		// put on the stack
		
		@SuppressWarnings("unused")
		final ExpressionStackAPI unusedType = parsingData_.popRawExpression();
		
		if (printProductionsDebug) {
			System.err.println("compound_type_name : type_name ...");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitType_name(@NotNull KantParser.Type_nameContext ctx)
	{
		// type_name
	    //	: JAVA_ID
	    //	| 'int'
		//  | 'Integer'
	    //	| 'double'
	    //	| 'char'
	    //	| 'String'
	
		final String typeName = null != ctx.JAVA_ID()? ctx.JAVA_ID().getText(): ctx.getText();

		Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
		if (null == type) {
			type = StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		
		parsingData_.pushExpression(type);
		
		if (printProductionsDebug) {
			if (null != ctx.JAVA_ID()) {
				System.err.print("type_name : JAVA_ID (");
			} else {
				System.err.print("type_name : (");
			}
			System.err.print(ctx.getText());
			System.err.println(")");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitIdentifier_list(@NotNull KantParser.Identifier_listContext ctx)
	{
		// identifier_list
	    //	: JAVA_ID
	    //	| identifier_list ',' JAVA_ID
	    //	| JAVA_ID ASSIGN expr
	    //	| identifier_list ',' JAVA_ID ASSIGN expr
	    
		/* nothing */
	}
    
	@Override public void exitParam_list(@NotNull KantParser.Param_listContext ctx)
	{
		// : param_decl
        // | param_list ',' param_decl
        // | /* null */
		
		// Declare parameters in the new scope
		// Save this for Pass 2.  Hmmm. Doesn't seem necessary.
		
		if (printProductionsDebug) {
			if (ctx.param_decl() != null && ctx.param_list() == null) {
				System.err.println("param_list : param_decl");
			} else if (ctx.param_list() != null) {
				System.err.println("param_list : param_list ';' param_decl");
			} else {
				System.err.println("param_list : /* null */");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitParam_decl(@NotNull KantParser.Param_declContext ctx)
	{
		// param_decl
        //		: type_name JAVA_ID
		
		// We need just to pop the type that the type_name production
		// put on the stack
		
		@SuppressWarnings("unused")
		final ExpressionStackAPI unusedType = parsingData_.popRawExpression();
		
		if (printProductionsDebug) {
			System.err.println("param_decl : type_name JAVA_ID");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitExpr(@NotNull KantParser.ExprContext ctx)
	{
		// expr
		//	: abelian_expr
		//	| boolean_expr
		//	| block
		//	| if_expr
	    //	| for_expr
	    //	| while_expr
	    //	| do_while_expr
	    //	| switch_expr
	    //	| BREAK
	    //	| CONTINUE
	    //	| RETURN expr
	    //  | RETURN
	    
		Expression expression = null;
		
		if (null != ctx.abelian_expr()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : abelian_expr"); }
		} else if (null != ctx.boolean_expr()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : boolean_expr"); }
		} else if (null != ctx.block()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : block"); }
		} else if (null != ctx.if_expr()) {
			// | if_expr.
			// It should be on top of the stack, so just let it be
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : if_expr"); }
		} else if (null != ctx.for_expr()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : for_expr"); }
		} else if (null != ctx.while_expr()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : while_expr"); }
		} else if (null != ctx.do_while_expr()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : do_while_expr"); }
		} else if (null != ctx.switch_expr()) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) { System.err.println("expr : switch_expr"); }
		} else if (null != ctx.BREAK()) {
			final long nestingLevelInsideBreakable = nestingLevelInsideBreakable(ctx.BREAK());
			final Expression currentBreakableExpression = parsingData_.currentBreakableExpression();
			if (null == currentBreakableExpression) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "There is no switch or loop statement to break", "", "", "");
			} else if (nestingLevelInsideBreakable == -1) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "The break statement is not in the scope of any switch or loop statement", "", "", "");
			}
			expression = new BreakExpression(ctx.getStart().getLine(), currentBreakableExpression, nestingLevelInsideBreakable);
			if (printProductionsDebug) { System.err.print("expr : BREAK (nesting level is "); System.err.print(nestingLevelInsideBreakable); System.err.println(")"); }
		} else if (null != ctx.CONTINUE()) {
			final long nestingLevelInsideBreakable = nestingLevelInsideBreakableForContinue(ctx.CONTINUE());
			final Expression currentContinuableExpression = parsingData_.nearestContinuableLoop();
			assert currentContinuableExpression instanceof SwitchExpression == false;
			if (null == currentContinuableExpression) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "There is no loop statement to continue", "", "", "");
			} else if (nestingLevelInsideBreakable == -1) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "The continue statement is not in the scope of any loop statement", "", "", "");
			}
			expression = new ContinueExpression(ctx.getStart().getLine(), currentContinuableExpression, nestingLevelInsideBreakable);
			if (printProductionsDebug) { System.err.println("expr : CONTINUE"); }
		} else if (null != ctx.RETURN()) {
			// The expression being returned is popped from
			// parsingData_.popExpression() in this.expressionFromReturnStatement
			expression = this.expressionFromReturnStatement(ctx.expr(), ctx.getParent(), ctx.getStart());
			if (printProductionsDebug) { System.err.println("expr : return_expr"); }
		} else {
			// Could be a parsing error
			expression = new NullExpression();
			if (printProductionsDebug) { System.err.println("expr : <internal error>"); }
		}
		
		parsingData_.pushExpression(expression);
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
		
	@Override public void exitAbelian_expr(@NotNull KantParser.Abelian_exprContext ctx)
	{
		// abelian_expr
		//  : <assoc=right>abelian_expr POW abelian_expr           
		//	| ABELIAN_SUMOP expr                           
		//	| LOGICAL_NEGATION expr
		//  | NEW message
        //	| NEW type_name '[' expr ']'
		//	| abelian_expr op=('*' | '/' | '%') abelian_expr       
		//	| abelian_expr op=('+' | '-') abelian_expr             
		//	| abelian_expr op=('<=' | '>=' | '<' | '>' | '==' | '!=') abelian_expr                    
		//	| null_expr
		//	| JAVA_ID
		//	| JAVA_ID ABELIAN_INCREMENT_OP
		//	| ABELIAN_INCREMENT_OP JAVA_ID
		//	| constant
		//	| '(' abelian_expr ')'
		//	| abelian_expr '[' expr ']'
		//	| abelian_expr '[' expr ']' ABELIAN_INCREMENT_OP
		//	| ABELIAN_INCREMENT_OP expr '[' expr ']'
		//	| ABELIAN_INCREMENT_OP expr '.' JAVA_ID
		//	| abelian_expr '.' JAVA_ID ABELIAN_INCREMENT_OP
		//	| abelian_expr '.' message
		//	| /* this. */ message
		//	| abelian_expr '.' CLONE
		//	| abelian_expr '.' JAVA_ID
	    //	| <assoc=right> abelian_expr ASSIGN expr
		
		Expression expression = null;
		
		if (null != ctx.abelian_expr() && ctx.abelian_expr().size() == 2 && null != ctx.POW()) {
			// : <assoc=right>abelian_expr POW abelian_expr
			final Expression expr2 = parsingData_.popExpression();
			final Expression expr1 = parsingData_.popExpression();
						
			final String operatorAsString = ctx.POW().getText();
		
			expression = new PowerExpression(expr1, operatorAsString, expr2, ctx.getStart(), this);
		
			if (printProductionsDebug) {
				System.err.println("abelian_expr : abelian_expr POW abelian_expr");
			}
		} else if (null != ctx.ABELIAN_SUMOP() && null != ctx.expr() && ctx.expr().size() == 1) {
			//	| SUMOP expr
			expression = parsingData_.popExpression();
			expression = new UnaryAbelianopExpression(expression, ctx.ABELIAN_SUMOP().getText());
			if (printProductionsDebug) {
				System.err.println("unary_abelian_expr : '-' product");
			}
		} else if (null != ctx.LOGICAL_NEGATION() && null != ctx.expr() && ctx.expr().size() > 0) {
			//	| LOGICAL_NEGATION  expr
			
			assert false;	// to be implemented. TODO.
			if (printProductionsDebug) {
				System.err.println("unary_abelian_expr : '!' product");
			}
		} else if (null != ctx.NEW()) {
			// 'new' message
			// 'new' type_name '[' expr ']'
			
			final KantParser.ExprContext sizeExprCtx = (null == ctx.expr())? null:
														((ctx.expr().size() == 0)? null: ctx.expr(0));
			expression = this.newExpr(ctx.children, ctx.getStart(), sizeExprCtx, ctx.message());
						
			if (printProductionsDebug) {
				System.err.print("expr : ");
				if (null != expression && expression instanceof NewExpression) {
					System.err.print(((NewExpression)expression).getText());
				} else {
					System.err.print("<unknown class>");
				}
				System.err.println("");
			}
			if (null == expression) {
				expression = new NullExpression();
			}
		} else if ((null != ctx.expr()) && (ctx.expr().size() == 1) && (null != ctx.abelian_expr()) &&
				(ctx.abelian_expr().size() == 1) && null != ctx.ASSIGN()) {
			// : lhs '=' rhs
			final Expression rhs = parsingData_.popExpression();
			final Expression lhs = parsingData_.popExpression();
			// rhs.setResultIsConsumed(true);	// done by assignmentExpr call
			expression = this.assignmentExpr(lhs, ctx.ASSIGN().getText(), rhs, ctx);
			if (printProductionsDebug) { System.err.println("ablian_expr : ablian_expr ASSIGN expr"); }
		} else if (null != ctx.abelian_expr() && ctx.abelian_expr().size() == 2 && null != ctx.ABELIAN_MULOP()) {
			//	| abelian_expr op=('*' | '/' | '%') abelian_expr
			final Expression expr2 = parsingData_.popExpression();
			final Expression expr1 = parsingData_.popExpression();
						
			final String operatorAsString = ctx.ABELIAN_MULOP().getText();
			expression = new ProductExpression(expr1, operatorAsString, expr2, ctx.getStart(), this);
		
			if (printProductionsDebug) {
				System.err.print("abelian_expr : abelian_expr "); System.err.print(operatorAsString); System.err.println(" abelian_expr");
			}
		} else if (null != ctx.abelian_expr() && ctx.abelian_expr().size() == 2 && null != ctx.ABELIAN_SUMOP()) {
			//	| abelian_expr op=('+' | '-') abelian_expr
			final Expression expr2 = parsingData_.popExpression();
			final Expression expr1 = parsingData_.popExpression();
						
			final String operatorAsString = ctx.ABELIAN_SUMOP().getText();
			expression = new SumExpression(expr1, operatorAsString, expr2);
			
			if (printProductionsDebug) {
				System.err.print("abelian_expr : abelian_expr "); System.err.print(operatorAsString); System.err.println(" abelian_expr");
			}
		} else if (null != ctx.abelian_expr() && ctx.abelian_expr().size() == 2 && null != ctx.ABELIAN_RELOP()) {
			//	| abelian_expr op=('<=' | '>=' | '<' | '>' | '==' | '!=') abelian_expr
			final TerminalNode relationalOperator = ctx.ABELIAN_RELOP();

			final Expression rhs = parsingData_.popExpression();
			final Expression lhs = parsingData_.popExpression();
			lhs.setResultIsConsumed(true);
			rhs.setResultIsConsumed(true);
			if (lhs.type().canBeConvertedFrom(rhs.type()) == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Expression '", rhs.getText(), "' is not of the right type","");
			}
			
			assert null != relationalOperator;
			final String operationAsString = relationalOperator.getText();
			expression = new RelopExpression(lhs, operationAsString, rhs);

			if (printProductionsDebug) {
				System.err.print("relop_expr : expr "); System.err.print(operationAsString); System.err.println(" expr");
			}
		} else if (null != ctx.null_expr()) {
			//	| null_expr
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.print("abelian_expr : null_expr");
			}
		} else if (null != ctx.JAVA_ID() && null == ctx.ABELIAN_INCREMENT_OP() &&
				(null == ctx.abelian_expr() || (ctx.abelian_expr().size() == 0))) {
			//	| JAVA_ID
			
			expression = idExpr(ctx.JAVA_ID(), ctx.getStart());
			if (printProductionsDebug) {
				System.err.print("expr : JAVA_ID (");
				System.err.print(ctx.JAVA_ID().getText());
				System.err.println(")");
			}
		} else if (null != ctx.JAVA_ID() && null != ctx.ABELIAN_INCREMENT_OP() &&
				(null == ctx.abelian_expr() || (ctx.abelian_expr().size() == 0))) {	
			//	| JAVA_ID ABELIAN_INCREMENT_OP
			//	| ABELIAN_INCREMENT_OP JAVA_ID
			final String id = ctx.JAVA_ID().getText();
			final Interval JavaIDInterval = ctx.JAVA_ID().getSourceInterval();
			final Interval OperatorInterval = ctx.ABELIAN_INCREMENT_OP().getSourceInterval();
			final PreOrPost preOrPost = JavaIDInterval.startsAfter(OperatorInterval)?
					UnaryopExpressionWithSideEffect.PreOrPost.Pre: UnaryopExpressionWithSideEffect.PreOrPost.Post;
			
			expression = this.jAVA_IDAtomUtility(id);
			
			assert null != expression;
			expression = new UnaryopExpressionWithSideEffect(expression, ctx.ABELIAN_INCREMENT_OP().getText(), preOrPost);
			assert null != expression;
			
			if (printProductionsDebug) {
				switch (preOrPost) {
				case Post:
					System.err.print("abelian_expr : JAVA_ID (");
					System.err.print(ctx.JAVA_ID().getText());
					System.err.println(") ABELIAN_INCREMENT_OP");
					break;
				case Pre:
					System.err.print("abelian_expr : ABELIAN_INCREMENT_OP JAVA_ID (");
					System.err.print(ctx.JAVA_ID().getText());
					System.err.println(")");
					break;
				}
			}
			
			checkForIncrementOpViolatingIdentifierConstness((UnaryopExpressionWithSideEffect)expression, ctx.getStart());
		} else if (null != ctx.constant()) {
			//	| constant
			
			// expression = Expression.makeConstantExpressionFrom(ctx.constant().getText());
			// is on the stack. We now have a "constant : ..." production,
			// so we don't make it here
			expression = parsingData_.popExpression();
			if (printProductionsDebug) {
				System.err.println("abelian_expr : constant");
			}
		} else if (null != ctx.abelian_expr() && (ctx.abelian_expr().size() > 0) && null == ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && (null == ctx.expr() || ctx.expr().size() == 0) && null == ctx.ABELIAN_INCREMENT_OP()) {
			//	| '(' abelian_expr ')'
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.println("abelian_expr : '(' abelian_expr ')'");
			}
		} else if (null != ctx.abelian_expr() && (ctx.abelian_expr().size() > 0) && null == ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && null != ctx.expr() && (ctx.expr().size() == 1) && null == ctx.ABELIAN_INCREMENT_OP()) {
			//	| abelian_expr '[' expr ']'
			final Expression indexExpr = parsingData_.popExpression();
			indexExpr.setResultIsConsumed(true);
			final Expression rawArrayBase = parsingData_.popExpression();
			final ArrayType arrayType = (ArrayType)rawArrayBase.type();
			assert arrayType instanceof ArrayType;
			final Type baseType = arrayType.baseType();
			final ArrayExpression arrayBase = new ArrayExpression(rawArrayBase, baseType);
			arrayBase.setResultIsConsumed(true);
			expression = new ArrayIndexExpression(arrayBase, indexExpr);
			
			if (printProductionsDebug) {
				System.err.println("abelian_expr : abelian_expr '[' expr ']'");
			}
		} else if (null != ctx.abelian_expr() && (ctx.abelian_expr().size() > 0) && null == ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && null != ctx.expr() && (ctx.expr().size() > 0) && null != ctx.ABELIAN_INCREMENT_OP()) {
			//	| abelian_expr '[' expr ']' ABELIAN_INCREMENT_OP
			//	| ABELIAN_INCREMENT_OP expr '[' expr ']'
			
			ParseTree arrayBase;
			KantParser.ExprContext theIndex;
			if (ctx.expr().size() == 2) {
				arrayBase = ctx.expr(0);
				theIndex = ctx.expr(1);
			} else {
				arrayBase = ctx.abelian_expr(0);
				theIndex = ctx.expr(0);
			}
			
			expression = (Expression)processIndexedArrayElement(arrayBase, theIndex, ctx.ABELIAN_INCREMENT_OP());
			assert expression instanceof Expression;
			
			checkForIncrementOpViolatingConstness((ArrayIndexExpressionUnaryOp)expression, ctx.getStart());
		} else if (null != ctx.ABELIAN_INCREMENT_OP() && null != ctx.expr() && (ctx.expr().size() == 1) && null != ctx.JAVA_ID()) {
			//	| ABELIAN_INCREMENT_OP expr '.' JAVA_ID
			expression = (Expression)this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), ctx.ABELIAN_INCREMENT_OP());
			assert expression instanceof Expression;
			
			if (printProductionsDebug) { System.err.print("expr : '++' expr '.' JAVA_ID ("); System.err.print(ctx.JAVA_ID().getText()); System.err.println(")");}
		} else if (null != ctx.abelian_expr() && (ctx.abelian_expr().size() > 0) && null != ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && (null == ctx.expr() || (ctx.expr().size() == 0)) && null != ctx.ABELIAN_INCREMENT_OP()) {
			//	| abelian_expr '.' JAVA_ID ABELIAN_INCREMENT_OP
			expression = (Expression)this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), ctx.ABELIAN_INCREMENT_OP());
			assert expression instanceof Expression;
			
			if (printProductionsDebug) { System.err.print("expr : expr '.' JAVA_ID ("); System.err.print(ctx.JAVA_ID().getText()); System.err.println(") ++");}
		} else if (null != ctx.abelian_expr() && (ctx.abelian_expr().size() > 0) && null != ctx.message()) {
			//	| abelian_expr '.' message
			// This routine actually does pop the expressions stack (and the Message stack)
			expression = this.messageSend(ctx.getStart(), ctx.abelian_expr(0));
			
			if (null == expression) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"No match for call: ", ctx.abelian_expr(0).getText(), ".", ctx.message().getText());
				expression = new NullExpression();
			}
												
			if (printProductionsDebug) { if (ctx.abelian_expr(0) != null) System.err.println("abelian_expr : abelian_expr '.' message"); else System.err.println("abelian_expr : message");}
		} else if ((null == ctx.abelian_expr() || (ctx.abelian_expr().size() == 0)) && null != ctx.message()) {
			//	| /* this. */ message
			// This routine actually does pop the expressions stack (and the Message stack)
			expression = this.messageSend(ctx.getStart(), null);
									
			if (printProductionsDebug) { System.err.println("abelian_expr : message");}
		} else if (null != ctx.abelian_expr() && (ctx.abelian_expr().size() > 0) && null != ctx.CLONE()) {
			//	| abelian_expr '.' CLONE
			
			final Expression qualifier = parsingData_.popExpression();
			expression = new DupMessageExpression(qualifier, qualifier.type());
			if (printProductionsDebug) { System.err.println("abelian_expr : abelian_expr '.' 'clone'"); }
		} else if (null != ctx.abelian_expr() && ctx.abelian_expr().size() == 1 && null != ctx.JAVA_ID()) {
			//	| abelian_expr '.' JAVA_ID
			// The following line DOES pop the expression stack
			expression = (Expression)this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), ctx.ABELIAN_INCREMENT_OP());
			assert expression instanceof Expression;
			
			if (printProductionsDebug) { System.err.print("abelian_expr : abelian_expr '.' JAVA_ID ("); System.err.print(ctx.JAVA_ID().getText()); System.err.println(")");}
		} else {
			assert false;
		}
		
		parsingData_.pushExpression(expression);
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	
	// I wanted to postpone this stuff until Pass 2, but omitting it
	// from Pass 1 causes the parse stack to get messed up here in
	// this pass. I still refrain from passing the argument info
	// in to the semantic analysis routines on Pass 1
	
	@Override public void enterMessage(@NotNull KantParser.MessageContext ctx)
	{
		// JAVA_ID '(' argument_list ')'
		
		parsingData_.pushArgumentList(new ActualArgumentList());
	}
	
	@Override public void exitMessage(@NotNull KantParser.MessageContext ctx)
	{
		// 	| JAVA_ID '(' argument_list ')'
		
		final String selectorName = ctx.JAVA_ID().getText();
		
		// Leave argument list processing to Pass 2...
		
		final Message newMessage = new Message(selectorName, null, ctx.getStart().getLine());
		parsingData_.pushMessage(newMessage);
		
		// ... but clean up the stack by popping off the arguments
		// This is definitely Pass 2 stuff.
		@SuppressWarnings("unused")
		ActualArgumentList argumentList = parsingData_.popArgumentList();
		
		if (printProductionsDebug) {
			System.err.print("message : JAVA_ID '(' argument_list ')' (");
			System.err.print(selectorName);
			System.err.println(")");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitBoolean_expr(@NotNull KantParser.Boolean_exprContext ctx)
	{
		// boolean_expr
		// : boolean_expr BOOLEAN_MULOP expr                       
		// | boolean_expr BOOLEAN_SUMOP expr
		// | constant
		// | abelian_expr
		
		Expression expression = null;
		String operation = null;
		
		if (null != ctx.BOOLEAN_MULOP()) {
			operation = ctx.BOOLEAN_MULOP().getText();
			final Expression rhs = parsingData_.popExpression();
			final Expression lhs = parsingData_.popExpression();
			rhs.setResultIsConsumed(true);
			lhs.setResultIsConsumed(true);
			expression = new ProductExpression(lhs, operation, rhs, ctx.getStart(), this);
			if (printProductionsDebug) {
				System.err.print("boolean_expr : boolean_expr ");
				System.err.print(operation);
				System.err.println(" boolean_expr");
			}
		} else if (null != ctx.BOOLEAN_SUMOP()) {
			operation = ctx.BOOLEAN_SUMOP().getText();
			final Expression rhs = parsingData_.popExpression();
			final Expression lhs = parsingData_.popExpression();
			rhs.setResultIsConsumed(true);
			lhs.setResultIsConsumed(true);
			expression = new SumExpression(lhs, operation, rhs);
			if (printProductionsDebug) {
				System.err.print("boolean_expr : boolean_expr ");
				System.err.print(operation);
				System.err.println(" boolean_expr");
			}
		} else if (null != ctx.constant()) {
			expression = parsingData_.popExpression();
			expression.setResultIsConsumed(true);
			if (printProductionsDebug) {
				System.err.println("boolean_expr : constant ");
			}
		} else if (null != ctx.abelian_expr()) {
			expression = parsingData_.popExpression();
			expression.setResultIsConsumed(true);
			if (printProductionsDebug) {
				System.err.println("boolean_expr : abelian_expr ");
			}
		} else {
			assert false;
		}
		
		parsingData_.pushExpression(expression);
	}
	

	@Override public void enterBlock(@NotNull KantParser.BlockContext ctx)
	{
		//	: '{' expr_and_decl_list '}'
        //	| '{' '}'
		// Set up the block
		final StaticScope oldScope = currentScope_;
		currentScope_ = new StaticScope(currentScope_, true);
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart().getLine());
		parsingData_.pushExprAndDecl(newList);

		final BlockExpression blockExpression = new BlockExpression(ctx.getStart().getLine(), newList, currentScope_);
		currentScope_.setDeclaration(oldScope.associatedDeclaration());	/// hmmm....
		
		parsingData_.pushBlockExpression(blockExpression);
	}

	@Override public void exitBlock(@NotNull KantParser.BlockContext ctx)
	{
		//	: '{' expr_and_decl_list '}'
        //	| '{' '}'
		// Just for balance. Do something with this later.
		// And we pop it in any case, because we pushed it unconditionally above
		@SuppressWarnings("unused")
		final ExprAndDeclList exprAndDeclList = parsingData_.popExprAndDecl();
		final BlockExpression expression = parsingData_.popBlockExpression();
		expression.parserIsDone();
	
		parsingData_.pushExpression(expression);
		
		currentScope_ = currentScope_.parentScope();
		
		if (printProductionsDebug) {
			if (null != ctx.expr_and_decl_list()) {
				System.err.println("block : '{' expr_and_decl_list '}'");
			} else {
				System.err.println("block : '{' '}'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterExpr_or_null(@NotNull KantParser.Expr_or_nullContext ctx)
	{ 
	}
	
	@Override public void exitExpr_or_null(@NotNull KantParser.Expr_or_nullContext ctx)
	{
		//  : expr
        //	| /* null */
        //	;
		if (ctx.expr() == null) {
			final Expression expression = new NullExpression();
			parsingData_.pushExpression(expression);
		} else {
			// just leave alone
		}
		
		if (printProductionsDebug) {
			if (ctx.expr() != null ) {
				System.err.println("expr_or_null : expr");
			} else {
				System.err.println("expr_or_null : /* null */");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitIf_expr(@NotNull KantParser.If_exprContext ctx)
	{
		// Version for all passes
		// if_expr
        // 		: 'if' '(' expr ')' expr
        // 		| 'if' '(' expr ')' expr 'else' expr
        // 		;
		
		@SuppressWarnings("unused")
		final ExprContext thenPartCtx = ctx.expr(1);
		
		final ExprContext elsePartCtx = ctx.expr().size() > 2? ctx.expr(2): null;
		
		Expression elsePart = null;
		if (null != elsePartCtx) {
			elsePart = parsingData_.popExpression();
		} else {
			elsePart = new NullExpression();
		}
		final Expression thenPart = parsingData_.popExpression();
		final Expression conditional = parsingData_.popExpression();
		final Type conditionalType = conditional.type();
		if (conditionalType.name().equals("boolean") == false) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Conditional expression `", conditional.getText(),
					"« is not of type boolean", "");
		}
		
		final Expression ifExpression = new IfExpression(conditional, thenPart, elsePart);
		parsingData_.pushExpression(ifExpression);
		
		if (printProductionsDebug) {
			if (null != elsePartCtx) {
				System.err.println("if_expr : 'if' '(' relop_expr ')' expr 'else' expr");
			} else {
				System.err.println("if_expr : 'if' '(' relop_expr ')' expr");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitTrivial_object_decl(@NotNull KantParser.Trivial_object_declContext ctx) {
		// trivial_object_decl : compound_type_name JAVA_ID
		final Compound_type_nameContext compound_type_name = ctx.compound_type_name();
		if (null == compound_type_name) {
			// It can happen if there's a bad syntax error.
			// Just punt for now.
			return;
		}
		final List<ParseTree> children = compound_type_name.children;
		final int numberOfChildren = children.size();
		final String typeName = children.get(0).getText();
		if (numberOfChildren == 3) {
			final String firstModifier = children.get(1).getText();
			final String secondModifier = children.get(2).getText();
			if (firstModifier.equals("[") && secondModifier.equals("]")) {
				// Is an array declaration
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"Array declaration is not appropriate for this context", "",
						"", "");
			}
		}
		
		Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
		if (null == type) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Type `", typeName,
					"« seems not to be declared in any enclosing scope", "");
			type = StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		
		final ObjectDeclaration objectDecl = new ObjectDeclaration(ctx.JAVA_ID().getText(), type, ctx.getStart().getLine());
		currentScope_.declareObject(objectDecl);
	}
	
	@Override public void enterFor_expr(@NotNull KantParser.For_exprContext ctx)
	{
		//  : 'for' '(' expr ';'    expr ';' expr ')' expr
		//  | 'for' '(' object_decl expr ';' expr ')' expr
	    //  | 'for' '(' JAVA_ID ':' expr ')' expr
		//  | 'for' '(' trivial_object_decl ':' expr ')' expr
		
		final StaticScope newScope = new StaticScope(currentScope_, true);
		
		// This is just a placeholder so that enclosed declarations get
		// registered. We fill in the meat in exitFor_expr
		final ForExpression forExpression = new ForExpression(null, null, null, null, newScope,
				ctx.getStart().getLine(), parsingDataArgumentAccordingToPass());
		newScope.setDeclaration(currentScope_.associatedDeclaration());	/// hmmm....
		
		currentScope_ = newScope;
		parsingData_.pushForExpression(forExpression);
	}
	
	@Override public void exitFor_expr(@NotNull KantParser.For_exprContext ctx)
	{
		//  : 'for' '(' expr ';'    expr ';' expr ')' expr
		//  | 'for' '(' object_decl expr ';' expr ')' expr
        //  | 'for' '(' JAVA_ID ':' expr ')' expr
		//  | 'for' '(' trivial_object_decl ':' expr ')' expr
		
		ForExpression expression = null;
		
		if ((null == ctx.JAVA_ID()) && (null == ctx.object_decl()) && (null == ctx.trivial_object_decl())
				&& (ctx.expr().size() == 4)) {
			//  : 'for' '(' expr ';'    expr ';' expr ')' expr
			final Expression body = parsingData_.popExpression();
			final Expression increment = parsingData_.popExpression();
			final Expression conditional = parsingData_.popExpression();
			final Expression initializer = parsingData_.popExpression();
			
			expression = parsingData_.popForExpression();
			
			body.setResultIsConsumed(false);
			increment.setResultIsConsumed(true);
			conditional.setResultIsConsumed(true);
			
			final Type conditionalType = conditional.type();
			
			if (conditionalType.name().equals("boolean") == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Conditional expression `", conditional.getText(),
						"« is not of type boolean", "");
				final BooleanConstant falseExpr = new BooleanConstant(true);
				expression.reInit(initializer, falseExpr, increment, body);
			} else {
				assert conditional.type().name().equals("boolean");
				expression.reInit(initializer, conditional, increment, body);
			}
		} else if ((null == ctx.JAVA_ID()) && (null != ctx.object_decl()) && (ctx.expr().size() == 3)) {
			//  | 'for' '(' object_decl expr ';' expr ')' expr
			final Expression body = parsingData_.popExpression();
			final Expression increment = parsingData_.popExpression();
			final Expression conditional = parsingData_.popExpression();
			expression = parsingData_.popForExpression();
			
			body.setResultIsConsumed(false);
			increment.setResultIsConsumed(true);
			conditional.setResultIsConsumed(true);
			
			final Type conditionalType = conditional.type();
			
			if (conditionalType.name().equals("boolean") == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Conditional expression `", conditional.getText(),
						"« is not of type boolean", "");
				final BooleanConstant falseExpr = new BooleanConstant(true);
				expression.reInit(null, falseExpr, increment, body);
			} else {
				assert conditional.type().name().equals("boolean");
				expression.reInit(null, conditional, increment, body);
			}
		} else if ((null != ctx.JAVA_ID()) && (null == ctx.trivial_object_decl()) && (ctx.expr().size() == 2)) {
			//  | 'for' '(' JAVA_ID ':' expr ')' expr
			final Expression body = parsingData_.popExpression();
			final Expression thingToIncrementOver = parsingData_.popExpression();
			// final IdentifierExpression JAVA_ID = (IdentifierExpression)parsingData_.popExpression();
			final String JAVA_IDasString = ctx.JAVA_ID().getText();
			final ObjectDeclaration JAVA_ID_DECL = currentScope_.lookupObjectDeclarationRecursive(JAVA_IDasString);
			if (null == JAVA_ID_DECL) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Loop identifier `", JAVA_IDasString,
						"« is not declared", "");
			}
			expression = parsingData_.popForExpression();
			
			body.setResultIsConsumed(false);
			thingToIncrementOver.setResultIsConsumed(true);
			
			if (thingToIncrementOver.type() instanceof ArrayType == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Expression `", thingToIncrementOver.getText(),
						"« is not iterable", "");
			}
			
			expression.reInitIterativeFor(JAVA_ID_DECL, thingToIncrementOver, body);
		} else if ((null != ctx.trivial_object_decl()) && (ctx.expr().size() == 2)) {
			//  | 'for' '(' trivial_object_decl ':' expr ')' expr
			final Expression body = parsingData_.popExpression();
			final Expression thingToIncrementOver = parsingData_.popExpression();
			expression = parsingData_.popForExpression();
			
			final List<ParseTree> children = ctx.trivial_object_decl().children;
			final int numberOfChildren = children.size();
			assert 2 == numberOfChildren;
			final String JAVA_IDasString = ctx.trivial_object_decl().JAVA_ID().getText();
	
			
			// final String JAVA_IDasString = ctx.JAVA_ID().getText();
			final ObjectDeclaration JAVA_ID_DECL = currentScope_.lookupObjectDeclaration(JAVA_IDasString);
			if (null == JAVA_ID_DECL) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Loop identifier `", JAVA_IDasString,
						"« is not declared", " (strange error)");
			}
			
			body.setResultIsConsumed(false);
			thingToIncrementOver.setResultIsConsumed(true);
					
			if (thingToIncrementOver.type() instanceof ArrayType == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Expression `", thingToIncrementOver.getText(),
						"« is not iterable", "");
			}
					
			expression.reInitIterativeFor(JAVA_ID_DECL, thingToIncrementOver, body);
		} else {
			assert false;
		}
		
		parsingData_.pushExpression(expression);
		
		currentScope_ = currentScope_.parentScope();
		
		if (printProductionsDebug) {
			if ((null == ctx.JAVA_ID()) && (null == ctx.object_decl()) && (null == ctx.trivial_object_decl())
					&& (ctx.expr().size() == 4)) {
				System.err.println("for_expr : 'for' '(' expr ';' expr ';' expr ')' expr");
			} else if ((null == ctx.JAVA_ID()) && (null != ctx.object_decl()) && (ctx.expr().size() == 3)) {
				System.err.println("for_expr : 'for' '(' object_decl expr ';' expr ')' expr");
			} else if ((null != ctx.JAVA_ID()) && (null == ctx.trivial_object_decl()) && (ctx.expr().size() == 2)) {
				System.err.println("for_expr : 'for' '(' JAVA_ID ':' expr ')' expr");
			} else if ((null != ctx.trivial_object_decl()) && (ctx.expr().size() == 2)) {
				System.err.println("for_expr : 'for' '(' trivial_object_decl ':' expr ')' expr");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterWhile_expr(@NotNull KantParser.While_exprContext ctx)
	{
		// while_expr
		//	: 'while' '(' expr ')' expr
		//	;
		
		// This is just a placeholder so that enclosed declarations get
		// registered. We fill in the meat in exitWhile_expr
		final WhileExpression whileExpression = new WhileExpression(null, null,
				ctx.getStart().getLine(), parsingDataArgumentAccordingToPass());
		
		parsingData_.pushWhileExpression(whileExpression);
	}
	
	@Override public void exitWhile_expr(@NotNull KantParser.While_exprContext ctx)
	{
		// while_expr
		//	: 'while' '(' expr ')' expr
	
		final Expression body = parsingData_.popExpression();
		final Expression conditional = parsingData_.popExpression();
		final WhileExpression expression = parsingData_.popWhileExpression();
		
		body.setResultIsConsumed(true);
		conditional.setResultIsConsumed(true);
		
		if (conditional.type() != StaticScope.globalScope().lookupTypeDeclaration("boolean")) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Condition in ``while«« statement is not of type boolean", conditional.getText(),
					" of type ", conditional.type().name());
		}
		
		expression.reInit(conditional, body);
		
		parsingData_.pushExpression(expression);
		
		if (printProductionsDebug) {
			if (null != ctx.expr() && null != ctx.expr(1)) {
				System.err.println("while_expr : 'while' '(' expr ')' expr");
			} else {
				System.err.println("while_expr : 'while' '(' ??? ')' expr");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterDo_while_expr(@NotNull KantParser.Do_while_exprContext ctx)
	{
		// do_while_expr
		//	: 'do' expr 'while' '(' expr ')'
		//	;

		// This is just a placeholder so that enclosed declarations get
		// registered. We fill in the meat in exitFor_expr
		final DoWhileExpression doWhileExpression = new DoWhileExpression(null, null,
				ctx.getStart().getLine(), parsingDataArgumentAccordingToPass());
		
		parsingData_.pushDoWhileExpression(doWhileExpression);
	}
	
	@Override public void exitDo_while_expr(@NotNull KantParser.Do_while_exprContext ctx)
	{
		// do_while_expr
		//	: 'do' expr 'while' '(' expr ')'

		final Expression conditional = parsingData_.popExpression();
		final Expression body = parsingData_.popExpression();
		final DoWhileExpression expression = parsingData_.popDoWhileExpression();
		
		body.setResultIsConsumed(true);
		conditional.setResultIsConsumed(true);
		
		if (conditional.type() != StaticScope.globalScope().lookupTypeDeclaration("boolean")) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Condition in ``do / while«« statement is not of type boolean", conditional.getText(),
					" of type ", conditional.type().name());
		}
		
		expression.reInit(conditional, body);
		
		parsingData_.pushExpression(expression);
		
		if (printProductionsDebug) {
			if (null != ctx.expr() && null != ctx.expr(1)) {
				System.err.println("do_while_expr : 'do' expr 'while' '(' expr ')'");
			} else {
				System.err.println("do_while_expr : 'do' expr 'while' '(' ??? ')'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterSwitch_expr(@NotNull KantParser.Switch_exprContext ctx)
	{
		// : 'switch' '(' expr ')' '{'  ( switch_body )* '}'
		final SwitchExpression switchExpression = new SwitchExpression(parsingDataArgumentAccordingToPass());
		parsingData_.pushSwitchExpr(switchExpression);
	}
	
	@Override public void exitSwitch_expr(@NotNull KantParser.Switch_exprContext ctx)
	{
		// : 'switch' '(' expr ')' '{'  ( switch_body )* '}'
		
		final SwitchExpression switchExpression = parsingData_.popSwitchExpr();
		assert switchExpression instanceof SwitchExpression;
		
		// Set all the goodies. The body is already taken care of.
		final Expression expr = parsingData_.popExpression();
		expr.setResultIsConsumed(true);
		switchExpression.addExpression(expr);
		parsingData_.pushExpression(switchExpression);
	
		final Expression expressionToSwitchOn = switchExpression.switchExpression();
		if (null != expressionToSwitchOn) {
			final Type switchExpressionType = expressionToSwitchOn.type();
			for (SwitchBodyElement aCase : switchExpression.orderedSwitchBodyElements()) {
				if (aCase.isDefault()) continue;
				if (switchExpressionType.canBeConvertedFrom(aCase.expression().type()) == false) {
					errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Case statement with expression of type ",
							aCase.type().name(), " is incompatible with switch expression of type ",
							switchExpressionType.name());
				}
			}
		}
		
		if (printProductionsDebug) {
			System.err.println("switch_expr : 'switch' '(' expr ')' '{'  ( switch_body )* '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterSwitch_body(@NotNull KantParser.Switch_bodyContext ctx)
	{
		// : ( 'case' constant | 'default' ) ':' expr_and_decl_list
		
		currentScope_ = new StaticScope(currentScope_, true);
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart().getLine());
		parsingData_.pushExprAndDecl(newList);
	}
	
	@Override public void exitSwitch_body(@NotNull KantParser.Switch_bodyContext ctx)
	{
		// : ( 'case' constant | 'default' ) ':' expr_and_decl_list
		
		Constant constant = null;
		boolean isDefault = false;
		if (null != ctx.constant()) {
			final Expression temp = parsingData_.popExpression();
			if (temp instanceof Constant == false) {
				ErrorLogger.error(ErrorType.Internal, ctx.getStart().getLine(), "Case statement has non-const expression: `",
					temp.getText(), "«", "");
				constant = new Constant.IntegerConstant(0);
			} else {
				constant = (Constant)temp;
			}
			if (null != parsingData_.currentSwitchExpr().elementForConstant(constant)) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Switch statement has multiple clauses for ",
						constant.getText(), ".", "");
			}
		} else {
			isDefault = true;
			if (parsingData_.currentSwitchExpr().hasDefault()) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Switch statement has multiple default clauses",
						"", "", "");
			}
		}
		
		final ExprAndDeclList expr_and_decl_list = parsingData_.popExprAndDecl();
		
		if (null != constant) {
			constant.setResultIsConsumed(true);
		}
		
		final SwitchBodyElement switchBodyElement = new SwitchBodyElement(constant, isDefault, expr_and_decl_list);
		parsingData_.currentSwitchExpr().addSwitchBodyElement(switchBodyElement);
		currentScope_ = currentScope_.parentScope();
		
		if (printProductionsDebug) {
			System.err.println("switch_body : ( 'case' constant | 'default' ) ':' expr_and_decl_list");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitNull_expr(@NotNull KantParser.Null_exprContext ctx)
	{
		// null_expr : NULL
		
		final Expression expression = new NullExpression();
		
		parsingData_.pushExpression(expression);
		
		if (printProductionsDebug) {
			System.err.println("null_expr : NULL");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterConstant(@NotNull KantParser.ConstantContext ctx) {
	}
	
	@Override public void exitConstant(@NotNull KantParser.ConstantContext ctx) {
		// constant
	    //	: STRING
	    //	| INTEGER
	    //	| FLOAT
	    //	| BOOLEAN

		final Constant constant = (Constant)Expression.makeConstantExpressionFrom(ctx.getText());
		assert constant instanceof Constant;
		parsingData_.pushExpression(constant);
		
		if (printProductionsDebug) { System.err.print("constant : "); System.err.println(ctx.getText());}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterArgument_list(@NotNull KantParser.Argument_listContext ctx)
	{
	}

	@Override public void exitArgument_list(@NotNull KantParser.Argument_listContext ctx)
	{
		// : expr
        // | argument_list ',' expr
        // | /* null */
		
		// All done in pass 2
		
		if (ctx.expr() != null) {
			final Expression expr = parsingData_.popExpression();
			expr.setResultIsConsumed(true);
			parsingData_.currentArgumentList().addActualArgument(expr);
		} else {
			// no actual argument Ñ OK
		}
		
		if (printProductionsDebug) {
			if (ctx.expr() != null && ctx.argument_list() == null) {
				System.err.println("argument_list : expr");
			} else if (ctx.argument_list() != null) {
				System.err.println("argument_list : argument_list ',' expr");
			} else {
				System.err.println("argument_list : /* null */");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	
	
	// --------------------------------------------------------------------------------------
	
	
	
    protected ClassDeclaration lookupOrCreateNewClassDeclaration(String name, StaticScope newScope, ClassDeclaration rawBaseClass, int lineNumber) {
		return new ClassDeclaration(name, newScope, rawBaseClass, lineNumber);
	}
	protected void createNewClassTypeSuitableToPass(ClassDeclaration newClass, String name, StaticScope newScope, ClassType baseType) {
		// Pass1 only
		final ClassType newClassType = new ClassType(name, newScope, baseType);
		currentScope_.declareType(newClassType);
		newScope.setDeclaration(newClass);
		newClass.setType(newClassType);
	}
	
	protected void lookupOrCreateRoleDeclaration(String roleName, int lineNumber) {
		final RoleDeclaration requestedRole = currentScope_.lookupRoleDeclaration(roleName);
		if (null != requestedRole) {
			currentRole_ = requestedRole;
			
			// The way parsing is designed, these things should
			// be defined once on pass 1 and then referenced only
			// on subsequent passes.
			assert false;
		} else {
			final StaticScope rolesScope = new StaticRoleScope(currentScope_);
			final RoleDeclaration roleDecl = new RoleDeclaration(roleName, rolesScope, currentContext_, lineNumber);
			rolesScope.setDeclaration(roleDecl);
			declareRole(currentScope_, roleDecl);
			
			final RoleType roleType = new RoleType(roleName, rolesScope);
			currentScope_.declareType(roleType);
			
			currentRole_ = roleDecl;
			assert null != currentRole_;
			currentRole_.setType(roleType);
		}
		// caller may reset currentScope Ñ NOT us
	}
	
	protected void lookupOrCreateStagePropDeclaration(String roleName, int lineNumber) {
		final StagePropDeclaration requestedStageProp = (StagePropDeclaration)currentScope_.lookupRoleDeclaration(roleName);
		if (null != requestedStageProp) {
			assert requestedStageProp instanceof StagePropDeclaration;
			currentRole_ = requestedStageProp;
			
			// The way parsing is designed, these things should
			// be defined once on pass 1 and then referenced only
			// on subsequent passes.
			assert false;
		} else {
			final StaticScope stagePropScope = new StaticRoleScope(currentScope_);
			final StagePropDeclaration stagePropDecl = new StagePropDeclaration(roleName, stagePropScope, currentContext_, lineNumber);
			stagePropScope.setDeclaration(stagePropDecl);
			declareRole(currentScope_, stagePropDecl);
			
			final StagePropType stagePropType = new StagePropType(roleName, stagePropScope);
			currentScope_.declareType(stagePropType);
			
			currentRole_ = stagePropDecl;
			assert null != currentRole_;
			currentRole_.setType(stagePropType);
		}
		// caller may reset currentScope Ñ NOT us
	}
	
	protected void updateInitializationLists(Expression initializationExpr, ObjectDeclaration objDecl) {
		/* Nothing on Pass 1 */
	}
	
	private boolean ifIsInABlockContext(RuleContext myParent) {
		boolean retval = false;
		while (myParent instanceof ProgramContext == false) {
			if (myParent instanceof BlockContext) {
				retval = true;
				break;
			} else if (myParent instanceof Expr_and_decl_listContext) {
				myParent = myParent.getParent();
			} else {
				break;
			}
		}
		return retval;
	}
	
	public static long nestingLevelInsideBreakable(ParseTree ctx) {
		// This counts the number of dynamic scopes between a given
		// context and any enclosing "breakable" context (While, DoWhile,
		// For, and Switch). It is used to tell "break" statements how
		// many activation records to close when they are run.
		//
		// In fact we need to count only Block scopes, it seems. The
		// only language constructs that push a scope are message
		// sends, blocks, and For loops. Since we stop at For loops
		// (because they are a breakable) we don't need to count them
		// (which we otherwise would because they push a scope).
		//
		// We do count the actual breakable level if it has
		// a scope.
		//
		// It's declared static only because it doesn't refer to "this".
		long retval = 0;

		for (; (null != ctx) && ((ctx instanceof ProgramContext) == false); ctx = ctx.getParent()) {
			/*
			if (ctx instanceof Expr_listContext) {
				// we just put this at the top as an optimization
				continue;
			} else
			*/
			
			if (ctx instanceof BlockContext) {
				retval++;
			} else if (ctx instanceof For_exprContext) {
				retval++;
				break;
			} else if (ctx instanceof While_exprContext) {
				break;
			} else if (ctx instanceof Do_while_exprContext) {
				break;
			} else if (ctx instanceof Switch_exprContext) {
				break;
			} else if (ctx instanceof Method_declContext) {
				// It doesn't matter how many open blocks we have
				// passed along the way - if we run into something
				// like a method scope, then there's no more chance
				// for finding a breakable scope in which this break
				// has meaning
				retval = -1;
				break;
			}
		}

		return retval;
	}
	
	public static long nestingLevelInsideBreakableForContinue(ParseTree ctx) {
		// Lie nestingLevelInsideBreakable, but for Continue rather than break.
		// The main difference is that we don't count the context of the for
		// loop itself, since it stays open across a continue execution
		long retval = 0;

		for (; (null != ctx) && ((ctx instanceof ProgramContext) == false); ctx = ctx.getParent()) {
			/*
			 if (ctx instanceof Expr_listContext) {
				// we just put this at the top as an optimization
				continue;
			} else
			*/
			if (ctx instanceof BlockContext) {
				retval++;
			} else if (ctx instanceof For_exprContext) {
				break;
			} else if (ctx instanceof While_exprContext) {
				break;
			} else if (ctx instanceof Do_while_exprContext) {
				break;
			} else if (ctx instanceof Switch_exprContext) {
				break;
			} else if (ctx instanceof Method_declContext) {
				// It doesn't matter how many open blocks we have
				// passed along the way - if we run into something
				// like a method scope, then there's no more chance
				// for finding a breakable scope in which this continue
				// statement has meaning
				retval = -1;
				break;
			}
		}
		return retval;
	}
	
	protected SimpleList variablesToInitialize_, initializationExpressions_;
	
	private DeclarationsAndInitializers processIdentifierList(Identifier_listContext identifier_list, Type type, int lineNumber)
	{
		variablesToInitialize_ = new SimpleList();
		initializationExpressions_ = new SimpleList();
		final List<ObjectDeclaration> objectDecls = new ArrayList<ObjectDeclaration>();
		
		this.processIdentifierListRecursive(identifier_list, type, lineNumber);
		
		final List<BodyPart> intializationExpressionsToReturn = new ArrayList<BodyPart>();
		
		final long initializationCount = variablesToInitialize_.count();
		for (int k = 0; k < initializationCount; k++) {
			final Expression initializationExpression = (Expression)initializationExpressions_.objectAtIndex(k);
			initializationExpression.setResultIsConsumed(true);
			final ObjectDeclaration objDecl = (ObjectDeclaration)variablesToInitialize_.objectAtIndex(k);
			final Type expressionType = initializationExpression.type();
			final Type declarationType = objDecl.type();
			if (null != declarationType && declarationType.canBeConvertedFrom(expressionType)) {
				// OLD:
				objDecl.setInitializationExpression(initializationExpression);
				
				// Still need this
				objectDecls.add(objDecl);
				
				final IdentifierExpression lhs = new IdentifierExpression(objDecl.name(), declarationType, currentScope_);
				final AssignmentExpression initialization = new AssignmentExpression(lhs, "=", initializationExpression);
				intializationExpressionsToReturn.add(initialization);
			} else {
				errorHook5p2(ErrorType.Fatal, objDecl.lineNumber(), "Type mismatch in initialization of ", objDecl.name(), "", "");
			}
		}
		
		final DeclarationsAndInitializers retval = new DeclarationsAndInitializers(objectDecls, intializationExpressionsToReturn);
		return retval;
	}
	
	private void processIdentifierListRecursive(Identifier_listContext identifier_list, Type type, int lineNumber)
	{
		final List<ParseTree> children = identifier_list.children;
		Token tok;
		ObjectDeclaration objDecl = null;
		for (ParseTree pt : children) {
			if (pt instanceof TerminalNodeImpl) {
				final TerminalNodeImpl tnpt = (TerminalNodeImpl)pt;
				if (tnpt.getChildCount() == 0) {
					tok = tnpt.getSymbol();
					final String tokAsText = tok.getText();
					if (tokAsText.equals("=") == true) {
						; // we pick it up under the ExprContext check below
					} else if (tokAsText.equals(",") == true) {
						;	// skip it; it separates elements
					} else {
						objDecl = currentScope_.lookupObjectDeclaration(tokAsText);
						if (null == objDecl) {
							objDecl = new ObjectDeclaration(tokAsText, type, lineNumber);
							declareObjectSuitableToPass(currentScope_, objDecl);
						} else {
							// Doesn't hurt to update type
							objDecl.updateType(type);
						}
					}
				} else {
					for (int i = 0; i < tnpt.getChildCount(); i++) {
						final ParseTree pt2 = tnpt.getChild(i);
						assert pt2 instanceof TerminalNodeImpl;
						final TerminalNodeImpl tnpt2 = (TerminalNodeImpl)pt2;
						tok = tnpt2.getSymbol();
						final String tokAsText = tok.getText();
						if (tokAsText.equals("=") == true) {
							; // we get it with the ExprContext catch below
						} else if (tokAsText.equals(",") == true) {
							; // skip it; it separates elements
						} else {
							objDecl = currentScope_.lookupObjectDeclaration(tokAsText);
							if (null == objDecl) {
								objDecl = new ObjectDeclaration(tokAsText, type, lineNumber);
								declareObjectSuitableToPass(currentScope_, objDecl);
							} else {
								// Doesn't hurt to update type
								objDecl.updateType(type);
							}
						}
					}
				}
			} else if (pt instanceof Identifier_listContext) {
				this.processIdentifierListRecursive((Identifier_listContext)pt, type, lineNumber);
				// System.err.print("Alert: ");
				// System.err.println(pt.getText());
			} else if (pt instanceof ExprContext) {
				final Expression initializationExpr = parsingData_.popExpression();
				assert initializationExpr != null;
				assert objDecl != null;
				updateInitializationLists(initializationExpr, objDecl);
			} else {
				assert false;
			}
		}
	}

	@Override public void enterParam_list(@NotNull KantParser.Param_listContext ctx)
	{
		if (ctx.param_decl() != null) {
			// : param_decl
	        // | param_list ',' param_decl
	        // | /* null */
			
			// Do most of this parameter stuff here on the second pass, because
			// we should have most of the type information by then
			String formalParameterName = ctx.param_decl().JAVA_ID().getText();
			final String paramTypeName = ctx.param_decl().type_name().getText();
			Type paramType = currentScope_.lookupTypeDeclarationRecursive(paramTypeName);
			if (null == paramType) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Parameter type ", paramTypeName, " not declared for ", formalParameterName);
				paramType = parsingData_.globalScope().lookupTypeDeclaration("void");
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "You cannot name a formal parameter `this«.", "", "", "");
			}
			
			ObjectDeclaration newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart().getLine());
			
			// They go in reverse order...
			if (newFormalParameter.name().equals("this")) {
				formalParameterName = "_error" + String.valueOf(variableGeneratorCounter_);
				newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart().getLine());
			}
			parsingData_.currentFormalParameterList().addFormalParameter(newFormalParameter);
		} else {
			// empty parameter list Ñ it's OK. For now.
		}
	}
	
	private Expression jAVA_IDAtomUtility(String id) {
		Expression expression = null;
		Type type = null;
		assert null != id && 0 < id.length();
		final ObjectDeclaration objdecl = currentScope_.lookupObjectDeclarationRecursive(id);
		if (null != objdecl) {
			type = objdecl.type();
			
			// This ID may have been declared in a local scope, or in
			// the enclosing type. Find out what kind of identifier it
			// is and adjust accordingly (e.g., qualifying with "this",
			// etc.)
			final StaticScope scopeWhereDeclared = objdecl.enclosingScope();
			final Declaration associatedDeclaration = scopeWhereDeclared.associatedDeclaration();
			assert null != associatedDeclaration;
			if (associatedDeclaration instanceof MethodDeclaration) {
				// It's a local variable
				expression = new IdentifierExpression(id, type, scopeWhereDeclared);
			} else if (associatedDeclaration instanceof TypeDeclaration) {
				// Class or Context (Roles don't have local identifiers)
				// The qualifier must be "this" Ñ otherwise, the QualifiedIdentifierExpression
				// production will have caught it, and it's illegal to access more than
				// a single enclosing type scope away (lookup simply won't find it)
				final StaticScope enclosingMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
				
				final IdentifierExpression self = new IdentifierExpression("this", associatedDeclaration.type(), enclosingMethodScope);
				self.setResultIsConsumed(true);
				
				final QualifiedIdentifierExpression qie = new QualifiedIdentifierExpression(self, id, type);
				expression = qie;
			}
		} else {
			// What the hell
			type = StaticScope.globalScope().lookupTypeDeclaration("void");
			expression = new IdentifierExpression(id, type, null);
		}
		
		return expression;
	}
	
	private Expression processIndexedArrayElement(ParseTree arrayExprCtx, KantParser.ExprContext sexpCtx,
			TerminalNode ABELIAN_INCREMENT_OPCtx) {
		// | abelian_expr '[' expr ']' ABELIAN_INCREMENT_OP
        // | ABELIAN_INCREMENT_OP expr '[' expr ']'
		
		Expression retval = null;
		final Expression indexExpr = parsingData_.popExpression();
		indexExpr.setResultIsConsumed(true);
		
		final Expression rawArrayBase = parsingData_.popExpression();
		final ArrayType arrayType = (ArrayType)rawArrayBase.type();
		assert arrayType instanceof ArrayType;
		final Type baseType = arrayType.baseType();
		final ArrayExpression arrayBase = new ArrayExpression(rawArrayBase, baseType);
		arrayBase.setResultIsConsumed(true);
		
		final Interval JavaIDInterval = arrayExprCtx.getSourceInterval();
		final Interval OperatorInterval = ABELIAN_INCREMENT_OPCtx.getSourceInterval();
		final UnaryopExpressionWithSideEffect.PreOrPost preOrPost = JavaIDInterval.startsAfter(OperatorInterval)?
				UnaryopExpressionWithSideEffect.PreOrPost.Pre: UnaryopExpressionWithSideEffect.PreOrPost.Post;
		retval = new ArrayIndexExpressionUnaryOp(arrayBase, indexExpr, ABELIAN_INCREMENT_OPCtx.getText(), preOrPost);
		return retval;
	}
		
	protected void checkExprDeclarationLevel(RuleContext ctxParent, Token ctxGetStart) {
		/* Nothing */
	}
	
	
	// ----------------------------------------------------------------------------------------

	// WARNING. Tricky code here
	protected void declareObject(StaticScope s, ObjectDeclaration objdecl) { s.declareObject(objdecl); }
	public void declareRole(StaticScope s, RoleDeclaration roledecl) { s.declareRole(roledecl); }
	
	protected void errorHook5p1(ErrorType errorType, int i, String s1, String s2, String s3, String s4) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4);
	}
	public void errorHook5p2(ErrorType errorType, int i, String s1, String s2, String s3, String s4) {
		/* nothing */
	}
	protected void errorHook6p1(ErrorType errorType, int i, String s1, String s2, String s3, String s4, String s5, String s6) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4, s5, s6);
	}
	protected void errorHook6p2(ErrorType errorType, int i, String s1, String s2, String s3, String s4, String s5, String s6) {
		/* nothing */
	}
	protected ContextDeclaration lookupOrCreateContextDeclaration(String name, int lineNumber) {
		final StaticScope newScope = new StaticScope(currentScope_);
		final ContextDeclaration contextDecl = new ContextDeclaration(name, newScope, currentContext_, lineNumber);
		newScope.setDeclaration(contextDecl);
		final ContextType newContextType = new ContextType(name, newScope);
		declareTypeSuitableToPass(currentScope_, newContextType);
		contextDecl.setType(newContextType);
		currentScope_.declareContext(contextDecl);
		currentScope_ = newScope;
		return contextDecl;
	}
	protected ClassDeclaration lookupOrCreateClassDeclaration(String name, ClassDeclaration rawBaseClass, ClassType baseType, int lineNumber) {
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		final ClassDeclaration newClass = this.lookupOrCreateNewClassDeclaration(name, newScope, rawBaseClass, lineNumber);
		currentScope_.declareClass(newClass);
		this.createNewClassTypeSuitableToPass(newClass, name, newScope, baseType);
		currentScope_ = newScope;
		return newClass;
	}
	protected void declareTypeSuitableToPass(StaticScope scope, Type decl) {
		scope.declareType(decl);
	}
	protected void declareObjectSuitableToPass(StaticScope scope, ObjectDeclaration objDecl) {
		scope.declareObject(objDecl);
	}
	protected void declareFormalParametersSuitableToPass(StaticScope scope, ObjectDeclaration objDecl) {
		scope.declareObject(objDecl);
	}
	
	@SuppressWarnings("unused")
	private ExpressionStackAPI exprFromExprDotJAVA_ID(TerminalNode ctxJAVA_ID, Token ctxGetStart, TerminalNode ctxABELIAN_INCREMENT_OP) {
		// Certified Pass 1 version ;-) There is no longer any Pass 2 version
		UnaryopExpressionWithSideEffect.PreOrPost preOrPost;
		Type type = null;
		final ExpressionStackAPI qualifier = parsingData_.popRawExpression();
		Expression expression = null;
		final String javaIdString = ctxJAVA_ID.getText();
		preOrPost = UnaryopExpressionWithSideEffect.PreOrPost.Post;
		
		if (null != ctxABELIAN_INCREMENT_OP) {
			final Interval JavaIDInterval = ctxJAVA_ID.getSourceInterval();
			final Interval OperatorInterval = ctxABELIAN_INCREMENT_OP.getSourceInterval();
			preOrPost = JavaIDInterval.startsAfter(OperatorInterval)?
					UnaryopExpressionWithSideEffect.PreOrPost.Pre: UnaryopExpressionWithSideEffect.PreOrPost.Post;
		}
		
		if (null != qualifier && null != qualifier.type() && qualifier.type().name().equals("Class")) {
			// This is where we handle types like "System" for System.out.print*
			// Now we need to get the actual class of that name
			final ClassType theClass = (ClassType)currentScope_.lookupTypeDeclarationRecursive(qualifier.name());
			assert theClass instanceof ClassType;
			
			final ObjectDeclaration odecl = theClass.type().enclosedScope().lookupObjectDeclaration(javaIdString);
			if (null != odecl) {
				type = odecl.type();
				assert type != null;
			}
			
			if (null != ctxABELIAN_INCREMENT_OP) {
				expression = new QualifiedClassMemberExpressionUnaryOp(theClass, javaIdString, type, ctxABELIAN_INCREMENT_OP.getText(), preOrPost);
			} else {
				expression = new QualifiedClassMemberExpression(theClass, javaIdString, type);
			}
		} else {
			final Expression object = (Expression)qualifier;
			object.setResultIsConsumed(true);
			final ObjectDeclaration odecl = object.type().enclosedScope().lookupObjectDeclarationRecursive(javaIdString);
		
			if (null != odecl) {
				type = odecl.type();
				assert type != null;
			}
			
			if (null != ctxABELIAN_INCREMENT_OP) {
				expression = new QualifiedIdentifierExpressionUnaryOp(object, javaIdString, type, ctxABELIAN_INCREMENT_OP.getText(), preOrPost);
			} else {
				expression = new QualifiedIdentifierExpression(object, javaIdString, type);
			}
			
			if (null == odecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Field `", javaIdString,
						"««not found as member of ", object.type().name());
			}
		}
		
		if (null == expression) {
			assert null != expression;
		}
		
		return expression;
	}
	
	public <ExprType> Expression newExpr(List<ParseTree> ctxChildren, Token ctxGetStart, ExprType ctxExpr, MessageContext ctxMessage) {
		// : 'new' message
		// | 'new' type_name '[' expr ']'
		// Called in all passes.
		Expression expression = null;
		final String isItNew = ctxChildren.get(0).getText();
		final Message message = parsingData_.popMessage();
		if (isItNew.equals("new") == false) {
			// redundant now
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Invalid class operator: ", isItNew, "", "");
			expression = new NullExpression();
		} else if (null == ctxExpr && null != ctxMessage){
			// : 'new' message
			final String className = message.selectorName(); // I know ÑÊkludge ...
			final Type type = currentScope_.lookupTypeDeclarationRecursive(className);
			if ((type instanceof ClassType) == false && (type instanceof ContextType) == false) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "'new ", className, "': can apply 'new' only to a class or context type", "");
				expression = new NullExpression();
			} else {
				// On the first pass, message doesn't yet have an argument list
				expression = new NewExpression(type, message, ctxMessage);
				
				// This adds a hokey argument to the message that
				// is used mainly for signature checking Ñ to see
				// if there is a constructor that matches the
				// arguments of the "new" message.
				addSelfAccordingToPass(type, message, currentScope_);
			}
			
			// Is there a constructor?
			// This does anything on Passes 2 and 3 only
			ctorCheck(type, message, ctxGetStart);
		} else if (null != ctxExpr && null == ctxMessage) {
			// | 'new' type_name '[' abelian_expr ']'
			final Expression expr = parsingData_.popExpression();
			final Type type_name_expression = (Type)parsingData_.popRawExpression();
			assert type_name_expression instanceof Type;
			final String typeName = type_name_expression.name();
			final Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			if (null == type) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "'new ", typeName, " [] for undefined type: ", typeName);
				expression = new NullExpression();
			} else {
				expr.setResultIsConsumed(true);
				expression = new NewArrayExpression(type, expr);
			}
		} else {
			assert false;	// internal error of some kind
		}
		assert null != expression;
		return expression;
	}
	public void addSelfAccordingToPass(Type type, Message message, StaticScope scope) {
		/* Nothing */
	}
	public void ctorCheck(Type type, Message message, Token ctxGetStart) {
		/* Nothing */
	}

	// This is a template function mainly for historial reasons data back to
	// expr and sexpr, and should be updated. We separated those non-terminals
	// as a workaround for left-recursion in some of the productions. In some sense
	// we blew up the grammar to avoid the ambiguity; here, we bring the leaves
	// (the processing for the duplicate productions) back together.
	
	// You find something analogous with exitExpr in pass 1.
	
	public <ExprType> Expression messageSend(Token ctxGetStart, ExprType ctxExpr) {
		// | expr '.' message
		// | message
		// Pass 1 version
		// Pop the expression for the indicated object and message
		Expression object = null;
		Type type = null;
		if (ctxExpr != null) {
			object = parsingData_.popExpression();
		} else {
			final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
			object = new IdentifierExpression("this", Expression.nearestEnclosingMegaTypeOf(currentScope_), nearestMethodScope);
		}
		assert null != object;
			
		final Message message = parsingData_.popMessage();
			
		final Type objectType = object.type();
		assert null != objectType;
			
		final String methodSelectorName = message.selectorName();
		final ClassDeclaration classdecl = currentScope_.lookupClassDeclarationRecursive(objectType.name());
		final MethodDeclaration mdecl = classdecl != null?
						classdecl.enclosedScope().lookupMethodDeclaration(methodSelectorName, null, true):
						null;
		if (null == mdecl) {
			// skip it Ñ we've already barked at the user
			// ErrorLogger.error(ctx.getStart().getLine(), "Method `", methodSelectorName, "« not declared in class ", classdecl.name());
			type = StaticScope.globalScope().lookupTypeDeclaration("void");
		} else {
			type = mdecl.returnType();
			
			// This is tautological a no-op, because we
			// castrate this stuff within Pass 1
			// checkForMessageSendViolatingConstness(mdecl.signature(), ctxGetStart);
		}
		assert type != null;
		
		object.setResultIsConsumed(true);
		return new MessageExpression(object, message, type, ctxGetStart.getLine());
	}
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationIn(TypeDeclaration classDecl, String methodSelectorName, ActualOrFormalParameterList parameterList) {
		// Pass 1 version. Pass 2 / 3 version turns on signature checking
		return classDecl.enclosedScope().lookupMethodDeclaration(methodSelectorName, parameterList, true);
	}
	protected Type processReturnType(Token ctxGetStart, Expression object, Type objectType, Message message) {
		final String objectTypeName = objectType.name();
		final ClassDeclaration classDecl = currentScope_.lookupClassDeclarationRecursive(objectTypeName);
		final RoleDeclaration roleDecl = currentScope_.lookupRoleDeclarationRecursive(objectTypeName);
		final ContextDeclaration contextDecl = currentScope_.lookupContextDeclarationRecursive(objectTypeName);
		
		final String methodSelectorName = message.selectorName();
		assert null != methodSelectorName;
		
		MethodDeclaration mdecl = null;
		Type returnType = null;
		final ActualArgumentList actualArgumentList = message.argumentList();
		
		if (null != classDecl) {
			mdecl = processReturnTypeLookupMethodDeclarationIn(classDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "« not declared in class ", classDecl.name());
			}
		} else if (null != roleDecl) {
			// Calling a role method
			mdecl = processReturnTypeLookupMethodDeclarationIn(roleDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "« not declared in Role ", roleDecl.name());
			}
		} else if (null != contextDecl) {
			mdecl = processReturnTypeLookupMethodDeclarationIn(contextDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "« not declared in Context ", contextDecl.name());
			}
		} else {
			if (object.name().length() > 0) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class or Role for ", object.name(), "", "");
			} else {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class or Role of this ", "type", "", "");
			}
			assert null == mdecl;
		}
		
		if (null != mdecl) {
			final FormalParameterList formals = mdecl.formalParameterList();
			assert formals != null;
			final ActualArgumentList actuals = message.argumentList();
			assert actuals != null;
			
			final TypeDeclaration typeDecl = null != classDecl? classDecl: contextDecl;
			
			// Type check is polymorphic in compiler passes
			this.typeCheck(formals, actuals, mdecl, typeDecl, ctxGetStart);
			
			returnType = mdecl.returnType();
		}
		
		returnType = null == returnType? StaticScope.globalScope().lookupTypeDeclaration("void"): returnType;
		
		return returnType;
	}
	
	protected MethodDeclaration getEnclosingMethodDecl() {
		StaticScope scope = currentScope_;
		MethodDeclaration retval = null;
		while (scope != StaticScope.globalScope() &&
				false == (currentScope_.associatedDeclaration() instanceof MethodDeclaration)) {
			scope = scope.parentScope();
		}
		if (null != scope && scope != StaticScope.globalScope()) {
			retval = (MethodDeclaration)scope.associatedDeclaration();
			assert null == retval || retval instanceof MethodDeclaration;
		}
		return retval;
	}
	protected MethodDeclaration methodWithinWhichIAmDeclared(StaticScope scope) {
		// Like getEnclosingMethodDecl, but returns null if there
		// is an intervening class, role
		MethodDeclaration retval = null;
		while (scope != null && scope != StaticScope.globalScope() &&
				false == (currentScope_.associatedDeclaration() instanceof MethodDeclaration)) {
			final Declaration associatedDeclaration = scope.associatedDeclaration();
			if (associatedDeclaration instanceof ContextDeclaration) {
				scope = null;
			} else if (associatedDeclaration instanceof StagePropDeclaration) {
				scope = null;
			} else if (associatedDeclaration instanceof RoleDeclaration) {
				scope = null;
			} else if (associatedDeclaration instanceof ClassDeclaration) {
				scope = null;
			} else if (associatedDeclaration instanceof StagePropDeclaration) {
				scope = null;
			} else {
				scope = scope.parentScope();
			}
		}
		if (null != scope && scope != StaticScope.globalScope()) {
			retval = (MethodDeclaration)scope.associatedDeclaration();
			assert null == retval || retval instanceof MethodDeclaration;
		}
		return retval;
	}
	protected RoleDeclaration isRoleAssignmentWithinContext(String idName) {
		RoleDeclaration retval = null;
		final MethodDeclaration enclosingMethod = getEnclosingMethodDecl();
		final Declaration associatedDeclaration = null != enclosingMethod?
				enclosingMethod.enclosingScope().associatedDeclaration():
					null;
		if (null != enclosingMethod && associatedDeclaration instanceof ContextDeclaration) {
			final ContextDeclaration contextDeclaration = (ContextDeclaration)associatedDeclaration;
			final RoleDeclaration roleDeclaration = contextDeclaration.enclosedScope().lookupRoleDeclaration(idName);
			retval = roleDeclaration;
		}
		return retval;
	}
	public Expression idExpr(TerminalNode ctxJAVA_ID, Token ctxGetStart) {
		// Pass 1 version
		final StaticScope globalScope = StaticScope.globalScope();
		Expression expression = null;
		Type type = null;
		StaticScope declaringScope = null;
		final String idName = ctxJAVA_ID.getText();
		final ObjectDeclaration objdecl = currentScope_.lookupObjectDeclarationRecursive(idName);
		final RoleDeclaration roleDecl = currentScope_.lookupRoleDeclarationRecursive(idName);
		if (null != objdecl) {
			if (null != this.isRoleAssignmentWithinContext(idName)) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			} else {
				type = objdecl.type();
			}
			declaringScope = objdecl.enclosingScope();
			
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
			
			// NOTE: This will also lump in references to Role identifiers
			// They are distinguished by its enclosing scope
			expression = new IdentifierExpression(idName, type, declaringScope);
		} else if (null != roleDecl) {
			// Someone is invoking a role. Cool.
			declaringScope = roleDecl.enclosingScope();
			final RoleType roleType = (RoleType)declaringScope.lookupTypeDeclaration(idName);	// Type$RoleType
			assert roleType instanceof RoleType;
			if (this.isInsideMethodDeclaration(ctxJAVA_ID)) {
				final StaticScope nearestEnclosingMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
				final IdentifierExpression qualifier = new IdentifierExpression("this", roleType, nearestEnclosingMethodScope);
				qualifier.setResultIsConsumed(true);
				expression = new QualifiedIdentifierExpression(qualifier, idName, roleType);
			} else {
				assert false;	// could be a static initializer? If so: to be coded.
			}
		} else {
			final ClassDeclaration cdecl = currentScope_.lookupClassDeclarationRecursive(idName);
			if (null != cdecl) {
				type = globalScope.lookupTypeDeclaration("Class");
				declaringScope = globalScope;
			}
			
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
			
			// NOTE: This will also lump in references to Role identifiers
			// They are distinguished by its enclosing scope
			expression = new IdentifierExpression(idName, type, declaringScope);
		}
		
		assert null != expression;
		return expression;
	}
	private boolean isInsideMethodDeclaration(TerminalNode ctx) {
		ParseTree walker = ctx;
		boolean retval = false;
		while (!(walker instanceof Type_declarationContext)) {
			if (walker instanceof TerminalNodeImpl) {
				;
			} else if (walker instanceof ExprContext) {
				;
			// } else if (walker instanceof Expr_listContext) {
			// 	;
			} else if (walker instanceof Expr_and_decl_listContext) {
				;
			} else if (walker instanceof Method_declContext) {
				retval = true;
				break;
			} else if (walker instanceof ProgramContext) {
				retval = false;
				break;
			} else if (walker instanceof Abelian_exprContext) {
				;
			} else {
				assert false;
				retval = false;
				break;
			}
			walker = walker.getParent();
		}

		return retval;
	}
	public void binopTypeCheck(final Expression leftExpr, String operationAsString,
			final Expression rightExpr, Token ctxGetStart) {
		/* Nothing */
	}
	protected void typeCheck(FormalParameterList formals, ActualArgumentList actuals,
			MethodDeclaration mdecl, TypeDeclaration classdecl, @NotNull Token ctxGetStart)
	{
		/* Nothing */
	}
	protected void reportMismatchesWith(int lineNumber, RoleType lhsType, Type rhsType) {
		/* Nothing */
	}
	public Expression assignmentExpr(Expression lhs, String operator, Expression rhs,
			KantParser.Abelian_exprContext ctx) {
		// abelian_expr ASSIGN expr
		assert null != rhs;
		assert null != lhs;
		
		final Token ctxGetStart = ctx.getStart();
			
		final Type lhsType = lhs.type(), rhsType = rhs.type();
		
		@SuppressWarnings("unused")
		boolean tf = lhsType instanceof RoleType;
		tf = null != rhsType;
		tf = lhsType.canBeConvertedFrom(rhsType, ctx.getStart().getLine(), this);
		
		
		
		
		if (lhsType instanceof RoleType && null != rhsType && lhsType.canBeConvertedFrom(rhsType) == false) {
			errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Role ", lhsType.name(), " cannot be played by object of type ", rhsType.name(), ":", "");
			this.reportMismatchesWith(ctxGetStart.getLine(), (RoleType)lhsType, rhsType);
		} else if (null != lhsType && null != rhsType && lhsType.canBeConvertedFrom(rhsType) == false) {
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Type of ", lhsType.name(), " is incompatible with expression type ", rhsType.name());
		} else if (lhs instanceof ArrayIndexExpression) {
			final Type anotherLhsType = ((ArrayIndexExpression)lhs).baseType();
			if (null != anotherLhsType && null != rhsType && anotherLhsType.canBeConvertedFrom(rhsType) == false) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Type of ", lhs.getText(), " is incompatible with expression type ", rhsType.name());
			}
		} else if ((lhs instanceof IdentifierExpression) == false &&
				   (lhs instanceof QualifiedIdentifierExpression) == false) {
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Can assign only to an identifer or qualified identifier", "", "", "");
		}
		
		rhs.setResultIsConsumed(true);
		final AssignmentExpression retval = new AssignmentExpression(lhs, operator, rhs);
		checkForAssignmentViolatingConstness(retval, ctx.getStart());
		
		return retval;
	}
	
	protected void checkForIncrementOpViolatingExpressionConstness(UnaryopExpressionWithSideEffect assignment, KantParser.ExprContext ctx) {
		/* nothing on pass 1 */
	}
	
	protected void checkForIncrementOpViolatingConstness(ArrayIndexExpressionUnaryOp expression, Token ctxGetStart) {
		/* nothing on pass 1 */
	}
	
	protected void checkForIncrementOpViolatingIdentifierConstness(UnaryopExpressionWithSideEffect assignment, Token ctxGetStart) {
		/* nothing on pass 1 */
	}
	
	protected void checkForAssignmentViolatingConstness(AssignmentExpression assignment, Token ctxGetStart) {
		/* nothing on pass 1 */
	}
	
	protected void checkForMessageSendViolatingConstness(MethodSignature signature, Token ctxGetStart) {
		/* nothing on pass 1 */
	}
	
	protected <ExprType> Expression expressionFromReturnStatement(ExprType ctxExpr, RuleContext unused, Token ctxGetStart)
	{
		Expression returnExpression = null;
		if (null != ctxExpr) {
			returnExpression = parsingData_.popExpression();
		}
		returnExpression.setResultIsConsumed(true);
		final Expression retval = new ReturnExpression(returnExpression, ctxGetStart.getLine());
		return retval;
	}
	
	protected ParsingData parsingDataArgumentAccordingToPass() {
		return parsingData_;
	}
	
	private void stackSnapshotDebug() {
		parsingData_.stackSnapshotDebug();
	}

	
	// ------------------------------------------------------------------------------------------------------- 
	protected ContextDeclaration currentContext_;	// should probably be a stack for generality. FIXME
	protected ParsingData parsingData_;
	protected StaticScope currentScope_;
	protected RoleDeclaration currentRole_;
	private int variableGeneratorCounter_;
    // -------------------------------------------------------------------------------------------------------
}