package run_time;

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
import run_time.RTContext.RTContextInfo;
import error.ErrorLogger;
import error.ErrorLogger.ErrorType;
import expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;


// Doubles for classes and contexts
public class RTObjectCommon implements RTObject, RTContextInstance {
	public RTObjectCommon(RTType classs) {
		classOrContext_ = classs;
		objectMembers_ = new HashMap<String, RTObject>();
		rTTypeMap_ = new HashMap<String, RTType>();
		rolesIAmPlayingInContext_ = new HashMap<RTContextObject, List<String>>();
		referenceCount_ = 1;
	}
	protected RTObjectCommon(RTObject object) {
		// Used to define RTDynamicScope
		if (object instanceof RTObjectCommon == false) {
			assert object instanceof RTObjectCommon;
		}
		classOrContext_ = object.rTType();
		objectMembers_ = ((RTObjectCommon)object).objectMembers();
		rTTypeMap_ = ((RTObjectCommon)object).objectDeclarations();
		rolesIAmPlayingInContext_ = ((RTObjectCommon)object).rolesIAmPlayingInContext_;
		referenceCount_ = 1;
	}
	@Override public Map<String, RTObject> objectMembers() { return objectMembers_; }
	@Override public void setObject(String name, RTObject object) {
		if (null == object) {
			assert null != object;
		}
		if (objectMembers_.containsKey(name)) {
			final RTObject oldObject = objectMembers_.get(name);
			objectMembers_.put(name, object);
			object.incrementReferenceCount();
			if (null != oldObject) {
				oldObject.decrementReferenceCount();
			}
		} else {
			// Must be a static member
			classOrContext_.setObject(name, object);
		}
	}
	@Override public Map<String, RTType> objectDeclarations() { return rTTypeMap_; }
	@Override public void addObjectDeclaration(String name, RTType type) {
		rTTypeMap_.put(name, type);
		objectMembers_.put(name, new RTNullObject());
	}
	@Override public RTObject getObject(String name) {
		RTObject retval = null;
		if (objectMembers_.containsKey(name)) {
			retval = objectMembers_.get(name);
		} else if (null != classOrContext_) {
			// Could be a static member...
			retval = classOrContext_.getObject(name);
		} else {
			// ... or could be that it's just not there
			// Redundant, but for clarity and completeness
			retval = null;
		}
		return retval;
	}
	@Override public RTType rTType() {
		return classOrContext_;
	}
	@Override public boolean equals(RTObject another) {
		return false;
	}
	@Override public boolean gt(RTObject another) {
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
	
	@Override public void enlistAsRolePlayerForContext(String roleName, RTContextObject contextInstance) {
		List<String> rolesIAmPlayingHere = null;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
		} else {
			rolesIAmPlayingHere = new ArrayList<String>();
			rolesIAmPlayingInContext_.put(contextInstance, rolesIAmPlayingHere);
		}
		
		rolesIAmPlayingHere.add(roleName);
		
		int count = 0;
		for (Map.Entry<RTContextObject, List<String>> iter : rolesIAmPlayingInContext_.entrySet()) {
			count += iter.getValue().size();
		}
		if ((1 < count) && (1 < rolesIAmPlayingInContext_.size())) {
			ErrorLogger.error(ErrorType.Fatal, "Object of type ", this.rTType().name(),
					" playing too many roles, including ", roleName);
			for (Map.Entry<RTContextObject, List<String>> iter : rolesIAmPlayingInContext_.entrySet()) {
				final String contextName = iter.getKey().rTType().name();
				final List<String> roleNames = iter.getValue();
				String message = "\tIn Context " + contextName +":";
				for (String aRoleName : roleNames) {
					message = message + " " + aRoleName;
				}
				ErrorLogger.error(ErrorType.Fatal, message, ".", "", "");
			}
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

	public static class RTContextObject extends RTObjectCommon implements RTObject {
		public RTContextObject(RTType classs) {
			super(classs);
			nameToRoleMap_ = new HashMap<String, RTRole>();
			nameToRoleBindingMap_ = new HashMap<String, RTObject>();
			
			// context$info is used to track things like
			// who our roleplayers are. It kind of seemed like this
			// was the place to set it but the timing is wrong. The
			// activation record setup initializes it to RTNullObject.
			// So, instead, we bind it on first demanded use. See
			// setRoleBinding, below.
			// final RTContextInfo contextInfo = new RTContextInfo(this);
			// this.setObject("context$info", contextInfo);
		}
		public void addRoleDeclaration(String name, RTRole role) {
			nameToRoleMap_.put(name, role);
			final RTNullObject nullObject = new RTNullObject();
			nameToRoleBindingMap_.put(name, nullObject);
			final RTContextInfo contextInfo = this.contextInfo();
			contextInfo.addRolePlayer(name, nullObject);
		}
		public Map<String, RTRole> roleDeclarations() {
			return nameToRoleMap_;
		}
		public RTObject getRoleBinding(String name) {
			RTObject retval = null;
			if (nameToRoleBindingMap_.containsKey(name)) {
				retval = nameToRoleBindingMap_.get(name);
			} else {
				assert false;
			}
			return retval;
		}
		private RTContextInfo contextInfo() {
			// Note that this may not be called until cleanup
			// in the case that the Context had no roles (which
			// implies that they were never elicited, which
			// implies that contextInfo() was never previously
			// invoked.) That means that during cleanup we
			// deliver an empty RTContextInfo object. That's O.K.
			RTContextInfo contextInfo = null;
			final RTObject rawContextInfo = getObject("context$info");
			if (rawContextInfo instanceof RTNullObject) {
				contextInfo = new RTContextInfo(this);
				this.setObject("context$info", contextInfo);
			} else {
				contextInfo = (RTContextInfo) rawContextInfo;
			}
			return contextInfo;
		}
		public void setRoleBinding(String name, RTObject value) {
			value.incrementReferenceCount();
			RTObject oldValue = null;
			if (nameToRoleBindingMap_.containsKey(name)) {
				oldValue = nameToRoleBindingMap_.get(name);
			}
			
			nameToRoleBindingMap_.put(name, value);
			
			if (value instanceof RTStageProp == false) {
				final RTContextInfo contextInfo = this.contextInfo();
				
				assert null != contextInfo;
				assert contextInfo instanceof RTContextInfo;
				
				if (null != oldValue) {
					oldValue.decrementReferenceCount();
					contextInfo.removeRolePlayer(name, oldValue);
				}
				
				contextInfo.addRolePlayer(name, value);
			}
		}
		public RTObject dup() {
			assert false;
			return null;
		}
		@Override public void decrementReferenceCount() {
			super.decrementReferenceCount();
			final RTContextInfo contextInfo = contextInfo();
			if (0 >= referenceCount()) {
				// I'm outta here. Let all my RolePlayers know
				contextInfo.removeAllRolePlayers();
			} else if (1 == referenceCount()) {
				// It could be that we were the last expression to
				// be evaluated, and the reference count is artificially
				// high because of that cache holding us
				if (this == RTExpression.lastExpressionResult()) {
					contextInfo.removeAllRolePlayers();
				}
			}
		}
		
		// Debugging only
		public void incrementReferenceCount() {
			super.incrementReferenceCount();
		}
	
		private final Map<String, RTRole> nameToRoleMap_;
		private final Map<String, RTObject> nameToRoleBindingMap_;
	}
	
	public static class RTIntegerObject extends RTObjectCommon implements RTObject {
		public RTIntegerObject(long foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed("int"));
			foobar_ = foobar;
		}
		protected RTIntegerObject(long foobar, String typeName) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(typeName));
			foobar_ = foobar;
		}
		public long intValue() { return foobar_; }
		@Override public boolean equals(RTObject another) {
			if ((another instanceof RTIntegerObject) == false) return false;
			else return foobar_ == ((RTIntegerObject)another).intValue();
		}
		@Override public boolean gt(RTObject another) {
			if ((another instanceof RTIntegerObject) == false) return false;
			else return foobar_ > ((RTIntegerObject)another).intValue();
		}
		@Override public RTObject plus(RTObject other) {
			long result = foobar_ + ((RTIntegerObject)other).intValue();
			return new RTIntegerObject(result);
		}
		@Override public RTObject minus(RTObject other) {
			long result = foobar_ - ((RTIntegerObject)other).intValue();
			return new RTIntegerObject(result);
		}
		@Override public RTObject times(RTObject other) {
			long result = foobar_ * ((RTIntegerObject)other).intValue();
			return new RTIntegerObject(result);
		}
		@Override public RTObject divideBy(RTObject other) {
			long result = foobar_ / ((RTIntegerObject)other).intValue();
			return new RTIntegerObject(result);
		}
		@Override public RTObject modulus(RTObject other) {
			long result = foobar_ % ((RTIntegerObject)other).intValue();
			return new RTIntegerObject(result);
		}
		@Override public RTObject unaryPlus() {
			return new RTIntegerObject(foobar_);
		}
		@Override public RTObject unaryMinus() {
			return new RTIntegerObject(-foobar_);
		}
		@Override public RTObject preIncrement() {
			return new RTIntegerObject(++foobar_);
		}
		@Override public RTObject postIncrement() {
			return new RTIntegerObject(foobar_++);
		}
		@Override public RTObject preDecrement() {
			return new RTIntegerObject(--foobar_);
		}
		@Override public RTObject postDecrement() {
			return new RTIntegerObject(foobar_--);
		}
		@Override public RTIntegerObject dup() {
			return new RTIntegerObject(foobar_);
		}
		@Override public RTObject toThePowerOf(RTObject other) {
			RTObject retval;
			if (other instanceof RTIntegerObject) {
				final long exponent = ((RTIntegerObject)other).intValue();
				final double result = Math.pow(foobar_, exponent);
				retval = new RTIntegerObject((int)result);
			} else if (other instanceof RTDoubleObject) {
				final double exponent = ((RTDoubleObject)other).doubleValue();
				final double result = Math.pow(foobar_, exponent);
				retval = new RTDoubleObject(result);
			} else {
				assert false;
				retval = null;
			}
			return retval;
		}
		
