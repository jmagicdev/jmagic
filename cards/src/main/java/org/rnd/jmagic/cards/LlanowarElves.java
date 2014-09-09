package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Llanowar Elves")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ELF})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class LlanowarElves extends Card
{
	public LlanowarElves(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
