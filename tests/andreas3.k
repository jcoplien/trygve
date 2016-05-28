class MyTemplate<T,U> {
   public MyTemplate() { i_ = 789 }
   public int ifunc() { return i_ }

   private int i_;
}


{
  // Uncommenting this makes it work!
  // MyTemplate<int,String> useless
  int i = (new MyTemplate<int,String>()).ifunc()
  System.out.println(i)
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
789
*/
