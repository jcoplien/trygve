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
0 warnings, 0 errors.
___________________________________________________________
size=2
*/
