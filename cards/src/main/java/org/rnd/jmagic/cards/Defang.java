package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Defang")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Defang extends Card
{
	public static final class DefangAbility1 extends StaticAbility
	{
		public static final class DefangEffect extends DamageReplacementEffect
		{
			public DefangEffect(Game game, String name)
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

		public DefangAbility1(GameState state)
		{
			super(state, "Prevent all damage that would be dealt by enchanted creature.");

			DamageReplacementEffect damageReplacementEffect = new DefangEffect(this.game, "Prevent all damage that would be dealt by enchanted creature.");

			this.addEffectPart(replacementEffectPart(damageReplacementEffect));
		}
	}

	public Defang(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Prevent all damage that would be dealt by enchanted creature.
		this.addAbility(new DefangAbility1(state));
	}
}
