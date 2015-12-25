package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.1
 *   Copyright (c)2015 James O. Coplien
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

public abstract class RTCode implements RTStackable {
	public RTCode() {
		super();
		referenceCount_ = 1;
	}
	public void setNextCode(RTCode next) {
		if (null != next) {
			next.incrementReferenceCount();
		}
		if (null != nextCode_) {
			nextCode_.decrementReferenceCount();
		}
		nextCode_ = next;
	}
	public RTCode nextCode() {
		return nextCode_;
	}
	public RTCode run() {
		return nextCode_;
	}
	public void incrementReferenceCount() {
		referenceCount_++;
	}
	public void decrementReferenceCount() {
		--referenceCount_;
	}
	public long referenceCount() {
		return referenceCount_;
	}

	protected long referenceCount_;
	protected RTCode nextCode_;
}
