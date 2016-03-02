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
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTMapObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTStackable;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
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

public final class MapClass {
	private static void declareMapMethod(final String methodSelector,
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
		final ObjectDeclaration self = new ObjectDeclaration("this", mapType_, 0);
		formals.addFormalParameter(self);
		final StaticScope methodScope = new StaticScope(mapType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		mapType_.enclosedScope().declareMethod(methodDecl);
	}
	private static void declarePutAllMethod() {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final FormalParameterList formals = new FormalParameterList();
		final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
		final ObjectDeclaration m = new ObjectDeclaration("m", mapType_, 0);
		formals.addFormalParameter(m);
		final ObjectDeclaration self = new ObjectDeclaration("this", mapType_, 0);
		formals.addFormalParameter(self);
		final StaticScope methodScope = new StaticScope(mapType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration("putAll", methodScope, voidType, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(voidType);
		methodDecl.setHasConstModifier(false);
		mapType_.enclosedScope().declareMethod(methodDecl);
	}
	public static void setup() {
		typeDeclarationList_ = new ArrayList<TypeDeclaration>();
		final StaticScope globalScope = StaticScope.globalScope();
		final Type integerType = StaticScope.globalScope().lookupTypeDeclaration("int");
		assert null != integerType;
		final Type voidType = StaticScope.globalScope().lookupTypeDeclaration("void");
		assert null != voidType;
		final Type booleanType = StaticScope.globalScope().lookupTypeDeclaration("boolean");
		assert null != booleanType;
		
		if (null == globalScope.lookupTypeDeclaration("Map")) {
			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;
			final TemplateDeclaration templateDecl = new TemplateDeclaration("Map", newScope, objectBaseClass, 0);
			newScope.setDeclaration(templateDecl);
			final Type K = new TemplateParameterType("K", null);
			final Type V = new TemplateParameterType("V", null);
			final IdentifierExpression keyTypeParamID = new IdentifierExpression("K", K, newScope, 0);
			final IdentifierExpression valueTypeParamID = new IdentifierExpression("V", V, newScope, 0);
			templateDecl.addTypeParameter(keyTypeParamID, 2);
			templateDecl.addTypeParameter(valueTypeParamID, 2);
			mapType_ = new TemplateType("Map", newScope, null);
			templateDecl.setType(mapType_);
			typeDeclarationList_.add(templateDecl);
			
			final Type intType = globalScope.lookupTypeDeclaration("int");
			
			// these arguments are backwards
			declareMapMethod("Map", mapType_, null, null, false);
			
			declareMapMethod("put", voidType, asList("value", "key"), asList(V, K), false);
			
			declarePutAllMethod();
			
			declareMapMethod("get", V, asList("key"), asList(K), true);
			
			declareMapMethod("containsKey", booleanType, asList("key"), asList(K), true);
			
			declareMapMethod("containsValue", booleanType, asList("value"), asList(V), true);
			
			declareMapMethod("remove", V, asList("key"), asList(K), false);
			
			declareMapMethod("size", intType, null, null, true);
			
			// Declare the type
			globalScope.declareType(mapType_);
			globalScope.declareTemplate(templateDecl);
		}
	}
	
	public static class RTMapCommon extends RTClass.RTObjectClass.RTSimpleObjectMethodsCommon {
		public RTMapCommon(final String className, final String methodName, final List<String> parameterNames,
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
			final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTCode retval = this.runDetails(myEnclosedScope);
			
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return retval;
		}
		public RTCode runDetails(final RTObject scope) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virutal method runDetails", "", "", "");
			return null;	// halt the machine
		}
	}
	public static class RTMapCtorCode extends RTMapCommon {
		public RTMapCtorCode(final StaticScope enclosingMethodScope) {
			super("Map", "Map", asList("key", "value"), asList("K","V"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			theMapObject.ctor();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(this);
			return super.nextCode();
		}
	}
	public static class RTPutCode extends RTMapCommon {
		public RTPutCode(final StaticScope enclosingMethodScope) {
			super("Map", "put", asList("key", "value"), asList("K", "V"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			final RTObject rawKey = activationRecord.getObject("key");
			final RTObject rawValue = activationRecord.getObject("value");
			theMapObject.put(rawKey, rawValue);
			return super.nextCode();
		}
	}
	public static class RTPutAllCode extends RTMapCommon {
		public RTPutAllCode(final StaticScope enclosingMethodScope) {
			super("Map", "putAll", asList("m"), asList("Map"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			final RTObject rawArg = activationRecord.getObject("m");
			theMapObject.putAll(rawArg);
			return super.nextCode();
		}
	}
	public static class RTGetCode extends RTMapCommon {
		public RTGetCode(final StaticScope enclosingMethodScope) {
			super("Map", "get", asList("key"), asList("K"), enclosingMethodScope, new TemplateParameterType("V", null));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			final RTObject rawKey = activationRecord.getObject("key");
			final RTObject answer = theMapObject.get(rawKey);
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", answer);
			
			return super.nextCode();
		}
	}
	public static class RTContainsKeyCode extends RTMapCommon {
		public RTContainsKeyCode(final StaticScope enclosingMethodScope) {
			super("Map", "containsKey", asList("key"), asList("K"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject key = activationRecord.getObject("key");
			final RTObject theRawMapObject = activationRecord.getObject("this");
			assert theRawMapObject instanceof RTMapObject;
			final RTMapObject theMapObject = (RTMapObject)theRawMapObject;
			final RTStackable answer = (RTStackable)theMapObject.containsKey(key);
			assert answer instanceof RTObject;
			
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", (RTObject)answer);
			
			return super.nextCode();
		}
	}
	public static class RTContainsValueCode extends RTMapCommon {
		public RTContainsValueCode(final StaticScope enclosingMethodScope) {
			super("Map", "containsValue", asList("value"), asList("V"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTIntegerObject value = (RTIntegerObject)activationRecord.getObject("key");
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			final RTStackable answer = (RTStackable)theMapObject.containsValue(value);
			assert answer instanceof RTObject;

			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", (RTObject)answer);
			
			return super.nextCode();
		}
	}
	public static class RTRemoveCode extends RTMapCommon {
		public RTRemoveCode(final StaticScope enclosingMethodScope) {
			super("Map", "remove", asList("key"), asList("K"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTIntegerObject key = (RTIntegerObject)activationRecord.getObject("key");
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			final RTStackable answer = (RTStackable)theMapObject.remove(key);
			assert answer instanceof RTObject;

			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", (RTObject)answer);
			
			return super.nextCode();
		}
	}
	public static class RTSizeCode extends RTMapCommon {
		public RTSizeCode(final StaticScope enclosingMethodScope) {
			super("Map", "size", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTMapObject theMapObject = (RTMapObject)activationRecord.getObject("this");
			final int rawResult = theMapObject.size();
			final RTIntegerObject answer = new RTIntegerObject(rawResult);
			assert answer instanceof RTObject;

			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", (RTObject)answer);
			
			return super.nextCode();
		}
	}

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static TemplateType mapType_;
}
