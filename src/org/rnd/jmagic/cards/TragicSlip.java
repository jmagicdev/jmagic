package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tragic Slip")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class TragicSlip extends Card
{
	public TragicSlip(GameState state)
	{
		super(state);

		// Target creature gets -1/-1 until end of turn.

		// Morbid \u2014 That creature gets -13/-13 until end of turn instead if
		// a creature died this turn.
		SetGenerator amount = IfThenElse.instance(Morbid.instance(), numberGenerator(-13), numberGenerator(-1));
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, amount, amount, "Target creature gets -1/-1 until end of turn.\n\nThat creature gets -13/-13 until end of turn instead if a creature died this turn."));

		state.ensureTracker(new Morbid.Tracker());
	}
}
