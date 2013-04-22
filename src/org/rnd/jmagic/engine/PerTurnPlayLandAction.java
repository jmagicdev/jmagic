package org.rnd.jmagic.engine;

/** Represents the once-per-turn action of playing a land. */
public class PerTurnPlayLandAction extends PlayLandAction
{
	/**
	 * @param game The game in which this action is to be performed.
	 * @param land The land to play.
	 */
	public PerTurnPlayLandAction(Game game, GameObject land, Player player)
	{
		super(game, "Play land " + land + " for the turn", land, player, 0);
	}

	/**
	 * Causes the acting player to play the land specified when this object was
	 * constructed.
	 * 
	 * @return True if the land was successfully played; false otherwise.
	 */
	@Override
	public boolean perform()
	{
		if(super.perform())
		{
			Player playerActing = this.game.physicalState.get(this.actorID);

			if(playerActing.playLandActionLastUsed == this.game.actualState.currentTurn())
			{
				playerActing.playLandActionUses += 1;
			}
			else
			{
				playerActing.playLandActionLastUsed = this.game.actualState.currentTurn();
				playerActing.playLandActionUses = 1;
			}
			return true;
		}
		return false;
	}
}
