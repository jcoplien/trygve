package info.fulloo.trygve.run_time;

/*
 * Trygve IDE
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

import java.util.Map;

import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;

public interface RTObject extends RTStackable {
	public RTObject getObject(final String name);
	public void addObjectDeclaration(final String objectName, final RTType type);
	public Map<String, RTType> objectDeclarations();
	public void setObject(final String objectName, final RTObject object);
	public Map<String, RTObject> objectMembers();
	public RTType rTType();
	public boolean isEqualTo(final Object another);
	public boolean gt(final RTObject another);
	public RTObject plus(final RTObject other);
	public RTObject minus(final RTObject other);
	public RTObject times(final RTObject other);
	public RTObject divideBy(final RTObject other);
	public RTObject modulus(final RTObject other);
	public RTObject unaryPlus();
	public RTObject unaryMinus();
	public RTObject unaryLogicalNegation();
	public RTObject preIncrement();
	public RTObject postIncrement();
	public RTObject preDecrement();
	public RTObject postDecrement();
	public RTObject performUnaryOpOnObjectNamed(final String idName, final String operator, final PreOrPost preOrPost_);
	public RTObject toThePowerOf(final RTObject exponent);
	public RTObject dup();
	public void incrementReferenceCount();
	public void decrementReferenceCount();
	public long referenceCount();
	public void enlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance);
	public void unenlistAsRolePlayerForContext(final String roleName, final RTContextObject contextInstance);
	public boolean equals(final RTObject other);
	public int hashCode();
}
