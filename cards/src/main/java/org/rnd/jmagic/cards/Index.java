package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Index")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Index extends Card
{
	public Index(GameState state)
	{
		super(state);

		// Look at the top five cards of your library, then put them back in any
		// order.
		EventFactory factory = new EventFactory(EventType.LOOK_AND_PUT_BACK, "Look at the top five cards of your library, then put them back in any order.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.TARGET, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
		this.addEffect(factory);
	}
}
