package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chain of Vapor")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class ChainofVapor extends Card
{
	public ChainofVapor(GameState state)
	{
		super(state);

		// Return target nonland permanent to its owner's hand.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "target nonland permanent"));
		this.addEffect(bounce(target, "Return target nonland permanent to its owner's hand."));

		// Then that permanent's controller may sacrifice a land.
		SetGenerator thatPlayer = ControllerOf.instance(target);

		EventFactory mayPay = playerMay(thatPlayer, sacrifice(thatPlayer, 1, LandPermanents.instance(), "Sacrifice a land."), "That permanent's controller may sacrifice a land.");

		// If the player does, he or she may copy this spell and may choose a
		// new target for that copy.
		EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "That player copies this spell and may choose a new target for that copy");
		copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
		copy.parameters.put(EventType.Parameter.OBJECT, This.instance());
		copy.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

		EventFactory mayCopy = playerMay(thatPlayer, copy, "He or she may copy this spell and may choose a new target for that copy");

		EventFactory copyIfPay = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy.");
		copyIfPay.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
		copyIfPay.parameters.put(EventType.Parameter.THEN, Identity.instance(mayCopy));
		this.addEffect(copyIfPay);
	}
}
