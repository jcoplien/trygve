class MyPanel extends Panel {
   public MyPanel() {
      Panel()
   }
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
}

class PanelTest {
   public PanelTest() {
   }

   public void test() {
      Color realColor = new Color(0.99, 0.99, 0.99);
      Color intColor = new Color(255, 255, 255);
      Panel panel = new MyPanel();
      Frame frame = new Frame("First Frame");
      frame.add("Center", panel);
      frame.resize(300, 300);
      panel.drawString(120, 120, "Hello world");
      panel.drawLine(120, 122, 190, 122);
      panel.drawRect(20, 20, 70, 100);
      panel.drawOval(45, 100, 80, 80);
      frame.setVisible(true)
   }
}

new PanelTest().test()
