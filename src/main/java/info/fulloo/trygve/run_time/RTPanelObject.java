package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.6
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

import java.awt.Panel;
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
		this.setObject("panelObject", thePanel_);
	}
	@Override public RTObject dup() {
		final RTPanelObject retval = new RTPanelObject(thePanel_, panelType_);
		return retval;
	}
	@Override public RTType rTType() {
		return panelType_;
	}
	
	public void ctor1() {
		assert false;
		// Unused?
		// thePanel_ = new GraphicsPanel(this);
		// this.setObject("panelObject", thePanel_);
	}
	
	
	public Panel panel() {
		return thePanel_;
	}
	
	private       GraphicsPanel thePanel_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType panelType_;
}