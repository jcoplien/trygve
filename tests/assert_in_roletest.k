context Context {
   role Role1 {
      public void role1Func() {
         assert (1 == 2, "1 == 2")
      }
   }
   public Context() {
      Role1 = 1
   }
   public void trigger() {
      Role1.role1Func()
   }
}

(new Context()).trigger()

/* GOLD:
___________________________________________________________
Line 4: Assertion failed: 1 == 2
	In script `assert'
	Called from script `role1Func'
	Called from script `trigger'
*/
