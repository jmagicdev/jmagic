package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodrock Cyclops")
@Types({Type.CREATURE})
@SubTypes({SubType.CYCLOPS})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BloodrockCyclops extends Card
{
	public BloodrockCyclops(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Bloodrock Cyclops attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
