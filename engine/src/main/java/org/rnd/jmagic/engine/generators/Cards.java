package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Cards extends SetGenerator
{
	private static final Cards _instance = new Cards();

	public static Cards instance()
	{
		return _instance;
	}

	private Cards()
	{
		// Singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject card: state.getAll(Card.class))
			if(!card.isGhost() && card.zoneID != -1)
				ret.add(card);

		return ret;
	}
}
