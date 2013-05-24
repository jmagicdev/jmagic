package org.rnd.jmagic.engine;

/**
 * A class must extend Identified and be referred to by ID if it meets the
 * following criteria:
 * <p>
 * 1. An instance of that class can change if an action fails to perform.
 * <p>
 * 2. A GameState holds a reference to an instance of that class.
 * <p>
 * For example, GameObject satisfies both criteria:
 * <p>
 * 1. A GameObject instance's tapped state can change when performing a
 * CastSpellOrActivateAbility action and must change back if the action fails.
 * <p>
 * 2. Zone instances (which are part of a GameState) hold references to
 * GameObject instances.
 * <p>
 * Thus, GameObject must extend Identified and instances must be referred to by
 * ID rather than by direct references. This allows a GameState to be reverted
 * by replacing GameObject instances with backed-up instances of the original
 * GameObject while preserving references to the "right" instance.
 */
abstract public class Identified implements Cloneable, Comparable<Identified>
{
	public Game game;
	public final int ID;
	private String name;
	public GameState state;

	/**
	 * @param state The game state in which this identified should be put
	 * @param name The name of this thing
	 */
	Identified(GameState state)
	{
		this.game = state.game;
		this.state = state;
		this.ID = this.game.getNextAvailableID();
		this.name = null;

		state.put(this);
	}

	public boolean attackable()
	{
		return false;
	}

	/**
	 * Even though Identified implements Cloneable, do not call
	 * Identified.clone(). Use Identified.clone(GameState) instead.
	 */
	@Deprecated
	@Override
	public Identified clone()
	{
		throw new UnsupportedOperationException("Using old clone on " + this + ". Use Identified.clone(GameState) instead.");
	}

	/**
	 * Make a copy of this object without having to know exactly what kind of
	 * object it is, and store that copy in that state (via
	 * {@link GameState#put(Identified)}.
	 * 
	 * @param state The GameState to store the clone in
	 */
	public Identified clone(GameState state)
	{
		try
		{
			Identified ret = (Identified)super.clone();
			ret.state = state;
			state.put(ret);
			return ret;
		}
		catch(CloneNotSupportedException ex)
		{
			throw new InternalError("Clone not supported: " + this);
		}
	}

	/**
	 * @return this.ID - compare.ID
	 */
	@Override
	public int compareTo(Identified compare)
	{
		return this.ID - compare.ID;
	}

	/**
	 * Puts a complete copy of this object into the actual state. Only override
	 * this if you have Identified members who need to have copy() called, and
	 * be sure to call super.copy()
	 */
	public void copy()
	{
		if(this.game.actualState != null)
			this.clone(this.game.actualState);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(null == obj)
			return false;
		if(getClass() != obj.getClass())
			return false;
		if(this.ID != ((Identified)obj).ID)
			return false;
		return true;
	}

	public Identified getActual()
	{
		if(this.state == this.game.actualState)
			return this;
		return this.game.actualState.get(this.ID);
	}

	public String getName()
	{
		if(this.name == null)
		{
			this.name = org.rnd.jmagic.Convenience.getName(this.getClass());
			if(this.name == null)
				throw new ClassFormatError("Class is missing @Name annotation or call to setName(String): " + this.getClass().getName());
		}
		return this.name;
	}

	public Identified getPhysical()
	{
		if(this.state == this.game.physicalState)
			return this;
		return this.game.physicalState.get(this.ID);
	}

	// We only override this because a.equals(b) implies (a.hashCode() ==
	// b.hashCode())
	@Override
	public int hashCode()
	{
		return /*31 + */this.ID;
	}

	/** @return Whether this is a GameObject. */
	public boolean isGameObject()
	{
		return false;
	}

	/** @return Whether this is a Keyword */
	public boolean isKeyword()
	{
		return false;
	}

	/** @return Whether this is a Player. */
	public boolean isPlayer()
	{
		return false;
	}

	protected void setName(String name)
	{
		if(name == null)
			throw new IllegalArgumentException("Name is null");

		this.name = name;
	}

	/**
	 * @return The name of this Identified
	 */
	@Override
	public String toString()
	{
		return this.getName();
	}

	/** Whether this is a permanent (that is, an object on the battlefield). */
	public boolean isPermanent()
	{
		return false;
	}

	/** @return Whether this object is an activated ability. */
	public boolean isActivatedAbility()
	{
		return false;
	}

	/** @return Whether this object is a triggered ability. */
	public boolean isTriggeredAbility()
	{
		return false;
	}

	/** @return Whether this object is a static ability. */
	public boolean isStaticAbility()
	{
		return false;
	}
}
