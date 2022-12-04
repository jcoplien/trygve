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

import info.fulloo.trygve.declarations.Declaration.ErrorDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.RoleType;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.declarations.Type.VarargsType;
import info.fulloo.trygve.mylibrary.SimpleList;


public class FormalParameterList extends ParameterListCommon {
	public FormalParameterList() {
		super(new SimpleList());
	}
	
	public void addFormalParameter(final Declaration parameter) {
		insertAtStart(parameter);
	}
	
	public Declaration parameterAtPosition(final int i) {
		final Object backFromParameterAtIndex = parameterAtIndex(i);
		Declaration retval = null;
		if (null != backFromParameterAtIndex) {
			retval = (Declaration)backFromParameterAtIndex;
		}
		return retval;
	}
	
	public boolean alignsWith(final ActualOrFormalParameterList pl) {
		return FormalParameterList.alignsWithParameterListIgnoringParamCommon(this, pl, null, false, -1);
	}
	
	public boolean alignsWithUsingConversion(final ActualOrFormalParameterList pl) {
		return FormalParameterList.alignsWithParameterListIgnoringParamCommon(this, pl, null, true, -1);
	}
	
	public static boolean alignsWithParameterListIgnoringParamNamed(final ActualOrFormalParameterList formals,
			final ActualOrFormalParameterList actuals, final String paramToIgnore, final boolean conversionAllowed) {
		return FormalParameterList.alignsWithParameterListIgnoringParamCommon(formals,
				actuals, paramToIgnore, conversionAllowed, -1);
	}
	
	public static boolean alignsWithParameterListIgnoringParamNamedWithRequiresCheck(final ActualOrFormalParameterList formals,
			final ActualOrFormalParameterList actuals, final String paramToIgnore, final boolean conversionAllowed) {
		boolean retval = FormalParameterList.alignsWithParameterListIgnoringParamCommon(formals,
				actuals, paramToIgnore, conversionAllowed, -1);
		if (false == retval) {
			final int formalsCount = formals.count();
			if (null == actuals) {
				if (formalsCount != 0) {
					retval = false;
				} else {
					// Redundant, but clear
					retval = true;
				}
			} else {
				retval = true;
				final int actualsCount = actuals.count();
				if (actualsCount != formalsCount && false == formals.containsVarargs()) {
					retval = false;
				} else {
					for (int i = 0; i < actualsCount; i++) {
						final String pl1Name = formals.nameOfParameterAtPosition(i),
								     pl2Name = actuals.nameOfParameterAtPosition(i);
						if (null != pl2Name && null != paramToIgnore && pl2Name.equals(paramToIgnore)) {
							continue;
						}
						
						// We really should be a bit more dutiful about knowing whether it's pl1 or
						// pl2 we're checking. But it's almost always "this" and since it's a
						// reserved word, it won't be aliased with a user variable
						if (null != pl1Name && null != paramToIgnore && pl1Name.equals(paramToIgnore)) {
							continue;
						}
						
						final Type actualsType = actuals.typeOfParameterAtPosition(i);
						final Type formalsType = formals.typeOfParameterAtPosition(i);
						final String actualsTypePathname = null == actualsType? "$wrong$": actualsType.pathName();	// these two are mainly
						final String formalsTypePathname = null == formalsType? "!Wrong!": formalsType.pathName();	//	for debugging
						if (null == actualsType || null == formalsType) {
							retval = false;
							break;
						} else if (formalsType instanceof VarargsType) {
							retval = true;
							break;
						} else if (actualsTypePathname.equals(formalsTypePathname)) {
							// This is a big of a kludge but was necessary to get things
							// to work comparing two different String instantiations. FIXME?
							continue;
						} else if (actualsType.enclosedScope() == formalsType.enclosedScope()) {
							continue;
						} else if (formalsType.isBaseClassOf(actualsType)) {
							continue;
						} else if (conversionAllowed) {
							retval = formalsType.canBeConvertedFrom(actualsType);
							if (false == retval && actualsType instanceof RoleType && formalsType instanceof ClassType) {
								final ClassType formalsClassType = (ClassType) formalsType;
								retval = formalsClassType.canBeConvertedFromRole((RoleType)actualsType);
							}
							if (false == retval) break;
						} else {
							retval = false;
							break;
						}
					}
				}
			}
		}
		return retval;
	}
	
	public static boolean alignsWithParameterListIgnoringParamAtPosition(final ActualOrFormalParameterList pl1,
			final ActualOrFormalParameterList pl2, final int paramToIgnore, final boolean conversionAllowed) {
		return FormalParameterList.alignsWithParameterListIgnoringParamCommon(pl1,
				pl2, null, conversionAllowed, paramToIgnore);
	}
	
