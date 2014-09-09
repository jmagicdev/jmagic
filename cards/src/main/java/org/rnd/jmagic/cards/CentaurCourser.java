package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Centaur Courser")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class CentaurCourser extends Card
{
	public CentaurCourser(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
