package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players turn
 */
public class TurnOf extends SetGenerator
{
	public static TurnOf instance(SetGenerator what)
	{
		return new TurnOf(what);
	}

	private final SetGenerator players;

	private TurnOf(SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set returnValue = new Set();

		for(Player player: this.players.evaluate(state, thisObject).getAll(Player.class))
		{
			Turn currentTurn = state.currentTurn();
			if(currentTurn.getOwner(state).equals(player))
				returnValue.add(currentTurn);
			for(Turn turn: state.futureTurns)
			{
				if(turn.getOwner(state).equals(player))
					returnValue.add(turn);
			}
		}

		return returnValue;
	}
}
