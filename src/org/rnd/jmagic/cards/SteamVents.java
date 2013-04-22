package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Steam Vents")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.MOUNTAIN})
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class SteamVents extends Card
{
	public SteamVents(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
