package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class CardsYouOwnOutsideTheGame extends SetGenerator
{
	private static final CardsYouOwnOutsideTheGame _instance = new CardsYouOwnOutsideTheGame();

	public static CardsYouOwnOutsideTheGame instance()
	{
		return _instance;
	}

	private CardsYouOwnOutsideTheGame()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return state.game.getWishboard().evaluate(state, thisObject);
	}

}
