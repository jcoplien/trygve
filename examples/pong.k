interface EventHandler {
   public void handleEvent(Event e);
}

class MyPanel extends Panel {
   int XSIZE = 1000;
   int YSIZE = 600;
   
   public int xsize() { return XSIZE }
   public int ysize() { return YSIZE }

   public MyPanel() {
      Panel();
      eventHandler_ = null;
      frame_  = new Frame("Bouncy");
      frame_.add("Center", this);
      frame_.resize(XSIZE, YSIZE);
      frame_.setVisible(true);
      repaint()
   }

/*
   public boolean handleEvent(Event event) {
      int eventId = event.id.clone;
      boolean retval = true;

      if (eventId == Event.MOUSE_DOWN) {
          System.err.print("v")
      } else if (eventId == Event.MOUSE_UP) {
         System.err.print("^");
      } else if (eventId == Event.MOUSE_MOVED) {
         System.err.print("<");
      }
      if (eventHandler_ != null) {
         eventHandler_.handleEvent(event)
      }
      return retval
   }
*/

   public void setEventHandler(EventHandler eh) {
      eventHandler_ = eh
      // System.err.println("setEventHandler called")
   }

   public Frame frame() { return frame_ }
 
   private Frame frame_;
   private EventHandler eventHandler_
}

class Point {
   public Point(int x, int y) { x_ = x.clone; y_ = y.clone }
   public void setXY(int x, int y)  { x_ = x.clone; y_ = y.clone }
   public int x() { return x_ }
   public int y() { return y_ }
   int x_, y_;
}

class BallObject extends Point {
   public BallObject(int x, int y) {
      Point(x, y);
      velocity_ = new Point(5, 5)
   }
   public int radius() { return RADIUS }
   public Point velocity() { return velocity_ }
   public void setVelocity(Point velocity) { velocity_ = velocity }
   Point velocity_;
   private int RADIUS = 10
}

context Arena implements EventHandler {
   public Arena() {
      MyPanel panel = new MyPanel();
      ThePanel = panel;
      Ball = new BallObject(50, 50);
      panel.setEventHandler(this)
   }
   public void run() {
      do {
         // int i = Inp.rd();
         ThePanel.clear();
         Ball.draw();
         Ball.velocityAdjust();
         Ball.step();
         ThePanel.refresh();
         Thread.sleep(20)
      } while (true)
   }
   role ThePanel {
      public void drawCircle(int x, int y, int r) {
         setForeground(Color.blue);
         fillOval(x+r, y+r, r, r)
      }
      public int maxX() { return xsize() }
      public int maxY() { return ysize() }
      public void refresh() { repaint() }
      public void clear() {
         removeAll();
         setForeground(Color.white);
         drawRect(0, 0, xsize(), ysize());
         setForeground(Color.white)
      }
   } requires {
      void fillOval(int x, int y, int h, int w);
      void drawRect(int x, int y, int h, int w);
      int xsize();
      int ysize();
      void repaint();
      void removeAll();
      void setForeground(Color color)
   }
   role Ball {
      public void draw() {
         ThePanel.drawCircle(x(), y(), radius())
      }
      public void step() {
         setXY(x() + velocity().x, y() + velocity().y);
      }
      public void velocityAdjust() {
         int maxX = ThePanel.maxX(), maxY = ThePanel.maxY();
         int xv = velocity().x, yv = velocity().y;
         xv = if (xv == 0) 5 else xv;
         yv = if (yv == 0) 5 else yv;
         int newX = xv.clone, newY = yv.clone;
         if (velocity().x() > 0) {
            if (x() + (radius() * 2) >= maxX) newX = -xv
         } else if (x() + 15 <= 0) newX = -xv;

         if (velocity().y() > 0) {
            if (y() + (radius() * 2) + 30 >= maxY) newY = -yv
         } else if (y() + (radius() * 2) <= 0) newY = -yv;

         Point retval = new Point(newX, newY);
         setVelocity(retval)
      }
   } requires {
      void setXY(int x, int y);
      int x();
      int y();
      Point velocity();
      void setVelocity(Point velocity);
      int radius()
   }

   private boolean EventHandler(Event e) {
      if (e.id == Event.MOUSE_MOVE) {
      }
      return true
   }
}

new Arena().run()
