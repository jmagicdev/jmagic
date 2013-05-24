package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Heart of Light")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class HeartofLight extends Card
{
	// Prevent all damage that would be dealt to and dealt by enchanted
	// creature.
	public static final class PreventDamage extends StaticAbility
	{
		public static final class PreventDamageEffect extends DamageReplacementEffect
		{
			public PreventDamageEffect(Game game, String name)
			{
				super(game, name);
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameObject source = (GameObject)this.getStaticSourceObject(context.game.actualState);
				int enchantedCreature = source.getAttachedTo();

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment assignment: damageAssignments)
					if(assignment.sourceID == enchantedCreature || assignment.takerID == enchantedCreature)
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

		public PreventDamage(GameState state)
		{
			super(state, "Prevent all damage that would be dealt to and dealt by enchanted creature");

			DamageReplacementEffect damageReplacementEffect = new PreventDamageEffect(this.game, "Prevent all damage that would be dealt to and dealt by enchanted creature.");

			this.addEffectPart(replacementEffectPart(damageReplacementEffect));
		}
	}

	public HeartofLight(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new PreventDamage(state));
	}
}
