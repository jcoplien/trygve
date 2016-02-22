class PanelTest {
   public PanelTest() {
   }

   public void test() {
      Color realColor = new Color(0.99, 0.99, 0.99);
      Color intColor = new Color(255, 255, 255);
      Panel panel = new Panel();
      Frame frame = new Frame("First Frame");
      frame.add("South", panel);
      frame.resize(300, 300);
      panel.drawText(150, 150, "Hello world");
      frame.setVisible(true)
   }
}

new PanelTest().test()
