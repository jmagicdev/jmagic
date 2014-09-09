package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Momentous Fall")
@Types({Type.INSTANT})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class MomentousFall extends Card
{
	public MomentousFall(GameState state)
	{
		super(state);

		// As an additional cost to cast Momentous Fall, sacrifice a creature.
		EventFactory sac = sacrificeACreature();
		this.addCost(sac);
		SetGenerator sacrificedCreature = OldObjectOf.instance(CostResult.instance(sac));

		// You draw cards equal to the sacrificed creature's power, then you
		// gain life equal to its toughness.
		this.addEffect(drawCards(You.instance(), PowerOf.instance(sacrificedCreature), "You draw cards equal to the sacrificed creature's power,"));
		this.addEffect(gainLife(You.instance(), ToughnessOf.instance(sacrificedCreature), "then you gain life equal to its toughness."));
	}
}
