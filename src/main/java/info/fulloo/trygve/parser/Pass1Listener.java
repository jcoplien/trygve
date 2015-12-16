package info.fulloo.trygve.parser;

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
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import info.fulloo.trygve.configuration.ConfigurationOptions;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.BodyPart;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Message;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.DeclarationList;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.ContextType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.StagePropType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Constant;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.ExpressionStackAPI;
import info.fulloo.trygve.expressions.Constant.BooleanConstant;
import info.fulloo.trygve.expressions.Expression.ArrayExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.AssignmentExpression;
import info.fulloo.trygve.expressions.Expression.BlockExpression;
import info.fulloo.trygve.expressions.Expression.BreakExpression;
import info.fulloo.trygve.expressions.Expression.ContinueExpression;
import info.fulloo.trygve.expressions.Expression.DoWhileExpression;
import info.fulloo.trygve.expressions.Expression.DupMessageExpression;
import info.fulloo.trygve.expressions.Expression.IndexExpression;
import info.fulloo.trygve.expressions.Expression.ForExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.IfExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.Expression.NewArrayExpression;
import info.fulloo.trygve.expressions.Expression.NewExpression;
import info.fulloo.trygve.expressions.Expression.NullExpression;
import info.fulloo.trygve.expressions.Expression.PowerExpression;
import info.fulloo.trygve.expressions.Expression.ProductExpression;
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
import info.fulloo.trygve.mylibrary.SimpleList;
import info.fulloo.trygve.parser.KantParser.Abelian_atomContext;
import info.fulloo.trygve.parser.KantParser.Abelian_exprContext;
import info.fulloo.trygve.parser.KantParser.Abelian_productContext;
import info.fulloo.trygve.parser.KantParser.Abelian_unary_opContext;
import info.fulloo.trygve.parser.KantParser.Argument_listContext;
import info.fulloo.trygve.parser.KantParser.BlockContext;
import info.fulloo.trygve.parser.KantParser.Compound_type_nameContext;
import info.fulloo.trygve.parser.KantParser.Do_while_exprContext;
import info.fulloo.trygve.parser.KantParser.ExprContext;
import info.fulloo.trygve.parser.KantParser.Expr_and_decl_listContext;
import info.fulloo.trygve.parser.KantParser.For_exprContext;
import info.fulloo.trygve.parser.KantParser.Identifier_listContext;
import info.fulloo.trygve.parser.KantParser.MessageContext;
import info.fulloo.trygve.parser.KantParser.Method_declContext;
import info.fulloo.trygve.parser.KantParser.Method_decl_hookContext;
import info.fulloo.trygve.parser.KantParser.Method_signatureContext;
import info.fulloo.trygve.parser.KantParser.Object_declContext;
import info.fulloo.trygve.parser.KantParser.ProgramContext;
import info.fulloo.trygve.parser.KantParser.Switch_exprContext;
import info.fulloo.trygve.parser.KantParser.Type_declarationContext;
import info.fulloo.trygve.parser.KantParser.While_exprContext;
import info.fulloo.trygve.semantic_analysis.Program;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticRoleScope;


public class Pass1Listener extends KantBaseListener {
	private boolean printProductionsDebug;
	private boolean stackSnapshotDebug;
	
	public Pass1Listener(final ParsingData parsingData) {
		parsingData_ = parsingData;
		
		currentScope_ = parsingData_.globalScope();
		currentContext_ = null;
		variableGeneratorCounter_ = 101;
		
		currentRole_ = null;
		currentInterface_ = null;
		
		printProductionsDebug = ConfigurationOptions.tracePass1();
		stackSnapshotDebug = ConfigurationOptions.stackSnapshotDebug();
	}
	
