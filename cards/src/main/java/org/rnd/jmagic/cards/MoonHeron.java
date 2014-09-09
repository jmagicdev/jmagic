package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Moon Heron")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.BIRD})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class MoonHeron extends Card
{
	public MoonHeron(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
