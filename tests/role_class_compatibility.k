context C1 {
    public C1(A a) {
        R = a;
        R.test();
    }

    role R {
        public void test() {
				// the problem lies here.
            new C2(R);
        }
        public void bar() {
            System.out.println("bar")
        }
        public void foo();
    }
    requires {
        void foo();
    }
}

context C2 {
    public C2(B b) {
        b.bar();
     }
}

class A {
    public void foo() {}
}

class B {
    public void foo() {}
    public void bar() {}
}

{
    new C1(new A());
}

/* GOLD:
line 10: The parameters to script `C2(R)' have scripts that are unavailable outside this Context, though some formal parameters of C2 presume they are available (they are likely Role scripts):
line 10: 	R.bar()
0 warnings, 2 errors.
___________________________________________________________
*/
