package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Undercity Reaches")
@Types({Type.PLANE})
@SubTypes({SubType.RAVNICA})
@ColorIdentity({})
public final class UndercityReaches extends Card
{
	public static final class HisNameIsFinkel extends EventTriggeredAbility
	{
		public HisNameIsFinkel(GameState state)
		{
			super(state, "Whenever a creature deals combat damage to a player, its controller may draw a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(CreaturePermanents.instance()));

			SetGenerator itsController = ControllerOf.instance(SourceOfDamage.instance(TriggerDamage.instance(This.instance())));

			this.addEffect(drawCards(itsController, 1, "Its controller may draw a card."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class HiEyeCue extends EventTriggeredAbility
	{
		public HiEyeCue(GameState state)
		{
			super(state, "Whenever you roll (C), you have no maximum hand size for the rest of the game.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
			this.addEffect(createFloatingEffect(Empty.instance(), "You have no maximum hand size for the rest of the game.", part));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public UndercityReaches(GameState state)
	{
		super(state);

		this.addAbility(new HisNameIsFinkel(state));

		this.addAbility(new HiEyeCue(state));
	}
}
