package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Grixis")
@Types({Type.PLANE})
@SubTypes({SubType.ALARA})
@ColorIdentity({})
public final class Grixis extends Card
{
	public static final class GrixisGivesUnearth extends StaticAbility
	{
		public GrixisGivesUnearth(GameState state)
		{
			super(state, "Blue, black, and/or red creature cards in your graveyard have unearth. The unearth cost is equal to the card's mana cost.");

			SetGenerator affected = Intersect.instance(Intersect.instance(HasColor.instance(Color.BLUE, Color.BLACK, Color.RED), HasType.instance(Type.CREATURE)), InZone.instance(GraveyardOf.instance(You.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.GRANT_COSTED_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(Unearth.class));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, affected);
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class ChaosResurrect extends EventTriggeredAbility
	{
		public ChaosResurrect(GameState state)
		{
			super(state, "Whenever you roll (C), put target creature card from a graveyard onto the battlefield under your control.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))), "target creature card from a graveyard");

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put target creature card from a graveyard onto the battlefield under your control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(factory);

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Grixis(GameState state)
	{
		super(state);

		this.addAbility(new GrixisGivesUnearth(state));

		this.addAbility(new ChaosResurrect(state));
	}
}
