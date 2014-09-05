package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Night of Souls' Betrayal")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class NightofSoulsBetrayal extends Card
{
	public NightofSoulsBetrayal(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, CreaturePermanents.instance(), "All creatures", -1, -1, true));
	}
}
