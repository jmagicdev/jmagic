package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Touch of the Eternal")
@Types({Type.ENCHANTMENT})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class TouchoftheEternal extends Card
{
	public static final class TouchoftheEternalAbility0 extends EventTriggeredAbility
	{
		public TouchoftheEternalAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, count the number of permanents you control. Your life total becomes that number.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory factory = new EventFactory(EventType.SET_LIFE, "Count the number of permanents you control. Your life total becomes that number.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(ControlledBy.instance(You.instance())));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public TouchoftheEternal(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, count the number of permanents you
		// control. Your life total becomes that number.
		this.addAbility(new TouchoftheEternalAbility0(state));
	}
}
