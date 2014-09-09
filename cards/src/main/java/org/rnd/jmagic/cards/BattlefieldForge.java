package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Battlefield Forge")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BattlefieldForge extends org.rnd.jmagic.cardTemplates.PainLand
{
	public BattlefieldForge(GameState state)
	{
		super(state, "RW");
	}
}
