package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nether Horror")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class NetherHorror extends Card
{
	public NetherHorror(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);
	}
}
