package info.fulloo.trygve.add_ons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTColorObject;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

public final class ColorClass {
	private static void declareColorMethod(final String methodSelector,
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
		final ObjectDeclaration self = new ObjectDeclaration("this", colorType_, 0);
		formals.addFormalParameter(self);
		final StaticScope methodScope = new StaticScope(colorType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		colorType_.enclosedScope().declareMethod(methodDecl);
	}
	
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == (colorType_ = (ClassType)globalScope.lookupTypeDeclaration("Color"))) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final Type intType = globalScope.lookupTypeDeclaration("int");
			final Type doubleType = globalScope.lookupTypeDeclaration("double");
			
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("Color", newScope, objectBaseClass, 0);
			newScope.setDeclaration(classDecl);
			colorType_ = new ClassType("Color", newScope, null);
			classDecl.setType(colorType_);
			typeDeclarationList_.add(classDecl);

			declareColorMethod("Color", null, asList("blue", "green", "red"), asList(intType, intType, intType), false);
			declareColorMethod("Color", null, asList("blue", "green", "red"), asList(doubleType, doubleType, doubleType), false);
			declareColorMethod("Color", null, asList("alpha", "blue", "green", "red"), asList(intType, intType, intType, intType), false);
			declareColorMethod("Color", null, asList("alpha", "blue", "green", "red"), asList(doubleType, doubleType, doubleType, doubleType), false);
			declareColorMethod("getRed", intType, null, null, false);
			declareColorMethod("getBlue", intType, null, null, false);
			declareColorMethod("getGreen", intType, null, null, false);
			
			// These need to be coordinated only with what is in the postSetupInitialization
			// method below.
			for (final String attributeName : asList("black", "blue", "cyan", "darkGray", "gray",
					"lightGray", "magenta", "orange", "pink", "red", "white", "green", "yellow")) {
				final ObjectDeclaration attributeDeclaration = new ObjectDeclaration(attributeName, colorType_, 0);
				attributeDeclaration.setAccess(AccessQualifier.PublicAccess, colorType_.enclosedScope(), 0);
				colorType_.enclosedScope().declareStaticObject(attributeDeclaration);
				colorType_.declareStaticObject(attributeDeclaration);
			}
			
