package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Agyrem")
@Types({Type.PLANE})
@SubTypes({SubType.RAVNICA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Agyrem extends Card
{
	public static final class WhiteDeath extends EventTriggeredAbility
	{
		public WhiteDeath(GameState state)
		{
			super(state, "Whenever a white creature dies, return it to the battlefield under its owner's control at the beginning of the next end step.");

			SetGenerator whiteCreatures = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE));
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), whiteCreatures, true);
			this.addPattern(pattern);

			SetGenerator it = NewObjectOf.instance(EventResult.instance(TriggerEvent.instance(This.instance())));

			EventFactory returnFactory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return it to the battlefield under its owner's control.");
			returnFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnFactory.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(it));
			returnFactory.parameters.put(EventType.Parameter.OBJECT, it);

			EventFactory factory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return it to the battlefield under its owner's control at the beginning of the next end step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnFactory));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class NonWhiteDeath extends EventTriggeredAbility
	{
		public NonWhiteDeath(GameState state)
		{
			super(state, "Whenever a nonwhite creature dies, return it to its owner's hand at the beginning of the next end step.");

			SetGenerator nonwhiteCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE));
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), nonwhiteCreatures, true);
			this.addPattern(pattern);

			SetGenerator it = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory returnFactory = new EventFactory(EventType.MOVE_OBJECTS, "Return it to its owner's hand.");
			returnFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(it)));
			returnFactory.parameters.put(EventType.Parameter.OBJECT, it);

			EventFactory factory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return it to its owner's hand at the beginning of the next end step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnFactory));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosBlock extends EventTriggeredAbility
	{
		public ChaosBlock(GameState state)
		{
			super(state, "Whenever you roll (C), creatures can't attack you until a player planeswalks.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			state.ensureTracker(new Planechase.UntilAPlayerPlaneswalks());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Attacking.instance(ControllerOf.instance(This.instance()))));

			EventFactory factory = new EventFactory(Planechase.CREATE_FCE_UNTIL_A_PLAYER_PLANESWALKS, "Creatures can't attack you until a player planeswalks.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public Agyrem(GameState state)
	{
		super(state);

		this.addAbility(new WhiteDeath(state));

		this.addAbility(new NonWhiteDeath(state));

		this.addAbility(new ChaosBlock(state));
	}
}
