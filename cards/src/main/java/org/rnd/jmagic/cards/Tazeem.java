package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Tazeem")
@Types({Type.PLANE})
@SubTypes({SubType.ZENDIKAR})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.SPECIAL)})
@ColorIdentity({})
public final class Tazeem extends Card
{
	public static final class RestrictBlocking extends StaticAbility
	{
		public RestrictBlocking(GameState state)
		{
			super(state, "Creatures can't block.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Blocking.instance()));
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class ChaosLandDraw extends EventTriggeredAbility
	{
		public ChaosLandDraw(GameState state)
		{
			super(state, "Whenever you roll (C), draw a card for each land you control.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			this.addEffect(drawCards(You.instance(), Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), LandPermanents.instance())), "Draw a card for each land you control."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Tazeem(GameState state)
	{
		super(state);

		this.addAbility(new RestrictBlocking(state));

		this.addAbility(new ChaosLandDraw(state));
	}
}
