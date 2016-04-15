package info.fulloo.trygve.editor;

/*
 * TextEditorGUI.java
 * http://sourceforge.net/projects/javatxteditor/?source=typ_redirect
 *
 * Created on 1 wrzesień 2008, 22:00
 * 
 * Trygve IDE 2.0
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

import java.awt.Color;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
import info.fulloo.trygve.lntextpane.LNTextPane;
import info.fulloo.trygve.parser.ParseRun;
import info.fulloo.trygve.parser.ParseRun.GuiParseRun;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RTWindowRegistryEntry;
import info.fulloo.trygve.run_time.RunTimeEnvironment;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

enum RunButtonState { Idle, Running, Disabled } ;

public class TextEditorGUI extends LNTextPane { //javax.swing.JFrame {

	final boolean OLD = false;
	private final static String defaultFile = "examples/pong.k";
    
    private File fileName = new File("noname");
    
    final String TrygveVersion = "2.0.4";
    
    public InputStream getIn() {
    	return console_.getIn();
    }
    
    /** Creates new form TextEditorGUI */
    public TextEditorGUI() {
    	super();
    	worker_ = null;
    	parseRun_ = null;
    	compiledWithoutError_ = false;
    	
		initComponents();
        loadFile(defaultFile);
        
        appWindowsExtantMap_ = new HashMap<RTWindowRegistryEntry, Boolean>();
    	
        updateButtons();
        oslMsg();
    }
    
    public void oslMsg() {
    	System.out.print("Trygve IDE, Version ");
    	System.out.print(TrygveVersion);
    	System.out.println(". Copyright (c)2016 James O. Coplien, jcoplien@gmail.com.");
    	System.out.println("Trygve IDE comes with ABSOLUTELY NO WARRANTY; for details click `show w'.");
    	System.out.println("This is free software, and you are welcome to redistribute it" +
    					" under certain conditions; click `show c' for details.");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * This is no longer maintained in the form editor, but rather
     * is edited manually.
     */
    
    private javax.swing.JScrollPane errorScrollPane = null;
	private javax.swing.text.JTextComponent errorPanel = null;
	
	public String errorPanelContents() {
		return errorPanel.getText();
	}
	public String editPanelContents() {
		return editPane.getText();
	}
	
    private void initComponents() {
    	worker_ = null;
        copyButton = new javax.swing.JButton();
        jScrollPane1 = super.scrollPane(); // new javax.swing.JScrollPane(); 
        if (OLD) {
        	jScrollPane2 = new javax.swing.JScrollPane();
        } else {
        	errorPanel = new javax.swing.JTextPane();
        	errorPanel.setMargin(new java.awt.Insets(3, 20, 3, 20));
        	errorPanel.setBackground(new java.awt.Color(233, 228, 242));
        	errorScrollPane = new javax.swing.JScrollPane(errorPanel);
        	javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        	
        	/*
        	javax.swing.JFrame frame = new javax.swing.JFrame("Message Console");
            frame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
            frame.getContentPane().add( errorScrollPane );
            frame.setSize(400, 120);
            frame.setVisible(true);
            */
        	
        	// Here is the zoo:
        	//		errorPanel is a JTextPane
        	//		editPane comes from JTextPane.editPane, of type JEditorPane
        	//		errorScrollPane created for errorPane

            console_ = new MessageConsole(errorPanel);
            console_.redirectOut();
            console_.redirectErr(java.awt.Color.RED, null);
        }
        
        editPane = super.editPane(); // new javax.swing.JEditorPane();
        if (OLD) {
        	editPane2 = new javax.swing.JEditorPane();
        } else {
        	;
        }
        
        cutButton = new javax.swing.JButton();
        pasteButton = new javax.swing.JButton();
        selectAllButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        clearButton2 = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        interruptButton = new javax.swing.JButton();
        parseButton = new javax.swing.JButton();
        wwwButton = new javax.swing.JButton();
        urlTextField = new javax.swing.JTextField();
        openFileButton = new javax.swing.JButton();
        fileSystemTextField = new javax.swing.JTextField();
        saveFileButton = new javax.swing.JButton();
        testButton = new javax.swing.JButton();
        showWButton = new javax.swing.JButton();
        showCButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        saveAsMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        copyMenu = new javax.swing.JMenuItem();
        cutMenu = new javax.swing.JMenuItem();
        pasteMenu = new javax.swing.JMenuItem();
        selectAllMenu = new javax.swing.JMenuItem();
        clearMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        exampleTextMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("trygve");
        setName("trygve"); // NOI18N

        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBar(null);
        if (OLD) {
        	jScrollPane2.setHorizontalScrollBar(null);
        }

        editPane.setBackground(new java.awt.Color(233, 228, 242));
        // editPane.setMargin(new java.awt.Insets(3, 20, 3, 20));
        editPane.setMargin(new java.awt.Insets(3, 3, 3, 20));
        if (OLD) {
        	editPane2.setBackground(new java.awt.Color(233, 228, 242));
        	editPane2.setMargin(new java.awt.Insets(3, 20, 3, 20));
        }
        jScrollPane1.setViewportView(editPane);
        if (OLD) {
        	jScrollPane2.setViewportView(editPane2);
        }

        cutButton.setText("Cut");
        cutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutButtonActionPerformed(evt);
            }
        });

        pasteButton.setText("Paste");
        pasteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteButtonActionPerformed(evt);
            }
        });

        selectAllButton.setText("Select All");
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear Text");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        
        clearButton2.setText("Clear Log");
        clearButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearLogButtonActionPerformed(evt);
            }
        });
        
        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        interruptButton.setText("Interrupt");
        interruptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interruptButtonActionPerformed(evt);
            }
        });
        interruptButton.setEnabled(false);
        
        parseButton.setText("Parse");
        parseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseButtonActionPerformed(evt);
            }
        });

        wwwButton.setText("WWW");
        wwwButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wwwButtonActionPerformed(evt);
            }
        });
        
        openFileButton.setText("Open File ➜");
        openFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileButtonActionPerformed(evt);
            }
        });
        
        saveFileButton.setText("⬅︎ Save File");
        saveFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileButtonActionPerformed(evt);
            }
        });
        
        testButton.setText("Run Tests");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed();
            }
        });
        
        showCButton.setText("show c");
        showCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCButtonActionPerformed();
            }
        });
        
        showWButton.setText("show w");
        showWButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showWButtonActionPerformed();
            }
        });

        urlTextField.setText("http://fulloo.info/Examples/TrygveExamples/complex1.k");
        urlTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlTextFieldActionPerformed(evt);
            }
        });
        
        fileSystemTextField.setText(defaultFile);
        saveFileButton.setEnabled(true);
        fileSystemTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSystemTextFieldActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        loadMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        loadMenu.setText("Open");
        loadMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuActionPerformed(evt);
            }
        });
        jMenu1.add(loadMenu);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenu.setText("Save");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenu);

        saveAsMenu.setText("Save as...");
        saveAsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuActionPerformed(evt);
            }
        });
        jMenu1.add(saveAsMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        copyMenu.setText("Copy");
        copyMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuActionPerformed(evt);
            }
        });
        jMenu2.add(copyMenu);

        cutMenu.setText("Cut");
        cutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuActionPerformed(evt);
            }
        });
        jMenu2.add(cutMenu);

        pasteMenu.setText("Paste");
        pasteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuActionPerformed(evt);
            }
        });
        jMenu2.add(pasteMenu);

        selectAllMenu.setText("Select All");
        selectAllMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllMenuActionPerformed(evt);
            }
        });
        jMenu2.add(selectAllMenu);

        clearMenu.setText("Clear");
        clearMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearMenuActionPerformed(evt);
            }
        });
        jMenu2.add(clearMenu);
        jMenu2.add(jSeparator1);

        exampleTextMenu.setText("Example Text");
        exampleTextMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exampleTextMenuActionPerformed(evt);
            }
        });
        jMenu2.add(exampleTextMenu);

        jMenuBar1.add(jMenu2);
        
        this.initLoadTestMenu();
        this.initExampleMenu();

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(wwwButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(urlTextField))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        	.addComponent(openFileButton)
                        	.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        	.addComponent(fileSystemTextField)
                       		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       		.addComponent(saveFileButton))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(copyButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cutButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pasteButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(selectAllButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(clearButton)
                            .addComponent(parseButton))))
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                		.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                				.addComponent(showWButton)
                				.addComponent(showCButton))
                		.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                				.addComponent(clearButton2)
                				.addComponent(runButton)
                				.addComponent(interruptButton)
                				.addComponent(testButton))
                		.addComponent(errorScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copyButton)
                    .addComponent(cutButton)
                    .addComponent(pasteButton)
                    .addComponent(selectAllButton)
                    .addComponent(clearButton)
                    .addComponent(parseButton)
                    .addComponent(showWButton)
                    .addComponent(showCButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wwwButton)
                    .addComponent(urlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                	.addComponent(openFileButton)
                	.addComponent(fileSystemTextField)
                	.addComponent(saveFileButton)
                	.addComponent(clearButton2)
                	.addComponent(runButton)
                	.addComponent(interruptButton)
                	.addComponent(testButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                	.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                	.addComponent(errorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initLoadTestMenu() {
    	final int numberOfTestCases = TestRunner.numberOfTestCases();
    	
        jMenu3 = new javax.swing.JMenu();
        jMenu3Files = new javax.swing.JMenuItem [numberOfTestCases];
        for (int i = 0; i < numberOfTestCases; i++) {
        	jMenu3Files[i] = new javax.swing.JMenuItem();
        }
        
    	jMenu3.setText("Load Test");
    	
    	final String [] allFileNames = new String [numberOfTestCases], allURLs = new String [numberOfTestCases];
    	for (int i = 0; i < numberOfTestCases; i++) {
    		allFileNames[i] = TestRunner.fileNameForTestCase(i);
    		allURLs[i] = TestRunner.urlForTestCase(i);
    	}
    	
    	Arrays.sort(allFileNames);
    	Arrays.sort(allURLs);
    	
    	
    	final char [] menuBreaks = { 'f', 'n', 's', 'z' }; int j = 0;
		JMenu submenu = new JMenu("a-" + (char)(menuBreaks[0] - 1));
    	for (int i = 0; i < numberOfTestCases; i++) {
    		assert 'a' > 'A';
    		final String fileName = allFileNames[i];
    		char firstChar = fileName.charAt(0);
    		if (firstChar > 'z') {
    			firstChar += 'a' - 'A';
    		}
    		if (firstChar >= menuBreaks[j]) {
    			jMenu3.add(submenu);
    			if (j < menuBreaks.length && menuBreaks[j+1] == 'z') {
    				final String newRange = menuBreaks[j] + "-" + 'z';
	    			submenu = new JMenu(newRange);
    			} else if (menuBreaks[j] != 'z') {
	    			final String newRange = menuBreaks[j] + "-" + (char)(menuBreaks[j+1]-1);
	    			submenu = new JMenu(newRange);
    			}
    			j++;
    		}
        	jMenu3Files[i].setText(allURLs[i]);
        	jMenu3Files[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                     loadTestCaseMenuActionPerformed(evt);
                }
            });
        	
            submenu.add(jMenu3Files[i]);
    	}
    	jMenu3.add(submenu);
    	
        jMenuBar1.add(jMenu3);
    }
    private void initExampleMenu() {
    	final int numberOfExampleCases = TestRunner.numberOfExampleCases();
    	
        jMenu4 = new javax.swing.JMenu();
        jMenu4Files = new javax.swing.JMenuItem [numberOfExampleCases];
        for (int i = 0; i < numberOfExampleCases; i++) {
        	jMenu4Files[i] = new javax.swing.JMenuItem();
        }
        
        jMenu4.setText("Load Example");
    	
    	final String [] allFileNames = new String [numberOfExampleCases], allURLs = new String [numberOfExampleCases];
    	for (int i = 0; i < numberOfExampleCases; i++) {
    		allFileNames[i] = TestRunner.fileNameForExample(i);
    		allURLs[i] = TestRunner.urlForExample(i);
    	}
    	
    	Arrays.sort(allFileNames);
    	Arrays.sort(allURLs);
    	
    	
    	final char [] menuBreaks = { 'n', 'z' }; int j = 0;
		JMenu submenu = new JMenu("a-" + (char)(menuBreaks[0] - 1));
    	for (int i = 0; i < numberOfExampleCases; i++) {
    		assert 'a' > 'A';
    		final String fileName = allFileNames[i];
    		char firstChar = fileName.charAt(0);
    		if (firstChar > 'z') {
    			firstChar += 'a' - 'A';
    		}
    		if (firstChar >= menuBreaks[j]) {
    			jMenu4.add(submenu);
    			if (j < menuBreaks.length && menuBreaks[j+1] == 'z') {
    				final String newRange = menuBreaks[j] + "-" + 'z';
	    			submenu = new JMenu(newRange);
    			} else if (menuBreaks[j] != 'z') {
	    			final String newRange = menuBreaks[j] + "-" + (char)(menuBreaks[j+1]-1);
	    			submenu = new JMenu(newRange);
    			}
    			j++;
    		}
        	jMenu4Files[i].setText(allURLs[i]);
        	jMenu4Files[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                     loadExampleMenuActionPerformed(evt);
                }
            });
        	
            submenu.add(jMenu4Files[i]);
    	}
    	jMenu4.add(submenu);
    	
        jMenuBar1.add(jMenu4);
    }

