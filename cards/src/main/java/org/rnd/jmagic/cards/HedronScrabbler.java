package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hedron Scrabbler")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("2")
@ColorIdentity({})
public final class HedronScrabbler extends Card
{
	public HedronScrabbler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Hedron Scrabbler gets +1/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForPump(state, this.getName(), +1, +1));
	}
}
