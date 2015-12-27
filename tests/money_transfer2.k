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
            for (int i = 1; i < Ledgers.size(); i++) {
                LedgerEntry entry = Ledgers.get(i);
                sum = sum + entry.amount;
            }
            return sum;
        }
    }
    requires {
        LedgerEntry get(int pos);
        int size();
    }
}

class LedgerEntry
{
    private String message_;
    private double amount_;

    public LedgerEntry(String message, double amount) {
        message_ = message;
        amount_ = amount;
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
    src.increaseBalance(30);
    System.out.println(src.getBalance());
}

/* GOLD:
line 27: WARNING: Method declaration for `getBalance' may hide method declared at line 18
line 24: Method `add' not declared in Role `Ledgers'.
line 24: Method `add' not declared in class classname (dubious error — see other messages)
line 31: Field `amount' not found as member of LedgerEntry
line 31: Invalid operands to `+' on type double with operand of type void
line 31: No such operation '+' on type double for argument void
line 31: Operation '+' cannot be applied to type double for argument void
line 63: Method `increaseBalance' not declared in Context Account
line 63: WARNING: Substituting double object for `30' for call to method `increaseBalance' at line 1
___________________________________________________________
*/
