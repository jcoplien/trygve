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
  public void print() {
     System.out.print(i_);
     System.out.print(" ");
     System.out.println(s_)
  }
}

class Test {
   public void test(String dummy) {
      System.out.println("");
      int threetwoone = 321;
      Base b1 = new Base(-threetwoone);
      Base b2 = new Base("1 2 3");
      b1.print();
      b2.print();
      System.out.println("hello world")
   }
}

{ Test test = new Test()  test.test("+-=") }


/* GOLD:
___________________________________________________________

-321 
0 1 2 3
hello world
*/
