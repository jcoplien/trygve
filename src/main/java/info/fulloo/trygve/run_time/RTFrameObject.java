package info.fulloo.trygve.run_time;

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
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;

public class RTFrameObject extends RTObjectCommon implements RTObject {
	public RTFrameObject(final RTType frameType) {
		super(frameType);
		frameType_ = frameType;	// e.g. an instance of RTClass
		theFrame_ = null;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public int size() {
		assert false;
		return 0;
	}
	@Override public int hashCode() {
		return theFrame_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		assert false;
		return false;
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
			ErrorLogger.error(ErrorType.Fatal, "Object of type ", this.rTType().name(),
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
	public void ctor1(final RTObject nameArg) {
		assert nameArg instanceof RTStringObject;
		final String name = ((RTStringObject)nameArg).stringValue();
		theFrame_ = new Frame(name);
	}
	
	public void ctor1(final String name) {
		theFrame_ = new Frame(name);
		theFrame_.setLayout(new BorderLayout());
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
		theFrame_.add(name, panel);
	}
	
	private       Frame theFrame_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType frameType_;
}