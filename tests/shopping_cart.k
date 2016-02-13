class Product {
   public Product (String name, double price) {
      name_ = name.clone;
      price_ = price.clone
   }
   public double price() { return price_ }
   public Product copy(double qualifiedPrice) {
      return this.clone
   }

   String name_;
   double price_
}

class User {
   public User(String name, double cash, List<Product> owns) {
      name_ = name.clone;
      cash_ = cash.clone;
      owns_ = owns.clone
   }
   public void takesOwnershipOf(List<Product> products) {
      // owns_.addAll(products)
   }
   public void debitCash(double amount) {
      cash_ = cash_ - amount
   }
   public double cashBalance() {
      return cash_
   }

   String name_;
   double cash_;
   List<Product> owns_
}

class Company {
   public Company(String name, double bankAccount, 
                  Map<int,Product> stock, Set<User> goldMembers) {
      name_ = name.clone;
      bankAccount_ = bankAccount;
      stock_ = stock;
      goldMembers_ = goldMembers
   }
   public void removeFromStock(Product p) {
      // stock_.removeValue(p);
   }
   public Product lookupProductID(int productId) const {
      return stock_.get(productId)
   }
   public Product stock(int productId) const {
      return stock_.get(productId)
   }
   public Set<User> listOfGoldMembers() const {
      return goldMembers_
   }
   public void increaseBalanceBy(double amount) {
      bankAccount_ = bankAccount_ + amount
   }

   String name_;
   double bankAccount_;
   Map<int,Product> stock_;
   Set<User> goldMembers_;
}

class Order {
   User customer_;
   Map<int, Product> items_;

   public Order(User customer, Map<int, Product> items) {
      customer_ = customer;
      if (items == null)
         items_ = new Map<int, Product>()
      else
         items_ = items;
   }
   public Product removeItem(int productId) {
      Product retval = items_.get(productId);
      items_.remove(productId);
      return retval
   }
   public Map<int, Product> getItems() {
      return items_
   }
   public boolean hasItem(int productId) {
      return (items_.get(productId) != null)
   }
}

context PlaceOrder {
   public PlaceOrder(Company comp, User user) {
      warehouse = comp;
      company = comp;
      customer = user;
      cart = new Order(user, null);

      // would be played by something else in a real-world app...
      paymentGateway = comp;
      companyAccount = comp
   }

   // Trigger methods
   public void processProductSelection(int desiredProductId) {
     if (warehouse.has(desiredProductId))
        cart.add(desiredProductId)
   }

   public Map<int,Product> getOrderDetails() {
      return cart.items()
   }

   private boolean processPayment() {
     if (!paymentGateway.confirmSufficientFunds()) return false
     if (!paymentGateway.initiateOrderPayment()) return false
     return warehouse.shipProducts()
   }

   private void processProductRemoval(int productId) {
     cart.remove(productId)
   }


   // Roles (in order of "appearance")
      
   role warehouse {
     public boolean has(int productId) { 
        return warehouse.hasStock(productId)
     }
     public Product get(int productId) {
        return stock(productId)
     }
     public boolean shipProducts() {
       customer.receiveProducts(cart.items());
       for (Product i : cart.items()) 
          warehouse.removeFromStock(i);
       return true
     }
     public boolean hasStock(int productId) {
        Product product = lookupProductID(productId);
        return (product != null)
     }
   } requires {
     void removeFromStock(Product p);
     Product lookupProductID(int productId);
     Product stock(int productId)
   }

   // This role has no methods, because we are only using it
   // to access the goldMembers collection.
   // We still designate it as a role because it is conceptually 
   // part of an interaction with the customer role.
   stageprop company {
      public Set<User> goldMembers() const {
         return listOfGoldMembers()
      }
      public boolean recognizesGoldMember(customer c) const {
         //
         return true
      }
   } requires {
      Set<User> listOfGoldMembers() const;
   }

   role customer {
     public void withdrawFunds(double amountToPay) {
        customer.debitCash(amountToPay)
     }
     public void receiveProducts(List<Product> products) {
        this.takesOwnershipOf(products)
     }
     private boolean isGoldMember() {
        return company.recognizesGoldMember(customer)
     }
     public double discountFactor() {
        return if (isGoldMember()) 0.5 else 1.0
     }
     public double cash() {
        return cashBalance()
     }
   } requires {
     void takesOwnershipOf(List<Product> products);
     void debitCash(double amount);
     double cashBalance();
   }

   role cart {
      public void add(int productId) {
        Product product = warehouse.get(productId)
        double qualifiedPrice = product.price() * 
                                     customer.discountFactor();
        Product qualifiedProduct = product.copy(qualifiedPrice);

        addItem(productId, qualifiedProduct)
      }
      public Product remove(int productId) {
         if (!cart.hasItem(productId))
            return null
         return removeItem(productId)
      }
      public Map<int,Product> items() {
         Map<int,Product> retval = getItems();
         // retval.sort();
         return retval
      }
      public double total() {
         double retval = 0.0;
         for (Product item : cart.items()) {
            retval = retval + item.price()
         }
         return retval
      }
   } requires {
      void addItem(int productId, Product qualifiedProduct);
      boolean hasItem(int productId);
      Product removeItem(int productId);
      Map<int,Product> getItems()
   }

   role paymentGateway {
      public boolean confirmSufficientFunds() {
         return customer.cash() >= cart.total()
      }
      public boolean initiateOrderPayment() {
        double amount = cart.total();
        customer.withdrawFunds(amount);
        companyAccount.depositFunds(amount);
        return true
      }
   }

   role companyAccount {
     public void depositFunds(double amount) {
        this.increaseBalanceBy(amount)
     }
   } requires {
     void increaseBalanceBy(double amount)
   }
}

{
   Set<User> goldMembers = new Set<User>();
   Map<int,Product> gAndCStock = new Map<int,Product>();
   Company gertrudAndCope = new Company("G&C", 100000.0, 
                  gAndCStock, goldMembers);
   Map<int,Product> sueOwns = new Map<int,Product>();
   User sue = new User("Sue", 5000.0, sueOwns);
   PlaceOrder newContext = new PlaceOrder(gertrudAndCope, sue);
}
