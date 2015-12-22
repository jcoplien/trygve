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
