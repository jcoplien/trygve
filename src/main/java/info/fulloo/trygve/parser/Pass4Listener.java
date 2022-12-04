package info.fulloo.trygve.parser;

/*
 * Trygve IDE 4.3
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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

import info.fulloo.trygve.configuration.ConfigurationOptions;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class Pass4Listener extends Pass3Listener {
	public Pass4Listener(final ParsingData parsingData) {
		super(parsingData);
		printProductionsDebug = ConfigurationOptions.tracePass4();
	}
	
	@Override public void exitAbelian_atom(KantParser.Abelian_atomContext ctx) {
		//  | NEW JAVA_ID type_list '(' argument_list ')'
		if (null != ctx.NEW() && null != ctx.type_list() && null != ctx.argument_list()) {
			// Instantiate the template here. Normally, templates are instantiated
			// in the production:
			//
			//    type_name: JAVA_ID type_list
			//
			// but here the grammar for NEW catches NEW operations on template
			// types explicitly. So we can't count on the above production to
			// turn the template into a class. We here replicate the logic
			// for that production:
			
			final ArrayList<String> typeNameList = parsingData_.popTypeNameList();
			final Type type = commonTemplateInstantiationHandling(ctx.JAVA_ID().toString(), ctx.getStart(),
					typeNameList);
			if (null == type) {
				errorHook5p2(ErrorIncidenceType.Internal, ctx.getStart(),
						"No Type returned from commonTemplateInstantiationHandling: ",
						"instantiating a type in an expression: `",
						ctx.getText(),
						"'."
						);
			}
			
			// Push it back so the Pass 1 logic below can use it...
			parsingData_.pushTypeNameList(typeNameList);
		}
		
		// Same as Pass 1 logic:
		super.exitAbelian_atom(ctx);
	}
	
	@Override protected Type lookupOrCreateTemplateInstantiation(final String templateName,
			final List<String> parameterTypeNames, final Token token) {
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
			InterfaceDeclaration interfaceDeclaration = currentScope_.lookupInterfaceDeclarationRecursive(typeName);
			if (null == classDeclaration && null == interfaceDeclaration) {
				assert false;
			}
			if (null != classDeclaration) {
				if (classDeclaration.methodsHaveBodyParts() == false) {
					templateScope.undeclareType(classDeclaration.type());
					templateScope.undeclareClass(classDeclaration);
					parsingData_.currentTemplateInstantiationList().removeDeclaration(classDeclaration);
				
					retval = super.lookupOrCreateTemplateInstantiationCommon(templateName, parameterTypeNames, token);
				
					classDeclaration = currentScope_.lookupClassDeclarationRecursive(typeName);
					classDeclaration.setMethodsHaveBodyParts(true);
				} else {
					retval = classDeclaration.type();
				}
			} else if (null != interfaceDeclaration) {
				if (interfaceDeclaration.methodsHaveBodyParts() == false) {
					templateScope.undeclareType(interfaceDeclaration.type());
					templateScope.undeclareInterface(interfaceDeclaration);
					parsingData_.currentTemplateInstantiationList().removeDeclaration(interfaceDeclaration);
				
					retval = super.lookupOrCreateTemplateInstantiationCommon(templateName, parameterTypeNames, token);
				
					interfaceDeclaration = currentScope_.lookupInterfaceDeclarationRecursive(typeName);
					interfaceDeclaration.setMethodsHaveBodyParts(true);
				} else {
					retval = interfaceDeclaration.type();
				}
			} else {
				assert false;	// ???  logic error
			}
		}
		return retval;
	}
	@Override public void errorHook5p2SpecialHook(final ErrorIncidenceType errorType, final Token token, final String s1, final String s2, final String s3, final String s4) {
		ErrorLogger.error(errorType, token, s1, s2, s3, s4);
	}
	@Override public void errorHook5p3(final ErrorIncidenceType errorType, final Token t, final String s1, final String s2, final String s3, final String s4) {
		;		// p3 only
	}
}


