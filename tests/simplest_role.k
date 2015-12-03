class Class1 {
}

context Context {
   role Role1 {
      public void roleMethod() { System.out.println("success") }
   }
   public Context(Class1 object) {
      Role1 = object
      Role1.roleMethod()
   }
}


(new Context(new Class1()))

/* GOLD:
___________________________________________________________
success
*/
