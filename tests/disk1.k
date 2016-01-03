class Disk {
    public Disk(int size) {
        size_ = size;
    }

    public int size() const { return size_ }
    public String toString() const { return size_.toString() }

    private int size_;
}

{
        Disk output1 = new Disk(2);
        System.out.println(output1.toString());

        String str = "1";
        switch(str) {
            case "1": 
                // NOTE: Runtime error here
                // Exception in thread "AWT-EventQueue-0" javalangAssertionError
                Disk output = new Disk(1);
                System.out.println(output.toString());
                if (1 == 1) {
                   int k = new int();
                   break;
                }
                break;
        }
        System.out.println(output1.toString());
}

/* GOLD:
___________________________________________________________
2
1
2
*/
