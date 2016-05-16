class Int {
	public Int(int i) { i_ = i.clone }
	public Int(int i, Int otherOne) { i_ = i.clone other = otherOne }
   public void incr() { i_ = i_ + 1; if (other is not null) other.incr }
	public void print() const { System.out.println(i_) }
   int i_;
   Int other
}

context UseCase {
	role I1 {
		public void step() { incr() }
		public void report() const { this.print() }
   } requires {
		void print() const;
		void incr()
	}
   role I2 {
		public void step() { incr() }
		public void report() const { this.print() }
	} requires {
		void print() const;
		void incr()
	}
	public void trigger(Int i1, Int i2) {
		I1 = i1
		I2 = i2
		I1.step
		I1.report
		I2.report
	}
}

{
Int i2 = new Int(3);
Int i1 = new Int(4, i2);
new UseCase().trigger(i1, i2)
}
