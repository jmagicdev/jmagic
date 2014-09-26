package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CreateDelayedTrigger extends EventType
{
	public static final EventType INSTANCE = new CreateDelayedTrigger();

	private CreateDelayedTrigger()
	{
		super("CREATE_DELAYED_TRIGGER");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.EFFECT; // awkward...
	}

	@Override
	public boolean perform(final Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		final GameObject causingObject = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		Set events = parameters.get(Parameter.EVENT);
		Set zoneChanges = parameters.get(Parameter.ZONE_CHANGE);
		Set damagePatterns = parameters.get(Parameter.DAMAGE);
		EventFactory effect = parameters.get(Parameter.EFFECT).getOne(EventFactory.class);

		SetGenerator duration = (parameters.containsKey(Parameter.EXPIRES) ? parameters.get(Parameter.EXPIRES).getOne(SetGenerator.class) : null);
		final DelayedTrigger trigger;
		if(parameters.containsKey(Parameter.EVENT))
		{
			trigger = new DelayedTrigger(game.physicalState, effect.name, causingObject, events.getAll(EventPattern.class), java.util.Collections.<DamagePattern>emptySet(), java.util.Collections.<ZoneChangePattern>emptySet(), effect, duration);
			game.physicalState.delayedTriggers.add(trigger);
		}
		else if(parameters.containsKey(Parameter.ZONE_CHANGE))
		{
			trigger = new DelayedTrigger(game.physicalState, effect.name, causingObject, java.util.Collections.<EventPattern>emptySet(), java.util.Collections.<DamagePattern>emptySet(), zoneChanges.getAll(ZoneChangePattern.class), effect, duration);
			game.physicalState.delayedTriggers.add(trigger);
		}
		else if(parameters.containsKey(Parameter.DAMAGE))
		{
			trigger = new DelayedTrigger(game.physicalState, effect.name, causingObject, java.util.Collections.<EventPattern>emptySet(), damagePatterns.getAll(DamagePattern.class), java.util.Collections.<ZoneChangePattern>emptySet(), effect, duration);
			game.physicalState.delayedTriggers.add(trigger);
		}
		else
			throw new UnsupportedOperationException("CREATE_DELAYED_TRIGGER must specify either EVENT or ZONE_CHANGE or DAMAGE parameter");

		if(parameters.containsKey(Parameter.COST))
		{
			// In order to keep an Identified reference from being held
			// through future game states, get the ID of this trigger for
			// use in the non-static anonymous classes that follow. Same
			// with the name of the causing object.
			final int triggerID = trigger.ID;
			final String objectName = causingObject.getName();

			final CostCollection cost = parameters.get(Parameter.COST).getOne(CostCollection.class);
			SpecialActionFactory stopFactory = new SpecialActionFactory()
			{
				@Override
				public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
				{
					return java.util.Collections.<PlayerAction>singleton(new StopDelayedTriggerAction(game, "Stop " + objectName + "'s delayed trigger from triggering", cost, triggerID, actor));
				}
			};

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(stopFactory));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(player));

			// This effect expires (that is, PLAYER can't stop this trigger
			// from triggering) when the trigger either triggers or is
			// stopped. That is, the effect expires when the trigger isn't
			// in the current state's delayed trigger list.
			SetGenerator expires = new SetGenerator()
			{
				@Override
				public Set evaluate(GameState state, Identified thisObject)
				{
					for(DelayedTrigger t: state.delayedTriggers)
						// if the trigger is in the list, the effect doesn't
						// expire yet
						if(t.ID == triggerID)
							return Empty.set;
					return NonEmpty.set;
				}
			};

			java.util.Map<Parameter, Set> stopParameters = new java.util.HashMap<Parameter, Set>();
			stopParameters.put(Parameter.CAUSE, new Set(trigger));
			stopParameters.put(Parameter.EFFECT, new Set(part));
			stopParameters.put(Parameter.EXPIRES, new Set(expires));
			Event allowStoppage = createEvent(game, "Allow " + player + " to stop '" + trigger + "'", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, stopParameters);
			allowStoppage.perform(event, false);
		}

		event.setResult(Identity.instance(trigger));
		return true;
	}
}