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

import java.util.HashMap;
import java.util.Map;


public class RTDynamicScope extends RTObjectCommon {
	public RTDynamicScope(String methodSelector, RTObject object, RTDynamicScope parentScope) {
		super(object);
		// An object IS a scope
		
		nameToRoleBindingMap_ = new HashMap<String, RTObject>();
		object.incrementReferenceCount();
		
		// So far this is used only for debugging
		methodSelector_ = methodSelector;
		parentScope_ = parentScope;
	}
	public RTDynamicScope(String methodSelector, RTDynamicScope parentScope) {
		super((RTType)null);
		nameToRoleBindingMap_ = new HashMap<String, RTObject>();
		
		// So far this is used only for debugging
		methodSelector_ = methodSelector;
		
		parentScope_ = parentScope;
	}
	public void setNamedSlotToValue(String name, RTObject value) {
		if (objectMembers_.containsKey(name)) {
			final RTObject oldValue = objectMembers_.get(name);
			objectMembers_.put(name,  value);
			value.incrementReferenceCount();
			if (null != oldValue) {
				oldValue.decrementReferenceCount();
			}
		} else {
			assert false;		// maybe need additional logic for roles
		}
	}
	
	public void closeScope() {
		// Called when a scope is closed
		// We should decrement the reference count on all of
		// our objects. First, the roles:

		for (Map.Entry<String, RTObject> iter : nameToRoleBindingMap_.entrySet()) {
			final RTObject boundToRole = iter.getValue();
			if (null != boundToRole) {
				boundToRole.decrementReferenceCount();
			}
		}
		
		for (Map.Entry<String, RTObject> iter : objectMembers_.entrySet()) {
			final RTObject boundToLocalIdentifier = iter.getValue();
			if (null != boundToLocalIdentifier) {
				boundToLocalIdentifier.decrementReferenceCount();
			}
		}
	}
	public RTDynamicScope nearestEnclosingScopeDeclaring(final String name) {
		RTDynamicScope retval = this;
		do {
			if (retval.objectMembers_.containsKey(name)) {
				break;
			} else {
				retval = retval.parentScope();
				if (retval == RunTimeEnvironment.runTimeEnvironment_.globalDynamicScope) {
					retval = null;
					break;
				}
			}
		} while (null != retval);
		return retval;
	}
	
	public void setRoleBinding(String name, RTObject value) {
		value.incrementReferenceCount();
		nameToRoleBindingMap_.put(name, value);
	}
	public RTObject getRoleBinding(String name) {
		RTObject retval = null;
		if (nameToRoleBindingMap_.containsKey(name)) {
		   	retval = nameToRoleBindingMap_.get(name);
	    } else {
	    	retval = new RTNullObject();
		    
	    }
		return retval;
	}
	public Map<String, RTObject> roleBindings() {
		return nameToRoleBindingMap_;
	}
	public RTDynamicScope parentScope() {
		return parentScope_;
	}
	public RTObject dup() {
		assert false;
		return this;
	}
		
	private final Map<String, RTObject> nameToRoleBindingMap_;
	private final RTDynamicScope parentScope_;
	
	@SuppressWarnings("unused")
	private String methodSelector_;
}
