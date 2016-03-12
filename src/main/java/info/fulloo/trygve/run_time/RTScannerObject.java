package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.6
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTClass.RTSystemClass.RTInputStreamInfo;

public class RTScannerObject extends RTObjectCommon implements RTObject {
	public RTScannerObject(final RTType scannerType) {
		super(scannerType);	// 
		scannerType_ = scannerType;	// e.g. an instance of RTClass
		baseType_ = null;
		theScanner_ = null;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public Type baseType() {
		return baseType_;
	}
	@Override public int hashCode() {
		return theScanner_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		return this == other;
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
			ErrorLogger.error(ErrorType.Fatal, "Object of type ", this.rTType().name(),
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
	private RTScannerObject(final Scanner theScanner, final Type baseType, final RTType scannerType) {
		super(scannerType);
		theScanner_ = theScanner;
		baseType_ = baseType;
		scannerType_ = scannerType;
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	public RTIterator makeIterator() {
		// maybe some day
		assert false;
		return null;
	}
	@Override public RTObject dup() {
		final RTScannerObject retval = new RTScannerObject(theScanner_, baseType_, scannerType_);
		return retval;
	}
	@Override public RTType rTType() {
		return scannerType_;
	}
	public void ctor1(final RTObject streamArg) {
		assert true;
		final RTInputStreamInfo inputStreamInfo = (RTInputStreamInfo)streamArg.getObject("inputStreamInfo");
		assert null != inputStreamInfo;
		final InputStream inputStream = inputStreamInfo.inputStream();
		theScanner_ = new Scanner(inputStream);
	}

	public RTObject nextLine() {
		final String nextLine = theScanner_.nextLine();
		final RTStringObject retval = new RTStringObject(nextLine);
		return retval;
	}
	public int compareTo(final RTObject other) {
		assert false;
		return 0;
	}
	public Scanner theScanner() {
		return theScanner_;
	}
	
	private       Scanner theScanner_;
	private final Type baseType_;
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType scannerType_;
}