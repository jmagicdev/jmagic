package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Yoked Plowbeast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class YokedPlowbeast extends Card
{
	public YokedPlowbeast(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Cycling (2) ((2), Discard this card: Draw a card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)"));
	}
}
