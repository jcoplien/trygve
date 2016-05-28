class ArrayDupTest {
   public void test() {
      int [] intArray = new int[5];
      for (int i = 0; i < 5; i++) {
         intArray[i] = i.clone()
      }
      for (int i = 0; i < 5; i++) {
         System.out.println(intArray[i])
      }
      intArray = null;
      // System.out.println(intArray)
   }
}

{ ArrayDupTest adt = new ArrayDupTest() adt.test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
0
1
2
3
4
*/
