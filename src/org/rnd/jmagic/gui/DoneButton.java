package org.rnd.jmagic.gui;

public class DoneButton extends javax.swing.JButton
{
	private static final long serialVersionUID = 1L;

	private java.util.Stack<java.awt.event.ActionListener> listeners;
	private java.util.Stack<String> texts;

	public DoneButton(String defaultText)
	{
		this.setEnabled(false);
		this.setText(defaultText);
		this.listeners = new java.util.Stack<java.awt.event.ActionListener>();
		this.texts = new java.util.Stack<String>();
	}

	public boolean hasListeners()
	{
		return !this.listeners.isEmpty();
	}

	public void popListener()
	{
		this.removeActionListener(this.listeners.pop());
		if(this.listeners.isEmpty())
			this.setEnabled(false);

		this.texts.pop();
		if(!this.texts.isEmpty())
			this.setText(this.texts.peek());
	}

	public void pushListener(java.awt.event.ActionListener actionListener, String text)
	{
		if(this.listeners.isEmpty())
			this.setEnabled(true);
		this.addActionListener(actionListener);
		this.listeners.push(actionListener);

		this.texts.push(text);
		this.setText(text);
	}
}