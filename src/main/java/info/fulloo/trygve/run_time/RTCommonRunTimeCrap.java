package info.fulloo.trygve.run_time;

import java.util.Map;

import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;

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
	@Override public boolean isEqualTo(Object another) {
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
	@Override public RTObject logicalAnd(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject logicalOr(final RTObject other) {
		assert false;
		return null;
	}
	@Override public RTObject logicalXor(final RTObject other) {
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
	@Override public RTObject unaryLogicalNegation() {
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
	@Override public RTObject toThePowerOf(RTObject other) {
		assert false;
		return null;
	}
	
	// I'm a little unhappy that these are copy-pasted. FIXME.
	@Override public void enlistAsRolePlayerForContext(final String roleName, RTContextObject contextInstance) {
		assert false;
	}
	@Override public void unenlistAsRolePlayerForContext(final String roleName, RTContextObject contextInstance) {
		assert false;
	}
	@Override public void enlistAsStagePropPlayerForContext(final String stagePropName, RTContextObject contextInstance) {
		assert false;
	}
	@Override public void unenlistAsStagePropPlayerForContext(final String stagePropName, RTContextObject contextInstance) {
		assert false;
	}
	@Override public RTObject performUnaryOpOnObjectNamed(final String idName, String operator, PreOrPost preOrPost_) {
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
	@Override public long referenceCount() {
		return referenceCount_;
	}
	@Override public boolean equals(final RTObject other) {
		assert false;
		return false;
	}
	
	protected int referenceCount_;
}
