package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Overgrown Tomb")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.FOREST})
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class OvergrownTomb extends Card
{
	public OvergrownTomb(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
