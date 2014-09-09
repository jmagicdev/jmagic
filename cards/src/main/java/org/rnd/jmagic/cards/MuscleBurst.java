package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Muscle Burst")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class MuscleBurst extends Card
{
	public MuscleBurst(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator X = Sum.instance(Union.instance(numberGenerator(3), Count.instance(Intersect.instance(HasName.instance("Muscle Burst"), InZone.instance(GraveyardOf.instance(Players.instance()))))));

		// Target creature gets +X/+X until end of turn, where X is 3 plus the
		// number of cards named Muscle Burst in all graveyards.
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), X, X, "Target creature gets +X/+X until end of turn, where X is 3 plus the number of cards named Muscle Burst in all graveyards."));
	}
}
