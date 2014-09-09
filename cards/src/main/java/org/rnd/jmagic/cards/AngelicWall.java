package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Angelic Wall")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AngelicWall extends Card
{
	public AngelicWall(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
