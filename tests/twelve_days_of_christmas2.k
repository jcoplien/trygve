class Person {
    public Person(String name) {
        assert(
            name.length() > 0, 
            "Existence comes with a nameth upon thy should'rs"
        );

        name_ = name.clone;
        gifts_ = new List<String>();
    }

    public String name() const { return name_ }
    public List<String> gifts() const { return gifts_ }

    private String name_;
    private List<String> gifts_;
}

class Ordinal {
    public Ordinal(int order) {
        assert(order >= 1 && order <= 12, 
            "'rdinals only supp'rt'd from one to twelve");

        order_ = order.clone;
    }

    public String name() const {
        return switch (order_) {
            case 1:  "first"; break;
            case 2:  "second"; break;
            case 3:  "third"; break;
            case 4:  "fourth"; break;
            case 5:  "fifth"; break;
            case 6:  "sixth"; break;
            case 7:  "seventh"; break;
            case 8:  "eighth"; break;
            case 9:  "ninth"; break;
            case 10: "tenth";  break;
            case 11: "eleventh"; break;
            case 12: "twelveth"; break;
            default: "error"
       }
    }

    private int order_;
}

// Based upon 
// https://groups.google.com/d/msg/object-composition/LKlJEHzFJSg/MYcjYMtFBQAJ
// with some assertion help from http://lingojam.com/EnglishtoShakespearean
context TwelveDaysOfChristmas {
    stageprop Giver {
        public String giftGiverName() const { return name() }
        public void giveAGift() {
            String gift = gifts().remove(0);
            Receiver.receiveGift(gift);
        }
        public boolean hasGiftsLeft() { return gifts().size() > 0 }
    } requires {
        String name() const;
        List<String> gifts() const;
    }

    stageprop Receiver {
        public String giftGetterName() const { return name() }
        public void receiveGift(String gift) {
            gifts().add(gift);
        }
        public String giftNumber(int number) {
            return gifts().get(number-1);
        }
        public int numberOfGifts() {
            return gifts().size();
        }
    } requires {
        String name() const;
        List<String> gifts() const;
    }

    stageprop Singers {
        public void singAboutGivingAGift() {
            int currentDay = 25 + Receiver.numberOfGifts();
            Date currentDate = new Date(2015, 12, currentDay);
            String ordinal = Christmas.dayOfTheSeason(currentDate);

            Singers.sing(
                "On the " + ordinal + " day of Christmas " +
                Giver.giftGiverName() + " gave to " + Receiver.giftGetterName()
            );
            Singers.pause();

            Giver.giveAGift();

            for(int i = Receiver.numberOfGifts(); i > 0; i--) {
                Singers.singAboutGift(Receiver.giftNumber(i));
            }
            Singers.pause();

            if(Giver.hasGiftsLeft())
                Singers.singAboutGivingAGift();
        }

        private void sing(String lyrics) {
            System.out.print(lyrics);
        }
        private void pause() {
            System.out.println();
        }
        private void singAboutGift(String gift) {
            sing(gift);

            switch(gift) {
                // The beautiful rings deserve a longer pause
                case "five gold rings":
                    sing(";"); pause();
                    break;

                case "seven swans a-swimming":
                case "ten lords a-leaping":
                    sing(","); pause();
                    break;

                // End the dove gift (second one) with "and" to complete the sentence
                case "two turtle doves":
                    pause(); sing("and ");
                    break;

                // The partridge ends the verse.
                case "a partridge in a pear tree":
                    sing("."); pause();
                    break;

                // Anything else, separate with a comma
                default:
                    sing(", ");
                    break;
            }
        }        
    } requires {
        void print(String text) const;
        void println(String text) const;
    }

    stageprop Christmas {
        public String dayOfTheSeason(Date date) {
            int startDate = this.getDate();
            int currentDate = date.getDate() + (if (date.getDate() < startDate) 31 else 0);

            return new Ordinal(currentDate - startDate + 1).name();
        }
    } requires {
        int getDate() const;
    }

    public TwelveDaysOfChristmas(Giver giver, Receiver receiver) {
        assert(giver != null, 
            "Certes elusive, a gift without a giv'r");

        assert(receiver != null, 
            "'tis still thy owneth gift, with nay one to receiveth it");

        Giver = giver;
        Receiver = receiver;
        Christmas = new Date(2015, 12, 25);
        Singers = System.out;
    }

    public void carol() {
        Singers.singAboutGivingAGift();
    }
}

{
    Person giver = new Person("my true love");
    Person receiver = new Person("me");

        List<String> gifts = giver.gifts();
        gifts.add("a partridge in a pear tree");
        gifts.add("two turtle doves");
        gifts.add("three french hens");
        gifts.add("four calling birds");
        gifts.add("five gold rings");
        gifts.add("six geese a-laying");
        gifts.add("seven swans a-swimming");
        gifts.add("eight maids a-milking");
        gifts.add("nine ladies dancing");
        gifts.add("ten lords a-leaping");
        gifts.add("eleven pipers piping");
        gifts.add("twelve drummers drumming");

    new TwelveDaysOfChristmas(giver, receiver).carol();
}

/* GOLD:
0 warnings, 0 errors.
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

On the eighth day of Christmas my true love gave to me
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
ten lords a-leaping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the eleventh day of Christmas my true love gave to me
eleven pipers piping, ten lords a-leaping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords a-leaping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.
*/
