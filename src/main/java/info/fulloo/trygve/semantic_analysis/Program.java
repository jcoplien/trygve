package info.fulloo.trygve.semantic_analysis;

import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.expressions.Expression;

public class Program {
	public Program(Expression main, TypeDeclarationList theRest, TypeDeclarationList templateInstantationList_) {
		main_ = main;
		theRest_ = theRest;
		templateInstantiations_ = templateInstantationList_;
		program_ = this;
	}
	public static Program program() {
		return program_;
	}
	public Expression main() {
		return main_;
	}
	public TypeDeclarationList templateInstantiations() {
		return templateInstantiations_;
	}
	public TypeDeclarationList theRest() {
		return theRest_;
	}
	private final Expression main_;
	private final TypeDeclarationList theRest_, templateInstantiations_;
	private static Program program_ = null;
}
