interface Countable {
    public int value() const;
    public int compareTo(Countable other) const;
}
class Number implements Countable {
    int number_;
    public Number(int num) { number_ = num.clone; }
    public int value() const { return number_; }
    public int compareTo(Countable other) const { return value() - other.value(); }
}
{
    Countable one = new Number(1);
    Countable another_one = new Number(1);

    List<Countable> countables = new List<Countable>();
    countables.add(one);

    System.out.println(countables.contains(one));
    System.out.println(countables.contains(another_one));
    System.out.println(countables.contains(new Number(1)));

    System.out.println(one >= another_one);
    System.out.println(one == another_one)
    System.out.println(one is another_one)
}

/* GOLD:
___________________________________________________________
true
true
true
true
true
false
*/