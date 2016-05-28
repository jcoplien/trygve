// List type bug in role-object contract #44

context C {
    public C(A a) {
        R = a;
    }

    role R {
        public List<Object> test2() {
           return list();
        }
    } requires {
        public List<Object> list();
    }

    public void test() {
       List<Object> aList = R.test2();
       System.out.println(aList.size())
    }
}

class A {
    public List<Object> list() {
	List<Object> retval = new List<Object>();
	retval.add(1)
        return retval;
    }
}

{
    new C(new A()).test();
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
1
*/
