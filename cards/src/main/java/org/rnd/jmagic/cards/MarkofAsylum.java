package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mark of Asylum")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class MarkofAsylum extends Card
{
	public static final class MarkOfAsylumReplacement extends DamageReplacementEffect
	{
		public MarkOfAsylumReplacement(Game game)
		{
			super(game, "Prevent all noncombat damage that would be dealt to creatures you control");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			Set valid = CREATURES_YOU_CONTROL.evaluate(context.state, this.getSourceObject(context.state));

			for(DamageAssignment damage: damageAssignments)
				if(!damage.isCombatDamage && valid.contains(context.state.get(damage.takerID)))
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

	public static final class PreventNoncombatDamage extends StaticAbility
	{
		public PreventNoncombatDamage(GameState state)
		{
			super(state, "Prevent all noncombat damage that would be dealt to creatures you control.");

			DamageReplacementEffect replacement = new MarkOfAsylumReplacement(state.game);
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public MarkofAsylum(GameState state)
	{
		super(state);

		// Prevent all noncombat damage that would be dealt to creatures you
		// control.
		this.addAbility(new PreventNoncombatDamage(state));
	}
}
