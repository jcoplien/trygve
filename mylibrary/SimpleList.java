package mylibrary;

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

public class SimpleList {
	public SimpleList() {
		allocatedSize_ = 5;
		rep_ = new Object[allocatedSize_];
		numElements_ = 0;
	}
	private void grow() {
		final Object[] oldRep = rep_;
		allocatedSize_ *= 2;
		rep_ = new Object[allocatedSize_];
		for (int i = 0; i < numElements_; i++) {
			rep_[i] = oldRep[i];
		}
	}
	public void add(Object o) {
		if (numElements_ >= allocatedSize_) {
			grow();
		}
		rep_[numElements_] = o;
		numElements_++;
	}
	public Object objectAtIndex(int i) {
		assert i < numElements_;
		return rep_[i];
	}
	public Object last() {
		return this.objectAtIndex(numElements_ - 1);
	}
	public void insertAtStart(Object o) {
		if (0 < numElements_) {
			if (numElements_ >= allocatedSize_) {
				grow();
			}
			for (int i = numElements_-1; i >= 0; --i) {
				rep_[i+1] = rep_[i];
			}
		}
		rep_[0] = o;
		numElements_++;
	}
	public void remove(Object o) {
		boolean removed = false;
		for (int i = 0; i < numElements_; i++) {
			if (removed) {
				rep_[i] = rep_[i+1];
			} else {
				if (o == rep_[i]) {
					rep_[i] = rep_[i+1];
					removed = true;
				}
			}
		}
		--numElements_;
	}
	public int count() {
		return numElements_;
	}
	
	private Object[] rep_;
	private int numElements_;
	private int allocatedSize_;
}
