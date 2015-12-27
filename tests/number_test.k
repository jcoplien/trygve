context NumberTest {
    role Number {
        public void noop() {}
    } requires {
        void add(int i);
        int get(int i);
        int size();
    }

    public NumberTest(int [] intArray) {
        // Need to create a temp list here, cannot assign directly to the Role
        // List<int> temp = new List<int>();
        Number = new List<int>();

        for(int i : intArray) {
            // Cannot use Number.add(i), parser error
            Number.add(i);
        }

        // The follow line gives error:
        // Exception in thread "AWT-EventQueue-0" javalangAssertionError
      System.out.println(Number.size());
    }

    public int sum() {
        int s = 0;
        // A temp assignment works for printing the size
        // int size = Number.size();
        System.out.println(Number.size());

        // Cannot use Number.size() in the for expression
        for(int i = 0; i <= Number.size(); i++) {
            // Cannot use Number.get in any way here, parser error
            int num = Number.get(i);
            s = s + num;
        }

        return s;
    }
}

{
    int [] cc = new int[4];
    cc[0] = 4; cc[1] = 3; cc[2] = 2; cc[3] =  1;

    NumberTest test = new NumberTest(cc);
    System.out.println(test.sum());
}

/* GOLD:
___________________________________________________________
4
4
line 0: List.get(): List index out-of-range: 4 on list of size 4
*/
