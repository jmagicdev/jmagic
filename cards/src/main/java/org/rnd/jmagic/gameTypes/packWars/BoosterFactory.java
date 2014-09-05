package org.rnd.jmagic.gameTypes.packWars;

import org.rnd.jmagic.engine.*;

public interface BoosterFactory
{
	public java.util.List<Card> createBooster(GameState state) throws org.rnd.jmagic.CardLoader.CardLoaderException;
}
