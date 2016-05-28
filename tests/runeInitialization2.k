class Base {
   int c = 7;
   public Base() {
      System.out.println("Base constructor");
      Blah();
   }
   public void Blah() {
      System.out.println("base " + c.toString());
   }
}

class Foo {
   private String str_;

   public Foo(String str) {
      str_ = str.clone;
   }
   public void Bar(String h){
      System.out.println("BAR BAR BAR " + h);
   }
}

class Derived extends Base {
   Foo foo;
   int h_;

   private Derived(){
   }

   public Derived(int h) {
      if (h == 0) {
         System.out.println("Can't use 0. Choosing a sensible default
instead");
         h_ = 1442233;
      } else {
         h_ = h.clone;
      }
   }

   public void DoIt() {
      foo = new Foo("Derived initializer");
      foo.Bar("10/" + h_.toString() + " is " + (10/h_).toString());
   }
}

class MoreDerived extends Derived {
   int i_ = 5;
   public MoreDerived() {
      Derived(i_);
      System.out.println("More derived is now constructed");
   }
   public void Blah() {
      System.out.println("Blah");
      DoIt();
      System.out.println("This should be the last thing printed "
         + i_.toString());
   }
}

{
   MoreDerived md = new MoreDerived();
   md.DoIt();
   md.Blah();
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Base constructor
base 7
More derived is now constructed
BAR BAR BAR 10/5 is 2
Blah
BAR BAR BAR 10/5 is 2
This should be the last thing printed 5
*/
