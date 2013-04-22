package org.rnd.jmagic.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.rnd.jmagic.gui.dialogs.*;

final class PlayOptions extends ConfigurationFrame.OptionPanel
{
	private static final class ColorButton extends JButton
	{
		private static final long serialVersionUID = 1L;

		private static final int ICON_HEIGHT = 11;
		private static final int ICON_WIDTH = 11;

		public Color color;

		public ColorButton()
		{
			super();

			this.color = null;

			this.setPreferredSize(new Dimension(ICON_WIDTH * 2, ICON_HEIGHT * 2));

			this.setAction(new AbstractAction("", new Icon()
			{
				@Override
				public int getIconHeight()
				{
					return ICON_HEIGHT;
				}

				@Override
				public int getIconWidth()
				{
					return ICON_WIDTH;
				}

				@Override
				public void paintIcon(Component c, Graphics g, int x, int y)
				{
					g.setColor(ColorButton.this.color);
					g.fillRect(x, y, 10, 10);
					g.setColor(Color.BLACK);
					g.drawRect(x, y, 10, 10);
				}
			})
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					final JColorChooser chooser = new JColorChooser(ColorButton.this.color);
					final JFrame frame = new JFrame("Choose a color...");
					Box vBox = Box.createVerticalBox();
					Box hBox = Box.createHorizontalBox();
					hBox.add(Box.createHorizontalGlue());
					hBox.add(new JButton(new AbstractAction("Cancel")
					{
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent e)
						{
							frame.dispose();
						}
					}));
					hBox.add(new JButton(new AbstractAction("OK")
					{
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent e)
						{
							ColorButton.this.color = chooser.getColor();
							ColorButton.this.repaint();
							frame.dispose();
						}
					}));
					vBox.add(chooser);
					vBox.add(hBox);
					frame.add(vBox);
					frame.pack();
					frame.setVisible(true);
				}
			});
		}
	}

	private static final long serialVersionUID = 1L;

	private JCheckBox actionPopup;
	private JCheckBox beepOnYourTurn;

	private JCheckBox highlight;
	private final String[] MISC_ZONES = new String[] {"Stack", "Exile zone", "Your library", "Your graveyard", "Opponent's library", "Opponent's graveyard"};
	private JComboBox miscL;
	private JComboBox miscR;
	private JCheckBox renderDmg;
	private JCheckBox renderCtrs;
	private JCheckBox rotate;
	private java.util.Map<Arrow.ArrowType, Box> arrows;

	PlayOptions()
	{
		super("Play");

		this.setLayout(new java.awt.BorderLayout());

		SpringLayout layout = new SpringLayout();

		this.rotate = new JCheckBox("Rotate Opponents' Cards");
		this.rotate.setToolTipText("Choosing this option orients your opponents' cards on the battlefield upside down.");
		layout.putConstraint(SpringLayout.WEST, this.rotate, 5, SpringLayout.WEST, this);

		this.actionPopup = new JCheckBox("Always Show Actions Menu");
		this.actionPopup.setToolTipText("Always show a menu describing actions you may take with a card when you click on it, even if there is only one such action.");
		layout.putConstraint(SpringLayout.NORTH, this.actionPopup, 5, SpringLayout.SOUTH, this.rotate);
		layout.putConstraint(SpringLayout.WEST, this.actionPopup, 5, SpringLayout.WEST, this);

		this.beepOnYourTurn = new JCheckBox("Beep When It's Your Turn");
		this.beepOnYourTurn.setToolTipText("Emit a sound when your turn starts.");
		layout.putConstraint(SpringLayout.NORTH, this.beepOnYourTurn, 5, SpringLayout.SOUTH, this.actionPopup);
		layout.putConstraint(SpringLayout.WEST, this.beepOnYourTurn, 5, SpringLayout.WEST, this);

		this.renderDmg = new JCheckBox("Render Damage");
		this.renderDmg.setToolTipText("Display damage marked on a card in the lower right corner of the card.");
		layout.putConstraint(SpringLayout.NORTH, this.renderDmg, 5, SpringLayout.SOUTH, this.beepOnYourTurn);
		layout.putConstraint(SpringLayout.WEST, this.renderDmg, 5, SpringLayout.WEST, this);

		this.renderCtrs = new JCheckBox("Render Counters");
		this.renderCtrs.setToolTipText("Display counters on cards on the battlefield in their art box.");
		layout.putConstraint(SpringLayout.NORTH, this.renderCtrs, 5, SpringLayout.SOUTH, this.renderDmg);
		layout.putConstraint(SpringLayout.WEST, this.renderCtrs, 5, SpringLayout.WEST, this);

		this.highlight = new JCheckBox("Highlight Selection");
		this.highlight.setToolTipText("Highlight cards selected during a multi-select on the battlefield.");
		layout.putConstraint(SpringLayout.NORTH, this.highlight, 5, SpringLayout.SOUTH, this.renderCtrs);
		layout.putConstraint(SpringLayout.WEST, this.highlight, 5, SpringLayout.WEST, this);

		this.miscL = new JComboBox(this.MISC_ZONES);
		this.miscR = new JComboBox(this.MISC_ZONES);

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(layout);

		innerPanel.add(this.rotate);
		innerPanel.add(this.actionPopup);
		innerPanel.add(this.beepOnYourTurn);
		innerPanel.add(this.renderDmg);
		innerPanel.add(this.renderCtrs);
		innerPanel.add(this.highlight);

		Box zoneBox = Box.createHorizontalBox();
		zoneBox.add(new JLabel("Default zone display"));
		zoneBox.add(Box.createHorizontalStrut(5));
		zoneBox.add(this.miscL);
		zoneBox.add(Box.createHorizontalStrut(5));
		zoneBox.add(this.miscR);
		layout.putConstraint(SpringLayout.NORTH, zoneBox, 5, SpringLayout.SOUTH, this.highlight);
		layout.putConstraint(SpringLayout.WEST, zoneBox, 5, SpringLayout.WEST, this);

		innerPanel.add(Box.createVerticalStrut(5));
		innerPanel.add(zoneBox);

		this.arrows = new java.util.HashMap<Arrow.ArrowType, Box>();

		JPanel arrowsBox = new JPanel();
		arrowsBox.setLayout(new BoxLayout(arrowsBox, BoxLayout.Y_AXIS));
		for(final Arrow.ArrowType arrow: Arrow.ArrowType.values())
		{
			Box singleArrow = Box.createHorizontalBox();

			singleArrow.add(new JCheckBox());
			singleArrow.add(new ColorButton());
			singleArrow.add(Box.createHorizontalStrut(5));
			singleArrow.add(new JLabel(arrow.description));
			singleArrow.add(Box.createHorizontalGlue());

			this.arrows.put(arrow, singleArrow);
			arrowsBox.add(singleArrow);
		}
		arrowsBox.setBorder(BorderFactory.createTitledBorder("Arrows"));

		layout.putConstraint(SpringLayout.NORTH, arrowsBox, 5, SpringLayout.SOUTH, zoneBox);
		layout.putConstraint(SpringLayout.WEST, arrowsBox, 5, SpringLayout.WEST, this);
		innerPanel.add(arrowsBox);

		this.add(innerPanel);
	}

	@Override
	public void loadSettings(java.util.Properties properties)
	{
		this.rotate.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.ROTATE_OPP_CARDS)));
		this.actionPopup.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.ACTIONS_POPUP)));
		this.beepOnYourTurn.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.BEEP_ON_YOUR_TURN)));
		this.renderDmg.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.RENDER_DAMAGE)));
		this.renderCtrs.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.RENDER_COUNTERS)));
		this.highlight.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.HIGHLIGHT_SELECTED_CARDS)));

		this.miscL.setSelectedItem(properties.getProperty(Play.PropertyKeys.MISC_ZONE_L));
		this.miscR.setSelectedItem(properties.getProperty(Play.PropertyKeys.MISC_ZONE_R));

		for(java.util.Map.Entry<Arrow.ArrowType, Box> entry: this.arrows.entrySet())
		{
			Box box = entry.getValue();
			JCheckBox checkbox = (JCheckBox)box.getComponent(0);
			ColorButton button = (ColorButton)box.getComponent(1);

			checkbox.setSelected(Boolean.parseBoolean(properties.getProperty(Play.PropertyKeys.getArrowEnabledKey(entry.getKey()))));
			button.color = Play.PropertyKeys.colorFromString(properties.getProperty(Play.PropertyKeys.getArrowColorKey(entry.getKey())));
		}
	}

	@Override
	public void saveChanges(java.util.Properties properties)
	{
		properties.setProperty(Play.PropertyKeys.ROTATE_OPP_CARDS, Boolean.toString(this.rotate.isSelected()));
		properties.setProperty(Play.PropertyKeys.ACTIONS_POPUP, Boolean.toString(this.actionPopup.isSelected()));
		properties.setProperty(Play.PropertyKeys.BEEP_ON_YOUR_TURN, Boolean.toString(this.beepOnYourTurn.isSelected()));
		properties.setProperty(Play.PropertyKeys.RENDER_DAMAGE, Boolean.toString(this.renderDmg.isSelected()));
		properties.setProperty(Play.PropertyKeys.RENDER_COUNTERS, Boolean.toString(this.renderCtrs.isSelected()));
		properties.setProperty(Play.PropertyKeys.HIGHLIGHT_SELECTED_CARDS, Boolean.toString(this.highlight.isSelected()));

		properties.setProperty(Play.PropertyKeys.MISC_ZONE_L, (String)this.miscL.getSelectedItem());
		properties.setProperty(Play.PropertyKeys.MISC_ZONE_R, (String)this.miscR.getSelectedItem());

		for(java.util.Map.Entry<Arrow.ArrowType, Box> entry: this.arrows.entrySet())
		{
			Box box = entry.getValue();
			JCheckBox checkbox = (JCheckBox)box.getComponent(0);
			ColorButton button = (ColorButton)box.getComponent(1);

			properties.setProperty(Play.PropertyKeys.getArrowEnabledKey(entry.getKey()), Boolean.toString(checkbox.isSelected()));
			properties.setProperty(Play.PropertyKeys.getArrowColorKey(entry.getKey()), Play.PropertyKeys.colorToString(button.color));
		}
	}
}