package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Stomping Ground")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.MOUNTAIN})
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE), @Printings.Printed(ex = Guildpact.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class StompingGround extends Card
{
	public StompingGround(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
