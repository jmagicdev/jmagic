package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Swift Kick")
@Types({Type.INSTANT})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class SwiftKick extends Card
{
	public SwiftKick(GameState state)
	{
		super(state);

		// Target creature you control gets +1/+0 until end of turn.
		SetGenerator yours = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(ptChangeUntilEndOfTurn(yours, +1, +0, "Target creature you control gets +1/+0 until end of turn."));

		// It fights target creature you don't control.
		SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
		SetGenerator theirs = targetedBy(this.addTarget(enemyCreatures, "target creature you don't control"));
		this.addEffect(fight(Union.instance(yours, theirs), "It fights target creature you don't control."));
	}
}
