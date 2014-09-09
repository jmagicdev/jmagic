package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Serene Remembrance")
@Types({Type.SORCERY})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SereneRemembrance extends Card
{
	public SereneRemembrance(GameState state)
	{
		super(state);

		// Shuffle Serene Remembrance and up to three target cards from a single
		// graveyard into their owners' libraries.
		Target target = new Target.SingleZone(InZone.instance(GraveyardOf.instance(Players.instance())), "up to three target cards from a single graveyard");
		target.setNumber(0, 3);
		this.addTarget(target);

		SetGenerator cards = Union.instance(This.instance(), targetedBy(target));
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(cards, OwnerOf.instance(cards)));
		this.addEffect(shuffle);
	}
}
