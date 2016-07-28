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
            System.out.println("foo2b")
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
            System.out.println("foo2b")
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
            System.out.println("foo2b")
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

interface A {
    public void foo1();
}

class AClass implements A {
   public void foo1() { }
}

interface B {
    public void foo1();
    public void foo2();
}

{
    new C1(new AClass());
}

/* GOLD:
line 3: Role `R3' cannot be played by object of type `A':
line 3: 	`foo2()' needed by Role `R3' does not appear in interface of `A'.
line 6: Defining `foo2' in R1 would create multiple scripts of the same name in the same object.
line 21: The parameters to script `C2(R2)' have scripts that are unavailable outside this Context, though some formal parameters of C2 presume they are available (they are likely Role scripts):
line 21: 	R2.foo2()
0 warnings, 5 errors.
___________________________________________________________
*/
