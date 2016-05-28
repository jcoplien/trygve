class Test {
   public void test() {
      int a, b, c;
      a = b = c = if (a == null) { int h = 123 }
                  else { int j; j = 456 };
      System.out.print(" a = "); System.out.println(a);
      System.out.print(" b = "); System.out.println(b);
      System.out.print(" c = "); System.out.println(c);
   }
}

{ Test t = new Test(); t.test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
 a = 123
 b = 123
 c = 123
*/
