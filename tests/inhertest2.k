class Base {
   public Base() {
      System.out.println("Base::Base");
   }
   public Base(String message) {
       System.out.print("Base::Base: ").println(message);
   }
   public void test() {
      System.out.println("Base::test()")
   }
}

class Derived extends Base {
   public Derived() {
      Base("explicit!");
      System.out.println("Derived::Derived")
   }
}

{ Derived d = new Derived()  d.test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Base::Base: explicit!
Derived::Derived
Base::test()
*/
