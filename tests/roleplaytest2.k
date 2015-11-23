context Context1 {
   public Context1(AnObject o) {
      C1R1 = o
      C1R2 = o
      System.out.println("Context1 ctor called:")
      C1R1.print()
   }
   role C1R1 {
      public void print() {
         System.out.println("I am C1R1");
      }
   }
   role C1R2 {
      public void print() {
         System.out.println("I am C1R2");
      }
   }
}
context Context2 {
   public Context2(AnObject o) {
      C2R1 = o
      System.out.println("Context2 ctor called:");
      o.print()
   }
   role C2R1 {
      void print() {
         System.out.println("I am C2R1");
      }
   }
}

class AnObject {
   public AnObject(int id) {
      id_ = id;
      System.out.print("AnObject::AnObject(");
      System.out.print(id);
      System.out.println(")");
   }
 
   public void print() {
      System.out.print("I am AnObject(");
      System.out.print(id_);
      System.out.println(")")
   }
   private int id_;
}

class TestDriver {
   public void test() {
      System.out.println("TestDriver.test");
      AnObject anObject = new AnObject(123);
      test2(anObject);
      Context1 context1 = new Context1(anObject);
      System.out.println("Done")
   }
   private void test2(AnObject thatObject) {
      System.out.println("TestDriver::test2(thatObject)");
      Context2 ctx2 = new Context2(thatObject);  // runtime error?
      System.out.println("exiting TestDriver::test2(thatObject)");
   }
}

(new TestDriver())
  .test()

/* GOLD:
line 3: WARNING: Both class `AnObject« and Role `C1R1« contain the same method signature `print()«. This results in several methods of the same name in the same object and may not behave as you expected.
line 4: WARNING: Both class `AnObject« and Role `C1R2« contain the same method signature `print()«. This results in several methods of the same name in the same object and may not behave as you expected.
line 21: WARNING: Both class `AnObject« and Role `C2R1« contain the same method signature `print()«. This results in several methods of the same name in the same object and may not behave as you expected.
___________________________________________________________
AnObject::AnObject(123)
TestDriver.test
Context2 ctor called:
I am AnObject(123)
TestDriver::test2(thatObject)
exiting TestDriver::test2(thatObject)
Context1 ctor called:
I am C1R1
Done
*/
