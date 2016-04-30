/* UI Library */

class Rect {
	public int x, y;
	public int w, h;

	public Rect(int ax, int ay, int aw, int ah){
		x = ax.clone; y = ay.clone; w = aw.clone; h = ah.clone;
	}

	public int cx(){ return x + w / 2; }
	public int cy(){ return y + h / 2; }

	public boolean contains(Point p){
		return (x <= p.x) && (p.x < x + w) && (y <= p.y) && (p.y < y + h);
	}
}

interface Control {
	public void    paint(Display display);
	public boolean handleEvent(Event event);
}

class Display extends Panel {
	public Display(Point size) {
		Panel();

		frame_  = new Frame("Book Checkout");
		frame_.add("Center", this);
		frame_.resize(size.x + 20, size.y + 40);
		frame_.setVisible(true);

		controls_ = new List<Control>();
	}

	public void invalidate(){
		clear();
		for(Control control: controls_){
			control.paint(this);
		}
		repaint();
	}

	public boolean handleEvent(Event event){
		boolean handled = false;
		for(Control control: controls_){
			if(control.handleEvent(event)){
				handled = true;
			}
		}
		if(handled){
			invalidate();
		}
		return handled;
	}

	public void add(Control control)    { controls_.add(control);    }
	public void remove(Control control) { controls_.remove(control); }

	private Frame frame_;
	private List<Control> controls_;
}

class Buttons implements Control {
	public Buttons() {
		list_ = new List<Button>();
	}

	public void paint(Display display){
		for(Button button: list_){
			button.paint(display);
		}
	}

	public boolean handleEvent(Event event){
		boolean handled = false;
		for(Button button: list_){
			if(button.handleEvent(event)){
				handled = true;
			}
		}
		return handled;
	}

	private List<Button> list_;

	public void add(Button button)    { list_.add(button);    }
	public void remove(Button button) { list_.remove(button); }
}

interface ButtonHandler {
	 public void handleButtonPress(Object buttonId)
}

class Button {
	private ButtonHandler handler_;
	private String label_;
	private Rect   bounds_;

	public Button(String label, Point p, int radius, ButtonHandler handler){
		handler_ = handler;
		label_ = label.clone;
		bounds_ = new Rect(p.x - radius, p.y - radius, radius * 2, radius * 2);

		down_ = false;
	}

	private boolean down_;

	public void press(){
		if(handler_ != null){
			handler_.handleButtonPress(this);
		}
	}

	public void pushed(){
		down_ = true;
	}
	public void released(){
		if(down_){
			down_ = false;
			press();
		}
	}
	public void clear(){
		down_ = false;
	}

	 public boolean handleEvent(Event event){
		if(!contains(new Point(event.x, event.y))){
			if(event.id == Event.MOUSE_UP){
				clear();
			}
			return false;
		}

		if(event.id == Event.MOUSE_DOWN){
			pushed();
			return true;
		}
		if(event.id == Event.MOUSE_UP){
			released();
			return true;
		}
		return false;
	}

	public boolean contains(Point p){
		int dx = p.x - bounds_.cx;
		int dy = p.y - bounds_.cy;
		double d = Math.sqrt(dx**2 + dy**2);
		return d < bounds_.h / 2; //TODO: use correct oval check
	}

	public void paint(Display display) {
		if(down_){
			display.setColor(new Color(200, 200, 200));
		} else {
			display.setColor(new Color(220, 220, 220));
		}

		display.fillOval(bounds_.x, bounds_.y, bounds_.w, bounds_.h);

		display.setColor(new Color(30, 30, 30));
		Point sz = display.measureString(label_);
		display.drawString(bounds_.cx - sz.x/2, bounds_.cy + sz.y/3, label_);
	}
}

class TextListView implements Control {
	int Padding = 10;
	int TitleHeight = 20;
	int TitleMargin = 10;

	private Rect bounds_;
	private String title_;

	public TextListView(String title, Rect bounds){
		bounds_ = bounds.clone;
		title_ = title.clone;
		lines_ = new List<String>();
	}

	public void paint(Display display){
		display.setColor(new Color(240, 240, 240));
		display.fillRect(bounds_.x, bounds_.y, bounds_.w, bounds_.h);

		display.setColor(new Color(200, 200, 200));
		display.drawRect(bounds_.x, bounds_.y, bounds_.w, bounds_.h);

		Point CharSize = display.measureString("Q");

		display.setColor(new Color(30, 30, 30));
		Point cursor = new Point(bounds_.x + Padding, bounds_.y + Padding + CharSize.y);

		display.drawString(cursor.x, cursor.y, title_);
		cursor.y = cursor.y + CharSize.y + TitleMargin;

		int row = 1;
		for (String line: lines_){
			String text = row.toString() + ". " + line;
			display.drawString(cursor.x, cursor.y, text);
			cursor.y = cursor.y + CharSize.y;
			row++;
		}
	}

