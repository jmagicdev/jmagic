package org.rnd.jmagic.gui;

import javax.swing.*;

public class LogPanel extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextPane mainPane;

	public LogPanel()
	{
		this.setLayout(new java.awt.BorderLayout());

		this.mainPane = new JTextPane();
		this.mainPane.setPreferredSize(new java.awt.Dimension(200, 0));
		this.mainPane.setEditable(false);

		this.add(new JScrollPane(this.mainPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), java.awt.BorderLayout.CENTER);
	}

	/**
	 * Adds a line to the log.
	 * 
	 * @param line The line of text to add to the log. Do not include a
	 * terminating newline.
	 */
	public void addLine(String line)
	{
		try
		{
			this.mainPane.getDocument().insertString(this.mainPane.getDocument().getLength(), "\n\n" + line, null);
		}
		catch(javax.swing.text.BadLocationException e)
		{
			throw new RuntimeException("Attempt to insert " + line + " threw BadLocationException.");
		}
	}
}
