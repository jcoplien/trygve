package info.fulloo.trygve.add_ons;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.fulloo.trygve.add_ons.PointClass.RTPointObject;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.graphics.GraphicsPanel;
import info.fulloo.trygve.run_time.*;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

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
					final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, null);
					formals.addFormalParameter(formalParameter);
				}
			}
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", panelType_, null);
		formals.addFormalParameter(self);
		final StaticScope methodScope = new StaticScope(panelType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, null, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		panelType_.enclosedScope().declareMethod(methodDecl, null);
	}
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == globalScope.lookupTypeDeclaration("Panel")) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final Type voidType = globalScope.lookupTypeDeclaration("void");
			final Type intType = globalScope.lookupTypeDeclaration("int");
			final Type colorType = globalScope.lookupTypeDeclaration("Color");
			final Type stringType = globalScope.lookupTypeDeclaration("String");
			final Type objectType = globalScope.lookupTypeDeclaration("Object");
			final Type pointType = globalScope.lookupTypeDeclaration("Point");

			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("Panel", newScope, objectBaseClass, null);
			newScope.setDeclaration(classDecl);
			panelType_ = new ClassType("Panel", newScope, null);
			classDecl.setType(panelType_);
			typeDeclarationList_.add(classDecl);

			// arguments are in reverse order
			declarePanelMethod("Panel", null, null, null, false);
			declarePanelMethod("setColor", voidType, asList("color"), asList(colorType), false);
			declarePanelMethod("getColor", colorType, null, null, false);
			declarePanelMethod("drawLine", voidType, asList("toY", "toX", "fromY", "fromX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("drawRect", voidType, asList("height", "width", "fromY", "fromX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("fillRect", voidType, asList("height", "width", "fromY", "fromX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("drawOval", voidType, asList("height", "width", "topY", "leftX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("fillOval", voidType, asList("height", "width", "topY", "leftX"), asList(intType, intType, intType, intType), false);
			declarePanelMethod("drawString", voidType, asList("text", "y", "x"), asList(stringType, intType, intType), false);
			declarePanelMethod("measureString", pointType, asList("text"), asList(stringType), false);
			declarePanelMethod("repaint", voidType, null, null, false);
			declarePanelMethod("clear", voidType, null, null, false);
			
			// add the pointer to the GraphicsPanel object
			// that contains all the goodies
			final ObjectDeclaration objectDecl = new ObjectDeclaration("panelObject", objectType, null);
			newScope.declareObject(objectDecl, null);
			
			// standard wrap-up
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
			if (self instanceof RTPanelObject) {
				;	// O.K.
			} else if (self instanceof RTObjectCommon){
				final RTType taype = ((RTObjectCommon)self).rTType();
				if (taype instanceof RTClass) {
					final RTClass theClass = (RTClass)taype;
					ClassDeclaration baseClass = theClass.baseClassDeclaration();
					do {
						if (baseClass.type().pathName().equals("Panel.")) {
							break;	// found it
						} else {
							baseClass = baseClass.baseClassDeclaration();
						}
					} while (null != baseClass);
					
					if (null != baseClass && baseClass.type().pathName().equals("Panel.")) {
						;	// O.K.
					} else {
						assert false;	// got a non-panel object — what's it doing here?
					}
				}
			}
			
			// Get the real driver
			final RTObject rawGraphicsPanel = self.getObject("panelObject");
			
			// Can be null on a ctor call, because it hasn't yet been set up
			assert rawGraphicsPanel instanceof RTNullObject || rawGraphicsPanel instanceof GraphicsPanel;
			final GraphicsPanel graphicsPanel = rawGraphicsPanel instanceof GraphicsPanel? (GraphicsPanel)rawGraphicsPanel: null;
			
			final RTCode retval = this.runDetails(activationRecord, graphicsPanel);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope, final GraphicsPanel thePanel) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
	}
	public static class RTSetColorCode extends RTPanelCommon {
		public RTSetColorCode(final StaticScope enclosingMethodScope) {
			super("Panel", "setColor", asList("color"), asList("Color"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject color = (RTObject)activationRecord.getObject("color");
			
			try {
				thePanel.setColor(color);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.setColor.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTGetColorCode extends RTPanelCommon {
		public RTGetColorCode(final StaticScope enclosingMethodScope) {
			super("Panel", "getColor", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("Color"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			RTObject value = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();

			try {
				final Color cRetval = thePanel.getColor();
				final Type colorType = StaticScope.globalScope().lookupTypeDeclaration("Color");
				final RTType rTColor = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(colorType.enclosedScope());
				value = new RTColorObject(cRetval.getRed(), cRetval.getGreen(), cRetval.getBlue(), rTColor);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.getColor.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}

			this.addRetvalTo(activationRecord);
			activationRecord.setNamedSlotToValue("ret$val", value);

			return super.nextCode();
		}
	}
	public static class RTDrawLineCode extends RTPanelCommon {
		public RTDrawLineCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawLine", asList("fromX", "fromY", "toX", "toY"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject fromX = (RTObject)activationRecord.getObject("fromX");
			final RTObject fromY = (RTObject)activationRecord.getObject("fromY");
			final RTObject toX = (RTObject)activationRecord.getObject("toX");
			final RTObject toY = (RTObject)activationRecord.getObject("toY");
			
			try {
				thePanel.drawLine(fromX, fromY, toX, toY);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.drawLine.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTDrawRectCode extends RTPanelCommon {
		public RTDrawRectCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawRect", asList("fromX", "fromY", "width", "height"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject fromX = (RTObject)activationRecord.getObject("fromX");
			final RTObject fromY = (RTObject)activationRecord.getObject("fromY");
			final RTObject width = (RTObject)activationRecord.getObject("width");
			final RTObject height = (RTObject)activationRecord.getObject("height");
			
			try {
				thePanel.drawRect(fromX, fromY, width, height);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.drawRect.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTFillRectCode extends RTPanelCommon {
		public RTFillRectCode(final StaticScope enclosingMethodScope) {
			super("Panel", "fillRect", asList("fromX", "fromY", "width", "height"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject fromXObject = (RTObject)activationRecord.getObject("fromX");
			final RTObject fromYObject = (RTObject)activationRecord.getObject("fromY");
			final RTObject widthObject = (RTObject)activationRecord.getObject("width");
			final RTObject heightObject = (RTObject)activationRecord.getObject("height");
			
			assert fromXObject instanceof RTIntegerObject;
			assert fromYObject instanceof RTIntegerObject;
			assert widthObject instanceof RTIntegerObject;
			assert heightObject instanceof RTIntegerObject;
			
			try {
				thePanel.fillRect(fromXObject, fromYObject, widthObject, heightObject);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.fillRect.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTDrawEllipseCode extends RTPanelCommon {
		public RTDrawEllipseCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawOval", asList("leftX", "topY", "width", "height"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject leftX = (RTObject)activationRecord.getObject("leftX");
			final RTObject topY = (RTObject)activationRecord.getObject("topY");
			final RTObject width = (RTObject)activationRecord.getObject("width");
			final RTObject height = (RTObject)activationRecord.getObject("height");
			
			try {
				thePanel.drawOval(leftX, topY, width, height);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.drawOval.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTFillEllipseCode extends RTPanelCommon {
		public RTFillEllipseCode(final StaticScope enclosingMethodScope) {
			super("Panel", "fillOval", asList("leftX", "topY", "width", "height"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject leftX = (RTObject)activationRecord.getObject("leftX");
			final RTObject topY = (RTObject)activationRecord.getObject("topY");
			final RTObject width = (RTObject)activationRecord.getObject("width");
			final RTObject height = (RTObject)activationRecord.getObject("height");
			
			try {
				thePanel.fillOval(leftX, topY, width, height);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.fillOval.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTDrawStringCode extends RTPanelCommon {
		public RTDrawStringCode(final StaticScope enclosingMethodScope) {
			super("Panel", "drawString", asList("x", "y", "text"), asList("int", "int", "String"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject x = (RTObject)activationRecord.getObject("x");
			final RTObject y = (RTObject)activationRecord.getObject("y");
			final RTObject text = (RTObject)activationRecord.getObject("text");
			try {
				thePanel.drawString(x, y, text);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.drawString(`", ((RTStringObject)text).toString(), "'.", "");
				RTMessage.printMiniStackStatus();
				return null;
			}

			return super.nextCode();
		}
	}
	public static class RTMeasureString extends RTPanelCommon {
		public RTMeasureString(final StaticScope enclosingMethodScope) {
			super("Panel", "measureString", asList("text"), asList("String"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("Point"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject text = (RTObject)activationRecord.getObject("text");

			final Point rawRetval = thePanel.measureString(text);
			final RTPointObject retval = new RTPointObject(rawRetval);

			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);

			return super.nextCode();
		}
	}
	public static class RTClearCode extends RTPanelCommon {
		public RTClearCode(final StaticScope enclosingMethodScope) {
			super("Panel", "clear", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			try{
				thePanel.clear();
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.clear()", ".", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTRepaintCode extends RTPanelCommon {
		public RTRepaintCode(final StaticScope enclosingMethodScope) {
			super("Panel", "repaint", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			assert null != thePanel;
			try{
				thePanel.flipBuffers();
				thePanel.repaint();
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel.repaint(`", "", "'.", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	public static class RTPanelCtorCode extends RTPanelCommon {
		public RTPanelCtorCode(final StaticScope enclosingMethodScope) {
			super("Panel", "Panel", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final GraphicsPanel thePanel) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObjectCommon thePanelObject = (RTObjectCommon)activationRecord.getObject("this");
			
			try {
				final GraphicsPanel theGraphicsPanel = new GraphicsPanel(thePanelObject);
				thePanelObject.setObject("panelObject", theGraphicsPanel);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(thePanelObject);
			} catch (final Exception e) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "FATAL: Bad call to Panel constructor.", "", "", "");
				RTMessage.printMiniStackStatus();
				return null;
			}
			
			return super.nextCode();
		}
	}
	

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	public static class EventClass {
		// Never instantiated? FIXME
		private static void declareEventMethod(final String methodSelector,
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
						final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, null);
						formals.addFormalParameter(formalParameter);
					}
				}
			}
			final ObjectDeclaration self = new ObjectDeclaration("this", eventType_, null);
			formals.addFormalParameter(self);
			final StaticScope methodScope = new StaticScope(eventType_.enclosedScope());
			final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, null, false);
			methodDecl.addParameterList(formals);
			methodDecl.setReturnType(returnType);
			methodDecl.setHasConstModifier(isConst);
			eventType_.enclosedScope().declareMethod(methodDecl, null);
		}
		
		public static void setup() {
			final StaticScope globalScope = StaticScope.globalScope();
			if (null == globalScope.lookupTypeDeclaration("Event")) {
				typeDeclarationList_ = new ArrayList<TypeDeclaration>();
				final Type panelType = globalScope.lookupTypeDeclaration("Panel");
				assert null != panelType;
				final Type intType = globalScope.lookupTypeDeclaration("int");
				final Type stringType = globalScope.lookupTypeDeclaration("String");
				
				final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
				assert null != objectBaseClass;

				final StaticScope newScope = new StaticScope(globalScope);
				final ClassDeclaration classDecl = new ClassDeclaration("Event", newScope, objectBaseClass, null);
				newScope.setDeclaration(classDecl);
				eventType_ = new ClassType("Event", newScope, null);
				classDecl.setType(eventType_);
				typeDeclarationList_.add(classDecl);

				// arguments are in reverse order
				declareEventMethod("Event", null, null, null, false);
				
				// Attributes
				final ObjectDeclaration idDeclaration = new ObjectDeclaration("id", intType, null);
				idDeclaration.setAccess(AccessQualifier.PublicAccess, eventType_.enclosedScope(), null);
				eventType_.enclosedScope().declareObject(idDeclaration, null);
				
				final ObjectDeclaration keyDeclaration = new ObjectDeclaration("key", intType, null);
				keyDeclaration.setAccess(AccessQualifier.PublicAccess, eventType_.enclosedScope(), null);
				eventType_.enclosedScope().declareObject(keyDeclaration, null);
				
				final ObjectDeclaration keyStringDeclaration = new ObjectDeclaration("keyString", stringType, null);
				keyStringDeclaration.setAccess(AccessQualifier.PublicAccess, eventType_.enclosedScope(), null);
				eventType_.enclosedScope().declareObject(keyStringDeclaration, null);
				
				final ObjectDeclaration locXDeclaration = new ObjectDeclaration("x", intType, null);
				locXDeclaration.setAccess(AccessQualifier.PublicAccess, eventType_.enclosedScope(), null);
				eventType_.enclosedScope().declareObject(locXDeclaration, null);
				
				final ObjectDeclaration locYDeclaration = new ObjectDeclaration("y", intType, null);
				locYDeclaration.setAccess(AccessQualifier.PublicAccess, eventType_.enclosedScope(), null);
				eventType_.enclosedScope().declareObject(locYDeclaration, null);
				
				// These need to be coordinated only with what is in the postSetupInitialization
				// method below, and what is in the driver (GraphicsPanel.java)
				for (final String attributeName : asList("MOUSE_UP", "MOUSE_DOWN", "MOUSE_ENTER", "MOUSE_EXIT", "MOUSE_DRAG",
						"MOUSE_MOVE", "KEY_PRESS", "KEY_RELEASE")) {
					final ObjectDeclaration attributeDeclaration = new ObjectDeclaration(attributeName, intType, null);
					attributeDeclaration.setAccess(AccessQualifier.PublicAccess, eventType_.enclosedScope(), null);
					eventType_.enclosedScope().declareStaticObject(attributeDeclaration);
					eventType_.declareStaticObject(attributeDeclaration);
				}
				
				globalScope.declareType(eventType_);
				globalScope.declareClass(classDecl);
			}
		}
		
		public static class RTEventCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
			public RTEventCommon(final String className, final String methodName, final List<String> parameterNames,
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
				// assert self instanceof RTEventObject;	// No, because it could be a class derived from Event
				final RTCode retval = this.runDetails(activationRecord, self);
				
				// All dogs go to heaven, and all return statements that
				// have something to return do it. We deal with consumption
				// in the message. This function's return statement will be
				// set for a consumed result in higher-level logic.
				
				return retval;
			}
			public RTCode runDetails(final RTObject scope, final RTObject theEvent) {
				// Effectively a pure virtual method, but Java screws us again...
				ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
				return new RTHalt();	// halt the machine
			}
		}

		public static class RTEventCtorCode extends RTEventCommon {
			public RTEventCtorCode(final StaticScope enclosingMethodScope) {
				super("Event", "Event", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
			}
			@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObject theEventObject) {
				RTEventObject.ctor1(theEventObject);
				RunTimeEnvironment.runTimeEnvironment_.pushStack(theEventObject);
				return super.nextCode();
			}
		}
		

		public static List<TypeDeclaration> typeDeclarationList() {
			return typeDeclarationList_;
		}
		
		private static List<TypeDeclaration> typeDeclarationList_;
		private static ClassType eventType_;
	}
	
	public static class RTEventClass extends RTObjectClass {
		public RTEventClass(final TypeDeclaration decl) {
			super(decl);
		}

		@Override public void postSetupInitialization() {
			// These need to be coordinated only with what is in the setup
			// method above, and what is in the driver (GraphicsPanel.java)
			
			final RTIntegerObject mouseUpValue = new RTIntegerObject(Event.MOUSE_UP);
			nameToStaticObjectMap_.put("MOUSE_UP", mouseUpValue);

			final RTIntegerObject mouseDownValue = new RTIntegerObject(Event.MOUSE_DOWN);
			nameToStaticObjectMap_.put("MOUSE_DOWN", mouseDownValue);
			
			final RTIntegerObject mouseEnterValue = new RTIntegerObject(Event.MOUSE_ENTER);
			nameToStaticObjectMap_.put("MOUSE_ENTER", mouseEnterValue);
			
			final RTIntegerObject mouseExitValue = new RTIntegerObject(Event.MOUSE_EXIT);
			nameToStaticObjectMap_.put("MOUSE_EXIT", mouseExitValue);

			final RTIntegerObject mouseDragValue = new RTIntegerObject(Event.MOUSE_DRAG);
			nameToStaticObjectMap_.put("MOUSE_DRAG", mouseDragValue);
			
			final RTIntegerObject mouseMoveValue = new RTIntegerObject(Event.MOUSE_MOVE);
			nameToStaticObjectMap_.put("MOUSE_MOVE", mouseMoveValue);
			
			final RTIntegerObject keyPressValue = new RTIntegerObject(Event.KEY_PRESS);
			nameToStaticObjectMap_.put("KEY_PRESS", keyPressValue);

			final RTIntegerObject keyReleaseValue = new RTIntegerObject(Event.KEY_RELEASE);
			nameToStaticObjectMap_.put("KEY_RELEASE", keyReleaseValue);
		}
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType panelType_;
}