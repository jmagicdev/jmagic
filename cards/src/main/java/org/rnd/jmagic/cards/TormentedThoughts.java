package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tormented Thoughts")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class TormentedThoughts extends Card
{
	public TormentedThoughts(GameState state)
	{
		super(state);

		// As an additional cost to cast Tormented Thoughts, sacrifice a
		// creature.
		EventFactory sacrifice = sacrificeACreature();
		this.addCost(sacrifice);

		// Target player discards a number of cards equal to the sacrificed
		// creature's power.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator sacrificed = OldObjectOf.instance(CostResult.instance(sacrifice));
		SetGenerator number = PowerOf.instance(sacrificed);
		this.addEffect(discardCards(target, number, "Target player discards a number of cards equal to the sacrificed creature's power."));
	}
}
