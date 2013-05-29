package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

@Name("Stacked")
@Description("Shuffles don't happen and flipping a coin allows the player flipping to pick winning or losing. Useful for testing cards and cheating. :-P")
public class Stacked extends GameType.SimpleGameTypeRule
{
	@Override
	public void modifyGameState(GameState physicalState)
	{
		physicalState.game.noRandom = true;
	}
}
