package info.fulloo.trygve.run_time;

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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTContext.RTContextInfo;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTExpression.RTRoleArrayIndexExpression;
import info.fulloo.trygve.run_time.RTExpression.RTRoleIdentifier;
import info.fulloo.trygve.semantic_analysis.StaticScope;

// For future reference:
// Integer.toHexString(System.identityHashCode(object))
// http://www.nomachetejuggling.com/2008/06/04/getting-a-java-objects-reference-id/

// Doubles for classes and contexts
public class RTObjectCommon extends RTCommonRunTimeCrap implements RTContextInstance {
	public RTObjectCommon(final RTType classs) {
		super();
		classOrContext_ = classs;
		objectMembers_ = new LinkedHashMap<String, RTObject>();
		rTTypeMap_ = new LinkedHashMap<String, RTType>();
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
		stagePropsIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	protected RTObjectCommon(final RTObject object) {
		super();
		// Used to define RTDynamicScope
		if (object instanceof RTObjectCommon == false) {
			assert object instanceof RTObjectCommon;
		}
		classOrContext_ = object.rTType();
		objectMembers_ = ((RTObjectCommon)object).objectMembers_;
		rTTypeMap_ = ((RTObjectCommon)object).objectDeclarations();
		rolesIAmPlayingInContext_ = ((RTObjectCommon)object).rolesIAmPlayingInContext_;
		stagePropsIAmPlayingInContext_ = ((RTObjectCommon)object).stagePropsIAmPlayingInContext_;
	}
	@Override public boolean isEqualTo(final Object other) {
		return this == other;
	}
	@Override public boolean equals(final Object other) {
		boolean retval = this == other;	// nice default - use identity as equality
		final ActualArgumentList pl = new ActualArgumentList();
		
		final RTType myType = this.rTType();
		assert myType instanceof RTClassAndContextCommon;
		final RTClassAndContextCommon myClass = (RTClassAndContextCommon)myType;
		final Type myTypeAsType = myClass.typeDeclaration().type();
		IdentifierExpression self = new IdentifierExpression("this", myTypeAsType, null, null);
		pl.addFirstActualParameter(self);
		
		assert other instanceof RTObject;
		RTObject otherObject = (RTObject) other;
		final RTType otherType = otherObject.rTType();
		assert otherType instanceof RTClassAndContextCommon;
		final RTClassAndContextCommon otherClass = (RTClassAndContextCommon)otherType;
		final Type otherTypeAsType = otherClass.typeDeclaration().type();
		IdentifierExpression otherVar = new IdentifierExpression("other", otherTypeAsType, null, null);
		pl.addActualArgument(otherVar);
		
		List<RTType> actualParameterStaticTypes = new ArrayList<RTType>();
		final RTMethod compareTo = myType.lookupMethodIgnoringParameterInSignatureWithConversionNamed("compareTo",
				actualParameterStaticTypes, pl, null);
		if (null != compareTo) {
			// The user has provided a compareTo function. Call it.
			final int startingStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
			RTDynamicScope currentDynamicScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final StaticScope staticScope = null;
			RTDynamicScope activationRecord = new RTDynamicScope(staticScope, "compareTo", currentDynamicScope, true);
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
			activationRecord.incrementReferenceCount();
			activationRecord.addObjectDeclaration("this", myType);
			
			// What did the programmer name the argument? Canonically it's "other"
			// but the programmer can choose anything...
			final FormalParameterList userCompareToParameters = compareTo.formalParameters();
			final String parameterName = userCompareToParameters.nameOfParameterAtPosition(1);
			activationRecord.addObjectDeclaration(parameterName, otherType);
			
			activationRecord.setObject("this", this);
			activationRecord.setObject(parameterName, otherObject);
			
			// return address
			RunTimeEnvironment.runTimeEnvironment_.pushStack(null);
			RunTimeEnvironment.runTimeEnvironment_.setFramePointer();

			RTCode pc = compareTo;
			do {
				pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			} while (null != pc && pc instanceof RTHalt == false);
			
			final RTObject result = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert result instanceof RTIntegerObject;
			retval = ((RTIntegerObject)result).intValue() == 0;
			
			final int currentStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
			if (startingStackSize != currentStackSize) {
				assert startingStackSize == currentStackSize;
			}
		}

		return retval;
	}
	@Override public String toString() {
		String retval = null;
		final ActualArgumentList pl = new ActualArgumentList();
		
		final RTType myType = this.rTType();
		assert myType instanceof RTClassAndContextCommon;
		final RTClassAndContextCommon myClass = (RTClassAndContextCommon)myType;
		final Type myTypeAsType = myClass.typeDeclaration().type();
		IdentifierExpression self = new IdentifierExpression("this", myTypeAsType, null, null);
		pl.addFirstActualParameter(self);
		
		final RTMethod toString = myType.lookupMethod("toString", pl);
		if (null != toString) {
			// The user has provided a toString function. Call it.
			final int startingStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
			final StaticScope staticScope = null;
			RTDynamicScope currentDynamicScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			RTDynamicScope activationRecord = new RTDynamicScope(staticScope,"toString", currentDynamicScope, true);
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
			activationRecord.incrementReferenceCount();
			activationRecord.addObjectDeclaration("this", myType);
						
			activationRecord.setObject("this", this);
			
			// return address
			RunTimeEnvironment.runTimeEnvironment_.pushStack(null);
			RunTimeEnvironment.runTimeEnvironment_.setFramePointer();

			RTCode pc = toString;
			do {
				pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			} while (null != pc && pc instanceof RTHalt == false);
			
			final RTObject result = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			if (result instanceof RTStringObject == false) {
				ErrorLogger.error(ErrorIncidenceType.Internal, "user-supplied toString() operation led to unexpected type in `", this.getText(), "'.", "");
				assert result instanceof RTStringObject;
			}
			retval = ((RTStringObject)result).toString();
			
			final int currentStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
			if (startingStackSize != currentStackSize) {
				ErrorLogger.error(ErrorIncidenceType.Internal, "Stack corruption related to toString() operation and `", this.getText(), "'.", "");
				assert startingStackSize == currentStackSize;
			}
		} else {
			ErrorLogger.error(ErrorIncidenceType.Runtime, "No toString() operation on `", this.getText(), "'.", "");
		}

		return retval;
	}
	@Override public void setObject(final String name, final RTObject object) {
		if (null == object) {
			ErrorLogger.error(ErrorIncidenceType.Internal, null,
					"Internal error: attempt to set ",
					name,
					" to a Java NULL.", "");
		} else if (objectMembers_.containsKey(name)) {
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
	// dead code.  FIXME.
	// private RTType classOrContext() {
	// 	return classOrContext_;
	// }
	@Override public Map<String, RTType> objectDeclarations() { return rTTypeMap_; }
	          public Map<String,RTObject> objectMembers() { return objectMembers_; }
	@Override public void addObjectDeclaration(final String name, final RTType type) {
		rTTypeMap_.put(name, type);
		final RTObject oldValue = objectMembers_.get(name);
		if (null != oldValue) {
			oldValue.decrementReferenceCount();
		}
		objectMembers_.put(name, new RTNullObject());
	}
	@Override public RTObject getObject(final String name) {
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
	
	@Override public void enlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {
		assert null != contextInstance;
		List<String> rolesIAmPlayingHere = null;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
		} else {
			rolesIAmPlayingHere = new ArrayList<String>();
			rolesIAmPlayingInContext_.put(contextInstance, rolesIAmPlayingHere);
		}
		
		rolesIAmPlayingHere.add(roleName);
		
		int count = 0;
		for (final Map.Entry<RTContextObject, List<String>> iter : rolesIAmPlayingInContext_.entrySet()) {
			count += iter.getValue().size();
		}
		if ((1 < count) && (1 < rolesIAmPlayingInContext_.size())) {
			ErrorLogger.error(ErrorIncidenceType.Fatal, "Object of type ", this.rTType().name(),
					" playing too many roles, including ", roleName);
			for (Map.Entry<RTContextObject, List<String>> iter : rolesIAmPlayingInContext_.entrySet()) {
				final StringBuffer stringBuffer = new StringBuffer();
				final String contextName = iter.getKey().rTType().name();
				final List<String> roleNames = iter.getValue();
				stringBuffer.append("\tIn Context `");
				stringBuffer.append(contextName);
				stringBuffer.append("':");
				boolean first = true;
				for (final String aRoleName : roleNames) {
					if (first == false) stringBuffer.append(","); else first = false;
					stringBuffer.append(" ");
					stringBuffer.append(aRoleName);
				}
				final String message = stringBuffer.toString();
				ErrorLogger.error(ErrorIncidenceType.Fatal, message, ".", "", "");
			}
			ErrorLogger.error(ErrorIncidenceType.Fatal,
					"Objects may play Role(s) only in one Context at a time (note: this does not apply to Stage Props).",
					System.getProperty("line.separator") ,
					"Further execution may exhibit undefined behaviour.", "");
		}
	}
	
	@Override public void enlistAsStagePropPlayerForContext(final String stagePropName, final RTContextObject contextInstance) {
		assert null != contextInstance;
		List<String> stagePropsIAmPlayingHere = null;
		if (stagePropsIAmPlayingInContext_.containsKey(contextInstance)) {
			stagePropsIAmPlayingHere = stagePropsIAmPlayingInContext_.get(contextInstance);
		} else {
			stagePropsIAmPlayingHere = new ArrayList<String>();
			stagePropsIAmPlayingInContext_.put(contextInstance, stagePropsIAmPlayingHere);
		}
		
		stagePropsIAmPlayingHere.add(stagePropName);
	}
	
	@Override public void unenlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {
		assert null != contextInstance;
		if (rolesIAmPlayingInContext_.containsKey(contextInstance)) {
			final List<String> rolesIAmPlayingHere = rolesIAmPlayingInContext_.get(contextInstance);
			rolesIAmPlayingHere.remove(roleName);
			if (0 == rolesIAmPlayingHere.size()) {
				rolesIAmPlayingInContext_.remove(contextInstance);
			}
		} else {
			if (contextInstance.isRoleOrStagePropArray(roleName)) {
				;		// O.K. Arrays aren't registered with the
						// Context — but their individual elements
						// as Role-players still refer back to the
						// Context object. Ignore checking for this
						// binding. Tant pis.
			} else {
				assert false;
			}
		}
	}
	
	@Override public void unenlistAsStagePropPlayerForContext(final String stagePropName, final RTContextObject contextInstance) {
		assert null != contextInstance;
		if (stagePropsIAmPlayingInContext_.containsKey(contextInstance)) {
			final List<String> stagePropsIAmPlayingHere = stagePropsIAmPlayingInContext_.get(contextInstance);
			stagePropsIAmPlayingHere.remove(stagePropName);
			if (0 == stagePropsIAmPlayingHere.size()) {
				stagePropsIAmPlayingInContext_.remove(contextInstance);
			}
		} else {
			assert false;
		}
	}

	public static class RTContextObject extends RTObjectCommon {
		public RTContextObject(final RTType classs) {
			super(classs);
			nameToRoleMap_ = new LinkedHashMap<String, RTRole>();
			nameToStagePropMap_ = new LinkedHashMap<String, RTStageProp>();
			nameToRoleBindingMap_ = new LinkedHashMap<String, RTObject>();
			nameToStagePropBindingMap_ = new LinkedHashMap<String, RTObject>();
			isRoleArrayMap_ = new LinkedHashMap<String, String>();
			isStagePropArrayMap_ = new LinkedHashMap<String, String>();
			
			uniqueID_ = ++idGenerator_;
			
			// context$info is used to track things like
			// who our role-players are. It kind of seemed like this
			// was the place to set it but the timing is wrong. The
			// activation record setup initializes it to RTNullObject.
			// So, instead, we bind it on first demanded use. See
			// setRoleBinding, below.
			// final RTContextInfo contextInfo = new RTContextInfo(this);
			// this.setObject("context$info", contextInfo);
		}
		public void addRoleDeclaration(final String name, final RTRole role) {
			final RTContextInfo contextInfo = this.contextInfo();
			final RTNullObject nullObject = new RTNullObject();
			if (role.isArray() == false) {
				nameToRoleMap_.put(name, role);
				if (null != nameToRoleBindingMap_.get(name)) {
					assert false;
				}
				nameToRoleBindingMap_.put(name, nullObject);
				contextInfo.addRolePlayer(name, nullObject);
			}
		}
		public void addStagePropDeclaration(final String name, final RTStageProp stageProp) {
			final RTContextInfo contextInfo = this.contextInfo();
			final RTNullObject nullObject = new RTNullObject();
			if (stageProp.isArray() == false) {
				nameToStagePropMap_.put(name, stageProp);
				if (null != nameToStagePropBindingMap_.get(name)) {
					assert false;
				}
				nameToStagePropBindingMap_.put(name, nullObject);
				contextInfo.addStagePropPlayer(name, nullObject);
			}
		}
		public Map<String, RTRole> roleDeclarations() {
			return nameToRoleMap_;
		}
		public RTObject getRoleOrStagePropBinding(final String name) {
			RTObject retval = null;
			if (nameToRoleBindingMap_.containsKey(name)) {
				retval = nameToRoleBindingMap_.get(name);
			} else if (nameToStagePropBindingMap_.containsKey(name)) {
				retval = nameToStagePropBindingMap_.get(name);
			} else {
				retval = null;
			}
			return retval;
		}
		private RTContextInfo contextInfo() {
			// Note that this may not be called until cleanup
			// in the case that the Context had no Roles (which
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
		public void setRoleBinding(final String name, final RTObject value) {
			value.incrementReferenceCount();
			RTObject oldValue = null;
			if (nameToRoleBindingMap_.containsKey(name)) {
				oldValue = nameToRoleBindingMap_.get(name);
			}
			
			nameToRoleBindingMap_.put(name, value);
			
			if (value instanceof RTStageProp == false) {
				final RTContextInfo contextInfo = this.contextInfo();
				
				assert null != contextInfo;
				
				if (null != oldValue) {
					oldValue.decrementReferenceCount();
					contextInfo.removeRolePlayer(name, oldValue);
				}
				
				contextInfo.addRolePlayer(name, value);
			}
		}
		public void setStagePropBinding(final String name, final RTObject value) {
			value.incrementReferenceCount();
			RTObject oldValue = null;
			if (nameToStagePropBindingMap_.containsKey(name)) {
				oldValue = nameToStagePropBindingMap_.get(name);
			}
			
			nameToStagePropBindingMap_.put(name, value);
			
			final RTContextInfo contextInfo = this.contextInfo();
			assert null != contextInfo;
				
			if (null != oldValue) {
				oldValue.decrementReferenceCount();
				contextInfo.removeStagePropPlayer(name, oldValue);
			}
				
			contextInfo.addStagePropPlayer(name, value);
		}
		public void designateRoleAsArray(final String roleArrayName) {
			isRoleArrayMap_.put(roleArrayName, roleArrayName);
		}
		public void designateStagePropAsArray(final String stagePropArrayName) {
			isStagePropArrayMap_.put(stagePropArrayName, stagePropArrayName);
		}
		public void setRoleArrayBindingToArray(final RTRoleIdentifier lhs, final RTArrayObject rhs) {
			rhs.incrementReferenceCount();
			final String roleName = lhs.name();
			RTObject oldValue = null;
			if (nameToRoleBindingMap_.containsKey(roleName)) {
				oldValue = (RTArrayObject)nameToRoleBindingMap_.get(roleName);
			}
			
			nameToRoleBindingMap_.put(roleName, rhs);
			
			final RTContextInfo contextInfo = this.contextInfo();
			assert null != contextInfo;
			
			if (null != oldValue) {
				int size = 0;
				if (oldValue instanceof RTArrayObject) {
					size = ((RTArrayObject)oldValue).size();
				} else if (oldValue instanceof RTListObject) {
					size = ((RTListObject)oldValue).size();
				} else {
					assert false;
				}
				for (int i = 0; i < size; i++) {
					final RTObject value = rhs.get(i);
					if (value instanceof RTStageProp == false) {
						value.decrementReferenceCount();
						contextInfo.removeRoleArrayPlayer(roleName, i);
					}
				}
			}
			
			for (final Map.Entry<String, String> iter : isRoleArrayMap_.entrySet()) {
				contextInfo.designateRoleAsArray(iter.getKey());
			}
			
			for (int i = 0; i < rhs.size(); i++) {
				final RTObject value = rhs.get(i);
				if (value instanceof RTStageProp == false) {
					contextInfo.addRoleArrayPlayer(roleName, i, value);
				}
			}
		}
		
		public void setRoleArrayBindingToList(final RTRoleIdentifier lhs, final RTListObject rhs) {
			rhs.incrementReferenceCount();
			final String roleName = lhs.name();
			RTObject oldValue = null;
			if (nameToRoleBindingMap_.containsKey(roleName)) {
				oldValue = (RTObject)nameToRoleBindingMap_.get(roleName);
			}
			
			nameToRoleBindingMap_.put(roleName, rhs);
			
			final RTContextInfo contextInfo = this.contextInfo();
			assert null != contextInfo;
			
			if (null != oldValue) {
				int size = 0;
				if (oldValue instanceof RTArrayObject) {
					size = ((RTArrayObject)oldValue).size();
				} else if (oldValue instanceof RTListObject) {
					size = ((RTListObject)oldValue).size();
				} else {
					assert false;
				}
				for (int i = 0; i < size; i++) {
					final RTObject value = rhs.get(i);
					if (value instanceof RTStageProp == false) {
						value.decrementReferenceCount();
						contextInfo.removeRoleArrayPlayer(roleName, i);
					}
				}
			}
			
			for (Map.Entry<String, String> iter : isRoleArrayMap_.entrySet()) {
				contextInfo.designateRoleAsArray(iter.getKey());
			}
			
			for (int i = 0; i < rhs.size(); i++) {
				final RTObject value = rhs.get(i);
				if (value instanceof RTStageProp == false) {
					contextInfo.addRoleArrayPlayer(roleName, i, value);
				}
			}
		}
		
		public void setStagePropArrayBindingToArray(final RTRoleIdentifier lhs, final RTArrayObject rhs) {
			// Refactor!
			rhs.incrementReferenceCount();
			final String roleName = lhs.name();
			RTArrayObject oldValue = null;
			if (nameToRoleBindingMap_.containsKey(roleName)) {
				oldValue = (RTArrayObject)nameToRoleBindingMap_.get(roleName);
			}
			
			nameToStagePropBindingMap_.put(roleName, rhs);
			
			final RTContextInfo contextInfo = this.contextInfo();
			assert null != contextInfo;
			
			if (null != oldValue) {
				for (int i = 0; i < oldValue.size(); i++) {
					final RTObject value = rhs.get(i);
					value.decrementReferenceCount();
					contextInfo.removeStagePropArrayPlayer(roleName, i);
				}
			}
			
			for (Map.Entry<String, String> iter : isStagePropArrayMap_.entrySet()) {
				contextInfo.designateStagePropAsArray(iter.getKey());
			}
			
			for (int i = 0; i < rhs.size(); i++) {
				final RTObject value = rhs.get(i);
				contextInfo.addStagePropArrayPlayer(roleName, i, value);
			}
		}
		
		public RTCode setRoleArrayElementBinding(final RTRoleArrayIndexExpression lhs, final RTObject rhs) {
			// Assign indicated object in RHS to a Role in LHS
			
			final String roleName = lhs.roleName();
			
			// Evaluate the index
			RTCode pc = lhs.indexExpression();
			do {
				if (pc instanceof RTHalt) {
					return pc;
				} else {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				}
			} while (null != pc);
			
			final RTObject rawIndexResult = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawIndexResult instanceof RTIntegerObject;
			final RTIntegerObject indexResult = (RTIntegerObject) rawIndexResult;
			
			// Current context could be in current$context if we're in a Role method,
			// or simply in "this" if we're in a Context method
			final RTObject currentContext = this;
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			contextInfo.setRolePlayerNamedAndIndexed(roleName, indexResult, rhs);
		
			return null;
		}
		
		public RTCode setStagePropArrayElementBinding(final RTRoleArrayIndexExpression lhs, final RTObject rhs) {
			// Assign indicated object in RHS to a Role in LHS
			
			final String stagePlayerName = lhs.roleName();
			
			// Evaluate the index
			RTCode pc = lhs.indexExpression();
			do {
				if (pc instanceof RTHalt) {
					return pc;
				} else {
					pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
				}
			} while (null != pc);
			
			final RTObject rawIndexResult = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawIndexResult instanceof RTIntegerObject;
			final RTIntegerObject indexResult = (RTIntegerObject) rawIndexResult;
			
			// Current context could be in current$context if we're in a Role method,
			// or simply in "this" if we're in a Context method
			final RTObject currentContext = this;
			final RTObject rawContextInfo = currentContext.getObject("context$info");
			assert rawContextInfo instanceof RTContextInfo;
			final RTContextInfo contextInfo = (RTContextInfo) rawContextInfo;
			
			contextInfo.setStagePropPlayerNamedAndIndexed(stagePlayerName, indexResult, rhs);
		
			return null;
		}
		
		@Override public RTObject dup() {
			assert false;
			return null;
		}
		
		// Debugging only
		@Override public void incrementReferenceCount() {
			super.incrementReferenceCount();
		}
		
		private void unbindAllRolesAndStageProps() {
			for (final Map.Entry<String,RTObject> iter : nameToRoleBindingMap_.entrySet()) {
				final RTObject rolePlayer = iter.getValue();
				rolePlayer.decrementReferenceCount();
			}
			for (final Map.Entry<String,RTObject> iter : nameToStagePropBindingMap_.entrySet()) {
				final RTObject stageProp = iter.getValue();
				stageProp.decrementReferenceCount();
			}
			
			nameToRoleBindingMap_ = new LinkedHashMap<String,RTObject>();
			nameToStagePropBindingMap_ = new LinkedHashMap<String,RTObject>();
		}
		
		@Override public void decrementReferenceCount() {
			super.decrementReferenceCount();
			final RTContextInfo contextInfo = contextInfo();
			if (0 == referenceCount()) {
				// I'm outta here. Let all my RolePlayers know
				contextInfo.removeAllRoleAndStagePropPlayers();
				unbindAllRolesAndStageProps();
				cleanedUp_ = true;
			} else if (1 == referenceCount() && this == RTExpression.lastExpressionResult()) {
				contextInfo.removeAllRoleAndStagePropPlayers();
				unbindAllRolesAndStageProps();
				RTExpression.setLastExpressionResult(new RTNullObject(), 0);
			} else if (0 > referenceCount()) {
				// REF_COUNT_TAG_BUG_1 / Issue 133:
				//
				// Something was formally very badly wrong here, because
				// we DO get to this code with test example
				// examples/keypad.k
				//
				// This was formerly a stop-the-train assertion, but
				// on looking into it, it should never happen
				//
				// This may be related to a reference count issue
				// in RTQualifiedIdentifierPart2.run. See tag REF_COUNT_TAG_BUG_1
				// in RTExpression.java. If we remove a reference count decrement
				// there, then testing seems to have a hard time getting us to
				// the former assertion here.
				//
				// I'm keeping the code here until we really figure out
				// what is going on with reference count for qualified
				// identifiers that get pushed onto the evaluation stack
				// FIXME.
				if (!cleanedUp_) {
					// this is here as a safety valve to help keep
					// running, even if the counts do get out of line.
					// testing so far does not reach this code
					//
					// This of course should never happen, and with the
					// fix to Issue 133, we cannot reproduce execution at
					// this code block.
					contextInfo.removeAllRoleAndStagePropPlayers();
					unbindAllRolesAndStageProps();
					cleanedUp_ = true;
				} else {
					// however, this seems to be where we ended up sometimes
					// (before we made the change in RTQualifiedIdentifierPart2.run)
					;
				}
			}
		}
		@Override public String getText() {
			return rTType().name();
		}
		@Override public int hashCode() {
			// This is necessary for the way that objects keep track
			// of their Role-playing registration. We need the canonical
			// hashCode() and equals(Object) methods.
			return getText().hashCode();
		}
		@Override public boolean equals(final Object other) {
			return this == other;
		}
		public boolean isRoleOrStagePropArray(final String roleOrStagePropName) {
			boolean retval = false;
			if (isRoleArrayMap_.containsKey(roleOrStagePropName)) {
				retval = true;
			} else if (isStagePropArrayMap_.containsKey(roleOrStagePropName)) {
				retval = true;
			}
			return retval;
		}
		public StaticScope staticScope() {
			RTType a = this.rTType();
			ContextDeclaration b = (ContextDeclaration)((RTContext)a).typeDeclaration_;
			return b.enclosedScope();
		}
		
		public int uniqueID() {
			// Used in debugging
			return uniqueID_;
		}

		// These used mainly for debugging
		final private int uniqueID_;
		private static int idGenerator_ = 0;
		
		private final Map<String, RTRole> nameToRoleMap_;
		private final Map<String, RTStageProp> nameToStagePropMap_;
		private       Map<String, RTObject> nameToRoleBindingMap_, nameToStagePropBindingMap_;
		private final Map<String, String> isRoleArrayMap_, isStagePropArrayMap_;
		private boolean cleanedUp_ = false;
	}
	
	public static class RTIntegerObject extends RTObjectCommon {
		public RTIntegerObject(final long foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed("int"));
			foobar_ = foobar;
		}
		protected RTIntegerObject(final long foobar, final String typeName) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(typeName));
			foobar_ = foobar;
		}
		public long intValue() { return foobar_; }
		@Override public boolean isEqualTo(final Object another) {
			if ((another instanceof RTIntegerObject) == false) return false;
			else return foobar_ == ((RTIntegerObject)another).intValue();
		}
		@Override public boolean gt(final RTObject other) {
			boolean result = false;
			if (other instanceof RTIntegerObject) {
				final long otherValue = ((RTIntegerObject)other).intValue();
				result = foobar_ > otherValue;
			} else if (other instanceof RTDoubleObject) {
				final double otherValue = ((RTDoubleObject)other).doubleValue();
				result = foobar_ > otherValue;
			} else {
				assert false;
			}
			return result;
		}
		@Override public RTObject plus(final RTObject other) {
			long result = 0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ + (long)((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ + ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTIntegerObject(result);
		}
		@Override public RTObject minus(final RTObject other) {
			long result = 0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ - (long)((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ - ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTIntegerObject(result);
		}
		@Override public RTObject times(final RTObject other) {
			long result = 0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ * (long)((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ * ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTIntegerObject(result);
		}
		@Override public RTObject divideBy(final RTObject other) {
			RTObject result = null;
			if (other instanceof RTDoubleObject) {
				result = new RTDoubleObject(((double)foobar_) / ((RTDoubleObject)other).doubleValue());
			} else if (other instanceof RTIntegerObject) {
				result = new RTIntegerObject(foobar_ / ((RTIntegerObject)other).intValue());
			} else {
				assert false;
			}
			return result;
		}
		@Override public RTObject modulus(final RTObject other) {
			long result = 0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ % (long)((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ % ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTIntegerObject(result);
		}
		@Override public RTObject unaryPlus() {
			return new RTIntegerObject(foobar_);
		}
		@Override public RTObject unaryMinus() {
			return new RTIntegerObject(-foobar_);
		}
		@Override public RTObject unaryLogicalNegation() {
			return new RTIntegerObject(foobar_ == 0? 1: 0);
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
		@Override public RTObject toThePowerOf(final RTObject other) {
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
		public RTObject to1CharString() {
			final char c = (char)foobar_;
			final String rawRetval = "" + c;
			final RTObject retval = new RTStringObject(rawRetval);
			return retval;
		}
		@Override public int hashCode() {
			return (int)foobar_;
		}
		@Override public String toString() {
			return this.getText();
		}
		@Override public boolean equals(final Object other) {
			boolean retval = true;
			if (other instanceof RTIntegerObject) {
				retval = foobar_ == ((RTIntegerObject)other).foobar_;
			} else {
				retval = false;
			}
			return retval;
		}
		@Override public String getText() {
			return Integer.toString((int)foobar_);
		}
		
		private long foobar_;
	}
	public static class RTBigIntegerObject extends RTIntegerObject {
		public RTBigIntegerObject(final int foobar) {
			super(foobar, "Integer");
		}
	}
	public static class RTDoubleObject extends RTObjectCommon {
		public RTDoubleObject(final double foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed("double"));
			foobar_ = foobar;
		}
		public double doubleValue() { return foobar_; }
		@Override public boolean isEqualTo(final Object another) {
			boolean retval = false;
			if (another instanceof RTIntegerObject) {
				retval = Math.abs(foobar_ - (double)((RTIntegerObject)another).intValue()) < EPSILON;
			} else if (another instanceof RTDoubleObject) {
				retval = Math.abs(foobar_ - ((RTDoubleObject)another).doubleValue()) < EPSILON;
			} else {
				assert false;
			}
			return retval;
		}
		@Override public boolean gt(final RTObject another) {
			final RTDoubleObject anotherAsDouble = RTClass.makeDouble(another);
			return foobar_ > anotherAsDouble.doubleValue();
		}
		@Override public RTObject plus(final RTObject other) {
			double result = 0.0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ + ((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ + ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTDoubleObject(result);
		}
		@Override public RTObject minus(final RTObject other) {
			double result = 0.0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ - ((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ - ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTDoubleObject(result);
		}
		@Override public RTObject times(final RTObject other) {
			double result = 0.0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ * ((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ * ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTDoubleObject(result);
		}
		@Override public RTObject divideBy(final RTObject other) {
			double result = 0.0;
			if (other instanceof RTDoubleObject) {
				result = foobar_ / ((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ / ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTDoubleObject(result);
		}
		@Override public RTObject modulus(final RTObject other) {
			double result = 0.0;
			if (other instanceof RTDoubleObject) {
				// ???
				result = foobar_ % ((RTDoubleObject)other).doubleValue();
			} else if (other instanceof RTIntegerObject) {
				result = foobar_ % ((RTIntegerObject)other).intValue();
			} else {
				assert false;
			}
			return new RTDoubleObject(result);
		}
		@Override public RTObject unaryPlus() {
			return new RTDoubleObject(foobar_);
		}
		@Override public RTObject unaryMinus() {
			return new RTDoubleObject(-foobar_);
		}
		@Override public RTObject unaryLogicalNegation() {
			assert false;
			return null;
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
		@Override public RTObject toThePowerOf(final RTObject other) {
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
		@Override public int hashCode() {
			return (int)foobar_;
		}
		@Override public boolean equals(final Object other) {
			boolean retval = true;
			if (other instanceof RTDoubleObject) {
				retval = this.isEqualTo(other);
			} else {
				retval = false;
			}
			return retval;
		}
		@Override public String getText() {
			return Double.toString((double)foobar_);
		}
		@Override public String toString() {
			return getText();
		}
		
		final double EPSILON = 0.00001;
		private double foobar_;
	}
	public static class RTStringObject extends RTObjectCommon {
		public RTStringObject(final String foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelClassNamed("String"));
			foobar_ = foobar;
		}
		public String stringValue() { return foobar_; }
		@Override public String toString() { return this.stringValue(); }
		@Override public boolean isEqualTo(Object another) {
			if ((another instanceof RTStringObject) == false) return false;
			else return foobar_.equals(((RTStringObject)another).stringValue());
		}
		@Override public boolean gt(final RTObject another) {
			if ((another instanceof RTStringObject) == false) return false;
			else return foobar_.compareTo (((RTStringObject)another).stringValue()) > 0;
		}
		@Override public RTObject plus(final RTObject other) {
			final String result = foobar_ + ((RTStringObject)other).stringValue();
			return new RTStringObject(result);
		}
		@Override public RTObjectCommon minus(final RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObjectCommon times(final RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObjectCommon divideBy(final RTObject other) {
			assert false;
			return null;
		}
		@Override public RTObjectCommon modulus(final RTObject other) {
			assert other instanceof RTStringObject;
			final CharSequence nothing = "", cut = ((RTStringObject)other).stringValue();
			final String rawRetval = foobar_.replace(cut, nothing);
			return new RTStringObject(rawRetval);
		}
		@Override public RTStringObject dup() {
			return new RTStringObject(foobar_);
		}
		public RTStringObject substring(final RTObject rTStart, final RTObject rTEnd) {
			RTStringObject retval = null;
			final RTIntegerObject start = (RTIntegerObject)rTStart;
			final RTIntegerObject end = (RTIntegerObject)rTEnd;
			final long iStart = start.intValue();
			final long iEnd = end.intValue();
			if (0 > iStart || foobar_.length() < iEnd) {
				ErrorLogger.error(ErrorIncidenceType.Runtime, null, "substring index out-of-range: (",
						Integer.toString((int)iStart) + ", " + Integer.toString((int)iEnd),
						") on String of length ", Integer.toString(foobar_.length()));
				RTMessage.printMiniStackStatus();
				retval = null;
			} else {
				final String sRetval = foobar_.substring((int)iStart, (int)iEnd);
				retval = new RTStringObject(sRetval);
			}
			return retval;
		}
		public RTStringObject replaceFirst(final RTObject regexArg, final RTObject replacementArg) {
			RTStringObject retval = null;
			final RTStringObject regex = (RTStringObject)regexArg;
			final RTStringObject replacement = (RTStringObject)replacementArg;
			final String sRetval = foobar_.replaceFirst(regex.stringValue(), replacement.stringValue());
			retval = new RTStringObject(sRetval);
			return retval;
		}
		RTIntegerObject indexOf(final RTObject arg) {
			final RTStringObject rTSearchString = (RTStringObject)arg;
			final String searchString = rTSearchString.stringValue();
			final long lRetval = foobar_.indexOf(searchString);
			final RTIntegerObject retval = new RTIntegerObject(lRetval);
			return retval;
		}
		RTStringObject join(final RTObject string, final RTListObject listOfStrings) {
			// This function is unused. FIXME
			assert false;
			return null;
		}
		@Override public int hashCode() {
			return foobar_.hashCode();
		}
		@Override public boolean equals(final Object other) {
			boolean retval = true;
			if (other instanceof RTStringObject) {
				retval = foobar_.equals(((RTStringObject)other).foobar_);
			} else {
				retval = false;
			}
			return retval;
		}
		public RTObject toInteger() {
			// Cheesy. Only decimal
			final Integer iRetval = Integer.valueOf(foobar_);
			RTIntegerObject retval = new RTIntegerObject(iRetval);
			return retval;
		}
		@Override public String getText() {
			return "\"" + foobar_ + "\"";
		}
		@Override public void incrementReferenceCount() {
			// For debugging only
			super.incrementReferenceCount();
		}
		@Override public void decrementReferenceCount() {
			// For debugging only
			super.decrementReferenceCount();
		}

		private final String foobar_;
	}
	
	public static class RTBooleanObject extends RTObjectCommon {
		public RTBooleanObject(final boolean foobar) {
			super(RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed("boolean"));
			foobar_ = foobar;
		}
		public boolean value() { return foobar_; }
		@Override public boolean isEqualTo(Object another) {
			if ((another instanceof RTBooleanObject) == false) return false;
			else return foobar_ == (((RTBooleanObject)another).value());
		}
		@Override public boolean gt(final RTObject another) {
			return false;
		}
		@Override public RTObject unaryLogicalNegation() {
			return new RTBooleanObject(!foobar_);
		}
		@Override public RTBooleanObject dup() {
			return new RTBooleanObject(foobar_);
		}
		@Override public int hashCode() {
			return foobar_? 1: 0;
		}
		@Override public boolean equals(final Object other) {
			boolean retval = true;
			if (other instanceof RTBooleanObject) {
				retval = foobar_ == ((RTBooleanObject)other).foobar_;
			} else {
				retval = false;
			}
			return retval;
		}
		@Override public RTObject logicalAnd(final RTObject other) {
			boolean answer = false;
			if (other instanceof RTBooleanObject) {
				answer = foobar_ && ((RTBooleanObject)other).foobar_;
			}
			return new RTBooleanObject(answer);
		}
		@Override public RTObject logicalOr(final RTObject other) {
			boolean answer = false;
			if (other instanceof RTBooleanObject) {
				answer = foobar_ || ((RTBooleanObject)other).foobar_;
			}
			return new RTBooleanObject(answer);
		}
		@Override public RTObject logicalXor(final RTObject other) {
			boolean answer = false;
			if (other instanceof RTBooleanObject) {
				answer = foobar_ ^ ((RTBooleanObject)other).foobar_;
			}
			return new RTBooleanObject(answer);
		}
		@Override public String getText() {
			return foobar_? "true": "false";
		}
		@Override public String toString() {
			return this.getText();
		}
		
		private boolean foobar_;
	}
	public static class RTNullObject extends RTObjectCommon {
		public RTNullObject() {
			super((RTClass)null);
		}
		@Override public RTObject dup() {
			return this;
		}
		@Override public boolean isEqualTo(final Object another) {
			return another instanceof RTNullObject;
		}
		@Override public int hashCode() {
			return 0;
		}
		@Override public boolean equals(final Object other) {
			return other instanceof RTNullObject;
		}
		@Override public String toString() {
			return "<null>";
		}
		@Override public void unenlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance) {
			// Not sure if no-op is the right thing to do, but
			// it avoids silly work.
		}
	}
	
	@Override public RTObject performUnaryOpOnObjectNamed(final String idName, final String operator, final PreOrPost preOrPost) {
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
	
	@Override public void incrementReferenceCount() {
		referenceCount_++;
	}
	@Override public void decrementReferenceCount() {
		--referenceCount_;
	}
	@Override public long referenceCount() {
		return referenceCount_;
	}
	
	// Special constructor only for cloning
	private RTObjectCommon(final RTType classOrContext, final Map<String,RTObject> objectMembers, Map<String, RTType> rTTypeMap) {
		super();
		classOrContext_ = classOrContext;
		
		// WARNING - These are only shallow copies
		objectMembers_ = new LinkedHashMap<String, RTObject>();
		rTTypeMap_ = new LinkedHashMap<String, RTType>(rTTypeMap);
		
		// Values should be unique
		for (Map.Entry<String, RTObject> iter : objectMembers.entrySet()) {
			final String key = iter.getKey();
			final RTObject value = iter.getValue();
			final RTObject clonedValue = value.dup();
			assert(null == objectMembers_.get(key));	// shouldn't be duplicates
			objectMembers_.put(key, clonedValue);
		}
	}
	@Override public RTObject dup() {
		RTObject retval = new RTObjectCommon(classOrContext_, objectMembers_, rTTypeMap_);
		return retval;
	}
	@Override public boolean equals(final RTObject other) {
		boolean retval = true;
		if (other.hashCode() != this.hashCode()) {
			retval = false;
		} else {
			if (other instanceof RTObjectCommon == false) {
				retval = false;
			} else {
				final RTObjectCommon commonOther = (RTObjectCommon)other;
				if (objectMembers_.size() != commonOther.objectMembers_.size()) {
					retval = false;
				} else {
					for (final String aKey : objectMembers_.keySet()) {
						final RTObject myValue = objectMembers_.get(aKey);
						final RTObject otherValue = other.getObject(aKey);
						if (null != myValue && myValue.equals(otherValue) == false) {
							retval = false;
							break;
						}
					}
				}
			}
		}
		return retval;
	}
	@Override public int hashCode() {
		int retval = 0;
		for (final RTObject aMember : objectMembers_.values()) {
			retval ^= aMember.hashCode();
		}
		return retval;
	}
	@Override public String getText() {
		return "<object of type " + (null == classOrContext_?
				"<null>": classOrContext_.name()) + ">";
	}
	
	private final RTType classOrContext_;
	protected     Map<String, RTObject> objectMembers_;
	private final Map<String, RTType> rTTypeMap_;
	private Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private Map<RTContextObject, List<String>> stagePropsIAmPlayingInContext_;
}
