package info.fulloo.trygve.editor;

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


import java.io.*;
import java.util.EventListener;
import java.awt.*;
import java.awt.event.KeyListener;

import javax.swing.event.*;
import javax.swing.text.*;

/*
 *  Create a simple console to display text messages.
 *
 *  Messages can be directed here from different sources. Each source can
 *  have its messages displayed in a different color.
 *
 *  Messages can either be appended to the console or inserted as the first
 *  line of the console
 *
 *  You can limit the number of lines to hold in the Document.
 *  
 *  https://tips4java.wordpress.com/2008/11/08/message-console/
 *  http://stackoverflow.com/questions/4443878/redirecting-system-out-to-jtextpane
 *
 *
 * Following code from:
 *     http://stackoverflow.com/questions/28512381/how-can-i-turn-a-text-area-into-an-input-stream-in-java
 */
public class MessageConsole
{
	private JTextComponent textComponent;
	private Document document;
	private boolean isAppend;
	private DocumentListener limitLinesListener;
	private InputStreamClass.DocInputStream in_;
	private ConsoleOutputStream out_;
	
    public InputStreamClass.DocInputStream getIn(){
        return in_;
    }

	public MessageConsole(JTextComponent textComponent)
	{
		this(textComponent, true);
	}

	/*
	 *	Use the text component specified as a simple console to display
	 *  text messages.
	 *
	 *  The messages can either be appended to the end of the console or
	 *  inserted as the first line of the console.
	 */
	public MessageConsole(JTextComponent textComponent, boolean isAppend)
	{
		this.textComponent = textComponent;
		this.document = textComponent.getDocument();
		this.isAppend = isAppend;
		textComponent.setEditable( false );
		this.reinitialize();
	}
	
	public void reinitialize() {
		// Here we set in_ to the MessageConsole's textComponent,
		// for fetching from elsewhere. I wonder if it should be
		// instead set to the error panel? That's passed in the
		// above constructor. But that ends up here, bound anyhow
		// to the error panel.
		
		if (null != in_) {
			textComponent.removeKeyListener(in_);
			try {
				in_.close();
			} catch (IOException ioex) {
				// who cares?
			}
			in_ =  null;
		}

		in_ = new InputStreamClass.DocInputStream(textComponent, this);
		
		// We want input to come here. See also setFocusable.
		// This ends binding to the errorPanel:
		//
		//	... errorPanel = new javax.swing.JTextPane();
		
        textComponent.setFocusable(true);
        
        // Make sure there are no other listeners
        final EventListener[] els = textComponent.getListeners(EventListener.class);
        for (EventListener listener : els) {
        	if (listener instanceof KeyListener) {
        		textComponent.removeKeyListener((KeyListener)listener);
        	}
        }
        
        textComponent.addKeyListener(in_);
	}
	
	public void processBackspace() {
		final String text = textComponent.getText();
		int l = text.length() - 1;
		if (l < 0) l = 0;
		textComponent.setText(text.substring(0, l));
		textComponent.setCaretPosition( document.getLength() );
	}
	
	public void processLinekill(int i) {
		final String text = textComponent.getText();
		int l = text.length() - i;
		if (l < 0) l = 0;
		textComponent.setText(text.substring(0, l));
		textComponent.setCaretPosition( document.getLength() );
	}
	
	public void flush2() {
    	// http://stackoverflow.com/questions/629315/dynamically-refresh-jtextarea-as-processing-occurs
		// It doesn't appear to be this that gets in the way of key interrupts happening.
		textComponent.update(textComponent.getGraphics());
	}

	/*
	 *  Redirect the output from the standard output to the console
	 *  using the default text color and null PrintStream
	 */
	public void redirectOut()
	{
		redirectOut(null, null);
	}

	/*
	 *  Redirect the output from the standard output to the console
	 *  using the specified color and PrintStream. When a PrintStream
	 *  is specified the message will be added to the Document before
	 *  it is also written to the PrintStream.
	 */
	public void redirectOut(Color textColor, PrintStream printStream)
	{
		out_ = new ConsoleOutputStream(textColor, printStream);
		System.setOut( new PrintStream(out_, true) );
	}
	
	
	/*
	 *  Redirect the output from the standard error to the console
	 *  using the default text color and null PrintStream
	 */
	public void redirectErr()
	{
		redirectErr(null, null);
	}

