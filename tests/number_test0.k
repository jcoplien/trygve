context NumberTest {
    role Number {} requires {
        void add(int i);
        int get(int i);
        int size();
    }

    public NumberTest(int [] intArray) {
        Number = new List<int>();
        for(int i : intArray) Number.add(i);

        System.out.println(Number.size());
    }

    public int sum() {
        System.out.println(Number.size());

        int s = 0;
        for(int i = 0; i < Number.size(); i++) {
            s = s + Number.get(i);
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
10
*/
