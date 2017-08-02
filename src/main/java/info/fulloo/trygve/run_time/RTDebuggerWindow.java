package info.fulloo.trygve.run_time;

import info.fulloo.trygve.editor.TextEditorGUI;
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


// https://www3.ntu.edu.sg/home/ehchua/programming/java/j5e_multithreading.html
public class RTDebuggerWindow extends JFrame {
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
      
      messagePanel_ = new javax.swing.JTextArea(15, 35);
  	  messagePanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
      messagePanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  messagePanel_.setSize(550, 300);
  	  
  	  messageScrollPane_ = new javax.swing.JScrollPane(messagePanel_);
  	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  messageScrollPane_.setPreferredSize(new Dimension(350, 300));
  	  
  	  tracebackPanel_ = new javax.swing.JTextArea(15, 35);
  	  tracebackPanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
  	  tracebackPanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  tracebackPanel_.setSize(600, 300);
	  
	  tracebackScrollPane_ = new javax.swing.JScrollPane(tracebackPanel_);
	  tracebackScrollPane_.setPreferredSize(new Dimension(350, 300));
	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  
  	  objectOverviewPanel_ = new javax.swing.JTextArea(15, 35);
  	  objectOverviewPanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
  	  objectOverviewPanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  objectOverviewPanel_.setSize(600, 300);
	  
  	  objectScrollPane_ = new javax.swing.JScrollPane(objectOverviewPanel_);
  	  objectScrollPane_.setPreferredSize(new Dimension(350, 300));
  	  