	public static boolean alignsWithParameterListIgnoringParamCommon(final ActualOrFormalParameterList formals,
			final ActualOrFormalParameterList actuals, final String paramToIgnore, final boolean conversionAllowed,
			final int parameterPositionToIgnore) {
		boolean retval = true;
		final int formalsCount = formals.count();
		if (null == actuals) {
			if (formalsCount != 0) {
				retval = false;
			} else {
				// Redundant, but clear
				retval = true;
			}
		} else {
			final int actualsCount = actuals.count();
			if (actualsCount != formalsCount && false == formals.containsVarargs()) {
				retval = false;
			} else {
				for (int i = 0; i < actualsCount; i++) {
					final String pl1Name = formals.nameOfParameterAtPosition(i),
							     pl2Name = actuals.nameOfParameterAtPosition(i);
					
					// We really should be a bit more dutiful about knowing whether it's pl1 or
					// pl2 we're checking. But it's almost always "this" and since it's a
					// reserved word, it won't be aliased with a user variable
					if (null != pl1Name && null != paramToIgnore && pl1Name.equals(paramToIgnore)) {
						continue;
					}
					if (0 <= parameterPositionToIgnore && i == parameterPositionToIgnore) {
						continue;
					}
					
					final Type actualsType = actuals.typeOfParameterAtPosition(i);
					final Type formalsType = formals.typeOfParameterAtPosition(i);
					final String actualsTypePathname = null == actualsType? "$wrong$": actualsType.pathName();	// these two are mainly
					final String formalsTypePathname = null == formalsType? "!Wrong!": formalsType.pathName();	//	for debugging
					if (null == actualsType || null == formalsType) {
						retval = false;
						break;
					} else if (formalsType instanceof VarargsType) {
						retval = true;
						break;
					} else if (actualsTypePathname.equals(formalsTypePathname)) {
						// This is a big of a kludge but was necessary to get things
						// to work comparing two different String instantiations. FIXME?
						continue;
					} else if (actualsType.enclosedScope() == formalsType.enclosedScope()) {
						continue;
					} else if (formalsType.isBaseClassOf(actualsType)) {
						continue;
					} else if (conversionAllowed) {
						retval = formalsType.canBeConvertedFrom(actualsType);
					} else {
						retval = false;
						break;
					}
				}
			}
		}
		return retval;
	}
	
	public static boolean alignsWithParameterListIgnoringRoleStuff(final ActualOrFormalParameterList pl1,
			final ActualOrFormalParameterList pl2, final boolean conversionIsAllowed) {
		boolean retval = true;
		int i = 0, j = 0;
		final int pl1Count = pl1.count(),
				  pl2Count = (null == pl2)? 0: pl2.count();
		if (null == pl2) {
			if (pl1Count != 0) {
				retval = false;
			} else {
				// Redundant, but clear
				retval = true;
			}
		} else {
			while (i < pl1Count || j < pl2Count) {
				boolean testFlag = true;
				final String pl1Name, pl2Name;
				
				if (i < pl1Count) {
					pl1Name = pl1.nameOfParameterAtPosition(i);
				} else {
					pl1Name = " abc ";
				}
				if (j < pl2Count) {
					pl2Name = pl2.nameOfParameterAtPosition(j);
				} else {
					pl2Name = " xyz ";
				}
				if (pl1Name.equals("this") || pl1Name.equals("t$this") ||
						pl1Name.equals("current$context") || pl1Name.equals("current$role")) {
					i++;
					testFlag = false;
				}
				if (pl2Name.equals("this") || pl2Name.equals("t$this") ||
						pl2Name.equals("current$context") || pl2Name.equals("current$role")) {
					j++;
					testFlag = false;
				}
				if (i == pl1Count && j == pl2Count) {
					break;
				}
				if (testFlag) {
					if (i >= pl1Count) {
						retval = false;
						break;
					} else if (j >= pl2Count) {
						retval = false;
						break;
					}
					final Type plt = pl2.typeOfParameterAtPosition(j);
					final Type myt = pl1.typeOfParameterAtPosition(i);
					
					if (null != plt && null != myt) {
						if (plt.enclosedScope() == myt.enclosedScope()) {
							i++; j++;
						} else if (plt.isBaseClassOf(myt)) {
							i++; j++;
						} else if (conversionIsAllowed && plt.canBeConvertedFrom(myt)) {
							i++; j++;
						} else {
							retval = false;
							break;
						}
					} else if (null == plt && null == myt) {
						// don't know what this means, should probably be an error
						i++; j++;
					} else {
						retval = false;
						break;
					}
				}
			}
		}
		
		if (i != pl1Count || j != pl2Count) {
			retval = false;
		}
		
		return retval;
	}
	
	@Override public Type typeOfParameterAtPosition(final int i) {
		return parameterAtPosition(i).type();
	}
	
	@Override public String nameOfParameterAtPosition(final int i) {
		return parameterAtPosition(i).name();
	}
	
