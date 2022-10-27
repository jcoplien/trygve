package info.fulloo.trygve.run_time;

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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.add_ons.PanelClass.RTEventClass;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTExpression.RTMessage.RTPostReturnProcessing;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class RTFrameObject extends RTObjectCommon {
	public RTFrameObject(final RTType frameType) {
		super(frameType);
		frameType_ = frameType;	// e.g. an instance of RTClass
		theFrame_ = null;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	@Override public int hashCode() {
		return theFrame_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		assert false;
		return false;
	}
	@Override public String toString() {
		return "<frame " + frameType_.name() + ">";
	}
	
	// I'm a little unhappy that these are copy-pasted. FIXME.
	@Override public void enlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {
		List<String> rolesIAmPlayingHere = null;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			; // rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
		} else {
			rolesIAmPlayingHere = new ArrayList<String>();
			rolesIAmPlayingInContext_.put(contextInstance, rolesIAmPlayingHere);
		}
		int count = 0;
		for (Map.Entry<RTContextObject, List<String>> iter : rolesIAmPlayingInContext_.entrySet()) {
			count += iter.getValue().size();
		}
		if ((1 < count) && (1 < rolesIAmPlayingInContext_.size())) {
			ErrorLogger.error(ErrorIncidenceType.Fatal, "Object of type ", this.rTType().name(),
					" playing too many roles, including ", roleName);
		}
	}
	@Override public void unenlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {
		List<String> rolesIAmPlayingHere = null;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
			rolesIAmPlayingHere.remove(roleName);
			if (0 == rolesIAmPlayingHere.size()) {
				rolesIAmPlayingInContext_.remove(contextInstance);
			}
		} else {
			assert false;
		}
	}
	
	
	public RTObject performUnaryOpOnObject(final RTObject theIndex, final String operation, final PreOrPost preOrPost) {
		assert false;
		return null;
	}
	private RTFrameObject(final Frame theFrame, final RTType frameType) {
		super(frameType);
		theFrame_ = theFrame;
		frameType_ = frameType;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	@Override public RTObject dup() {
		final RTFrameObject retval = new RTFrameObject(theFrame_, frameType_);
		return retval;
	}
	@Override public RTType rTType() {
		return frameType_;
	}
	
	public void setBackground(final RTObject colorArg) {
		assert colorArg instanceof RTColorObject;
		final Color color = ((RTColorObject)colorArg).color();
		theFrame_.setBackground(color);
	}
	public void setForeground(final RTObject colorArg) {
		assert colorArg instanceof RTColorObject;
		final Color color = ((RTColorObject)colorArg).color();
		theFrame_.setBackground(color);
	}
	
	private static class MyFrame extends Frame implements RTWindowRegistryEntry {
		public MyFrame(final String loc, final RTFrameObject frameObject) {
			super(loc);
			assert null != frameObject;
			frameObject_ = frameObject;
			RunTimeEnvironment.runTimeEnvironment_.gui().windowCreate(this);
		}
		public MyFrame(final RTFrameObject frameObject) {
			super();
			assert null != frameObject;
			frameObject_ = frameObject;
			RunTimeEnvironment.runTimeEnvironment_.gui().windowCreate(this);
		}
		@Override public boolean handleEvent(final Event e) {
			if (e.id == WindowEvent.WINDOW_CLOSING) {
				handleCloseProgrammatically(e);
				shutDown();
				return true;
			}
			return false;
		}
		public void shutDown() {
			if (null != frameObject_) {
				frameObject_.windowIsClosing();
			}
			RunTimeEnvironment.runTimeEnvironment_.gui().windowCloseDown(this);
			frameObject_ = null;
		}
		public void handleCloseProgrammatically(final Event e) {
			final RTType rTType = frameObject_.rTType();
			assert rTType instanceof RTClass;
			final info.fulloo.trygve.declarations.Type type = StaticScope.globalScope().lookupTypeDeclaration(rTType.name());
			final info.fulloo.trygve.declarations.Type eventType = StaticScope.globalScope().lookupTypeDeclaration("Event");
			final FormalParameterList pl = new FormalParameterList();
			final ObjectDeclaration self = new ObjectDeclaration("this", type, type.token());
			final ObjectDeclaration event = new ObjectDeclaration("e", eventType, null);

			pl.addFormalParameter(event);
			pl.addFormalParameter(self);
			
			final RTMethod hE = rTType.lookupMethod("windowClosing", pl);
			if (null != hE) {
				final int preStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				this.dispatchCloseCall(hE, e);
				
				// Get the return value from the user function telling whether they
				// handled the interrupt or not. If not, we should handle it ourselves
				// (if it's a keystroke event)
				//
				// Note that this shouldn't affect blocked reads...
				RTStackable oldEventArg = RunTimeEnvironment.runTimeEnvironment_.popStack();
				if ((oldEventArg instanceof RTObjectCommon) && ((RTObjectCommon)oldEventArg).rTType() instanceof RTEventClass == false) {
					assert false;
				}
				
				int postStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();

				if (postStackDepth != preStackDepth) {
					// assert postStackDepth == preStackDepth;
					assert (postStackDepth > preStackDepth);
					
					// Dunno what's going on but just try to recover
					while (postStackDepth > preStackDepth) {
						RunTimeEnvironment.runTimeEnvironment_.popStack();
						postStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();
					}
					
					return;
				}
			}
		}
		private void dispatchCloseCall(final RTMethod method, final Event e) {
			// Just do a method call into the user space
			final RTCode halt = null;
			final ClassType eventType = (ClassType)StaticScope.globalScope().lookupTypeDeclaration("Event");
			final RTType rTType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(eventType.enclosedScope());
			final MethodDeclaration methodDecl = method.methodDeclaration();
			final StaticScope methodParentScope = null == methodDecl? null: methodDecl.enclosingScope();
			final String debugName = null == methodParentScope? "???": methodParentScope.name();
			final RTPostReturnProcessing retInst = new RTPostReturnProcessing(halt, "Interrupt", debugName, method.token());
			retInst.setResultIsConsumed(true);
			final RTObject event = RTEventObject.ctor1(e);

			RunTimeEnvironment.runTimeEnvironment_.pushStack(event);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(retInst);
			RunTimeEnvironment.runTimeEnvironment_.setFramePointer();
			
			final RTDynamicScope activationRecord = new RTDynamicScope(
					null,
					method.name(),
					RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope(),
					true);
			
			if (null != methodDecl) {
				// Get formal parameter name that the user used for the "event" parameter
				final MethodSignature signature = methodDecl.signature();
				final FormalParameterList formalParameters = signature.formalParameterList();
				final Declaration eventParameter = formalParameters.parameterAtPosition(1);
				assert eventParameter instanceof ObjectDeclaration;
				final String eventName = eventParameter.name();
				
				activationRecord.addObjectDeclaration(eventName, rTType);
				activationRecord.addObjectDeclaration("this", null);
				activationRecord.setObject(eventName, event);
				activationRecord.setObject("this", frameObject_);
				RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
				activationRecord.incrementReferenceCount();
				
				RTCode pc = method;
				do {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				} while (null != pc && pc instanceof RTHalt == false);
			}
		}
		
		private final static long serialVersionUID = 438492109;
		private RTFrameObject frameObject_;
	}
	
	public void ctor0() {
		theFrame_ = new MyFrame(this);
		theFrame_.setLayout(new BorderLayout());
	}
	
	public void ctor1(final String name) {
		theFrame_ = new MyFrame(name, this);
		theFrame_.setLayout(new BorderLayout());
	}
	
	public void windowIsClosing() {
		theFrame_.dispose();
		theFrame_ = null;
	}
	
	public void resize(final int width, final int height) {
		theFrame_.setSize(width, height);
	}
	public void setSize(final int width, final int height) {
		theFrame_.setSize(width, height);
	}
	public void show() {
		setVisible(true);
	}
	public void setVisible(final boolean tf) {
		theFrame_.setVisible(tf);
	}
	public void add(final String name, final Panel panel) {
		assert true;
		theFrame_.add(name, panel);
	}
	
	private       Frame theFrame_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType frameType_;
}