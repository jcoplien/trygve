// D for DATA

class MyPanel extends Panel {
   int XSIZE = 1000;
   int YSIZE = 600;
   
   public int xsize() const { return XSIZE }
   public int ysize() const { return YSIZE }

   public MyPanel() {
      Panel();
      frame_  = new Frame("Mouse Info Test");
      frame_.add("Center", this);
      frame_.resize(xsize(), ysize());
      frame_.setVisible(true);
      drawRect(0, 0, xsize(), ysize());
      repaint()
   }

   public Frame frame() { return frame_ }
 
   private Frame frame_
}


// C for CONTEXT

context Arena {
   public Arena() {
      MyPanel panel = new MyPanel();
      THE_PANEL = panel;
      THE_PANEL.clear();
   }
   public void run() {
      do {
         THE_PANEL.clearObjects();
         System.err.println(MouseInfo.getPointerInfo().getLocation().x)
         Thread.sleep(20)
      } while (true)
   }

// I for  INTERACTON
   role THE_PANEL {
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
}

new Arena().run()
