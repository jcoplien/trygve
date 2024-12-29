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


import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.ClassOrContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.StagePropType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleArrayDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.ContextType;
import info.fulloo.trygve.declarations.Type.InterfaceType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.declarations.Type.TemplateTypeForAnInterface;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticRoleScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticInterfaceScope;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.Token;

public class Pass0Listener extends KantBaseListener {
	public Pass0Listener(final ParsingData parsingData) {
		parsingData_ = parsingData;
		
		currentScope_ = parsingData_.globalScope();
		currentContext_ = null;
		
		currentRoleOrStageProp_ = null;
		currentInterface_ = null;
		
		printProductionsDebug = false;
		stackSnapshotDebug = false;
	}
	
	@Override public void enterProgram(KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// : type_declaration_list
		
		final TypeDeclarationList currentList = new TypeDeclarationList(ctx.getStart());
		parsingData_.pushTypeDeclarationList(currentList);
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
		
		final String name = null != ctx.JAVA_ID(0)? ctx.JAVA_ID(0).getText(): "*error*";
		ClassDeclaration rawBaseClass = null;
		
		if (null != ctx.class_body()) {
			final TerminalNode baseClassNode = ctx.JAVA_ID(1);
			
			if (null != baseClassNode) {
				final String baseTypeName = baseClassNode.getText();
				Type rawBaseType = currentScope_.lookupTypeDeclarationRecursive(baseTypeName);
				rawBaseClass = currentScope_.lookupClassDeclarationRecursive(baseTypeName);
				if (false == (rawBaseType instanceof ClassType)) {
					// Leave to pass 2
					errorHook6p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Base type `", baseTypeName,
							"' is not a declared class type as base of `", name, "'.", "");
					
					// Stumbling measures
					rawBaseType = StaticScope.globalScope().lookupTypeDeclaration("Object");
					rawBaseClass = StaticScope.globalScope().lookupClassDeclaration("Object");
				} else {
					baseType = (ClassType)rawBaseType;
					if (baseType.name().equals(name)) {
						errorHook5p2(ErrorIncidenceType.Fatal, ctx.getStart(), "Er, no.", "", "", "");
					}
				}
			} else {
				// Redundant: for readability...
				rawBaseClass = (ClassDeclaration) objectBaseClass.enclosedScope().associatedDeclaration();
			}
			
			if (null != ctx.type_parameters()) {
				final TemplateDeclaration newTemplate = this.lookupOrCreateTemplateDeclaration(name, rawBaseClass, baseType, false, ctx.getStart());
				currentScope_.declareTemplate(newTemplate);
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
	
	@Override public void enterInterface_declaration(KantParser.Interface_declarationContext ctx)
	{
		// interface_declaration : 'interface' JAVA_ID '{' interface_body '}'
		//                       | 'interface' JAVA_ID type_parameters '{' interface_body '}'

		if (null != ctx.interface_body()) {
			String typeName = ctx.JAVA_ID().getText();
			if (null != ctx.type_parameters() && 0 < ctx.type_parameters().getText().length()) {
				final TemplateDeclaration newTemplate = this.lookupOrCreateTemplateDeclaration(typeName, null, null, true, ctx.getStart());
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
	
	@Override public void exitContext_declaration(KantParser.Context_declarationContext ctx)
	{
		// : 'context' JAVA_ID '{' context_body '}'

		exitType_declarationCommon();
		
		if (null != currentContext_) {
			currentContext_ = currentContext_.parentContext();
		}

		if (printProductionsDebug) {
			System.err.println("context_declaration : 'context' JAVA_ID '{' context_body '}'");
		}
	}
	
	@Override public void exitClass_declaration(KantParser.Class_declarationContext ctx)
	{
		// | 'class'   JAVA_ID type_parameters (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'
		// | 'class'   JAVA_ID (implements_list)* 'extends' JAVA_ID '{' class_body '}'
		
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
	}
	
	@Override public void exitInterface_declaration(KantParser.Interface_declarationContext ctx)
	{
		// | 'interface' JAVA_ID '{' interface_body '}'
		
		exitType_declarationCommon();
		
		if (printProductionsDebug) {
			System.err.println("interface_declaration : 'interface' JAVA_ID '{' interface_body   '}'");
		}
	}
		
	private void exitType_declarationCommon() {
		// This is the Pass 0 version
		// type_declaration : context_declaration
		//                  | class_declaration
		//                  | interface_declaration
		assert null != currentScope_;
		final Declaration rawNewDeclaration = currentScope_.associatedDeclaration();
		assert rawNewDeclaration instanceof TypeDeclaration;	
		final TypeDeclaration newDeclaration = (TypeDeclaration)rawNewDeclaration;
			
		final StaticScope newDeclarationParentScope = newDeclaration.enclosingScope();
		final Type environment = Expression.nearestEnclosingMegaTypeOf(newDeclarationParentScope);
		if (null == environment) {
			// Only declare it if it's at most global scope
			parsingData_.currentTypeDeclarationList().addDeclaration(newDeclaration);
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
		// This is the Pass 0 version
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
	
	protected void implementsCheck(final ClassOrContextDeclaration newDeclaration, final Token token) {
		// nothing on Pass 0
	}
	
	@Override public void enterRole_decl(KantParser.Role_declContext ctx)
	{
		// : 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		// | 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		// | access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		// | 'role' role_vec_modifier JAVA_ID '{' '}'
		// | 'role' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'role' role_vec_modifier JAVA_ID '{' '}'
		// | access_qualifier 'role' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		//
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isRoleArray = vecText.length() > 0;	// "[]"
		
		if (null != ctx.access_qualifier()) {
			errorHook5p1(ErrorIncidenceType.Warning, ctx.getStart(), "WARNING: Gratuitous access qualifier `",
					ctx.access_qualifier().getText(), "' ignored", ".");
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role - resulting grammar error got here with that
			// null condition. Not much to do but to punt

			final String roleName = JAVA_ID.getText();
		
			// Return value is through currentRole_
			lookupOrCreateRoleDeclaration(roleName, ctx.getStart(), isRoleArray);
		
			assert null != currentRoleOrStageProp_;
		
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorIncidenceType.Fatal, ctx.getStart(), "Role ", roleName, " can be declared only in a Context scope - not ", currentScope_.name());
			}
			currentScope_ = currentRoleOrStageProp_.enclosedScope();
		} else {
			currentRoleOrStageProp_ = null;
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
		
		// In the self_methods rule we've been gathering signatures
		// in the "requires" section and storing them in the Role
		// declaration. Just tell the type about the declaration so
		// that type checking can track it back.
		
		if (null != currentRoleOrStageProp_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See
			// the comment above on entry to the production.

			final Type rawRoleType = currentRoleOrStageProp_.type();
			assert rawRoleType instanceof RoleType;
			final RoleType type = (RoleType)rawRoleType;
			type.setBacklinkToRoleDecl(currentRoleOrStageProp_);

			currentRoleOrStageProp_ = null;
			currentScope_ = currentScope_.parentScope();
		}
	}
	
	@Override public void enterStageprop_decl(KantParser.Stageprop_declContext ctx)
	{
		// : 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{'  '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		//
		// Pass1 logic. INVOKED BY CORRESPONDING PASS2 RULE
		
		final String vecText = ctx.role_vec_modifier().getText();
		final boolean isRoleArray = vecText.length() > 0;	// "[]"
		
		if (null != ctx.access_qualifier()) {
			errorHook5p1(ErrorIncidenceType.Warning, ctx.getStart(), "WARNING: Gratuitous access qualifier `",
					ctx.access_qualifier().getText(), "' ignored", ".");
		}
		
		final TerminalNode JAVA_ID = ctx.JAVA_ID();
		
		if (null != JAVA_ID) {
			// It *can* be null. Once had an object declaration inside
			// a role - resulting grammar error got here with that
			// null condition. Not much to do but to punt

			final String roleName = JAVA_ID.getText();
		
			// Return value is through currentRole_
			lookupOrCreateStagePropDeclaration(roleName, ctx.getStart(), isRoleArray);
		
			assert null != currentRoleOrStageProp_;
		
			final Declaration currentScopesDecl = currentScope_.associatedDeclaration();
			if (!(currentScopesDecl instanceof ContextDeclaration)) {
				errorHook5p1(ErrorIncidenceType.Fatal, ctx.getStart(), "Role ", roleName, " can be declared only in a Context scope - not ", currentScope_.name());
			}
			currentScope_ = currentRoleOrStageProp_.enclosedScope();
		} else {
			currentRoleOrStageProp_ = null;
		}
	}
	
	@Override public void exitStageprop_decl(KantParser.Stageprop_declContext ctx)
	{
		// : 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		// | 'stageprop' role_vec_modifier JAVA_ID '{'  '}' REQUIRES '{' self_methods '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		// | access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		
		// All three passes. INVOKED BY PASS 2 VERSION (probably shouldn't
		// be, as it does nothing in pass 2. FIXME)
		
		// In the self_methods rule we've been gathering signatures
		// in the "requires" section and storing them in the Role
		// declaration. Just tell the type about the declaration so
		// that type checking can track it back.
		
		if (null != currentRoleOrStageProp_) {
			// The IF statement is just to recover from bad
			// behaviour elicited by syntax errors. See
			// the comment above on entry to the production.

			final Type rawRoleType = currentRoleOrStageProp_.type();
			assert rawRoleType instanceof StagePropType;
			final StagePropType type = (StagePropType)rawRoleType;
			type.setBacklinkToRoleDecl(currentRoleOrStageProp_);

			currentRoleOrStageProp_ = null;
			currentScope_ = currentScope_.parentScope();
		}
	}
	
	
	// -------------------------------------------------------------------------------
	
	protected ClassDeclaration lookupOrCreateClassDeclaration(final String name, final ClassDeclaration rawBaseClass,
			final ClassType baseType, final Token token) {
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		final ClassDeclaration newClass = this.lookupOrCreateNewClassDeclaration(name, newScope, rawBaseClass, token);
		currentScope_.declareClass(newClass);
		this.createNewClassTypeSuitableToPass(newClass, name, newScope, baseType);
		currentScope_ = newScope;
		return newClass;
	}

	protected void createNewClassTypeSuitableToPass(final ClassDeclaration newClass, final String name, final StaticScope newScope, final ClassType baseType) {
		// Pass0 only
		final ClassType newClassType = new ClassType(name, newScope, baseType);
		currentScope_.declareType(newClassType);
		newScope.setDeclaration(newClass);
		newClass.setType(newClassType);
	}

    protected ClassDeclaration lookupOrCreateNewClassDeclaration(final String name, final StaticScope newScope,
    		final ClassDeclaration rawBaseClass, final Token token) {
		if (null == rawBaseClass) {
			assert null != rawBaseClass;
		}
    	return new ClassDeclaration(name, newScope, rawBaseClass, token);
	}
	
	protected InterfaceDeclaration lookupOrCreateInterfaceDeclaration(final String name, final Token token) {
		assert null == currentInterface_;
		assert null != currentScope_;
		
		// We use a special scope for interfaces, since method lookup
		// will fail if using the usual Scope method lookup — an interface
		// has not methods — only interfaces
		final StaticScope newScope = new StaticInterfaceScope(currentScope_);
		currentInterface_ = this.lookupOrCreateNewInterfaceDeclaration(name, newScope, token);
		currentScope_.declareInterface(currentInterface_);
		this.createNewInterfaceTypeSuitableToPass(currentInterface_, name, newScope);
		
		// currentScope_ is set by caller after return
		return currentInterface_;
	}
	
	protected void createNewInterfaceTypeSuitableToPass(final InterfaceDeclaration newInterface, final String name, final StaticScope newScope) {
		// Pass1 only
		final InterfaceType newInterfaceType = new InterfaceType(name, newScope, false);
		currentScope_.declareType(newInterfaceType);
		newScope.setDeclaration(newInterface);
		newInterface.setType(newInterfaceType);
	}
	
	private InterfaceDeclaration lookupOrCreateNewInterfaceDeclaration(final String name, final StaticScope scope, final Token token) {
		return new InterfaceDeclaration(name, scope, token);
	}
	
	protected void lookupOrCreateRoleDeclaration(final String roleName, final Token token, final boolean isRoleArray) {
		final RoleDeclaration requestedRole = currentScope_.lookupRoleOrStagePropDeclaration(roleName);
		if (null != requestedRole) {
			currentRoleOrStageProp_ = requestedRole;
			
			// Something wrong
			ErrorLogger.error(ErrorIncidenceType.Fatal, token,
					"Duplicate declaration of Role `",  roleName, "´ (original at line ",
					requestedRole.lineNumber() + ").");
		} else {
			final StaticScope rolesScope = new StaticRoleScope(currentScope_);
			final RoleDeclaration roleDecl =
					isRoleArray
					   ? new RoleArrayDeclaration(roleName, rolesScope, currentContext_, token)
					   : new RoleDeclaration(roleName, rolesScope, currentContext_, token);
			rolesScope.setDeclaration(roleDecl);
			
			// declareRoleOrStageProp will also declare the name as an array handle
			// if isRoleArray was set (in pass 2)
			declareRoleOrStageProp(currentScope_, roleDecl, token);
			
			final RoleType roleType = new RoleType(roleName, rolesScope);
			currentScope_.declareType(roleType);
			
			currentRoleOrStageProp_ = roleDecl;
			assert null != currentRoleOrStageProp_;
			currentRoleOrStageProp_.setType(roleType);
		}
		// caller may reset currentScope - NOT us
	}
	
	protected void lookupOrCreateStagePropDeclaration(final String stagePropName, final Token token, final boolean isStagePropArray) {
		final RoleDeclaration requestedStageProp = currentScope_.lookupRoleOrStagePropDeclaration(stagePropName);
		if (null != requestedStageProp) {
			currentRoleOrStageProp_ = requestedStageProp;
			
			// Something wrong
			ErrorLogger.error(ErrorIncidenceType.Fatal, token,
					"Duplicate declaration of Stage Prop `",  stagePropName, "´ (original at line ",
					requestedStageProp.lineNumber() + ").");
		} else {
			final StaticScope stagePropsScope = new StaticRoleScope(currentScope_);
			final StagePropDeclaration stagePropDecl =
					isStagePropArray
					   ? new StagePropArrayDeclaration(stagePropName, stagePropsScope, currentContext_, token)
					   : new StagePropDeclaration(stagePropName, stagePropsScope, currentContext_, token);
			stagePropsScope.setDeclaration(stagePropDecl);
			
			// declareRoleOrStageProp will also declare the name as an array handle
			// if isRoleArray was set (in pass 2)
			declareRoleOrStageProp(currentScope_, stagePropDecl, token);
			
			final RoleType roleType = new StagePropType(stagePropName, stagePropsScope);
			currentScope_.declareType(roleType);
			
			currentRoleOrStageProp_ = stagePropDecl;
			assert null != currentRoleOrStageProp_;
			currentRoleOrStageProp_.setType(roleType);
		}
		// caller may reset currentScope - NOT us
	}
	
	public void declareRoleOrStageProp(final StaticScope s, final RoleDeclaration roledecl, final Token token) {
		s.declareRoleOrStageProp(roledecl);
	}
	
	protected ContextDeclaration lookupOrCreateContextDeclaration(final String name, final Token token) {
		// Create it here in Pass 0
		final StaticScope newScope = new StaticScope(currentScope_);
		final ContextDeclaration contextDecl = new ContextDeclaration(name, newScope, currentContext_, token);
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
	
	protected TemplateDeclaration lookupOrCreateTemplateDeclaration(final String name, final TypeDeclaration rawBaseType, final Type baseType,
			final boolean isInterface, final Token token) {
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		final TemplateDeclaration newTemplate = this.lookupOrCreateNewTemplateDeclaration(name, newScope, rawBaseType, token, isInterface);
		newScope.setDeclaration(newTemplate);
		currentScope_.declareTemplate(newTemplate);
		this.createNewTemplateTypeSuitableToPass(newTemplate, name, newScope, (ClassType)baseType, isInterface);
		currentScope_ = newScope;
		return newTemplate;
	}
	
	protected void createNewTemplateTypeSuitableToPass(final TemplateDeclaration newTemplate, final String name,
			final StaticScope newScope, final ClassType baseType, boolean isInterface) {
		// Pass1 only
		final TemplateType newTemplateType = isInterface?
				new TemplateTypeForAnInterface(name, newScope, baseType):
					new TemplateType(name, newScope, baseType);
		currentScope_.declareType(newTemplateType);
		newScope.setDeclaration(newTemplate);
		newTemplate.setType(newTemplateType);
	}
	
    protected TemplateDeclaration lookupOrCreateNewTemplateDeclaration(final String name, final StaticScope newScope,
    		final TypeDeclaration rawBaseClass, final Token token, final boolean isInterface) {
    	TemplateDeclaration retval = null;
    	if (isInterface) {
    		retval = new TemplateDeclaration(name, newScope, rawBaseClass, token);
    	} else {
    		retval = new TemplateDeclaration(name, newScope, rawBaseClass, token);
    	}
    	return retval;
	}
	
	protected void declareTypeSuitableToPass(final StaticScope scope, final Type decl) {
		scope.declareType(decl);
	}
	
	protected void declareObjectSuitableToPass(final StaticScope scope, final ObjectDeclaration objDecl) {
		final String objectIdentifier = objDecl.name();
		final Declaration existingDecl = scope.lookupObjectDeclaration(objectIdentifier);
		if (null != existingDecl) {
			errorHook5p1(ErrorIncidenceType.Fatal, objDecl.token(), "Multiple declarations of `",
					objectIdentifier, "'", "");
		} else {
			scope.declareObject(objDecl, this);
		}
	}
	
	protected void errorHook5p1(final ErrorIncidenceType errorType, final Token ct, final String s1, final String s2, final String s3, final String s4) {
		/* Nothing */
	}
	public void errorHook5p2(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4) {
		/* nothing */
	}
	public void errorHook5p2SpecialHook(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4) {
		/* nothing */
	}
	protected void errorHook6p1(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		ErrorLogger.error(errorType, token, s1, s2, s3, s4, s5, s6);
	}
	public void errorHook6p2(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6) {
		/* nothing */
	}

	// ------------------------------------------------------------------------------------------------------- 
	protected ContextDeclaration currentContext_;	// should probably be a stack for generality. FIXME
	protected ParsingData parsingData_;
	protected StaticScope currentScope_;
	protected RoleDeclaration currentRoleOrStageProp_;
	protected InterfaceDeclaration currentInterface_;
	protected boolean printProductionsDebug;
	protected boolean stackSnapshotDebug;
    // -------------------------------------------------------------------------------------------------------
}
