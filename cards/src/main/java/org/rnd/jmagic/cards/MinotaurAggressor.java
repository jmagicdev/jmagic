package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Minotaur Aggressor")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.MINOTAUR})
@ManaCost("6R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class MinotaurAggressor extends Card
{
	public MinotaurAggressor(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(2);

		// First strike, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
