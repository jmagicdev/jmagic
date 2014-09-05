package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Volcanic Island")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.ISLAND})
@Printings({@Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class VolcanicIsland extends Card
{
	public VolcanicIsland(GameState state)
	{
		super(state);

	}
}
