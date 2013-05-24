package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Regenerate extends EventType
{	public static final EventType INSTANCE = new Regenerate();

	 private Regenerate()
	{
		super("REGENERATE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// 701.11. Regenerate
		// 701.11a If the effect of a resolving spell or ability regenerates
		// a permanent, it creates a replacement effect that protects the
		// permanent the next time it would be destroyed this turn. In this
		// case, "Regenerate [permanent]" means "The next time [permanent]
		// would be destroyed this turn, instead remove all damage marked on
		// it and tap it. If it's an attacking or blocking creature, remove
		// it from combat."
		// 701.11b If the effect of a static ability regenerates a
		// permanent, it replaces destruction with an alternate effect each
		// time that permanent would be destroyed. In this case,
		// "Regenerate [permanent]" means "Instead remove all damage marked
		// on [permanent] and tap it. If it's an attacking or blocking
		// creature, remove it from combat."
		Set ret = new Set();
		Set removeFromCombat = new Set();

		Set objects = parameters.get(Parameter.OBJECT);
		for(GameObject object: objects.getAll(GameObject.class))
		{
			GameObject physicalObject = object.getPhysical();
			physicalObject.setDamage(0);

			if(object.getAttackingID() != -1 || !object.getBlockingIDs().isEmpty())
				removeFromCombat.add(object);

			ret.add(object);
		}

		java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
		tapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		tapParameters.put(Parameter.OBJECT, objects);
		createEvent(game, "Tap " + objects, EventType.TAP_PERMANENTS, tapParameters).perform(event, false);

		if(!removeFromCombat.isEmpty())
		{
			java.util.Map<Parameter, Set> removeParameters = new java.util.HashMap<Parameter, Set>();
			removeParameters.put(Parameter.OBJECT, removeFromCombat);
			createEvent(game, "Remove " + removeFromCombat + " from combat", EventType.REMOVE_FROM_COMBAT, removeParameters).perform(event, false);
		}

		event.setResult(Identity.instance(ret));
		return true;
	}
}