package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shared Discovery")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SharedDiscovery extends Card
{
	public SharedDiscovery(GameState state)
	{
		super(state);

		// As an additional cost to cast Shared Discovery, tap four untapped
		// creatures you control.
		SetGenerator guys = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL);
		EventFactory tap = new EventFactory(EventType.TAP_CHOICE, "tap four untapped creatures you control");
		tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tap.parameters.put(EventType.Parameter.PLAYER, You.instance());
		tap.parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
		tap.parameters.put(EventType.Parameter.CHOICE, guys);
		this.addCost(tap);

		// Draw three cards.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
	}
}
