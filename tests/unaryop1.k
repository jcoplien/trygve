class UnaryOp {
   int k;
   public void test() {
      int [] intArray;
      intArray = new int[12];
      int j = 6;
      intArray[6] = 8;
      k = 5;

      this.k++;
      System.out.print("k = "); System.out.println(k);

      j++;
      System.out.print("j = "); System.out.println(j);

      System.out.print("intArray[6] = ");
      System.out.println(intArray[6]);

      j = intArray[6]++;

      System.out.print("j = "); System.out.print(j);

      System.out.print("; intArray[6] = ");
      System.out.println(intArray[6]);
   }
}

{ UnaryOp u = new UnaryOp()  u.test() }

/* GOLD:
___________________________________________________________
k = 6
j = 7
intArray[6] = 8
j = 8; intArray[6] = 9
*/
