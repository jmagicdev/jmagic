package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gush")
@Types({Type.INSTANT})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class Gush extends Card
{
	public Gush(GameState state)
	{
		super(state);

		// You may return two Islands you control to their owner's hand rather
		// than pay Gush's mana cost.
		EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return two Islands you control to their owner's hand");
		bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
		bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
		bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		bounce.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND)));
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may return two Islands you control to their owner's hand rather than pay Gush's mana cost.", bounce));

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
