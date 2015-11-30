{ String s = "abc"; int i = s.length() System.out.println(i)
  double d = i;
  String s2 = d.toString()  System.out.println(s2)
  System.out.println(i.toString())
  Date date = new Date();
  String dateAsString = date.toString()
  String partOfDate = dateAsString.substring(0,33)
  System.out.println(partOfDate)
}

/* GOLD:
line 2: WARNING: Substituting double object for `i' in assignment to `d'.
___________________________________________________________
3
3.0
3
java.util.GregorianCalendar[time=
*/
