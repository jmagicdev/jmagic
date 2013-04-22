package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Quest for Renewal")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class QuestforRenewal extends Card
{
	public static final class QuestforRenewalAbility0 extends EventTriggeredAbility
	{
		public QuestforRenewalAbility0(GameState state)
		{
			super(state, "Whenever a creature you control becomes tapped, you may put a quest counter on Quest for Renewal.");

			SimpleEventPattern tap = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			tap.put(EventType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addPattern(tap);

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for Renewal"));
		}
	}

	public static final class UntapYourCreaturesSometimes extends StaticAbility
	{
		public UntapYourCreaturesSometimes(GameState state)
		{
			super(state, "As long as there are four or more quest counters on Quest for Renewal, untap all creatures you control during each other player's untap step.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(untap(CREATURES_YOU_CONTROL, "Untap all permanents you control.")));
			this.addEffectPart(part);

			SetGenerator otherPlayersUntapSteps = RelativeComplement.instance(OwnerOf.instance(CurrentStep.instance()), You.instance());
			SetGenerator hasFourQuestCounters = Intersect.instance(Between.instance(4, null), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.QUEST)));
			this.canApply = Both.instance(this.canApply, Both.instance(otherPlayersUntapSteps, hasFourQuestCounters));
		}
	}

	public QuestforRenewal(GameState state)
	{
		super(state);

		// Whenever a creature you control becomes tapped, you may put a quest
		// counter on Quest for Renewal.
		this.addAbility(new QuestforRenewalAbility0(state));

		// As long as there are four or more quest counters on Quest for
		// Renewal, untap all creatures you control during each other player's
		// untap step.
		this.addAbility(new UntapYourCreaturesSometimes(state));
	}
}
