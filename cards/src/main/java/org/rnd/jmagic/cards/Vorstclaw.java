package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vorstclaw")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.ELEMENTAL})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class Vorstclaw extends Card
{
	public Vorstclaw(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);
	}
}