private void copyButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
    this.editPane.copy();
}//GEN-LAST:event_copyButtonActionPerformed

private void cutButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutButtonActionPerformed
    this.editPane.cut();
}//GEN-LAST:event_cutButtonActionPerformed

private void pasteButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteButtonActionPerformed
    this.editPane.paste();
}//GEN-LAST:event_pasteButtonActionPerformed

private void selectAllButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
    this.editPane.selectAll();
}//GEN-LAST:event_selectAllButtonActionPerformed

private void copyMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuActionPerformed
    this.editPane.copy();
}//GEN-LAST:event_copyMenuActionPerformed

private void cutMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuActionPerformed
    this.editPane.cut();
}//GEN-LAST:event_cutMenuActionPerformed

private void pasteMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuActionPerformed
    this.editPane.paste();
}//GEN-LAST:event_pasteMenuActionPerformed

private void selectAllMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllMenuActionPerformed
    this.editPane.selectAll();
}//GEN-LAST:event_selectAllMenuActionPerformed

private void exampleTextMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exampleTextMenuActionPerformed
	this.editPane.setText("// Press the Parse button, then press the Run button\n\nSystem.out.println(\"Hello, World!!!\")");
}//GEN-LAST:event_exampleTextMenuActionPerformed

private void saveMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
    try {
    	final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName), "UTF-8"));
        writer.write(this.editPane.getText());
        writer.close();
    }
    catch (IOException ioe) {
        this.editPane.setText("Pardon. Can't write file. Please contact: jcoplien@gmail.com");
    }
}//GEN-LAST:event_saveMenuActionPerformed

