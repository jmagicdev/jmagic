package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nema Siltlurker")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class NemaSiltlurker extends Card
{
	public NemaSiltlurker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);
	}
}
