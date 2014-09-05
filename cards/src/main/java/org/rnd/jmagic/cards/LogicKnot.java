package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Logic Knot")
@Types({Type.INSTANT})
@ManaCost("XUU")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LogicKnot extends Card
{
	public LogicKnot(GameState state)
	{
		super(state);

		// Delve
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Counter target spell unless its controller pays (X).
		Target target = this.addTarget(Spells.instance(), "target spell");
		EventFactory counter = counter(targetedBy(target), "Counter target spell");

		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (X)");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("1")));
		pay.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		pay.parameters.put(EventType.Parameter.PLAYER, controller);

		EventFactory counterUnless = unless(controller, counter, pay, "Counter target spell unless its controller pays (X).");
		this.addEffect(counterUnless);
	}
}
