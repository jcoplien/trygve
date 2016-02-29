class Base {
   public Base() { }
}

class Derived extends Base {
   // Sneaky override
   public void bscript() {
      System.out.println("Derived.bscript")
   }
}

class Base2 {
   public Base2() { }
   public void bscript() {
      System.out.println("Base2.bscript")
   }
}

class Derived2 extends Base2 {
   public void bscript() {
      System.out.println("Derived2.bscript")
   }
}

context c {
   role b {
      public void bscript() {
         System.out.println("b.bscript")
      }
   }
   public c(Base ba) {
      b = ba;
   }
   public c(Base2 ba) {
      b = ba
   }

   public void test() { b.bscript() }
}

{ c C = new c();  C.test() }

/* GOLD:
line 35: WARNING: Both class `Base2' and Role `b' contain the same method signature `bscript()'. This results in several methods of the same name in the same object and may not behave as you expected.
___________________________________________________________
b.bscript
*/
