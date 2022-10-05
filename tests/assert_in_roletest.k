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
0 warnings, 0 errors.
___________________________________________________________

Line 4: Assertion failed: 1 == 2
	In script `Object.assert'
	Called from script `Role1.role1Func'
	Called from script `Context.trigger'
*/
