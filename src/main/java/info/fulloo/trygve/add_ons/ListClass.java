package info.fulloo.trygve.add_ons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;  
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTListObject;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTStackable;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import static java.util.Arrays.asList;

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
 * For further information about the trygve project, please contact
 * Jim Coplien at jcoplien@gmail.com
 */

public final class ListClass {
	private static void declareListMethod(final String methodSelector, final Type returnType,
			final List<String> paramNameList,
			final List<Type> paramTypeList, final boolean isConst) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final FormalParameterList formals = new FormalParameterList();
		if (null != paramNameList) {
			final Iterator<Type> paramTypeIter = paramTypeList.iterator();
			for (final String paramName : paramNameList) {
				final Type paramType = paramTypeIter.next();
				final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, null);
				formals.addFormalParameter(formalParameter);
			}
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", listType_, null);
		formals.addFormalParameter(self);
		StaticScope methodScope = new StaticScope(listType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, null, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		listType_.enclosedScope().declareMethod(methodDecl, null);
	}
	
	public static Type addListOfXTypeNamedY(final Type type, final String typeName) {
		Type retval = null;
		final TemplateDeclaration listDecl = (TemplateDeclaration)listType_.enclosedScope().associatedDeclaration();
			
		final StaticScope templateScope = listDecl.enclosingScope();
		final StaticScope templateEnclosedScope = listDecl.enclosedScope();
		final TypeDeclaration baseClass = listDecl.baseClass();
		final String baseClassName = null == baseClass? "void": baseClass.name();
		final ClassDeclaration baseClassDecl = null == baseClass? null:
							templateScope.lookupClassDeclarationRecursive(baseClassName);
		
		ClassDeclaration classDeclaration = StaticScope.globalScope().lookupClassDeclarationRecursive(typeName);
		if (null == classDeclaration) {
			// Create a new type vector from the type parameters
			final TemplateInstantiationInfo templateInstantiationInfo = new TemplateInstantiationInfo(listDecl, typeName);
			templateInstantiationInfo.add(type);
			
			// templateEnclosedScope isn't really used, because a new enclosedScope_ object
			// is created by ClassDeclaration.elaborateFromTemplate(templateDeclaration)
			classDeclaration = new ClassDeclaration(typeName, templateEnclosedScope,
					baseClassDecl, null);
			classDeclaration.elaborateFromTemplate(listDecl, templateInstantiationInfo, null);
			final Type rawNewType = classDeclaration.type();
			assert rawNewType instanceof ClassType;
			final ClassType newType = (ClassType)rawNewType;
			templateInstantiationInfo.setClassType(newType);

			templateScope.declareType(newType);
			templateScope.declareClass(classDeclaration);

			// Here's where we queue template instantiations for code generation
			// Dangerous to leave it out, but I think we can get by for now, because
			// it will be covered by some other use within the user program
			// parsingData_.currentTemplateInstantiationList().addDeclaration(classDeclaration);
			retval = newType;
		} else {
			retval = classDeclaration.type();
		}
		return retval;
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
		final Type stringType = StaticScope.globalScope().lookupTypeDeclaration("String");
		assert null != stringType;
		final Type objectType = StaticScope.globalScope().lookupTypeDeclaration("Object");
		assert null != objectType;
		
		if (null == globalScope.lookupTypeDeclaration("List")) {
			final StaticScope newScope = new StaticScope(globalScope);
			final TemplateDeclaration templateDecl = new TemplateDeclaration("List", newScope, /*Base Class*/ null, null);
			newScope.setDeclaration(templateDecl);
			final Type T = new TemplateParameterType("T", null);
			final IdentifierExpression typeParamId = new IdentifierExpression("T", T, newScope, null);
			templateDecl.addTypeParameter(typeParamId, 1);
			listType_ = new TemplateType("List", newScope, null);
			templateDecl.setType(listType_);
			typeDeclarationList_.add(templateDecl);
			
			final Type intType = globalScope.lookupTypeDeclaration("int");
			
			declareListMethod("List", listType_, null, null, false);
			
			declareListMethod("add", voidType, asList("element"), asList(T), false);
			
			declareListMethod("get", T, asList("theIndex"), asList(integerType), true);
			
			declareListMethod("at", T, asList("theIndex"), asList(integerType), true);
			
			declareListMethod("set", voidType, asList("object", "theIndex"), asList(T, integerType), false);
			
			declareListMethod("atPut", voidType, asList("object", "theIndex"), asList(T, integerType), false);
			
			declareListMethod("indexOf", intType, asList("element"), asList(T), true);
			
			declareListMethod("remove", booleanType, asList("element"), asList(T), false);
			
			declareListMethod("remove", T, asList("theIndex"), asList(intType), false);
			
			declareListMethod("contains", booleanType, asList("element"), asList(T), true);
			
			declareListMethod("size", intType, null, null, true);
			
			declareListMethod("isEmpty", booleanType, null, null, true);
			
			declareListMethod("sort", voidType, null, null, false);

			declareListMethod("reverse", listType_, null, null, true);
			
			// kludge.
			assert null != stringType;
			
			final Type listOfStringType = addListOfXTypeNamedY(stringType, "List<String>");
			                              addListOfXTypeNamedY(objectType, "List<Object>");
			
			// Yeah, parameters are backwards in order for addStringMethod.
			StaticScope.addStringMethod(stringType, "join", stringType, asList("elements", "delimiter"), asList(listOfStringType, stringType), true);
			final Type arrayOfStringType = StaticScope.globalScope().lookupTypeDeclaration("String_$array");
			assert null != arrayOfStringType;
			StaticScope.addStringMethod(stringType, "join", stringType, asList("elements", "delimiter"), asList(arrayOfStringType, stringType), true);
			
			// Declare the type
			globalScope.declareType(listType_);
			globalScope.declareTemplate(templateDecl);
		}
	}
	
