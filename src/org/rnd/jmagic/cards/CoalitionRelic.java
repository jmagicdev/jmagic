package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Coalition Relic")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({})
public final class CoalitionRelic extends Card
{
	public static final class CoalitionRelicAbility1 extends ActivatedAbility
	{
		public CoalitionRelicAbility1(GameState state)
		{
			super(state, "(T): Put a charge counter on Coalition Relic.");
			this.costsTap = true;
			this.addEffect(putCountersOnThis(1, Counter.CounterType.CHARGE, "Coalition Relic"));
		}
	}

	/**
	 * @eparam SOURCE: coalition relic
	 */
	public static final EventType COALITION_RELIC_EVENT = new EventType("COALITION_RELIC_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject coalitionRelic = parameters.get(Parameter.SOURCE).getOne(GameObject.class);
			int num = CountersOn.get(coalitionRelic, Counter.CounterType.CHARGE).size();
			Event removeCounters = removeCountersFromThis(num, Counter.CounterType.CHARGE, "Coalition Relic").createEvent(game, event.getSource());
			removeCounters.perform(event, true);

			int newNum = CountersOn.get(coalitionRelic.getActual(), Counter.CounterType.CHARGE).size();
			StringBuilder mana = new StringBuilder();
			for(int i = newNum; i < num; i++)
				mana.append("(WUBRG)");
			addManaToYourManaPoolFromAbility(mana.toString()).createEvent(game, event.getSource()).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class ChargeCounterMana extends EventTriggeredAbility
	{
		public ChargeCounterMana(GameState state)
		{
			super(state, "At the beginning of your precombat main phase, remove all charge counters from Coalition Relic. Add one mana of any color to your mana pool for each counter removed this way.");

			SimpleEventPattern beginPrecombatMain = new SimpleEventPattern(EventType.BEGIN_PHASE);
			beginPrecombatMain.put(EventType.Parameter.PHASE, PhaseOf.instance(You.instance(), Phase.PhaseType.PRECOMBAT_MAIN));
			this.addPattern(beginPrecombatMain);

			EventFactory effect = new EventFactory(COALITION_RELIC_EVENT, "At the beginning of your precombat main phase, remove all charge counters from Coalition Relic. Add one mana of any color to your mana pool for each counter removed this way.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			this.addEffect(effect);
		}
	}

	public CoalitionRelic(GameState state)
	{
		super(state);

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));

		// (T): Put a charge counter on Coalition Relic.
		this.addAbility(new CoalitionRelicAbility1(state));

		// At the beginning of your precombat main phase, remove all charge
		// counters from Coalition Relic. Add one mana of any color to your mana
		// pool for each counter removed this way.
		this.addAbility(new ChargeCounterMana(state));
	}
}
