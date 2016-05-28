class Base {
   public Base() { System.out.println("Base::Base"); }
}

class Derived extends Base {
   public Derived(int k) {
      System.out.print("Derived::Derived(").print(k).println(")");
   }
   public void hello() {
      System.out.println("hello world")
   }
}

(new Derived(789)).hello()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Base::Base
Derived::Derived(789)
hello world
*/
