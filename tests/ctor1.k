class Base {
  public Base(int i) {
    i_ = i;
    s_ = ""
  }
  public Base(String s) {
   i_ = 0;
   s_ = s
  }
  int i_;
  String s_;
}

class Test {
   public void test(String dummy) {
      Base b1 = new Base(1);
      // Base b2 = new Base("abc");
      System.out.println("hello world")
   }
}

{ Test test = new Test()  test.test("+-=") }

/* GOLD:
line 3: WARNING: Assignment / initialization does not create a new instance. Both `i_' and `i' will refer to the same object. Use `i.clone' to create a separate instance.
line 8: WARNING: Assignment / initialization does not create a new instance. Both `s_' and `s' will refer to the same object. Use `s.clone' to create a separate instance.
2 warnings, 0 errors.
___________________________________________________________
hello world
*/
