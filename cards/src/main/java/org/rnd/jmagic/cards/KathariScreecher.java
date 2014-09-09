package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kathari Screecher")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SOLDIER})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class KathariScreecher extends Card
{
	public KathariScreecher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(2)(U)"));
	}
}
