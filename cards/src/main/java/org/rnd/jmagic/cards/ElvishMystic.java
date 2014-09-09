package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Elvish Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class ElvishMystic extends Card
{
	public ElvishMystic(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
