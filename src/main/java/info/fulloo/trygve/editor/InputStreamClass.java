package info.fulloo.trygve.editor;

import java.awt.Event;

import static java.util.Arrays.asList;
import info.fulloo.trygve.declarations.AccessQualifier;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.graphics.GraphicsEventHandler;
import info.fulloo.trygve.graphics.GraphicsPanel;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTDynamicScope;
import info.fulloo.trygve.run_time.RTObject;
import info.fulloo.trygve.run_time.RTObjectCommon;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTClass.RTSystemClass.RTInputStreamInfo;
import info.fulloo.trygve.run_time.RTExpression.RTMessage;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.JTextComponent;

/*
 * Trygve IDE 1.6
 *   Copyright (c)2016 James O. Coplien, jcoplien@gmail.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */


public class InputStreamClass {
	public static void declareInputStreamMethod(final String methodSelector, final Type returnType,
			final String paramName,
			final Type paramType, final boolean isConst) {
		final AccessQualifier Public = AccessQualifier.PublicAccess;
		
		final FormalParameterList formals = new FormalParameterList();
		if (null != paramName) {
			final ObjectDeclaration formalParameter = new ObjectDeclaration(paramName, paramType, 0);
			formals.addFormalParameter(formalParameter);
		}
		final ObjectDeclaration self = new ObjectDeclaration("this", inputStreamType_, 0);
		formals.addFormalParameter(self);
		StaticScope methodScope = new StaticScope(inputStreamType_.enclosedScope());
		final MethodDeclaration methodDecl = new MethodDeclaration(methodSelector, methodScope, returnType, Public, 0, false);
		methodDecl.addParameterList(formals);
		methodDecl.setReturnType(returnType);
		methodDecl.setHasConstModifier(isConst);
		inputStreamType_.enclosedScope().declareMethod(methodDecl);
	}
	
	public static void setup() {
		final StaticScope globalScope = StaticScope.globalScope();
		if (null == globalScope.lookupTypeDeclaration("InputStream")) {
			typeDeclarationList_ = new ArrayList<TypeDeclaration>();
			final ClassDeclaration objectBaseClass = globalScope.lookupClassDeclaration("Object");
			assert null != objectBaseClass;

			final StaticScope newScope = new StaticScope(globalScope);
			final ClassDeclaration classDecl = new ClassDeclaration("InputStream", newScope, objectBaseClass, 0);
			newScope.setDeclaration(classDecl);
			inputStreamType_ = new ClassType("InputStream", newScope, null);
			classDecl.setType(inputStreamType_);
			typeDeclarationList_.add(classDecl);
			
			// method read()
			final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
			declareInputStreamMethod("read", intType, null, null, false);
			
			// special constructor
			final Type panelType = StaticScope.globalScope().lookupTypeDeclaration("Panel");
			assert null != panelType;
			declareInputStreamMethod("InputStream", null, "panel", panelType, false);
			
			// Declare the type
			globalScope.declareType(inputStreamType_);
			globalScope.declareClass(classDecl);
			
			// This is a kludge...  But an O.K. one.
			// This code belongs in System.setup(), but reciprocal precedence means that
			// we're stuck because System depends on InputStream and InputStream
			// depends on System. So we moved this here into the InputStream initialization.
			assert null != inputStreamType_;
			final Type systemClassType = StaticScope.globalScope().lookupTypeDeclaration("System");
			final ObjectDeclaration inputDeclaration = new ObjectDeclaration("in", inputStreamType_, 0);
			systemClassType.enclosedScope().declareStaticObject(inputDeclaration);
			systemClassType.declareStaticObject(inputDeclaration);
		}
	}
	public static class RTInputStreamCommon extends RTMessage {
		public RTInputStreamCommon(final String className, final String methodName,
				final String parameterName, final String parameterTypeName,
				final StaticScope enclosingMethodScope, final Type returnType) {
			super(methodName,
					RTMessage.buildArguments(className, methodName,
							null == parameterName?     null: asList(parameterName),
							null == parameterTypeName? null: asList(parameterTypeName),
							enclosingMethodScope, false),
					inputStreamType_, Expression.nearestEnclosingMegaTypeOf(enclosingMethodScope), false);
		}
		public RTCode run() {
			// Don't need to push or pop anything. The return code stays
			// until the RTReturn statement processes it, and everything
			// else has been popped into the activation record by
			// RTMessage
			// 		NO: returnCode = (RTCode)RunTimeEnvironment.runTimeEnvironment_.popStack();
			// 		Yes, but...: assert returnCode instanceof RTCode;
			
			// Parameters have all been packaged into the
			// activation record
			final RTObject myEnclosedScope = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			
			final RTObjectCommon theStream = (RTObjectCommon)myEnclosedScope.getObject("this");
			
			// Can be null, e.g., on a constructor call
			final RTCode nextPC = this.runDetails(myEnclosedScope, theStream);
			
			// We DO push a return value, which is just "this"
			// It is always returned. It is up to the RTReturn / RTMessage /
			// RTPostReturnProcessing logic to deal with consumption.
			
			final RTObject self = myEnclosedScope.getObject("this");
			assert null != self;
			RunTimeEnvironment.runTimeEnvironment_.pushStack(self);
			
			// All dogs go to heaven, and all return statements that
			// have something to return do it. We deal with consumption
			// in the message. This function's return statement will be
			// set for a consumed result in higher-level logic.
			
			return nextPC;
		}
		public RTCode runDetails(final RTObject scope, final RTObjectCommon finalStream) {
			// Effectively a pure virtual method, but Java screws us again...
			ErrorLogger.error(ErrorType.Internal, "call of pure virtual method runDetails (System domain)", "", "", "");
			return null;	// halt the machine
		}
		protected void addRetvalTo(final RTDynamicScope activationRecord) {
			if (null == activationRecord.getObject("ret$val")) {
				activationRecord.addObjectDeclaration("ret$val", null);
			}
		}
	}
	
