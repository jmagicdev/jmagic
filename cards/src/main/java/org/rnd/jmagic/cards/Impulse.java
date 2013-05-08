package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Impulse")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.VISIONS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Impulse extends Card
{
	public Impulse(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM, "Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
		this.addEffect(factory);
	}
}
