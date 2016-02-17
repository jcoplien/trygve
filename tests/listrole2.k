context Account {
    private List<Amount> test_;

    public void increaseBalance(Amount amount) {
        test_.add(amount);
    }
 
    stageprop Amount {
    }

    public Account() {
       test_ = new List<Amount>()
    }
}

{
    Account src = new Account();
    src.increaseBalance(30);
}

/* GOLD:
___________________________________________________________
*/