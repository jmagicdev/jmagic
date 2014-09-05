package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Enclave Elite")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.MERFOLK})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class EnclaveElite extends Card
{
	public EnclaveElite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Multikicker (1)(U) (You may pay an additional (1)(U) any number of
		// times as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(U)");
		this.addAbility(kicker);

		// Islandwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// Enclave Elite enters the battlefield with a +1/+1 counter on it for
		// each time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(state, "Enclave Elite", kicker.costCollections[0]));
	}
}
