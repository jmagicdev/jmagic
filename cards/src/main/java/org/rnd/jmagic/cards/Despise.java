package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Despise")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Despise extends Card
{
	public Despise(GameState state)
	{
		super(state);

		// Target opponent reveals his or her hand. You choose a creature or
		// planeswalker card from it. That player discards that card.

		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		SetGenerator cards = InZone.instance(HandOf.instance(target));
		SetGenerator creaturesAndPw = HasType.instance(Identity.instance(Type.CREATURE, Type.PLANESWALKER));

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target opponent reveals his or her hand.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, cards);
		this.addEffect(reveal);

		EventFactory factory = new EventFactory(EventType.DISCARD_FORCE, "You choose a creature or planeswalker card from it. That player discards that card.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.TARGET, target);
		factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(creaturesAndPw));
		this.addEffect(factory);
	}
}
