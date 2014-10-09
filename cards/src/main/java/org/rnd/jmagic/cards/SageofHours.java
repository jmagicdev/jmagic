package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sage of Hours")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class SageofHours extends Card
{
	public static final class SageofHoursAbility0 extends EventTriggeredAbility
	{
		public SageofHoursAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Sage of Hours, put a +1/+1 counter on it.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Sage of Hours"));
		}
	}

	public static final class SageofHoursAbility1 extends ActivatedAbility
	{
		public SageofHoursAbility1(GameState state)
		{
			super(state, "Remove all +1/+1 counters from Sage of Hours: For each five counters removed this way, take an extra turn after this one.");

			SetGenerator all = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));
			EventFactory cost = removeCountersFromThis(all, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Remove all +1/+1 counters from Sage of Hours");
			this.addEffect(cost);

			SetGenerator removed = Count.instance(CostResult.instance(cost));
			SetGenerator turns = DivideBy.instance(removed, numberGenerator(5), false);

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.NUMBER, turns);
			this.addEffect(new EventFactory(EventType.TAKE_EXTRA_TURN, parameters, "Remove all +1/+1 counters from Sage of Hours: For each five counters removed this way, take an extra turn after this one."));
		}
	}

	public SageofHours(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Sage of Hours,
		// put a +1/+1 counter on it.
		this.addAbility(new SageofHoursAbility0(state));

		// Remove all +1/+1 counters from Sage of Hours: For each five counters
		// removed this way, take an extra turn after this one.
		this.addAbility(new SageofHoursAbility1(state));
	}
}
