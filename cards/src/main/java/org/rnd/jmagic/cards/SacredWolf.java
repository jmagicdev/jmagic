package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sacred Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class SacredWolf extends Card
{
	public SacredWolf(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
