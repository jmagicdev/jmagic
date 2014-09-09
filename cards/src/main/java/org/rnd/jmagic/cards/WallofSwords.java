package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Swords")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class WallofSwords extends Card
{
	public WallofSwords(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
