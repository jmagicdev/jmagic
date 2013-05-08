package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Equip extends Keyword
{
	public static final String COST_TYPE = "Equip";

	private final CostCollection cost;

	/**
	 * Creates a new Equip whose equip cost is free;
	 * 
	 * @param state The {@link GameState} in which to create this ability.
	 */
	public Equip(GameState state)
	{
		this(state, "(0)");
	}

	/**
	 * Creates a new Equip whose equip cost is a mana cost.
	 * 
	 * @param state Game state in which to create this ability.
	 * @param cost The equip cost, with parentheses around each mana symbol.
	 */
	public Equip(GameState state, String cost)
	{
		super(state, "Equip " + cost);
		this.cost = new CostCollection(COST_TYPE, cost);
	}

	public Equip(GameState state, CostCollection cost)
	{
		super(state, "Equip" + (cost.events.isEmpty() ? " " : "\u2014") + cost + ".");
		this.cost = cost;
	}

	private Equip(GameState state, String name, CostCollection cost)
	{
		super(state, name);
		this.cost = cost;
	}

	@Override
	public Equip create(Game game)
	{
		return new Equip(game.physicalState, this.getName(), this.cost);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new EquipAbility(this.state, this.cost));

		return ret;
	}

	public static final class EquipAbility extends ActivatedAbility
	{
		private final CostCollection cost;

		public EquipAbility(GameState state, CostCollection cost)
		{
			super(state, cost + ": Attach to target creature you control. Equip only as a sorcery.");

			this.cost = cost;

			this.setManaCost(cost.manaCost);
			for(EventFactory otherCost: cost.events)
				this.addCost(otherCost);

			Target target = this.addTarget(Intersect.instance(ControlledBy.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS)), CreaturePermanents.instance()), "target creature you control");
			this.addEffect(attach(ABILITY_SOURCE_OF_THIS, targetedBy(target), "Attach to target creature you control."));

			this.activateOnlyAtSorcerySpeed();
		}

		@Override
		public EquipAbility create(Game game)
		{
			return new EquipAbility(game.physicalState, this.cost);
		}
	}
}
