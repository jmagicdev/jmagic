package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Harmless Assault")
@Types({Type.INSTANT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class HarmlessAssault extends Card
{
	public static final class HarmlessAssaultEffect extends DamageReplacementEffect
	{
		public HarmlessAssaultEffect(Game game, String name)
		{
			super(game, name);
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment assignment: damageAssignments)
				if(assignment.isCombatDamage && context.state.<GameObject>get(assignment.sourceID).getAttackingID() != -1)
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

	public HarmlessAssault(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt this turn by attacking
		// creatures.
		DamageReplacementEffect replacement = new HarmlessAssaultEffect(state.game, "Prevent all combat damage that would be dealt this turn by attacking creatures.");

		this.addEffect(createFloatingReplacement(replacement, "Prevent all combat damage that would be dealt this turn."));

	}
}
