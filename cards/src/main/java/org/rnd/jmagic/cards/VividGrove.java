package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vivid Grove")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Lorwyn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class VividGrove extends VividLand
{
	public VividGrove(GameState state)
	{
		super(state, Color.GREEN);
	}
}
