package org.rnd.jmagic.gui;

import org.rnd.jmagic.sanitized.*;

class CardInfoPanel extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;
	private static final java.awt.Rectangle TOP_LEFT_PIXEL = new java.awt.Rectangle(0, 0, 1, 1);

	private static final class ImageCacheKey
	{
		public final int ID;
		public final int characteristicIndex;
		public final SanitizedGameObject.CharacteristicSet displayType;

		public ImageCacheKey(int ID, int characteristicIndex, SanitizedGameObject.CharacteristicSet displayType)
		{
			this.ID = ID;
			this.characteristicIndex = characteristicIndex;
			this.displayType = displayType;
		}

		@Override
		public int hashCode()
		{
			return 63 * (63 + this.ID) + 31 * this.displayType.hashCode() + 31 * (31 + this.characteristicIndex);
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(!(obj instanceof ImageCacheKey))
				return false;
			ImageCacheKey other = (ImageCacheKey)obj;
			if(this.ID != other.ID)
				return false;
			if(this.characteristicIndex != other.characteristicIndex)
				return false;
			if(this.displayType != other.displayType)
				return false;
			return true;
		}
	}

	private boolean displayArrows;

	/**
	 * Which version of the focus should be rendered?
	 */
	private SanitizedGameObject.CharacteristicSet displayType;

	/**
	 * This is just a storage place for
	 * {@link #setFocus(int, SanitizedGameState, int)} to communicate to
	 * {@link #paintComponent(java.awt.Graphics)} what to render. To change the
	 * actual focus, see {@link #focusID}.
	 */
	private SanitizedIdentified focus;

	private int focusCharacteristicIndex;

	/**
	 * The ID of the focus to render. This will be used to look-up the actual
	 * focus object for {@link #paintComponent(java.awt.Graphics)} to use.
	 */
	private int focusID;

	private boolean forceUpdate;

	private final Play gui;

	private java.util.Map<ImageCacheKey, java.awt.Image> largeImageCache;

	final javax.swing.JScrollPane scroll;

	private final JMagicTextPane textbox;

	public CardInfoPanel(Play play)
	{
		super(null);
		this.displayArrows = false;
		this.displayType = SanitizedGameObject.CharacteristicSet.ACTUAL;
		this.focus = null;
		this.focusCharacteristicIndex = -1;
		this.forceUpdate = false;
		this.gui = play;
		this.largeImageCache = new java.util.HashMap<ImageCacheKey, java.awt.Image>();

		this.textbox = new JMagicTextPane()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public javax.swing.JToolTip createToolTip()
			{
				return new JMagicToolTip();
			}
		};

		this.scroll = new javax.swing.JScrollPane(this.textbox, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.scroll.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
		this.scroll.setBounds(new java.awt.Rectangle(CardGraphics.LARGE_CARD_PADDING_LEFT, CardGraphics.LARGE_CARD_TEXT_BOX_TOP, CardGraphics.LARGE_CARD_TEXT_WIDTH, CardGraphics.LARGE_CARD_TEXT_BOX_HEIGHT));
		this.scroll.setOpaque(false);
		this.scroll.getViewport().setOpaque(false);

		// GUI stuff
		this.setMinimumSize(CardGraphics.LARGE_CARD);
		this.setPreferredSize(CardGraphics.LARGE_CARD);
		this.add(this.scroll);

		javax.swing.ToolTipManager ttm = javax.swing.ToolTipManager.sharedInstance();
		ttm.setInitialDelay(200);
		ttm.setDismissDelay(10000);
	}

	private void addArrow(Arrow arrow)
	{
		for(int id: new int[] {arrow.sourceID, arrow.targetID})
		{
			if(!this.gui.arrows.containsKey(id))
				this.gui.arrows.put(id, new java.util.HashSet<Arrow>());
			this.gui.arrows.get(id).add(arrow);
		}
	}

	public void clearArrows()
	{
		this.displayArrows = false;
		this.gui.mainWindow.repaint();
	}

	@Override
	public javax.swing.JToolTip createToolTip()
	{
		return new JMagicToolTip();
	}

	public void forceUpdate()
	{
		this.forceUpdate = true;
	}

	public int getArrowFocus()
	{
		return (!this.displayArrows || this.focus == null ? -1 : this.focus.ID);
	}

	private java.awt.Image getLargeCardImage(SanitizedIdentified card, SanitizedGameObject.CharacteristicSet option, java.awt.Font font, int characteristicIndex)
	{
		if(card == null)
			return CardGraphics.getLargeCard(null, option, this.gui.state, font, characteristicIndex);

		ImageCacheKey key = new ImageCacheKey(card.ID, characteristicIndex, option);

		if(!this.largeImageCache.containsKey(key))
			this.largeImageCache.put(key, CardGraphics.getLargeCard(card, option, this.gui.state, font, characteristicIndex));

		return this.largeImageCache.get(key);
	}

	@Override
	protected void paintComponent(java.awt.Graphics g)
	{
		super.paintComponent(g);
		CardGraphics cardGraphics = new CardGraphics(g, this.gui.state);
		// If the focus is a SGO without the relevant display option, just show
		// the actual characteristics
		if((this.focus instanceof SanitizedGameObject) && !(((SanitizedGameObject)(this.focus)).characteristics[0].containsKey(this.displayType)))
			this.displayType = SanitizedGameObject.CharacteristicSet.ACTUAL;
		cardGraphics.drawImage(getLargeCardImage(this.focus, this.displayType, this.getFont(), this.focusCharacteristicIndex), 0, 0, null);
		if(this.scroll.isVisible() && this.focus == null)
			this.scroll.setVisible(false);
	}

	/**
	 * Set the focus to be shown
	 *
	 * @param focusID The ID of the {@link SanitizedIdentified} to become the
	 * new focus
	 * @param state What {@link SanitizedGameState} to use when determining
	 * which {@link SanitizedIdentified} is the focus
	 */
	private void setFocus(int focusID, SanitizedGameState state, int characteristicIndex)
	{
		this.displayArrows = true;

		boolean sameFocus;
		if(null == this.focus)
			sameFocus = (focusID == -1);
		else
			sameFocus = (focusID == this.focus.ID) && (this.focusCharacteristicIndex == characteristicIndex);

		if(!this.forceUpdate && sameFocus)
		{
			this.gui.mainWindow.repaint();
			return;
		}

		this.focusID = focusID;
		this.focus = state.get(focusID);
		this.focusCharacteristicIndex = characteristicIndex;
		if((null == this.focus) || (this.focus instanceof SanitizedPlayer))
		{
			this.scroll.setVisible(false);
		}
		else
		{
			SanitizedGameObject o = (SanitizedGameObject)this.focus;
			this.scroll.setVisible(true);

			// If the focus doesn't have characteristics for the display option,
			// show the actual characteristics instead
			if(!(o.characteristics[0].containsKey(this.displayType)))
				this.displayType = SanitizedGameObject.CharacteristicSet.ACTUAL;

			this.textbox.setText(o, state, this.displayType, characteristicIndex);
			this.gui.setHelpText(this.textbox.getHelpText());
			if(!sameFocus)
				this.textbox.scrollRectToVisible(TOP_LEFT_PIXEL);
		}

		this.gui.mainWindow.repaint();
		this.forceUpdate = false;
	}

	public void setFocusToGameObject(SanitizedGameObject card, SanitizedGameState state, SanitizedGameObject.CharacteristicSet type, int characteristicIndex)
	{
		if(type != this.displayType)
		{
			this.displayType = type;
			this.forceUpdate();
		}

		this.setFocus((card == null ? -1 : card.ID), state, characteristicIndex);

		if(null == card)
			this.textbox.setForeground(java.awt.Color.WHITE);
		else if(card.faceDown && this.displayType == SanitizedGameObject.CharacteristicSet.ACTUAL)
			this.textbox.setForeground(java.awt.Color.WHITE);
		else
			this.textbox.setForeground(java.awt.Color.BLACK);
	}

	public void setFocusToNonGameObject(int ID, SanitizedGameState state)
	{
		if(null != this.displayType)
		{
			this.displayType = SanitizedGameObject.CharacteristicSet.ACTUAL;
			this.forceUpdate();
		}
		this.setFocus(ID, state, 0);
	}

	@Override
	public void setToolTipText(String text)
	{
		super.setToolTipText(text);
		this.textbox.setToolTipText(text);
	}

	public void update()
	{
		this.gui.arrows.clear();
		this.largeImageCache.clear();

		for(SanitizedIdentified id: this.gui.state.values())
		{
			if(id instanceof SanitizedGameObject)
			{
				SanitizedGameObject card = (SanitizedGameObject)id;
				for(int i = 0; i < card.characteristics.length; i++)
				{
					SanitizedCharacteristics characteristics = card.characteristics[i].get(SanitizedGameObject.CharacteristicSet.ACTUAL);
					for(int modeIndex: characteristics.selectedModes)
						for(SanitizedTarget possibleTarget: characteristics.modes.get(modeIndex - 1).targets)
							if(characteristics.chosenTargets.containsKey(possibleTarget))
								for(SanitizedTarget chosenTarget: characteristics.chosenTargets.get(possibleTarget))
									if(chosenTarget != null && chosenTarget.targetID != -1)
										addArrow(new Arrow(card.ID, chosenTarget.targetID, Arrow.ArrowType.TARGET));
					for(int attachmentID: card.attachments)
						addArrow(new Arrow(attachmentID, card.ID, Arrow.ArrowType.ATTACHMENT));
					if(card.attachedTo != -1)
						addArrow(new Arrow(card.ID, card.attachedTo, Arrow.ArrowType.ATTACHMENT));
					if(card.attackingID != -1)
						addArrow(new Arrow(card.ID, card.attackingID, Arrow.ArrowType.ATTACKING));
					if(card.blockingIDs != null)
						for(int attacker: card.blockingIDs)
							addArrow(new Arrow(card.ID, attacker, Arrow.ArrowType.BLOCKING));
					if(card.blockedByIDs != null)
						for(int blocker: card.blockedByIDs)
							addArrow(new Arrow(blocker, card.ID, Arrow.ArrowType.BLOCKING));
					if(card.controllerID != -1)
						addArrow(new Arrow(card.controllerID, card.ID, Arrow.ArrowType.CONTROLLER));
					for(int linkID: card.linkObjects)
						addArrow(new Arrow(card.ID, linkID, Arrow.ArrowType.LINK));
					if(card.pairedWith != -1)
						addArrow(new Arrow(card.ID, card.pairedWith, Arrow.ArrowType.PAIR));
					if(card instanceof SanitizedNonStaticAbility)
					{
						SanitizedNonStaticAbility nsa = (SanitizedNonStaticAbility)card;
						addArrow(new Arrow(nsa.sourceID, card.ID, Arrow.ArrowType.SOURCE));
						if(nsa.causeID != -1)
							addArrow(new Arrow(nsa.causeID, card.ID, Arrow.ArrowType.CAUSE));
					}
				}
			}
		}

		if(null != this.focus)
		{
			this.forceUpdate();
			this.setFocus(this.focusID, this.gui.state, this.focusCharacteristicIndex);
		}
	}
}
