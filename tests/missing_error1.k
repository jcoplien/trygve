context TransferMoney {
    
    role DestinationAccount {
        public void deposit() {
            this.increaseBalance(Amount);
        }
    } requires {
        void increaseBalance(double amt);
    }

    stageprop Amount {}
}

{ }