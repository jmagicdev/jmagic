package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Magebane Armor")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class MagebaneArmor extends Card
{
	public static final class Magebane extends StaticAbility
	{
		public Magebane(GameState state)
		{
			super(state, "Equipped creature gets +2/+4 and loses flying.");

			SetGenerator who = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(who, 2, 4));

			ContinuousEffect.Part abilityPart = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			abilityPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
			abilityPart.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffectPart(abilityPart);
		}
	}

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
				int equippedCreature = source.getAttachedTo();

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment assignment: damageAssignments)
					if(!assignment.isCombatDamage && assignment.takerID == equippedCreature)
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
			super(state, "Prevent all noncombat damage that would be dealt to equipped creature.");

			DamageReplacementEffect damageReplacementEffect = new PreventDamageEffect(this.game, "Prevent all damage that would be dealt to and dealt by enchanted creature.");

			this.addEffectPart(replacementEffectPart(damageReplacementEffect));
		}
	}

	public MagebaneArmor(GameState state)
	{
		super(state);

		this.addAbility(new Magebane(state));
		this.addAbility(new PreventDamage(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
