package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Savage Punch")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class SavagePunch extends Card
{
	public SavagePunch(GameState state)
	{
		super(state);

		// Target creature you control fights target creature you don't control.
		SetGenerator yours = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

		SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
		SetGenerator theirs = targetedBy(this.addTarget(enemyCreatures, "target creature you don't control"));

		EventFactory fight = fight(Union.instance(yours, theirs), "Target creature you control fights target creature you don't control.");

		// Ferocious \u2014 The creature you control gets +2/+2 until end of
		// turn before it fights if you control a creature with power 4 or
		// greater.
		EventFactory boost = ptChangeUntilEndOfTurn(yours, +2, +2, "The creature you control gets +2/+2 until end of turn.");

		this.addEffect(ifThenElse(Ferocious.instance(), sequence(boost, fight), fight, "Target creature you control fights target creature you don't control.\n\nFerocious \u2014 The creature you control gets +2/+2 until end of turn before it fights if you control a creature with power 4 or greater."));
	}
}
