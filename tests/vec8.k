context C {
    role [] rolevector { void foo() {int theIndex = 0} }
    public C(Object o) {
       // rolevector = o
   }
}

{
   Object roleplayer = new Object();
   C c = new C(roleplayer);
   String [] ls = new String[3];
   // for (String s : ls) System.out.format("%s\n", s)
   for (String s : ls) System.out.print(s);
   System.out.println()
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
<null><null><null>
*/
