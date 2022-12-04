package info.fulloo.trygve.run_time;

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

import java.awt.Event;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class RTEventObject extends RTObjectCommon {
	public RTEventObject(final RTType eventType) {
		super(eventType);
		eventType_ = eventType;	// e.g. an instance of RTClass
		theEvent_ = null;
		
		this.setupCommon();
		
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	private void setupCommon() {
		final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
		final StaticScope intScope = intType.enclosedScope();
		final RTType rTIntType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(intScope);
		
		final Type stringType = StaticScope.globalScope().lookupTypeDeclaration("String");
		final StaticScope stringScope = stringType.enclosedScope();
		final RTType rTStringType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(stringScope);
		
		this.addObjectDeclaration("id", rTIntType);
		this.addObjectDeclaration("key", rTIntType);
		this.addObjectDeclaration("keyString", rTStringType);
		this.addObjectDeclaration("x", rTIntType);
		this.addObjectDeclaration("y", rTIntType);
	}
	
	public int size() {
		assert false;
		return 0;
	}
	@Override public int hashCode() {
		return theEvent_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		assert false;
		return false;
	}
	@Override public String toString() {
		return "<event>";
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
	
	public RTEventObject(final Event theEvent, final RTType eventType) {
		super(eventType);
		theEvent_ = theEvent;
		eventType_ = eventType;
		
		this.setupCommon();
		
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	@Override public RTObject dup() {
		final RTEventObject retval = new RTEventObject(theEvent_, eventType_);
		return retval;
	}
	@Override public RTType rTType() {
		return eventType_;
	}

	public static void ctor1(final RTObject theEventObject) {
		theEventObject.setObject("id", new RTNullObject());
		theEventObject.setObject("key", new RTNullObject());
		theEventObject.setObject("keyString", new RTNullObject());
		theEventObject.setObject("x", new RTNullObject());
		theEventObject.setObject("y", new RTNullObject());
	}
	public static RTObject ctor1(final Event e) {
		final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
		final StaticScope intScope = intType.enclosedScope();
		final RTType rTIntType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(intScope);
		
		final Type stringType = StaticScope.globalScope().lookupTypeDeclaration("String");
		final StaticScope stringScope = stringType.enclosedScope();
		final RTType rTStringType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(stringScope);
		
		final Type eventType = StaticScope.globalScope().lookupTypeDeclaration("Event");
		final StaticScope eventScope = eventType.enclosedScope();
		final RTType rTEventType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(eventScope);
		
		final RTObject theEventObject = new RTObjectCommon(rTEventType);
		theEventObject.addObjectDeclaration("id", rTIntType);
		theEventObject.addObjectDeclaration("key", rTIntType);
		theEventObject.addObjectDeclaration("keyString", rTStringType);
		theEventObject.addObjectDeclaration("x", rTIntType);
		theEventObject.addObjectDeclaration("y", rTIntType);
		
		theEventObject.setObject("x", new RTIntegerObject(e.x));
		theEventObject.setObject("y", new RTIntegerObject(e.y));
		theEventObject.setObject("id", new RTIntegerObject(e.id));
		theEventObject.setObject("key", new RTIntegerObject(e.key));
		final char cKey = (char)e.key;
		final String keyAsString = "" + cKey;
		theEventObject.setObject("keyString", new RTStringObject(keyAsString));
		
		return theEventObject;
	}
	
	private       Event theEvent_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType eventType_;
}