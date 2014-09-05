package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Battlefield Forge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Apocalypse.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BattlefieldForge extends org.rnd.jmagic.cardTemplates.PainLand
{
	public BattlefieldForge(GameState state)
	{
		super(state, "RW");
	}
}
