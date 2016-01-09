class Complex {
   public Complex(double realPart, double imaginaryPart) {
      realPart_ = realPart.clone;
      imaginaryPart_ = imaginaryPart.clone
   }
   public Complex(double realPart) {
      realPart_ = realPart.clone;
      imaginaryPart_ = 0.0
   }
   public Complex() { realPart_ = imaginaryPart_ = 0.0 }
   public Complex +(Complex other) {
      double resultingRealPart = other.realPart() + realPart_;
      double resultingImaginaryPart = other.imaginaryPart() + imaginaryPart_;
      Complex retval = new Complex(resultingRealPart, resultingImaginaryPart);
      return retval
   }
   public double realPart() const { return realPart_ }
   public double imaginaryPart() const { return imaginaryPart_ }

   private double realPart_, imaginaryPart_;
}

/* GOLD:
___________________________________________________________
sum = (3.0, 0.0)
*/
{
   Complex c = new Complex(1.0, -1.0);
   Complex d = new Complex(2.0,  1.0);
   Complex sum = c + d;
   System.out.print("sum = (")
             .print(sum.realPart())
             .print(", ")
             .print(sum.imaginaryPart())
             .println(")")
}