private void loadTestCaseMenuActionPerformed(final java.awt.event.ActionEvent evt) {
	runButton.setEnabled(false);
	parseButton.setEnabled(true);
	interruptButton.setEnabled(true);
	urlTextField.setText(evt.getActionCommand());
	this.wwwButtonActionPerformed(evt);
}
private void loadExampleMenuActionPerformed(final java.awt.event.ActionEvent evt) {
	runButton.setEnabled(false);
	parseButton.setEnabled(true);
	interruptButton.setEnabled(true);
	urlTextField.setText(evt.getActionCommand());
	this.wwwButtonActionPerformed(evt);
}

private void loadMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuActionPerformed
    final JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

    	final StringBuilder stringBuilder = new StringBuilder();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileChooser.getSelectedFile()), "UTF-8"));
            while (reader.ready()) {
                stringBuilder.append(reader.readLine() + "\n");
            }
            reader.close();
            this.editPane.setText(stringBuilder.toString());
            this.fileName = fileChooser.getSelectedFile();
            fileSystemTextField.setText(this.fileName.getAbsolutePath());
            saveFileButton.setEnabled(true);
        }
        catch (IOException ioe) {
            this.editPane.setText("Pardon. Can't open file. Please contact with: pkrawczak@gmail.com");
        }
    }
}//GEN-LAST:event_loadMenuActionPerformed

