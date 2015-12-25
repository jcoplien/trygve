package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 1.1
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

import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class Message {
	public Message(final String selectorName, final ActualArgumentList argumentList,
			final long lineNumber, final Type enclosingMegaType) {
		selectorName_ = selectorName;
		argumentList_ = argumentList;
		lineNumber_ = lineNumber;
		enclosingMegaType_ = enclosingMegaType;
		
		// Just a default until it gets filled in - avoid null ptr problems
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
			argumentListString = "()";
		} else {
			// If we ever need self: final Expression zerothArgument = argumentList_.parameterAtPosition(0);
			final ActualArgumentList justTheArgs = new ActualArgumentList();
			for (int i = 1; i < argumentList_.count(); i++) {
				justTheArgs.addActualArgument(argumentList_.parameterAtPosition(i));
			}
			argumentListString = justTheArgs.getText();
		}
		if (null == selectorName_) {
			selectorNameString = "<no selector>";
		} else {
			selectorNameString = selectorName_;
		}
		final String retval = selectorNameString + argumentListString;
		return retval;
	}
	public void addActualThisParameter(final Expression objectForWhichMethodIsInvoked) {
		final Type type = objectForWhichMethodIsInvoked.type();
		if (null != type) {		// error stumble check
			if (type.name().equals("Class")) {
				;	// add no parameter
			} else {
				argumentList_.addFirstActualParameter(objectForWhichMethodIsInvoked);
			}
		}
	}
	
	public void setArgumentList(final ActualArgumentList argumentList) {
		argumentList_ = argumentList;
	}
	
	public void setReturnType(final Type returnType) {
		returnType_ = returnType;
	}
	public Type returnType() {
		return returnType_;
	}
	public Type enclosingMegaType() {
		return enclosingMegaType_;
	}
	
	private String selectorName_;
	private final Type enclosingMegaType_;
	private ActualArgumentList argumentList_;
	private long lineNumber_;
	private Type returnType_;
}
