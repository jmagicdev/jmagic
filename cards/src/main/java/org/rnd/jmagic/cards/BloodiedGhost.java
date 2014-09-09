package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodied Ghost")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1(WB)(WB)")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class BloodiedGhost extends Card
{
	public BloodiedGhost(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 1, Counter.CounterType.MINUS_ONE_MINUS_ONE));
	}
}
