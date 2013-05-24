package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class DealDamageEvenlyCantBeRegenerated extends EventType
{	public static final EventType INSTANCE = new DealDamageEvenlyCantBeRegenerated();

	 private DealDamageEvenlyCantBeRegenerated()
	{
		super("DEAL_DAMAGE_EVENLY_CANT_BE_REGENERATED");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.TAKER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Event damageEvent = createEvent(game, event.getName(), EventType.DEAL_DAMAGE_EVENLY, parameters);
		boolean ret = damageEvent.perform(event, false);

		SetPattern affectedCreatures = new SimpleSetPattern(Intersect.instance(TakerOfDamage.instance(EventDamage.instance(Identity.instance(event))), CreaturePermanents.instance()));

		SimpleEventPattern regenerate = new SimpleEventPattern(EventType.REGENERATE);
		regenerate.put(EventType.Parameter.OBJECT, affectedCreatures);

		EventReplacementEffectStopper stopRegen = new EventReplacementEffectStopper(parameters.get(Parameter.SOURCE).getOne(GameObject.class), null, regenerate);
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.STOP_REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(stopRegen));

		java.util.Map<EventType.Parameter, Set> stopRegenParameters = new java.util.HashMap<EventType.Parameter, Set>();
		stopRegenParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.SOURCE));
		stopRegenParameters.put(EventType.Parameter.EFFECT, new Set(part));
		Event regenStopper = createEvent(game, "A creature dealt damage this way can't be regenerated this turn.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, stopRegenParameters);
		ret = regenStopper.perform(event, false) && ret;

		event.setResult(Empty.set);

		return ret;
	}
}