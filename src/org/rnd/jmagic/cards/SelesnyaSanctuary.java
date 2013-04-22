package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Selesnya Sanctuary")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaSanctuary extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public SelesnyaSanctuary(GameState state)
	{
		super(state, 'G', 'W');
	}
}
