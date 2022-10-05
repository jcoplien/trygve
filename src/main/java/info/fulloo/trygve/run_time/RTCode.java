package info.fulloo.trygve.run_time;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2022 James O. Coplien, jcoplien@gmail.com
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
		isBreakpoint_ = false;
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
	
	public void setBreakpoint(boolean tf) {
		isBreakpoint_ = tf;
	}
	
	public final boolean isBreakpoint() {
		return isBreakpoint_;
	}
	
	public int lineNumber() {
		return 0;
	}
	
	public Token token() {
		return null;
	}
	
	public List<RTCode> connectedExpressions() {
		return new ArrayList<RTCode>();
	}

	protected long referenceCount_;
	protected RTCode nextCode_;
	protected boolean isBreakpoint_;
}
