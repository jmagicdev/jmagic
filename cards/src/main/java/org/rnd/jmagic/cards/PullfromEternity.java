package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pull from Eternity")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class PullfromEternity extends Card
{
	public PullfromEternity(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(InZone.instance(ExileZone.instance()), FaceUp.instance(), Cards.instance()), "target face-up exiled card");

		EventType.ParameterMap moveParameters = new EventType.ParameterMap();
		moveParameters.put(EventType.Parameter.CAUSE, This.instance());
		moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.PUT_INTO_GRAVEYARD, moveParameters, "Put target face-up exiled card into its owner's graveyard."));
	}
}
