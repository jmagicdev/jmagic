package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Shield of the Avatar")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class ShieldoftheAvatar extends Card
{

	public static final class ShieldoftheAvatarAbility0 extends StaticAbility
	{
		public ShieldoftheAvatarAbility0(GameState state)
		{
			super(state, "If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.");

			this.addEffectPart(replacementEffectPart(new PreventDamageEffect(this.game, "If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.")));
		}
	}

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
			int equippedCreature = source.getAttachedTo();

			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment assignment: damageAssignments)
				if(assignment.takerID == equippedCreature)
					ret.add(assignment);
			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			int critters = CREATURES_YOU_CONTROL.evaluate(this.game, this.getStaticSourceObject(this.game.actualState)).size();

			// keys are sourceIDs, values are how much damage we've prevented
			// from that source
			java.util.Map<Integer, Integer> countRemoved = new java.util.HashMap<>();
			java.util.Iterator<DamageAssignment> d = damageAssignments.iterator();
			while(d.hasNext())
			{
				DamageAssignment damage = d.next();
				if(!countRemoved.containsKey(damage.sourceID))
				{
					d.remove();
					countRemoved.put(damage.sourceID, 1);
				}
				else
				{
					int count = countRemoved.get(damage.sourceID);
					if(count < critters)
					{
						d.remove();
						countRemoved.put(damage.sourceID, count + 1);
					}
				}
			}

			return new java.util.LinkedList<EventFactory>();
		}
	}

	public ShieldoftheAvatar(GameState state)
	{
		super(state);

		// If a source would deal damage to equipped creature, prevent X of that
		// damage, where X is the number of creatures you control.
		this.addAbility(new ShieldoftheAvatarAbility0(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
