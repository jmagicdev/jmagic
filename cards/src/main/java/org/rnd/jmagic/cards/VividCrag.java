package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vivid Crag")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Lorwyn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class VividCrag extends VividLand
{
	public VividCrag(GameState state)
	{
		super(state, Color.RED);
	}
}
