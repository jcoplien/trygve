class Class1 {
   public void roleMethod1(boolean b) { }
   public void roleMethod2(boolean b) { }
}

context Context {
   role Role1 {
      void roleMethod1(int i, String c) {
      }
      void roleMethod2(boolean b) { }
   }
   public Context(Class1 object) {
      Role1 = object
   }
}


(new Context(new Class1()))

/* GOLD:
line 13: WARNING: Both class `Class1' and Role `Role1' contain the same method signature `roleMethod2(boolean)'. This results in several methods of the same name in the same object and may not behave as you expected.
___________________________________________________________
*/
