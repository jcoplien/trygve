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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTIterator.RTListIterator;

public class RTListObject extends RTObjectCommon implements RTObject, RTIterable {
	public RTListObject(RTType listType) {
		super(listType);	// 
		listType_ = listType;	// e.g. an instance of RTClass
		baseType_ = null;
		theList_ = new ArrayList<RTObject>();
		rolesIAmPlayingInContext_ = new HashMap<RTContextObject, List<String>>();
	}
	
	public Type baseType() {
		return baseType_;
	}
	private int calculateIndexFrom(RTObject theIndexObject) {
		int theIndex = -1;
		if (theIndexObject instanceof RTIntegerObject) {
			theIndex = (int)((RTIntegerObject)theIndexObject).intValue();
		} else {
			assert false;
		}
		
		assert theIndex >= 0;
		assert theIndex < theList_.size();
		return theIndex;
	}
	public RTObject getObject(RTObject theIndexObject) {
		final int theIndex = calculateIndexFrom(theIndexObject);
		final RTObject retval = theList_.get(theIndex);
		return retval;
	}
	public int size() {
		return theList_.size();
	}
	public void setObject(RTObject theIndexObject, RTObject rhs) {			
		final int theIndex = calculateIndexFrom(theIndexObject);
		
		final RTObject oldValue = theList_.get(theIndex);
		if (null != oldValue) {
			oldValue.decrementReferenceCount();
		}
		theList_.set(theIndex, rhs);
		rhs.incrementReferenceCount();
	}
	
	// I'm a little unhappy that these are copy-pasted. FIXME.
	@Override public void enlistAsRolePlayerForContext(String roleName, RTContextObject contextInstance) {
		List<String> rolesIAmPlayingHere = null;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
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
	@Override public void unenlistAsRolePlayerForContext(String roleName, RTContextObject contextInstance) {
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
	
	
	public RTObject performUnaryOpOnObject(RTObject theIndex, String operation, PreOrPost preOrPost) {
		assert false;
		return null;
	}
	private RTListObject(List<RTObject> theList, Type baseType, RTType listType) {
		super(listType);
		theList_ = new ArrayList<RTObject>();
		baseType_ = baseType;
		listType_ = listType;
		for (int k = 0; k < theList_.size(); k++) {
			theList_.set(k, theList_.get(k));
		}
		rolesIAmPlayingInContext_ = new HashMap<RTContextObject, List<String>>();
	}
	public RTIterator makeIterator() {
		final RTIterator retval = new RTListIterator(this);
		return retval;
	}
	@Override public RTObject dup() {
		final RTListObject retval = new RTListObject(theList_, baseType_, listType_);
		return retval;
	}
	@Override public RTType rTType() {
		return listType_;
	}
	
	public boolean isEmpty() {
		return theList_.isEmpty();
	}
	public RTObject get(int i) {
		return theList_.get(i);
	}
	public void add(RTObject element) {
		theList_.add(element);
	}
	public void ctor() {
	}
	public RTObject indexOf(RTObject element) {
		final int rawRetval = theList_.indexOf(element);
		final RTObject retval = new RTIntegerObject(rawRetval);
		return retval;
	}
	public RTObject contains(RTObject element) {
		final boolean rawRetval = theList_.contains(element);
		final RTObject retval = new RTBooleanObject(rawRetval);
		return retval;
	}
	
	private final List<RTObject> theList_;
	private final Type baseType_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType listType_;
}