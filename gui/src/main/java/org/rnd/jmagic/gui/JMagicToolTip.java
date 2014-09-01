package org.rnd.jmagic.gui;

import java.awt.*;

import javax.swing.*;

public class JMagicToolTip extends JToolTip
{
	private static final long serialVersionUID = 1L;

	private JMagicTextPane textPane;

	public JMagicToolTip()
	{
		this.textPane = new JMagicTextPane();
		this.textPane.setOpaque(true);
		this.setTipText(this.getToolTipText());
		this.add(this.textPane);

		this.textPane.setMinimumSize(new Dimension(200, 10));
		this.textPane.setMaximumSize(new Dimension(200, 800));
		this.textPane.setSize(new Dimension(200, Integer.MAX_VALUE));
		this.textPane.setPreferredSize(new Dimension(200, 5 * this.textPane.getPreferredSize().height));
	}

	@Override
	public void setTipText(String text)
	{
		this.textPane.setText(text == null ? "" : text);
		super.setTipText(text);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return this.textPane.getPreferredSize();
		// return new Dimension(200, 100);
	}
}
