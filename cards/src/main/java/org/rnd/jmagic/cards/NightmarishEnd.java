package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nightmarish End")
@Types({Type.INSTANT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class NightmarishEnd extends Card
{
	public NightmarishEnd(GameState state)
	{
		super(state);

		// Target creature gets -X/-X until end of turn, where X is the number
		// of cards in your hand.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator X = Subtract.instance(numberGenerator(0), Count.instance(InZone.instance(HandOf.instance(You.instance()))));
		this.addEffect(ptChangeUntilEndOfTurn(target, X, X, "Target creature gets -X/-X until end of turn."));
	}
}
