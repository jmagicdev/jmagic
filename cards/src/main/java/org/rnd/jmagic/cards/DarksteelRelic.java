package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Darksteel Relic")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DarksteelRelic extends Card
{
	public DarksteelRelic(GameState state)
	{
		super(state);

		// Darksteel Relic is indestructible. (Effects that say "destroy" don't
		// destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.Indestructible(state, this.getName()));
	}
}
