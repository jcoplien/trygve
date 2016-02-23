class MyPanel extends Panel {
   public MyPanel() {
      Panel()
   }
   public void handleEvent(Event event) {
      System.err.println("handled event")
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
      frame.setVisible(true)
   }
}

new PanelTest().test()
