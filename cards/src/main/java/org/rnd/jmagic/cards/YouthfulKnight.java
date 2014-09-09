package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Youthful Knight")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class YouthfulKnight extends Card
{
	public YouthfulKnight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
