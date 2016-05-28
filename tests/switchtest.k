class SwitchTest {
   public void test() {
      for (int i = 0; i < 20; i++) {
         System.out.println(i);

         switch (i) {
	 case 1:
	     System.out.println("is unity");
	 case 3:
	 case 5:
	     System.out.println("is odd");
	 case 2:
               System.out.println("is prime");
               if (i < 4) break;
	 case 4:
               System.out.println("is even, except for 5");
	     break;
	 case 6:
	     System.out.println("is six");
	     break;
	 case 7:
	     System.out.println("is seven");
	     break;
	 default:
	     System.out.println("is something else");
	     break;
         }

         System.out.println("right after switch");

         if (i == 10) break;
      }
   }
}

{ SwitchTest switchTest = new SwitchTest()  switchTest.test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
0
is something else
right after switch
1
is unity
is odd
is prime
right after switch
2
is prime
right after switch
3
is odd
is prime
right after switch
4
is even, except for 5
right after switch
5
is odd
is prime
is even, except for 5
right after switch
6
is six
right after switch
7
is seven
right after switch
8
is something else
right after switch
9
is something else
right after switch
10
is something else
right after switch
*/
