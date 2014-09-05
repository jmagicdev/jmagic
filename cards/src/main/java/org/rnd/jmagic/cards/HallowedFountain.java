package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Hallowed Fountain")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.PLAINS})
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE), @Printings.Printed(ex = Dissension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class HallowedFountain extends Card
{
	public HallowedFountain(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
