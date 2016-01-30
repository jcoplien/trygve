class JoinTest {
    public JoinTest() {}

    public void test() {
        List<String> los = new List<String>();
        los.add("1");
        los.add("2");
        los.add("3");
        los.add("4");
        String string = String.join("+", los);
        System.out.println(string);
    }
}


(new JoinTest()).test()

/* GOLD:
___________________________________________________________
1+2+3+4
*/
