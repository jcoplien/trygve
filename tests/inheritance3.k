class Base {
   public Base() { i_ = 456 }
   private int i_;
}

class Derived extends Base {
   public Derived() { i_ = 789 }
   private int i_;
   public void test() {
     System.out.print("i_ = ").println(i_)
   }
}

(new Derived()).test()

/* GOLD:
line 7: Object declaration `i_' appears both in class `Derived' and in base class `Base'.
line 7:   (The same identifier name may not appear multiple times in the same run-time scope.)
line 10: Object declaration `i_' appears both in class `Derived' and in base class `Base'.
line 10:   (The same identifier name may not appear multiple times in the same run-time scope.)
___________________________________________________________
*/
