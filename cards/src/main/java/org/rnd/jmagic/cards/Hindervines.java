package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Hindervines")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class Hindervines extends Card
{
	public static final class HindervinesEffect extends DamageReplacementEffect
	{
		public HindervinesEffect(Game game, String name)
		{
			super(game, name);
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			outer: for(DamageAssignment assignment: damageAssignments)
				if(assignment.isCombatDamage)
				{
					for(Counter counter: context.state.<GameObject>get(assignment.sourceID).counters)
						if(counter.getType().equals(Counter.CounterType.PLUS_ONE_PLUS_ONE))
							continue outer;
					ret.add(assignment);
				}
			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return java.util.Collections.emptyList();
		}
	}

	public Hindervines(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt this turn by creatures
		// with no +1/+1 counters on them.
		DamageReplacementEffect replacement = new HindervinesEffect(state.game, "Prevent all combat damage that would be dealt this turn by attacking by creatures with no +1/+1 counters on them.");

		this.addEffect(createFloatingReplacement(replacement, "Prevent all combat damage that would be dealt this turn."));
	}
}
