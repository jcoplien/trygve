package info.fulloo.trygve.declarations;

import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.TemplateParameterType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.mylibrary.SimpleList;


public class FormalParameterList extends ParameterListCommon implements ActualOrFormalParameterList {
	public FormalParameterList() {
		super(new SimpleList());
	}
	public void addFormalParameter(Declaration parameter) {
		insertAtStart(parameter);
	}
	public ObjectDeclaration parameterAtPosition(int i) {
		return (ObjectDeclaration) parameterAtIndex(i);
	}
	public boolean alignsWith(ActualOrFormalParameterList pl) {
		return FormalParameterList.alignsWithParameterListIgnoringParam(this, pl, null);
	}
	public static boolean alignsWithParameterListIgnoringParam(ActualOrFormalParameterList pl1, ActualOrFormalParameterList pl2, String paramToIgnore) {
		boolean retval = true;
		final int myCount = pl1.count();
		if (null == pl2) {
			if (myCount != 0) {
				retval = false;
			} else {
				// Redundant, but clear
				retval = true;
			}
		} else {
			final int plCount = pl2.count();
			if (plCount != myCount) {
				retval = false;
			} else {
				for (int i = 0; i < plCount; i++) {
					final String plName = pl2.nameOfParameterAtPosition(i);
					if (null != plName && null != paramToIgnore && plName.equals(paramToIgnore)) {
						continue;
					}
					final Type plt = pl2.typeOfParameterAtPosition(i);
					final Type myt = pl1.typeOfParameterAtPosition(i);
					if (plt.enclosedScope() == myt.enclosedScope()) {
						continue;
					} else if (plt.isBaseClassOf(myt)) {
						continue;
					} else {
						retval = false;
						break;
					}
				}
			}
		}
		return retval;
	}
	
	@Override public Type typeOfParameterAtPosition(int i) {
		return parameterAtPosition(i).type();
	}
	@Override public String nameOfParameterAtPosition(int i) {
		return parameterAtPosition(i).name();
	}
	@Override public ActualOrFormalParameterList mapTemplateParameters(TemplateInstantiationInfo templateTypes) {
		// templateTypes can be null if we're processing a lookup in an actual template
		final FormalParameterList retval = new FormalParameterList();
		for (int i = count() - 1; i >= 0; --i) {
			final ObjectDeclaration aParameter = parameterAtPosition(i);
			final Type typeOfParameter = typeOfParameterAtPosition(i);
			
			// This method's scope has been been given a templateTypes
			// list only if that scope corresponds to an instantiated
			// class. We can get here for the lookup in the initial template,
			// in which case templateTypes.size() == 0. 
			if (null != typeOfParameter && typeOfParameter instanceof TemplateParameterType && null != templateTypes && templateTypes.size() > 0) {
				assert templateTypes.size() > i - 1;
				final ObjectDeclaration substituteDecl = new ObjectDeclaration(
						aParameter.name(), templateTypes.get(i - 1), aParameter.lineNumber());
				retval.addFormalParameter(substituteDecl);
			} else if (null != typeOfParameter && typeOfParameter instanceof TemplateType && null != templateTypes) {
				final ObjectDeclaration substituteDecl = new ObjectDeclaration(
						aParameter.name(), templateTypes.classType(), aParameter.lineNumber());
				retval.addFormalParameter(substituteDecl);
			} else {
				retval.addFormalParameter(aParameter);
			}
		}
		return retval;
	}
}
