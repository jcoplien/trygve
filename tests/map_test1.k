class MapTest {
   public void test() {
      Map<int,String> map = new Map<int,String>();
      map.put(1, "one");
      map.put(2, "two");
      String s = map.get(1);
      System.out.println(s)
   }
}

(new MapTest()).test()