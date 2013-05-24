package org.rnd.jmagic.gui.dialogs;

import org.rnd.jmagic.gui.*;
import org.rnd.jmagic.sanitized.*;

public class ObjectChoosePanel extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;

	public static final int ZONE_PADDING = 15;

	private java.awt.Dimension maxSize;

	public ObjectChoosePanel(Play gui, java.util.Collection<Integer> choices)
	{
		this.maxSize = null;

		this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

		// This order is somewhat arbitrary, and I'm not passionate about any
		// particular ordering. However I do believe there should be *some*
		// consistent order here, just so that the player isn't disoriented by
		// having different zones be in different orders between resolving
		// multiple Cranial Extractions. (for example) -RulesGuru
		java.util.List<Integer> zones = new java.util.LinkedList<Integer>();
		java.util.Map<Integer, String> zoneNames = new java.util.HashMap<Integer, String>();
		zones.add(gui.state.battlefield);
		zoneNames.put(gui.state.battlefield, "Battlefield");
		zones.add(gui.state.stack);
		zoneNames.put(gui.state.stack, "Stack");
		zones.add(gui.state.exileZone);
		zoneNames.put(gui.state.exileZone, "Exile zone");
		zones.add(gui.state.commandZone);
		zoneNames.put(gui.state.commandZone, "Command zone");

		SanitizedPlayer you = (SanitizedPlayer)gui.state.get(gui.playerID);
		zones.add(you.library);
		zoneNames.put(you.library, "Your library");
		zones.add(you.graveyard);
		zoneNames.put(you.graveyard, "Your graveyard");
		zones.add(you.hand);
		zoneNames.put(you.hand, "Your hand");
		zones.add(you.sideboard);
		zoneNames.put(you.sideboard, "Your sideboard");
		for(int playerID: gui.state.players)
		{
			if(playerID == gui.playerID)
				continue;

			SanitizedPlayer player = (SanitizedPlayer)gui.state.get(playerID);
			zones.add(player.library);
			zoneNames.put(player.library, player.name + "'s library");
			zones.add(player.graveyard);
			zoneNames.put(player.graveyard, player.name + "'s graveyard");
			zones.add(player.hand);
			zoneNames.put(player.hand, player.name + "'s hand");
			zones.add(player.sideboard);
			zoneNames.put(player.sideboard, player.name + "'s sideboard");
		}

		zones.add(-1);
		zoneNames.put(-1, "Other");

		// keys are zone IDs, values are lists of choices in that zone
		java.util.Map<Integer, java.util.List<Integer>> objects = new java.util.HashMap<Integer, java.util.List<Integer>>();
		for(int choice: choices)
		{
			SanitizedGameObject object = (SanitizedGameObject)gui.state.get(choice);
			int zoneID = (zones.contains(object.zoneID) ? object.zoneID : -1);
			if(!objects.containsKey(zoneID))
				objects.put(zoneID, new java.util.LinkedList<Integer>());
			objects.get(zoneID).add(choice);
		}

		int maxPanelWidth = gui.mainWindow.getWidth() - 100;
		boolean firstZone = true;
		for(int zoneID: zones)
		{
			if(!objects.containsKey(zoneID))
				continue;

			if(!firstZone)
				this.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, ZONE_PADDING)));

			String zoneName = zoneNames.get(zoneID);
			javax.swing.JLabel zoneLabel = new javax.swing.JLabel(zoneName);
			zoneLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.add(zoneLabel);

			java.util.List<Integer> theseObjects = objects.remove(zoneID);

			ScrollingCardPanel.InnerCardPanel<Integer> thisZone = new ScrollingCardPanel.InnerCardPanel<Integer>(gui)
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
			ScrollingCardPanel scrollingPanel = new ScrollingCardPanel(thisZone);
			scrollingPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, maxPanelWidth));
			thisZone.update(theseObjects, true);
			scrollingPanel.setAlignmentX(CENTER_ALIGNMENT);
			this.add(scrollingPanel);

			firstZone = false;
		}

		this.setMaximumSize(new java.awt.Dimension(maxPanelWidth, this.getMaximumSize().height));
	}

	@Override
	public java.awt.Dimension getPreferredSize()
	{
		if(this.maxSize == null)
			return super.getPreferredSize();
		java.awt.Dimension prefer = super.getPreferredSize();
		java.awt.Dimension max = this.maxSize;
		return new java.awt.Dimension(Math.min(prefer.width, max.width), Math.min(prefer.height, max.height));
	}

	@Override
	public void setMaximumSize(java.awt.Dimension size)
	{
		super.setMaximumSize(size);
		this.maxSize = size;
	}
}
