package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Conjured Currency")
@Types({Type.ENCHANTMENT})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class ConjuredCurrency extends Card
{
	public static final class ConjuredCurrencyAbility0 extends EventTriggeredAbility
	{
		public ConjuredCurrencyAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may exchange control of Conjured Currency and target permanent you neither own nor control.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), Union.instance(OwnedBy.instance(You.instance()), ControlledBy.instance(You.instance()))), "target permanent you neither own nor control"));

			EventFactory factory = new EventFactory(EventType.EXCHANGE_CONTROL, "Exchange control of Conjured Currency and target permanent you neither own nor control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(ABILITY_SOURCE_OF_THIS, target));
			this.addEffect(youMay(factory));
		}
	}

	public ConjuredCurrency(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may exchange control of Conjured
		// Currency and target permanent you neither own nor control.
		this.addAbility(new ConjuredCurrencyAbility0(state));
	}
}
