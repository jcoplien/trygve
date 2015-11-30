context Context {
   role Role1 {
      public void foo() {
         System.out.println("Context.Role1.foo()")
      }
      public void bar() {
         System.out.println("Context.Role1.bar()")
      }
   }
   public Context(Class1 object) {
      Role1 = object
   }
   public void trigger() {
      System.out.println("Context.trigger()")
      Role1.foo()
   }
}

class Class1 {
   public void foo() {
      System.out.println("Class.foo()");
      this.bar();
   }
   public void bar() {
      System.out.println("Class.bar()")
   }
}

{
  Class1 object = new Class1();
  Context c1 = new Context(object);
  object.foo()
  object.bar()
  c1.trigger()
}

/* GOLD:
line 11: WARNING: Both class `Class1' and Role `Role1' contain the same method signature `foo()'. This results in several methods of the same name in the same object and may not behave as you expected.
line 11: WARNING: Both class `Class1' and Role `Role1' contain the same method signature `bar()'. This results in several methods of the same name in the same object and may not behave as you expected.
___________________________________________________________
Class.foo()
Class.bar()
Class.bar()
Context.trigger()
Context.Role1.foo()
*/
