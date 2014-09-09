package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dissipate")
@Types({Type.INSTANT})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class Dissipate extends Card
{
	public Dissipate(GameState state)
	{
		super(state);

		// Counter target spell. If that spell is countered this way, exile it
		// instead of putting it into its owner's graveyard.
		Target target = this.addTarget(Spells.instance(), "target spell");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(new EventFactory(EventType.COUNTER, parameters, "Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard."));
	}
}
