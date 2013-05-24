package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spell Rupture")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SpellRupture extends Card
{
	public SpellRupture(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (X), where X is the
		// greatest power among creatures you control.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		EventFactory counter = counter(target, "Counter target spell");

		SetGenerator controller = ControllerOf.instance(target);
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (X) where X is the greatest power among creatures you control.");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1")));
		pay.parameters.put(EventType.Parameter.NUMBER, Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL)));
		pay.parameters.put(EventType.Parameter.PLAYER, controller);
		this.addEffect(unless(controller, counter, pay, "Counter target spell unless its controller pays (X), where X is the greatest power among creatures you control."));

	}
}
