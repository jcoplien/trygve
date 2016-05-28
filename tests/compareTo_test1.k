class Number {
    int number_
    public Number(int num) { number_ = num.clone }
    public int value() const { return number_ }
    public int compareTo(Number other) const { 
        return if(value() > other.value()) 1
               else if (value() == other.value()) 0
               else -1
    }
    public Number +(Number other) const {
        return new Number(number_ + other.number_)
    }
    public String toString() const { return number_.toString() }
}
{
    Number sn1 = new Number(2)
    Number sn2 = new Number(3)
    System.out.println((sn1 + sn2).toString())
    System.out.println(sn1 > sn2) // <--- execution fails silently
    System.out.println((sn1 + sn2) > sn2)

}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
5
false
true
*/
