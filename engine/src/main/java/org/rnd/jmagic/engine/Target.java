package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

public class Target implements Cloneable, Sanitizable
{
	public static final class SingleZone extends Target
	{
		public SingleZone(SetGenerator filter, String name)
		{
			super(filter, name);
		}

		@Override
		public boolean checkSpecialRestrictions(GameState state, java.util.List<Target> choices)
		{
			int firstZone = -1;
			for(Target choice: choices)
			{
				int zoneID = state.<GameObject>get(choice.targetID).zoneID;
				if(-1 == firstZone)
					firstZone = zoneID;
				else if(zoneID != firstZone)
					return false;
			}
			return true;
		}
	}

	/**
	 * A {@link SetGenerator} that will evaluate to the player who chooses this
	 * target. null means the controller of the object with this target will
	 * choose.
	 */
	public final SetGenerator chooser;

	/**
	 * When this target is to be chosen. Mostly used for kicker spells that only
	 * have their targets when kicked.
	 */
	public SetGenerator condition;

	/**
	 * The number assigned to this target, if there is one. This is used for
	 * combat damage and for spells and abilities which ask a player to divide a
	 * quantity.
	 */
	public int division;

	public SetGenerator legalChoices;

	// This is only used for SanitizedTarget's toString, which, at the time of
	// this comment, is only used when adding choices made by a player to Play's
	// log.
	public String name;

	/**
	 * Represents the number of times this target can be chosen. Must evaluate
	 * to a NumberRange.
	 */
	protected SetGenerator number;

	/**
	 * This is used to tell GameObject.generateTargets to remove this target
	 * from the remaining choices made for this object. For an example, see Cone
	 * of Flame.
	 */
	public boolean restrictFromLaterTargets;

	public int targetID;

	/**
	 * Create a fake-target for the purposes for damage assignment with an
	 * unknown amount of damage assigned to it
	 * 
	 * @param target What this target is "targeting".
	 */
	public Target(Identified target)
	{
		this(target, -1);
	}

	/**
	 * Create a fake-target for the purposes for damage assignment
	 * 
	 * @param target What this target is "targeting".
	 * @param division What quantity this target received during the damage
	 * assignment.
	 */
	Target(Identified target, int division)
	{
		this.chooser = null;
		this.condition = org.rnd.jmagic.engine.generators.NonEmpty.instance();
		this.division = division;
		this.legalChoices = null;
		this.name = target.getName();
		this.number = Between.instance(1, 1);
		this.restrictFromLaterTargets = false;
		this.targetID = target.ID;
	}

	/**
	 * Create a target targeting a single target using the properties from
	 * another Target object. This is used to create each option for choosing
	 * initial targets of a new spell or ability and for new targets of existing
	 * spells or abilities.
	 * 
	 * @param target What this target is targeting.
	 * @param properties The target this target was "made from". Used to check
	 * if the target is legal as a spell resolves, and also to copy division
	 * amounts (so that writing a card like Cone of Flame is intuitive).
	 */
	public Target(Identified target, Target properties)
	{
		this.chooser = properties.chooser;
		this.condition = org.rnd.jmagic.engine.generators.NonEmpty.instance();
		this.division = properties.division;
		this.legalChoices = properties.legalChoices;
		// Because this constructor is used for options for unchosen targets, we
		// need to maintain the original name of the target.
		this.name = properties.name;
		this.number = Between.instance(1, 1);
		this.restrictFromLaterTargets = properties.restrictFromLaterTargets;
		this.targetID = target.ID;
	}

	/**
	 * Create a target that hasn't been chosen yet
	 * 
	 * @param legalChoices A set generator that evaluates to the legal choices
	 * for this target.
	 * @param name The name of the target, for example "target creature".
	 */
	protected Target(SetGenerator legalChoices, String name)
	{
		this(legalChoices, null, name);
	}

	/**
	 * Create a target that hasn't been chosen yet
	 * 
	 * @param legalChoices A set generator that evaluates to the legal choices
	 * for this target.
	 * @param chooser A setgenerator representing the player who will choose
	 * this target, or null for the default.
	 */
	public Target(SetGenerator legalChoices, SetGenerator chooser, String name)
	{
		this.chooser = chooser;
		this.condition = org.rnd.jmagic.engine.generators.NonEmpty.instance();
		this.division = -1;
		this.legalChoices = legalChoices;
		this.name = name;
		this.number = Between.instance(1, 1);
		this.restrictFromLaterTargets = false;
		this.targetID = -1;
	}

