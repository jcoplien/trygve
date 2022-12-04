package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 4.0
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

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;

public class RTColorObject extends RTObjectCommon {
	public RTColorObject(final RTType colorType, final String colorName) {
		super(colorType);
		colorType_ = colorType;	// e.g. an instance of RTClass
		theColor_ = null;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
		colorName_ = colorName;
	}
	
	public int size() {
		assert false;
		return 0;
	}
	@Override public int hashCode() {
		return theColor_.hashCode();
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
	private RTColorObject(final Color theColor, final RTType colorType) {
		super(colorType);
		theColor_ = theColor;
		colorType_ = colorType;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	public RTColorObject(int r, int g, int b, final String colorName, final RTType colorType) {
		super(colorType);
		colorType_ = colorType;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
		theColor_ = new Color(r, g, b);
		colorName_ = colorName;
	}
	public RTColorObject(int r, int g, int b, final RTType colorType) {
		this(r, g, b, null, colorType);
	}

	@Override public RTObject dup() {
		final Color dupedColor = new Color(theColor_.getRed(), theColor_.getGreen(), theColor_.getBlue());
		final RTColorObject retval = new RTColorObject(dupedColor, colorType_);
		return retval;
	}
	@Override public RTType rTType() {
		return colorType_;
	}
	
	public void ctor1(final int r, final int g, final int b) {
		theColor_ = new Color(r, g, b);
	}
	public void ctor2(final double r, final double g, final double b) {
		theColor_ = new Color((int)(255*r), (int)(255*g), (int)(255*b));
	}
	public void ctor3(final int r, final int g, final int b, final int alpha) {
		theColor_ = new Color(r, g, b, alpha);
	}
	public void ctor4(final double r, final double g, final double b, final double alpha) {
		theColor_ = new Color((int)(255*r), (int)(255*g), (int)(255*b), (int)(255*alpha));
	}
	
	public int getRed() {
		return theColor_.getRed();
	}
	public int getGreen() {
		return theColor_.getGreen();
	}
	public int getBlue() {
		return theColor_.getBlue();
	}
	public Color color() {
		return theColor_;
	}
	@Override public String toString() {
		String retval = colorName_;
		if (null == retval) {
			retval = String.format("(red=%d, green=%d, blue=%d)", getRed(), getGreen(), getBlue());
		}
		return retval;
	}
	
	
	private       Color theColor_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType colorType_;
	private       String colorName_;
}