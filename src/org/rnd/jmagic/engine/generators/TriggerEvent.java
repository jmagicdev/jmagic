package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the event which triggered each given EventTriggeredAbility
 */
public class TriggerEvent extends SetGenerator
{
	public static TriggerEvent instance(SetGenerator what)
	{
		return new TriggerEvent(what);
	}

	private final SetGenerator eventTriggeredAbilities;

	private TriggerEvent(SetGenerator eventTriggeredAbilities)
	{
		this.eventTriggeredAbilities = eventTriggeredAbilities;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		Set eventTriggeredAbilities = this.eventTriggeredAbilities.evaluate(state, thisObject);
		for(EventTriggeredAbility eta: eventTriggeredAbilities.getAll(EventTriggeredAbility.class))
		{
			Event cause = state.get(eta.eventCause);
			if(null != cause)
				ret.add(cause);
		}
		return ret;
	}

}
