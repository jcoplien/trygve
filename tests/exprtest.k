class ExprTest {
   public void test() {
      double d = 3.14;
      int four = 4;
      double piOverFour;
      piOverFour = d / four;
      System.out.println(piOverFour)
   }
}

{ ExprTest math = new ExprTest()  math.test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
0.785
*/
