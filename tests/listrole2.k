context Account {
    private List<Amount> test_;

    public void increaseBalance(Amount amount) {
        test_.add(amount);
    }
 
    stageprop Amount {
    }
}

{
    Account src = new Account();
    src.increaseBalance(30);}
