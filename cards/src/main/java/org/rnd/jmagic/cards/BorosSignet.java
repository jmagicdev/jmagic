package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Boros Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public BorosSignet(GameState state)
	{
		super(state, 'R', 'W');
	}
}
