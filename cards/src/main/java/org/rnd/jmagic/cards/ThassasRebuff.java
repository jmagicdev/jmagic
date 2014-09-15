package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thassa's Rebuff")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ThassasRebuff extends Card
{
	public ThassasRebuff(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (X), where X is your
		// devotion to blue.

		Target target = this.addTarget(Spells.instance(), "target spell");
		EventFactory counter = counter(targetedBy(target), "Counter target " + target.name);

		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (X), where X is their devotion to blue");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
		pay.parameters.put(EventType.Parameter.NUMBER, DevotionTo.instance(Color.BLUE));
		pay.parameters.put(EventType.Parameter.PLAYER, controller);
		EventFactory effect = unless(controller, counter, pay, "Counter target spell unless its controller pays (X), where X is your devotion to blue.");
		this.addEffect(effect);
	}
}
