package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Condescend")
@Types({Type.INSTANT})
@ManaCost("XU")
@ColorIdentity({Color.BLUE})
public final class Condescend extends Card
{
	public Condescend(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		EventFactory counter = counter(targetedBy(target), "Counter target spell");

		ManaSymbol xSymbol = new ManaSymbol("1");
		xSymbol.isX = true;
		xSymbol.colorless = 1;

		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (X)");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.instance(xSymbol));
		pay.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		pay.parameters.put(EventType.Parameter.PLAYER, controller);
		this.addEffect(unless(controller, counter, pay, "Counter target spell unless its controller pays (X)."));

		this.addEffect(scry(2, "\n\nScry 2."));
	}
}
