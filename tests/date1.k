class Test {
   public void test() {
      int one = 1, two = 2, three = 3, four = 4, five = 5;
      int result = one - two + three - four + five + two++ + + ++two + three;
      System.out.println(result)
      result = one * two * three / four * five / two++ * + ++two * three;
      System.out.println(result)
      Date date = new Date();
      int year = date.getYear();
      System.out.println(year)
      int date = date.getDate()
      System.out.println(date)
   }
}

{new Test().test()}

/* GOLD:
___________________________________________________________
12
54
*/
