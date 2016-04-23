package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 2.0
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;   
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public class RTContext extends RTClassAndContextCommon implements RTType, RTContextInstance {
	public RTContext(final TypeDeclaration decl) {
		super(decl);
		stringToContextDeclMap_ = new LinkedHashMap<String, RTContext>();
		stringToClassDeclMap_ = new LinkedHashMap<String, RTClass>();
		nameToTypeObjectMap_ = new LinkedHashMap<String, RTType>();
		nameToRoleObjectMap_ = new LinkedHashMap<String, RTRole>();
		nameToStagePropObjectMap_ = new LinkedHashMap<String, RTStageProp>();
		nameToObjectDeclMap_ = new LinkedHashMap<String, RTObject>();
		isRoleArrayMap_ = new LinkedHashMap<String, String>();
		isStagePropArrayMap_ = new LinkedHashMap<String, String>();
		
		// Contexts have a special administrative tag-along, used
		// initially for tracking its Role-players
		this.addObjectDeclaration("context$info", null);
		
		populateNameToTypeObjectMap();
		populateNameToStaticObjectMap();
		populateNameToRoleAndStagePropMaps();
		populateIsRoleArrayMap();
	}
	private void populateNameToRoleAndStagePropMaps() {
		assert null != typeDeclaration_;
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		final List<RoleDeclaration> roleDeclarations = enclosedScope.roleDeclarations();

		for (final RoleDeclaration roleDecl : roleDeclarations) {
			final String name = roleDecl.name();
			
			// Need the RTRole / RTStageProp declaration instead...
			RTRole rTRole = nameToRoleObjectMap_.get(name);
			RTStageProp rTStageProp = nameToStagePropObjectMap_.get(name);
			if (null == rTRole) {
				 if (roleDecl instanceof StagePropDeclaration) {
					rTStageProp = new RTStageProp((StagePropDeclaration)roleDecl);
					nameToStagePropObjectMap_.put(name, rTStageProp);
				} else if (roleDecl instanceof RoleDeclaration) {
					rTRole = new RTRole(roleDecl);
					nameToRoleDeclMap_.put(name, rTRole);
				} else {
					assert false;
				}
			}
		}
	}
	private void populateIsRoleArrayMap() {
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		final List<RoleDeclaration> roleDeclarations = enclosedScope.roleDeclarations();
		for (final RoleDeclaration roleDeclaration : roleDeclarations) {
			if (roleDeclaration.isArray()) {
				final String name = roleDeclaration.name();
				isRoleArrayMap_.put(name, name);
			}
		}
	}
	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		return nameToRoleDeclMap_;
	}
	@Override public Map<String, RTStageProp> nameToStagePropDeclMap() {
		return nameToStagePropDeclMap_;
	}
	@Override public void addClass(final String typeName, RTClass classDecl) {
		stringToClassDeclMap_.put(typeName,  classDecl);
	}
	@Override public void addContext(final String typeName, final RTContext contextDecl) {
		stringToContextDeclMap_.put(typeName, contextDecl);
	}
	@Override public void addStageProp(final String stagePropName, final RTStageProp stagePropType) {
		nameToStagePropObjectMap_.put(stagePropName, stagePropType);
	}
	@Override public void addRole(final String roleName, final RTRole roleType) {
		// Are these two arrays redundant? TODO
		if (nameToRoleDeclMap_.containsKey(roleName) == false) {
			nameToRoleDeclMap_.put(roleName,  roleType);
		}
		nameToRoleObjectMap_.put(roleName, roleType);
	}
	@Override public void addObjectDeclaration(final String objectName, final RTType objectType) {
		nameToTypeObjectMap_.put(objectName, objectType);
	}
	@Override public void setObject(final String name, final RTObject value) {
		nameToObjectDeclMap_.put(name, value);
		value.incrementReferenceCount();
	}
	@Override public RTObject getObject(final String name) {
		return nameToObjectDeclMap_.get(name);
	}
	@Override public RTRole getRole(final String name) {
		return nameToRoleObjectMap_.get(name);
	}
	public RTRole getRoleDecl(final String name) {
		return nameToRoleDeclMap_.get(name);
	}
	public RTStageProp getStageProp(final String name) {
		return nameToStagePropObjectMap_.get(name);
	}
	public RTStageProp getStagePropDecl(final String name) {
		return nameToStagePropDeclMap_.get(name);
	}
	@Override public RTType typeNamed(String typeName) {
		RTType retval = this.stringToClassDeclMap_.get(typeName);
		if (null == retval) {
			retval = this.stringToContextDeclMap_.get(typeName);
		}
		return retval;
	}
	public TypeDeclaration contextDeclaration() {
		return typeDeclaration_;
	}
	@Override public RTType rTType() {
		return rTType_;
	}
	public void designateRoleAsArray(final String roleArrayName) {
		isRoleArrayMap_.put(roleArrayName, roleArrayName);
	}
	public void designateStagePropAsArray(final String stagePropArrayName) {
		isStagePropArrayMap_.put(stagePropArrayName, stagePropArrayName);
	}
	public final Set<String> isRoleArrayMapEntries() {
		return isRoleArrayMap_.keySet();
	}
	public final Set<String> isStagePropArrayMapEntries() {
		return isStagePropArrayMap_.keySet();
	}
	
	public static class RTContextInfo extends RTObjectCommon {
		public RTContextInfo(final RTContextObject theContext) {
			super((RTType)null);	// we don't use the type info
			rolePlayers_ = new LinkedHashMap<String, RTObject>();
			stagePropPlayers_ = new LinkedHashMap<String, RTObject>();
			roleArrayPlayers_ = new LinkedHashMap<String, Map<Integer,RTObject>>();
			stagePropArrayPlayers_ = new LinkedHashMap<String, Map<Integer,RTObject>>();
			isRoleArrayMap_ = new LinkedHashMap<String, String>();
			isStagePropArrayMap_ = new LinkedHashMap<String, String>();
			rTContext_ = theContext;
		}
		void addRolePlayer(final String roleName, final RTObject rolePlayer) {
			rolePlayers_.put(roleName, rolePlayer);
			rolePlayer.enlistAsRolePlayerForContext(roleName, rTContext_);
		}
		void addStagePropPlayer(final String stagePropName, final RTObject stagePropPlayer) {
			stagePropPlayers_.put(stagePropName, stagePropPlayer);
			stagePropPlayer.enlistAsStagePropPlayerForContext(stagePropName, rTContext_);
		}
		void addRoleArrayPlayer(final String roleArrayName, final int theIndex, final RTObject rolePlayer) {
			if (roleArrayPlayers_.containsKey(roleArrayName) == false) {
				roleArrayPlayers_.put(roleArrayName, new LinkedHashMap<Integer,RTObject>());
			}
			roleArrayPlayers_.get(roleArrayName).put(Integer.valueOf(theIndex), rolePlayer);
			rolePlayer.enlistAsRolePlayerForContext(roleArrayName, rTContext_);
		}
		void addStagePropArrayPlayer(final String stagePropArrayName, final int theIndex, final RTObject stagePropPlayer) {
			if (stagePropArrayPlayers_.containsKey(stagePropArrayName) == false) {
				stagePropArrayPlayers_.put(stagePropArrayName, new LinkedHashMap<Integer,RTObject>());
			}
			stagePropArrayPlayers_.get(stagePropArrayName).put(Integer.valueOf(theIndex), stagePropPlayer);
			stagePropPlayer.enlistAsStagePropPlayerForContext(stagePropArrayName, rTContext_);
		}
		public void setRolePlayerNamedAndIndexed(final String roleArrayName, final RTIntegerObject indexObject, final RTObject rolePlayer) {
			final Integer theIndex = Integer.valueOf((int)indexObject.intValue());
			Map<Integer,RTObject> intToObjectMap = roleArrayPlayers_.get(roleArrayName);
			if (null == intToObjectMap) {
				intToObjectMap = new LinkedHashMap<Integer,RTObject>();
				roleArrayPlayers_.put(roleArrayName, intToObjectMap);
			} else if (intToObjectMap.containsKey(theIndex)) {
				this.removeRoleArrayPlayer(roleArrayName, theIndex.intValue());
			}
			intToObjectMap.put(theIndex, rolePlayer);
			
			// Almost forgot this...
			rolePlayer.enlistAsRolePlayerForContext(roleArrayName, rTContext_);
		}
		public void setStagePropPlayerNamedAndIndexed(final String stagePropArrayName, final RTIntegerObject indexObject, final RTObject rolePlayer) {
			final Integer theIndex = Integer.valueOf((int)indexObject.intValue());
			Map<Integer,RTObject> intToObjectMap = stagePropArrayPlayers_.get(stagePropArrayName);
			if (null == intToObjectMap) {
				intToObjectMap = new LinkedHashMap<Integer,RTObject>();
				stagePropArrayPlayers_.put(stagePropArrayName, intToObjectMap);
			} else if (intToObjectMap.containsKey(theIndex)) {
				this.removeStagePropArrayPlayer(stagePropArrayName, theIndex.intValue());
			}
			intToObjectMap.put(theIndex, rolePlayer);
			
			// Almost forgot this...
			rolePlayer.enlistAsStagePropPlayerForContext(stagePropArrayName, rTContext_);
		}
		public void removeRolePlayer(final String roleName, final RTObject rolePlayer) {
			rolePlayers_.remove(roleName);
			rolePlayer.unenlistAsRolePlayerForContext(roleName, rTContext_);
		}
		public void removeRoleArrayPlayer(final String roleArrayName, final int theIndex) {
			final Map<Integer,RTObject> rolePlayerArray = roleArrayPlayers_.get(roleArrayName);
			final RTObject oldRolePlayer = rolePlayerArray.get(Integer.valueOf(theIndex));
			assert null != oldRolePlayer;
			rolePlayerArray.remove(Integer.valueOf(theIndex));
			oldRolePlayer.unenlistAsRolePlayerForContext(roleArrayName, rTContext_);
		}
		public void removeStagePropPlayer(final String stagePropName, final RTObject stagePropPlayer) {
			stagePropPlayers_.remove(stagePropName);
			stagePropPlayer.unenlistAsStagePropPlayerForContext(stagePropName, rTContext_);
		}
		public void removeStagePropArrayPlayer(final String stagePropArrayName, final int theIndex) {
			final Map<Integer,RTObject> stagePropPlayerArray = stagePropArrayPlayers_.get(stagePropArrayName);
			final RTObject oldRolePlayer = stagePropPlayerArray.get(Integer.valueOf(theIndex));
			assert null != oldRolePlayer;
			stagePropPlayerArray.remove(Integer.valueOf(theIndex));
			oldRolePlayer.unenlistAsStagePropPlayerForContext(stagePropArrayName, rTContext_);
		}
		public void removeAllRoleAndStagePropPlayers() {
			for (final Map.Entry<String, RTObject> iter : rolePlayers_.entrySet()) {
				final String roleName = iter.getKey();
				if (this.isRoleArray(roleName) == false) {
					final RTObject rolePlayer = iter.getValue();
					rolePlayer.unenlistAsRolePlayerForContext(roleName, rTContext_);
				}
			}
			for (final Map.Entry<String, RTObject> iter : stagePropPlayers_.entrySet()) {
				final String stagePropName = iter.getKey();
				if (this.isStagePropArray(stagePropName) == false) {
					final RTObject stagePropPlayer = iter.getValue();
					stagePropPlayer.unenlistAsStagePropPlayerForContext(stagePropName, rTContext_);
				}
			}
			for (final Map.Entry<String, Map<Integer, RTObject>> iter : roleArrayPlayers_.entrySet()) {
				final String roleArrayName = iter.getKey();
				for (Map.Entry<Integer, RTObject> iter2 : iter.getValue().entrySet()) {
					final RTObject rolePlayerArray = iter2.getValue();
					rolePlayerArray.unenlistAsRolePlayerForContext(roleArrayName, rTContext_);
				}
			}
			for (final Map.Entry<String, Map<Integer, RTObject>> iter : stagePropArrayPlayers_.entrySet()) {
				final String stagePropPlayerArrayName = iter.getKey();
				for (Map.Entry<Integer, RTObject> iter2 : iter.getValue().entrySet()) {
					final RTObject stagePropPlayerArray = iter2.getValue();
					stagePropPlayerArray.unenlistAsStagePropPlayerForContext(stagePropPlayerArrayName, rTContext_);
				}
			}
		}
		public RTStackable rolePlayerNamedAndIndexed(final String roleName, final RTIntegerObject theIndex) {
			RTStackable retval = null;
			final Map<Integer,RTObject> intToObjectMap = roleArrayPlayers_.get(roleName);
			final int iIndex = (int)theIndex.intValue();
			if (null == intToObjectMap) {
				// The map at roleArrayPlayers_[roleName] would never have been set
				// up if the vector / array size were zero. Treat as an out-of-range
				// error
				ErrorLogger.error(ErrorIncidenceType.Runtime, 0, "Role vector `", roleName, "' indexed out-of-range at index ", String.valueOf(iIndex), ".", "");
				RTMessage.printMiniStackStatus(); 
				retval = null;
			} else {
				if (iIndex >= intToObjectMap.size()) {
					ErrorLogger.error(ErrorIncidenceType.Runtime, 0, "Role vector `", roleName, "' indexed out-of-range (too large) at index ", String.valueOf(iIndex), ".", "");
					RTMessage.printMiniStackStatus();
					retval = (RTStackable) new RTHalt();
				} else if (iIndex < 0) {
						ErrorLogger.error(ErrorIncidenceType.Runtime, 0, "Role vector `", roleName, "' indexed out-of-range (negative) at index ", String.valueOf(iIndex), ".", "");
						RTMessage.printMiniStackStatus();
						retval = (RTStackable) new RTHalt();
				} else {
					retval = intToObjectMap.get(Integer.valueOf(iIndex));
				}
			}
			return retval;
		}
		
		public void designateRoleAsArray(final String roleArrayName) {
			isRoleArrayMap_.put(roleArrayName, roleArrayName);
		}
		public void designateStagePropAsArray(final String stagePropArrayName) {
			isStagePropArrayMap_.put(stagePropArrayName, stagePropArrayName);
		}
		private boolean isRoleArray(final String roleName) {
			return isRoleArrayMap_.containsKey(roleName);
		}
		private boolean isStagePropArray(final String roleName) {
			return isStagePropArrayMap_.containsKey(roleName);
		}
		public RTIntegerObject indexOfRolePlayer(final String roleName, final RTObject rolePlayer) {
			RTIntegerObject retval = new RTIntegerObject(-1);
			final Map<Integer,RTObject> roleVecElements = roleArrayPlayers_.get(roleName);
			if (null == roleVecElements) {
				retval = null;
				ErrorLogger.error(ErrorIncidenceType.Runtime, "Undefined behavior: attempted use of unbound Role vector `",
						roleName, "'.", "");
				RTMessage.printMiniStackStatus();
			} else {
				for (Map.Entry<Integer, RTObject> aRole : roleVecElements.entrySet()) {
					final RTObject potentialRolePlayer = aRole.getValue();
					if (potentialRolePlayer == rolePlayer) {
						retval = new RTIntegerObject(aRole.getKey().intValue());
						break;
					}
				}
			}
			return retval;
		}
		public RTIntegerObject indexOfLastRolePlayer(final String roleName) {
			final Map<Integer,RTObject> roleVecElements = roleArrayPlayers_.get(roleName);
			RTIntegerObject retval = null;
			
			if (null == roleVecElements) {
				retval = null;
				ErrorLogger.error(ErrorIncidenceType.Runtime, "Undefined behavior: attempted use of unbound Role vector `",
						roleName, "'.", "");
				RTMessage.printMiniStackStatus();
			} else {
				retval = new RTIntegerObject(roleVecElements.size() - 1);
			}
			
			return retval;
		}
		
		private final Map<String, RTObject> rolePlayers_, stagePropPlayers_;
		private final Map<String, String> isRoleArrayMap_, isStagePropArrayMap_;
		private final Map<String, Map<Integer,RTObject>> roleArrayPlayers_;
		private final Map<String, Map<Integer,RTObject>> stagePropArrayPlayers_;
		private final RTContextObject rTContext_;
	}
	
	private Map<String, RTContext> stringToContextDeclMap_;
	private Map<String, RTClass> stringToClassDeclMap_;
	private Map<String, RTRole> nameToRoleObjectMap_;
	private Map<String, RTStageProp> nameToStagePropObjectMap_;
	private Map<String, RTObject> nameToObjectDeclMap_;
	private final Map<String, String> isRoleArrayMap_, isStagePropArrayMap_;
	private RTType rTType_;
}
