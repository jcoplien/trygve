package info.fulloo.trygve.declarations;

import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public interface TypeDeclaration {
	public int lineNumber();
	public String name();
	public StaticScope enclosingScope();
	public StaticScope enclosedScope();
	public Type type();
	public ObjectDeclaration lookupStaticObjectDeclaration(String name);
	public void declareStaticObject(ObjectDeclaration decl);
}
