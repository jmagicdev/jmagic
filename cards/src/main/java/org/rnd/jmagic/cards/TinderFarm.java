package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tinder Farm")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class TinderFarm extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public TinderFarm(GameState state)
	{
		super(state, Color.GREEN);
	}
}
