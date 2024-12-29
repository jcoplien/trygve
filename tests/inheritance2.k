class ABaseClass {
   public ABaseClass(int intArgument) {
      storage_ = intArgument.clone;
   }
   public String toString() {
      return "ABaseClass" + "xyz"
   }
   public void foo() {
      int localVariable = 5;
   }
   public void bar(String s) {
      int myVariable = 12345;
      System.out.print("ABaseClass.bar(\"")
      System.out.print(s)
      System.out.println("\") is called")
   }
   private int storage_
}

class ADerivedClass extends ABaseClass {
   public ADerivedClass() {
      anInteger = 0
   }
   public String toString() {
      return "ADerivedClass" + "xyz"
   }
   public void foo() {
      int localVariable = 5;
   }
   private int anInteger
}

context AContext {
   public AContext(ABaseClass role1Player) { Role1 = role1Player }
   role Role1 {
      public void role1Method() {
         this.bar("hello world")
      }
   } requires {
      void bar(String s);
   }
   public void trigger() { Role1.role1Method() }
}

{
  AContext a = new AContext(new ABaseClass(987))
  a.trigger()
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________

ABaseClass.bar("hello world") is called
*/
