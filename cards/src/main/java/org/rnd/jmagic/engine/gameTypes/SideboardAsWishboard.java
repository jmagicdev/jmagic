package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * This replaces the games value for "cards you own outside the game" with the
 * player's sideboard.
 */
@Name("Sideboard")
@Description("An extra zone used to hold cards outside the game but fetchable by cards inside the game (ex: Glittering Wish)")
public class SideboardAsWishboard extends GameType.SimpleGameTypeRule
{
	@Override
	public void modifyGameState(GameState physicalState)
	{
		physicalState.game.setWishboard(InZone.instance(SideboardOf.instance(You.instance())));
	}
}
