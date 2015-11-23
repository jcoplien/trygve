class SimpleIncrTest {
   public void test() {
      int j = 0;
      for (int i = 0; i < 6; i++) {
         j++;
         System.out.println(j)
      }
   }
}

{ SimpleIncrTest simpleIncrement = new SimpleIncrTest()   simpleIncrement.test() }

/* GOLD:
___________________________________________________________
1
2
3
4
5
6
*/
