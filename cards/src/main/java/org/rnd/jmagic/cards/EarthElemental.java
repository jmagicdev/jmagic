package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Earth Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class EarthElemental extends Card
{
	public EarthElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);
	}
}
