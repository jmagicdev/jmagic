package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Golden Hind")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class GoldenHind extends Card
{
	public GoldenHind(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
