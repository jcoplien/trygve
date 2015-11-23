class C {
   void pr() {
      System.out.println("hello world");
      System.out.println("hello verden")
   }
   void pr2() {
      System.out.println("before");
      pr();
      System.out.print(123);
      System.out.println(" after")
   }
}

{ C c = new C()  c.pr2() }
