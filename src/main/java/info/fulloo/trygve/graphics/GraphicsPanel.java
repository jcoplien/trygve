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
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect.PreOrPost;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass.RTHalt;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTColorObject;
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
import java.util.Map;

// From DrawText.java

public class GraphicsPanel extends Panel implements ActionListener, RTObject {
	public GraphicsPanel(final RTObjectCommon rTPanel) {
		rTPanel_ = rTPanel;
	}

	public boolean canDraw() {
		assert null != back_;
		if (back_.g == null) {
			back_.reset();
		}
		return back_.g != null;
	}

	public void setColor(final RTObject colorObject) {
		if (!(colorObject instanceof RTColorObject)) {
			assert colorObject instanceof RTColorObject;
		}
		final Color color = ((RTColorObject)colorObject).color();

		if (canDraw()){
			back_.g.setColor(color);
		}
	}

	public Color getColor() {
		if (canDraw()) {
			return back_.g.getColor();
		}
		return Color.white;
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

		if (canDraw()) {
			back_.g.drawLine(fromX, fromY, toX, toY);
		}
	}
	public void clear(){
		if (canDraw()) {
			back_.g.clearRect(0, 0, back_.width, back_.height);
		}
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

		if (canDraw()) {
			back_.g.drawRect(fromX, fromY, width, height);
		}
	}
	public void fillRect(final RTObject xArg, final RTObject yArg, final RTObject widthArg, final RTObject heightArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert widthArg instanceof RTIntegerObject;
		assert heightArg instanceof RTIntegerObject;

		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final int width = (int)((RTIntegerObject)widthArg).intValue();
		final int height = (int)((RTIntegerObject)heightArg).intValue();

		if (canDraw()) {
			back_.g.fillRect(x, y, width, height);
		}
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

		if (canDraw()) {
			back_.g.drawOval(x, y, width, height);
		}
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

		if (canDraw()) {
			back_.g.fillOval(x, y, width, height);
		}
	}
	public void drawString(final RTObject xArg, final RTObject yArg, final RTObject stringArg) {
		assert xArg instanceof RTIntegerObject;
		assert yArg instanceof RTIntegerObject;
		assert stringArg instanceof RTStringObject;
		final int x = (int)((RTIntegerObject)xArg).intValue();
		final int y = (int)((RTIntegerObject)yArg).intValue();
		final String string = ((RTStringObject)stringArg).stringValue();

		if (canDraw()) {
			back_.g.drawString(string, x, y);
		}
	}
	public Point measureString(final RTObject stringArg) {
		assert stringArg instanceof RTStringObject;
		final String string = ((RTStringObject)stringArg).stringValue();

		if (canDraw()) {
			final FontMetrics m = back_.g.getFontMetrics();
			return new Point(m.stringWidth(string), m.getHeight());
		}
		return new Point(string.length(), 10);
	}

