class MyTemplate <T, U> {
   public T getNext() { return arrayImplementation_[i_++] }
   public void add(T another) {
      T x = new T();
   }

   private T [] arrayImplementation_;
   private int i_;
}


(new MyTemplate<int, String>()).getNext()

/* GOLD:
___________________________________________________________
*/
