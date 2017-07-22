package info.fulloo.trygve.run_time;

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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import info.fulloo.trygve.add_ons.PanelClass;
import info.fulloo.trygve.configuration.ConfigurationOptions;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.run_time.RTClass.*;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTExpression.RTAssignment;
import info.fulloo.trygve.run_time.RTExpression.RTAssignment.RTAssignmentPart2;
import info.fulloo.trygve.run_time.RTExpression.RTAssignment.RTAssignmentPart2.RTAssignmentPart2B;
import info.fulloo.trygve.run_time.RTExpression.RTConstant;
import info.fulloo.trygve.run_time.RTExpression.RTIdentifier;
import info.fulloo.trygve.run_time.RTExpression.RTIf;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTExpression.RTMessage.RTPostReturnProcessing;
import info.fulloo.trygve.run_time.RTExpression.RTNew;
import info.fulloo.trygve.run_time.RTExpression.RTQualifiedIdentifier;
import info.fulloo.trygve.run_time.RTExpression.RTQualifiedIdentifier.RTQualifiedIdentifierPart2;
import info.fulloo.trygve.run_time.RTExpression.RTReturn;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTDoubleObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public class RunTimeEnvironment {
	public static RunTimeEnvironment runTimeEnvironment_;
	public RunTimeEnvironment(final TextEditorGUI gui) {
		super();
		gui_ = gui;
		stringToRTContextMap_ = new LinkedHashMap<String, RTContext>();
		stringToRTClassMap_ = new LinkedHashMap<String, RTClass>();
		stringToRTInterfaceMap_ = new LinkedHashMap<String, RTInterface>();
		pathToTypeMap_ = new LinkedHashMap<String, RTType>();
		reboot();
		setRunTimeEnvironment(this);
		allClassList_ = new ArrayList<RTClass>();
		this.preDeclareTypes();
		if (null != gui) {
			redirectedInputStream_ = gui.getIn();
		} else {
			redirectedInputStream_ = System.in;
		}
	}
	private static void setRunTimeEnvironment(final RunTimeEnvironment theThis) {
		runTimeEnvironment_ = theThis;
	}
	public void reboot() {
		RTExpression.reboot();	// reset lastExpression_, etc.
		swingWorkerThreadStack_ = new Stack<RTStackable>();
		awtEventQueueStack_ = new Stack<RTStackable>();
		swingWorkerDynamicScopes_ = new Stack<RTDynamicScope>();
		awtEventDynamicScopes_ = new Stack<RTDynamicScope>();
		awtEventDynamicScopes_.push(this.globalDynamicScope);
		swingWorkerFramePointerStack_ = new Stack<Integer>();
		awtEventFramePointerStack_ = new Stack<Integer>();
		if (null != gui_) {
			redirectedInputStream_ = gui_.getIn();
		} else {
			redirectedInputStream_ = System.in;
		}
	}
	public final Stack<RTStackable> theStack() {
		final String threadName = Thread.currentThread().getName();
		if (threadName.startsWith("AWT-EventQueue")) {
			return awtEventQueueStack_;
		} else {
			return swingWorkerThreadStack_;
		}
	}
	public void stopCurrentThread() {
		final String threadName = Thread.currentThread().getName();
		if (threadName.startsWith("AWT-EventQueue")) {
			awtIsRunning_ = false;
		} else {
			swingWorkerIsRunning_ = false;
		}
	}
	public void stopAllThreads() {
		awtIsRunning_ = swingWorkerIsRunning_ = false;
	}
	public boolean isRunning() {
		boolean retval = true;
		final String threadName = Thread.currentThread().getName();
		if (threadName.startsWith("AWT-EventQueue")) {
			retval = awtIsRunning_;
		} else {
			retval = swingWorkerIsRunning_;
		}
		return retval/* & !Thread.currentThread().isInterrupted()*/;
	}
	public void resetIsRunningForAll() {
		awtIsRunning_ = swingWorkerIsRunning_ = true;
	}
	private Stack<Integer> framePointerStack() {
		final String threadName = Thread.currentThread().getName();
		if (threadName.startsWith("AWT-EventQueue")) {
			return awtEventFramePointerStack_;
		} else {
			return swingWorkerFramePointerStack_;
		}
	}
	private Stack<RTDynamicScope> dynamicScopeStack() {
		final String threadName = Thread.currentThread().getName();
		if (threadName.startsWith("AWT-EventQueue")) {
			return awtEventDynamicScopes_;
		} else {
			return swingWorkerDynamicScopes_;
		}
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
		final ClassDeclaration eventClassDecl = StaticScope.globalScope().lookupClassDeclaration("Event");
		assert null != eventClassDecl;
		
		this.addTopLevelClass("int",     new RTIntegerClass(intClassDecl));
		this.addTopLevelClass("Integer", new RTIntegerClass(int2ClassDecl));
		this.addTopLevelClass("double",  new RTDoubleClass(doubleClassDecl));
		this.addTopLevelClass("boolean", new RTBooleanClass(booleanClassDecl));
		this.addTopLevelClass("String",  new RTStringClass(stringClassDecl));
		this.addTopLevelClass("Event",  new PanelClass.RTEventClass(eventClassDecl));
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
		resetIsRunningForAll();
		
		// Set up an activation record. We even need one for
		// main so it can declare t$his, if there's an
		// argument to the constructor
		handleMetaInits();
		
		final RTExpression exitNode = new RTHalt();
		mainExpr.setNextCode(exitNode);
		
		final RTDynamicScope firstActivationRecord = new RTDynamicScope("_main", null, true);
		globalDynamicScope = firstActivationRecord;
		RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(firstActivationRecord);
		firstActivationRecord.incrementReferenceCount();
		
		// Take all object declarations from global scope and
		// get them into firstActivationRecord
		final StaticScope globalScope = StaticScope.globalScope();
		final List<ObjectDeclaration> objectDeclarations = globalScope.objectDeclarations();
		for (final ObjectDeclaration objectDeclaration : objectDeclarations) {
			firstActivationRecord.addObjectDeclaration(objectDeclaration.name(), null);
		}
		
		// And go.
		RTCode pc = mainExpr;
		do {
			final RTCode oldPc = pc;
			pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			if (null != pc) {
				if (pc instanceof RTHalt) {
					pc = null;
					break;
				} else {
					pc.incrementReferenceCount();
				}
			}
			oldPc.decrementReferenceCount();
		} while (pc != null && pc != exitNode/* && !Thread.currentThread().isInterrupted()*/);
		finalCleanup();
	}
	private void finalCleanup() {
		final RTObject lastEvaluated = RTExpression.lastExpressionResult();
		if (null != lastEvaluated && false == lastEvaluated instanceof RTNullObject) {
			lastEvaluated.decrementReferenceCount();
		}
		RTExpression.setLastExpressionResult(new RTNullObject(), 0);
		if (null != RunTimeEnvironment.runTimeEnvironment_) {
			final RTDebuggerWindow debugger = RunTimeEnvironment.runTimeEnvironment_.rTDebuggerWindow();
			if (null != debugger) {
				debugger.debuggerWindowMessage("Execution complete\n");
			}
		}
	}
	public synchronized void setFramePointer() {
		final int stackSize = theStack().size();
		framePointerStack().push(new Integer(stackSize));
	}
	
	public RTStackable popDownToFramePointer() {
		RTStackable retval = null;
		final int stackSize = theStack().size();
		final int framePointer = (framePointerStack().pop()).intValue();
		if (framePointer > stackSize) {
			ErrorLogger.error(ErrorIncidenceType.Internal, 0, "Stack corruption: framePointer ", String.valueOf(framePointer), 
					" > stackSize ", String.valueOf(stackSize));
			assert false;
		}
		while (theStack().size() > framePointer) {
			@SuppressWarnings("unused")
			final RTStackable popped = theStack().pop();	// save value here for debugging only
		}
		retval = theStack().peek();
		return retval;
	}
	public RTStackable popDownToFramePointerMinus1() {
		RTStackable retval = null;
		final int stackSize = theStack().size();
		int framePointer = (framePointerStack().pop()).intValue() + 1;
		if (framePointer < stackSize) {
			ErrorLogger.error(ErrorIncidenceType.Internal, 0, "Stack corruption: framePointer ", String.valueOf(framePointer), 
					" > stackSize ", String.valueOf(stackSize));
			assert false;
		}
		while (theStack().size() > framePointer) {
			theStack().pop();
		}
		retval = theStack().peek();
		return retval;
	}
	
	private void handleMetaInits() {
		for (final RTClass aRunTimeClass : allClassList_) {
			aRunTimeClass.metaInit();
			aRunTimeClass.postSetupInitialization();
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

		theStack().push(stackable);
		
		if (ConfigurationOptions.runtimeStackTrace()) {
			printStack();
		}
	}
	public RTStackable popStack() {
		if (ConfigurationOptions.runtimeStackTrace()) {
			printStack();
		}
		final RTStackable retval = theStack().pop();
		return retval;
	}
	public synchronized int stackIndex() {
		return theStack().size();
	}
	public synchronized RTStackable stackValueAtIndex(final int index) {
		return theStack().get(index);
	}
	public synchronized RTStackable peekStack() { return theStack().peek(); }
	public int stackSize() { return theStack().size(); }
	
	public void pushDynamicScope(final RTDynamicScope element) {
		// Reference count is managed by the caller
		dynamicScopeStack().push(element);
		if (ConfigurationOptions.activationRecordStackTrace()) {
			printDynamicScopeStack();
		}
	}
	public RTDynamicScope popDynamicScope() {
		final RTDynamicScope retval = dynamicScopeStack().pop();
		
		if (ConfigurationOptions.activationRecordStackTrace()) {
			printDynamicScopeStack();
		}
		
		// We don't decrement the reference count here; that is handled
		// elsewhere (see, e.g., RTReturn>>run())
		return retval;
	}
	private void printDynamicScopeStack() {
		System.err.format("vvvvvvvvvvvvvvvvvvvvvv\n");
		for (final RTDynamicScope aScope : dynamicScopeStack()) {
			System.err.format("%s\n", aScope.name());
		}
		System.err.format("^^^^^^^^^^^^^^^^^^^^^^\n");
	}
	public RTDynamicScope currentDynamicScope() {
		if (dynamicScopeStack().size() <= 0) {
			assert dynamicScopeStack().size() > 0;
		}
		return dynamicScopeStack().peek();
	}
	public void popDynamicScopeInstances(final long depth) {
		// May be zero, but usually not
		for (int i = 0; i < depth; i++) {
			final RTDynamicScope retval = popDynamicScope();
			retval.decrementReferenceCount();
		}
	}
	public int currentFramePointer() {
		// Mainly for debugging
		int retval = -1;
		if (framePointerStack().size() > 0) {
			retval = framePointerStack().peek();
		}
		return retval;
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
	private void runnerPrefix(final RTCode code) {
		if (null == code) {
			assert null != code;		// put the check up here, out of the
										// code we will be stepping through...
		}
		final PrintStream stream = System.err;
		if (ConfigurationOptions.fullExecutionTrace()) {
			if (null == code.getClass()) {
				stream.format("> *code == NULL\n");
			} else {
				String lineNumber = "   ";
				if (code instanceof RTMessage) {
					lineNumber = Integer.toString(((RTMessage)code).lineNumber()) + ".";
				} else if (code instanceof RTMethod) {
					lineNumber = Integer.toString(((RTMethod)code).lineNumber()) + ".";
				} else if (code instanceof RTIdentifier) {
					lineNumber = Integer.toString(((RTIdentifier)code).lineNumber()) + ".";
				} else if (code instanceof RTAssignment) {
					lineNumber = Integer.toString(((RTAssignment)code).lineNumber()) + ".";
				} else if (code instanceof RTAssignmentPart2) {
					lineNumber = Integer.toString(((RTAssignmentPart2)code).lineNumber()) + ".";
				} else if (code instanceof RTAssignmentPart2B) {
					lineNumber = Integer.toString(((RTAssignmentPart2B)code).lineNumber()) + ".";
				} else if (code instanceof RTNew) {
					lineNumber = Integer.toString(((RTNew)code).lineNumber()) + ".";
				} else if (code instanceof RTIf) {
					lineNumber = Integer.toString(((RTIf)code).lineNumber()) + ".";
				} else if (code instanceof RTConstant) {
					lineNumber = Integer.toString(((RTConstant)code).lineNumber()) + ".";
				} else if (code instanceof RTQualifiedIdentifier) {
					lineNumber = Integer.toString(((RTQualifiedIdentifier)code).lineNumber()) + ".";
				} else if (code instanceof RTQualifiedIdentifierPart2) {
					lineNumber = Integer.toString(((RTQualifiedIdentifierPart2)code).lineNumber()) + ".";
				} else if (code instanceof RTReturn) {
					final int iLineNumber = ((RTReturn)code).lineNumber();
					if (0 > iLineNumber) {
						lineNumber = Integer.toString(iLineNumber) + ".";
					}
				}

				stream.format("> %4s  %s", lineNumber, code.getClass().getSimpleName());
				
				if (code instanceof RTMessage) {
					stream.format(" \"%s\"", ((RTMessage)code).methodSelectorName());
				} else if (code instanceof RTMethod) {
					stream.format(" \"%s\"", ((RTMethod)code).methodDeclaration().getText());
				} else if (code instanceof RTNew) {
					stream.format(" \"%s\"", ((RTNew)code).toString());
				} else if (code instanceof RTIdentifier) {
					stream.format(" \"%s\"", ((RTIdentifier)code).name());
				} else if (code instanceof RTReturn) {
					stream.format(" from \"%s\"", ((RTReturn)code).methodName());
				} else if (code instanceof RTQualifiedIdentifier) {
					stream.format(" from \"%s\"", ((RTQualifiedIdentifier)code).getText());
				} else if (code instanceof RTPostReturnProcessing) {
					stream.format(" for \"%s\"", ((RTPostReturnProcessing)code).name());
				} else if (code instanceof RTConstant) {
					stream.format(" for \"%s\"", ((RTConstant)code).getText());
				} else if (code instanceof RTAssignment) {
					stream.format(" (\"%s\")", ((RTAssignment)code).getText());
				} else if (code instanceof RTAssignmentPart2) {
					stream.format(" (\"%s\")", ((RTAssignmentPart2)code).getText());
				}
				stream.format("\n");
			}
		}
	}
	public RTCode runner(final RTCode code) {
		runnerPrefix(code);
		if (code.isBreakpoint()) {
			rTDebugger_.breakpointFiredAt(code);
		}
		final RTCode retval = isRunning()? code.run(): null;
		// Thread.yield();		// be a good citizen
		return retval;
	}
	
	public InputStream redirectedInputStream() {
		return redirectedInputStream_;
	}
	public TextEditorGUI gui() {
		return gui_;
	}
	
	public void printStack() {
		final int stackSize = theStack().size();
		System.err.format("________________________________________________________ (%d)\n", stackSize);
		final int endIndex = stackSize > 5? stackSize - 5: 0;
		for (int i = stackSize-1; i >= endIndex; i--) {
			RTStackable element = theStack().elementAt(i);
			if (null == element) {
				System.err.format(":  NULL");
			} else {
				System.err.format(":  %s", element.getClass().getSimpleName());
				if (element instanceof RTIntegerObject) {
					System.err.format(" (\"%d\")", ((RTIntegerObject)element).intValue());
				} else if (element instanceof RTDoubleObject) {
					System.err.format(" (\"%f\")", ((RTDoubleObject)element).doubleValue());
				} else if (element instanceof RTStringObject) {
					System.err.format(" (\"%s\")", ((RTStringObject)element).stringValue());
				} else if (element instanceof RTBooleanObject) {
					System.err.format(" (\"%b\")", ((RTBooleanObject)element).value());
				} else if (element instanceof RTContextObject) {
					System.err.format(" (\"%s\")", ((RTContextObject)element).rTType().name());
				} else if (element instanceof RTRole) {
					System.err.format(" (\"%s\")", ((RTRole)element).name());
				} else if (element instanceof RTPostReturnProcessing) {
					System.err.format(" for \"%s\"", ((RTPostReturnProcessing)element).name());
				}
			}
			if (framePointerStack().contains(new Integer(i-1))) {
				System.err.format(" <== frame pointer (%d)", i+1);
			}
			System.err.format("\n");
		}
		if (endIndex != 0) {
			System.err.format(":  ...\n");
		}
	}
	
	public void setDebugger(RTDebuggerWindow rTDebugger) {
		rTDebugger_ = rTDebugger;
	}
	public RTDebuggerWindow rTDebuggerWindow() {
		return rTDebugger_;
	}
	public final Collection<RTClass> allRTClasses() {
		final Collection<RTClass> retval = stringToRTClassMap_.values();
		return retval;
	}
	public final Collection<RTContext> allRTContexts() {
		final Collection<RTContext> retval = stringToRTContextMap_.values();
		return retval;
	}
	
	
	private final Map<String, RTContext> stringToRTContextMap_;
	private final Map<String, RTClass> stringToRTClassMap_;
	private final Map<String, RTInterface> stringToRTInterfaceMap_;
	private final Map<String, RTType> pathToTypeMap_;
	
	private       Stack<RTStackable> swingWorkerThreadStack_;
	private       Stack<RTStackable> awtEventQueueStack_;
	private       Stack<Integer> swingWorkerFramePointerStack_;
	private       Stack<Integer> awtEventFramePointerStack_;
	
	private       boolean awtIsRunning_, swingWorkerIsRunning_;
	
	private       Stack<RTDynamicScope> swingWorkerDynamicScopes_;
	private       Stack<RTDynamicScope> awtEventDynamicScopes_;
	
	private final List<RTClass> allClassList_;
	public        RTDynamicScope globalDynamicScope;
	private       InputStream redirectedInputStream_;
	private final TextEditorGUI gui_;
	private       RTDebuggerWindow rTDebugger_;
}
