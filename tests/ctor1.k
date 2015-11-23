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
___________________________________________________________
hello world
*/
