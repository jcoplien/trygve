context Context1 {
   public Context1(AnObject o) {
      C1R1 = o
      C1R2 = o
      System.out.println("Context1 ctor called:");
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
   public AnObject(int id) { id_ = id.clone() }
   public void print() {
      System.out.print("I am AnObject(");
      System.out.print(id_);
      System.out.println(")")
   }
   private int id_;
}

class TestDriver {
   public void test() {
      AnObject anObject = new AnObject(123);
      Context1 context1 = new Context1(anObject);
      Context2 context2 = new Context2(anObject);	// runtime error
      System.out.println("Done")
   }
}

{
  TestDriver driver = new TestDriver()
  driver.test()
}


/* GOLD:
line 3: WARNING: Both class `AnObject' and Role `C1R1' contain the same script signature `print()'. This results in several scripts of the same name in the same object and may not behave as you expected.
line 4: WARNING: Both class `AnObject' and Role `C1R2' contain the same script signature `print()'. This results in several scripts of the same name in the same object and may not behave as you expected.
line 22: WARNING: Both class `AnObject' and Role `C2R1' contain the same script signature `print()'. This results in several scripts of the same name in the same object and may not behave as you expected.
3 warnings, 0 errors.
___________________________________________________________
Context1 ctor called:
I am C1R1
Object of type AnObject playing too many roles, including C2R1
	In Context `Context1': C1R1, C1R2.
	In Context `Context2': C2R1.
Objects may play Role(s) only in one Context at a time (note: this does not apply to Stage Props).
Further execution may exhibit undefined behaviour.
Context2 ctor called:
I am AnObject(123)
Done
*/
