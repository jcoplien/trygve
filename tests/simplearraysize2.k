class Test {
    public Test() {
       List<int>list = new List<int>();
       list.add(1)  list.add(2)
       int j = list.size();
       System.out.print("size=").println(j)
    }
}

(new Test())

/* GOLD:
___________________________________________________________
size=2
*/
