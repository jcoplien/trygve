class MyTemplate <T,U> {
   public T getNext() { return arrayImplementation_[i_++] }
   public int ifunc() { return i_ }

   private T [] arrayImplementation_;
   private int i_;
}


{
  MyTemplate<int,String> mt = new MyTemplate<int,String>()
  mt.ifunc()
  mt.getNext()
}

/* GOLD:
___________________________________________________________
*/
