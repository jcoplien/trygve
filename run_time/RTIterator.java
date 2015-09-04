package run_time;

import java.util.Map;

import expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import run_time.RTObjectCommon.RTContextObject;
import run_time.RTObjectCommon.RTIntegerObject;

public abstract class RTIterator implements RTObject {
	public RTIterator() {
		super();
		referenceCount_ = 1;
	}
	public abstract boolean isThereANext();
	public abstract void advance();
	public abstract RTObject next();
	
	public static class RTArrayIterator extends RTIterator {
		public RTArrayIterator(RTArrayObject whatIAmIteratingOver) {
			super();
			whatIAmIteratingOver_ = whatIAmIteratingOver;
			arraySize_ = whatIAmIteratingOver_.size();
			currentIndex_ = 0;
		}
		public boolean isThereANext() {
			return currentIndex_ < arraySize_;
		}
		public RTObject next() {
			final RTIntegerObject theIndexObject = new RTIntegerObject(currentIndex_);
			assert currentIndex_ < arraySize_;
			return whatIAmIteratingOver_.getObject(theIndexObject);
		}
		public void advance() {
			currentIndex_++;
		}
		private final RTArrayObject whatIAmIteratingOver_;
		private int currentIndex_;
		private final int arraySize_;
	}

	@Override public RTObject getObject(String name) { assert false; return null; }
	@Override public void addObjectDeclaration(String objectName, RTType type) { assert false; }
	@Override public Map<String, RTType> objectDeclarations() { assert false; return null; }
	@Override public void setObject(String objectName, RTObject object) { assert false; }
	@Override public Map<String, RTObject> objectMembers() { assert false; return null; }
	@Override public RTType rTType() { assert false; return null; }
	@Override public boolean equals(RTObject another) { assert false; return false; }
	@Override public boolean gt(RTObject another) { assert false; return false; }
	@Override public RTObject plus(RTObject other) { assert false; return null; }
	@Override public RTObject minus(RTObject other) { assert false; return null; }
	@Override public RTObject times(RTObject other) { assert false; return null; }
	@Override public RTObject divideBy(RTObject other) { assert false; return null; }
	@Override public RTObject modulus(RTObject other) { assert false; return null; }
	@Override public RTObject unaryPlus() { assert false; return null; }
	@Override public RTObject unaryMinus() { assert false; return null; }
	@Override public RTObject preIncrement() { assert false; return null; }
	@Override public RTObject postIncrement() { assert false; return null; }
	@Override public RTObject preDecrement() { assert false; return null; }
	@Override public RTObject postDecrement() { assert false; return null; }
	@Override public RTObject performUnaryOpOnObjectNamed(String idName, String operator,
			PreOrPost preOrPost_) { assert false; return null; }
	@Override public RTObject toThePowerOf(RTObject exponent) { assert false; return null; }
	@Override public RTObject dup() { assert false; return null; }
	@Override public void incrementReferenceCount() { referenceCount_++; }
	@Override public void decrementReferenceCount() { --referenceCount_; }
	@Override public long referenceCount() { return referenceCount_; }
	@Override public void enlistAsRolePlayerForContext(String roleName,
			RTContextObject contextInstance) { assert false; }
	@Override public void unenlistAsRolePlayerForContext(String roleName,
			RTContextObject contextInstance) { assert false; }
	
	private int referenceCount_;
}
