context TransferMoney {
    stageprop Amount {} requires {
       int compareTo(double amt) const;
       double +(double amt) const;
       double -(double amt) const
    }

    public TransferMoney(Account source, Account destination, double amount) {
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
            // I'll eventually change this so the compiler
            // translates a ">" call to the appropriate
            // compareTo code
            // if ( Amount.compareTo(this.getBalance()) > 0 ) {
            if ( Amount > this.getBalance() ) {
                assert(false, "Insufficient funds");
            }
            this.decreaseBalance(Amount + 0.0);
        }
    }
    requires {
        void decreaseBalance(double amt);
        double getBalance();
    }

    role DestinationAccount {
        public void deposit() {
            this.increaseBalance(Amount + 0.0);
        }
    } requires {
        void increaseBalance(double amt)
    }
}

class Account {
    public Account() {
       balance_ = 0.0
    }
    private double balance_;

    void increaseBalance(double amount) {
        this.balance_ = this.balance_ + amount
    }
    
    void decreaseBalance(double amount) {
        this.balance_ = this.balance_ - amount
    }
    
    double getBalance() {
        return this.balance_;
    }
}

{
    Account src = new Account();
    Account dst = new Account();
    TransferMoney transfer = new TransferMoney(src, dst, 10.0);
    transfer.run()
}


/* GOLD:
___________________________________________________________
Line 34: Assertion failed: Insufficient funds
*/
