package info.fulloo.trygve.run_time;

import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.graphics.GraphicsPanel;
import info.fulloo.trygve.parser.ParseRun;
import info.fulloo.trygve.run_time.RTClass.RTIntegerClass;
import info.fulloo.trygve.run_time.RTClass.RTStringClass;
import info.fulloo.trygve.run_time.RTContext.RTContextInfo;
import info.fulloo.trygve.run_time.RTExpression.RTMessage.RTPostReturnProcessing;
import info.fulloo.trygve.run_time.RTObjectCommon.RTContextObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTStringObject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;




// https://www3.ntu.edu.sg/home/ehchua/programming/java/j5e_multithreading.html
public class RTDebuggerWindow extends JFrame {
	
	// These My* classes are here to wrap the text elements of the debugger window.
	// There seems to be some kind of asynchronous bug, perhaps related to thread
	// safety, that causes the cursor object to disappear for text fields
	// when there is an interaction between the debugger and the program. It
	// might also owe to the program erasing the cursor when outputting. Who
	// knows. But it causes the program to die while being repainted in
	// response to some event linked to the breakpoint, because the paint
	// logic wants there to be a cursor there. The problem showed up in
	// getSelectionStart and getSelectionEnd, so we provide our own version
	// of those that provide a default cursor if there is none in place.
	//
	// Another possible reason: Multiple threads may be updating the same
	// JTextArea in the main window
	//
	// The problem seems to be more with JTextArea than JTextField, but for
	// completeness we cover them both.
	public class MyJTextField extends JTextField {
		public MyJTextField(final int columns) {
			super(columns);
		}
		 @Override public int getSelectionStart() {
			   final Caret caret = super.getCaret();
			   if (null == caret) {
				   setCaret(new DefaultCaret());
			   }
			   final int start = null == caret? 0: Math.min(caret.getDot(), caret.getMark());
			   return start;
		   }
		   @Override public int getSelectionEnd() {
			   final Caret caret = super.getCaret();
			   if (null == caret) {
				   setCaret(new DefaultCaret());
			   }
			   final int end = null == caret? 0: Math.max(caret.getDot(), caret.getMark());
			   return end;
		   }
		private final static long serialVersionUID = 237718234;
	}
	public class MyJTextArea extends JTextArea {
	   private static final long serialVersionUID = 1L;
	   public MyJTextArea(int h, int w) {
		   super(h, w);
	   }
	   @Override public int getSelectionStart() {
		   final Caret caret = super.getCaret();
		   if (null == caret) {
			   setCaret(new DefaultCaret());
		   }
		   final int start = null == caret? 0: Math.min(caret.getDot(), caret.getMark());
		   return start;
	   }
	   @Override public int getSelectionEnd() {
		   final Caret caret = super.getCaret();
		   if (null == caret) {
			   setCaret(new DefaultCaret());
		   }
		   final int end = null == caret? 0: Math.max(caret.getDot(), caret.getMark());
		   return end;
	   }
	}
	public RTDebuggerWindow(final TextEditorGUI gui) {
	  gui_ = gui;
	  allBreakpointedExpressions_ = new ArrayList<RTCode>();
      final Container cp = getContentPane();
      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      cp.setLayout(layout);
      
      setBreakpointButton_ = new JButton("Set Breakpoint at cursor");
      setBreakpointButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 RunTimeEnvironment runTimeEnvironment = RunTimeEnvironment.runTimeEnvironment_;
        	 if (null != runTimeEnvironment) {
        		 // If runTimeEnvironment is null, maybe the program hasn't been parsed
        		 final int lineNumber = runTimeEnvironment.gui().currentLineNumber();
        		 final int byteOffset = runTimeEnvironment.gui().currentByteOffset();
        		 final ParseRun parseRun = gui.getParseRun();
        		 assert (null != parseRun);
        		 setBreakpointAt(byteOffset, lineNumber, parseRun);
        	 }
         }
      });
      
      // When the debugger closes, notify the main IDE
      addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gui_.debuggerClosed();
			}
      });
      
      removeBreakpointButton_ = new JButton("Delete Breakpoint");
      removeBreakpointButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 RunTimeEnvironment runTimeEnvironment = RunTimeEnvironment.runTimeEnvironment_;
        	 if (null != runTimeEnvironment) {
        		 // If runTimeEnvironment is null, maybe the program hasn't been parsed
        		 final int lineNumber = runTimeEnvironment.gui().currentLineNumber();
        		 final int byteOffset = runTimeEnvironment.gui().currentByteOffset();
        		 final ParseRun parseRun = gui.getParseRun();
        		 assert (null != parseRun);
        		 removeBreakpointAt(byteOffset, lineNumber, parseRun);
        	 }
         }
      });
      
      this.updateParsingStatus(0, false);
      
      continueButton_ = new JButton("Continue");
      continueButton_.setEnabled(false);
      continueButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 continueButton_.setEnabled(false);
        	 pauseButton_.setEnabled(true);
        	 if (null != breakpointSemaphore_) breakpointSemaphore_.take();
        	 debuggerWindowMessage("Running\n");
         }
      });
      
      pauseButton_ = new JButton("Pause");
      pauseButton_.setEnabled(false);
      pauseButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 pauseButton_.setEnabled(false);
      	     RunTimeEnvironment.runTimeEnvironment_.pauseButtonActionPerformed();
         }
      });
      
      interruptButton_ = new JButton("Interrupt");
      interruptButton_.setEnabled(true);
      interruptButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
      	     gui_.interruptButtonActionPerformed(evt);
         }
      });
      
      clearButton_ = new JButton("Clear");
      clearButton_.setEnabled(true);
      clearButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
      	     messagePanel_.setText("");
      	     messagePanelContent_ = "";
         }
      });

      runButton_ = new JButton("Run");
      runButton_.setEnabled(true);
      runButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 runButton_.setEnabled(false);
        	 pauseButton_.setEnabled(true);
      	     gui_.runButtonActionPerformed(evt);
         }
      });
      
      messagePanel_ = new MyJTextArea(15, 35);
  	  messagePanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
      messagePanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  messagePanel_.setSize(550, 300);
  	  
  	  messageScrollPane_ = new javax.swing.JScrollPane(messagePanel_);
  	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  messageScrollPane_.setPreferredSize(new Dimension(350, 300));
  	  
  	  tracebackPanel_ = new MyJTextArea(15, 35);
  	  tracebackPanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
  	  tracebackPanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  tracebackPanel_.setSize(600, 300);
	  
	  tracebackScrollPane_ = new javax.swing.JScrollPane(tracebackPanel_);
	  tracebackScrollPane_.setPreferredSize(new Dimension(350, 300));
	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  
  	  objectOverviewPanel_ = new MyJTextArea(15, 35);
  	  objectOverviewPanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
  	  objectOverviewPanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  objectOverviewPanel_.setSize(600, 300);
	  
  	  objectScrollPane_ = new javax.swing.JScrollPane(objectOverviewPanel_);
  	  objectScrollPane_.setPreferredSize(new Dimension(350, 300));
  	  
  	  filterLabel_ = new JLabel("Filter: ");
  	  
  	  final int columns = 20;
  	  filterText_ = "";
  	  filterField_ = new MyJTextField(columns);
  	  filterField_.setText("");
  	  filterField_.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
     	     filterFieldActionPerformed(evt);
        }
      });

  	  filterTextAck_ = new JLabel("");
	  
  	  layout.setAutoCreateGaps(true);
      layout.setHorizontalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
              .addComponent(setBreakpointButton_)
              .addComponent(removeBreakpointButton_)
              .addComponent(continueButton_)
              .addComponent(pauseButton_)
              .addComponent(interruptButton_)
              .addGap(100, 300, 1000)
              .addComponent(runButton_))
          .addGroup(layout.createSequentialGroup()
        	  .addGroup(layout.createParallelGroup()
                          .addComponent(messageScrollPane_)
                    	  .addComponent(clearButton_))
              .addComponent(tracebackScrollPane_)
              .addGroup(layout.createParallelGroup()
                  .addComponent(objectScrollPane_)
                  .addGroup(layout.createSequentialGroup()
                		  .addComponent(filterLabel_)
                		  .addComponent(filterField_)
                		  .addComponent(filterTextAck_))))
          .addGroup(layout.createSequentialGroup())
      );
 
      layout.setVerticalGroup(
          layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        	  .addComponent(setBreakpointButton_)
              .addComponent(removeBreakpointButton_)
              .addComponent(continueButton_)
              .addComponent(runButton_)
              .addComponent(pauseButton_)
              .addComponent(interruptButton_))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        	 .addGroup(
        			 layout.createSequentialGroup()
        			   .addComponent(messageScrollPane_)
        			   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        			   .addGroup(
        				  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        				    .addComponent(clearButton_)))
        	 .addComponent(tracebackScrollPane_)
        	 .addGroup(
        			 layout.createSequentialGroup()
        			   .addComponent(objectScrollPane_)
        			   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        			   .addGroup(
        				  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        				    .addComponent(filterLabel_)
        				    .addComponent(filterField_)
        				    .addComponent(filterTextAck_))))
      );

      // pack();
 
      // Not: setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); instead:
      this_ = this;
      addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    	        this_.close();
    	    }
    	});
      
      setTitle("Debugger");
      setSize(1100, 400);
      setLocation(600, 400);
      setVisible(true);
   }
   private int plantBreakpointInExpression(int byteOffset, int lineNumber, ParseRun parseRun) {
	   final RTCode breakableExpression = parseRun.expressionForByteOffsetInBuffer(byteOffset, gui_);
	   int realLineNumber = -1;	// error return
	   if (null != breakableExpression) {
		   if (allBreakpointedExpressions_.contains(breakableExpression)) {
			   realLineNumber = -2;	// error code for duplicate breakpoint
		   } else {
			   breakableExpression.setBreakpoint(true);
			   realLineNumber = breakableExpression.lineNumber();
			   allBreakpointedExpressions_.add(breakableExpression);
		   }
	   } else {
		   // error message
	   }
	   
	   return realLineNumber;
   }
   private int removeBreakpointInExpression(int byteOffset, int lineNumber, ParseRun parseRun) {
	   final RTCode breakableExpression = parseRun.expressionForByteOffsetInBuffer(byteOffset, gui_);
	   int realLineNumber = -1;	// error return
	   if (null != breakableExpression) {
		   breakableExpression.setBreakpoint(false);
		   realLineNumber = breakableExpression.lineNumber();
		   allBreakpointedExpressions_.remove(breakableExpression);
	   } else {
		   // error message
	   }
	   
	   return realLineNumber;
   }
   private void setBreakpointAt(int byteOffset, int lineNumber, ParseRun parseRun) {
	   // Go through the program looking for the first expression
	   // at this line number. Keep this temporary plant because it
	   // helps verify the line number when the user requests it (instead
	   // of being surprised when pressing "Run" that a line is not
	   // breakpointable.
	   //
	   // We also keep this plant because it enables the user to set
	   // breakpoints in the middle of execution
	   final int realLineNumber = this.plantBreakpointInExpression(byteOffset, lineNumber, parseRun);
	   if (-1 == realLineNumber) {
		   debuggerWindowMessage("Cannot set breakpoint at line " + lineNumber + ".\n");
	   } else if (-2 == realLineNumber) {
		   debuggerWindowMessage("There is already a breakpoint there.\n");
	   } else {
		   // Mark it in the GUI. The "+ 1" is because the method returns an
		   // offset to a newline. When the breakpoint gets set in the buffer
		   // it highlights only up to a newline, so without the + 1 the
		   // length of the highlighted text would be zero...
		   final int realByteOffset = gui_.bufferOffsetForLineNumber(realLineNumber) + 1;
		   gui_.setBreakpointToEOLAt(realByteOffset, realLineNumber);
		   debuggerWindowMessage(
			   "Breakpoint set at line " + realLineNumber + " (" + lineNumber + " requested)\n"
			   );
	   }
   }
   private void removeBreakpointAt(int byteOffset, int lineNumber, ParseRun parseRun) {
	   // Go through the program looking for the first expression
	   // at this line number. Keep this temporary plant because it
	   // helps verify the line number when the user requests it (instead
	   // of being surprised when pressing "Run" that a line is not
	   // breakpointable.
	   final int realLineNumber = this.removeBreakpointInExpression(byteOffset, lineNumber, parseRun);
	   if (-1 == realLineNumber) {
		   debuggerWindowMessage(
				   "Cannot remove breakpoint at line " + lineNumber + ".\n"
				   );
	   } else {
		   // Unmark it in the GUI. The "+ 1" is because the method returns an
		   // offset to a newline. When the breakpoint gets set in the buffer
		   // it highlights only up to a newline, so without the + 1 the
		   // length of the highlighted text would be zero...
		   final int realByteOffset = gui_.bufferOffsetForLineNumber(realLineNumber) + 1;
		   gui_.unsetBreakpointToEOLAt(realByteOffset, realLineNumber);
		   debuggerWindowMessage(
			   "Breakpoint removed at line " + realLineNumber + " (" + lineNumber + " requested)\n"
			   );
	   }
   }
   public void plantAllBreakpointsAccordingToGUIMarkers(final ParseRun parseRun) {
	   this.removeAllBreakpoints();
	   final ArrayList<Integer> breakpointByteOffsets = gui_.breakpointByteOffsets();
	   for (final Integer ii: breakpointByteOffsets) {
		   final int bufferOffset = ii.intValue();
		   final int lineNumber = gui_.lineNumberForBufferOffset(bufferOffset);
		   final int realLineNumber = this.plantBreakpointInExpression(bufferOffset, lineNumber, parseRun);
		   if (-2 == realLineNumber) {
			   debuggerWindowMessage(
					   "Internal error: there is already a breakpoint at line " + lineNumber + ".\n"
					   );
		   } else if (-1 == realLineNumber) {
			   debuggerWindowMessage(
					   "Internal error: cannot set breakpoint at line " + lineNumber + ".\n"
					   );
		   } else if (realLineNumber != lineNumber) {
			   debuggerWindowMessage(
					   "Internal error: breakpoint at line " + lineNumber +
					   	" failed, replaced by breakpoint at line " + realLineNumber + ".\n"
					   );
		   }
	   }
   }
   public void removeAllBreakpoints() {
	   for (final RTCode expr: allBreakpointedExpressions_) {
		   // If the code has been re-parsed and this expr doesn't
		   // exist any more, that's O.K. - the GC won't have gotten
		   // it because the allBreakpointedExpressions_ array keeps it
		   // around. It is of no consequence. Removing the breakpoint
		   // doesn't help anything but it is innocuous.
		   //
		   // No matter how the source code has moved around, we still
		   // have the original expr where the breakpoint was planted,
		   // and we can clean it up.
		   expr.setBreakpoint(false);
	   }
	   
	   // It is really necessary to remove all items. Subsequent
	   // attempts to set breakpoints will check if the expression
	   // is already in this list. If it is, it will be an error.
	   allBreakpointedExpressions_.clear();
   }
   
   private void filterFieldActionPerformed(ActionEvent event) {
	   filterText_ = filterField_.getText();
	   filterField_.setText("");
	   filterTextAck_.setText(filterText_);
	   if (null != breakpointSemaphore_ && breakpointSemaphore_.locked() == false) {
		   // We're halted. Update the display
		   objectOverviewPanelContent_ = "";
		   displayObjectSnapshot(RunTimeEnvironment.runTimeEnvironment_.swingWorkerDynamicScope());
	   }
   }
   
   public void updateParsingStatus(int numberOfErrors, boolean parseNeeded) {
	   if (0 == numberOfErrors && false == parseNeeded) {
		   setBreakpointButton_.setEnabled(null != RunTimeEnvironment.runTimeEnvironment_);
		   removeBreakpointButton_.setEnabled(null != RunTimeEnvironment.runTimeEnvironment_);
	   } else {
		   setBreakpointButton_.setEnabled(false);
		   removeBreakpointButton_.setEnabled(false);
	   }
   }
   public void running() {
	   debuggerWindowMessage("Running\n");
	   pauseButton_.setEnabled(true);
	   objectOverviewPanelContent_ = "";
	   objectOverviewPanel_.setText(objectOverviewPanelContent_);
   }
   public void enableRunButton(boolean tf) {
	   runButton_.setEnabled(tf);
   }
   public void enableInterruptButton(boolean tf) {
	   interruptButton_.setEnabled(tf);
	   pauseButton_.setEnabled(tf);
   }
   public void debuggerWindowMessage(final String message) {
	   tracebackPanel_.setText("");
	   messagePanelContent_ = messagePanelContent_.concat(message);
	   messagePanel_.setText(messagePanelContent_);
	   final JScrollBar vertical = messageScrollPane_.getVerticalScrollBar();
	   vertical.setValue( vertical.getMaximum() );
   }
   private void stopCommon(final RTCode code) {
	   this.printMiniStackStatus();
	   objectOverviewPanelContent_ = "";
	   displayObjectSnapshot(RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope());
	   pauseButton_.setEnabled(false);
	   int lineNumber = code.lineNumber() - 5;
	   if (0 > lineNumber) lineNumber = 5;
	   gui_.makeLineVisible(lineNumber);
	   breakpointSemaphore_ = new RTSemaphore();
	   try {
		   breakpointSemaphore_.release();
	   } catch (InterruptedException e) {
		   e.printStackTrace();
		   breakpointSemaphore_.take();
	   }
   }
   public void pauseAt(final RTCode code) {
	   continueButton_.setEnabled(true);
	   debuggerWindowMessage("Paused at line " + code.lineNumber() + ". Stopped.\n");
	   this.stopCommon(code);
   }
   public void breakpointFiredAt(final RTCode code) {
	   continueButton_.setEnabled(true);
	   debuggerWindowMessage("Breakpoint at line " + code.lineNumber() + ". Stopped.\n");
	   this.stopCommon(code);
   }
   
   private BTreeElement snapshotAnElement(final String name, final RTObject instanceArg, final int indent) {
	   BTreeElement retval = null, element = null;
	   String rowString = "";
	   RTObject instance = instanceArg;
	   if (name.startsWith("temp$") || name.startsWith("parseTemp$")) {
		   return null;
	   }
	   for (int i = 0; i < indent; i++) {
		   rowString += SPACES;
	   }
	   if (false == name.equalsIgnoreCase("context$info")) {
		   rowString += name + ": ";
	   }
	   
	   // We need to treat GraphicsPanel differently. GraphicsPanel
	   // is managed internally but has the appearance of a user-
	   // defined type, so it does not honor the same RTType protocol
	   // as other built-ins. That means that calling rTType on
	   // instance results in an assertion. But users can subclass
	   // GraphicsPanel, so it can show up here.
	   final RTType type = instanceArg instanceof GraphicsPanel?
			   null:
			   instance.rTType();
	   
	   if (instanceArg instanceof GraphicsPanel) {
		   rowString += instance.toString() + "\n";
		   retval = tree_.new Row(tree_, rowString);
	   } else if (type instanceof RTStringClass) {
		   final RTStringObject stringObject = (RTStringObject) instance;
		   rowString += "\"" + stringObject.toString() + "\"\n";
		   retval = tree_.new Row(tree_, rowString);
	   } else if (type instanceof RTIntegerClass) {
		   final RTIntegerObject integerObject = (RTIntegerObject) instance;
		   rowString += integerObject.toString() + "\n";
		   retval = tree_.new Row(tree_, rowString);
	   } else if (instance instanceof RTListObject) {
		   rowString += instance.rTType().name() + "\n"; // instance.toString();
		   retval = tree_.new Row(tree_, rowString);
		   
		   final RTListObject list = (RTListObject) instance;
		   for (int i = 0; i < list.size(); i++) {
			   final RTObject listElement = list.get(i);
			   element = snapshotAnElement(Integer.toString(i), listElement, indent + 1);
			   retval.addChild(element);
		   }
	   } else if (instance.getClass().getSimpleName().equals("RTObjectCommon")) {
		   // Is a class object
		   // Put type out on this line
		   rowString += "instance of class " + type.name() + "\n";
		   retval = tree_.new Row(tree_, rowString);
		   rowString = "";
		   
		   final RTObjectCommon rtocInstance = (RTObjectCommon) instance;
		   final Map<String,RTObject> objectMembers = rtocInstance.objectMembers();
		   final Iterator<String> objectNameIter = objectMembers.keySet().iterator();
		   while (objectNameIter.hasNext()) {
			   final String identifierName = objectNameIter.next();
			   instance = objectMembers.get(identifierName);
			   element = snapshotAnElement(identifierName, instance, indent + 1);
			   retval.addChild(element);
		   }
	   } else if (instance instanceof RTContextObject) {
		   // Is a Context object
		   rowString += "instance of Context " + type.name() + "\n";
		   retval = tree_.new Row(tree_, rowString);
		   rowString = "";
		   
		   final RTContextObject rtcoInstance = (RTContextObject) instance;
		   final Map<String,RTObject> objectMembers = rtcoInstance.objectMembers();
		   final Iterator<String> objectNameIter = objectMembers.keySet().iterator();
		   while (objectNameIter.hasNext()) {
			   final String identifierName = objectNameIter.next();
			   instance = objectMembers.get(identifierName);
			   element = snapshotAnElement(identifierName, instance, indent + 1);
			   retval.addChild(element);
		   }
	   } else if (instance instanceof RTContextInfo) {
		   final RTContextInfo contextInfo = (RTContextInfo) instance;
		   final Map<String, RTObject> rolePlayers = contextInfo.rolePlayers();
		   final Map<String, RTObject> stagePropPlayers = contextInfo.rolePlayers();
		   
		   // For RTContextInfo, there is nothing printed above in this cycle.
		   // rowString is a set of one-level indented blanks, and if we put
		   // it as an argument to Row it screws things up in the output...
		   retval = tree_.new Row(tree_);
		   
		   for (final String roleName: rolePlayers.keySet() ) {
			   rowString = "";
			   for (int i = 0; i < indent; i++) {
				   rowString += SPACES;
			   }
			   final RTObject rolePlayer = rolePlayers.get(roleName);
			   final String typeName = null == rolePlayer.rTType()? "<NULL>": rolePlayer.rTType().name();
			   rowString += "Role " +
					   roleName + " bound to object of type " + typeName +
					   "\n";

			   final PrintedBTree.Row newRow = tree_.new Row(tree_, rowString);
			   retval.addChild(newRow);
			   element = snapshotAnElement(roleName, rolePlayer, indent + 1);
			   newRow.addChild(element);
		   }
		   
		   for (final String stagePropName: stagePropPlayers.keySet() ) {
			   rowString = "";
			   for (int i = 0; i < indent; i++) {
				   rowString += SPACES;
			   }
			   final RTObject stagePropPlayer = stagePropPlayers.get(stagePropName);
			   final String typeName = null == stagePropPlayer.rTType()? "<NULL>": stagePropPlayer.rTType().name();
			   rowString += "Stageprop " +
					   stagePropName + " bound to object of type " + typeName +
					   "\n";
			   final PrintedBTree.Row newRow = tree_.new Row(tree_, rowString);
			   retval.addChild(newRow);
			   element = snapshotAnElement(stagePropName, stagePropPlayer, indent + 1);
			   newRow.addChild(element);
		   }
	   } else {
		   rowString += instance.toString() + "\n";
		   retval = tree_.new Row(tree_, rowString);
	   }
	   return retval;
   }
   private void displayObjectSnapshot(final RTDynamicScope scope) {
	   tree_ = new PrintedBTree();
	   displayObjectSnapshotRecur(scope);
   }
   private void displayObjectSnapshotRecur(final RTDynamicScope scopeArg) {
	   BTreeElement element = null;
	   if (null != scopeArg) {
		   RTDynamicScope scope = scopeArg;
		   final Map<String,RTObject> objectMembers = scope.objectMembers();
		   final Iterator<String> objectNameIter = objectMembers.keySet().iterator();
		   while (objectNameIter.hasNext()) {
			   final String identifierName = objectNameIter.next();
			   final RTObject instance = objectMembers.get(identifierName);
			   element = snapshotAnElement(identifierName, instance, 0);
			   tree_.addChild(element);
		   }
		   scope = scope.parentScope();
		   displayObjectSnapshotRecur(scope);
		   if (null == scope || scope.isARealMethodScope()) {
			   // Now dump it in the window
			   // old: objectOverviewPanelContent_ = tree_.toString();
			   objectOverviewPanelContent_ = tree_.filterTraverse(filterText_);	// new
			   objectOverviewPanel_.setText(objectOverviewPanelContent_);
			   final JScrollBar vertical = objectScrollPane_.getVerticalScrollBar();
			   vertical.setValue( vertical.getMaximum() );
			   objectOverviewPanel_.updateUI();
		   }
	   }
   }

   public void close() {
	   // Go through and tear out all the annotations
	   // in the edit pane
	   
	   // This doesn't work. The user may have edited the buffer
	   // since the breakpoints were set which means the offsets
	   // are wrong.
	   /*
	   final Iterator<Map.Entry<Integer, Object>> it = breakpoints_.entrySet().iterator();
	   while (it.hasNext()) {
	    	Map.Entry<Integer, Object> pair = (Map.Entry<Integer, Object>)it.next();
	    	gui_.removeBreakpointAt(pair.getKey().intValue());
	   }
	   */
	   
	   // Instead, just look for everything in the buffer with this annotation
	   // and remove it
	   gui_.removeAllBreakpoints();
	   gui_.unregisterDebugger();
	   
	   // take out the hooks in all the breakpoints we planted
	   this.removeAllBreakpoints();
	   this.dispose();	// suicide
   }
   private void printMiniStackStatus() {
		// Experiment: Pop down stack looking for information about the method
	    RTPostReturnProcessing returnChit = null;
	    int lastLineNumber = RunTimeEnvironment.runTimeEnvironment_.currentExecutingLineNumber();
	    final Stack<RTStackable> theStack = RunTimeEnvironment.runTimeEnvironment_.theStack();
		RTStackable topOfStack = null;
		tracebackScrollContent_ = "";
		for (int stackIndex = theStack.size() - 1; 0 <= stackIndex; stackIndex--) {
			RTPostReturnProcessing currentMethodReturn;
			topOfStack = theStack.get(stackIndex);
			if (topOfStack instanceof RTPostReturnProcessing) {
				returnChit = (RTPostReturnProcessing) topOfStack;
				currentMethodReturn = (RTPostReturnProcessing)topOfStack;
				tracebackScrollContent_ = tracebackScrollContent_.concat("In `" +
						currentMethodReturn.debugName() + "." +
						currentMethodReturn.name() + ":' " + lastLineNumber + "\n");
				lastLineNumber = returnChit.lineNumber();
			} else {
				tracebackScrollContent_ = tracebackScrollContent_.concat(
						"In " + topOfStack.getClass().getSimpleName() + ": "
						+ "???" + "\n");
			}
			for (stackIndex--; 0 <= stackIndex; stackIndex--) {
				topOfStack = theStack.get(stackIndex);
				if (topOfStack instanceof RTPostReturnProcessing) {
					returnChit = (RTPostReturnProcessing) topOfStack;
					currentMethodReturn = (RTPostReturnProcessing)topOfStack;
					tracebackScrollContent_ = tracebackScrollContent_.concat(
							" Called from `" + currentMethodReturn.debugName() + "." +
							currentMethodReturn.name() + ":' " + lastLineNumber + "\n");
					lastLineNumber = returnChit.lineNumber();
				}
			}
			/*
			tracebackScrollContent_ = tracebackScrollContent_.concat(
					" popping stack: type is " + topOfStack.getClass().getSimpleName() + "\n");
			*/
		}
		if (topOfStack instanceof RTPostReturnProcessing) {
			returnChit = (RTPostReturnProcessing) topOfStack;
			tracebackScrollContent_ = tracebackScrollContent_.concat(
				" Called from `main:' " + returnChit.lineNumber() + "\n");
		} else if (null == topOfStack) {
			final int current = RunTimeEnvironment.runTimeEnvironment_.currentExecutingLineNumber();
			tracebackScrollContent_ = tracebackScrollContent_.concat(
					" Called from `main:' " + current + "\n");
		}

		tracebackPanel_.setText(tracebackScrollContent_);
		final JScrollBar vertical = tracebackScrollPane_.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}
   
   
	public interface BTreeElement {
		public String toString();
		public void addChild(final BTreeElement element);
		public void setParent(final BTreeElement element);
		public BTreeElement parent();
		public ArrayList<BTreeElement> children();
		public void mark();
		public void markAll();
		public boolean marked();
		public void unmarkAll();
		public String localToString();
		public boolean thisLevelMatchesKey(final String filter);
	}
	public abstract class PrintedBTreeCommon implements BTreeElement {
		@Override public void mark() { marked_ = true; }
		@Override public boolean marked() { return marked_; }
		@Override public void unmarkAll() {
			marked_ = false;
			for (BTreeElement element: children()) element.unmarkAll();
		}
		@Override public void markAll() {
			marked_ = true;
			for (BTreeElement element: children()) element.markAll();
		}
		@Override public boolean thisLevelMatchesKey(final String filter) {
			return -1 != localToString().indexOf(filter);
		}
		@Override public BTreeElement parent() { return parent_; }
		@Override public void setParent(final BTreeElement element) { parent_ = element; }
		
		protected BTreeElement parent_;
		protected boolean marked_ = false;
	}
	public class PrintedBTree extends PrintedBTreeCommon {
		public PrintedBTree() {
			tree_ = new Row(null);
		}
		public class Row extends PrintedBTreeCommon {
			public Row(final BTreeElement parent) {
				localString_ = "";
				row_ = new ArrayList<BTreeElement>();
				parent_ = parent;
			}
			public Row(final BTreeElement parent, final String string) {
				row_ = new ArrayList<BTreeElement>();
				addChild(new Atom(this, string));
				parent_ = parent;
				localString_ = string;
			}
			@Override public String toString() {
				String retval = "";
				for (final BTreeElement printable: row_) {
					if (null != printable) {
						retval += printable.toString();
					}
				}
				return retval;
			}
			@Override public String localToString() { return localString_; }
			@Override public void addChild(final BTreeElement element) { if (null == element) return; element.setParent(this); row_.add(element); }
			@Override public ArrayList<BTreeElement> children() { return row_; }
			
			private final ArrayList<BTreeElement> row_;
			private final String localString_;
		}
		public class Atom extends PrintedBTreeCommon {
			public Atom(final BTreeElement parent, final String string) {
				string_ = string;
				parent_ = parent;
			}
			@Override public String toString() { return string_; }
			@Override public void addChild(final BTreeElement element) { assert false; }
			@Override public ArrayList<BTreeElement> children() { return new ArrayList<BTreeElement>(); }
			@Override public String localToString() { return string_; }
			private final String string_;
		}
		@Override public BTreeElement parent() { return tree_.parent(); }
		@Override public void setParent(final BTreeElement parent) { tree_.setParent(parent); }
		@Override public String toString() { return tree_.toString(); }
		          public String filterTraverse(final String filter) {
		        	  unmarkAll();
		        	  String retval = recursiveFilterTraverse(this, filter);
		        	  return retval;
		          }
		          private String recursiveFilterTraverse(final BTreeElement element, final String filter) {
		        	  String retval = "";
		        	  if (false == marked()) {
		        		  // See if just this level matches the key
		        		  if (0 == filter.length() || element.thisLevelMatchesKey(filter)) {
		        			  // We have a winner:
		        			  final Stack<String> pathToTop = new Stack<String>();
		        			  for (BTreeElement walker = element.parent(); null!= walker; walker = walker.parent()) {
		        				  if (walker.marked()) break;		// risky, but might be pretty
		        				  final String thisPath = walker.localToString();
		        				  walker.mark();
		        				  pathToTop.push(thisPath);
		        			  }
		        			  while (false == pathToTop.isEmpty()) {
		        				  retval += pathToTop.pop();
		        			  }
		        			  element.markAll();
		        			  retval += element.toString();
		        		  } else {
		        			  for (final BTreeElement element2: element.children()) {
		        				  retval += recursiveFilterTraverse(element2, filter);
		        			  }
		        		  }
		        	  }
		        	  return retval;
		          }
		@Override public String localToString() { return tree_.localToString(); }
		@Override public void addChild(final BTreeElement element) { if (null == element) return; element.setParent(this); tree_.addChild(element); }
		@Override public ArrayList<BTreeElement> children() { return tree_.children(); }
		private final BTreeElement tree_;
	}
   
   private final static long serialVersionUID = 438512109;
   private final JButton setBreakpointButton_;
   private final JButton removeBreakpointButton_;
   private final JButton runButton_;
   private final JButton continueButton_;
   private final JButton interruptButton_;
   private final JButton clearButton_;
   private final JButton pauseButton_;
   private final JLabel filterLabel_, filterTextAck_;
   private final JTextField filterField_;
   private       String filterText_;
   private final TextEditorGUI gui_;
   private RTDebuggerWindow this_;
   private RTSemaphore breakpointSemaphore_;
   private final JScrollPane messageScrollPane_;
   private final JTextArea messagePanel_;
   private final JScrollPane tracebackScrollPane_;
   private final JTextArea tracebackPanel_;
   private final JScrollPane objectScrollPane_;
   private final JTextArea objectOverviewPanel_;
   private String messagePanelContent_ = "", tracebackScrollContent_ = "", objectOverviewPanelContent_ = "";
   private final ArrayList<RTCode> allBreakpointedExpressions_;
   private       PrintedBTree tree_;
   private String SPACES = "    ";
}
