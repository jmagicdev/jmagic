package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sightless Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SOLDIER})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SightlessGhoul extends Card
{
	public SightlessGhoul(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sightless Ghoul can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
