package run_time;

import java.util.Map;
import run_time.RTObjectCommon.RTContextObject;
import expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;

public class RTCommonRunTimeCrap implements RTObject {
	public RTCommonRunTimeCrap() {
		referenceCount_ = 1;
	}
	@Override public RTObject getObject(String name) {
		assert false;
		return null;
	}
	@Override public void addObjectDeclaration(String objectName, RTType type) {
		assert false;
	}
	@Override public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}
	@Override public void setObject(String objectName, RTObject object) {
		// Maybe someday we upgrade arrays to have Map semantics
		// and do everything with String selectors
		assert false;
	}
	@Override public Map<String, RTObject> objectMembers() {
		assert false;
		return null;
	}
	@Override public RTType rTType() {
		assert false;
		return null;
	}
	@Override public boolean equals(RTObject another) {
		assert false;
		return false;
	}
	@Override public boolean gt(RTObject another) {
		assert false;
		return false;
	}
	@Override public RTObject plus(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject minus(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject times(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject divideBy(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject modulus(RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject unaryPlus() {
		assert false;
		return null;
	}
	@Override public RTObject unaryMinus() {
		assert false;
		return null;
	}
	@Override public RTObject preIncrement() {
		assert false;
		return null;
	}
	@Override public RTObject postIncrement() {
		assert false;
		return null;
	}
	@Override public RTObject preDecrement() {
		assert false;
		return null;
	}
	@Override public RTObject postDecrement() {
		assert false;
		return null;
	}
	@Override public RTObject toThePowerOf(RTObject other) { assert false; return null; }
	
	// I'm a little unhappy that these are copy-pasted. FIXME.
	@Override public void enlistAsRolePlayerForContext(String roleName, RTContextObject contextInstance) {
		assert false;
	}
	@Override public void unenlistAsRolePlayerForContext(String roleName, RTContextObject contextInstance) {
		assert false;
	}
	@Override public RTObject performUnaryOpOnObjectNamed(String idName, String operator, PreOrPost preOrPost_) {
		assert false;	 // meaningless for arrays
		return null;
	}
	@Override public RTObject dup() {
		assert false;
		return null;
	}
	@Override public void incrementReferenceCount() {
		referenceCount_++;
	}
	@Override public void decrementReferenceCount() {
		--referenceCount_;
	}
	@Override
	public long referenceCount() {
		return referenceCount_;
	}
	
	private int referenceCount_;
}
