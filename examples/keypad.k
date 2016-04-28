// UI library
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

class Mouse {
	public Point position;
	public boolean down;
	public boolean released;

	public Mouse(){
		position = new Point(0, 0);
	}

	public void clear(){
		released = false;
	}

	public boolean clicked(Rect r){
		if(!r.contains(position)){
			return false;
		}
		if(released){
			released = false;
			return true;
		}
		return false;
	}
}

class MousePanel extends Panel {
	public Mouse mouse;
	public MousePanel(){
		Panel()
		mouse = new Mouse();
	}

	// TODO: use a list to store clicks, otherwise double-clicks and
	//       quick-events may get lost
	public boolean handleEvent(Event event) {
		if(event == null){
			return false;
		}
		if(event.id == Event.MOUSE_MOVE){
			mouse.position.x = event.x.clone;
			mouse.position.y = event.y.clone;
		}
		if(event.id == Event.MOUSE_DOWN){
			mouse.down = true;
		}
		if(event.id == Event.MOUSE_UP){
			mouse.down = false;
			mouse.released = true;
		}
		return true;
	}
}

// Implements basic immediate mode UI
context BasicUI {
	private MousePanel panel_;
	private Frame frame_;
	private Point size_;

	private boolean breaknext_;

	public BasicUI(){
		size_ = new Point(640, 480);
		panel_ = new MousePanel();
		breaknext_ = false;

		frame_ = new Frame("");
		frame_.add("Center", panel_);
		frame_.resize(size_.x, size_.y);

		frame_.setVisible(true);
	}

	public Point size() { return size_; }
	public Mouse mouse() { return panel_.mouse; }

	public void clear()   { panel_.clear(); }
	public void repaint() { panel_.repaint(); }

	public boolean nextEvent() {
		mouse().clear();
		if(breaknext_){
			breaknext_ = false;
			return false;
		}
		repaint();
		clear();
		Thread.sleep(33);
		return true;
	}
	public void breakonce() {
		breaknext_ = true;
	}

	public void color(Color color) { panel_.setColor(color); }
	public void rect(Rect r){
		panel_.fillRect(r.x, r.y, r.w, r.h);
	}
	public void text(String s, Rect r){
		panel_.drawString(r.x + 8 + 10, r.cy + 5, s);
	}
	public boolean button(String s, Rect r){
		if(r.contains(mouse().position)){
			color(new Color(240, 240, 240));
		} else {
			color(new Color(200, 200, 200));
		}
		rect(r);
		color(Color.black);
		text(s, r);
		return mouse().clicked(r);
	}
}

class TileLayout {
	private Point start;
	private Point at;
	private Point size;
	private int column;
	private int columns;
	private int padding;

	public TileLayout(Point astart, Point asize, int acolumns) {
		start = new Point(astart.x.clone, astart.y.clone);
		at  = new Point(astart.x.clone, astart.y.clone);
		size  = new Point(asize.x.clone, asize.y.clone);
		column = 0;
		columns = acolumns.clone;
		padding = 5;
	}

	public Rect row(){
		int left = columns - column;
		Rect r = new Rect(at.x, at.y, (size.x + padding) * left - padding, size.y);
		column = columns.clone;
		advance();
		return r;
	}

	public Rect next(){
		Rect r = new Rect(at.x, at.y, size.x, size.y);
		advance();
		return r;
	}

	public void advance(){
		column++;
		if(column >= columns){
			column = 0;
			at.x = start.x.clone;
			at.y = at.y + size.y + padding;
		} else {
			at.x = at.x + size.x + padding;
		}
	}
}

class Keypad {
	private String content_;
	public Keypad(){
		content_ = "";
	}

	public void clear(){ content_ = ""; }
	public void backspace(){
		if(content_.length > 0){
			content_ = content_.substring(0, content_.length - 1);
		}
	}
	public String content(){ return content_; }
	public void input(String key){ content_ = content_ + key; }
}

context App {
	public App(){
		UI = new BasicUI();
		Keys = new Keypad();
	}
	public void run(){
		UI.eventLoop();
	}

	role Keys {} requires {
		public void clear();
		public void backspace();
		public String content();
		public void input(String key);
	}

	role UI {
		public void eventLoop(){
			while(true){
				while(nextEvent()){
					buttons();
				}
				System.out.println("RESULT: " + Keys.content());

				// alternatively go to a second screen
			}
		}

		public void buttons(){
			TileLayout lay = new TileLayout(new Point(20, 20), new Point(50, 50), 3);

			button(Keys.content(), lay.row);

			if(button("C", lay.next)){ Keys.clear();     }
			if(button("<", lay.next)){ Keys.backspace(); }
			if(button("R", lay.next)){ breakonce() }

			if(button("1", lay.next)){ Keys.input("1"); }
			if(button("2", lay.next)){ Keys.input("2"); }
			if(button("3", lay.next)){ Keys.input("3"); }

			if(button("4", lay.next)){ Keys.input("4"); }
			if(button("5", lay.next)){ Keys.input("5"); }
			if(button("6", lay.next)){ Keys.input("6"); }

			if(button("7", lay.next)){ Keys.input("7"); }
			if(button("8", lay.next)){ Keys.input("8"); }
			if(button("9", lay.next)){ Keys.input("9"); }
		}
	} requires {
		void breakonce();
		boolean nextEvent();
		boolean button(String s, Rect r);
	}
}

new App().run()

