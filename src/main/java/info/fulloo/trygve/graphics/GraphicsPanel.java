package info.fulloo.trygve.graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

// From DrawText.java

public class GraphicsPanel extends Panel implements ActionListener {
	private static class StringRecord {
		public StringRecord(final int x, final int y, final String string, final Color color) {
			x_ = x;
			y_ = y;
			string_ = string;
			color_ = color;
			if (null == color_) {
				color_ = Color.black;
			}
		}
		
		public String toString() {
			return string_;
		}
		
		public int x() { return x_; }
		public int y() { return y_; }
		public Color color() { return color_; }
		
		private final int x_, y_;
		private String string_;
		private Color color_;
	}
	
	@Override public void actionPerformed(final ActionEvent event) {
		assert false;
	}
	
	@Override public boolean handleEvent(final Event e) {
		switch (e.id) {
		  case Event.MOUSE_DOWN:
			assert false;
		    return true;
		  case Event.MOUSE_UP:
			assert false;
		    repaint();
		    return true;
		  case Event.MOUSE_MOVE:
			return true;
		  case Event.MOUSE_DRAG:
		    repaint();
		    return true;
		  case Event.WINDOW_DESTROY:
		    System.exit(0);
		    return true;
		  case Event.MOUSE_EXIT:
		  	return true;
		  case Event.MOUSE_ENTER:
			return true;
		  default:
		    return false;
		}
	}
	
	@Override public void paint(final Graphics g) {
		/* draw the current rectangles */
		g.setColor(getForeground());
		g.setPaintMode();
		for (int i=0; i < rectangles_.size(); i++) {
		    final Rectangle p = rectangles_.elementAt(i);
		    g.setColor((Color)rectColors_.elementAt(i));
		    if (p.width != -1) {
		    	g.drawLine(p.x, p.y, p.width, p.height);
		    } else {
		    	g.drawLine(p.x, p.y, p.x, p.y);
		    }
		}
		
		/* draw the current lines */
		g.setColor(getForeground());
		g.setPaintMode();
		for (int i=0; i < lines_.size(); i++) {
		    final Rectangle p = lines_.elementAt(i);
		    g.setColor((Color)lineColors_.elementAt(i));
		    g.drawLine(p.x, p.y, p.x+p.width, p.y+p.height);
		}
		
		/* draw the current ellipses */
		g.setColor(getForeground());
		g.setPaintMode();
		for (int i=0; i < ellipses_.size(); i++) {
		    final Ellipse2D p = ellipses_.elementAt(i);
		    g.setColor((Color)ellipseColors_.elementAt(i));
		    g.drawOval((int)p.getCenterX(), (int)p.getCenterY(), (int)p.getWidth(), (int)p.getHeight());
		}
		
		/* draw the current texts */
		g.setColor(getForeground());
		g.setPaintMode();
		for (int i=0; i < strings_.size(); i++) {
		    final StringRecord p = strings_.elementAt(i);
		    g.setColor((Color)strings_.elementAt(i).color());
		    g.drawString(p.toString(), p.x(), p.y());
		}
	}
	
	public void addRectangle(final Rectangle rect, final Color color) {
		rectangles_.addElement(rect);
		rectColors_.addElement(color);
	}
	
	public void addLine(final Rectangle line, final Color color) {
		lines_.addElement(line);
		lineColors_.addElement(color);
	}
	
	public void addEllipse(int x, int y, int width, int height, final Color color) {
		final Ellipse2D ellipse = new Ellipse2D.Float(x, y, width, height);
		ellipses_.addElement(ellipse);
		ellipseColors_.addElement(color);
	}
	
	public void addString(int x, int y, final String string, final Color color) {
		final StringRecord stringRecord = new StringRecord(x, y, string, color);
		strings_.addElement(stringRecord);
	}
	
	public GraphicsPanel() {
		rectangles_ = new Vector<Rectangle>();
		rectColors_ = new Vector<Color>();
		
		// A line is a funny kind of Rectangle (Javathink)
		lines_ = new Vector<Rectangle>();
		lineColors_ = new Vector<Color>();
		
		ellipses_ = new Vector<Ellipse2D>();
		ellipseColors_ = new Vector<Color>();
		
		strings_ = new Vector<StringRecord>();
	}
	
	private final Vector<Rectangle> rectangles_;
	private final Vector<Color> rectColors_;
	
	private final Vector<Rectangle> lines_;
	private final Vector<Color> lineColors_;
	
	private final Vector<Ellipse2D> ellipses_;
	private final Vector<Color> ellipseColors_;
	
	private final Vector<StringRecord> strings_;
	
	private static final long serialVersionUID = 238269472;
}
