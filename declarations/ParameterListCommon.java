package declarations;

import java.util.List;

import mylibrary.SimpleList;

public abstract class ParameterListCommon implements ActualOrFormalParameterList  {
	public ParameterListCommon(SimpleList formalParameters) {
		parameters_ = formalParameters;
	}
	public int count() {
		return parameters_.count();
	}
	public Object parameterAtIndex(int i) {
		return parameters_.objectAtIndex(i);
	}
	public void insertAtStart(Object parameter) {
		parameters_.insertAtStart(parameter);
	}
	public void addArgument(Object parameter) {
		parameters_.add(parameter);
	}
	public Object argumentAtPosition(int i) {
		return (Object)parameterAtIndex(i);
	}
	// NOTE: This method is here just for genericity in
	// implementing the ActualOrFormalParameterList interface
	@Override public abstract Type typeOfParameterAtPosition(int i);
	@Override public abstract String nameOfParameterAtPosition(int i);
	@Override public ActualOrFormalParameterList mapTemplateParameters(TemplateInstantiationInfo templateTypes) {
		return this;
	}
	
	private SimpleList parameters_;
}
