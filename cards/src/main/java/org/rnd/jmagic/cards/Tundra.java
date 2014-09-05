package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tundra")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.ISLAND})
@Printings({@Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class Tundra extends Card
{
	public Tundra(GameState state)
	{
		super(state);

	}
}
