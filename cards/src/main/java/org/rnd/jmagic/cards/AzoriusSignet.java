package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Azorius Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public AzoriusSignet(GameState state)
	{
		super(state, 'W', 'U');
	}
}
