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


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTIterator.RTMapIterator;
import info.fulloo.trygve.semantic_analysis.StaticScope;
import info.fulloo.trygve.declarations.TypeDeclaration;

public class RTMapObject extends RTObjectCommon implements RTIterable {
	@Override public RTObject getObject(final RTObject theIndexObject) {
		final RTObject retval = theMap_.get(theIndexObject);
		return retval;
	}
	public RTMapObject(final RTType mapType) {
		super(mapType);
		
		// MAP>>KEYS
		RTClass classMapType = null;
		TypeDeclaration declaration = null;
		
		if (mapType instanceof RTClass) {
			classMapType = (RTClass)mapType;
			declaration = classMapType.typeDeclaration();
		}
		final String mapTypeName = declaration.name();
		String keyTypeName = mapTypeName.substring(4);
		final int commaIndex = keyTypeName.indexOf(',');
		keyTypeName = keyTypeName.substring(0, commaIndex);
		keyType_ = StaticScope.globalScope().lookupTypeDeclaration(keyTypeName);	
		mapType_ = mapType;	// e.g. an instance of RTClass
		
		// need to do Value Type eventually, too, like keyTyoe_ above
		valueType_ = null;
		
		theMap_ = new LinkedHashMap<RTObject,RTObject>();
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	
	public Type keyType() {
		return keyType_;
	}
	public Type valueType() {
		return valueType_;
	}
	@Override public int size() {
		return theMap_.size();
	}
	@Override public int hashCode() {
		return theMap_.hashCode();
	}
	@Override public boolean equals(final Object other) {
		boolean retval = true;
		if (other instanceof RTMapObject) {
			retval = theMap_.equals(((RTMapObject)other).theMap_);
		} else {
			retval = false;
		}
		return retval;
	}
	@Override public String toString() {
		return "<Map>";
	}
	
	// I'm a little unhappy that these are copy-pasted. FIXME.
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
			ErrorLogger.error(ErrorIncidenceType.Fatal, "Object of type ", this.rTType().name(),
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
	private RTMapObject(final Map<RTObject, RTObject> theMap, final Type keyType, final Type valueType, final RTType mapType) {
		super(mapType);
		theMap_ = new LinkedHashMap<RTObject, RTObject>();
		keyType_ = keyType;
		valueType_ = valueType;
		mapType_ = mapType;
		for (final RTObject k : theMap_.keySet()) {
			theMap_.put(k, theMap.get(k));
		}
		rolesIAmPlayingInContext_ = new LinkedHashMap<RTContextObject, List<String>>();
	}
	public RTIterator makeIterator() {
		final RTIterator retval = new RTMapIterator(this);
		return retval;
	}
	@Override public RTObject dup() {
		final RTMapObject retval = new RTMapObject(theMap_, keyType_, valueType_, mapType_);
		return retval;
	}
	@Override public RTType rTType() {
		return mapType_;
	}
	
	public RTObject remove(final RTObject key) {
		RTObject retval = theMap_.remove(key);
		assert (null != retval);
		// reference count decremented in caller
		return retval;
	}
	public RTObject get(final RTObject element) {
		RTObject retval = theMap_.get(element);
		if (null == retval) {
			ErrorLogger.error(ErrorIncidenceType.Runtime,
					null,
					"ERROR: Map object <",
					null == keyType_? "null": keyType_.toString(), " -> " + element.getText(),
					",",
					null == valueType_? "null": valueType_.toString(),
					"> used before initialized. Further execution may exhibit undefined behavior.");
			retval = new RTNullObject();
		}
		return retval;
	}
	public void ctor() { }
	@Override public void decrementReferenceCount() {
		super.decrementReferenceCount();
		if (referenceCount_ == 0) {
			for (final RTObject o : theMap_.keySet()) {
				theMap_.get(o).decrementReferenceCount();
				o.decrementReferenceCount();
			}
		} else if (referenceCount_ < 0) {
			assert false;
		}
	}
	public void put(final RTObject key, final RTObject value) {
		assert (null != value);
		theMap_.put(key, value);
		key.incrementReferenceCount();
		value.incrementReferenceCount();
	}
	public void putAll(final RTObject rawMap) {
		assert rawMap instanceof RTMapObject;
		final RTMapObject map = (RTMapObject) rawMap;
		for (final RTObject key : map.theMap_.keySet()) {
			this.put(key, map.get(key));
		}
	}
	public RTObject containsKey(final RTObject key) {
		final boolean rawRetval = theMap_.containsKey(key);
		final RTObject retval = new RTBooleanObject(rawRetval);
		return retval;
	}
	public RTObject containsValue(final RTObject value) {
		final boolean rawRetval = theMap_.containsValue(value);
		final RTObject retval = new RTBooleanObject(rawRetval);
		return retval;
	}
	public Iterator<RTObject> RTIterator() {
		final Set<RTObject> keySet = theMap_.keySet();
		return keySet.iterator();
	}
	
	// MAP>>KEYS
	public Set<RTObject> keys() {
		final Set<RTObject> keySet = theMap_.keySet();
		return keySet;
	}
	
	// DEBUG
	public Map<RTObject, RTObject> theMap() { return theMap_; }
	
	private final Map<RTObject, RTObject> theMap_;
	private final Type keyType_, valueType_;	// unused? FIXME
	private final Map<RTContextObject, List<String>> rolesIAmPlayingInContext_;
	private final RTType mapType_;
}