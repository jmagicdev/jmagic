package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trapfinder's Trick")
@Types({Type.SORCERY})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class TrapfindersTrick extends Card
{
	public TrapfindersTrick(GameState state)
	{
		super(state);

		// Target player reveals his or her hand
		Target target = this.addTarget(Players.instance(), "target player");
		SetGenerator cardsInHand = InZone.instance(HandOf.instance(targetedBy(target)));

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target player reveals his or her hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, cardsInHand);
		this.addEffect(reveal);

		// and discards all Trap cards.
		EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, "and discards all Trap cards.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.CARD, Intersect.instance(HasSubType.instance(SubType.TRAP), cardsInHand));
		this.addEffect(discard);
	}
}
