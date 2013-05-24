package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DistributeDamage extends EventType
{	public static final EventType INSTANCE = new DistributeDamage();

	 private DistributeDamage()
	{
		super("DISTRIBUTE_DAMAGE");
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
		Set takers = parameters.get(Parameter.TAKER);
		boolean unpreventable = parameters.containsKey(Parameter.PREVENT);

		for(Target takerTarget: takers.getAll(Target.class))
		{
			Identified taker = game.actualState.get(takerTarget.targetID);
			for(int i = 0; i < takerTarget.division; i++)
				event.addDamage(source, taker, unpreventable);
		}

		event.setResult(Empty.set);
		return true;
	}
}