package info.fulloo.trygve.parser;

/*
 * Trygve IDE 1.1
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


import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Type;
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
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.semantic_analysis.StaticScope.StaticRoleScope;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Pass0Listener extends KantBaseListener {
	public Pass0Listener(final ParsingData parsingData) {
		parsingData_ = parsingData;
		
		currentScope_ = parsingData_.globalScope();
		currentContext_ = null;
		
		currentRole_ = null;
		currentInterface_ = null;
		
		printProductionsDebug = false;
		stackSnapshotDebug = false;
	}
	
	@Override public void enterProgram(KantParser.ProgramContext ctx)
	{
		// : type_declaration_list main
		// : type_declaration_list
		
		final TypeDeclarationList currentList = new TypeDeclarationList(ctx.getStart().getLine());
		parsingData_.pushTypeDeclarationList(currentList);
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
				rawBaseClass = (ClassDeclaration) objectBaseClass.enclosedScope().associatedDeclaration();
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
		
		// This is the Pass 0 version
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
	}
	
	protected void implementsCheck(final ClassDeclaration newDeclaration, int lineNumber) {
		// nothing on Pass 0
	}
	
	
	// -------------------------------------------------------------------------------
	
	protected ClassDeclaration lookupOrCreateClassDeclaration(final String name, final ClassDeclaration rawBaseClass, final ClassType baseType, final int lineNumber) {
		assert null != currentScope_;
		final StaticScope newScope = new StaticScope(currentScope_);
		final ClassDeclaration newClass = this.lookupOrCreateNewClassDeclaration(name, newScope, rawBaseClass, lineNumber);
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

    protected ClassDeclaration lookupOrCreateNewClassDeclaration(final String name, final StaticScope newScope, final ClassDeclaration rawBaseClass, final int lineNumber) {
		if (null == rawBaseClass) {
			assert null != rawBaseClass;
		}
    	return new ClassDeclaration(name, newScope, rawBaseClass, lineNumber);
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
	
	protected void createNewInterfaceTypeSuitableToPass(final InterfaceDeclaration newInterface, final String name, final StaticScope newScope) {
		// Pass1 only
		final InterfaceType newInterfaceType = new InterfaceType(name, newScope);
		currentScope_.declareType(newInterfaceType);
		newScope.setDeclaration(newInterface);
		newInterface.setType(newInterfaceType);
	}
	
	private InterfaceDeclaration lookupOrCreateNewInterfaceDeclaration(final String name, final StaticScope scope, final int lineNumber) {
		return new InterfaceDeclaration(name, scope, lineNumber);
	}
	
	protected void lookupOrCreateRoleDeclaration(final String roleName, final int lineNumber, final boolean isRoleArray) {
		final RoleDeclaration requestedRole = currentScope_.lookupRoleOrStagePropDeclaration(roleName);
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
			
			// declareRoleOrStageProp will also declare the name as an array handle
			// if isRoleArray was set (in pass 2)
			declareRoleOrStageProp(currentScope_, roleDecl, lineNumber);
			
			final RoleType roleType = new RoleType(roleName, rolesScope);
			currentScope_.declareType(roleType);
			
			currentRole_ = roleDecl;
			assert null != currentRole_;
			currentRole_.setType(roleType);
		}
		// caller may reset currentScope - NOT us
	}
	
	public void declareRoleOrStageProp(final StaticScope s, final RoleDeclaration roledecl, final int lineNumber) {
		s.declareRoleOrStageProp(roledecl);
	}
	
	protected ContextDeclaration lookupOrCreateContextDeclaration(final String name, final int lineNumber) {
		// Create it here in Pass 0
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
	
	protected void createNewTemplateTypeSuitableToPass(final TemplateDeclaration newTemplate, final String name, final StaticScope newScope, final ClassType baseType) {
		// Pass1 only
		final TemplateType newTemplateType = new TemplateType(name, newScope, baseType);
		currentScope_.declareType(newTemplateType);
		newScope.setDeclaration(newTemplate);
		newTemplate.setType(newTemplateType);
	}
	
    protected TemplateDeclaration lookupOrCreateNewTemplateDeclaration(final String name, final StaticScope newScope, final TypeDeclaration rawBaseClass, final int lineNumber) {
    	return new TemplateDeclaration(name, newScope, rawBaseClass, lineNumber);
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

	// ------------------------------------------------------------------------------------------------------- 
	protected ContextDeclaration currentContext_;	// should probably be a stack for generality. FIXME
	protected ParsingData parsingData_;
	protected StaticScope currentScope_;
	protected RoleDeclaration currentRole_;
	protected InterfaceDeclaration currentInterface_;
	protected boolean printProductionsDebug;
	protected boolean stackSnapshotDebug;
    // -------------------------------------------------------------------------------------------------------
}
