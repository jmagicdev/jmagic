package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tribute to Hunger")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class TributetoHunger extends Card
{
	public TributetoHunger(GameState state)
	{
		super(state);

		// Target opponent sacrifices a creature. You gain life equal to that
		// creature's toughness.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		EventFactory sacrifice = sacrifice(target, 1, CreaturePermanents.instance(), "Target opponent sacrifices a creature.");
		this.addEffect(sacrifice);

		this.addEffect(gainLife(You.instance(), ToughnessOf.instance(OldObjectOf.instance(EffectResult.instance(sacrifice))), "You gain life equal to that creature's toughness."));
	}
}
