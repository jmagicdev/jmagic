package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Breeding Pool")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.ISLAND})
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE), @Printings.Printed(ex = Dissension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class BreedingPool extends Card
{
	public BreedingPool(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
