package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rush of Blood")
@Types({Type.INSTANT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class RushofBlood extends Card
{
	public RushofBlood(GameState state)
	{
		super(state);

		// Target creature gets +X/+0 until end of turn, where X is its power.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator X = PowerOf.instance(target);
		this.addEffect(ptChangeUntilEndOfTurn(target, X, numberGenerator(0), "Target creature gets +X/+0 until end of turn, where X is its power."));
	}
}
