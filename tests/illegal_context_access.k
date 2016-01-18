context Ctx {
   public Ctx() {Role=this; System.out.println("Ctx enter" )}
   role Role {
      public void roleScript1() {
        System.out.println("roleScript1 enter" )
         this.ctxMethod1()
        System.out.println("roleScript1 exit" )
      }
      public void roleScript2() {
        System.out.println("roleScript2 enter" )
         this.ctxMethod2()
        System.out.println("roleScript2 exit" )
      }
   } requires {
      void ctxMethod1(); 
      void ctxMethod2()
   }
   public void trigger() { 
      Role.roleScript1() 
  }
   public void ctxMethod1() {
      System.out.println("ctxMethod1 enter" )
      Role.roleScript2()
      System.out.println("ctxMethod1 exit" )
   }
   public void ctxMethod2() {
      System.out.println("ctxMethod2 enter and exit" )
   }
}

{
  Ctx a = new Ctx()
  a.trigger()
}

/* GOLD:
line 3: WARNING: Script declaration for `ctxMethod2' may hide script declared at line 26
line 3: WARNING: Script declaration for `ctxMethod1' may hide script declared at line 21
___________________________________________________________
Ctx enter
roleScript1 enter
ctxMethod1 enter
roleScript2 enter
ctxMethod2 enter and exit
roleScript2 exit
ctxMethod1 exit
roleScript1 exit
*/
