package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Wood")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class WallofWood extends Card
{
	public WallofWood(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
	}
}
