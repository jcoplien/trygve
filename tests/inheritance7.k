class Base {
	public int x;
	private int y;
}

class Derived extends Base {
	public int x;
	public int y;
}

new Derived()

/* GOLD:
line 7: Object declaration `x' appears both in class `Derived' and in base class `Base'.
line 7:   (The same identifier name may not appear multiple times in the same run-time scope.)
0 warnings, 2 errors.
___________________________________________________________
*/
