class Currency
{
    public Currency(double amount) {
        amount_ = amount.clone
    }
    public Currency +(Currency amount) {
        assert(false)
        return this;
    }
    public Currency -(Currency amount) {
        assert(false)
        return this;
    }
    public Currency() {
    }
    public String name() const
    {
        assert(false);
        return ""
    }
    public String sign() const
    {
        assert(false);
        return ""
    }
    public double amountInEuro() const {
        return 0.0
    }
    public double amount() const {
        return amount_
    }
    public String toString() const {
       return amountInEuro().toString()
    }
    public int compareTo(Currency other) {
       if (amount() > other.amount()) return 1
       else if (amount() < other.amount()) return -1;
       return 0
    }

    private double amount_
}

class Euro extends Currency {
    public Euro(double amount) {
        Currency(amount)
    }
    public Euro -(Currency amount) {
        return new Euro(amount() - amount.amountInEuro())
    }
    public Euro +(Currency amount) {
        return new Euro(amount() + amount.amountInEuro())
    }
    public String name() const
    {
        return "Euro";
    }
    public String sign() const
    {
        return "€";
    }
    public double amountInEuro() const
    {
        return amount()
    }
    public String toString() const {
        return amount().toString()
    }
}

class Account
{
    public Account() {
       acct_ = 1234
    }
    public String accountID() const {
       return acct_.toString()    
    }
    public Currency availableBalance() const { assert(false); return null }
    public void increaseBalance(Currency amount) { assert(false) }
    public void decreaseBalance(Currency amount) { assert(false) }
    public void updateLog(String message, Date dt, Currency amount) {
        assert(false)
    }
    
    private int acct_
}

class CheckingAccount extends Account {
    public CheckingAccount() {
        availableBalance_ = new Euro(100.00)
    }
    public Currency availableBalance() const
    {
        return availableBalance_
    }
    public void decreaseBalance(Currency c) {
        availableBalance_ = availableBalance_ -  c
    }
    public void updateLog(String message, Date t, Currency c) const {
        System.out.print("account: ").print(accountID())
                  .print(" CheckingAccount::updateLog(\"").print(message)
                  .print("\", ").print(t.toString()).print(", ")
                  .print(c.toString()).print(")")
                  .println()
    }
    public void increaseBalance(Currency c) {
        availableBalance_ = availableBalance_ + c
    }

    private Currency availableBalance_
}

class SavingsAccount extends Account {
    public SavingsAccount() {
        availableBalance_ = new Euro(0.00)
    }
    public Currency availableBalance() const {
        return availableBalance_
    }
    public void decreaseBalance(Currency c) {
        assert(c > availableBalance_);
        availableBalance_ = availableBalance_ - c
    }

    public void updateLog(String logMessage, Date timeOfTransaction,
                               Currency amountForTransaction) const {
        assert(logMessage.length() > 0);
        assert(logMessage.length() < MAX_BUFFER_SIZE);
        // assert(new Date() < timeOfTransaction);
        System.out.print("account: ").print(accountID())
                  .print(" SavingsAccount::updateLog(\"").print(logMessage)
                  .print("\", ").print(timeOfTransaction.toString())
                  .print(", ").print(amountForTransaction.toString())
                  .print(")").println()
    }
    public void increaseBalance(Currency c) {
        availableBalance_ = availableBalance_ + c
    }

    private Currency availableBalance_;
    private int  MAX_BUFFER_SIZE = 256
}

class InvestmentAccount extends Account
{
    public InvestmentAccount() {
       availableBalance_ = new Euro(0.00)
    }
    public Currency availableBalance() const {
        return availableBalance_
    }
    public void increaseBalance(Currency c) {
        availableBalance_ =  availableBalance_ + c
    }
    public void decreaseBalance(Currency c) {
        availableBalance_ = availableBalance_ - c;
    }
    public void updateLog(String s, Date t, Currency c) const {
       System.out.print("account: ").print(accountID()) 
                 .print(" InvestmentAccount::updateLog(\"")
                 .print(s).print("\", ").print(t.toString())
                 .print(", ").print(c.toString()).print(")")
                 .println()
    }

    private Currency availableBalance_;
}

class Creditor
{
    public Creditor(Account account) {
        account_ = account
    }
    public Account account() { return account_ }
    public Currency amountOwed() const { return new Currency(0.0) }

    private Account account_
}

class ElectricCompany extends Creditor
{
    public ElectricCompany() {
        Creditor(new CheckingAccount())
    }
    public Currency amountOwed() const {
        return new Euro(15.0)
    }
}

class GasCompany extends Creditor
{
    public GasCompany() {
        Creditor( new SavingsAccount());
        account().increaseBalance(new Euro(500.00))    // start off with a balance of 500
    }
    public Currency amountOwed() const {
        return new Euro(18.76)
    }
}

