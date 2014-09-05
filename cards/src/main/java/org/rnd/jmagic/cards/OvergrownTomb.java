package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Overgrown Tomb")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.FOREST})
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class OvergrownTomb extends Card
{
	public OvergrownTomb(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