	/*
	 *  Redirect the output from the standard error to the console
	 *  using the specified color and PrintStream. When a PrintStream
	 *  is specified the message will be added to the Document before
	 *  it is also written to the PrintStream.
	 */
	public void redirectErr(Color textColor, PrintStream printStream)
	{
		ConsoleOutputStream cos = new ConsoleOutputStream(textColor, printStream);
		System.setErr( new PrintStream(cos, true) );
	}

	/*
	 *  To prevent memory from being used up you can control the number of
	 *  lines to display in the console
	 *
	 *  This number can be dynamically changed, but the console will only
	 *  be updated the next time the Document is updated.
	 */
	public void setMessageLines(int lines)
	{
		if (limitLinesListener != null)
			document.removeDocumentListener( limitLinesListener );

		limitLinesListener = new LimitLinesDocumentListener(lines, isAppend);
		document.addDocumentListener( limitLinesListener );
	}

	/*
	 *	Class to intercept output from a PrintStream and add it to a Document.
	 *  The output can optionally be redirected to a different PrintStream.
	 *  The text displayed in the Document can be color coded to indicate
	 *  the output source.
	 */
	class ConsoleOutputStream extends ByteArrayOutputStream
	{
		private final String EOL = System.getProperty("line.separator");
		private SimpleAttributeSet attributes;
		private PrintStream printStream;
		private StringBuffer buffer = new StringBuffer(80);
		private boolean isFirstLine;

		/*
		 *  Specify the option text color and PrintStream
		 */
		public ConsoleOutputStream(Color textColor, PrintStream printStream)
		{
			if (textColor != null) {
				attributes = new SimpleAttributeSet();
				StyleConstants.setForeground(attributes, textColor);
			}

			this.printStream = printStream;

			if (isAppend) {
				isFirstLine = true;
			}
		}

		/*
		 *  Override this method to intercept the output text. Each line of text
		 *  output will actually involve invoking this method twice:
		 *
		 *  a) for the actual text message
		 *  b) for the newLine string
		 *
		 *  The message will be treated differently depending on whether the line
		 *  will be appended or inserted into the Document
		 */
		public void flush()
		{
			String message = toString();

			if (message.length() == 0) return;

			if (isAppend)
			    handleAppend(message);
			else
			    handleInsert(message);

			reset();
		}

		/*
		 *	We don't want to have blank lines in the Document. The first line
		 *  added will simply be the message. For additional lines it will be:
		 *
		 *  newLine + message
		 */
		private void handleAppend(String message)
		{
			//  This check is needed in case the text in the Document has been
			//	cleared. The buffer may contain the EOL string from the previous
			//  message.

			if (document.getLength() == 0)
				buffer.setLength(0);

			if (EOL.equals(message))
			{
				buffer.append(message);
			}
			else
			{
				buffer.append(message);
				clearBuffer();
			}

		}
		
		/*
		 *  We don't want to merge the new message with the existing message
		 *  so the line will be inserted as:
		 *
		 *  message + newLine
		 */
		private void handleInsert(String message)
		{
			buffer.append(message);

			if (EOL.equals(message))
			{
				clearBuffer();
			}
		}

		/*
		 *  The message and the newLine have been added to the buffer in the
		 *  appropriate order so we can now update the Document and send the
		 *  text to the optional PrintStream.
		 */
		private void clearBuffer()
		{
			//  In case both the standard out and standard err are being redirected
			//  we need to insert a newline character for the first line only

			if (isFirstLine && document.getLength() != 0)
			{
			    buffer.insert(0, "\n");
			}

			isFirstLine = false;
			String line = buffer.toString();

			try
			{
				if (isAppend)
				{
					int offset = document.getLength();
					document.insertString(offset, line, attributes);
					textComponent.setCaretPosition( document.getLength() );
				}
				else
				{
					document.insertString(0, line, attributes);
					textComponent.setCaretPosition( 0 );
				}
			}
			catch (BadLocationException ble) {}

			if (printStream != null)
			{
				printStream.print(line);
			}

			buffer.setLength(0);
		}
	}
}
