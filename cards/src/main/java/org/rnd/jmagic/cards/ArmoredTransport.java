package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Armored Transport")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ArmoredTransport extends Card
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
					if(assignment.isCombatDamage && assignment.takerID == thisObject.ID && thisObject.getBlockedByIDs().contains(assignment.sourceID))
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
			super(state, "Prevent all combat damage that would be dealt to Armored Transport by creatures blocking it.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new MCHammerEffect(state.game, "Prevent all combat damage that would be dealt to Armored Transport by creatures blocking it.")));
			this.addEffectPart(part);
		}
	}

	public ArmoredTransport(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Prevent all combat damage that would be dealt to Armored Transport by
		// creatures blocking it.
		this.addAbility(new MCHammer(state));
	}
}
