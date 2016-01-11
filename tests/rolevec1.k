context Context1 {
   role [] RoleArray {
       int count() { return 1; }
       public void doSomething() {
          System.out.print("Context1::doSomething on index ").
                     println(index);
          this.classFunc();
       }
   } requires {
       void classFunc();
   }
   public void trigger(AClass o1, AClass o2) {
      RoleArray[0] = o1;
      RoleArray[1] = o2;
      RoleArray[0].doSomething();
   }
   public void trigger2(AClass [] objectVec) {
      System.out.println("Context1::trigger2");
      RoleArray = objectVec;
      System.out.println("RoleArray has been assigned");
      RoleArray[1].doSomething()
   }
}

class AClass {
   public AClass(int i) {
      i_ = i.clone;
   }
   public void classFunc() {
      int [] intarray;
      int j;
      intarray = new int [3];
      for (int k = 0; k < 3; k++) intarray[k] = k.clone();
      j = intarray[0];
      System.out.print("AClass::classFunc(")
                .print(i_).println(")")
   }
   private int i_;
}

class Test {
   private void test1() {
      System.out.println("Test::test1");
      (new Context1()).trigger(new AClass(1), new AClass(2))
   }
   private void test2() {
      System.out.println("Test::test2");
      AClass [] objectArray = new AClass[2];
      objectArray[0] = new AClass(5);
      objectArray[1] = new AClass(6);
      Context1 anotherContext = new Context1();
      anotherContext.trigger2(objectArray);
   }
   public void test() {
      test1();
      test2();
   }
}

(new Test()).test()

/* GOLD:
___________________________________________________________
Test::test1
Context1::doSomething on index 0
AClass::classFunc(1)
Test::test2
Context1::trigger2
RoleArray has been assigned
Context1::doSomething on index 1
AClass::classFunc(6)
*/
