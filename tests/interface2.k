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

class SeaPlane extends Vehicle implements Plane, Submarine {
   public void fly() { System.out.println("fly") }
   public void swim() { System.out.println("swim") }
}

{
   Plane test = new SeaPlane();
   test.fly();
}

/* GOLD:
line 14: Class `SeaPlane' does not implement interface `Submarine' because definition of `submerge()' is missing in the class.
0 warnings, 1 error.
___________________________________________________________
*/
