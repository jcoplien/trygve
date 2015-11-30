package info.fulloo.trygve.parser;

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

import java.util.List;

import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class Pass4Listener extends Pass3Listener {
	public Pass4Listener(ParsingData parsingData) {
		super(parsingData);
	}
	
	@Override protected Type lookupOrCreateTemplateInstantiation(String templateName, List<String> parameterTypeNames, int lineNumber) {
		// This varies by pass. Here we first remove the instantiation, so that the
		// new one picks up the body created in Pass 3.
		final TemplateDeclaration templateDeclaration = currentScope_.lookupTemplateDeclarationRecursive(templateName);
		Type retval = null;
		
		if (null != templateDeclaration) {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(templateName);
			stringBuffer.append("<");
			int i = 0;
			for (final String parameterTypeName : parameterTypeNames) {
				stringBuffer.append(parameterTypeName);
				i++;
				if (i < parameterTypeNames.size()) {
					stringBuffer.append(",");
				}
			}
			stringBuffer.append(">");
			final String typeName = stringBuffer.toString();
			
			final StaticScope templateScope = templateDeclaration.enclosingScope();
			ClassDeclaration classDeclaration = currentScope_.lookupClassDeclarationRecursive(typeName);
			if (null == classDeclaration) {
				assert false;
			}
			if (classDeclaration.methodsHaveBodyParts() == false) {
				templateScope.undeclareType(classDeclaration.type());
				templateScope.undeclareClass(classDeclaration);
				parsingData_.currentTemplateInstantiationList().removeDeclaration(classDeclaration);
				
				retval = super.lookupOrCreateTemplateInstantiationCommon(templateName, parameterTypeNames, lineNumber);
				
				classDeclaration = currentScope_.lookupClassDeclarationRecursive(typeName);
				classDeclaration.setMethodsHaveBodyParts(true);
			} else {
				retval = classDeclaration.type();
			}
		}
		return retval;
	}
}


