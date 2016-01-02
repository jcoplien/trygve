class MyInt {
   public MyInt(double i) { i_ = i }
   public MyInt +(MyInt other) {
      MyInt retval = new MyInt(i_ + other.i_);
      return retval;
   }
   public void print() { System.out.println(i_) }
   public double i_
}
{
  MyInt one = new MyInt(1.0);
  MyInt two = new MyInt(2.0);
  MyInt three = one + two;
  three.print()
}