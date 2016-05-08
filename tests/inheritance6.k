class Base {
}

class Derived extends Base {
}

class Base2 {
	public Derived func(int i, Base j) { return null }
	public Base func2(int i, Derived j) { return null }
	public Base func3(int i, Base j) { return null }
	public Derived func4(int i, Derived j) { return null }
}

class Derived2 extends Base2 {
	public Derived func(int i, Base j) { return null } // ok
	public Base func2(int i, Derived j) { return null } // ok
	public Derived func3(int i, Derived j) { return null } // bad
	public Base func4(int i, Base j) { return null } // ok
	public void test() {
	}
}

class Derived2b extends Base2 {
	public Base func(int i, Base j) { return null } // ok
	public Derived func2(int i, Derived j) { return null } // bad
	public Base func3(int i, Derived j) { return null } // ok
	public Derived func4(int i, Base j) { return null } // irrelevant
}

class Derived2c extends Base2 {
	public Base func(int i, Derived j) { return null } // ok
	public Derived func2(int i, Base j) { return null } // irrelevant
	public Base func3(int i, Base j) { return null } // ok
	public Derived func4(int i, Derived j) { return null } // ok
}

class Derived2d extends Base2 {
	public Derived func(int i, Derived j) { return null } // ok
	public Base func2(int i, Base j) { return null } // irrelevant
	public Derived func3(int i, Base j) { return null } // bad
	public Base func4(int i, Derived j) { return null }  // ok
}


new Derived2().test()

/* GOLD:
line 17: Return type `Base' of `func3(int,Derived)' in class `Base2' must be no less restrictive than `Derived' in the derived class.
line 25: Return type `Base' of `func2(int,Derived)' in class `Base2' must be no less restrictive than `Derived' in the derived class.
line 40: Return type `Base' of `func3(int,Base)' in class `Base2' must be no less restrictive than `Derived' in the derived class.
___________________________________________________________
*/
