package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guard Gomazoa")
@Types({Type.CREATURE})
@SubTypes({SubType.JELLYFISH})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class GuardGomazoa extends Card
{
	public static final class MCHammer extends StaticAbility
	{
		public static final class MCHammerEffect extends DamageReplacementEffect
		{
			public MCHammerEffect(Game game, String name)
			{
				super(game, name);
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);

				for(DamageAssignment assignment: damageAssignments)
					if(assignment.isCombatDamage && assignment.takerID == thisObject.ID)
						ret.add(assignment);

				return ret;
			}

			@Override
			public boolean isPreventionEffect()
			{
				return true;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return java.util.Collections.emptyList();
			}
		}

		public MCHammer(GameState state)
		{
			super(state, "Prevent all combat damage that would be dealt to Guard Gomazoa.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new MCHammerEffect(state.game, "Prevent all combat damage that would be dealt to Guard Gomazoa.")));
			this.addEffectPart(part);
		}
	}

	public GuardGomazoa(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Prevent all combat damage that would be dealt to Guard Gomazoa.
		this.addAbility(new MCHammer(state));
	}
}
