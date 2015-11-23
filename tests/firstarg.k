context Jerry {
   public Jerry(String s) {
      System.out.println("executed Jerry::Jerry")
   }
   public void f() {
      System.out.println("executed Jerry::f")
   }
}

{
  Jerry tom = new Jerry("abc")
  tom.f()
}
