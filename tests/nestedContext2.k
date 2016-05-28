context Outer {
    public Outer(Class1 o1, Class2 o2) {
        System.out.println("Outer.Outer")
        R1 = o1;
        R2 = o2
    }

    context Inner {
       public Inner(Class1 o3, Class2 o4) {
          System.out.println("Inner.Inner");
          R3 = o3;
          R4 = o4;
          System.out.println("Inner.Inner a bit further");
          R3.aFunc()
       }
       role R3 {
          public void aFunc() {
             System.out.println("R3.aFunc()")
             C1func()
          }
       } requires {
          void C1func()
       }
       role R4 {
       }
       public void trigger() {
           System.out.println("Inner.trigger()");
           R3.aFunc()
       }
    }

    role R1 {
       public void test() {
          System.out.println("R1.test()")
          R2.foobar()
       }
    }

    role R2 {
       public void foobar() {
          System.out.println("R2.foobar()")
          Inner anInnerContext = new Inner(new Class1(),
                                           new Class2());
          anInnerContext.trigger();
          C2func()
       }
    } requires {
       void C2func();
    }

    public void test() {
        System.out.println("Outer.test()")
        R1.test();
    }
}

class Class1 {
    public void C1func() {
       context C3 {
          role DeepRole {
             public void deepRole1() {
                 System.out.println("Class1.C3.DeepRole.deepRole1")
             }
          } requires {
             public void C1funcb();
          }
          public C3(Class1 cs1) {
               DeepRole = cs1
          }
          public void trigger() {
             DeepRole.deepRole1()
          }
       }

       System.out.println("Class1.C1func()");

       C3 newContext = new C3(new Class1());
       newContext.trigger()
    }
    public void C1funcb() {
       System.out.println("Class1.C1funcb()")
    }
}

class Class2 {
    public void C2func() {
       System.out.println("Class2.C2func()")
    }
}


{
   (new Outer(new Class1(), new Class2())).test()
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Outer.Outer
Outer.test()
R1.test()
R2.foobar()
Inner.Inner
Inner.Inner a bit further
R3.aFunc()
Class1.C1func()
Class1.C3.DeepRole.deepRole1
Inner.trigger()
R3.aFunc()
Class1.C1func()
Class1.C3.DeepRole.deepRole1
Class2.C2func()
*/
