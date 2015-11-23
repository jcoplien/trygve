class C {
  public void foo() {
     String s1, s2;
     s1 = s2 + "xyz";
  }
}

/* GOLD:
line 6: Missing main expression.
___________________________________________________________
*/