private void clearButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
    this.editPane.setText("");
}//GEN-LAST:event_clearButtonActionPerformed

private void clearLogButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearLogButtonActionPerformed
    this.errorPanel.setText("");
}//GEN-LAST:event_clearLogButtonActionPerformed

public void simpleRun() {
	console_.reinitialize();
	final RTExpression rTMainExpr = parseRun_.mainExpr();
	virtualMachine_.reboot();
    virtualMachine_.run(rTMainExpr);
}

private void setRunButtonState(final RunButtonState state) {
	if (userWindowsAreOpen()) {
		runButton.setEnabled(true);
    	runButton.setForeground(Color.RED);
	} else {
		switch (state) {
		case Idle:
			runButton.setEnabled(true);
	    	runButton.setForeground(Color.BLACK);
			break;
		case Running:
			runButton.setEnabled(true);
	    	runButton.setForeground(Color.RED);
			break;
		case Disabled:
			runButton.setEnabled(false);
	    	runButton.setForeground(Color.BLACK);
			break;
		}
	}
}

private void setInterruptButtonState(final RunButtonState state) {
	if (userWindowsAreOpen()) {
		interruptButton.setEnabled(true);
	} else {
		switch (state) {
		case Idle:
			interruptButton.setEnabled(false);
			break;
		case Running:
			interruptButton.setEnabled(true);
			break;
		case Disabled:
			interruptButton.setEnabled(false);
			break;
		}
	}
}

