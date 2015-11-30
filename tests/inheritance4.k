class Base {
   public Base() { i_ = 456 }
   private int i_;
}

class Derived extends Base {
   public Derived() { i_ = 789 }
   public void test() {
     System.out.print("i_ = ").println(i_)
   }
}

(new Derived()).test()

/* GOLD:
line 7: Symbol `i_' is not public and so is not accessible to `Derived'.
line 7: Object `i_' is not declared in scope `Derived'.
line 7: Type of `Null' is incompatible with expression type `int'.
line 9: Symbol `i_' is not public and so is not accessible to `Derived'.
line 9: Object `i_' is not declared in scope `test'.
line 9: No match for call: System.out.print("i_ = ").println(i_)
___________________________________________________________
*/
