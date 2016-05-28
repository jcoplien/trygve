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
   role Role1 {
      public void role1Method() {
         this.bar("hello world")
      }
   } requires {
      void bar(String s);
   }
   public void trigger() { Role1.role1Method() }
}

{ AContext a = new AContext() a.trigger() }


/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
line 33: FATAL: TERMINATED: Attempting to invoke method `bar' on a null object
popping stack: type is RTStringObject
popping stack: type is RTNullObject
	In script `Role1.bar'
	Called from script `Role1.role1Method'
	Called from script `AContext.trigger'
*/
