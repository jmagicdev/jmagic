package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Beastmaster Ascension")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class BeastmasterAscension extends Card
{
	public static final class AttackForQuestCounter extends EventTriggeredAbility
	{
		public AttackForQuestCounter(GameState state)
		{
			super(state, "Whenever a creature you control attacks, you may put a quest counter on Beastmaster Ascension.");

			SimpleEventPattern attackPattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			attackPattern.put(EventType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addPattern(attackPattern);

			this.addEffect(youMayPutAQuestCounterOnThis("Beastmaster Ascension"));
		}
	}

	public static final class BeastmasterPump extends StaticAbility
	{
		public BeastmasterPump(GameState state)
		{
			super(state, "As long as Beastmaster Ascension has seven or more quest counters on it, creatures you control get +5/+5.");

			SetGenerator questCounters = CountersOn.instance(This.instance(), Counter.CounterType.QUEST);
			SetGenerator sevenCounters = Intersect.instance(Count.instance(questCounters), Between.instance(7, null));
			this.canApply = Both.instance(this.canApply, sevenCounters);

			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +5, +5));
		}
	}

	public BeastmasterAscension(GameState state)
	{
		super(state);

		// Whenever a creature you control attacks, you may put a quest counter
		// on Beastmaster Ascension.
		this.addAbility(new AttackForQuestCounter(state));

		// As long as Beastmaster Ascension has seven or more quest counters on
		// it, creatures you control get +5/+5.
		this.addAbility(new BeastmasterPump(state));
	}
}
