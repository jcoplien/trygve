context program {
     role lines {
        public String[] foo() {
           return split(",")
        }
     } requires {
          String [] split(String str);          
     }

     public program(String input){
            String[] parts = input.split(" ");
            lines = input;
     }

     public void test() {
        String [] result = lines.foo();
        for (String a : result) System.out.println(a)
     }
}

new program("a,b,c").test()
