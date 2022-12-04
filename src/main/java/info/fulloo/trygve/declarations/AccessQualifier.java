package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2023 James O. Coplien, jcoplien@gmail.com
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

public enum AccessQualifier {
	PublicAccess ("public"),
	PrivateAccess ("private"),
	DefaultAccess ("default"),
	UNKNOWN ("UNKNOWN");
	
	private final String clearVersion;
	private AccessQualifier(final String clear) {
		this.clearVersion = clear;
	}
	public final String asString() {
		return this.clearVersion;
	}
	public static final AccessQualifier accessQualifierFromString(final String arg) {
		for (final AccessQualifier s : AccessQualifier.values()) {
			if (s.asString().equals(arg))
				return s;
		}
		return DefaultAccess;
	}
}
