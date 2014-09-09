package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nimbus Swimmer")
@Types({Type.CREATURE})
@SubTypes({SubType.LEVIATHAN})
@ManaCost("XGU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class NimbusSwimmer extends Card
{
	public NimbusSwimmer(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Nimbus Swimmer enters the battlefield with X +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Nimbus Swimmer", ValueOfX.instance(This.instance()), "X +1/+1 counters on it", Counter.CounterType.PLUS_ONE_PLUS_ONE));
	}
}
