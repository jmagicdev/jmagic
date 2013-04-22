package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.53. Replicate
 * 
 * 702.53a Replicate is a keyword that represents two abilities. The first is a
 * static ability that functions while the spell with replicate is on the stack.
 * The second is a triggered ability that functions while the spell with
 * replicate is on the stack. "Replicate [cost]" means "As an additional cost to
 * cast this spell, you may pay [cost] any number of times" and "When you cast
 * this spell, if a replicate cost was paid for it, copy it for each time its
 * replicate cost was paid. If the spell has any targets, you may choose new
 * targets for any of the copies." Paying a spell's replicate cost follows the
 * rules for paying additional costs in rules 601.2b and 601.2e-g.
 * 
 * 702.53b If a spell has multiple instances of replicate, each is paid
 * separately and triggers based on the payments made for it, not any other
 * instance of replicate.
 */
public final class Replicate extends Keyword
{
	public static final String COST_TYPE = "Replicate";

	private final String costString;
	private final CostCollection costCollection;

	public Replicate(GameState state, String cost)
	{
		super(state, "Replicate " + cost);

		this.costString = cost;
		this.costCollection = new CostCollection(Replicate.COST_TYPE, true, cost);
	}

	@Override
	public Replicate create(Game game)
	{
		return new Replicate(game.physicalState, this.costString);
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new ReplicateTrigger(this.state, this.costCollection));
	}

	@Override
	public java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new ReplicateStatic(this.state, this.costCollection));
	}

	public static final class ReplicateStatic extends StaticAbility
	{
		private final CostCollection costCollection;

		public ReplicateStatic(GameState state, CostCollection costCollection)
		{
			super(state, "As an additional cost to cast this spell, you may pay " + costCollection + " any number of times.");

			this.costCollection = costCollection;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.OPTIONAL_ADDITIONAL_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(costCollection));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}

		@Override
		public ReplicateStatic create(Game game)
		{
			return new ReplicateStatic(game.physicalState, this.costCollection);
		}
	}

	public static final class ReplicateTrigger extends EventTriggeredAbility
	{
		private final CostCollection costCollection;

		public ReplicateTrigger(GameState state, CostCollection costCollection)
		{
			super(state, "When you cast this spell, if a replicate cost was paid for it, copy it for each time its replicate cost was paid. If the spell has any targets, you may choose new targets for any of the copies.");
			this.costCollection = costCollection;

			this.addPattern(whenYouCastThisSpell());
			this.triggersFromStack();

			SetGenerator timesReplicated = ThisSpellWasKicked.instance(costCollection);

			this.interveningIf = timesReplicated;

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy it for each time its replicate cost was paid. If the spell has any targets, you may choose new targets for any of the copies.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, timesReplicated);
			this.addEffect(factory);
		}

		@Override
		public ReplicateTrigger create(Game game)
		{
			return new ReplicateTrigger(game.physicalState, this.costCollection);
		}
	}
}
