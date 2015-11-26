package info.fulloo.trygve.run_time;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.run_time.RTClass.*;
import info.fulloo.trygve.run_time.RTExpression.RTNullExpression;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public class RunTimeEnvironment {
	public static RunTimeEnvironment runTimeEnvironment_;
	public RunTimeEnvironment() {
		super();
		stringToRTContextMap_ = new LinkedHashMap<String, RTContext>();
		stringToRTClassMap_ = new LinkedHashMap<String, RTClass>();
		stringToRTInterfaceMap_ = new LinkedHashMap<String, RTInterface>();
		pathToTypeMap_ = new LinkedHashMap<String, RTType>();
		reboot();
		runTimeEnvironment_ = this;
		allClassList_ = new ArrayList<RTClass>();
		this.preDeclareTypes();
	}
	public void reboot() {
		stack = new Stack<RTStackable>();
		dynamicScopes = new Stack<RTDynamicScope>();
		framePointers_ = new Stack<IntWrapper>();
	}
	private void preDeclareTypes() {
		final ClassDeclaration intClassDecl = StaticScope.globalScope().lookupClassDeclaration("int");
		assert null != intClassDecl;
		final ClassDeclaration int2ClassDecl = StaticScope.globalScope().lookupClassDeclaration("Integer");
		assert null != int2ClassDecl;
		final ClassDeclaration doubleClassDecl = StaticScope.globalScope().lookupClassDeclaration("double");
		assert null != doubleClassDecl;
		final ClassDeclaration stringClassDecl = StaticScope.globalScope().lookupClassDeclaration("String");
		assert null != stringClassDecl;
		final ClassDeclaration booleanClassDecl = StaticScope.globalScope().lookupClassDeclaration("boolean");
		assert null != booleanClassDecl;
		
		this.addTopLevelClass("int",     new RTIntegerClass(intClassDecl));
		this.addTopLevelClass("Integer", new RTIntegerClass(int2ClassDecl));
		this.addTopLevelClass("double",  new RTDoubleClass(doubleClassDecl));
		this.addTopLevelClass("boolean", new RTBooleanClass(booleanClassDecl));
		this.addTopLevelClass("String",  new RTStringClass(stringClassDecl));
	}
	public void addTopLevelContext(final String contextName, final RTContext context) {
		stringToRTContextMap_.put(contextName, context);
	}
	public void addTopLevelClass(final String className, final RTClass aClass) {
		stringToRTClassMap_.put(className, aClass);
	}
	public void addTopLevelInterface(final String interfaceName, final RTInterface anInterface) {
		stringToRTInterfaceMap_.put(interfaceName, anInterface);
	}
	public RTContext topLevelContextNamed(final String contextName) {
		return stringToRTContextMap_.get(contextName);
	}
	public RTClass topLevelClassNamed(final String className) {
		final RTClass retval =  stringToRTClassMap_.get(className);
		return retval;
	}
	public RTInterface topLevelInterfaceNamed(final String interfaceName) {
		final RTInterface retval =  stringToRTInterfaceMap_.get(interfaceName);
		return retval;
	}
	public RTType topLevelTypeNamed(final String name) {
		RTType retval = this.topLevelContextNamed(name);
		if (null == retval) {
			retval = this.topLevelClassNamed(name);
			if (null == retval) {
				retval = this.topLevelInterfaceNamed(name);
			}
		}
		return retval;
	}
	public void run(final RTExpression mainExpr) {
		// Set up an activation record. We even need one for
		// main so it can declare t$his, if there's an
		// argument to the constructor
		handleMetaInits();
		
		final RTExpression exitNode = new RTNullExpression();
		mainExpr.setNextCode(exitNode);
		
		final RTDynamicScope firstActivationRecord = new RTDynamicScope("_main", null);
		globalDynamicScope = firstActivationRecord;
		RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(firstActivationRecord);
		
		// And go.
		RTCode pc = mainExpr;
		do {
			final RTCode oldPc = pc;
			pc = pc.run();
			if (null != pc) {
				pc.incrementReferenceCount();
			}
			oldPc.decrementReferenceCount();
		} while (pc != null && pc != exitNode);
	}
	public void setFramePointer() {
		final int stackSize = stack.size();
		framePointers_.push(new IntWrapper(stackSize));
	}
	private static class IntWrapper {
		public IntWrapper(final int value) {
			value_ = value;
		}
		public int value() {
			return value_;
		}
		private int value_;
	}
	public RTStackable popDownToFramePointer() {
		RTStackable retval = null;
		final int stackSize = stack.size();
		int framePointer = (framePointers_.pop()).value();
		if (framePointer > stackSize) {
			ErrorLogger.error(ErrorType.Internal, 0, "Stack corruption: framePointer ", String.valueOf(framePointer), 
					" > stackSize ", String.valueOf(stackSize));
			assert false;
		}
		while (stack.size() > framePointer) {
			stack.pop();
		}
		retval = stack.peek();
		return retval;
	}
	public RTStackable popDownToFramePointerMinus1() {
		RTStackable retval = null;
		final int stackSize = stack.size();
		int framePointer = (framePointers_.pop()).value() + 1;
		if (framePointer < stackSize) {
			ErrorLogger.error(ErrorType.Internal, 0, "Stack corruption: framePointer ", String.valueOf(framePointer), 
					" > stackSize ", String.valueOf(stackSize));
			assert false;
		}
		while (stack.size() > framePointer) {
			stack.pop();
		}
		retval = stack.peek();
		return retval;
	}
	
	private void handleMetaInits() {
		for (final RTClass aRunTimeClass : allClassList_) {
			aRunTimeClass.metaInit();
		}
	}
	public void addToListOfAllClasses(final RTClass aClass) {
		if (allClassList_.contains(aClass)) {
			assert false;
		} else {
			allClassList_.add(aClass);
		}
	}
	
	public void pushStack(final RTStackable stackable) {
		if (null != stackable) {
			// Can be null (e.g., the nextCode for end-of-evaluation at the end of the program)
			stackable.incrementReferenceCount();
		}
		stack.push(stackable);
	}
	public RTStackable popStack() {
		final RTStackable retval = stack.pop();
		return retval;
	}
	public int stackIndex() {
		return stack.size();
	}
	public RTStackable stackValueAtIndex(final int index) {
		return stack.get(index);
	}
	public RTStackable peekStack() { return stack.peek(); }
	public int stackSize() { return stack.size(); }
	
	public void pushDynamicScope(final RTDynamicScope element) {
		// Subtle. Reference count was initialized to one and this is
		// its first use, so we don't increment
		// element.incrementReferenceCount();
		dynamicScopes.push(element);
	}
	public RTDynamicScope popDynamicScope() {
		final RTDynamicScope retval = dynamicScopes.pop();
		
		// We don't decrement the reference count here; that is handled
		// elsewhere (see, e.g., RTReturn>>run())
		return retval;
	}
	public RTDynamicScope currentDynamicScope() {
		return dynamicScopes.peek();
	}
	public void popDynamicScopeInstances(long depth) {
		// May be zero, but usually not
		for (int i = 0; i < depth; i++) {
			final RTDynamicScope retval = dynamicScopes.pop();
			retval.decrementReferenceCount();
		}
	}
	public void registerTypeByPath(final String path, final RTType rTType) {
		pathToTypeMap_.put(path, rTType);
	}
	public RTType typeFromPath(final String path) {
		RTType retval;
		if (pathToTypeMap_.containsKey(path)) {
			retval = pathToTypeMap_.get(path);
		} else {
			retval = null;
		}
		return retval;
	}
	
	
	private Map<String, RTContext> stringToRTContextMap_;
	private Map<String, RTClass> stringToRTClassMap_;
	private Map<String, RTInterface> stringToRTInterfaceMap_;
	private Map<String, RTType> pathToTypeMap_;
	private Stack<RTStackable> stack;
	private Stack<IntWrapper> framePointers_;
	private Stack<RTDynamicScope> dynamicScopes;
	private List<RTClass> allClassList_;
	public  RTDynamicScope globalDynamicScope;
}
