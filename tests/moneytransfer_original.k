context TransferMoney {
    public TransferMoney(Account source, Account destination, 
                         double amount) {
        SourceAccount = source;
        DestinationAccount = destination;
        Amount = amount;
        Bank = this;
    }
    
    // Execute the use case
    public void run() {
        Bank.transfer();
    }
    
    stageprop Bank {
        public void transfer() {
            SourceAccount.withdraw();
            DestinationAccount.deposit();
        }
    }

    role SourceAccount {    
        public void withdraw() {
            assert (this.getBalance() >= Amount);
            this.decreaseBalance(Amount);
        }
    }
    requires {
        void decreaseBalance(double amt);
        double getBalance();
    }

    role DestinationAccount {
        public void deposit() {
            this.increaseBalance(Amount);
        }
    }
    requires {
        void increaseBalance(double amt);
    }

    stageprop Amount {}
}

class Account {
    private double balance_;

    void increaseBalance(double amount) {
        this.balance_ = this.balance_ + amount;
    }
    
    void decreaseBalance(double amount) {
        this.balance_ = this.balance_ - amount;
    }
    
    double getBalance() {
        return this.balance_;
    }
}

{
    Account src = new Account();
    Account dst = new Account();
    TransferMoney transfer = new TransferMoney(src, dst, 10.0);
    transfer.run();
}

/* GOLD:
line 24: Expression 'current$context.Amount' is not of the right type (double).
line 24: You may not use an object of type 'Amount' as an argument to `>='.
line 25: Type of actual parameter `current$context.Amount' (Amount) in call of `decreaseBalance' does not match type of formal parameter `amt' (double amt)
line 35: Type of actual parameter `current$context.Amount' (Amount) in call of `increaseBalance' does not match type of formal parameter `amt' (double amt)
___________________________________________________________
*/
