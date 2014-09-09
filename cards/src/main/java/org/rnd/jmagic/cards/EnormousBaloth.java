package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Enormous Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("6G")
@ColorIdentity({Color.GREEN})
public final class EnormousBaloth extends Card
{
	public EnormousBaloth(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);
	}
}
