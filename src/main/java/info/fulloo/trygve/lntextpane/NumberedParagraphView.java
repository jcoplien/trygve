package info.fulloo.trygve.lntextpane;

/*
 * Trygve IDE 4.3
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

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.View;

class NumberedParagraphView extends ParagraphView {
    // public static short NUMBERS_WIDTH=25;
    public static short NUMBERS_WIDTH=30;

    public NumberedParagraphView(Element e) {
        super(e);
        short top = 0;
        short left = 0;
        short bottom = 0;
        short right = 0;
        this.setInsets(top, left, bottom, right);
    }

    protected void setInsets(short top, short left, short bottom,
                             short right) {super.setInsets
                             (top,(short)(left+NUMBERS_WIDTH),
                             bottom,right);
    }

    public void paintChild(Graphics g, Rectangle r, int n) {
    	boolean line0 = false;		// kludge
    	if (r.x == 3) {				// kludge
    		line0 = true;			// kludge
    		r.x = 33;				// kludge
    	}							// kludge
        super.paintChild(g, r, n);
        if (line0) r.x = 3;			// kludge
        int previousLineCount = getPreviousLineCount();
        String formattedNumber;
        final int numberX = r.x - getLeftInset();
        final int numberY = r.y + r.height - 3;	// was 5
        if (n > 0) {
        	// This seems to set n > 0 if there is wrapping. Don't put out
        	// a bogus line number
        	formattedNumber = "     ";
        } else {
        	formattedNumber = Integer.toString(previousLineCount + n + 1);
	        while (formattedNumber.length() < 3) {
	        	formattedNumber = " " + formattedNumber;
	        }
        }
        g.drawString(formattedNumber, numberX, numberY);
    }

    public int getPreviousLineCount() {
        int lineCount = 0;
        View parent = this.getParent();
        int count = parent.getViewCount();
        for (int i = 0; i < count; i++) {
            if (parent.getView(i) == this) {
                break;
            }
            else {
            	// Count source number lines rather than display lines...
                lineCount += 1; // Old (display lines): parent.getView(i).getViewCount();
            }
        }
        return lineCount;
    }
}
