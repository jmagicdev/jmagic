package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kalonian Behemoth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class KalonianBehemoth extends Card
{
	public KalonianBehemoth(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
	}
}
