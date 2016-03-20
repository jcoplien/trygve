class MyPanel extends Panel {
   public MyPanel() {
      Panel();
      frame_  = new Frame("Book Checkout");
      frame_.add("Center", this);
      frame_.resize(600, 400);
      repaint();
      setForeground(Color.blue);
      buttons_ = new List<Button>()
   }
  
   public void addButton(Button button) { buttons_.add(button) }

   public boolean handleEvent(Event event) {
      int eventId = event.id.clone;
      boolean retval = false;

      if (eventId == Event.MOUSE_DOWN) {
          for (Button button : buttons_) {
             if (button.contains(event.x, event.y)) {
                button.wasPushed();
                retval = true;
                break
             }
          }
      } else if (eventId == Event.MOUSE_UP) {
          for (Button button : buttons_) {
             if (button.contains(event.x, event.y)) {
                button.wasReleasedInsideButton();
                retval = true
             } else {
                button.wasReleasedOutsideButton()
             }
          }
      }
      return retval
   }

   public Frame frame() { return frame_ }
 
   private List<Button> buttons_;
   private Frame frame_
}

interface ButtonHandler {
   public void buttonWasReleased(Object buttonId)
}

class Button {
   public Button(MyPanel p, String label, int x, int y, int radius,
                 ButtonHandler handler) {
      panel_ = p;
      x_ = x.clone; y_ = y.clone;
      label_ = label.clone;
      panel_.addButton(this);
      handler_ = handler;
      radius_ = radius.clone;
      state_ = "up";
      this.draw()
   }

   public void wasPushed() { state_="down"; draw() }
   public void wasReleasedInsideButton() {
      state_="up"; handler_.buttonWasReleased(this); draw()
   }
   public void wasReleasedOutsideButton() {
      // In case of a drag that started inside a button
      state_="up"; draw()
   }
 
   public boolean contains(int x, int y) {
      int centerX = x_ + radius_;
      int centerY = y_ + radius_;
      double distanceToCenter =
               Math.sqrt(((x - centerX) ** 2) + ((y - centerY) ** 2));
      return (distanceToCenter < radius_)
   }

   public void draw() {
      panel_.drawOval(x_, y_, radius_, radius_);
      panel_.drawString(x_ + radius_ - ((label_.length() * 6) / 2),
                        y_+ radius_ + 6 , label_)
      Color saveColor = panel_.getForeground();
      if (state_ == "down") {
         panel_.setForeground(Color.blue);
         panel_.fillOval(x_, y_, radius_, radius_)
      } else {
         panel_.setForeground(new Color(0, 255, 255));
         panel_.fillOval(x_+2, y_+1, radius_-2, radius_-1);
         panel_.drawOval(x_, y_, radius_, radius_)
      }
      panel_.setForeground(saveColor)
   }

   public int x() { return x_ }
   public int y() { return y_ }

   private ButtonHandler handler_;
   private MyPanel panel_;
   private String label_;
   private int x_, y_, radius_;
   private String state_
}

interface View {
   public void notifyChanged();
}

class TextListView implements View {
   int TextIndent = 5;
   int TopMargin = 15;
   int FrameWidth = 400;
   int FrameHeight = 75;
   int LineHeight = 14;
   int TitleHeight = 20;

   public TextListView(String title, MyPanel panel, int xOffset, int yOffset) {
      items_ = new List<String>();
      panel_ = panel;
      xOffset_ = xOffset.clone;
      yOffset_ = yOffset.clone;
      frame_ = panel.frame();
      title_ = title.clone;
      clearList_ = new List<Object>();
      reframe()
   }
   public void notifyChanged() {
      int x = xOffset_ + TextIndent;
      int y = yOffset_ + TopMargin + TitleHeight;
      reframe();
      int listCounter = 1;
      for (String item : items_) {
         String toPrint = listCounter.toString() + ". " + item;
         panel_.setForeground(Color.black);
         Object cookie = panel_.drawString(x, y, toPrint);
         clearList_.add(cookie);
         y = y + LineHeight;
         listCounter++
      }
      panel_.setForeground(Color.blue);
   }
   public void removeAll() {
      items_ = new List<String>();
      for (Object o : clearList_) {
         panel_.remove(o)
      }
      clearList_ = new List<Object>();
      this.notifyChanged();
      this.reframe()
   }
   public void addItem(String item) { items_.add(item) }
   private void reframe() {
      // Text outline
      panel_.fillRect(xOffset_, yOffset_ + TitleHeight,
                      FrameWidth, FrameHeight);
      panel_.drawRect(xOffset_, yOffset_ + TitleHeight,
                      FrameWidth, FrameHeight)
   }

