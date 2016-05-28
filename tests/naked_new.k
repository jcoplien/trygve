context C1 {
    public C1(Object o) {
        R1 = o;
    }
    role R1 {
        void foo() {}
    }
}

context C2 {
    public C2(Object o) {
        R1 = o;
    }
    role R1 {
        void foo() {}
    }    
}

class Test {
    public void t1(Object o) {
        new C1(o);
    }
    
    public void t2(Object o) {
        new C2(o);
    }
}

{
    Test t = new Test();
    Object o = new Object();
    t.t1(o);
    t.t2(o);
    System.out.println("finis")
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
finis
*/
