package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindswipe")
@Types({Type.INSTANT})
@ManaCost("XUR")
@ColorIdentity({Color.RED, Color.BLUE})
public final class Mindswipe extends Card
{
	public Mindswipe(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (X).
		Target target = this.addTarget(Spells.instance(), "target spell");

		EventFactory counter = counter(targetedBy(target), "Counter target spell");

		ManaSymbol xSymbol = new ManaSymbol("1");
		xSymbol.isX = true;
		xSymbol.colorless = 1;

		SetGenerator X = ValueOfX.instance(This.instance());

		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (X)");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.instance(xSymbol));
		pay.parameters.put(EventType.Parameter.NUMBER, X);
		pay.parameters.put(EventType.Parameter.PLAYER, controller);
		this.addEffect(unless(controller, counter, pay, "Counter target spell unless its controller pays (X)."));

		// Mindswipe deals X damage to that spell's controller.
		this.addEffect(spellDealDamage(X, controller, "Mindswipe deals X damage to that spell's controller."));
	}
}
