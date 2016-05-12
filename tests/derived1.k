class Base {}
class DerivedA extends Base { }
class DerivedB extends Base {}

class Base2 {
    public void func(Base x) { System.out.println("Base2.func") }
}

class Derived2 extends Base2 {
    public void func(DerivedA x) { System.out.println("Derived2.func") }
}

{
    new Derived2().func(new DerivedB());
}

/* GOLD:
___________________________________________________________
Base2.func
*/
