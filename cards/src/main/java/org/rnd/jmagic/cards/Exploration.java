package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Exploration")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = UrzasSaga.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Exploration extends Card
{
	public Exploration(GameState state)
	{
		super(state);

		// You may play an additional land on each of your turns.
		this.addAbility(new org.rnd.jmagic.abilities.PlayExtraLands.Final(state, 1, "You may play an additional land on each of your turns."));
	}
}
