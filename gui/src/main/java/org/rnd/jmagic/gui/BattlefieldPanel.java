package org.rnd.jmagic.gui;

import org.rnd.jmagic.sanitized.*;

class BattlefieldPanel extends javax.swing.JPanel
{
	private class PlacementInformation
	{
		private java.awt.Point center;
		private java.awt.Rectangle border;
		private Boolean tapped;
		private boolean selected;

		public PlacementInformation(int objectID)
		{
			this.border = null;
			this.selected = false;

			// Initialize both to half the height so that the card won't be
			// outside the battlefield when it taps/untaps.
			int x = CardGraphics.SMALL_CARD.height / 2;
			int y = CardGraphics.SMALL_CARD.height / 2;

			SanitizedGameObject card = (SanitizedGameObject)BattlefieldPanel.this.gui.state.get(objectID);
			this.tapped = card.tapped;

			if(card.controllerID == BattlefieldPanel.this.gui.playerID)
				y += (BattlefieldPanel.this.battlefieldPanel.getParent().getHeight() - CardGraphics.SMALL_CARD.height);

			this.center = new java.awt.Point(x, y);
		}

		public PlacementInformation(int x, int y)
		{
			this.center = new java.awt.Point(x, y);
			this.border = null;
			this.tapped = null;
			this.selected = false;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((this.center == null) ? 0 : this.center.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null || getClass() != obj.getClass())
				return false;
			PlacementInformation other = (PlacementInformation)obj;
			if(!getOuterType().equals(other.getOuterType()))
				return false;
			if(this.center == null)
			{
				if(other.center != null)
					return false;
			}
			else if(!this.center.equals(other.center))
				return false;
			return true;
		}

		public java.awt.Point getCenter()
		{
			return this.center;
		}

		public boolean isTapped(int objectID)
		{
			if(this.tapped == null)
				this.tapped = ((SanitizedGameObject)BattlefieldPanel.this.gui.state.get(objectID)).tapped;
			return this.tapped.booleanValue();
		}

		public java.awt.Rectangle getBorder(int objectID)
		{
			if(this.border == null)
			{
				if(this.isTapped(objectID))
				{
					int x = this.getCenter().x - (CardGraphics.SMALL_CARD.height / 2);
					int y = this.getCenter().y - (CardGraphics.SMALL_CARD.width / 2);
					int width = CardGraphics.SMALL_CARD.height;
					int height = CardGraphics.SMALL_CARD.width;
					this.border = new java.awt.Rectangle(x, y, width, height);
				}
				else
				{
					int x = this.getCenter().x - (CardGraphics.SMALL_CARD.width / 2);
					int y = this.getCenter().y - (CardGraphics.SMALL_CARD.height / 2);
					int width = CardGraphics.SMALL_CARD.width;
					int height = CardGraphics.SMALL_CARD.height;
					this.border = new java.awt.Rectangle(x, y, width, height);
				}
			}
			return this.border;
		}

		public void reset()
		{
			this.tapped = null;
			this.border = null;
		}

		private BattlefieldPanel getOuterType()
		{
			return BattlefieldPanel.this;
		}
	}

	private static final long serialVersionUID = 1L;

	private static java.awt.Rectangle intersectsAny(java.awt.Rectangle single, java.util.Collection<java.awt.Rectangle> rects)
	{
		for(java.awt.Rectangle rect: rects)
			if(single.intersects(rect))
				return rect;
		return null;
	}

	// This is specifically typed because we require a map that returns object
	// in insertion order. We use insertion order to represent the z-order of
	// cards.
	private final java.util.LinkedHashMap<Integer, PlacementInformation> cardPlacements;

	private final Play gui;

	private final javax.swing.JPanel battlefieldPanel;

	private java.awt.Point initialPoint;
	private java.awt.Point boundaryPoint;

