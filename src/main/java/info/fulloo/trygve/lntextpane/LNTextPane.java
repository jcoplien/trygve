package info.fulloo.trygve.lntextpane;

/*
 * Trygve IDE
 *   Copyright (c)2015 James O. Coplien
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

// http://www.developer.com/java/other/article.php/3318421/Add-Line-Numbering-in-the-JEditorPane.htm

import javax.swing.*;

public class LNTextPane extends JFrame {
    public LNTextPane() {
        edit_ = new JEditorPane();
        edit_.setEditorKit(new NumberedEditorKit());

        scroll_ = new JScrollPane(edit_);
        getContentPane().add(scroll_);
        setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public JScrollPane scrollPane()
    {
    	return scroll_;
    }
    public JEditorPane editPane()
    {
    	return edit_;
    }
    private JScrollPane scroll_;
    private JEditorPane edit_;
    
    static final long serialVersionUID = 991540;
}