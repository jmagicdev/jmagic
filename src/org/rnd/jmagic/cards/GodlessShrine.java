package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Godless Shrine")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.PLAINS})
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class GodlessShrine extends Card
{
	public GodlessShrine(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
