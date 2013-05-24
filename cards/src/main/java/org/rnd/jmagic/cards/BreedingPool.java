package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Breeding Pool")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.ISLAND})
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE), @Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class BreedingPool extends Card
{
	public BreedingPool(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
