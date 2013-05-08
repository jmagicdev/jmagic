package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hymn to Tourach")
@Types({Type.SORCERY})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.FALLEN_EMPIRES, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class HymntoTourach extends Card
{
	public HymntoTourach(GameState state)
	{
		super(state);

		// Target player discards two cards at random.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory factory = new EventFactory(EventType.DISCARD_RANDOM, "Target player discards two cards at random.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, target);
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		this.addEffect(factory);
	}
}
