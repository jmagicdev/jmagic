package org.rnd.jmagic.gui;

import org.rnd.jmagic.sanitized.*;

class MiscZonePanel extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;
	private final Play gui;
	private SanitizedZone zone;
	public int zoneFocus;

	private javax.swing.JPanel zonePanel;
	private javax.swing.JComboBox<String> zoneChooser;

	public MiscZonePanel(Play play, String zoneName)
	{
		super(new java.awt.BorderLayout());
		this.gui = play;
		this.zone = null;

		// If the zone name isn't recognized, use any zone from the list.
		Integer focus = this.gui.zones.get(zoneName);

		// This handles "Opponent's library" by referencing the first library
		// that isn't yours
		// TODO: add correct handling for team games (non-opponent players)
		if(focus == null && zoneName.contains("Opponent"))
		{
			zoneName = zoneName.replace("Opponent", "");
			for(String key: this.gui.zones.keySet())
				if(key.endsWith(zoneName))
				{
					zoneName = key;
					focus = this.gui.zones.get(zoneName);
					break;
				}
		}

		if(focus != null)
			this.zoneFocus = focus;
		else
			this.zoneFocus = this.gui.zones.values().iterator().next();

		this.zonePanel = new javax.swing.JPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(java.awt.Graphics g)
			{
				super.paintComponent(g);

				// TODO: render damage? in the misc zone panel? this can
				// probably default to false right?
				boolean renderCounters = Boolean.parseBoolean(MiscZonePanel.this.gui.properties.getProperty(Play.PropertyKeys.RENDER_COUNTERS));

				java.util.List<Integer> cards = MiscZonePanel.this.zone.objects;
				CardGraphics cg = new CardGraphics(g, MiscZonePanel.this.gui.state);
				// Iterate through the zone backwards since the "top" of
				// the zone should be drawn last
				java.util.ListIterator<Integer> i = cards.listIterator(cards.size());
				while(i.hasPrevious())
				{
					SanitizedGameObject card = (SanitizedGameObject)(MiscZonePanel.this.gui.state.get(i.previous()));
					cg.drawImage(MiscZonePanel.this.gui.getSmallCardImage(card, false, renderCounters, this.getFont()), 0, 0, null);
					cg.translate(0, CardGraphics.SMALL_CARD_PADDING.height);
				}
			}
		};
		javax.swing.event.MouseInputAdapter mouseInputListener = new javax.swing.event.MouseInputAdapter()
		{
			/**
			 * @return The card the mouse is over; null if it's not over a card.
			 */
			private SanitizedGameObject getHoveredCard(java.awt.event.MouseEvent e)
			{
				java.util.List<Integer> cardIDs = MiscZonePanel.this.zone.objects;
				int numCards = cardIDs.size();
				if(0 == numCards)
					return null;

				if(CardGraphics.SMALL_CARD.width <= e.getX())
					return null;

				int index = numCards - 1 - e.getY() / CardGraphics.SMALL_CARD_PADDING.height;
				if(index < 0)
				{
					// Hovering over any part of the last drawn card
					if(CardGraphics.SMALL_CARD_PADDING.height * (numCards - 1) + CardGraphics.SMALL_CARD.height < e.getY())
						return null;
					index = 0;
				}
				return (SanitizedGameObject)(MiscZonePanel.this.gui.state.get(cardIDs.get(index)));
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				SanitizedGameObject card = getHoveredCard(e);
				if(null != card)
					MiscZonePanel.this.gui.identifiedMouseEvent(card, e);
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e)
			{
				MiscZonePanel.this.gui.cardInfoPanel.clearArrows();
			}

			@Override
			public void mouseMoved(java.awt.event.MouseEvent e)
			{
				SanitizedGameObject hoveredCard = getHoveredCard(e);
				if(null != hoveredCard)
				{
					int reverseIndex = MiscZonePanel.this.zone.objects.size() - MiscZonePanel.this.zone.objects.indexOf(hoveredCard.ID) - 1;
					java.awt.Point cardStart = new java.awt.Point(0, reverseIndex * CardGraphics.SMALL_CARD_PADDING.height);
					SanitizedGameObject.CharacteristicSet displayOption = CardGraphics.getLargeCardDisplayOption(e, cardStart, hoveredCard, false);
					MiscZonePanel.this.gui.cardInfoPanel.setFocusToGameObject(hoveredCard, MiscZonePanel.this.gui.state, displayOption);
				}
				else
					MiscZonePanel.this.gui.cardInfoPanel.clearArrows();
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e)
			{
				SanitizedGameObject card = getHoveredCard(e);
				if(null != card)
					MiscZonePanel.this.gui.identifiedMouseEvent(card, e);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e)
			{
				SanitizedGameObject card = getHoveredCard(e);
				if(null != card)
					MiscZonePanel.this.gui.identifiedMouseEvent(card, e);
			}
		};
		this.zonePanel.addMouseListener(mouseInputListener);
		this.zonePanel.addMouseMotionListener(mouseInputListener);

		javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(this.zonePanel, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getViewport().addChangeListener(new javax.swing.event.ChangeListener()
		{
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent e)
			{
				MiscZonePanel.this.updateCardLocations();

				// Make sure CardInfoPanel isn't caching the arrows for any of
				// the cards this hid.
				MiscZonePanel.this.gui.cardInfoPanel.forceUpdate();
			}
		});
		scroll.getVerticalScrollBar().setUnitIncrement(CardGraphics.SMALL_CARD_PADDING.height / 2);

		this.add(scroll);

		this.zoneChooser = new javax.swing.JComboBox<String>(MiscZonePanel.this.gui.zones.keySet().toArray(new String[]{}));
		this.zoneChooser.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				MiscZonePanel.this.zoneFocus = MiscZonePanel.this.gui.zones.get(MiscZonePanel.this.zoneChooser.getSelectedItem());
				MiscZonePanel.this.update();
			}
		});
		this.zoneChooser.setSelectedItem(zoneName);

		javax.swing.border.EmptyBorder emptyBorder = new javax.swing.border.EmptyBorder(0, 0, 0, 0);
		this.setBorder(emptyBorder);
		this.zonePanel.setBorder(emptyBorder);
		this.zoneChooser.setBorder(emptyBorder);
		scroll.setBorder(emptyBorder);

		this.zoneChooser.setPreferredSize(new java.awt.Dimension(CardGraphics.LARGE_CARD.width / 2, this.zoneChooser.getPreferredSize().height));
		this.add(this.zoneChooser, java.awt.BorderLayout.PAGE_START);

		this.update();
	}

	public void setSelectedItem(Object item)
	{
		this.zoneChooser.setSelectedItem(item);
	}

	public void update()
	{
		this.updateCardLocations();
		int preferredHeight = CardGraphics.SMALL_CARD.height;
		if(0 < this.zone.objects.size())
			preferredHeight += CardGraphics.SMALL_CARD_PADDING.height * (this.zone.objects.size() - 1);
		this.zonePanel.setPreferredSize(new java.awt.Dimension(CardGraphics.SMALL_CARD.width, preferredHeight));
		this.zonePanel.revalidate();
		this.repaint();
	}

	private void updateCardLocations()
	{
		int x = CardGraphics.SMALL_CARD.width / 2;
		this.zone = (SanitizedZone)(this.gui.state.get(this.zoneFocus));
		int numObjects = this.zone.objects.size();
		for(int i = 0; i < numObjects; i++)
		{
			int y = CardGraphics.SMALL_CARD_PADDING.height * i;
			int indexInZone = numObjects - 1 - i;
			if(indexInZone == 0)
				y += CardGraphics.SMALL_CARD.height / 2;
			else
				y += CardGraphics.SMALL_CARD_PADDING.height / 2;

			java.awt.Point cardLocation = new java.awt.Point(x, y);
			java.awt.Point scrollModify = Play.getLocationInsideWindow(this.zonePanel);
			cardLocation.translate(scrollModify.x, scrollModify.y);
			this.gui.cardLocations.put(this.zone.objects.get(indexInZone), cardLocation);
		}
	}
}
