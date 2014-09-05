package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tropical Island")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.FOREST})
@Printings({@Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class TropicalIsland extends Card
{
	public TropicalIsland(GameState state)
	{
		super(state);

	}
}
