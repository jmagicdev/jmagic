package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hedron Rover")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class HedronRover extends Card
{
	public HedronRover(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Hedron Rover gets +2/+2 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForPump(state, this.getName(), +2, +2));
	}
}
