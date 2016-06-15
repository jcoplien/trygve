class MyClass {
	public MyClass(int i) { i_ = i.clone }
	public void f() { System.err.print("this is MyClass.f: ").println(i_) }
	int i_
}

context C {
	role R1 {
		public void f();
	} requires {
		void f()
	}
	role R2 {
	} requires {
		void f()
	}

	public C() {
		R1  = new MyClass(1)
		R2 = new MyClass(2)
	}

	public void doit() {
		System.err.println("doit")
		R1.f()
	}
}

new C().doit

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
doit
this is MyClass.f: 1
*/