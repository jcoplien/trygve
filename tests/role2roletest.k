context Context {
   role R1 {
       
   } requires {
       String toString() const;
   }
   role R2 {
       public void R2script() {
          String st = R1.toString();
          System.out.println(st)
       }
   }
   public void test() {
      R2.R2script()
   }
   public Context(int r1Player, int r2Player) {
      R1 = r1Player;
      R2 = r2Player
   }
}

{
   (new Context(15, 25)).test()
}
  