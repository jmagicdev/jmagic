package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Marrow Shards")
@Types({Type.INSTANT})
@ManaCost("(W/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class MarrowShards extends Card
{
	public MarrowShards(GameState state)
	{
		super(state);

		// ((w/p) can be paid with either (W) or 2 life.)

		// Marrow Shards deals 1 damage to each attacking creature.
		this.addEffect(spellDealDamage(1, Attacking.instance(), "Marrow Shards deals 1 damage to each attacking creature."));
	}
}
