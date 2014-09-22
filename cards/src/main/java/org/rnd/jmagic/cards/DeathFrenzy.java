package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Death Frenzy")
@Types({Type.SORCERY})
@ManaCost("3BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class DeathFrenzy extends Card
{
	public DeathFrenzy(GameState state)
	{
		super(state);

		// All creatures get -2/-2 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), -2, -2, "All creatures get -2/-2 until end of turn."));

		// Whenever a creature dies this turn, you gain 1 life.
		EventFactory gainLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Whenever a creature dies this turn, you gain 1 life.");
		gainLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		gainLater.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(whenXDies(CreaturePermanents.instance())));
		gainLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(gainLife(You.instance(), 1, "You gain 1 life.")));
		this.addEffect(gainLater);
	}
}
