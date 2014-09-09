package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gold Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("2")
@ColorIdentity({Color.WHITE})
public final class GoldMyr extends Card
{
	public GoldMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
