interface Plane {
   public void fly();
}

interface Boat {
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
___________________________________________________________
fly
*/