context TransferMoneyContext
{
    // Roles

    role AMOUNT {
        public Currency(double amount);
        public Currency +(Currency amount);
        public Currency -(Currency amount);
        public String name() const;
        public String sign() const;
        public double amountInEuro() const;
        public double amount() const;
        public String toString() const;
        public int compareTo(Currency other) 
    } requires {
        Currency(double amount);
        Currency +(Currency amount);
        Currency -(Currency amount);
        String name() const;
        String sign() const;
        double amountInEuro() const;
        double amount() const;
        String toString() const;
        int compareTo(Currency other) 
    }

    role GUI
    {
        public void displayScreen(int displayCode)
    } requires {
        void displayScreen(int displayCode)
    }
    
    role SOURCE_ACCOUNT {
        public void transferTo() {
            // This code is reviewable and meaningfully testable with stubs!
            int SUCCESS_DEPOSIT_SCREEN = 10;

            // beginTransaction();
            
            if (this.availableBalance() < AMOUNT) {
                // endTransaction();
                assert(false, "Unavailable balance")
            } else {
                this.decreaseBalance(AMOUNT);
                DESTINATION_ACCOUNT.increaseBalance(AMOUNT);
                this.updateLog("Transfer Out", new Date(), AMOUNT);
                DESTINATION_ACCOUNT.updateLog("Transfer In", new Date(), AMOUNT);
            }
            // GUI.displayScreen(SUCCESS_DEPOSIT_SCREEN);
            // endTransaction()
        }
    } requires {
        void decreaseBalance(Currency amount);
        Currency availableBalance() const;
        void updateLog(String msg, Date time, Currency amount)
    }

    role DESTINATION_ACCOUNT {
        public void transferFrom() {
            this.increaseBalance(AMOUNT);
            this.updateLog("Transfer in", new Date(), AMOUNT);
        }
        public void increaseBalance(Currency amount);
        public void updateLog(String msg, Date time, Currency amount)
    } requires {
        void increaseBalance(Currency amount);
        void updateLog(String msg, Date time, Currency amount)
    }

    public TransferMoneyContext(Currency amount, Account source, Account destination)
    {
        SOURCE_ACCOUNT = source;
        DESTINATION_ACCOUNT = destination;
        AMOUNT = amount
    }
    public TransferMoneyContext() {
        lookupBindings()
    }
    public void doit()
    {
        SOURCE_ACCOUNT.transferTo()
    }
    private void lookupBindings() {
        // These are somewhat arbitrary and for illustrative
        // purposes. The simulate a database lookup
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.increaseBalance(new Euro(100.00)); // prime it with some money
    
        SOURCE_ACCOUNT = investmentAccount;
        DESTINATION_ACCOUNT = new SavingsAccount();
        DESTINATION_ACCOUNT.increaseBalance(new Euro(500.00)); // start it off with money
        AMOUNT = new Euro(30.00)
    }
}

context PayBillsContext
{
    public PayBillsContext() {
       lookupBindings
    }
    role [] CREDITORS {
    } requires {
       Currency amountOwed()
    }
    stageprop SOURCE_ACCOUNT {
        public String accountID() const;
        public Currency availableBalance() const;
        public void increaseBalance(Currency amount) unused;
        public void decreaseBalance(Currency amount) unused;
        public void updateLog(String message, Date dt, Currency amount) unused
    } requires {
        String accountID() const;
        Currency availableBalance() const;
        void increaseBalance(Currency amount);
        void decreaseBalance(Currency amount);
        void updateLog(String message, Date dt, Currency amount)
    }

    // Use case behaviours

    public void doit()  {
        for (Creditor credit : CREDITORS) {
            // Error
            SOURCE_ACCOUNT.updateLog("Error check", new Date(),
                                new Currency(0.0));
            // Note that here we invoke another Use Case
            TransferMoneyContext xfer = new TransferMoneyContext(
                                                      credit.amountOwed(),
                                                      SOURCE_ACCOUNT,
                                                      credit.account());
            xfer.doit()
        }
    }

    private void lookupBindings() {
       // These are somewhat arbitrary and for illustrative
       // purposes. The simulate a database lookup
       InvestmentAccount investmentAccount = new InvestmentAccount();
       investmentAccount.increaseBalance(new Euro(100.00)); // prime it with some money
       SOURCE_ACCOUNT = investmentAccount;

       Creditor [] creditors = new Creditor [2];

       creditors[0] = new ElectricCompany();
       creditors[1] = new GasCompany();

       CREDITORS = creditors
    }
}


{
    // Main

    TransferMoneyContext  aNewUseCase = new TransferMoneyContext();
    aNewUseCase.doit();

    PayBillsContext anotherNewUseCase = new PayBillsContext();
    anotherNewUseCase.doit()
}

/* GOLD:
line 187: WARNING: substituting object of type `Currency' for `[new Euro]'.
line 198: WARNING: substituting object of type `Currency' for `[new Euro]'.
line 326: Script `updateLog(String message, Date dt, Currency amount)' is declared as unused in `SOURCE_ACCOUNT' at line 312.
2 warnings, 1 error.
___________________________________________________________
*/