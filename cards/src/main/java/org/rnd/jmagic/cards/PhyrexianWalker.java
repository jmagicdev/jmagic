package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phyrexian Walker")
@Types({Type.ARTIFACT, Type.CREATURE})
@ManaCost("0")
@ColorIdentity({})
public final class PhyrexianWalker extends Card
{
	public PhyrexianWalker(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);
	}
}
