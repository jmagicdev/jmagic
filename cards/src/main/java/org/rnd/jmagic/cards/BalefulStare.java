package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Baleful Stare")
@Types({Type.SORCERY})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class BalefulStare extends Card
{
	public BalefulStare(GameState state)
	{
		super(state);

		// Target opponent reveals his or her hand. You draw a card for each
		// Mountain and red card in it.

		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		SetGenerator cards = InZone.instance(HandOf.instance(target));

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target opponent reveals his or her hand.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, cards);
		this.addEffect(reveal);

		SetGenerator count = Count.instance(Intersect.instance(EffectResult.instance(reveal), Union.instance(HasSubType.instance(SubType.MOUNTAIN), HasColor.instance(Color.RED))));
		this.addEffect(drawCards(You.instance(), count, "You draw a card for each Mountain and red card in it."));
	}
}
