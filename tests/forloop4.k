class AnObject {
    public AnObject() { System.out.print(" o ") }
}

class ForLoopInitTest {
   public ForLoopInitTest() { System.out.println("Started.") }
   public void run(int limit) {
      for (int i = 1; i <= limit; i++) {
         System.out.print(i).print(" ")
         AnObject init = new AnObject()
         if (i % 5 == 0) System.out.println("")
      }
   }
}

{
  ForLoopInitTest driver = new ForLoopInitTest()
  driver.run(20)
}

/* GOLD:
___________________________________________________________
Started.
1  o 2  o 3  o 4  o 5  o 
6  o 7  o 8  o 9  o 10  o 
11  o 12  o 13  o 14  o 15  o 
16  o 17  o 18  o 19  o 20  o 
*/
