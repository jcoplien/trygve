class Base {
   int c = 7;
   public Base(Base self) {
      System.out.println("Base constructor");
      System.out.println("-------");
      self.Blah();
      System.out.println("-------");
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

   private Derived() {
      System.out.println("never used");
   }

   public Derived(int h, Base b) { 
      Base(b);
      if (h == 0) {
         System.out.println("Can't use 0. Choosing a sensible default instead");
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
      Derived(i_, this);
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
   Base b = md;
   System.out.println("-------");
   b.Blah();
   System.out.println("-------");
}

/* GOLD:
___________________________________________________________
Base constructor
-------
Blah
line 45: FATAL: TERMINATED: Attempting to invoke method toString on a null object
popping stack: type is RTNullObject
	In script `int.toString'
	Called from script `String.+'
	Called from script `String.+'
	Called from script `String.+'
	Called from script `Foo.Bar'
	Called from script `Derived.DoIt'
	Called from script `Base.Blah'
	Called from script `Base.Base'
	Called from script `Derived.Derived'
	Called from script `MoreDerived.MoreDerived'
*/

