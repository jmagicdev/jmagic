package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grave Betrayal")
@Types({Type.ENCHANTMENT})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GraveBetrayal extends Card
{
	/**
	 * @eparam CAUSE: Grave Betrayal
	 * @eparam PLAYER: CAUSE's controller
	 * @eparam OBJECT: CAUSE's target
	 * @eparam RESULT: empty
	 */
	public static final EventType GRAVE_BETRAYAL_EVENT = new EventType("GRAVE_BETRAYAL_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set you = parameters.get(Parameter.PLAYER);
			GameObject target = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			// Put target creature card in a graveyard onto the battlefield
			// under your control.
			java.util.Map<Parameter, Set> ontoFieldParameters = new java.util.HashMap<Parameter, Set>();
			ontoFieldParameters.put(Parameter.CAUSE, cause);
			ontoFieldParameters.put(Parameter.CONTROLLER, you);
			ontoFieldParameters.put(Parameter.OBJECT, new Set(target));
			ontoFieldParameters.put(Parameter.COUNTER, new Set(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			Event ontoField = createEvent(game, "Return it to the battlefield under your control with an additional +1/+1 counter on it.", PUT_ONTO_BATTLEFIELD_WITH_COUNTERS, ontoFieldParameters);

			// Not top level -- the creature needs to be a black Zombie the
			// entire time it's on the battlefield.
			ontoField.perform(event, false);
			SetGenerator thatCreature = NewObjectOf.instance(ontoField.getResultGenerator());

			// That creature is a black Zombie in addition to its other colors
			// and types.
			ContinuousEffect.Part blackPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			blackPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);
			blackPart.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));

			ContinuousEffect.Part zombiePart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			zombiePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);
			zombiePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ZOMBIE));

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "That creature is a black Zombie in addition to its other colors and types.");
			factory.parameters.put(Parameter.CAUSE, Identity.instance(cause));
			factory.parameters.put(Parameter.EFFECT, Identity.instance(blackPart, zombiePart));
			factory.parameters.put(Parameter.EXPIRES, Identity.instance(Empty.instance()));
			ontoField.getResult().getOne(ZoneChange.class).events.add(factory);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class GraveBetrayalAbility0 extends EventTriggeredAbility
	{
		public GraveBetrayalAbility0(GameState state)
		{
			super(state, "Whenever a creature you don't control dies, return it to the battlefield under your control with an additional +1/+1 counter on it at the beginning of the next end step. That creature is a black Zombie in addition to its other colors and types.");
			this.addPattern(whenXDies(RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()))));

			SetGenerator originalTrigger = delayedTriggerContext(This.instance());
			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(originalTrigger));

			EventFactory move = new EventFactory(GRAVE_BETRAYAL_EVENT, "Return it to the battlefield under your control with an additional +1/+1 counter on it. That creature is a black Zombie in addition to its other colors and types.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.PLAYER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, thatCard);

			EventFactory factory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return it to the battlefield under your control with an additional +1/+1 counter on it at the beginning of the next end step. That creature is a black Zombie in addition to its other colors and types.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(move));
			this.addEffect(factory);
		}
	}

	public GraveBetrayal(GameState state)
	{
		super(state);

		// Whenever a creature you don't control dies, return it to the
		// battlefield under your control with an additional +1/+1 counter on it
		// at the beginning of the next end step. That creature is a black
		// Zombie in addition to its other colors and types.
		this.addAbility(new GraveBetrayalAbility0(state));
	}
}
