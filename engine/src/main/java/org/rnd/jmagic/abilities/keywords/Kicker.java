package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/** Represents Kicker and Multikicker. */
public final class Kicker extends Keyword
{
	private static String generateString(CostCollection... costs)
	{
		StringBuilder ret = new StringBuilder();

		if(costs.length > 0)
		{
			ret.append(costs[0]);

			for(int i = 1; i < costs.length; i++)
				ret.append(" and/or " + costs[i]);
		}

		return ret.toString();
	}

	public static final String COST_TYPE = "Kicker";
	public final CostCollection[] costCollections;

	public Kicker(GameState state, CostCollection... costs)
	{
		super(state, (costs[0].allowMultiples ? "Multikicker " : "Kicker ") + generateString(costs));
		this.costCollections = costs;
	}

	public Kicker(GameState state, String manaCost)
	{
		this(state, false, manaCost);
	}

	public Kicker(GameState state, boolean multi, String manaCost)
	{
		this(state, new CostCollection(COST_TYPE, multi, manaCost));
	}

	@Override
	public Kicker create(Game game)
	{
		return new Kicker(game.physicalState, this.costCollections);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new KickerCost(this.state, this.costCollections));
	}

	public static final class KickerCost extends StaticAbility
	{
		public final CostCollection[] costCollections;

		public KickerCost(GameState state, CostCollection... costs)
		{
			super(state, "You may pay an additional " + generateString(costs) + (costs[0].allowMultiples ? " any number of times" : "") + " as you cast this spell.");
			this.costCollections = costs;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.OPTIONAL_ADDITIONAL_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance((Object[])costs));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}

		@Override
		public KickerCost create(Game game)
		{
			return new KickerCost(game.physicalState, this.costCollections);
		}
	}
}
