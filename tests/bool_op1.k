class Test {
   public void test() {
      int i = 1, j = 2, k = 3
      boolean b1, b2, b3
      b1 = 1 > 2
      b2 = 2 < 3
      b3 = b1 && b2
      boolean b4 = b1 || b2
      System.out.print("b1=")  .print(b1)
                .print(", b2=").print(b2)
                .print(", b3=").print(b3)
                .print(", b4=").print(b4)
                .println()
      boolean nb1
      nb1 = !b1
      boolean nb2 = !b2
      boolean nb3 = !b3
      boolean nb4 = !b4
      System.out.print("nb1=")  .print(nb1)
                .print(", nb2=").print(nb2)
                .print(", nb3=").print(nb3)
                .print(", nb4=").print(nb4)
                .println()
   }
}

(new Test()).test()

/* GOLD:
___________________________________________________________
b1=false, b2=true, b3=false, b4=true
nb1=true, nb2=false, nb3=true, nb4=false
*/
