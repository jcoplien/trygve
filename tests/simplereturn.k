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
___________________________________________________________
Test.callee aha...
*/
