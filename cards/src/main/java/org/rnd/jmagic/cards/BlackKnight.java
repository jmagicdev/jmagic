package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Black Knight")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("BB")
@ColorIdentity({Color.BLACK})
public final class BlackKnight extends Card
{
	public BlackKnight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromWhite(state));
	}
}
