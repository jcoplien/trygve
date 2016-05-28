class Fact {
   int fact(int n) {
      return if (n <= 1) 1 else n * fact(n-1)
   }
   public void factRunner() {
     int [] numbers = new int[5];
     numbers[0] = 1
     numbers[1] = 3
     numbers[2] = 5
     numbers[3] = 6
     numbers[4] = 20
     for (int aparam : numbers)
        System.out.print(aparam)
	.print(" factorial is ")
	.print(fact(aparam))
	.println(".")
   }
}

(new Fact()).factRunner()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
1 factorial is 1.
3 factorial is 6.
5 factorial is 120.
6 factorial is 720.
20 factorial is 2432902008176640000.
*/
