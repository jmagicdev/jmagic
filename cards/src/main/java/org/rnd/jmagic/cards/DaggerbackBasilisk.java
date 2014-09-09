package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Daggerback Basilisk")
@Types({Type.CREATURE})
@SubTypes({SubType.BASILISK})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class DaggerbackBasilisk extends Card
{
	public DaggerbackBasilisk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
