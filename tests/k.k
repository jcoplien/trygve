class K {
   public K() {
     m = new Map<int, String>()
     m.atPut(1, "one")
     m.atPut(2, "two")
     m.atPut(3, "three")
   }
   Map<int,String> m
   void foo() {
       List<int> l = m.keys
       for (String s: l) {
          System.out.println(s)
       }
   }
}

{ new K().foo }
