package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the ZoneChange which triggered each given
 * ZoneChangeTriggeredAbility
 */
public class TriggerZoneChange extends SetGenerator
{
	public static TriggerZoneChange instance(SetGenerator what)
	{
		return new TriggerZoneChange(what);
	}

	private final SetGenerator abilities;

	private TriggerZoneChange(SetGenerator abilities)
	{
		this.abilities = abilities;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		Set abilities = this.abilities.evaluate(state, thisObject);
		for(EventTriggeredAbility ability: abilities.getAll(EventTriggeredAbility.class))
			if(ability.zoneChangeCause != null)
				ret.add(ability.zoneChangeCause);
		return ret;
	}
}
