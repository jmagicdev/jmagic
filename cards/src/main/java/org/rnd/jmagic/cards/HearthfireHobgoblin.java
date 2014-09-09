package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hearthfire Hobgoblin")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SOLDIER})
@ManaCost("(RW)(RW)(RW)")
@ColorIdentity({Color.WHITE, Color.RED})
public final class HearthfireHobgoblin extends Card
{
	public HearthfireHobgoblin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));
	}
}
