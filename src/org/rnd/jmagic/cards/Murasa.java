package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Murasa")
@Types({Type.PLANE})
@SubTypes({SubType.ZENDIKAR})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Murasa extends Card
{
	public static final class PackageDeal extends EventTriggeredAbility
	{
		public PackageDeal(GameState state)
		{
			super(state, "Whenever a nontoken creature enters the battlefield, its controller may search his or her library for a basic land card, put it onto the battlefield tapped, then shuffle his or her library.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), Intersect.instance(NonToken.instance(), CreaturePermanents.instance()), false));

			SetGenerator controller = ControllerOf.instance(NewObjectOf.instance(TriggerZoneChange.instance(This.instance())));

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Its controller searches his or her library for a basic land card, puts it onto the battlefield tapped, then shuffles his or her library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, controller);
			factory.parameters.put(EventType.Parameter.PLAYER, controller);
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND)));
			this.addEffect(playerMay(controller, factory, "Its controller may search his or her library for a basic land card, put it onto the battlefield tapped, then shuffle his or her library."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosAnimation extends EventTriggeredAbility
	{
		public ChaosAnimation(GameState state)
		{
			super(state, "Whenever you roll (C), target land becomes a 4/4 creature that's still a land.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(LandPermanents.instance(), "target land");

			ContinuousEffect.Part ptPart = setPowerAndToughness(targetedBy(target), 4, 4);

			ContinuousEffect.Part creaturePart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			creaturePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			creaturePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));

			SetGenerator expiration = Empty.instance();

			this.addEffect(createFloatingEffect(expiration, "Target land becomes a 4/4 creature that's still a land.", ptPart, creaturePart));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public Murasa(GameState state)
	{
		super(state);

		this.addAbility(new PackageDeal(state));

		this.addAbility(new ChaosAnimation(state));
	}
}
