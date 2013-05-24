package org.rnd.jmagic.gui;

/**
 * This is the ui painter used for the action menu that pops up when choosing
 * from several player actions for the same object.
 */
public class ContextDialogPainter extends javax.swing.plaf.basic.BasicMenuItemUI
{
	private String lastKnownText;
	private java.awt.font.TextLayout lastLayout;

	public ContextDialogPainter()
	{
		this.lastKnownText = null;
		this.lastLayout = null;
	}

	public void drawString(java.awt.Graphics g, String text, int x, int y)
	{
		if(text == null || text.length() <= 0)
			return;

		java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;

		if(text.equals(this.lastKnownText))
		{
			this.lastLayout.draw(g2d, x, y);
		}
		else
		{
			java.text.AttributedString attrText = CardGraphics.getAttributedString(text, g2d.getFontMetrics(), true);
			java.awt.font.TextLayout textLayout = new java.awt.font.TextLayout(attrText.getIterator(), g2d.getFontRenderContext());
			textLayout.draw(g2d, x, y);
			this.lastKnownText = text;
			this.lastLayout = textLayout;
		}
	}

	@Override
	protected void paintText(java.awt.Graphics g, javax.swing.JMenuItem menuItem, java.awt.Rectangle textRect, String text)
	{
		javax.swing.ButtonModel model = menuItem.getModel();
		java.awt.FontMetrics fm = g.getFontMetrics(menuItem.getFont());

		if(!model.isEnabled())
		{
			// *** paint the text disabled
			java.awt.Color disabled = javax.swing.UIManager.getColor("MenuItem.disabledForeground");
			if(disabled != null)
			{
				g.setColor(disabled);
				drawString(g, text, textRect.x, (textRect.y + fm.getAscent()));
			}
			else
			{
				g.setColor(menuItem.getBackground().brighter());
				drawString(g, text, textRect.x, (textRect.y + fm.getAscent()));
				g.setColor(menuItem.getBackground().darker());
				drawString(g, text, (textRect.x - 1), (textRect.y + fm.getAscent() - 1));
			}
		}
		else
		{
			// *** paint the text normally
			if(model.isArmed())
			{
				g.setColor(this.selectionForeground); // Uses protected
				// field.
			}
			drawString(g, text, textRect.x, (textRect.y + fm.getAscent()));
		}
	}
}