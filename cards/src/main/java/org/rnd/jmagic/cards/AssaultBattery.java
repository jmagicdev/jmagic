package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Assault // Battery")
@Types({Type.SORCERY})
@ColorIdentity({Color.GREEN, Color.RED})
public final class AssaultBattery extends SplitCard
{
	public AssaultBattery(GameState state)
	{
		super(state, Assault.class, Battery.class);
	}
}
