package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/** Represents the action of playing a land. */
public final class PlayLandAction extends PlayerAction
{
	public final int landID;
	private int playedID;

	/**
	 * @param game The game in which this action is to be performed.
	 * @param name The name of this action. This should usually start with "Play
	 * land [name of land]".
	 * @param land The land to play.
	 */
	public PlayLandAction(Game game, String name, GameObject land, Player player)
	{
		// the source is the game, 0
		super(game, name, player, 0);
		this.landID = land.ID;
	}

	/**
	 * There's no rule to quote about not being to play lands that are
	 * prohibited, but cards like Pardic Miner prohibit playing lands, so mirror
	 * the same structure for casting spells and activating abilities.
	 */
	public boolean attempt()
	{
		GameObject land = this.game.actualState.get(this.landID);
		Player player = this.game.actualState.get(this.actorID);

		Event event = new Event(this.game.physicalState, player + " plays " + land + ".", EventType.PLAY_LAND);
		event.parameters.put(EventType.Parameter.ACTION, Identity.instance(this));
		event.parameters.put(EventType.Parameter.PLAYER, Identity.instance(player));
		event.parameters.put(EventType.Parameter.OBJECT, Identity.instance(land));

		event.setSource(land);
		return !event.isProhibited(this.game.actualState);
	}

	@Override
	public int getSourceObjectID()
	{
		return this.landID;
	}

	/**
	 * Performs this action. Causes the acting player to play the land specified
	 * on construction.
	 */
	@Override
	public boolean perform()
	{
		Player player = this.game.actualState.get(this.actorID);

		GameObject land = this.game.actualState.get(this.landID);
		Event event = new Event(this.game.physicalState, player + " plays " + land + ".", EventType.PLAY_LAND);
		event.parameters.put(EventType.Parameter.ACTION, Identity.instance(this));
		event.parameters.put(EventType.Parameter.PLAYER, Identity.instance(player));
		event.parameters.put(EventType.Parameter.LAND, This.instance());
		event.setSource(land);
		if(event.perform(null, true))
		{
			this.playedID = NewObjectOf.instance(event.getResultGenerator()).evaluate(this.game, null).getOne(GameObject.class).ID;
			return true;
		}

		return false;
	}

	/**
	 * @return If this action has been successfully performed, the land that was
	 * played as a result of the action; otherwise, null.
	 */
	public GameObject played()
	{
		return this.game.actualState.<GameObject>get(this.playedID);
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedPlayLandAction(this);
	}

	@Override
	public PlayerInterface.ReversionParameters getReversionReason()
	{
		Player player = this.game.physicalState.get(this.actorID);
		GameObject land = this.game.physicalState.get(this.landID);
		return new PlayerInterface.ReversionParameters("PlayLandAction", player.getName() + " failed to play " + land.getName() + ".");
	}
}
