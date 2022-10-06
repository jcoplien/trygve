class Test {
   public void test() {
      int k, o, p;

      for (k = 0; k < 5; k++) {
         System.out.print(k); System.out.print(" ");
      }
      System.out.println("");

      for (int l = 5; l < 10; l++) {
         System.out.print(l); System.out.print(" ");
      }
      System.out.println("");

      int [] mp = new int[5];
      for (int m = 0; m < 5; m++) {
         mp[m] = 10 - m
      }

      for (int n : mp) {
         System.out.print(n); System.out.print(" ")
      }
      System.out.println("");

      for (o : mp) {
         System.out.print(o - 5); System.out.print(" ")
      }
      System.out.println("");

      {
         for (p : mp) {
            System.out.print(p - 10); System.out.print(" ")
         }
         System.out.println("");
      }

   }
}

(new Test()).test()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
0 1 2 3 4 
5 6 7 8 9 
10 9 8 7 6 
5 4 3 2 1 
0 -1 -2 -3 -4 
*/
