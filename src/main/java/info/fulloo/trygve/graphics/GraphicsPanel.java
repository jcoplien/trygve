package info.fulloo.trygve.graphics;

import info.fulloo.trygve.add_ons.PanelClass.RTEventClass;
import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTColorObject;
import info.fulloo.trygve.run_time.RTCookieObject;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTEventObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTBooleanObject;
import info.fulloo.trygve.run_time.RTStackable;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.RTMessage.RTPostReturnProcessing;
import info.fulloo.trygve.run_time.RTMethod;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTObjectCommon;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.semantic_analysis.StaticScope;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Map;
import java.util.Vector;

// From DrawText.java

public class GraphicsPanel extends Panel implements ActionListener, RTObject {
	public GraphicsPanel(final RTObjectCommon rTPanel) {
		rTPanel_ = rTPanel;
		makeVectors();
	}
	
	public void setBackground(final RTObject colorArg) {
		assert colorArg instanceof RTColorObject;
		final Color color = ((RTColorObject)colorArg).color();
		this.setBackground(color);
	}
	public void setForeground(final RTObject colorArg) {
		assert colorArg instanceof RTColorObject;
		final Color color = ((RTColorObject)colorArg).color();
		this.setForeground(color);
	}
	public void drawLine(final RTObject fromXArg, final RTObject fromYArg, final RTObject toXArg, final RTObject toYArg) {
		assert fromXArg instanceof RTIntegerObject;
		assert fromYArg instanceof RTIntegerObject;
		assert toXArg instanceof RTIntegerObject;
		assert toYArg instanceof RTIntegerObject;
		final int fromX = (int)((RTIntegerObject)fromXArg).intValue();
		final int fromY = (int)((RTIntegerObject)fromYArg).intValue();
		final int toX = (int)((RTIntegerObject)toXArg).intValue();
		final int toY = (int)((RTIntegerObject)toYArg).intValue();
		final Rectangle newRect = new Rectangle(fromX, fromY, Math.abs(toX-fromX), Math.abs(toY-fromY));
		final Color currentColor = getForeground();
		this.addLine(newRect, currentColor);
	}
	public void drawRect(final RTObject fromXArg, final RTObject fromYArg, final RTObject widthArg, final RTObject heightArg) {
		assert fromXArg instanceof RTIntegerObject;
		assert fromYArg instanceof RTIntegerObject;
		assert widthArg instanceof RTIntegerObject;
		assert heightArg instanceof RTIntegerObject;
		final int fromX = (int)((RTIntegerObject)fromXArg).intValue();
		final int fromY = (int)((RTIntegerObject)fromYArg).intValue();
		final int width = (int)((RTIntegerObject)widthArg).intValue();
		final int height = (int)((RTIntegerObject)heightArg).intValue();
		final Rectangle newRect = new Rectangle(fromX, fromY, width, height);
		final Color currentColor = getForeground();
		this.addRectangle(newRect, currentColor);
	}
	public void drawOval(final RTObject xArg, final RTObject yArg, final RTObject widthArg, final RTObject heightArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert widthArg instanceof RTIntegerObject;
		assert heightArg instanceof RTIntegerObject;
		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final int width = (int)((RTIntegerObject)widthArg).intValue();
		final int height = (int)((RTIntegerObject)heightArg).intValue();
		final Color currentColor = getForeground();
		this.addOval(x, y, width, height, currentColor);
	}
	public void fillOval(final RTObject xArg, final RTObject yArg, final RTObject widthArg, final RTObject heightArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert widthArg instanceof RTIntegerObject;
		assert heightArg instanceof RTIntegerObject;
		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final int width = (int)((RTIntegerObject)widthArg).intValue();
		final int height = (int)((RTIntegerObject)heightArg).intValue();
		final Color currentColor = getForeground();
		this.addFilledOval(x, y, width, height, currentColor);
	}
	public RTObject drawString(final RTObject xArg, final RTObject yArg, final RTObject stringArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert stringArg instanceof RTStringObject;
		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final String string = ((RTStringObject)stringArg).stringValue();
		final Color currentColor = getForeground();
		final RTObject retval = this.addString(x, y, string, currentColor);
		return retval;
	}
	
