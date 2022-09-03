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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTIterator.RTSetIterator;

public class RTSetObject extends RTObjectCommon implements RTIterable {
	public RTSetObject(final RTType setType) {
		super(setType);	// 
		setType_ = setType;	// e.g. an instance of RTClass
		baseType_ = null;
		theSet_ = new HashSet<RTObject>();
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public Type baseType() {
		return baseType_;
	}
	@Override public RTObject getObject(final RTObject theIndexObject) {
		assert false;
		return null;
	}
	@Override public int size() {
		return theSet_.size();
	}
	@Override public int hashCode() {
		return theSet_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		boolean retval = true;
		if (other instanceof RTSetObject) {
			retval = theSet_.equals(((RTSetObject)other).theSet_);
		} else {
			retval = false;
		}
		return retval;
	}
	@Override public String toString() {
		return "<Set>";
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
	private RTSetObject(final Set<RTObject> theSet, final Type baseType, final RTType setType) {
		super(setType);
		theSet_ = new HashSet<RTObject>();
		baseType_ = baseType;
		setType_ = setType;
		theSet_.addAll(theSet);
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	public RTIterator makeIterator() {
		final RTIterator retval = new RTSetIterator(this);
		return retval;
	}
	@Override public RTObject dup() {
		final RTSetObject retval = new RTSetObject(theSet_, baseType_, setType_);
		return retval;
	}
	@Override public RTType rTType() {
		return setType_;
	}
	
	public boolean isEmpty() {
		return theSet_.isEmpty();
	}
	public boolean isValidIndex(final int i) {
		return i < theSet_.size() && i >= 0;
	}
	public boolean remove(final RTObject o) {
		o.decrementReferenceCount();
		return theSet_.remove(o);
	}
	@Override public void decrementReferenceCount() {
		super.decrementReferenceCount();
		if (referenceCount_ == 0) {
			for (final RTObject o : theSet_) {
				o.decrementReferenceCount();
			}
		} else if (referenceCount_ < 0) {
			assert false;
		}
	}
	public void add(final RTObject element) {
		// We could be adding an element that's already in the set
		// The new one will overwrite the old one. We need to
		// decrement the reference count of the one in the set
		// (if it is there)
		if (theSet_.contains(element)) {
			for (final RTObject o : theSet_) {
				if (o.equals(element)) {
					o.decrementReferenceCount();
					break;
				}
			}
		}
		
		// Now just continue as normal
		theSet_.add(element);
		
		// ...and the Set holds the element so increment its reference count
		element.incrementReferenceCount();
	}
	public void ctor() {
	}
	public RTObject contains(final RTObject element) {
		final boolean rawRetval = theSet_.contains(element);
		final RTObject retval = new RTBooleanObject(rawRetval);
		return retval;
	}
	public int compareTo(final RTObject other) {
		assert false;
		return 0;
	}
	public Iterator<RTObject> RTIterator() {
		return theSet_.iterator();
	}
	
	private final Set<RTObject> theSet_;
	private final Type baseType_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType setType_;
}