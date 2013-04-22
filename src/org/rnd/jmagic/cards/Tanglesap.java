package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Tanglesap")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Tanglesap extends Card
{
	public static final class PreventCombatDamage extends DamageReplacementEffect
	{
		public PreventCombatDamage(Game game)
		{
			super(game, "Prevent all combat damage that would be dealt this turn by creatures without trample");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment assignment: damageAssignments)
				if(assignment.isCombatDamage && !context.state.<GameObject>get(assignment.sourceID).hasAbility(org.rnd.jmagic.abilities.keywords.Trample.class))
					ret.add(assignment);
			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return new java.util.LinkedList<EventFactory>();
		}
	}

	public Tanglesap(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt this turn by creatures
		// without trample.
		this.addEffect(createFloatingReplacement(new PreventCombatDamage(this.game), "Prevent all combat damage that would be dealt this turn."));

	}
}