	public void handleEventProgrammatically(final Event e) {
		final RTType rTType = rTPanel_.rTType();
		assert rTType instanceof RTClass;
		final TypeDeclaration typeDeclaration = ((RTClass)rTType).typeDeclaration();
		final Type eventType = StaticScope.globalScope().lookupTypeDeclaration("Event");
		final FormalParameterList pl = new FormalParameterList();
		final ObjectDeclaration self = new ObjectDeclaration("this", typeDeclaration.type(), typeDeclaration.lineNumber());
		final ObjectDeclaration event = new ObjectDeclaration("e", eventType, 0);

		pl.addFormalParameter(event);
		pl.addFormalParameter(self);
		
		final RTMethod hE = rTType.lookupMethod("handleEvent", pl);
		if (null != hE) {
			final int preStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();
			this.dispatchInterrupt(hE, e);
			
			// Get the return value from the user function telling whether they
			// handled the interrupt or not. If not, we should handle it ourselves
			// (if it's a keystroke event)
			//
			// Note that this shouldn't affect blocked reads...
			final RTObject retval = (RTObject)RunTimeEnvironment.runTimeEnvironment_.popStack();
			RTStackable oldEventArg = RunTimeEnvironment.runTimeEnvironment_.popStack();
			if ((oldEventArg instanceof RTObjectCommon) && ((RTObjectCommon)oldEventArg).rTType() instanceof RTEventClass == false) {
				assert false;
			}
			
			int postStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();

			if (postStackDepth != preStackDepth) {
				// assert postStackDepth == preStackDepth;
				assert (postStackDepth > preStackDepth);
				
				// Dunno what's going on but just try to recover
				while (postStackDepth > preStackDepth) {
					RunTimeEnvironment.runTimeEnvironment_.popStack();
					postStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				}
				
				return;
			}
			
			if (retval instanceof RTBooleanObject) {
				final boolean interruptWasHandled = ((RTBooleanObject)retval).value();
				if (interruptWasHandled == false) {
					if (e.key != 0 && e.id == Event.KEY_RELEASE) {
						checkIOIntegration(e);
					}
				}
			} else {
				assert false;
			}
		} else {
			if (e.key != 0 && e.id == Event.KEY_RELEASE) {
				checkIOIntegration(e);
			}
		}
	}
	
	protected void checkIOIntegration(final Event e) {
		if (null != eventsAreBeingHijacked_) {
			eventsAreBeingHijacked_.checkIOIntegration(e);
		}
	}
	
