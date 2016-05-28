class ArraySizeTest {
   public void test() {
      int [] array = new int[5]
      int sz;
      sz = array.size()
      int j = this.size();
      System.out.print("Size of array is ").print(sz)
         .print(", and j=").println(j)
   }
   public int size() {
      String [] stringArray = new String[123];
      return stringArray.size()
   }
}

{
  ArraySizeTest ast = new ArraySizeTest()
  ast.test()
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Size of array is 5, and j=123
*/
