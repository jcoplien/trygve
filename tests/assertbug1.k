class Letter {
    public Letter(String l) {
        String validLetters = "ABCDEFG";

        // This assertion works
        // assert(l != null);

        // Not this one (but using a temp var works)
        // Method `assert' not declared in class `Letter'
        /*
        assert(
            validLetters.contains(l), 
            "Not a valid letter: " + l + " (only A-G allowed)"
        );
        */

        // This one compiles but crashes
        // operator problem?
        assert(l == "A" || l == "B", "Only A or B allowed actually");

        letter_ = l;
    }

    public String letter() {
        return letter_;
    }

    private String letter_;
}

{
    System.out.println(new Letter("A").letter());
}

/* GOLD:
line 21: WARNING: Assignment / initialization does not create a new instance. Both `letter_' and `l' will refer to the same object. Use `l.clone' to create a separate instance.
1 warning, 0 errors.
___________________________________________________________
A
*/
