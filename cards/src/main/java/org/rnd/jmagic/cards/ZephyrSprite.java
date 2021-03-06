package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zephyr Sprite")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class ZephyrSprite extends Card
{
	public ZephyrSprite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
