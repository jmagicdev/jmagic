package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Watery Grave")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.ISLAND})
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class WateryGrave extends Card
{
	public WateryGrave(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
