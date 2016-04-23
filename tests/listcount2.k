class Test {
   public void test() {
      List<String> x = new List<String>();
      x.add("123");
      String i = x.remove(0);
      System.out.println(i)
   }
}

context Context {
   public Context() {
      TestRole = new Test()
      TestRole.test2()
   }
   role TestRole {
      public void test2() { test() }
   } requires {
      void test()
   }
}

new Context()
