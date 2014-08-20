package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TurnPermanentsFaceDown extends EventType
{	public static final EventType INSTANCE = new TurnPermanentsFaceDown();

	 private TurnPermanentsFaceDown()
	{
		super("TURN_PERMANENTS_FACE_DOWN");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> result = new java.util.HashSet<GameObject>();
		for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			if(o.isPermanent())
			{
				// 711.6 ... If a spell or ability tries to turn a
				// double-faced permanent face down, nothing happens.
				if(o.getBackFace() != null)
					continue;

				// 707.2a If a face-up permanent is turned face down by a
				// spell or ability, it becomes a 2/2 face-down creature
				// with no text, no name, no subtypes, no expansion symbol,
				// and no mana cost. These values are the copiable values of
				// that object's characteristics.
				GameObject physical = o.getPhysical();

				// 707.2a If a face-up permanent is turned face down by a
				// spell or ability, it becomes a 2/2 face-down creature
				// with no text, no name, no subtypes, no expansion symbol,
				// and no mana cost. These values are the copiable values of
				// that object's characteristics.
				physical.faceDownValues = new FaceDownCard();

				result.add(o);
			}
		}

		event.setResult(Identity.fromCollection(result));
		return true;
	}
}