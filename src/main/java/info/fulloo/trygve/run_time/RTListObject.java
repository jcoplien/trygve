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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTIterator.RTListIterator;

public class RTListObject extends RTObjectCommon implements RTIterable {
	public RTListObject(final RTType listType) {
		super(listType);	// 
		listType_ = listType;	// e.g. an instance of RTClass
		baseType_ = null;
		theList_ = new ArrayList<RTObject>();
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public Type baseType() {
		return baseType_;
	}
	private int calculateIndexFrom(final RTObject theIndexObject) {
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
	public RTObject getObject(final RTObject theIndexObject) {
		final int theIndex = calculateIndexFrom(theIndexObject);
		final RTObject retval = theList_.get(theIndex);
		return retval;
	}
	public int size() {
		return theList_.size();
	}
	@Override public int hashCode() {
		return theList_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		boolean retval = true;
		if (other instanceof RTListObject) {
			retval = theList_.equals(((RTListObject)other).theList_);
		} else {
			retval = false;
		}
		return retval;
	}
	@Override public String toString() {
		return "<List>";
	}
	public void setObject(final RTObject theIndexObject, final RTObject rhs) {			
		final int theIndex = calculateIndexFrom(theIndexObject);
		
		final RTObject oldValue = theList_.get(theIndex);
		if (null != oldValue) {
			oldValue.decrementReferenceCount();
		}
		theList_.set(theIndex, rhs);
		rhs.incrementReferenceCount();
	}
	
	// I'm more than a little unhappy that these are copy-pasted. FIXME.
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
	private RTListObject(final List<RTObject> theList, final Type baseType, final RTType listType) {
		super(listType);
		theList_ = new ArrayList<RTObject>();
		baseType_ = baseType;
		listType_ = listType;
		for (int j = 0; j < theList_.size(); j++) {
			theList_.set(j, theList_.get(j));
		}
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
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
	public boolean isValidIndex(final int i) {
		return i < theList_.size() && i >= 0;
	}
	public RTObject get(final int i) {
		RTObject retval;
		if (i >= theList_.size()) {
			ErrorLogger.error(ErrorIncidenceType.Runtime, null, "List index out-of-range: ",
					Integer.toString(i), " on list of size ", Integer.toString(theList_.size()));
			RTMessage.printMiniStackStatus();
			retval = null;
		} else {
			retval = theList_.get(i);
		}
		return retval;
	}
	public RTObject remove(final int i) {
		final RTObject retval = theList_.remove(i);
		// don't decrement reference count here, because it may prematurely
		// to to zero and cause bad things to happen (particularly for Context
		// instances) in case the removed value is being assigned to some lhs
		// (as it usually is assigned to ret$val by the caller ListClass$RTRemoveCode)
		return retval;
	}
	public boolean remove(final RTObject o) {
		// here we can decrement reference count and be done with it
		o.decrementReferenceCount();
		return theList_.remove(o);
	}
	@Override public void decrementReferenceCount() {
		super.decrementReferenceCount();
		if (referenceCount_ == 0) {
			for (final RTObject o : theList_) {
				o.decrementReferenceCount();
			}
		} else if (referenceCount_ < 0) {
			assert false;
		}
	}
	public void add(final RTObject element) {
		theList_.add(element);
		element.incrementReferenceCount();
	}
	public void ctor() {
	}
	public RTObject indexOf(final RTObject element) {
		final int rawRetval = theList_.indexOf(element);
		final RTObject retval = new RTIntegerObject(rawRetval);
		return retval;
	}
	public RTObject contains(final RTObject element) {
		final boolean rawRetval = theList_.contains(element);
		final RTObject retval = new RTBooleanObject(rawRetval);
		return retval;
	}
	public int compareTo(final RTObject other) {
		assert false;
		return 0;
	}
	
	static class RTObjectComparator implements Comparator<RTObject> {
        public int compare(final RTObject o1, final RTObject o2) {
            return o1.compareTo(o2);
        }
    }
	public void sort() {
		Collections.sort(theList_, new RTObjectComparator());
	}
	
	private final List<RTObject> theList_;
	private final Type baseType_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType listType_;
}