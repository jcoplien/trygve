package declarations;

import java.util.ArrayList;
import java.util.List;

import declarations.Declaration.TemplateDeclaration;
import declarations.Declaration.TypeParameter;
import declarations.Type.ClassType;

public class TemplateInstantiationInfo {
	public TemplateInstantiationInfo(TemplateDeclaration templateDecl) {
		super();
		actualParameters_ = new ArrayList<Type>();
		templateDeclaration_ = templateDecl;
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
	public Type classSubstitionForTemplateTypeNamed(final String templateTypeName) {
		final TypeParameter formalTypeParam = templateDeclaration_.typeParameterNamed(templateTypeName);
		final int parameterPositionOfFormalParam = formalTypeParam.argumentPosition();
		return this.parameterAtIndex(parameterPositionOfFormalParam);
	}
	
	private List<Type> actualParameters_;
	private ClassType classType_;
	private final TemplateDeclaration templateDeclaration_;
}
