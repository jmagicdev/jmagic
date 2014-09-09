package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vengeful Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class VengefulVampire extends Card
{
	public VengefulVampire(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
