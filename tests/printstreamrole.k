context C1 {
    // Can remedy by reversing the order
    // of the declarations. But it needs a
    // better diagnostic
    public C1() {
        log = System.out;
        log.println("test");
    }

    role log {
    }
    requires {
        void println(String msg);
    }
}

{
    new C1();
}

/* GOLD:
line 7: Script `println(String)' not declared in Role `log'.
line 7: 	Try moving the declaration of `log' before the definition of method `C1()'.
___________________________________________________________
*/
