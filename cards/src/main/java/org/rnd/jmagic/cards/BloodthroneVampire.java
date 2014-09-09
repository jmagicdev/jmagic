package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodthrone Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class BloodthroneVampire extends Card
{
	public BloodthroneVampire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice a creature: Bloodthrone Vampire gets +2/+2 until end of
		// turn.
		this.addAbility(new org.rnd.jmagic.abilities.Cannibalize(state, "Bloodthrone Vampire"));
	}
}
