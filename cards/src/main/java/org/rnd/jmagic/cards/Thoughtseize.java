package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thoughtseize")
@Types({Type.SORCERY})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Thoughtseize extends Card
{
	public Thoughtseize(GameState state)
	{
		super(state);

		// Target player reveals his or her hand.
		Target target = this.addTarget(Players.instance(), "target player");
		SetGenerator targetPlayer = targetedBy(target);
		SetGenerator cardsInHand = InZone.instance(HandOf.instance(targetPlayer));

		EventType.ParameterMap revealParameters = new EventType.ParameterMap();
		revealParameters.put(EventType.Parameter.CAUSE, This.instance());
		revealParameters.put(EventType.Parameter.OBJECT, cardsInHand);
		this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Target player reveals his or her hand."));

		// You choose a nonland card from it. That player discards that card.
		EventType.ParameterMap discardParameters = new EventType.ParameterMap();
		discardParameters.put(EventType.Parameter.CAUSE, This.instance());
		discardParameters.put(EventType.Parameter.PLAYER, You.instance());
		discardParameters.put(EventType.Parameter.TARGET, targetPlayer);
		discardParameters.put(EventType.Parameter.CHOICE, Identity.instance(RelativeComplement.instance(cardsInHand, HasType.instance(Type.LAND))));
		this.addEffect(new EventFactory(EventType.DISCARD_FORCE, discardParameters, "You choose a nonland card from it. That player discards that card."));

		// You lose 2 life.
		this.addEffect(loseLife(You.instance(), 2, "You lose 2 life."));
	}
}
