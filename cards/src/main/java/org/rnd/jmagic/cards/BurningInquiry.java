package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Burning Inquiry")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BurningInquiry extends Card
{
	public BurningInquiry(GameState state)
	{
		super(state);

		// Each player draws three cards,
		this.addEffect(drawCards(Players.instance(), 3, "Each player draws three cards,"));

		// then discards three cards at random.
		EventType.ParameterMap discardParameters = new EventType.ParameterMap();
		discardParameters.put(EventType.Parameter.CAUSE, This.instance());
		discardParameters.put(EventType.Parameter.PLAYER, Players.instance());
		discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		this.addEffect(new EventFactory(EventType.DISCARD_RANDOM, discardParameters, "then discards three cards at random."));
	}
}
