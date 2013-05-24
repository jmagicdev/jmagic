package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sacred Foundry")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.MOUNTAIN})
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class SacredFoundry extends Card
{
	public SacredFoundry(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
