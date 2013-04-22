package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Condescend")
@Types({Type.INSTANT})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Condescend extends Card
{
	public Condescend(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		EventFactory counter = counter(targetedBy(target), "Counter target spell");

		ManaPool xPool = new ManaPool();
		ManaSymbol xSymbol = new ManaSymbol("X");
		xSymbol.isX = true;
		xPool.add(xSymbol);

		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (X)");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.instance(xPool));
		pay.parameters.put(EventType.Parameter.PLAYER, controller);
		this.addEffect(unless(controller, counter, pay, "Counter target spell unless its controller pays (X)."));

		this.addEffect(scry(2, "\n\nScry 2."));
	}
}