public void runButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
	if (null != worker_) {
		worker_.cancel(true);
		worker_ = null;
	}
	
	worker_ = new SwingWorker<Integer, Void>() {
	    @Override public Integer doInBackground() {
	    	setRunButtonState(RunButtonState.Running);
	    	setInterruptButtonState(RunButtonState.Running);
	    	parseButton.setEnabled(false);
	    	testButton.setEnabled(false);
	    	simpleRun();
	    	parseButton.setEnabled(true);
	    	testButton.setEnabled(true);
	    	setRunButtonState(RunButtonState.Idle);
	    	setInterruptButtonState(RunButtonState.Idle);
	    	parseButton.setEnabled(true);
	        return Integer.valueOf(JOptionPane.PLAIN_MESSAGE);
	    }

	    @Override public void done() {
	       // Just wrap up.
	       setRunButtonState(RunButtonState.Idle);
	       parseButton.setEnabled(true);
	       setInterruptButtonState(RunButtonState.Idle);
	       worker_ = null;
	    }
	};

	worker_.execute();
}//GEN-LAST:event_runButtonActionPerformed

public void interruptButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
	killAppWindows();
	parseButton.setEnabled(true);
	testButton.setEnabled(true);
	setRunButtonState(RunButtonState.Idle);
	setInterruptButtonState(RunButtonState.Idle);
	if (null != worker_) {
		try {
			worker_.cancel(true);
		} catch (final Exception e) {
			ErrorLogger.error(ErrorIncidenceType.Runtime,
					"Program enactment interrupted by user.", "", "", "");
		} finally {
			ErrorLogger.error(ErrorIncidenceType.Runtime,
					"Program enactment interrupted by user.", "", "", "");
		}
	}
	worker_ = null;
}//GEN-LAST:event_interruptButtonActionPerformed

public void parseButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parseButtonActionPerformed
	final String program = editPane.getText();
    parseRun_  = new GuiParseRun(program, this);
    assert parseRun_ != null;
	virtualMachine_ = parseRun_.virtualMachine();
	compiledWithoutError_ = ErrorLogger.numberOfFatalErrors() == 0;
	updateButtons();
}//GEN-LAST:event_parseButtonActionPerformed

private void clearMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMenuActionPerformed
    this.editPane.setText("");
}//GEN-LAST:event_clearMenuActionPerformed

public void wwwButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wwwButtonActionPerformed
    URLGet urlTest = new URLGet();
    
    final String url = urlTextField.getText();
    lastFileLoaded_ = url.toString();
    
    this.editPane.setText(urlTest.getSite(url));
    this.fileSystemTextField.setText("");
    saveFileButton.setEnabled(false);
}//GEN-LAST:event_wwwButtonActionPerformed

public void openFileButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wwwButtonActionPerformed
	final String pathName = fileSystemTextField.getText();

    final StringBuilder stringBuilder = new StringBuilder();
    try {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathName), "UTF-8"));
        while (reader.ready()) {
            stringBuilder.append(reader.readLine() + "\n");
        }
        reader.close();
        this.editPane.setText(stringBuilder.toString());
        this.fileName = new File(pathName);
        saveFileButton.setEnabled(true);
        parseButton.setEnabled(true);
    }
    catch (IOException ioe) {
        this.editPane.setText("Pardon. Can't open file. Cope needs to check his code");
    }

    this.fileName = new File(pathName);
    
    saveFileButton.setEnabled(true);
}//GEN-LAST:event_openFileButtonActionPerformed

public boolean compiledWithoutError() {
	return compiledWithoutError_;
}
public void resetCompiledWithoutError() {
	compiledWithoutError_ = false;
}

private void saveFileButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wwwButtonActionPerformed
	final String pathName = fileSystemTextField.getText();
	this.fileName = new File(pathName);
	
    try {
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName), "UTF-8"));
        writer.write(this.editPane.getText());
        writer.close();
    }
    catch (IOException ioe) {
        this.editPane.setText("Pardon. Can't write file. Ask Cope to have a look at the code");
    }
}//GEN-LAST:event_saveFileButtonActionPerformed

private void testButtonActionPerformed() {//GEN-FIRST:event_wwwButtonActionPerformed
	this.errorPanel.setText("");
	final TestRunner testRunner = new TestRunner(this);
	interruptButton.setEnabled(true);
	testRunner.runTests();	
	interruptButton.setEnabled(false);
}//GEN-LAST:event_saveFileButtonActionPerformed