  	  final int columns = 20;
  	  filterText_ = "";
  	  filterField_ = new javax.swing.JTextField(columns);
  	  filterField_.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
     	     filterFieldActionPerformed(evt);
        }
     });
	  
  	  layout.setAutoCreateGaps(true);
      layout.setHorizontalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
              .addComponent(setBreakpointButton_)
              .addComponent(removeBreakpointButton_)
              .addComponent(continueButton_)
              .addComponent(runButton_)
              .addComponent(pauseButton_)
              .addComponent(interruptButton_))
          .addGroup(layout.createSequentialGroup()
        	  .addGroup(layout.createParallelGroup()
                          .addComponent(messageScrollPane_)
                    	  .addComponent(clearButton_))
              .addComponent(tracebackScrollPane_)
              .addGroup(layout.createParallelGroup()
                  .addComponent(objectScrollPane_)
            	  .addComponent(filterField_)))
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
        				    .addComponent(filterField_))))
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
   private String snapshotAnElement(final String name, final RTObject instanceArg, int indent, final boolean printAllBelowArg) {
	   boolean printAllBelow = printAllBelowArg;
	   String retval = "";
	   RTObject instance = instanceArg;
	   if (name.startsWith("temp$")) {
		   return retval;
	   }
	   for (int i = 0; i < indent; i++) {
		   retval += "    ";
	   }
	   if (false == name.equalsIgnoreCase("context$info")) {
		   retval += name + ": ";
	   }
	   final RTType type = instance.rTType();
	   if (type instanceof RTStringClass) {
		   final RTStringObject stringObject = (RTStringObject) instance;
		   retval += "\"" + stringObject.toString() + "\"\n";
	   } else if (type instanceof RTIntegerClass) {
		   final RTIntegerObject integerObject = (RTIntegerObject) instance;
		   retval += integerObject.toString() + "\n";
	   } else if (instance instanceof RTListObject) {
		   retval += instance.rTType().name() + "\n"; // instance.toString();
		   final RTListObject list = (RTListObject) instance;
		   for (int i = 0; i < list.size(); i++) {
			   final RTObject listElement = list.get(i);
			   retval += snapshotAnElement(Integer.toString(i), listElement, indent + 1, printAllBelow);
		   }
	   } else if (instance.getClass().getSimpleName().equals("RTObjectCommon")) {
		   // Is a class object
		   // Put type out on this line
		   retval += "instance of class " + type.name() + "\n";
		   final RTObjectCommon rtocInstance = (RTObjectCommon) instance;
		   final Map<String,RTObject> objectMembers = rtocInstance.objectMembers();
		   final Iterator<String> objectNameIter = objectMembers.keySet().iterator();
		   while (objectNameIter.hasNext()) {
			   final String identifierName = objectNameIter.next();
			   instance = objectMembers.get(identifierName);
			   retval += snapshotAnElement(identifierName, instance, indent + 1, printAllBelow);
		   }
	   } else if (instance instanceof RTContextObject) {
		   // Is a Context object
		   retval += "instance of context " + type.name() + "\n";
		   final RTContextObject rtcoInstance = (RTContextObject) instance;
		   final Map<String,RTObject> objectMembers = rtcoInstance.objectMembers();
		   final Iterator<String> objectNameIter = objectMembers.keySet().iterator();
		   while (objectNameIter.hasNext()) {
			   final String identifierName = objectNameIter.next();
			   instance = objectMembers.get(identifierName);
			   retval += snapshotAnElement(identifierName, instance, indent + 1, printAllBelow);
		   }
	   } else if (instance instanceof RTContextInfo) {
		   final RTContextInfo contextInfo = (RTContextInfo) instance;
		   final Map<String, RTObject> rolePlayers = contextInfo.rolePlayers();
		   final Map<String, RTObject> stagePropPlayers = contextInfo.rolePlayers();
		   
		   for (final String roleName: rolePlayers.keySet() ) {
			   final RTObject rolePlayer = rolePlayers.get(roleName);
			   final String typeName = null == rolePlayer.rTType()? "<NULL>": rolePlayer.rTType().name();
			   retval += "Role " +
					   roleName + " bound to object of type " + typeName +
					   "\n";
			   retval += snapshotAnElement(roleName, rolePlayer, indent + 1, printAllBelow);
		   }
		   
		   for (int i = 0; i < indent; i++) {
			   retval += "    ";
		   }
		   for (final String stagePropName: stagePropPlayers.keySet() ) {
			   final RTObject stagePropPlayer = stagePropPlayers.get(stagePropName);
			   final String typeName = null == stagePropPlayer.rTType()? "<NULL>": stagePropPlayer.rTType().name();
			   retval += "Stageprop " +
					   stagePropName + " bound to object of type " + typeName +
					   "\n";
			   retval += snapshotAnElement(stagePropName, stagePropPlayer, indent + 1, printAllBelow);
		   }
	   } else {
		   retval += instance.toString() + "\n";
	   }
	   return retval;
   }
   private void displayObjectSnapshot(RTDynamicScope scope) {
	   if (null != scope) {
		   Map<String,RTObject> objectMembers = scope.objectMembers();
		   Iterator<String> objectNameIter = objectMembers.keySet().iterator();
		   while (objectNameIter.hasNext()) {
			   final String identifierName = objectNameIter.next();
			   final RTObject instance = objectMembers.get(identifierName);
			   final String thisMember = snapshotAnElement(identifierName, instance, 0, false);
			   if (0 == filterText_.length() || -1 != thisMember.indexOf(filterText_)) {
				   objectOverviewPanelContent_ += thisMember;
			   }
		   }
		   scope = scope.parentScope();
		   displayObjectSnapshot(scope);
		   if (null == scope || scope.isARealMethodScope()) {
			   // Now dump it in the window
			   objectOverviewPanel_.setText(objectOverviewPanelContent_);
			   final JScrollBar vertical = objectScrollPane_.getVerticalScrollBar();
			   vertical.setValue( vertical.getMaximum() );
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
   
   private final static long serialVersionUID = 438512109;
   private final JButton setBreakpointButton_;
   private final JButton removeBreakpointButton_;
   private final JButton runButton_;
   private final JButton continueButton_;
   private final JButton interruptButton_;
   private final JButton clearButton_;
   private final JButton pauseButton_;
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
}
