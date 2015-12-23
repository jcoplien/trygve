context Context {
   role Role1 {
       public void roleFunction1() {
           assert(1 == 2, "1 == 2")
       }
   }
   public void trigger() {
      Role1.roleFunction1();
   }
   public Context() {
      Role1 = 1
   }
}

(new Context()).trigger()