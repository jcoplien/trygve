class MyPanel extends Panel {
   class ALine {
      public ALine(String text, Color color) {
         text_ = text.clone; color_ = color.clone
      }
      public String text() { return text_ }
      public Color color() { return color_ }
      String text_; Color color_;
   }
   public MyPanel() {
      Panel();
      frame_  = new Frame("Book Checkout");
      frame_.add("Center", this);
      frame_.resize(600, 400);
      text_ = new List<ALine>();
      repaint();
      setColor(Color.blue);
      reframe()
   }
   private void reframe() {
      // Text outline
      Color saveColor = panel_.getColor();
      panel_.setColor(Color.white)
      panel_.fillRect(20, 85, 550, 150)
      panel_.setColor(saveColor)

      drawRect(20, 85, 550, 150);
   }

/*
   public void handleEvent(Event event) {
      int eventId = event.id.clone;
      if (eventId == Event.MOUSE_DOWN) {
          System.out.print("MOUSE_DOWN event: ")
      } else if (eventId == Event.MOUSE_UP) {
          System.out.print("MOUSE_UP event: ")
      } else if (eventId == Event.MOUSE_DRAG) {
          System.out.print("MOUSE_DRAG event: ")
      } else if (eventId == Event.KEY_PRESS) {
          System.out.print("KEY_PRESS event: \"");
          String key = event.keyString.clone;
          System.out.print(key).print("\" ")
      } else {
          System.out.print(eventId).print(" ")
      }
      System.out.print("handled event at (")
                .print(event.x).print(", ")
                .print(event.y).println(")")
   }
*/
   public void print(String text) {
      text_.add(new ALine(text.clone, this.getColor()));
      redisplayTextBuffer()
   }
   public void println(String text) {
      this.print(text);
      text_.add(new ALine("\n", Color.blue))
   }
   private int width(String s) {
      return (s.length() * 6)
   }
   private int numberOfLinesIn(List<ALine> lines) {
      int retval = 0;
      for (ALine aLine : lines)
         if (aLine.text() == "\n") retval++;
      return retval
   }
   public void redisplayTextBuffer() {
      int y = 100, x = 25;
      int numberOfLinesInFrame = 9, currentLine = 1;
      clear();
      drawRect(20, 85, 550, 150);

      int numberOfLinesInText = numberOfLinesIn(text_);
      for (ALine aLine : text_) {
         if (numberOfLinesInText - currentLine < numberOfLinesInFrame) {
            if (aLine.text() == "\n") {
               x = 25;
               y = y + 14
            } else {
               this.setColor(aLine.color());
               this.drawString(x, y, aLine.text());
System.err.print("last output to screen: ").println(aLine.text())
               x = x + width(aLine.text())
            }
         } else if (aLine.text() == "\n") {
            currentLine++;
         }
      }
      this.setColor(Color.blue);
      repaint();
      frame_.setVisible(true);
   }
   public void flush() {
      redisplayTextBuffer()
   }

   private List<ALine> text_;
   private Frame frame_
}

/*class PanelTest {
   public PanelTest() {
   }

   public void test() {
      Color realColor = new Color(0.99, 0.99, 0.99);
      Color intColor = new Color(255, 255, 255);
      Panel panel = new MyPanel();
      Frame frame = new Frame("First Frame");
      frame.add("Center", panel);
      frame.resize(300, 300);
      panel.setColor(Color.black);
      panel.drawString(120, 120, "Hello world");
      panel.setColor(Color.green);
      panel.drawLine(120, 122, 190, 122);
      panel.setColor(Color.blue);
      panel.drawRect(20, 20, 70, 100);
      panel.setColor(Color.red);
      panel.drawOval(45, 100, 80, 80);
      frame.setVisible(true)
   }
}
*/

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
            while (++attempts <= 3) {
                Screen.displayEnterPin();
                if (Keypad.readPin() == scanCardPin()) break
                else Screen.displayInvalidPin();
            }

            if (attempts > 3)
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

    role Screen {
        public void displayWelcomeMessage() {
            println("Welcome to the automatic library borrowing service!");
            CardReader.validateCardPin();
        }
        public void displayScannedItems() {
            println("Borrowed items:");
            for(ItemRecord item : BookScanner.currentScannedItems()) {
                setColor(Color.magenta);
                println(item.name());
                setColor(Color.blue);
            }
            selectOptions();
        }
        public void displayItemAlreadyScanned(ItemRecord item) {
            setColor(Color.red);
            println("Item '"+item.name()+"' has already been scanned.");
            setColor(Color.blue);
            selectOptions();
        }
        public void displayEnterPin() {
            print("Enter PIN: ");
            flush()
        }
        public void displayText(String text) {
           print(text);
        }
        public void displayInvalidPinAttempts() {
            setColor(Color.red);
            println("Three invalid attempts, please remove your card.")
            setColor(Color.blue);
        }
        public void displayInvalidPin() {
            setColor(Color.red);
            println("Invalid PIN code.")
            setColor(Color.blue);
        }
        public void echoInvisibleChar() {
            setColor(new Color(52, 200, 5));
            print("*")
            setColor(Color.blue);
        }
        public void closeLine() {
            println("")
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
            print("Select option: ");

            String choice = Keypad.readChar();
            Screen.setColor(new Color(52, 200, 5));
            Screen.println(choice);
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
        public void update() { redisplayTextBuffer(); }
    } requires {
        void print(String text);
        void println(String text);
        void flush();
        void repaint();
        void clear();
        void redisplayTextBuffer();
        void setColor(Color color);
        Color getColor()	// temporary
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
        public String readChar() {
           int a = read();
           assert (a isnot null);
           Screen.update();
           return a.to1CharString()
        }
        public String readPin() {
            String retval = "";
            for (int i = 0; i < 4; i++) {
                String theChar = readChar();
                retval = retval + theChar;
                Screen.echoInvisibleChar();
                Screen.update();
            }
            Screen.closeLine();
            return retval
        }
    } requires {
        int read();
    }

    ///////////////////////////////////////////////////////////

    public BorrowLibraryItems() {
        MyPanel panel = new MyPanel();
        CardReader = new MockCardReader();
        BookScanner = new MockBookScanner();
        Screen = panel;
        Printer = new ScreenPrinter();
        InputStream keypadInput = new InputStream(panel);
        Keypad = keypadInput
    }

    public void start() {
        Screen.displayWelcomeMessage();
    }
}

new BorrowLibraryItems().start()

