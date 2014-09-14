package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Great Hart")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class GreatHart extends Card
{
	public GreatHart(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);
	}
}
