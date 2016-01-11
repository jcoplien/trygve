context TransferMoney {
    public TransferMoney(Account source, Account destination, Currency amount) {
        SourceAccount = source;
        DestinationAccount = destination;
        Amount = amount;
        Bank = this;
    }

    stageprop Amount {} requires {
        double value() const;
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
            if ( Amount.value() > this.getBalance().value() ) {
                assert(false, "Insufficient funds");
            }
            this.decreaseBalance(Amount);
        }
    } requires {
        void decreaseBalance(Currency amt);
        Currency getBalance();
    }

    role DestinationAccount {
        public void deposit() {
            this.increaseBalance(Amount);
        }
    } requires {
        void increaseBalance(Currency amt)
    }
}

class Account {
    private Currency balance_;

    public Account() {
       balance_ = new Currency(0.0);
    }

    void increaseBalance(Currency amount) {
        this.balance_ = new Currency(this.balance_.value() + amount.value());
    }

    void decreaseBalance(Currency amount) {
        this.balance_ = new Currency(this.balance_.value() - amount.value());
    }

    Currency getBalance() {
        return this.balance_;
    }
}

class Currency {
    private double value_;
    public Currency(double val) {
        value_ = val;
    }
    public double value() const {
        return value_;
    }
}


{
    Account src = new Account();
    Account dst = new Account();
    TransferMoney transfer = new TransferMoney(src, dst, new Currency(10.0));
    transfer.run()
}

/* GOLD:
line 30: Script `decreaseBalance(Amount)' not declared in Role `SourceAccount'.
line 39: Script `increaseBalance(Amount)' not declared in Role `DestinationAccount'.
line 69: WARNING: Assignment / initialization does not create a new instance. Both `value_' and `val' will refer to the same object. Use `val.clone' to create a separate instance.
___________________________________________________________
*/
