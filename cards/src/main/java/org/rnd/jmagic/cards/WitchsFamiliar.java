package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Witch's Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.FROG})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class WitchsFamiliar extends Card
{
	public WitchsFamiliar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
