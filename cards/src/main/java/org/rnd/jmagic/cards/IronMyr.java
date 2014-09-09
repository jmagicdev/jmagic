package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Iron Myr")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("2")
@ColorIdentity({Color.RED})
public final class IronMyr extends Card
{
	public IronMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForR(state));
	}
}
