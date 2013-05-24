package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class Cycling extends CyclingBase
{
	public static final String COST_TYPE = "Cycling";

	private CostCollection cost;

	public Cycling(GameState state, String manaCost)
	{
		super(state, "Cycling " + manaCost);
		this.cost = new CostCollection(COST_TYPE, manaCost);
	}

	public Cycling(GameState state, CostCollection cost)
	{
		super(state, "Cycling" + (cost.events.isEmpty() ? " " : "\u2014") + cost);
		this.cost = cost;
	}

	@Override
	public Cycling create(Game game)
	{
		return new Cycling(game.physicalState, this.cost);
	}

	@Override
	protected java.util.List<CyclingAbilityBase<?>> createCyclingAbilities(GameState state)
	{
		java.util.List<CyclingAbilityBase<?>> ret = new java.util.LinkedList<CyclingAbilityBase<?>>();

		ret.add(new CyclingAbility(state, this));

		return ret;
	}

	@Override
	protected CostCollection getCost()
	{
		return this.cost;
	}

	public static final class CyclingAbility extends CyclingAbilityBase<Cycling>
	{
		public CyclingAbility(GameState state, Cycling parent)
		{
			super(state, parent, parent.getCost() + ", discard this: Draw a card");
			this.addEffect(drawACard());
		}

		@Override
		public CyclingAbility create(Game game)
		{
			return new CyclingAbility(game.physicalState, game.physicalState.<Cycling>get(this.parentID));
		}
	}
}
