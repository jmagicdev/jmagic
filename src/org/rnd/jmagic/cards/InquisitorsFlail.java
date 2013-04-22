package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Inquisitor's Flail")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class InquisitorsFlail extends Card
{
	public static final class InquisitorsFlailAbility0 extends StaticAbility
	{
		public static final class DoubleDamageEffect extends DamageReplacementEffect
		{
			public DoubleDamageEffect(Game game)
			{
				super(game, "If equipped creature would deal combat damage, it deals double that damage instead.");
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch batch = new DamageAssignment.Batch();

				for(DamageAssignment assignment: damageAssignments)
				{
					if(!assignment.isCombatDamage)
						continue;
					Identified effectSource = this.getStaticSourceObject(context.state);
					GameObject source = context.state.get(assignment.sourceID);
					if(!source.getAttachments().contains(effectSource.ID))
						continue;
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

		public InquisitorsFlailAbility0(GameState state)
		{
			super(state, "If equipped creature would deal combat damage, it deals double that damage instead.");

			this.addEffectPart(replacementEffectPart(new DoubleDamageEffect(this.game)));
		}
	}

	public static final class InquisitorsFlailAbility1 extends StaticAbility
	{
		public static final class DoubleDamageEffect extends DamageReplacementEffect
		{
			public DoubleDamageEffect(Game game)
			{
				super(game, "If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead.");
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch batch = new DamageAssignment.Batch();

				for(DamageAssignment assignment: damageAssignments)
				{
					if(!assignment.isCombatDamage)
						continue;
					Identified effectSource = this.getStaticSourceObject(context.state);
					Identified taker = context.state.get(assignment.takerID);
					if(taker.isGameObject() && !((GameObject)taker).getAttachments().contains(effectSource.ID))
						continue;
					GameObject source = context.state.get(assignment.sourceID);
					if(!source.getTypes().contains(Type.CREATURE))
						continue;
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

		public InquisitorsFlailAbility1(GameState state)
		{
			super(state, "If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead.");

			this.addEffectPart(replacementEffectPart(new DoubleDamageEffect(this.game)));
		}
	}

	public InquisitorsFlail(GameState state)
	{
		super(state);

		// If equipped creature would deal combat damage, it deals double that
		// damage instead.
		this.addAbility(new InquisitorsFlailAbility0(state));

		// If another creature would deal combat damage to equipped creature, it
		// deals double that damage to equipped creature instead.
		this.addAbility(new InquisitorsFlailAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
