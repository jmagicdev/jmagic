package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Darksteel Relic")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DarksteelRelic extends Card
{
	public DarksteelRelic(GameState state)
	{
		super(state);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));
	}
}
