package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Stomping Ground")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.MOUNTAIN})
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class StompingGround extends Card
{
	public StompingGround(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
