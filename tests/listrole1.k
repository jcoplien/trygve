context Account {
    public Account(List<LedgerEntry> ledgers) {
        Ledgers = ledgers;
    }

    public Account() {
        Ledgers = new List<LedgerEntry>();
        test_ = new List<Amount>();
    }

    private List<Amount> test_;

    public void increaseBalance(Amount amount) {
        Ledgers.addEntry("depositing", amount + 0);
        test_.add(amount);
    }

    public void decreaseBalance(Amount amount) {
        Ledgers.addEntry("withdrawing", 0 - (amount + 0));
    }

    public int getBalance() {
        return Ledgers.calculateBalance();
    }

    stageprop Amount {} requires {
       int compareTo(int amt) const;
       int +(int amt) const;
       int -(int amt) const;
    }

    role Ledgers {
        public void addEntry(String message, int amount) {
            Ledgers.add(new LedgerEntry(message, amount));
        }

        public int calculateBalance() {
            int sum = 0;
            for (int i = 0; i < Ledgers.size(); i++) {
                sum = sum + Ledgers.get(i).amount();
            }
            return sum;
        }
    }
    requires {
        void add(LedgerEntry entry);
        LedgerEntry get(int pos);
        int size();
    }
}

class LedgerEntry
{
    private String message_;
    private int amount_;

    public LedgerEntry(String message, int amount) {
        message_ = message.clone;
        amount_ = amount.clone;
    }

    public String message() {
        return message_;
    }

    public int amount() {
        return amount_;
    }
}

{
    Account src = new Account();
    src.increaseBalance(30);
    System.out.println(src.getBalance());
}