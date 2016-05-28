context Context {
   role Role {
      void roleMethod() {
         requiresScript();
      }
   } requires {
      void requiresScript();
   }
   public Context(AClass anObject) {
      Role = anObject
   }
   public void run() {
      Role.requiresScript();
   }
}

class AClass {
   public void requiresScript() {
      System.out.println("hello Context")
   }
}

{ (new Context(new AClass())).run() }

/* GOLD:
line 13: Context script `run' may enact only Role scripts. Script `requiresScript()' is an instance script from a class and is inaccessible to Context `Context'.
0 warnings, 1 error.
___________________________________________________________
*/
