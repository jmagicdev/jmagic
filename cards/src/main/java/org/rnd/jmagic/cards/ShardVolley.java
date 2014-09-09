package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shard Volley")
@Types({Type.INSTANT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class ShardVolley extends Card
{
	public ShardVolley(GameState state)
	{
		super(state);

		this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Shard Volley deals 3 damage to target creature or player."));
	}
}
