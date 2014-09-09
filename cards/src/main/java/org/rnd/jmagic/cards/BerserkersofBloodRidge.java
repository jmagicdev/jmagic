package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Berserkers of Blood Ridge")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class BerserkersofBloodRidge extends Card
{
	public BerserkersofBloodRidge(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Berserkers of Blood Ridge attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
