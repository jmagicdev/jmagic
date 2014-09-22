package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Denial")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class WallofDenial extends Card
{
	public WallofDenial(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(8);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
	}
}
