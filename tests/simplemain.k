context C { public
  void m()
   {
      C c = new C();
      for (int i = 0; i < 10; i = i + 1) {
	System.out.print(i);
          System.out.print(" ")
      }
      System.out.println("")
   }
}

{ C c = new C()  c.m() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
0 1 2 3 4 5 6 7 8 9 
*/
