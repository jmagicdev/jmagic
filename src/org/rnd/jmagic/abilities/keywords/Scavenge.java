package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Scavenge extends Keyword
{
	public static final String COST_TYPE = "Scavenge";

	private CostCollection cost;

	public Scavenge(GameState state, String manaCost)
	{
		super(state, "Scavenge " + manaCost);
		this.cost = new CostCollection(COST_TYPE, manaCost);
	}

	public Scavenge(GameState state, CostCollection cost)
	{
		super(state, "Scavenge" + (cost.events.isEmpty() ? " " : "\u2014") + cost);
		this.cost = cost;
	}

	@Override
	public Scavenge create(Game game)
	{
		return new Scavenge(game.physicalState, this.cost);
	}

	@Override
	protected final java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.LinkedList<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new ExileForCounters(this.state, this.cost));
		return ret;
	}

	public static final class ExileForCounters extends ActivatedAbility
	{
		private CostCollection cost;

		public ExileForCounters(GameState state, CostCollection cost)
		{
			super(state, cost + ", Exile this card from your graveyard: Put a number of +1/+1 counters equal to this card’s power on target creature. Activate this ability only any time you could cast a sorcery.");
			this.cost = cost;

			this.setManaCost(cost.manaCost);
			for(EventFactory factory: cost.events)
				this.addCost(factory);

			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile this card from your graveyard."));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(PowerOf.instance(ABILITY_SOURCE_OF_THIS), Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a number of +1/+1 counters equal to this card’s power on target creature."));

			this.activateOnlyFromGraveyard();
			this.activateOnlyAtSorcerySpeed();
		}

		@Override
		public ExileForCounters create(Game game)
		{
			return new ExileForCounters(game.physicalState, this.cost);
		}
	}
}
