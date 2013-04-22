package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blackmail")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Blackmail extends Card
{
	public Blackmail(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		// Target player reveals three cards from his or her hand and you choose
		// one of them. That player discards that card.
		EventFactory factory = new EventFactory(REVEAL_SOME_CARDS_AND_DISCARD_FORCE, "Target player reveals three cards from his or her hand and you choose one of them. That player discards that card.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		factory.parameters.put(EventType.Parameter.TARGET, target);
		this.addEffect(factory);
	}
}
