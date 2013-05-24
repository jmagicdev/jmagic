package org.rnd.jmagic.gui;

import java.awt.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gui.ScrollingCardPanel.*;
import org.rnd.jmagic.sanitized.*;

class PlayerPanel extends javax.swing.JPanel
{
	private static java.awt.Dimension PREFERRED_VIEWPORT_SIZE = null;

	private final class ManaPanel extends javax.swing.JPanel implements javax.swing.Scrollable
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(java.awt.Graphics g)
		{
			super.paintComponent(g);

			CardGraphics cg = new CardGraphics(g, PlayerPanel.this.gui.state);
			int i = 0;
			for(ManaSymbol s: PlayerPanel.this.player.pool)
			{
				if(PlayerPanel.this.gui.choiceType == org.rnd.jmagic.engine.PlayerInterface.ChoiceType.MANA_PAYMENT && PlayerPanel.this.gui.choose != null && PlayerPanel.this.gui.choose.contains(i))
					cg.drawImage(CardGraphics.getImage("icons/select.png"), 0, 0, null);
				cg.drawManaSymbol(s);
				cg.translate(0, CardGraphics.LARGE_MANA_SYMBOL.height + PlayerPanel.POOL_MANA_SYMBOL_PADDING.height);
				i++;
			}
		}

		@Override
		public java.awt.Dimension getPreferredScrollableViewportSize()
		{
			if(PREFERRED_VIEWPORT_SIZE == null)
			{
				Container viewport = this.getParent();
				javax.swing.JComponent scrollPane = (javax.swing.JComponent)viewport.getParent();
				java.awt.Insets borderInsets = scrollPane.getBorder().getBorderInsets(scrollPane);
				PREFERRED_VIEWPORT_SIZE = new java.awt.Dimension(CardGraphics.LARGE_MANA_SYMBOL.width, CardGraphics.SMALL_CARD.height - borderInsets.top - borderInsets.bottom);
			}
			return PREFERRED_VIEWPORT_SIZE;
		}

		@Override
		public int getScrollableBlockIncrement(java.awt.Rectangle visibleRect, int orientation, int direction)
		{
			return CardGraphics.LARGE_MANA_SYMBOL.height * 5;
		}

		@Override
		public boolean getScrollableTracksViewportHeight()
		{
			return false;
		}

		@Override
		public boolean getScrollableTracksViewportWidth()
		{
			return true;
		}

