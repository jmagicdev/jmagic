package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Safe Passage")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class SafePassage extends Card
{
	public static final class SafePassageReplacement extends DamageReplacementEffect
	{
		public SafePassageReplacement(Game game)
		{
			super(game, "Prevent all damage that would be dealt to you and creatures you control");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			Set valid = Union.instance(You.instance(), CREATURES_YOU_CONTROL).evaluate(context.state, this.getSourceObject(context.state));

			for(DamageAssignment damage: damageAssignments)
				if(valid.contains(context.state.get(damage.takerID)))
					ret.add(damage);

			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return new java.util.LinkedList<EventFactory>();
		}
	}

	public SafePassage(GameState state)
	{
		super(state);

		DamageReplacementEffect replacement = new SafePassageReplacement(state.game);
		this.addEffect(createFloatingReplacement(replacement, "Prevent all damage that would be dealt to you and creatures you control this turn."));
	}
}
