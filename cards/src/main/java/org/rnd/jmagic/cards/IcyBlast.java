package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Icy Blast")
@Types({Type.INSTANT})
@ManaCost("XU")
@ColorIdentity({Color.BLUE})
public final class IcyBlast extends Card
{
	public IcyBlast(GameState state)
	{
		super(state);

		// Tap X target creatures.

		// Ferocious \u2014 If you control a creature with power 4 or greater,
		// those creatures don't untap during their controllers' next untap
		// steps.

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature").setSingleNumber(ValueOfX.instance(This.instance())));

		EventFactory tap = tap(target, "Tap X target creatures.");

		EventFactory tapHard = new EventFactory(EventType.TAP_HARD, "Tap X target creatures. Those creatures don't untap during their controllers' next untap steps.");
		tapHard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tapHard.parameters.put(EventType.Parameter.OBJECT, target);

		this.addEffect(ifThenElse(Ferocious.instance(), tapHard, tap, "Tap X target creatures.\n\nFerocious \u2014 If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps."));
	}
}
