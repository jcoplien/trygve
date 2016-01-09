context Account {
    public Account(List<LedgerEntry> ledgers) {
        Ledgers = ledgers;
    }
    
    public Account() {
        Ledgers = new List<LedgerEntry>();
    }
    
    public void increaseBalance(double amount) {
        Ledgers.addEntry("depositing", amount);
    }
    
    public void decreaseBalance(double amount) {
        Ledgers.addEntry("withdrawing", 0.0 - amount);
    }
    
    public double getBalance() {
        return Ledgers.getBalance();
    }
    
    role Ledgers {
        public void addEntry(String message, double amount) {
            Ledgers.add(new LedgerEntry(message, amount));
        }
        
        public double getBalance() {
            double sum = 0.0;
            for (int i = 0; i < Ledgers.size(); i++) {
                LedgerEntry entry = Ledgers.get(i);
                sum = sum + entry.getAmount();
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
    private double amount_;

    public LedgerEntry(String message, double amount) {
        message_ = message.clone;
        amount_ = amount.clone;
    }
    
    public String getMessage() {
        return message_;
    }
    
    public double getAmount() {
        return amount_;
    }
}

{
    Account src = new Account();
    src.increaseBalance(30.0);
    System.out.println(src.getBalance());
}

/* GOLD:
line 27: WARNING: Script declaration for `getBalance' may hide method declared at line 18
___________________________________________________________
30.0
*/
