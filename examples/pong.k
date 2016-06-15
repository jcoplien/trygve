interface EventHandler {
   public boolean handleEvent(Event e);
}

class MyFrame extends Frame {
   private MyFrame() { Frame("") }
   public MyFrame(String s) { Frame(s) }
   public void windowClosing (Event e) { System.exit(0) }
}

class MyPanel extends Panel {
   int XSIZE = 1000;
   int YSIZE = 600;

   public int xsize() { return XSIZE }
   public int ysize() { return YSIZE }

   public MyPanel() {
      Panel();
      eventHandler_ = null;
      frame_  = new MyFrame("Bouncy");
      frame_.add("Center", this);
      frame_.resize(XSIZE, YSIZE);
      frame_.setVisible(true);
      drawRect(0, 0, xsize(), ysize());
      repaint()
   }

   public boolean handleEvent(Event event) {
      boolean retval = true;
      if (event.id == Event.MOUSE_MOVE) {
         if (eventHandler_ != null) {
            eventHandler_.handleEvent(event)
         }
      }
      return retval
   }

   public void setEventHandler(EventHandler eh) {
      eventHandler_ = eh
   }

   public Frame frame() { return frame_ }

   private Frame frame_;
   private EventHandler eventHandler_
}



class BallObject extends Point {
   public BallObject(int x, int y) {
      Point(x, y);
      velocity_ = new Point(7, 7)
   }
   public int radius() { return RADIUS }
   public Point velocity() { return velocity_ }
   public void setVelocity(Point velocity) { velocity_=velocity }
   Point velocity_;
   private int RADIUS = 15
}

context Arena implements EventHandler {
   public Arena() {
      MyPanel panel = new MyPanel();
      ThePanel = panel;
      Ball = new BallObject(50, 50);
      Paddle = new Point(450, 560);
      panel.setEventHandler(this)
   }
   public void run() {
      do {
         ThePanel.clear();
         Paddle.draw();
         Ball.velocityAdjust();
         Ball.draw()
         ThePanel.flush();

         Ball.step();
         Thread.sleep(20)
      } while (true)
   }
   role ThePanel {
      public void drawCircle(int x, int y, int r) {
         fillOval(x+r, y+r, r, r)
      }
      public void drawPaddle(int xs, int ys, int h, int w) {
         drawRect(xs, ys, h, w)
      }
      public int maxX() { return xsize() }
      public int maxY() { return ysize() }
      public void flush() { repaint() }
      public void clear() {
         setColor(new Color(227, 221, 240));
         fillRect(0, 0, maxX()-1, maxY()-1)
      }
   } requires {
      void fillOval(int x, int y, int h, int w);
      void drawRect(int x, int y, int h, int w);
      void fillRect(int x, int y, int h, int w);
      int xsize();
      int ysize();
      void setColor(Color color);
      void repaint();
   }
   role Paddle {
      public int thickness() const { return 10 }
      public int width() const { return 100 }
      public void draw() {
         ThePanel.setColor(new Color(32, 170, 64));
         ThePanel.drawPaddle(getX - (width() / 2), getY,
                             width(), thickness())
      }
      public void erase() {
         ThePanel.setColor(new Color(239, 237, 239));
         ThePanel.drawPaddle(getX - (width() / 2), getY,
                             width(), thickness())
      }
      public void moveTo(Point p) {
         setXY(p.getX, getY)
      }
      public boolean contains(int p) const {
         return ((p > getX() - (width() / 2)) &&
                (p < getX() + (width() / 2)))
      }
      public int vertical() const { return getY() - thickness() }
   } requires {
      void setXY(int x, int y);
      int getX() const;
      int getY() const
   }
   role Ball {
      public void erase() {
         ThePanel.setColor(new Color(239, 237, 239));
         ThePanel.drawCircle(getX, getY, radius())
      }
      public void draw() {
         ThePanel.setColor(Color.blue);
         ThePanel.drawCircle(getX, getY, radius())
      }
      public void step() {
         setXY(getX + velocity().getX, getY() + velocity().getY);
      }
      private boolean bouncingOffOfPaddle() {
         if (getY + (radius() * 2) > Paddle.vertical()) {
            return Paddle.contains(getX() + radius())
         }
         return false
      }
      public void velocityAdjust() {
         int maxX = ThePanel.maxX(), maxY = ThePanel.maxY();
         int xv = velocity().getX().clone, yv = velocity().getY().clone;
         int newX = xv.clone, newY = yv.clone;
         if (velocity().getX > 0) {
            if (getX() + (radius() * 2) >= maxX) newX = -xv
         } else if (getX() + radius() <= 0) newX = -xv;

         if (velocity().getY() > 0) {
            if (bouncingOffOfPaddle()) newY = -yv
         } else {
            if (getY() + (radius() * 2) <= 0) newY = -yv
         }

         Point retval = new Point(newX, newY);
         setVelocity(retval)
      }
   } requires {
      void setXY(int x, int y);
      int getX();
      int getY();
      Point velocity();
      void setVelocity(Point velocity);
      int radius()
   }

   private boolean handleEvent(Event e) {
      if (e.id == Event.MOUSE_MOVE) {
         Point newLoc = new Point(e.x, e.y);
         Paddle.moveTo(newLoc)
      }
      return true
   }
}

new Arena().run()
