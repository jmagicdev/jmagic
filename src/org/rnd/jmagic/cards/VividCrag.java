package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Vivid Crag")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class VividCrag extends VividLand
{
	public VividCrag(GameState state)
	{
		super(state, Color.RED);
	}
}