private void showWButtonActionPerformed() {//GEN-FIRST:event_showWButtonActionPerformed
	System.out.println("\n\n                                            NO WARRANTY\n\n" + 
			"11. BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.\n\n" +
			"12. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY AND/OR REDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.\n\n");
}//GEN-LAST:event_showWFileButtonActionPerformed

private void showCButtonActionPerformed() {//GEN-FIRST:event_showCButtonActionPerformed
	System.out.println("\n\nTERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION\n\n" +
			"0. This License applies to any program or other work which contains a notice placed by the copyright holder saying it may be distributed under the terms of this General Public License. The \"Program\", below, refers to any such program or work, and a \"work based on the Program\" means either the Program or any derivative work under copyright law: that is to say, a work containing the Program or a portion of it, either verbatim or with modifications and/or translated into another language. (Hereinafter, translation is included without limitation in the term \"modification\".) Each licensee is addressed as \"you\".\n\n" +
			"Activities other than copying, distribution and modification are not covered by this License; they are outside its scope. The act of running the Program is not restricted, and the output from the Program is covered only if its contents constitute a work based on the Program (independent of having been made by running the Program). Whether that is true depends on what the Program does.\n\n" +
			"1. You may copy and distribute verbatim copies of the Program's source code as you receive it, in any medium, provided that you conspicuously and appropriately publish on each copy an appropriate copyright notice and disclaimer of warranty; keep intact all the notices that refer to this License and to the absence of any warranty; and give any other recipients of the Program a copy of this License along with the Program.\n\n" +
			"You may charge a fee for the physical act of transferring a copy, and you may at your option offer warranty protection in exchange for a fee.\n\n" +
			"2. You may modify your copy or copies of the Program or any portion of it, thus forming a work based on the Program, and copy and distribute such modifications or work under the terms of Section 1 above, provided that you also meet all of these conditions:\n\n" +
			"a) You must cause the modified files to carry prominent notices stating that you changed the files and the date of any change.\n\n" +
			"b) You must cause any work that you distribute or publish, that in whole or in part contains or is derived from the Program or any part thereof, to be licensed as a whole at no charge to all third parties under the terms of this License.\n\n" +
			"c) If the modified program normally reads commands interactively when run, you must cause it, when started running for such interactive use in the most ordinary way, to print or display an announcement including an appropriate copyright notice and a notice that there is no warranty (or else, saying that you provide a warranty) and that users may redistribute the program under these conditions, and telling the user how to view a copy of this License. (Exception: if the Program itself is interactive but does not normally print such an announcement, your work based on the Program is not required to print an announcement.)\n\n" +
			"These requirements apply to the modified work as a whole. If identifiable sections of that work are not derived from the Program, and can be reasonably considered independent and separate works in themselves, then this License, and its terms, do not apply to those sections when you distribute them as separate works. But when you distribute the same sections as part of a whole which is a work based on the Program, the distribution of the whole must be on the terms of this License, whose permissions for other licensees extend to the entire whole, and thus to each and every part regardless of who wrote it.\n\n" +
			"Thus, it is not the intent of this section to claim rights or contest your rights to work written entirely by you; rather, the intent is to exercise the right to control the distribution of derivative or collective works based on the Program.\n\n" +
			"In addition, mere aggregation of another work not based on the Program with the Program (or with a work based on the Program) on a volume of a storage or distribution medium does not bring the other work under the scope of this License.\n\n" +
			"3. You may copy and distribute the Program (or a work based on it, under Section 2) in object code or executable form under the terms of Sections 1 and 2 above provided that you also do one of the following:\n\n" +
			"a) Accompany it with the complete corresponding machine-readable source code, which must be distributed under the terms of Sections 1 and 2 above on a medium customarily used for software interchange; or,\n\n" +
			"b) Accompany it with a written offer, valid for at least three years, to give any third party, for a charge no more than your cost of physically performing source distribution, a complete machine-readable copy of the corresponding source code, to be distributed under the terms of Sections 1 and 2 above on a medium customarily used for software interchange; or,\n\n" +
			"c) Accompany it with the information you received as to the offer to distribute corresponding source code. (This alternative is allowed only for noncommercial distribution and only if you received the program in object code or executable form with such an offer, in accord with Subsection b above.)\n\n" +
			"The source code for a work means the preferred form of the work for making modifications to it. For an executable work, complete source code means all the source code for all modules it contains, plus any associated interface definition files, plus the scripts used to control compilation and installation of the executable. However, as a special exception, the source code distributed need not include anything that is normally distributed (in either source or binary form) with the major components (compiler, kernel, and so on) of the operating system on which the executable runs, unless that component itself accompanies the executable.\n\n" +
			"If distribution of executable or object code is made by offering access to copy from a designated place, then offering equivalent access to copy the source code from the same place counts as distribution of the source code, even though third parties are not compelled to copy the source along with the object code.\n\n" +
			"4. You may not copy, modify, sublicense, or distribute the Program except as expressly provided under this License. Any attempt otherwise to copy, modify, sublicense or distribute the Program is void, and will automatically terminate your rights under this License. However, parties who have received copies, or rights, from you under this License will not have their licenses terminated so long as such parties remain in full compliance.\n\n" +
			"5. You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program (or any work based on the Program), you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it.\n\n" +
			"6. Each time you redistribute the Program (or any work based on the Program), the recipient automatically receives a license from the original licensor to copy, distribute or modify the Program subject to these terms and conditions. You may not impose any further restrictions on the recipients' exercise of the rights granted herein. You are not responsible for enforcing compliance by third parties to this License.\n\n" +
			"7. If, as a consequence of a court judgment or allegation of patent infringement or for any other reason (not limited to patent issues), conditions are imposed on you (whether by court order, agreement or otherwise) that contradict the conditions of this License, they do not excuse you from the conditions of this License. If you cannot distribute so as to satisfy simultaneously your obligations under this License and any other pertinent obligations, then as a consequence you may not distribute the Program at all. For example, if a patent license would not permit royalty-free redistribution of the Program by all those who receive copies directly or indirectly through you, then the only way you could satisfy both it and this License would be to refrain entirely from distribution of the Program.\n\n" +
			"If any portion of this section is held invalid or unenforceable under any particular circumstance, the balance of the section is intended to apply and the section as a whole is intended to apply in other circumstances.\n\n" +
			"It is not the purpose of this section to induce you to infringe any patents or other property right claims or to contest validity of any such claims; this section has the sole purpose of protecting the integrity of the free software distribution system, which is implemented by public license practices. Many people have made generous contributions to the wide range of software distributed through that system in reliance on consistent application of that system; it is up to the author/donor to decide if he or she is willing to distribute software through any other system and a licensee cannot impose that choice.\n\n" +
			"This section is intended to make thoroughly clear what is believed to be a consequence of the rest of this License.\n\n" +
			"8. If the distribution and/or use of the Program is restricted in certain countries either by patents or by copyrighted interfaces, the original copyright holder who places the Program under this License may add an explicit geographical distribution limitation excluding those countries, so that distribution is permitted only in or among countries not thus excluded. In such case, this License incorporates the limitation as if written in the body of this License.\n\n" +
			"9. The Free Software Foundation may publish revised and/or new versions of the General Public License from time to time. Such new versions will be similar in spirit to the present version, but may differ in detail to address new problems or concerns.\n\n" +
			"Each version is given a distinguishing version number. If the Program specifies a version number of this License which applies to it and \"any later version\", you have the option of following the terms and conditions either of that version or of any later version published by the Free Software Foundation. If the Program does not specify a version number of this License, you may choose any version ever published by the Free Software Foundation.\n\n" +
			"10. If you wish to incorporate parts of the Program into other free programs whose distribution conditions are different, write to the author to ask for permission. For software which is copyrighted by the Free Software Foundation, write to the Free Software Foundation; we sometimes make exceptions for this. Our decision will be guided by the two goals of preserving the free status of all derivatives of our free software and of promoting the sharing and reuse of software generally.\n\n");
}//GEN-LAST:event_showCFileButtonActionPerformed

