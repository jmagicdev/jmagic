package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Fight extends EventType
{
	public static final EventType INSTANCE = new Fight();

	private Fight()
	{
		super("FIGHT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> fighters = new java.util.HashSet<GameObject>();

		fighters.addAll(parameters.get(Parameter.OBJECT).getAll(GameObject.class));

		for(Target target: parameters.get(Parameter.OBJECT).getAll(Target.class))
		{
			GameObject object = game.actualState.getByIDObject(target.targetID);
			if(object != null)
				fighters.add(object);
		}

		if(fighters.size() != 2)
		{
			event.setResult(Empty.set);
			return false;
		}

		java.util.Iterator<GameObject> iterator = fighters.iterator();

		GameObject one = iterator.next();
		Set oneSet = new Set(one);

		GameObject two = iterator.next();
		Set twoSet = new Set(two);

		java.util.Map<Parameter, Set> oneParameters = new java.util.HashMap<Parameter, Set>();
		oneParameters.put(Parameter.SOURCE, oneSet);
		oneParameters.put(Parameter.NUMBER, new Set(one.getPower()));
		oneParameters.put(Parameter.TAKER, twoSet);
		Event oneDamage = createEvent(game, one + " deals damage equal to its power to " + two, EventType.DEAL_DAMAGE_EVENLY, oneParameters);

		java.util.Map<Parameter, Set> twoParameters = new java.util.HashMap<Parameter, Set>();
		twoParameters.put(Parameter.SOURCE, twoSet);
		twoParameters.put(Parameter.NUMBER, new Set(two.getPower()));
		twoParameters.put(Parameter.TAKER, oneSet);
		Event twoDamage = createEvent(game, two + " deals damage equal to its power to " + one, EventType.DEAL_DAMAGE_EVENLY, twoParameters);

		boolean ret = true;
		if(!oneDamage.perform(event, false))
			ret = false;
		if(!twoDamage.perform(event, false))
			ret = false;

		if(ret)
			event.setResult(Identity.instance(one, two));
		return ret;
	}
}