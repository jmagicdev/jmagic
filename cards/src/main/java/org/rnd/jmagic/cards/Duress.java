package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duress")
@Types({Type.SORCERY})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Duress extends Card
{
	public Duress(GameState state)
	{
		super(state);

		// Target opponent
		Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

		// reveals his or her hand.
		SetGenerator cards = InZone.instance(HandOf.instance(targetedBy(target)));
		EventType.ParameterMap revealParameters = new EventType.ParameterMap();
		revealParameters.put(EventType.Parameter.CAUSE, This.instance());
		revealParameters.put(EventType.Parameter.OBJECT, cards);
		this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Target opponent reveals his or her hand."));

		// You choose a noncreature, nonland card from it. That player discards
		// that card.
		// normally, i wouldn't bother calling out the cards in the opponents'
		// hands, since the DISCARD_FORCE will take care of that, but i need a
		// set to take things away from
		SetGenerator creaturesAndLands = HasType.instance(Identity.instance(Type.CREATURE, Type.LAND));
		SetGenerator choices = RelativeComplement.instance(cards, creaturesAndLands);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		parameters.put(EventType.Parameter.CHOICE, Identity.instance(choices));
		this.addEffect(new EventFactory(EventType.DISCARD_FORCE, parameters, "You choose a noncreature, nonland card from it. That player discards that card."));
	}
}
