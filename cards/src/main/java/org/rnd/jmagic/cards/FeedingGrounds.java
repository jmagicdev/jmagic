package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Feeding Grounds")
@Types({Type.PLANE})
@SubTypes({SubType.MURAGANDA})
@ColorIdentity({})
public final class FeedingGrounds extends Card
{
	public static final class CheapRed extends StaticAbility
	{
		public CheapRed(GameState state)
		{
			super(state, "Red spells cost (1) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.RED), Spells.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("1")));
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class CheapGreen extends StaticAbility
	{
		public CheapGreen(GameState state)
		{
			super(state, "Green spells cost (1) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.GREEN), Spells.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("1")));
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class DoubleMintSteroids extends EventTriggeredAbility
	{
		public DoubleMintSteroids(GameState state)
		{
			super(state, "Whenever you roll (C), put X +1/+1 counters on target creature, where X is that creature's converted mana cost.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(putCounters(ConvertedManaCostOf.instance(targetedBy(target)), Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put X +1/+1 counters on target creature, where X is that creature's converted mana cost."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public FeedingGrounds(GameState state)
	{
		super(state);

		this.addAbility(new CheapRed(state));

		this.addAbility(new CheapGreen(state));

		this.addAbility(new DoubleMintSteroids(state));
	}
}
