package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Night of Souls' Betrayal")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class NightofSoulsBetrayal extends Card
{
	public NightofSoulsBetrayal(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, CreaturePermanents.instance(), "All creatures", -1, -1, true));
	}
}