	private void dispatchInterrupt(final RTMethod method, final Event e) {
		// Just do a method call into the user space
		final RTCode halt = null;
		final ClassType eventType = (ClassType)StaticScope.globalScope().lookupTypeDeclaration("Event");
		final RTType rTType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(eventType.enclosedScope());
		final MethodDeclaration methodDecl = method.methodDeclaration();
		final StaticScope methodParentScope = null == methodDecl? null: methodDecl.enclosingScope();
		final String debugName = null == methodParentScope? "???": methodParentScope.name();
		final RTPostReturnProcessing retInst = new RTPostReturnProcessing(halt, "Interrupt", debugName);
		retInst.setResultIsConsumed(true);
		final RTObject event = RTEventObject.ctor1(e);

		RunTimeEnvironment.runTimeEnvironment_.pushStack(event);
		RunTimeEnvironment.runTimeEnvironment_.pushStack(retInst);
		RunTimeEnvironment.runTimeEnvironment_.setFramePointer();
		
		final RTDynamicScope activationRecord = new RTDynamicScope(
				method.name(),
				RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
		
		if (null != methodDecl) {
			// Get formal parameter name for "event" parameter
			final MethodSignature signature = methodDecl.signature();
			final FormalParameterList formalParameters = signature.formalParameterList();
			final Declaration eventParameter = formalParameters.parameterAtPosition(1);
			assert eventParameter instanceof ObjectDeclaration;
			final String eventName = eventParameter.name();
			
			activationRecord.addObjectDeclaration(eventName, rTType);
			activationRecord.addObjectDeclaration("this", null);
			activationRecord.setObject(eventName, event);
			activationRecord.setObject("this", rTPanel_);
			RunTimeEnvironment.runTimeEnvironment_.pushDynamicScope(activationRecord);
			
			RTCode pc = method;
			do {
				pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			} while (null != pc && pc instanceof RTHalt == false);
		}
	}
	
	// ------------------ Internal stuff ------------------------------
	
	private void makeVectors() {
		rectangles_ = new Vector<Rectangle>();
		rectColors_ = new Vector<Color>();
		
		// A line is a funny kind of Rectangle (Javathink)
		lines_ = new Vector<Rectangle>();
		lineColors_ = new Vector<Color>();
		
		ellipses_ = new Vector<Ellipse2D>();
		ellipseColors_ = new Vector<Color>();
		
		filledEllipses_ = new Vector<Ellipse2D>();
		filledEllipseColors_ = new Vector<Color>();
		
		strings_ = new Vector<StringRecord>();
	}
	
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
		
		@Override public String toString() {
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
	
	@Override public synchronized boolean handleEvent(final Event e) {
		// These constants need to be coordinated only with stuff in the
		// setup and postSetupInitialization methods in PanelClass.java
		if (inInterrupt_++ > 0) {
			--inInterrupt_;
			return false;
		}
		
		boolean retval = false;
		
		try {
			int startingStackSize = 0;
			
			switch (e.id) {
			  case Event.KEY_PRESS:
			  case Event.KEY_RELEASE:
				startingStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				this.handleEventProgrammatically(e);
				assert RunTimeEnvironment.runTimeEnvironment_.stackSize() ==  startingStackSize;
				retval = true;
				break;
			  case Event.MOUSE_DRAG:
			  case Event.MOUSE_DOWN:
			  case Event.MOUSE_UP:
				startingStackSize = RunTimeEnvironment.runTimeEnvironment_.stackSize();
				this.handleEventProgrammatically(e);
				final Graphics g = this.getGraphics();
				if (null != g) {
					paint(g);
					// repaint();
				}
				
				assert RunTimeEnvironment.runTimeEnvironment_.stackSize() <= startingStackSize;
				
				// Desperate attempt to fix up something that we don't know the reason for
				while (RunTimeEnvironment.runTimeEnvironment_.stackSize() != startingStackSize) {
					RunTimeEnvironment.runTimeEnvironment_.popStack();
				}
			
				retval = true;
				break;
			  case Event.WINDOW_DESTROY:
				System.exit(0);
				retval = true;
				break;
			  case Event.MOUSE_EXIT:
			  case Event.MOUSE_ENTER:
			  case Event.MOUSE_MOVE:
				this.handleEventProgrammatically(e);
				retval = true;
				break;
			  default:
				retval = false;
				break;
			}
		} finally {
			inInterrupt_ = 0;
		}
		return retval;
	}
	
	private synchronized void drawEllipses(final Graphics g) {
		try {
			/* draw the current ellipses */
			g.setColor(getForeground());
			g.setPaintMode();
			for (int i = 0; i < ellipses_.size(); i++) {
			    final Ellipse2D p = ellipses_.elementAt(i);
			    g.setColor((Color)ellipseColors_.elementAt(i));
			    g.drawOval((int)p.getCenterX(), (int)p.getCenterY(), (int)p.getWidth(), (int)p.getHeight());
			}
		} catch (ArrayIndexOutOfBoundsException exception) {
			;
		}
	}
	private synchronized void drawFilledEllipses(final Graphics g) {
		/* same, only filled ellipses */
		try {
			for (int i = 0; i < filledEllipses_.size(); i++) {
				final Ellipse2D p = filledEllipses_.elementAt(i);
				g.setColor((Color)filledEllipseColors_.elementAt(i));
				g.fillOval((int)p.getCenterX(), (int)p.getCenterY(), (int)p.getWidth(), (int)p.getHeight());
			}
		} catch (ArrayIndexOutOfBoundsException exception) {
			;
		}
	}
	
	@Override public void paint(final Graphics g) {
		if (inDrawing_++ > 0) {
			--inDrawing_;
			return;
		}
		
		/* draw the current rectangles */
		g.setColor(getForeground());
		g.setPaintMode();
		
		// Copies, just in case we get interrupted
		@SuppressWarnings("unchecked")
		Vector<Rectangle> rectanglesCopy = (Vector<Rectangle>) rectangles_.clone();
		
		@SuppressWarnings("unchecked")
		Vector<Color> rectColorsCopy = (Vector<Color>) rectColors_.clone();
		
		try {
			for (int i = 0; i < rectanglesCopy.size(); i++) {
			    final Rectangle p = rectanglesCopy.elementAt(i);
			    g.setColor((Color)rectColorsCopy.elementAt(i));
			    if (p.width != -1) {
			    	g.drawRect(p.x, p.y, p.width, p.height);
			    } else {
			    	g.drawLine(p.x, p.y, p.width, p.height);
			    }
			}
		} catch (ArrayIndexOutOfBoundsException exception) {
			;
		}
		
		/* draw the current lines */
		g.setColor(getForeground());
		g.setPaintMode();
		for (int i = 0; i < lines_.size(); i++) {
		    final Rectangle p = lines_.elementAt(i);
		    g.setColor((Color)lineColors_.elementAt(i));
		    g.drawLine(p.x, p.y, p.x+p.width, p.y+p.height);
		}
		
		drawEllipses(g);
		drawFilledEllipses(g);
		
		/* draw the current texts */
		g.setColor(getForeground());
		g.setPaintMode();
		for (int i = 0; i < strings_.size(); i++) {
		    final StringRecord p = strings_.elementAt(i);
		    g.setColor(p.color());
		    g.drawString(p.toString(), p.x(), p.y());
		}
		
		inDrawing_ = 0;
	}
	
	@Override public void removeAll() {
		makeVectors();
		super.removeAll();
	}
	
	public void remove(final RTObject thingToRemoveRTObject) {
		assert thingToRemoveRTObject instanceof RTCookieObject;
		final Object thingToRemove = ((RTCookieObject)thingToRemoveRTObject).cookie();

		boolean removed = false;
		for (int i = 0; i < strings_.size(); i++) {
			if (strings_.get(i) == thingToRemove) {
				strings_.remove(i);
				removed = true;
				break;
			}
		}
		if (removed == false) {
			for (int i = 0; i < ellipses_.size(); i++) {
				if (ellipses_.get(i) == thingToRemove) {
					ellipses_.remove(i);
					ellipseColors_.remove(i);
					removed = true;
					break;
				}
			}
			if (removed == false) {
				for (int i = 0; i < rectangles_.size(); i++) {
					if (rectangles_.get(i) == thingToRemove) {
						rectangles_.remove(i);
						rectColors_.remove(i);
						removed = true;
						break;
					}
				}
				if (removed == false) {
					for (int i = 0; i < lines_.size(); i++) {
						if (lines_.get(i) == thingToRemove) {
							lines_.remove(i);
							lineColors_.remove(i);
							removed = true;
							break;
						}
					}
				}
			}
		}
	}
	
	public synchronized void addRectangle(final Rectangle rect, final Color color) {
		rectangles_.addElement(rect);
		rectColors_.addElement(color);
	}
	
	public synchronized void fillRectangle(final RTObject xObject, final RTObject yObject, 
			final RTObject widthObject, final RTObject heightObject) {
		final int x = (int)((RTIntegerObject)yObject).intValue();
		final int y = (int)((RTIntegerObject)widthObject).intValue();
		final int width = (int)((RTIntegerObject)xObject).intValue();
		final int height = (int)((RTIntegerObject)heightObject).intValue();
		final Graphics g = this.getGraphics();
		if (null != g) {
			g.fillRect(x, y, width, height);
		}
	}
	
	public synchronized void addLine(final Rectangle line, final Color color) {
		lines_.addElement(line);
		lineColors_.addElement(color);
	}
	
	public synchronized void addOval(final int x, final int y, final int width, final int height, final Color color) {
		final Ellipse2D ellipse = new Ellipse2D.Float(x, y, width, height);
		ellipses_.addElement(ellipse);
		ellipseColors_.addElement(color);
	}
	
	public synchronized void addFilledOval(final int x, final int y, final int width, final int height, final Color color) {
		final Ellipse2D ellipse = new Ellipse2D.Float(x, y, width, height);
		filledEllipses_.addElement(ellipse);
		filledEllipseColors_.addElement(color);
	}
	
	public synchronized RTObject addString(final int x, final int y, final String string, final Color color) {
		final StringRecord stringRecord = new StringRecord(x, y, string, color);
		strings_.addElement(stringRecord);
		final RTObject retval = new RTCookieObject(stringRecord);
		return retval;
	}
	
	public void setGraphicsEventHandler(final GraphicsEventHandler eventsAreBeingHijacked) {
		eventsAreBeingHijacked_ = eventsAreBeingHijacked;
	}
	
	private       volatile Vector<Rectangle> rectangles_;
	private       volatile Vector<Color> rectColors_;
	
	private       volatile Vector<Rectangle> lines_;
	private       volatile Vector<Color> lineColors_;
	
	private       volatile Vector<Ellipse2D> ellipses_;
	private       volatile Vector<Color> ellipseColors_;
	
	private       volatile Vector<Ellipse2D> filledEllipses_;
	private       volatile Vector<Color> filledEllipseColors_;
	
	private       volatile Vector<StringRecord> strings_;
	private       RTObjectCommon rTPanel_;
	
	private       static volatile int inInterrupt_ = 0, inDrawing_ = 0;
	
	private static final long serialVersionUID = 238269472;
	private int referenceCount_ = 1;

	// Junk so that we fit into the RTObject framework
	@Override public RTObject getObject(String name) { assert false; return null; }
	@Override public void addObjectDeclaration(String objectName, RTType type) { assert false; }
	@Override public Map<String, RTType> objectDeclarations() { assert false; return null; }
	@Override public void setObject(String objectName, RTObject object) { assert false; }
	@Override public RTType rTType() { assert false; return null; }
	@Override public boolean isEqualTo(Object another) { assert false; return false; }
	@Override public boolean gt(RTObject another) { assert false; return false; }
	@Override public int compareTo(Object other) { assert false; return 0; }
	@Override public boolean equals(RTObject other) { assert false; return false; }
	@Override public RTObject plus(RTObject other) { assert false; return null; }
	@Override public RTObject minus(RTObject other) { assert false; return null; }
	@Override public RTObject times(RTObject other) { assert false; return null; }
	@Override public RTObject divideBy(RTObject other) { assert false; return null; }
	@Override public RTObject modulus(RTObject other) { assert false; return null; }
	@Override public RTObject unaryPlus() { assert false; return null; }
	@Override public RTObject unaryMinus() { assert false; return null; }
	@Override public RTObject logicalOr(RTObject other) { assert false; return null; }
	@Override public RTObject logicalAnd(RTObject other) { assert false; return null; }
	@Override public RTObject logicalXor(RTObject other) { assert false; return null; }
	@Override public RTObject unaryLogicalNegation() { assert false; return null; }
	@Override public RTObject preIncrement() { assert false; return null; }
	@Override public RTObject postIncrement() { assert false; return null; }
	@Override public RTObject preDecrement() { assert false; return null; }
	@Override public RTObject postDecrement() { assert false; return null; }
	@Override public RTObject performUnaryOpOnObjectNamed(String idName, String operator,
			PreOrPost preOrPost_) { assert false; return null; }
	@Override public RTObject toThePowerOf(RTObject exponent) { assert false; return null; }
	@Override public RTObject dup() { assert false; return null; }
	@Override public void incrementReferenceCount() { referenceCount_++; }
	@Override public void decrementReferenceCount() { --referenceCount_; }
	@Override public long referenceCount() { return referenceCount_; }
	@Override public void enlistAsRolePlayerForContext(String roleName,
			RTContextObject contextInstance) { assert false; }
	@Override public void unenlistAsRolePlayerForContext(String roleName,
			RTContextObject contextInstance) { assert false; }
	@Override public void enlistAsStagePropPlayerForContext(String stagePropName,
			RTContextObject contextInstance) { assert false; }
	@Override public void unenlistAsStagePropPlayerForContext(String stagePropName,
			RTContextObject contextInstance) { assert false; }
	@Override public String getText() { assert false; return null; }
	
	private GraphicsEventHandler eventsAreBeingHijacked_;
}
