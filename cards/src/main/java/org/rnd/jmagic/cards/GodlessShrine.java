package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Godless Shrine")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.PLAINS})
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE), @Printings.Printed(ex = Guildpact.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class GodlessShrine extends Card
{
	public GodlessShrine(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
