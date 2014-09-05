package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Temple Garden")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.PLAINS})
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class TempleGarden extends Card
{
	public TempleGarden(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
