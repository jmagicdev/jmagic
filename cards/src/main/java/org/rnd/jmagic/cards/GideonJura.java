package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gideon Jura")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GIDEON})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class GideonJura extends Card
{
	public static final class AttackMe extends LoyaltyAbility
	{
		public AttackMe(GameState state)
		{
			super(state, +2, "During target opponent's next turn, creatures that player controls attack Gideon Jura if able.");
			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

			// This code creates an effect that starts *now* and lasts until the
			// end of the target's next turn. If it ever becomes possible to
			// attack when it's not your turn, this will be wrong.

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetedBy(target))));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, ABILITY_SOURCE_OF_THIS);

			EventFactory effect = createFloatingEffect("During target opponent's next turn, creatures that player controls attack Gideon Jura if able.", part);
			effect.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Intersect.instance(CurrentStep.instance(), CleanupStepOf.instance(targetedBy(target)))));
			this.addEffect(effect);
		}
	}

	public static final class Vengeance extends LoyaltyAbility
	{
		public Vengeance(GameState state)
		{
			super(state, -2, "Destroy target tapped creature.");
			Target target = this.addTarget(Intersect.instance(Tapped.instance(), HasType.instance(Type.CREATURE)), "target tapped creature");
			this.addEffect(destroy(targetedBy(target), "Destroy target tapped creature."));
		}
	}

	public static final class Animate extends LoyaltyAbility
	{
		private static final class PreventToGideon extends DamageReplacementEffect
		{
			private PreventToGideon(Game game, String name)
			{
				super(game, name);
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				Identified ability = this.getSourceObject(context.game.actualState);
				int gideon = ((ActivatedAbility)ability).sourceID;
				for(DamageAssignment assignment: damageAssignments)
					if(assignment.takerID == gideon)
						ret.add(assignment);

				return ret;
			}

			@Override
			public java.util.List<org.rnd.jmagic.engine.EventFactory> prevent(org.rnd.jmagic.engine.DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return java.util.Collections.emptyList();
			}
		}

		public Animate(GameState state)
		{
			super(state, 0, "Until end of turn, Gideon Jura becomes a 6/6 Human Soldier creature that's still a planeswalker. Prevent all damage that would be dealt to him this turn.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			ContinuousEffect.Part ptPart = setPowerAndToughness(thisCard, 6, 6);

			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thisCard);
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE, SubType.HUMAN, SubType.SOLDIER));

			ContinuousEffect.Part preventPart = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			preventPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new PreventToGideon(state.game, "Prevent all damage that would be dealt to Gideon Jura")));

			this.addEffect(createFloatingEffect("Until end of turn, Gideon Jura becomes a 6/6 Human Soldier creature that's still a planeswalker. Prevent all damage that would be dealt to him this turn.", ptPart, typesPart, preventPart));
		}
	}

	public GideonJura(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(6);

		// +2: During target opponent's next turn, creatures that player
		// controls attack Gideon Jura if able.
		this.addAbility(new AttackMe(state));

		// -2: Destroy target tapped creature.
		this.addAbility(new Vengeance(state));

		// 0: Until end of turn, Gideon Jura becomes a 6/6 Human Soldier
		// creature that's still a planeswalker. Prevent all damage that would
		// be dealt to him this turn.
		this.addAbility(new Animate(state));
	}
}
