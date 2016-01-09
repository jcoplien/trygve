class Polynomial {
		public Polynomial(double a, double b, double c) {
			a_ = a.clone; b_ = b.clone; c_ = c.clone
		}
		private double four_a_c() {
			return 4.0 * a_ * c_;
		}
		private double denominator() {
			return a_ + a_;
		}
		private double rootArg() {
return b_ ** 2 - (this.four_a_c());
		}
		public double root() {
			return (-b_ + Math.sqrt(this.rootArg())) / this.denominator();
		}

		private double a_, b_, c_;
	}


{ System.out.println(new Polynomial(1.0, 2.0, 1.0).root()) }

/* GOLD:
___________________________________________________________
-1.0
*/
