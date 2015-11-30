// Press the Parse button, then press the Run button

{
double a, b, c;

a = 1;
b = -1;
c = -6;

// Math.sqrt(16.0)

System.out.println((-b + (Math.sqrt(b **2 - (4.0 * a * c))) ) / (a + a))

// (-b + (Math.sqrt(b ** 2 - (4.0 * a * c))) ) / (a + a)
// (-(-1) + (sqrt((-1 ** 2) - (4 * 1 * -6)))) / (1 + 1)
// (1     + (sqrt( 1        - (-24)       )))  / (2)
// (1     + (sqrt ( 1 + 24))) / 2
// (1     + (5)) / 2
// 3
}

/* GOLD:
line 6: WARNING: Substituting double object for `1' in assignment to `a'.
line 7: WARNING: Substituting double object for `[ -1 ]' in assignment to `b'.
line 8: WARNING: Substituting double object for `[ -6 ]' in assignment to `c'.
___________________________________________________________
3.0
*/
