package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;
import static org.rnd.jmagic.Convenience.*;

public class Ferocious extends SetGenerator
{
	private static final Ferocious _instance = new Ferocious();

	public static Ferocious instance()
	{
		return _instance;
	}

	private Ferocious()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return CREATURES_YOU_CONTROL.evaluate(state, thisObject).getAll(GameObject.class)//
		.stream().anyMatch(o -> o.getTypes().contains(Type.CREATURE) && o.getPower() >= 4)//
		? NonEmpty.set : Empty.set;
	}

}
