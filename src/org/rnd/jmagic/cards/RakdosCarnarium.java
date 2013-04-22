package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rakdos Carnarium")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosCarnarium extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public RakdosCarnarium(GameState state)
	{
		super(state, 'B', 'R');
	}
}