	private static class DeclarationsAndInitializers {
		public DeclarationsAndInitializers(final List<ObjectDeclaration> objectDecls, final List<BodyPart> initializations) {
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
	
	@Override public void enterProgram(KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// : type_declaration_list
		
		final TypeDeclarationList currentList = new TypeDeclarationList(ctx.getStart().getLine());
		parsingData_.pushTypeDeclarationList(currentList);
	}
	
	@Override public void exitProgram(KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// | type_declaration_list
		
		final TypeDeclarationList currentList = parsingData_.popTypeDeclarationList();
		final TypeDeclarationList templateInstantiationList = parsingData_.currentTemplateInstantiationList();
		
		if (null == ctx.main()) {
			if (null != ctx.getStop()) {
				errorHook5p2(ErrorType.Fatal, ctx.getStop().getLine(), "Missing main expression.", "", "", "");
			} else {
				errorHook5p2(ErrorType.Fatal, 1, "Missing main expression.", " Did you enter any program at all?", "", "");
			}
			new Program(null, currentList, templateInstantiationList);	// static singleton
		} else {
			final Expression main = parsingData_.popExpression();
			new Program(main, currentList, templateInstantiationList);	// static singleton
		}
		
		printProductionsDebug = false;
		stackSnapshotDebug = false;
		
		if (printProductionsDebug) {
			if (null != ctx.main()) {
				System.err.println("program : type_declaration_list main");
			} else {
				System.err.println("program : type_declaration_list [ERROR]");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterMain(KantParser.MainContext ctx)
	{
		// main
		//	: expr
		
		/* nothing */
	}
	
	@Override public void exitMain(KantParser.MainContext ctx)
	{
		// main
		//	: expr
		
		if (printProductionsDebug) {
			System.err.println("main : expr");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterType_declaration_list(KantParser.Type_declaration_listContext ctx)
	{
		// type_declaration_list
	    //		: type_declaration
	    //		| type_declaration_list type_declaration
	    // 		| /* null */
		
		/* nothing */
	}

	@Override public void enterType_declaration(KantParser.Type_declarationContext ctx)
	{
		// : 'context' JAVA_ID '{' context_body '}'
		// | 'class'   JAVA_ID type_parameters (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		// | 'interface' JAVA_ID '{' interface_body '}'
		
		final Type objectBaseClass = StaticScope.globalScope().lookupTypeDeclaration("Object");
		assert null != objectBaseClass;
		assert objectBaseClass instanceof ClassType;
		ClassType baseType = (ClassType)objectBaseClass;
		
		final String name = ctx.JAVA_ID(0).getText();
		ClassDeclaration rawBaseClass = null;
		if (null != ctx.context_body()) {
			currentContext_ = this.lookupOrCreateContextDeclaration(name, ctx.getStart().getLine());
		} else if (null != ctx.class_body()) {
			final TerminalNode baseClassNode = ctx.JAVA_ID(1);
			
			if (null != baseClassNode) {
				final String baseTypeName = baseClassNode.getText();
				final Type rawBaseType = currentScope_.lookupTypeDeclarationRecursive(baseTypeName);
				rawBaseClass = currentScope_.lookupClassDeclarationRecursive(baseTypeName);
				if ((rawBaseType instanceof ClassType) == false) {
					// Leave to pass 2
					errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(), "Base type `", baseTypeName,
							"' is not a declared class type as base of `", name, "'.", "");
				} else {
					baseType = (ClassType)rawBaseType;
					if (baseType.name().equals(name)) {
						errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Er, no.", "", "", "");
					}
				}
			} else {
				// Redundant: for readability...
				rawBaseClass = (ClassDeclaration)objectBaseClass.enclosedScope().associatedDeclaration();
			}
			
			if (null != ctx.type_parameters()) {
				final TemplateDeclaration newTemplate = this.lookupOrCreateTemplateDeclaration(name, rawBaseClass, baseType, ctx.getStart().getLine());
				currentScope_ = newTemplate.enclosedScope();
				parsingData_.pushTemplateDeclaration(newTemplate);
			} else {
				final ClassDeclaration newClass = this.lookupOrCreateClassDeclaration(name, rawBaseClass, baseType, ctx.getStart().getLine());
				currentScope_ = newClass.enclosedScope();
				parsingData_.pushClassDeclaration(newClass);
			}
		} else if (null != ctx.interface_body()) {
			currentInterface_ = this.lookupOrCreateInterfaceDeclaration(name, ctx.getStart().getLine());
			currentScope_ = currentInterface_.enclosedScope();
		} else {
			assert false;	// need diagnostic message for user later
		}
	}
	
	@Override public void exitType_declaration(KantParser.Type_declarationContext ctx)
	{
		// : 'context' JAVA_ID '{' context_body '}'
		// | 'class'   JAVA_ID type_parameters (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID (implements_list)* 'extends' JAVA_ID '{' class_body '}'
		// | 'interface' JAVA_ID '{' interface_body '}'
		
		// One version serves all three passes
		assert null != currentScope_;
		final Declaration rawNewDeclaration = currentScope_.associatedDeclaration();
		assert rawNewDeclaration instanceof TypeDeclaration;	
		final TypeDeclaration newDeclaration = (TypeDeclaration)rawNewDeclaration;
		parsingData_.currentTypeDeclarationList().addDeclaration(newDeclaration);
		
		final StaticScope parentScope = currentScope_.parentScope();
		currentScope_ = parentScope;
		if (null != currentContext_) {
			currentContext_ = currentContext_.parentContext();
		}
		
		if (newDeclaration instanceof ClassDeclaration) {
			if (null != ((ClassDeclaration)newDeclaration).generatingTemplate()) {
				parsingData_.popTemplateDeclaration();
			}
			this.implementsCheck((ClassDeclaration)newDeclaration, ctx.getStart().getLine());
		} else if (newDeclaration instanceof ClassDeclaration) {
			parsingData_.popClassDeclaration();
			// implements_list is taken care of along the way
		} else if (newDeclaration instanceof TemplateDeclaration) {
			parsingData_.popTemplateDeclaration();
		}
		
		currentInterface_ = null;
		currentRole_ = null;
		
		if (printProductionsDebug) {
			if (ctx.context_body() != null) {
				System.err.println("type_declaration : 'context' JAVA_ID '{' context_body '}'");
			} else if (ctx.class_body() != null && ctx.JAVA_ID(1) == null) {
				System.err.println("type_declaration : 'class' JAVA_ID '{' class_body   '}'");
			} else if (ctx.interface_body() != null && ctx.JAVA_ID(1) == null) {
				System.err.println("type_declaration : 'interface' JAVA_ID '{' interface_body   '}'");
			} else {
				System.err.println("type_declaration : 'class' JAVA_ID 'extends' JAVA_ID '{' class_body  '}'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	protected void implementsCheck(final ClassDeclaration newDeclaration, int lineNumber) {
		// nothing on pass one
	}
	
	@Override public void exitImplements_list(KantParser.Implements_listContext ctx) {
		// : 'implements' JAVA_ID
		// | implements_list ',' JAVA_ID
		
		final String interfaceName = ctx.JAVA_ID().getText();
		InterfaceDeclaration anInterface = currentScope_.lookupInterfaceDeclarationRecursive(interfaceName);
		
		if (null == anInterface) {
			errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(),
					"Interface ", interfaceName, " is not declared.", "", "", "");
			anInterface = new InterfaceDeclaration(" error", null, ctx.getStart().getLine());
		}
		
		final ClassType classType = (ClassType)parsingData_.currentClassDeclaration().type();
		this.addInterfaceTypeSuitableToPass(classType, (InterfaceType)anInterface.type());
		
		if (printProductionsDebug) {
			if (ctx.implements_list() != null) {
				System.err.println("implements_list : implements_list ',' JAVA_ID");
			} else {
				System.err.println("implements_list : JAVA_ID");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	protected void addInterfaceTypeSuitableToPass(final ClassType classType, final InterfaceType interfaceType) {
		// nothing on pass one
	}
	
	@Override public void exitType_parameters(KantParser.Type_parametersContext ctx) {
		// : '<' type_parameter (',' type_parameter)* '>'
		// Pop from the expression stack and add to current template declaration
		final TemplateDeclaration currentTemplateDecl = parsingData_.currentTemplateDeclaration();
		final Map<String,String> dupMap = new LinkedHashMap<String,String>();
		final int numberOfActualParameters = ctx.type_parameter().size();
		for (int i = 0; i < numberOfActualParameters; i++) {
			final IdentifierExpression type_name = (IdentifierExpression)parsingData_.popExpression();
			if (dupMap.containsKey(type_name.name())) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"Duplicate template parameter name: ", type_name.name(), "", "");
				currentTemplateDecl.addTypeParameter(new IdentifierExpression("$error$",
						StaticScope.globalScope().lookupTypeDeclaration("void"), currentScope_),
						numberOfActualParameters);
			} else {
				dupMap.put(type_name.name(), type_name.name());
				currentTemplateDecl.addTypeParameter(type_name, numberOfActualParameters);
			}
		}
		
		if (printProductionsDebug) {
			System.err.println("type_parameters : '<' type_parameter (',' type_parameter)* '>'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitType_parameter(KantParser.Type_parameterContext ctx) {
		// : type_name ('extends' type_name)?
		// Push it on the expression stack (not ideal, but its there)
		
		// But, first, pop off the type_name entry... We don't use it here
		@SuppressWarnings("unused")
		final Object unused_type_name = parsingData_.popRawExpression();
		
		final TemplateDeclaration currentTemplateDecl = parsingData_.currentTemplateDeclaration();
		final StaticScope scope = currentTemplateDecl.enclosedScope();
		ClassType baseClassType = null;
		if (ctx.type_name().size() > 1) {
			final String baseClassName = ctx.type_name(1).getText();
			final Type rawBaseClassType = scope.lookupTypeDeclarationRecursive(baseClassName);
			assert null == rawBaseClassType || rawBaseClassType instanceof ClassType;
			baseClassType = (ClassType)rawBaseClassType; // may be null
		}
		final Type type = new TemplateParameterType(ctx.type_name(0).getText(), baseClassType);
		final Expression type_name = new IdentifierExpression(ctx.type_name(0).getText(), type, scope);
		parsingData_.pushExpression(type_name);
		
		if (printProductionsDebug) {
			System.err.println("type_parameter : type_name ('extends' type_name)?");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterType_list(KantParser.Type_listContext ctx) {
		// : '<' type_name (',' type_name)* '>'
		final ArrayList<String> typeNameList = new ArrayList<String>();
		parsingData_.pushTypeNameList(typeNameList);
	}
	@Override public void exitType_list(KantParser.Type_listContext ctx) {
		// : '<' type_name (',' type_name)* '>'
		final List<String> currentTypeNameList = parsingData_.currentTypeNameList();
		for (int i = 0; i < ctx.type_name().size(); i++) {
			// But, first, pop off the type_name entry... We don't use it here
			@SuppressWarnings("unused")
			final Object unused_type_name = parsingData_.popRawExpression();
			
			currentTypeNameList.add(ctx.type_name(i).getText());
		}
		
		if (printProductionsDebug) {
			System.err.println("type_list : '<' type_name (',' type_name)* '>'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	
	@Override public void enterContext_body(KantParser.Context_bodyContext ctx)
	{
		// context_body
	    //		: context_body context_body_element
	    //		| context_body_element
	    //		| /* null */
		
		/* nothing */
	}

	@Override public void enterContext_body_element(KantParser.Context_body_elementContext ctx)
	{
		// context_body_element
	    //	: method_decl
	    //	| object_decl
	    //	| role_decl
	    //	| stageprop_decl
		
		/* nothing */
	}

	@Override public void enterRole_decl(KantParser.Role_declContext ctx)
	{
		// : 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		// | 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		//
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isRoleArray = vecText.length() > 0;	// "[]"
		
		if (null != ctx.access_qualifier()) {
			errorHook5p1(ErrorType.Warning, ctx.getStart().getLine(), "WARNING: Gratuitous access qualifier `",
					ctx.access_qualifier().getText(), "' ignored", ".");
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role - resulting grammar error got here with that
			// null condition. Not much to do but to punt

			final String roleName = JAVA_ID.getText();
		
			// Return value is through currentRole_
			lookupOrCreateRoleDeclaration(roleName, ctx.getStart().getLine(), isRoleArray);
		
			assert null != currentRole_;
		
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorType.Fatal, ctx.getStart().getLine(), "Role ", roleName, " can be declared only in a Context scope - not ", currentScope_.name());
			}
			currentScope_ = currentRole_.enclosedScope();
		} else {
			currentRole_ = null;
		}
	}
	
	@Override public void exitRole_decl(KantParser.Role_declContext ctx)
	{
		// : 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		// | 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		
		// All three passes. INVOKED BY PASS 2 VERSION (probably shouldn't
		// be, as it does nothing in pass 2. FIXME)
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isRoleArray = vecText.length() > 0;	// "[]"
		
		// In the self_methods rule we've been gathering signatures
		// in the "requires" section and storing them in the Role
		// declaration. Just tell the type about the declaration so
		// that type checking can track it back.
		
		if (null != currentRole_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See
			// the comment above on entry to the production.

			final Type rawRoleType = currentRole_.type();
			assert rawRoleType instanceof RoleType;
			final RoleType type = (RoleType)rawRoleType;
			type.setBacklinkToRoleDecl(currentRole_);

			currentRole_ = null;
			currentScope_ = currentScope_.parentScope();
		}
		if (printProductionsDebug) {
			if (ctx.self_methods() == null && ctx.access_qualifier() == null) {
				System.err.print("role_decl : ");
				if (isRoleArray) System.err.print("[] ");
				System.err.println("'role' JAVA_ID '{' role_body '}'");
			} else if(ctx.self_methods() != null && ctx.access_qualifier() == null) {
				System.err.print("role_decl : ");
				if (isRoleArray) System.err.print("[] ");
				System.err.println("'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			} else if(ctx.self_methods() == null && ctx.access_qualifier() != null) {
				System.err.println("role_decl : access_qualifier ");
				if (isRoleArray) System.err.print("[] ");
				System.err.println("'role' JAVA_ID '{' role_body '}'");
			} else {
				System.err.print("role_decl : access_qualifier ");
				if (isRoleArray) System.err.print("[] ");
				System.err.println("'role' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			}
		}
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterRole_body(KantParser.Role_bodyContext ctx)
	{
		// role_body
	    //	: method_decl
	    //	| role_body method_decl
	    //	| object_decl				// illegal
	    //	| role_body object_decl		// illegal - for better error messages only
		//  | /* null */
		
		/* nothing */
	}
	
	@Override public void exitRole_body(KantParser.Role_bodyContext ctx)
	{
		// : method_decl
        // | role_body method_decl
        // | object_decl				// illegal
        // | role_body object_decl		// illegal - for better error messages only
		// | /* null */
		
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

	@Override public void enterSelf_methods(KantParser.Self_methodsContext ctx)
	{
		// : self_methods ';' method_signature
		// | method_signature
		// | self_methods /* null */ ';'
		
		parsingData_.pushFormalParameterList(new FormalParameterList());
	}
	
	@Override public void exitSelf_methods(KantParser.Self_methodsContext ctx)
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
	
	@Override public void enterStageprop_decl(KantParser.Stageprop_declContext ctx)
	{
		// stageprop_decl
		// : 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isStagePropArray = vecText.length() > 0;	// "[]"
		
		if (null != ctx.access_qualifier()) {
			final String accessQualifier = ctx.access_qualifier().getText();
			if (0 != accessQualifier.length()) {
				errorHook5p1(ErrorType.Warning, ctx.getStart().getLine(), "WARNING: Gratuitous access qualifier `",
						ctx.access_qualifier().getText(), "' ignored", ".");
			}
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role - resulting grammar error got here with that
			// null condition. Not much to do but to punt
			
			final String stagePropName = JAVA_ID.getText();
			lookupOrCreateStagePropDeclaration(stagePropName, ctx.getStart().getLine(), isStagePropArray);
			
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorType.Fatal, ctx.getStart().getLine(), "Stageprop ", stagePropName, " can be declared only in a Context scope - not ", currentScope_.name());
			}
			currentScope_ = currentRole_.enclosedScope();
		} else {
			currentRole_ = null;
		}
	}

	@Override public void exitStageprop_decl(KantParser.Stageprop_declContext ctx)
	{
		// stageprop_decl
		// : 'stageprop' role_vec_modifier JAVA_ID '{' role_body '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' role_body '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isStagePropArray = vecText.length() > 0;	// "[]"
		
		if (null != currentRole_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See comment
			// elsewhere (in exitRole_decl?)
			final Type rawType = currentRole_.type();
			assert rawType instanceof StagePropType;
			final StagePropType type = (StagePropType)rawType;
			type.setBacklinkToRoleDecl(currentRole_);
			
			// Make sure self_methods are const
			if (currentRole_.requiresConstMethods()) {
				final Map<String, MethodSignature> requiredSelfSignatures = currentRole_.requiredSelfSignatures();
				for (Map.Entry<String, MethodSignature> iter : requiredSelfSignatures.entrySet()) {
					final String methodName = iter.getKey();
					final MethodSignature signature = iter.getValue();
					if (signature.hasConstModifier() == false) {
						errorHook6p2(ErrorType.Warning, ctx.getStart().getLine(),
								"WARNING: Signatures for functions required by stageprops like ", currentRole_.name(),
								" should have a const modifier: method ", methodName, " does not.", "");
					}
				}
			}

			currentRole_ = null;
			currentScope_ = currentScope_.parentScope();
		}

		if (printProductionsDebug) {
			if (ctx.self_methods() == null && ctx.access_qualifier() == null) {
				System.err.print("stageprop_decl : ");
				if (isStagePropArray) System.err.print("[] ");
				System.err.println("'stageprop' JAVA_ID '{' role_body '}'");
			} else if(ctx.self_methods() != null && ctx.access_qualifier() == null) {
				System.err.print("stageprop_decl : ");
				if (isStagePropArray) System.err.print("[] ");
				System.err.println("'stageprop' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			} else if(ctx.self_methods() == null && ctx.access_qualifier() != null) {
				System.err.println("stageprop_decl : access_qualifier ");
				if (isStagePropArray) System.err.print("[] ");
				System.err.println("'stageprop' JAVA_ID '{' role_body '}'");
			} else {
				System.err.print("stageprop_decl : access_qualifier ");
				if (isStagePropArray) System.err.print("[] ");
				System.err.println("'stageprop' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterStageprop_body(KantParser.Stageprop_bodyContext ctx) {
		// stageprop_body
	    //	: method_decl
	    //	| stageprop_body method_decl
	    
		/* nothing */
	}
	
	@Override public void exitStageprop_body(KantParser.Stageprop_bodyContext ctx) {
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

	@Override public void enterClass_body(KantParser.Class_bodyContext ctx) {
		// class_body
	    //	: class_body class_body_element
	    //	| class_body_element
	    //	| /* null */
	    
		/* nothing */
	}

	@Override public void enterClass_body_element(KantParser.Class_body_elementContext ctx) {
		// class_body_element
	    //	: method_decl
	    //	| object_decl
	    
		/* nothing */
	}
	
	@Override public void enterInterface_body(KantParser.Interface_bodyContext ctx) {
		parsingData_.pushFormalParameterList(new FormalParameterList());
	}
	
	@Override public void exitInterface_body(KantParser.Interface_bodyContext ctx) {
		// : interface_body ';' method_signature
		// | method_signature
		// | interface_body /* null */ ';'
		
		final Method_signatureContext contextForSignature = ctx.method_signature();
		if (null != contextForSignature) {
			final MethodSignature signature = parsingData_.popMethodSignature();
			final FormalParameterList plInProgress = parsingData_.popFormalParameterList();
		
			// Add a declaration of "this." These are class instance methods, never
			// role methods, so there is no need to add a current$context argument
			final ObjectDeclaration self = new ObjectDeclaration("this", currentInterface_.type(), ctx.getStart().getLine());
			plInProgress.addFormalParameter(self);
		
			signature.addParameterList(plInProgress);
			currentInterface_.addSignature(signature);
			
			// Add it to type, too
			final InterfaceType interfaceType = (InterfaceType)currentInterface_.type();
			this.addSignatureSuitableToPass(interfaceType, signature);
		}
		
		if (printProductionsDebug) {
			if (null != ctx.interface_body()) {
				System.err.println("interface_body : interface_body ';' method_signature");
			} else if (null != ctx.method_signature()) {
				System.err.println("interface_body : method_signature");
			} else {
				System.err.println("interface_body : /* null */ ';'");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	protected void addSignatureSuitableToPass(final InterfaceType interfaceType, final MethodSignature signature) {
		// nothing in pass 1
	}

	@Override public void enterMethod_decl(KantParser.Method_declContext ctx)
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
		//
		// There is no "static" modifier in the grammar, so all user-declared functions
		// will be non-static for now
		final MethodDeclaration currentMethod = new MethodDeclaration(
				methodSelector, newScope, returnType,
				accessQualifier, lineNumber, false);
		currentMethod.addParameterList(pl);
		
		newScope.setDeclaration(currentMethod);
		
		currentScope_.declareMethod(currentMethod);
		currentScope_ = newScope;
		
		parsingData_.pushFormalParameterList(pl);
	}
	
	@Override public void exitMethod_decl_hook(KantParser.Method_decl_hookContext ctx)
	{
		// method_decl_hook
		//	: method_signature

		final Declaration associatedDeclaration = currentScope_.parentScope().associatedDeclaration();
		final Type classOrRoleOrContextType = associatedDeclaration.type();
		
		boolean isRoleMethodInvocation = classOrRoleOrContextType instanceof RoleType;
		
		assert  classOrRoleOrContextType instanceof ClassType || 
				isRoleMethodInvocation ||
				classOrRoleOrContextType instanceof ContextType ||
				classOrRoleOrContextType instanceof TemplateType;
		
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
			// Functionality duplicated on Passes 3 & 4
			declareFormalParametersSuitableToPass(currentScope_, objectDeclaration);
		}
		
		// Can't we associate the signature with the parameter list here? Experiment.
		parsingData_.currentMethodSignature().addParameterList(parsingData_.currentFormalParameterList());
		
		if (printProductionsDebug) {
			System.err.println("method_decl_hook : method_signature");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitMethod_decl(KantParser.Method_declContext ctx)
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

	@Override public void enterMethod_signature(KantParser.Method_signatureContext ctx)
	{
		// : access_qualifier return_type method_name '(' param_list ')' CONST*
		// | access_qualifier return_type method_name CONST*
		// | access_qualifier method_name '(' param_list ')' CONST*
		final String name = ctx.method_name().getText();
		final String accessQualifierString = null != ctx.access_qualifier()? ctx.access_qualifier().getText(): "";
		final AccessQualifier accessQualifier = AccessQualifier.accessQualifierFromString(accessQualifierString);
		
		Type returnType = null;
		// There may not be any return type at all - as for a constructor
		final KantParser.Return_typeContext returnTypeContext = ctx.return_type();
		if (null != returnTypeContext) {
			final String returnTypeName = returnTypeContext.getText();
			returnType = currentScope_.lookupTypeDeclarationRecursive(returnTypeName);
		
			// null is O.K. as a return type!
		}
		
		final int lineNumber = ctx.getStart().getLine();
		final MethodSignature currentMethod = new MethodSignature(name, returnType, accessQualifier, lineNumber, false);

		if (null != ctx.CONST()) {
			currentMethod.setHasConstModifier(ctx.CONST().size() > 0);
		}
		
		parsingData_.pushMethodSignature(currentMethod);
	}
	
	@Override public void exitMethod_signature(KantParser.Method_signatureContext ctx)
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

	@Override public void enterExpr_and_decl_list(KantParser.Expr_and_decl_listContext ctx)
	{
	}
	
	private void addInitializationsForObjectDecls(final DeclarationList object_decls) {
		final ExprAndDeclList currentExprAndDecl = parsingData_.currentExprAndDecl();
		for (final BodyPart bp : object_decls.declarations()) {
			if (bp instanceof ObjectDeclaration) {
				final ObjectDeclaration odecl = (ObjectDeclaration)bp;
				final Expression initializationExpression = odecl.initializationExpression();
				if (null != initializationExpression) {
					assert initializationExpression instanceof AssignmentExpression;
					currentExprAndDecl.addBodyPart(initializationExpression);
				}
			}
		}
	}
	
	@Override public void exitExpr_and_decl_list(KantParser.Expr_and_decl_listContext ctx)
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
		final ExprAndDeclList currentExprAndDecl = parsingData_.currentExprAndDecl();
		if (null != ctx.expr()) {
			expr = parsingData_.popExpression();
		}
		if (null != ctx.object_decl()) {
			object_decl = parsingData_.popDeclarationList();
		}
		if (null != expr && null != object_decl) {
			currentExprAndDecl.addBodyPart(expr);
			addInitializationsForObjectDecls(object_decl);
			
			// Does this really add anything?
			currentExprAndDecl.addBodyPart(object_decl);
		} else if (null != expr_and_decl_list && null != object_decl) {
			addInitializationsForObjectDecls(object_decl);
			currentExprAndDecl.addBodyPart(object_decl);
		} else if (null != object_decl) {
			currentExprAndDecl.addBodyPart(object_decl);
			addInitializationsForObjectDecls(object_decl);
		} else if (null != expr_and_decl_list && null != expr) {
			currentExprAndDecl.addBodyPart(expr);
		} else if (null != ctx.expr_and_decl_list() && null == ctx.expr() && null == ctx.object_decl()) {
			// just a gratuitous null statement that we can ignore
		} else {
			// null list - it's O.K.
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

	@Override public void exitReturn_type(KantParser.Return_typeContext ctx)
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
	
	@Override public void exitMethod_name(KantParser.Method_nameContext ctx)
	{
		// method_name
		//	: JAVA_ID

		/* nothing */
	}
	
	@Override public void exitAccess_qualifier(KantParser.Access_qualifierContext ctx)
	{
		//	access_qualifier
	    //		: 'public'
		//		| 'private'
		//		| /* null */

		/* nothing */
		if (printProductionsDebug) {
			System.err.println("access_qualifier : public|private|/*null*/");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterObject_decl(KantParser.Object_declContext ctx)
	{
		// object_decl
	    //	: access_qualifier compound_type_name identifier_list ';'
	    //	| access_qualifier compound_type_name identifier_list
	    //	| compound_type_name identifier_list /* null expr */ ';'
	    //	| compound_type_name identifier_list
	}
	
	@Override public void exitObject_decl(KantParser.Object_declContext ctx)
	{
		// object_decl
	    //	: access_qualifier compound_type_name identifier_list ';'
	    //	| access_qualifier compound_type_name identifier_list
	    //	| compound_type_name identifier_list /* null expr */ ';'
	    //	| compound_type_name identifier_list
		
		// One semantic routine serves all three passes

		final int lineNumber = ctx.getStart().getLine();
		final KantParser.Access_qualifierContext accessQualifierContext = ctx.access_qualifier();
		final String accessQualifierString = accessQualifierContext != null? accessQualifierContext.getText(): "";
		AccessQualifier accessQualifier = AccessQualifier.accessQualifierFromString(accessQualifierString);
		if (null == accessQualifier) {
			accessQualifier = AccessQualifier.accessQualifierFromString(" default");
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
		if (3 == numberOfChildren) {
			final String firstModifier = children.get(1).getText();
			final String secondModifier = children.get(2).getText();
			if (firstModifier.equals("[") && secondModifier.equals("]")) {
				// Is an array declaration
				isArray = true;
			}
		}
		
		final Declaration associatedDeclaration = currentScope_.associatedDeclaration();
		if (associatedDeclaration instanceof StagePropDeclaration) {
			errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(), "Stage props are stateless, so the decaration of objects of type ", typeName, " in ",
					associatedDeclaration.name(), " are not allowed.", "");
			declaredObjectDeclarations = new ArrayList<ObjectDeclaration>();	// empty list just to keep things happy
		} else if (associatedDeclaration instanceof RoleDeclaration) {
			errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(), "Roles are stateless, so the decaration of objects of type ", typeName, " in ",
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
				final String aName = type.getText() + "_$array";
				type = new ArrayType(aName, type);
			}
						
			final Identifier_listContext identifier_list = ctx.identifier_list();
			final DeclarationsAndInitializers idInfo = this.processIdentifierList(identifier_list, type, lineNumber, accessQualifier);
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
				// syntax, and handle the intialisations in some other way (e.g., letting
				// the constructor handle them.
				
				// In any case, errors can cause currentExprAndDecl() to be empty, so we
				// need to bail out accordingly
				
				if (parsingData_.currentExprAndDeclExists()) {
					// final ExprAndDeclList currentExprAndDecl = parsingData_.currentExprAndDecl();
					for (int z = 0; z < intializationExprs.size(); z++) {
						if (null != currentForExpression) {
							// For loops are special
							final List<BodyPart> bodyParts = new ArrayList<BodyPart>();
							bodyParts.add(intializationExprs.get(z));
							currentForExpression.addInitExprs(bodyParts);
						} else if (null != currentBlockExpression) {
							// Blocks are... kind of special...
							// Well, no. We'll handle them through the declarations as well.
							// currentBlockExpression.bodyParts().add(intializationExprs.get(z));
						} else {
							// We can't add to currentExprAndDecl here, as we did before,
							// because the timing is wrong. Defer its processing to
							// exitExpr_and_decl_list where we'll slot it in with the
							// corresponding declaration. We effect this by tying the
							// initialization to the individual ObjectDeclaration above
							// in processIdentifierList.
							
							// No: currentExprAndDecl.addBodyPart(intializationExprs.get(z));
						}
					}
				} else {
					return;	// punt - error return
				}
			}
			
			for (final ObjectDeclaration aDecl : declaredObjectDeclarations) {
				this.nameCheck(aDecl.name(), ctx.getStart().getLine());
			}
		}

		// Package all the stuff in declaredObjectDeclarations into an ExprAndDeclList
		// and push it onto the ExprAndDecl stack.
		final DeclarationList declarationList = new DeclarationList(lineNumber);
		
		for (final ObjectDeclaration aDecl : declaredObjectDeclarations) {
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
	
	@Override public void exitCompound_type_name(KantParser.Compound_type_nameContext ctx)
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
	
	protected Type commonTemplateInstantiationHandling(final String templateName, final int lineNumber, final List<String> typeNameList) {
		/*
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(templateName);
		stringBuffer.append("<");
		for (int i = 0; i < typeNameList.size(); i++) {
			final String parameterName = typeNameList.get(i);
			stringBuffer.append(parameterName);
			if (i < typeNameList.size() - 1) {
				stringBuffer.append(",");
			}
		}
		stringBuffer.append(">");
		final String typeName = stringBuffer.toString();
		*/
		
		// Create a new class!
		final Type type = this.lookupOrCreateTemplateInstantiation(templateName, typeNameList, lineNumber);
		return type;
	}
	
	@Override public void exitType_name(KantParser.Type_nameContext ctx)
	{
		// type_name
	    //	: JAVA_ID
		//  | JAVA_ID type_list
	    //	| 'int'
		//  | 'Integer'
	    //	| 'double'
	    //	| 'char'
	    //	| 'String'
		
		// All three passes
	
		Type type = null;
		String typeName = null;
		if (null != ctx.JAVA_ID() && null == ctx.type_list()) {
			typeName = ctx.JAVA_ID().getText();
	
			type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
		} else if (null == ctx.JAVA_ID() && null == ctx.type_list()) {
			typeName = ctx.getText();
			
			type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
		} else if (null != ctx.JAVA_ID() && null != ctx.type_list()) {
			// | JAVA_ID type_list
			// Must be in the context of a template instantiation in progress
			
			// Create a new class!
			final List<String> typeNameList = parsingData_.popTypeNameList();
			type = this.commonTemplateInstantiationHandling(ctx.JAVA_ID().getText(), ctx.getStart().getLine(), typeNameList);
		} else {
			assert false;
		}
		
		if (null != type) {
			// error stumbling null check
			parsingData_.pushExpression(type);
		} else {
			parsingData_.pushExpression(new NullExpression());
		}
		
		if (printProductionsDebug) {
			if (null != ctx.JAVA_ID()) {
				System.err.print("type_name : JAVA_ID (");
				System.err.print(ctx.getText());
				System.err.println(")");
			} else if (null != ctx.JAVA_ID() && null != ctx.type_list()) {
				System.err.print("type_name : JAVA_ID (");
				System.err.print(ctx.JAVA_ID().getText());
				System.err.println(") '<' JAVA_ID (");
				System.err.print(ctx.type_list().getText());
				System.err.println(") '>'");
			} else {
				System.err.print("type_name : (");
				System.err.print(ctx.getText());
				System.err.println(")");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitIdentifier_list(KantParser.Identifier_listContext ctx)
	{
		// identifier_list
	    //	: JAVA_ID
	    //	| identifier_list ',' JAVA_ID
	    //	| JAVA_ID ASSIGN expr
	    //	| identifier_list ',' JAVA_ID ASSIGN expr
	    
		/* nothing */
		if (printProductionsDebug) {
			if (null != ctx.JAVA_ID() && null == ctx.expr()) {
				System.err.print("identifier_list : JAVA_ID [");
				System.err.print(ctx.JAVA_ID().getText());
				System.err.println("]");
			} else if (null != ctx.JAVA_ID() && null != ctx.identifier_list()) {
				System.err.print("identifier_list : identifier_list ',' JAVA_ID [");
				System.err.print(ctx.JAVA_ID().getText());
				System.err.println("]");
			} else if (null != ctx.JAVA_ID() && null != ctx.expr()) {
				System.err.println("identifier_list : JAVA_ID ASSIGN expr");
			} else if (null != ctx.identifier_list() && null != ctx.JAVA_ID() && null != ctx.expr()) {
				System.err.println("identifier_list : identifier_list ',' JAVA_ID ASSIGN expr");
			} else {
				assert false;
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
    
	@Override public void exitParam_list(KantParser.Param_listContext ctx)
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

	@Override public void exitParam_decl(KantParser.Param_declContext ctx)
	{
		// param_decl
        //		: compound_type_name JAVA_ID
		
		// We need just to pop the type that the type_name production
		// put on the stack
		
		if (parsingData_.currentExpressionExists()) {
			// This above check shouldn't be needed
			@SuppressWarnings("unused")
			final ExpressionStackAPI unusedType = parsingData_.popRawExpression();
		}
		
		if (printProductionsDebug) {
			System.err.println("param_decl : compound_type_name JAVA_ID");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitExpr(KantParser.ExprContext ctx)
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
			if (parsingData_.currentExpressionExists()) {
				// Error stumbling (undefined method)
				expression = parsingData_.popExpression();
			} else {
				expression = new NullExpression();
			}
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
			// It may be null.
			expression = this.expressionFromReturnStatement(ctx.expr(), ctx.getParent(), ctx.getStart());
			if (null != ctx.expr()) {
				expression.setResultIsConsumed(true);	// consumed by the return statement
			}
			
			if (printProductionsDebug) {
				if (null == ctx.expr()) {
					System.err.println("expr : RETURN");
				} else {
					System.err.println("expr : RETURN expr");
				}
			}
		} else {
			// Could be a parsing error
			expression = new NullExpression();
			if (printProductionsDebug) { System.err.println("expr : <internal error>"); }
		}
		
		parsingData_.pushExpression(expression);
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitAbelian_expr(KantParser.Abelian_exprContext ctx) {	
		// : abelian_product (ABELIAN_SUMOP abelian_product)*
		// | abelian_expr op=('!=' | '==' | GT | LT | '>=' | '<=') abelian_expr
		// | <assoc=right> abelian_expr ASSIGN expr
		
		Expression expression = null;
		
		if (null != ctx.abelian_product() && ctx.abelian_product().size() > 1 && null != ctx.ABELIAN_SUMOP()) {
			//	| abelian_expr op=('+' | '-') abelian_expr
			
			// Make it left-associative
			final int abelianSumopSize = ctx.ABELIAN_SUMOP().size();
			final Stack<Expression> exprStack = new Stack<Expression>();
			for (int i = 0; i < abelianSumopSize; i++) {
				final Expression expr2 = parsingData_.popExpression();
				exprStack.push(expr2);
			}
			
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
				for (int i = 0; i < abelianSumopSize; i++) {
					final Expression expr2 = exprStack.pop();
					final String operatorAsString = ctx.ABELIAN_SUMOP(i).getText();
					expression = new SumExpression(expression, operatorAsString, expr2);
				}
			} else {
				expression = new NullExpression();
			}
			
			if (printProductionsDebug) {
				System.err.print("abelian_expr : abelian_expr ");
				for (int i = 0; i < ctx.ABELIAN_SUMOP().size(); i++) {
					System.err.print("`");
					System.err.print(ctx.ABELIAN_SUMOP(i).getText());
					System.err.print("' abelian_expr ");
				}
				System.err.println();
			}
		} else if (null != ctx.abelian_product() && ctx.abelian_product().size() == 1) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) {
				System.err.println("abelian_expr : abelian_product ");
			}
		} else if (null != ctx.abelian_expr() && ctx.abelian_expr().size() > 1 &&
				null != ctx.op && ctx.op.getText().length() > 0) {
			//	| abelian_expr op=('<=' | '>=' | '<' | '>' | '==' | '!=') abelian_expr
			final Token relationalOperator = ctx.op;
	
			final Expression rhs = parsingData_.popExpression();
			final Expression lhs = parsingData_.popExpression();
			lhs.setResultIsConsumed(true);	// is this right? FIXME
			rhs.setResultIsConsumed(true);
			if (lhs.type().canBeConvertedFrom(rhs.type()) == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"Expression '", rhs.getText(), "' is not of the right type","");
			}
			
			assert null != relationalOperator;
			final String operationAsString = relationalOperator.getText();
			expression = new RelopExpression(lhs, operationAsString, rhs);
	
			if (printProductionsDebug) {
				System.err.print("relop_expr : abelian_expr ");
				System.err.print(operationAsString);
				System.err.println(" abelian_expr");
			}
		} else if ((null != ctx.expr()) && (null != ctx.abelian_expr()) &&
				(ctx.abelian_expr().size() == 1) && null != ctx.ASSIGN()) {
			// : lhs '=' rhs
			Expression rhs = null, lhs = null;
			if (parsingData_.currentExpressionExists()) {
				rhs = parsingData_.popExpression();
			} else {
				rhs = new NullExpression();
			}
			if (parsingData_.currentExpressionExists()) {
				lhs = parsingData_.popExpression();
			} else {
				lhs = new NullExpression();
			}
			// rhs.setResultIsConsumed(true);	// done by assignmentExpr call
			expression = this.assignmentExpr(lhs, ctx.ASSIGN().getText(), rhs, ctx);
			if (printProductionsDebug) { System.err.println("abelian_expr : abelian_expr ASSIGN expr"); }
		} else {
			assert false;
		}
		
		if (null != expression) {
			// null check is error stumbling check
			parsingData_.pushExpression(expression);
		}
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitAbelian_product(KantParser.Abelian_productContext ctx) {
		// : abelian_unary_op (ABELIAN_MULOP abelian_unary_op)*
		// | <assoc=right> abelian_product POW abelian_atom
		
		Expression expression = null;
		
		if (null != ctx.abelian_unary_op() && ctx.abelian_unary_op().size() > 1 && null != ctx.ABELIAN_MULOP()) {
			//	| abelian_expr op=('*' | '/' | '%') abelian_expr
			
			final int abelianMulopSize = ctx.ABELIAN_MULOP().size();
			final Stack<Expression> exprStack = new Stack<Expression>();
			for (int i = 0; i < abelianMulopSize; i++) {
				if (parsingData_.currentExpressionExists()) {
					final Expression expr2 = parsingData_.popExpression();
					exprStack.push(expr2);
				} else {
					break;
				}
			}
			
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
				for (int i = 0; i < abelianMulopSize; i++) {
					final Expression expr2 = exprStack.pop();
					final String operatorAsString = ctx.ABELIAN_MULOP(i).getText();
					expression = new ProductExpression(expression, operatorAsString, expr2, ctx.getStart(), this);
				}
			} else {
				expression = new NullExpression();
			}
			
			
			if (printProductionsDebug) {
				System.err.print("abelian_product : abelian_unary_op ");
				for (int i = 0; i < ctx.ABELIAN_MULOP().size(); i++) {
					System.err.print("`");
					System.err.print(ctx.ABELIAN_MULOP(i).getText());
					System.err.print("' abelian_unary_op ");
				}
				System.err.println();
			}
		} else if (null != ctx.abelian_unary_op() && ctx.abelian_unary_op().size() == 1) {
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.println("abelian_product : abelian_unary_op ");
			}
		} else if (null != ctx.abelian_product()
				&& null != ctx.abelian_atom() && null != ctx.POW()) {
			// : <assoc=right>abelian_expr POW abelian_atom
			final Expression expr2 = parsingData_.popExpression();
			final Expression expr1 = parsingData_.popExpression();
						
			final String operatorAsString = ctx.POW().getText();
		
			expression = new PowerExpression(expr1, operatorAsString, expr2, ctx.getStart(), this);
		
			if (printProductionsDebug) {
				System.err.println("abelian_expr : abelian_expr POW expr");
			}
		} else {
			assert false;
		}
		
		if (null != expression) {
			// null check is error stumbling check
			parsingData_.pushExpression(expression);
		}
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitAbelian_unary_op(KantParser.Abelian_unary_opContext ctx) {
		// :  ABELIAN_SUMOP abelian_atom
		// |  LOGICAL_NEGATION abelian_atom
		// |  abelian_atom
		Expression expression = null;
		
		if (null != ctx.ABELIAN_SUMOP()) {
			// ABELIAN_SUMOP abelian_atom
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
				expression = new UnaryAbelianopExpression(expression, ctx.ABELIAN_SUMOP().getText());
			} else {
				expression = new NullExpression();
			}
			
			if (printProductionsDebug) {
				System.err.println("abelian_unary_op : '-' abelian_atom");
			}
		} else if (null != ctx.LOGICAL_NEGATION()) {
			//	| LOGICAL_NEGATION  abelian_atom
			expression = parsingData_.popExpression();
			final Type type = expression.type();
			if (StaticScope.globalScope().lookupTypeDeclaration("boolean").canBeConvertedFrom(type)) {
				;	// is O.K.
			} else {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"Expression `", expression.getText(), "' is not of type boolean.", "");
			}
			expression = new UnaryAbelianopExpression(expression, "!");
			
			if (printProductionsDebug) {
				System.err.println("abelian_unary_op : '!' abelian_atom");
			}
		} else {
			if (parsingData_.currentExpressionExists()) {
				// Only do it if it's there to protect against error stumbling.
				expression = parsingData_.popExpression();
			} else {
				expression = new NullExpression();
			}
			if (printProductionsDebug) {
				System.err.println("abelian_unary_op : abelian_atom");
			}
		}
		
		
		if (null != expression) {
			// null check is error stumbling check
			parsingData_.pushExpression(expression);
		}
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterAbelian_atom(KantParser.Abelian_atomContext ctx) {
		//  | NEW JAVA_ID type_list '(' argument_list ')'
		if (null != ctx.NEW() && null != ctx.type_list() && null != ctx.argument_list()) {
			parsingData_.pushArgumentList(new ActualArgumentList());
		}
	}
	
	@Override public void exitAbelian_atom(KantParser.Abelian_atomContext ctx)
	{
		//  abelian_expr         
		//  | NEW message
        //	| NEW type_name '[' expr ']'
		//  | NEW JAVA_ID type_list '(' argument_list ')'
		//  | abelian_atom '.' JAVA_ID
		//  | abelian_atom '.' message
		//	| null_expr
		//	| JAVA_ID
		//	| abelian_atom ABELIAN_INCREMENT_OP
		//	| ABELIAN_INCREMENT_OP abelian_atom
		//	| constant
		//	| '(' abelian_expr ')'
		//	| abelian_atom '[' expr ']'
		//	| abelian_atom '[' expr ']' ABELIAN_INCREMENT_OP
		//	| ABELIAN_INCREMENT_OP abelian_atom '[' expr ']'
		//	| ABELIAN_INCREMENT_OP abelian_atom '.' JAVA_ID
		//	| abelian_atom '.' JAVA_ID ABELIAN_INCREMENT_OP
		//	| /* this. */ message
		//	| abelian_atom '.' CLONE
		
		// All passes. EXPLICITLY INVOKED FROM PASS 4
		
		Expression expression = null;
		
		if (null != ctx.NEW() && null != ctx.type_list() && null != ctx.argument_list()) {
			//  | NEW JAVA_ID type_list '(' argument_list ')'
			Type type = null;
			final ActualArgumentList argument_list = parsingData_.popArgumentList();
			final List<String> typeParameterNameList = parsingData_.popTypeNameList();

			final String JAVA_ID = ctx.JAVA_ID().getText();
			final TemplateDeclaration templateDeclaration = currentScope_.lookupTemplateDeclarationRecursive(JAVA_ID);
			if (null == templateDeclaration) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"Cannot find template ", JAVA_ID, "", "");
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
				expression = new NullExpression();
			} else {
				final StringBuffer typeNameBuffer = new StringBuffer();
				typeNameBuffer.append(JAVA_ID); typeNameBuffer.append("<");
				for (int i = 0; i < typeParameterNameList.size(); i++) {
					typeNameBuffer.append(typeParameterNameList.get(i));
					if (i < typeParameterNameList.size() - 1) {
						typeNameBuffer.append(",");
					}
				}
				typeNameBuffer.append(">");
				final String compoundTypeName = typeNameBuffer.toString();
				type = currentScope_.lookupTypeDeclarationRecursive(compoundTypeName);
				if (null == type) {
					type = this.lookupOrCreateTemplateInstantiation(JAVA_ID, typeParameterNameList, ctx.getStart().getLine());
					if (null == type) {
						errorHook5p2(ErrorType.Internal, ctx.getStart().getLine(),
							"Cannot find template instantiation ", compoundTypeName, "", "");
						type = StaticScope.globalScope().lookupTypeDeclaration("void");
					}
				}
				
				final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				final Message message = new Message(compoundTypeName, argument_list, ctx.getStart().getLine(), enclosingMegaType);
				final NewExpression newExpr = new NewExpression(type, message, ctx.getStart().getLine(), enclosingMegaType);
				ctorCheck(type, message, ctx.getStart().getLine());
				addSelfAccordingToPass(type, message, currentScope_);
				expression = newExpr;
			}
			
			final MethodDeclaration constructor = type.enclosedScope().lookupMethodDeclaration(JAVA_ID, argument_list, false);
			if (null != constructor) {
				final boolean isAccessible = currentScope_.canAccessDeclarationWithAccessibility(constructor, constructor.accessQualifier(), ctx.getStart().getLine());
				if (isAccessible == false) {
					errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(),
							"Cannot access constructor `", constructor.signature().getText(),
							"' with `", constructor.accessQualifier().asString(), "' access qualifier.","");
				}
			}

			if (printProductionsDebug) {
				System.err.println("abelian_atom : NEW JAVA_ID type_list '(' argument_list ')'");
			}
		} else if (null != ctx.abelian_atom() && null != ctx.JAVA_ID()) {
			//	| abelian_atom '.' JAVA_ID
			final ExpressionStackAPI rawExpression = this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), null);
			assert rawExpression instanceof Expression;
			// The following line DOES pop the expression stack
			expression = (Expression)rawExpression;
			if (printProductionsDebug) { System.err.print("abelian_atom : abelian_atom '.' JAVA_ID ("); System.err.print(ctx.JAVA_ID().getText()); System.err.println(")");}
		} else if (null != ctx.abelian_atom() && null != ctx.message()) {
			//	| abelian_atom '.' message
			// This routine actually does pop the expressions stack (and the Message stack)
			expression = this.messageSend(ctx.getStart(), ctx.abelian_atom());
			
			if (null == expression) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
						"No match for call: ", ctx.abelian_atom().getText(), ".", ctx.message().getText());
				expression = new NullExpression();
			}
												
			if (printProductionsDebug) {
				System.err.println("abelian_product : abelian_atom '.' message");
			}
		} else if (null != ctx.NEW()) {
			// 'new' message
			// 'new' type_name '[' expr ']'
			
			final KantParser.ExprContext sizeExprCtx = (null == ctx.expr())? null:
														((ctx.expr().size() == 0)? null: ctx.expr(0));
			expression = this.newExpr(ctx.children, ctx.getStart(), sizeExprCtx, ctx.message());
			
			if (printProductionsDebug) {
				System.err.print("expr : ");
				if (expression instanceof NewExpression) {
					System.err.print(((NewExpression)expression).getText());
				} else {
					System.err.print("<unknown class>");
				}
				System.err.println("");
			}
		} else if (null != ctx.null_expr()) {
			//	| null_expr
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.print("abelian_atom : null_expr");
			}
		} else if (null != ctx.JAVA_ID() && null == ctx.ABELIAN_INCREMENT_OP() &&
				(null == ctx.abelian_expr())) {
			//	| JAVA_ID
			
			expression = idExpr(ctx.JAVA_ID(), ctx.getStart());
			if (printProductionsDebug) {
				System.err.print("abelian_atom : JAVA_ID (");
				System.err.print(ctx.JAVA_ID().getText());
				System.err.println(")");
			}
		} else if (null != ctx.abelian_atom() && null != ctx.ABELIAN_INCREMENT_OP() &&
				(null == ctx.abelian_expr())) {	
			//	| abelian_atom ABELIAN_INCREMENT_OP
			//	| ABELIAN_INCREMENT_OP abelian_atom
			final Interval AbelianAtomInterval = ctx.abelian_atom().getSourceInterval();
			final Interval OperatorInterval = ctx.ABELIAN_INCREMENT_OP().getSourceInterval();
			final PreOrPost preOrPost = AbelianAtomInterval.startsAfter(OperatorInterval)?
					UnaryopExpressionWithSideEffect.PreOrPost.Pre: UnaryopExpressionWithSideEffect.PreOrPost.Post;
			
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
				assert null != expression;
				expression = new UnaryopExpressionWithSideEffect(expression, ctx.ABELIAN_INCREMENT_OP().getText(), preOrPost);
				assert null != expression;
			} else {
				expression = new NullExpression();
			}
			
			if (printProductionsDebug) {
				switch (preOrPost) {
				case Post:
					System.err.print("abelian_atom : abelian_atom (");
					System.err.print(ctx.abelian_atom().getText());
					System.err.println(") ABELIAN_INCREMENT_OP");
					break;
				case Pre:
					System.err.print("abelian_atom : ABELIAN_INCREMENT_OP abelian_atom (");
					System.err.print(ctx.abelian_atom().getText());
					System.err.println(")");
					break;
				}
			}
			
			checkForIncrementOpViolatingIdentifierConstness((UnaryopExpressionWithSideEffect)expression, ctx.getStart());
		} else if (null != ctx.ABELIAN_INCREMENT_OP() && null != ctx.abelian_atom() && null != ctx.JAVA_ID()) {
			//	| ABELIAN_INCREMENT_OP abelian_atom '.' JAVA_ID
			final ExpressionStackAPI rawExpression = this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), ctx.ABELIAN_INCREMENT_OP());
			assert rawExpression instanceof Expression;
			expression = (Expression)rawExpression;
			if (printProductionsDebug) { System.err.print("abelian_atom : '++' expr '.' JAVA_ID ("); System.err.print(ctx.JAVA_ID().getText()); System.err.println(")");}
		} else if (null != ctx.constant()) {
			//	| constant
			
			// expression = Expression.makeConstantExpressionFrom(ctx.constant().getText());
			// is on the stack. We now have a "constant : ..." production,
			// so we don't make it here
			expression = parsingData_.popExpression();
			if (printProductionsDebug) {
				System.err.println("abelian_atom : constant");
			}
		} else if (null != ctx.abelian_expr() && null == ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && (null == ctx.expr() || ctx.expr().size() == 0) && null == ctx.ABELIAN_INCREMENT_OP()) {
			//	| '(' abelian_expr ')'
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.println("abelian_atom : '(' abelian_expr ')'");
			}
		} else if (null != ctx.abelian_atom() && null == ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && null != ctx.expr() && (ctx.expr().size() == 1) && null == ctx.ABELIAN_INCREMENT_OP()) {
			//	| abelian_atom '[' expr ']'
			final Expression indexExpr = parsingData_.popExpression();
			indexExpr.setResultIsConsumed(true);
			final Expression rawArrayBase = parsingData_.popExpression();
			
			// The fidelity of this varies according to how much
			// type information we have at hand
			final int lineNumber = ctx.getStart().getLine();
			expression = processIndexExpression(rawArrayBase, indexExpr, lineNumber);
			
			if (printProductionsDebug) {
				System.err.println("abelian_atom : abelian_expr '[' expr ']'");
			}
		} else if (null != ctx.abelian_atom() && null == ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && null != ctx.expr() && (ctx.expr().size() > 0) && null != ctx.ABELIAN_INCREMENT_OP()) {
			//	| abelian_atom '[' expr ']' ABELIAN_INCREMENT_OP
			//	| ABELIAN_INCREMENT_OP abelian_atom '[' expr ']'
			
			ParseTree arrayBase;
			KantParser.ExprContext theIndex;
			if (ctx.expr().size() == 2) {
				arrayBase = ctx.expr(0);
				theIndex = ctx.expr(1);
			} else {
				arrayBase = ctx.abelian_atom();
				theIndex = ctx.expr(0);
			}
			final ExpressionStackAPI rawExpression = processIndexedArrayElement(arrayBase, theIndex, ctx.ABELIAN_INCREMENT_OP());
			assert rawExpression instanceof Expression;
			expression = (Expression)rawExpression;
			checkForIncrementOpViolatingConstness((ArrayIndexExpressionUnaryOp)expression, ctx.getStart());
			if (printProductionsDebug) {
				if (null != ctx.abelian_expr()) {
					System.err.println("abelian_atom : abelian_expr '[' expr ']' ABELIAN_INCREMENT_OP");
				} else {
					System.err.println("abelian_atom : ABELIAN_INCREMENT_OP expr '[' expr ']'");
				}
			}
		} else if (null != ctx.abelian_atom() && null != ctx.JAVA_ID() && null == ctx.CLONE()
				&& null == ctx.message() && (null == ctx.expr() || (ctx.expr().size() == 0)) && null != ctx.ABELIAN_INCREMENT_OP()) {
			//	| abelian_atom '.' JAVA_ID ABELIAN_INCREMENT_OP
			final ExpressionStackAPI rawExpression = this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), ctx.ABELIAN_INCREMENT_OP());
			assert rawExpression instanceof Expression;
			expression = (Expression)rawExpression;
			if (printProductionsDebug) { System.err.print("abelian_atom : abelian_atom '.' JAVA_ID ("); System.err.print(ctx.JAVA_ID().getText()); System.err.println(") ++");}
		} else if ((null == ctx.abelian_atom()) && (null == ctx.abelian_expr()) && null != ctx.message()) {
			//	| /* this. */ message
			// This routine actually does pop the expressions stack (and the Message stack)
			expression = this.messageSend(ctx.getStart(), null);
									
			if (printProductionsDebug) { System.err.println("abelian_atom : /* this. */ message");}
		} else if (null != ctx.abelian_atom() && null != ctx.CLONE()) {
			//	| abelian_atom '.' CLONE
			
			final Expression qualifier = parsingData_.popExpression();
			expression = new DupMessageExpression(qualifier, qualifier.type());
			if (printProductionsDebug) { System.err.println("abelian_atom : abelian_expr '.' 'clone'"); }
		} else {
			assert false;
		}
		
		if (null != expression) {
			// null check is error stumbling check
			parsingData_.pushExpression(expression);
		}
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	protected Expression processIndexExpression(final Expression rawArrayBase, final Expression indexExpr, final int lineNumber) {
		// Pass 1 version. Overridden in Pass 2
		
		Expression expression = null;
		
		// On pass one, types may not yet be set up so we may
		// stumble here (particularly if there is a forward reference
		// to a type). So be generous.
		final Type arrayBaseType = rawArrayBase.type();
		if (arrayBaseType instanceof ArrayType) {
			final ArrayType arrayType = (ArrayType)arrayBaseType;	// instance of ArrayType
			final Type baseType = arrayType.baseType();	// like int
			final ArrayExpression arrayBase = new ArrayExpression(rawArrayBase, baseType);
			arrayBase.setResultIsConsumed(true);
			expression = new ArrayIndexExpression(arrayBase, indexExpr, lineNumber);
		} else {
			expression = new NullExpression();
		}
		return expression;
	}
	
	
	// I wanted to postpone this stuff until Pass 2, but omitting it
	// from Pass 1 causes the parse stack to get messed up here in
	// this pass. I still refrain from passing the argument info
	// in to the semantic analysis routines on Pass 1
	
	@Override public void enterMessage(KantParser.MessageContext ctx)
	{
		// JAVA_ID '(' argument_list ')'
		
		parsingData_.pushArgumentList(new ActualArgumentList());
	}
	
	@Override public void exitMessage(KantParser.MessageContext ctx)
	{
		// 	| JAVA_ID '(' argument_list ')'
		
		final String selectorName = ctx.JAVA_ID().getText();
		
		// Leave argument list processing to Pass 2...
		
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final Message newMessage = new Message(selectorName, null, ctx.getStart().getLine(), enclosingMegaType);
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
	
	@Override public void exitBoolean_expr(KantParser.Boolean_exprContext ctx)
	{
		// boolean_expr
		// : boolean_expr BOOLEAN_MULOP expr                       
		// | boolean_expr BOOLEAN_SUMOP expr
		// | constant
		// | abelian_expr
		
		Expression expression = null;
		String operation = null;
		final Type booleanType = StaticScope.globalScope().lookupTypeDeclaration("boolean");
		
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
		
		if ((expression.type().canBeConvertedFrom(booleanType)) || (expression.type().canBeConvertedFrom(booleanType))) {
			;		// ok.
		} else {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
					"Expression ", expression.getText(), " is not of type boolean.", "");
		}
		
		parsingData_.pushExpression(expression);
	}
	

	@Override public void enterBlock(KantParser.BlockContext ctx)
	{
		//	: '{' expr_and_decl_list '}'
        //	| '{' '}'
		// Set up the block
		final StaticScope oldScope = currentScope_;
		currentScope_ = new StaticScope(currentScope_, true);
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart().getLine());
		parsingData_.pushExprAndDecl(newList);

		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final BlockExpression blockExpression = new BlockExpression(ctx.getStart().getLine(), newList, currentScope_, enclosingMegaType);
		currentScope_.setDeclaration(oldScope.associatedDeclaration());	/// hmmm....
		
		parsingData_.pushBlockExpression(blockExpression);
	}

	@Override public void exitBlock(KantParser.BlockContext ctx)
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

	@Override public void enterExpr_or_null(KantParser.Expr_or_nullContext ctx)
	{ 
	}
	
	@Override public void exitExpr_or_null(KantParser.Expr_or_nullContext ctx)
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

	@Override public void exitIf_expr(KantParser.If_exprContext ctx)
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
		final Expression thenPart = parsingData_.currentExpressionExists()?
				parsingData_.popExpression(): new NullExpression();
		final Expression conditional = parsingData_.currentExpressionExists()?
				parsingData_.popExpression(): new NullExpression();
		final Type conditionalType = conditional.type();
		if (conditionalType.name().equals("boolean") == false) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Conditional expression `", conditional.getText(),
					"' is not of type boolean", "");
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
	
	@Override public void exitTrivial_object_decl(KantParser.Trivial_object_declContext ctx) {
		// trivial_object_decl : compound_type_name JAVA_ID
		final String idName = ctx.JAVA_ID().getText();
		
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
						"An array declaration is not appropriate for this context", "",
						"", "");
			}
		}
		
		this.nameCheck(idName, ctx.getStart().getLine());
		
		Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
		if (null == type) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Type `", typeName,
					"' seems not to be declared in any enclosing scope", "");
			type = StaticScope.globalScope().lookupTypeDeclaration("void");
		}
		
		final ObjectDeclaration objectDecl = new ObjectDeclaration(idName, type, ctx.getStart().getLine());
		currentScope_.declareObject(objectDecl);
	}
	
	@Override public void enterFor_expr(KantParser.For_exprContext ctx)
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
	
	@Override public void exitFor_expr(KantParser.For_exprContext ctx)
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
						"' is not of type boolean", "");
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
						"' is not of type boolean", "");
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
			final String JAVA_IDasString = ctx.JAVA_ID().getText();
			final ObjectDeclaration JAVA_ID_DECL = currentScope_.lookupObjectDeclarationRecursive(JAVA_IDasString);
			if (null == JAVA_ID_DECL) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Loop identifier `", JAVA_IDasString,
						"' is not declared", "");
			}
			expression = parsingData_.popForExpression();
			
			body.setResultIsConsumed(false);
			thingToIncrementOver.setResultIsConsumed(true);
			
			final Type typeIncrementingOver = thingToIncrementOver.type();
			if (typeIncrementingOver instanceof ArrayType == false &&
					typeIncrementingOver.name().startsWith("List<") == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Expression `", thingToIncrementOver.getText(),
						"' is not iterable", "");
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
						"' is not declared", " (strange error)");
			}
			
			body.setResultIsConsumed(false);
			thingToIncrementOver.setResultIsConsumed(true);
			
			final Type typeIncrementingOver = thingToIncrementOver.type();
			if (typeIncrementingOver instanceof ArrayType == false &&
					typeIncrementingOver.name().startsWith("List<") == false) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Expression `", thingToIncrementOver.getText(),
						"' is not iterable", "");
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
			} else {
				System.err.println("undefined production");
				assert false;
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void enterWhile_expr(KantParser.While_exprContext ctx)
	{
		// while_expr
		//	: 'while' '(' expr ')' expr
		//	;
		
		// This is just a placeholder so that enclosed declarations get
		// registered. We fill in the meat in exitWhile_expr
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final WhileExpression whileExpression = new WhileExpression(null, null,
				ctx.getStart().getLine(), parsingDataArgumentAccordingToPass(),
				nearestEnclosingMegaType);
		
		parsingData_.pushWhileExpression(whileExpression);
	}
	
	@Override public void exitWhile_expr(KantParser.While_exprContext ctx)
	{
		// while_expr
		//	: 'while' '(' expr ')' expr
	
		final Expression body = parsingData_.popExpression();
		final Expression conditional = parsingData_.currentExpressionExists()?
				parsingData_.popExpression(): new NullExpression();
		final WhileExpression expression = parsingData_.currentWhileExpressionExists()?
				parsingData_.popWhileExpression(): null;
		
		body.setResultIsConsumed(true);
		conditional.setResultIsConsumed(true);
		
		if (conditional.type() != StaticScope.globalScope().lookupTypeDeclaration("boolean")) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Condition in `while' statement is not of type boolean", conditional.getText(),
					" of type ", conditional.type().name());
		}
		
		if (expression != null) {
			expression.reInit(conditional, body);
			parsingData_.pushExpression(expression);
		} else {
			parsingData_.pushExpression(new NullExpression());
		}
		
		if (printProductionsDebug) {
			if (null != ctx.expr() && null != ctx.expr(1)) {
				System.err.println("while_expr : 'while' '(' expr ')' expr");
			} else {
				System.err.println("while_expr : 'while' '(' ??? ')' expr");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterDo_while_expr(KantParser.Do_while_exprContext ctx)
	{
		// do_while_expr
		//	: 'do' expr 'while' '(' expr ')'
		//	;

		// This is just a placeholder so that enclosed declarations get
		// registered. We fill in the meat in exitFor_expr
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final DoWhileExpression doWhileExpression = new DoWhileExpression(null, null,
				ctx.getStart().getLine(), parsingDataArgumentAccordingToPass(),
				nearestEnclosingMegaType);
		
		parsingData_.pushDoWhileExpression(doWhileExpression);
	}
	
	@Override public void exitDo_while_expr(KantParser.Do_while_exprContext ctx)
	{
		// do_while_expr
		//	: 'do' expr 'while' '(' expr ')'

		final Expression conditional = parsingData_.popExpression();
		final Expression body = parsingData_.popExpression();
		final DoWhileExpression expression = parsingData_.popDoWhileExpression();
		
		body.setResultIsConsumed(true);
		conditional.setResultIsConsumed(true);
		
		if (conditional.type() != StaticScope.globalScope().lookupTypeDeclaration("boolean")) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Condition in `do / while' statement is not of type boolean", conditional.getText(),
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

	@Override public void enterSwitch_expr(KantParser.Switch_exprContext ctx)
	{
		// : 'switch' '(' expr ')' '{'  ( switch_body )* '}'
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final SwitchExpression switchExpression = new SwitchExpression(parsingDataArgumentAccordingToPass(), enclosingMegaType);
		parsingData_.pushSwitchExpr(switchExpression);
	}
	
	@Override public void exitSwitch_expr(KantParser.Switch_exprContext ctx)
	{
		// : 'switch' '(' expr ')' '{'  ( switch_body )* '}'
		final SwitchExpression switchExpression = parsingData_.popSwitchExpr();
		
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
					errorHook6p2(ErrorType.Fatal, ctx.getStart().getLine(), "Case statement with expression of type `",
							aCase.type().name(), "' is incompatible with switch expression of type `",
							switchExpressionType.name(), "'.", "");
				}
			}
		}
		
		if (printProductionsDebug) {
			System.err.println("switch_expr : 'switch' '(' expr ')' '{'  ( switch_body )* '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterSwitch_body(KantParser.Switch_bodyContext ctx)
	{
		// : ( 'case' constant | 'default' ) ':' expr_and_decl_list
		
		currentScope_ = new StaticScope(currentScope_, true);
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart().getLine());
		parsingData_.pushExprAndDecl(newList);
	}
	
	@Override public void exitSwitch_body(KantParser.Switch_bodyContext ctx)
	{
		// : ( 'case' constant | 'default' ) ':' expr_and_decl_list
		
		Constant constant = null;
		boolean isDefault = false;
		if (null != ctx.constant()) {
			final Expression temp = parsingData_.popExpression();
			if (temp instanceof Constant == false) {
				ErrorLogger.error(ErrorType.Internal, ctx.getStart().getLine(), "Case statement has non-const expression: `",
					temp.getText(), "'", "");
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
		
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final SwitchBodyElement switchBodyElement = new SwitchBodyElement(constant, isDefault, expr_and_decl_list, enclosingMegaType);
		parsingData_.currentSwitchExpr().addSwitchBodyElement(switchBodyElement);
		currentScope_ = currentScope_.parentScope();
		
		if (printProductionsDebug) {
			System.err.println("switch_body : ( 'case' constant | 'default' ) ':' expr_and_decl_list");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}

	@Override public void exitNull_expr(KantParser.Null_exprContext ctx)
	{
		// null_expr : NULL
		
		final Expression expression = new NullExpression();
		
		parsingData_.pushExpression(expression);
		
		if (printProductionsDebug) {
			System.err.println("null_expr : NULL");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterConstant(KantParser.ConstantContext ctx) {
	}
	
	@Override public void exitConstant(KantParser.ConstantContext ctx) {
		// constant
	    //	: STRING
	    //	| INTEGER
	    //	| FLOAT
	    //	| BOOLEAN
		final Expression rawConstant = Expression.makeConstantExpressionFrom(ctx.getText());
		assert rawConstant instanceof Constant;
		final Constant constant = (Constant)rawConstant;
		parsingData_.pushExpression(constant);
		
		if (printProductionsDebug) { System.err.print("constant : "); System.err.println(ctx.getText());}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterArgument_list(KantParser.Argument_listContext ctx)
	{
	}

	@Override public void exitArgument_list(KantParser.Argument_listContext ctx)
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
			// no actual argument - OK
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
	
	
	
    protected ClassDeclaration lookupOrCreateNewClassDeclaration(final String name, final StaticScope newScope, final ClassDeclaration rawBaseClass, final int lineNumber) {
		if (null == rawBaseClass) {
			assert null != rawBaseClass;
		}
    	return new ClassDeclaration(name, newScope, rawBaseClass, lineNumber);
	}
    protected TemplateDeclaration lookupOrCreateNewTemplateDeclaration(final String name, final StaticScope newScope, final TypeDeclaration rawBaseClass, final int lineNumber) {
    	return new TemplateDeclaration(name, newScope, rawBaseClass, lineNumber);
	}
	protected void createNewClassTypeSuitableToPass(final ClassDeclaration newClass, final String name, final StaticScope newScope, final ClassType baseType) {
		// Pass1 only
		final ClassType newClassType = new ClassType(name, newScope, baseType);
		currentScope_.declareType(newClassType);
		newScope.setDeclaration(newClass);
		newClass.setType(newClassType);
	}
	protected void createNewTemplateTypeSuitableToPass(final TemplateDeclaration newTemplate, final String name, final StaticScope newScope, final ClassType baseType) {
		// Pass1 only
		final TemplateType newTemplateType = new TemplateType(name, newScope, baseType);
		currentScope_.declareType(newTemplateType);
		newScope.setDeclaration(newTemplate);
		newTemplate.setType(newTemplateType);
	}
	protected void createNewInterfaceTypeSuitableToPass(final InterfaceDeclaration newInterface, final String name, final StaticScope newScope) {
		// Pass1 only
		final InterfaceType newInterfaceType = new InterfaceType(name, newScope);
		currentScope_.declareType(newInterfaceType);
		newScope.setDeclaration(newInterface);
		newInterface.setType(newInterfaceType);
	}
	
	protected void lookupOrCreateRoleDeclaration(final String roleName, final int lineNumber, final boolean isRoleArray) {
		final RoleDeclaration requestedRole = currentScope_.lookupRoleDeclaration(roleName);
		if (null != requestedRole) {
			currentRole_ = requestedRole;
			
			// The way parsing is designed, these things should
			// be defined once on pass 1 and then referenced only
			// on subsequent passes.
			assert false;
		} else {
			final StaticScope rolesScope = new StaticRoleScope(currentScope_);
			final RoleDeclaration roleDecl =
					isRoleArray
					   ? new RoleArrayDeclaration(roleName, rolesScope, currentContext_, lineNumber)
					   : new RoleDeclaration(roleName, rolesScope, currentContext_, lineNumber);
			rolesScope.setDeclaration(roleDecl);
			
			// declareRole will also declare the name as an array handle
			// if isRoleArray was set (in pass 2)
			declareRole(currentScope_, roleDecl, lineNumber);
			
			final RoleType roleType = new RoleType(roleName, rolesScope);
			currentScope_.declareType(roleType);
			
			currentRole_ = roleDecl;
			assert null != currentRole_;
			currentRole_.setType(roleType);
		}
		// caller may reset currentScope - NOT us
	}
	
	protected void lookupOrCreateStagePropDeclaration(final String roleName, final int lineNumber, final boolean isStagePropArray) {
		final Declaration rawDeclaration = currentScope_.lookupRoleDeclaration(roleName);
		assert null == rawDeclaration || rawDeclaration instanceof StagePropDeclaration;
		final StagePropDeclaration requestedStageProp = (StagePropDeclaration)rawDeclaration;
		if (null != requestedStageProp) {
			currentRole_ = requestedStageProp;
			
			// The way parsing is designed, these things should
			// be defined once on pass 1 and then referenced only
			// on subsequent passes.
			assert false;
		} else {
			final StaticScope stagePropScope = new StaticRoleScope(currentScope_);
			final StagePropDeclaration stagePropDecl =
					isStagePropArray
					   ? new StagePropArrayDeclaration(roleName, stagePropScope, currentContext_, lineNumber)
					   : new StagePropDeclaration(roleName, stagePropScope, currentContext_, lineNumber);
			stagePropScope.setDeclaration(stagePropDecl);
			declareRole(currentScope_, stagePropDecl, lineNumber);
			
			final StagePropType stagePropType = new StagePropType(roleName, stagePropScope);
			currentScope_.declareType(stagePropType);
			
			currentRole_ = stagePropDecl;
			assert null != currentRole_;
			currentRole_.setType(stagePropType);
		}
		// caller may reset currentScope - NOT us
	}
	
	protected Type lookupOrCreateTemplateInstantiation(final String templateName, final List<String> parameterTypeNames, final int lineNumber) {
		// This varies by pass. On the last pass we first remove the instantiation, so that the
		// new one picks up the body created in Pass 3.
		return lookupOrCreateTemplateInstantiationCommon(templateName, parameterTypeNames, lineNumber);
	}
	
	protected Type lookupOrCreateTemplateInstantiationCommon(final String templateName, final List<String> parameterTypeNames, final int lineNumber) {
		Type retval = null;
		final TemplateDeclaration templateDeclaration = currentScope_.lookupTemplateDeclarationRecursive(templateName);
		if (null == templateDeclaration) {
			errorHook5p2(ErrorType.Fatal, lineNumber, "Template ", templateName, " is not defined. ", "");
		} else {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(templateName);
			stringBuffer.append("<");
			int i = 0;
			for (final String parameterTypeName : parameterTypeNames) {
				stringBuffer.append(parameterTypeName);
				i++;
				if (i < parameterTypeNames.size()) {
					stringBuffer.append(",");
				}
			}
			stringBuffer.append(">");
			final String typeName = stringBuffer.toString();
			
			final StaticScope templateScope = templateDeclaration.enclosingScope();
			final StaticScope templateEnclosedScope = templateDeclaration.enclosedScope();
			final TypeDeclaration baseClass = templateDeclaration.baseClass();
			final String baseClassName = null == baseClass? "void": baseClass.name();
			final ClassDeclaration baseClassDecl = null == baseClass? null:
								templateScope.lookupClassDeclarationRecursive(baseClassName);
			
			ClassDeclaration classDeclaration = currentScope_.lookupClassDeclarationRecursive(typeName);
			if (null == classDeclaration) {
				// Create a new type vector from the type parameters
				final TemplateInstantiationInfo newTypes = new TemplateInstantiationInfo(templateDeclaration, typeName);
				for (final String aTypeName : parameterTypeNames) {
					final Type correspondingType = currentScope_.lookupTypeDeclarationRecursive(aTypeName);
					if (null == correspondingType) {
						errorHook5p2(ErrorType.Fatal, lineNumber,
								"Cannot find type named ", aTypeName, " in instantiation of ", templateDeclaration.name());
						newTypes.add(StaticScope.globalScope().lookupTypeDeclaration("void"));
					} else {
						newTypes.add(correspondingType);
					}
				}
				
				// templateEnclosedScope isn't really used, because a new enclosedScope_ object
				// is created by ClassDeclaration.elaborateFromTemplate(templateDeclaration)
				classDeclaration = new ClassDeclaration(typeName, templateEnclosedScope,
						baseClassDecl, lineNumber);
				classDeclaration.elaborateFromTemplate(templateDeclaration, newTypes);
				final Type rawNewType = classDeclaration.type();
				assert rawNewType instanceof ClassType;
				final ClassType newType = (ClassType)rawNewType;
				newTypes.setClassType(newType);

				templateScope.declareType(newType);
				templateScope.declareClass(classDeclaration);

				retval = newType;
				
				// Here's where we queue template instantiatons for code generation
				parsingData_.currentTemplateInstantiationList().addDeclaration(classDeclaration);
			} else {
				retval = currentScope_.lookupTypeDeclarationRecursive(typeName);
				assert null != retval;
			}
		}
		return retval;
	}
	
	protected InterfaceDeclaration lookupOrCreateInterfaceDeclaration(final String name, final int lineNumber) {
		assert null == currentInterface_;
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		currentInterface_ = this.lookupOrCreateNewInterfaceDeclaration(name, newScope, lineNumber);
		currentScope_.declareInterface(currentInterface_);
		this.createNewInterfaceTypeSuitableToPass(currentInterface_, name, newScope);
		
		// currentScope_ is set by caller after return
		return currentInterface_;
	}
	
	private InterfaceDeclaration lookupOrCreateNewInterfaceDeclaration(final String name, final StaticScope scope, final int lineNumber) {
		return new InterfaceDeclaration(name, scope, lineNumber);
	}
	
	protected void updateInitializationLists(final Expression initializationExpr, final ObjectDeclaration objDecl) {
		/* Nothing on Pass 1 */
	}
	
	private boolean ifIsInABlockContext(final RuleContext myParentArg) {
		RuleContext myParent = myParentArg;
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
	
	public static long nestingLevelInsideBreakable(final ParseTree ctxArg) {
		ParseTree ctx = ctxArg;
		
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
	
	public static long nestingLevelInsideBreakableForContinue(final ParseTree ctxArg) {
		ParseTree ctx = ctxArg;
		
		// Like nestingLevelInsideBreakable, but for Continue rather than break.
		// The main difference is that we don't count the context of the for
		// loop itself, since it stays open across a continue execution
		long retval = 0;

		for (; (null != ctx) && ((ctx instanceof ProgramContext) == false); ctx = ctx.getParent()) {
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
	
	private DeclarationsAndInitializers processIdentifierList(final Identifier_listContext identifier_list,
			final Type type, final int lineNumber, final AccessQualifier accessQualifier)
	{
		variablesToInitialize_ = new SimpleList();
		initializationExpressions_ = new SimpleList();
		final List<ObjectDeclaration> objectDecls = new ArrayList<ObjectDeclaration>();
		
		this.processIdentifierListRecursive(identifier_list, type, lineNumber, accessQualifier);
		
		final List<BodyPart> intializationExpressionsToReturn = new ArrayList<BodyPart>();
		
		final long initializationCount = variablesToInitialize_.count();
		for (int j = 0; j < initializationCount; j++) {
			final Expression initializationExpression = (Expression)initializationExpressions_.objectAtIndex(j);
			initializationExpression.setResultIsConsumed(true);
			final ObjectDeclaration objDecl = (ObjectDeclaration)variablesToInitialize_.objectAtIndex(j);
			      Type expressionType = initializationExpression.type();
			final Type declarationType = objDecl.type();
			
			if (null != declarationType && null != expressionType &&
					declarationType.canBeConvertedFrom(expressionType)) {
				// Still need this, though old initialization framework is gone
				objectDecls.add(objDecl);
				
				final IdentifierExpression lhs = new IdentifierExpression(objDecl.name(), declarationType, currentScope_);
				final AssignmentExpression initialization = new AssignmentExpression(lhs, "=", initializationExpression, identifier_list.getStart().getLine(), this);
				intializationExpressionsToReturn.add(initialization);
				
				// New initialization association
				objDecl.setInitialization(initialization);
			} else {
				errorHook5p2(ErrorType.Fatal, objDecl.lineNumber(), "Type mismatch in initialization of `", objDecl.name(), "'.", "");
			}
		}
		
		final DeclarationsAndInitializers retval = new DeclarationsAndInitializers(objectDecls, intializationExpressionsToReturn);
		return retval;
	}
	
	private void processIdentifierListRecursive(final Identifier_listContext identifier_list, final Type type, final int lineNumber, final AccessQualifier accessQualifier)
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
						this.nameCheck(tokAsText, lineNumber);
						objDecl = this.pass1InitialDeclarationCheck(tokAsText, lineNumber);
						if (null == objDecl) {
							objDecl = new ObjectDeclaration(tokAsText, type, lineNumber);
							declareObjectSuitableToPass(currentScope_, objDecl);
							objDecl.setAccess(accessQualifier, currentScope_, lineNumber);
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
						this.nameCheck(tokAsText, lineNumber);
						if (tokAsText.equals("=") == true) {
							; // we get it with the ExprContext catch below
						} else if (tokAsText.equals(",") == true) {
							; // skip it; it separates elements
						} else {
							objDecl = this.pass1InitialDeclarationCheck(tokAsText, lineNumber);
							if (null == objDecl) {
								objDecl = new ObjectDeclaration(tokAsText, type, lineNumber);
								declareObjectSuitableToPass(currentScope_, objDecl);
								objDecl.setAccess(accessQualifier, currentScope_, lineNumber);
							} else {
								// Doesn't hurt to update type
								objDecl.updateType(type);
							}
						}
					}
				}
			} else if (pt instanceof Identifier_listContext) {
				this.processIdentifierListRecursive((Identifier_listContext)pt, type, lineNumber, accessQualifier);
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
	
	public ObjectDeclaration pass1InitialDeclarationCheck(final String name, final int lineNumber) {
		final ObjectDeclaration objDecl = currentScope_.lookupObjectDeclaration(name);
		if (null != objDecl) {
			final String addedMessage = "(earlier declaration at line " +
								Integer.toString(objDecl.lineNumber()) + ").";
			errorHook5p1(ErrorType.Fatal, lineNumber, "Identifier `",
					name, "' declared multiple times ", addedMessage);
		}
		return objDecl;
	}

	@Override public void enterParam_list(KantParser.Param_listContext ctx)
	{
		if (ctx.param_decl() != null) {
			// : param_decl
	        // | param_list ',' param_decl
	        // | /* null */
			
			// Do most of this parameter stuff here on the second pass, because
			// we should have most of the type information by then
			String formalParameterName = ctx.param_decl().JAVA_ID().getText();
			final String paramTypeName = ctx.param_decl().compound_type_name().getText();
			String paramTypeBaseName = null;
			
			// Handle vector arguments
			final List<ParseTree> children = ctx.param_decl().compound_type_name().children;
			final int numberOfChildren = children.size();
			paramTypeBaseName = children.get(0).getText();
			boolean isArray = false;
			if (numberOfChildren == 3) {
				final String firstModifier = children.get(1).getText();
				final String secondModifier = children.get(2).getText();
				if (firstModifier.equals("[") && secondModifier.equals("]")) {
					// Is an array declaration
					isArray = true;
				}
			}
			
			Type paramType = currentScope_.lookupTypeDeclarationRecursive(paramTypeBaseName);
			if (null == paramType) {
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "Parameter type ", paramTypeName, " not declared for ", formalParameterName);
				paramType = parsingData_.globalScope().lookupTypeDeclaration("void");
				errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(), "You cannot name a formal parameter `this'.", "", "", "");
			} else if (isArray) {
				// A derived type
				final String aName = paramType.getText() + "_$array";
				paramType = new ArrayType(aName, paramType);
			}
			
			ObjectDeclaration newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart().getLine());
			
			// They go in reverse order...
			if (newFormalParameter.name().equals("this")) {
				formalParameterName = "_error" + String.valueOf(variableGeneratorCounter_);
				newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart().getLine());
			}
			parsingData_.currentFormalParameterList().addFormalParameter(newFormalParameter);
		} else {
			// empty parameter list - it's OK. For now.
		}
	}
	
	
	private Expression processIndexedArrayElement(final ParseTree arrayExprCtx, final KantParser.ExprContext sexpCtx,
			final TerminalNode ABELIAN_INCREMENT_OPCtx) {
		// | abelian_expr '[' expr ']' ABELIAN_INCREMENT_OP
        // | ABELIAN_INCREMENT_OP expr '[' expr ']'
		
		Expression retval = null;
		final Expression indexExpr = parsingData_.popExpression();
		indexExpr.setResultIsConsumed(true);
		
		final Expression rawArrayBase = parsingData_.popExpression();
		final Type rawArrayBaseType = rawArrayBase.type();
		assert rawArrayBaseType instanceof ArrayType;
		final ArrayType arrayType = (ArrayType)rawArrayBaseType;
		final Type baseType = arrayType.baseType();
		final ArrayExpression arrayBase = new ArrayExpression(rawArrayBase, baseType);
		arrayBase.setResultIsConsumed(true);
		
		final Interval JavaIDInterval = arrayExprCtx.getSourceInterval();
		final Interval OperatorInterval = ABELIAN_INCREMENT_OPCtx.getSourceInterval();
		final UnaryopExpressionWithSideEffect.PreOrPost preOrPost = JavaIDInterval.startsAfter(OperatorInterval)?
				UnaryopExpressionWithSideEffect.PreOrPost.Pre: UnaryopExpressionWithSideEffect.PreOrPost.Post;
		retval = new ArrayIndexExpressionUnaryOp(arrayBase, indexExpr, ABELIAN_INCREMENT_OPCtx.getText(), preOrPost,
				sexpCtx.getStart().getLine());
		return retval;
	}
		
	protected void checkExprDeclarationLevel(RuleContext ctxParent, Token ctxGetStart) {
		/* Nothing */
	}
	
	
	// ----------------------------------------------------------------------------------------

	// WARNING. Tricky code here
	protected void declareObject(final StaticScope s, final ObjectDeclaration objdecl) { s.declareObject(objdecl); }
	public void declareRole(final StaticScope s, final RoleDeclaration roledecl, final int lineNumber) {
		s.declareRole(roledecl);
	}
	
	protected void errorHook5p1(final ErrorType errorType, final int i, final String s1, final String s2, final String s3, final String s4) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4);
	}
	public void errorHook5p2(final ErrorType errorType, final int i, final String s1, final String s2, final String s3, final String s4) {
		/* nothing */
	}
	protected void errorHook6p1(final ErrorType errorType, final int i, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		ErrorLogger.error(errorType, i, s1, s2, s3, s4, s5, s6);
	}
	public void errorHook6p2(final ErrorType errorType, final int i, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		/* nothing */
	}
	protected ContextDeclaration lookupOrCreateContextDeclaration(final String name, final int lineNumber) {
		final StaticScope newScope = new StaticScope(currentScope_);
		final ContextDeclaration contextDecl = new ContextDeclaration(name, newScope, currentContext_, lineNumber);
		newScope.setDeclaration(contextDecl);
		final Type rawObjectType = StaticScope.globalScope().lookupTypeDeclaration("Object");
		assert rawObjectType instanceof ClassType;
		final ClassType objectType = (ClassType) rawObjectType;
		final ContextType newContextType = new ContextType(name, newScope, objectType);
		declareTypeSuitableToPass(currentScope_, newContextType);
		contextDecl.setType(newContextType);
		currentScope_.declareContext(contextDecl);
		currentScope_ = newScope;
		return contextDecl;
	}
	protected ClassDeclaration lookupOrCreateClassDeclaration(final String name, final ClassDeclaration rawBaseClass, final ClassType baseType, final int lineNumber) {
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		final ClassDeclaration newClass = this.lookupOrCreateNewClassDeclaration(name, newScope, rawBaseClass, lineNumber);
		currentScope_.declareClass(newClass);
		this.createNewClassTypeSuitableToPass(newClass, name, newScope, baseType);
		currentScope_ = newScope;
		return newClass;
	}
	protected TemplateDeclaration lookupOrCreateTemplateDeclaration(final String name, final TypeDeclaration rawBaseType, final Type baseType, final int lineNumber) {
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		final TemplateDeclaration newTemplate = this.lookupOrCreateNewTemplateDeclaration(name, newScope, rawBaseType, lineNumber);
		newScope.setDeclaration(newTemplate);
		currentScope_.declareTemplate(newTemplate);
		this.createNewTemplateTypeSuitableToPass(newTemplate, name, newScope, (ClassType)baseType);
		currentScope_ = newScope;
		return newTemplate;
	}
	protected void declareTypeSuitableToPass(final StaticScope scope, final Type decl) {
		scope.declareType(decl);
	}
	protected void declareObjectSuitableToPass(final StaticScope scope, final ObjectDeclaration objDecl) {
		final String objectIdentifier = objDecl.name();
		final Declaration existingDecl = scope.lookupObjectDeclaration(objectIdentifier);
		if (null != existingDecl) {
			errorHook5p1(ErrorType.Fatal, objDecl.lineNumber(), "Multiple declarations of `",
					objectIdentifier, "'", "");
		} else {
			scope.declareObject(objDecl);
		}
	}
	protected void declareFormalParametersSuitableToPass(StaticScope scope, ObjectDeclaration objDecl) {
		scope.declareObject(objDecl);
	}
	
	private ExpressionStackAPI exprFromExprDotJAVA_ID(final TerminalNode ctxJAVA_ID, final Token ctxGetStart, final TerminalNode ctxABELIAN_INCREMENT_OP) {
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
		
		assert null != qualifier;
		if (null != qualifier.type() && qualifier.type().name().equals("Class")) {
			// This is where we handle types like "System" for System.out.print*
			// Now we need to get the actual class of that name
			final Type rawClass = currentScope_.lookupTypeDeclarationRecursive(qualifier.name());
			assert rawClass instanceof ClassType;
			final ClassType theClass = (ClassType)rawClass;
			
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
			
			if (null != odecl) {
				final boolean isAccessible = currentScope_.canAccessDeclarationWithAccessibility(odecl, odecl.accessQualifier_, 
						ctxGetStart.getLine());
				if (isAccessible == false) {
					errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(),
							"Cannot access expression `", expression.getText(),
							"' with `", odecl.accessQualifier_.asString(), "' access qualifier.", "");
				}
			}
			
			if (null == odecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Field `", javaIdString,
						"' not found as member of ", object.type().name());
			}
		}
		
		assert null != expression;
		
		return expression;
	}
	
	public <ExprType> Expression newExpr(final List<ParseTree> ctxChildren, final Token ctxGetStart,
			final ExprType ctxExpr, final MessageContext ctxMessage) {
		// : 'new' message
		// | 'new' type_name '[' expr ']'
		// Called in all passes.
		Expression expression = null;	// guaranteed non-null return
		final Message message = parsingData_.popMessage();
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		if (null == ctxExpr && null != ctxMessage){
			// : 'new' message
			final String className = message.selectorName(); // I know -- kludge ...
			final Type type = currentScope_.lookupTypeDeclarationRecursive(className);
			if ((type instanceof ClassType) == false && (type instanceof ContextType) == false) {
				if (type instanceof TemplateParameterType) {
					// then it's Ok
					expression = new NewExpression(type, message, ctxMessage.getStart().getLine(), enclosingMegaType);
					addSelfAccordingToPass(type, message, currentScope_);
				} else {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "`new ", className, "': can apply `new' only to a class or Context type", "");
					expression = new NullExpression();
				}
			} else {
				// On the first pass, message doesn't yet have an argument list
				expression = new NewExpression(type, message, ctxMessage.getStart().getLine(), enclosingMegaType);
				
				// This adds a hokey argument to the message that
				// is used mainly for signature checking - to see
				// if there is a constructor that matches the
				// arguments of the "new" message.
				addSelfAccordingToPass(type, message, currentScope_);
			}
			
			// Is there a constructor?
			// This does anything on Passes 2 and 3 only
			ctorCheck(type, message, ctxGetStart.getLine());
		} else if (null != ctxExpr && null == ctxMessage) {
			// | 'new' type_name '[' abelian_expr ']'
			final Expression expr = parsingData_.popExpression();
			final ExpressionStackAPI raw_type_expression = parsingData_.popRawExpression();
			assert raw_type_expression instanceof Type;
			final Type type_name_expression = (Type)raw_type_expression;
			final String typeName = type_name_expression.name();
			final Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			if (null == type) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "'new ", typeName, " [] for undefined type: ", typeName);
				expression = new NullExpression();
			} else {
				expr.setResultIsConsumed(true);
				expression = new NewArrayExpression(type, expr, enclosingMegaType);
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
	public void ctorCheck(Type type, Message message, int lineNumber) {
		/* Nothing */
	}

	// This is a template function mainly for historial reasons data back to
	// expr and sexpr, and should be updated. We separated those non-terminals
	// as a workaround for left-recursion in some of the productions. In some sense
	// we blew up the grammar to avoid the ambiguity; here, we bring the leaves
	// (the processing for the duplicate productions) back together.
	
	// You find something analogous with exitExpr here in pass 1.
	
	public <ExprType> Expression messageSend(final Token ctxGetStart, final ExprType ctxExpr) {
		// | expr '.' message
		// | message
		// Pass 1 version
		// Pop the expression for the indicated object and message
		Expression object = null;
		Type type = null, enclosingMegaType = null;
		MethodDeclaration mdecl = null;
		
		if (ctxExpr != null) {
			if (parsingData_.currentExpressionExists()) {
				object = parsingData_.popExpression();
			} else {
				object = new NullExpression();
			}
		} else {
			final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
			enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
			if (null == enclosingMegaType) {
				object = new NullExpression();
			} else {
				object = new IdentifierExpression("this", enclosingMegaType, nearestMethodScope);
			}
		}
		assert null != object;
			
		final Message message = parsingData_.popMessage();

		if (null == enclosingMegaType && object instanceof NullExpression) {
			// Because this here is Pass 1 code this really does nothing.
			// We'll catch it again on Pass 2
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
					"Invoking method `", message.selectorName(), "' on implied object `this' in a non-object context.", "");;
		} else {
			final Type objectType = object.type();
			if (null == objectType) return new NullExpression();	// error stumbling avoidance
				
			final String methodSelectorName = message.selectorName();
			final ClassDeclaration classdecl = currentScope_.lookupClassDeclarationRecursive(objectType.name());
			mdecl = classdecl != null?
							classdecl.enclosedScope().lookupMethodDeclaration(methodSelectorName, null, true):
							null;
							
			if (null != mdecl && objectType.name().equals("Class")) {
				// Is of the form ClassType.classMethod()
				assert object instanceof IdentifierExpression;
				if (false == mdecl.signature().isStatic()) {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
							"Attempt to call instance method `" + mdecl.signature().getText(),
							"' as though it were a static method of class `", objectType.name(), "'.");
				}
			}				
											
			if (null == mdecl) {
				// final String className = classdecl != null? classdecl.name(): " <unresolved>.";
				// skip it - we'll barked at the user in pass 2
				// errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "' not declared in class ", className);
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			} else {
				type = mdecl.returnType();
				
				// This is tautological a no-op, because we
				// castrate this stuff within Pass 1
				// checkForMessageSendViolatingConstness(mdecl.signature(), ctxGetStart);
			}
			
			// If there is an error in the method return type, type might still be null
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
			
			if (null != type && type instanceof TemplateParameterType) {
				// Is a template type. Change the return type into a bona fide type here
				final StaticScope objectScope = objectType.enclosedScope();
				final TemplateInstantiationInfo templateInstantiationInfo = objectScope.templateInstantiationInfo();
				type = templateInstantiationInfo.classSubstitionForTemplateTypeNamed(type.name());
			}
			
			assert type != null;
		}
		
		object.setResultIsConsumed(true);
		final boolean isStatic = (null != mdecl) && (null != mdecl.signature()) && mdecl.signature().isStatic();
		return new MessageExpression(object, message, type, ctxGetStart.getLine(), isStatic);
	}
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationIn(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 1 version. Pass 2 / 3 version ignores "this" in signature,
		// and checks the signature
		final StaticScope classScope = classDecl.enclosedScope();
		return classScope.lookupMethodDeclaration(methodSelectorName, parameterList, true);
	}
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationUpInheritanceHierarchy(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 1 version. Pass 2 / 3 version ignores "this" in signature,
		// and checks the signature
		StaticScope classScope = classDecl.enclosedScope();
		MethodDeclaration retval = classScope.lookupMethodDeclaration(methodSelectorName, parameterList, true);
		if (null == retval) {
			if (classDecl instanceof ClassDeclaration) {	// should be
				ClassDeclaration classDeclAsClassDecl = (ClassDeclaration) classDecl;
				final ClassDeclaration baseClassDeclaration = classDeclAsClassDecl.baseClassDeclaration();
				if (null != baseClassDeclaration) {
					classScope = baseClassDeclaration.enclosedScope();
					retval =  classScope.lookupMethodDeclaration(methodSelectorName, parameterList, true);
				} else {
					retval = null;
				}
			}
		}
		return retval;
	}
	/*
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationRecursiveIn(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 1 version. Pass 2 / 3 version ignores "this" in signature
		// and checks the signature
		return classDecl.enclosedScope().lookupMethodDeclarationRecursive(methodSelectorName, parameterList, true);
	}
	*/
	protected Type processReturnType(final Token ctxGetStart, final Expression object, final Type objectType, final Message message) {
		final String objectTypeName = objectType.name();
		final ClassDeclaration classDecl = currentScope_.lookupClassDeclarationRecursive(objectTypeName);
		final RoleDeclaration roleDecl = currentScope_.lookupRoleDeclarationRecursive(objectTypeName);
		final ContextDeclaration contextDecl = currentScope_.lookupContextDeclarationRecursive(objectTypeName);
		final InterfaceDeclaration interfaceDecl = currentScope_.lookupInterfaceDeclarationRecursive(objectTypeName);
		
		final String methodSelectorName = message.selectorName();
		assert null != methodSelectorName;
		
		MethodDeclaration mdecl = null;
		Type returnType = null;
		final ActualArgumentList actualArgumentList = message.argumentList();
		
		if (null != classDecl) {
			mdecl = processReturnTypeLookupMethodDeclarationUpInheritanceHierarchy(classDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				final Type currentEnclosingType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				if (currentEnclosingType instanceof TemplateType) {
					// Ingore parameters as in Pass 1. We may not find a match with a template type...
					mdecl = classDecl.enclosedScope().lookupMethodDeclarationRecursive(methodSelectorName, actualArgumentList, true);
					if (null == mdecl) {
						errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName,
								"' not declared in class `", classDecl.name(), "'", "");
					}
				} else {
					errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName,
							"' not declared in class `", classDecl.name(), "'", "");
				}
			}
			
			final ClassDeclaration baseClassDeclaration = classDecl.baseClassDeclaration();
			if (null != baseClassDeclaration) {
				final String baseClassName = baseClassDeclaration.name();
				boolean noerrors = true;
				if (methodSelectorName.equals(baseClassName) && null != mdecl && 
						mdecl.enclosingScope().pathName().equals(baseClassDeclaration.enclosedScope().pathName())) {
					// We are invoking a base class constructor. Cool.
					// Make sure it's the first thing in the class.
					if (parsingData_.currentExprAndDeclExists()) {
						final ExprAndDeclList currentExprAndDecl = parsingData_.currentExprAndDecl();
						if (currentExprAndDecl.bodyParts().isEmpty() == false) {
							errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
									"Call of base class constructor `",
									baseClassName,
									"' must be the first statement in the derived class constructor.",
									"");
							noerrors = false;
						}
					}
					
					// Make sure the current method is a constructor!
					final MethodSignature currentMethod = parsingData_.currentMethodSignature();
					final ClassDeclaration currentClass = parsingData_.currentClassDeclaration();
					if (currentClass.name().equals(currentMethod.name()) == false) {
						errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
								"Base class constructor `",
								baseClassName,
								"' can be explicitælly invoked only from a derived class constructor.",
								"");
						noerrors = false;
					}
					
					if (noerrors) {
						// There is code that automatically generates a
						// base class constructor call (to a default constructor)
						// if it can find one. If the programmer has taken over,
						// cancel the automatic one.
						
						// Get the calling method
						final StaticScope callingScope = classDecl.enclosedScope();
						FormalParameterList paramsToCurrentMethod = currentMethod.formalParameterList();
						final String callingConstructorName = classDecl.name();
						final MethodDeclaration callingMethod = callingScope.lookupMethodDeclaration(callingConstructorName, paramsToCurrentMethod, false);
						assert null != callingMethod;
						callingMethod.hasManualBaseClassConstructorInvocations(true);
					}
				}
			}
		} else if (null != roleDecl) {
			// Calling a role method
			mdecl = processReturnTypeLookupMethodDeclarationIn(roleDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "' not declared in Role ", roleDecl.name());
			}
		} else if (null != contextDecl) {
			mdecl = processReturnTypeLookupMethodDeclarationUpInheritanceHierarchy(contextDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "' not declared in Context ", contextDecl.name());
			}
		} else if (null != interfaceDecl) {
			final MethodSignature methodSignature = interfaceDecl.lookupMethodSignatureDeclaration(methodSelectorName, actualArgumentList);
			if (null == methodSignature) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Method `", methodSelectorName, "' not declared in interface ", interfaceDecl.name());
			}
		} else if (objectTypeName.equals("Class")) {
			final ClassDeclaration classDeclaration = currentScope_.lookupClassDeclarationRecursive(object.name());
			if (null == classDeclaration) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class, Role, or interface `", object.name(), "'", "");
			} else {
				mdecl = classDeclaration.enclosedScope().lookupMethodDeclaration(methodSelectorName, actualArgumentList, false);
				if (null == mdecl) {
					mdecl = classDeclaration.enclosedScope().lookupMethodDeclarationWithConversion(methodSelectorName, actualArgumentList, false);
					if (null == mdecl) {
						errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find static method `" + methodSelectorName,
							"' of class `", object.name(), "'.");
					}
				}
			}
		} else if (objectTypeName.endsWith("_$array")) {
			if (methodSelectorName.equals("size") && actualArgumentList.count() == 1) {
				returnType = StaticScope.globalScope().lookupTypeDeclaration("int");	// is O.K.
			} else {
				if (object.name().length() > 0) {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class, Role, or interface for `", object.name(), "'.", "");
				} else {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class, Role, or interface of this ", "type", "", "");
				}
				assert null == mdecl;
			}
		} else {
			if (object.name().length() > 0) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class, Role, or interface for `", object.name(), "'.", "");
			} else {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Cannot find class, Role, or interface of this ", "type", "", "");
			}
			assert null == mdecl;
		}
		
		if (null != mdecl) {
			final FormalParameterList formals = mdecl.formalParameterList();
			assert formals != null;
			final ActualArgumentList actuals = message.argumentList();
			assert actuals != null;
			
			final TypeDeclaration typeDecl = null != classDecl? classDecl: contextDecl;
			
			if (mdecl.name().equals("assert")) {
				int k = 0;
				k++;
			}
			// Type check is polymorphic in compiler passes
			this.typeCheckIgnoringParameter(formals, actuals, mdecl, typeDecl, "this", ctxGetStart);
			
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
			final Declaration rawRetval = scope.associatedDeclaration();
			assert null == rawRetval || rawRetval instanceof MethodDeclaration;
			retval = (MethodDeclaration)rawRetval;
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
			final Declaration rawDeclaration = scope.associatedDeclaration();
			assert null == rawDeclaration || rawDeclaration instanceof MethodDeclaration;
			retval = (MethodDeclaration)rawDeclaration;
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
		final StaticScope nearestEnclosingMethodScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
		if (idName.equals("index")) {
			// This is a legal identifier if invoked from within the
			// scope of a Role, where the Role is declared as a Role
			// vector type
			type = StaticScope.globalScope().lookupTypeDeclaration("void");	// default/error value
			expression = new NullExpression();
			if (null == currentRole_) {
				errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
						"Symbol index may be used only within certain Role methods.", "", "", "");
			} else {
				if (currentRole_.isArray()) {
					expression = new IndexExpression(currentRole_, currentContext_);
				} else {
					errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(),
							"Symbol index may be used only within a Role vector method. The role ",
							currentRole_.name(), " is a scalar.", "");
				}
			}
		} else if (null != objdecl) {
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
			final Type rawRoleType = declaringScope.lookupTypeDeclaration(idName);	// Type$RoleType
			assert rawRoleType instanceof RoleType;
			final RoleType roleType = (RoleType)rawRoleType;
			if (this.isInsideMethodDeclaration(ctxJAVA_ID)) {
				final IdentifierExpression qualifier = new IdentifierExpression("this", roleType, nearestEnclosingMethodScope);
				qualifier.setResultIsConsumed(true);
				expression = new QualifiedIdentifierExpression(qualifier, idName, roleType);
			} else {
				errorHook5p2(ErrorType.Unimplemented, ctxGetStart.getLine(),
						"Static initializers for Roles are unimplemented.", "", "", "");
			}
		} else {
			final ClassDeclaration cdecl = currentScope_.lookupClassDeclarationRecursive(idName);
			if (null != cdecl) {
				type = globalScope.lookupTypeDeclaration("Class");
				declaringScope = globalScope;
			} else if (null == declaringScope) {
				// NOTE: This will also lump in references to Role identifiers
				// They are distinguished by their enclosing scope
				declaringScope = Expression.nearestEnclosingMethodScopeOf(currentScope_);
			}
				
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
				
			expression = new IdentifierExpression(idName, type, declaringScope);
		}
		
