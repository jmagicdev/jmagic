package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the damage which triggered each given triggered ability
 */
public class TriggerDamage extends SetGenerator
{
	public static TriggerDamage instance(SetGenerator what)
	{
		return new TriggerDamage(what);
	}

	public static Set get(EventTriggeredAbility ability)
	{
		return new Set(ability.damageCause);
	}

	private final SetGenerator abilities;

	private TriggerDamage(SetGenerator abilities)
	{
		this.abilities = abilities;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(EventTriggeredAbility ability: this.abilities.evaluate(state, thisObject).getAll(EventTriggeredAbility.class))
		{
			Set cause = TriggerDamage.get(ability);
			if(null != cause)
				ret.addAll(cause);
		}
		return ret;
	}

}
