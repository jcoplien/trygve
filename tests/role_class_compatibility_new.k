context C1 {
    public C1(A a) {
        R3 = a;
        R3.test();
    }
    role R1 {
        public void test() {
            new C2(R1);
        }
        public void foo2() {
            System.out.println("foo2")
        }
        public void foo1();
    } requires {
        void foo1();
        void foo2();
        void foo3();
    }
	 role R2 {
        public void test() {
            new C2(R2);
        }
        public void foo2() {
            System.out.println("foo2")
        }
        public void foo1();
    } requires {
        void foo1();
    }
	 role R3 {
        public void test() {
				// the problem lies here.
            new C2(R3);
        }
        public void foo2b() {
            System.out.println("foo2")
        }
        public void foo1();
    } requires {
        void foo1();
        void foo2()
    }
}

context C2 {
    public C2(B b) {
        b.foo2();
     }
}

class A {
    public void foo1() {}
}

class B {
    public void foo1() {}
    public void foo2() {}
}

{
    new C1(new A());
}

/* GOLD:
line 3: Role `R3' cannot be played by object of type `A':
line 3: 	`foo2()' needed by Role `R3' does not appear in interface of `A'.
line 6: Declaration of `foo2' in R1 would create multiple methods of the same name in the same object.
line 8: The parameters to script `C2(R1)' have scripts that are unavailable outside this Context (e.g., they are Role scripts).
line 21: The parameters to script `C2(R2)' have scripts that are unavailable outside this Context (e.g., they are Role scripts).
0 warnings, 5 errors.
___________________________________________________________
*/
