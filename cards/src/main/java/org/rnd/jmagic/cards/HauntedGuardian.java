package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Haunted Guardian")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class HauntedGuardian extends Card
{
	public HauntedGuardian(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Defender, first strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