	/**
	 * @param game The game in which to check.
	 * @param thisObject The object to be used when a target refers to the
	 * object it's on.
	 * @return Whether this target currently has any legal choices.
	 */
	public boolean canBeChosen(Game game, GameObject thisObject)
	{
		org.rnd.util.NumberRange range = this.number.evaluate(game, thisObject).getOne(org.rnd.util.NumberRange.class);
		if(range.getLower(0) == 0)
			return true;
		int numLegalTargets = this.legalChoicesNow(game, thisObject).size();
		return range.getLower(0) <= numLegalTargets;
	}

	/** Java-copies this target. */
	@Override
	public Target clone()
	{
		try
		{
			return (Target)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}

	/**
	 * This method is used to express targeting restrictions that are too
	 * complicated to express otherwise. Usually, targeting restrictions can
	 * easily be expressed via the SetGenerator describing the
	 * {@link #legalChoices} for the target, the target's {@link #number}, and
	 * the {@link #restrictFromLaterTargets} flag. If those things are not
	 * sufficient, you may override this method on your target.
	 * 
	 * See {@link org.rnd.jmagic.cards.DiluvianPrimordial} for an example of
	 * this.
	 * 
	 * @param state The game state to check the choices in
	 * @param choices The potential list of objects/players/zones chosen for
	 * this target.
	 * @return whether the potential list of declared targets is legal.
	 */
	public boolean checkSpecialRestrictions(GameState state, java.util.List<Target> choices)
	{
		return true;
	}

	/**
	 * If you think you need to override this method, you probably want to
	 * override {@link #checkSpecialRestrictions(java.util.List)} instead.
	 * 
	 * @param game The game in which to check.
	 * @param thisObject The object that should be used for any This generators.
	 * @return Whether the thing this is targeting is a legal choice.
	 */
	public final boolean isLegal(Game game, GameObject thisObject)
	{
		if(this.targetID == -1)
			return false;

		Set legalChoicesNow = this.legalChoicesNow(game, thisObject);

		for(GameObject object: legalChoicesNow.getAll(GameObject.class))
			if((this.targetID == object.ID) && !object.isGhost())
				return true;

		for(Player player: legalChoicesNow.getAll(Player.class))
			if(this.targetID == player.ID)
				return true;

		for(Zone zone: legalChoicesNow.getAll(Zone.class))
			if(this.targetID == zone.ID)
				return true;

		return false;
	}

	/**
	 * Returns a set describing the legal choices for this target, with all
	 * untargetable objects removed from the set.
	 * 
	 * @param targeting What is doing the targeting; things not targetable by
	 * this object will not be in the returned set.
	 * @return A set describing the legal choices for this target.
	 */
	public Set legalChoicesNow(Game game, GameObject targeting)
	{
		Set ret = this.legalChoices.evaluate(game, targeting);

		java.util.Collection<Targetable> toRemove = new java.util.LinkedList<Targetable>();
		for(Targetable t: ret.getAll(Targetable.class))
		{
			if(t.cantBeTheTargetOf().match(game.actualState, (Identified)t, new Set(targeting)))
				toRemove.add(t);
		}
		ret.removeAll(toRemove);
		ret.remove(targeting); // can't target itself

		return ret;
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedTarget(this);
	}

	/**
	 * Tells this target how many times it can be selected. Different
	 * objects/players/zones must be selected each time (for cards like Seeds of
	 * Strength, use three different targets).
	 * 
	 * @param lower The minimum number of times it can be selected.
	 * @param upper The maximum number of times it can be selected. For spells
	 * that say "any number" and don't ask the player to divide damage, use
	 * null. For spells that say "any number" and ask the player to divide
	 * damage, use the amount of damage being divided.
	 */
	public void setNumber(int lower, Integer upper)
	{
		this.number = Between.instance(lower, upper);
	}

	/**
	 * Tells this target how many times it can be selected. Different
	 * objects/players/zones must be selected each time (for cards like Seeds of
	 * Strength, use three different targets).
	 * 
	 * @param N A generator <i>evaluating to a single number</i>.
	 */
	public void setSingleNumber(SetGenerator N)
	{
		this.number = Between.instance(N, N);
	}

	/**
	 * Tells this target how many times it can be selected. Different
	 * objects/players/zones must be selected each time (for cards like Seeds of
	 * Strength, use three different targets).
	 * 
	 * @param range A generator evaluating to a NumberRange.
	 */
	public void setRange(SetGenerator range)
	{
		this.number = range;
	}

	/** @return A string representation of this target. */
	@Override
	public String toString()
	{
		return this.name;
	}
}
