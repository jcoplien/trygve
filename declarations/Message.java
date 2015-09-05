package declarations;

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

import semantic_analysis.StaticScope;
import declarations.ActualArgumentList;
import expressions.Expression;

public class Message {
	public Message(String selectorName, ActualArgumentList argumentList, long lineNumber) {
		selectorName_ = selectorName;
		argumentList_ = argumentList;
		lineNumber_ = lineNumber;
		
		// Just a default until it gets filled in Ñ avoid null ptr problems
		returnType_ = StaticScope.globalScope().lookupTypeDeclaration("void");
	}
	public String selectorName() {
		return selectorName_;
	}
	public ActualArgumentList argumentList() { 
		return argumentList_;
	}
	public long lineNumber() {
		return lineNumber_;
	}
	public String getText() {
		String argumentListString = null, selectorNameString = null;
		if (null == argumentList_) {
			argumentListString = "<no arguments>";
		} else {
			argumentListString = argumentList_.getText();
		}
		if (null == selectorName_) {
			argumentListString = "<no selector>";
		} else {
			argumentListString = selectorName_;
		}
		String retval = selectorNameString + "(" + argumentListString + ")";
		return retval;
	}
	public void addActualThisParameter(Expression objectForWhichMethodIsInvoked) {
		argumentList_.addFirstActualParameter(objectForWhichMethodIsInvoked);
	}
	
	public void setArgumentList(ActualArgumentList argumentList) {
		argumentList_ = argumentList;
	}
	
	public void setReturnType(Type returnType) {
		returnType_ = returnType;
	}
	public Type returnType() {
		return returnType_;
	}
	
	private String selectorName_;
	private ActualArgumentList argumentList_;
	private long lineNumber_;
	private Type returnType_;
}
