class Base {
   public Base() { i_ = 456 }
   public int i_;
}

class Derived extends Base {
   public Derived() { i_ = 789 }
   public void test() {
     System.out.print("i_ = ").println(i_)
   }
}

(new Derived()).test()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
i_ = 789
*/
