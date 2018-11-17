class DateTest {
   public void test() {
      printDate(new Date(2015, 12, 25), "Date 1")
      printDate(new Date(2015, 12, 32), "Date 2")
      printDate(new Date(0, 0, 0), "Zero")
      printDate(new Date(666, 666, 666), "End of the world")
      printDate(new Date(), "Today")
   }
   
   private void printDate(Date date, String description) {
      System.out.print(description).println(":")
                .print("year=").print(date.getYear())
                .print(", month=").print(date.getMonth())
                .print(", date=").print(date.getDate())
                .println().println()
   }
}

(new DateTest()).test()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Date 1:
year=2015, month=12, date=25

Date 2:
year=2016, month=1, date=1

Zero:
year=2, month=11, date=30

End of the world:
year=723, month=3, date=28

Today:
year=2018, month=11, date=17
*/
