package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inferno Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class InfernoTrap extends Card
{
	/**
	 * Evaluates to all creatures that dealt damage to any of the given objects
	 * or players this turn.
	 */
	public static final class CreaturesThatDealtDamageToThisTurn extends SetGenerator
	{
		public static final class WhoDealtDamageTracker extends Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
		{
			private java.util.HashMap<Integer, java.util.Set<Integer>> ids = new java.util.HashMap<Integer, java.util.Set<Integer>>();
			private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.ids);

			@SuppressWarnings("unchecked")
			@Override
			public WhoDealtDamageTracker clone()
			{
				WhoDealtDamageTracker ret = (WhoDealtDamageTracker)super.clone();
				ret.ids = (java.util.HashMap<Integer, java.util.Set<Integer>>)this.ids.clone();
				ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.ids);
				return ret;
			}

			@Override
			protected java.util.Map<Integer, java.util.Set<Integer>> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				return event.type == EventType.DEAL_DAMAGE_BATCHES;
			}

			@Override
			protected void reset()
			{
				this.ids.clear();
			}

			@Override
			protected void update(GameState state, Event event)
			{
				java.util.Set<DamageAssignment> assignments = event.parameters.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(DamageAssignment.class);
				for(DamageAssignment assignment: assignments)
					if(state.<GameObject>get(assignment.sourceID).getTypes().contains(Type.CREATURE))
					{
						if(!this.ids.containsKey(assignment.takerID))
							this.ids.put(assignment.takerID, new java.util.HashSet<Integer>());
						this.ids.get(assignment.takerID).add(assignment.sourceID);
					}
			}

		}

		public static CreaturesThatDealtDamageToThisTurn instance(SetGenerator what)
		{
			return new CreaturesThatDealtDamageToThisTurn(what);
		}

		private final SetGenerator what;

		private CreaturesThatDealtDamageToThisTurn(SetGenerator what)
		{
			this.what = what;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			WhoDealtDamageTracker flag = state.getTracker(WhoDealtDamageTracker.class);

			java.util.Map<Integer, java.util.Set<Integer>> flagValue = flag.getValue(state);

			java.util.Set<Integer> ids = new java.util.HashSet<Integer>();
			Set what = this.what.evaluate(state, thisObject);
			for(GameObject taker: what.getAll(GameObject.class))
			{
				java.util.Set<Integer> dealtDamageToThis = flagValue.get(taker.ID);
				ids.addAll(dealtDamageToThis);
			}
			return IdentifiedWithID.instance(ids).evaluate(state, null);
		}
	}

	public InfernoTrap(GameState state)
	{
		super(state);

		// If you've been dealt damage by two or more creatures this turn, you
		// may pay (R) rather than pay Inferno Trap's mana cost.
		state.ensureTracker(new CreaturesThatDealtDamageToThisTurn.WhoDealtDamageTracker());
		SetGenerator condition = Intersect.instance(Count.instance(CreaturesThatDealtDamageToThisTurn.instance(You.instance())), Between.instance(2, null));
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), condition, "If you've been dealt damage by two or more creatures this turn", "(R)"));

		// Inferno Trap deals 4 damage to target creature.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Inferno Trap deals 4 damage to target creature."));
	}
}
