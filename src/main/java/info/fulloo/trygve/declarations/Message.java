package info.fulloo.trygve.declarations;

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

import java.util.ArrayList;
import java.util.List;

import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Type.RoleType;
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
	
	public void replaceActualThisParameter(final Expression objectForWhichMethodIsInvoked) {
		final Type type = objectForWhichMethodIsInvoked.type();
		if (null != type) {		// error stumble check
			if (type.name().equals("Class")) {
				assert false;	// this is a leftover from addActualThisParameter, shouldn't happen
			} else {
				argumentList_.replaceFirstActualParameter(objectForWhichMethodIsInvoked);
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
	public boolean validInRunningEnviroment(final MethodDeclaration targetMethodDeclaration) {
		// Normal argument checking is done on the basis
		// of signature mapping. Let's assume that matches.
		// One of the actual parameters may be a Role type,
		// passed into a formal parameter that matches. If
		// the method executes in a different Context (without
		// access to the Role methods) then the call is
		// meaningless. Check that here.
		boolean retval = true;
		// final StaticScope targetMethodScope = targetMethodDeclaration.enclosedScope();
		// final Type targetType = Expression.nearestEnclosingMegaTypeOf(targetMethodScope);
		final FormalParameterList formalParameters = targetMethodDeclaration.formalParameterList();
		
		final int numberOfActualParameters = argumentList_.count();
		
		// We start at 1 rather than 0 so we can discount "this"
		for (int argNumber = 1; argNumber < numberOfActualParameters; argNumber++) {
			final Expression actualParameter = argumentList_.parameterAtPosition(argNumber);
			final Type typeOfActualParameter = actualParameter.type(),
					   baseTypeOfActualParameter = actualParameter.baseType();
			RoleType roleType = null;
			if (typeOfActualParameter instanceof RoleType) {	// or, of course, by implication StageProp, too
				roleType = (RoleType) typeOfActualParameter;
			} else if (baseTypeOfActualParameter instanceof RoleType) {
				roleType = (RoleType) baseTypeOfActualParameter;
			}
			
			if (null != roleType) {
				// Then we're calling from a Context - we have to be,
				// or no RoleTypes would be available. Check to see if
				// the receiving script's argument depends on matching
				// any Role methods. If so, make sure that the receiving
				// object is guaranteed to be in the same Context as
				// the caller
				final Declaration correspondingFormalParameter = formalParameters.parameterAtPosition(argNumber);
				final Type correspondingFormalParameterType = correspondingFormalParameter.type();
				
				if (correspondingFormalParameterType instanceof RoleType) {
					// We need not check Role types, because if it's a Role type
					// it must be accessible to the caller and therefore within the
					// same context
					continue;
				} else {
					// Requires methods (which are a superset of the published
					// methods) are all Ok. Other Role methods don't make the cut.
					// The roleType must support all methods of the formal parameter,
					// by using only those methods in its "requires" section
					final List<MethodDeclaration> formalParamMethods =
							null == correspondingFormalParameterType? new ArrayList<MethodDeclaration>():
								(null == correspondingFormalParameterType.enclosedScope()?
										new ArrayList<MethodDeclaration>():
										correspondingFormalParameterType.enclosedScope().methodDeclarations()
								);
						
					for (final MethodDeclaration aMethodOfTheFormalParameter : formalParamMethods) {
						if (aMethodOfTheFormalParameter.isAConstructor()) {
							continue;
						} else {
							final MethodSignature parametersSignature = aMethodOfTheFormalParameter.signature();
							final MethodSignature roleAnswer = roleType.associatedDeclaration().lookupRequiredMethodSignatureDeclaration(parametersSignature);
							if (null == roleAnswer) {
								// Couldn't find it — game over
								retval = false;
								break;
							}
						}
					}
					if (false == retval) break;
				}
			}
		}
		return retval;
	}
	
	private String selectorName_;
	private final Type enclosingMegaType_;
	private ActualArgumentList argumentList_;
	private long lineNumber_;
	private Type returnType_;
}
