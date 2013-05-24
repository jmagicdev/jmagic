package org.rnd.jmagic.engine;

/** Represents a phase within a turn. */
public class Phase implements Ownable, Iterable<Step>
{
	/** Represents the different kinds of phases. */
	public enum PhaseType
	{
		BEGINNING
		{
			@Override
			public java.util.LinkedList<Step> steps(Player owner)
			{
				java.util.LinkedList<Step> returnValue = new java.util.LinkedList<Step>();
				returnValue.add(new Step(owner, Step.StepType.UNTAP));
				returnValue.add(new Step(owner, Step.StepType.UPKEEP));
				returnValue.add(new Step(owner, Step.StepType.DRAW));
				return returnValue;
			}

			@Override
			public String toString()
			{
				return "beginning";
			}
		},
		PRECOMBAT_MAIN
		{
			@Override
			public java.util.LinkedList<Step> steps(Player owner)
			{
				java.util.LinkedList<Step> returnValue = new java.util.LinkedList<Step>();
				returnValue.add(new Step(owner, Step.StepType.PRECOMBAT_MAIN));
				return returnValue;
			}

			@Override
			public String toString()
			{
				return "pre-combat main";
			}
		},
		COMBAT
		{
			@Override
			public java.util.LinkedList<Step> steps(Player owner)
			{
				java.util.LinkedList<Step> returnValue = new java.util.LinkedList<Step>();
				returnValue.add(new Step(owner, Step.StepType.BEGINNING_OF_COMBAT));
				returnValue.add(new Step(owner, Step.StepType.DECLARE_ATTACKERS));
				returnValue.add(new Step(owner, Step.StepType.DECLARE_BLOCKERS));
				returnValue.add(new Step(owner, Step.StepType.COMBAT_DAMAGE));
				returnValue.add(new Step(owner, Step.StepType.END_OF_COMBAT));
				return returnValue;
			}

			@Override
			public String toString()
			{
				return "combat";
			}
		},
		POSTCOMBAT_MAIN
		{
			@Override
			public java.util.LinkedList<Step> steps(Player owner)
			{
				java.util.LinkedList<Step> returnValue = new java.util.LinkedList<Step>();
				returnValue.add(new Step(owner, Step.StepType.POSTCOMBAT_MAIN));
				return returnValue;
			}

			@Override
			public String toString()
			{
				return "post-combat main";
			}
		},
		ENDING
		{
			@Override
			public java.util.LinkedList<Step> steps(Player owner)
			{
				java.util.LinkedList<Step> returnValue = new java.util.LinkedList<Step>();
				returnValue.add(new Step(owner, Step.StepType.END));
				returnValue.add(new Step(owner, Step.StepType.CLEANUP));
				return returnValue;
			}

			@Override
			public String toString()
			{
				return "ending";
			}
		};

		/**
		 * Specifies which steps should occur in a phase of this type.
		 * 
		 * @param owner Who owns the phase (and thus owns the steps).
		 * @return The steps contained in a phase of this type.
		 */
		abstract public java.util.LinkedList<Step> steps(Player owner);
	}

	/**
	 * If this is not a combat phase, false. If this is a combat phase, whether
	 * blockers have been declared this phase. Before blockers have been
	 * declared, no creature is considered blocked or unblocked.
	 */
	public boolean blockersDeclared;

	private String name;

	/** Whose phase this is (whose turn it is as well). */
	public int ownerID;

	/** The steps in this phase. */
	public java.util.List<Step> steps;

	/** Steps in this phase that have been run. */
	public java.util.List<Step> stepsRan;

	public final PhaseType type;

	/**
	 * @param owner Who owns this phase.
	 * @param type What kind of phase this is.
	 */
	public Phase(Player owner, PhaseType type)
	{
		// order dependency
		this.type = type;
		this.setOwner(owner);
		this.addSteps(owner);

		this.blockersDeclared = false;

		this.stepsRan = new java.util.LinkedList<Step>();
	}

	private void addSteps(Player owner)
	{
		this.steps = this.type.steps(owner);
	}

	/** @return Who owns this phase. */
	@Override
	public Player getOwner(GameState state)
	{
		return state.get(this.ownerID);
	}

	/**
	 * Enables convenient iteration over the steps in this phase.
	 * 
	 * @return An iterator over the steps in this phase.
	 */
	@Override
	public java.util.Iterator<Step> iterator()
	{
		return this.steps.iterator();
	}

	/** Tells this phase who owns it. */
	public void setOwner(Player owner)
	{
		if(owner == null)
			throw new UnsupportedOperationException("Null phase owner.");
		this.name = owner + "'s " + this.type + " phase";
		this.ownerID = owner.ID;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
