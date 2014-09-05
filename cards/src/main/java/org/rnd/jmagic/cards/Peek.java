package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Peek")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Peek extends Card
{
	public Peek(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		EventType.ParameterMap lookParameters = new EventType.ParameterMap();
		lookParameters.put(EventType.Parameter.CAUSE, This.instance());
		lookParameters.put(EventType.Parameter.OBJECT, InZone.instance(HandOf.instance(targetedBy(target))));
		lookParameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(new EventFactory(EventType.LOOK, lookParameters, "Look at target player's hand."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
