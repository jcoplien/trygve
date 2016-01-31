class InSituInitTest {
    public InSituInitTest() {}

    private String string = "Hello world!";

    public void test() {
        System.out.println(string);
    }
}


(new InSituInitTest()).test()

/* GOLD:
___________________________________________________________
1+2+3+4
*/
