context C1 {
    // Can remedy by reversing the order
    // of the declarations. But it needs a
    // better diagnostic
    public C1() {
        log = System.out;
        log.pl("test");
    }

    role log {
        public void pl(String message) { println(message) }
    }
    requires {
        void println(String msg);
    }
}

{
    new C1();
}

/* In the old days this wouldn't parse. Now it works.
line 7: Script `println(String)' not declared in Role `log'.
line 7: 	Try moving the declaration of `log' before the definition of method `C1()'.
___________________________________________________________
*/

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
test
*/
