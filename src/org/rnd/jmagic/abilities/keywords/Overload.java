package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.94. Overload
 * 
 * 702.94a Overload is a keyword that represents two static abilities: one that
 * functions from any zone in which the spell with overload can be cast and
 * another that functions while the card is on the stack. Overload [cost] means
 * “You may choose to pay [cost] rather than pay this spell’s mana cost” and “If
 * you chose to pay this spell’s overload cost, change its text by replacing all
 * instances of the word ‘target’ with the word ‘each.’” Using the overload
 * ability follows the rules for paying alternative costs in rules 601.2b and
 * 601.2e–g.
 * 
 * 702.94b If a player chooses to pay the overload cost of a spell, that spell
 * won’t require any targets. It may affect objects that couldn’t be chosen as
 * legal targets if the spell were cast without its overload cost being paid.
 * 
 * 702.94c Overload’s second ability creates a text-changing effect. See rule
 * 612, “Text-Changing Effects.”
 */
public final class Overload extends Keyword
{
	public static final String OVERLOAD_MANA = "Overload";

	private final CostCollection cost;

	public Overload(GameState state, String mana)
	{
		this(state, new CostCollection(OVERLOAD_MANA, mana));
	}

	public Overload(GameState state, CostCollection cost)
	{
		super(state, "Overload " + cost.toString());
		this.cost = cost;
	}

	@Override
	public Overload create(Game game)
	{
		return new Overload(game.physicalState, new CostCollection(this.cost));
	}

	@Override
	public java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new OverloadAbility(this.state, this.cost));
		ret.add(new OverloadStatic(this.state));
		return ret;
	}

	public final static class OverloadAbility extends StaticAbility
	{
		private CostCollection cost;

		public OverloadAbility(GameState state, CostCollection cost)
		{
			super(state, "You may pay " + cost + " rather than pay this spell's mana cost if a player was dealt combat damage this turn by a source that, at the time it dealt that damage, was under your control and had any of this spell's creature types.");

			this.cost = cost;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(cost));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}

		@Override
		public OverloadAbility create(Game game)
		{
			return new OverloadAbility(game.physicalState, this.cost);
		}
	}

	public final static class OverloadStatic extends StaticAbility
	{
		public OverloadStatic(GameState state)
		{
			super(state, "If you chose to pay this spell's overload cost, change its text by replacing all instances of the word 'target' with the word 'each.'");

			ContinuousEffect.Part part = new ContinuousEffect.Part(OVERLOAD);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.canApply = WasOverloaded.instance(This.instance());
		}
	}

	/**
	 * @eparam OBJECT: the object(s) who get the ability
	 */
	public static final ContinuousEffectType OVERLOAD = new ContinuousEffectType("OVERLOAD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(ContinuousEffectType.Parameter.OBJECT).getAll(GameObject.class))
			{
				for(Mode mode: object.getModes())
					mode.targets.clear();
				object.getChosenTargets().clear();
			}
		}

		@Override
		public Layer layer()
		{
			return Layer.TEXT_CHANGE;
		}
	};

	public static class WasOverloaded extends SetGenerator
	{
		public static WasOverloaded instance(SetGenerator what)
		{
			return new WasOverloaded(what);
		}

		public static boolean get(GameObject object)
		{
			CostCollection alt = object.getAlternateCost();
			if(alt != null && alt.type.equals(Overload.OVERLOAD_MANA))
				return true;
			return false;
		}

		private SetGenerator what;

		private WasOverloaded(SetGenerator what)
		{
			this.what = what;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
				if(get(object))
					return NonEmpty.set;

			return Empty.set;
		}
	}
}
