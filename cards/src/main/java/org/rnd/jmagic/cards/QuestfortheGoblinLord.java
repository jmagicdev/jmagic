package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Quest for the Goblin Lord")
@Types({Type.ENCHANTMENT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class QuestfortheGoblinLord extends Card
{
	public static final class GoblinfallForQuestCounter extends EventTriggeredAbility
	{
		public GoblinfallForQuestCounter(GameState state)
		{
			super(state, "Whenever a Goblin enters the battlefield under your control, you may put a quest counter on Quest for the Goblin Lord.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.GOBLIN), You.instance(), false));
			this.addEffect(youMayPutAQuestCounterOnThis("Quest for the Goblin Lord"));
		}
	}

	public static final class BeastmasterPump extends StaticAbility
	{
		public BeastmasterPump(GameState state)
		{
			super(state, "As long as Quest for the Goblin Lord has five or more quest counters on it, creatures you control get +2/+0.");

			SetGenerator questCounters = CountersOn.instance(This.instance(), Counter.CounterType.QUEST);
			SetGenerator fiveCounters = Intersect.instance(Count.instance(questCounters), Between.instance(5, null));
			this.canApply = Both.instance(this.canApply, fiveCounters);

			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +2, +0));
		}
	}

	public QuestfortheGoblinLord(GameState state)
	{
		super(state);

		// Whenever a Goblin enters the battlefield under your control, you may
		// put a quest counter on Quest for the Goblin Lord.
		this.addAbility(new GoblinfallForQuestCounter(state));

		// As long as Quest for the Goblin Lord has five or more quest counters
		// on it, creatures you control get +2/+0.
		this.addAbility(new BeastmasterPump(state));
	}
}
