package info.fulloo.trygve.declarations;

import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class Message {
	public Message(String selectorName, ActualArgumentList argumentList, long lineNumber, Type enclosingMegaType) {
		selectorName_ = selectorName;
		argumentList_ = argumentList;
		lineNumber_ = lineNumber;
		enclosingMegaType_ = enclosingMegaType;
		
		// Just a default until it gets filled in Ñ avoid null ptr problems
		returnType_ = StaticScope.globalScope().lookupTypeDeclaration("void");
	}
	public String selectorName() {
		return selectorName_;
	}
	public ActualArgumentList argumentList() { 
		return argumentList_;
	}
	public long lineNumber() {
		return lineNumber_;
	}
	public String getText() {
		String argumentListString = null, selectorNameString = null;
		if (null == argumentList_) {
			argumentListString = "<no arguments>";
		} else {
			argumentListString = argumentList_.getText();
		}
		if (null == selectorName_) {
			argumentListString = "<no selector>";
		} else {
			argumentListString = selectorName_;
		}
		String retval = selectorNameString + "(" + argumentListString + ")";
		return retval;
	}
	public void addActualThisParameter(Expression objectForWhichMethodIsInvoked) {
		argumentList_.addFirstActualParameter(objectForWhichMethodIsInvoked);
	}
	
	public void setArgumentList(ActualArgumentList argumentList) {
		argumentList_ = argumentList;
	}
	
	public void setReturnType(Type returnType) {
		returnType_ = returnType;
	}
	public Type returnType() {
		return returnType_;
	}
	public Type enclosingMegaType() {
		return enclosingMegaType_;
	}
	
	private String selectorName_;
	private final Type enclosingMegaType_;
	private ActualArgumentList argumentList_;
	private long lineNumber_;
	private Type returnType_;
}
