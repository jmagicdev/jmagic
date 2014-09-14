package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Charging Badger")
@Types({Type.CREATURE})
@SubTypes({SubType.BADGER})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class ChargingBadger extends Card
{
	public ChargingBadger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
