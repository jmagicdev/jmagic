package org.rnd.jmagic.gui.dialogs;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gui.*;

public class ColorChoosePanel extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;

	public final java.util.Map<java.awt.geom.Arc2D, org.rnd.jmagic.engine.Color> colors;
	private org.rnd.jmagic.engine.Color choice;
	public boolean choiceReady;

	public ColorChoosePanel(final Play gui, final java.util.List<org.rnd.jmagic.engine.Color> choices)
	{
		this.choiceReady = false;
		this.choice = null;
		this.colors = new java.util.HashMap<java.awt.geom.Arc2D, org.rnd.jmagic.engine.Color>();

		this.setSize(100, 100);
		this.setPreferredSize(new java.awt.Dimension(100, 100));

		java.util.EnumSet<Color> colorsSet = java.util.EnumSet.copyOf(choices);

		int wedgeDegrees = -360 / colorsSet.size();
		int initAngle = 90 - (wedgeDegrees / 2);
		int i = 0;
		for(org.rnd.jmagic.engine.Color color: colorsSet)
			this.colors.put(new java.awt.geom.Arc2D.Float(0, 0, this.getWidth(), this.getHeight(), initAngle + i++ * wedgeDegrees, wedgeDegrees, java.awt.geom.Arc2D.PIE), color);

		this.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				for(java.awt.geom.Arc2D arc: ColorChoosePanel.this.colors.keySet())
				{
					if(arc.contains(e.getPoint()))
					{
						Color color = ColorChoosePanel.this.colors.get(arc);

						int i = choices.indexOf(color);

						if(i != -1)
							gui.choose = java.util.Collections.singletonList(i);
						else
							gui.choose = java.util.Collections.emptyList();
						gui.choiceReady();
					}
				}
			}
		});
	}

	@Override
	public void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);

		java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
		for(java.awt.geom.Arc2D arc: this.colors.keySet())
		{
			java.awt.Color newColor = null;
			switch(this.colors.get(arc))
			{
			case WHITE:
				newColor = new java.awt.Color(255, 251, 213);
				break;
			case BLUE:
				newColor = new java.awt.Color(58, 183, 243);
				break;
			case BLACK:
				newColor = new java.awt.Color(46, 46, 46);
				break;
			case RED:
				newColor = new java.awt.Color(242, 77, 21);
				break;
			case GREEN:
				newColor = new java.awt.Color(68, 155, 99);
				break;
			}
			g2.setColor(newColor);
			g2.fill(arc);
		}
	}

	public org.rnd.jmagic.engine.Color getChoice()
	{
		return this.choice;
	}
}
