package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Syncopate")
@Types({Type.INSTANT})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Syncopate extends Card
{
	public Syncopate(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (X).
		Target target = this.addTarget(Spells.instance(), "target spell");
		EventFactory counter = counter(targetedBy(target), "Counter target spell");
		counter.parameters.put(EventType.Parameter.TO, ExileZone.instance());

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
