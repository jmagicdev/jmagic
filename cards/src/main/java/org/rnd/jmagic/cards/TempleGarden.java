package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Temple Garden")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.PLAINS})
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class TempleGarden extends Card
{
	public TempleGarden(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
