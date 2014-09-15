package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Impetuous Sunchaser")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ImpetuousSunchaser extends Card
{
	public ImpetuousSunchaser(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Impetuous Sunchaser attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
