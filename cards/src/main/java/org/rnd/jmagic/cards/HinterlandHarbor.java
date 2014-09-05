package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Hinterland Harbor")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class HinterlandHarbor extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public HinterlandHarbor(GameState state)
	{
		super(state, SubType.FOREST, SubType.ISLAND);
	}
}
