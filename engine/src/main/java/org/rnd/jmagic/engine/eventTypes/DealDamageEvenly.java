package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DealDamageEvenly extends EventType
{	public static final EventType INSTANCE = new DealDamageEvenly();

	 private DealDamageEvenly()
	{
		super("DEAL_DAMAGE_EVENLY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.TAKER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject source = parameters.get(Parameter.SOURCE).getOne(GameObject.class);
		int damageAmount = Sum.get(parameters.get(Parameter.NUMBER));
		boolean unpreventable = parameters.containsKey(Parameter.PREVENT);
		java.util.Collection<Identified> takers = new java.util.LinkedList<Identified>();
		takers.addAll(parameters.get(Parameter.TAKER).getAll(Player.class));
		takers.addAll(parameters.get(Parameter.TAKER).getAll(GameObject.class));

		for(Identified taker: takers)
			for(int i = 0; i < damageAmount; i++)
				event.addDamage(source, taker, unpreventable);

		event.setResult(Empty.set);
		return true;
	}
}