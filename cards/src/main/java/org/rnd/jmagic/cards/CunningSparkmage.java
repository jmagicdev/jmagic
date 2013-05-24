package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cunning Sparkmage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class CunningSparkmage extends Card
{
	public CunningSparkmage(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (T): Cunning Sparkmage deals 1 damage to target creature or player.
		this.addAbility(new org.rnd.jmagic.abilities.Ping(state, this.getName()));
	}
}