   private List<String> items_;
   private List<Object> clearList_
   private MyPanel panel_;
   private int xOffset_, yOffset_;
   private Frame frame_;
   private String title_
}

class TextLogView implements View {
   int TextIndent = 5;
   int TopMargin = 15;
   int CharacterPixelWidth = 6;
   int FrameWidth = 550, FrameHeight = 150;
   int numberOfLinesInFrame = 9;
   int LineHeight = 14;

   public TextLogView(MyPanel panel, int xOffset, int yOffset) {
      text_ = new List<ALine>();
      dynamicStringCookies_ = new List<Object>();
      panel_ = panel;
      frame_ = panel.frame();
      xOffset_ = xOffset.clone;
      yOffset_ = yOffset.clone;
      reframe()
   }
   private void reframe() {
      // Text outline
      panel_.fillRect(xOffset_, yOffset_, FrameWidth, FrameHeight);
      panel_.drawRect(xOffset_, yOffset_, FrameWidth, FrameHeight);
      panel_.drawRect(xOffset_, yOffset_, FrameWidth, FrameHeight)
   }

   class ALine {
      public ALine(String text, Color color) {
         text_ = text.clone; color_ = color.clone
      }
      public String text() { return text_ }
      public Color color() { return color_ }
      String text_; Color color_
   }

   public void print(String text) {
      text_.add(new ALine(text.clone, panel_.getForeground()));
      this.notifyChanged()
   }
   public void println(String text) {
      this.print(text);
      text_.add(new ALine("\n", Color.blue))
   }
   private int widthOf(String s) { return (s.length() * CharacterPixelWidth) }
   public void notifyChanged() {
      int y = yOffset_ + TopMargin, x = xOffset_ + TextIndent;
      int currentLine = 1;
      for (Object a : dynamicStringCookies_) panel_.remove(a);
      dynamicStringCookies_ = new List<Object>();
      reframe();

      int numberOfLinesInText = numberOfLinesIn(text_);
      for (ALine aLine : text_) {
         if (numberOfLinesInText - currentLine < numberOfLinesInFrame) {
            if (aLine.text() == "\n") {
               x = xOffset_ + TextIndent;
               y = y + LineHeight
            } else {
               panel_.setForeground(aLine.color());
               Object cookie = panel_.drawString(x, y, aLine.text());
               dynamicStringCookies_.add(cookie)
               x = x + widthOf(aLine.text())
            }
         } else if (aLine.text() == "\n") {
            currentLine++;
         }
      }
      panel_.setForeground(Color.blue);
      panel_.repaint();
      frame_.setVisible(true)
   }
   private int numberOfLinesIn(List<ALine> lines) {
      int retval = 0;
      for (ALine aLine : lines)
         if (aLine.text() == "\n") retval++;
      return retval
   }

   public void flush() {
      this.notifyChanged()
   }

   private List<ALine> text_;
   private List<Object> dynamicStringCookies_;
   private MyPanel panel_
   private Frame frame_;
   private int xOffset_, yOffset_
}


interface ItemRecord {
    public String name();
}

class Book implements ItemRecord {
    public Book(String name) { 
        name_ = name.clone; 
        id_ = Math.random()
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
        books_.add(new Book("Master and Man"))
    }

