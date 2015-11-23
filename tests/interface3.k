interface Plane {
   public void fly();
}

interface Submarine {
   public void swim();
   public void submerge();
}

class Vehicle {
  public void license() { }
}

class SeaPlane extends Vehicle implements Plane, Boat {
   public void fly() { System.out.println("fly") }
   public void swim() { System.out.println("swim") }
}

{
   Plane test = new SeaPlane();
   test.fly();
}

/* GOLD:
line 14: Interface Boat is not declared.
line 14: Class `SeaPlane« is using an undeclared interface: see other error messages
___________________________________________________________
*/
