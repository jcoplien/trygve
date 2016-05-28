class Test {
   boolean isMinor() { return true }
   public void test() {
      String sus2 = "a", sus4 = "x"
      String s = (if(isMinor()) "m" else "") + sus2 + sus4;
      System.out.print("s=").println(s)
   }
}

{ new Test().test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
s=max
*/