	public static class RTInputStreamCtor1Code extends RTInputStreamCommon {
		public RTInputStreamCtor1Code(final StaticScope enclosingMethodScope) {
			super("InputStream", "InputStream", "panel", "PanelType", enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("void"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObjectCommon theStream) {
			// Second argument is probably null. Should maybe assert for it.
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTObject panelArg = (RTObject)activationRecord.getObject("panel");
			final GraphicsPanel panel = (GraphicsPanel)panelArg.getObject("panelObject");
			final PanelInputStream panelStream = new PanelInputStream(panelArg);
			final RTInputStreamInfo newInputStreamInfo = new RTInputStreamInfo(panelStream, panel);
			theStream.addObjectDeclaration("inputStreamInfo", null);
			assert null != newInputStreamInfo;
			theStream.setObject("inputStreamInfo", newInputStreamInfo);
			RunTimeEnvironment.runTimeEnvironment_.pushStack(theStream);
			return super.nextCode();
		}
	}
	public static class RTReadCode extends RTInputStreamCommon {
		public RTReadCode(final StaticScope enclosingMethodScope) {
			super("InputStream", "read", null, null, enclosingMethodScope, StaticScope.globalScope().lookupTypeDeclaration("int"));
		}
		@Override public RTCode runDetails(final RTObject myEnclosedScope, final RTObjectCommon theStream) {
			RTObject retval = null;
			final RTInputStreamInfo streamInfo = (RTInputStreamInfo)theStream.getObject("inputStreamInfo");
			final PanelInputStream theActualStream = (PanelInputStream)streamInfo.inputStream();
			try {
				final int theInput = theActualStream.read();
				retval = new RTIntegerObject(theInput);
			} catch (IOException expection) {
				retval = new RTIntegerObject(-1);
			}

			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			this.addRetvalTo(activationRecord);
			activationRecord.setObject("ret$val", retval);
			
			return super.nextCode();
		}
	}
	
	
	public static class DocInputStream extends InputStream implements GraphicsEventHandler, KeyListener {
	    public final int BACKSPACE = 010;  // DEL
	    public final int LINEKILL = 025;   // CTRL_U  21
	    public final int CR = 012;
	    public final int LF = 015;
	    
	    volatile ArrayBlockingQueue<Integer> queue;
	    volatile int available_;
	    final JTextComponent myComponent_;
	    final MessageConsole console_;
	    
	    public boolean handleEvent(final Event e) {
	    	return true;
	    }
	    
	    protected DocInputStream() {
	    	queue = new ArrayBlockingQueue<Integer>(1024);
	    	myComponent_ = null;
	    	console_ = null;
	    }
	
	    public DocInputStream(final JTextComponent associatedTextComponent, final MessageConsole console) {
	        queue = new ArrayBlockingQueue<Integer>(1024);
	        myComponent_ = associatedTextComponent;
	        console_ = console;
	        available_ = 0;
	    }
	    
	    protected void requestFocus() {
	    	 myComponent_.requestFocusInWindow();
	    }
	    
	    protected void echo(final Integer i) {
	    	System.out.print((char)i.intValue());
	    	System.out.flush();
	    }
	
	    @Override public int read() throws IOException {
	    	requestFocus();
	        Integer i = null;
	        
	        try {
	            i = queue.take();
	        } catch (final InterruptedException ex) {
	        	ErrorLogger.error(ErrorType.Runtime, "\n! ! ! Enactment interrupted.\n",
	        			"", "", "");
	        }
	        if (i != null) {
	        	if (i != BACKSPACE && i != LINEKILL) {
	        		echo(i);
	        	}
	            return i;
	        }
	        return -1;
	    }
	
	    @Override public int read(byte[] b, int off, int len) throws IOException {
	    	requestFocus();
	    	available_ = 0;
	        if (b == null) {
	            throw new NullPointerException();
	        } else if (off < 0 || len < 0 || len > b.length - off) {
	            throw new IndexOutOfBoundsException();
	        } else if (len == 0) {
	            return 0;
	        }
	        
	        int c = read();
	        int i = 0;
	        
	        // First character after read
	        switch (c) {
	        case -1:
	        	return -1;
	        case BACKSPACE:
	        	i = 0;
	        	break;
	        case LINEKILL:
	        	i = 0;
	        	break;
	        case CR:
	        case LF:
	        	b[off+0] = '\r';
	        	b[off+1] = '\n';
	        	available_ = 2;
	        	return 2;
	        default:
	        	b[off] = (byte)c;
	        	i = 1;
	        	break;
	        }
	
	        try {
	            for (; i < len;) {
	                c = read();	// blocking read
	                
	                if (c < 010 || (c >= 016 && c <= 031) || (c == 013)) {
	                	// Ignore control characters
	                	continue;
	                } else switch (c) {
	                case -1:
	                	return -1;
	                case BACKSPACE:
	                	i--;
	                	if (i >= 0) {
	                		b[off + i] = '\0';
	                		
	                		// Remove one character from the end
	                		// of the console display, buffer, etc.
	                		console_.processBackspace();
	                	} else {
	                		i = 0;
	                	}
	                	break;
	                case LINEKILL:
	                	// Tidy up the console by removing the characters
	                	// we added in.
	                	console_.processLinekill(i);
	                	i = 0;
	                	break;
	                case CR:
	                case LF:
	                	// We stuff in a \r and \n to meet Scanner requirements; without
	                	// these, we get a "No line found" message on a nextLine() call.
	                	// See Scanner.findWithinHorizon(Pattern pattern, int horizon),
	                	// which elicits the exception thrown within Scanner.nextLine
	                	if (i + 1 < len) {
	                		b[off + i++] = '\r';
	                		b[off + i++] = '\n';
	                	}
	                	return i;
	                default:
	                    b[off + i] = (byte)c;
	                    i++;
	                    break;
	                }
	            }
	        } catch (IOException ee) {
	        }
	        
	        return i;
	
	    }
	
	    @Override public int available(){
	        return available_;
	    }
	    
	    @Override public void keyReleased(KeyEvent e) {
	    }
	
	    @Override public void keyPressed(KeyEvent e) {
	    }
	
	    @Override public void keyTyped(KeyEvent e) {
	        final int c = e.getKeyChar();
	        try {
	            queue.put(c);
	        } catch (InterruptedException ex) {
	            Logger.getLogger(Console.class.getName()).
	                    log(Level.SEVERE, null, ex);
	        }
	    }
	    
	    @Override public void checkIOIntegration(final Event e) { }
	}
	
	public static class PanelInputStream extends DocInputStream implements KeyListener, GraphicsEventHandler {
	    protected PanelInputStream(final RTObject rTPanel) {
	    	super();
			
			// Can be null on a ctor call, because it hasn't yet been set up
			assert rTPanel instanceof RTNullObject || rTPanel instanceof RTObjectCommon;
			final GraphicsPanel graphicsPanel = rTPanel instanceof RTObjectCommon? (GraphicsPanel)rTPanel.getObject("panelObject"): null;
			panel_ = graphicsPanel;
			assert panel_ instanceof GraphicsPanel;
	    }
	    
	    protected void requestFocus() {
	    	 panel_.requestFocusInWindow();
	    }
	    
	    @Override public void checkIOIntegration(final Event e) {
			try {
			    queue.put(e.key);
			} catch (InterruptedException ex) {
			    Logger.getLogger(Console.class.getName()).
			            log(Level.SEVERE, null, ex);
			}
	    }
	    
	    @Override public int read(byte[] b, int off, int len) throws IOException {
	    	panel_.setGraphicsEventHandler(this);
	    	final int retval = super.read(b, off, len);
	    	panel_.setGraphicsEventHandler(null);
	    	return retval;
	    }
	    
	    @Override public int read() throws IOException {
	    	panel_.setGraphicsEventHandler(this);
	    	final int retval = super.read();
	    	panel_.setGraphicsEventHandler(null);
	    	return retval;
	    }
	    
	    @Override protected void echo(final Integer i) { }
	    
	   
	    final GraphicsPanel panel_;
	}
	
	
	public static List<TypeDeclaration> typeDeclarationList() {
		return typeDeclarationList_;
	}
	
	private static List<TypeDeclaration> typeDeclarationList_;
	private static Type inputStreamType_;
}