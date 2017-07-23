package info.fulloo.trygve.run_time;

import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.parser.ParseRun;
import info.fulloo.trygve.run_time.RTExpression.RTMessage.RTPostReturnProcessing;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;


// https://www3.ntu.edu.sg/home/ehchua/programming/java/j5e_multithreading.html
public class RTDebuggerWindow extends JFrame {
   public RTDebuggerWindow(TextEditorGUI gui) {
	  gui_ = gui;
	  allBreakpointedExpressions_ = new ArrayList<RTCode>();
      final Container cp = getContentPane();
      cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
      
      setBreakpointButton_ = new JButton("Set Breakpoint at cursor");
      cp.add(setBreakpointButton_);
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
      cp.add(removeBreakpointButton_);
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
      cp.add(continueButton_);
      continueButton_.setEnabled(false);
      continueButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 continueButton_.setEnabled(false);
        	 pauseButton_.setEnabled(true);
        	 breakpointSemaphore_.take();
        	 debuggerWindowMessage("Running\n");
         }
      });
      
      runButton_ = new JButton("Run");
      cp.add(runButton_);
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
      cp.add(pauseButton_);
      pauseButton_.setEnabled(false);
      pauseButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 pauseButton_.setEnabled(false);
      	     RunTimeEnvironment.runTimeEnvironment_.pauseButtonActionPerformed();
         }
      });
      
      interruptButton_ = new JButton("Interrupt");
      cp.add(interruptButton_);
      interruptButton_.setEnabled(true);
      interruptButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
      	     gui_.interruptButtonActionPerformed(evt);
         }
      });
      
      messagePanel_ = new javax.swing.JTextArea(15, 35);
  	  messagePanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
      messagePanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  messagePanel_.setSize(550, 300);
  	  
  	  messageScrollPane_ = new javax.swing.JScrollPane(messagePanel_);
  	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  messageScrollPane_.setPreferredSize(new Dimension(350, 300));
  	  cp.add(messageScrollPane_);
  	  
  	  tracebackPanel_ = new javax.swing.JTextArea(15, 35);
  	  tracebackPanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
  	  tracebackPanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  tracebackPanel_.setSize(600, 300);
	  
	  tracebackScrollPane_ = new javax.swing.JScrollPane(tracebackPanel_);
	  tracebackScrollPane_.setPreferredSize(new Dimension(350, 300));
	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  cp.add(tracebackScrollPane_);
 
      // Not: setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); instead:
      this_ = this;
      addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    	        this_.close();
    	    }
    	});
      
      setTitle("Debugger");
      setSize(780, 400);
      setLocation(600, 200);
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
   private void stopCommon() {
	   this.printMiniStackStatus();
	   breakpointSemaphore_ = new RTSemaphore();
	   try {
		   breakpointSemaphore_.release();
	   } catch (InterruptedException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   breakpointSemaphore_.take();
	   }
	   pauseButton_.setEnabled(false);
   }
   public void pauseAt(RTCode code) {
	   continueButton_.setEnabled(true);
	   debuggerWindowMessage("Paused at line " + code.lineNumber() + ". Stopped.\n");
	   this.stopCommon();
   }
   public void breakpointFiredAt(RTCode code) {
	   continueButton_.setEnabled(true);
	   debuggerWindowMessage("Breakpoint at line " + code.lineNumber() + ". Stopped.\n");
	   this.stopCommon();
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
   private final JButton pauseButton_;
   private final TextEditorGUI gui_;
   private RTDebuggerWindow this_;
   private RTSemaphore breakpointSemaphore_;
   private final JScrollPane messageScrollPane_;
   private final JTextArea messagePanel_;
   private final JScrollPane tracebackScrollPane_;
   private final JTextArea tracebackPanel_;
   private String messagePanelContent_ = "", tracebackScrollContent_ = "";
   private final ArrayList<RTCode> allBreakpointedExpressions_;
}
