class A {
    public void foo(int x, int y) {System.err.println("A")}
}

class B extends A {
    public void foo(int x) {System.err.println("B")}
}

{
    //this should compile
    new A().foo(1, 2);
    System.err.println("back from new A()")
    //but this should not, right?
    new B().foo(1, 2);
    System.err.println("back from new B()")
}

/* GOLD:
___________________________________________________________
A
back from new A()
A
back from new B()
*/