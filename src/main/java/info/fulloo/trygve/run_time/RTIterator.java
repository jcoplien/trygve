package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.3
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

import java.util.Iterator;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;

public abstract class RTIterator extends RTCommonRunTimeCrap {
	public RTIterator() {
		super();
		referenceCount_ = 1;
	}
	public abstract boolean isThereANext();
	public abstract void advance();
	public abstract RTObject next();
	
	public static RTIterator makeIterator(RTIterable iterable) {
		return iterable.makeIterator();
	}
	
	@Override public String getText() {
		return "<iterator>";
	}
	
	public static class RTArrayIterator extends RTIterator {
		public RTArrayIterator(final RTIterable whatIAmIteratingOver) {
			super();
			assert whatIAmIteratingOver instanceof RTArrayObject;
			whatIAmIteratingOver_ = whatIAmIteratingOver;
			arraySize_ = whatIAmIteratingOver_.size();
			currentIndex_ = 0;
		}
		public boolean isThereANext() {
			return currentIndex_ < arraySize_;
		}
		public int compareTo(final Object rawOther) {
			int retval = 0;
			final RTArrayIterator other = ((RTArrayIterator)rawOther);
			if (currentIndex_ < other.currentIndex_) retval = -1;
			else if (currentIndex_ > other.currentIndex_) retval = 1;
			return retval;
		}
		public RTObject next() {
			final RTIntegerObject theIndexObject = new RTIntegerObject(currentIndex_);
			assert currentIndex_ < arraySize_;
			return whatIAmIteratingOver_.getObject(theIndexObject);
		}
		public void advance() {
			currentIndex_++;
		}
		@Override public boolean equals(final RTObject other) {
			assert false;
			return false;
		}
		
		private final RTIterable whatIAmIteratingOver_;
		private int currentIndex_;
		private final int arraySize_;
	}
	
	public static class RTListIterator extends RTIterator {
		public RTListIterator(final RTIterable whatIAmIteratingOver) {
			super();
			assert whatIAmIteratingOver instanceof RTListObject;
			whatIAmIteratingOver_ = (RTListObject)whatIAmIteratingOver;
			listSize_ = whatIAmIteratingOver_.size();
			currentIndex_ = 0;
		}
		@Override public boolean isThereANext() {
			return currentIndex_ < listSize_;
		}
		@Override public int compareTo(final Object rawOther) {
			int retval = 0;
			final RTArrayIterator other = ((RTArrayIterator)rawOther);
			if (currentIndex_ < other.currentIndex_) retval = -1;
			else if (currentIndex_ > other.currentIndex_) retval = 1;
			return retval;
		}
		@Override public RTObject next() {
			assert currentIndex_ < listSize_;
			return whatIAmIteratingOver_.get(currentIndex_);
		}
		@Override public void advance() {
			currentIndex_++;
		}
		@Override public boolean equals(final RTObject other) {
			assert false;
			return false;
		}
		
		private final RTListObject whatIAmIteratingOver_;
		private int currentIndex_;
		private final int listSize_;
	}
	
	public static class RTSetIterator extends RTIterator {
		public RTSetIterator(final RTIterable whatIAmIteratingOver) {
			super();
			assert whatIAmIteratingOver instanceof RTSetObject;
			whatIAmIteratingOver_ = (RTSetObject)whatIAmIteratingOver;
			theIterator_ = whatIAmIteratingOver_.RTIterator();
		}
		@Override public boolean isThereANext() {
			return theIterator_.hasNext();
		}
		@Override public int compareTo(final Object rawOther) {
			assert false;
			return 0;
		}
		@Override public boolean equals(final RTObject other) {
			assert false;
			return false;
		}
		@Override public RTObject next() {
			return theIterator_.next();
		}
		@Override public void advance() {
			theIterator_.next();
		}

		private final RTSetObject whatIAmIteratingOver_;
		private final Iterator<RTObject> theIterator_;
	}

	/*
	@Override public RTObject getObject(String name) { assert false; return null; }
	@Override public void addObjectDeclaration(String objectName, RTType type) { assert false; }
	@Override public Map<String, RTType> objectDeclarations() { assert false; return null; }
	@Override public void setObject(String objectName, RTObject object) { assert false; }
	@Override public RTType rTType() { assert false; return null; }
	@Override public boolean isEqualTo(Object another) { assert false; return false; }
	@Override public boolean gt(RTObject another) { assert false; return false; }
	@Override public RTObject plus(RTObject other) { assert false; return null; }
	@Override public RTObject minus(RTObject other) { assert false; return null; }
	@Override public RTObject logicalAnd(final RTObject other) { assert false; return null; }
	@Override public RTObject logicalOr(final RTObject other) { assert false; return null; }
	@Override public RTObject logicalXor(final RTObject other) { assert false; return null; }
	@Override public RTObject times(RTObject other) { assert false; return null; }
	@Override public RTObject divideBy(RTObject other) { assert false; return null; }
	@Override public RTObject modulus(RTObject other) { assert false; return null; }
	@Override public RTObject unaryPlus() { assert false; return null; }
	@Override public RTObject unaryMinus() { assert false; return null; }
	@Override public RTObject unaryLogicalNegation() { assert false; return null; }
	@Override public RTObject preIncrement() { assert false; return null; }
	@Override public RTObject postIncrement() { assert false; return null; }
	@Override public RTObject preDecrement() { assert false; return null; }
	@Override public RTObject postDecrement() { assert false; return null; }
	@Override public RTObject performUnaryOpOnObjectNamed(String idName, String operator,
			PreOrPost preOrPost_) { assert false; return null; }
	@Override public RTObject toThePowerOf(RTObject exponent) { assert false; return null; }
	@Override public RTObject dup() { assert false; return null; }
	@Override public void incrementReferenceCount() { referenceCount_++; }
	@Override public void decrementReferenceCount() { --referenceCount_; }
	@Override public long referenceCount() { return referenceCount_; }
	@Override public void enlistAsRolePlayerForContext(final String roleName,
			final RTContextObject contextInstance) { assert false; }
	@Override public void unenlistAsRolePlayerForContext(final String roleName,
			final RTContextObject contextInstance) { assert false; }
	@Override public void enlistAsStagePropPlayerForContext(final String stagePropName,
			final RTContextObject contextInstance) { assert false; }
	@Override public void unenlistAsStagePropPlayerForContext(final String stagePropName,
			final RTContextObject contextInstance) { assert false; }

	private int referenceCount_;
	*/
}
