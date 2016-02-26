{
   Set<int> set = new Set<int>();
   set.add(1);
   set.add(2);
   set.add(3);
   set.add(5);
   set.add(8);
   System.out.print("Set size is ").println(set.size());
   for (int a : set) { System.out.println(a); }

   Map<String, String> aMap = new Map<String, String>();
   aMap.put("a", "A");
   aMap.put("b", "B");
   aMap.put("c", "C");

   System.out.println("Now maps");

   for (String key : aMap) { System.out.println(aMap.get(key)) }
}
