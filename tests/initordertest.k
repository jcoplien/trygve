class AnObject {
   public AnObject() {
       System.out.println("AnObject::AnObject");
   }
}

class TestDriver {
   public void test() {
      System.out.println("TestDriver.test");
      AnObject anObject = new AnObject();
      Math.sqrt(16)
   }
}

(new TestDriver()).test()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
TestDriver.test
AnObject::AnObject
*/
