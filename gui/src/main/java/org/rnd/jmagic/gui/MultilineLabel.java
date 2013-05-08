package org.rnd.jmagic.gui;

public class MultilineLabel extends javax.swing.JComponent
{
	private static final long serialVersionUID = 1L;

	private String text;
	private int lines;
	private int maxWidth = Integer.MAX_VALUE;

	public MultilineLabel(int lines)
	{
		this.lines = lines;
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		if(text == null || (0 == text.length()))
			text = " ";

		String old = this.text;
		this.text = text;
		firePropertyChange("text", old, this.text);
		if(old == null || !old.equals(text))
		{
			revalidate();
			repaint();
		}
	}

	public int getMaxWidth()
	{
		return this.maxWidth;
	}

	public void setMaxWidth(int maxWidth)
	{
		if(maxWidth <= 0)
			throw new IllegalArgumentException();
		int old = this.maxWidth;
		this.maxWidth = maxWidth;
		firePropertyChange("maxWidth", old, this.maxWidth);
		if(old != this.maxWidth)
		{
			revalidate();
			repaint();
		}
	}

	@Override
	public java.awt.Dimension getPreferredSize()
	{
		return paintOrGetSize((java.awt.Graphics2D)this.getGraphics(), getMaxWidth(), false);
	}

	@Override
	public java.awt.Dimension getMinimumSize()
	{
		return getPreferredSize();
	}

	@Override
	protected void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);
		paintOrGetSize((java.awt.Graphics2D)g, getWidth(), true);
	}

	private java.awt.Dimension paintOrGetSize(java.awt.Graphics2D g, int width, boolean paint)
	{
		// WHY THE HELL WON'T THE BACKGROUND JUST EFFING DRAW CORRECTLY?
		// TODO : Anyone who can figure out how to get it to just draw right,
		// please change this. -RulesGuru
		if(paint)
		{
			java.awt.Color old = g.getColor();
			g.setColor(this.getBackground());
			g.fillRect(0, 0, width, this.getHeight());
			g.setColor(old);
		}

		java.awt.Insets insets = getInsets();
		width -= insets.left + insets.right + 4;
		float w = insets.left + insets.right;
		float y = insets.top;
		float height = -1;
		if(width > 0 && this.text != null && this.text.length() > 0)
		{
			java.text.AttributedString as = new java.text.AttributedString(getText());
			as.addAttribute(java.awt.font.TextAttribute.FONT, getFont());
			java.text.AttributedCharacterIterator aci = as.getIterator();
			java.awt.font.LineBreakMeasurer lbm = new java.awt.font.LineBreakMeasurer(aci, g.getFontRenderContext());
			float max = 0;
			while(lbm.getPosition() < aci.getEndIndex())
			{
				java.awt.font.TextLayout textLayout = lbm.nextLayout(width);
				if(paint)
					textLayout.draw(g, 2 + insets.left, y + textLayout.getAscent());
				y += textLayout.getDescent() + textLayout.getLeading() + textLayout.getAscent();
				max = Math.max(max, textLayout.getVisibleAdvance());

				// this line will be hit multiple times, but it's unavoidable
				// since textLayout only exists inside this loop.
				height = this.lines * (textLayout.getDescent() + textLayout.getLeading() + textLayout.getAscent());
			}
			w += max;
		}
		return new java.awt.Dimension((int)Math.ceil(w), (int)Math.ceil(height) + insets.bottom);
	}
}
