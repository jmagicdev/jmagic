package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;

public class Mode implements Sanitizable
{
	/**
	 * Represents the number the player is asked to divide and what kind of
	 * thing they are dividing ("damage", "counters", etc) when they announce a
	 * spell/ability containing this mode.
	 */
	public SetGenerator division;

	public java.util.List<EventFactory> effects;

	public final int sourceID;

	/**
	 * Any targets referenced by effects in this mode. These targets should not
	 * be changed when targets are "solidified".
	 */
	public java.util.List<Target> targets;

	/**
	 * Constructs a mode with no effects.
	 */
	public Mode(int sourceID)
	{
		this.division = numberGenerator(0);
		this.effects = new java.util.LinkedList<EventFactory>();
		this.sourceID = sourceID;
		this.targets = new java.util.LinkedList<Target>();
	}

	public Mode(Mode copy, int sourceID)
	{
		this(sourceID);

		this.division = copy.division;
		// These need to be shallow copies so that effects that refer to targets
		// still refer to the right ones
		this.effects.addAll(copy.effects);
		this.targets.addAll(copy.targets);
	}

	/**
	 * Adds an effect to this mode.
	 * 
	 * @param e The effect to add.
	 */
	public void addEffect(EventFactory e)
	{
		this.effects.add(e);
	}

	/**
	 * Adds a target to this mode.
	 * 
	 * @param t The target to add.
	 */
	public void addTarget(Target t)
	{
		this.targets.add(t);
	}

	/**
	 * @param game The game in which to check.
	 * @param object The object the mode should use when resolving 'this'.
	 * @return Whether this mode can have all its targets chosen.
	 */
	public boolean canBeChosen(Game game, GameObject object)
	{
		if(org.rnd.jmagic.abilities.keywords.Overload.WasOverloaded.get(object))
			return true;

		for(Target t: this.targets)
			if(!t.canBeChosen(game, object))
				return false;

		return true;
	}

	/**
	 * Gets an effect from this mode.
	 * 
	 * @param index Which effect to get. The first effect is 1.
	 * @return The effect.
	 */
	public EventFactory getEffect(int index)
	{
		return this.effects.get(index - 1);
	}

	/**
	 * Gets a target from this mode.
	 * 
	 * @param source The object which added the Target
	 * @param index Which target to get. The first target is 1.
	 * @return The target.
	 */
	public Target getTarget(Identified source, int index)
	{
		return this.targets.get(index - 1);
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedMode(this, this.sourceID, state.<GameObject>get(this.sourceID).getModes().indexOf(this));
	}

	/**
	 * toString is being overloaded because the text of the effects of this mode
	 * (and thus the desired output) are not known at object creation, so we
	 * can't set the objects name to them. Instead, just output text formatted
	 * similarly to Identified.toString.
	 */
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder("(");
		for(EventFactory e: this.effects)
			ret.append((ret.length() > 0 ? "; " : "") + e.name);
		ret.append(")");
		return ret.toString();
	}
}
