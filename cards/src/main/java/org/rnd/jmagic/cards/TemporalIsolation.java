package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Temporal Isolation")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class TemporalIsolation extends Card
{
	public static final class TemporalIsolationAbility2 extends StaticAbility
	{
		public TemporalIsolationAbility2(GameState state)
		{
			super(state, "Enchanted creature has shadow.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Shadow.class));
		}
	}

	public static final class PreventDamage extends StaticAbility
	{
		public static final class TemporalIsolationEffect extends DamageReplacementEffect
		{
			public TemporalIsolationEffect(Game game, String name)
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
					if(assignment.sourceID == enchantedCreature)
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
			super(state, "Prevent all damage that would be dealt by enchanted creature.");

			DamageReplacementEffect damageReplacementEffect = new TemporalIsolationEffect(this.game, "Prevent all damage that would be dealt by enchanted creature.");

			this.addEffectPart(replacementEffectPart(damageReplacementEffect));
		}
	}

	public TemporalIsolation(GameState state)
	{
		super(state);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has shadow.
		this.addAbility(new TemporalIsolationAbility2(state));

		// Prevent all damage that would be dealt by enchanted creature.
		this.addAbility(new PreventDamage(state));
	}
}
