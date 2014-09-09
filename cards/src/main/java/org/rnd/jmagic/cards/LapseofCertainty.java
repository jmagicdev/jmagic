package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lapse of Certainty")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class LapseofCertainty extends Card
{
	public LapseofCertainty(GameState state)
	{
		super(state);

		// Counter target spell. If that spell is countered this way, put it on
		// top of its owner's library instead of into that player's graveyard.
		Target target = this.addTarget(Spells.instance(), "target spell");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		parameters.put(EventType.Parameter.TO, LibraryOf.instance(OwnerOf.instance(targetedBy(target))));
		this.addEffect(new EventFactory(EventType.COUNTER, parameters, "Counter target spell. If that spell is countered this way, put on top of its owner's library instead of into that player's graveyard."));
	}
}
