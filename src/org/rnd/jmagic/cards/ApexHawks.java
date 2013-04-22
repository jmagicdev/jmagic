package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Apex Hawks")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ApexHawks extends Card
{
	public ApexHawks(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Multikicker (1)(W) (You may pay an additional (1)(W) any number of
		// times as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(W)");
		this.addAbility(kicker);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Apex Hawks enters the battlefield with a +1/+1 counter on it for each
		// time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(state, "Apex Hawks", kicker.costCollections[0]));
	}
}
