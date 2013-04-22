package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gnarlid Pack")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GnarlidPack extends Card
{
	public GnarlidPack(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Multikicker (1)(G) (You may pay an additional (1)(G) any number of
		// times as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(G)");
		this.addAbility(kicker);

		// Gnarlid Pack enters the battlefield with a +1/+1 counter on it for
		// each time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(state, "Gnarlid Pack", kicker.costCollections[0]));
	}
}
