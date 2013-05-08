package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Sunpetal Grove")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SunpetalGrove extends Magic2010DualLand
{
	public SunpetalGrove(GameState state)
	{
		super(state, SubType.FOREST, SubType.PLAINS);
	}
}
