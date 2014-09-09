package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gruesome Discovery")
@Types({Type.SORCERY})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class GruesomeDiscovery extends Card
{
	public GruesomeDiscovery(GameState state)
	{
		super(state);

		// Target player discards two cards. If a creature died this turn,
		// instead that player reveals his or her hand, you choose two cards
		// from it, then that player discards those cards.

		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory normalDiscard = discardCards(target, 2, "Target player discards two cards.");

		SetGenerator cards = InZone.instance(HandOf.instance(target));
		EventType.ParameterMap revealParameters = new EventType.ParameterMap();
		revealParameters.put(EventType.Parameter.CAUSE, This.instance());
		revealParameters.put(EventType.Parameter.OBJECT, cards);
		EventFactory reveal = new EventFactory(EventType.REVEAL, revealParameters, "Target opponent reveals his or her hand.");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.TARGET, target);
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		EventFactory discardForce = new EventFactory(EventType.DISCARD_FORCE, parameters, "You choose a noncreature card from it, then that player discards that card.");

		this.addEffect(ifThenElse(Morbid.instance(), sequence(reveal, discardForce), normalDiscard, "Target player discards two cards. If a creature died this turn, instead that player reveals his or her hand, you choose two cards from it, then that player discards those cards."));

		state.ensureTracker(new Morbid.Tracker());
	}
}
