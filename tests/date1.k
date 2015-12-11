class Test {
   public void test() {
      int one = 1, two = 2, three = 3, four = 4, five = 5;
      int result = one - two + three - four + five +
          two++ + + ++two + three;
      System.out.println(result)
      result = one * two * three / four * five / two++ *
            + ++two * three;
      System.out.println(result)
      Date date;
      date = new Date();
      int year = date.getYear();
      System.out.println(year)
      int idate = date.getDate()
      System.out.println(idate)
   }
}

{new Test().test()}

/* GOLD:
___________________________________________________________
12
54
*/
