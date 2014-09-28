package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rotting Mastodon")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.ELEPHANT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class RottingMastodon extends Card
{
	public RottingMastodon(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(8);
	}
}
