package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ideas Unbound")
@Types({Type.SORCERY})
@SubTypes({SubType.ARCANE})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.SAVIORS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class IdeasUnbound extends Card
{
	public IdeasUnbound(GameState state)
	{
		super(state);

		// Draw three cards.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));

		// Discard three cards at the beginning of the next end step.
		// at the beginning of the next end step.
		EventFactory delayedTrigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Discard three cards at the beginning of the next end step.");
		delayedTrigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
		delayedTrigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		delayedTrigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(discardCards(You.instance(), 3, "Discard three cards.")));
		this.addEffect(delayedTrigger);
	}
}
