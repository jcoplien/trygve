class MyPanel extends Panel {
   public MyPanel() {
      Panel();
      frame_  = new Frame("Book Checkout");
      frame_.add("Center", this);
      frame_.resize(600, 400);
      repaint();
      setColor(Color.blue);
      buttons_ = new List<Button>()
   }

   public void addButton(Button button) { buttons_.add(button) }

   public boolean handleEvent(Event event) {
      int eventId = event.id.clone;
      boolean retval = false;

      if (eventId == Event.MOUSE_DOWN) {
          for (Button button : buttons_) {
             if (button.contains(event.x, event.y)) {
                button.wasPushed();
                retval = true;
                break
             }
          }
      } else if (eventId == Event.MOUSE_UP) {
          for (Button button : buttons_) {
             if (button.contains(event.x, event.y)) {
                button.wasReleasedInsideButton();
                retval = true
             } else {
                button.wasReleasedOutsideButton()
             }
          }
      }
      return retval
   }

   public Frame frame() { return frame_ }

   private List<Button> buttons_;
   private Frame frame_
}

interface ButtonHandler {
   public void buttonWasReleased(Object buttonId)
}

class Button {
   public Button(MyPanel p, String label, int x, int y, int radius,
                 ButtonHandler handler) {
      panel_ = p;
      x_ = x.clone; y_ = y.clone;
      label_ = label.clone;
      panel_.addButton(this);
      handler_ = handler;
      radius_ = radius.clone;
      state_ = "up";
      this.draw()
   }

   public void wasPushed() { state_="down"; draw() }
   public void wasReleasedInsideButton() {
      state_="up"; handler_.buttonWasReleased(this); draw()
   }
   public void wasReleasedOutsideButton() {
      // In case of a drag that started inside a button
      state_="up"; draw()
   }

   public boolean contains(int x, int y) {
      int centerX = x_ + radius_;
      int centerY = y_ + radius_;
      double distanceToCenter =
               Math.sqrt(((x - centerX) ** 2) + ((y - centerY) ** 2));
      return (distanceToCenter < radius_)
   }

   public void draw() {
      panel_.drawOval(x_, y_, radius_, radius_);
      panel_.drawString(x_ + radius_ - ((label_.length() * 6) / 2),
                        y_+ radius_ + 6 , label_)
      Color saveColor = panel_.getColor();
      if (state_ == "down") {
         panel_.setColor(Color.blue);
         panel_.fillOval(x_, y_, radius_, radius_)
      } else {
         panel_.setColor(new Color(0, 255, 255));
         panel_.fillOval(x_+2, y_+1, radius_-2, radius_-1)
      }
      panel_.setColor(saveColor)
   }

   public int x() { return x_ }
   public int y() { return y_ }

   private ButtonHandler handler_;
   private MyPanel panel_;
   private String label_;
   private int x_, y_, radius_;
   private String state_
}

class TaskView {
   int x_, y_;
}