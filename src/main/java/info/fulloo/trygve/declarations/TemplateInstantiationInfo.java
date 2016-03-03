package info.fulloo.trygve.declarations;

/*
 * Trygve IDE 1.6
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

import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Declaration.TypeParameter;
import info.fulloo.trygve.declarations.Type.ClassType;

public class TemplateInstantiationInfo {
	public TemplateInstantiationInfo(final TemplateDeclaration templateDecl, final String typeName) {
		super();
		actualParameters_ = new ArrayList<Type>();
		templateDeclaration_ = templateDecl;
		typeName_ = typeName;
	}
	public void addTypeParameter(final Type typeParameter) {
		actualParameters_.add(typeParameter);
	}
	public void add(final Type typeParameter) {
		this.addTypeParameter(typeParameter);
	}
	public void setClassType(final ClassType classType) {
		classType_ = classType;
	}
	public Type parameterAtIndex(final int i) {
		final int size = actualParameters_.size();
		assert i < size;
		return actualParameters_.get(i);
	}
	public Type get(final int i) {
		return parameterAtIndex(i);
	}
	public ClassType classType() {
		return classType_;
	}
	public int size() {
		return actualParameters_.size();
	}
	public Type classSubstitionForTemplateTypeNamed(final String templateTypeName) {
		final TypeParameter formalTypeParam = templateDeclaration_.typeParameterNamed(templateTypeName);
		final int parameterPositionOfFormalParam = formalTypeParam.argumentPosition();
		final int numberOfParameters = this.actualParameters_.size();
		return this.parameterAtIndex(numberOfParameters - parameterPositionOfFormalParam - 1);
	}
	public Type classSubstitionForFormalParameterNamed(final String templateTypeName) {
		// This is a bit kludgy...  FIXME?
		Type retval = null;
		for (final Type aType : actualParameters_) {
			if (aType.name().equals(templateTypeName)) {
				retval = aType;
				break;
			}
		}
		return retval;
	}
	public final String templateName() {
		return templateDeclaration_.name();
	}
	public final TemplateDeclaration templateDeclaration() {
		return templateDeclaration_;
	}
	public final String fullTypeName() {
		return typeName_;
	}
	
	private final List<Type> actualParameters_;
	private ClassType classType_;
	private final TemplateDeclaration templateDeclaration_;
	private final String typeName_;
}
