package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindbreak Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class MindbreakTrap extends Card
{
	/**
	 * Keys are playerIDs, values are number of spells cast by that player this
	 * turn.
	 */
	public static final class SpellCount extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.Map<Integer, Integer> counts = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.counts);

		@Override
		public SpellCount clone()
		{
			SpellCount ret = (SpellCount)super.clone();
			ret.counts = new java.util.HashMap<Integer, Integer>(this.counts);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.counts);
			return ret;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type == EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
				if(event.getResult(state).getOne(GameObject.class).isSpell())
					return true;
			return false;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Player caster = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);
			if(this.counts.containsKey(caster.ID))
				this.counts.put(caster.ID, this.counts.get(caster.ID) + 1);
			else
				this.counts.put(caster.ID, 1);
		}

		@Override
		protected void reset()
		{
			this.counts.clear();
		}
	}

	/**
	 * Evaluates to the highest spell count for the given players. For example,
	 * if A cast 2 spells, B cast 3 spells, and C hasn't cast spells, and {A, B,
	 * C} are given to this set generator, it will evaluate to {3}.
	 * 
	 * Requires the org.rnd.jmagic.engine.SpellCount flag.
	 */
	private static class MaximumPlayerSpellCount extends SetGenerator
	{
		private SetGenerator who;

		public static MaximumPlayerSpellCount instance(SetGenerator who)
		{
			return new MaximumPlayerSpellCount(who);
		}

		private MaximumPlayerSpellCount(SetGenerator who)
		{
			this.who = who;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			java.util.Map<Integer, Integer> flagValue = state.getTracker(SpellCount.class).getValue(state);
			Set players = this.who.evaluate(state, thisObject);

			int count = 0;
			for(Player player: players.getAll(Player.class))
				if(flagValue.containsKey(player.ID))
				{
					int value = flagValue.get(player.ID);
					if(value > count)
						count = value;
				}
			return new Set(count);
		}
	}

	public MindbreakTrap(GameState state)
	{
		super(state);

		state.ensureTracker(new SpellCount());

		// If an opponent cast three or more spells this turn, you may pay (0)
		// rather than pay Mindbreak Trap's mana cost.
		SetGenerator maxOppSpellCount = MaximumPlayerSpellCount.instance(OpponentsOf.instance(You.instance()));
		SetGenerator threeOrMore = Between.instance(3, null);
		SetGenerator trapCondition = Intersect.instance(maxOppSpellCount, threeOrMore);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent cast three or more spells this turn", "(0)"));

		// Exile any number of target spells.
		Target target = this.addTarget(Spells.instance(), "target spell");
		target.setNumber(0, null);
		this.addEffect(exile(targetedBy(target), "Exile any number of target spells."));
	}
}
