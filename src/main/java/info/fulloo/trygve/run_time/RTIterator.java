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

import java.util.Iterator;

import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;

public abstract class RTIterator extends RTCommonRunTimeCrap {
	public RTIterator() {
		super();
		referenceCount_ = 0;
	}
	public abstract boolean isThereANext();
	public abstract void advance();
	public abstract RTObject next();
	
	public static RTIterator makeIterator(final RTIterable iterable) {
		return iterable.makeIterator();
	}
	
	@Override public String getText() {
		return "<iterator>";
	}
	
	
	public RTType rTType() {
		return null;	// a lie, but looks reasonable in debugging
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
			// NO ADVANCE! We should consider getting rid of
			// advance because it can't be separated from
			// next()
			
			// theIterator_.next();
		}

		private final RTSetObject whatIAmIteratingOver_;
		private final Iterator<RTObject> theIterator_;
	}
	
	public static class RTMapIterator extends RTIterator {
		public RTMapIterator(final RTIterable whatIAmIteratingOver) {
			super();
			assert whatIAmIteratingOver instanceof RTMapObject;
			whatIAmIteratingOver_ = (RTMapObject)whatIAmIteratingOver;
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
			// Like list — see above
			// theIterator_.next();
		}

		private final RTMapObject whatIAmIteratingOver_;
		private final Iterator<RTObject> theIterator_;
	}
}
