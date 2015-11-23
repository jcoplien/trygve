class List <T, U> {
   public List() { i_ = 0; arrayImplementation_ = new T [12]; }
   public void add(T another) {
      arrayImplementation_[i_++] = another;
   }

   private T [] arrayImplementation_;
   private int i_;
}

class Test {
   public void test() {
      List<int, String> li = new List<int, String>();
      li.add(1);
   }
}

(new Test()).test()