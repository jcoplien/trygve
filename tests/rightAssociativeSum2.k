class Test {
   public void test() {
      int one = 1, two = 2, three = 3, four = 4, five = 5;
      int result = one - two + three - four + five + two+++ + ++two + three;
      System.out.println(result)
      result = one * two * three / four * five / two++ * + ++two * three;
      System.out.println(result)
   }
}

{new Test().test()}

/* GOLD:
___________________________________________________________
12
54
*/
