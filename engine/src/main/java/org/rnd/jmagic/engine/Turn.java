package org.rnd.jmagic.engine;

/** Represents a turn in a game. */
public class Turn implements Ownable, Iterable<Phase>
{
	/** Whether "until end of turn" and "this turn" effects have ended */
	public boolean endEffects;

	/** Whether this turn is an extra turn granted by an effect. */
	public boolean extra;

	private String name;

	/** The phases in this turn. */
	public java.util.List<Phase> phases;

	/**
	 * The phases in this turn that have already been passed through. Does not
	 * include the current phase.
	 */
	public java.util.List<Phase> phasesRan;

	/** Whose turn it is. */
	public int ownerID;

	/**
	 * @param owner Whose turn this will be.
	 */
	public Turn(Player owner)
	{
		this.endEffects = false;
		this.extra = false;
		this.name = owner + "'s turn";
		this.phases = new java.util.LinkedList<Phase>();
		this.phasesRan = new java.util.LinkedList<Phase>();

		// order dependency
		this.setOwner(owner);
		this.addPhase(owner, Phase.PhaseType.BEGINNING);
		this.addPhase(owner, Phase.PhaseType.PRECOMBAT_MAIN);
		this.addPhase(owner, Phase.PhaseType.COMBAT);
		this.addPhase(owner, Phase.PhaseType.POSTCOMBAT_MAIN);
		this.addPhase(owner, Phase.PhaseType.ENDING);
	}

	/**
	 * Adds a phase to this turn.
	 * 
	 * @param owner Who will own the phase
	 * @param type The kind of phase to add.
	 */
	private void addPhase(Player owner, Phase.PhaseType type)
	{
		this.phases.add(new Phase(owner, type));
	}

	/** @return Whose turn this is. */
	@Override
	public Player getOwner(GameState state)
	{
		return state.get(this.ownerID);
	}

	/** @return An iterator over the phases in this turn. */
	@Override
	public java.util.Iterator<Phase> iterator()
	{
		return this.phases.iterator();
	}

	/**
	 * Tells this turn whose it is.
	 * 
	 * @param owner The player whose turn this is.
	 */
	public void setOwner(Player owner)
	{
		if(owner == null)
		{
			this.name = "Nobody's turn";
			this.ownerID = -1;
		}
		else
		{
			this.name = owner + "'s turn";
			this.ownerID = owner.ID;
		}
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
