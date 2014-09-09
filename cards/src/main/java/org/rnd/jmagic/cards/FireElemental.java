package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fire Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class FireElemental extends Card
{
	public FireElemental(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
