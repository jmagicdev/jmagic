package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sudden Storm")
@Types({Type.INSTANT})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class SuddenStorm extends Card
{
	public SuddenStorm(GameState state)
	{
		super(state);

		// Tap up to two target creatures. Those creatures don't untap during
		// their controllers' next untap steps.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "up to two target creatures").setNumber(0, 2));
		EventFactory tap = new EventFactory(EventType.TAP_HARD, "Tap up to two target creatures. Those creatures don't untap during their controllers' next untap steps.");
		tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tap.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(tap);

		// Scry 1.
		this.addEffect(scry(1, "Scry 1."));
	}
}
