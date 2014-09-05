package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shielded Passage")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ShieldedPassage extends Card
{
	private static final class Prevent extends DamageReplacementEffect
	{
		private SetGenerator target;

		private Prevent(Game game, String name, SetGenerator target)
		{
			super(game, name);
			this.target = target;
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			Identified ability = this.getSourceObject(context.game.actualState);

			java.util.Set<Integer> ids = new java.util.HashSet<Integer>();
			for(Identified identified: this.target.evaluate(context.state, ability).getAll(Identified.class))
				ids.add(identified.ID);

			for(DamageAssignment assignment: damageAssignments)
				if(ids.contains(assignment.takerID))
					ret.add(assignment);

			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return java.util.Collections.emptyList();
		}
	}

	public ShieldedPassage(GameState state)
	{
		super(state);

		// Prevent all damage that would be dealt to target creature this turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new Prevent(state.game, "Prevent all damage that would be dealt to target creature", target)));
		this.addEffect(createFloatingEffect("Prevent all damage that would be dealt to target creature this turn.", part));
	}
}
