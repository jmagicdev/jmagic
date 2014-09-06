package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public class SanitizedGameObject extends SanitizedIdentified
{
	public static enum CharacteristicSet
	{
		ACTUAL, BACK_FACE, FLIP, PHYSICAL;
	}

	private static final long serialVersionUID = 10L;

	public final java.util.Map<CharacteristicSet, SanitizedCharacteristics>[] characteristics;

	public final int controllerID;
	public final int ownerID;
	public final int zoneID;

	public final java.util.List<Counter> counters;
	public final int damage;

	public final int attackingID;
	public final java.util.List<Integer> blockedByIDs;
	public final java.util.List<Integer> blockingIDs;
	public final java.util.Set<Integer> defendingIDs;

	// TODO: consider replacing all booleans with a bit field
	public final boolean flipped, faceDown, tapped, ghost, transformed, hasACopyEffect;

	public final java.util.Set<Color> canProduce;
	public final int valueOfX;

	public final java.util.Collection<Integer> attachments;
	public final int attachedTo;

	public final java.util.Collection<Integer> linkObjects;
	public final java.util.Collection<Object> otherLinks;

	public final boolean isCard;
	public final boolean isEmblem;

	public final int pairedWith;

	@SuppressWarnings("unchecked")
	public SanitizedGameObject(GameObject go, Player whoFor)
	{
		super(go);

		this.controllerID = go.controllerID;
		this.ownerID = go.ownerID;
		this.zoneID = go.zoneID;

		Characteristics[] objectCharacteristics = go.getCharacteristics();
		this.characteristics = new java.util.HashMap[objectCharacteristics.length];
		for(int i = 0; i < this.characteristics.length; ++i)
		{
			this.characteristics[i] = new java.util.HashMap<CharacteristicSet, SanitizedCharacteristics>();
			this.characteristics[i].put(CharacteristicSet.ACTUAL, objectCharacteristics[i].sanitize(go.state, whoFor));
		}

		this.counters = go.counters;
		this.damage = go.getDamage();

		this.attackingID = go.getAttackingID();
		this.blockedByIDs = go.getBlockedByIDs();
		this.blockingIDs = go.getBlockingIDs();
		this.defendingIDs = go.getDefendingIDs();

		this.flipped = go.isFlipped();
		this.faceDown = (null != go.faceDownValues);
		this.tapped = go.isTapped();
		this.ghost = go.isGhost();
		this.transformed = go.isTransformed();
		this.hasACopyEffect = go.hasACopyEffect;

		java.util.Set<Color> canProduce = java.util.EnumSet.noneOf(Color.class);
		for(ManaSymbol.ManaType manaType: CouldBeProducedBy.objectCouldProduce(go.game, go, new java.util.HashSet<SetGenerator>()))
		{
			if(manaType == ManaSymbol.ManaType.COLORLESS)
				continue;
			canProduce.add(manaType.getColor());
		}
		this.canProduce = canProduce;

		this.valueOfX = go.getValueOfX();

		this.attachments = go.getAttachments();
		this.attachedTo = go.getAttachedTo();

		java.util.Collection<Integer> linkObjects = new java.util.LinkedList<Integer>();
		java.util.Collection<Object> otherLinks = new java.util.LinkedList<Object>();
		for(NonStaticAbility a: go.getNonStaticAbilities())
		{
			Set linkInformation = a.getLinkManager().getLinkInformation(a.state);
			if(linkInformation != null)
			{
				java.util.Set<Identified> objects = linkInformation.getAll(Identified.class);
				for(Identified link: objects)
					linkObjects.add(link.ID);
				linkInformation.removeAll(objects);
				otherLinks.addAll(linkInformation);
			}
		}
		for(StaticAbility a: go.getStaticAbilities())
		{
			Set linkInformation = a.getLinkManager().getLinkInformation(a.state);
			if(linkInformation != null)
			{
				java.util.Set<Identified> objects = linkInformation.getAll(Identified.class);
				for(Identified link: objects)
					linkObjects.add(link.ID);
				linkInformation.removeAll(objects);
				otherLinks.addAll(linkInformation);
			}
		}
		this.linkObjects = linkObjects;
		this.otherLinks = otherLinks;

		this.isCard = go.isCard();
		// TODO : instanceof HATE
		this.isEmblem = go instanceof Emblem;

		GameObject pairedWith = go.getPairedWith(go.state);
		if(null == pairedWith)
			this.pairedWith = -1;
		else
			this.pairedWith = pairedWith.ID;

		// alternate characteristics
		if(!go.isCard())
			return;

		boolean actuallyVisible = go.isVisibleTo(whoFor);
		boolean storePhysical = false;
		if(this.transformed)
			storePhysical = true;
		else if(go.getBackFace() != null)
			this.characteristics[0].put(CharacteristicSet.BACK_FACE, go.getBackFace().sanitize(go.state, whoFor));

		boolean faceDown = go.faceDownValues != null;
		boolean physicallyVisible = go.getPhysical().isVisibleTo(whoFor);
		if((faceDown && physicallyVisible) || (!faceDown && actuallyVisible))
		{
			if(!this.flipped && go.getBottomHalf() != null)
				this.characteristics[0].put(CharacteristicSet.FLIP, go.getBottomHalf().sanitize(go.state, whoFor));
			if(go.hasACopyEffect || this.flipped)
				storePhysical = true;
		}

		if(storePhysical || (faceDown && physicallyVisible))
			this.characteristics[0].put(CharacteristicSet.PHYSICAL, go.getPhysical().getCharacteristics()[0].sanitize(go.game.physicalState, whoFor));
	}

	public java.util.Collection<org.rnd.jmagic.sanitized.SanitizedIdentified> sanitizeAbilities(GameState state, Player whoFor)
	{
		java.util.Collection<org.rnd.jmagic.sanitized.SanitizedIdentified> ret = new java.util.LinkedList<org.rnd.jmagic.sanitized.SanitizedIdentified>();
		for(java.util.Map<CharacteristicSet, SanitizedCharacteristics> characteristicsMap: this.characteristics)
			for(SanitizedCharacteristics characteristics: characteristicsMap.values())
				for(int ID: characteristics.abilities)
				{
					if(ID == -1)
						continue;

					GameState sanitizeIn = state;
					if(!state.containsIdentified(ID))
						// this can happen if we are looking at a back face or
						// bottom half of a card that is front-face-up or
						// unflipped.
						// Sanitizing such an object causes its
						// back-face/bottom-half abilities to be generated in
						// the
						// physical state.
						sanitizeIn = state.game.physicalState;
					Identified ability = sanitizeIn.get(ID);
					java.io.Serializable sanitized = ((Sanitizable)ability).sanitize(sanitizeIn, whoFor);
					ret.add((org.rnd.jmagic.sanitized.SanitizedIdentified)sanitized);
				}
		return ret;
	}
}
