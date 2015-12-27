class Person {
    public Person(String name) {
        assert(
            name.length() > 0, 
            "Existence comes with a nameth upon thy should'rs"
        );
        name_ = name;
    }

    public String name() const {
        return name_;
    }

    private String name_;
}

class Ordinal {
    public Ordinal(int order) {
        assert(order >= 1 && order <= 12, 
            "'rdinals only supp'rt'd from one to twelve");

        order_ = order;
    }

    public String name() const {
        String name = "";
        if(order_ == 1)  name = "first";
        if(order_ == 2)  name = "second";
        if(order_ == 3)  name = "third";
        if(order_ == 4)  name = "fourth";
        if(order_ == 5)  name = "fifth";
        if(order_ == 6)  name = "sixth";
        if(order_ == 7)  name = "seventh";
        if(order_ == 8)  name = "eighth";
        if(order_ == 9)  name = "ninth";
        if(order_ == 10) name = "tenth";
        if(order_ == 11) name = "eleventh";
        if(order_ == 12) name = "twelveth";        
        return name;
    }

    private int order_;
}

context TwelveDaysOfChristmas {
    stageprop Giver {} requires {
        String name() const;
    }

    stageprop Receiver {} requires {
        String name() const;
    }

    stageprop Singers {
        public void sing(String text) {
            System.out.print(text);
        }
        public void rest() {
            System.out.println();
        }
        public void pause(String gift, int order) {
            // The beautiful rings deserve a pause
            if(gift == "five gold rings") {
                System.out.println(";");
                return;
            }

            // End second last gift with "and" to complete the sentence
            if(order == 2) {
                System.out.println();
                System.out.print("and ");
                return;
            }

            // Anything else, separate with a comma and
            // a newline for every three gifts.
            if(gift != "a partridge in a pear tree") {
                System.out.print(", ");
                // The order is backwards in the song, so it needs to be reversed.
                if((11-order) % 3 == 0) System.out.println();
                return;
            }
        }        
    } requires {
        void print(String text) const;
        void println(String text) const;
    }

    role Gifts {} requires {
        String get(int pos);
        int size();
    }

    stageprop Christmas {
/*
        public String dayOnSeason(Date date) {
            // NOTE: Date::getTime would be useful here
            // https://docs.oracle.com/javase/7/docs/api/java/util/Date.html#getTime()
            int startDate = this.getDate();

            // NOTE: It seems like this expression doesn't work
            int currentDate = date.getDate() + if(date.getDate() < startDate) then 31 else 0;

            return new Ordinal(currentDate - startDate + 1).name();
        }
*/
    } requires {
        int getDate() const;
    }

    public TwelveDaysOfChristmas(Giver giver, Receiver receiver, Gifts gifts) {
/*
        // NOTE: Cannot test for null (Expression '<NULL>' is not of the right type)
        assert(giver != null, 
            "Certes elusive, a gift without a giv'r");

        // NOTE: Cannot test for null
        assert(receiver != null, 
            "'tis still thy owneth gift, with nay one to receiveth it");
*/

        assert(gifts.size() == 12, 
            "Thither shouldst be twelve presents f'r each day of christmas");

        Giver = giver;
        Gifts = gifts;
        Receiver = receiver;
        Christmas = new Date(2015, 12, 25);
        Singers = System.out;
    }

    public void carol() {
        for(int day = 1; day <= 12; day = day + 1) {
            Date currentDate = new Date(2015, 11, day+24);
            //String ordinal = Christmas.dayOnSeason(currentDate);
            String ordinal = new Ordinal(day).name();

            Singers.sing(
                "On the " + ordinal + " day of Christmas " +
                Giver.name() + " gave to " + Receiver.name()
            );
            Singers.rest();

            for(int i = day; i > 0; i = i - 1) {
                String gift = Gifts.get(i-1);
                Singers.sing(gift);
                Singers.pause(gift, i);
            }

            Singers.sing(".");
            Singers.rest();
            Singers.rest();
        }
    }
}

{
    Person giver = new Person("my true love");
    Person receiver = new Person("me");

    List<String> gifts = new List<String>();
    gifts.add("a partridge in a pear tree");
    gifts.add("two turtle doves");
    gifts.add("three french hens");
    gifts.add("four calling birds");
    gifts.add("five gold rings");
    gifts.add("six geese a-laying");
    gifts.add("seven swans a-swimming");
    gifts.add("eight maids a-milking");
    gifts.add("nine ladies dancing");
    gifts.add("ten lords a-leping");
    gifts.add("eleven pipers piping");
    gifts.add("twelve drummers drumming");

    new TwelveDaysOfChristmas(giver, receiver, gifts).carol();
}

/* GOLD:
___________________________________________________________

On the first day of Christmas my true love gave to me
a partridge in a pear tree.

On the second day of Christmas my true love gave to me
two turtle doves
and a partridge in a pear tree.

On the third day of Christmas my true love gave to me
three french hens, two turtle doves
and a partridge in a pear tree.

On the fourth day of Christmas my true love gave to me
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the fifth day of Christmas my true love gave to me
five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the sixth day of Christmas my true love gave to me
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the seventh day of Christmas my true love gave to me
seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the eigth day of Christmas my true love gave to me
eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the ninth day of Christmas my true love gave to me
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the tenth day of Christmas my true love gave to me
ten lords a-leping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the eleventh day of Christmas my true love gave to me
eleven pipers piping, ten lords a-leping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the twelfth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords a-leping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.
*/
