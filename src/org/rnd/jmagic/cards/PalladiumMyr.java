package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Palladium Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PalladiumMyr extends Card
{
	public PalladiumMyr(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T): Add (2) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(2)"));
	}
}
