package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Affinity extends Keyword
{
	private final SetGenerator whatFor;
	private final String whatForText;

	public Affinity(GameState state, SetGenerator whatFor, String whatForText)
	{
		super(state, "Affinity for " + whatForText);
		this.whatFor = whatFor;
		this.whatForText = whatForText;
	}

	public static final class AffinityAbility extends StaticAbility
	{
		private final SetGenerator whatFor;
		private final String whatForText;

		public AffinityAbility(GameState state, SetGenerator whatFor, String whatForText)
		{
			super(state, "This spell costs (1) less to cast for each " + whatForText + " you control.");

			this.whatFor = whatFor;
			this.whatForText = whatForText;

			SetGenerator stuffYouControl = Intersect.instance(whatFor, ControlledBy.instance(You.instance()));

			ContinuousEffect.Part reduction = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			reduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			reduction.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("1")));
			reduction.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(stuffYouControl));
			this.addEffectPart(reduction);

			this.canApply = THIS_IS_ON_THE_STACK;
		}

		@Override
		public AffinityAbility create(Game game)
		{
			return new AffinityAbility(game.physicalState, this.whatFor, this.whatForText);
		}
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new AffinityAbility(this.state, this.whatFor, this.whatForText));
	}

	public static final class ForArtifacts extends Affinity
	{
		public ForArtifacts(GameState state)
		{
			super(state, ArtifactPermanents.instance(), "artifacts");
		}
	}

	public static final class ForForests extends Affinity
	{
		public ForForests(GameState state)
		{
			super(state, HasSubType.instance(SubType.FOREST), "forests");
		}
	}
}