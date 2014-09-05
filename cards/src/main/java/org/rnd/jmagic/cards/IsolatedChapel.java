package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Isolated Chapel")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class IsolatedChapel extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public IsolatedChapel(GameState state)
	{
		super(state, SubType.PLAINS, SubType.SWAMP);
	}
}
