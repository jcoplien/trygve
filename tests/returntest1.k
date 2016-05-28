class ArraySizeTest {
   public void test() {
      int j = this.size();
   }
   public int size() {
      System.out.println("made it into size")
      return this.size2()
   }
  public int size2() {
      System.out.println("made it into size2")
      return 7
   }
}

{
  ArraySizeTest ast = new ArraySizeTest()
  ast.test()
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
made it into size
made it into size2
*/
