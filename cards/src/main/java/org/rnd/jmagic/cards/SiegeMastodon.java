package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Siege Mastodon")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class SiegeMastodon extends Card
{
	public SiegeMastodon(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);
	}
}
