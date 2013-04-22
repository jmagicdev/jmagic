package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Golgari Rot Farm")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariRotFarm extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public GolgariRotFarm(GameState state)
	{
		super(state, 'B', 'G');
	}
}
