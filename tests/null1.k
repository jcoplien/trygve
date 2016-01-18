class NullTest {
   public void f1() {
      f2(null)
   }
   public void f2(String s) {
      if (s == null) {
         System.out.println("yup, it's null")
      } else {
         System.out.println("uh oh")
      }
   }
}

{ (new NullTest()).f1() }