package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Orzhov Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public OrzhovSignet(GameState state)
	{
		super(state, 'W', 'B');
	}
}
