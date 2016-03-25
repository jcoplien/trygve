context Context {
   role [] RoleVec {
      public void rvf1() { RoleVec.rvf2(); RoleVec[1].rvf3() }
      void rvf3() { RoleVec.rvf2() }
      void rvf2() {
         System.out.print("rvf2: ").println(toString()) 
      }
   } requires {
      String toString()
   }
   public Context(List<String> roleplayers) {
      RoleVec = roleplayers
   }
   public void test() {
      RoleVec[0].rvf1();
      RoleVec.rvf1()
   }
}

{
  List<String> theList = new List<String>();
  theList.add("123"); theList.add("456");
  new Context(theList).test()
}

/* GOLD:
line 16: Trying to access a Role method `rvf1' on the vector ID `RoleVec'.
___________________________________________________________
*/