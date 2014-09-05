package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Avenging Arrow")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AvengingArrow extends Card
{
	public AvengingArrow(GameState state)
	{
		super(state);

		// Destroy target creature that dealt damage this turn.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), WasDealtDamageThisTurn.instance()), "target creature that dealt damage this turn."));
		this.addEffect(destroy(target, "Destroy target creature that dealt damage this turn."));

		state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
	}
}