	public BattlefieldPanel(Play play)
	{
		super(new java.awt.BorderLayout());
		this.gui = play;

		this.cardPlacements = new java.util.LinkedHashMap<Integer, PlacementInformation>();

		this.initialPoint = null;
		this.boundaryPoint = null;

		this.battlefieldPanel = new javax.swing.JPanel((java.awt.LayoutManager)null)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(java.awt.Graphics g)
			{
				super.paintComponent(g);

				CardGraphics cg = new CardGraphics(g, BattlefieldPanel.this.gui.state);

				boolean rotateOppCards = Boolean.parseBoolean(BattlefieldPanel.this.gui.properties.getProperty(Play.PropertyKeys.ROTATE_OPP_CARDS));
				boolean highlightSelected = Boolean.parseBoolean(BattlefieldPanel.this.gui.properties.getProperty(Play.PropertyKeys.HIGHLIGHT_SELECTED_CARDS));
				boolean renderDamage = Boolean.parseBoolean(BattlefieldPanel.this.gui.properties.getProperty(Play.PropertyKeys.RENDER_DAMAGE));
				boolean renderCounters = Boolean.parseBoolean(BattlefieldPanel.this.gui.properties.getProperty(Play.PropertyKeys.RENDER_COUNTERS));
				java.awt.Color fill = javax.swing.UIManager.getColor("Tree.selectionBackground");
				fill = new java.awt.Color(fill.getRed(), fill.getGreen(), fill.getBlue(), 128);

				for(Integer i: BattlefieldPanel.this.cardPlacements.keySet())
				{
					PlacementInformation info = BattlefieldPanel.this.cardPlacements.get(i);

					java.awt.Rectangle position = info.getBorder(i);
					cg.pushTransform();
					SanitizedGameObject card = (SanitizedGameObject)(BattlefieldPanel.this.gui.state.get(i));
					cg.translate(position.x, position.y);

					cg.pushTransform();

					// Use these to determine which corner of `position` is the
					// top-left corner of the card.
					boolean drawFromTop = true;
					boolean drawFromLeft = true;

					if(info.isTapped(i))
						drawFromLeft = !drawFromLeft;

					if(rotateOppCards && card.controllerID != BattlefieldPanel.this.gui.playerID)
					{
						drawFromTop = !drawFromTop;
						drawFromLeft = !drawFromLeft;
					}

					// Top Left
					if(drawFromTop && drawFromLeft)
					{
						// normal, do nothing
					}
					// Top Right
					else if(drawFromTop)
					{
						cg.translate(position.width, 0);
						cg.rotate(Math.PI / 2);
					}
					// Bottom Left
					else if(drawFromLeft)
					{
						cg.translate(0, position.height);
						cg.rotate(-Math.PI / 2);
					}
					// Bottom Right
					else
					{
						cg.translate(position.width, position.height);
						cg.rotate(Math.PI);
					}

					cg.drawImage(BattlefieldPanel.this.gui.getSmallCardImage(card, renderDamage, renderCounters, this.getFont()), 0, 0, null);

					cg.popTransform();

					if(BattlefieldPanel.this.gui.divisions != null && BattlefieldPanel.this.gui.divisions.containsKey(i))
					{
						if(BattlefieldPanel.this.gui.dividingOn != i)
						{
							int division = BattlefieldPanel.this.gui.divisions.get(i);

							cg.drawDivision(division);
						}
					}

					if(highlightSelected && info.selected)
					{
						cg.setColor(fill);
						cg.fillRect(0, 0, position.width, position.height);
					}

					cg.popTransform();
				}

				if(BattlefieldPanel.this.initialPoint != null)
				{
					java.awt.Rectangle selectedRectangle = BattlefieldPanel.this.getSelectedRectangle();

					cg.setColor(fill);
					cg.fillRect(selectedRectangle.x, selectedRectangle.y, selectedRectangle.width, selectedRectangle.height);

					java.awt.Color border = javax.swing.UIManager.getColor("Tree.selectionBorderColor");
					cg.setColor(new java.awt.Color(border.getRed(), border.getGreen(), border.getBlue(), 128));
					cg.drawRect(selectedRectangle.x, selectedRectangle.y, selectedRectangle.width, selectedRectangle.height);
				}
			}
		};
		javax.swing.event.MouseInputAdapter mouseInputListener = new javax.swing.event.MouseInputAdapter()
		{
			// Specifically use a linked hash map, since iterating over it will
			// return the elements in insertion-order, which we use to preserve
			// z-ordering on the battlefield.
			private java.util.LinkedHashMap<Integer, java.awt.Point> heldCards = new java.util.LinkedHashMap<Integer, java.awt.Point>();
			private java.awt.Point mouseDown = null;

			/**
			 * Find the most-recently-added object in the battlefield which
			 * contains the current mouse position
			 */
			private int getHoveredCardID(java.awt.event.MouseEvent e)
			{
				int best = -1;
				for(Integer i: BattlefieldPanel.this.cardPlacements.keySet())
					if(BattlefieldPanel.this.cardPlacements.get(i).getBorder(i).contains(e.getPoint()))
						best = i;

				return best;
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				int hoveredCardID = getHoveredCardID(e);
				if(-1 == hoveredCardID)
					BattlefieldPanel.this.gui.battlefieldClicked(e);
				else
					BattlefieldPanel.this.gui.identifiedMouseEvent(BattlefieldPanel.this.gui.state.get(hoveredCardID), e);
				this.heldCards.clear();
			}

			@Override
			public void mouseDragged(java.awt.event.MouseEvent e)
			{
				if(this.heldCards.isEmpty())
				{
					BattlefieldPanel.this.resizeSelectionArea(e.getPoint());
				}
				else
				{
					for(Integer heldCardID: this.heldCards.keySet())
					{
						if(!BattlefieldPanel.this.cardPlacements.containsKey(heldCardID))
							continue;

						java.awt.Point offset = new java.awt.Point(this.heldCards.get(heldCardID));
						offset.translate(e.getPoint().x, e.getPoint().y);
						PlacementInformation position = new PlacementInformation(offset.x, offset.y);
						PlacementInformation oldPosition = BattlefieldPanel.this.cardPlacements.get(heldCardID);
						if(oldPosition == null)
							throw new NullPointerException("Card placement null for ID " + heldCardID);
						position.selected = oldPosition.selected;

						if(position.equals(oldPosition))
							return;

						int left = position.getBorder(heldCardID).x;
						int top = position.getBorder(heldCardID).y;
						if(left < 0)
							offset.translate(-left, 0);
						if(top < 0)
							offset.translate(0, -top);
						if(left < 0 || top < 0)
						{
							position = new PlacementInformation(offset.x, offset.y);
							position.selected = oldPosition.selected;
						}

						BattlefieldPanel.this.cardPlacements.remove(heldCardID);
						BattlefieldPanel.this.cardPlacements.put(heldCardID, position);

						java.awt.Point modify = Play.getLocationInsideWindow(BattlefieldPanel.this.battlefieldPanel);
						modify.translate(offset.x, offset.y);
						BattlefieldPanel.this.gui.cardLocations.put(heldCardID, modify);
					}

					// Repaint the whole window to get the arrows too
					BattlefieldPanel.this.gui.mainWindow.repaint();
				}
			}

			@Override
			public void mouseMoved(java.awt.event.MouseEvent e)
			{
				int hoveredCardID = getHoveredCardID(e);
				SanitizedGameObject hoveredCard = (SanitizedGameObject)BattlefieldPanel.this.gui.state.get(hoveredCardID);
				if(hoveredCard != null)
				{
					java.awt.Point cardStart = BattlefieldPanel.this.cardPlacements.get(hoveredCardID).getBorder(hoveredCardID).getLocation();

					boolean shouldFlip = Boolean.parseBoolean(BattlefieldPanel.this.gui.properties.getProperty(org.rnd.jmagic.gui.Play.PropertyKeys.ROTATE_OPP_CARDS));
					boolean flipped = shouldFlip && hoveredCard.controllerID != BattlefieldPanel.this.gui.playerID;

					SanitizedGameObject.CharacteristicSet displayOption = CardGraphics.getLargeCardDisplayOption(e, cardStart, hoveredCard, flipped);
					BattlefieldPanel.this.gui.cardInfoPanel.setFocusToGameObject(hoveredCard, BattlefieldPanel.this.gui.state, displayOption);
				}
				else
					BattlefieldPanel.this.gui.cardInfoPanel.clearArrows();
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e)
			{
				this.mouseDown = e.getPoint();

				int hoveredCardID = getHoveredCardID(e);
				if(hoveredCardID == -1)
				{
					// Only accept left clicks
					if(e.getButton() != java.awt.event.MouseEvent.BUTTON1)
						return;

					for(Integer i: this.heldCards.keySet())
						if(BattlefieldPanel.this.cardPlacements.containsKey(i))
							BattlefieldPanel.this.cardPlacements.get(i).selected = false;
					this.heldCards.clear();
					BattlefieldPanel.this.setSelectionArea(e.getPoint());
				}
				else
				{
					BattlefieldPanel.this.gui.identifiedMouseEvent(BattlefieldPanel.this.gui.state.get(hoveredCardID), e);

					// Only accept left clicks
					if(e.getButton() != java.awt.event.MouseEvent.BUTTON1)
						return;

					if(this.heldCards.containsKey(hoveredCardID))
					{
						for(Integer heldCardID: this.heldCards.keySet())
						{
							if(!BattlefieldPanel.this.cardPlacements.containsKey(heldCardID))
								continue;

							java.awt.Point offset = new java.awt.Point(BattlefieldPanel.this.cardPlacements.get(heldCardID).getCenter());
							offset.translate(-this.mouseDown.x, -this.mouseDown.y);
							this.heldCards.put(heldCardID, offset);
						}
					}
					else
					{
						for(Integer i: this.heldCards.keySet())
							if(BattlefieldPanel.this.cardPlacements.containsKey(i))
								BattlefieldPanel.this.cardPlacements.get(i).selected = false;
						this.heldCards.clear();
						java.awt.Point offset = new java.awt.Point(BattlefieldPanel.this.cardPlacements.get(hoveredCardID).getCenter());
						offset.translate(-this.mouseDown.x, -this.mouseDown.y);
						this.heldCards.put(hoveredCardID, offset);
					}
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e)
			{
				BattlefieldPanel.this.gui.cardInfoPanel.clearArrows();
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e)
			{
				int hoveredCardID = getHoveredCardID(e);
				if(-1 != hoveredCardID)
					BattlefieldPanel.this.gui.identifiedMouseEvent(BattlefieldPanel.this.gui.state.get(hoveredCardID), e);

				// Only accept left clicks
				if(e.getButton() != java.awt.event.MouseEvent.BUTTON1)
					return;

				if(this.heldCards.isEmpty())
				{
					java.awt.Rectangle selection = BattlefieldPanel.this.getSelectedRectangle();
					if(selection == null)
						return;
					for(Integer i: BattlefieldPanel.this.cardPlacements.keySet())
					{
						PlacementInformation info = BattlefieldPanel.this.cardPlacements.get(i);
						if(info.getBorder(i).intersects(selection))
						{
							info.selected = true;
							this.heldCards.put(i, null);
						}
					}
					BattlefieldPanel.this.setSelectionArea(null);
				}
				else
				{
					this.mouseDragged(e);
					this.mouseDown = null;
					BattlefieldPanel.this.resizeViewport();
				}
			}
		};
		this.battlefieldPanel.addMouseMotionListener(mouseInputListener);
		this.battlefieldPanel.addMouseListener(mouseInputListener);

		javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(this.battlefieldPanel, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getHorizontalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e)
			{
				switch(e.getAdjustmentType())
				{
				case java.awt.event.AdjustmentEvent.UNIT_INCREMENT:
				case java.awt.event.AdjustmentEvent.UNIT_DECREMENT:
				case java.awt.event.AdjustmentEvent.BLOCK_INCREMENT:
				case java.awt.event.AdjustmentEvent.TRACK:
					java.awt.Point modify = Play.getLocationInsideWindow(BattlefieldPanel.this.battlefieldPanel);
					for(java.util.Map.Entry<Integer, PlacementInformation> entry: BattlefieldPanel.this.cardPlacements.entrySet())
					{
						java.awt.Point center = new java.awt.Point(entry.getValue().getCenter());
						center.translate(modify.x, modify.y);
						BattlefieldPanel.this.gui.cardLocations.put(entry.getKey(), center);
					}
					break;
				default:
					break;
				}
			}
		});
		this.add(scroll);

		// This method should only be called from the event-handler thread, but
		// since this panel hasn't been realized yet, this should be fine.
		this.update();
	}

	public void update()
	{
		// We manipulate this list, so make a copy of it so the state isn't
		// destroyed in the process.
		java.util.List<Integer> objects = new java.util.LinkedList<Integer>(((SanitizedZone)this.gui.state.get(this.gui.state.battlefield)).objects);
		java.util.Collection<java.awt.Rectangle> occupied = new java.util.HashSet<java.awt.Rectangle>();

		java.util.Iterator<Integer> iter = this.cardPlacements.keySet().iterator();
		while(iter.hasNext())
		{
			Integer id = iter.next();
			if(!objects.contains(id))
				iter.remove();
			else
			{
				PlacementInformation info = this.cardPlacements.get(id);
				// Reset the placement information after every update in case
				// things became tapped or untapped.
				info.reset();
				occupied.add(info.getBorder(id));
			}
		}

		int attachmentTranslateY = CardGraphics.SMALL_CARD_PADDING_TOP + this.getFontMetrics(this.getFont()).getHeight();
		while(!objects.isEmpty())
		{
			int id = objects.remove(0);
			java.awt.Point center = null;

			if(this.cardPlacements.containsKey(id))
				center = this.cardPlacements.get(id).getCenter();
			else
			{
				// if it's attached to something, place it near and behind what
				// it's attached to.
				SanitizedGameObject object = (SanitizedGameObject)(this.gui.state.get(id));
				if(object.attachedTo == -1 || this.gui.state.players.contains(object.attachedTo) || //
				!((SanitizedZone)this.gui.state.get(this.gui.state.battlefield)).objects.contains(object.attachedTo))
				{
					PlacementInformation placement = new PlacementInformation(id);
					while(true)
					{
						java.awt.Rectangle intersection = intersectsAny(placement.getBorder(id), occupied);
						if(intersection == null)
							break;
						// Move horizontally by small card height to ensure that
						// automatically placed cards wont overlap when tapped.
						placement = new PlacementInformation(intersection.x + (intersection.width / 2) + CardGraphics.SMALL_CARD.height + CardGraphics.SMALL_CARD_PADDING.width, placement.getCenter().y);
					}
					this.cardPlacements.put(id, placement);
					center = placement.getCenter();
					occupied.add(placement.getBorder(id));
				}
				else
				{
					// if we haven't placed the card this is attached to yet,
					// push this off till later and keep going
					if(!this.gui.cardLocations.containsKey(object.attachedTo))
					{
						objects.add(id);
						continue;
					}
					java.awt.Point attachmentCenter = this.cardPlacements.get(object.attachedTo).getCenter();
					PlacementInformation placement = new PlacementInformation(attachmentCenter.x, attachmentCenter.y);
					placement.reset();
					placement.getCenter().translate(CardGraphics.SMALL_CARD_PADDING_LEFT, attachmentTranslateY);

					this.cardPlacements.put(id, placement);
					center = placement.getCenter();
					occupied.add(placement.getBorder(id));
				}
			}

			java.awt.Point modify = Play.getLocationInsideWindow(this.battlefieldPanel);
			modify.translate(center.x, center.y);
			this.gui.cardLocations.put(id, modify);
		}

		this.resizeViewport();

		this.repaint();
	}

	public void resizeViewport()
	{

		int maxX = 0;
		int maxY = 0;

		for(Integer id: this.cardPlacements.keySet())
		{
			PlacementInformation info = this.cardPlacements.get(id);
			java.awt.Rectangle bounds = info.getBorder(id);

			if(bounds.x + bounds.width > maxX)
				maxX = bounds.x + bounds.width;
			if(bounds.y + bounds.height > maxY)
				maxY = bounds.y + bounds.height;
		}

		this.battlefieldPanel.setPreferredSize(new java.awt.Dimension(maxX, maxY));
		this.battlefieldPanel.revalidate();
	}

	public void setSelectionArea(java.awt.Point initialPoint)
	{
		this.initialPoint = (initialPoint == null ? null : new java.awt.Point(initialPoint));
		resizeSelectionArea(initialPoint);
	}

	public void resizeSelectionArea(java.awt.Point boundaryPoint)
	{
		this.boundaryPoint = (boundaryPoint == null ? null : new java.awt.Point(boundaryPoint));
		this.repaint();
	}

	public java.awt.Rectangle getSelectedRectangle()
	{
		if(this.initialPoint == null || this.boundaryPoint == null)
			return null;

		int left = Math.min(BattlefieldPanel.this.initialPoint.x, BattlefieldPanel.this.boundaryPoint.x);
		int top = Math.min(BattlefieldPanel.this.initialPoint.y, BattlefieldPanel.this.boundaryPoint.y);
		int width = Math.max(BattlefieldPanel.this.initialPoint.x, BattlefieldPanel.this.boundaryPoint.x) - left;
		int height = Math.max(BattlefieldPanel.this.initialPoint.y, BattlefieldPanel.this.boundaryPoint.y) - top;
		return new java.awt.Rectangle(left, top, width, height);
	}
}
