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
line 24: Expression 'Amount' is not of the right type
line 25: Method `decreaseBalance' not declared in Role `SourceAccount'.
line 35: Method `increaseBalance' not declared in Role `DestinationAccount'.
___________________________________________________________
*/
