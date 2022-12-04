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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTIterator.RTArrayIterator;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;

public class RTArrayObject implements RTObject, RTIterable {
	public RTArrayObject(final int size, final RTArrayType arrayType) {
		super();
		size_ = size;
		baseType_ = arrayType.baseType();
		rTArrayType_ = arrayType;
		referenceCount_ = 0;
		theArray_ = new RTObject[size_];
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
		stagePropsIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public Type baseType() {
		return baseType_;
	}
	
	@Override public RTObject getObject(final String name) {
		assert false;
		return null;
	}
	
	public void atPut(final RTStackable theIndexParam, final RTStackable objectParam) {
		assert theIndexParam instanceof RTObject;
		assert objectParam instanceof RTObject;
		final RTObject theIndex = (RTObject) theIndexParam;
		final RTObject object = (RTObject) objectParam;
		final int primitiveIndex = calculateIndexFrom(theIndex);
		object.incrementReferenceCount();
		theArray_[primitiveIndex].decrementReferenceCount();
		theArray_[primitiveIndex] = object;
	}
	
	public RTObject at(final RTStackable theIndexParam) {
		assert theIndexParam instanceof RTObject;
		final RTObject theIndex = (RTObject) theIndexParam;
		final int primitiveIndex = calculateIndexFrom(theIndex);
		final RTObject retval = theArray_[primitiveIndex];
		return retval;
	}
	
	@Override public String getText() {
		return "<array>";
	}
	@Override public String toString() {
		return this.getText();
	}
	private int calculateIndexFrom(final RTObject theIndexObject) {
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
	public RTObject getObject(final RTObject theIndexObject) {
		final int theIndex = calculateIndexFrom(theIndexObject);
		
		// final RTObject oldValue = theArray_[theIndex];
		// if (null != oldValue) {
		// 	oldValue.decrementReferenceCount();
		// }
		final RTObject retval = theArray_[theIndex];
		return retval;
	}
	public RTObject get(final int theIndex) {
		return theArray_[theIndex];
	}
	public int size() {
		return size_;
	}
	@Override public void addObjectDeclaration(final String objectName, final RTType type) {
		assert false;
	}
	@Override public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}
	@Override public void setObject(final String objectName, final RTObject object) {
		// Maybe someday we upgrade arrays to have Map semantics
		// and do everything with String selectors
		assert false;
	}
	public void setObject(final RTObject theIndexObject, final  RTObject rhs) {			
		final int theIndex = calculateIndexFrom(theIndexObject);
		
		final RTObject oldValue = theArray_[theIndex];
		if (null != oldValue) {
			oldValue.decrementReferenceCount();
		}
		theArray_[theIndex] = rhs;
		rhs.incrementReferenceCount();
	}
	@Override public RTType rTType() {
		return rTArrayType_;
	}
	@Override public boolean isEqualTo(final Object another) {
		assert false;
		return false;
	}
	@Override public boolean gt(final RTObject another) {
		assert false;
		return false;
	}
	@Override public RTObject plus(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject minus(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject times(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject divideBy(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject modulus(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject logicalAnd(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject logicalOr(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject logicalXor(final RTObject other) {
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
	@Override public RTObject toThePowerOf(final RTObject other) {
		assert false;
		return null;
	}
	
	// I'm a little unhappy that these are copy-pasted. FIXME.
	@Override public void enlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {
		List<String> rolesIAmPlayingHere = null;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			;  // rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
		} else {
			rolesIAmPlayingHere = new ArrayList<String>();
			rolesIAmPlayingInContext_.put(contextInstance, rolesIAmPlayingHere);
		}
		int count = 0;
		for (final Map.Entry<RTContextObject, List<String>> iter : rolesIAmPlayingInContext_.entrySet()) {
			count += iter.getValue().size();
		}
		if ((1 < count) && (1 < rolesIAmPlayingInContext_.size())) {
			ErrorLogger.error(ErrorIncidenceType.Fatal, "Object of type `", this.rTType().name(),
					"' playing too many roles, including `", roleName+ "'.");
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
	@Override public void enlistAsStagePropPlayerForContext(final String roleName, final RTContextObject contextInstance) {
		List<String> stagePropsIAmPlayingHere = null;
		if (stagePropsIAmPlayingInContext_.containsKey(contextInstance)) {
			;  // rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
		} else {
			stagePropsIAmPlayingHere = new ArrayList<String>();
			stagePropsIAmPlayingInContext_.put(contextInstance, stagePropsIAmPlayingHere);
		}
		int count = 0;
		for (final Map.Entry<RTContextObject, List<String>> iter : stagePropsIAmPlayingInContext_.entrySet()) {
			count += iter.getValue().size();
		}
		if ((1 < count) && (1 < stagePropsIAmPlayingInContext_.size())) {
			ErrorLogger.error(ErrorIncidenceType.Fatal, "Object of type ", this.rTType().name(),
					" playing too many roles, including ", roleName);
		}
	}
	@Override public void unenlistAsStagePropPlayerForContext(final String roleName, final RTContextObject contextInstance) {
		assert null != contextInstance;
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
			ErrorLogger.error(ErrorIncidenceType.Internal, null, "array index ", Integer.toString(index), " beyone array bounds 0:",
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
	private RTArrayObject(final RTObject [] theArray, final Type baseType, final RTArrayType rTArrayType, final int size) {
		super();
		theArray_ = theArray.clone();
		baseType_ = baseType;
		rTArrayType_ = new RTArrayType(baseType_, rTArrayType.arrayType());
		size_ = size;
		for (int j = 0; j < size_; j++) {
			theArray_[j] = theArray_[j].dup();
		}
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
		stagePropsIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	public RTIterator makeIterator() {
		final RTIterator retval = new RTArrayIterator(this);
		return retval;
	}
	@Override public RTObject dup() {
		final RTArrayObject retval = new RTArrayObject(theArray_, baseType_, rTArrayType_, size_);
		return retval;
	}
	@Override public void incrementReferenceCount() {
		referenceCount_++;
	}
	@Override public void decrementReferenceCount() {
		--referenceCount_;
	}
	@Override public long referenceCount() {
		return referenceCount_;
	}
	@Override public boolean equals(final RTObject other) {
		boolean retval = true;
		if (other instanceof RTArrayObject) {
			retval = true;
			for (int i = 0; i < size_; i++) {
				if (theArray_[i].equals(((RTArrayObject)other).theArray_[i]) == false) {
					retval = false;
					break;
				}
			}
		} else {
			retval = false;
		}
		return retval;
	}
	@Override public int compareTo(final Object other) {
		int retval = 0;
		if (this.equals(other)) {
			retval = 0;
		} else if (size() < ((RTArrayObject)other).size()) {
			retval = -1;
		} else {
			retval = 1;
		}
		
		return retval;
	}
	
	private final RTObject [] theArray_;
	private final Type baseType_;
	private final int size_;
	private int referenceCount_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_,
						stagePropsIAmPlayingInContext_;
	private final RTArrayType rTArrayType_;
}