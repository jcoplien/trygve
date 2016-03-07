class Test {
   public Test() {
   }
   public void test() {
      String s1 = "tweedledee";
      String s2 = "tweedledum";
      String s3 = s1;
      String s4 = s2;
      String s5 = s1.clone
      System.out.println(s1 == s1);	// true
      System.out.println(s1 == s2);	// false
      System.out.println(s1 is s1);	// true
      System.out.println(s1 is s2);	// false
      System.out.println(s1 is not s2);	// true
      System.out.println(s1 isnot s1);	// false
      System.out.println(s1 is s3);	// true
      System.out.println(s4 isnot s2);	// false
      System.out.println(s5 isnot s1);	// true
      System.out.format("%s %d\n", s1, 1)
   }
}

new Test().test()

/* GOLD:
line 7: WARNING: Assignment / initialization does not create a new instance. Both `s3' and `s1' will refer to the same object. Use `s1.clone' to create a separate instance.
line 8: WARNING: Assignment / initialization does not create a new instance. Both `s4' and `s2' will refer to the same object. Use `s2.clone' to create a separate instance.
___________________________________________________________
true
false
true
false
true
false
true
false
true
tweedledee 1
*/