	@Override public ActualOrFormalParameterList mapTemplateParameters(final TemplateInstantiationInfo templateTypes) {
		// templateTypes can be null if we're processing a lookup in an actual template
		final FormalParameterList retval = new FormalParameterList();
		for (int i = count() - 1; i >= 0; --i) {
			final Declaration aParameter = parameterAtPosition(i);
			final Type typeOfParameter = typeOfParameterAtPosition(i);
			
			// This method's scope has been been given a templateTypes
			// list only if that scope corresponds to an instantiated
			// class. We can get here for the lookup in the initial template,
			// in which case templateTypes.size() == 0. 
			if (null != typeOfParameter && typeOfParameter instanceof TemplateParameterType && null != templateTypes && templateTypes.size() > 0) {
				final int templateTypesSize = templateTypes.size();
				if (templateTypesSize <= i - 1) {
					return null;
					// assert templateTypesSize > i - 1;
				}
				final ObjectDeclaration substituteDecl = new ObjectDeclaration(
						aParameter.name(), templateTypes.get(i - 1), aParameter.token());
				retval.addFormalParameter(substituteDecl);
			} else if (null != typeOfParameter && typeOfParameter instanceof TemplateType && null != templateTypes) {
				final ObjectDeclaration substituteDecl = new ObjectDeclaration(
						aParameter.name(), templateTypes.classType(), aParameter.token());
				retval.addFormalParameter(substituteDecl);
			} else {
				retval.addFormalParameter(aParameter);
			}
		}
		return retval;
	}
	
	@Override public String getText() {
		final StringBuffer stringBuffer = new StringBuffer();
		final int numberOfParameters = this.count();
		stringBuffer.append("(");
		if (numberOfParameters == 0) {
			stringBuffer.append(")");
		} else for (int i = 0; i < numberOfParameters; i++) {
			final Type argumentType = this.typeOfParameterAtPosition(i);
			final Declaration argument = this.parameterAtPosition(i);
			stringBuffer.append(argumentType.name());
			stringBuffer.append(" ");
			stringBuffer.append(argument.name());
			if (i == numberOfParameters - 1) {
				stringBuffer.append(")");
			} else {
				stringBuffer.append(", ");
			}
		}
		return stringBuffer.toString();
	}
	
	public String selflessGetText() {
		final StringBuffer stringBuffer = new StringBuffer();
		final int numberOfParameters = this.count();
		stringBuffer.append("(");
		if (numberOfParameters == 1) {
			stringBuffer.append(")");
		} else for (int i = 1; i < numberOfParameters; i++) {
			final Type argumentType = this.typeOfParameterAtPosition(i);
			final Declaration argument = this.parameterAtPosition(i);
			if (argument instanceof ErrorDeclaration) {
				stringBuffer.append("Error)");
			} else {
				stringBuffer.append(argumentType.name());
				stringBuffer.append(" ");
				stringBuffer.append(argument.name());
				if (i == numberOfParameters - 1) {
					stringBuffer.append(")");
				} else {
					stringBuffer.append(", ");
				}
			}
		}
		return stringBuffer.toString();
	}
	
	@Override public int hashCode() {
		final int numberOfPositions = this.count();
		final String middlePosition = numberOfPositions > 0? nameOfParameterAtPosition(numberOfPositions / 2): "rumplestiltskin";
		final int middlePositionLength = middlePosition.length();
		return (int)numberOfPositions * 37 +
				((middlePositionLength > 0)?
						(int)middlePosition.charAt(middlePositionLength / 2):
						41);
	}
	
	public boolean equals(final Object o) {
		boolean retval = true;
		if (o instanceof FormalParameterList) {
			final FormalParameterList other = (FormalParameterList)o;
			int myNumberOfParameters = count(), otherNumberOfParameters = other.count();
			if (myNumberOfParameters == otherNumberOfParameters) {
				for (int i = 0; i < myNumberOfParameters; i++) {
					final String myName = this.nameOfParameterAtPosition(i), otherName = other.nameOfParameterAtPosition(i);
					if (myName.equals(otherName)) {
						final Type myType = this.typeOfParameterAtPosition(i), otherType = other.typeOfParameterAtPosition(i);
						if (false == myType.pathName().equals(otherType.pathName())) {
							retval = false;
							break;
						}
					} else {
						retval = false;
						break;
					}
				}
			}
		}
		return retval;
	}
	
	public int userParameterCount() {
		// Returns the number of total parameters less the declaration
		// for this and current$context. Caller usually checks for zero / non-zero
		int retval = super.count();
		if (1 == retval && this.parameterAtPosition(0).name().equals("this")) {
			retval = 0;
		} else if (2 == retval && this.parameterAtPosition(0).name().equals("current$context") &&
				this.parameterAtPosition(0).name().equals("this")) {
			retval = 0;
		} else {
			retval -= 1;
		}
		assert retval >= 0;
		return retval;
	}
	
	@Override public boolean isError() {
		boolean retval = false;
		for (int i = 0; i < count(); i++) {
			final Declaration paramDecl = parameterAtPosition(i);
			assert paramDecl instanceof ObjectDeclaration || paramDecl.isError();
			if (paramDecl.isError()) {
				retval = true;
				break;
			}
		}
		return retval;
	}
}
