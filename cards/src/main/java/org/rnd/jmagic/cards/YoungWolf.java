package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Young Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class YoungWolf extends Card
{
	public YoungWolf(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
