context C1 {
    stageprop [] Word {
        public String letter() { return toString(); }
    } requires {
        String toString() const;
    }
    role R1 {
        public void tests_word() {
            System.out.println(Word[4].letter); // <-- doesn't work
        }
    }
    public C1(String [] letters) {
        Word = letters;
    }
    public void run() {
        System.out.println(Word[4].letter); // <-- works
        R1.tests_word;
    }   
}
context C2 {
    role Caller {
        public void cues_c1() {
            new C1("0123456789".split("")).run();
        }
    }
    public void run2() {
        Caller.cues_c1;
    }
}
{
    new C2().run2();
}

/* GOLD:
___________________________________________________________
4
4
*/