	public static class RTListCommon extends RTMessage {
		public RTListCommon(final String className, final String methodName,
				final List<String> parameterName, final List<String> parameterTypeName,
				final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName, RTMessage.buildArguments(className, methodName, 
														parameterName, parameterTypeName,
														enclosingMethodScope, false),
					returnType,
					Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope),
					false);
		}
		public RTCode run() {
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
			ErrorLogger.error(ErrorIncidenceType.Internal, "call of pure virtual method runDetails", "", "", "");
			return null;	// halt the machine
		}
		protected void addRetvalTo(final RTDynamicScope activationRecord) {
			if (null == activationRecord.getObject("ret$val")) {
				activationRecord.addObjectDeclaration("ret$val", null);
			}
		}
	}
	public static class RTListCtorCode extends RTListCommon {
		public RTListCtorCode(final StaticScope enclosingMethodScope) {
			super("List", "List", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			theListObject.ctor();
			RunTimeEnvironment.runTimeEnvironment_.pushStack(this);
			return super.nextCode();
		}
	}
	public static class RTAddCode extends RTListCommon {
		public RTAddCode(final StaticScope enclosingMethodScope) {
			super("List", "add", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTObject rawElement = activationRecord.getObject("element");
			theListObject.add(rawElement);
			// above method increments reference count
			
			return super.nextCode();
		}
	}
	public static class RTRemoveICode extends RTListCommon {
		public RTRemoveICode(final StaticScope enclosingMethodScope) {
			super("List", "remove", asList("theIndex"), asList("int"), enclosingMethodScope, new TemplateParameterType("T", null));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTObject rawIndex = activationRecord.getObject("theIndex");
			assert rawIndex instanceof RTIntegerObject;
			final RTIntegerObject integerIndex = (RTIntegerObject) rawIndex;
			final long theIndex = integerIndex.intValue();
			final RTObject result = theListObject.remove((int)theIndex);
			
			// The object has been removed from the list. It has one less
			// owner. We should decrement its reference count. However, it
			// maybe be in an expression like "lhs = alist.remove(x)" where
			// the assignment to lhs may again bump its reference count up.
			// If it reached zero during the transition we don't want to
			// trigger all kinds of cleanup operations (e.g., if it is a
			// Context instance, to do all the RolePlayer processing).
			//
			// What should we do?  See below.
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			// Now that it's assigned to ret$val we can decrement the reference count to
			// reflect that it's no longer held by the list
			result.decrementReferenceCount();

			return super.nextCode();
		}
	}
	public static class RTRemoveTCode extends RTListCommon {
		public RTRemoveTCode(final StaticScope enclosingMethodScope) {
			super("List", "remove", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("boolean"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTObject rawElement = activationRecord.getObject("element");

			final boolean bResult = theListObject.remove(rawElement);
			final RTObject result = new RTBooleanObject(bResult);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			return super.nextCode();
		}
	}
	public static class RTSizeCode extends RTListCommon {
		public RTSizeCode(final StaticScope enclosingMethodScope) {
			super("List", "size", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final int rawResult = theListObject.size();
			final RTIntegerObject result = new RTIntegerObject(rawResult);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	public static class RTGetCode extends RTListCommon {
		public RTGetCode(final StaticScope enclosingMethodScope, final Token token) {
			super("List", "get", asList("theIndex"), asList("int"), enclosingMethodScope, new TemplateParameterType("T", null));
			token_ = token;
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode pc = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTIntegerObject argument = (RTIntegerObject)activationRecord.getObject("theIndex");
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			if (null == argument) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, token_,
						"Use of uninitialized list value, or index out of range.", "", "", "");
				pc = new RTHalt();	// halt instruction
			} else {
				RTObject result = null;
				if (theListObject.isValidIndex((int)argument.intValue())) {
					result = theListObject.get((int)argument.intValue());

					addRetvalTo(activationRecord);
					activationRecord.setObject("ret$val", result);
					
					pc = super.nextCode();
				} else {
					ErrorLogger.error(ErrorIncidenceType.Runtime, token_,
							"List.get(): List index out-of-range: ",
							Integer.toString((int)argument.intValue()),
							" on list of size ", Integer.toString(theListObject.size()));
					pc = new RTHalt();
				}
			}
			return pc;
		}
		
		private final Token token_;
	}
	
	public static class RTSetCode extends RTListCommon {
		public RTSetCode(final StaticScope enclosingMethodScope, final Token token) {
			super("List", "set", asList("object", "theIndex"), asList("T", "int"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
			token_ = token;
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode pc = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTIntegerObject theIndex = (RTIntegerObject)activationRecord.getObject("theIndex");
			final RTObject object = (RTObject)activationRecord.getObject("object");
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			if (null == theIndex) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, token_,
						"Use of uninitialized list value, or index out of range.", "", "", "");
				pc = new RTHalt();	// halt instruction
			} else {
				if (theListObject.isValidIndex((int)theIndex.intValue())) {
					theListObject.setObject(theIndex, object);
					pc = super.nextCode();
				} else {
					ErrorLogger.error(ErrorIncidenceType.Runtime, token_,
							"List.get(): List index out-of-range: ",
							Integer.toString((int)theIndex.intValue()),
							" on list of size ", Integer.toString(theListObject.size()));
					pc = new RTHalt();
				}
			}
			return pc;
		}
		
		private final Token token_;
	}
	
	public static class RTAtCode extends RTListCommon {
		public RTAtCode(final StaticScope enclosingMethodScope, final Token token) {
			super("List", "at", asList("theIndex"), asList("int"), enclosingMethodScope, new TemplateParameterType("T", null));
			token_ = token;
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			RTCode pc = null;
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTIntegerObject argument = (RTIntegerObject)activationRecord.getObject("theIndex");
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			if (null == argument) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, token_,
						"Use of uninitialized list value, or index out of range.", "", "", "");
				pc = new RTHalt();	// halt instruction
			} else {
				RTObject result = null;
				if (theListObject.isValidIndex((int)argument.intValue())) {
					result = theListObject.get((int)argument.intValue());

					addRetvalTo(activationRecord);
					activationRecord.setObject("ret$val", result);
					
					pc = super.nextCode();
				} else {
					ErrorLogger.error(ErrorIncidenceType.Runtime, token_,
							"List.at(): List index out-of-range: ",
							Integer.toString((int)argument.intValue()),
							" on list of size ", Integer.toString(theListObject.size()));
					pc = new RTHalt();
				}
			}
			return pc;
		}
		
		private final Token token_;
	}
	public static class RTIndexOfCode extends RTListCommon {
		public RTIndexOfCode(final StaticScope enclosingMethodScope) {
			super("List", "indexOf", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTStackable stackableArgument = activationRecord.getObject("element");
			final RTObject argument = (RTObject)stackableArgument;
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTObject result = theListObject.indexOf(argument);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	public static class RTContainsCode extends RTListCommon {
		public RTContainsCode(final StaticScope enclosingMethodScope) {
			super("List", "contains", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject argument = activationRecord.getObject("element");
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final RTObject result = (RTObject)theListObject.contains(argument);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	public static class RTIsEmptyCode extends RTListCommon {
		public RTIsEmptyCode(final StaticScope enclosingMethodScope) {
			super("List", "isEmpty", asList("element"), asList("T"), enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			final boolean rawResult = theListObject.isEmpty();
			final RTBooleanObject result = new RTBooleanObject(rawResult);
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", result);
			
			return super.nextCode();
		}
	}
	public static class RTSortCode extends RTListCommon {
		public RTSortCode(final StaticScope enclosingMethodScope) {
			super("List", "sort", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			
			theListObject.sort();
	
			return super.nextCode();
		}
	}
	public static class RTReverseCode extends RTListCommon {
		public RTReverseCode(final StaticScope enclosingMethodScope, Type returnTypeDecl) {
			super("List", "reverse", null, null, enclosingMethodScope, returnTypeDecl);
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope) {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			      RTListObject theListObject = (RTListObject)activationRecord.getObject("this");
			
			theListObject = theListObject.reverse();
			
			addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", theListObject);
	
			return super.nextCode();
		}
	}
	

	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static TemplateType listType_;
}
