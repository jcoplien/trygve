package declarations;

import java.util.ArrayList;
import java.util.List;

import declarations.Type.ClassType;

public class TemplateInstantiationInfo {
	public TemplateInstantiationInfo() {
		super();
		actualParameters_ = new ArrayList<Type>();
	}
	public void addTypeParameter(Type typeParameter) {
		actualParameters_.add(typeParameter);
	}
	public void add(Type typeParameter) {
		this.addTypeParameter(typeParameter);
	}
	public void setClassType(ClassType classType) {
		classType_ = classType;
	}
	public Type parameterAtIndex(int i) {
		assert i < actualParameters_.size();
		return actualParameters_.get(i);
	}
	public Type get(int i) {
		return parameterAtIndex(i);
	}
	public ClassType classType() {
		return classType_;
	}
	public int size() {
		return actualParameters_.size();
	}
	
	private List<Type> actualParameters_;
	private ClassType classType_;
}
