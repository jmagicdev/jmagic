package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mistcutter Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("XG")
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MistcutterHydra extends Card
{
	public MistcutterHydra(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Mistcutter Hydra can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Haste, protection from blue
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlue(state));

		// Mistcutter Hydra enters the battlefield with X +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X +1/+1 counters on it", Counter.CounterType.PLUS_ONE_PLUS_ONE));
	}
}
