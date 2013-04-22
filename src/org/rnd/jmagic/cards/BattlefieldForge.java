package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Battlefield Forge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BattlefieldForge extends org.rnd.jmagic.cardTemplates.PainLand
{
	public BattlefieldForge(GameState state)
	{
		super(state, "RW");
	}
}
