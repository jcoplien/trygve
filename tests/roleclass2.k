class Class1 {
   public void roleMethod1(boolean b) { }
   public void roleMethod1(boolean b) { }
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
line 3: Multiple declarations of `roleMethod1(boolean)' in scope `Class1'.
0 warnings, 1 error.
___________________________________________________________
*/
