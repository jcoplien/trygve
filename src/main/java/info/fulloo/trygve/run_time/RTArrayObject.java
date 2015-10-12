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
import info.fulloo.trygve.run_time.RTIterator.RTArrayIterator;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;

public class RTArrayObject implements RTObject, RTIterable {
	public RTArrayObject(int size, RTArrayType arrayType) {
		super();
		size_ = size;
		baseType_ = arrayType.baseType();
		referenceCount_ = 1;
		theArray_ = new RTObject[size_];
		rolesIAmPlayingInContext_ = new HashMap<RTContextObject, List<String>>();
	}
	
	public Type baseType() {
		return baseType_;
	}
	
	@Override public RTObject getObject(String name) {
		assert false;
		return null;
	}
	private int calculateIndexFrom(RTObject theIndexObject) {
		int theIndex = -1;
		if (theIndexObject instanceof RTIntegerObject) {
			theIndex = (int)((RTIntegerObject)theIndexObject).intValue();
		} else {
			assert false;
		}
		
		assert theIndex >= 0;
		assert theIndex < size_;
		return theIndex;
	}
	public RTObject getObject(RTObject theIndexObject) {
		final int theIndex = calculateIndexFrom(theIndexObject);
		
		// final RTObject oldValue = theArray_[theIndex];
		// if (null != oldValue) {
		// 	oldValue.decrementReferenceCount();
		// }
		final RTObject retval = theArray_[theIndex];
		return retval;
	}
	public RTObject get(int theIndex) {
		return theArray_[theIndex];
	}
	public int size() {
		return size_;
	}
	@Override public void addObjectDeclaration(String objectName, RTType type) {
		assert false;
	}
	@Override public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}
	@Override public void setObject(String objectName, RTObject object) {
		// Mapbe someday we upgrade arrays to have Map semantics
		// and do everything with String selectors
		assert false;
	}
	public void setObject(RTObject theIndexObject, RTObject rhs) {			
		final int theIndex = calculateIndexFrom(theIndexObject);
		
		final RTObject oldValue = theArray_[theIndex];
		if (null != oldValue) {
			oldValue.decrementReferenceCount();
		}
		theArray_[theIndex] = rhs;
		rhs.incrementReferenceCount();
	}
	@Override public Map<String, RTObject> objectMembers() {
		assert false;
		return null;
	}
	@Override public RTType rTType() {
		assert false;
		return null;
	}
	@Override public boolean equals(Object another) {
		assert false;
		return false;
	}
	@Override public boolean gt(RTObject another) {
		assert false;
		return false;
	}
	@Override public RTObject plus(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject minus(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject times(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject divideBy(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject modulus(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject unaryPlus() {
		assert false;
		return null;
	}
	@Override public RTObject unaryMinus() {
		assert false;
		return null;
	}
	@Override public RTObject unaryLogicalNegation() {
		assert false;
		return null;
	}
	@Override public RTObject preIncrement() {
		assert false;
		return null;
	}
	@Override public RTObject postIncrement() {
		assert false;
		return null;
	}
	@Override public RTObject preDecrement() {
		assert false;
		return null;
	}
	@Override public RTObject postDecrement() {
		assert false;
		return null;
	}
	@Override public RTObject toThePowerOf(RTObject other) {
		assert false;
		return null;
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
		RTObject retval = null;
		final int index = this.calculateIndexFrom(theIndex);
		if (index >= size_) {
			ErrorLogger.error(ErrorType.Internal, 0, "array index ", Integer.toString(index), " beyone array bounds 0:",
					Integer.toString(size_));
		}
		switch (preOrPost) {
		case Pre:
			if (operation.equals("++")) {
				retval = theArray_[index].preIncrement();
			} else if (operation.equals("--")) {
				retval = theArray_[index].preDecrement();
			} else {
				assert false;
			}
			break;
		case Post:
			if (operation.equals("++")) {
				retval = theArray_[index].postIncrement();
			} else if (operation.equals("--")) {
				retval = theArray_[index].postDecrement();
			} else {
				assert false;
			}
			break;
		}
		return retval;
	}
	@Override public RTObject performUnaryOpOnObjectNamed(String idName, String operator, PreOrPost preOrPost_) {
		assert false;	 // meaningless for arrays
		return null;
	}
	private RTArrayObject(RTObject [] theArray, Type baseType, int size) {
		super();
		theArray_ = theArray.clone();
		baseType_ = baseType;
		size_ = size;
		for (int k = 0; k < size_; k++) {
			theArray_[k] = theArray_[k].dup();
		}
		rolesIAmPlayingInContext_ = new HashMap<RTContextObject, List<String>>();
	}
	public RTIterator makeIterator() {
		final RTIterator retval = new RTArrayIterator(this);
		return retval;
	}
	@Override public RTObject dup() {
		final RTArrayObject retval = new RTArrayObject(theArray_, baseType_, size_);
		return retval;
	}
	@Override public void incrementReferenceCount() {
		referenceCount_++;
	}
	@Override public void decrementReferenceCount() {
		--referenceCount_;
	}
	@Override
	public long referenceCount() {
		return referenceCount_;
	}
	
	private final RTObject [] theArray_;
	private final Type baseType_;
	private final int size_;
	private int referenceCount_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
}