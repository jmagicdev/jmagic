package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Skitter of Lizards")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SkitterofLizards extends Card
{
	public SkitterofLizards(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Multikicker (1)(R) (You may pay an additional (1)(R) any number of
		// times as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(R)");
		this.addAbility(kicker);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Skitter of Lizards enters the battlefield with a +1/+1 counter on it
		// for each time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(state, "Skitter of Lizards", kicker.costCollections[0]));
	}
}
