package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Show extends EventType
{
	public static final EventType INSTANCE = new Show();

	private Show()
	{
		super("SHOW");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		for(GameObject object: objects)
		{
			object = object.getPhysical();
			for(Player p: game.actualState.players)
			{
				object.setPhysicalVisibility(p, true);
				object.setActualVisibility(p, true);
				org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Reveal(event, objects, p);
				p.alert(sanitized);
			}
		}

		event.setResult(Empty.set);
		return true;
	}
}