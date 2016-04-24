// D for DATA
//Worked 2016.04.19:02
interface EventHandler {
	public boolean handleEvent(Event e);
}
class MyPanel extends Panel {
	int XSIZE = 1000;
	int YSIZE = 600;
	private Frame frame_;
	private EventHandler eventHandler_
	public MyPanel() {
		Panel();
		eventHandler_ = null;
		frame_	= new Frame("Bouncy");
		frame_.add("Center", this);
		frame_.resize(xsize(), ysize());
		frame_.setVisible(true);
		drawRect(0, 0, xsize(), ysize());
		repaint()
	}
	public int xsize() const { return XSIZE }
	public int ysize() const { return YSIZE }
	public boolean handleEvent( Event event ) {
			boolean retval = true;
			if (event.id == Event.MOUSE_MOVE) {
				if (eventHandler_ != null) {retval = eventHandler_.handleEvent(event)}
			}
			return retval
	}
	public void setEventHandler(EventHandler eh) {eventHandler_ = eh}
	public Frame frame() { return frame_ }
}
class BallObject extends Point {
	Point velocity_;
	private int DIAMETER = 15;
	public BallObject(int x, int y) {
		Point(x, y);
		velocity_ = new Point(7, 7)
	}
	public int diameter() const { return DIAMETER }
	public Point velocity() const { return velocity_ }
	public void setVelocity(Point velocity) { velocity_=velocity }
	public void paint(MyPanel pan) {pan.fillOval(x(),y(), diameter(), diameter()) }
}
// C for CONTEXT
context Arena implements EventHandler {
	public Arena() {
		MyPanel panel = new MyPanel();
		THE_PANEL = panel;
		THE_PANEL.clear();
		BALL = new BallObject(50, 50);
		PADDLE = new Point(450, 560);
		panel.setEventHandler(this)
	} 
	private boolean handleEvent(Event e) {
		if (e.id == Event.MOUSE_MOVE) {PADDLE.moveTo(new Point(e.x, e.y))}
		return true
	}
	public void run() {
		do {
			THE_PANEL.clearObjects();
			PADDLE.erase();
			PADDLE.draw();
			BALL.erase()
			BALL.velocityAdjust();
			BALL.draw();
			BALL.step();
			Thread.sleep(20)
		}while (true)
	}
	// I for	INTERACTON
	role THE_PANEL {
			public void drawCircle(int x, int y, int d) { fillOval(x, y, d, d)}
			public void drawPADDLE(int xs, int ys, int h, int w) { drawRect(xs, ys, h, w) }
			public int maxX() const { return xsize() }
			public int maxY() const { return ysize() }
			public void setColor(Color c) { setForeground(c) }
			public void clearObjects() { removeAll() }
			public void clear() {
			setColor(new Color(227, 221, 240));
			fillRect(0, 0, maxX()-1, maxY()-1)
		}
	} requires {
		void fillOval(int x, int y, int h, int w);
		void drawRect(int x, int y, int h, int w);
		void fillRect(int x, int y, int h, int w);
		int xsize() const;
		int ysize() const;
		void removeAll();
		void setForeground(Color color)
	}
	role PADDLE {
			public int thickness() const { return 10 }
			public int width() const { return 100 }
			public void draw() {
			THE_PANEL.setColor(new Color(32, 170, 64));
			THE_PANEL.drawPADDLE(x() - (width() / 2), y(), width(), thickness())
		}
			public void erase() {
				THE_PANEL.setColor(new Color(239, 237, 239));
				THE_PANEL.drawPADDLE(x() - (width() / 2), y(), width(), thickness())
			}
			public void moveTo(Point p) { setXY(p.x(), y()) }
			public boolean contains(int x) const {
			return (x > x()) && (x < (x() + width()))
		}
			public int vertical() const { return y() - thickness() }
	} requires {
		void setXY(int x, int y);
		int x() const;
		int y() const
	}
	role BALL {
			public void erase() {
				THE_PANEL.setColor(new Color(239, 237, 239));
				THE_PANEL.drawCircle(x(), y(), diameter())
			}
			public void draw() {
				THE_PANEL.setColor(Color.blue);
				THE_PANEL.drawCircle(x(), y(), diameter())
			}
			public void step() { setXY(x() + velocity().x, y() + velocity().y) }
			private boolean bouncingOffOfPADDLE() {
			return if (y() + (diameter()*2) > PADDLE.vertical) PADDLE.contains (x()) else false
		}
			public void velocityAdjust() {
			// System.out.println(y());
			int xv = velocity().x,  yv = velocity().y;
			int newXv = xv.clone,  newYv = yv.clone;
			if (xv > 0) {	// Moving right
				if (x() + diameter() >= THE_PANEL.maxX) newXv = - xv else newXv = xv
			}
			else // Moving left
				if (x() + diameter() <= 0) newXv = - xv else newXv = xv
			if (yv > 0) { // Moving down
				 if (bouncingOffOfPADDLE())newYv = - yv else newYv = yv
			} else // Moving up
				if (y() + (diameter() * 2) <= 0) newYv = - yv else newYv = yv;
			setVelocity(new Point(newXv, newYv))
			}
		} requires {
			void setXY(int x, int y);
			int x() const;
			int y() const;
			Point velocity();
			void setVelocity(Point velocity);
			int diameter()
		}
	}

new Arena().run()