	public boolean handleEvent(Event event){ return false; }

	private List<String> lines_;
	public void add(String line)    { lines_.add(line); }
	public void remove(String line) { lines_.remove(line); }
	public void clear()             { lines_ = new List<String>(); }
}

class TextLogView implements Control {
	int Padding = 10;

	private Rect bounds_;

	public TextLogView(Rect bounds){
		bounds_ = bounds.clone;
		lines_ = new List<Line>();
		lineCount_ = 0;
	}

	public void paint(Display display){
		display.setColor(new Color(240, 240, 240));
		display.fillRect(bounds_.x, bounds_.y, bounds_.w, bounds_.h);

		display.setColor(new Color(200, 200, 200));
		display.drawRect(bounds_.x, bounds_.y, bounds_.w, bounds_.h);

		Point CharSize = display.measureString("Q");

		display.setColor(new Color(30, 30, 30));
		Point cursor = new Point(bounds_.x + Padding, bounds_.y + CharSize.y);

		int linesInFrame = (bounds_.h - 2 * Padding) / CharSize.y;

		int currentLine = 0;
		for (Line line: lines_){
			if(lineCount_ - currentLine <= linesInFrame){
				if(line.text == "\n"){
					cursor.x = bounds_.x + Padding;
					cursor.y = cursor.y + CharSize.y;
				} else {
					display.setColor(line.color);
					display.drawString(cursor.x, cursor.y, line.text);

					Point lineSize = display.measureString(line.text);
					cursor.x = cursor.x + lineSize.x;
				}
			}
			if(line.text == "\n"){
				currentLine++;
			}
		}
	}

	public boolean handleEvent(Event event){ return false; }

	class Line {
		public Line(String atext, Color acolor) {
			text = atext.clone;
			color = acolor.clone;
		}
		public String text;
		public Color  color;
	}

	private List<Line> lines_;
	private int        lineCount_;
	public void print(String line, Color color)   {
		if(line == ""){
			return;
		}
		lines_.add(new Line(line, color));
		if(line == "\n"){
			lineCount_++;
		}
	}
	public void println(String line, Color color) {
		print(line, color);
		print("\n", color);
	}
}

/* Card verification */

interface Card {
	public int triesLeft();
	public boolean locked();
	public boolean verify(String pin);
}

class Card_Mock implements Card {
	private int     triesLeft_;
	private String  pin_;

	public Card_Mock(){
		triesLeft_ = 3;
		pin_ = "1234";
	}

	public int triesLeft()            { return triesLeft_; }
	public boolean locked()           { return triesLeft_ <= 0; }
	public boolean verify(String pin) {
		if(locked()){
			return false;
		}
		boolean success = pin_ == pin;
		if(success){
			triesLeft_ = 3;
		} else {
			triesLeft_ = triesLeft_ - 1;
		}
		return success;
	}
}

class CardReader_Mock {
	private Card current_;

	public CardReader_Mock()      { current_ = null; }
	public Card current()         { return current_; }
	public void waitForCard(int timeout) {
		Thread.sleep(timeout);
		current_ = new Card_Mock();
	}
	public void remove()          { current_ = null; }
}

/* Book */

interface Item {
	public double id();
	public String name();
}

class ItemSet {
	private List<Item> items_;

	public ItemSet() {
		items_ = new List<Item>();
	}

	public void include(Item item) { if(!contains(item)){ items_.add(item) }; }
	public void exclude(Item item) { items_.remove(item); }
	public void clear()            { items_ = new List<Item>(); }

	public List<Item> list()       { return items_; }
	public boolean contains(Item item) {
		for(Item existing: items_){
			if(existing.id() == item.id()){
				return true;
			}
		}
		return false;
	}
}

class Book implements Item {
	private String name_;
	private double id_;

	public Book(String name) {
		name_ = name.clone;
		id_ = Math.random()
	}

	public double id()   const { return id_;   }
	public String name() const { return name_; }
}

interface ItemScanner {
	public Item scan();
}

class ItemScanner_Mock implements ItemScanner {
	public void ItemScanner_Mock(){
		available_ = new List<Item>();

		available_.add(new Book("War and Peace"));
		available_.add(new Book("Anna Karenina"));
		available_.add(new Book("The Kingdom of God is Within You"));
		available_.add(new Book("Master and Man"))
	}

	private List<Item> available_;
	public Item scan(){
		int i = (Math.random() * 1000.0).toInteger();
		while(i >= available_.size){
			i = i - available_.size;
		}

		return available_.get(i);
	}
}

class LinePrinter {
	public void println(String text) {
		System.out.println("| " + text);
	}

	public void cut() {
		System.out.println("----------------------------------");
	}
}

