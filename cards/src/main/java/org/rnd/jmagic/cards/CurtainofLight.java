package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Curtain of Light")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class CurtainofLight extends Card
{
	public CurtainofLight(GameState state)
	{
		super(state);

		Target target = this.addTarget(Unblocked.instance(), "target unblocked creature");

		EventType.ParameterMap blockedParameters = new EventType.ParameterMap();
		blockedParameters.put(EventType.Parameter.ATTACKER, targetedBy(target));
		blockedParameters.put(EventType.Parameter.DEFENDER, Empty.instance());
		this.addEffect(new EventFactory(EventType.BECOMES_BLOCKED, blockedParameters, "Target unblocked attacking creature becomes blocked."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
