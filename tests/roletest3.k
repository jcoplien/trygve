context Context1 {
   role Role1 {
      public void make() {
         if ("abc" == "abc") {
            System.out.println("strings abc and abc are equal")
         } else {
            System.out.println("strings abc and xyz are NOT equal")
         }
         if ("abc" == "xyz") {
            System.out.println("strings abc and xyz are equal")
         } else {
            System.out.println("strings abc and xyz are NOT equal")
         }
         Role1.manufacture()
      }
      public void manufacture() {
         System.out.println("Context1.Role1.shadowMake()");
         this.manufacture();
         Role2.use()
      }
   } requires {
      void manufacture()
   }
   role Role2 {
      public void use() {
         System.out.println("Context1.Role2.use()");
         this.consume()
      }
   } requires {
      void consume();
   }
   public Context1(Maker manufacturer, Consumer consumer) {
      System.out.println("Context1::Context1");
      Role1 = manufacturer;
      Role2 = consumer
   }

   public void run() {
      Role1.make()
   }
}

class Maker {
   public void manufacture() {
      System.out.println("Manufacturer.manufacture()");
   }
}

class Consumer {
   public void consume() {
      System.out.println("Consumer.consume()")
   }
}

{
   Context1 context1 = new Context1(new Maker(), new Consumer())
   context1.run()
}

/* GOLD:
line 2: Defining `manufacture' in Role1 would create multiple scripts of the same name in the same object.
line 34: WARNING: Both class `Maker' and Role `Role1' contain the same script signature `manufacture()'. This results in several scripts of the same name in the same object and may not behave as you expected.
1 warning, 1 error.
___________________________________________________________
*/
