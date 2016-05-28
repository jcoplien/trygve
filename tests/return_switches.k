class Ordinal {
    public Ordinal {}

    public String ordinal1(int order) {
        switch(order) {
            case 1: return "first";
            case 2: return "second";
            case 3: return "third";
            default: assert(false);
        }
        return "ERROR1";
    }

    public String ordinal2(int order) {
        return if(order == 1) "first"
        else if(order == 2) "second"
        else if(order == 3) "third"
        else {
            assert(false); "ERROR2";
        }
    }

    public String ordinal3(int order) {
        return switch(order) {
            case 1: "first"; break;
            case 2: "second"; break;
            case 3: "third"; break;
            default: assert(false); "ERROR3";
        }
    }
}

{
    System.out.println(new Ordinal().ordinal1(1));
    System.out.println(new Ordinal().ordinal2(2));
    System.out.println(new Ordinal().ordinal3(3));
    System.out.println(new Ordinal().ordinal3(4));
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
first
second
third
Line 28: Assertion failed.
*/
