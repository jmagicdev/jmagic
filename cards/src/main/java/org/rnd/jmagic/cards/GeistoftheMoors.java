package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Geist of the Moors")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class GeistoftheMoors extends Card
{
	public GeistoftheMoors(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
