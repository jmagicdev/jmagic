package org.rnd.jmagic.engine.gameTypes.packWars;

import org.rnd.jmagic.engine.*;

public interface BoosterFactory
{
	public java.util.List<Card> createBooster(GameState state) throws org.rnd.jmagic.CardLoader.CardLoaderException;
}