			globalScope.declareType(colorType_);
			globalScope.declareClass(classDecl);
			classDecl.setType(colorType_);
		}
	}
	
	
	public static class RTColorCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTColorCommon(final String className, final String methodName, final List<String> parameterNames,
				final List<String> parameterTypeNames, final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName, RTMessage.buildArguments(className, methodName, parameterNames, parameterTypeNames, enclosingMethodScope, false),
					returnType, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
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
			assert self instanceof RTColorObject;
			final RTCode retval = this.runDetails(activationRecord, (RTColorObject)self);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope, final RTColorObject color) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
	}
	public static class RTGetRedCode extends RTColorCommon {
		public RTGetRedCode(final StaticScope enclosingMethodScope) {
			super("Color", "getRed", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject theColorObject) {
			assert null != theColorObject;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColor = activationRecord.getObject("this");
			assert theColor instanceof RTColorObject;
			final int rgbComponent = ((RTColorObject)theColor).getRed();
			final RTIntegerObject retval = new RTIntegerObject(rgbComponent);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	public static class RTGetGreenCode extends RTColorCommon {
		public RTGetGreenCode(final StaticScope enclosingMethodScope) {
			super("Color", "getGreen", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject color) {
			assert null != color;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColor = activationRecord.getObject("this");
			assert theColor instanceof RTColorObject;
			final int rgbComponent = ((RTColorObject)theColor).getGreen();
			final RTIntegerObject retval = new RTIntegerObject(rgbComponent);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	public static class RTGetBlueCode extends RTColorCommon {
		public RTGetBlueCode(final StaticScope enclosingMethodScope) {
			super("Color", "getBlue", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject color) {
			assert null != color;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColor = activationRecord.getObject("this");
			assert theColor instanceof RTColorObject;
			final int rgbComponent = ((RTColorObject)theColor).getBlue();
			final RTIntegerObject retval = new RTIntegerObject(rgbComponent);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	public static class RTColorCtor1Code extends RTColorCommon {
		public RTColorCtor1Code(final StaticScope enclosingMethodScope) {
			super("Color", "Color", asList("red", "green", "blue"), asList("int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject theColor) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColorObject = activationRecord.getObject("this");
			assert theColorObject instanceof RTColorObject;
			final RTObject redArg = activationRecord.getObject("red");
			final RTObject greenArg = activationRecord.getObject("green");
			final RTObject blueArg = activationRecord.getObject("blue");
			assert redArg instanceof RTIntegerObject;
			assert greenArg instanceof RTIntegerObject;
			assert blueArg instanceof RTIntegerObject;
			final int red = (int)((RTIntegerObject)redArg).intValue();
			final int green = (int)((RTIntegerObject)greenArg).intValue();
			final int blue = (int)((RTIntegerObject)blueArg).intValue();
			((RTColorObject)theColorObject).ctor1(red, green, blue);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theColorObject);
			return super.nextCode();
		}
	}
	public static class RTColorCtor2Code extends RTColorCommon {
		public RTColorCtor2Code(final StaticScope enclosingMethodScope) {
			super("Color", "Color", asList("red", "green", "blue"), asList("double", "double", "double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject theColor) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColorObject = activationRecord.getObject("this");
			assert theColorObject instanceof RTColorObject;
			final RTObject redArg = activationRecord.getObject("red");
			final RTObject greenArg = activationRecord.getObject("green");
			final RTObject blueArg = activationRecord.getObject("blue");
			assert redArg instanceof RTDoubleObject;
			assert greenArg instanceof RTDoubleObject;
			assert blueArg instanceof RTDoubleObject;
			final double red = ((RTDoubleObject)redArg).doubleValue();
			final double green = ((RTDoubleObject)greenArg).doubleValue();
			final double blue = ((RTDoubleObject)blueArg).doubleValue();
			((RTColorObject)theColorObject).ctor2(red, green, blue);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theColorObject);
			return super.nextCode();
		}
	}
	public static class RTColorCtor3Code extends RTColorCommon {
		public RTColorCtor3Code(final StaticScope enclosingMethodScope) {
			super("Color", "Color", asList("red", "green", "blue", "alpha"), asList("int", "int", "int", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject theColor) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColorObject = activationRecord.getObject("this");
			assert theColorObject instanceof RTColorObject;
			final RTObject redArg = activationRecord.getObject("red");
			final RTObject greenArg = activationRecord.getObject("green");
			final RTObject blueArg = activationRecord.getObject("blue");
			final RTObject alphaArg = activationRecord.getObject("alpha");
			assert redArg instanceof RTIntegerObject;
			assert greenArg instanceof RTIntegerObject;
			assert blueArg instanceof RTIntegerObject;
			assert alphaArg instanceof RTIntegerObject;
			final int red = (int)((RTIntegerObject)redArg).intValue();
			final int green = (int)((RTIntegerObject)greenArg).intValue();
			final int blue = (int)((RTIntegerObject)blueArg).intValue();
			final int alpha = (int)((RTIntegerObject)alphaArg).intValue();
			((RTColorObject)theColorObject).ctor3(red, green, blue, alpha);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theColorObject);
			return super.nextCode();
		}
	}
	public static class RTColorCtor4Code extends RTColorCommon {
		public RTColorCtor4Code(final StaticScope enclosingMethodScope) {
			super("Color", "Color", asList("red", "green", "blue", "alpha"), asList("double", "double", "double", "double"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTColorObject theColor) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject theColorObject = activationRecord.getObject("this");
			assert theColorObject instanceof RTColorObject;
			final RTObject redArg = activationRecord.getObject("red");
			final RTObject greenArg = activationRecord.getObject("green");
			final RTObject blueArg = activationRecord.getObject("blue");
			final RTObject alphaArg = activationRecord.getObject("alpha");
			assert redArg instanceof RTDoubleObject;
			assert greenArg instanceof RTDoubleObject;
			assert blueArg instanceof RTDoubleObject;
			assert alphaArg instanceof RTDoubleObject;
			final double red = ((RTDoubleObject)redArg).doubleValue();
			final double green = ((RTDoubleObject)greenArg).doubleValue();
			final double blue = ((RTDoubleObject)blueArg).doubleValue();
			final double alpha = ((RTDoubleObject)alphaArg).doubleValue();
			((RTColorObject)theColorObject).ctor4(red, green, blue, alpha);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theColorObject);
			return super.nextCode();
		}
	}
	
	public static class RTColorClass extends RTObjectClass {
		public RTColorClass(final TypeDeclaration decl) {
			super(decl);
		}
		
		@Override public void postSetupInitialization() {
			
			// These need to be coordinated only with what is in the setup
			// method above
			RTType rTColorType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(colorType_.enclosedScope());
			final RTColorObject blackValue = new RTColorObject(0, 0, 0, rTColorType);
			nameToStaticObjectMap_.put("black", blackValue);
			final RTColorObject blueValue = new RTColorObject(0, 0, 255, rTColorType);
			nameToStaticObjectMap_.put("blue", blueValue);
			final RTColorObject cyanValue = new RTColorObject(0, 255, 255, rTColorType);
			nameToStaticObjectMap_.put("cyan", cyanValue);
			final RTColorObject darkGrayValue = new RTColorObject(64, 64, 64, rTColorType);
			nameToStaticObjectMap_.put("darkGray", darkGrayValue);
			final RTColorObject grayValue = new RTColorObject(128, 128, 128, rTColorType);
			nameToStaticObjectMap_.put("gray", grayValue);
			final RTColorObject lightGrayValue = new RTColorObject(192, 192, 192, rTColorType);
			nameToStaticObjectMap_.put("lightGray", lightGrayValue);
			final RTColorObject magentaValue = new RTColorObject(255, 0, 255, rTColorType);
			nameToStaticObjectMap_.put("magenta", magentaValue);
			final RTColorObject orangeValue = new RTColorObject(255, 200, 0, rTColorType);
			nameToStaticObjectMap_.put("orange", orangeValue);
			final RTColorObject redValue = new RTColorObject(255, 0, 0, rTColorType);
			nameToStaticObjectMap_.put("red", redValue);
			final RTColorObject pinkValue = new RTColorObject(255, 175, 175, rTColorType);
			nameToStaticObjectMap_.put("pink", pinkValue);
			final RTColorObject whiteValue = new RTColorObject(255, 255, 255, rTColorType);
			nameToStaticObjectMap_.put("white", whiteValue);
			final RTColorObject greenValue = new RTColorObject(0, 255, 0, rTColorType);
			nameToStaticObjectMap_.put("green", greenValue);
			final RTColorObject yellowValue = new RTColorObject(255, 255, 0, rTColorType);
			nameToStaticObjectMap_.put("yellow", yellowValue);
		}
	}
	

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static ClassType colorType_ = null;
}