	public void handleEventProgrammatically(final Event e) {
		final RTType rTType = rTPanel_.rTType();
		assert rTType instanceof RTClass;
		final TypeDeclaration typeDeclaration = ((RTClass)rTType).typeDeclaration();
		final Type eventType = StaticScope.globalScope().lookupTypeDeclaration("Event");
		final FormalParameterList pl = new FormalParameterList();
		final ObjectDeclaration self = new ObjectDeclaration("this", typeDeclaration.type(), typeDeclaration.token());
		final ObjectDeclaration event = new ObjectDeclaration("e", eventType, null);

		pl.addFormalParameter(event);
		pl.addFormalParameter(self);

		final RTMethod hE = rTType.lookupMethod("handleEvent", pl);
		if (null != hE) {
			final MethodDeclaration methodDecl = hE.methodDeclaration();
			final Type returnType = methodDecl.returnType();
			final String returnTypePathName = returnType.pathName();
			if (false == returnTypePathName.equals("boolean.")) {
				ErrorLogger.error(ErrorIncidenceType.Internal, "Return type of `handleEvent' is not boolean in class `",
						methodDecl.enclosingScope().name() + "' at line ",
						Integer.toString(methodDecl.lineNumber()), ".");
				// not sure what else to do here...
			}
			final int preStackDepth = RunTimeEnvironment.runTimeEnvironment_.stackSize();
			this.dispatchInterrupt(hE, e);

			// Get the return value from the user function telling whether they
			// handled the interrupt or not. If not, we should handle it ourselves
			// (if it's a keystroke event)
			//
			// Note that this shouldn't affect blocked reads...
			RTStackable retval = RunTimeEnvironment.runTimeEnvironment_.peekStack();
			if (false == retval instanceof RTPostReturnProcessing) {
				retval = RunTimeEnvironment.runTimeEnvironment_.popStack();
			}
			
			// > 0 check is just empirical - shouldn't happen but does
			if (RunTimeEnvironment.runTimeEnvironment_.stackSize() > 0) {
				RTStackable oldEventArg = RunTimeEnvironment.runTimeEnvironment_.popStack();
				if ((oldEventArg instanceof RTObjectCommon) && ((RTObjectCommon)oldEventArg).rTType() instanceof RTEventClass == false) {
					assert false;
				}
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
				if (false == interruptWasHandled) {
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
		final RTPostReturnProcessing retInst = new RTPostReturnProcessing(halt,
				"Interrupt", debugName, method.token());
		retInst.setResultIsConsumed(true);
		final RTObject event = RTEventObject.ctor1(e);

		RunTimeEnvironment.runTimeEnvironment_.pushStack(event);
		RunTimeEnvironment.runTimeEnvironment_.pushStack(retInst);
		RunTimeEnvironment.runTimeEnvironment_.setFramePointer();

		final RTDynamicScope activationRecord = new RTDynamicScope(
				null,
				method.name(),
				RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope(),
				true);

		if (null != methodDecl) {
			// Get formal parameter name that the user used for the "event" parameter
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
			activationRecord.incrementReferenceCount();

			RTCode pc = method;
			do {
				pc = RunTimeEnvironment.runTimeEnvironment_.runner(pc);
			} while (null != pc && false == pc instanceof RTHalt);
		}
	}

	// ------------------ Internal stuff ------------------------------

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

	@Override public void update(Graphics g){
		paint(g);
	}
	@Override public void paint(final Graphics g) {
		synchronized (this){
			if(front_ == null || front_.g == null){
				front_.reset();
			}

			if(front_ != null && front_.g != null){
				g.drawImage(front_.image, 0, 0, this);
			}
		}
	}

	public void flipBuffers(){
		synchronized (this){
			Buffer temp = back_;
			back_ = front_;
			front_ = temp;

			back_.updateSize();
			// This hides the usage of the double-buffer at the cost of performance
			if(front_.image != null && back_.g != null){
				back_.g.drawImage(front_.image, 0, 0, this);
			}
		}
	}

	public void setGraphicsEventHandler(final GraphicsEventHandler eventsAreBeingHijacked) {
		eventsAreBeingHijacked_ = eventsAreBeingHijacked;
	}

	class Buffer {
		Image image;
		Graphics g;
		int width, height;

		public void updateSize(){
			if(height != getSize().height || width != getSize().width){
				reset();
			}
		}

		public void reset(){
			height = getSize().height;
			width = getSize().width;
			if (g != null) {
				g.dispose();
				g = null;
			}
			if (image != null) {
				image.flush();
				image = null;
			}
			System.gc();

			if (width == 0 || height == 0) {
				width = 640;
				height = 480;
			}

			// when the panel isn't put on the screen this can fail
			image = createImage(width, height);
			if (image != null) {
				g = image.getGraphics();
			}
		}
	}

	private volatile Buffer back_ = new Buffer();
	private volatile Buffer front_ = new Buffer();

	private       RTObjectCommon rTPanel_;

	private       static volatile int inInterrupt_ = 0;

	private static final long serialVersionUID = 238269472;
	private              int referenceCount_ = 0;

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
