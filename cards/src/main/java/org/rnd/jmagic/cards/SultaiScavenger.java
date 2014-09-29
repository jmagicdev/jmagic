package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sultai Scavenger")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.WARRIOR})
@ManaCost("5B")
@ColorIdentity({Color.BLACK})
public final class SultaiScavenger extends Card
{
	public SultaiScavenger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
