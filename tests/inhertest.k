class Base {
   public Base() {
     i_ = 1;
   }
   public void report() {
      System.out.println("You have called Base.report()")
   }
   private int i_;
}

class Derived extends Base {
   public Derived() {
     j_ = 2
   }
   public void report() {
      System.out.println("You have called Derived.report()")
   }
   private int j_;
}

class Test {
   public void test() {
      Base b = new Base();
      b.report();
      b = new Derived();
      b.report()
   }
}

{ Test baseDerived = new Test()  baseDerived.test() }

/* GOLD:
___________________________________________________________
You have called Base.report()
You have called Derived.report()
*/
