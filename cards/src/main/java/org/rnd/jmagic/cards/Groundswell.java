package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Groundswell")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Groundswell extends Card
{
	public Groundswell(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		// Target creature gets +2/+2 until end of turn.
		// If you had a land enter the battlefield under your control this turn,
		// that creature gets +4/+4 until end of turn instead.
		state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator amount = IfThenElse.instance(LandfallForSpells.instance(), numberGenerator(4), numberGenerator(2));
		this.addEffect(ptChangeUntilEndOfTurn(target, amount, amount, "Target creature gets +2/+2 until end of turn.\n\nIf you had a land enter the battlefield under your control this turn, that creature gets +4/+4 until end of turn instead."));
	}
}
