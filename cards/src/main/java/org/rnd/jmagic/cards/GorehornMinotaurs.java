package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gorehorn Minotaurs")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.WARRIOR})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class GorehornMinotaurs extends Card
{
	public GorehornMinotaurs(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Bloodthirst 2 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 2));
	}
}
