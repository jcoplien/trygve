context RoleViolation {
   role R1 {
      // Ensures that Role-player count can be
      // incremented only by invoking increment method
      public void increment() { this.incr() }
      public int val() { return this.value() }
   } requires {
      void incr();   // exposed only to R1 methods, or the world?
      int value()
   }
   role R2 {
      public void m2() { R1.incr() }
   }
   public RoleViolation() {
     R1 = new Role1Player();
     R2 = new Role2Player()
   }
   public void run() {
     assert(R1.val == 1)
     R1.increment()
     assert(R1.val == 2)
     R2.m2()
     assert(R1.val == 2, "counter should be 2")
   }
}

class Role1Player {
public Role1Player() { value_ = 1 }
public void incr() { value_++ }
public int value() { return value_ }
       int value_
}

class Role2Player { }

new RoleViolation().run()