		assert null != expression;
		return expression;
	}
	
	private boolean isInsideMethodDeclaration(final TerminalNode ctx) {
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
			} else if (walker instanceof Abelian_atomContext) {
				;
			} else if (walker instanceof Abelian_unary_opContext) {
				;
			} else if (walker instanceof Abelian_productContext) {
				;
			} else if (walker instanceof Identifier_listContext) {
				;
			} else if (walker instanceof Object_declContext) {
				;
			} else if (walker instanceof Argument_listContext) {
				;
			} else if (walker instanceof MessageContext) {
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
	public void binopTypeCheck(final Expression leftExpr, final String operationAsString,
			final Expression rightExpr, final Token ctxGetStart) {
		/* Nothing */
	}
	protected void typeCheckIgnoringParameter(final FormalParameterList formals, final ActualArgumentList actuals,
			final MethodDeclaration mdecl, final TypeDeclaration classdecl, final String parameterToIgnore, final Token ctxGetStart)
	{
		/* Nothing */
	}
	protected void reportMismatchesWith(final int lineNumber, final RoleType lhsType, final Type rhsType) {
		/* Nothing */
	}
	public Expression assignmentExpr(final Expression lhs, final String operator, final Expression rhs,
			final KantParser.Abelian_exprContext ctx) {
		// abelian_expr ASSIGN expr
		assert null != rhs;
		assert null != lhs;
		
		final Token ctxGetStart = ctx.getStart();
			
		final Type lhsType = lhs.type(), rhsType = rhs.type();
		
		@SuppressWarnings("unused")
		boolean tf = lhsType instanceof RoleType;
		tf = null != rhsType;
		if (lhsType instanceof RoleType && rhsType instanceof ArrayType) {
			if (((RoleType)lhsType).isArray()) {
				tf = lhsType.canBeConvertedFrom(((ArrayType)rhsType).baseType(), ctx.getStart().getLine(), this);
			} else {
				errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Type of `", lhs.getText(),
						"' is incompatible with expression type `", rhsType.name(), "'.", "");
			}
		} else if (null != lhsType){
			tf = lhsType.canBeConvertedFrom(rhsType, ctx.getStart().getLine(), this);
		} else {
			tf = false;
		}
		
		if (lhs.name().equals("this")) {
			errorHook5p2(ErrorType.Noncompliant, ctx.getStart().getLine(),
					"You're on your own here.", "", "", "");
		}
		
		if (lhs.name().equals("index")) {
			errorHook5p2(ErrorType.Fatal, ctx.getStart().getLine(),
					"`index' is a reserved word which is a read-only property of a Role vector element,",
					" and may not be assigned.", "", "");
		} else if (lhsType instanceof RoleType && null != rhsType && rhsType instanceof ArrayType) {
			final Type baseType = ((ArrayType)rhsType).baseType();
			if (lhsType.canBeConvertedFrom(baseType) == false) {
				errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Role vector ", lhsType.name(), " cannot be played by vector of objects of type ",
						((ArrayType)rhsType).baseType().name(), ":", "");
			}
			this.checkRoleClassNameCollision((RoleType)lhsType, baseType, ctxGetStart.getLine());
		} else if (lhsType instanceof RoleType && null != rhsType) {
			if (lhsType.canBeConvertedFrom(rhsType) == false) {
				errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Role `", lhsType.name(), "' cannot be played by object of type `", rhsType.name(), "':", "");
				this.reportMismatchesWith(ctxGetStart.getLine(), (RoleType)lhsType, rhsType);
			}
			this.checkRoleClassNameCollision((RoleType)lhsType, rhsType, ctxGetStart.getLine());
		} else if (null != lhsType && null != rhsType && lhsType.canBeConvertedFrom(rhsType) == false) {
			errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Type of `", lhsType.name(), "' is incompatible with expression type `", rhsType.name(), "'.", "");
		} else if (lhs instanceof ArrayIndexExpression) {
			final Type anotherLhsType = ((ArrayIndexExpression)lhs).baseType();
			if (null != anotherLhsType && null != rhsType && anotherLhsType.canBeConvertedFrom(rhsType) == false) {
				errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Type of `", lhs.getText(),
						"' is incompatible with expression type `", rhsType.name(), "'.", "");
			}
		} else if (lhs instanceof RoleArrayIndexExpression) {
			if (lhsType.canBeConvertedFrom(rhsType) == false) {
				errorHook6p2(ErrorType.Fatal, ctxGetStart.getLine(), "Role `", lhsType.name(),
						"' cannot be played by object of type `", rhsType.name(), "':", "");
				this.reportMismatchesWith(ctxGetStart.getLine(), (RoleType)lhsType, rhsType);
			}
		} else if ((lhs instanceof IdentifierExpression) == false &&
				   (lhs instanceof QualifiedIdentifierExpression) == false) {
			errorHook5p2(ErrorType.Fatal, ctxGetStart.getLine(), "Can assign only to an identifier, qualified identifier, or vector element.", "", "", "");
		}
		
		rhs.setResultIsConsumed(true);
		final AssignmentExpression retval = new AssignmentExpression(lhs, operator, rhs, ctx.getStart().getLine(), this);
		checkForAssignmentViolatingConstness(retval, ctx.getStart());
		
		return retval;
	}
	
	private void checkRoleClassNameCollision(final RoleType lhsType, final Type baseType, int lineNumber) {
		if (baseType instanceof ClassType) {
			// There should be no duplicates between signatures in RoleType
			// and those in Class Type
			final ClassType classType = (ClassType)baseType;
			final List<MethodDeclaration> classMethodList = classType.enclosedScope().methodDeclarations();
			for (final MethodDeclaration methodDeclaration : classMethodList) {
				final ActualOrFormalParameterList parameterList = methodDeclaration.formalParameterList();
				final String methodSelector = methodDeclaration.name();
				final MethodDeclaration correspondingRoleMethod = lhsType.enclosedScope().lookupMethodDeclarationIgnoringRoleStuff(methodSelector, parameterList);
				if (null != correspondingRoleMethod) {
					errorHook6p2(ErrorType.Warning, lineNumber,
							"WARNING: Both class `" + baseType.name(), "' and Role `" + lhsType.name(),
							"' contain the same method signature `", correspondingRoleMethod.signature().getText(),
							"'. This results in several methods of the same name in the same object",
							" and may not behave as you expected.");
				}
			}
		}
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
	
	protected <ExprType> Expression expressionFromReturnStatement(final ExprType ctxExpr, final RuleContext unused, final Token ctxGetStart) {
		// Pass 1 version. There is another version for Pass 3 / 4.
		Expression retval = null;
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		if (null != ctxExpr) {
			final Expression returnExpression = parsingData_.popExpression();
			returnExpression.setResultIsConsumed(true);
			retval = new ReturnExpression(returnExpression, ctxGetStart.getLine(), enclosingMegaType);
		}
		return retval;
	}
	
	public void nameCheck(final String name, int lineNumber) {
		if (name.equals("this") || name.equals("Ralph") || name.equals("Sue") || name.equals("index")) {
			errorHook5p2(ErrorType.Fatal, lineNumber, "Please avoid the use of the names `this', `Sue', `index' and `Ralph' for identifiers.", "", "", "");
		}
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
	protected InterfaceDeclaration currentInterface_;
	private int variableGeneratorCounter_;
    // -------------------------------------------------------------------------------------------------------
}