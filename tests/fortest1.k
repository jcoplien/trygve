class A {
   public void b() { }
}
class Test {
   public Test() { a = new A();
      buttons_ = new List<int>(); buttons_.add(1) }
   public boolean handleEvent(int i) {
      boolean retval = false;
      if (i != 0) {
          for (int button : buttons_) {
             if (1 != 0) {
                a.b();
                retval = true;
   System.err.print("set retval = true... -> ")
                break
             }
          }
      }
   System.err.println(retval)
      return retval
   }
   private List<int> buttons_
   A a
}

new Test().handleEvent(1)