		@Override
		public int getScrollableUnitIncrement(java.awt.Rectangle visibleRect, int orientation, int direction)
		{
			return CardGraphics.LARGE_MANA_SYMBOL.height + PlayerPanel.POOL_MANA_SYMBOL_PADDING.height;
		}
	}

	static final int CARDS_IN_HAND = 7;
	static final int MANA_SYMBOLS_IN_POOL = 5;
	static final java.awt.Dimension POOL_MANA_SYMBOL_PADDING = new java.awt.Dimension(1, 1);
	private static final long serialVersionUID = 1L;
	private final Play gui;
	final InnerCardPanel<Integer> handPanel;
	final javax.swing.JPanel manaPanel;
	final javax.swing.JPanel playerInfo;
	private SanitizedPlayer player;
	private int playerFocus;

	public PlayerPanel(final Play play, int playerID)
	{
		super(new java.awt.BorderLayout());
		this.gui = play;
		this.player = null;
		this.playerFocus = playerID;

		this.handPanel = new ScrollingCardPanel.InnerCardPanel<Integer>(play)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public java.awt.Image getImage(Integer ref)
			{
				return this.gui.getSmallCardImage(this.gui.state.get(ref), false, false, this.getFont());
			}

			@Override
			public SanitizedIdentified getIdentified(Integer ref)
			{
				return this.gui.state.get(ref);
			}
		};
		this.handPanel.setPreferredSize(new java.awt.Dimension((CardGraphics.SMALL_CARD.width + CardGraphics.SMALL_CARD_PADDING.width) * PlayerPanel.CARDS_IN_HAND - CardGraphics.SMALL_CARD_PADDING.width, CardGraphics.SMALL_CARD.height));

		this.manaPanel = new ManaPanel();
		this.manaPanel.setPreferredSize(new java.awt.Dimension(CardGraphics.LARGE_MANA_SYMBOL.width, (CardGraphics.LARGE_MANA_SYMBOL.height + CardGraphics.SMALL_CARD_PADDING.height) * PlayerPanel.MANA_SYMBOLS_IN_POOL - CardGraphics.SMALL_CARD_PADDING.height));

		this.playerInfo = new javax.swing.JPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(java.awt.Graphics g)
			{
				super.paintComponent(g);

				CardGraphics cg = new CardGraphics(g, PlayerPanel.this.gui.state);
				cg.drawImage(PlayerPanel.this.gui.getSmallCardImage(PlayerPanel.this.gui.state.get(PlayerPanel.this.playerFocus), false, false, this.getFont()), 0, 0, null);

				if(PlayerPanel.this.gui.divisions != null && PlayerPanel.this.gui.divisions.containsKey(PlayerPanel.this.playerFocus))
				{
					int division = PlayerPanel.this.gui.divisions.get(PlayerPanel.this.playerFocus);

					cg.drawDivision(division);
				}
			}
		};
		this.playerInfo.setPreferredSize(CardGraphics.SMALL_CARD);
		this.playerInfo.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				PlayerPanel.this.gui.identifiedMouseEvent(PlayerPanel.this.player, e);
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e)
			{
				PlayerPanel.this.gui.identifiedMouseEvent(PlayerPanel.this.player, e);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e)
			{
				PlayerPanel.this.gui.identifiedMouseEvent(PlayerPanel.this.player, e);
			}
		});
		this.playerInfo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(java.awt.event.MouseEvent e)
			{
				PlayerPanel.this.gui.cardInfoPanel.setFocusToNonGameObject(PlayerPanel.this.playerFocus, PlayerPanel.this.gui.state);
			}
		});

		javax.swing.JPanel manaAndInfo = new javax.swing.JPanel();
		manaAndInfo.setLayout(new javax.swing.BoxLayout(manaAndInfo, javax.swing.BoxLayout.X_AXIS));
		manaAndInfo.add(this.playerInfo);
		manaAndInfo.add(javax.swing.Box.createHorizontalStrut(5));
		manaAndInfo.add(new javax.swing.JScrollPane(this.manaPanel, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), java.awt.BorderLayout.LINE_START);

		this.add(new ScrollingCardPanel(this.handPanel));
		this.add(manaAndInfo, java.awt.BorderLayout.LINE_START);

		this.update(false);
	}

	public int getPlayer()
	{
		return this.playerFocus;
	}

	public void setPlayer(int player)
	{
		this.playerFocus = player;
	}

	public void update(boolean resize)
	{
		this.player = (SanitizedPlayer)(this.gui.state.get(this.playerFocus));

		java.awt.Point playerLocation = Play.getLocationInsideWindow(this);
		playerLocation.translate(CardGraphics.SMALL_CARD.width / 2, CardGraphics.SMALL_CARD.height / 2);
		this.gui.cardLocations.put(PlayerPanel.this.playerFocus, playerLocation);

		this.handPanel.update(((SanitizedZone)this.gui.state.get(this.player.hand)).objects, resize);

		java.util.List<Integer> displayOrder = this.handPanel.getObjects();
		for(int i = 0; i < displayOrder.size(); i++)
		{
			java.awt.Point topLeft = new java.awt.Point(i * (CardGraphics.SMALL_CARD.width + CardGraphics.SMALL_CARD_PADDING.width), 0);
			java.awt.Point modify = playerLocation;
			topLeft.translate(modify.x, modify.y);
			this.gui.cardLocations.put(displayOrder.get(i), topLeft);
		}

		if(resize)
		{
			this.manaPanel.setPreferredSize(new java.awt.Dimension(CardGraphics.LARGE_MANA_SYMBOL.width, (CardGraphics.LARGE_MANA_SYMBOL.height + PlayerPanel.POOL_MANA_SYMBOL_PADDING.height) * PlayerPanel.this.player.pool.converted()));
			this.manaPanel.revalidate();
		}

		this.repaint();
	}
}
