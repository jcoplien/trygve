class MyPanel extends Panel {
   int XSIZE = 600;
   int YSIZE = 400;

   public int xsize() { return XSIZE }
   public int ysize() { return YSIZE }

   public MyPanel() {
      Panel();
      frame_  = new Frame("Bouncy");
      frame_.add("Center", this);
      frame_.resize(600, 400);
      frame_.setVisible(true);
      repaint()
   }

   public boolean handleEvent(Event event) {
      int eventId = event.id.clone;
      boolean retval = false;

      if (eventId == Event.MOUSE_DOWN) {

      } else if (eventId == Event.MOUSE_UP) {

      }
      return retval
   }

   public Frame frame() { return frame_ }

   private Frame frame_
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

context Arena {
   public Arena() {
      MyPanel panel = new MyPanel();
      ThePanel = panel;
      Ball = new BallObject(50, 50);
      Inp = new InputStream(panel);
   }
   public void run() {
      do {
         //int i = Inp.rd();
         int ballXVelocity=5, ballYVelocity=5
         ThePanel.clear();
         Ball.draw();
         Ball.velocityAdjust();
         Ball.step();
         ThePanel.refresh()
      } while (true)
   }
   role Inp {
      public int rd() { return read() }
   } requires {
      int read();
   }
   role ThePanel {
      public void drawCircle(int x, int y, int r) {
         setColor(Color.blue);
         drawOval(x+r, y+r, r, r)
      }
      public int maxX() { return xsize() }
      public int maxY() { return ysize() }
      public void refresh() { repaint() }
      public void clear() {
         setColor(Color.white);
         drawRect(0, 0, xsize(), ysize());
         setColor(Color.white)
      }
   } requires {
      void drawOval(int x, int y, int h, int w);
      void drawRect(int x, int y, int h, int w);
      int xsize();
      int ysize();
      void repaint();
      void setColor(Color color)
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
            if (x() + radius() >= maxX) newX = -xv
         } else if (x() - radius() <= 0) newX = -xv;

         if (velocity().y() > 0) {
            if (y() + radius() >= maxY) newY = -yv
         } else if (y() - radius() <= 0) newY = -yv;

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
}

{
new Arena().run();
System.out.println("done")
}
