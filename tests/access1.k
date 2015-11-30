class cl1 {
   private int cl1o1;
   public int cl1o2;
   private cl1(int i) {
   }
}

context c1 {
   role c1r1 {
     void c1r1f1() {
        c1r2.c1r2f1();
        c1r2.c1r2f2(new cl1())
     }
     void clr1f2() {
        private int i;
        c1r1.c1r1f1();
     }
     private void c1r1f3() {
     }
   }
   role c1r2 {
      public void c1r2f1() {
         int j = cl1o.cl1o1;
      }
      void c1r2f2(cl1 cl1o) {
         int i1 = cl1o.cl1o1;
         int i1 = cl1o.cl1o2;
         int k = cl1o.cl1o1;
      }
   }
   private role clr3 {
      void c1r3f1() {
      }
   }
   void trigger(int k) { }
}

{
   cl1 cl1o = new cl1(456);
   c1 newcontext = new c1();
   newcontext.trigger(123)
}


/* GOLD:
line 15: Warning: Identifier `i' has a gratuitous access qualifier.
line 27: Identifier `i1' declared multiple times (earlier declaration at line 26).
line 31: WARNING: Gratuitous access qualifier `private' ignored.
line 12: Cannot access method `c1r2f2' with `default' access qualifier.
line 23: Object `cl1o' is not declared in scope `c1r2f1'.
line 23: Field `cl1o1' not found as member of void
line 23: Type mismatch in initialization of `j'.
line 26: Cannot access expression `cl1o.cl1o1' with `private' access qualifier.
line 28: Cannot access expression `cl1o.cl1o1' with `private' access qualifier.
line 39: Cannot access constructor `cl1' with `private' access qualifier.
line 41: Cannot access method `trigger' with `default' access qualifier.
___________________________________________________________
*/
