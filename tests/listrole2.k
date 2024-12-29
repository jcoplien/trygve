context C1 {
    role R {
        public void foo() {
            System.out.println("foo");
        }
    }

    public C1(Object o) {
        R = o
        test(R);
    }

    public void test(R x) {
        x.foo();
    }

}

{
    new C1(new Object());
}

/* GOLD:
line 13: WARNING: Declaring a Role parameter `x' for Context script `test' is unorthodox. The script should directly access the Role `R' instead.
1 warning, 0 errors.
___________________________________________________________
foo
*/
