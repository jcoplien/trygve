class Digit {
    public Digit(int digit) {
        this.digit_ = digit; // Gives the error below, removing "this" makes it work
    }

    public int digit() {
        return this.digit_; // "this" works here
    }

    private int digit_;
}

{ int i = (new Digit(1)).digit() System.out.println(i) }

/* GOLD:
___________________________________________________________
1
*/