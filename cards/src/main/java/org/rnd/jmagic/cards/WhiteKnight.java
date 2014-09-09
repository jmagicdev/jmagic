package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("White Knight")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class WhiteKnight extends Card
{
	public WhiteKnight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike, protection from black
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlack(state));
	}
}
