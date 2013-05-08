package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to "you" in the context of evaluation
 */
public class You extends SetGenerator
{
	private static final You _instance = new You();

	public static You instance()
	{
		return _instance;
	}

	private You()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(!(thisObject.isGameObject()))
			throw new UnsupportedOperationException("Tried to evaluate You on " + thisObject);

		// If thisObject is an ability printed on an object, then get that
		// object's controller, or if it's on a player, get that player
		if((thisObject.isActivatedAbility() || thisObject.isTriggeredAbility()) && ((NonStaticAbility)thisObject).zoneID == -1)
		{
			Identified source = ((NonStaticAbility)thisObject).getSource(state);
			if(source.isGameObject())
				return new Set(((GameObject)source).getController(state));
			return new Set(source); // source is a player
		}

		// Otherwise just get thisObject's controller
		return new Set(state.<GameObject>get(thisObject.ID).getController(state));
	}
}