public String getFileNameField() {
	return fileSystemTextField.getText();
}
public void setFileNameField(String fileName) {
	fileSystemTextField.setText(fileName);
	if (fileName.length() > 0) {
		saveFileButton.setEnabled(true);
	} else {
		saveFileButton.setEnabled(false);
	}
}
public void setWWWFileNameField(String fileName) {
	urlTextField.setText(fileName);
}
public MessageConsole console() {
	return console_;
}

private void urlTextFieldActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlTextFieldActionPerformed

	final URLGet urlTest = new URLGet();
    
	final String url = urlTextField.getText();
    
    this.editPane.setText(urlTest.getSite(url));
}//GEN-LAST:event_urlTextFieldActionPerformed

private void loadFile(final String pathName) {
	final StringBuilder stringBuilder = new StringBuilder();
    try {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathName), "UTF-8"));
        while (reader.ready()) {
            stringBuilder.append(reader.readLine() + System.getProperty("line.separator"));
        }
        reader.close();
        this.editPane.setText(stringBuilder.toString());
        this.fileName = new File(pathName);
        lastFileLoaded_ = this.fileName.getName();
    }
    catch (IOException ioe) {
        this.editPane.setText("Pardon. Can't open file. Cope needs to check his code");
    }
}

private void fileSystemTextFieldActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlTextFieldActionPerformed
	final String pathName = fileSystemTextField.getText();
	loadFile(pathName);
}//GEN-LAST:event_fileSystemTextFieldActionPerformed

