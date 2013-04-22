package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * Represents instructions for determining whether a Set matches some criteria.
 * 
 * Classes implementing this interface should not be stateful. If you don't know
 * what that means (and you probably don't), talk to CommsGuy.
 */
public interface SetPattern
{
	public static final SetPattern ALWAYS_MATCH = new SetPattern()
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			return true;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// Nothing to freeze
		}
	};

	public static final SetPattern CASTABLE = new SetPattern()
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			return (null != Identity.instance(set).evaluate(state, thisObject).getOne(Castable.class));
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// Nothing to freeze
		}
	};

	public static final SetPattern EVERYTHING = new SetPattern()
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			// "Everything" isn't "nothing".
			return !set.isEmpty();
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// Nothing to freeze
		}
	};
	public static final SetPattern NEVER_MATCH = new SetPattern()
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// Nothing to freeze
		}
	};

	public static final SetPattern NON_MANA_ACTIVATED_ABILITIES = new SetPattern()
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			Set mostRecent = Identity.instance(set).evaluate(state, thisObject);
			for(ActivatedAbility object: mostRecent.getAll(ActivatedAbility.class))
				if(!object.isManaAbility())
					return true;
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// Nothing to freeze
		}
	};

	/**
	 * Determines whether a given set matches this pattern.
	 * 
	 * @param state The game state to match in.
	 * @param thisObject The object to evaluate set generators with.
	 * @param set The set to match against.
	 * @return Whether the given set matches this pattern.
	 */
	public boolean match(GameState state, Identified thisObject, Set set);

	/**
	 * Protects this SetPattern from text change effects.
	 * 
	 * @param state The game state to evaluate in.
	 * @param thisObject The object to evaluate set generators with.
	 */
	public void freeze(GameState state, Identified thisObject);
}
