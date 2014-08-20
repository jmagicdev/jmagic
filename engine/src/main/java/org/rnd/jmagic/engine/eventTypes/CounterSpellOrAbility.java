package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CounterSpellOrAbility extends EventType
{	public static final EventType INSTANCE = new CounterSpellOrAbility();

	 private CounterSpellOrAbility()
	{
		super("COUNTER_SPELL_OR_ABILITY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();
		boolean allCountered = true;
		Set counterer = parameters.get(Parameter.CAUSE);
		Zone zone = (parameters.containsKey(Parameter.TO) ? parameters.get(Parameter.TO).getOne(Zone.class) : null);
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
			counterParameters.put(Parameter.CAUSE, counterer);
			counterParameters.put(Parameter.OBJECT, new Set(object));
			if(zone != null)
				counterParameters.put(Parameter.TO, new Set(zone));
			Event counterOne = createEvent(game, "Counter " + object, EventType.COUNTER_ONE, counterParameters);
			counterOne.perform(event, false);
			result.addAll(counterOne.getResult());
		}

		event.setResult(Identity.fromCollection(result));

		return allCountered;
	}
}