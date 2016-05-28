class Test {
   void callee() {
      System.out.print("Test.callee").println(" aha...")
   }
   public void test() {
      callee()
   }
}

(new Test()).test()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Test.callee aha...
*/
