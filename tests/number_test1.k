context NumberTest {
    role Number {
        public void Add(int i) { add(i) }
        public int  Get(int i) { return get(i) }
        public int  Size()     { return size() }
    } requires {
        void add(int i);
        int get(int i);
        int size();
    }

    public NumberTest(int [] intArray) {
        Number = new List<int>();

        for(int i : intArray) Number.Add(i);

        System.out.println(Number.Size());
    }

    public int sum() {
        System.out.println(Number.Size());

        int s = 0;
        for(int i = 0; i < Number.Size(); i++) {
            s = s + Number.Get(i);
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
