package info.fulloo.trygve.editor;

/*
 * Trygve IDE 4.0
 *   Copyright (c)2023 James O. Coplien, jcoplien@gmail.com
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

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/*
 *  A class to control the maximum number of lines to be stored in a Document
 *
 *  Excess lines can be removed from the start or end of the Document
 *  depending on your requirement.
 *
 *  a) if you append text to the Document, then you would want to remove lines
 *     from the start.
 *  b) if you insert text at the beginning of the Document, then you would
 *     want to remove lines from the end.
 */
public class LimitLinesDocumentListener implements DocumentListener
{
	private int maximumLines;
	private boolean isRemoveFromStart;

	/*
	 *  Specify the number of lines to be stored in the Document.
	 *  Extra lines will be removed from the start of the Document.
	 */
	public LimitLinesDocumentListener(int maximumLines)
	{
		this(maximumLines, true);
	}

	/*
	 *  Specify the number of lines to be stored in the Document.
	 *  Extra lines will be removed from the start or end of the Document,
	 *  depending on the boolean value specified.
	 */
	public LimitLinesDocumentListener(int maximumLines, boolean isRemoveFromStart)
	{
		setLimitLines(maximumLines);
		this.isRemoveFromStart = isRemoveFromStart;
	}

	/*
	 *  Return the maximum number of lines to be stored in the Document
	 */
	public int getLimitLines()
	{
		return maximumLines;
	}

	/*
	 *  Set the maximum number of lines to be stored in the Document
	 */
	public void setLimitLines(int maximumLines)
	{
		if (maximumLines < 1)
		{
			String message = "Maximum lines must be greater than 0";
			throw new IllegalArgumentException(message);
		}

		this.maximumLines = maximumLines;
	}

	//  Handle insertion of new text into the Document

	public void insertUpdate(final DocumentEvent e)
	{
		//  Changes to the Document can not be done within the listener
		//  so we need to add the processing to the end of the EDT

		SwingUtilities.invokeLater( new Runnable()
		{
			public void run()
			{
				removeLines(e);
			}
		});
	}

	public void removeUpdate(DocumentEvent e) {}
	public void changedUpdate(DocumentEvent e) {}

	/*
	 *  Remove lines from the Document when necessary
	 */
	private void removeLines(DocumentEvent e)
	{
		//  The root Element of the Document will tell us the total number
		//  of line in the Document.

		Document document = e.getDocument();
		Element root = document.getDefaultRootElement();

		while (root.getElementCount() > maximumLines)
		{
			if (isRemoveFromStart)
			{
				removeFromStart(document, root);
			}
			else
			{
				removeFromEnd(document, root);
			}
		}
	}

	/*
	 *  Remove lines from the start of the Document
	 */
	private void removeFromStart(Document document, Element root)
	{
		Element line = root.getElement(0);
		int end = line.getEndOffset();

		try
		{
			document.remove(0, end);
		}
		catch(BadLocationException ble)
		{
			System.out.println(ble);
		}
	}

	/*
	 *  Remove lines from the end of the Document
	 */
	private void removeFromEnd(Document document, Element root)
	{
		//  We use start minus 1 to make sure we remove the newline
		//  character of the previous line

		Element line = root.getElement(root.getElementCount() - 1);
		int start = line.getStartOffset();
		int end = line.getEndOffset();

		try
		{
			document.remove(start - 1, end - start);
		}
		catch(BadLocationException ble)
		{
			System.out.println(ble);
		}
	}
}