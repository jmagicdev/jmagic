package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

public abstract class Absorb extends Keyword
{
	public final static class AbsorbAbility extends StaticAbility
	{
		public static class AbsorbEffect extends DamageReplacementEffect
		{
			private final int N;

			public AbsorbEffect(Game game, int N)
			{
				super(game, "If a source would deal damage to this creature, prevent " + N + " of that damage.");
				this.makePreventionEffect();
				this.N = N;
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				for(DamageAssignment damage: damageAssignments)
					if(damage.takerID == this.getStaticSourceObject(context.game.actualState).ID)
						ret.add(damage);

				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				// keys are source IDs, values are damage to prevent from that
				// source
				java.util.Map<Integer, DamageAssignment.Batch> toPrevent = new java.util.HashMap<Integer, DamageAssignment.Batch>();

				// Get the first N damage assignments, or, if there are less
				// than N, get them all
				for(DamageAssignment assignment: damageAssignments)
				{
					if(!toPrevent.containsKey(assignment.sourceID))
						toPrevent.put(assignment.sourceID, new DamageAssignment.Batch());
					DamageAssignment.Batch fromSameSource = toPrevent.get(assignment.sourceID);
					if(fromSameSource.size() < this.N)
						fromSameSource.add(assignment);
				}

				for(DamageAssignment.Batch prevent: toPrevent.values())
					damageAssignments.removeAll(prevent);
				return new java.util.LinkedList<EventFactory>();
			}
		}

		private final int N;

		public AbsorbAbility(GameState state, int N)
		{
			super(state, "If a source would deal damage to this creature, prevent " + N + " of that damage.");
			this.N = N;
			this.addEffectPart(replacementEffectPart(new AbsorbEffect(this.game, this.N)));
		}

		@Override
		public AbsorbAbility create(Game game)
		{
			return new AbsorbAbility(game.physicalState, this.N);
		}
	}

	protected final int N;

	public Absorb(GameState state, int N)
	{
		super(state, "Absorb " + N);
		this.N = N;
	}

	public static final class Final extends Absorb
	{
		public Final(GameState state, int N)
		{
			super(state, N);
		}

		@Override
		public Absorb.Final create(Game game)
		{
			return new Final(game.physicalState, this.N);
		}
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new AbsorbAbility(this.state, this.N));
		return ret;
	}
}
