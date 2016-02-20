interface ItemRecord {
    public String name();
}

class Book implements ItemRecord {
    public Book(String name) { 
        name_ = name.clone; 
        id_ = Math.random();
    }

    public double id() const { return id_ }
    public String name() const { return name_ }

    private String name_;
    private double id_;
}

class MockBookScanner {
    public MockBookScanner() {
        scannedItems_ = new List<ItemRecord>();

        // A list of books for testing purposes
        books_ = new List<ItemRecord>();
        books_.add(new Book("War and Peace"));
        books_.add(new Book("Anna Karenina"));
        books_.add(new Book("The Kingdom of God is Within You"));
        books_.add(new Book("Master and Man"));
    }

    public ItemRecord scanItem() {
        ItemRecord item;
        if(Math.random() > 0.75) item = books_.get(0)
        else if(Math.random() > 0.75) item = books_.get(1)
        else if(Math.random() > 0.75) item = books_.get(2)
        else item = books_.get(3);

        System.out.println("*** Scanning '" + item.name() + "' ***");
        return item;
    }

    public List<ItemRecord> scannedItems() { return scannedItems_ }
    
    List<ItemRecord> scannedItems_;
    List<ItemRecord> books_;
}

class MockCardReader {
    public String scanCardPin() const { return "1234" }
}

class ScreenPrinter {
    void println(String text) const {
        System.out.println("| " + text);
    }

    void cutPaper() const {
        System.out.println("--------------------------------------------------------");
    }
}

/////////////////////////////////////////////////////////

context BorrowLibraryItems {
    stageprop CardReader {
        public void validateCardPin() {
            int attempts = 0;
            while(++attempts <= 3) {
                Screen.displayEnterPin();

                if(Keypad.readPin() == scanCardPin()) break
                else Screen.displayInvalidPin();
            }

            if(attempts > 3)
                Screen.displayInvalidPinAttempts()
            else
                BookScanner.borrowCurrentLoanItem();
        }
    } requires {
        String scanCardPin() const;
    }

    role BookScanner {
        public void borrowCurrentLoanItem() {
            ItemRecord item = scanItem();

            if(scannedItems().contains(item))
                Screen.displayItemAlreadyScanned(item)
            else {
                scannedItems().add(item);
                Screen.displayScannedItems();
            }
        }
    
        public List<ItemRecord> currentScannedItems() {
            return scannedItems()
        }
    } requires {
        ItemRecord scanItem();
        List<ItemRecord> scannedItems();
    }

    stageprop Screen {
        public void displayWelcomeMessage() {
            println("Welcome to the automatic library borrowing service!");
            CardReader.validateCardPin();
        }
        public void displayScannedItems() {
            println("Borrowed items:");
            for(ItemRecord item : BookScanner.currentScannedItems()) {
                println(item.name());
            }
            selectOptions();
        }
        public void displayItemAlreadyScanned(ItemRecord item) {
            println("Item '"+item.name()+"' has already been scanned.");
            selectOptions();
        }
        public void displayEnterPin() {
            print("Enter PIN: ")
        }
        public void displayInvalidPinAttempts() {
            println("Three invalid attempts, please remove your card.")
        }
        public void displayInvalidPin() {
            println("Invalid PIN code.")
        }
        public void displayRemoveLibraryCard() {
            println("Don't forget your library card!");
            displayThankyouMessage();
        }
        public void displayThankyouMessage() {
            println("Thank you for using our automatic borrowing service!");
        }

        // Not part of the use case, but required because of
        // limitations in the enviroment.
        public void selectOptions() {
            println("");
            println("1. Borrow another item");
            println("2. Finish with a receipt");
            println("3. Finish without a receipt");
            print("Select option and press Enter: ");

            String choice = Keypad.readPin();
            switch(choice) {
                case "2":
                    Printer.printReceiptOfScannedItems();
                    break;
                case "3":
                    this.displayRemoveLibraryCard();
                    break;
                case "1":
                    BookScanner.borrowCurrentLoanItem();
                    break;
                default:
                    println("Invalid option.");
                    this.selectOptions();
                    break;
            }
        }
    } requires {
        void print(String text) const;
        void println(String text) const;
    }
    
    stageprop Printer {
        public void printReceiptOfScannedItems() {
            cutPaper();
            println("Borrowed items:");
            for(ItemRecord item : BookScanner.currentScannedItems()) {
                println(item.name());
            }
            cutPaper();

            Screen.displayRemoveLibraryCard();
        }
    } requires {
        void println(String text) const;
        void cutPaper() const;
    }

    role Keypad {
        public String readPin() {
            return nextLine()
        }
    } requires {
        String nextLine();
    }

    ///////////////////////////////////////////////////////////

    public BorrowLibraryItems() {
        CardReader = new MockCardReader();
        BookScanner = new MockBookScanner();
        Screen = System.out;
        Printer = new ScreenPrinter();
        Keypad = new Scanner(System.in);
    }

    public void start() {
        Screen.displayWelcomeMessage();
    }
}

new BorrowLibraryItems().start()