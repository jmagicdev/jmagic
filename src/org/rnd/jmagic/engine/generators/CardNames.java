package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class CardNames extends SetGenerator
{
	private static final CardNames _instance = new CardNames();

	public static CardNames instance()
	{
		return _instance;
	}

	private CardNames()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return new Set(state.game.gameType.getCardPool().keySet());
	}

}
