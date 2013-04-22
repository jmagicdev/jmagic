package org.rnd.jmagic.engine;

/**
 * Represents a 2/2 creature with no text, no name, no subtypes, no expansion
 * symbol, and no mana cost, for use by the following rules:
 * 
 * 707.2a If a face-up permanent is turned face down by a spell or ability, it
 * becomes a 2/2 face-down creature with no text, no name, no subtypes, no
 * expansion symbol, and no mana cost. These values are the copiable values of
 * that object's characteristics.
 * 
 * 702.34b To cast a card using its morph ability, turn it face down. It becomes
 * a 2/2 face-down creature card, with no text, no name, no subtypes, no
 * expansion symbol, and no mana cost. ...
 */
public class FaceDownCard extends Characteristics
{
	public FaceDownCard()
	{
		super();
		this.power = 2;
		this.toughness = 2;
		this.types = java.util.EnumSet.of(Type.CREATURE);
	}
}
