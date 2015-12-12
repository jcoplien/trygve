class WhileTest {
   public WhileTest() { System.out.println("Started.") }
   public void run(int limit) {
      int hours = 3;
      while (hours < limit) {
         int counter = 3;

         do {
            --hours;

            System.out.print(hours); System.out.print(" ");

            if (hours == 1) break;
            counter = counter - 1
         } while (counter > 0);

         int aDeclaration = hours;

         hours = hours + 4;
         System.out.print(hours);
         System.out.print("   ")
      }
      System.out.println();
   }
}

{
  WhileTest driver = new WhileTest()
  driver.run(20)
}

/* GOLD:
___________________________________________________________
Started.
2 1 5   4 3 2 6   5 4 3 7   6 5 4 8   7 6 5 9   8 7 6 10   9 8 7 11   10 9 8 12   11 10 9 13   12 11 10 14   13 12 11 15   14 13 12 16   15 14 13 17   16 15 14 18   17 16 15 19   18 17 16 20   
*/
