{
    double n1 = 2.34
    int i = Math.round(n1)
    System.out.println(i)

    double n2 = 2.51
    double d = Math.round(n2)
    System.out.println(d)
}

/* GOLD:
line 7: WARNING: Substituting double object for `Math.round()' in assignment to `d'.
1 warning, 0 errors.
___________________________________________________________
2
3.0
*/