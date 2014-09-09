package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Air")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class WallofAir extends Card
{
	public WallofAir(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
