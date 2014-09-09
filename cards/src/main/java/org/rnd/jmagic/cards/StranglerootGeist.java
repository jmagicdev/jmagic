package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Strangleroot Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("GG")
@ColorIdentity({Color.GREEN})
public final class StranglerootGeist extends Card
{
	public StranglerootGeist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
