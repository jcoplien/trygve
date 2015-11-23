context Context1 {
   stageprop Role1 {
      public void make() {
         Role1.shadowMake()
      }
      public void shadowMake() {
         System.out.println("Context1.Role1.make()");
         this.manufacture();
         Role2.use()
      }
   } requires {
      void manufacture() const
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
   public void manufacture() const {
      System.out.println("Manufacturer.manufacture()");
   }
}

class Consumer {
   public void consume() {
      System.out.println("Consumer.consume()")
   }
}

(new Context1(new Maker(), new Consumer())).run()

/* GOLD:
___________________________________________________________
Context1::Context1
Context1.Role1.make()
Manufacturer.manufacture()
Context1.Role2.use()
Consumer.consume()
*/
