context Context {
   role Role1 {
      public int checksum() {
         String base10Rep = Role1.toString();
         int repLength = base10Rep.length();
         String lastDigit = base10Rep.substring(repLength-1,
                                             repLength);
         System.out.print("toString is ").println(base10Rep)
         System.out.print("Last digit is ").println(lastDigit)
         int somecalculationbasedonlastdigit = 456
         return somecalculationbasedonlastdigit
      }
   } requires {
      String toString()
   }
   public Context(int role1Player) {
      Role1 = role1Player
   }
   public void trigger() {
      int checksum = Role1.checksum()
      System.out.print("Checksum is ").println(checksum)
   }
}

{
   int anInt = 123;
   Context c = new Context(anInt);
   c.trigger()
}

/* GOLD:
___________________________________________________________
toString is 123
Last digit is 3
Checksum is 456
*/
