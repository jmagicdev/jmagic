package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodcrazed Neonate")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BloodcrazedNeonate extends Card
{
	public BloodcrazedNeonate(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Bloodcrazed Neonate attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, "Bloodcrazed Neonate"));

		// Whenever Bloodcrazed Neonate deals combat damage to a player, put a
		// +1/+1 counter on it.
		this.addAbility(new org.rnd.jmagic.abilities.MeleeGetPlusOnePlusOneCounters(state, "Bloodcrazed Neonate", 1));
	}
}
