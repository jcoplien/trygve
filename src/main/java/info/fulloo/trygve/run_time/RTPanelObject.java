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

import java.awt.Color;
import java.awt.Panel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.graphics.GraphicsPanel;

public class RTPanelObject extends RTObjectCommon implements RTObject {
	public RTPanelObject(final RTType panelType) {
		super(panelType);
		panelType_ = panelType;	// e.g. an instance of RTClass
		thePanel_ = null;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public int size() {
		assert false;
		return 0;
	}
	@Override public int hashCode() {
		return thePanel_.hashCode();
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
	private RTPanelObject(final GraphicsPanel thePanel, final RTType panelType) {
		super(panelType);
		thePanel_ = thePanel;
		panelType_ = panelType;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	@Override public RTObject dup() {
		final RTPanelObject retval = new RTPanelObject(thePanel_, panelType_);
		return retval;
	}
	@Override public RTType rTType() {
		return panelType_;
	}
	
	public void ctor1() {
		thePanel_ = new GraphicsPanel();
	}
	
	public void setBackground(final RTObject colorArg) {
		assert colorArg instanceof RTColorObject;
		final Color color = ((RTColorObject)colorArg).color();
		thePanel_.setBackground(color);
	}
	public void setForeground(final RTObject colorArg) {
		assert colorArg instanceof RTColorObject;
		final Color color = ((RTColorObject)colorArg).color();
		thePanel_.setBackground(color);
	}
	public void drawLine(final RTObject fromXArg, final RTObject fromYArg, final RTObject toXArg, final RTObject toYArg) {
		assert fromXArg instanceof RTIntegerObject;
		assert fromYArg instanceof RTIntegerObject;
		assert toXArg instanceof RTIntegerObject;
		assert toYArg instanceof RTIntegerObject;
		final int fromX = (int)((RTIntegerObject)fromXArg).intValue();
		final int fromY = (int)((RTIntegerObject)fromYArg).intValue();
		final int toX = (int)((RTIntegerObject)toXArg).intValue();
		final int toY = (int)((RTIntegerObject)toYArg).intValue();
		final Rectangle newRect = new Rectangle(fromX, fromY, Math.abs(toX-fromX), Math.abs(toY-fromY));
		thePanel_.addLine(newRect, null);
	}
	public void drawRect(final RTObject fromXArg, final RTObject fromYArg, final RTObject heightArg, final RTObject widthArg) {
		assert fromXArg instanceof RTIntegerObject;
		assert fromYArg instanceof RTIntegerObject;
		assert widthArg instanceof RTIntegerObject;
		assert heightArg instanceof RTIntegerObject;
		final int fromX = (int)((RTIntegerObject)fromXArg).intValue();
		final int fromY = (int)((RTIntegerObject)fromYArg).intValue();
		final int width = (int)((RTIntegerObject)widthArg).intValue();
		final int height = (int)((RTIntegerObject)heightArg).intValue();
		final Rectangle newRect = new Rectangle(fromX, fromY, width, height);
		thePanel_.addRectangle(newRect, null);
	}
	public void drawEllipse(final RTObject xArg, final RTObject yArg, final RTObject widthArg, final RTObject heightArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert widthArg instanceof RTIntegerObject;
		assert heightArg instanceof RTIntegerObject;
		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final int width = (int)((RTIntegerObject)widthArg).intValue();
		final int height = (int)((RTIntegerObject)heightArg).intValue();
		thePanel_.addEllipse(x, y, width, height, null);
	}
	public void drawString(final RTObject xArg, final RTObject yArg, final RTObject stringArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert stringArg instanceof RTStringObject;
		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final String string = ((RTStringObject)stringArg).stringValue();
		thePanel_.addString(x, y, string, null);
	}
	public Panel panel() {
		return thePanel_;
	}
	
	private       GraphicsPanel thePanel_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType panelType_;
}