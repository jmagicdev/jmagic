package org.rnd.jmagic.engine;

/** This class is used by the engine to generate the per turn play land actions. */
public class PerTurnPlayLandActionFactory extends PlayLandActionFactory
{
	public PerTurnPlayLandActionFactory(Game game)
	{
		super(game);
	}

	/**
	 * Creates a per turn play land action.
	 * 
	 * @param land The land to play.
	 * @return The action that plays the specified land.
	 */
	@Override
	public PerTurnPlayLandAction createAction(Player who, GameObject land)
	{
		return new PerTurnPlayLandAction(this.game, land, who);
	}
}
