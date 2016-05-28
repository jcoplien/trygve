class InSituInitTest {
    public InSituInitTest() {}

    private String string = "Hello ";

    public void test() {
        System.out.print(string);
    }
}

class InSituInitTest2 {
    private String string = "world!";

    public void test() {
        System.out.println(string);
    }
}


{
(new InSituInitTest()).test()
(new InSituInitTest2()).test()
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Hello world!
*/