context BorrowLibraryItems implements ButtonHandler {
	role Guide {
		public void welcome(){
			Transcript.line("Welcome to the automatic borrowing machine.");
			CardReader.validate();
			Transcript.line("Thank you for using the automated borrowing machine.");
		}

		public void menu(){
			BookScanner.scanItem();

			while(true){
				Transcript.linefeed();
				Transcript.line("1. Borrow an item.");
				Transcript.line("2. Finish with a receipt.");
				Transcript.line("3. Finish without a receipt.");

				Transcript.text("Select: ");
				String choice = Keypad.readChar();
				Transcript.line(choice);

				switch(choice){
					case "1":
						BookScanner.scanItem();
						break;
					case "2":
						Printer.printReceipt();
						return;
					case "3":
						return;
					default:
						Transcript.error("Invalid option.");
						break;
				}
			}
		}
	}

	role BookScanner {
		public void scanItem(){
			Transcript.line("Place the book under the Scanner.");
			Item item = scan();
			CheckoutList.add(item);
		}
	} requires {
		Item scan();
	}

	role CheckoutList {
		public void add(Item item) {
			if(contains(item)){
				Transcript.warning("'" + item.name() + "' already scanned.");
			} else {
				include(item);
				Transcript.text("'" + item.name() + "' added.");
				CheckoutListView.update();
			}
		}

		public void removeAll()   {
			clear();
			Transcript.line("All items removed from checkout list!");
			CheckoutListView.update();
		}
		public List<Item> items() { return list(); }
	} requires {
		void    include(Item item);
		void    exclude(Item item);
		void    clear();
		boolean contains(Item item);
		List<Item> list();
	}

	role CheckoutListView {
		public void update(){
			clear();
			for(Item item: CheckoutList.items()){
				add(item.name());
			}
			Screen.update();
		}
	} requires {
		void add(String line);
		void clear();
	}

	role Printer {
		public void printReceipt(){
			cut();
			println("Borrowed items:");
			int row = 1;
			for(Item item: CheckoutList.items()){
				println(row.toString() + ". " + item.name());
				row++;
			}
			cut();
		}
	} requires {
		void println(String text);
        void cut();
	}

	role CardReader {
		public void validate(){
			waitForCard(100);
			if(current() == null){
				Transcript.error("No card inserted!");
				return;
			}

			Card card = current();
			boolean verified = false;

			while(!card.locked()){
				String pin = Keypad.enterPin();

				if(!card.verify(pin)){
					Transcript.error("Invalid PIN. " + card.triesLeft().toString() + " tries left.");
				} else {
					verified = true;
					break;
				}
			}

			if(card.locked()){
				Transcript.error("Card locked!");
			} else {
				if (!verified) {
					Transcript.error("Verification failed!");
				} else {
					Guide.menu();
				}
			}

			Transcript.line("DON'T FORGET YOUR CARD!");
		}
	} requires {
		public void waitForCard(int timeout);
		public Card current();
		public void remove();
	}

	role Transcript { // should be a prop
		public void text(String s)       { print  (s,    new Color(30, 30, 30));  Screen.update(); }
		public void line(String text)    { println(text, new Color(30, 30, 30));  Screen.update(); }
		public void error(String text)   { println(text, new Color(220, 30, 30)); Screen.update(); }
		public void warning(String text) { println(text, new Color(180, 180, 30)); Screen.update(); }

		public void passwordChar() { print("*", new Color(30, 30, 180)); Screen.update(); }
		public void linefeed()     { print("\n", new Color(30, 30, 30)); }
	} requires {
		void print(String text, Color color);
		void println(String text, Color color);
	}

	role Keypad { // should be a prop
		public String readChar(){
			int a = read();
			assert(a != null);
			return a.to1CharString();
		}

		public String enterPin(){
			Transcript.text("ENTER PIN: ");

			String pin = "";
			for(int i = 0; i < 4; i++){
				String key = readChar();
				pin = pin + key;
				Transcript.passwordChar();
			}
			Transcript.linefeed();
			return pin;
		}
	} requires {
		int read();
	}

	role Screen {
		public void update(){ invalidate(); }
	} requires {
		void invalidate();
	}

	//////////////////////////
	private Display display_;

	public BorrowLibraryItems(){
		display_ = new Display(new Point(600, 400));
		display_.add(new Button("Clear", new Point( 30, 30), 20, this));

		TextLogView  transcript = new TextLogView(new Rect(10, 60, 325, 300));
		TextListView checkoutlistview = new TextListView("Checkout List", new Rect(355, 60, 235, 300));

		display_.add(checkoutlistview);
		display_.add(transcript);

		// setup roles
		Screen = display_;
		Keypad = new InputStream(display_);
		Transcript = transcript;
		CardReader = new CardReader_Mock();

		BookScanner = new ItemScanner_Mock();
		Printer = new LinePrinter();

		CheckoutList = new ItemSet();
		CheckoutListView = checkoutlistview;
	}

	public void handleButtonPress(Object button){
		CheckoutList.removeAll();
	}

	public void start(){
		Guide.welcome();
	}
}

new BorrowLibraryItems().start()