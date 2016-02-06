package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.4
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

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RTDateObject extends RTObjectCommon {
	public RTDateObject(final RTType dateType) {
		super(dateType);	// 
		dateType_ = dateType;	// e.g. an instance of RTClass
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
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
	private RTDateObject(final Calendar theDate, final RTType dateType) {
		super(dateType);
		theDate_ = (Calendar)theDate.clone();
		dateType_ = dateType;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	@Override public RTObject dup() {
		final RTDateObject retval = new RTDateObject(theDate_, dateType_);
		return retval;
	}
	@Override public RTType rTType() {
		return dateType_;
	}
	public RTObject getYear() {
		final int year = theDate_.get(Calendar.YEAR) + 1900;
		return new RTIntegerObject(year);
	}
	public RTObject getMonth() {
		final int month = theDate_.get(Calendar.MONTH) + 1;
		return new RTIntegerObject(month);
	}
	public RTObject getDate() {
		final int date = theDate_.get(Calendar.DATE);
		return new RTIntegerObject(date);
	}
	public RTObject getDay() {
		final int day = theDate_.get(Calendar.DAY_OF_WEEK);
		return new RTIntegerObject(day);
	}
	public RTObject toStringCall() {
		final String string = theDate_.toString();
		return new RTStringObject(string);
	}
	public void setYear(final RTObject year) {
		theDate_.set(Calendar.YEAR, (int)((RTIntegerObject)year).intValue() - 1900);
	}
	public void setMonth(final RTObject month) {
		theDate_.set(Calendar.MONTH, (int)((RTIntegerObject)month).intValue() - 1);
	}
	public void setDate(final RTObject date) {
		theDate_.set(Calendar.DATE, (int)((RTIntegerObject)date).intValue());
	}
	public void setDay(final RTObject day) {
		theDate_.set(Calendar.DAY_OF_WEEK, (int)((RTIntegerObject)day).intValue());
	}
	public void ctor(final RTObject year, final RTObject month, final RTObject date) {
		simpleCtor();
		setYear(year);
		setMonth(month);
		setDate(date);
	}
	public void simpleCtor() {
		theDate_ = Calendar.getInstance();
	}
	@Override public int hashCode() {
		return theDate_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		boolean retval = true;
		if (other instanceof RTDateObject) {
			retval = theDate_.equals(((RTDateObject)other).theDate_);
		} else {
			retval = false;
		}
		return retval;
	}
	@Override public int compareTo(final Object other) {
		int retval = 0;
		if (other instanceof RTDateObject) {
			retval = theDate_.compareTo(((RTDateObject)other).theDate_);
		} else {
			assert false;
		}
		return retval;
	}
	
	private Calendar theDate_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType dateType_;
}