		private long foobar_;
	}
	public static class RTBigIntegerObject extends RTIntegerObject implements RTObject {
		public RTBigIntegerObject(int foobar) {
			super(foobar, "Integer");
		}
	}
	public static class RTDoubleObject extends RTObjectCommon implements RTObject {
		RTDoubleObject(double foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed("double"));
			foobar_ = foobar;
		}
		public double doubleValue() { return foobar_; }
		@Override public boolean equals(RTObject another) {
			if ((another instanceof RTIntegerObject) == false) return false;
			else return foobar_ == ((RTIntegerObject)another).intValue();
		}
		@Override public boolean gt(RTObject another) {
			if ((another instanceof RTDoubleObject) == false) return false;
			else return foobar_ > ((RTDoubleObject)another).doubleValue();
		}
		@Override public RTObject plus(RTObject other) {
			final double result = foobar_ + ((RTDoubleObject)other).doubleValue();
			return new RTDoubleObject(result);
		}
		@Override public RTObject minus(RTObject other) {
			final double result = foobar_ - ((RTDoubleObject)other).doubleValue();
			return new RTDoubleObject(result);
		}
		@Override public RTObject times(RTObject other) {
			final double result = foobar_ * ((RTDoubleObject)other).doubleValue();
			return new RTDoubleObject(result);
		}
		@Override public RTObject divideBy(RTObject other) {
			final double result = foobar_ / ((RTDoubleObject)other).doubleValue();
			return new RTDoubleObject(result);
		}
		@Override public RTObject modulus(RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObject unaryPlus() {
			return new RTDoubleObject(foobar_);
		}
		@Override public RTObject unaryMinus() {
			return new RTDoubleObject(-foobar_);
		}
		@Override public RTObject preIncrement() {
			return new RTDoubleObject(++foobar_);
		}
		@Override public RTObject postIncrement() {
			return new RTDoubleObject(foobar_++);
		}
		@Override public RTObject preDecrement() {
			return new RTDoubleObject(--foobar_);
		}
		@Override public RTObject postDecrement() {
			return new RTDoubleObject(foobar_--);
		}
		@Override public RTDoubleObject dup() {
			return new RTDoubleObject(foobar_);
		}
		@Override public RTObject toThePowerOf(RTObject other) {
			double result = 0.0;
			if (other instanceof RTIntegerObject) {
				final long exponent = ((RTIntegerObject)other).intValue();
				result = Math.pow(foobar_, exponent);
			} else if (other instanceof RTDoubleObject) {
				final double exponent = ((RTDoubleObject)other).doubleValue();
				result = Math.pow(foobar_, exponent);
			} else {
				assert false;
			}
			return new RTDoubleObject(result);
		}
		
		private double foobar_;
	}
	public static class RTStringObject extends RTObjectCommon implements RTObject {
		RTStringObject(String foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelClassNamed("String"));
			foobar_ = foobar;
		}
		public String stringValue() { return foobar_; }
		@Override public boolean equals(RTObject another) {
			if ((another instanceof RTStringObject) == false) return false;
			else return foobar_.equals(((RTStringObject)another).stringValue());
		}
		@Override public boolean gt(RTObject another) {
			if ((another instanceof RTStringObject) == false) return false;
			else return foobar_.compareTo (((RTStringObject)another).stringValue()) > 0;
		}
		@Override public RTObject plus(RTObject other) {
			String result = foobar_ + ((RTStringObject)other).stringValue();
			return new RTStringObject(result);
		}
		@Override public RTObjectCommon minus(RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObjectCommon times(RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObjectCommon divideBy(RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObjectCommon modulus(RTObject other) {
			assert other instanceof RTStringObject;
			final CharSequence nothing = new String(""), cut = ((RTStringObject)other).stringValue();
			final String rawRetval = foobar_.replace(cut, nothing);
			return new RTStringObject(rawRetval);
		}
		@Override public RTStringObject dup() {
			return new RTStringObject(foobar_);
		}

		private String foobar_;
	}
	public static class RTBooleanObject extends RTObjectCommon implements RTObject {
		RTBooleanObject(boolean foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed("boolean"));
			foobar_ = foobar;
		}
		public boolean value() { return foobar_; }
		@Override public boolean equals(RTObject another) {
			if ((another instanceof RTBooleanObject) == false) return false;
			else return foobar_ == (((RTBooleanObject)another).value());
		}
		@Override public boolean gt(RTObject another) {
			return false;
		}
		@Override public RTBooleanObject dup() {
			return new RTBooleanObject(foobar_);
		}
		
		private boolean foobar_;
	}
	public static class RTNullObject extends RTObjectCommon implements RTObject {
		public RTNullObject() {
			super((RTClass)null);
		}
		@Override public RTObject dup() {
			return this;
		}
		@Override public boolean equals(RTObject another) {
			return another instanceof RTNullObject;
		}
	}
	
	public RTObject performUnaryOpOnObjectNamed(String idName, String operator, PreOrPost preOrPost) {
		RTObject retval = null;
		if (objectMembers_.containsKey(idName)) {
			retval = objectMembers_.get(idName);
		}
		switch (preOrPost) {
		case Pre:
			if (operator.equals("++")) {
				retval = retval.preIncrement();
			} else if (operator.equals("--")) {
				retval = retval.preDecrement();
			} else {
				assert false;
			}
			break;
		case Post:
			if (operator.equals("++")) {
				retval = retval.postIncrement();
			} else if (operator.equals("--")) {
				retval = retval.postDecrement();
			} else {
				assert false;
			}
			break;
		}
		
		assert null != retval;
		
		return retval;
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
	private RTObjectCommon(RTType classOrContext, Map<String,RTObject> objectMembers, Map<String, RTType> rTTypeMap) {
		super();
		classOrContext_ = classOrContext;
		
		// WARNING - These are only shallow copies
		objectMembers_ = new HashMap<String, RTObject>();
		rTTypeMap_ = new HashMap<String, RTType>(rTTypeMap);
		
		// Values should be unique
		for (Map.Entry<String, RTObject> iter : objectMembers.entrySet()) {
			final String key = iter.getKey();
			final RTObject value = iter.getValue();
			final RTObject clonedValue = value.dup();
			objectMembers_.put(key, clonedValue);
		}
	}
	public RTObject dup() {
		RTObject retval = new RTObjectCommon(classOrContext_, objectMembers_, rTTypeMap_);
		return retval;
	}
	
	private final RTType classOrContext_;
	protected final Map<String, RTObject> objectMembers_;
	private final Map<String, RTType> rTTypeMap_;
	private Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	
	private long referenceCount_;
}
