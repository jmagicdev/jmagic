package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quest for the Gemblades")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class QuestfortheGemblades extends Card
{
	public static final class DamageForQuestCounter extends EventTriggeredAbility
	{
		public DamageForQuestCounter(GameState state)
		{
			super(state, "Whenever a creature you control deals combat damage to a creature, you may put a quest counter on Quest for the Gemblades.");

			this.addPattern(whenDealsCombatDamageToACreature(CREATURES_YOU_CONTROL));

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for the Gemblades"));
		}
	}

	public static final class QuestCounterCounters extends ActivatedAbility
	{
		public QuestCounterCounters(GameState state)
		{
			super(state, "Remove a quest counter from Quest for the Gemblades and sacrifice it: Put four +1/+1 counters on target creature.");
			this.addCost(removeCountersFromThis(1, Counter.CounterType.QUEST, "Quest for the Gemblades"));

			this.addCost(sacrificeThis("Quest for the Gemblades"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(putCounters(4, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put four +1/+1 counters on target creature."));
		}
	}

	public QuestfortheGemblades(GameState state)
	{
		super(state);

		// Whenever a creature you control deals combat damage to a creature,
		// you may put a quest counter on Quest for the Gemblades.
		this.addAbility(new DamageForQuestCounter(state));

		// Remove a quest counter from Quest for the Gemblades and sacrifice it:
		// Put four +1/+1 counters on target creature.
		this.addAbility(new QuestCounterCounters(state));
	}
}
