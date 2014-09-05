package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Badlands")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.SWAMP})
@Printings({@Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class Badlands extends Card
{
	public Badlands(GameState state)
	{
		super(state);

	}
}
