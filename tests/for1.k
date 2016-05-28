class C {
   public void fobar() {
      int j = 0;
      for (int i = 5; i < 10; i = i + 1) j = j + 1;
   }
}

/* GOLD:
line 6: Missing main expression.
0 warnings, 1 error.
___________________________________________________________
*/
