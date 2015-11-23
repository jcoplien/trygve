class MyClass {
     public void mcf(String p1, int p2) { }
}

context TransferMoney {
     role SourceAccount {
         public void transferIn(int j, int k, String l) {
         }
     }
     public TransferMoney(MyClass c)
     {
        SourceAccount.transferIn(100, 200, "argument")
        MyClass mcv;
        mcv.mcf("abc", 30)
     }
     int f(MyClass x) {
         int g = 3;
         return g;
     }
}

{ TransferMoney transaction = new TransferMoney() transaction.f(new MyClass()) }
