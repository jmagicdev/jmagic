package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Executioner's Swing")
@Types({Type.INSTANT})
@ManaCost("WB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ExecutionersSwing extends Card
{
	public ExecutionersSwing(GameState state)
	{
		super(state);

		// Target creature that dealt damage this turn gets -5/-5 until end of
		// turn.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasDealtDamageThisTurn.instance()), "target creature that dealt damage this turn."));
		this.addEffect(ptChangeUntilEndOfTurn(target, -5, -5, "Target creature that dealt damage this turn gets -5/-5 until end of turn."));

		state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
	}
}
