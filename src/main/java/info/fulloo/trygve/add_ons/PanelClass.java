package info.fulloo.trygve.add_ons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTPanelObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

/*
 * Trygve IDE 1.5
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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

public final class PanelClass {
	private static void declarePanelMethod(final String methodSelector,
			final Type returnType,
			final List<String> paramNames,
			final List<Type> paramTypes,
			final boolean isConst) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final Iterator<Type> typeIterator = null == paramTypes? null: paramTypes.iterator();
		final FormalParameterList formals = new FormalParameterList();
		if (null != paramNames) {
			for (final String paramName : paramNames) {
				if (null != paramName) {
					final Type paramType = typeIterator.next();
					final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
					formals.addFormalParameter(formalParameter);
				}
			}
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", panelType_, 0);
		formals.addFormalParameter(self);
		final StaticScope methodScope = new StaticScope(panelType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		panelType_.enclosedScope().declareMethod(methodDecl);
	}
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == globalScope.lookupTypeDeclaration("Panel")) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final Type voidType = globalScope.lookupTypeDeclaration("void");
			final Type intType = globalScope.lookupTypeDeclaration("int");
			final Type colorType = globalScope.lookupTypeDeclaration("Color");
			final Type stringType = globalScope.lookupTypeDeclaration("String");
			
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("Panel", newScope, objectBaseClass, 0);
			newScope.setDeclaration(classDecl);
			panelType_ = new ClassType("Panel", newScope, null);
			classDecl.setType(panelType_);
			typeDeclarationList_.add(classDecl);

			// arguments are in reverse order
			declarePanelMethod("Panel", null, null, null, false);
			declarePanelMethod("setBackground", voidType, asList("color"), asList(colorType), false);
			declarePanelMethod("setForeground", voidType, asList("color"), asList(colorType), false);
			declarePanelMethod("drawLine", voidType, asList("toY", "toX", "fromY", "fromX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("drawRect", voidType, asList("toY", "toX", "fromY", "fromX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("drawEllipse", voidType, asList("radius", "centerY", "centerX"), asList(intType, intType, intType), false);
			declarePanelMethod("drawText", voidType, asList("text", "x", "y"), asList(stringType, intType, intType), false);
			
			globalScope.declareType(panelType_);
			globalScope.declareClass(classDecl);
		}
	}
	
	public static class RTPanelCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTPanelCommon(final String className, final String methodName, final List<String> parameterNames,
				final List<String> parameterTypeNames,
				final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName, RTMessage.buildArguments(className, methodName, parameterNames, parameterTypeNames, enclosingMethodScope, false), returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
		}
		@Override public RTCode run() {
			// Don't need to push or pop anything. The return code stays
			// until the RTReturn statement processes it, and everything
			// else has been popped into the activation record by
			// RTMessage
			// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
			// 		Yes, but...: assert returnCode instanceof RTCode;
			
			// Parameters have all been packaged into the
			// activation record
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject self = (RTObject)activationRecord.getObject("this");
			assert self instanceof RTPanelObject;
			final RTCode retval = this.runDetails(activationRecord, (RTPanelObject)self);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope, final RTPanelObject thePanel) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
			return null;	// halt the machine
		}
	}
	public static class RTSetBackgroundCode extends RTPanelCommon {
		public RTSetBackgroundCode(final StaticScope enclosingMethodScope) {
			super("Panel", "setBackground", asList("color"), asList("Color"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject color = (RTObject)activationRecord.getObject("color");
			thePanel.setBackground(color);
			
			return super.nextCode();
		}
	}
	public static class RTSetForegroundCode extends RTPanelCommon {
		public RTSetForegroundCode(final StaticScope enclosingMethodScope) {
			super("Panel", "setForeground", asList("color"), asList("Color"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject color = (RTObject)activationRecord.getObject("color");
			thePanel.setForeground(color);
			return super.nextCode();
		}
	}
	public static class RTDrawLineCode extends RTPanelCommon {
		public RTDrawLineCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawLine", asList("fromX", "fromY", "toX", "toY"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject fromX = (RTObject)activationRecord.getObject("fromX");
			final RTObject fromY = (RTObject)activationRecord.getObject("fromY");
			final RTObject toX = (RTObject)activationRecord.getObject("toX");
			final RTObject toY = (RTObject)activationRecord.getObject("toY");
			thePanel.drawLine(fromX, fromY, toX, toY);
			
			return super.nextCode();
		}
	}
	public static class RTDrawRectCode extends RTPanelCommon {
		public RTDrawRectCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawRect", asList("fromX", "fromY", "toX", "toY"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject fromX = (RTObject)activationRecord.getObject("fromX");
			final RTObject fromY = (RTObject)activationRecord.getObject("fromY");
			final RTObject toX = (RTObject)activationRecord.getObject("toX");
			final RTObject toY = (RTObject)activationRecord.getObject("toY");
			thePanel.drawRect(fromX, fromY, toX, toY);
			
			return super.nextCode();
		}
	}
	public static class RTDrawEllipseCode extends RTPanelCommon {
		public RTDrawEllipseCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawEllipse", asList("x", "y", "width", "height"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject x = (RTObject)activationRecord.getObject("x");
			final RTObject y = (RTObject)activationRecord.getObject("y");
			final RTObject width = (RTObject)activationRecord.getObject("width");
			final RTObject height = (RTObject)activationRecord.getObject("height");
			thePanel.drawEllipse(x, y, width, height);
			
			return super.nextCode();
		}
	}
	public static class RTDrawTextCode extends RTPanelCommon {
		public RTDrawTextCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawText", asList("x", "y", "text"), asList("int", "int", "String"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject x = (RTObject)activationRecord.getObject("x");
			final RTObject y = (RTObject)activationRecord.getObject("y");
			final RTObject text = (RTObject)activationRecord.getObject("text");
			thePanel.drawText(x, y, text);
			
			return super.nextCode();
		}
	}
	public static class RTPanelCtorCode extends RTPanelCommon {
		public RTPanelCtorCode(final StaticScope enclosingMethodScope) {
			super("Panel", "Panel", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTPanelObject thePanel) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTPanelObject thePanelObject = (RTPanelObject)activationRecord.getObject("this");
			thePanelObject.ctor1();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(thePanelObject);
			return super.nextCode();
		}
	}
	

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType panelType_;
}