    public ItemRecord scanItem() {
        ItemRecord item;
        if(Math.random() > 0.75) item = books_.get(0)
        else if(Math.random() > 0.75) item = books_.get(1)
        else if(Math.random() > 0.75) item = books_.get(2)
        else item = books_.get(3);

        System.out.println("*** Scanning '" + item.name() + "' ***");
        return item
    }

    public List<ItemRecord> scannedItems() { return scannedItems_ }
    
    public void removeItem(ItemRecord item) { scannedItems_.remove(item) }

    List<ItemRecord> scannedItems_;
    List<ItemRecord> books_
}

class MockCardReader {
    public String scanCardPin() const { return "1234" }
}

class ScreenPrinter {
    void println(String text) const {
        System.out.println("| " + text);
    }

    void cutPaper() const {
       System.out.println("----------------------------------------------------");
    }
}

/////////////////////////////////////////////////////////

context BorrowLibraryItems implements ButtonHandler {
    stageprop CardReader {
        public void validateCardPin() {
            int attempts = 0;
            while (++attempts <= 3) {
                Transcript.displayEnterPin();
                if (Keypad.readPin() == scanCardPin()) break
                else Transcript.displayInvalidPin();
            }

            if (attempts > 3)
                Transcript.displayInvalidPinAttempts()
            else
                BookScanner.borrowCurrentLoanItem();
        }
    } requires {
        String scanCardPin() const;
    }

    role BookScanner {
        public void borrowCurrentLoanItem() {
            ItemRecord item = scanItem();

            if (scannedItems().contains(item))
                Transcript.displayItemAlreadyScanned(item)
            else {
                scannedItems().add(item);
                Accountant.displayScannedItems();
            }
        }

        public void removeCheckoutItems() {
           List<ItemRecord> items = currentScannedItems();
           int theIndex = items.size() - 1;
           while (theIndex >= 0) { removeItem(items.get(theIndex--)) }
        }
    
        public List<ItemRecord> currentScannedItems() { return scannedItems() }

        public int numberOfScannedItems() { return scannedItems().size() }
    } requires {
        ItemRecord scanItem();
        List<ItemRecord> scannedItems();
        void removeItem(ItemRecord item)
    }

    role ColorHandler {
       public void setForegroundColor(Color c) { setForeground(c) }
       public Color inputColor() { return new Color(50, 200, 5) }
    } requires {
       void setForeground(Color c)
    }

    role TranscriptView {
        public void addText(String text) { print(text) }
        public void addTextColored(String text, Color c) {
           ColorHandler.setForegroundColor(c);
           print(text);
           ColorHandler.setForegroundColor(Color.blue)
        }
        public void addTextLine(String text) { println(text) }
        public void addTextLineColored(String text, Color c) {
           ColorHandler.setForegroundColor(c);
           println(text);
           ColorHandler.setForegroundColor(Color.blue)
        }
        public void echoInvisibleChar() {
           ColorHandler.setForegroundColor(ColorHandler.inputColor());
           print("*")
           ColorHandler.setForegroundColor(Color.blue);
        }
        public void closeLine() { println("") }
        public void changed() { notifyChanged() }
    } requires {
        void print(String text) const;
        void println(String text) const;
        void notifyChanged()
    }

    role Transcript {
       public void displayEnterPin() {
          TranscriptView.addText("Enter PIN: ");
          TranscriptView.changed();
       }
       public void displayWelcomeMessage() {
           TranscriptView.addTextLine(
              "Welcome to the automatic library borrowing service!");
           CardReader.validateCardPin();
       }
       public void addText(String s) { TranscriptView.addText(s) }
       public void addTextLine(String s) { TranscriptView.addTextLine(s) }
       public void addTextLineColored(String s, Color c) {
          TranscriptView.addTextLineColored(s, c)
       }
       public void displayItemAlreadyScanned(ItemRecord item) {
           TranscriptView.addTextLineColored(
              "Item '"+item.name()+"' has already been scanned.", Color.red);
           Menu.selectOptions();
       }
       public void displayInvalidPinAttempts() {
           TranscriptView.addTextLineColored(
              "Three invalid attempts, please remove your card.",
              Color.red);
       }
       public void displayInvalidPin() {
           TranscriptView.addTextLineColored("Invalid PIN code.", Color.red)
       }
       public void displayRemoveLibraryCard() {
           TranscriptView.addTextLine("Don't forget your library card!");
           displayThankyouMessage();
       }
       public void displayThankyouMessage() {
           TranscriptView.addTextLine(
               "Thank you for using our automatic borrowing service!");
       }
    }

    role Accountant {
        public void displayScannedItems() {
            Transcript.addTextLine("Borrowed items:");
            CheckoutList.reset();
            for (ItemRecord item : BookScanner.currentScannedItems()) {
                Transcript.addTextLineColored(item.name(), Color.magenta);
                CheckoutList.nowIncludes(item.name())
            }
            CheckoutList.changed();
            Menu.selectOptions();
        }
    }
    
    stageprop Printer {
        public void printReceiptOfScannedItems() {
            cutPaper();
            println("Borrowed items:");
            for(ItemRecord item : BookScanner.currentScannedItems()) {
                println(item.name());
            }
            cutPaper();

            Transcript.displayRemoveLibraryCard();
        }
    } requires {
        void println(String text) const;
        void cutPaper() const;
    }

    role Keypad {
        public String readChar() {
           int a = read();
           assert (a isnot null);
           TranscriptView.changed();
           CheckoutList.changed();
           return a.to1CharString()
        }
        public String readPin() {
            String retval = "";
            for (int i = 0; i < 4; i++) {
                String theChar = readChar();
                retval = retval + theChar;
                TranscriptView.echoInvisibleChar();
                TranscriptView.changed();
            }
            TranscriptView.closeLine();
            return retval
        }
    } requires {
        int read();
    }

    role Menu {
        // Not part of the use case, but required because of
        // limitations in the enviroment.
        public void selectOptions() {
            Transcript.addText("");
            Transcript.addTextLine("1. Borrow another item");
            Transcript.addTextLine("2. Finish with a receipt");
            Transcript.addTextLine("3. Finish without a receipt");
            Transcript.addText("Select option: ");

            String choice = Keypad.readChar();
            Transcript.addTextLineColored(choice, ColorHandler.inputColor());
            switch(choice) {
                case "2":
                    Printer.printReceiptOfScannedItems();
                    break;
                case "3":
                    Transcript.displayRemoveLibraryCard();
                    break;
                case "1":
                    BookScanner.borrowCurrentLoanItem();
                    break;
                default:
                    Transcript.addTextLine("Invalid option.");
                    Menu.selectOptions();
                    break;
            }
            CheckoutList.changed()
        }
    }

    role CheckoutList {
       public void nowIncludes(String book) { addItem(book) }
       public void changed() { notifyChanged() }
       public void reset() { removeAll() }
    } requires {
       void addItem(String x);
       void notifyChanged();
       void removeAll()
    }

    stageprop ClearButton {
       public void buttonWasPushed(Object buttonID) {
          if (BookScanner.numberOfScannedItems() > 0) {
             BookScanner.removeCheckoutItems();
             Transcript.addTextLineColored("\nAll checkouts removed!", Color.red);
             Transcript.addText("Select option: ")
          }
          CheckoutList.reset()
       }
    }

    ///////////////////////////////////////////////////////////

    public BorrowLibraryItems() {
        MyPanel panel = new MyPanel();
        ColorHandler = panel;
        CardReader = new MockCardReader();
        BookScanner = new MockBookScanner();
        Transcript = null;
        TranscriptView = new TextLogView(panel, 20, 85);
        panel.drawString(20, 260, "Checked-out books");
        CheckoutList = new TextListView("Checkout List", panel, 20, 250)
        Printer = new ScreenPrinter();
        Keypad = new InputStream(panel);
        ClearButton = new Button(panel, "Clear", 35, 20, 40, this)
    }

    public void buttonWasReleased(Object buttonID) {
       ClearButton.buttonWasPushed(buttonID)
    }

    public void start() { Transcript.displayWelcomeMessage() }
}

new BorrowLibraryItems().start()
