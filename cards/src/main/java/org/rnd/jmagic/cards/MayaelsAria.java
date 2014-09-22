package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.util.*;

@Name("Mayael's Aria")
@Types({Type.ENCHANTMENT})
@ManaCost("RGW")
@ColorIdentity({Color.WHITE, Color.RED, Color.GREEN})
public final class MayaelsAria extends Card
{
	public static final class MayaelsTrigger extends EventTriggeredAbility
	{
		public MayaelsTrigger(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater. Then you gain 10 life if you control a creature with power 10 or greater. Then you win the game if you control a creature with power 20 or greater.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			{
				EventFactory counterFactory = putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control");

				EventFactory first = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater.");
				first.parameters.put(EventType.Parameter.IF, Intersect.instance(CREATURES_YOU_CONTROL, HasPower.instance(Identity.instance(new NumberRange(5, null)))));
				first.parameters.put(EventType.Parameter.THEN, Identity.instance(counterFactory));
				this.addEffect(first);
			}

			// You gain 10 life if you control a creature with power 10 or
			// greater.
			{
				EventFactory lifeFactory = gainLife(You.instance(), 10, "You gain 10 life");

				EventFactory second = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You gain 10 life if you control a creature with power 10 or greater.");
				second.parameters.put(EventType.Parameter.IF, Intersect.instance(CREATURES_YOU_CONTROL, HasPower.instance(Identity.instance(new NumberRange(10, null)))));
				second.parameters.put(EventType.Parameter.THEN, Identity.instance(lifeFactory));
				this.addEffect(second);
			}

			// You win the game if you control a creature with power 20 or
			// greater.
			{
				EventFactory third = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You win the game if you control a creature with power 20 or greater.");
				third.parameters.put(EventType.Parameter.IF, Intersect.instance(CREATURES_YOU_CONTROL, HasPower.instance(Identity.instance(new NumberRange(20, null)))));
				third.parameters.put(EventType.Parameter.THEN, Identity.instance(youWinTheGame()));
				this.addEffect(third);
			}
		}
	}

	public MayaelsAria(GameState state)
	{
		super(state);

		this.addAbility(new MayaelsTrigger(state));
	}
}