private void saveAsMenuActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuActionPerformed
	final JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        try {
        	final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName), "UTF-8"));
            writer.write(this.editPane.getText());
            writer.close();
        }
        catch (IOException ioe) {
            this.editPane.setText("Pardon. Can't write file. Please contact with: pkrawczak@gmail.com");
        }
    }
}//GEN-LAST:event_saveAsMenuActionPerformed

private void updateButtons() {
	if (compiledWithoutError_) {
		setRunButtonState(RunButtonState.Idle);
	} else {
		setRunButtonState(RunButtonState.Disabled);
	}
}

//------------- Application window management ----------------------

private void resetButtonsBasedOnWindowQueue() {
	if (appWindowsExtantMap_.size() > 0) {
		setRunButtonState(RunButtonState.Running);
		setInterruptButtonState(RunButtonState.Running);
		parseButton.setEnabled(false);
	} else {
		setRunButtonState(RunButtonState.Idle);
		setInterruptButtonState(RunButtonState.Idle);
		parseButton.setEnabled(true);
	}
	this.update(getGraphics());
}

public void windowCreate(final RTWindowRegistryEntry window) {
	appWindowsExtantMap_.put(window, true);
	assert appWindowsExtantMap_.size() > 0;
	resetButtonsBasedOnWindowQueue();
}

public void windowCloseDown(final RTWindowRegistryEntry window) {
	appWindowsExtantMap_.remove(window);
	resetButtonsBasedOnWindowQueue();
}

public boolean userWindowsAreOpen() {
	return appWindowsExtantMap_.size() > 0;
}

private void killAppWindows() {
	final HashMap<RTWindowRegistryEntry, Boolean> appMap = new HashMap<RTWindowRegistryEntry, Boolean>();
	appMap.putAll(appWindowsExtantMap_);
	for (final RTWindowRegistryEntry aWindow : appMap.keySet()) {
		aWindow.shutDown();
	}
}

// ------------- Private data ----------------------

	private Map<RTWindowRegistryEntry, Boolean> appWindowsExtantMap_;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearButton2;
    private javax.swing.JButton runButton;
    private javax.swing.JButton interruptButton;
    private javax.swing.JButton parseButton;
    private javax.swing.JMenuItem clearMenu;
    private javax.swing.JMenuItem jMenu3Files[];
    private javax.swing.JMenuItem jMenu4Files[];
    private javax.swing.JButton copyButton;
    private javax.swing.JMenuItem copyMenu;
    private javax.swing.JButton cutButton;
    private javax.swing.JMenuItem cutMenu;
    private javax.swing.JEditorPane editPane;
    private javax.swing.JEditorPane editPane2;
    private javax.swing.JMenuItem exampleTextMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem loadMenu;
    private javax.swing.JButton pasteButton;
    private javax.swing.JMenuItem pasteMenu;
    private javax.swing.JMenuItem saveAsMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JButton selectAllButton;
    private javax.swing.JMenuItem selectAllMenu;
    private javax.swing.JTextField urlTextField;
    private javax.swing.JTextField fileSystemTextField;
    private javax.swing.JButton wwwButton;
    private javax.swing.JButton openFileButton;
    private javax.swing.JButton saveFileButton;
    private javax.swing.JButton testButton;
    private javax.swing.JButton showWButton;
    private javax.swing.JButton showCButton;
    
    // End of variables declaration//GEN-END:variables
    
    private ParseRun parseRun_;
    private SwingWorker<Integer, Void> worker_;
    private RunTimeEnvironment virtualMachine_;
    private boolean compiledWithoutError_;
    
    @SuppressWarnings("unused")
    private String lastFileLoaded_;
    
    MessageConsole console_;

    static final long serialVersionUID = 991540;
}