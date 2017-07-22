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
      this.updateParsingStatus(0, false);
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
      
      continueButton_ = new JButton("Continue");
      cp.add(continueButton_);
      continueButton_.setEnabled(false);
      continueButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 continueButton_.setEnabled(false);
        	 breakpointSemaphore_.take();
        	 debuggerWindowMessage(
      			   "Running\n"
      			   );
         }
      });
      
      runButton_ = new JButton("Run");
      cp.add(runButton_);
      runButton_.setEnabled(true);
      runButton_.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
        	 runButton_.setEnabled(false);
      	     gui_.runButtonActionPerformed(evt);
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
      
      messagePanel_ = new javax.swing.JTextArea(15, 25);
  	  messagePanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
      messagePanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  messagePanel_.setSize(500, 300);
  	  
  	  messageScrollPane_ = new javax.swing.JScrollPane(messagePanel_);
  	  javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
  	  cp.add(messageScrollPane_);
  	  
  	  tracebackPanel_ = new javax.swing.JTextArea(15, 25);
  	  tracebackPanel_.setMargin(new java.awt.Insets(3, 3, 3, 3));
  	  tracebackPanel_.setBackground(new java.awt.Color(233, 228, 242));
  	  tracebackPanel_.setSize(500, 300);
	  
	  tracebackScrollPane_ = new javax.swing.JScrollPane(tracebackPanel_);
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
      setSize(700, 400);
      setLocation(600, 200);
      setVisible(true);
   }
   private int markExpressionWithBreakpoint(int byteOffset, int lineNumber, ParseRun parseRun) {
	   final RTCode breakableExpression = parseRun.expressionForByteOffsetInBuffer(byteOffset, gui_);
	   int realLineNumber = -1;	// error return
	   if (null != breakableExpression) {
		   breakableExpression.setBreakpoint(true);
		   realLineNumber = breakableExpression.lineNumber();
		   allBreakpointedExpressions_.add(breakableExpression);
	   } else {
		   // error message
	   }
	   
	   return realLineNumber;
   }
   private void setBreakpointAt(int byteOffset, int lineNumber, ParseRun parseRun) {
	   // Go through the program looking for the first expression
	   // at this line number
	   final int realLineNumber = this.markExpressionWithBreakpoint(byteOffset, lineNumber, parseRun);
	   if (-1 == realLineNumber) {
		   debuggerWindowMessage(
				   "Cannot set breakpoint at line " + lineNumber + ".\n"
				   );
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
   public void updateParsingStatus(int numberOfErrors, boolean parseNeeded) {
	   if (0 == numberOfErrors && false == parseNeeded) {
		   setBreakpointButton_.setEnabled(null != RunTimeEnvironment.runTimeEnvironment_);
	   } else {
		   setBreakpointButton_.setEnabled(false);
	   }
   }
   public void running() {
	   debuggerWindowMessage(
			   "Running\n"
			   );
   }
   public void enableRunButton(boolean tf) {
	   runButton_.setEnabled(tf);
   }
   public void enableInterruptButton(boolean tf) {
	   interruptButton_.setEnabled(tf);
   }
   public void debuggerWindowMessage(final String message) {
	   tracebackPanel_.setText("");
	   messagePanelContent_ = messagePanelContent_.concat(message);
	   messagePanel_.setText(messagePanelContent_);
	   final JScrollBar vertical = messageScrollPane_.getVerticalScrollBar();
	   vertical.setValue( vertical.getMaximum() );
   }
   public void breakpointFiredAt(RTCode code) {
	   continueButton_.setEnabled(true);
	   debuggerWindowMessage(
			   "Breakpoint at line " + code.lineNumber() + ". Stopped.\n"
			   );
	   this.printMiniStackStatus();
	   breakpointSemaphore_ = new RTSemaphore();
	   try {
		   breakpointSemaphore_.release();
	   } catch (InterruptedException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   breakpointSemaphore_.take();
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
   
   public void removeAllBreakpoints() {
	   for (RTCode expr: allBreakpointedExpressions_) {
		   expr.setBreakpoint(false);
	   }
   }
   
   private void printMiniStackStatus() {
		// Experiment: Pop down stack looking for information about the method
	    final Stack<RTStackable> theStack = RunTimeEnvironment.runTimeEnvironment_.theStack();
		RTStackable topOfStack;
		tracebackScrollContent_ = "";
		for (int stackIndex = theStack.size() - 1; 0 <= stackIndex; stackIndex--) {
			topOfStack = theStack.get(stackIndex);
			if (topOfStack instanceof RTPostReturnProcessing) {
				RTPostReturnProcessing currentMethodReturn = (RTPostReturnProcessing)topOfStack;
				tracebackScrollContent_ = tracebackScrollContent_.concat("In `" +
						currentMethodReturn.debugName() + "." +
						currentMethodReturn.name() + "'\n");
				for (; 0 <= stackIndex; stackIndex--) {
					topOfStack = theStack.get(stackIndex);
					if (topOfStack instanceof RTPostReturnProcessing) {
						currentMethodReturn = (RTPostReturnProcessing)topOfStack;
						tracebackScrollContent_ = tracebackScrollContent_.concat(
								" Called from `" + currentMethodReturn.debugName() + "." +
								currentMethodReturn.name() + "'\n");
					}
				}
			}
			tracebackScrollContent_ = tracebackScrollContent_.concat(
					" popping stack: type is " + topOfStack.getClass().getSimpleName() + "\n");
		}
		tracebackScrollContent_ = tracebackScrollContent_.concat("\n");
		tracebackPanel_.setText(tracebackScrollContent_);
		final JScrollBar vertical = tracebackScrollPane_.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}
   
   private final static long serialVersionUID = 438512109;
   private final JButton setBreakpointButton_;
   private final JButton runButton_;
   private final JButton continueButton_;
   private final JButton interruptButton_;
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
