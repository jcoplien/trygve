class Assert {
    public Assert(boolean expression, String message) {
        if(expression == false) {
            System.out.print("ASSERTION FAILED: ");
            System.out.println(message);
            0/0;
        }
    }
}

class CreditCard {
    public CreditCard(List<int> number) {
        assert (number != null, "CreditCard null List arg check")
        number_ = number;
    }

    public CreditCard(int [] number) {
        number_ = new List<int>();
        for(int i : number) number_.add(i);
        assert(number.size() == number_.size())
    }

    public List<int> number() {
        return this.number_;
    }

    private List<int> number_;
}

context LuhnValidation {
    stageprop CheckDigit { } requires {
        String toString() const;
    }

    role Number {
        public boolean validate() {
            String check;
            System.out.println("Validating credit card");
            System.out.print("CheckDigit: ")
                      .println(check = CheckDigit.toString());

            return this.checksum() == check;
        }
        private String checksum() {
            int unit = this.sumDigits() * 9 % 10;
            System.out.print("Calculated checksum: ");
            System.out.println(unit);
            return unit.toString();
        }
        private int sumDigits() {
            int sum = 0;
            boolean doubleDigit = true;
            for(int i = this.size()-2; i >= 0; i--) {
                int curr = this.get(i);

                // Double the value of every second digit
                if (doubleDigit) {
                    curr = curr + curr;
                    // If sum is above 10, add the digits.
                    if (curr > 9) curr = curr - 9;
                }
                doubleDigit = if (doubleDigit == false) true else false;

                System.out.print(curr);
                if (i > 0) System.out.print(" + ");
                sum = sum + curr;
            }
            System.out.print(" = ");
            System.out.println(sum);
            return sum;
        }
    } requires {
        int size() const;
        int get(int pos) const;
    }

    public LuhnValidation(List<int> number) {
       new Assert(number.size() > 1, "Number must be at least two digits.");

        Number = number;
        CheckDigit = number.get(number.size()-1);
    }

    public boolean validate() {
        return Number.validate();
    }
}

{
    // Visa card
    int [] cc = new int[16];
    cc[0] =  4; cc[1] =  0; cc[2] =  1; cc[3] =  1;
    cc[4] =  8; cc[5] =  8; cc[6] =  8; cc[7] =  8;
    cc[8] =  8; cc[9] =  8; cc[10] = 8; cc[11] = 8;
    cc[12] = 1; cc[13] = 8; cc[14] = 8; cc[15] = 2;

    CreditCard creditCard = new CreditCard(cc);
    LuhnValidation luhn = new LuhnValidation(creditCard.number());
    System.out.println(luhn.validate());

    // Other number
    List<int> num = new List<int>();
    num.add(7); num.add(9); num.add(9); num.add(2);
    num.add(7); num.add(3); num.add(9); num.add(8);
    num.add(7); num.add(1); num.add(3);

    LuhnValidation luhn2 = new LuhnValidation(num);
    System.out.println(luhn2.validate());

}

/* GOLD:
___________________________________________________________
Validating credit card
CheckDigit: 2
7 + 8 + 2 + 8 + 7 + 8 + 7 + 8 + 7 + 8 + 7 + 1 + 2 + 0 + 8 = 88
Calculated checksum: 2
true
Validating credit card
CheckDigit: 3
2 + 7 + 7 + 9 + 6 + 7 + 4 + 9 + 9 + 7 = 67
Calculated checksum: 3
true
*/
