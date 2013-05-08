package org.rnd.jmagic.engine;

abstract public class PlayerAction implements Sanitizable
{
	public final int actorID;

	public final Game game;

	public final int sourceID;

	/** True if this action can't be reversed when reversing the game state. */
	public boolean irreversible;

	/** Keeps track of mana spent while performing this action. */
	public ManaPool manaPaid;

	/** Keeps track of mana produced while performing this action. */
	public ManaPool manaProduced;

	public String name;

	/** The action that performed this action. */
	public PlayerAction outerAction;

	/**
	 * Creates a player action that does nothing when performed.
	 * 
	 * @param game The game in which this action is to be performed.
	 * @param name The name of the action. This should usually Magic-ese that
	 * describes what the action will do; for example, "Play land Forest for the
	 * turn" or "Cast Giant Growth" or the text of an activated ability.
	 */
	public PlayerAction(Game game, String name, Player actor, int source)
	{
		this.actorID = actor.ID;
		this.game = game;
		this.irreversible = false;
		this.manaPaid = new ManaPool();
		this.manaProduced = new ManaPool();
		this.name = name;
		this.outerAction = null;
		this.sourceID = source;
	}

	public final Player actor()
	{
		return this.game.actualState.get(this.actorID);
	}

	public int getSourceObjectID()
	{
		return -1;
	}

	/**
	 * Performs this action.
	 * 
	 * @return Whether the action was successfully performed.
	 */
	abstract public boolean perform();

	abstract public PlayerInterface.ReversionParameters getReversionReason();

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedPlayerAction(this);
	}

	/**
	 * Backs up the current game state and performs this action. If the action
	 * is successfully performed, the backup is discarded; otherwise, the backup
	 * is restored.
	 * 
	 * TODO : "Replay" irreversible actions.
	 * 
	 * @return Whether the action was successfully performed.
	 */
	public final boolean saveStateAndPerform()
	{
		// Set the game's current action
		this.outerAction = this.game.currentAction;
		this.game.currentAction = this;

		// Save the current-state
		this.game.backupStates.push(this.game.physicalState.clone(true));

		// Perform the action
		boolean legalPlay = this.perform();

		// Pop the saved game state and keep it around
		GameState savedState = this.game.backupStates.pop();

		// If they couldn't perform the action, restore the backed-up state
		if(!legalPlay)
		{
			this.game.physicalState.clear();
			// TODO : Apply irreversible actions to saved states
			this.game.physicalState = savedState;

			for(Player player: savedState.players)
			{
				player.comm.alertStateReversion(this.getReversionReason());
			}
		}
		else
		{
			savedState.clear();
			savedState = null;
		}

		// Finish up
		this.game.currentAction = this.outerAction;
		return legalPlay;
	}

	/** @return A string representation of this player action. */
	@Override
	public String toString()
	{
		return this.name;
	}
}
