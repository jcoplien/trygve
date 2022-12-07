package info.fulloo.trygve.parser;

/*
 * Trygve IDE 4.3
 *   Copyright (c)2023 James O. Coplien, jcoplien@gmail.com
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

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
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
import info.fulloo.trygve.declarations.Declaration.ClassOrContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.ErrorDeclaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectSubclassDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleArrayDeclaration;
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
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.BuiltInType;
import info.fulloo.trygve.declarations.Type.ClassOrContextType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.ContextType;
import info.fulloo.trygve.declarations.Type.ErrorType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.StagePropType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.declarations.Type.TemplateTypeForAnInterface;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.declarations.Type.VarargsType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Constant;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.ExpressionList;
import info.fulloo.trygve.expressions.ExpressionStackAPI;
import info.fulloo.trygve.expressions.Constant.BooleanConstant;
import info.fulloo.trygve.expressions.Expression.ArrayExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.AssignmentExpression;
import info.fulloo.trygve.expressions.Expression.InternalAssignmentExpression;
import info.fulloo.trygve.expressions.Expression.BlockExpression;
import info.fulloo.trygve.expressions.Expression.BreakExpression;
import info.fulloo.trygve.expressions.Expression.ContinueExpression;
import info.fulloo.trygve.expressions.Expression.DoWhileExpression;
import info.fulloo.trygve.expressions.Expression.DupMessageExpression;
import info.fulloo.trygve.expressions.Expression.ErrorExpression;
import info.fulloo.trygve.expressions.Expression.IdentityBooleanExpression;
import info.fulloo.trygve.expressions.Expression.IndexExpression;
import info.fulloo.trygve.expressions.Expression.LastIndexExpression;
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
import info.fulloo.trygve.expressions.Expression.TopOfStackExpression;
import info.fulloo.trygve.expressions.Expression.UnaryAbelianopExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect;
import info.fulloo.trygve.expressions.Expression.WhileExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.expressions.MethodInvocationEnvironmentClass;
import info.fulloo.trygve.mylibrary.SimpleList;
import info.fulloo.trygve.parser.KantParser.Abelian_atomContext;
import info.fulloo.trygve.parser.KantParser.Abelian_exprContext;
import info.fulloo.trygve.parser.KantParser.Abelian_productContext;
import info.fulloo.trygve.parser.KantParser.Abelian_unary_opContext;
import info.fulloo.trygve.parser.KantParser.Argument_listContext;
import info.fulloo.trygve.parser.KantParser.BlockContext;
import info.fulloo.trygve.parser.KantParser.Boolean_atomContext;
import info.fulloo.trygve.parser.KantParser.Boolean_exprContext;
import info.fulloo.trygve.parser.KantParser.Boolean_productContext;
import info.fulloo.trygve.parser.KantParser.Boolean_unary_opContext;
import info.fulloo.trygve.parser.KantParser.Builtin_type_nameContext;
import info.fulloo.trygve.parser.KantParser.Compound_type_nameContext;
import info.fulloo.trygve.parser.KantParser.Do_while_exprContext;
import info.fulloo.trygve.parser.KantParser.ExprContext;
import info.fulloo.trygve.parser.KantParser.Expr_and_decl_listContext;
import info.fulloo.trygve.parser.KantParser.For_exprContext;
import info.fulloo.trygve.parser.KantParser.Identifier_listContext;
import info.fulloo.trygve.parser.KantParser.If_exprContext;
import info.fulloo.trygve.parser.KantParser.MessageContext;
import info.fulloo.trygve.parser.KantParser.Method_declContext;
import info.fulloo.trygve.parser.KantParser.Method_decl_hookContext;
import info.fulloo.trygve.parser.KantParser.Method_signatureContext;
import info.fulloo.trygve.parser.KantParser.Object_declContext;
import info.fulloo.trygve.parser.KantParser.ProgramContext;
import info.fulloo.trygve.parser.KantParser.Switch_bodyContext;
import info.fulloo.trygve.parser.KantParser.Switch_exprContext;
import info.fulloo.trygve.parser.KantParser.Type_and_expr_and_decl_listContext;
import info.fulloo.trygve.parser.KantParser.Type_declarationContext;
import info.fulloo.trygve.parser.KantParser.Type_listContext;
import info.fulloo.trygve.parser.KantParser.While_exprContext;
import info.fulloo.trygve.semantic_analysis.Program;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticInterfaceScope;
import info.fulloo.trygve.add_ons.SetClass;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;



public class Pass1Listener extends Pass0Listener {
	public Pass1Listener(final ParsingData parsingData) {
		super(parsingData);
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
	
	@Override public void exitProgram(KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// | type_declaration_list
		
		final TypeDeclarationList currentList = parsingData_.popTypeDeclarationList();
		final TypeDeclarationList templateInstantiationList = parsingData_.currentTemplateInstantiationList();
		
		if (null == ctx.main()) {
			if (null != ctx.getStop()) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStop(), "Missing main expression.", "", "", "");
			} else {
				final CommonToken errorToken = new CommonToken(0);
				errorToken.setLine(1);
				errorHook5p2(ErrorIncidenceType.Fatal, errorToken, "Missing main expression.", " Did you enter any program at all?", "", "");
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

	@Override public void enterContext_declaration(KantParser.Context_declarationContext ctx)
	{
		// context_declaration : 'context' JAVA_ID (implements_list)* '{' context_body '}'
		final String name = ctx.JAVA_ID().getText();
		
		if (null != ctx.context_body()) {
			final ContextDeclaration oldContext = currentContext_;
			currentContext_ = this.lookupOrCreateContextDeclaration(name, ctx.getStart());
			currentContext_.setParentContext(oldContext);
			parsingData_.pushContextDeclaration(currentContext_);
		} else {
			assert false;
		}
	}
	
	@Override public void enterInterface_declaration(KantParser.Interface_declarationContext ctx)
	{
		// interface_declaration : 'interface' JAVA_ID '{' interface_body '}'
		//                       | 'interface' JAVA_ID type_parameters '{' interface_body '}'

		if (null != ctx.interface_body()) {
			String typeName = ctx.JAVA_ID().getText();
			if (null != ctx.type_parameters() && 0 < ctx.type_parameters().getText().length()) {
				final StaticScope globalScope = StaticScope.globalScope();
				final ClassDeclaration objectBaseClassDecl = globalScope.lookupClassDeclaration("Object");
				
				final Type objectBaseClassType = StaticScope.globalScope().lookupTypeDeclaration("Object");
				assert null != objectBaseClassType;
				assert objectBaseClassType instanceof ClassType;
				
				final TemplateDeclaration newTemplate = this.lookupOrCreateTemplateDeclaration(typeName, objectBaseClassDecl, objectBaseClassType, true, ctx.getStart());
				currentScope_ = newTemplate.enclosedScope();
				parsingData_.pushTemplateDeclaration(newTemplate);
			} else {
				currentInterface_ = this.lookupOrCreateInterfaceDeclaration(typeName, ctx.getStart());
				currentScope_ = currentInterface_.enclosedScope();
			}
		} else {
			assert false;
		}
	}
	
	@Override protected ContextDeclaration lookupOrCreateContextDeclaration(final String name, final Token token) {
		// Pass 1 - 4 version
		final ContextDeclaration contextDecl = currentScope_.lookupContextDeclarationRecursive(name);
		assert null != contextDecl;  // maybe turn into an error message later
		
		// TRIAL -seems to work
		contextDecl.enclosedScope().setParentScope(currentScope_);

		currentScope_ = contextDecl.enclosedScope();
		assert null != currentScope_;	// maybe turn into an error message later
		return contextDecl;
	}
	@Override protected TemplateDeclaration lookupOrCreateTemplateDeclaration(final String name, final TypeDeclaration rawBaseType, final Type baseType,
			final boolean isInterface, final Token token) {
		// Pass 1 - 3 version
		TemplateDeclaration newTemplate = currentScope_.lookupTemplateDeclarationRecursive(name);
		if (null == newTemplate) {
			assert false;	// something wrong
		}
		return newTemplate;
	}
	@Override protected InterfaceDeclaration lookupOrCreateInterfaceDeclaration(final String name, final Token token) {
		// Pass 1 - 4 version
		final InterfaceDeclaration newInterface = currentScope_.lookupInterfaceDeclarationRecursive(name);
		assert null != newInterface;
		return newInterface;
	}
	
	@Override protected ClassDeclaration lookupOrCreateClassDeclaration(final String name, final ClassDeclaration rawBaseClass, final ClassType baseType, final Token token) {
		assert null != currentScope_;
		ClassDeclaration newClass = currentScope_.lookupClassDeclaration(name);
		StaticScope classScope = null;
		if (null == newClass) {
			assert false;	// shouldn't be finding new classes in Pass 1...
			classScope = new StaticScope(currentScope_);
			newClass = this.lookupOrCreateNewClassDeclaration(name, classScope, rawBaseClass, token);
			assert null != rawBaseClass;
			final ClassType newClassType = new ClassType(name, classScope, (ClassType)rawBaseClass.type());

			currentScope_.declareType(newClassType);
			classScope.setDeclaration(newClass);
			newClass.setType(newClassType);
		} else {
			currentScope_.updateClassDeclaration(newClass);
			classScope = newClass.enclosedScope();
		}
		currentScope_ = classScope;
		return newClass;
	}
	
	private void insertDefaultCtor(final StaticScope scope, final TypeDeclaration decl) {
		// Let's make one
		final FormalParameterList parameterList = new FormalParameterList();
		final ObjectDeclaration selfDecl = new ObjectDeclaration("this", decl.type(), decl.token());
		parameterList.addFormalParameter(selfDecl);
		final StaticScope constructorScope = new StaticScope(scope);
		final MethodDeclaration newCtor = new MethodDeclaration(decl.name(),
				constructorScope, null, AccessQualifier.PublicAccess, decl.token(), false);
		constructorScope.setDeclaration(newCtor);
		newCtor.addParameterList(parameterList);
		scope.declareMethod(newCtor, this);
		final Expression returnStatement = new ReturnExpression(decl.name(),
				null, decl.token(), decl.type(), constructorScope);
		final ExprAndDeclList ctorBody = new ExprAndDeclList(decl.token());
		ctorBody.addBodyPart(returnStatement);
		newCtor.setBody(ctorBody);
	}
	
	private void checkNeedsCtor(final TypeDeclaration decl) {
		// All classes and Context should have a default constructor.
		// If someone invokes "new Foo()" we want to enact a constructor,
		// if for no other reason than to execute the in-situ initializations.
		
		assert decl instanceof ClassDeclaration || decl instanceof ContextDeclaration;
		final StaticScope scope = decl.enclosedScope();
		final ActualArgumentList argumentList = new ActualArgumentList();
		final Expression self = new IdentifierExpression("this", decl.type(), decl.enclosedScope(), decl.token());
		argumentList.addFirstActualParameter(self);
		final MethodDeclaration theConstructor = scope.lookupMethodDeclaration(decl.name(), argumentList, false);
		if (null == theConstructor) {
			// Let's make one
			insertDefaultCtor(scope, decl);
		}
	}
	
	private void exitType_declarationCommon() {
		// This is the Pass 1-4 version
		// type_declaration : context_declaration
		//                  | class_declaration
		//                  | interface_declaration
		
		// One version serves passes 1 - 4
		assert null != currentScope_;
		final Declaration rawNewDeclaration = currentScope_.associatedDeclaration();
		assert rawNewDeclaration instanceof TypeDeclaration;	
		final TypeDeclaration newDeclaration = (TypeDeclaration)rawNewDeclaration;

		final StaticScope newDeclarationParentScope = newDeclaration.enclosingScope();
		final Type environment = Expression.nearestEnclosingMegaTypeOf(newDeclarationParentScope);
		if (null == environment) {
			// Only declare it only if it's at most global scope.
			// Otherwise, it will be declared in the types of
			// the enclosing scope, and compilation will compile
			// it as a component of the outer one
			parsingData_.currentTypeDeclarationList().addDeclaration(newDeclaration);
		}
		
		if (newDeclaration instanceof ClassDeclaration || newDeclaration instanceof ContextDeclaration) {
			checkNeedsCtor(newDeclaration);
		}
		
		final StaticScope parentScope = currentScope_.parentScope();
		currentScope_ = parentScope;
		
		if (newDeclaration instanceof ClassDeclaration) {
			if (null != ((ClassDeclaration)newDeclaration).generatingTemplate()) {
				parsingData_.popTemplateDeclaration();
				assert false;	// this never seems to get invoked
			} else {
				parsingData_.popClassDeclaration();
				// implements_list is taken care of along the way
				assert true;
			}
		} else if (newDeclaration instanceof ContextDeclaration) {
			parsingData_.popContextDeclaration();
		} else if (newDeclaration instanceof TemplateDeclaration) {
			parsingData_.popTemplateDeclaration();
		}
		
		currentInterface_ = null;
		currentRoleOrStageProp_ = null;
	}
		
	@Override public void exitType_declaration(KantParser.Type_declarationContext ctx)
	{
		// type_declaration : context_declaration
		//                  | class_declaration
		//                  | interface_declaration
		
		if (printProductionsDebug) {
			if (null != ctx.context_declaration()) {
				System.err.println("type_declaration : context_declaration");
			} else if (null != ctx.class_declaration()) {
				System.err.println("type_declaration : class_declaration");
			} else if (null != ctx.interface_declaration()) {
				System.err.println("type_declaration : interface_declaration");
			} else {
				assert false;
			}
		}
	}
	
	@Override public void exitContext_declaration(KantParser.Context_declarationContext ctx)
	{
		// : 'context' JAVA_ID '{' context_body '}'
		
		exitType_declarationCommon();
		
		this.implementsCheck((ClassOrContextDeclaration)currentContext_.type().enclosedScope().associatedDeclaration(), ctx.getStart());
		
		if (null != currentContext_) {
			currentContext_ = currentContext_.parentContext();
		}
				
		if (printProductionsDebug) {
			System.err.println("context_declaration : 'context' JAVA_ID '{' context_body '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterClass_declaration(KantParser.Class_declarationContext ctx)
	{
		// class_declaration : 'class'   JAVA_ID type_parameters (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		
		final Type objectBaseClass = StaticScope.globalScope().lookupTypeDeclaration("Object");
		assert null != objectBaseClass;
		assert objectBaseClass instanceof ClassType;
		ClassType baseType = (ClassType)objectBaseClass;
		
		final String name = ctx.JAVA_ID(0).getText();
		ClassDeclaration rawBaseClass = null;
		
		 if (null != ctx.class_body()) {
			final TerminalNode baseClassNode = ctx.JAVA_ID(1);
			
			if (null != baseClassNode) {
				final String baseTypeName = baseClassNode.getText();
				final Type rawBaseType = currentScope_.lookupTypeDeclarationRecursive(baseTypeName);
				rawBaseClass = currentScope_.lookupClassDeclarationRecursive(baseTypeName);
				if ((rawBaseType instanceof ClassType) == false) {
					// Leave to pass 2
					errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Base type `", baseTypeName,
							"' is not a declared class type as base of `", name, "'.", "");
				} else {
					baseType = (ClassType)rawBaseType;
					if (baseType.name().equals(name)) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Er, no.", "", "", "");
					}
				}
			} else {
				// Redundant: for readability...
				rawBaseClass = (ClassDeclaration)objectBaseClass.enclosedScope().associatedDeclaration();
			}
			
			if (null != ctx.type_parameters()) {
				final TemplateDeclaration newTemplate = this.lookupOrCreateTemplateDeclaration(name, rawBaseClass, baseType, false, ctx.getStart());
				currentScope_ = newTemplate.enclosedScope();
				parsingData_.pushTemplateDeclaration(newTemplate);
			} else {
				final ClassDeclaration newClass = this.lookupOrCreateClassDeclaration(name, rawBaseClass, baseType, ctx.getStart());
				currentScope_ = newClass.enclosedScope();
				parsingData_.pushClassDeclaration(newClass);
			}
		} else {
			assert false;
		}
	}
	
	@Override public void exitClass_declaration(KantParser.Class_declarationContext ctx)
	{
		// class_declaration : 'class'   JAVA_ID type_parameters (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		//                   | 'class'   JAVA_ID (implements_list)* 'extends' JAVA_ID '{' class_body '}'
		
		final Declaration rawNewDeclaration = currentScope_.associatedDeclaration();
		assert rawNewDeclaration instanceof TypeDeclaration;	
		final TypeDeclaration newDeclaration = (TypeDeclaration)rawNewDeclaration;
		
		if (newDeclaration instanceof ClassDeclaration || newDeclaration instanceof ContextDeclaration) {
			// (Could be a template, in which case we skip it)
			this.implementsCheck((ClassOrContextDeclaration)newDeclaration, ctx.getStart());
		}
		
		exitType_declarationCommon();
		
		if (printProductionsDebug) {
			if (null != ctx.type_parameters() && null != ctx.class_body() && null == ctx.JAVA_ID(1)) {
				System.err.println("class_declaration : 'class' JAVA_ID type_parameters (implements_list)* '{' class_body '}'");
			} else if (null != ctx.type_parameters() && null != ctx.class_body() && null != ctx.JAVA_ID(1)) {
				System.err.println("class_declaration : JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'");
			} else if (null == ctx.type_parameters() && null != ctx.class_body() && null == ctx.JAVA_ID(1)) {
				System.err.println("class_declaration : JAVA_ID (implements_list)* '{' class_body '}'");
			} else if (null == ctx.type_parameters() && null != ctx.class_body() && null != ctx.JAVA_ID(1)) {
				System.err.println("class_declaration : JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'");
			} else {
				assert false;
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitInterface_declaration(KantParser.Interface_declarationContext ctx)
	{
		// interface_declaration : 'interface' JAVA_ID '{' interface_body '}'
		
		exitType_declarationCommon();
		
		if (printProductionsDebug) {
			System.err.println("interface_declaration : 'interface' JAVA_ID '{' interface_body   '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override protected void implementsCheck(final ClassOrContextDeclaration newDeclaration, final Token token) {
		// nothing on pass one
	}
	
	private String buildFullTypeName(KantParser.Implements_listContext ctx, final List<String> typeArguments) {
		ParseTree thing = null;
		String fullInstantiatedTypeName = ctx.JAVA_ID().getText();
		switch (ctx.getChildCount()) {
		case 3:
			thing = ctx.getChild(2);
			break;
		case 4:
			thing = ctx.getChild(3);
			break;
		}
		
		if (thing instanceof Type_listContext) {
			fullInstantiatedTypeName += "<";
			for (int i = 0 + 1; i < thing.getChildCount() - 1; i++) {
				final String typeParameter = thing.getChild(i).getText();
				fullInstantiatedTypeName += typeParameter;
				typeArguments.add(typeParameter);
				if (i < thing.getChildCount() - 1 - 1) {
					fullInstantiatedTypeName += ",";
				}
			}
			fullInstantiatedTypeName += ">";
		}
		return fullInstantiatedTypeName;
	}
	
	@Override public void enterImplements_list(KantParser.Implements_listContext ctx) {
		// : 'implements' JAVA_ID
		// : 'implements' JAVA_ID type_list
		// | implements_list ',' JAVA_ID
		// | implements_list ',' JAVA_ID type_list

		final List<String> typeNameList = new ArrayList<String>();
		
		/* (void) */ this.buildFullTypeName(ctx, typeNameList);

		if (0 < typeNameList.size()) {
			// Create the new interface and put it in the symbol table
			final Type type = commonTemplateInstantiationHandling(ctx.JAVA_ID().toString(), ctx.getStart(),
					typeNameList);
			if (null == type) {
				errorHook5p2(ErrorIncidenceType.Internal, ctx.getStart(),
						"No Type returned from commonTemplateInstantiationHandling: ",
						"instantiating a type in an expression: `",
						ctx.getText(),
						"'."
						);
			}
		}
	}
	
	@Override public void exitImplements_list(KantParser.Implements_listContext ctx) {
		// : 'implements' JAVA_ID
		// : 'implements' JAVA_ID implements_list
		// | implements_list ',' JAVA_ID
		// | implements_list ',' JAVA_ID implements_list
		
		String interfaceName = ctx.JAVA_ID().getText();
		InterfaceDeclaration anInterface = currentScope_.lookupInterfaceDeclarationRecursive(interfaceName);
		
		if (null == anInterface) {
			final List<String> typeArguments = new ArrayList<String>();
			interfaceName = this.buildFullTypeName(ctx, typeArguments);
			anInterface = currentScope_.lookupInterfaceDeclarationRecursive(interfaceName);
			
			if (null == anInterface) {
				errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"Interface `", interfaceName, "' is not declared.", "", "", "");
				anInterface = new InterfaceDeclaration(" error", null, ctx.getStart());
			}
		}
		
		final ClassOrContextType classOrContextType = (ClassOrContextType)parsingData_.currentClassOrContextDeclaration().type();
		this.addInterfaceTypeSuitableToPass(classOrContextType, (InterfaceType)anInterface.type());
		
		if (printProductionsDebug) {
			if (ctx.implements_list() != null) {
				System.err.println("implements_list : implements_list ',' JAVA_ID");
			} else {
				System.err.println("implements_list : JAVA_ID");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	protected void addInterfaceTypeSuitableToPass(final ClassOrContextType classType, final InterfaceType interfaceType) {
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
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"Duplicate template parameter name: ", type_name.name(), "", "");
				currentTemplateDecl.addTypeParameter(new IdentifierExpression("$error$",
						new ErrorType(), currentScope_, ctx.getStart()),
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
		final Expression type_name = new IdentifierExpression(ctx.type_name(0).getText(), type, scope, ctx.getStart());
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
		super.enterRole_decl(ctx);
	}
	
	@Override public void exitRole_decl(KantParser.Role_declContext ctx)
	{
		super.exitRole_decl(ctx);
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isRoleArray = vecText.length() > 0;	// "[]"
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
	
	@Override protected void lookupOrCreateRoleDeclaration(final String roleName, final Token token, final boolean isRoleArray) {
		final RoleDeclaration requestedRole = currentScope_.lookupRoleOrStagePropDeclaration(roleName);
		if (null != requestedRole) {
			currentRoleOrStageProp_ = requestedRole;
			
			// The way parsing is designed, these things should
			// be defined once on pass 0 and then referenced only
			// on subsequent passes.
		}
		// caller may reset currentScope - NOT us
	}
	

	@Override public void enterRole_body(KantParser.Role_bodyContext ctx)
	{
		// role_body
	    //	: method_decl
	    //	| role_body method_decl
	    //	| object_decl				// illegal
	    //	| role_body object_decl		// illegal - for better error messages only
		//	| method_signature ';'*
		//	| role_body method_signature ';'*
		//  | /* null */
		
		if (null != ctx.method_signature()) {
			final FormalParameterList formalParameterList = new FormalParameterList();
			parsingData_.pushFormalParameterList(formalParameterList);
		}
	}
	
	@Override public void exitRole_body(KantParser.Role_bodyContext ctx)
	{
		// : method_decl
        // | role_body method_decl
        // | object_decl				// illegal
        // | role_body object_decl		// illegal - for better error messages only
		// | method_signature ';'*
		// | role_body method_signature ';'*
		// | /* null */
		
		if (null != ctx.object_decl()) {
			@SuppressWarnings("unused")
			final DeclarationList objectDecl = parsingData_.popDeclarationList();
			// We have issued an error message about this already elsewhere
		}
		
		if (null != ctx.method_signature()) {
			final MethodSignature signature = parsingData_.popMethodSignature();
			final FormalParameterList plInProgress = parsingData_.popFormalParameterList();
		
			// Add a declaration of "this." These are class instance methods, never
			// role methods, so there is no need to add a current$context argument
			final ObjectDeclaration self = new ObjectDeclaration("this", currentRoleOrStageProp_.type(), ctx.getStart());
			plInProgress.addFormalParameter(self);
		
			signature.addParameterList(plInProgress);
			currentRoleOrStageProp_.addPublishedSignature(signature);
			
			// It should also exist in the "requires" section!
			final MethodSignature requiresSignature = currentRoleOrStageProp_.lookupRequiredMethodSignatureDeclaration(signature.name());
			if (null == requiresSignature) {
				// Silent error on pass1; O.K. to gripe on pass 2
				errorHook6p2(ErrorIncidenceType.Fatal, signature.token(),
						"Published signature for `",
						signature.getText(),
						"' must also be in `requires' section.",
						"", "", "");
			} else {
				if (requiresSignature.formalParameterList().alignsWith(plInProgress) == false) {
					errorHook6p2(ErrorIncidenceType.Fatal, signature.token(),
							"Published signature for `",
							signature.getText(),
							"' doesn't match that in the `requires' section (line ",
							String.format("%d", requiresSignature.token().getLine()), ").", "");
				}
			}
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
			final ObjectDeclaration self = new ObjectDeclaration("this", currentRoleOrStageProp_.type(), ctx.getStart());
			plInProgress.addFormalParameter(self);
		
			signature.addParameterList(plInProgress);
			currentRoleOrStageProp_.addRequiredSignatureOnSelf(signature, this);
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
		//
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isStagePropArray = vecText.length() > 0;	// "[]"
		
		if (null != ctx.access_qualifier()) {
			final String accessQualifier = ctx.access_qualifier().getText();
			if (0 != accessQualifier.length()) {
				errorHook5p1(ErrorIncidenceType.Warning, ctx.getStart(), "WARNING: Gratuitous access qualifier `",
						ctx.access_qualifier().getText(), "' ignored", ".");
			}
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role - resulting grammar error got here with that
			// null condition. Not much to do but to punt
			
			final String stagePropName = JAVA_ID.getText();
			lookupOrCreateStagePropDeclaration(stagePropName, ctx.getStart(), isStagePropArray);
			
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorIncidenceType.Fatal, ctx.getStart(), "Stageprop ", stagePropName, " can be declared only in a Context scope - not ", currentScope_.name());
			}
			currentScope_ = currentRoleOrStageProp_.enclosedScope();
		} else {
			currentRoleOrStageProp_ = null;
		}
	}

	@Override public void exitStageprop_decl(KantParser.Stageprop_declContext ctx)
	{
		// stageprop_decl
		// : 'stageprop' role_vec_modifier JAVA_ID '{' role_body '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' role_body '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' '}'
		// | access_qualifier 'stageprop' JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isStagePropArray = vecText.length() > 0;	// "[]"
		
		if (null != currentRoleOrStageProp_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See comment
			// elsewhere (in exitRole_decl?)
			final Type rawType = currentRoleOrStageProp_.type();
			assert rawType instanceof StagePropType;
			final StagePropType type = (StagePropType)rawType;
			type.setBacklinkToRoleDecl(currentRoleOrStageProp_);
			
			// Make sure self_methods are const
			if (currentRoleOrStageProp_.requiresConstMethods()) {
				final Map<String, List<MethodSignature>> requiredSelfSignatures = currentRoleOrStageProp_.requiredSelfSignatures();
				for (Map.Entry<String, List<MethodSignature>> iter : requiredSelfSignatures.entrySet()) {
					final String methodName = iter.getKey();
					final List<MethodSignature> signatures = iter.getValue();
					for (final MethodSignature signature : signatures) {
						if (false == signature.hasConstModifier() &&
								false == signature.isUnusedInThisContext()) {
							// If the published version has its unused flag set, we're Ok
							final MethodSignature publishedSignature = currentRoleOrStageProp_.lookupPublishedSignatureDeclaration(signature);
							if (null != publishedSignature && publishedSignature.isUnusedInThisContext() == true) {
								;	// an "unused" flag is as good as a const
							} else {
								errorHook6p2(ErrorIncidenceType.Warning, ctx.getStart(),
										"WARNING: Signatures for functions required by stageprops like `", currentRoleOrStageProp_.name(),
										"' should have a const modifier: method `", methodName, "' does not.", "");
							}
						}
					}
				}
			}

			currentRoleOrStageProp_ = null;
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
		//  | object_decl
		//  | stageprop_body object_decl
		//	| method_signature ';'*
		//	| role_body method_signature ';'*
		//	| method_signature UNUSED ';'*
		//	| role_body method_signature UNUSED ';'*
	    
		if (null != ctx.method_signature()) {
			final FormalParameterList formalParameterList = new FormalParameterList();
			parsingData_.pushFormalParameterList(formalParameterList);
		}
	}
	
	@Override public void exitStageprop_body(KantParser.Stageprop_bodyContext ctx) {
		// stageprop_body
	    //	: method_decl
	    //	| stageprop_body method_decl
		//  | object_decl
		//  | stageprop_body object_decl
		//	| method_signature ';'*
		//	| role_body method_signature ';'*
		//	| method_signature UNUSED ';'*
		//	| role_body method_signature UNUSED ';'*
		
		if (null != ctx.object_decl()) {
			@SuppressWarnings("unused")
			final DeclarationList objectDecl = parsingData_.popDeclarationList();
			// We have issued an error message about this already elsewhere
		}
		
		final boolean unusedFlag = null != ctx.UNUSED();
		
		if (null != ctx.method_signature()) {
			final MethodSignature signature = parsingData_.popMethodSignature();
			signature.setUnused(unusedFlag);
			final FormalParameterList plInProgress = parsingData_.popFormalParameterList();
		
			// Add a declaration of "this." These are class instance methods, never
			// role methods, so there is no need to add a current$context argument
			final ObjectDeclaration self = new ObjectDeclaration("this", currentRoleOrStageProp_.type(), ctx.getStart());
			plInProgress.addFormalParameter(self);
		
			signature.addParameterList(plInProgress);
			currentRoleOrStageProp_.addPublishedSignature(signature);
			
			// It should also exist in the "requires" section!
			final MethodSignature requiresSignature = currentRoleOrStageProp_.lookupRequiredMethodSignatureDeclaration(signature.name());
			if (null == requiresSignature) {
				// Silent error on pass1; O.K. to gripe on pass 2
				errorHook6p2(ErrorIncidenceType.Fatal, signature.token(),
						"Published signature for `",
						signature.getText(),
						"' must also be in `requires' section.",
						"", "", "");
			} else {
				if (requiresSignature.formalParameterList().alignsWith(plInProgress) == false) {
					errorHook6p2(ErrorIncidenceType.Fatal, signature.token(),
							"Published signature for `",
							signature.getText(),
							"' doesn't match that in the `requires' section (line ",
							String.format("%d", requiresSignature.token()), ").", "");
				}
			}
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
		//  | type_declaration
	    
		/* nothing */
		
		if (printProductionsDebug) {
			if (null != ctx.method_decl()) {
				System.err.println("class_body_element : method_decl");
			} else if (null != ctx.object_decl()) {
				System.err.println("class_body_element : object_decl");
			} else if (null != ctx.type_declaration()) {
				System.err.println("class_body_element : type_declaration");
			} else {
				assert false;
			}
		}
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
			final Declaration associatedDeclaration = currentScope_.associatedDeclaration();
			if (null == associatedDeclaration) {
				assert null != associatedDeclaration;
			}
			final Type classOrRoleOrContextType = associatedDeclaration.type();
		
			// Add a declaration of "this." These are class instance methods, never
			// role methods, so there is no need to add a current$context argument
			final ObjectDeclaration self = new ObjectDeclaration("this", classOrRoleOrContextType, ctx.getStart());
			plInProgress.addFormalParameter(self);
		
			signature.addParameterList(plInProgress);
			
			// This is a kludge. Has to be a better way. FIXME.
			if (null != currentInterface_) {
				currentInterface_.addSignature(signature);
				
				// Add it to type, too
				final InterfaceType interfaceType = (InterfaceType)currentInterface_.type();
				this.addSignatureSuitableToPass(interfaceType, signature);
			} else {
				final String name = classOrRoleOrContextType.name();
				final TemplateDeclaration newTemplate = currentScope_.lookupTemplateDeclarationRecursive(name);
				this.addTemplateSignatureSuitableToPass((TemplateType)newTemplate.type(), signature);
			}	
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
	protected void addTemplateSignatureSuitableToPass(final TemplateType interfaceType, final MethodSignature signature) {
		// nothing in pass 1
	}

	@Override public void enterMethod_decl(KantParser.Method_declContext ctx)
	{
		// : method_decl_hook '{' expr_and_decl_list '}'
		
		// This one seems different enough that we'll have our own pass 2 version.
		// In Pass 1, get just enough to define the scope for the enclosed
		// declarations.
		
		// Set up the block
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart());
		parsingData_.pushExprAndDecl(newList);

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
				accessQualifier, ctx.getStart(), false);
		currentMethod.addParameterList(pl);
		
		newScope.setDeclaration(currentMethod);
		
		currentScope_.declareMethod(currentMethod, this);
		currentScope_ = newScope;
		
		parsingData_.pushFormalParameterList(pl);
	}
	
	@Override public void exitMethod_decl_hook(KantParser.Method_decl_hookContext ctx)
	{
		// method_decl_hook
		//	: method_signature
		final Declaration associatedDeclaration = currentScope_.parentScope().associatedDeclaration();
		if (null == associatedDeclaration) {
			assert null != associatedDeclaration;
		}
		final Type classOrRoleOrContextType = associatedDeclaration.type();
		
		boolean isRoleMethodInvocation = classOrRoleOrContextType instanceof RoleType;
		
		if (classOrRoleOrContextType instanceof ClassType || 
				isRoleMethodInvocation ||
				classOrRoleOrContextType instanceof ContextType ||
				classOrRoleOrContextType instanceof TemplateType) {
			;
		} else {
			assert  classOrRoleOrContextType instanceof ClassType || 
					isRoleMethodInvocation ||
					classOrRoleOrContextType instanceof ContextType ||
					classOrRoleOrContextType instanceof TemplateType;
		}
		
		// Add declaration of "this" as a formal parameter
		final ObjectDeclaration self = new ObjectDeclaration("this", classOrRoleOrContextType, ctx.getStart());
		parsingData_.currentFormalParameterList().addFormalParameter(self);
		
		if (isRoleMethodInvocation) {
			// Add declaration of "current$context" as a formal parameter,
			// right next to "this"
			Type contextType = classOrRoleOrContextType;
			if (contextType instanceof RoleType) {
				final StaticScope scope = contextType.enclosedScope();
				contextType = Expression.nearestEnclosingMegaTypeOf(scope.parentScope());
			}
			final ObjectDeclaration currentContext = new ObjectDeclaration("current$context", contextType, ctx.getStart());
			parsingData_.currentFormalParameterList().addFormalParameter(currentContext);
		}
		
		final boolean isAConstructor = classOrRoleOrContextType.name().equals(parsingData_.currentMethodSignature().name());
		if (classOrRoleOrContextType instanceof ContextType && false == isAConstructor) {
			// Issue 65. See if any of the parameters is a Role type
			for (int i = 0; i < parsingData_.currentFormalParameterList().count(); i++) {
				final Declaration parameterDeclaration = parsingData_.currentFormalParameterList().parameterAtPosition(i);
				if (parameterDeclaration.type() instanceof RoleType) {
					errorHook6p2(ErrorIncidenceType.Warning, ctx.getStart(),
							"WARNING: Declaring a Role parameter `",
							parameterDeclaration.name(), "' for Context script `",
							parsingData_.currentMethodSignature().name(),
							String.format("' is unorthodox. The script should directly access the Role `%s' instead.",
									parameterDeclaration.type().name()),
							"");
				}
			}
		}
		
		for (int i = 0; i <  parsingData_.currentFormalParameterList().count(); i++) {
			final Declaration objectDeclaration = parsingData_.currentFormalParameterList().parameterAtPosition(i);
			
			// Nothing on Pass 1
			// Processed on Pass 2
			// Functionality duplicated on Passes 3 & 4
			if (objectDeclaration instanceof ObjectDeclaration) {
				declareFormalParametersSuitableToPass(currentScope_, (ObjectDeclaration)objectDeclaration);
			}
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
		// : method_decl_hook '{' type_and_expr_and_decl_list '}'
		
		// Declare parameters in the new scope
		// This is definitely a Pass2 thing, so there is a special Pass 2 version
		
		assert currentScope_.associatedDeclaration() instanceof MethodDeclaration;
		
		final MethodDeclaration currentMethod = (MethodDeclaration)currentScope_.associatedDeclaration();
		assert currentMethod instanceof MethodDeclaration;
		
		final MethodSignature sig = parsingData_.currentMethodSignature();
		final Type returnType = sig.returnType();
		currentMethod.setReturnType(returnType);
		
		final StaticScope parentScope = currentScope_.parentScope();
		currentScope_ = parentScope;
		
		// Keep the stack clean
		final MethodSignature methodSignatureInProgress = parsingData_.popMethodSignature();
		currentMethod.setHasConstModifier(methodSignatureInProgress.hasConstModifier());
		
		parsingData_.popFormalParameterList();	// hope this is the right place
		parsingData_.popExprAndDecl();  // Move to Context, Role, Class, StageProp productions???
		
		if (printProductionsDebug) {
			System.err.println("method_decl : method_decl_hook '{' type_and_expr_and_decl_list '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	private Type customStringToType(final String returnTypeName) {
		// This mainly turns both String and String[] into
		// reasonable types
		Type returnType = null;
		final int lastRightBracket = returnTypeName.trim().lastIndexOf(']');
		if (lastRightBracket == returnTypeName.length()-1) {
			// It is an array. Get the base type
			final int lastLeftBracket = returnTypeName.lastIndexOf('[');
			if (lastLeftBracket != -1) {
				final String baseType = returnTypeName.substring(0, lastLeftBracket).trim();
				returnType = currentScope_.lookupTypeDeclarationRecursive(baseType);
				if (null != returnType) {
					returnType = new ArrayType(baseType + "_$array", returnType);
				}
			} else {
				// This probably never works, and is probably some kind
				// of syntax error. Hope it pops up somewhere
				returnType = currentScope_.lookupTypeDeclarationRecursive(returnTypeName);
			}
		} else {
			returnType = currentScope_.lookupTypeDeclarationRecursive(returnTypeName);
			// null is O.K. as a return type!
		}
		return returnType;
	}

	@Override public void enterMethod_signature(KantParser.Method_signatureContext ctx)
	{
		// : access_qualifier return_type method_name '(' param_list ')' CONST*
		// | access_qualifier return_type method_name CONST*
		// | access_qualifier method_name '(' param_list ')' CONST*
		
		final String name = ctx.method_name().getText();
		String returnTypeName = "";
		final String accessQualifierString = null != ctx.access_qualifier()? ctx.access_qualifier().getText(): "";
		final AccessQualifier accessQualifier = AccessQualifier.accessQualifierFromString(accessQualifierString);
		
		Type returnType = null;
		// There may not be any return type at all - as for a constructor
		final KantParser.Return_typeContext returnTypeContext = ctx.return_type();
		if (null != returnTypeContext) {
			returnTypeName = returnTypeContext.getText();
			returnType = customStringToType(returnTypeName);
		}
		
		final MethodSignature currentMethod = new MethodSignature(name, returnType, accessQualifier, ctx.getStart(), false);

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
		
		// Update return type (e.g., if it is a template, we will now
		// have more information)
		final KantParser.Return_typeContext returnTypeContext = ctx.return_type();
		if (null != returnTypeContext) {
			final String returnTypeName = returnTypeContext.getText();
			
			// final Type updatedReturnType = currentScope_.lookupTypeDeclarationRecursive(returnTypeName);
			final Type updatedReturnType = customStringToType(returnTypeName);
			final MethodSignature currentMethodSignature = parsingData_.currentMethodSignature();
			assert null != currentMethodSignature;
			currentMethodSignature.setReturnType(updatedReturnType);
			// null is NOT O.K. here as a return type, I think... Or can it be a ctor?
		}
		
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
		// "Tail-recursive parsing" causes this loop to visit the
		// initializations in right-to-left order. Cachec them
		// away in a list and then add them backwards. The result
		// is that multiple initializations within a declaration will
		// be evaluated left-to-right.
		final ExprAndDeclList currentExprAndDecl = parsingData_.currentExprAndDecl();
		Stack<Expression> initializationExprs = new Stack<Expression>();
		Expression initializationExpression = null;
		for (final BodyPart bp : object_decls.declarations()) {
			if (bp instanceof ObjectDeclaration) {
				final ObjectDeclaration odecl = (ObjectDeclaration)bp;
				initializationExpression = odecl.initializationExpression();
				if (null != initializationExpression) {
					assert initializationExpression instanceof AssignmentExpression;
					initializationExprs.push(initializationExpression);
				}
			}
		}
		while (initializationExprs.size() > 0) {
			initializationExpression = initializationExprs.pop();
			currentExprAndDecl.addBodyPart(initializationExpression);
		}
	}
	
	@Override public void exitType_and_expr_and_decl_list(KantParser.Type_and_expr_and_decl_listContext ctx)
	{
		// type_and_expr_and_decl_list : expr_and_decl_list
 		//                             | expr_and_decl_list type_declaration
		//                             | type_declaration expr_and_decl_list

		;	// nothing
		
		if (printProductionsDebug) {
			if (null != ctx.type_declaration()) {
				if (ctx.type_declaration().start.getStartIndex() < ctx.expr_and_decl_list().start.getStartIndex()) {
					System.err.println("type_expr_and_decl_list : type_declaration expr_and_decl_list");
				} else {
					System.err.println("type_expr_and_decl_list : expr_and_decl_list type_declaration");
				}
			} else  {
				System.err.println("type_expr_and_decl_list : expr_and_decl_list");
			}
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
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
		if (null != ctx.expr() && parsingData_.currentExpressionExists()) {
			expr = parsingData_.popExpression();
		}
		if (null != ctx.object_decl()) {
			// stumbling check
			if (parsingData_.currentDeclarationListExists()) {
				object_decl = parsingData_.popDeclarationList();
			} else {
				object_decl = null;
			}
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
        //		: type_name '[' ']'
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
		//  | ABELIAN_MULOP
		//  | ABELIAN_SUMOP

		/* nothing — it's pure text, so we just do text processing */
		/* in the productions that depend on this one. */
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

		final Token token = ctx.getStart();
		
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
			errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Stage props are stateless, so the declaration of objects of type `",
					typeName, "' in `",
					associatedDeclaration.name(), "' are not allowed.", "");
			declaredObjectDeclarations = new ArrayList<ObjectDeclaration>();	// empty list just to keep things happy
		} else if (associatedDeclaration instanceof RoleDeclaration) {
			errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Roles are stateless, so the declaration of objects of type `",
					typeName, "' in `",
					associatedDeclaration.name(), "' are not allowed.", "");
			declaredObjectDeclarations = new ArrayList<ObjectDeclaration>();	// empty list just to keep things happy
		} else {
			Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			
			if (null != type && isArray) {
				// A derived type
				final String aName = type.getText() + "_$array";
				type = new ArrayType(aName, type);
			}
			
			if (null == type) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Type `", typeName, "' undefined for declaration.", "");
				
				// Put in some reasonable type to avoid stumbling
				type = new ErrorType();
			}
						
			final Identifier_listContext identifier_list = ctx.identifier_list();
			final DeclarationsAndInitializers idInfo = this.processIdentifierList(identifier_list, type, token, accessQualifier);
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
				// There may not even be a currentExprAndDecl... We used to presume that initializations
				// occurred where there were ExprAndDecl blocks in play. We changed this because we now
				// allow inline initialization of object members in the class syntax. Now we must handle
				// the initialisations in the constructor. Queue them up to the declaration if so.
				
				// In any case, errors can cause currentExprAndDecl() to be empty, so we
				// need to bail out accordingly
				
				if (parsingData_.currentExprAndDeclExists()) {
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
				} else if (currentScope_.associatedDeclaration() instanceof ClassDeclaration ||
						currentScope_.associatedDeclaration() instanceof ContextDeclaration) {
					// Then this is an in situ initialization - i.e., someone is putting the
					// initialization with the declaration of an instance object instead of
					// initializing it in the constructor

					final ObjectSubclassDeclaration declaration = (ObjectSubclassDeclaration)currentScope_.associatedDeclaration();
					declaration.addInSituInitializers(intializationExprs);
				} else {
					final DeclarationList declarationList = new DeclarationList(ctx.getStart());
					declarationList.addDeclaration(new ErrorDeclaration(null));
					parsingData_.pushDeclarationList(declarationList);
					return;	// punt - error return
				}
			}
			
			for (final ObjectDeclaration aDecl : declaredObjectDeclarations) {
				this.nameCheck(aDecl.name(), ctx.getStart());
			}
		}

		// Package all the stuff in declaredObjectDeclarations into an ExprAndDeclList
		// and push it onto the ExprAndDecl stack.
		final DeclarationList declarationList = new DeclarationList(ctx.getStart());
		
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
	
	protected Type commonTemplateInstantiationHandling(final String templateName, final Token token, final List<String> typeNameList) {
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
		final Type type = this.lookupOrCreateTemplateInstantiation(templateName, typeNameList, token);
		return type;
	}
	
	@Override public void exitBuiltin_type_name(KantParser.Builtin_type_nameContext ctx)
	{
		if (printProductionsDebug) {
			System.err.print("builtin_type_name : ('");
			System.err.print(ctx.getText());
			System.err.println("')");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void exitType_name(KantParser.Type_nameContext ctx)
	{
		// type_name
	    //	: JAVA_ID
		//  | JAVA_ID type_list
	    //	| builtin_type_name
		
		// All four passes
	
		Type type = null;
		String typeName = null;
		if (null != ctx.JAVA_ID() && null == ctx.type_list()) {
			//  | type_name : JAVA_ID
			typeName = ctx.JAVA_ID().getText();
	
			type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
		} else if (null == ctx.JAVA_ID() && null == ctx.type_list()) {
			//  | type_name : builtin_type_name
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
			type = this.commonTemplateInstantiationHandling(ctx.JAVA_ID().getText(), ctx.getStart(), typeNameList);
			this.updateTypesAccordingToPass(type, typeNameList);
		} else {
			assert false;
		}
		
		if (null != type) {
			// error stumbling null check
			parsingData_.pushExpression(type);
		} else {
			parsingData_.pushExpression(new ErrorExpression(null));
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
	
	protected void updateTypesAccordingToPass(final Type type, final List<String> typeNameList) {
		// nothing
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
		final Token token = ctx.getStart();
		
		if (null != ctx.abelian_expr()) {
			if (parsingData_.currentExpressionExists()) {
				// Error stumbling (undefined method)
				expression = parsingData_.popExpression();
			} else {
				expression = new ErrorExpression(null);
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
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
			} else {
				expression = new ErrorExpression(null);
			}
			if (printProductionsDebug) { System.err.println("expr : switch_expr"); }
		} else if (null != ctx.BREAK()) {
			final long nestingLevelInsideBreakable = nestingLevelInsideBreakable(ctx.BREAK());
			Expression currentBreakableExpression = null;
			if (parsingData_.currentBreakableExpressionExists()) {
				currentBreakableExpression = parsingData_.currentBreakableExpression();
			}
			if (null == currentBreakableExpression) {
				errorHook5p2(ErrorIncidenceType.Fatal, token, "There is no switch or loop statement to break", "", "", "");
			} else if (nestingLevelInsideBreakable == -1) {
				errorHook5p2(ErrorIncidenceType.Fatal, token, "The break statement is not in the scope of any switch or loop statement", "", "", "");
			}
			if (null != currentBreakableExpression) {
				expression = new BreakExpression(token, currentBreakableExpression, nestingLevelInsideBreakable);
			} else {
				expression = new ErrorExpression(null);
			}
			if (printProductionsDebug) { System.err.print("expr : BREAK (nesting level is "); System.err.print(nestingLevelInsideBreakable); System.err.println(")"); }
		} else if (null != ctx.CONTINUE()) {
			final long nestingLevelInsideBreakable = nestingLevelInsideBreakableForContinue(ctx.CONTINUE());
			final Expression currentContinuableExpression = parsingData_.nearestContinuableLoop();
			assert currentContinuableExpression instanceof SwitchExpression == false;
			if (null == currentContinuableExpression) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "There is no loop statement to continue", "", "", "");
			} else if (nestingLevelInsideBreakable == -1) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "The continue statement is not in the scope of any loop statement", "", "", "");
			}
			expression = new ContinueExpression(token, currentContinuableExpression, nestingLevelInsideBreakable);
			if (printProductionsDebug) { System.err.println("expr : CONTINUE"); }
		} else if (null != ctx.RETURN()) {
			expression = processReturnExpression(ctx);
		} else {
			// Could be a parsing error
			expression = new ErrorExpression(null);
			if (printProductionsDebug) { System.err.println("expr : <internal error>"); }
		}

		parsingData_.pushExpression(expression);
		
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	private Expression processReturnExpression(KantParser.ExprContext ctx) {
		// The expression being returned is popped from
		// parsingData_.popExpression() in this.expressionFromReturnStatement
		// It may be null.
		
		final ExprContext exprContext = ctx.expr();
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		Expression expression = this.expressionFromReturnStatement(ctx.expr(), ctx.getParent(), ctx.getStart());
		
		final MethodSignature currentMethod = parsingData_.currentMethodSignature();
		final String methodName = null == currentMethod? "<null>": currentMethod.name();
		
		if (null != exprContext && null != expression) {
			expression.setResultIsConsumed(true);	// consumed by the return statement
		
			if (null != expression && this.getClass().getSimpleName().equals("Pass1Listener") == false) {
				if (null == expression.type()) {
					assert null != expression.type();
				}
				assert null != expression.type().pathName();
				if (expression.type().pathName().equals("void")) {
					assert true;	// tests/chord_identifier7.k
					expression = new TopOfStackExpression();
				}
			}
			
			// Now, speaking of the return statement... Methods stick one in at
			// the end for free. But we have an explicit return here and it
			// may not be at the end. If it is, then there may just be extra
			// redundant return code. But watch for scope management stuff.
			
			if (null != nearestEnclosingMegaType) {		// error stumbling
				if (expression instanceof ReturnExpression) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
							"You may not return another `return' expression.", "", "", "");
					expression = new ErrorExpression(expression);
				} else {
					// Check to make sure it is of the right type
					final Type methodReturnType = currentMethod.returnType();
					
					assert null != expression;
					final Type expressionType = expression.type();
					if (null != methodReturnType && null != expressionType) {
						if (methodReturnType.pathName().equals(expressionType.pathName())) {
							;  // we're cool
						} else if (expressionType.pathName().equals("Null")) {
							;	// Null converts to anything
						} else if (methodReturnType.canBeConvertedFrom(expressionType)) {
							// We're almost cool...
							errorHook5p2(ErrorIncidenceType.Warning, ctx.getStart(),
									"WARNING: substituting object of type `",
									methodReturnType.name(),
									"' for `",
									expression.getText() + "'.");
							expression = expression.promoteTo(methodReturnType);
							expression.setResultIsConsumed(true);
						} else {
							errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
									"Type mismatch in return statement. Expected `",
									methodReturnType.name(),
									"' and found `",
									expression.type().getText() + "'.");
						}
					}
				}
				expression = new ReturnExpression(methodName, expression, ctx.getStart(),
						nearestEnclosingMegaType, currentScope_);
			} else {
				expression = new ReturnExpression(methodName, null, ctx.getStart(),
						nearestEnclosingMegaType, currentScope_);
			}
		} else {
			expression = new ReturnExpression(methodName, null, ctx.getStart(),
					nearestEnclosingMegaType, currentScope_);
		}
		
		if (printProductionsDebug) {
			if (null == ctx.expr()) {
				System.err.println("expr : RETURN");
			} else {
				System.err.println("expr : RETURN expr");
			}
		}
		
		return expression;
	}
	
	@Override public void exitAbelian_expr(KantParser.Abelian_exprContext ctx) {	
		// : abelian_product (ABELIAN_SUMOP abelian_product)*
		// | <assoc=right> abelian_expr ASSIGN expr
		// | if_expr
		
		Expression expression = null;
		MethodInvocationEnvironmentClass originMethodClass, targetMethodClass;
		
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
					
					// WAIT: What if the type has implemented its
					// own version of '+' or '-'?
					final ActualArgumentList params = new ActualArgumentList();
					params.addActualArgument(expr2);
					params.addFirstActualParameter(expression);
					final MethodDeclaration myOperatorFunc =
							null != expression && null != expression.type() &&
							null != expression.type().enclosedScope()?
									expression.type().enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
											operatorAsString, params, false, /*parameterToIgnore*/ null):
										null;
					if (null != myOperatorFunc) {
						// This dude or dudette has defined their own
						// operator. Arrange a full-blown method call
						// to handle it
						final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
						final Message message = new Message(operatorAsString, params, ctx.getStart(), enclosingMegaType);
						
						expression.setResultIsConsumed(true);
						expr2.setResultIsConsumed(true);
						originMethodClass = currentScope_.methodInvocationEnvironmentClass();
						if (expression.type() instanceof RoleType) {
							targetMethodClass = MethodInvocationEnvironmentClass.RoleEnvironment;
						} else if (expression.type() instanceof ClassType) {
							targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
						} else if (expression.type() instanceof BuiltInType) {
							targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
						} else if (expression.type() instanceof ContextType) {
							targetMethodClass = MethodInvocationEnvironmentClass.ContextEnvironment;
						} else {
							targetMethodClass = MethodInvocationEnvironmentClass.Unknown;
						}
						
						boolean isPolymorphic = true;
						if (amInConstructor()) {
							if (expression instanceof IdentifierExpression) {
								if (((IdentifierExpression)expression).name().equals("this")) {
									isPolymorphic = false;
								}
							}
						}
						
						message.setReturnType(myOperatorFunc.type());
						expression = new MessageExpression(expression, message, myOperatorFunc.type(), ctx.getStart(), false,
								originMethodClass, targetMethodClass, isPolymorphic);
					} else if (expression.type() instanceof RoleType){
						// Check if it is in the requires section for a Role
						
						final Type type = expression.type();
						final Declaration associatedDeclaration =
								(type instanceof StagePropType)? ((StagePropType)type).associatedDeclaration():
																 ((RoleType)type).associatedDeclaration();
						final Map<String, List<MethodSignature>> requiresSection =
								(associatedDeclaration instanceof StagePropDeclaration)?
										((StagePropDeclaration)associatedDeclaration).requiredSelfSignatures():
										((RoleDeclaration)associatedDeclaration).requiredSelfSignatures();
						final List<MethodSignature> newMethodSignatures = requiresSection.get(operatorAsString);
						if (null != newMethodSignatures) {
							// TODO: Should probably do more signature-checking here to make
							// sure this is going to fly at run time. Tests?
							final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
							originMethodClass = currentScope_.methodInvocationEnvironmentClass();
							targetMethodClass = associatedDeclaration.type().enclosedScope().methodInvocationEnvironmentClass();
						final Message message = new Message(operatorAsString, params, ctx.getStart(), enclosingMegaType);
							expression = new MessageExpression(expression, message, expr2.type(), ctx.getStart(), false,
									originMethodClass, targetMethodClass, !amInConstructor());
						} else {
							expression = new SumExpression(expression, operatorAsString, expr2, ctx.getStart(), this);
						}
					} else {
						// No need to set x.setResultIsConsumed for the two operands,
						// because the SumExpression constructor does it.
						expression = new SumExpression(expression, operatorAsString, expr2, ctx.getStart(), this);
					}
				}	
			} else {
				expression = new ErrorExpression(null);
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
		} else if ((null != ctx.expr()) && (null != ctx.abelian_expr()) &&
				null != ctx.ASSIGN()) {
			// : lhs '=' rhs
			Expression rhs = null, lhs = null;
			if (parsingData_.currentExpressionExists()) {
				rhs = parsingData_.popExpression();
			} else {
				rhs = new ErrorExpression(null);
			}
			if (parsingData_.currentExpressionExists()) {
				lhs = parsingData_.popExpression();
			} else {
				lhs = new ErrorExpression(null);
			}
			// rhs.setResultIsConsumed(true);	// done by assignmentExpr call
			expression = this.assignmentExpr(lhs, ctx.ASSIGN().getText(), rhs, ctx);
			if (printProductionsDebug) { System.err.println("abelian_expr : abelian_expr ASSIGN expr"); }
		} else if (null != ctx.if_expr()) {
			// | if_expr
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
			} else {
				expression = new ErrorExpression(null);
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
	
	private Expression handleRelopCall(final Expression lhs,
			final String operationAsString, final Expression rhs, final Token token) {

		Expression expression = null;
		ActualArgumentList argumentList = new ActualArgumentList();
		argumentList.addActualArgument(rhs);
		final Type lhsType = lhs.type();
		assert null != lhsType;
		final Expression self = new IdentifierExpression("t$his", lhsType, currentScope_,
				lhs.token());
		argumentList.addFirstActualParameter(self);

		MethodSignature methodSignature = null;
		if (lhsType instanceof InterfaceType) {
			methodSignature = ((InterfaceType)lhsType).lookupMethodSignatureWithConversionIgnoringParameter("compareTo", argumentList, null);
		} else {
			final MethodDeclaration methodDecl = lhsType.enclosedScope().lookupMethodDeclarationRecursive("compareTo", argumentList, false);
			methodSignature = methodDecl.signature();
		}
		if (null != methodSignature) {
			// O.K., it does have a compareTo. Generate code that will call
			// compareTo on the users' behalf
			
			// Nice constants
			final StaticScope globalScope = StaticScope.globalScope();
			final Type stringType = globalScope.lookupTypeDeclaration("String");
			final Type intType = globalScope.lookupTypeDeclaration("int");
			final Type booleanType = globalScope.lookupTypeDeclaration("boolean");
			final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
			
			// The final result
			ExpressionList expressionList = null;
			
			// Generate some temporaries
			String variableNameForOperatorString, variableNameForCompareToResult;
			variableNameForOperatorString = "parseTemp$" + Integer.toString(kantParserVariableGeneratorCounter_);
			kantParserVariableGeneratorCounter_++;
			variableNameForCompareToResult = "parseTemp$" + Integer.toString(kantParserVariableGeneratorCounter_);
			kantParserVariableGeneratorCounter_++;
			
			final ObjectDeclaration variableForOperatorStringDecl = new ObjectDeclaration(variableNameForOperatorString, stringType, token);
			currentScope_.declareObject(variableForOperatorStringDecl, this);
			final ObjectDeclaration variableForCompareToResultDecl = new ObjectDeclaration(variableNameForCompareToResult, stringType, token);
			currentScope_.declareObject(variableForCompareToResultDecl, this);
			
			final IdentifierExpression operatorString = new IdentifierExpression(variableNameForOperatorString, stringType,
					currentScope_, token);
			final IdentifierExpression compareToResult = new IdentifierExpression(variableNameForCompareToResult, intType,
					currentScope_, token);
			
			// Get an expression for the operator string
			final Expression operatorStringExpression = Constant.makeConstantExpressionFrom(
					"\"" + operationAsString + "\"");
			operatorStringExpression.setResultIsConsumed(true);
			
			// Assign the operator string into its expression
			final AssignmentExpression assignOpString = new InternalAssignmentExpression(
					operatorString, "=", operatorStringExpression, token, this);
			assignOpString.setResultIsConsumed(false);
			expressionList = new ExpressionList(assignOpString, enclosingMegaType);
		
			// Set up the argument list for the call to "compareTo".
			// If we got here it should be in the "requires" section
			// of the Role definition
			argumentList = new ActualArgumentList();
			argumentList.addActualArgument(rhs);
			argumentList.addFirstActualParameter(lhs);
			
			final Message compareToMessage = new Message("compareTo", argumentList, token, enclosingMegaType);
			compareToMessage.setReturnType(intType);
			
			IdentifierExpression qualifier = new IdentifierExpression("this", enclosingMegaType,
						currentScope_, token);
				
			final QualifiedIdentifierExpression newLhs = new QualifiedIdentifierExpression(qualifier, lhs.name(), lhs.type());
			newLhs.setResultIsConsumed(true);
			
			// The compareTo message is applied to its first own
			// argument - in this case, lhs
			MethodInvocationEnvironmentClass originMethodClass, targetMethodClass;
			
			originMethodClass = currentScope_.methodInvocationEnvironmentClass();
			targetMethodClass = lhs.type().enclosedScope().methodInvocationEnvironmentClass();
		
			expression = new MessageExpression(lhs, compareToMessage, intType, token, false,
					originMethodClass, targetMethodClass, !amInConstructor());
			expression.setResultIsConsumed(true);	// leave the compareTo return value on the stack
			
			// Assign result of compareTo
			final AssignmentExpression assignCompareTo = new InternalAssignmentExpression(
					compareToResult, "=", expression, token, this);
			assignCompareTo.setResultIsConsumed(false);
			expressionList.addExpression(assignCompareTo);
			
			final ActualArgumentList paramListToConverter = new ActualArgumentList();
			
			paramListToConverter.addActualArgument(compareToResult);
			paramListToConverter.addActualArgument(operatorString);
			
			final Message convertMessage = new Message("compareTo$toBoolean", paramListToConverter, token, enclosingMegaType);
			convertMessage.setReturnType(booleanType);
			
			final Type objectType = StaticScope.globalScope().lookupTypeDeclaration("Object");
			final Expression classObjectExpression = new IdentifierExpression("Object", objectType,
					StaticScope.globalScope(), token);
			originMethodClass = currentScope_.methodInvocationEnvironmentClass();
			targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
			expression = new MessageExpression(classObjectExpression, convertMessage, booleanType,
					token, true, originMethodClass, targetMethodClass, !amInConstructor());
			expression.setResultIsConsumed(true);

			// This sucks. Expression lists usually take the first seed
			// argument as the type of the list. Override it.
			expressionList.setType(booleanType);
			expressionList.setResultIsConsumed(true);
			expressionList.addExpression(expression);
			
			expression = expressionList;
		} else {
			// This was kind of the last chance. We've probably
			// already given the user some diagnostics. Now let's
			// give her some advice.
			errorHook6p2(ErrorIncidenceType.Fatal, token,
					"To use `", operationAsString + "' on object `",
					lhs.name(), "' you must declare a compareTo(",
					rhs.type().name() + " argument",
					") operation in the `requires' section of " +  lhs.type().name());
			expression = new ErrorExpression(lhs);
		}
		
		return expression;
	}
	
	private Expression handleRelopCallWithRoleLHS(final Expression lhs,
			final String operationAsString, final Expression rhs, final Token token) {
		assert lhs.type() instanceof RoleType;

		Expression expression = null;
		ActualArgumentList argumentList = new ActualArgumentList();
		argumentList.addActualArgument(rhs);
		final RoleType roleType = (RoleType)lhs.type();
		assert null != roleType;
		final Expression self = new IdentifierExpression("t$his", roleType, currentScope_,	// just this?
				lhs.token());
		argumentList.addFirstActualParameter(self);

		final MethodDeclaration methodDecl = rhs.type().enclosedScope().
				lookupMethodDeclarationIgnoringParameter(operationAsString, argumentList, "this",
						/* conversionAllowed = */ false);
		if (null == methodDecl) {
			// There is an invocation of a boolean operator on a Role
			// or Stage Prop. Since Roles are inherently untyped,
			// they don't know how to do relops. And for right now
			// we don't allow users to define relops, since doing it
			// consistently is hard... We do, however, allow them to
			// define a compareTo method in the Java Comparable interface
			// tradition. We've already pre-checked above whether it
			// has one. Let's check again:
			
			if (roleType.hasCompareToOrHasOperatorForArgOfType(operationAsString, rhs.type())) {
				// O.K., it does. Generate code that will call
				// compareTo on the users' behalf
				
				// Nice constants
				final Type stringType = StaticScope.globalScope().lookupTypeDeclaration("String");
				final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
				final Type booleanType = StaticScope.globalScope().lookupTypeDeclaration("boolean");
				final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				
				// The final result
				ExpressionList expressionList = null;
				
				// Generate some temporaries
				String variableNameForOperatorString, variableNameForCompareToResult;
				variableNameForOperatorString = "parseTemp$" + Integer.toString(kantParserVariableGeneratorCounter_);
				kantParserVariableGeneratorCounter_++;
				variableNameForCompareToResult = "parseTemp$" + Integer.toString(kantParserVariableGeneratorCounter_);
				kantParserVariableGeneratorCounter_++;
				
				final ObjectDeclaration variableForOperatorStringDecl = new ObjectDeclaration(variableNameForOperatorString, stringType, token);
				currentScope_.declareObject(variableForOperatorStringDecl, this);
				final ObjectDeclaration variableForCompareToResultDecl = new ObjectDeclaration(variableNameForCompareToResult, stringType, token);
				currentScope_.declareObject(variableForCompareToResultDecl, this);
				
				final IdentifierExpression operatorString = new IdentifierExpression(variableNameForOperatorString, stringType,
						currentScope_, token);
				final IdentifierExpression compareToResult = new IdentifierExpression(variableNameForCompareToResult, intType,
						currentScope_, token);
				
				// Get an expression for the operator string
				final Expression operatorStringExpression = Constant.makeConstantExpressionFrom(
						"\"" + operationAsString + "\"");
				operatorStringExpression.setResultIsConsumed(true);
				
				// Assign the operator string into its expression
				final AssignmentExpression assignOpString = new InternalAssignmentExpression(
						operatorString, "=", operatorStringExpression, token, this);
				assignOpString.setResultIsConsumed(false);
				expressionList = new ExpressionList(assignOpString, enclosingMegaType);
			
				// Set up the argument list for the call to "compareTo".
				// If we got here it should be in the "requires" section
				// of the Role definition
				argumentList = new ActualArgumentList();
				argumentList.addActualArgument(rhs);
				argumentList.addFirstActualParameter(lhs);
				
				final Message compareToMessage = new Message("compareTo", argumentList, token, enclosingMegaType);
				compareToMessage.setReturnType(intType);
				
				// Are we in a Role or just in a Context?
				// (Probably unnecessary, but it keeps a tidy house early)
				IdentifierExpression qualifier = null;
				if (null != currentScope_.lookupObjectDeclaration("current$context")) {
					// In another Role — get the Context pointer
					// to qualify *this* Role
					qualifier = new IdentifierExpression("current$context", currentContext_.type(),
							currentScope_, token);
				} else {
					// We're at the Context level — just use "this"
					qualifier = new IdentifierExpression("this", currentContext_.type(),
							currentScope_, token);
				}
					
				final QualifiedIdentifierExpression newLhs = new QualifiedIdentifierExpression(qualifier, lhs.name(), lhs.type());
				newLhs.setResultIsConsumed(true);
				
				// The compareTo message is applied to its first own
				// argument - in this case, lhs
				MethodInvocationEnvironmentClass originMethodClass, targetMethodClass;
				
				originMethodClass = currentScope_.methodInvocationEnvironmentClass();
				targetMethodClass = lhs.type().enclosedScope().methodInvocationEnvironmentClass();
			
				expression = new MessageExpression(lhs, compareToMessage, intType, token, false,
						originMethodClass, targetMethodClass, !amInConstructor());
				expression.setResultIsConsumed(true);
				
				// Assign result of compareTo
				final AssignmentExpression assignCompareTo = new InternalAssignmentExpression(
						compareToResult, "=", expression, token, this);
				assignCompareTo.setResultIsConsumed(false);
				expressionList.addExpression(assignCompareTo);
				
				final ActualArgumentList paramListToConverter = new ActualArgumentList();
				
				paramListToConverter.addActualArgument(compareToResult);
				paramListToConverter.addActualArgument(operatorString);
				
				final Message convertMessage = new Message("compareTo$toBoolean", paramListToConverter, token, enclosingMegaType);
				convertMessage.setReturnType(booleanType);
				
				// Because Roles are bound to objects — and knowledge of their classes —
				// only at run time, we can't put compareTo$toBoolean in the Role
				// scope. It's a static, so it can live anywhere. It could live in
				// Class or Object but that's a bit of an overly broad scope for
				// it. We could just clone a declaration of it in every scope where
				// there is a converTo method. But that doesn't allow end users to
				// create their own types with convertTo methods in the interest
				// of making the built-in relational operators work for them. So
				// we put it up in Object. Looking it up through rhs should work.
				final Type objectType = StaticScope.globalScope().lookupTypeDeclaration("Object");
				final Expression classObjectExpression = new IdentifierExpression("Object", objectType,
						StaticScope.globalScope(), token);
				originMethodClass = currentScope_.methodInvocationEnvironmentClass();
				targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
				expression = new MessageExpression(classObjectExpression, convertMessage, booleanType,
						token, true, originMethodClass, targetMethodClass, !amInConstructor());
				expression.setResultIsConsumed(true);

				// This sucks. Expression lists usually take the first seed
				// argument as the type of the list. Override it.
				expressionList.setType(booleanType);
				expressionList.setResultIsConsumed(true);
				expressionList.addExpression(expression);
				
				expression = expressionList;
			} else {
				// This was kind of the last chance. We've probably
				// already given the user some diagnostics. Now let's
				// give her some advice.
				if (lhs.isntError() && rhs.isntError()) {
					errorHook6p2(ErrorIncidenceType.Fatal, token,
							"To use `", operationAsString + "' on Role `",
							lhs.name(), "' you must declare a compareTo(",
							rhs.type().name() + " argument",
							") operation in the `requires' section of " +  lhs.name());
				}
				expression = new ErrorExpression(lhs);
			}
		} else {
			expression = new RelopExpression(lhs, operationAsString, rhs);
		}
		return expression;
	}
	
	@Override public void exitBoolean_expr(KantParser.Boolean_exprContext ctx) {	
		// : boolean_product (ABELIAN_SUMOP abelian_product)*
		// | boolean_expr op=('&&' | '||' | '^' ) boolean_expr
		// | if_expr
		// | abelian_expr op1=('is' | 'Is') op2 =('not' | 'Not') abelian_expr
		// | abelian_expr op=('!=' | '==' | GT | LT | '>=' | '<=' | (is/isnot variants)) abelian_expr
		// | <assoc=right> boolean_expr ASSIGN expr
		
		Expression expression = null;
		boolean useCompareTo = false;
		
		if (null != ctx.op1 && null != ctx.op2) {
			// 'is' 'not'
			Expression rhs = null, lhs = null;
			if (parsingData_.currentExpressionExists()) {
				rhs = parsingData_.popExpression();
			} else {
				rhs = new ErrorExpression(null);
			}
			if (parsingData_.currentExpressionExists()) {
				lhs = parsingData_.popExpression();
			} else {
				lhs = new ErrorExpression(null);
			}
			lhs.setResultIsConsumed(true);
			rhs.setResultIsConsumed(true);
			
			expression = new IdentityBooleanExpression(lhs, "is not", rhs);
		} else if ((null != ctx.abelian_expr() && ctx.abelian_expr().size() == 1) && (null != ctx.boolean_expr()) &&
				(null == ctx.boolean_expr() || ctx.boolean_expr().size() == 0) && null == ctx.ASSIGN()) {
			// : abelian_expr
			Expression rhs = null;
			if (parsingData_.currentExpressionExists()) {
				rhs = parsingData_.popExpression();
			} else {
				rhs = new ErrorExpression(null);
			}

			// rhs.setResultIsConsumed(true);	// done by assignmentExpr call
			expression = rhs;
			if (printProductionsDebug) { System.err.println("boolean_expr : abelian_expr"); }
		} else if (null != ctx.if_expr()) {
			// | if_expr
			expression = parsingData_.popExpression();
			if (printProductionsDebug) {
				System.err.println("boolean_expr : if_expr ");
			}
		} else if (null != ctx.boolean_product() && ctx.boolean_product().size() > 1 && null != ctx.BOOLEAN_SUMOP()) {
			// | boolean_expr op=('&&' | '||' | '^' ) boolean_expr
			
			// Make it left-associative
			final int booleanSumopSize = ctx.BOOLEAN_SUMOP().size();
			final Stack<Expression> exprStack = new Stack<Expression>();
			for (int i = 0; i < booleanSumopSize; i++) {
				final Expression expr2 = parsingData_.popExpression();
				exprStack.push(expr2);
			}
			
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
				for (int i = 0; i < booleanSumopSize; i++) {
					final Expression expr2 = exprStack.pop();
					final String operatorAsString = ctx.BOOLEAN_SUMOP(i).getText();
					expression = new SumExpression(expression, operatorAsString, expr2, ctx.getStart(), this);
				}
			} else {
				expression = new ErrorExpression(null);
			}
			
			if (printProductionsDebug) {
				System.err.print("boolean_expr : boolean_expr ");
				for (int i = 0; i < ctx.BOOLEAN_SUMOP().size(); i++) {
					System.err.print("`");
					System.err.print(ctx.BOOLEAN_SUMOP(i).getText());
					System.err.print("' boolean_expr ");
				}
				System.err.println();
			}
		} else if (null != ctx.boolean_product() && ctx.boolean_product().size() == 1) {
			expression = parsingData_.popExpression();
			if (printProductionsDebug) {
				System.err.println("boolean_expr : boolean_product ");
			}
		} else if (null != ctx.boolean_expr() && ctx.boolean_expr().size() > 1 &&
				null != ctx.op && ctx.op.getText().length() > 0) {
			//	| abelian_expr op=('&&') abelian_expr
			final Token relationalOperator = ctx.op;
	
			final Expression rhs = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
					new ErrorExpression(null);
			final Expression lhs = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
					new ErrorExpression(null);
			lhs.setResultIsConsumed(true);
			rhs.setResultIsConsumed(true);
			if (lhs.isntError() && rhs.isntError() && lhs.type().canBeConvertedFrom(rhs.type()) == false) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"Expression '" + rhs.getText(), "' is not of the right type (",
						lhs.type().getText(), ").");
			}
			
			assert null != relationalOperator;
			final String operationAsString = relationalOperator.getText();
			expression = new RelopExpression(lhs, operationAsString, rhs);
	
			if (printProductionsDebug) {
				System.err.print("relop_expr : boolean_expr ");
				System.err.print(operationAsString);
				System.err.println(" boolean_expr");
			}
		} else if ((null != ctx.boolean_expr()) &&
				(ctx.boolean_expr().size() == 1) &&
				(ctx.expr() != null) &&
				null != ctx.ASSIGN()) {
			// : lhs '=' rhs
			Expression rhs = null, lhs = null;
			if (parsingData_.currentExpressionExists()) {
				rhs = parsingData_.popExpression();
			} else {
				rhs = new ErrorExpression(null);
			}
			if (parsingData_.currentExpressionExists()) {
				lhs = parsingData_.popExpression();
			} else {
				lhs = new ErrorExpression(null);
			}
			// rhs.setResultIsConsumed(true);	// done by assignmentExpr call
			expression = this.assignmentExpr(lhs, ctx.ASSIGN().getText(), rhs, ctx);
			if (printProductionsDebug) { System.err.println("boolean_expr : boolean_expr ASSIGN boolean_expr"); }
		} else if (null != ctx.abelian_expr() && ctx.abelian_expr().size() > 1 &&
				null != ctx.op && ctx.op.getText().length() > 0) {
			//	| abelian_expr op=('<=' | '>=' | '<' | '>' | '==' | '!=' | (is /isnot variants)) abelian_expr
			final Token relationalOperator = ctx.op;
	
			Expression rhs = null, lhs = null;
			if (parsingData_.currentExpressionExists()) {
				rhs = parsingData_.popExpression();
			} else {
				rhs = new ErrorExpression(null);
			}
			if (parsingData_.currentExpressionExists()) {
				lhs = parsingData_.popExpression();
			} else {
				lhs = new ErrorExpression(null);
			}
			lhs.setResultIsConsumed(true);
			rhs.setResultIsConsumed(true);
			
			assert null != relationalOperator;
			final String operationAsString = relationalOperator.getText();
			
			if (operationAsString.equals("is") || operationAsString.equals("Is") ||
					operationAsString.equals("isnot") || operationAsString.equals("IsNot")) {
				expression = new IdentityBooleanExpression(lhs, operationAsString, rhs);
			} else {
				// Refactor into method
				final Type lhsType = null == lhs? null: lhs.type(),
							rhsType = null == rhs? null: rhs.type();
				
				if (null != lhsType && null != rhsType) {
					if (lhsType.canBeConvertedFrom(rhsType) == false && lhs.isntError() &&
							lhsType.isntError() && rhs.isntError() && rhsType.isntError() &&
							!(rhs instanceof NullExpression)) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
								"Expression `" + rhs.getText(), "' is not of the right type (",
								lhs.type().getText(), ").");
					}
					
					if (lhsType.canBeLhsOfBinaryOperatorForRhsType(operationAsString, rhsType)) {
						;	// O.K.
					} else if (lhs.isntError() && lhs.type().isntError() && rhs.isntError() &&
							rhsType.isntError()) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
								"You may not apply '" + operationAsString, "' to objects of type `",
								lhs.type().getText(), "'.");
					}
				}
	
				final ActualArgumentList parameterList = new ActualArgumentList();
				parameterList.addActualArgument(lhs);
				parameterList.addActualArgument(rhs);
				if (null != lhs && null != rhs && null != lhsType && (lhsType instanceof InterfaceType) == false &&
						lhs.isntError() && rhs.isntError() && lhsType.isntError() && null != rhsType && rhsType.isntError() &&
						(operationAsString.equals("<") || operationAsString.equals(">") ||
								operationAsString.equals("<=") || operationAsString.equals(">=") ||
								operationAsString.equals("==") || operationAsString.equals("!=")) &&
						null != lhsType.enclosedScope().lookupMethodDeclarationRecursive("compareTo", parameterList, false)) {
					useCompareTo = true;	// then, yeah...
				} else if (null != lhs && null != rhs && null != lhsType && lhsType instanceof InterfaceType &&
						(operationAsString.equals("<") || operationAsString.equals(">") ||
								operationAsString.equals("<=") || operationAsString.equals(">=") ||
								operationAsString.equals("==") || operationAsString.equals("!=")) &&
						null != ((StaticInterfaceScope)lhs.type().enclosedScope()).
							lookupMethodDeclarationWithConversionIgnoringParameter("compareTo", parameterList, false, "this")) {
					useCompareTo = true;	// then, yeah...
				} else if (null != rhs && null != rhsType &&
						rhs.type().canBeRhsOfBinaryOperator(operationAsString)) {
					;	// O.K.
				} else if (rhs instanceof NullExpression) {
					;	// can always compare with NULL
				} else if (rhs.isntError() && null != rhs.type() && rhs.type().isntError()){
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
							"You may not use an object of type '" +
									(null == rhs || null == rhs.type()?
											"<unknown>": rhs.type().getText()),
									"' as an argument to `",
									operationAsString, "'.");
				}
				
				if (lhs.type() instanceof RoleType) {
					// We may need to convert the operator
					// to a call of compareTo
					if ((operationAsString.equals("==") || operationAsString.equals("!="))
							&& rhs.type().pathName().equals("Null")) {
						// Comparing to NULL is O.K...  for now....
						expression = new RelopExpression(lhs, operationAsString, rhs);
					} else {
						expression = handleRelopCallWithRoleLHS(lhs, operationAsString, rhs, ctx.getStart());
					}
				} else {
					if (useCompareTo && lhs.type() instanceof BuiltInType == false && rhs.type().pathName().equals("Null") == false) {
						// We found above that things are all set up to use compareTo. Use it.
						expression = handleRelopCall(lhs, operationAsString, rhs, ctx.getStart());
					} else {
						expression = new RelopExpression(lhs, operationAsString, rhs);
					}
				}
			}
	
			if (printProductionsDebug) {
				System.err.print("boolean_expr : abelian_expr ");
				System.err.print(operationAsString);
				System.err.println(" abelian_expr");
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
				expression = new ErrorExpression(null);
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
	
	@Override public void exitBoolean_product(KantParser.Boolean_productContext ctx) {
		// : boolean_unary_op (BOOLEAN_MULOP boolean_unary_op)*
		
		Expression expression = null;
		List<KantParser.Boolean_unary_opContext> unaryOp = ctx.boolean_unary_op();
		assert null != unaryOp;
		
		if (null != unaryOp && unaryOp.size() == 1 && (null == ctx.BOOLEAN_MULOP() || 0 == ctx.BOOLEAN_MULOP().size())) {
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
			} else {
				expression = new ErrorExpression(null);
			}
			
			if (printProductionsDebug) {
				System.err.println("boolean_product : boolean_unary_op ");
			}
		} else if (null != unaryOp && (unaryOp.size() > 1) && null != ctx.BOOLEAN_MULOP() &&  0 < ctx.BOOLEAN_MULOP().size()) {
			// boolean_unary_op (BOOLEAN_MULOP boolean_unary_op)*
			
			final int booleanMulopSize = ctx.BOOLEAN_MULOP().size();
			final Stack<Expression> exprStack = new Stack<Expression>();
			for (int i = 0; i < booleanMulopSize; i++) {
				if (parsingData_.currentExpressionExists()) {
					final Expression expr2 = parsingData_.popExpression();
					exprStack.push(expr2);
				} else {
					break;
				}
			}
			
			if (parsingData_.currentExpressionExists()) {
				expression = parsingData_.popExpression();
				for (int i = 0; i < booleanMulopSize; i++) {
					final Expression expr2 = exprStack.pop();
					final String operatorAsString = ctx.BOOLEAN_MULOP(i).getText();
					expression = new ProductExpression(expression, operatorAsString, expr2, ctx.getStart(), this);
				}
			} else {
				expression = new ErrorExpression(null);
			}
			
			
			if (printProductionsDebug) {
				System.err.print("boolean_product : boolean_unary_op ");
				for (int i = 0; i < ctx.BOOLEAN_MULOP().size(); i++) {
					System.err.print("`");
					System.err.print(ctx.BOOLEAN_MULOP(i).getText());
					System.err.print("' boolean_unary_op ");
				}
				System.err.println();
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
				expression = new UnaryAbelianopExpression(expression, ctx.ABELIAN_SUMOP().getText(), this);
			} else {
				expression = new ErrorExpression(null);
			}
			
			if (printProductionsDebug) {
				System.err.println("abelian_unary_op : '-' abelian_atom");
			}
		} else {
			if (parsingData_.currentExpressionExists()) {
				// Only do it if it's there to protect against error stumbling.
				expression = parsingData_.popExpression();
			} else {
				expression = new ErrorExpression(null);
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
	
	@Override public void exitBoolean_unary_op(KantParser.Boolean_unary_opContext ctx) {
		// :  LOGICAL_NEGATION boolean_expr
  		// |  boolean_atom
		
		Expression expression = null;
		
		if (null != ctx.LOGICAL_NEGATION()) {
			//	| LOGICAL_NEGATION boolean_expr
			
			expression = parsingData_.popExpression();
			final Type type = expression.type();
			if (StaticScope.globalScope().lookupTypeDeclaration("boolean").canBeConvertedFrom(type)) {
				;	// is O.K.
			} else {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"Expression `", expression.getText(), "' is not of type boolean.", "");
			}
			expression = new UnaryAbelianopExpression(expression, "!", this);
			
			if (printProductionsDebug) {
				System.err.println("boolean_unary_op : '!' boolean_expr");
			}
		} else {
			// |  boolean_atom
			
			if (parsingData_.currentExpressionExists()) {
				// Only do it if it's there to protect against error stumbling.
				expression = parsingData_.popExpression();
			} else {
				expression = new ErrorExpression(null);
			}
			if (printProductionsDebug) {
				System.err.println("boolean_unary_op : boolean_atom");
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
	
	private Expression checkNakedNew(final Expression newExpr) {
		if (newExpr instanceof NewExpression == false && newExpr instanceof NewArrayExpression == false) {
			assert newExpr instanceof NewExpression || newExpr instanceof NewArrayExpression;
		}
		Expression retval = newExpr;
		
		// It's possible to write an expression of the form:
		//
		//		new foo
		//
		// without binding the result. That means that the
		// object reference count is never manipulated, which
		// means that the object is never reclaimed. If we
		// have one of those, just give it a temporary home
		// in the current activation record. Activation record
		// closure will clean it up.

		if (newExpr.resultIsConsumed() == false && this instanceof Pass4Listener) {
			// Pass4Listener check above is so we do this only once,
			// avoiding multiple declarations of the temporary
			// variable
			final String tempName = "temp$" + parsingData_.variableGeneratorCounter_;
			parsingData_.variableGeneratorCounter_++;
			final ObjectDeclaration tempVariableDecl = new ObjectDeclaration(tempName, newExpr.type(), newExpr.token());;
			currentScope_.declareObject(tempVariableDecl, this);
			
			final IdentifierExpression tempVariable = new IdentifierExpression(tempName, newExpr.type(), currentScope_, newExpr.token());
			
			retval = new InternalAssignmentExpression(tempVariable, "=", newExpr, newExpr.token(), this);
		}
		return retval;
	}
	
	@Override public void exitAbelian_atom(KantParser.Abelian_atomContext ctx)
	{
		//  abelian_expr         
		//  | NEW message
		//	| NEW type_name
        //	| NEW type_name '[' expr ']'
		//  | NEW JAVA_ID type_list '(' argument_list ')'
		//  | abelian_atom '.' JAVA_ID
		//  | abelian_atom '.' message
		//  | builtin_type_name '.' message
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
		
		final Token token = ctx.getStart();
		Expression expression = null;
		
		if (null != ctx.NEW() && null != ctx.type_list() && null != ctx.argument_list()) {
			//  | NEW JAVA_ID type_list '(' argument_list ')'
			Type type = null;
			final ActualArgumentList argument_list = parsingData_.popArgumentList();
			final List<String> typeParameterNameList = parsingData_.popTypeNameList();

			final String JAVA_ID = ctx.JAVA_ID().getText();
			final TemplateDeclaration templateDeclaration = currentScope_.lookupTemplateDeclarationRecursive(JAVA_ID);
			if (null == templateDeclaration) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"Cannot find template ", JAVA_ID, "", "");
				type = new ErrorType();
				expression = new ErrorExpression(null);
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
					type = this.lookupOrCreateTemplateInstantiation(JAVA_ID, typeParameterNameList, token);
					if (null == type) {
						errorHook5p2(ErrorIncidenceType.Internal, ctx.getStart(),
							"Cannot find template instantiation ", compoundTypeName, "", "");
						type = new ErrorType();
					}
				}
				
				final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				final Message message = new Message(compoundTypeName, argument_list, ctx.getStart(), enclosingMegaType);
				final NewExpression newExpr = new NewExpression(type, message, token, enclosingMegaType);
				ctorCheck(type, message, token);
				addSelfAccordingToPass(type, message, currentScope_);
				expression = newExpr;
			}
			
			final MethodDeclaration constructor = type.enclosedScope().lookupMethodDeclaration(JAVA_ID, argument_list, false);
			if (null != constructor) {
				final boolean isAccessible = currentScope_.canAccessDeclarationWithAccessibility(constructor, constructor.accessQualifier(), token);
				if (isAccessible == false) {
					errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart(),
							"Cannot access constructor `", constructor.signature().getText(),
							"' with `", constructor.accessQualifier().asString(), "' access qualifier.","");
				}
				
				// Create a new message just for checking (not dispatching)
				final String methodSelectorName = constructor.name();
				final Type enclosingType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				final Message message = new Message(methodSelectorName, argument_list, token, enclosingType);
				final List<String> nonmatchingMethods = message.validInRunningEnviroment(constructor);
				if (0 < nonmatchingMethods.size()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "The parameters to script `",
							methodSelectorName + message.argumentList().selflessGetText(),
							"' have scripts that are unavailable outside this Context, ",
							"though some formal parameters of " + methodSelectorName +
							" presume they are available (they are likely Role scripts):");
					for (final String badMethod : nonmatchingMethods) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
								"\t", badMethod, "", "");
					}
				}
			}
			
			expression = checkNakedNew(expression);

			if (printProductionsDebug) {
				System.err.println("abelian_atom : NEW JAVA_ID type_list '(' argument_list ')'");
			}
		} else if (null == ctx.ABELIAN_INCREMENT_OP() && null != ctx.abelian_atom() && null != ctx.JAVA_ID()) {
			//	| abelian_atom '.' JAVA_ID
			// The following line DOES pop the expression stack
			final ExpressionStackAPI rawExpression = this.exprFromExprDotJAVA_ID(ctx.JAVA_ID(), ctx.getStart(), null);
			assert rawExpression instanceof Expression;
			expression = (Expression)rawExpression;
			if (printProductionsDebug) {
				System.err.print("abelian_atom : abelian_atom '.' JAVA_ID (");
				System.err.print(ctx.JAVA_ID().getText()); System.err.println(")");
			}
		} else if (null == ctx.builtin_type_name() && null != ctx.abelian_atom() && null != ctx.message()) {
			//	| abelian_atom '.' message
			// This routine actually does pop the expressions stack (and the Message stack)
			expression = this.messageSend(ctx.getStart(), ctx.abelian_atom(), null);
			
			if (null == expression) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"No match for call: ", ctx.abelian_atom().getText(), ".", ctx.message().getText());
				expression = new ErrorExpression(null);
			}
												
			if (printProductionsDebug) {
				System.err.println("abelian_product : abelian_atom '.' message");
			}
		} else if (null == ctx.NEW() && null != ctx.builtin_type_name() && null != ctx.message()) {
			//	| builtin_type_name '.' message
			//    (e.g., String.join)
			
			// This routine actually does pop the expressions stack (and the Message stack)
			expression = this.messageSend(ctx.getStart(), ctx.abelian_atom(), ctx.builtin_type_name());
			
			if (null == expression || expression.isError()) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"No match for call: ", ctx.type_name().getText(), ".", ctx.message().getText());
				expression = new ErrorExpression(null);
			}
												
			if (printProductionsDebug) {
				System.err.println("type_name : abelian_atom '.' message");
			}
		} else if (null != ctx.NEW()) {
			// NEW message
			// NEW type_name '[' expr ']'
			
			final KantParser.ExprContext sizeExprCtx = (null == ctx.expr())? null:
															((ctx.expr().size() == 0)? null: ctx.expr(0));
			final List<ParseTree> ctxChildren = ctx.children;
			final Token ctxGetStart = ctx.getStart();
			final MessageContext ctxMessage = ctx.message();
			expression = this.newExpr(ctxChildren, ctxGetStart, sizeExprCtx, ctxMessage);
			
			if (expression instanceof NullExpression == false && expression.isntError()) {
				expression = checkNakedNew(expression);
			}
			
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
				expression = new ErrorExpression(null);
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
			final Expression indexExpr = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
						new ErrorExpression(null);
			indexExpr.setResultIsConsumed(true);
			final Expression rawArrayBase = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
						new ErrorExpression(indexExpr);
			
			// The fidelity of this varies according to how much
			// type information we have at hand
			expression = processIndexExpression(rawArrayBase, indexExpr, token);
			
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
			expression = this.messageSend(ctx.getStart(), null, null);
									
			if (printProductionsDebug) { System.err.println("abelian_atom : /* this. */ message");}
		} else if (null != ctx.abelian_atom() && null != ctx.CLONE()) {
			//	| abelian_atom '.' CLONE
			
			final Expression qualifier = parsingData_.popExpression();
			expression = new DupMessageExpression(qualifier, qualifier.type());
			
			if (qualifier.isError() || qualifier.type().isError()) {
				expression = new ErrorExpression(expression);
			}
			
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
	
	@Override public void enterBoolean_atom(KantParser.Boolean_atomContext ctx) {
		// : constant
		// JAVA_ID
		// | null_expr
		// | '(' boolean_expr ')'
		
        // nothing
	}
	
	@Override public void exitBoolean_atom(KantParser.Boolean_atomContext ctx)
	{
		// : constant
		// | null_expr
		// | JAVA_ID
		// | '(' boolean_expr ')'
		
		// All passes.
		
		Expression expression = null;
		
		if (null != ctx.null_expr()) {
			// | null_expr
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.print("abelian_atom : null_expr");
			}
		} else if (null != ctx.JAVA_ID() && (null == ctx.boolean_expr())) {
			// | JAVA_ID
			
			expression = idExpr(ctx.JAVA_ID(), ctx.getStart());
			if (printProductionsDebug) {
				System.err.print("boolean_atom : JAVA_ID (");
				System.err.print(ctx.JAVA_ID().getText());
				System.err.println(")");
			}
		} else if (null != ctx.constant()) {
			//	| constant
			
			// expression = Expression.makeConstantExpressionFrom(ctx.constant().getText());
			// is on the stack. We now have a "constant : ..." production,
			// so we don't make it here
			expression = parsingData_.popExpression();
			if (printProductionsDebug) {
				System.err.println("boolean_atom : constant");
			}
		} else if (null != ctx.boolean_expr() && null == ctx.JAVA_ID()) {
			//	| '(' boolean_expr ')'
			expression = parsingData_.popExpression();
			
			if (printProductionsDebug) {
				System.err.println("boolean_atom : '(' boolean_expr ')'");
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
	
	protected Expression processIndexExpression(final Expression rawArrayBase, final Expression indexExpr, final Token token) {
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
			expression = new ArrayIndexExpression(arrayBase, indexExpr, token);
		} else {
			expression = new ErrorExpression(null);
		}
		return expression;
	}
	
	
	// I wanted to postpone this stuff until Pass 2, but omitting it
	// from Pass 1 causes the parse stack to get messed up here in
	// this pass. I still refrain from passing the argument info
	// in to the semantic analysis routines on Pass 1
	
	@Override public void enterMessage(KantParser.MessageContext ctx)
	{
		// 	: method_name '(' argument_list ')'
		//  | type_name '(' argument_list ')'
		
		parsingData_.pushArgumentList(new ActualArgumentList());
	}
	
	@Override public void exitMessage(KantParser.MessageContext ctx)
	{
		// 	: method_name '(' argument_list ')'
		//  | type_name '(' argument_list ')'
		
		String selectorName = null;
		if (null != ctx.method_name()) {
			selectorName = ctx.method_name().getText();
		} else if (null != ctx.type_name()) {
			final ExpressionStackAPI typeName = parsingData_.popRawExpression();
			selectorName = typeName.name();
		} else {
			assert false;
		}		
		// Leave argument list processing to Pass 2...
		
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final Message newMessage = new Message(selectorName, null, ctx.getStart(), enclosingMegaType);
		parsingData_.pushMessage(newMessage);
		
		// ... but clean up the stack by popping off the arguments
		// This is definitely Pass 2 stuff.
		@SuppressWarnings("unused")
		ActualArgumentList argumentList = parsingData_.popArgumentList();
		
		if (printProductionsDebug) {
			System.err.print("message : message_name '(' argument_list ')' (");
			System.err.print(selectorName);
			System.err.println(")");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterBlock(KantParser.BlockContext ctx)
	{
		//	: '{' expr_and_decl_list '}'
        //	| '{' '}'
		
		// Set up the block
		currentScope_ = new StaticScope(currentScope_, true);
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart());
		parsingData_.pushExprAndDecl(newList);

		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final BlockExpression blockExpression = new BlockExpression(ctx.getStart(), newList, currentScope_, enclosingMegaType);
		currentScope_.setDeclaration(null);	/// hmmm....
		
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
			final Expression expression = new ErrorExpression(null);
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
				parsingData_.popExpression(): new ErrorExpression(null);
		final Expression conditional = parsingData_.currentExpressionExists()?
				parsingData_.popExpression(): new ErrorExpression(null);
		final Type conditionalType = conditional.type();
		if (null != conditionalType && conditionalType.name().equals("boolean") == false && conditional.isntError()) {
			errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Conditional expression `", conditional.getText(),
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
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
						"An array declaration is not appropriate for this context", "",
						"", "");
			}
		}
		
		this.nameCheck(idName, ctx.getStart());
		
		Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
		if (null == type) {
			errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Type `", typeName,
					"' seems not to be declared in any enclosing scope", "");
			type = new ErrorType();
		}
		
		final ObjectDeclaration objectDecl = new ObjectDeclaration(idName, type, ctx.getStart());
		currentScope_.declareObject(objectDecl, this);
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
				ctx.getStart(), parsingDataArgumentAccordingToPass());
		newScope.setDeclaration(null);	/// hmmm.... TODO: Fix this for while and do/while loops too
		
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
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Conditional expression `", conditional.getText(),
						"' is not of type boolean", "");
				final BooleanConstant falseExpr = new BooleanConstant(true);
				expression.reInit(initializer, falseExpr, increment, body);
			} else {
				assert conditional.type().name().equals("boolean");
				expression.reInit(initializer, conditional, increment, body);
			}
		} else if ((null == ctx.JAVA_ID()) && (null != ctx.object_decl()) && (ctx.expr().size() == 3)) {
			//  | 'for' '(' object_decl expr ';' expr ')' expr
			
			final Expression body = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
					new ErrorExpression(null);
			final Expression increment = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
					new ErrorExpression(null);
			final Expression conditional = parsingData_.currentExpressionExists()?
					parsingData_.popExpression():
					new ErrorExpression(null);
			expression = parsingData_.popForExpression();
			
			body.setResultIsConsumed(false);
			increment.setResultIsConsumed(true);
			conditional.setResultIsConsumed(true);
			
			final Type conditionalType = conditional.type();
			
			if (conditionalType.name().equals("boolean") == false) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Conditional expression `", conditional.getText(),
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
			      Declaration JAVA_OBJECT_OR_ROLE_DECL = currentScope_.lookupObjectDeclarationRecursive(JAVA_IDasString);
			if (null == JAVA_OBJECT_OR_ROLE_DECL) {
				// Could it be a role?
				if (null != currentContext_) {
					final StaticScope contextScope = currentContext_.enclosedScope();
					JAVA_OBJECT_OR_ROLE_DECL = contextScope.lookupRoleOrStagePropDeclaration(JAVA_IDasString);
				}
				if (null == JAVA_OBJECT_OR_ROLE_DECL) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Loop identifier `", JAVA_IDasString,
							"' is not declared", "");
				}
			}
			expression = parsingData_.popForExpression();
			
			body.setResultIsConsumed(false);
			thingToIncrementOver.setResultIsConsumed(true);
			
			final Type typeIncrementingOver = thingToIncrementOver.type();
			if (typeIncrementingOver instanceof ArrayType == false &&
					typeIncrementingOver.name().startsWith("List<") == false &&
					typeIncrementingOver.name().startsWith("Set<") == false &&
					typeIncrementingOver.name().startsWith("Map<") == false) {
				if (thingToIncrementOver.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Expression `", thingToIncrementOver.getText(),
							"' is not iterable", "");
				}
			}
			
			expression.reInitIterativeFor(JAVA_OBJECT_OR_ROLE_DECL, thingToIncrementOver, body);
		} else if ((null != ctx.trivial_object_decl()) && (ctx.expr().size() == 2)) {
			//  | 'for' '(' trivial_object_decl ':' expr ')' expr
			
			Expression thingToIncrementOver = null;
			final Expression body = parsingData_.popExpression();
			if (parsingData_.currentExpressionExists()) {
				thingToIncrementOver = parsingData_.popExpression();
			} else {
				thingToIncrementOver = new ErrorExpression(body);
			}
			expression = parsingData_.popForExpression();
			
			final List<ParseTree> children = ctx.trivial_object_decl().children;
			final int numberOfChildren = children.size();
			assert 2 == numberOfChildren;
			final String JAVA_IDasString = ctx.trivial_object_decl().JAVA_ID().getText();
	
			
			// final String JAVA_IDasString = ctx.JAVA_ID().getText();
			final ObjectDeclaration JAVA_ID_DECL = currentScope_.lookupObjectDeclaration(JAVA_IDasString);
			if (null == JAVA_ID_DECL) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Loop identifier `", JAVA_IDasString,
						"' is not declared", " (strange error)");
			}
			
			body.setResultIsConsumed(false);
			thingToIncrementOver.setResultIsConsumed(true);
			
			final Type typeIncrementingOver = thingToIncrementOver.type();
			
			boolean isRoleArray = false;
			if (typeIncrementingOver instanceof RoleType) {
				final RoleType roleType = (RoleType) typeIncrementingOver;
				isRoleArray = roleType.isArray();
			}
			
			if (isRoleArray) {
				;		// an O.K. possibility
			} else if (typeIncrementingOver instanceof ArrayType == false &&
					null != typeIncrementingOver &&
					typeIncrementingOver.name().startsWith("List<") == false &&
					typeIncrementingOver.name().startsWith("Set<") == false &&
					typeIncrementingOver.name().startsWith("Map<") == false) {
				if (thingToIncrementOver.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Expression `", thingToIncrementOver.getText(),
						"' is not iterable", "");
				}
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
				ctx.getStart(), parsingDataArgumentAccordingToPass(),
				nearestEnclosingMegaType);
		
		parsingData_.pushWhileExpression(whileExpression);
	}
	
	@Override public void exitWhile_expr(KantParser.While_exprContext ctx)
	{
		// while_expr
		//	: 'while' '(' expr ')' expr
	
		final Expression body = parsingData_.popExpression();
		final Expression conditional = parsingData_.currentExpressionExists()?
				parsingData_.popExpression(): new ErrorExpression(null);
		final WhileExpression expression = parsingData_.currentWhileExpressionExists()?
				parsingData_.popWhileExpression(): null;
		
		body.setResultIsConsumed(true);
		conditional.setResultIsConsumed(true);
		
		if (conditional.type() != StaticScope.globalScope().lookupTypeDeclaration("boolean")) {
			errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Condition in `while' statement is not of type boolean", conditional.getText(),
					" of type ", conditional.type().name());
		}
		
		if (expression != null) {
			expression.reInit(conditional, body);
			parsingData_.pushExpression(expression);
		} else {
			parsingData_.pushExpression(new ErrorExpression(null));
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
				ctx.getStart(), parsingDataArgumentAccordingToPass(),
				nearestEnclosingMegaType);
		
		parsingData_.pushDoWhileExpression(doWhileExpression);
	}
	
	@Override public void exitDo_while_expr(KantParser.Do_while_exprContext ctx)
	{
		// do_while_expr
		//	: 'do' expr 'while' '(' expr ')'

		final Expression conditional = parsingData_.currentExpressionExists()?
				parsingData_.popExpression():
				new ErrorExpression(null);
		final Expression body = parsingData_.currentExpressionExists()?
				parsingData_.popExpression():
				new ErrorExpression(null);
		final Expression expression = parsingData_.currentDoWhileExpressionExists()?
				parsingData_.popDoWhileExpression():
				new ErrorExpression(null);
		
		body.setResultIsConsumed(true);
		conditional.setResultIsConsumed(true);
		
		if (conditional.type() != StaticScope.globalScope().lookupTypeDeclaration("boolean")) {
			errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Condition in `do / while' statement is not of type boolean", conditional.getText(),
					" of type ", conditional.type().name());
		}
		
		if (expression instanceof DoWhileExpression) {
			((DoWhileExpression)expression).reInit(conditional, body);
		}
		
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
		
		currentScope_ = new StaticScope(currentScope_, true);
		
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		final SwitchExpression switchExpression = new SwitchExpression(parsingDataArgumentAccordingToPass(), enclosingMegaType,
				currentScope_);
		parsingData_.pushSwitchExpr(switchExpression);
	}
	
	@Override public void exitSwitch_expr(KantParser.Switch_exprContext ctx)
	{
		// : 'switch' '(' expr ')' '{' ( switch_body )* '}'
		// One version serves passes 1 - 4
		
		final SwitchExpression switchExpression = parsingData_.popSwitchExpr();
		
		// Set all the goodies. The body is already taken care of.
		if(parsingData_.currentExpressionExists()) {	// error stumbling check
			final Expression expr = parsingData_.popExpression();
			expr.setResultIsConsumed(true);
			switchExpression.addExpression(expr);
			parsingData_.pushExpression(switchExpression);
		
			final Expression expressionToSwitchOn = switchExpression.switchExpression();
			if (null != expressionToSwitchOn) {
				Type potentialSwitchExpressionType = null;
				final Type switchExpressionType = expressionToSwitchOn.type();
				boolean stillEvaluating = true;
				for (final SwitchBodyElement aCase : switchExpression.orderedSwitchBodyElements()) {
					// See if all case body expressions are of the same type
					if (null == potentialSwitchExpressionType && stillEvaluating) {
						if (aCase.hasNoBody() == false) {
							potentialSwitchExpressionType = aCase.type();
						}
					}
					
					if (null != potentialSwitchExpressionType) {
						if (stillEvaluating) {
							final String caseTypePathName = aCase.type().pathName();
							if (caseTypePathName.equals("void")) {
								// it could be because there is no
								// expression to go with the case statement.
								// If so, don't let it upset things.
								if (aCase.hasNoBody()) {
									stillEvaluating = true;
								} else {
									stillEvaluating = caseTypePathName.equals("void");
								}
							} else {
								stillEvaluating = caseTypePathName.equals(potentialSwitchExpressionType.pathName());
							}
						}
						
						// Make sure that all case test expressions are of type of switch expression
						if (aCase.isDefault()) continue;
						if (switchExpressionType.canBeConvertedFrom(aCase.expression().type()) == false &&
								switchExpressionType.isntError()) {
							errorHook6p2(ErrorIncidenceType.Warning, ctx.getStart(), "Case statement with expression of type `",
									aCase.type().name(), "' is incompatible with switch expression of type `",
									switchExpressionType.name(), "'.", "");
						}
					}
				}	/* for */
				
				if (stillEvaluating) {		// then we made it through all the cases!
					// We have a consistent expression type since all case
					// statements yield the same type
					switchExpression.setExpressionType(potentialSwitchExpressionType);
				}
			} /* if */
		} /* if */
		
		currentScope_ = currentScope_.parentScope();
		
		if (printProductionsDebug) {
			System.err.println("switch_expr : 'switch' '(' expr ')' '{' ( switch_body )* '}'");
		}
		if (stackSnapshotDebug) stackSnapshotDebug();
	}
	
	@Override public void enterSwitch_body(KantParser.Switch_bodyContext ctx)
	{
		// : ( 'case' constant | 'default' ) ':' expr_and_decl_list
		
		final ExprAndDeclList newList = new ExprAndDeclList(ctx.getStart());
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
				ErrorLogger.error(ErrorIncidenceType.Internal, ctx.getStart(), "Case statement has non-const expression: `",
					temp.getText(), "'", "");
				constant = new Constant.IntegerConstant(0);
			} else {
				constant = (Constant)temp;
			}
			if (null != parsingData_.currentSwitchExpr().elementForConstant(constant)) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Switch statement has multiple clauses for ",
						constant.getText(), ".", "");
			}
		} else {
			isDefault = true;
			if (parsingData_.currentSwitchExpr().hasDefault()) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Switch statement has multiple default clauses",
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
	
	@Override protected void lookupOrCreateStagePropDeclaration(final String roleName, final Token token, final boolean isStagePropArray) {
		final RoleDeclaration requestedStageProp = currentScope_.lookupRoleOrStagePropDeclaration(roleName);
		if (null != requestedStageProp) {
			currentRoleOrStageProp_ = requestedStageProp;
			
			// The way parsing is designed, these things should
			// be defined once on pass 0 and then referenced only
			// on subsequent passes.
		}
		// caller may reset currentScope - NOT us
	}
	
	protected Type lookupOrCreateTemplateInstantiation(final String templateName,
			final List<String> parameterTypeNames, final Token token) {
		// This varies by pass. On the last pass we first remove the instantiation, so that the
		// new one picks up the body created in Pass 3.
		return lookupOrCreateTemplateInstantiationCommon(templateName, parameterTypeNames, token);
	}
	

	private Type instantiateClass(final List<String> parameterTypeNames, final String typeName,
			final TemplateDeclaration templateDeclaration, Token token) {
		Type retval = null;
		final StaticScope templateScope = templateDeclaration.enclosingScope();
		
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
					errorHook5p2(ErrorIncidenceType.Fatal, token,
						"Cannot find type named ", aTypeName, " in instantiation of ", templateDeclaration.name());
					newTypes.add(new ErrorType());
				} else {
					newTypes.add(correspondingType);
				}
			}
		
			// templateEnclosedScope isn't really used, because a new enclosedScope_ object
			// is created by ClassDeclaration.elaborateFromTemplate(templateDeclaration)
			final StaticScope templateEnclosedScope = templateDeclaration.enclosedScope();
			classDeclaration = new ClassDeclaration(typeName, templateEnclosedScope,
				baseClassDecl, token);
			classDeclaration.elaborateFromTemplate(templateDeclaration, newTypes, token);
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
		
		retval.pass2Instantiations();
		
		return retval;
	}
	
	private Type instantiateInterface(final List<String> parameterTypeNames, final String typeName, final TemplateDeclaration templateDeclaration,
			Token token) {
		Type retval = null;
		final StaticScope templateScope = templateDeclaration.enclosingScope();
	
		InterfaceDeclaration interfaceDeclaration = currentScope_.lookupInterfaceDeclarationRecursive(typeName);
		if (null == interfaceDeclaration) {
			// Create a new type vector from the type parameters
			final TemplateInstantiationInfo newTypes = new TemplateInstantiationInfo(templateDeclaration, typeName);
			for (final String aTypeName : parameterTypeNames) {
				final Type correspondingType = currentScope_.lookupTypeDeclarationRecursive(aTypeName);
				if (null == correspondingType) {
					errorHook5p2(ErrorIncidenceType.Fatal, token,
						"Cannot find type named ", aTypeName, " in instantiation of ", templateDeclaration.name());
					newTypes.add(new ErrorType());
				} else {
					newTypes.add(correspondingType);
				}
			}
		
			// templateEnclosedScope isn't really used, because a new enclosedScope_ object
			// is created by ClassDeclaration.elaborateFromTemplate(templateDeclaration)
			final StaticScope templateEnclosedScope = templateDeclaration.enclosedScope();

			interfaceDeclaration = new InterfaceDeclaration(typeName, templateEnclosedScope,
				token);
			interfaceDeclaration.elaborateFromTemplate(templateDeclaration, newTypes, token);
			final Type rawNewType = interfaceDeclaration.type();
			assert rawNewType instanceof InterfaceType;
			final InterfaceType newType = (InterfaceType)rawNewType;
			newTypes.setInterfaceType(newType);

			templateScope.declareType(newType);
			templateScope.declareInterface(interfaceDeclaration);

			retval = newType;
		
			// Here's where we queue template instantiatons for code generation
			parsingData_.currentTemplateInstantiationList().addDeclaration(interfaceDeclaration);
		} else {
			retval = currentScope_.lookupTypeDeclarationRecursive(typeName);
			assert null != retval;
		}
		return retval;
	}
	
	protected Type lookupOrCreateTemplateInstantiationCommon(final String templateName, final List<String> parameterTypeNames, final Token token) {
		Type retval = null;
		final TemplateDeclaration templateDeclaration = currentScope_.lookupTemplateDeclarationRecursive(templateName);
		if (null == templateDeclaration) {
			errorHook5p2(ErrorIncidenceType.Fatal, token, "Template ", templateName, " is not defined. ", "");
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
			
			if (templateDeclaration.type() instanceof TemplateTypeForAnInterface) {
				retval = this.instantiateInterface(parameterTypeNames, typeName, templateDeclaration, token);
			} else {
				retval = this.instantiateClass(parameterTypeNames, typeName, templateDeclaration, token);
			}
		}
		return retval;
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
			final Type type, final Token token, final AccessQualifier accessQualifier)
	{
		variablesToInitialize_ = new SimpleList();
		initializationExpressions_ = new SimpleList();
		final List<ObjectDeclaration> objectDecls = new ArrayList<ObjectDeclaration>();
		
		this.processIdentifierListRecursive(identifier_list, type, token, accessQualifier);
		
		final List<BodyPart> intializationExpressionsToReturn = new ArrayList<BodyPart>();
		
		final long initializationCount = variablesToInitialize_.count();
		for (int j = 0; j < initializationCount; j++) {
			final Expression initializationExpression = (Expression)initializationExpressions_.objectAtIndex(j);
			initializationExpression.setResultIsConsumed(true);
			final ObjectDeclaration objDecl = (ObjectDeclaration)variablesToInitialize_.objectAtIndex(j);
			      Type expressionType = initializationExpression.type();
			final Type declarationType = objDecl.type();
	

			// MAP>>KEYS
			boolean okForNow = false;
			if (declarationType instanceof ClassType &&
					(expressionType instanceof TemplateType || expressionType instanceof ClassType)) {
				okForNow = true;
			}

			if (okForNow || (null != declarationType && null != expressionType &&
					declarationType.canBeConvertedFrom(expressionType)) ||
					initializationExpression instanceof NullExpression) {	// GNU
				
				// Still need this, though old initialization framework is gone
				objectDecls.add(objDecl);
				
				final IdentifierExpression lhs = new IdentifierExpression(objDecl.name(), declarationType, currentScope_, token);
				final AssignmentExpression initialization = new AssignmentExpression(lhs, "=", initializationExpression, identifier_list.getStart(), this);
				intializationExpressionsToReturn.add(initialization);
				
				// New initialization association
				objDecl.setInitialization(initialization);
			} else if (expressionType == null) {
				int k=0;
				k++;
			} else if (expressionType.isntError() && declarationType.isntError() && initializationExpression.isntError()) {
				errorHook5p2(ErrorIncidenceType.Fatal, objDecl.token(),
						"Type mismatch in initialization of `",
						objDecl.name(), "'.", "");
			}
		}
		
		final DeclarationsAndInitializers retval = new DeclarationsAndInitializers(objectDecls, intializationExpressionsToReturn);
		return retval;
	}
	
	private void processIdentifierListRecursive(final Identifier_listContext identifier_list, final Type type, final Token token, final AccessQualifier accessQualifier)
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
						this.nameCheck(tokAsText, token);
						objDecl = this.pass1InitialDeclarationCheck(tokAsText, token);
						if (null == objDecl) {
							objDecl = new ObjectDeclaration(tokAsText, type, token);
							declareObjectSuitableToPass(currentScope_, objDecl);
							objDecl.setAccess(accessQualifier, currentScope_, token);
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
						this.nameCheck(tokAsText, token);
						if (tokAsText.equals("=") == true) {
							; // we get it with the ExprContext catch below
						} else if (tokAsText.equals(",") == true) {
							; // skip it; it separates elements
						} else {
							objDecl = this.pass1InitialDeclarationCheck(tokAsText, token);
							if (null == objDecl) {
								objDecl = new ObjectDeclaration(tokAsText, type, token);
								declareObjectSuitableToPass(currentScope_, objDecl);
								objDecl.setAccess(accessQualifier, currentScope_, token);
							} else {
								// Doesn't hurt to update type
								objDecl.updateType(type);
							}
						}
					}
				}
			} else if (pt instanceof Identifier_listContext) {
				this.processIdentifierListRecursive((Identifier_listContext)pt, type, token, accessQualifier);
				// System.err.print("Alert: ");
				// System.err.println(pt.getText());
			} else if (pt instanceof ExprContext) {
				if (parsingData_.currentExpressionExists()) {
					final Expression initializationExpr = parsingData_.popExpression();
					assert initializationExpr != null;
					assert objDecl != null;
					updateInitializationLists(initializationExpr, objDecl);
				}
			} else {
				assert false;
			}
		}
	}
	
	public ObjectDeclaration pass1InitialDeclarationCheck(final String name, final Token token) {
		final ObjectDeclaration objDecl = currentScope_.lookupObjectDeclaration(name);
		if (null != objDecl) {
			final String addedMessage = "(earlier declaration at line " +
								Integer.toString(objDecl.lineNumber()) + ").";
			errorHook5p1(ErrorIncidenceType.Fatal, token, "Identifier `",
					name, "' declared multiple times ", addedMessage);
		}
		return objDecl;
	}

	@Override public void enterParam_list(KantParser.Param_listContext ctx)
	{
		if (ctx.param_decl() != null) {
			// : param_decl
	        // | param_list ',' param_decl
			// | param_list ELLIPSIS
	        // | param_list ',' ELLIPSIS
	        // | /* null */
			
			// Do most of this parameter stuff here on the second pass, because
			// we should have most of the type information by then
			if (null == ctx.ELLIPSIS()) {
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
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
							"Parameter type `", paramTypeName, "' not declared for `",
							formalParameterName + "'.");
					paramType = new ErrorType();
				} else if (formalParameterName.equals("this")) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "You cannot name a formal parameter `this'.", "", "", "");
					paramType = new ErrorType();
				} else if (isArray) {
					// A derived type
					final String aName = paramType.getText() + "_$array";
					paramType = new ArrayType(aName, paramType);
				}
				
				Declaration newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart());
				if (paramType.isError()) {
					newFormalParameter = new ErrorDeclaration("");
				}
				
				// They go in reverse order...
				if (newFormalParameter.name().equals("this")) {
					formalParameterName = "_error" + String.valueOf(parsingData_.variableGeneratorCounter_);
					parsingData_.variableGeneratorCounter_++;
					newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart());
				}
				if (parsingData_.currentFormalParameterList().containsVarargs()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(),
							"Formal parameter `", newFormalParameter.name(),
							"' comes after `...', which must be the last element in a parameter list.",
							"");
				}
				parsingData_.currentFormalParameterList().addFormalParameter(newFormalParameter);
			} else {
				// ...
				final MethodSignature currentMethod = parsingData_.currentMethodSignature();
				final Type paramType = new VarargsType(currentMethod.name());
				final String formalParameterName = "...";
				final ObjectDeclaration newFormalParameter = new ObjectDeclaration(formalParameterName, paramType, ctx.getStart());
				parsingData_.currentFormalParameterList().addFormalParameter(newFormalParameter);
			}
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
				sexpCtx.getStart());
		return retval;
	}
		
	protected void checkExprDeclarationLevel(RuleContext ctxParent, Token ctxGetStart) {
		/* Nothing */
	}
	
	
	// ----------------------------------------------------------------------------------------

	// WARNING. Tricky code here
	protected void declareObject(final StaticScope s, final ObjectDeclaration objdecl) { s.declareObject(objdecl, this); }

	protected void declareFormalParametersSuitableToPass(final StaticScope scope, final ObjectDeclaration objDecl) {
		scope.declareObject(objDecl, this);
	}
	
	private ExpressionStackAPI exprFromExprDotJAVA_ID(final TerminalNode ctxJAVA_ID, final Token ctxGetStart, final TerminalNode ctxABELIAN_INCREMENT_OP) {
		// Certified Pass 1 version ;-) There is no longer any Pass 2+ version
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
				// It must be static
				final ObjectDeclaration odecl2 = theClass.type().enclosedScope().lookupStaticDeclaration(javaIdString);
				if (null == odecl2) {
					errorHook5p2(ErrorIncidenceType.Fatal,
							ctxGetStart,
							"Attempt to access instance member `",
							javaIdString,
							"' as a member of class `" + theClass.name(),
							"'.");
					type = new ErrorType();
				} else {
					type = odecl.type();
				}
				assert type != null;
				
				if (null != ctxABELIAN_INCREMENT_OP) {
					expression = new QualifiedClassMemberExpressionUnaryOp(theClass, javaIdString, type, ctxABELIAN_INCREMENT_OP.getText(), preOrPost);
				} else {
					expression = new QualifiedClassMemberExpression(theClass, javaIdString, type);
				}
			} else {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
						"Member `", javaIdString, "' of `" + qualifier.name(), "' is not defined.");
				expression = new ErrorExpression(null);
			}
		} else {
			final Expression object = (Expression)qualifier;
			object.setResultIsConsumed(true);
			final Type objectType = object.type();
			Declaration odecl = null;
			if (objectType.isntError()) {
				odecl = objectType.enclosedScope().lookupObjectDeclarationRecursive(javaIdString);
			}
		
			if (null != odecl) {
				type = odecl.type();
				assert type != null;
				
				if (null != ctxABELIAN_INCREMENT_OP) {
					expression = new QualifiedIdentifierExpressionUnaryOp(object, javaIdString, type, ctxABELIAN_INCREMENT_OP.getText(), preOrPost);
				} else {
					expression = new QualifiedIdentifierExpression(object, javaIdString, type);
				}
				
				if (odecl instanceof ObjectDeclaration) {
					final ObjectDeclaration odeclAsOdecl = (ObjectDeclaration)odecl;
					final boolean isAccessible = currentScope_.canAccessDeclarationWithAccessibility(
							odeclAsOdecl, odeclAsOdecl.accessQualifier_, ctxGetStart);
					if (isAccessible == false) {
						errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart,
								"Cannot access expression `", expression.getText(),
								"' with `", odeclAsOdecl.accessQualifier_.asString(), "' access qualifier.", "");
					} else if (ConfigurationOptions.enforceObjectEncapsulation() &&
							AccessQualifier.PrivateAccess == odeclAsOdecl.accessQualifier_) {
						// Can access private only if it is self
						if ("IdentifierExpression".equals(qualifier.getClass().getSimpleName())) {
							final IdentifierExpression ie = (IdentifierExpression)qualifier;
							final String identifierName = ie.name();
							if (!"this".equals(identifierName)) {
								errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart,
										"Cannot access expression `", expression.getText(),
										"' with `private' access qualifier across instances: violates encapsulation.", "", "", "");
							}
						}
					}
				} else if (odecl.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
							"Cannot access expression `", expression.getText(),
							"' with non-object type declaration", "");
				}
			} else if (objectType instanceof InterfaceType) {
				// Could be a method invocation without the paren. NEW
				final InterfaceType interfaceType = (InterfaceType) objectType;
				final StaticScope interfaceTypeScope = interfaceType.enclosedScope();
				final InterfaceDeclaration interfaceDeclaration =
							(InterfaceDeclaration)interfaceTypeScope.associatedDeclaration();
				final ActualArgumentList argList = new ActualArgumentList();
				argList.addActualArgument(object);
				final MethodSignature methodSignature = interfaceDeclaration.lookupMethodSignatureDeclaration(javaIdString, argList);
				if (null != methodSignature) {
					// If it is a message shorthand it should have no user parameter
					if (0 == methodSignature.formalParameterList().userParameterCount()) {
						// This is it. NEW.
						final Type objectMegatype = Expression.nearestEnclosingMegaTypeOf(currentScope_);
						// if (objectMegatype == null) {
						// 	assert (objectMegatype != null);
						// }
						final boolean isPolymorphic = true;	// a guess...
						final MethodInvocationEnvironmentClass invokingEnvironment = currentScope_.methodInvocationEnvironmentClass();
						final Message message = new Message(javaIdString, argList, ctxGetStart, objectMegatype);
						expression = new MessageExpression(object, message,
								methodSignature.type(), ctxGetStart, false, invokingEnvironment,
								object.type().enclosedScope().methodInvocationEnvironmentClass(),
								isPolymorphic);
					}
				} else {
					assert (false);		// worth investigating
				}
			} else if (null == (expression = degenerateProcedureCheck(qualifier, objectType, javaIdString, ctxGetStart))){
				if (object.isntError() && object.type().isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Field `", javaIdString,
						"' not found as member of `", object.type().name() + "'.");
				}
				type = new ErrorType();
				odecl = new ErrorDeclaration("");
				expression = new ErrorExpression(null);
			}
		}
		
		return null == expression? new ErrorExpression(null): expression;
	}
	
	private Expression degenerateProcedureCheck(final ExpressionStackAPI object, final Type objectType,
			final String methodSelectorName, final Token token) {
		// Eiffel-style feature invocation
		Expression retval = null;

		if (object instanceof Expression && null != objectType) {
			final ActualArgumentList argumentList = new ActualArgumentList();
			
			if (objectType instanceof RoleType) {
				IdentifierExpression currentContext = new IdentifierExpression("current$context", currentContext_.type(),
						currentScope_, token);
				if (((RoleType)objectType).isAParameterlessRequiresMethod(methodSelectorName) == false) {
					argumentList.addFirstActualParameter(currentContext);
				}
				argumentList.addActualArgument((Expression)object);
			} else {
				argumentList.addFirstActualParameter((Expression)object);
			}
			
			MethodDeclaration methodDecl = objectType.enclosedScope().lookupMethodDeclarationRecursive(
					methodSelectorName, argumentList, false);
			if (null == methodDecl) {
				if (objectType instanceof RoleType) {
					// Check "requires" methods.
					
					final RoleDeclaration roleDeclaration = ((RoleType)objectType).associatedDeclaration();
					final MethodSignature roleMethodSignature = roleDeclaration.lookupRequiredMethodSignatureDeclaration(methodSelectorName);
					if (null != roleMethodSignature) {
						if (0 == roleMethodSignature.formalParameterList().userParameterCount()) {
							// o.k.
							methodDecl = new MethodDeclaration(roleMethodSignature, currentScope_, token);
							methodDecl.setReturnType(roleMethodSignature.returnType());
						}
					}
				}
			}
			
			if (null != methodDecl) {
				final Message message = new Message(methodSelectorName, argumentList, token, Expression.nearestEnclosingMegaTypeOf(currentScope_));
				MethodInvocationEnvironmentClass originMethodClass = MethodInvocationEnvironmentClass.Unknown;
				if (null != currentScope_.associatedDeclaration()) {
					originMethodClass = currentScope_.methodInvocationEnvironmentClass();
				} else {
					final Type anotherType = ((Expression)object).enclosingMegaType();
					if (null != anotherType) {
						final StaticScope anotherScope = anotherType.enclosedScope();
						originMethodClass = anotherScope.methodInvocationEnvironmentClass();
					} else {
						originMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;	// outermost scope
					}
				}
				
				MethodInvocationEnvironmentClass targetMethodClass = null;
				if (objectType instanceof RoleType) {
					// Requires methods get ClassEnvironment designation
					final RoleType roleType = (RoleType)object.type();
					final RoleDeclaration roleDecl = (RoleDeclaration)roleType.associatedDeclaration();
					final MethodSignature requiredSignatureDecl = roleDecl.lookupRequiredMethodSignatureDeclaration(message.selectorName());
					final MethodSignature publishedDecl = null == requiredSignatureDecl? null: roleDecl.lookupPublishedSignatureDeclaration(requiredSignatureDecl);
					if (null != requiredSignatureDecl) {
						if (null == publishedDecl) {
							final StaticScope currentMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
							errorHook6p2(ErrorIncidenceType.Fatal, token,
									"Context script `", currentMethodScope.associatedDeclaration().name(),
									"' may enact only Role scripts. Script `",
									message.selectorName() + message.argumentList().selflessGetText(),
									"' is an instance script from a class and is inaccessible to Context `",
									roleDecl.enclosingScope().name() + "'.");
						} else {
							targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
						}
					} else {
						targetMethodClass = MethodInvocationEnvironmentClass.RoleEnvironment;
					}
				} else if (objectType instanceof ContextType) targetMethodClass = MethodInvocationEnvironmentClass.ContextEnvironment;
				else targetMethodClass = MethodInvocationEnvironmentClass.ClassEnvironment;
				
				final boolean isStatic = (null != methodDecl) && (null != methodDecl.signature()) && methodDecl.signature().isStatic();
			
				boolean isPolymorphic = true;
				if (amInConstructor()) {
					if (object instanceof IdentifierExpression) {
						// Don't dynamically dispatch methods from within a constructor
						if (((IdentifierExpression)object).name().equals("this")) {
							isPolymorphic = false;
						}
					}
				}
				
				// MAP>>KEYS
				Type methodReturnType = methodDecl.returnType();
				
				if (message.selectorName().equals("keys")) {
					if (methodReturnType instanceof TemplateType) {
						assert (objectType.name().startsWith("Map<"));
						String keyName = objectType.name().substring(4);
						final int indexOfComma = keyName.indexOf(',');
						keyName = keyName.substring(0, indexOfComma);
						final String returnTypeName = methodReturnType.name() + "<" + keyName + ">";
					
						// We want the type Declaration for the Set<int>
						Type type = StaticScope.globalScope().lookupTypeDeclaration(returnTypeName);
						if (null == type) {
							// Then we need to declare it
							type = SetClass.addSetOfXTypeNamedY(null, returnTypeName);
						}
						final StaticScope returnTypeScope = type.enclosedScope();
						final Declaration returnTypeDeclaration = returnTypeScope.associatedDeclaration();
						      ClassDeclaration returnTypeClassDeclaration = null;
						if (returnTypeDeclaration instanceof ClassDeclaration) {
							returnTypeClassDeclaration = (ClassDeclaration) returnTypeDeclaration;
							// ... else it is a reportable error?
						}
						
						// Actually, getting "type" above is probably enough
						methodReturnType = returnTypeDeclaration.type();
					}

				}
				
				retval = new MessageExpression((Expression) object, message, methodReturnType,
						token, isStatic, originMethodClass, targetMethodClass, isPolymorphic);
			}
		}
		return retval;
	}
	
	public <ExprType> Expression newExpr(final List<ParseTree> ctxChildren, final Token ctxGetStart,
			final ExprType ctxExpr, final MessageContext ctxMessage) {
		// : NEW message
		// | NEW type_name '[' expr ']'
		
		// Called in all passes.
		Expression expression = null;	// guaranteed non-null return
		final Message message = parsingData_.popMessage();
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		if (null == ctxExpr && null != ctxMessage){
			// : 'new' message
			final String classOrContextName = message.selectorName(); // I know -- kludge ...
			final Type type = currentScope_.lookupTypeDeclarationRecursive(classOrContextName);
			if ((type instanceof ClassType) == false && (type instanceof ContextType) == false
					&& (type instanceof BuiltInType) == false) {
				if (type instanceof TemplateParameterType) {
					// then it's Ok
					expression = new NewExpression(type, message, ctxMessage.getStart(), enclosingMegaType);
					addSelfAccordingToPass(type, message, currentScope_);
				} else {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "`new ", classOrContextName, "': can apply `new' only to a class or Context type", "");
					expression = new ErrorExpression(null);
				}
			} else {
				// On the first pass, message doesn't yet have an argument list
				expression = new NewExpression(type, message, ctxMessage.getStart(), enclosingMegaType);
				
				// This adds a hokey argument to the message that
				// is used mainly for signature checking - to see
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
			final ExpressionStackAPI raw_type_expression = parsingData_.popRawExpression();
			assert raw_type_expression instanceof Type;
			final Type type_name_expression = (Type)raw_type_expression;
			final String typeName = type_name_expression.name();
			final Type type = currentScope_.lookupTypeDeclarationRecursive(typeName);
			if (null == type) {
				if (expr.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "'new ", typeName, " [] for undefined type: ", typeName);
				}
				expression = new ErrorExpression(expr);
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
	public void addSelfAccordingToPass(final Type type, final Message message, final StaticScope scope) {
		/* Nothing */
	}
	public void ctorCheck(final Type type, final Message message, final Token token) {
		/* Nothing */
	}
	
	protected boolean amInConstructor() {
		boolean retval = false;
		final Declaration declaration = currentScope_.associatedDeclaration();
		if (declaration instanceof MethodDeclaration) {
			final MethodDeclaration currentMethodDeclaration = (MethodDeclaration)declaration;
			final Type methodsMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
			final String methodName = currentMethodDeclaration.name();
			final String megaTypeName = methodsMegaType.name();
			retval = methodName.equals(megaTypeName);
		}
		return retval;
	}

	// This is a template function mainly for historial reasons data back to
	// expr and sexpr, and should be updated. We separated those non-terminals
	// as a workaround for left-recursion in some of the productions. In some sense
	// we blew up the grammar to avoid the ambiguity; here, we bring the leaves
	// (the processing for the duplicate productions) back together.
	
	// You find something analogous with exitExpr here in pass 1.
	
	public <ExprType> Expression messageSend(final Token ctxGetStart, final ExprType ctx_abelianAtom,
			final Builtin_type_nameContext ctx_typeName) {
		// | expr '.' message
		// | message
		// Pass 1 version
		// Pop the expression for the indicated object and message
		Expression object = null;
		Type type = null, enclosingMegaType = null;
		MethodDeclaration mdecl = null;
		
		if (null != ctx_abelianAtom) {
			if (parsingData_.currentExpressionExists()) {
				object = parsingData_.popExpression();
			} else {
				object = new ErrorExpression(null);
			}
		} else if (null != ctx_typeName) {
			// e.g. String.join
			final String typeName = ctx_typeName.getText();
			final Type theType = currentScope_.lookupTypeDeclarationRecursive(typeName);
			final Type classType = StaticScope.globalScope().lookupTypeDeclaration("Class");
			object = new IdentifierExpression(theType.name(), classType, classType.enclosedScope().parentScope(),
						ctxGetStart);
		} else {
			// This fix means that you can't call "assert" naked
			// from _main any more (did it ever work?)
			final StaticScope nearestMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
			enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
			if (null == enclosingMegaType || null == nearestMethodScope) {
				errorHook5p1(ErrorIncidenceType.Fatal, ctxGetStart, "Method `", ctxGetStart.getText(),
									"' cannot be found.", "");
				object = new ErrorExpression(null);
			} else {
				object = new IdentifierExpression("this", enclosingMegaType, nearestMethodScope, ctxGetStart);
	
			}
		}
		assert null != object;
			
		final Message message = parsingData_.popMessage();

		if (null == enclosingMegaType && (object instanceof NullExpression ||
				object.isError())) {
			// Because this here is Pass 1 code this really does nothing.
			// We'll catch it again on Pass 2
			errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
					"Invoking method `", message.selectorName(), "' on implied object `this' in a non-object context.", "");
		} else {
			final Type objectType = object.type();
			if (null == objectType) return new ErrorExpression(object);	// error stumbling avoidance
				
			final String methodSelectorName = message.selectorName();
			final ClassDeclaration classdecl = currentScope_.lookupClassDeclarationRecursive(objectType.name());
			mdecl = classdecl != null?
							classdecl.enclosedScope().lookupMethodDeclaration(methodSelectorName, null, true):
							null;
							
			if (null != mdecl) {
				if (objectType.name().equals("Class")) {
					// Is of the form ClassType.classMethod()
					assert object instanceof IdentifierExpression;
					if (false == mdecl.signature().isStatic()) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
								"Attempt to call instance method `" + mdecl.signature().getText(),
								"' as though it were a static method of class `", objectType.name(), "'.");
					}
				}
			}
											
			if (null == mdecl) {
				// final String className = classdecl != null? classdecl.name(): " <unresolved>.";
				// skip it - we'll barked at the user in pass 2
				// errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart.getLine(), "Script `", methodSelectorName, "' not declared in class ", className);
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
		final MethodInvocationEnvironmentClass targetMethodClass = null == message.enclosingMegaType()?
				MethodInvocationEnvironmentClass.ClassEnvironment:
				message.enclosingMegaType().enclosedScope().methodInvocationEnvironmentClass();
		final boolean isStatic = (null != mdecl) && (null != mdecl.signature()) && mdecl.signature().isStatic();
	
		boolean isPolymorphic = true;
		if (amInConstructor()) {
			if (object instanceof IdentifierExpression) {
				// Don't dynamically dispatch methods from within a constructor
				if (((IdentifierExpression)object).name().equals("this")) {
					isPolymorphic = false;
				}
			}
		}
		
		if (null != type) {
			// Should probably never be true - here...
			final boolean isAConstructor = type.name().equals(message.selectorName());
			if (false == isAConstructor) {
				// Update the message type properly. Constructors are weird in
				// that the message type is void whereas the expression type is
				// in terms of the thing being constructed.
				message.setReturnType(type);
			}
		}

		final MessageExpression retval = new MessageExpression(object, message, type, ctxGetStart, isStatic,
				originMethodClass, targetMethodClass, isPolymorphic);

		return retval;
	}
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationIn(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 1 version. Pass 2 / 3 version ignores "this" in signature,
		// and checks the signature
		final StaticScope classScope = classDecl.enclosedScope();
		return classScope.lookupMethodDeclaration(methodSelectorName, parameterList, true);
	}
	protected MethodDeclaration processReturnTypeLookupMethodDeclarationIgnoringRoleStuffIn(final TypeDeclaration classDecl, final String methodSelectorName, final ActualOrFormalParameterList parameterList) {
		// Pass 1 version. Pass 2 / 3 version is the same for now,
		// but checks the signature
		final StaticScope classScope = classDecl.enclosedScope();
		return classScope.lookupMethodDeclarationIgnoringRoleStuff(methodSelectorName, parameterList);
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

	protected Type processReturnType(final Token ctxGetStart, final Expression object, final Type objectType, final Message message) {
		final String objectTypeName = objectType.name();
		final ClassDeclaration classDecl = currentScope_.lookupClassDeclarationRecursive(objectTypeName);
		final RoleDeclaration roleDecl = currentScope_.lookupRoleOrStagePropDeclarationRecursive(objectTypeName);
		final ContextDeclaration contextDecl = currentScope_.lookupContextDeclarationRecursive(objectTypeName);
		final InterfaceDeclaration interfaceDecl = currentScope_.lookupInterfaceDeclarationRecursive(objectTypeName);
		
		final String methodSelectorName = message.selectorName();
		assert null != methodSelectorName;
		
		MethodDeclaration mdecl = null;
		Type returnType = null;
		final ActualArgumentList actualArgumentList = message.argumentList();
		boolean roleHint = false;
		
		if (null != classDecl) {
			mdecl = processReturnTypeLookupMethodDeclarationUpInheritanceHierarchy(classDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				final Type currentEnclosingType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
				if (currentEnclosingType instanceof TemplateType) {
					// Ingore parameters as in Pass 1. We may not find a match with a template type...
					mdecl = classDecl.enclosedScope().lookupMethodDeclarationRecursive(methodSelectorName, actualArgumentList, true);
					if (null == mdecl && actualArgumentList.isntError()) {
						errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart, "Script `",
								methodSelectorName + actualArgumentList.selflessGetText(),
								"' not declared in class `", classDecl.name(), "'.", "");
					}
				} else if (actualArgumentList.isntError()) {
					// Look at Object. Could be an assert or something
					final ClassDeclaration objectDecl = currentScope_.lookupClassDeclarationRecursive("Object");
					assert null != objectDecl;
					mdecl = processReturnTypeLookupMethodDeclarationIgnoringRoleStuffIn(objectDecl, methodSelectorName, actualArgumentList);
					
					if (null == mdecl) {
						errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart, "Script `",
								methodSelectorName + actualArgumentList.selflessGetText(),
								"' not declared in class `", classDecl.name(), "'.", "");
					}
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
							errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
									"Call of base class constructor `",
									baseClassName,
									"' must be the first statement in the derived class constructor.",
									"");
							noerrors = false;
						}
						if (mdecl.accessQualifier() != AccessQualifier.PublicAccess) {
							errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
									"Call of base class constructor for class `",
									baseClassName,
									"', which is not accessible to class `",
									classDecl.name() + "'.");
							noerrors = false;
						}
					}
					
					// Make sure the current method is a constructor!
					final MethodSignature currentMethod = parsingData_.currentMethodSignature();
					final ClassDeclaration currentClass = parsingData_.currentClassDeclaration();
					if (currentClass.name().equals(currentMethod.name()) == false) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
								"Base class constructor `",
								baseClassName,
								"' can be explicitly invoked only from a derived class constructor.",
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
				// First, check in requires list
				final Map<String, List<MethodSignature>> requiresSection = roleDecl.requiredSelfSignatures();
				final List<MethodSignature> possibleRequiredFunctions = requiresSection.get(methodSelectorName);
				if (null != possibleRequiredFunctions) {
					for (final MethodSignature aRequiredFunction : possibleRequiredFunctions) {
						// We don't insist on parameter type matching in Pass 1. Pass 2 will catch that.
						// But we'll try.
						if (aRequiredFunction.formalParameterList().alignsWithUsingConversion(actualArgumentList)) {
							mdecl = new MethodDeclaration(aRequiredFunction, roleDecl.enclosedScope(), aRequiredFunction.token());
							mdecl.addParameterList(aRequiredFunction.formalParameterList());
							mdecl.setReturnType(mdecl.returnType());
							break;
						}
					}
					
					// Even if the signatures don't match, it may be because we have incomplete
					// type information. For now, give it a pass if the selector name is O.K.
					if (possibleRequiredFunctions.size() > 0) {
						final MethodSignature aRequiredFunction = possibleRequiredFunctions.get(0);
						mdecl = new MethodDeclaration(aRequiredFunction, roleDecl.enclosedScope(), aRequiredFunction.token());
						mdecl.addParameterList(aRequiredFunction.formalParameterList());
						mdecl.setReturnType(mdecl.returnType());
					}
				}
				
				if (null == mdecl) {
					// If this is a Role variable, it's fair game to look for
					// methods in what will be a base class for every Role-player:
					// class object
					final ClassDeclaration objectDecl = currentScope_.lookupClassDeclarationRecursive("Object");
					assert null != objectDecl;
					
					mdecl = processReturnTypeLookupMethodDeclarationIn(objectDecl, methodSelectorName, actualArgumentList);
					if (null == mdecl) {
						mdecl = processReturnTypeLookupMethodDeclarationIgnoringRoleStuffIn(objectDecl, methodSelectorName, actualArgumentList);
						roleHint = true;
					}
				}
			}
			if (null == mdecl) {
				if (actualArgumentList.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Script `",
						methodSelectorName + actualArgumentList.selflessGetText(),
						"' not declared in Role `", roleDecl.name() + "'.");
				}
				if (message.lineNumber() < roleDecl.lineNumber()) {
					final MethodSignature enclosingMethod = parsingData_.currentMethodSignature();
					if (null != enclosingMethod) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "\tTry moving the declaration of `", roleDecl.name(),
								"' before the definition of method `", enclosingMethod.getText() + "'.");
					} else {
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "\tTry moving the declaration of `", roleDecl.name(),
							"' before the invocation of `", methodSelectorName+ "'.");
					}
				}
			}
		} else if (null != contextDecl) {
			mdecl = processReturnTypeLookupMethodDeclarationUpInheritanceHierarchy(contextDecl, methodSelectorName, actualArgumentList);
			if (null == mdecl) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Script `", methodSelectorName + actualArgumentList.selflessGetText(),
						"' not declared in Context `", contextDecl.name() + "'.");
			}
		} else if (null != interfaceDecl) {
			// Methods aren't filled in for template interfaces until Pass 4,
			// so don't gripe about problems until then
			boolean cutItSomeSlack = false;
			final InterfaceType interfaceType = (InterfaceType)interfaceDecl.type();
			cutItSomeSlack = interfaceType.isTemplateInstantiation();
			cutItSomeSlack &= !(this instanceof Pass4Listener);
			
			final MethodSignature methodSignature = interfaceDecl.lookupMethodSignatureDeclaration(methodSelectorName, actualArgumentList);
			if (null == methodSignature) {
				if (false == cutItSomeSlack) {
					if (actualArgumentList.isntError()) {
						errorHook5p2SpecialHook(ErrorIncidenceType.Fatal, ctxGetStart, "Script `", methodSelectorName + actualArgumentList.selflessGetText(),
								"' not declared in Interface `", interfaceDecl.name() + "'.");
					}
				}
				returnType = new ErrorType();
			} else {
				returnType = methodSignature.returnType();
			}
		} else if (objectTypeName.equals("Class")) {
			final ClassDeclaration classDeclaration = currentScope_.lookupClassDeclarationRecursive(object.name());
			if (null == classDeclaration) {
				if (object.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Cannot find class, Role, or interface `",
							object.name(), "'", "");
				}
			} else {
				mdecl = classDeclaration.enclosedScope().lookupMethodDeclaration(methodSelectorName, actualArgumentList, false);
				if (null == mdecl) {
					mdecl = classDeclaration.enclosedScope().lookupMethodDeclarationWithConversionIgnoringParameter(
							methodSelectorName, actualArgumentList, false, /*parameterToIgnore*/ null);
					if (null == mdecl) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
								"Cannot find static script `" + methodSelectorName
								+ actualArgumentList.getText(),
							"' of class `", object.name(), "'.");
					}
				}
			}
		} else if (objectTypeName.endsWith("_$array")) {
			if (methodSelectorName.equals("size") && actualArgumentList.count() == 1) {
				returnType = StaticScope.globalScope().lookupTypeDeclaration("int");	// is O.K.
			} else if (methodSelectorName.equals("at") && actualArgumentList.count() == 2) {
				returnType = ((ArrayType)objectType).baseType();						// is O.K.
			} else if (methodSelectorName.equals("atPut") && actualArgumentList.count() == 3) {
				returnType = StaticScope.globalScope().lookupTypeDeclaration("void");	// is O.K.
			} else {
				if (object.name().length() > 0) {
					if (object.isntError()) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Cannot find class, Role, or interface for `",
							object.name(), "'.", "");
					}
				} else {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Cannot find class, Role, or interface of this ", "type", "", "");
				}
				assert null == mdecl;
			}
		} else {
			if (object.name().length() > 0) {
				if (object.isntError() && objectType.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Cannot find class, Role, or interface for `",
							object.name(), "'.", "");
				}
			} else {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart, "Cannot find class, Role, or interface of this ", "type", "", "");
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
			this.typeCheckIgnoringParameter(formals, actuals, mdecl, typeDecl, "this", ctxGetStart, roleHint);
			
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
			final RoleDeclaration roleDeclaration = contextDeclaration.enclosedScope().lookupRoleOrStagePropDeclaration(idName);
			retval = roleDeclaration;
		}
		return retval;
	}
	
	private class ClassAndObjectDeclaration {
		public ClassAndObjectDeclaration(final ClassType classType, final ObjectDeclaration objectDecl) {
			classType_ = classType;
			objectDecl_ = objectDecl;
		}
		
		public ClassType classType() { return classType_; }
		public ObjectDeclaration objectDecl() { return objectDecl_; }
		
		private ClassType classType_;
		private ObjectDeclaration objectDecl_;
	}
	
	private ClassAndObjectDeclaration isMemberOfEnclosingObject(final String idName) {
		ClassAndObjectDeclaration retval = null;
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(currentScope_);
		if (null != nearestEnclosingMegaType) {
			if (nearestEnclosingMegaType instanceof ClassType) {
				ClassType nearestEnclosingClass = (ClassType) nearestEnclosingMegaType;
				do {
					final StaticScope classScope = nearestEnclosingClass.enclosedScope();
					final ObjectDeclaration odecl = classScope.lookupObjectDeclaration(idName);
					if (null != retval) {
						retval = new ClassAndObjectDeclaration(nearestEnclosingClass, odecl);
						break;
					} else {
						// Try the base class
						nearestEnclosingClass = nearestEnclosingClass.baseClass();
					}
				} while (null != nearestEnclosingClass);
			}
		}
		return retval;
	}
	public Expression idExpr(final TerminalNode ctxJAVA_ID, final Token ctxGetStart) {
		// Pass 1 version
		final StaticScope globalScope = StaticScope.globalScope();
		Expression expression = null;
		Type type = null;
		StaticScope declaringScope = null;
		final String idName = ctxJAVA_ID.getText();
		ObjectDeclaration objdecl = currentScope_.lookupObjectDeclarationRecursive(idName);
		final RoleDeclaration roleDecl = currentScope_.lookupRoleOrStagePropDeclarationRecursive(idName);
		final StaticScope nearestEnclosingMethodScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
		ClassAndObjectDeclaration classAndObjectDeclaration = null;
		if (idName.equals("index") || idName.equals("lastIndex")) {
			// This is a legal identifier if invoked from within the
			// scope of a Role, where the Role is declared as a Role
			// vector type
			type = StaticScope.globalScope().lookupTypeDeclaration("void");	// default/error value
			expression = new NullExpression();
			if (null == currentRoleOrStageProp_) {
				errorHook5p2(ErrorIncidenceType.Fatal, ctxGetStart,
						"Symbol `", idName, "' may be used only within certain Role methods.", "");
				type = new ErrorType();
			} else {
				if (currentRoleOrStageProp_.isArray()) {
					expression = idName.equals("index")?
							new IndexExpression(currentRoleOrStageProp_, currentContext_):
								new LastIndexExpression(currentRoleOrStageProp_, currentContext_);
				} else {
					errorHook6p2(ErrorIncidenceType.Fatal, ctxGetStart,
							"Symbol `", idName, "' may be used only within a Role vector method. The Role ",
							currentRoleOrStageProp_.name(), " is a not a vector.", "");
					type = new ErrorType();
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
			expression = new IdentifierExpression(idName, type, declaringScope, ctxGetStart);
		} else if (null != roleDecl) {
			// Someone is invoking a role. Cool.
			declaringScope = roleDecl.enclosingScope();
			final Type rawRoleType = declaringScope.lookupTypeDeclaration(idName);	// Type$RoleType
			if (null != rawRoleType) {	// stumbling check
				assert rawRoleType instanceof RoleType;
				final RoleType roleType = (RoleType)rawRoleType;
				if (this.isInsideMethodDeclaration(ctxJAVA_ID)) {
					final IdentifierExpression qualifier = new IdentifierExpression("this", roleType, nearestEnclosingMethodScope, ctxGetStart);
					qualifier.setResultIsConsumed(true);
					expression = new QualifiedIdentifierExpression(qualifier, idName, roleType);
				} else {
					errorHook5p2(ErrorIncidenceType.Unimplemented, ctxGetStart,
							"Static initializers for Roles are unimplemented.", "", "", "");
					expression = new ErrorExpression(null);
				}
			} else {
				expression = new ErrorExpression(null);
			}
		} else if (null != (classAndObjectDeclaration = isMemberOfEnclosingObject(idName))) {
			objdecl = classAndObjectDeclaration.objectDecl();
			final Type classType = classAndObjectDeclaration.classType();
			final IdentifierExpression qualifier = new IdentifierExpression("this", classType, nearestEnclosingMethodScope, ctxGetStart);
			qualifier.setResultIsConsumed(true);
			expression = new QualifiedIdentifierExpression(qualifier, idName, objdecl.type());
		} else {
			final ClassDeclaration cdecl = currentScope_.lookupClassDeclarationRecursive(idName);
			if (null != cdecl) {
				type = globalScope.lookupTypeDeclaration("Class");
				declaringScope = globalScope;
			} else if (null == declaringScope) {
				// NOTE: This will also lump in references to Role identifiers
				// They are distinguished by their enclosing scope
				declaringScope = Expression.nearestEnclosingMethodScopeAround(currentScope_);
			}
				
			if (null == type) {
				type = StaticScope.globalScope().lookupTypeDeclaration("void");
			}
				
			expression = new IdentifierExpression(idName, type, declaringScope, ctxGetStart);
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
			} else if (walker instanceof Type_and_expr_and_decl_listContext) {
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
			} else if (walker instanceof BlockContext) {
				;
			} else if (walker instanceof For_exprContext) {
				;
			} else if (walker instanceof While_exprContext) {
				;
			} else if (walker instanceof Boolean_exprContext) {
				;
			} else if (walker instanceof If_exprContext) {
				;
			} else if (walker instanceof Boolean_unary_opContext) {
				;
			} else if (walker instanceof Boolean_productContext) {
				;
			} else if (walker instanceof Switch_bodyContext) {
				;
			} else if (walker instanceof Switch_exprContext) {
				;
			} else if (walker instanceof Do_while_exprContext) {
				;
			} else if (walker instanceof Boolean_atomContext) {	// pong.k
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
			final MethodDeclaration mdecl, final TypeDeclaration classdecl, final String parameterToIgnore,
			final Token ctxGetStart, final boolean roleHint)
	{
		/* Nothing */
	}
	protected void reportMismatchesWith(final Token token, final RoleType lhsType, final Type rhsType) {
		/* Nothing */
	}
	private void checkTemplateParameterTypeCompatibilityOf(final Token token, final Type lhs, final Type rhs) {
		boolean error = false;
		final Type lhsType = lhs.type(), rhsType = rhs.type();
		final String lhsTypeName = lhsType.name();
		final String rhsTypeName = rhsType.name();
		final int index1 = lhsTypeName.indexOf('<');
		final String lhsArgList = lhsTypeName.substring(index1 + 1, lhsTypeName.length() - index1 + 2);
		final String rhsArgList = rhsTypeName.substring(index1 + 1, rhsTypeName.length() - index1 + 2);
		final String [] lhsParameters = lhsArgList.split(",");
		final String [] rhsParameters = rhsArgList.split(",");
		final int l = rhsParameters.length;
		if (l == lhsParameters.length) {
			for (int i = 0; i < l; i++) {
				final String lhsParameterTypeName = lhsParameters[i];
				final String rhsParameterTypeName = rhsParameters[i];
				if (lhsParameterTypeName.equals(rhsParameterTypeName) ) {
					continue;
				} else {
					final ClassDeclaration c1decl = currentScope_.lookupClassDeclarationRecursive(lhsParameterTypeName);
					final ClassDeclaration c2decl = currentScope_.lookupClassDeclarationRecursive(rhsParameterTypeName);
					if (c1decl != null && c2decl != null) {
						final Type c1type = c1decl.type(), c2type = c2decl.type();
						final boolean tf = c1type.canBeConvertedFrom(c2type, token, this);
						if (false == tf) {
							error = true;
							break;
						}
					} else {
						error = true;
						break;
					}
				}
			}
		} else {
			error = true;
		}
		if (error) {
			errorHook6p2(ErrorIncidenceType.Fatal, token, "Type of `", lhs.getText(),
				"' is incompatible with expression type `", rhsType.name(), "'.", "");
		}
	}
	public <ContextArgType extends ParserRuleContext> Expression assignmentExpr(final Expression lhs, final String operator, final Expression rhs,
			final ContextArgType ctx) {
		// abelian_expr ASSIGN expr
		assert null != rhs;
		assert null != lhs;
		
		final Token token = ctx.getStart();
			
		final Type lhsType = lhs.type(), rhsType = rhs.type();
		
		boolean tf = lhsType instanceof RoleType;
		tf = null != rhsType;
		if (lhsType instanceof RoleType && rhsType instanceof ArrayType) {
			if (((RoleType)lhsType).isArray()) {
				tf = lhsType.canBeConvertedFrom(((ArrayType)rhsType).baseType(), token, this);
			} else {
				// Maybe the Role just wants to be played by an array, building on
				// its at and atPut interface
				tf = lhsType.canBeConvertedFrom(rhsType, token, this);
				if (tf == false) {
					if (lhs.isntError() && rhs.isntError() && rhsType.isntError()) {
						errorHook6p2(ErrorIncidenceType.Fatal, token, "Type of `", lhs.getText(),
							"' is incompatible with expression type `", rhsType.name(), "'.", "");
					}
				}
			}
		} else if (null != lhsType && null != rhsType) {
			tf = lhsType.canBeConvertedFrom(rhsType, token, this);
		} else {
			tf = false;
		}
		
		if (lhs.name().equals("this")) {
			errorHook5p2(ErrorIncidenceType.Noncompliant, token,
					"You're on your own here.", "", "", "");
		}
		
		if (lhs.name().equals("index") || lhs.name().equals("lastIndex")) {
			errorHook5p2(ErrorIncidenceType.Fatal, token,
					"`index' is a reserved word which is a read-only property of a Role vector element,",
					" and may not be assigned.", "", "");
		} else if (lhsType instanceof RoleType && null != rhsType && rhsType instanceof ArrayType) {
			final Type baseType = ((ArrayType)rhsType).baseType();
			if (lhsType.canBeConvertedFrom(baseType)) {
				this.checkRoleClassNameCollision((RoleType)lhsType, baseType, token);
			} else {
				// Maybe the Role is trying to be an array, using the at and atPut
				// facilities...
				if (lhsType.canBeConvertedFrom(rhsType)) {
					this.checkRoleClassNameCollision((RoleType)lhsType, rhsType, token);
				} else {
					errorHook6p2(ErrorIncidenceType.Fatal, token, "Role vector elements of type `", lhsType.name(),
							"' cannot be played by objects of type `",
							((ArrayType)rhsType).baseType().name(), "':", "");
				}
			}
		} else if (lhsType instanceof RoleType && null != rhsType) {
			final boolean isRoleArray = lhsType instanceof RoleType &&
					((RoleType)lhsType).associatedDeclaration() instanceof RoleArrayDeclaration;
			final boolean isStagePropArray = lhsType instanceof StagePropType &&
					((StagePropType)lhsType).associatedDeclaration() instanceof StagePropArrayDeclaration;
			if ((isRoleArray || isStagePropArray) && rhsType instanceof ClassType && rhsType.name().startsWith("List<")) {
				// includes stage props
				final String ofWhatThisIsAList = rhsType.name().substring(5, rhsType.name().length() - 1);
				final Type rhsBaseType = currentScope_.lookupTypeDeclarationRecursive(ofWhatThisIsAList);
				if (null != rhsBaseType && rhsBaseType.isntError()) {	// error stumbling check
					tf = lhsType.canBeConvertedFrom(rhsBaseType, token, this);
					if (false == tf && lhs.isntError() && rhs.isntError()) {
						errorHook6p2(ErrorIncidenceType.Fatal, token, "Roles in `", lhsType.name(),
								"' cannot be played by objects of type `", rhsBaseType.name(), "':", "");
						this.reportMismatchesWith(token, (RoleType)lhsType, rhsBaseType);
					}
				}
			} else if (lhsType.canBeConvertedFrom(rhsType) == false && lhs.isntError() &&
					rhs.isntError() && lhsType.isntError() && rhsType.isntError()) {
				errorHook6p2(ErrorIncidenceType.Fatal, token, "Role `", lhsType.name(),
						"' cannot be played by object of type `", rhsType.name(), "':", "");
				this.reportMismatchesWith(token, (RoleType)lhsType, rhsType);
			}
			this.checkRoleClassNameCollision((RoleType)lhsType, rhsType, token);
		} else if (null != lhsType && null != rhsType && lhsType.canBeConvertedFrom(rhsType) == false
				&& lhs.isntError() && rhs.isntError() && rhsType.isntError()) {
			final String lhsTypeName = lhsType.name();
			final String rhsTypeName = rhsType.name();
			final boolean lhsIsTemplateType = lhsTypeName.indexOf("<") > 0  && lhsTypeName.indexOf(">") > 0;
			final boolean rhsIsTemplateType = rhsTypeName.indexOf("<") > 0  && rhsTypeName.indexOf(">") > 0;
			if (lhsIsTemplateType && rhsIsTemplateType) {
				// Look for covariance in template argument types
				checkTemplateParameterTypeCompatibilityOf(token, lhsType, rhsType);
			} else {
				errorHook6p2(ErrorIncidenceType.Fatal, token, "Type of `", lhsTypeName,
					"' is incompatible with expression type `", rhsType.name(), "'.", "");
			}
		} else if (lhs instanceof ArrayIndexExpression) {
			final Type anotherLhsType = ((ArrayIndexExpression)lhs).baseType();
			if (null != anotherLhsType && null != rhsType &&
					anotherLhsType.canBeConvertedFrom(rhsType) == false &&
					lhs.isntError() && rhs.isntError() && rhsType.isntError()) {
				errorHook6p2(ErrorIncidenceType.Fatal, token, "Type of `", lhs.getText(),
						"' is incompatible with expression type `", rhsType.name(), "'.", "");
			}
		} else if (lhs instanceof RoleArrayIndexExpression) {
			if (lhsType.canBeConvertedFrom(rhsType) == false && lhs.isntError() &&
					rhs.isntError()) {
				errorHook6p2(ErrorIncidenceType.Fatal, token, "Role `", lhsType.name(),
						"' cannot be played by object of type `", rhsType.name(), "':", "");
				this.reportMismatchesWith(token, (RoleType)lhsType, rhsType);
			}
		} else if (lhs instanceof MessageExpression && lhs.name().equals("at")) {
			// See if LHS has at atPut
			boolean found = false;
			Expression retval = null;
			final MessageExpression atExpr = (MessageExpression)lhs;
			final MethodDeclaration atPut = atExpr.objectExpression().type().enclosedScope().lookupMethodDeclaration("atPut", null, true);
			if (atExpr.objectExpression().type().name().startsWith("List<") && null != atPut) {
				// There IS an atPut
				final ActualArgumentList paramList = new ActualArgumentList();
				final Expression self = (Expression)atExpr.message().argumentList().argumentAtPosition(0);
				final Expression indexExpr = (Expression)atExpr.message().argumentList().argumentAtPosition(1);
				paramList.addFirstActualParameter(self);
				//paramList.addActualArgument(self);
				self.setResultIsConsumed(true);
				paramList.addActualArgument(indexExpr);
				indexExpr.setResultIsConsumed(true);
				paramList.addActualArgument(rhs);
				rhs.setResultIsConsumed(true);
				
				if (indexExpr.type().name().equals("int") == false) {
					errorHook5p2(ErrorIncidenceType.Fatal, token,
							"Index must evaluate to an int.", "", "", "");
					retval = new ErrorExpression(indexExpr);
					found = true;		// a constructive lie
				} else {
					MethodInvocationEnvironmentClass callerEnvClass = MethodInvocationEnvironmentClass.ClassEnvironment;
					if (lhs.enclosingMegaType() instanceof ContextType) {
						callerEnvClass = MethodInvocationEnvironmentClass.ContextEnvironment;
					} else if (lhs.enclosingMegaType() instanceof ClassType) {
						callerEnvClass = MethodInvocationEnvironmentClass.ClassEnvironment;
					} else if (lhs.enclosingMegaType() instanceof RoleType) {
						callerEnvClass = MethodInvocationEnvironmentClass.RoleEnvironment;
					} else {
						// cheating...
						callerEnvClass = MethodInvocationEnvironmentClass.GlobalEnvironment;
					}
					
					final Message message = new Message("atPut",
							paramList,
							token,
							lhs.enclosingMegaType());
					
					retval = new MessageExpression(
							self,
							message,
							rhs.type(),
							token,
							false, /*isStatic*/
							callerEnvClass,
							MethodInvocationEnvironmentClass.ClassEnvironment,
							true /*isPolymorphic*/ );
					
					found = true;
				}
			}
			if (found) {
				return retval;
			} else {
				if ((lhs instanceof IdentifierExpression) == false &&
						   (lhs instanceof QualifiedIdentifierExpression) == false &&
						   lhs.isntError() && rhs.isntError()) {
					errorHook5p2(ErrorIncidenceType.Fatal, token,
							"Can assign only to an identifier, qualified identifier, or vector element.",
							"", "", "");
				}
			}
		}
		
		rhs.setResultIsConsumed(true);

		final AssignmentExpression retval = new AssignmentExpression(lhs, operator, rhs, token, this);
		checkForAssignmentViolatingConstness(retval, ctx.getStart());
		
		return retval;
	}
	
	private void checkRoleClassNameCollision(final RoleType lhsType, final Type baseType, Token token) {
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
					errorHook6p2(ErrorIncidenceType.Warning, token,
							"WARNING: Both class `" + baseType.name(), "' and Role `" + lhsType.name(),
							"' contain the same script signature `", correspondingRoleMethod.signature().getText(),
							"'. This results in several scripts of the same name in the same object",
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
		if (null != ctxExpr) {
			final Expression returnExpression = parsingData_.popExpression();
			returnExpression.setResultIsConsumed(true);
			retval = returnExpression;
		}
		return retval;
	}
	
	public void nameCheck(final String name, Token token) {
		if (name.equals("this") || name.equals("Ralph") || name.equals("Sue") || name.equals("index")||
				name.equals("lastIndex")) {
			errorHook5p2(ErrorIncidenceType.Fatal, token,
					"Please avoid the use of the names `this', `Sue', `index', `lastIndex' and `Ralph' for identifiers.",
					"", "", "");
		}
	}
	
	@Override protected void errorHook5p1(final ErrorIncidenceType errorType, final Token t, final String s1, final String s2, final String s3, final String s4) {
		ErrorLogger.error(errorType, t, s1, s2, s3, s4);
	}
	
	protected ParsingData parsingDataArgumentAccordingToPass() {
		return parsingData_;
	}
	
	private void stackSnapshotDebug() {
		parsingData_.stackSnapshotDebug();
	}
	
	public static int kantParserVariableGeneratorCounter_ = 101;
}