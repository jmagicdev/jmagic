package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Valley Dasher")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ValleyDasher extends Card
{
	public ValleyDasher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Valley Dasher attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
