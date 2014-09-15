package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fated Infatuation")
@Types({Type.INSTANT})
@ManaCost("UUU")
@ColorIdentity({Color.BLUE})
public final class FatedInfatuation extends Card
{
	public FatedInfatuation(GameState state)
	{
		super(state);

		// Put a token onto the battlefield that's a copy of target creature you
		// control.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

		EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of target creature you control.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);

		// If it's your turn, scry 2.
		SetGenerator itsYourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
		this.addEffect(ifThen(itsYourTurn, scry(2, "Scry 2."), "If it's your turn, scry 2."));
	}
}
