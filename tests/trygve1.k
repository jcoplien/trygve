class MyClass {
   public MyClass() { }
   public String b() { }
   public void increaseBalanceBy(int a) {
   }
   private void *pointer_;
}

class NewClass {
    public String d(int f, boolean g, int h) {
    }
}

context TransferMoney {
     MyClass a;
     role SourceAccount {
         public void transferIn(int j, int k, String l) {
            a.b();
            a.c();
            return 1;
            this.increaseBalanceBy(j)
         }
     } requires {
         void increaseBalanceBy(int amount)
     }
     public TransferMoney(MyClass c)
     {
        SourceAccount = c;
        SourceAccount.transferIn(100, 200, "argument")
     }
     NewClass c;
     
     boolean g;
     public void amethod() {
        int m, n, o;
        int f = 1, i2 = 6, i3 = 0;
        String s1, s2;
        m = n + o / i2 - i3;
        g = (true || (1 != 2));
        s1 = s2 + "abc";
        c.d(f, g, 1);
        c.d(3, true, "xyz");
        s2 = "xyz";
        s1 = s2
     }
}


{ TransferMoney transaction = new TransferMoney() transferMoney.amethod() }

/* GOLD:
line 6: column 17: no viable alternative at input 'class MyClass {\n   public MyClass() { }\n   public String b() { }\n   public void increaseBalanceBy(int a) {\n   }\n   private void *pointer_'
line 49: Missing main expression.
0 warnings, 2 errors.
___________________________________________________________
*/
