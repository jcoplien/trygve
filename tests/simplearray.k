class AClass {
   public AClass(int param) {
      param_ = param.clone;
      System.out.print("AClass (");
      System.out.print(param);
      System.out.println(")")
   }
   public void display() {
      System.out.println(param_)
   }
   private int param_
}

class ArrayTest {
   public void test() {
      int [] intarray;
      int i;
      intarray = new int[12];
      intarray[6] = 123;
      i = intarray[6];
      System.out.println(i);

      AClass [] classArray;
      classArray = new AClass [7];
      for (int in = 0; in < 7; ++in) {
          classArray[in] = new AClass(in)
      }

      System.out.println("exited for loop");

      AClass ref;
      ref = classArray[4];
      ref.display();
   }
}

{ ArrayTest arrayTest = new ArrayTest()  arrayTest.test() }

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________

123
AClass (0)
AClass (1)
AClass (2)
AClass (3)
AClass (4)
AClass (5)
AClass (6)
exited for loop
4
*/
