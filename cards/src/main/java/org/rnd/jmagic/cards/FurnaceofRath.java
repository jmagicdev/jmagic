package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Furnace of Rath")
@Types({Type.ENCHANTMENT})
@ManaCost("1RRR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class FurnaceofRath extends Card
{
	public static final class DoubleDamageEffect extends DamageReplacementEffect
	{
		public DoubleDamageEffect(Game game)
		{
			super(game, "If a source would deal damage to a creature or player, it deals double that damage to that creature or player instead.");
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch batch = new DamageAssignment.Batch();

			for(DamageAssignment assignment: damageAssignments)
			{
				Identified taker = context.state.get(assignment.takerID);
				boolean dealingToPlayer = taker.isPlayer();
				boolean dealingToCreature = (taker.isGameObject()) && (((GameObject)taker).getTypes().contains(Type.CREATURE));
				if(dealingToPlayer || dealingToCreature)
					batch.add(assignment);
			}

			return batch;
		}

		@Override
		public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
		{
			java.util.Collection<DamageAssignment> duplicates = new java.util.LinkedList<DamageAssignment>();
			for(DamageAssignment assignment: damageAssignments)
				duplicates.add(new DamageAssignment(assignment));
			damageAssignments.addAll(duplicates);

			return new java.util.LinkedList<EventFactory>();
		}
	}

	public static final class DoubleDamageAbility extends StaticAbility
	{
		public DoubleDamageAbility(GameState state)
		{
			super(state, "If a source would deal damage to a creature or player, it deals double that damage to that creature or player instead.");

			this.addEffectPart(replacementEffectPart(new DoubleDamageEffect(this.game)));
		}
	}

	public FurnaceofRath(GameState state)
	{
		super(state);

		this.addAbility(new DoubleDamageAbility(state));
	}
}
