class DateTest {
   public void test() {
      Date newDate  = new Date(2015, 12, 25);
      int year = newDate.getYear()
      int month = newDate.getMonth()
      int date = newDate.getDate()
      System.out.print("year=").print(year)
                .print(", month=").print(month)
                .print(", date=").print(date)
                .println()
      newDate  = new Date(2015, 12, 32);
      year = newDate.getYear()
      month = newDate.getMonth()
      date = newDate.getDate()
      System.out.print("year=").print(year)
                .print(", month=").print(month)
                .print(", date=").print(date)
                .println()
      }
}

(new DateTest()).test()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
year=2015, month=12, date=25
year=2016, month=1, date=1
*/
