package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Azorius Chancery")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusChancery extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public AzoriusChancery(GameState state)
	{
		super(state, 'W', 'U');
	}
}
