context program {
     public String[] foo() {
        return "a,b,c".split(",")
     }
     public void test() {
        String [] result = foo();
        for (String a : result) System.out.println(a)
     }
}

new program().test()
