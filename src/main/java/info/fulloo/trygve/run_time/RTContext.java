package info.fulloo.trygve.run_time;

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
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public class RTContext extends RTClassAndContextCommon implements RTType, RTContextInstance {
	public RTContext(TypeDeclaration decl) {
		super(decl);
		stringToContextDeclMap_ = new HashMap<String, RTContext>();
		stringToClassDeclMap_ = new HashMap<String, RTClass>();
		nameToTypeObjectMap_ = new HashMap<String, RTType>();
		nameToRoleObjectMap_ = new HashMap<String, RTRole>();
		nameToStagePropObjectMap_ = new HashMap<String, RTStageProp>();
		nameToObjectDeclMap_ = new HashMap<String, RTObject>();
		isRoleArrayMap_ = new HashMap<String, String>();
		
		// Contexts have a special administrative tag-along, used
		// initially for tracking its Role-players
		this.addObjectDeclaration("context$info", null);
		
		// if (null != typeDeclaration_) {
		// 	rTType_ = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(decl.type().enclosedScope());
		// }
		populateNameToTypeObjectMap();
		populateNameToStaticObjectMap();
		populateNameToRoleMap();
		populateIsRoleArrayMap();
	}
	protected void populateNameToRoleMap() {
		assert null != typeDeclaration_;
		final StaticScope enclosedScope = typeDeclaration_.enclosedScope();
		final List<RoleDeclaration> roleDeclarations = enclosedScope.roleDeclarations();

		for (final RoleDeclaration roleDecl : roleDeclarations) {
			final String name = roleDecl.name();
			
			// Need the RTRole declaration instead...
			RTRole rTRole  = nameToRoleObjectMap_.get(name);
			if (null == rTRole) {
				rTRole = new RTRole(roleDecl);
			}
			nameToRoleDeclMap_.put(name, rTRole);
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
	public Map<String, RTRole> nameToRoleDeclMap() {
		return nameToRoleDeclMap_;
	}
	@Override public void addClass(String typeName, RTClass classDecl) {
		stringToClassDeclMap_.put(typeName,  classDecl);
	}
	@Override public void addContext(String typeName, RTContext contextDecl) {
		stringToContextDeclMap_.put(typeName, contextDecl);
	}
	@Override public void addStageProp(String stagePropName, RTStageProp stagePropType) {
		nameToStagePropObjectMap_.put(stagePropName, stagePropType);
	}
	public void addRole(String roleName, RTRole roleType) {
		// Are these two arrays redundant? TODO
		if (nameToRoleDeclMap_.containsKey(roleName) == false) {
			nameToRoleDeclMap_.put(roleName,  roleType);
		}
		nameToRoleObjectMap_.put(roleName, roleType);
	}
	@Override public void addObjectDeclaration(String objectName, RTType objectType) {
		nameToTypeObjectMap_.put(objectName, objectType);
	}
	@Override public Map<String, RTObject> objectMembers() {
		return nameToObjectDeclMap_;
	}
	@Override public void setObject(String name, RTObject value) {
		nameToObjectDeclMap_.put(name, value);
		value.incrementReferenceCount();
	}
	@Override public RTObject getObject(String name) {
		return nameToObjectDeclMap_.get(name);
	}
	@Override public RTRole getRole(String name) {
		return nameToRoleObjectMap_.get(name);
	}
	public RTStageProp getStageProp(String name) {
		return nameToStagePropObjectMap_.get(name);
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
	public final Set<String> isRoleArrayMapEntries() {
		return isRoleArrayMap_.keySet();
	}
	
	public static class RTContextInfo extends RTObjectCommon {
		public RTContextInfo(final RTContextObject theContext) {
			super((RTType)null);	// we don't use the type info
			rolePlayers_ = new HashMap<String, RTObject>();
			roleArrayPlayers_ = new HashMap<String, Map<Integer,RTObject>>();
			isRoleArrayMap_ = new HashMap<String, String>();
			rTContext_ = theContext;
		}
		void addRolePlayer(final String roleName, RTObject rolePlayer) {
			rolePlayers_.put(roleName, rolePlayer);
			rolePlayer.enlistAsRolePlayerForContext(roleName, rTContext_);
		}
		void addRoleArrayPlayer(final String roleArrayName, int theIndex, RTObject rolePlayer) {
			if (roleArrayPlayers_.containsKey(roleArrayName) == false) {
				roleArrayPlayers_.put(roleArrayName, new HashMap<Integer,RTObject>());
			}
			roleArrayPlayers_.get(roleArrayName).put(Integer.valueOf(theIndex), rolePlayer);
			rolePlayer.enlistAsRolePlayerForContext(roleArrayName, rTContext_);
		}
		public void setRolePlayerNamedAndIndexed(final String roleArrayName, RTIntegerObject indexObject, RTObject rolePlayer) {
			final Integer theIndex = Integer.valueOf((int)indexObject.intValue());
			Map<Integer,RTObject> intToObjectMap = roleArrayPlayers_.get(roleArrayName);
			if (null == intToObjectMap) {
				intToObjectMap = new HashMap<Integer,RTObject>();
				roleArrayPlayers_.put(roleArrayName, intToObjectMap);
			} else if (intToObjectMap.containsKey(theIndex)) {
				this.removeRoleArrayPlayer(roleArrayName, theIndex.intValue());
			}
			intToObjectMap.put(theIndex, rolePlayer);
			
			// Almost forgot this...
			rolePlayer.enlistAsRolePlayerForContext(roleArrayName, rTContext_);
		}
		public void removeRolePlayer(final String roleName, RTObject rolePlayer) {
			rolePlayers_.remove(roleName);
			rolePlayer.unenlistAsRolePlayerForContext(roleName, rTContext_);
		}
		public void removeRoleArrayPlayer(final String roleArrayName, int theIndex) {
			final Map<Integer,RTObject> rolePlayerArray = roleArrayPlayers_.get(roleArrayName);
			final RTObject oldRolePlayer = rolePlayerArray.get(Integer.valueOf(theIndex));
			assert null != oldRolePlayer;
			rolePlayerArray.remove(Integer.valueOf(theIndex));
			oldRolePlayer.unenlistAsRolePlayerForContext(roleArrayName, rTContext_);
		}
		public void removeAllRolePlayers() {
			for (Map.Entry<String, RTObject> iter : rolePlayers_.entrySet()) {
				final String roleName = iter.getKey();
				if (this.isRoleArray(roleName) == false) {
					final RTObject rolePlayer = iter.getValue();
					rolePlayer.unenlistAsRolePlayerForContext(roleName, rTContext_);
				}
			}
			for (Map.Entry<String, Map<Integer, RTObject>> iter : roleArrayPlayers_.entrySet()) {
				final String roleName = iter.getKey();
				for (Map.Entry<Integer, RTObject> iter2 : iter.getValue().entrySet()) {
					final RTObject rolePlayer = iter2.getValue();
					rolePlayer.unenlistAsRolePlayerForContext(roleName, rTContext_);
				}
			}
		}
		public RTObject rolePlayerNamedAndIndexed(final String roleName, RTIntegerObject theIndex) {
			final Map<Integer,RTObject> intToObjectMap = roleArrayPlayers_.get(roleName);
			final RTObject retval = intToObjectMap.get(Integer.valueOf((int)theIndex.intValue()));
			return retval;
		}
		
		public void designateRoleAsArray(final String roleArrayName) {
			isRoleArrayMap_.put(roleArrayName, roleArrayName);
		}
		private boolean isRoleArray(final String roleName) {
			return isRoleArrayMap_.containsKey(roleName);
		}
		public RTIntegerObject indexOfRolePlayer(final String roleName, final RTObject rolePlayer) {
			RTIntegerObject retval = new RTIntegerObject(-1);
			final Map<Integer,RTObject> roleVecElements = roleArrayPlayers_.get(roleName);
			for (Map.Entry<Integer, RTObject> aRole : roleVecElements.entrySet()) {
				final RTObject potentialRolePlayer = aRole.getValue();
				if (potentialRolePlayer == rolePlayer) {
					retval = new RTIntegerObject(aRole.getKey().intValue());
					break;
				}
			}
			return retval;
		}
		
		private final Map<String, RTObject> rolePlayers_;
		private final Map<String, String> isRoleArrayMap_;
		private final Map<String, Map<Integer,RTObject>> roleArrayPlayers_;
		private final RTContextObject rTContext_;
	}
	
	private Map<String, RTContext> stringToContextDeclMap_;
	private Map<String, RTClass> stringToClassDeclMap_;
	private Map<String, RTRole> nameToRoleObjectMap_;
	private Map<String, RTStageProp> nameToStagePropObjectMap_;
	private Map<String, RTObject> nameToObjectDeclMap_;
	private final Map<String, String> isRoleArrayMap_;
	private RTType rTType_;
}
