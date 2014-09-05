package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Call to Heel")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CalltoHeel extends Card
{
	public CalltoHeel(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));

		this.addEffect(drawCards(ControllerOf.instance(targetedBy(target)), 1, "Its controller draws a card."));
	}
}
