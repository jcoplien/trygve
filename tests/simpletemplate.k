class Map <T, U> {
   public Map() { i_ = 0; arrayImplementation_ = new T [12]; }
   public T getNext() { return arrayImplementation_[i_++] }
   public void add(T another) {
      System.out.print("li.add entered, i_ = ");
      System.out.println(i_);
      T x = new T();
      arrayImplementation_[i_++] = another;
      System.out.print("Map<T,U> called with another = ")
      System.out.println(arrayImplementation_[i_ - 1])
   }

   private T [] arrayImplementation_;
   private int i_;
}

class Test {
   List<int> intList_;
   Map<int, String> intMap;
   public Test() { intList_ = new List<int>(); }

   public void test() {
      int k = intList_.size();
      System.out.print("Test::test entered, evaluated k = ");
      System.out.println(k);
      Map<int, String> li = new Map<int, String>();
      System.out.println("About to call li_.add(123)");
      li.add(123);
      System.out.println("About to call intList_.add(123)");
      intList_.add(123);
      System.out.println("About to call li.add(321)");
      li.add(321);
      System.out.print("Back from intList_.add(123), size is now ");
      System.out.println(intList_.size());
   }
}

(new Test()).test()

/* GOLD:
___________________________________________________________
Test::test entered, evaluated k = 0
About to call li_.add(123)
li.add entered, i_ = 0
Map<T,U> called with another = 123
About to call intList_.add(123)
About to call li.add(321)
li.add entered, i_ = 1
Map<T,U> called with another = 321
Back from intList_.add(123), size is now 1
*/
